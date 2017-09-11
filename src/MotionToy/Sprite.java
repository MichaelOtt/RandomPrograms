package MotionToy;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Write a description of class Sprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sprite
{
    private double x,y;
    private double speedx,speedy;
    private double width, height;
    private double originalwidth, originalheight;
    private int Red, Green, Blue;
    private boolean circle;
    private int deathspeed;

    public boolean isDead;
    public Sprite(int deathrate, double speedrange)
    {
    	deathspeed = deathrate;
        init(speedrange);
        x = (int)(Math.random()*1000);
        y = (int)(Math.random()*1000);
    }
    public void init(double speedrange)
    {
        isDead = false;
        int randomize = (int)(Math.random() * 2);
        if (randomize == 0)
        {
            circle = true;
        }
        else if (randomize == 1)
        {
            circle = false;
        }
        Red = (int)(Math.random()*255);
        Green = (int)(Math.random()*255);
        Blue = (int)(Math.random()*255);
        do {
        speedx = (Math.random()*speedrange);
        if ((int)(Math.random()*2) == 0)
        {
            speedx *= -1;
        }
        speedy = (Math.random()*speedrange);
        if ((int)(Math.random() * 2) == 0)
        {
            speedy *= -1;
        }} while (Math.sqrt(speedx * speedx + speedy * speedy) > speedrange);
        width = (int)(Math.random()*50) + 5;
        originalwidth = width;
        height = (int)(Math.random()*50) + 5;
        originalheight = height;

    }
    public Sprite(int inputx, int inputy, int deathrate, double speedrange)
    {
        init(speedrange);
        deathspeed = deathrate;
        x = inputx - width/2;
        y = inputy - height/2;
    }
    public void update(Dimension d)
    {
        width -= originalwidth/deathspeed;
        height -= originalheight/deathspeed;
        if(width <= 0 || height <= 0)
        {
            isDead = true;
        }
        x += speedx;
        y += speedy;
        if (x>d.width - width)
        {
            speedx *= -1;
            x = d.width - width - 1;
        }
        if (x<0)
        {
            speedx *= -1;
            x = 1;
        }
        if (y>d.height - height)
        {
            speedy *= -1;
            y = d.height - height - 1;
        }
        if (y<0)
        {
            speedy *= -1;
            y = 1;
        }
    }
    public void draw(Graphics g)
    {
        //g.setColor(new Color((int)(Math.random() * 255),(int)(Math.random() * 255), (int)(Math.random() * 255)));
        g.setColor( new Color(Red,Green,Blue));
        if (circle == true)
        {
            g.fillOval((int)x,(int)y,(int)width,(int)height);
        }
        else if (circle == false)
        {
            g.fillRect((int)x,(int)y,(int)width,(int)height);
        }
        
    }
}
