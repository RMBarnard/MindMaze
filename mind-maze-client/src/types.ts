export type NewGameRequest = { mapSize: string };
export type JoinGameRequest = { joinCode: string; playerCount: number; mapSize: string };

export type ClientToServerMessage = { typeId: number; payloadJson: string }

export type NewGameResponse = { success: boolean; joinCode: string };

export type ServerToClientMessage = { typeId: number; payloadJson: string }
