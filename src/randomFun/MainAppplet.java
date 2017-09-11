package randomFun;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import ImageInteraction.Sprite2;
import MotionToy.Sprite;
import MotionToy.Task;

import java.util.Timer;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
public class MainAppplet extends JApplet implements MouseListener, MouseMotionListener
{
	private Timer myTimer;
	private Task3 myTask;
	private Dimension mySize;
	private Image offscreen;
	private Graphics bufferGraphics;
	ArrayList<Sprite3>mySprites;
	private int mousex, mousey;
	private boolean isrunning; 
	private final int maxnum = 2500;//2500 is where it starts blinking
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
	public void init()
	{
        addMouseListener(this);
        addMouseMotionListener(this);
        mySprites = new ArrayList<Sprite3>();
        mySize = getSize();
        offscreen = createImage(mySize.width, mySize.height);
        bufferGraphics = offscreen.getGraphics();
        myTimer = new Timer();
        myTask = new Task3(this);
        int count = 0;
        long delay = 10;
        myTimer.schedule(myTask,count,delay);
	}
	public void painter()
	{
        bufferGraphics.clearRect(0,0,mySize.width, mySize.height);
        //bufferGraphics.setColor(Color.black);
        //bufferGraphics.drawString(mySprites.size() + "", 50, 50);
        for(int i = 0; i < mySprites.size(); i++)
        {
            Sprite3 s = mySprites.get(i);
            s.draw(bufferGraphics);
        }
        repaint();
	}
	public void paint(Graphics g)
	{
		g.drawImage(offscreen,0,0,this);		
	}
	public void update()
	{
		if (mySprites.size() > maxnum)
		{
			for (int i = 0; i < mySprites.size() - maxnum; i++)
			{
				Sprite3 s = mySprites.get(i);
				mySprites.remove(s);
				i--;
			}
		}
		if (isrunning == true)
		{
			mySprites.add(new Sprite3(mousex,mousey));
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
            Sprite3 s = mySprites.get(i);
            s.update(mySize);
        }		
	}
}
