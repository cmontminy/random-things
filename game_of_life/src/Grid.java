import java.util.ArrayList;
import processing.core.PApplet;

public class Grid {

    private final static int DEFAULT_SIZE = 10;
    
    private PApplet p;
    private Square[][] board;
    private int xNum;
    private int yNum;
    private int xSize;
    private int ySize;

    public Grid(PApplet p) {
        this(DEFAULT_SIZE, DEFAULT_SIZE, p);
    }

    public Grid(int xNum, int yNum, PApplet p) {
    	this.p = p;
        this.xNum = xNum;
        this.yNum = yNum;
        xSize = p.width / xNum;
        ySize = p.height / yNum;
        board = new Square[xNum][yNum];
        makeBoard();
        for (int i = 0; i < 100; i++) {
            int randX = (int) p.random(getXNum());
            int randY = (int) p.random(getYNum());
            updateState(randX, randY, true);
        }
    }
    
    public void updateSquares() {
        Grid newBoard = new Grid(getXNum(), getYNum(), p);
        for (int y = 0; y < yNum; y++) {
            for (int x = 0; x < xNum; x++) {
                Square[] currentNeighbors = getNeighbors(x, y);
                int numSquares = currentNeighbors.length;
                if (getState(x, y)) {
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
        this.board = newBoard.board;
        // System.out.println("UPDATED BOARD");
        // board.printBoard();
    }

    public void drawBoard() {
        boolean[][] squareStatus = getStates();
        for (int x = 0; x < xNum; x++) {
            for (int y = 0; y < yNum; y++) {
                if (squareStatus[x][y]) {
                    p.rect(x * xSize, y * ySize, xSize, ySize);
                } else {
                	p.fill(255);
                    p.rect(x * xSize, y * ySize, xSize, ySize);
                    p.fill(0);
                }
            }
        }
    }

    public int getXNum() {
        return xNum;
    }

    public int getYNum() {
        return yNum;
    }

    public boolean[][] getStates() {
        boolean[][] states = new boolean[xNum][yNum];
        for (int x = 0; x < xNum; x++) {
            for (int y = 0; y < yNum; y++) {
                states[x][y] = board[x][y].getState();
            }
        }
        return states;
    }
    
    

    public Square[] getNeighbors(int x, int y) {
        ArrayList<Square> neighbors = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0)) {
                    if (x + i >= 0 && y + j >= 0 && x + i < board.length && y + j < board[0].length) {
                        if (board[x + i][y + j].getState()) {
                            neighbors.add(board[x + i][y + j]);
                        }
                    }
                }
            }
        }

        Square[] result = new Square[neighbors.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = neighbors.get(i);
        }
        return result;
    }

    public boolean getState(int x, int y) {
        return board[x][y].getState();
    }

    public void updateState(int x, int y, boolean status) {
        board[x][y].setState(status);
    }

    public void printBoard() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[x][y].getState()) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void makeBoard() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                board[x][y] = new Square(x, y, false);
            }
        }
    }
}