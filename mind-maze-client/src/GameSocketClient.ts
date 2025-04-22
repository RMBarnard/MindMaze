import { ClientToServerMessage, ServerToClientMessage } from "./types";

type MessageHandler = (msg: ServerToClientMessage) => void;

export class GameSocketClient {
  private ws: WebSocket;
  private handler: MessageHandler;

  constructor(userId: number, handler: MessageHandler) {
    const url = `ws://${window.location.hostname}:8080/game/${userId}`;
    this.ws = new WebSocket(url);
    this.handler = handler;

    this.ws.onopen = () => {
      console.log("WebSocket connected");
    };

    this.ws.onmessage = (event) => {
      try {
        const data: ServerToClientMessage = JSON.parse(event.data);
        this.handler(data);
      } catch (err) {
        console.error("Invalid message from server", err);
      }
    };

    this.ws.onclose = () => {
      console.log("WebSocket disconnected");
    };

    this.ws.onerror = (err) => {
      console.error("WebSocket error", err);
    };
  }

  send(message: ClientToServerMessage) {
    if (this.ws.readyState === WebSocket.OPEN) {
      console.log("Sending message: " + JSON.stringify(message));
      this.ws.send(JSON.stringify(message));
    } else {
      console.warn("WebSocket not open");
    }
  }

  close() {
    this.ws.close();
  }
}
