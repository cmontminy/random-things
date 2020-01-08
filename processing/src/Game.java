import processing.core.PApplet;

public class Game extends PApplet {

    private static Grid board;


    private static final Color BACKGROUND = new Color(64, 64, 64); // gray
    private static final Color ONE = new Color(212, 18, 26); // d4121a - red
    private static final Color TWO = new Color(228, 94, 11); // e45e0b - orange
    private static final Color THREE = new Color(239, 132, 19); // ef8413 - yellow
    private static final Color FOUR = new Color(94, 131, 29); // 5e831d - green
    private static final Color FIVE = new Color(21, 45, 41); // 152d29 - blue

    public static void main(String[] args) {
        PApplet.main("Game");
    }
    
    public void settings() {
    	// Don't add anything else here I think
    	size(LConstants.screenWidth, LConstants.screenHeight + LConstants.menuHeight);
    }
    
    public void setup() {
    	// One time setup operations
        System.out.println("Launching game with a board of size " + LConstants.screenWidth + " x " + LConstants.screenHeight);
        // Uncomment to slowdown the animation:
        //frameRate(5);
        board = new Grid(this);	
    }
<<<<<<< HEAD
 
    private static void updateGame(Grid board) {
        DrawingPanel p = new DrawingPanel(board.getXSize() * pixelSize, board.getYSize() * pixelSize);
        Graphics g = p.getGraphics();
        g.setColor(BACKGROUND);
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
                newBoard.getSquare(x, y).setColorNum(board.getSquareColor(x, y));
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
        board = newBoard;
    }

    private static void drawBoard(Graphics g) {
        boolean[][] squareStatus = board.getStates();
        for (int x = 0; x < board.getXSize(); x++) {
            for (int y = 0; y < board.getYSize(); y++) {
                if (squareStatus[x][y]) {
                    g.setColor(convertToColor(board.getSquare(x, y).getColor()));
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                } else {
                    g.setColor(BACKGROUND);
                    g.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
                    g.setColor(ONE);
                }
            }
        }
=======
    
    public void draw() {
    	// Operations to repeat
    	board.updateSquares();
    	board.drawBoard();
>>>>>>> c491316202ed331b766949a5cff7a26ee1eaefff
    }

    private static Color convertToColor(int colorNum) {
        switch (colorNum) {
        case 1:
            return ONE;
        case 2:
            return TWO;
        case 3:
            return THREE;
        case 4:
            return FOUR;
        case 5:
            return FIVE;
        default:
            return FIVE;
        }
    }
}