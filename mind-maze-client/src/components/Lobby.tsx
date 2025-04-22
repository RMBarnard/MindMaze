import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { GameSocketClient } from "../GameSocketClient";
import { ServerToClientMessage, ClientToServerMessage } from "../types";
import { getOrCreateUserId } from "../getOrCreateUserId";

function Lobby() {
  const { joinCode } = useParams();
  const [players, setPlayers] = useState<string[]>([]);
  const socketRef = useRef<GameSocketClient | null>(null);

  useEffect(() => {
    const userId = getOrCreateUserId();
    const client = new GameSocketClient(userId, handleMessage);
    socketRef.current = client;

    client.send({
      type: 10,
      payloadJson: JSON.stringify({ joinCode }),
    });

    return () => client.close();
  }, [joinCode]);

  const handleMessage = (msg: ServerToClientMessage) => {
    if (msg.type === 11) {
      const data = JSON.parse(msg.payloadJson);
      setPlayers(data.players); // Expecting `players: string[]` in payload
    }
  };

  return (
    <div>
      <h2>Lobby: {joinCode}</h2>
      <h3>Players:</h3>
      <ul>
        {players.map((p, i) => (
          <li key={i}>{p}</li>
        ))}
      </ul>
    </div>
  );
}

export default Lobby;

