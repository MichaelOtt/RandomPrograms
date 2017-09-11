package ImageInteraction;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Write a description of class ExplosionParticle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TrailParticle
{
    public double x,y;
    private double speedx,speedy;
    public double width, height;
    private double originalwidth, originalheight;
    public boolean isDead;
    public TrailParticle()
    {
        init();
        x = (int)(Math.random()*1000);
        y = (int)(Math.random()*1000);
    }
    public void init()
    {
        isDead = false;

        speedx = (Math.random()*1);
        if ((int)(Math.random()*2) == 0)
        {
            speedx *= -1;
        }
        speedy = (Math.random()*1);
        if ((int)(Math.random() * 2) == 0)
        {
            speedy *= -1;
        }
        width = 10;
        originalwidth = width;
        height = 10;
        originalheight = height;

    }
    public TrailParticle(double inputx, double inputy)
    {
        init();
        x = inputx - width/2;
        y = inputy - height/2;
    }
    public void update(Dimension d)
    {
        width -= originalwidth/10;
        height -= originalheight/10;

        if(width <= 0 || height <= 0)
        {
            isDead = true;
        }
        x += speedx;
        y += speedy;
        if (x>d.width - width)
        {
            isDead = true;  
        }
        if (x<0)
        {
            isDead = true;
        }
        if (y>d.height - height)
        {
            isDead = true;
        }
        if (y<0)
        {
            isDead = true;
        }
    }
    public void draw(Graphics g)
    {
        //g.setColor(new Color((int)(Math.random() * 255),(int)(Math.random() * 255), (int)(Math.random() * 255)));
        g.setColor(Color.black);
        g.fillOval((int)x,(int)y,(int)width,(int)height);
    }
}
