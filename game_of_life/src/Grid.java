import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;

public class Grid {

    private PApplet p;
    private Square[][] board;
    private Button playButton;
    private int xNum;
    private int yNum;
    private int xSize;
    private int ySize;
    private boolean isActive;
    private boolean prevPressed;

    public Grid(PApplet p) {
    	this.p = p;
    	playButton = new Button(p, LConstants.playButtonX, LConstants.playButtonY, LConstants.playButtonWidth,
    			LConstants.playButtonHeight, LConstants.playButtonText);
    	playButton.setActive(true);
        this.xNum = LConstants.screenWidth / LConstants.pixelSize;
        this.yNum = LConstants.screenHeight / LConstants.pixelSize;
        xSize = p.width / xNum;
        ySize = (p.height - LConstants.menuHeight) / yNum;
        board = new Square[xNum][yNum];
       
        makeBoard();
//        for (int i = 0; i < 100; i++) {
//            int randX = (int) p.random(getXNum());
//            int randY = (int) p.random(getYNum());
//            updateState(randX, randY, true);
//        }
    }

    private void handleButtons() {
    	if (playButton.isClicked()) {
    		isActive = !isActive;
    	}
    }
    
    private void handleMouseInput() {
    	int pXPos = p.pmouseX / LConstants.pixelSize;
    	int pYPos = (p.pmouseY - LConstants.menuHeight) / LConstants.pixelSize;
    	int xPos = p.mouseX / LConstants.pixelSize;
    	int yPos = (p.mouseY - LConstants.menuHeight) / LConstants.pixelSize;
    	if ((!prevPressed || (pXPos != xPos || pYPos != yPos)) && p.mousePressed 
    			&& !isActive && cordsInBounds(xPos, yPos)) {
    		updateState(xPos, yPos, !getState(xPos, yPos));
    	}
    	prevPressed = p.mousePressed;
    }
    
    private boolean cordsInBounds(int xPos, int yPos) {
    	return xPos >= 0 && xPos < xNum && yPos >= 0 && yPos < yNum;
    }
    
    public void updateSquares() {
    	handleButtons();
    	handleMouseInput();
    	if (isActive) {
	        Grid newBoard = new Grid(p);
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
    }

    public void drawBoard() {
    	p.background(240);
    	p.rectMode(PConstants.CORNER);
        boolean[][] squareStatus = getStates();
        for (int x = 0; x < xNum; x++) {
            for (int y = 0; y < yNum; y++) {
                if (squareStatus[x][y]) {
                    p.rect(x * xSize, y * ySize + LConstants.menuHeight, xSize, ySize);
                } else {
                	p.fill(255);
                    p.rect(x * xSize, y * ySize + LConstants.menuHeight, xSize, ySize);
                    p.fill(0);
                }
            }
        }
        playButton.display();
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