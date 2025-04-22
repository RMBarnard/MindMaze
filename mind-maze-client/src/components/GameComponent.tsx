import { useEffect, useRef, useState } from "react";
import { GameSocketClient } from "../GameSocketClient";
import { ServerToClientMessage } from "../types";

function GameComponent({ userId }: { userId: number }) {
  const socketRef = useRef<GameSocketClient | null>(null);
  const [message, setMessage] = useState<string | null>(null);
  const [mapSize, setMapSize] = useState("small");

  useEffect(() => {
    const client = new GameSocketClient(userId, handleMessage);
    socketRef.current = client;

    return () => {
      if (client.ws.readyState === 1) {
        client.close();
      }
    };
  }, [userId]);

  const handleMessage = (message: ServerToClientMessage) => {
    console.log("Received from server:", message);
    setMessage(message.payloadJson);
  };

  const startNewGame = () => {
    const msg: ClientToServerMessage = {
      typeId: 1, // NEW_GAME_REQUEST
      payloadJson: JSON.stringify({ mapSize })
    };
    socketRef.current?.send(msg);
  };

  return (
    <div>
      <h2>Start New Game</h2>
      <select value={mapSize} onChange={(e) => setMapSize(e.target.value)}>
        <option value="small">Small</option>
        <option value="medium">Medium</option>
        <option value="large">Large</option>
      </select>
      <button onClick={startNewGame}>Start Game</button>
      <p>Message from server: {message ?? "Waiting for message..."}</p>
    </div>
  );
}

export default GameComponent;
