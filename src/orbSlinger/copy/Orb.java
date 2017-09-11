package orbSlinger.copy;
import java.awt.*;

import javax.swing.*;
public class Orb 
{
	
	public double x,y,speedx,speedy,radius, frictionx, frictiony;
	public Orb(double inputx, double inputy, double inputradius)
	{
		x = inputx;
		y = inputy;
		speedx = 0;
		speedy = 0;
		radius = inputradius;
	}
	public void update(Dimension dim, double friction, int mousex, int mousey, boolean grabbing)
	{

		if (grabbing == true)
		{
		double differencex = mousex - (x + radius/2);
		double differencey = mousey - (y + radius/2);
		speedx += (differencex / 100) / ((radius/2)*(radius/2) / 100);
		speedy += (differencey / 100) / ((radius/2)*(radius/2) / 100);
		//double tempx = Math.abs(speedx) * Math.abs(speedx);
		//double tempy = Math.abs(speedy) * Math.abs(speedy);
		/*if (tempx + tempy < (friction * friction))
		{
			speedx = 0;
			speedy = 0;
		}*/
		}
		x += speedx;
		y += speedy;
		double sum = Math.sqrt(speedx*speedx + speedy*speedy);
		frictionx = friction * (Math.abs(speedx) / sum);
		frictiony = friction * (Math.abs(speedy) / sum);
		//frictionx = friction;
		//frictiony = friction;
		if (x < 0)
		{
			speedx *= -1;
			x = 1;
		}
		if (x > dim.width - radius)
		{
			speedx *= -1;
			x = dim.width - radius;
		}
		if (y < 0)
		{
			speedy *= -1;
			y = 1;
		}
		if (y > dim.height - radius)
		{
			speedy *= -1;
			y = dim.height - radius;
		}
		if (speedx > 0)
		{
			speedx-=frictionx / ((radius/2)*(radius/2) / 100);
			if (speedx < 0)
			{
				speedx = 0;
			}
		}
		else if (speedx < 0)
		{
			speedx+= frictionx / ((radius/2)*(radius/2) / 100);
			if (speedx > 0)
			{
				speedx = 0;
			}
		}
		else
		{
			
		}
		if (speedy > 0)
		{
			speedy -= frictiony / ((radius/2)*(radius/2) / 100);
			if (speedy < 0)
			{
				speedy = 0;
			}
		}
		else if (speedy < 0)
		{
			speedy+= frictiony / ((radius/2)*(radius/2) / 100);
			if (speedy > 0)
			{
				speedy = 0;
			}
		}
		else
		{
			
		}
		
	}
	public void draw(Graphics g, double red, double green, double blue, boolean running)
	{
		g.setColor(Color.black);
		if (running)
		{
		g.setColor(new Color((int)red, (int)green, (int)blue));
		}
		g.fillOval((int)x,(int)y,(int)radius,(int)radius);
	}

}
