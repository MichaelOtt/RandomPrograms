import java.awt.*;
import javax.swing.*;

public class appletTesting extends JApplet
{
	Dimension dim;
	public void paint (Graphics screen)
	{
		dim = getSize();
		Image pic = getImage(getDocumentBase(), "bunny war.jpg");
		screen.clearRect(0, 0, dim.width, dim.height);
		screen.drawImage(pic,0,0,dim.width,dim.height,this);
		Font f = new Font("Arial", Font.BOLD, 36);
		screen.setFont(f);
		screen.setColor(Color.white);
		screen.drawString("Testing is awsome", dim.width - 500, dim.height - 100);
		screen.drawLine(10, 10, 75, 75);
		screen.drawRect(10,10, 50, 50);
		screen.fillRect(60, 60, 25, 25);
		int x[] = {50,150,250,250,50};
		int y[] = {50,10,50,100,100};
		int points = x.length;
		
		screen.fillPolygon(x, y, points);
		
		
		
		
		
	}
}
