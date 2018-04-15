import javax.swing.*;
import java.awt.Dimension;

public class Painter extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Balls!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Ball());

        frame.setPreferredSize(new Dimension(700,500));
        frame.pack();
        frame.setVisible(true);
    }
}
