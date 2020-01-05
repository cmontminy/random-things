import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Game {

    private static Grid board;
    private static int pixelSize = 50;
    private static int xSize = pixelSize * 38;
    private static int ySize = pixelSize * 19;

    public static void main(String[] args) {
        System.out.println("Launching game with a board of size " + xSize + " x " + ySize);
        startGame(xSize, ySize);
    }

    private static void startGame(int x, int y) {
        board = new Grid(x / pixelSize, y / pixelSize);
        makeRandomSquares(100);
        updateGame(board);
    }

    private static void makeRandomSquares(int numSquares) {
        for (int i = 0; i < numSquares; i++) {
            Random rand = new Random();
            int randX = rand.nextInt(board.getXSize());
            int randY = rand.nextInt(board.getYSize());
            board.updateState(randX, randY, true);
        }
    }

    private static void updateGame(Grid board) {
        DrawingPanel p = new DrawingPanel(board.getXSize() * pixelSize, board.getYSize() * pixelSize);
        Graphics g = p.getGraphics();
        g.setColor(Color.BLACK);
        drawBoard(g);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            updateSquares();
            drawBoard(g);
            System.out.println("DONE");
        }
    }

    private static void updateSquares() {
        Grid newBoard = new Grid(board.getXSize(), board.getYSize());
        for (int y = 0; y < board.getYSize(); y++) {
            for (int x = 0; x < board.getXSize(); x++) {
                Square[] currentNeighbors = board.getNeighbors(x, y);
                int numSquares = currentNeighbors.length;
                if (board.getState(x, y)) {
                    if (numSquares == 2 || numSquares == 3) {
                        newBoard.updateState(x, y, true);
                    } else {
                        newBoard.updateState(x, y, false);
                    }
                } else {
                    if (numSquares == 3) {
                        newBoard.updateState(x, y, true);
                    } else {
                        newBoard.updateState(x, y, false);
                    }
                }
            }
        }
        // System.out.println("NEW BOARD");
        // newBoard.printBoard();
        board = newBoard;
        // System.out.println("UPDATED BOARD");
        // board.printBoard();
    }

    private static void drawBoard(Graphics g) {
        boolean[][] squareStatus = board.getStates();
        for (int x = 0; x < board.getXSize(); x++) {
            for (int y = 0; y < board.getYSize(); y++) {
                if (squareStatus[x][y]) {
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                    g.setColor(Color.BLACK);
                }
            }
        }
    }
}