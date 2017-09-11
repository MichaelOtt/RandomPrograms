package randomFun;
import java.awt.*;
import javax.swing.*;
public class Sprite3 
{
	public static int red = 2,green = 2,blue = 2;
	public static int redup = (int)(Math.random()*4 + 1), greenup = (int)(Math.random()*4 + 1), blueup = (int)(Math.random()*4 + 1);
	public int red_,green_,blue_;
	public double x,y,speedx,speedy,width,height;
	public Sprite3(double inputx, double inputy)
	{
		int randomize = (int)(Math.random() * 3);
		if (randomize == 0)
		{
			red += redup;
		}
		else if (randomize == 1)
		{
			green += greenup;
		}
		else
		{
			blue += blueup;
		}
		if (red >= 250)
		{
			redup = -(int)(Math.random()*4 + 1);
			red = 250;
		}
		if (red <= 5)
		{
			redup = (int)(Math.random()*4 + 1);
			red = 5;
		}
		if (blue >= 250 )
		{
			blueup = -(int)(Math.random()*4 + 1);
			blue = 250;
		}
		if ( blue <= 5)
		{
			blueup = (int)(Math.random()*4 + 1);
			blue = 5;
		}
		if (green >= 250)
		{
			greenup = -(int)(Math.random()*4 + 1);
			green = 250;
		}
		if(green <= 5)
		{
			greenup = (int)(Math.random()*4 + 1);
			green = 5;
		}
		red_ = red;
		blue_ = blue;
		green_ = green;
		x = inputx;
		y = inputy;
		speedx = 5;
		speedy = 5;
		width = 20;
		height = 20;
	}
	public void update(Dimension dim)
	{
		x += speedx;
		y += speedy;
		if (x < 0)
		{
			speedx *= -1;
			x = 1;
		}
		if (x > dim.width - width)
		{
			speedx *= -1;
			x = dim.width - width;
		}
		if (y < 0)
		{
			speedy *= -1;
			y = 1;
		}
		if (y > dim.height - height)
		{
			speedy *= -1;
			y = dim.height - height;
		}
	}
	public void draw(Graphics g)
	{
		g.setColor(new Color(red_,green_,blue_));
		g.fillOval((int)x,(int)y,(int)width,(int)height);
	}

}
