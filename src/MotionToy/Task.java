package MotionToy;
/**
 * Write a description of class Task here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TimerTask;
public class Task extends TimerTask
{
    MotionToy parent;
    public Task(MotionToy myParent)
    {
        parent = myParent;
    }
	public void run()
    {
        parent.update();
        parent.painter();
    }
}
