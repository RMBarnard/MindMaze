import { ClientToServerMessage, ServerToClientMessage } from "./types"

type MessageHandler = (msg: ServerToClientMessage) => void;

export class GameSocketClient {
  private ws: WebSocket;
  private listeners: Set<MessageHandler> = new Set();

  constructor(userId: number) {
    const url = `ws://${window.location.hostname}:8080/game/${userId}`;
    this.ws = new WebSocket(url);

    this.ws.onopen = () => {
      console.log("WebSocket connected");
    };

    this.ws.onmessage = (event) => {
      console.log("Received message " + event.data);
      try {
        const data: ServerToClientMessage = JSON.parse(event.data);
        for (const listener of this.listeners) {
          listener(data);
        }
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
      this.ws.send(JSON.stringify(message));
    } else {
      console.warn("WebSocket not open");
    }
  }

  subscribe(handler: MessageHandler) {
    this.listeners.add(handler);
  }

  unsubscribe(handler: MessageHandler) {
    this.listeners.delete(handler);
  }

  close() {
    this.ws.close();
  }
}
