package com.davidread.lightsout;

import java.util.Random;

/**
 * {@link LightsOutGame} is a model class for the Lights Out game. The game involves a grid of
 * randomly initialized light/dark cells. When a cell is selected, itself and adjacent cells are
 * inverted. The goal of the game is to darken all cells in the grid in as few clicks as possible.
 */
public class LightsOutGame {

    /**
     * Int constant for board grid size.
     */
    public static final int GRID_SIZE = 3;

    /**
     * 2D boolean array representing the board. True cells are lit up, false cells are dark.
     */
    private final boolean[][] lightsGrid;

    /**
     * Int to keep track of the number of clicks used in the game.
     */
    private int countClicks;

    /**
     * Constructs a new {@link LightsOutGame}.
     */
    public LightsOutGame() {
        lightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    /**
     * Restarts the game with a new randomly generated {@link #lightsGrid}.
     */
    public void newGame() {
        Random randomNumGenerator = new Random();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                lightsGrid[row][col] = randomNumGenerator.nextBoolean();
            }
        }
        countClicks = 0;
    }

    /**
     * Returns true if the {@link #lightsGrid} cell at the given coordinates is lit.
     *
     * @param row Row coordinate.
     * @param col Column coordinate.
     */
    public boolean isLightOn(int row, int col) {
        return lightsGrid[row][col];
    }

    /**
     * Inverts the {@link #lightsGrid} cell at the given coordinates and all adjacent cells.
     *
     * @param row Row coordinate.
     * @param col Column coordinate.
     */
    public void selectLight(int row, int col) {
        lightsGrid[row][col] = !lightsGrid[row][col];
        if (row > 0) {
            lightsGrid[row - 1][col] = !lightsGrid[row - 1][col];
        }
        if (row < GRID_SIZE - 1) {
            lightsGrid[row + 1][col] = !lightsGrid[row + 1][col];
        }
        if (col > 0) {
            lightsGrid[row][col - 1] = !lightsGrid[row][col - 1];
        }
        if (col < GRID_SIZE - 1) {
            lightsGrid[row][col + 1] = !lightsGrid[row][col + 1];
        }
        countClicks++;
    }

    /**
     * Returns true if the game is over. This happens when all the lights are off.
     */
    public boolean isGameOver() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (lightsGrid[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the number of clicks used in this game.
     */
    public int getCountClicks() {
        return countClicks;
    }

    /**
     * Returns the state of {@link #lightsGrid} as a {@link String}.
     */
    public String getState() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                char value = lightsGrid[row][col] ? 'T' : 'F';
                stringBuilder.append(value);
            }
        }
        stringBuilder.append(countClicks);
        return stringBuilder.toString();
    }

    /**
     * Sets the state of {@link #lightsGrid} given a {@link String} generated using
     * {@link #getState()}.
     *
     * @param gameState A {@link String} generated using {@link #getState()}.
     */
    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                lightsGrid[row][col] = gameState.charAt(index) == 'T';
                index++;
            }
        }
        countClicks = Integer.parseInt(gameState.substring(index));
    }
}