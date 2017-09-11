package towers;
/**
 * Write a description of class Task here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TimerTask;
public class Task5 extends TimerTask
{
    ShapeWar parent;
    public Task5(ShapeWar myParent)
    {
        parent = myParent;
    }
    public void run()
    {
        parent.update();
        parent.painter();
    }
}