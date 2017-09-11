package Timeline;
import javax.swing.*;

import java.lang.Integer;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Timeline extends JApplet implements KeyListener
{
	ArrayList<String>lines;
	ArrayList<String>dates;
	ArrayList<String>events;
	Dimension dim;
	int originalOffsetX = 150;
	int offsetX = 150;
	int spacingX = 200;
	int keyCode;
	boolean showingDates = true;
	boolean showingEvents = true;
	public void init()
	{
		addKeyListener(this);
		lines = new ArrayList<String>();
		dates = new ArrayList<String>();
		events = new ArrayList<String>();

		String fileName = JOptionPane.showInputDialog(null,"type in quiz name");
		if(readFromFile(fileName))
		{
			
		}
		else
		{
			try
			{
				BufferedWriter out = new BufferedWriter(new FileWriter("Timelines/" + fileName + ".txt"));
				while (true)
				{
					String date = JOptionPane.showInputDialog(null,"type in a date or done if finished");
					if (date.equals("done"))
					{
						break;
					}
					String event = JOptionPane.showInputDialog(null,"type in a event");
					out.write(date + " = " + event);
					out.newLine();
					lines.add(date + " = " + event);
					dates.add(date);
					events.add(event);
					System.out.println(date + " = " + event);
				}
				lines.add(null);
				out.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String a=JOptionPane.showInputDialog(null,"type in number of questions");
		}
 
		repaint();
		
	}
	public boolean readFromFile(String name)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader("Timelines/" + name + ".txt"));
			String fileline = "";
			do
			{
			
				try 
				{
					fileline = in.readLine();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				lines.add(fileline);
			}while (fileline != null);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
		
		for (int i = 0; i < lines.size(); i++)
		{
			System.out.println(lines.get(i));
		}
		for (int i = 0; i < lines.size()-1; i++)
		{
			int indexOfDash = lines.get(i).indexOf("=");
			String line = lines.get(i);
			dates.add(line.substring(0,indexOfDash-1));
			events.add(line.substring(indexOfDash+2,line.length()));
		}
		return true;
		
	}
	public void paint(Graphics g)
	{
		dim = getSize();
		g.clearRect(0, 0, dim.width, dim.height);
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		g.drawLine(0,dim.height/2,dim.width,dim.height/2);
		for (int i = 0; i < lines.size()-1; i++)
		{
			int offsetY = 0;
			if (i%2 == 0)
			{
				offsetY = 30;
			}
			int dateWidth = metrics.stringWidth(dates.get(i));
			int eventWidth = metrics.stringWidth(events.get(i));
			int dateHeight = metrics.getHeight();
			int eventHeight = metrics.getHeight();
			g.drawLine(i*spacingX+offsetX, dim.height/2 - 50 + offsetY, i*spacingX+offsetX, dim.height/2 + 50 + offsetY);
			g.drawString(dates.get(i), i*spacingX+offsetX-dateWidth/2, dim.height/2 - 55 + offsetY);
			g.drawString(events.get(i), i*spacingX+offsetX-eventWidth/2, dim.height/2 + offsetY + 65);
			if (!showingDates)
			{
				g.fillRect(i*spacingX+offsetX-dateWidth/2,dim.height/2-55+offsetY-dateHeight,dateWidth,dateHeight+2);
			}
			if (!showingEvents)
			{
				g.fillRect(i*spacingX+offsetX-eventWidth/2,dim.height/2 + offsetY + 65-eventHeight,eventWidth,eventHeight+2);
			}
		}
	}
	public void keyPressed(KeyEvent e) 
	{
		keyCode = e.getKeyCode();
		if (keyCode == e.VK_LEFT)
        {
        	offsetX+=50;
        	if (offsetX > originalOffsetX)
        	{
        		offsetX = originalOffsetX;
        	}
        }
        if (keyCode == e.VK_RIGHT)
        {
        	offsetX-=50;
        	if (offsetX < -(lines.size()-2)*spacingX + dim.width - originalOffsetX)
        	{
        		offsetX = -(lines.size()-2)*spacingX + dim.width - originalOffsetX;
        	}
        }
        if (keyCode == e.VK_UP)
        {
        	showingDates = !showingDates;
        }
        if (keyCode == e.VK_DOWN)
        {
        	showingEvents = !showingEvents;
        }
        repaint();
	}

	public void keyReleased(KeyEvent e) 
	{

		
	}
	public void keyTyped(KeyEvent e) 
	{

		
	}
}
