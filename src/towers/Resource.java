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
public class Resource extends Point 
{
    public static double width = (double)3/(double)5 * size, height = (double)3/(double)5 * size;
    public Resource(int inputx, int inputy)
    {
        x = inputx - width/2;
        y = inputy - height/2;
        centerx = x + width/2;
        centery = y + height/2;
        otherx = x + width;
        othery = y + height;
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
    	double tempx = x * zoom;
    	double tempy = y * zoom;
    	tempx = tempx + centerdistancex;
    	tempy = tempy + centerdistancey;
    	double tempwidth = width * zoom;
    	double tempheight = height * zoom;
    	double tempotherx = tempx + tempwidth;
    	double tempothery = tempy + tempheight;
        g.setColor(Color.blue);
        int[] xpos = {(int)(tempx + tempwidth/2), (int)(tempotherx), (int)(tempx + tempwidth/2), (int)(tempx)};
        int[] ypos = {(int)(tempy) , (int)(tempy + tempheight/2), (int)(tempothery), (int)(tempy+tempheight/2)};
        g.fillPolygon(xpos,ypos,4);
        g.setColor(Color.black);
        g.drawPolygon(xpos,ypos,4);

    }
}
