import javax.swing.*;
import java.awt.Dimension;

public class Painter extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Kuleczki!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Kula());

        frame.setPreferredSize(new Dimension(700,500));
        frame.pack();
        frame.setVisible(true);
    }
}
