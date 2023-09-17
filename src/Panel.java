import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; // makes the program listen to the arrow keys on the keyboard
import java.util.ArrayList;
import java.util.Random;


public class Panel extends JPanel implements ActionListener, KeyListener {
    private static class gridTile{
        int x; // x-axis of the tile
        int y; // y-axis of the tile

        gridTile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int GAME_WIDTH;
    int GAME_HEIGHT;
    int gridTile  = 15;
    private boolean showMessage = false;
    int xVelocity;
    int yVelocity;

    gridTile snake;
    gridTile apple;
    ArrayList<gridTile> snakeLength; // as the snake it's the apple the body gets longer. this is where the pixels will be stored
    Random random;
    Timer time; // to make the snake move
    private boolean gameOver = false;
    private boolean gameOverDisplayed = false;



    // ================  CONSTRUCTOR ===========================================
    Panel(int GAME_WIDTH, int GAME_HEIGHT){
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.setPreferredSize(new Dimension(this.GAME_WIDTH, this.GAME_HEIGHT));
        this.setBackground(new Color(17, 18, 17));
        addKeyListener(this); // this makes it so that the key listener in panel is being listened by the GUI
        this.setFocusable(true);

        //----- SNAKE -------->>>>>>
        snake = new gridTile(5, 5); // initial snake
        snakeLength = new ArrayList<gridTile>(); // snake when it takes an apple

        //----- APPLE -------->>>>>>
        apple = new gridTile(10, 10);
        random = new Random();
        appleLocation();

        // components for the movement of the snake
        xVelocity =0;
        yVelocity=1;
        time = new Timer(100, this);
        time.start();
    }

    // ================  PAINT ===========================================
    public void paint(Graphics g){ // render the shape
        super.paint(g);
        draw(g);
    }

    // ================  DRAW ===========================================
    public void draw(Graphics g){
        // grid tiles of the actual game
        for (int i = 0; i < GAME_WIDTH/gridTile; i++){ // divide the game width (600) by the grid tile (25px) to map out the snake's movement
            //        x1:                  y1:          x2       y2
            g.drawLine(i * gridTile, 0,i *gridTile,GAME_HEIGHT);
            g.drawLine(0,i *gridTile, GAME_WIDTH,i*gridTile);
        }

        //----- APPLE -------->>>>>>
        g.setColor(Color.red);
        g.fillRect(apple.x * gridTile, apple.y*gridTile, gridTile, gridTile);

        //----- SNAKE -------->>>>>>
        g.setColor(Color.green);
        // set snake starting position. avoid border as it can easily end the game
        g.fillRect(snake.x * gridTile, snake.y*gridTile, gridTile, gridTile);

        //----- SNAKE LENGTH -------->>>>>>
        for (int i=0; i<snakeLength.size(); i++){
            gridTile snakeLengthAdd = snakeLength.get(i);
            g.fillRect(snakeLengthAdd.x*gridTile, snakeLengthAdd.y*gridTile, gridTile,gridTile);
        }

        //----- SCORE and GaAME OVER TRACKER -------->>>>>>
        g.setFont(new Font("Digital-7", Font.PLAIN, 18));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeLength.size()), gridTile- 16, gridTile);

        }
        else{
            g.drawString("Score: " + String.valueOf(snakeLength.size()), gridTile-16, gridTile);
        }
    }

    // ================  APPLE LOCATION ===========================================
    public void appleLocation(){
        apple.x = random.nextInt(GAME_WIDTH/gridTile);
        apple.y = random.nextInt(GAME_HEIGHT/gridTile);
    }

    // ================  MOVE ===========================================
    public void move() throws InterruptedException {
        // if the snake gets on the apple, the apple will be part of snake length
        if (collision(snake, apple)) {
            snakeLength.add(new gridTile(apple.x, apple.y));
            appleLocation();
        }

        // snake length moves with the snake body
        for (int i = snakeLength.size() - 1; i >= 0; i--) {
            gridTile snakeLengthAdd = snakeLength.get(i);
            if (i == 0) {
                snakeLengthAdd.x = snake.x;
                snakeLengthAdd.y = snake.y;
            } else {
                gridTile prevSnakeLength = snakeLength.get(i - 1);
                snakeLengthAdd.x = prevSnakeLength.x;
                snakeLengthAdd.y = prevSnakeLength.y;
            }
        }
        snake.x += xVelocity;
        snake.y += yVelocity;

        // ================  GAME OVER ===========================================
        // two ways for the player to lose: snake collides with itself or snake hits the corner of the gui
        // for the snake to collide with itself:
        for (int i = 0; i < snakeLength.size(); i++) {
            gridTile snakeLengthAdd = snakeLength.get(i);

            if (collision(snake, snakeLengthAdd)) {
                gameOver = true;

            }
        }
        if (snake.x*gridTile < 0 || snake.x*gridTile > GAME_WIDTH || snake.y*gridTile < 0 || snake.y*gridTile > GAME_HEIGHT ) {
            gameOver = true;
        }
    }
    // ================  COLLISION ===========================================
    public boolean collision(gridTile gT1, gridTile gT2){
        return  (gT1.x == gT2.x && gT1.y == gT2.y);
    }

    // ================  OVERRIDE ===========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            move();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        repaint(); // basically repaints the snake unto a new grid which creates the illusion of it moving
        if (gameOver){
            time.stop();
        }
    }

    // key pressed only. the rest leave blank
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_UP && yVelocity !=1){
            xVelocity =0;
            yVelocity = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN  && yVelocity != -1){
            xVelocity = 0;
            yVelocity = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && xVelocity != -1){
            xVelocity = 1;
            yVelocity = 0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && xVelocity !=1){
            xVelocity = -1;
            yVelocity = 0;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
