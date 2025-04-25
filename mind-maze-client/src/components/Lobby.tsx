import { useContext, useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { GameSocketClient } from "../GameSocketClient";
import { ServerToClientMessage, ClientToServerMessage } from "../types";
import { getOrCreateUserId } from "../getOrCreateUserId";
import { WebSocketContext } from "../WebSocketProvider";

function Lobby() {
  const { joinCode } = useParams();
  const [players, setPlayers] = useState<string[]>([]);
  const [mapSize, setMapSize] = useState<string>("");
  const socket = useContext(WebSocketContext);

  useEffect(() => {
    socket?.send({
      typeId: 10,
      payloadJson: JSON.stringify({ joinCode }),
    });

    const handleMessage = (msg: ServerToClientMessage) => {
      console.log("received message " + JSON.stringify(msg));
      if (msg.typeId === 12) {
        const data = JSON.parse(msg.payloadJson);
        setPlayers(data.joinedPlayerIds); // Expecting `players: string[]` in payload
        setMapSize(data.mapSize);
      }
    };

    socket?.subscribe(handleMessage)

  }, [joinCode]);

  return (
    <div>
      <h2>Lobby: {joinCode}</h2>
      <h3>Map Size: {mapSize}</h3>
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

