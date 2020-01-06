import processing.core.PApplet;

public class Game extends PApplet {

    private static Grid board;
    private static int screenWidth = 800;
    private static int screenHeight = 800;
    private static int pixelSize = 20;

    public static void main(String[] args) {
        PApplet.main("Game");
    }
    
    public void settings() {
    	// Don't add anything else here I think
    	size(screenWidth, screenHeight);
    }
    
    public void setup() {
    	// One time setup operations
        System.out.println("Launching game with a board of size " + screenWidth + " x " + screenHeight);
        // Uncomment to slowdown the animation:
        //frameRate(5);
        board = new Grid(screenWidth / pixelSize, screenHeight / pixelSize, this);	
    }
    
    public void draw() {
    	// Operations to repeat
    	board.updateSquares();
    	board.drawBoard();
    }
}