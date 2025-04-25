import React, { createContext, useEffect, useState } from "react";
import { GameSocketClient } from "./GameSocketClient";
import { ServerToClientMessage } from "./types";
import { getOrCreateUserId } from "./getOrCreateUserId";

export const WebSocketContext = createContext<GameSocketClient | null>(null);

export const WebSocketProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [client, setClient] = useState<GameSocketClient | null>(null);

  useEffect(() => {
    const userId = getOrCreateUserId()
    const serverClient = new GameSocketClient(userId, (msg: ServerToClientMessage) => {
      console.log("Global WebSocket message:", msg);
    });

    setClient(serverClient);
    return () => {
      //console.log("Closing the client");
      //client.close();
    };
  }, []);

  return (
    <WebSocketContext.Provider value={client}>
      {children}
    </WebSocketContext.Provider>
  );
};
