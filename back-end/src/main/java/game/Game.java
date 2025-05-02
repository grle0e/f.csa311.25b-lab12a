package game;

import java.util.*;

enum Player {
    PLAYER0(0), PLAYER1(1);
    final int value;
    Player(int value) { this.value = value; }
}

public class Game {
    private final Board board;
    private final Player player;
    private final List<Game> history;

    public Game() {
        this(new Board(), Player.PLAYER0);
    }

    public Game(Board board, Player nextPlayer) {
        this(board, nextPlayer, List.of());
    }

    public Game(Board board, Player nextPlayer, List<Game> history) {
        this.board = board;
        this.player = nextPlayer;
        this.history = history;
    }

    public Board getBoard() { return this.board; }
    public Player getPlayer() { return this.player; }

    public Game play(int x, int y) {
        if (this.board.getCell(x, y) != null || this.getWinner() != null)
            return this;
        List<Game> newHistory = new ArrayList<>(this.history);
        newHistory.add(this);
        Player nextPlayer = this.player == Player.PLAYER0 ? Player.PLAYER1 : Player.PLAYER0;
        return new Game(this.board.updateCell(x, y, this.player), nextPlayer, newHistory);
    }

    public Game undo() {
        if (this.history.isEmpty()) return this;
        return this.history.get(this.history.size() - 1);
    }

    public Player getWinner() {
        return GameState.getWinner(this.board);
    }
}
