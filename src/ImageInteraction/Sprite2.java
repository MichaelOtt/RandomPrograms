package ImageInteraction;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.ImageObserver;
public class Sprite2
{
    public double x,y; 
    public double speedx, speedy, width, height;
    public boolean isDead = false;
    public int explosioncount;
    private double explosionheight, explosionwidth;
    private Image []explosion;
    private Image myImage;
    private ImageObserver io;
    public boolean exploding = false;
    Sprite2(int x, int y, double speedX, double speedY, ImageObserver io)
    {
        explosioncount = 0;
        explosion = new Image[18];
        try 
        {
            myImage = ImageIO.read(new File("bomb.png"));
            for(int i = 0; i < 18; i++)
            {
                explosion[i] = ImageIO.read(new File("explosion" + i + ".png"));
            }
        }catch (IOException e) {
        }
        this.io = io;
        width = myImage.getWidth(io);
        height = myImage.getHeight(io);
        isDead = false;
        this.x = x - width/2;
        this.y = y - height/2;
        this.speedx = speedX;
        this.speedy = speedY;
        
    }
    public void update(Dimension dim)
    {
        if (exploding == true)
        {
        }
        if (exploding && explosioncount<17)
        {
            explosioncount++;
        }
        if (explosioncount == 17)
        {
            isDead = true;
        }
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
            x = dim.width - width - 1;
        }
        if (y < 0)
        {
            speedy *= -1;
            y = 1;
        }
        if (y > dim.height - height)
        {
            speedy *= -1;
            y = dim.height - height - 1;
        }
        
    }
    public void CheckCollision(Sprite2 other)
    {
        if (x <= other.x + other.width && x + width >= other.x && y <= other.y + other.height && y + height >= other.y)
        {
            exploding = true;
            
            speedx = 0;
            speedy = 0;
            other.exploding = true;
            other.speedx = 0;
            other.speedy = 0;
        }
    }
    public void paint(Graphics g)
    {
        if (exploding)
        {
            explosionwidth = explosion[explosioncount].getWidth(io);
            explosionheight = explosion[explosioncount].getHeight(io);
            g.drawImage(explosion[explosioncount],(int)(x - explosionwidth/2),(int)(y - explosionheight/2), io);
        }
        else
        {
        g.drawImage(myImage,(int)x,(int)y,io);
        }
        
    }

}
