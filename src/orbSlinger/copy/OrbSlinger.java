package orbSlinger.copy;
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import randomFun.Sprite3;

import ImageInteraction.Sprite2;
import MotionToy.Sprite;
import MotionToy.Task;

import java.util.Timer;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
public class OrbSlinger extends JApplet implements MouseListener, MouseMotionListener, KeyListener
{
	private Timer myTimer;
	private Task4 myTask;
	private Dimension mySize;
	private Image offscreen;
	private Graphics bufferGraphics;
	ArrayList<Orb>mySprites;
	ArrayList<OrbBlur>myBlur;
	private int mousex, mousey;
	private double radius = 20;
	private boolean creating;
	private boolean increasing = false;
	private boolean decreasing = false;
	private double friction = 0.1;
	private boolean dragging = false;
	private boolean stopped = false;
	private boolean pathing = false;
	private boolean bluring = true;
	private double gridcount = 2;
	private int bluramount = 50;
	private double redamount = 255;
	private double greenamount = 0;
	private double blueamount = 0;
	private double colorspot;
	private double colorspeed = 100;
	private boolean increasingcolor;
	private boolean decreasingcolor;
	private boolean increasingblur;
	private boolean decreasingblur;
	private boolean colorrunning = false;
	private boolean text = true;
	public void keyPressed (KeyEvent e)
    {
		mySize = getSize();
	    int keyCode = e.getKeyCode();
	    if (keyCode == 47)
	    {
	    	colorrunning = !colorrunning;
	    }
	    if (keyCode == 32)//32 is the code for space
	    {
        	creating = !creating;
        }
	    if (keyCode == e.VK_UP)
	    {
	    	increasing = true;
	    }
	    if (keyCode == e.VK_DOWN)
	    {
	    	decreasing = true;
	    }
	    if (keyCode == e.VK_RIGHT)
	    {
	    	friction += 0.01;
	    }
	    if (keyCode == e.VK_LEFT)
	    {
	    	friction -= 0.01;
	    	if (friction < 0)
	    	{
	    		friction = 0;
	    	}
	    }
	    if (keyCode == 61)//plus
	    {
	    	friction += 0.1;
	    }
	    if (keyCode == 79)//o
	    {
	    	decreasingcolor = true;
	    }
	    if (keyCode == 80)//p
	    {
	    	increasingcolor = true;
	    }
	    if (keyCode == 222)//.
	    {
	    	increasingblur = true;
	    }
	    if (keyCode == 59)//,
	    {
	    	decreasingblur = true;
	    }
	    if (keyCode == e.VK_MINUS)
	    {
	    	friction -= 0.1;
	    	if (friction < 0)
	    	{
	    		friction = 0;
	    	}
	    }
	    if (keyCode == e.VK_ESCAPE)
	    {
	    	stopped = !stopped;
	    }
	    if (keyCode == e.VK_ENTER)
	    {
	    	pathing = !pathing;
	    }
	    if (keyCode == e.VK_SHIFT)
	    {
	    	bluring = !bluring;
	    }
	    if (keyCode == 157)//command
	    {
	    	String name = new String();
	    	name = JOptionPane.showInputDialog(null,"name your picture");
	    	boolean done = false;
	    	Image myImage;
	    	while (!done)
	    	{
		        try 
		        {
		            myImage = ImageIO.read(new File("Orb Slinger Pictures/" + name + ".png"));
		            name = JOptionPane.showInputDialog(null,"picture taken");

		        }catch (IOException e1) {
		        	done = true;
		        }
	    	}
	    	try {
				ImageIO.write((RenderedImage)offscreen, "png" ,new File("Orb Slinger Pictures/" + name + ".png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }
	    if (keyCode == 91)
	    {
	    	gridcount--;
	    	if (gridcount <= 0)
	    	{
	    		gridcount = 0;
	    	}
	    }
	    if (keyCode == 93)
	    {
	    	gridcount++;
	    }
	    if (keyCode == e.VK_L)
	    {
	    	text = !text;
	    }
	    if (keyCode == 192)
	    {
	    	for (int x = 1; x < gridcount+1; x++)
	    	{
	    		for (int y = 1; y < gridcount+1; y++)
	    		{
	    			mySprites.add(new Orb((mySize.width/gridcount) * x - (mySize.width/(gridcount*2)) - radius/2, (mySize.height/gridcount) * y - (mySize.height/(gridcount*2)) - radius/2, radius));
	    		}
	    	}
	    }
	    if (keyCode == 8)//delete key
	    {
	    	for (int i = 0; i < mySprites.size(); i++)
	    	{
	    		Orb s = mySprites.get(i);
	    		mySprites.remove(s); 
	    		i--;
	    	}
	    	for (int i = 0; i < myBlur.size(); i++)
	    	{
	    		OrbBlur s = myBlur.get(i);
	    		myBlur.remove(s); 
	    		i--;
	    	}
	    }
	    if (keyCode == 92)//
	    {
	    	radius = 20;
	    	friction = 0.1;
	    	stopped = false;
	    	pathing = false;
	    	bluring = true;
	    	gridcount = 2;
	    	bluramount = 50;
	    	colorspeed = 100;
	    	colorrunning = false;
	    }
	    double tempfriction = friction * 100;
	    friction = Math.round(tempfriction);
	    friction = friction / 100;
	}
    public void keyReleased (KeyEvent e)
    {
    	int keyCode = e.getKeyCode();
	    if (keyCode == e.VK_UP)
	    {
	    	increasing = false;
	    }
	    if (keyCode == e.VK_DOWN)
	    {
	    	decreasing = false;
	    }

	    if (keyCode == 79)//o
	    {
	    	decreasingcolor = false;
	    }
	    if (keyCode == 80)//p
	    {
	    	increasingcolor = false;
	    }

	    if (keyCode == 222)//.
	    {
	    	increasingblur = false;
	    }
	    if (keyCode == 59)//,
	    {
	    	decreasingblur = false;
	    }
    }
    public void keyTyped (KeyEvent e)
    {
        
    }
    public void mouseDragged(MouseEvent e)
    {        
    	mousex = e.getX();
    	mousey = e.getY();
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
    	mousex = e.getX();
    	mousey = e.getY();
    	if (creating == true)
    	{
    		mySprites.add(new Orb(mousex - radius/2,mousey - radius/2, radius));
    	}
    	else
    	{
    		dragging = true;
    	}
    	creating = false;
    }
    public void mouseReleased(MouseEvent e)
    {
    	dragging = false;
    }
	public void init()
	{
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        mySprites = new ArrayList<Orb>();
        myBlur = new ArrayList<OrbBlur>();
        mySize = getSize();
        offscreen = createImage(mySize.width, mySize.height);
        bufferGraphics = offscreen.getGraphics();
        myTimer = new Timer();
        myTask = new Task4(this);
        int count = 0;
        long delay = 10;
        myTimer.schedule(myTask,count,delay);
	}
	public void painter()
	{

		if (!stopped)
		{
		if (!pathing)
		{
        bufferGraphics.clearRect(0,0,mySize.width, mySize.height);
		}
        if (mySprites.size() > 0)
        {
        Orb j = mySprites.get(mySprites.size() - 1);
        //bufferGraphics.drawString("y: " + j.frictiony, 50, 150);
        //bufferGraphics.drawString("x: " + j.frictionx, 50, 200);
        //bufferGraphics.drawString("sum: " + (j.frictionx + j.frictiony), 50, 250);
        }
		if (creating == true)
		{
			bufferGraphics.setColor(Color.black);
			if (colorrunning)
			{
			bufferGraphics.setColor(new Color((int)redamount, (int)greenamount, (int)blueamount));
			}
			bufferGraphics.fillOval(mousex - (int)(radius/2), mousey - (int)(radius/2), (int)radius, (int)radius);
		}
        //bufferGraphics.drawString();
        bufferGraphics.setColor(Color.black);
        if (text)
        {
        bufferGraphics.drawString("friction: " + friction, 50, 50);
        bufferGraphics.drawString("grid number: " + gridcount, 50, 100);
        bufferGraphics.drawString("radius: " + radius, 50, 150);
        bufferGraphics.drawString("Orbs: " + mySprites.size(), 50,200);
        bufferGraphics.drawString("Blur: " + myBlur.size(), 50, 250);
	    if (bluring == true)
	    {
	    	bufferGraphics.drawString("blur: on", 50, 300);
	    }
	    else
	    {
	    	bufferGraphics.drawString("blur: off", 50, 300);
	    }
	    bufferGraphics.drawString("Blur Amount: " + bluramount, 50, 350);
	    bufferGraphics.drawString("Color Speed: " + colorspeed, 50, 400);
        }
        for(int i = 0; i < mySprites.size(); i++)
        {
            Orb s = mySprites.get(i);
            s.draw(bufferGraphics, redamount, greenamount, blueamount, colorrunning);
    		if (dragging == true)
    		{
    			bufferGraphics.setColor(Color.black);
    			bufferGraphics.drawLine(mousex, mousey, (int)(s.x + s.radius/2), (int)(s.y + s.radius/2));
    		}
        }
        if (bluring == true)
        {
        for (int i = 0; i < myBlur.size(); i++)
        {
        	OrbBlur s = myBlur.get(i);
        	s.draw(bufferGraphics, redamount, greenamount, blueamount, colorrunning);
        }
        }
        if (text)
        {
        bufferGraphics.setColor(new Color(255, 0, 0, 100));
        bufferGraphics.fillOval(mySize.width/2 - 5, mySize.height/2 - 5, 10, 10);
        }
        repaint();
		}
	}
	public void paint(Graphics g)
	{
		g.drawImage(offscreen,0,0,this);		
	}
	public void update()
	{
		if (!stopped)
		{
		if (increasing == true)
		{
			radius*=1.02;
		}
		if (decreasing == true)
		{
			radius/=1.02;
		}
		if (increasingcolor == true)
		{
			colorspeed+=0.1;
			if (colorspeed >= 255)
			{
				colorspeed = 255;
			}
		}
		if (decreasingcolor == true)
		{
			colorspeed-=0.1;
			if (colorspeed <= 0)
			{
				colorspeed = 0;
			}
		}
		if (increasingblur)
		{
			bluramount++;
		}
		if (decreasingblur)
		{
			bluramount--;
		    if (bluramount <= 0)
		    {
		    	bluramount = 0;
		    }
		}
	    double tempcolorspeed = colorspeed * 100;
	    colorspeed = Math.round(tempcolorspeed);
	    colorspeed = colorspeed / 100;
		if (colorspot == 0)
		{
			greenamount += colorspeed;
			if (greenamount >= 255)
			{
				greenamount = 255;
				colorspot = 1;
			}
		}
		else if (colorspot == 1)
		{
			redamount -= colorspeed;
			if (redamount <= 0)
			{
				redamount = 0;
				colorspot = 2;
			}
		}
		else if (colorspot == 2)
		{
			blueamount += colorspeed;
			if (blueamount >= 255)
			{
				blueamount = 255;
				colorspot = 3;
			}
		}
		else if (colorspot == 3)
		{
			greenamount -= colorspeed;
			if (greenamount <= 0)
			{
				greenamount = 0;
				colorspot = 4;
			}
		}
		else if (colorspot == 4)
		{
			redamount += colorspeed;
			if (redamount >= 255)
			{
				redamount = 255;
				colorspot = 5;
			}
		}
		else if (colorspot == 5)
		{
			blueamount -= colorspeed;
			if (blueamount <= 0)
			{
				blueamount = 0;
				colorspot = 0;
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
            Orb s = mySprites.get(i);
            s.update(mySize, friction, mousex, mousey, dragging);
            if (bluring == true && s.speedx != 0 && s.speedy != 0)
            {
            myBlur.add(new OrbBlur(s.x + s.radius/2,s.y + s.radius/2, s.radius, s.radius));
            }
        }
        for (int i = 0; i < myBlur.size(); i++)
        {
        	OrbBlur s = myBlur.get(i);
        	s.update(bluramount);
        	if (s.isDead == true)
        	{
        		myBlur.remove(s);
        	}
        }
		}
	}
}
