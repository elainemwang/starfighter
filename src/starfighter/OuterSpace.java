package starfighter;

//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OuterSpace extends Canvas implements KeyListener, Runnable {

    private Ship ship;
    private Alien alienOne;
    private Alien alienTwo;
    private boolean shot = true;

    // uncomment once you are ready for this part
    private AlienHorde horde;
    private Bullets shots;

    private boolean[] keys;
    private BufferedImage back;

    public OuterSpace() {
        setBackground(Color.black);

        keys = new boolean[5];

        //instantiate other instance variables
        //Ship, Alien
        horde = new AlienHorde(16);
        shots = new Bullets();
        
        ship = new Ship(400, 500, 50, 50, 5);
        //alienOne = new Alien(100, 100);
        //alienTwo = new Alien(200, 100);
        

        this.addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    public void update(Graphics window) {
        if(ship.getDead()==false){
            paint(window);
        }
    }

    public void paint(Graphics window) {
        //set up the double buffering to make the game animation nice and smooth
        Graphics2D twoDGraph = (Graphics2D) window;

        //take a snap shop of the current screen and same it as an image
        //that is the exact same width and height as the current screen
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics graphToBack = back.createGraphics();

        graphToBack.setColor(Color.BLACK);
        graphToBack.fillRect(0, 0, 800, 600);
        graphToBack.setColor(Color.BLUE);
        graphToBack.drawString("StarFighter ", 25, 50);

        if (keys[0] == true) {
            ship.move("LEFT");
        }
        if (keys[1] == true) {
            ship.move("RIGHT");
        }
        if (keys[2] == true) {
            ship.move("UP");
        }
        if (keys[3] == true) {
            ship.move("DOWN");
        }
        if (keys[4] == true && shot == true && ship.getDead() == false) {
            shots.add(new Ammo(ship.getX()+ship.getWidth()/2,ship.getY()));
            shot = false;
        }

        //add code to move Ship, Alien, etc.
        ship.draw(graphToBack);
        
        horde.drawEmAll(graphToBack);
        horde.moveEmAll();
        
        shots.drawEmAll(graphToBack);
        shots.moveEmAll();
        
        //alienOne.draw(graphToBack);
        //alienTwo.draw(graphToBack);

        //add in collision detection to see if Bullets hit the Aliens and if Aliens hit the Ship
        horde.removeDeadOnes(shots.getList());
        
        for(Alien al : horde.getList()){
            if((al.getX()>ship.getX()&&al.getX()<ship.getX()+ship.getWidth())&&(al.getY()<ship.getY()+ship.getHeight()&&al.getY()>ship.getY())){
                graphToBack.setColor(Color.BLACK);
                graphToBack.fillRect(0, 0, 800, 600);
                graphToBack.setColor(Color.RED);
                graphToBack.drawString("GAME OVER ", 400, 300);
                ship.setDead(true);
            }
        }
        
        
        twoDGraph.drawImage(back, null, 0, 0);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = true;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = false;
            shot = true;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {
        //no code needed here
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
        }
    }
}
