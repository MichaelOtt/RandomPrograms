package physicsEngine;
/**
 * Write a description of class Task2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.TimerTask;
public class Task extends TimerTask
{
    physicsEngine parent;
    
    public Task(physicsEngine myParent)
    {
        parent = myParent;
    }
    public void run()
    {
        parent.update();
        parent.draw();
    }
}