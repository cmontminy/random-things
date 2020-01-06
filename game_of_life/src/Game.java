import processing.core.PApplet;

public class Game extends PApplet {

    private static Grid board;
    private static int pixelSize = 50;
    private static int xSize = pixelSize * 38;
    private static int ySize = pixelSize * 19;

    public static void main(String[] args) {
        PApplet.main("Game");
    }
    
    public void settings() {
    	size(xSize, ySize);
    }
    
    public void setup() {
        System.out.println("Launching game with a board of size " + xSize + " x " + ySize);
        startGame(xSize, ySize);	
    }
    
    public void draw() {
    	updateSquares();
    	drawBoard();
    }
    
    private void startGame(int x, int y) {
        board = new Grid(x / pixelSize, y / pixelSize);
        makeRandomSquares(100);
    }

    private void makeRandomSquares(int numSquares) {
        for (int i = 0; i < numSquares; i++) {
            int randX = (int) random(board.getXSize());
            int randY = (int) random(board.getYSize());
            board.updateState(randX, randY, true);
        }
    }

    private void updateSquares() {
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

    private void drawBoard() {
        boolean[][] squareStatus = board.getStates();
        for (int x = 0; x < board.getXSize(); x++) {
            for (int y = 0; y < board.getYSize(); y++) {
                if (squareStatus[x][y]) {
                	fill(0);
                    rect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                } else {
                    fill(255);
                    rect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                }
            }
        }
    }
}