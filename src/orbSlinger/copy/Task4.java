package orbSlinger.copy;
/**
 * Write a description of class Task here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TimerTask;
public class Task4 extends TimerTask
{
    OrbSlinger parent;
    public Task4(OrbSlinger myParent)
    {
        parent = myParent;
    }
    public void run()
    {
        parent.update();
        parent.painter();
    }
}
