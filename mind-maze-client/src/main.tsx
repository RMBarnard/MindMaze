import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import MainMenu from "./components/MainMenu";
import Lobby from "./components/Lobby";
import { WebSocketProvider } from "./WebSocketProvider";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <WebSocketProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainMenu />} />
          <Route path="/lobby/:joinCode" element={<Lobby />} />
        </Routes>
      </BrowserRouter>
    </WebSocketProvider>
  </React.StrictMode>
);
