package ImageInteraction;
/**
 * Write a description of class Task2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TimerTask;
public class Task2 extends TimerTask
{
    ImageInteraction parent;
    public Task2(ImageInteraction myParent)
    {
        parent = myParent;
    }
    public void run()
    {
        parent.update();
        parent.draw();
    }
}