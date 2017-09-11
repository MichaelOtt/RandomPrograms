package MotionToy;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.ArrayList;
/**
 * Class MotionToy - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class MotionToy extends JApplet implements MouseListener, MouseMotionListener, KeyListener
{
    private Timer myTimer;
    private Task myTask;
    private Dimension mySize;
    private Image offscreen;
    private Graphics bufferGraphics;
    ArrayList<Sprite>mySprites;
    private boolean isrunning = false;
    private int mousex;
    private int mousey;
    private int deathrate = 200;
    private double speed = 5;
    private int spawnnumber = 20;
    private boolean tracing = false;
    public void init()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        mySprites = new ArrayList<Sprite>();
        /*for (int i = 0; i<5000; i++)
        {
            mySprites.add(new Sprite());
            
        }*/
        mySize = getSize();
        offscreen = createImage(mySize.width, mySize.height);
        bufferGraphics = offscreen.getGraphics();
        myTimer = new Timer();
        myTask = new Task(this);
        int count = 0;
        long delay = 10;
        myTimer.schedule(myTask,count,delay);
        
    }
    public void keyPressed(KeyEvent e)
    {
    	int keyCode = e.getKeyCode();
    	if (keyCode == e.VK_UP)
    	{
    		speed+= 0.1;
    	}
    	if (keyCode == e.VK_DOWN)
    	{
    		speed-= 0.1;
    	}
    	if (keyCode == e.VK_LEFT)
    	{
    		deathrate--;
    	}
    	if (keyCode == e.VK_RIGHT)
    	{
    		deathrate++;
    	}
    	if (keyCode == 44)//<
    	{
    		deathrate-= 50;
    	}
    	if (keyCode == 46)
    	{
    		deathrate+= 50;//>
    	}
    	if (keyCode == e.VK_MINUS)
    	{
    		spawnnumber--;
    		if (spawnnumber <= 0)
    		{
    			spawnnumber = 0;
    		}
    	}
    	if (keyCode == 61)
    	{
    		spawnnumber++;
    	}
    	if (keyCode == e.VK_ENTER)
    	{
    		tracing = !tracing;
    	}
    	speed = Math.round(speed * 100);
    	speed /= 100;
		if (deathrate <= 0)
		{
			deathrate = 1;
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
    }
    public void mouseMoved(MouseEvent e)
    {

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
        isrunning = true;
        mousex = e.getX();
        mousey = e.getY();
    }
    public void mouseReleased(MouseEvent e)
    {
        isrunning = false;
    }
    public void update()
    {
        if (isrunning == true)
        {
            for (int i = 0;i < spawnnumber; i++)
            {
                mySprites.add(new Sprite(mousex,mousey,deathrate, speed ));
            }
        }
           
           Dimension current = getSize();
           if ((current.width != mySize.width || current.height != mySize.height) && current.width > 0  && current.height > 0) 
           {
               offscreen = createImage(current.width + 1, current.height + 1);
               bufferGraphics = offscreen.getGraphics();
           }
           mySize = getSize();
           for(int i = 0; i < mySprites.size(); i++)
           {
               Sprite s = mySprites.get(i);
               s.update(mySize);
               if (s.isDead == true)
               {
                   mySprites.remove(s); 
                   i--;
               }
           }
    }
    public void painter()
    {
    	if (!tracing)
    	{
        bufferGraphics.clearRect(0,0,mySize.width, mySize.height);
    	}
        bufferGraphics.drawString("" + deathrate, 50, 50);
        bufferGraphics.drawString("" + speed, 50, 100);
        bufferGraphics.drawString("" + spawnnumber, 50, 150);
        for(Sprite s: mySprites)
        {
            s.draw(bufferGraphics);
        }
        repaint();
    }
    public void paint(Graphics g)
    {
    	g.drawImage(offscreen,0,0,this);
    }
    

}
