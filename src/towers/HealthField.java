package towers;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Write a description of class Sprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthField extends Point 
{
    public static int cost = 25;
    //public static double greenmaxhealth = 500, yellowmaxhealth = 500;
    //public static double greenrange = 3 * size, yellowrange = 3 * size;
    public boolean isHealing;
    public static double width = (double)3/(double)5 * size, height = (double)3/(double)5 * size, greenheal = 0.5, yellowheal = 0.5;
    public static double[] teammaxhealth = new double[teamnum];
    public static double[] teamheal = new double[teamnum];
    public static double[] teamrange = new double[teamnum];
    public HealthField(int inputx, int inputy, int team)
    {
    	unit = false;
        isDead = false;
        x = inputx - width/2;
        y = inputy - height/2;
        centerx = x + width/2;
        centery = y + height/2;
        this.team = team;
        health = teammaxhealth[team];
        otherx = x + width;
        othery = y + height;
    }
    public void healed(double healing)
    {
        health += healing;
        if (health > teammaxhealth[team])
        {
        	health = teammaxhealth[team];
        }
    }
    public void update(Dimension d)
    {
    }
    public void updatepoints(double size)
    {
    	width =  ((double)3/(double)5)*size;
    	height =  ((double)3/(double)5)*size;
    	centerx = x + width/2;
    	centery = y + height/2;
    	otherx = x + width;
    	othery = y + height;
    }
    public void draw(Graphics g, double centerdistancex, double centerdistancey, double zoom)
    {
    	double maxhealth;
    	maxhealth = teammaxhealth[team];
    	double range;
    	range = teamrange[team];
    	double tempx = x * zoom;
    	double tempy = y * zoom;
    	tempx = tempx + centerdistancex;
    	tempy = tempy + centerdistancey;
    	double tempwidth = width * zoom;
    	double tempheight = height * zoom;
    	double tempsize = size*zoom;
    	double tempcenterx = tempx + tempwidth/2;
    	double tempcentery = tempy + tempheight/2;
    	double temprange = range * zoom;
    	g.setColor(Point.teamColors[team]);
        g.fillRect((int)tempx, (int)tempy, (int)(tempwidth), (int)(tempheight));
        g.setColor(Color.black);
        g.drawRect((int)tempx, (int)tempy, (int)(tempwidth), (int)(tempheight));
        g.setColor(Color.black);
        g.drawLine((int)(tempx + tempwidth/2 - ((double)3/(double)50)*tempsize), (int)(tempy + tempwidth/2), (int)(tempx + tempwidth/2 + ((double)3/(double)50)*tempsize), (int)(tempy + tempwidth/2));
        g.drawLine((int)(tempx + tempwidth/2), (int)(tempy + tempwidth/2-((double)3/(double)50)*tempsize), (int)(tempx + tempwidth/2), (int)(tempy + tempwidth/2+((double)3/(double)50)*tempsize));
        g.setColor(Color.black);
        g.drawOval((int)(tempcenterx - temprange), (int)(tempcentery - temprange), (int)(temprange*2), (int)(temprange*2));
        int red =  (int)((maxhealth - health)/maxhealth * 255 * 2);
        if (red > 255)
        {
            red = 255;
        }
        if (red < 0)
        {
        	red = 0;
        }
        int green = (int)(510 - (((maxhealth - health)/maxhealth * 255 * 2)));
        if (green > 255)
        {
            green = 255;
        }
        if (green < 0)
        {
            green = 0;
        }
        g.setColor(new Color(red,green,0));
        double percent = (health / maxhealth);
        g.fillRect((int)tempx, (int)tempy - (int)(((double)1/(double)10)*tempsize), (int)(tempwidth * percent),(int)(((double)3/(double)50) * tempsize));
        g.setColor(Color.black);
        g.drawRect((int)tempx, (int)tempy - (int)(((double)1/(double)10)*tempsize), (int)(tempwidth * percent),(int)(((double)3/(double)50) * tempsize));
    }
}
