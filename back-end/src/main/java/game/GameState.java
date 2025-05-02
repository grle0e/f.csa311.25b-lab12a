package game;

import java.util.*;

public class GameState {
    private final Cell[] cells;
    private final String instructions;
    private final boolean winner;

    private GameState(Cell[] cells, String instructions, boolean winner) {
        this.cells = cells;
        this.instructions = instructions;
        this.winner = winner;
    }

    public static GameState forGame(Game game) {
        List<int[]> winningLine = getWinningLine(game.getBoard());
        boolean hasWinner = winningLine != null;
        Cell[] cells = getCells(game.getBoard(), game.getPlayer(), winningLine);

        String instructions;
        if (hasWinner) {
            instructions = "Winner: " + (game.getWinner() == Player.PLAYER0 ? "X" : "O");
        } else if (Arrays.stream(cells).noneMatch(Cell::isPlayable)) {
            instructions = "Draw!";
        } else {
            instructions = "Current player: " + (game.getPlayer() == Player.PLAYER0 ? "X" : "O");
        }

        return new GameState(cells, instructions, hasWinner);
    }

    public static Player getWinner(Board board) {
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        Player[] cells = board.getCells();
        for (int[] line : lines) {
            Player p = cells[line[0]];
            if (p != null && p == cells[line[1]] && p == cells[line[2]]) {
                return p;
            }
        }
        return null;
    }

    private static List<int[]> getWinningLine(Board board) {
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        Player[] cells = board.getCells();
        for (int[] line : lines) {
            Player p = cells[line[0]];
            if (p != null && p == cells[line[1]] && p == cells[line[2]]) {
                return List.of(
                    new int[]{line[0] % 3, line[0] / 3},
                    new int[]{line[1] % 3, line[1] / 3},
                    new int[]{line[2] % 3, line[2] / 3}
                );
            }
        }
        return null;
    }

    private static Cell[] getCells(Board board, Player nextPlayer, List<int[]> winningLine) {
        Cell[] cells = new Cell[9];
        Set<String> winSet = new HashSet<>();
        if (winningLine != null) {
            for (int[] p : winningLine) winSet.add(p[0] + "," + p[1]);
        }
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                String text = "";
                boolean playable = false;
                boolean isWinner = winSet.contains(x + "," + y);
                Player player = board.getCell(x, y);
                if (player == Player.PLAYER0) text = "X";
                else if (player == Player.PLAYER1) text = "O";
                else if (player == null) playable = true;
                cells[3 * y + x] = new Cell(x, y, text, playable, isWinner);
            }
        }
        return cells;
    }

    @Override
    public String toString() {
        return """
            {
              "cells": %s,
              "instructions": "%s",
              "winner": %b
            }
        """.formatted(Arrays.toString(this.cells), this.instructions, this.winner);
    }
}

class Cell {
    private final int x, y;
    private final String text;
    private final boolean playable;
    private final boolean winner;

    Cell(int x, int y, String text, boolean playable, boolean winner) {
        this.x = x; this.y = y; this.text = text; this.playable = playable; this.winner = winner;
    }

    public boolean isPlayable() { return playable; }

    @Override
    public String toString() {
        return """
            {"text": "%s", "playable": %b, "x": %d, "y": %d, "winner": %b}
        """.formatted(this.text, this.playable, this.x, this.y, this.winner);
    }
}
