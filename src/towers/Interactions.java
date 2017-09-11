package towers;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.ArrayList;
import java.awt.image.ImageObserver;
/**
 * Write a description of class Interactions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Interactions
{
    public void Interactions(int dimensionx, int dimensiony, ArrayList <Point>mySuicideBombers,  ArrayList<Point>myLaserTowers, ArrayList<Point>myBarriers, ArrayList<Point>mySuicideBomberSpawners, ArrayList<Point>myHealthFields, ArrayList<Point>myDamageFields, ArrayList<Point>myLaserUnitSpawners, ArrayList<Point>myLaserUnits, ArrayList<Point>myTerritoryBeacons, Dimension mySize, ArrayList<Point>combination)
    {
        //Suicide Bomber Interactions
        for(int i = 0; i < mySuicideBombers.size(); i++)
        {
            SuicideBomber s = (SuicideBomber)mySuicideBombers.get(i);
            Info info = new Info();
            info.closestx = s.centerx;
            info.closesty = s.centery;
            info = distance(s,combination, info, s.team);         
            s.update(info.closestx, info.closesty, info.distancex, info.distancey, dimensionx, dimensiony);
            if (s.going)
            {
            if (s.targetx == s.centerx && s.targety == s.centery)
            {
                s.going = false;
            }
            }
        }
        for(int i = 0; i < mySuicideBombers.size(); i++)
        {
            SuicideBomber s = (SuicideBomber)mySuicideBombers.get(i);
            for (int j = 0; j < combination.size(); j++)
            {
                Point l = combination.get(j);
                if (s.team != l.team)
                {
                    if (s.x <= l.otherx && s.otherx >= l.x &&
                        s.y <= l.othery && s.othery >= l.y)
                    {
                    	double damage;
                    	double maxhealth;
                    	damage = SuicideBomber.teamdamage[s.team];
                    	maxhealth = SuicideBomber.teammaxhealth[s.team];
                        l.attacked(damage);
                        s.attacked(maxhealth);
                    }
                }
            }
        }
        //Laser Unit Interactions
        for(int i = 0; i < myLaserUnits.size(); i++)
        {
            LaserUnit s = (LaserUnit)myLaserUnits.get(i);
            Info info = new Info();
            info.closestx = s.centerx;
            info.closesty = s.centery;
            info = distance(s,combination, info, s.team);
            s.isAttacking = false;
            if (!s.going)
            {
            double damage;
            double range;
            damage = LaserUnit.teamdamage[s.team];
            range = LaserUnit.teamrange[s.team];
            if (info.closestspot <= range)
            {
                s.attackingx = info.closestx;
                s.attackingy = info.closesty;
                s.isAttacking = true;
                Point l = combination.get(info.attackedenemy);
                
                l.attacked(damage);
            }
            else
            {
                s.update(info.closestx, info.closesty, info.distancex, info.distancey, dimensionx, dimensiony);
            }
            }
            else
            {
                s.update(info.closestx, info.closesty, info.distancex, info.distancey, dimensionx, dimensiony);
                if (s.targetx == s.centerx && s.targety == s.centery)
                {
                    s.going = false;
                }
            }
        }

        //Laser Tower Interactions
        for(int i = 0; i < myLaserTowers.size(); i++)
        {
            LaserTower s = (LaserTower)myLaserTowers.get(i);
            Info info = new Info();
            info.attackedenemy = 0;
            distance(s,combination,info, s.team);
            s.isAttacking = false;
            double range;
            double damage;
            range = LaserTower.teamrange[s.team];
            damage = LaserTower.teamdamage[s.team];
            if (info.closestspot <= range)
            {
                s.attackingx = info.closestx;
                s.attackingy = info.closesty;
                s.isAttacking = true;
                Point l = combination.get(info.attackedenemy);
                l.attacked(damage);
            }
        }
        //health field interaction
        for (int i = 0; i < myHealthFields.size(); i++)
        {
            HealthField s = (HealthField)myHealthFields.get(i);
            for (int k = 0; k < combination.size(); k++)
            {
                Point p = combination.get(k);
                if (s.team == p.team)
                {
                	double heal;
                	double range;
                	heal = HealthField.teamheal[s.team];
                	range = HealthField.teamrange[s.team];
                    if (p.health > 0)
                    {
                    double distancex = Math.abs(p.centerx - s.centerx);
                    double distancey = Math.abs(p.centery - s.centery);
                    if (distancex * distancex + distancey * distancey <= range * range)
                    {
                        p.healed(heal);
                    }
                    }
                }
            }       
        }
        //damage field interaction
        for (int i = 0; i < myDamageFields.size(); i++)
        {
            DamageField s = (DamageField)myDamageFields.get(i);
            for (int k = 0; k < combination.size(); k++)
            {
                Point p = combination.get(k);
                if (s.team != p.team)
                {
                	double damage;
                	double range;
                	damage = DamageField.teamdamage[s.team];
                	range = DamageField.teamrange[s.team];
                    double distancex = Math.abs(p.centerx - s.centerx);
                    double distancey = Math.abs(p.centery - s.centery);
                    if (distancex * distancex + distancey * distancey <= range * range)
                    {
                        p.attacked(damage);
                    }
                }
            }       
        }
        death(combination, mySuicideBombers, myLaserTowers, myBarriers, mySuicideBomberSpawners, myHealthFields, myDamageFields, myLaserUnitSpawners, myLaserUnits, myTerritoryBeacons);
    }
    public void drawInteractions()
    {
        
    }
    public Info distance(Point firstSprite, ArrayList<Point>secondSprites, Info info, int team)
    {
        for (int i = 0; i < secondSprites.size(); i++)
        {
            Point p = secondSprites.get(i);
            if (!firstSprite.going)
            {
            if (team != p.team)
            {
            double distancex = Math.abs(p.centerx - firstSprite.centerx);
            double distancey = Math.abs(p.centery - firstSprite.centery);
            if (distancex * distancex + distancey * distancey <= info.closestspot * info.closestspot)
            {
                info.closestspot = Math.sqrt(distancex * distancex + distancey * distancey);
                info.closestx = p.centerx;
                info.closesty = p.centery;
                info.distancex = distancex;
                info.distancey = distancey;
                info.attackedenemy = i;
            }
            }
            }
            else
            {
                info.distancex = Math.abs(firstSprite.centerx - firstSprite.targetx);
                info.distancey = Math.abs(firstSprite.centery - firstSprite.targety);
                info.closestx = firstSprite.targetx;
                info.closesty = firstSprite.targety;
                info.closestspot = Math.sqrt(info.distancex * info.distancex + info.distancey * info.distancey);
            }
        }
        return info;
    }
    public void death(ArrayList<Point>combination, ArrayList <Point>mySuicideBombers,  ArrayList<Point>myLaserTowers, ArrayList<Point>myBarriers, ArrayList<Point>mySuicideBomberSpawners, ArrayList<Point>myHealthFields, ArrayList<Point>myDamageFields, ArrayList<Point>myLaserUnitSpawners, ArrayList<Point>myLaserUnits, ArrayList<Point>myTerritoryBeacons)
    {
        checkdeath(mySuicideBombers);
        checkdeath(myLaserTowers);
        checkdeath(myBarriers);
        checkdeath(mySuicideBomberSpawners);
        checkdeath(myHealthFields);
        checkdeath(myDamageFields);
        checkdeath(myLaserUnitSpawners);
        checkdeath(myLaserUnits);
        checkdeath(myTerritoryBeacons);
    }
    public void checkdeath(ArrayList<Point>list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            Point l = list.get(i);
            if (l.isDead)
            {
                  list.remove(l);
                  
            }
        }
        
    }
    public ArrayList<Point> combine(ArrayList<Point>full, ArrayList<Point>part)
    {
        for (int i = 0; i < part.size(); i++)
        {
            Point l = part.get(i);
            full.add(l);
        }
        return full;
    }
}
