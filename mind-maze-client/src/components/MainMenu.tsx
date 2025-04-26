import { useEffect, useContext, useState } from "react";
import { WebSocketContext } from "../WebSocketProvider";
import { NewGameResponse, ServerToClientMessage, JoinGameResponse } from "../types";
import { useNavigate } from "react-router-dom";

function MainMenu() {
  const navigate = useNavigate();
  const [joinId, setJoinCode] = useState("");
  const socketClient = useContext(WebSocketContext);

  useEffect(() => {
    if (!socketClient) return;

    const handleMessage = (msg: ServerToClientMessage) => {
      if (msg.typeId === 2 || msg.typeId === 4) {
        console.log("MainMenu received:", msg);
        const response: NewGameResponse = JSON.parse(msg.payloadJson);
        if (response.success) {
          navigate(`/lobby/${response.joinCode}`);
        };
      }
    }

    socketClient.subscribe(handleMessage);

    return () => {
      socketClient.unsubscribe(handleMessage);
    };
  }, [socketClient]);

  const createGame = (mapSize: string) => {
    socketClient?.send({
      typeId: 1, // NEW_GAME_REQUEST
      payloadJson: JSON.stringify({ mapSize }),
    });
  };

  const joinGame = () => {
    socketClient?.send({
      typeId: 3,
      payloadJson: JSON.stringify({ joinId }),
    });
  };

  return (
    <div>
      <h1>MindMaze</h1>
      <button onClick={() => createGame("small")}>New Game (Small)</button>
      <button onClick={() => createGame("medium")}>New Game (Medium)</button>
      <button onClick={() => createGame("large")}>New Game (Large)</button>

      <h2>Join Game</h2>
      <input
        type="text"
        placeholder="Enter Join Code"
        value={joinId}
        onChange={(e) => setJoinCode(e.target.value)}
      />
      <button onClick={joinGame}>Join Game</button>
    </div>
  );
}

export default MainMenu;

