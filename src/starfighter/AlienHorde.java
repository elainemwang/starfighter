package starfighter;

//(c) A+ Computer Science
//www.apluscompsci.com
//Name -
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde {

    private List<Alien> aliens;

    public AlienHorde(int size) {
        int x = 35;
        int row = 1;
        aliens = new ArrayList<Alien>();
        for(int i = 1; i <= size; i++){
            add(new Alien(x,row * 50));
            x+=100;
            if(i%8==0&&i!=0){
                row++;
                x = 35;
            }
        }
    }

    public void add(Alien al) {
        aliens.add(al);
    }

    public void drawEmAll(Graphics window) {
        for(Alien a : aliens){
            a.draw(window);
        }
    }

    public void moveEmAll() {
        for(Alien a : aliens){
            if(a.getY()<700){
                a.move("DOWN");
            }
            else{
                a.setY(0);
            }
        }
    }
    
    public void remove(Alien a){
        aliens.remove(a);
    }

    public void removeDeadOnes(List<Ammo> shots) {
        List<Alien> removeAl = new ArrayList<Alien>();
        for(Ammo a : shots){
            for(Alien al : aliens){
                if((a.getX()>al.getX()&&a.getX()<al.getX()+al.getWidth())&&(a.getY()<al.getY()+al.getHeight()&&a.getY()>al.getY())){
                    removeAl.add(al);
                }
            }
        }
        aliens.removeAll(removeAl);
    }
    
    public List<Alien> getList() {
        return aliens;
    }

    public String toString() {
        return "";
    }
}
