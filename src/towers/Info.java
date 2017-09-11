package towers;
/**
 * Write a description of class Info here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Info
{
    public double distancex, distancey, closestx, closesty, closestspot, health;
    public int attackedenemy, whichlist;
    public Info()
    {
        closestspot = 5000000;
        distancex = 0;
        distancey = 1;      
        health = 10000;
    }
}
