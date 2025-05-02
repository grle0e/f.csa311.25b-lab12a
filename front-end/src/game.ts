export interface GameState {
  cells: Cell[];
  instructions: string;
  winner: boolean;
}

export interface Cell {
  text: string;
  playable: boolean;
  x: number;
  y: number;
  winner?: boolean;
} 
