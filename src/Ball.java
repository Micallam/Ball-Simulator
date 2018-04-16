import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Ball extends JPanel {

    private ArrayList<BallComponent> ballComponentList;

    private Timer timer;
    private final int DELAY = 16;
    private static int MAX_BALLS = 50;
    private static int numberOfBalls;


    public Ball() {
        ballComponentList = new ArrayList<>();
        addMouseListener(new bListener());
        addMouseMotionListener(new bListener());
        addMouseWheelListener(new bListener());
        addKeyListener(new bListener());
        timer = new Timer(DELAY, new bListener());
        setBackground(Color.BLACK);
        setFocusable(true);

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(BallComponent b: ballComponentList){
            if(b.x-(b.size/2)<0 || b.x+(b.size/2)>getWidth()){
                g.fillOval(b.x-(b.size/2), b.y-(b.size/2), (int)(b.size/1.3), b.size);
            }
            else if(b.y-(b.size/2)<0 || b.y+(b.size/2)>getHeight()){
                g.fillOval(b.x-(b.size/2), b.y-(b.size/2), b.size, (int)(b.size/1.3));
            }
            else{
                g.fillOval(b.x-(b.size/2), b.y-(b.size/2), b.size, b.size);
            }
            g.setColor(b.color);
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString("Number of balls: "+Integer.toString(Ball.numberOfBalls), 10, 50);
        g.drawString("MAX: "+Integer.toString(this.MAX_BALLS), 500, 50);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        g.drawString("(change with arrows)", 500, 70);
    }

    private class bListener implements  MouseListener,
                                        ActionListener,
                                        MouseMotionListener,
                                        MouseWheelListener,
                                        KeyListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(numberOfBalls<MAX_BALLS) {
                ballComponentList.add(new BallComponent(e.getX(), e.getY(), 40));
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
            for(BallComponent k: ballComponentList){
                k.updatePosition();
            }
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(numberOfBalls<MAX_BALLS) {
                ballComponentList.add(new BallComponent(e.getX(), e.getY(), 40));
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e){
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            for(int i = 0; i< ballComponentList.size(); i++){
                ballComponentList.get(i).updateBallSize(e.getWheelRotation());
                if(ballComponentList.get(i).size<1){
                    ballComponentList.remove(i);
                    numberOfBalls--;
                }
            }
            repaint();
        }

        @Override
        public void keyTyped(KeyEvent e){
        }

        @Override
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_UP){
                Ball.MAX_BALLS ++;
            }else if((e.getKeyCode() == KeyEvent.VK_DOWN) && Ball.MAX_BALLS>0){
                Ball.MAX_BALLS --;
                if(Ball.numberOfBalls>Ball.MAX_BALLS){
                    ballComponentList.remove(ballComponentList.size()-1);
                    numberOfBalls--;
                }
            }

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e){
        }
    }

    private class BallComponent {
        private Color color;
        public int x;
        public int y;
        public int size;
        private final int MAXSPEED = 5;
        public int xspeed;
        public int yspeed;

        public BallComponent(int x, int y, int size) {
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
            double distanceToCollision;

            for(BallComponent ballComponent : ballComponentList){
                if(ballComponent.x == x && ballComponent.y == y) continue;

                distanceBetweenBalls = (x- ballComponent.x)*(x- ballComponent.x) + (y- ballComponent.y)*(y- ballComponent.y);
                distanceToCollision = ((size/2)+(ballComponent.size/2)) * ((size/2)+(ballComponent.size/2));
                if(distanceBetweenBalls < distanceToCollision){
                    ballReflection();
                    ballComponent.ballReflection();
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