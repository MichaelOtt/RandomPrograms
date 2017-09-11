package physicsEngine;
import java.awt.Graphics;
import java.awt.Color;
import java.util.*;
/**
 * Write a description of class Sprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sprite
{
    public double x, y, speedx, speedy, aX, aY, radius;
    
    public boolean selected;
    public boolean anchored;
    public ArrayList<Sprite>attached;
    public Sprite(double x, double y)
    {
        attached = new ArrayList<Sprite>();
        this.x = x;
        this.y = y;
        speedx = 0;
        speedy = 0;
        aX = 0;
        aY = 0;
        selected = false;
        anchored = false;
        radius = 20;
    }
    public void update()
    {
        if (!selected && !anchored)
        {
            aX = 0;
            aY = 0;
            for (int i = 0; i < attached.size(); i++)
            {
                Sprite s = attached.get(i);
                aX += (s.x + s.radius/2) - (x + radius/2);
                aY += (s.y + s.radius/2) - (y + radius/2);
            }
            aX *= 0.01;
            aY *= 0.01;
            speedx = (speedx + aX);
            speedy = (speedy + aY);
            speedx *= 0.97;
            speedy *= 0.97;
            speedy += 0.1;
            x += speedx;
            y += speedy;
            
        }
    }
    public void draw(Graphics g)
    {
        if (selected)
        {
            g.setColor(Color.green);
        }
        else if (anchored)
        {
            g.setColor(Color.blue);
        }
        else
        {
            g.setColor(Color.black);
        }
        g.fillOval((int)x,(int)y,(int)radius,(int)radius);
        g.setColor(Color.black);
        for (int i = 0; i < attached.size(); i++)
        {
            Sprite s = attached.get(i);
            g.drawLine((int)(x + radius/2), (int)(y + radius/2), (int)(s.x + s.radius/2), (int)(s.y + s.radius/2));            
        }
    }
}
