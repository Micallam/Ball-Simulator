import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Kula extends JPanel {

    private ArrayList<Kulka> kulkaList;

    private Timer timer;
    private final int DELAY = 16;
    private final int MAX_BALLS = 50;
    private static int numberOfBalls;


    public Kula() {
        kulkaList = new ArrayList<>();
        addMouseListener(new kListener());
        addMouseMotionListener(new kListener());
        addMouseWheelListener(new kListener());
        timer = new Timer(DELAY, new kListener());
        setBackground(Color.BLACK);

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Kulka k:kulkaList){
            if(k.x-(k.size/2)<0 || k.x+(k.size/2)>getWidth()){
                g.fillOval(k.x-(k.size/2), k.y-(k.size/2), (int)(k.size/1.3), k.size);
            }
            else if(k.y-(k.size/2)<0 || k.y+(k.size/2)>getHeight()){
                g.fillOval(k.x-(k.size/2), k.y-(k.size/2), k.size, (int)(k.size/1.3));
            }
            else{
                g.fillOval(k.x-(k.size/2), k.y-(k.size/2), k.size, k.size);
            }
            g.setColor(k.color);
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Liczba kulek: "+Integer.toString(Kula.numberOfBalls), 50, 50);
    }

    private class kListener implements  MouseListener,
                                        ActionListener,
                                        MouseMotionListener,
                                        MouseWheelListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(numberOfBalls<MAX_BALLS) {
                kulkaList.add(new Kulka(e.getX(), e.getY(), 40));
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            timer.start();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            timer.stop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for(Kulka k:kulkaList){
                k.updatePosition();
            }
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(numberOfBalls<MAX_BALLS) {
                kulkaList.add(new Kulka(e.getX(), e.getY(), 40));
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e){
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            for(int i=0;i<kulkaList.size();i++){
                kulkaList.get(i).updateBallSize(e.getWheelRotation());
                if(kulkaList.get(i).size<1){
                    kulkaList.remove(i);
                    numberOfBalls--;
                }
            }
            repaint();
        }
    }

    private class Kulka{
        private Color color;
        public int x;
        public int y;
        public int size;
        private final int MAXSPEED = 5;
        public int xspeed;
        public int yspeed;

        public Kulka(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            xspeed = (int)(Math.random()* MAXSPEED *2 - MAXSPEED);
            yspeed = (int)(Math.random()* MAXSPEED *2 - MAXSPEED);
            if(xspeed == 0 && yspeed == 0){
                xspeed = 1;
                yspeed = 1;
            }

            numberOfBalls++;
            setRandomColor();
        }

        public void updatePosition(){
            x+=xspeed;
            y+=yspeed;

            if(x-(size/2)<0 || x+(size/2)>getWidth()){
                xspeed = -xspeed;
            }
            if(y-(size/2)<0 || y+(size/2)>getHeight()){
                yspeed = -yspeed;
            }

            checkCollision();
        }

        public void checkCollision(){
            double distanceBetweenBalls;
            double distanceToCollision = size * size;

            for(Kulka kulka: kulkaList){
                if(kulka.x == x && kulka.y == y) continue;

                distanceBetweenBalls = (x-kulka.x)*(x-kulka.x) + (y-kulka.y)*(y-kulka.y);
                if(distanceBetweenBalls < distanceToCollision){
                    ballReflection();
                    kulka.ballReflection();
                }
            }
        }

        public void ballReflection(){
            xspeed=-xspeed;
            yspeed=-yspeed;
        }

        public void updateBallSize(int addToSize){
            size+=addToSize;
        }

        public void setRandomColor(){
            Random rand = new Random();

            color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        }
    }
}