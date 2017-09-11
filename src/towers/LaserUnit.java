package towers;
import java.awt.*;

import javax.swing.*;
public class LaserUnit extends Point 
{
	//public static double greenspeed = (double)1/(double)50*size, yellowspeed = (double)1/(double)50*size, greenmaxhealth = 100, yellowmaxhealth = 100, greendamage = 0.5, yellowdamage = 0.5;
	public static double width = (double)2/(double)5 * size, height = (double)2/(double)5 * size;
	public boolean isAttacking;
    public double attackingx, attackingy;
    //public static double greenrange = 2 * size, yellowrange = 2 * size;
    public static double[] teammaxhealth = new double[teamnum];
    public static double[] teamdamage = new double[teamnum];
    public static double[] teamrange = new double[teamnum];
    public static double[] teamspeed = new double[teamnum];
	public LaserUnit(double inputx, double inputy, int team)
	{
		unit = true;
		x = inputx - width/2;
		y = inputy - height/2;
		centerx = x + width/2;
		centery = y + height/2;
		this.team = team;
        health = teammaxhealth[team];
		this.team = team;
		otherx = x + width;
    	othery = y + height;
	}
	public void update(double xattack, double yattack, double distancex, double distancey, int dimensionx, int dimensiony)
	{
		double speed;
		speed = teamspeed[team];
		centerx = x + width/2;
		centery = y + height/2;
		double sum = Math.sqrt(distancex*distancex + distancey*distancey);
		double movementx = distancex/sum * speed;
		double movementy = distancey/sum * speed;
		if (centerx < xattack)
		{
			x+=movementx;
			centerx = x + width/2;
			if (centerx > xattack)
			{
				x = xattack - width/2;
			}
		}
		else if (centerx > xattack)
		{
			x-=movementx;
			centerx = x + width/2;
			if (centerx < xattack)
			{
				x = xattack - width/2;
			}
		}
		else
		{
			
		}
		if (centery < yattack)
		{
			y+=movementy;
			centery = y + height/2;
			if (centery > yattack)
			{
				y = yattack - height/2;
			}
		}
		else if (centery > yattack)
		{
			y-=movementy;
			centery = y + height/2;
			if (centery < yattack)
			{
				y = yattack - height/2;
			}
		}
		else
		{
			
		}
		otherx = x + width;
    	othery = y + height;
    	if (x < 0 - size)
    	{
    		x = 0 - size;
    	}
    	else if (otherx > dimensionx * size + size)
    	{
    		x = dimensionx * size - width + size;
    	}
    	if (y < 0 - size)
    	{
    		y = 0 - size;
    	}
    	else if (othery > dimensiony * size + size)
    	{
    		y = dimensiony * size - height + size;
    	}
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
	public void updatepoints(double size)
    {
    	width =  ((double)2/(double)5)*size;
    	height =  ((double)2/(double)5)*size;
    	centerx = x + width/2;
    	centery = y + height/2;
    	otherx = x + width;
    	othery = y + height;
    }
	public void draw (Graphics g, double centerdistancex, double centerdistancey, double zoom)
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
    	double tempattackingx = (attackingx * zoom) + centerdistancex;
    	double tempattackingy = (attackingy * zoom) + centerdistancey;
    	g.setColor(Point.teamColors[team]);
		g.fillRect((int)tempx,(int)tempy,(int)tempwidth,(int)tempheight);
        if (selected)
        {
            g.setColor(Color.blue);
        }
        else
        {
        g.setColor(Color.black);
        }
		g.drawRect((int)tempx,(int)tempy,(int)tempwidth,(int)tempheight);
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
        if (isAttacking)
        {
            g.setColor(Color.red);
            g.drawLine((int)(tempx + tempwidth/2), (int)(tempy + tempheight/2), (int)tempattackingx, (int)tempattackingy);
        }
	}

}
