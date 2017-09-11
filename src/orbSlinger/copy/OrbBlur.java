package orbSlinger.copy;
import java.awt.*;

import javax.swing.*;
public class OrbBlur 
{
	public double x,y,width,height, originalwidth, originalheight;
	public boolean isDead = false;
	public OrbBlur(double inputx, double inputy, double inputwidth, double inputheight)
	{
		x = inputx;
		y = inputy;
		width = inputwidth;
		height = inputheight;
		originalwidth = width;
		originalheight = height;
	}
	public void update(int bluramount)
	{
		width -= originalwidth/bluramount;
		height -= originalheight/bluramount;
		if (width <= 0 || height <= 0)
		{
			isDead = true;
		}
	}
	public void draw(Graphics g, double red, double green, double blue, boolean running)
	{
		g.setColor(Color.black);
		if (running)
		{
		g.setColor(new Color((int)red, (int)green, (int)blue));
		}
		g.fillOval((int)(x - width/2),(int)(y - height/2),(int)width,(int)height);
	}

}
