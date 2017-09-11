package towers;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;
/**
 * Write a description of class SuicideBomberSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuicideBomberSpawner extends Point 
{
    public static int cost = 100;
    public double counter;
   // public static double greencountertop = 150, yellowcountertop = 150, greenmaxhealth = 500, yellowmaxhealth = 500;
    public static double width = (double)3/(double)5 * size, height = (double)3/(double)5 * size;
    public static double[] teammaxhealth = new double[teamnum];
    public static double[] teamcountertop = new double[teamnum];
    public SuicideBomberSpawner(int inputx, int inputy, int team)
    {
    	unit = false;
        x = inputx - width/2;
        y = inputy - height/2;
        centerx = x + width/2;
        centery = y + height/2;
        counter = 0;
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
    public void update(ArrayList<Point> suicideBombers)
    {
    	double countertop;
    	countertop = teamcountertop[team];
        counter++;
        if (counter >= countertop)
        {
            counter = 0;
            suicideBombers.add(new SuicideBomber (centerx, centery, team));
        }
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
    	double tempx = x * zoom;
    	double tempy = y * zoom;
    	tempx = tempx + centerdistancex;
    	tempy = tempy + centerdistancey;
    	double tempwidth = width * zoom;
    	double tempheight = height * zoom;
    	double tempsize = size*zoom;
    	g.setColor(Point.teamColors[team]);
        g.fillOval((int)tempx, (int)tempy, (int)(tempwidth), (int)(tempwidth));
        g.setColor(Color.black);
        g.drawOval((int)tempx, (int)tempy, (int)(tempwidth), (int)(tempwidth));
        g.fillOval((int)(tempx + tempwidth/2 - ((double)6/(double)50)*tempsize), (int)(tempy + tempheight/2 - ((double)6/(double)50)*tempsize), (int)((double)12/(double)50*tempsize), (int)((double)12/(double)50*tempsize));
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
