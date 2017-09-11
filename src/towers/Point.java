package towers;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
public class Point
{
    public double centerx,centery, health, x, y, otherx, othery, targetx, targety;
    public static double size;
    public boolean isDead, selected, going;
    public int team;
    public static int teamnum;
    public boolean unit;
    public static Color[] teamColors = new Color[0];
    public void attacked(double damaged)
    {
        health -= damaged;
        if (health <= 0)
        {
            isDead = true;
        }
    }
    public void healed(double healing)
    {
    }
    public void draw(Graphics g, double centerdistancex, double centerdistancey, double zoom)
    {
    	
    }
    public void updatepoints(double size)
    {
    	
    }
}
