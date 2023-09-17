import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage extends JFrame implements ActionListener {
    JFrame myFrame = new JFrame();
    JButton button = new JButton("Start Game");
    JLabel label = new JLabel("SNAKE GAME");
    JPanel panel = new JPanel();
    LaunchPage(){

        label.setFont(new Font("Digital-7", Font.PLAIN,36));
        label.setForeground(Color.green);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(100, 200, 400, 100);

        button.setFont(new Font("Digital-7", Font.PLAIN,14));
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBounds(250, 280, 100,40);

        panel.setBackground(new Color(17, 18, 17));
        panel.setLayout(null);
        panel.setBounds(0,0, myFrame.getWidth(), myFrame.getHeight());
        panel.add(label);
        panel.add(button);

        myFrame.setSize(600,600);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setTitle("Snake");
        myFrame.setLocationRelativeTo(null);
        myFrame.setBackground(new Color(17, 18, 17));

        myFrame.add(panel);
        myFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==button){
            myFrame.dispose();
            Frame frame = new Frame();
        }

    }
}
