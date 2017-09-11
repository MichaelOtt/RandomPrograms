package towers;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
public class Button 
{
	public double relativex, relativey, width, height, x, y;
	public String label;
	public int stringlength, stringwidth;
	public boolean isClicked, isVisible, hascost;
	public static int teamnum;
	public boolean[] teamisDisabled = new boolean[teamnum];
	public int[] teamtimes = new int[teamnum];
	public int[] teamcost = new int[teamnum];
	public Menu menu;
	public boolean gamebutton;
	public Button(int relativex, int relativey, int width, int height, String label, int cost, boolean isVisible, String menuString, boolean gamebutton)
	{
		this.gamebutton = gamebutton;
	    this.menu = Menu.assignButton(menuString);
		this.relativex = relativex;
		this.relativey = relativey;
		this.label = label;
		this.width = width;
		this.height = height;
		for (int i = 0; i < teamcost.length; i++)
		{
			teamcost[i] = cost;
		}
		isClicked = false;
		this.isVisible = isVisible;
		if (cost == 0)
		{
			this.hascost = false;
		}
		else
		{
			this.hascost = true;
		}
	}
	public boolean pressed(int mousex, int mousey)
	{
		if (mousex >= x && mousex <= x + width && mousey >= y && mousey <= y + height)
		{
			return true;		
		}
		else
		{
			return false;
		}
	}
	public void draw(Graphics g, Dimension mySize, int team, int[] teamresources)
	{
		String templabel = label;
		int cost = 0;
		if (gamebutton)
		{
		cost = teamcost[team];
		int times;
		times = teamtimes[team];
		if (hascost)
		{
		templabel = label + " ($" + cost + ")";
		templabel = templabel + "(" + times + ")";
		}
		else
		{
		templabel = label;
		}
		}
		stringlength = templabel.length();
		stringwidth = stringlength*7;
		if (isVisible && menu.showing)
		{
			x = relativex;
			y = mySize.height - relativey;
			g.setColor(Color.black);
			g.fillRect((int)x - 1, (int)y - 1, (int)width + 5, (int)height + 5);
			g.setColor(Color.gray);
			if (!isClicked)
			{
				g.fillRect((int)x, (int)y, (int)width + 3, (int)height + 3);
			}
			else
			{
				g.fillRect((int)x + 3, (int)y + 3, (int)width - 1, (int)height - 1);
			}
			g.setColor(Color.black);
			if (!isClicked)
			{
				g.drawString(templabel,(int)(x + width/2 - stringwidth/2), (int)(y + height/2 + 6));
			}
			else
			{
				g.drawString(templabel,(int)(x + width/2 - stringwidth/2 + 3), (int)(y + height/2 + 6 + 3));
			}
			if (gamebutton)
			{
			if ((teamisDisabled[team]) || (cost > teamresources[team]) && hascost)
			{
				g.setColor(new Color(0,0,0,100));
				g.fillRect((int)x - 1, (int)y - 1, (int)width + 5, (int)height + 5);
			}
			}
		}
	}
}
