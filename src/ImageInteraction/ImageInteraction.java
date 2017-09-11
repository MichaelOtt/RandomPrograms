package ImageInteraction;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
/**
 * Class ImageInteraction - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class ImageInteraction extends JApplet implements MouseListener, MouseMotionListener, KeyListener
{
    private Timer myTimer;
    private Task2 myTask;
    private Dimension mySize;
    private Image offscreen;
    private boolean drawing = false;
    private double originalpointx, originalpointy, nextpointx, nextpointy;
    private Graphics bufferGraphics;
    private ArrayList<Sprite2> mySprites;
    private ArrayList<ExplosionParticle> myParticles;
    private ArrayList<TrailParticle> myTrail;
    private String keycode = new String();
    public void keyPressed (KeyEvent e)
    {
    	bufferGraphics.setColor(Color.black);
        int keyCode = e.getKeyCode();
        if (keyCode == 32)//32 is the code for space
        {
        	for(int i = 0; i < mySprites.size(); i++)
            {
        		Sprite2 s = mySprites.get(i);
        		s.exploding= true;
        		s.speedx = 0; 
        		s.speedy = 0;
            }
        }
        else
        {
        	keycode = "" + keyCode;

        }
    }
    public void keyReleased (KeyEvent e)
    {
        
    }
    public void keyTyped (KeyEvent e)
    {
        
    }
    public void mouseDragged(MouseEvent e)
    {        
        drawing = true;
        nextpointx = e.getX();
        nextpointy = e.getY();
    }
    public void mouseMoved(MouseEvent e)
    {
        nextpointx = e.getX();
        nextpointy = e.getY();
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
        drawing = true;
        originalpointx = e.getX();
        originalpointy = e.getY();
        
    }
    public void mouseReleased(MouseEvent e)
    {
        nextpointx = e.getX();
        nextpointy = e.getY();
        drawing = false;
        double differencex = originalpointx - nextpointx;
        double differencey = originalpointy - nextpointy;
        mySprites.add(new Sprite2(e.getX(), e.getY(), differencex/10,differencey/10, this));
    }

    public void update()
    {
        Dimension current = getSize();
        for (int t = 0; t < myTrail.size(); t++)
        {
        	TrailParticle tr = myTrail.get(t);
        	tr.update(mySize);
        }
        if ((current.width != mySize.width || current.height != mySize.height) && current.width > 5 && current.height > 5)
        {
            offscreen = createImage(current.width + 1,current.height + 1);
            bufferGraphics = offscreen.getGraphics();
            mySize = current;
        }
        for(int i = 0; i < mySprites.size(); i++)
        {
            Sprite2 s = mySprites.get(i);
            s.update(mySize);
            myTrail.add(new TrailParticle(s.x + s.width, s.y));
            
            for (int k = i + 1; k < mySprites.size(); k++)
            {
                Sprite2 s2 = mySprites.get(k);
                if (k != i)
                {
                s.CheckCollision(s2);
                }
            }
            for(int r = 0; r < myParticles.size(); r++)
            {
                ExplosionParticle p = myParticles.get(r);
                if (p.x + p.width >= s.x && p.x <= s.x + s.width && p.y + p.width >= s.y && p.y <= s.y + s.height && s.exploding == false)
                {
                    s.exploding = true;
                    s.speedx = 0;
                    s.speedy = 0;
                }
            }
            if (s.explosioncount == 1)
            {
                for (int j = 0; j<200; j++)
                {
                    myParticles.add(new ExplosionParticle(s.x, s.y));
                }
            }
            if (s.isDead == true)
            {
                mySprites.remove(s); 
                i--;
            }

        }
        for(int i = 0; i < myParticles.size(); i++)
        {
            ExplosionParticle s = myParticles.get(i);
            s.update(mySize);
            if (s.isDead == true)
            {
                myParticles.remove(s);
                i--;
            }
            
        }
    }
        
    public void init()
    {
       mySprites = new ArrayList<Sprite2>();
       myParticles = new ArrayList<ExplosionParticle>();
       myTrail = new ArrayList<TrailParticle>();
       mySize = getSize();
       offscreen = createImage(mySize.width, mySize.height);
       bufferGraphics = offscreen.getGraphics();
       myTimer = new Timer();
       myTask = new Task2(this);
       int count = 0;
       long delay = 30;
       addMouseListener(this);
       addMouseMotionListener(this);
       addKeyListener(this);
       myTimer.schedule(myTask,count,delay);
        
    }

   
    public void draw()
    {
        bufferGraphics.clearRect(0,0,mySize.width, mySize.height);
        bufferGraphics.drawString(keycode, 100, 100);
        if (drawing == true)
        {
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawLine((int)originalpointx, (int)originalpointy, (int)nextpointx, (int)nextpointy);
        }
        for (int i = 0; i < mySprites.size(); i ++)
        {
        	Sprite2 s = mySprites.get(i);
        	s.paint(bufferGraphics);
        }
        for (int i = 0; i < myParticles.size(); i ++)
        {
        	ExplosionParticle s = myParticles.get(i);
        	s.draw(bufferGraphics);
        }
        for (int i = 0; i < myTrail.size(); i++)
        {
        	TrailParticle s = myTrail.get(i);
        	s.draw(bufferGraphics);
        }
        repaint();
    }
    public void paint(Graphics g)
    {
        g.drawImage(offscreen,0,0,this);

    }


}
