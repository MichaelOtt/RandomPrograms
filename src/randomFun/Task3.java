package randomFun;
/**
 * Write a description of class Task here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TimerTask;
public class Task3 extends TimerTask
{
    MainAppplet parent;
    public Task3(MainAppplet myParent)
    {
        parent = myParent;
    }
    public void run()
    {
        parent.update();
        parent.painter();
    }
}
