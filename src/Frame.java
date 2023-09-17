import javax.swing.*;

public class Frame extends JFrame {
    final int GAME_HEIGHT = 600;
    final int GAME_WIDTH = GAME_HEIGHT;
    Panel panel = new Panel(GAME_HEIGHT,GAME_WIDTH);

    Frame(){

        this.setTitle("Snake");
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(panel);


        this.pack();
        panel.requestFocus();
        this.setVisible(true);

    }
}
