import { useNavigate } from "react-router-dom";
import { GameSocketClient } from "../GameSocketClient";
import { useEffect, useRef } from "react";
import { ServerToClientMessage, ClientToServerMessage } from "../types";
import { getOrCreateUserId } from "../getOrCreateUserId";


function MainMenu() {
  const navigate = useNavigate();
  const socketRef = useRef<GameSocketClient | null>(null);

  useEffect(() => {
    const userId = getOrCreateUserId();
    const client = new GameSocketClient(userId, handleMessage);
    socketRef.current = client;
    return () => client.close();
  }, []);

  const handleMessage = (msg: ServerToClientMessage) => {
    if (msg.typeId === 2) {
      const response: NewGameResponse = JSON.parse(msg.payloadJson);
      console.log(response);
      if (response.success) {
        navigate(`/lobby/${response.joinCode}`);
      } else {
        alert("Failed to create game.");
      }
    }
  };

  const createGame = (size: string) => {
    const payload = JSON.stringify({ mapSize: size });
    socketRef.current?.send({
      typeId: 1,
      payloadJson: payload,
    });
  };

  return (
    <div>
      <h1>MindMaze</h1>
      <p>Select a map size to start a new game:</p>
      <button onClick={() => createGame("small")}>Small</button>
      <button onClick={() => createGame("medium")}>Medium</button>
      <button onClick={() => createGame("large")}>Large</button>
    </div>
  );
}

export default MainMenu;

