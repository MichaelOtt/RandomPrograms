package physicsEngine;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.awt.event.*;
import java.awt.image.*;
/**
 * Class physicsEngine - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class physicsEngine extends JApplet implements MouseListener, MouseMotionListener, KeyListener
{

    private Timer myTimer;
    private TimerTask myTask;
    public Image offscreen;
    public Graphics bufferGraphics;
    public Dimension mySize;
    private ArrayList<Sprite> mySprites;
    int mousex, mousey;
    int selectednum, secondselectednum;
    int whichbutton;
    int mousecode;
    public void init()
    {
       mySprites = new ArrayList<Sprite>();
       mySize = getSize();
       offscreen = createImage(mySize.width, mySize.height);
       bufferGraphics = offscreen.getGraphics();
       myTimer = new Timer();
       myTask = new Task(this);
       int count = 0;
       long delay = 24;
       addMouseListener(this);
       addMouseMotionListener(this);
       addKeyListener(this);
       for (int x=0; x<10; x++)
       {
           Sprite previous = new Sprite(x*50 + 50, 0);
           mySprites.add(previous);
           previous.anchored = true;
           for (int y = 1; y < 10; y++)
           {
               Sprite next = new Sprite (x*50+50, y*10);
               next.attached.add(previous);
               previous.attached.add(next);
               mySprites.add(next);
               previous = next;
           }
       }
       for (int x=1; x<10; x++)
       {
           for (int y = 0; y < 10; y++)
           {
               Sprite right = mySprites.get(10*x+y);
               Sprite left = mySprites.get(10*(x-1) + y);
               right.attached.add(left);
               left.attached.add(right);
           }
       }
       
       myTimer.schedule(myTask,count,delay);
    }
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if (keyCode == e.VK_SPACE)
        {   mySprites = new ArrayList<Sprite>();
              for (int x=0; x<10; x++)
       {
           Sprite previous = new Sprite(x*50 + 50, 0);
           mySprites.add(previous);
           previous.anchored = true;
           for (int y = 1; y < 10; y++)
           {
               Sprite next = new Sprite (x*50+50, y*10);
               next.attached.add(previous);
               previous.attached.add(next);
               mySprites.add(next);
               previous = next;
           }
       }
       for (int x=1; x<10; x++)
       {
           for (int y = 0; y < 10; y++)
           {
               Sprite right = mySprites.get(10*x+y);
               Sprite left = mySprites.get(10*(x-1) + y);
               right.attached.add(left);
               left.attached.add(right);
           }
       }
        }
        if (keyCode == e.VK_SHIFT)
        {
            mySprites.add(new Sprite(mousex - 10, mousey - 10));
            Sprite s = mySprites.get(mySprites.size() - 1);
            s.anchored = true;
            
        }
    }
    public void keyReleased(KeyEvent e)
    {
        
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public void mouseDragged(MouseEvent e)
    {
        mousex = e.getX();
        mousey = e.getY();
        if (whichbutton == 1)
        {
        whichbutton = 1;
        Sprite s = mySprites.get(selectednum);
        if (s.selected)
        {
        s.x = mousex - s.radius/2;
        s.y = mousey - s.radius/2;
        }
        }
        if (whichbutton == 3)
        {
            whichbutton = 2;
            
        }
    }
    public void mouseMoved(MouseEvent e)
    {
        mousex = e.getX();
        mousey = e.getY();
    }
    public void mouseClicked(MouseEvent e)
    {

    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
        mousecode = e.getButton();
        mousex = e.getX();
        mousey = e.getY();
        double mindistance = distance(e, mySprites.get(0));
        selectednum = 0;
        for (int i = 0; i < mySprites.size(); i++)
        {
            Sprite s = mySprites.get(i);
            double distance = distance(e, s);
            if (distance < mindistance)
            {
                mindistance = distance;
                selectednum = i;
            }
        }
        Sprite s = mySprites.get(selectednum);
        if (mousecode == 1)
        {
            s.selected = true;
            s.x = mousex - s.radius/2;
            s.y = mousey - s.radius/2;
            whichbutton = 1;
        }
        if (mousecode == 3)
        {
            whichbutton = 2;
        }
    }
    public void mouseReleased(MouseEvent e)
    {
        mousex = e.getX();
        mousey = e.getY();
        if (whichbutton == 1)
        {
        Sprite s = mySprites.get(selectednum);
        if (s.selected)
        {
        s.selected = false;
        s.anchored = !s.anchored;
        }
        whichbutton = 0;
        }
        if (whichbutton == 2)
        {
            whichbutton = 0;
            double mindistance = distance(e, mySprites.get(0));
            secondselectednum = 0;
            for (int i = 0; i < mySprites.size(); i++)
            {
                Sprite s = mySprites.get(i);
                double distance = distance(e, s);
                if (distance < mindistance)
                {
                    mindistance = distance;
                    secondselectednum = i;
                }
            }
            Sprite s = mySprites.get(secondselectednum);
            Sprite p = mySprites.get(selectednum);
            boolean attached = false;
            for (int i = 0; i < s.attached.size(); i++)
            {
                if (s.attached.get(i) == p)
                {
                    attached = true;
                    break;
                }
            }
            if (attached)
            {
                s.attached.remove(p);
                p.attached.remove(s);
            }
            else
            {
                s.attached.add(p);
                p.attached.add(s);
            }
        }
    }
    public void update()
    {
        Dimension current = getSize();
        if ((current.width != mySize.width || current.height != mySize.height) && current.width > 5 && current.height > 5)
        {
            offscreen = createImage(current.width + 1,current.height + 1);
            bufferGraphics = offscreen.getGraphics();
            mySize = current;
        }
        mySize = getSize();
        for (int i = 0; i < mySprites.size(); i++)
        {
            Sprite p = mySprites.get(i);
            p.update();
        }
    }
    public void draw()
    {
        bufferGraphics.clearRect(0,0,mySize.width,mySize.height);
        bufferGraphics.setColor(Color.black);
        for (int i = 0; i < mySprites.size(); i++)
        {
            Sprite p = mySprites.get(i);
            p.draw(bufferGraphics);
        }
        bufferGraphics.setColor(Color.black);
        Sprite s = mySprites.get(selectednum);
        if (whichbutton == 2)
        {
            bufferGraphics.drawLine((int)(s.x + s.radius/2), (int)(s.y + s.radius/2), mousex, mousey);
        }
        bufferGraphics.drawString(mousecode + "", 50, 100);
        repaint();
    }
    public void paint(Graphics g)
    {
    	g.drawImage(offscreen,0,0,this);
    }
    public double distance(MouseEvent e, Sprite s)
    {
        return Math.rint(Math.abs(s.x - e.getX()) * Math.abs(s.x - e.getX()) + Math.abs(s.y - e.getY()) * Math.abs(s.y - e.getY()));
    }

}
