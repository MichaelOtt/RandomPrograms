package towers;
import java.applet.Applet;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
public class ShapeWar extends JApplet implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener
{
	private int[] keys = {16,49,50,51,52,53,54,55};
	private int boundkey = -1;
	private int teamnum = 2;
	private final int maxteams = 100;
	private Color[] teamColors = new Color[maxteams];
	private int team = 0;
	private int createdbeacons = 2;
	private int dimensionx = 100, dimensiony = 50;
	private int resourcenum = 200;
	private double size = 50;
	private double zoom = 50/size;
	private int resourcesgiven = 500;
	private double horizontalshift = 0;
	private double verticalshift = 0;
	private double horizontalshifting = 0;
	private double verticalshifting = 0;
	private double centerdistancex, centerdistancey;
    private Timer myTimer;
    private Task5 myTask;
    private Dimension mySize;
    private Image offscreen;
    private Graphics bufferGraphics;
    private Interactions Interaction = new Interactions();
    private int[][] territory = new int[dimensionx + 1][dimensiony + 1];
    ArrayList<Point>mySuicideBombers;
    ArrayList<Point>myLaserTowers;
    ArrayList<Point>mySuicideBomberSpawners;
    ArrayList<Point>myBarriers;
    ArrayList<Point>myHealthFields;
    ArrayList<Point>myDamageFields;
    ArrayList<Point>myLaserUnitSpawners;
    ArrayList<Point>myLaserUnits;
    ArrayList<Point>myTerritoryBeacons;
    ArrayList<Point>myResources;
    ArrayList<Button>myButtons;
    ArrayList<Menu>myMenus;
    ArrayList<Button>myMenuButtons;
    ArrayList<Point>combination = new ArrayList<Point>();
    private boolean creatingLaserTower, creatingSuicideBomberSpawner, creatingBarrier, creatingHealthField, creatingDamageField, creatingLaserUnitSpawner, creatingTerritoryBeacon;
    private boolean over = false;
    private int mousex, mousey;
    private int keyCode;
    private int[] teamresources = new int[teamnum];
    private int[] teamgain = new int[teamnum];
    //private boolean team;
    private int resourcecountertop = 50, resourcecounter;
    private int firstmousex, firstmousey;
    private boolean dragging;
    private boolean[] teamdeath = new boolean[teamnum];
    private int[] teamunits = new int[teamnum];
    private boolean[] teamwinner = new boolean[teamnum];
    private boolean winner;
    private boolean reset;
    private boolean finishedInitializing;
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean territorysquares = true;
    private boolean ongrid = true;
    public void keyPressed (KeyEvent e)
    {
    	if (Menu.showing("Game Menu"))
    	{
    		keyPressedRunning(e);
    	}
    	else
    	{
    		keyPressedMenu(e);
    	}
    }
    public void keyPressedMenu(KeyEvent e)
    {
    	if (boundkey != -1)
    	{
    		int indexofswitch = -1;
    		for (int i = 0; i < keys.length; i++)
    		{
    			if (keys[i] == e.getKeyCode() && i != boundkey)
    			{
    				indexofswitch = i;
    			}
    		}
    		if (indexofswitch == -1)
    		{
    			keys[boundkey] = e.getKeyCode();
    		}
    		else
    		{
    			keys[indexofswitch] = keys[boundkey];
    			keys[boundkey] = e.getKeyCode();
    		}
    		boundkey = -1;
    	}
    }
    public void keyPressedRunning (KeyEvent e)
    {
        keyCode = e.getKeyCode();
        if (keyCode == keys[0])
        {
            team++;
            if (team == teamnum)
            {
            	team = 0;
            }
            if (teamdeath[team] == true)
            {
            do
            {
            	team++;
            	 if (team == teamnum)
                 {
                 	team = 0;
                 }
            }while(teamdeath[team] == true);
            }
        }
        if (keyCode == keys[1])
        {
            creatingLaserTower = !creatingLaserTower;
            creatingSuicideBomberSpawner = false;
            creatingBarrier = false;
            creatingDamageField = false;
            creatingHealthField = false;
            creatingLaserUnitSpawner = false;
            creatingTerritoryBeacon = false;
        }
        if (keyCode == keys[2])
        {
            creatingSuicideBomberSpawner = !creatingSuicideBomberSpawner;
            creatingLaserTower = false;
            creatingBarrier = false;
            creatingDamageField = false;
            creatingHealthField = false;
            creatingLaserUnitSpawner = false;
            creatingTerritoryBeacon = false;
        }
        if (keyCode == keys[3])
        {
            creatingBarrier = !creatingBarrier;
            creatingLaserTower = false;
            creatingSuicideBomberSpawner = false;
            creatingDamageField = false;
            creatingHealthField = false;
            creatingLaserUnitSpawner = false;
            creatingTerritoryBeacon = false;
        }
        if (keyCode == keys[4])
        {
            creatingHealthField = !creatingHealthField;
            creatingLaserTower = false;
            creatingSuicideBomberSpawner = false;
            creatingDamageField = false;
            creatingBarrier = false;
            creatingLaserUnitSpawner = false;
            creatingTerritoryBeacon = false;
        }
        if (keyCode == keys[5])
        {
            creatingDamageField = !creatingDamageField;
            creatingLaserTower = false;
            creatingSuicideBomberSpawner = false;
            creatingHealthField = false;
            creatingBarrier = false;
            creatingLaserUnitSpawner = false;
            creatingTerritoryBeacon = false;
        }
        if (keyCode == keys[6])
        {
            creatingLaserUnitSpawner = !creatingLaserUnitSpawner;
            creatingLaserTower = false;
            creatingSuicideBomberSpawner = false;
            creatingHealthField = false;
            creatingBarrier = false;
            creatingDamageField = false;
            creatingTerritoryBeacon = false;
        }
        if (keyCode == keys[7])
        {
            creatingTerritoryBeacon = !creatingTerritoryBeacon;
            creatingLaserTower = false;
            creatingSuicideBomberSpawner = false;
            creatingHealthField = false;
            creatingBarrier = false;
            creatingDamageField = false;
            creatingLaserUnitSpawner = false;
        }
        if (keyCode == e.VK_UP)
        {
        	verticalshifting = -1/(zoom*size)*15;
        }
        if (keyCode == e.VK_DOWN)
        {
        	verticalshifting = 1/(zoom*size)*15;
        }
        if (keyCode == e.VK_LEFT)
        {
        	horizontalshifting = -1/(zoom*size)*15;
        }
        if (keyCode == e.VK_RIGHT)
        {
        	horizontalshifting = 1/(zoom*size)*15;
        }
    }
    public void keyReleased (KeyEvent e)
    {
    	if (Menu.showing("Game Menu"))
    	{
    	keyReleasedRunning(e);
    	}
    }
    public void keyReleasedRunning (KeyEvent e)
    {
    	int keyCode = e.getKeyCode();
    	if (keyCode == e.VK_UP)
        {
        	verticalshifting = 0;
        }
        if (keyCode == e.VK_DOWN)
        {
        	verticalshifting = 0;
        }
        if (keyCode == e.VK_LEFT)
        {
        	horizontalshifting = 0;
        }
        if (keyCode == e.VK_RIGHT)
        {
        	horizontalshifting =0;
        }
    }
    public void keyTyped (KeyEvent e)
    {      
    }
    public void mouseDragged(MouseEvent e)
    {        
        mySize = getSize();
        mousex = e.getX();
        mousey = e.getY();
        if (mousex <= 0)
        {
            mousex = 1;
        }
        if (mousex >= mySize.width)
        {
            mousex = mySize.width - 1;
        }
        if (mousey <= 0)
        {
            mousey = 1;
        }
        if (mousey >= mySize.height)
        {
            mousey = mySize.height - 1;
        }
    }
    public void mouseMoved(MouseEvent e)
    {
        mousex = e.getX();
        mousey = e.getY();
    }
    public void mouseClicked(MouseEvent e)
    {

    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
    	if (Menu.showing("Game Menu"))
    	{
    		mousePressedRunning(e);
    	}
    	else
    	{
    		mousePressedMenu(e);
    	}
    }
    public void mousePressedRunning(MouseEvent e)
    {
    	centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
        centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
        
        mousex = e.getX();
        mousey = e.getY();
        int mousecode = e.getButton();
        if (mousecode == 1)
        {
	        overOther(combination);
	        double tempmousex = (double)((mousex - centerdistancex)/zoom) / (size);
	        double tempmousey = (double)((mousey - centerdistancey)/zoom) / (size);
	        tempmousex = Math.round(tempmousex);
	        tempmousey = Math.round(tempmousey);
	        tempmousex *= (size);
	        tempmousey *= (size);
	        int cost = 0;
	        if (creatingLaserTower == true)
	        {
	            cost = LaserTower.cost;
	        }
	        else if (creatingSuicideBomberSpawner == true)
	        {
	            cost = SuicideBomberSpawner.cost;
	        }        
	        else if (creatingBarrier == true)
	        {
	            cost = Barrier.cost;
	        }
	        else if (creatingHealthField == true)
	        {
	            cost = HealthField.cost;
	        }
	        else if (creatingDamageField == true)
	        {
	            cost = DamageField.cost;
	        }
	        else if (creatingLaserUnitSpawner == true)
	        {
	            cost = LaserUnitSpawner.cost;
	        }
	        else if (creatingTerritoryBeacon == true)
	        {
	            cost = TerritoryBeacon.cost;
	        }
	        else
	        {
	        	boolean buttonclicked = false;
		        for (int i = 0; i < myButtons.size(); i++)
		        {
		        	Button s = myButtons.get(i);
		        	if (s.pressed(mousex, mousey) && s.isVisible && s.menu.showing)
		        	{
		        		buttonclicked = true;
		        		s.isClicked = true;
		        	}
		        }
	        	if (!buttonclicked)
	        	{
		            dragging = true;
		            firstmousex = mousex;
		            firstmousey = mousey;
		            for (int i = 0; i < combination.size(); i++)
		            {
		                Point l = combination.get(i);
		                if (l.unit)
		                {
		                l.selected = false;
		                }
		            }
	        	}
	        	
	        }
	        if (over == false)
	        {
	            teamresources[team] -= cost;
	        }
	        if (creatingLaserTower == true && over == false)
	        {
	            creatingLaserTower = false;
	            myLaserTowers.add(new LaserTower((int)tempmousex,(int)tempmousey, team));
	        }
	        if (creatingSuicideBomberSpawner == true && over == false)
	        {
	            creatingSuicideBomberSpawner = false;
	            mySuicideBomberSpawners.add(new SuicideBomberSpawner((int)tempmousex,(int)tempmousey, team));
	        }        
	        if (creatingBarrier == true && over == false)
	        {
	            creatingBarrier = false;
	            myBarriers.add(new Barrier((int)tempmousex,(int)tempmousey, team));
	        }
	        if (creatingHealthField == true && over == false)
	        {
	            creatingHealthField = false;
	            myHealthFields.add(new HealthField((int)tempmousex, (int)tempmousey, team));
	        }
	        if (creatingDamageField == true && over == false)
	        {
	            creatingDamageField = false;
	            myDamageFields.add(new DamageField((int)tempmousex, (int)tempmousey, team));            
	        }
	        if (creatingLaserUnitSpawner == true && over == false)
	        {
	            creatingLaserUnitSpawner = false;
	            myLaserUnitSpawners.add(new LaserUnitSpawner((int)tempmousex, (int)tempmousey, team));
	        }
	        if (creatingTerritoryBeacon == true && over == false)
	        {
	            creatingTerritoryBeacon = false;
	            myTerritoryBeacons.add(new TerritoryBeacon((int)tempmousex, (int)tempmousey, team));
	        }
        }
        else
        {
        	centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
            centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
            double tempmousex = (mousex - centerdistancex) / zoom;
            double tempmousey = (mousey - centerdistancey) / zoom;
            for (int i = 0; i < combination.size(); i++)
            {
                Point l = combination.get(i);
                if (l.selected)
                {
                	if (l.unit)
                	{
                    l.going = true;
                    l.targetx = tempmousex;
                    l.targety = tempmousey;
                	}
                }
            }
        }
    }
    public void mousePressedMenu(MouseEvent e)
    {
        for (int i = 0; i < myMenuButtons.size(); i++)
        {
        	Button s = myMenuButtons.get(i);
        	if (s.pressed(mousex, mousey) && s.isVisible && s.menu.showing)
        	{
        		s.isClicked = true;
        	}
        }
        repaint();
    }
    public void mouseReleased(MouseEvent e)
    {
    	if (Menu.showing("Game Menu"))
    	{
    		mouseReleasedRunning(e);
    	}
    	else
    	{
    		mouseReleasedMenu(e);
    	}
    }
    public void mouseReleasedMenu(MouseEvent e)
    {
    	for (int i = 0; i < myMenuButtons.size(); i++)
        {
        	Button b = myMenuButtons.get(i);
        	if (b.isClicked && b.pressed(e.getX(), e.getY()))
        	{
        	b.isClicked = false;
        	String a = "";
        	int buttonnum = i;
        	switch(buttonnum)
        	{
        		
        		case 0:
	        		initializeGame();
	        		Menu.closeMenu("Main Menu");
	        		Menu.openMenu("Game Menu");
	        		break;
        		case 1:
        			Menu.closeMenu("Main Menu");
        			Menu.openMenu("Multiplayer Menu");
        			break;
        		case 2:
	        		Menu.closeMenu("Main Menu");
	        		Menu.openMenu("Options Menu");
	        		break;
        		case 3:
        			Menu.closeMenu("Main Menu");
        			Menu.openMenu("Key Bindings Menu");
        			
        			break;
        		case 4:
        			Menu.closeMenu("Options Menu");
        			Menu.openMenu("Main Menu");
        			writeToFile();
        			break;
        		case 5:
        			Menu.closeMenu("Key Bindings Menu");
        			Menu.openMenu("Main Menu");
        			writeToFile();
        			boundkey = -1;
        			break;
        		case 6:
        			a =JOptionPane.showInputDialog(null,"type in width");
        			dimensionx = Integer.parseInt(a);
        			break;
        		case 7:
        			a =JOptionPane.showInputDialog(null,"type in height");
        			dimensiony = Integer.parseInt(a);
        			break;
        		case 8:
        			a =JOptionPane.showInputDialog(null,"type in starting number of beacons");
        			createdbeacons = Integer.parseInt(a);
        			break;
        		case 9:
        			a =JOptionPane.showInputDialog(null,"type in starting number of teams");
        			teamnum = Integer.parseInt(a);
        			if (teamnum > maxteams)
        			{
        				teamnum = maxteams;
        			}
        			break;
        		case 10:
        			a = JOptionPane.showInputDialog(null,"type in starting number of resource spots");
        			resourcenum = Integer.parseInt(a);
        			break;
        		case 11:
        			a = JOptionPane.showInputDialog(null,"type in starting resources");
        			resourcesgiven = Integer.parseInt(a);
        			break;
        		case 12:
        			if (boundkey != 6)
        			boundkey = 6;
        			else
        			boundkey = -1;
        			break;
        		case 13:
        			if (boundkey != 2)
        			boundkey = 2;
        			else
            		boundkey = -1;
        			break;
        		case 14:
        			if (boundkey != 7)
        			boundkey = 7;
        			else
                	boundkey = -1;
        			break;
        		case 15:
        			if (boundkey != 3)
        			boundkey = 3;
        			else
                    boundkey = -1;
        			break;
        		case 16:
        			if (boundkey != 4)
        			boundkey = 4;
        			else
                    boundkey = -1;
        			break;
        		case 17:
        			if (boundkey != 5)
        			boundkey = 5;
        			else
                    boundkey = -1;
        			break;
        		case 18:
        			if (boundkey != 1)
        			boundkey = 1;
        			else
                    boundkey = -1;
        			break;
        		case 19:
        			if (boundkey != 0)
        			boundkey = 0;
        			else
                    boundkey = -1;
        			break;
        		case 20:
        			Menu.closeMenu("Main Menu");
        			Menu.openMenu("Instructions Menu");
        			break;
        		case 21:
        			Menu.openMenu("Main Menu");
        			Menu.closeMenu("Instructions Menu");
        			break;
        		case 22:
        			Menu.openMenu("Main Menu");
        			Menu.closeMenu("Multiplayer Menu");
        			break;
        		case 25:
        			territorysquares = !territorysquares;
        			if (territorysquares)
        			{
        				myMenuButtons.get(25).label = "Territory Squares (on)";
        			}
        			else
        			{
        				myMenuButtons.get(25).label = "Territory Squares (off)";
        			}
        			break;
        		case 26:
        			ongrid = !ongrid;
        			if (ongrid)
        			{
        				myMenuButtons.get(26).label = "On Grid";
        			}
        			else
        			{
        				myMenuButtons.get(26).label = "On Squares";
        			}
        		
        	}
        	}
        	else
        	{
        		b.isClicked = false;
        	}
        }
    	repaint();
    }
    public void mouseReleasedRunning(MouseEvent e)
    { 
    	centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
        centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
        int mousecode = e.getButton();
        if (mousecode == 1)
        {
        for (int k = 0; k < myButtons.size(); k++)
        {
        	Button b = myButtons.get(k);
        	if (b.isClicked && b.pressed(e.getX(), e.getY()))
        	{
        	b.isClicked = false;
        	int buttonnum = k;
///////////////////////////////////////////////////////////BUTTON ASSIGNMENTS////////////////////////////////////////////////////////////////////////////////////////////
        		//Suicide Bomber
        		if (buttonnum == 0)
        		{	
        			myButtons.get(7).isVisible = !myButtons.get(7).isVisible;
        			myButtons.get(8).isVisible = !myButtons.get(8).isVisible;
        			myButtons.get(9).isVisible = !myButtons.get(9).isVisible;
        			myButtons.get(10).isVisible = !myButtons.get(10).isVisible;
        		}
        		//Laser Unit
        		if (buttonnum == 1)
        		{
        			myButtons.get(11).isVisible = !myButtons.get(11).isVisible;
        			myButtons.get(12).isVisible = !myButtons.get(12).isVisible;
        			myButtons.get(13).isVisible = !myButtons.get(13).isVisible;
        			myButtons.get(14).isVisible = !myButtons.get(14).isVisible;
        			myButtons.get(15).isVisible = !myButtons.get(15).isVisible;
        		}
        		//Barrier
        		if (buttonnum == 2)
        		{
        			myButtons.get(16).isVisible = !myButtons.get(16).isVisible;
        		}
        		//Laser Tower
        		if (buttonnum == 3)
        		{
        			myButtons.get(17).isVisible = !myButtons.get(17).isVisible;
        			myButtons.get(18).isVisible = !myButtons.get(18).isVisible;
        			myButtons.get(19).isVisible = !myButtons.get(19).isVisible;
        		}
        		if (buttonnum == 4)
        		{
        			myButtons.get(20).isVisible = !myButtons.get(20).isVisible;
        			myButtons.get(21).isVisible = !myButtons.get(21).isVisible;
        			myButtons.get(22).isVisible = !myButtons.get(22).isVisible;
        		}
        		if (buttonnum == 5)
        		{
        			myButtons.get(23).isVisible = !myButtons.get(23).isVisible;
        			myButtons.get(24).isVisible = !myButtons.get(24).isVisible;
        			myButtons.get(25).isVisible = !myButtons.get(25).isVisible;
        		}
        		if (buttonnum == 6)
        		{
        			myButtons.get(26).isVisible = !myButtons.get(26).isVisible;
        			myButtons.get(27).isVisible = !myButtons.get(27).isVisible;
        		}
        		if (buttonnum == 28)
        		{
        			Menu.closeMenu("Game Menu");
					Menu.openMenu("Main Menu");
					for (int i = 0; i < myMenuButtons.size(); i++)
					{
						Button unclick = myMenuButtons.get(i);
						unclick.isClicked = false;
					}
        		}
        		if (buttonnum == 29)
        		{
        			initializeGame();
        		}
        		// green button control
        			if (teamresources[team] >= b.teamcost[team] && b.hascost && !b.teamisDisabled[team])// out of bounds exception
        			{
        				b.teamtimes[team]++;
        				teamresources[team] -= b.teamcost[team];
        				switch (buttonnum)
        				{
        					//Suicide Bomber
        					case 7: SuicideBomber.teamspeed[team] += (double)2/(double)50 * size;
        						break;
        					case 8: SuicideBomber.teamdamage[team] += 20;
        						break;
        					case 9: SuicideBomber.teammaxhealth[team] += 20;
        						for (int i = 0; i < mySuicideBombers.size(); i++)
        						{
        							SuicideBomber s = (SuicideBomber)mySuicideBombers.get(i);
        							if (s.team == team)
        							{
        								s.healed(20);
        							}
        						}
        						break;
        					case 10: 
    							SuicideBomberSpawner.teamcountertop[team] -= 10;
    							b.teamcost[team] += 500;
    							if (SuicideBomberSpawner.teamcountertop[team] == 20)
    							{
    								b.teamisDisabled[team] = true;
    							}
        						break;
        					//Laser Unit
        					case 11: LaserUnit.teamspeed[team] += (double)1/(double)100 * size;
        						break;
        					case 12: LaserUnit.teamdamage[team] += 0.1;
        						break;
        					case 13: LaserUnit.teammaxhealth[team] += 20;
	        					for (int i = 0; i < myLaserUnits.size(); i++)
	        					{
	        						LaserUnit s = (LaserUnit)myLaserUnits.get(i);
	        						if (s.team == team)
	        						{
	        							s.healed(20);
	        						}
	        					}
	        					break;
        					case 14:
	        					LaserUnitSpawner.teamcountertop[team] -= 10;
	        					b.teamcost[team] += 500;
	        					if (LaserUnitSpawner.teamcountertop[team] == 20)
	        					{
	        						b.teamisDisabled[team] = true;
	        					}
	        					break;
        					case 15:LaserUnit.teamrange[team] += 0.25 * size;
        						break;
        					//Barrier
        					case 16: Barrier.teammaxhealth[team] += 100;
	        					for (int i = 0; i < myBarriers.size(); i++)
	        					{
	        						Barrier s = (Barrier)myBarriers.get(i);
	        						if (s.team == team)
	        						{
	        							s.healed(100);
	        						}
	        					}
	        					break;
        					case 17: LaserTower.teammaxhealth[team] += 20;
	        					for (int i = 0; i < myLaserTowers.size(); i++)
	        					{
	        						LaserTower s = (LaserTower)myLaserTowers.get(i);
	        						if (s.team == team)
	        						{
	        							s.healed(20);
	        						}
	        					}
	        					break;
        					case 18: LaserTower.teamdamage[team] += 0.2;
        						break;
        					case 19: LaserTower.teamrange[team] += 0.5 * size;
        						break;
        					case 20: HealthField.teammaxhealth[team] += 20;
	        					for (int i = 0; i < myHealthFields.size(); i++)
	        					{
	        						HealthField s = (HealthField)myHealthFields.get(i);
	        						if (s.team == team)
	        						{
	        							s.healed(20);
	        						}
	        					}
	        					break;
        					case 21: HealthField.teamheal[team] += 0.1;
        						break;
        					case 22: HealthField.teamrange[team] += 0.25*size;
        						break;
        					case 23: DamageField.teammaxhealth[team] += 20;
	        					for (int i = 0; i < myDamageFields.size(); i++)
	        					{
	        						DamageField s = (DamageField)myDamageFields.get(i);
	        						if (s.team == team)
	        						{
	        							s.healed(20);
	        						}
	        					}
	        					break;
        					case 24: DamageField.teamdamage[team] += 0.025;
        						break;
        					case 25: DamageField.teamrange[team] += 0.25*size;
        						break;
        					case 26: TerritoryBeacon.teammaxhealth[team] += 20;
	        					for (int i = 0; i < myTerritoryBeacons.size(); i++)
	        					{
	        						TerritoryBeacon s = (TerritoryBeacon)myTerritoryBeacons.get(i);
	        						if (s.team == team)
	        						{
	        							s.healed(20);
	        						}
	        					}
        					case 27: TerritoryBeacon.teamrange[team] += 1;
        						break;
        						
        				}
        			}
        	}
        	else
        	{
        		b.isClicked = false;
        	}
        }
        if (dragging == true)
        {
        dragging = false;
        
        for (int i = 0; i < combination.size(); i++)
        {
            Point l = combination.get(i);
            if (l.unit)
            {
            int firstx, firsty, secondx, secondy;
            if (firstmousex < mousex)
            {
                firstx = firstmousex;
                secondx = mousex;
            }
            else
            {
                firstx = mousex;
                secondx = firstmousex;
            }
            if (firstmousey < mousey)
            {
                firsty = firstmousey;
                secondy = mousey;
            }
            else
            {
                firsty = mousey;
                secondy = firstmousey;
            }
            if (l.x <= (secondx - centerdistancex)/zoom && l.otherx >= (firstx - centerdistancex)/zoom && l.y <= (secondy - centerdistancey)/zoom && l.othery >= (firsty - centerdistancey)/zoom && l.team == team)
            {
            	l.selected = true;
            }
            }
        }
        }
    }
    }
    public void mouseWheelMoved(MouseWheelEvent e)
    {
    	if (Menu.showing("Game Menu"))
    	{
    		mouseWheelMovedRunning(e);
    	}
    }
    public void mouseWheelMovedRunning(MouseWheelEvent e) 
    {
    	double notches = e.getWheelRotation();
    	zoom -= notches/100*zoom*2;
    	if (zoom <= 0)
    	{
    		zoom = 0;
    	}
    }
    public void init()
    {
    	setSize(dim);
    	try
    	{
    		BufferedReader in = new BufferedReader(new FileReader("data.txt"));
    		String fileline = in.readLine();
    		String Data [] = fileline.split(",");
    		dimensionx = Integer.parseInt(Data[0]);
    		dimensiony = Integer.parseInt(Data[1]);
    		createdbeacons = Integer.parseInt(Data[2]);
    		teamnum = Integer.parseInt(Data[3]);
    		resourcenum = Integer.parseInt(Data[4]);
    		resourcesgiven = Integer.parseInt(Data[5]);
    		keys[0] = Integer.parseInt(Data[6]);
    		keys[1] = Integer.parseInt(Data[7]);
    		keys[2] = Integer.parseInt(Data[8]);
    		keys[3] = Integer.parseInt(Data[9]);
    		keys[4] = Integer.parseInt(Data[10]);
    		keys[5] = Integer.parseInt(Data[11]);
    		keys[6] = Integer.parseInt(Data[12]);
    		keys[7] = Integer.parseInt(Data[13]);
    		territorysquares = Boolean.parseBoolean(Data[14]);
    		ongrid = Boolean.parseBoolean(Data[15]);
    	}
    	catch (IOException e) 
    	{
    		writeToFile();
    	}
    	myTimer = new Timer();
        myTask = new Task5(this);
        int count = 0;
        long delay = 10;
    	myTimer.schedule(myTask,count,delay);
    	mySize = getSize();
    	offscreen = createImage(mySize.width, mySize.height);
        bufferGraphics = offscreen.getGraphics();
    	addMouseListener(this);
    	addMouseMotionListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
        myMenus = new ArrayList<Menu>();
        myMenuButtons = new ArrayList<Button>();
        //creating of all menus//
        myMenus.add(new Menu(true, "Main Menu"));
        myMenus.add(new Menu(false, "Game Menu"));
        myMenus.add(new Menu(false, "Options Menu"));
        myMenus.add(new Menu(false, "Key Bindings Menu"));
        myMenus.add(new Menu(false, "Instructions Menu"));
        myMenus.add(new Menu(false, "Multiplayer Menu"));
        Menu.myMenus = myMenus;
        //end of creation of all menus//
        myMenuButtons.add( new Button(5,35,170,30,"Sand Box Mode", 0, true, "Main Menu", false));//button 0
        myMenuButtons.add( new Button(185,35,170,30,"Multiplayer", 0, true, "Main Menu", false));//button 1
        myMenuButtons.add( new Button(365,35,170,30,"Options", 0, true, "Main Menu", false));//button 2
        myMenuButtons.add( new Button(545,35,170,30,"Key Bindings", 0, true, "Main Menu", false));//button 3 
        myMenuButtons.add( new Button(5,35,170,30,"Back", 0, true, "Options Menu", false));//button 4
        myMenuButtons.add( new Button(5,35,170,30,"Back", 0, true, "Key Bindings Menu", false));//button 5
        
        // options
        
        myMenuButtons.add( new Button(185,35,170,30,"X Dimension", 0, true, "Options Menu", false));//button 6
        myMenuButtons.add( new Button(365,35,170,30,"Y Dimension", 0, true, "Options Menu", false));//button 7
        myMenuButtons.add( new Button(545,35,170,30,"Starting Beacons", 0, true, "Options Menu", false));//button 8
        myMenuButtons.add( new Button(725,35,170,30,"Number Of Teams", 0, true, "Options Menu", false));//button 9
        myMenuButtons.add( new Button(905,35,170,30,"Resource Spots", 0, true, "Options Menu", false));//button 10
        myMenuButtons.add( new Button(1085,35,170,30,"Starting Resources", 0, true, "Options Menu", false));//button 11
        
        // key bindings
        
        myMenuButtons.add( new Button(500,45,275,30,"Laser Unit Spawner", 0, true, "Key Bindings Menu", false));//button 12
        myMenuButtons.add( new Button(500,95,275,30,"Suicide Bomber Spawner", 0, true, "Key Bindings Menu", false));//button 13
        myMenuButtons.add( new Button(500,145,275,30,"Territory Beacon", 0, true, "Key Bindings Menu", false));//button 14
        myMenuButtons.add( new Button(500,195,275,30,"Barrier", 0, true, "Key Bindings Menu", false));//button 15
        myMenuButtons.add( new Button(500,245,275,30,"Health Field", 0, true, "Key Bindings Menu", false));//button 16
        myMenuButtons.add( new Button(500,295,275,30,"Damage Field", 0, true, "Key Bindings Menu", false));//button 17
        myMenuButtons.add( new Button(500,345,275,30,"Laser Tower", 0, true, "Key Bindings Menu", false));//button 18
        myMenuButtons.add( new Button(500,395,275,30,"Change Team", 0, true, "Key Bindings Menu", false));//button 19
        
        //instructions
        
        myMenuButtons.add( new Button(725,35,170,30,"Instructions",0,true, "Main Menu", false));//button 20
        myMenuButtons.add( new Button(5,35,170,30,"Back", 0, true, "Instructions Menu", false));//button 21
        
        //multiplayer buttons
        
        myMenuButtons.add( new Button(5,35,170,30,"Back", 0, true, "Multiplayer Menu", false));//button 22
        myMenuButtons.add( new Button(185,35,170,30,"Create Game", 0, true, "Multiplayer Menu", false));//button 23
        myMenuButtons.add( new Button(365,35,170,30,"Join Game", 0, true, "Multiplayer Menu", false));//button 24
        
        // other buttons
        myMenuButtons.add(new Button(5,725,170,30, "Territory Squares (off)", 0, true, "Options Menu", false));//button 25
        myMenuButtons.add(new Button(185,725,170,30, "On Squares", 0, true, "Options Menu", false));//button 26
        if (territorysquares)
        {
        	myMenuButtons.get(25).label = "Territory Squares (on)";
        }
        if (ongrid)
        {
        	myMenuButtons.get(26).label = "On Grid";
        }
        initializeGame();
    }
    public void initializeGame()
    {
    	
    	//global variables reset////////////////////////////////////////////////////////////////
    	finishedInitializing = false;
    	team = 0;
    	winner = false;
    	teamwinner = new boolean[teamnum];
    	teamdeath = new boolean[teamnum];
    	teamunits = new int[teamnum];
    	teamresources = new int[teamnum];
    	teamgain = new int[teamnum];
    	resourcecounter = 0;
    	territory = new int[dimensionx + 1][dimensiony + 1];
    	if (dimensionx > dimensiony)
    	{
    		zoom = 1100 / (size * dimensionx);
    	}
    	else
    	{
    		zoom = 650 / (size * dimensiony);
    	}
    	//zoom = 50/size;
    	verticalshift = 0;
    	horizontalshift = 0;
    	horizontalshifting = 0;
    	verticalshifting = 0;
    	
    	//global variables reset end////////////////////////////////////////////////////////////
    	
    	Point.teamnum = teamnum;
    	//COLORS --------------- COLORS --------------- COLORS ------------------- COLORS ----------------- COLORS ---------
    	teamColors[0] = Color.green;
    	teamColors[1] = Color.yellow;
    	teamColors[2] = Color.MAGENTA;
    	teamColors[3] = Color.CYAN;
    	teamColors[4] = Color.ORANGE;
    	teamColors[5] = Color.pink;
    	teamColors[6] = Color.lightGray;
    	if (teamnum > 7)
    	{
    		for (int i = 7; i < maxteams; i++)
    		{
    			teamColors[i] = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
    		}
    	}
    	//COLORS --------------- COLORS --------------- COLORS ------------------- COLORS ----------------- COLORS ---------
    	Point.teamColors = teamColors;
        for (int i = 0; i < teamresources.length; i++)
        {
        	teamresources[i] = resourcesgiven;
        	
        }
        Point.size = size;
        
        myLaserTowers = new ArrayList<Point>();
        mySuicideBombers = new ArrayList<Point>();
        mySuicideBomberSpawners = new ArrayList<Point>();
        myBarriers = new ArrayList<Point>();
        myHealthFields = new ArrayList<Point>();
        myDamageFields = new ArrayList<Point>();
        myLaserUnitSpawners = new ArrayList<Point>();
        myLaserUnits = new ArrayList<Point>();
        myTerritoryBeacons = new ArrayList<Point>();
        myResources = new ArrayList<Point>();
        myButtons = new ArrayList<Button>();
        initializevalues();
        //button placement/////////////////////////////////////////////////////////////////////////////////////////////
        // increase y values by 10 more than previous height
        // increase x values by 10 more than previous width
        Button.teamnum = teamnum;
        myButtons.add(new Button(5, 35, 170, 30, "Suicide Bomber Upgrages", 0, true, "Game Menu", true));// button 0
        myButtons.add(new Button(185, 35, 170, 30, "Laser Unit Upgrades", 0, true, "Game Menu", true));// button 1
        myButtons.add(new Button(365, 35, 170, 30, "Barrier Upgrades", 0, true, "Game Menu", true));// button 2
        myButtons.add(new Button(545, 35, 170, 30, "Laser Tower Upgrades", 0, true, "Game Menu", true));// button 3
        myButtons.add(new Button(725, 35, 170, 30, "Health Field Upgrades", 0, true, "Game Menu", true));// button 4
        myButtons.add(new Button(905, 35, 170, 30, "Damage Field Upgrades", 0, true, "Game Menu", true));// button 5
        myButtons.add(new Button(1085, 35, 180, 30, "Territory Beacon Upgrades", 0, true, "Game Menu", true));
        //Suicide Bomber buttons
        myButtons.add(new Button(5, 155, 170, 30, "Speed", 500, false, "Game Menu", true));//button 7
        myButtons.add(new Button(5, 115, 170, 30, "Damage", 1000, false, "Game Menu", true));//button 8
        myButtons.add(new Button(5, 75, 170, 30, "Health", 500, false, "Game Menu", true));//button 9
        myButtons.add(new Button(5, 195, 170, 30, "Spawn Rate", 500, false, "Game Menu", true));//button 10
        //Laser Unit buttons
        myButtons.add(new Button(185, 155, 170, 30, "Speed", 1000, false, "Game Menu", true));//button 11
        myButtons.add(new Button(185, 115, 170, 30, "Damage", 1000, false, "Game Menu", true));//button 12
        myButtons.add(new Button(185, 75, 170, 30, "Health", 500, false, "Game Menu", true));//button 13
        myButtons.add(new Button(185, 195, 170, 30, "Spawn Rate", 500, false, "Game Menu", true));//button 14
        myButtons.add(new Button(185, 235, 170, 30, "Range", 1000, false, "Game Menu", true));//button 15
        //Barrier buttons
        myButtons.add(new Button(365, 75, 170, 30, "Health", 250, false, "Game Menu", true));//button 16
        //Laser Tower buttons
        myButtons.add(new Button(545, 75, 170, 30, "Health", 250, false, "Game Menu", true));//button 17
        myButtons.add(new Button(545, 115, 170, 30, "Damage", 500, false, "Game Menu", true));//button 18
        myButtons.add(new Button(545, 155, 170, 30, "Range", 500, false, "Game Menu", true));//button 19
        //Health Field buttons
        myButtons.add(new Button(725, 75, 170, 30, "Health", 250, false, "Game Menu", true));//button 20
        myButtons.add(new Button(725, 115, 170, 30, "Healing", 500, false, "Game Menu", true));//button 21
        myButtons.add(new Button(725, 155, 170, 30, "Range", 500, false, "Game Menu", true));//button 22
        //Damage Field buttons
        myButtons.add(new Button(905, 75, 170, 30, "Health", 250, false, "Game Menu", true));//button 23
        myButtons.add(new Button(905, 115, 170, 30, "Damage", 500, false, "Game Menu", true));//button 24
        myButtons.add(new Button(905, 155, 170, 30, "Range", 500, false, "Game Menu", true));//button 25
        //Territory Beacon buttons
        myButtons.add(new Button(1085, 75, 180, 30, "Health", 100, false, "Game Menu", true));//button 26
        myButtons.add(new Button(1085, 115, 180, 30, "Range", 3000, false, "Game Menu", true));//button 27
        
        //Special Buttons
        myButtons.add(new Button(5,725,170,30, "Quit", 0, true, "Game Menu", true));//button 28
        myButtons.add(new Button(1095,725,170,30, "Restart", 0, true, "Game Menu", true));//button 29
        //end of button placement/////////////////////////////////////////////////////////////////////////////////////////////
        mySize = getSize();
        centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
        centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
        int [] xbeacons = new int[createdbeacons*teamnum];
        int [] ybeacons = new int[createdbeacons*teamnum];
        for (int t = 0; t < teamnum; t++)
        {
        for (int i = t*createdbeacons; i < (t+1)*createdbeacons; i++)
        {
            boolean done = true;
            do
            {
            done = true;
            xbeacons[i] = (int)(Math.random()*(dimensionx + 1))*(int)size;
            ybeacons[i] = (int)(Math.random()*(dimensiony + 1))*(int)size;
            for (int k = 0; k < i; k++)
            {
                if (Math.abs(xbeacons[i]/size - xbeacons[k]/size) < TerritoryBeacon.teamrange[0] + 1 && Math.abs(ybeacons[i]/size - ybeacons[k]/size) < TerritoryBeacon.teamrange[0] + 1)
                {
                    done = false;
                }
            }
            }while (!done);
            myTerritoryBeacons.add( new TerritoryBeacon(xbeacons[i], ybeacons[i], t));
        }
        }
        int[] xresources = new int[resourcenum];
        int[] yresources = new int[resourcenum];
        for (int i = 0; i < resourcenum; i++)
        {
            boolean done = true;
            do
            {
            done = true;
            xresources[i] = (int)(Math.random()*(dimensionx + 1))*(int)size;
            yresources[i] = (int)(Math.random()*(dimensiony + 1))*(int)size;
            for (int j = 0; j < createdbeacons*teamnum; j++)
            {
            	if (xresources[i] == xbeacons[j] && yresources[i] == ybeacons[j])
            	{
            		done = false;
            	}
            }
            for (int k = 0; k < i; k++)
            {
                if ((xresources[i] == xresources[k] && yresources[i] == yresources[k]))
                {
                    done = false;
                }
            }
            }while (!done);
            myResources.add( new Resource(xresources[i], yresources[i]));
        }
        finishedInitializing = true;
        
    }
    public void runningpainter()
    {
        bufferGraphics.clearRect(0,0,mySize.width, mySize.height);
        bufferGraphics.setColor(Color.black);
        //bufferGraphics.drawString(keyCode + "", 50, 100);
        drawLines();
        creating();
        bufferGraphics.setColor(Color.black);
        for (int i = 0; i < myResources.size(); i++)
        {
        	Point s = myResources.get(i);
        	s.draw(bufferGraphics, centerdistancex, centerdistancey, zoom);
        }
        for (int i = 0; i < combination.size(); i++)
        {
        	Point s = combination.get(i);
        	s.draw(bufferGraphics, centerdistancex, centerdistancey, zoom);
        }      
        for (int i = 0; i < myButtons.size(); i++)
        {
        	Button s = myButtons.get(i);
        	s.draw(bufferGraphics, mySize, team, teamresources);
        }
        if (dragging)
        {
            bufferGraphics.setColor(Color.black);
            int firstx, firsty, width, height;
            if (firstmousex < mousex)
            {
                firstx = firstmousex;
                width = mousex-firstmousex;
            }
            else
            {
                firstx = mousex;
                width = firstmousex - mousex;
            }
            if (firstmousey < mousey)
            {
                firsty = firstmousey;
                height = mousey-firstmousey;
            }
            else
            {
                firsty = mousey;
                height = firstmousey - mousey;
            }
            bufferGraphics.drawRect(firstx, firsty, width, height);
        }
        Font r = new Font("Arial", Font.PLAIN, 14);
		bufferGraphics.setFont(r);
		int numdigits = 1;
        double tempdigits = 1;
        while (teamresources[team] > tempdigits - 1)
        {
        	numdigits++;
        	tempdigits *= 10;
        }
		bufferGraphics.setColor(teamColors[team]);
        bufferGraphics.fillRect(mySize.width/2-(32+numdigits*4), 7, 65 + numdigits * 8, 15);
        bufferGraphics.setColor(Color.black);
        bufferGraphics.drawString("resources: " + teamresources[team], mySize.width/2-(32+numdigits*4), 20);
    }
    public void paint(Graphics g)
    {
    	Dimension current = getSize();
    	if ((current.width != mySize.width || current.height != mySize.height) && current.width > 0  && current.height > 0) 
        {
            offscreen = createImage(current.width + 1, current.height + 1);
            bufferGraphics = offscreen.getGraphics();
        }
        mySize = getSize();
    	if (!Menu.showing("Game Menu"))
    	{
    		menupainter();
    	}
        g.drawImage(offscreen,0,0, this);
    }
    public void runningupdate()
    {
    	verticalshift += verticalshifting;
    	horizontalshift += horizontalshifting;
        centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
        centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
        resourcecounter++;
        if (resourcecounter == resourcecountertop)
        {
            resourcecounter = 0;
            for (int i = 0; i < teamresources.length; i++)
            {
            	teamresources[i] += teamgain[i];
            }
        }
        combination = new ArrayList<Point>();
        combination = combine(combination, mySuicideBomberSpawners);
        combination = combine(combination, myLaserTowers);
        combination = combine(combination, myBarriers);
        combination = combine(combination, myHealthFields);
        combination = combine(combination, myDamageFields);
        combination = combine(combination, myLaserUnitSpawners);
        combination = combine(combination, myTerritoryBeacons);
        combination = combine(combination, myLaserUnits);
        combination = combine(combination, mySuicideBombers);
        createTerritory();
        resourcegain();
        overOther(combination);
        Interaction.Interactions(dimensionx, dimensiony, mySuicideBombers, myLaserTowers, myBarriers, mySuicideBomberSpawners, myHealthFields, myDamageFields, myLaserUnitSpawners, myLaserUnits, myTerritoryBeacons, mySize, combination);
        combination = new ArrayList<Point>();
        combination = combine(combination, mySuicideBomberSpawners);
        combination = combine(combination, myLaserTowers);
        combination = combine(combination, myBarriers);
        combination = combine(combination, myHealthFields);
        combination = combine(combination, myDamageFields);
        combination = combine(combination, myLaserUnitSpawners);
        combination = combine(combination, myTerritoryBeacons);
        combination = combine(combination, myLaserUnits);
        combination = combine(combination, mySuicideBombers);
        for(int i = 0; i < mySuicideBomberSpawners.size(); i++)
        {
            SuicideBomberSpawner s = (SuicideBomberSpawner)mySuicideBomberSpawners.get(i);
            s.update(mySuicideBombers);
        }
        for (int i = 0; i < myLaserUnitSpawners.size(); i++)
        {
            LaserUnitSpawner s = (LaserUnitSpawner)myLaserUnitSpawners.get(i);
            s.update(myLaserUnits);
        }
        checkdeath();
        checkwinner();
        if (winner)
        {
        	myButtons.get(28).label = "Continue";
        }
    }
    public void creating()
    {
        if (creatingLaserTower == true)
        {
            double width = LaserTower.width;
            double height = LaserTower.height;
            width*=zoom;
            height*=zoom;
            double tempsize = size * zoom;
            double range;
            range = LaserTower.teamrange[team];
            bufferGraphics.setColor(teamColors[team]);
            range *= zoom;
            bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            bufferGraphics.fillRect((int)(mousex - ((double)3/(double)50)*tempsize), (int)(mousey - ((double)3/(double)50)*tempsize), (int)(((double)6/(double)50)*tempsize), (int)(((double)6/(double)50)*tempsize));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawOval((int)(mousex - range), (int)(mousey - range), (int)(range*2), (int)(range*2));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
        if (creatingBarrier == true)
        {
            double width = Barrier.width;
            double height = Barrier.height;
            width*=zoom;
            height*=zoom;
            bufferGraphics.setColor(teamColors[team]);
            bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
        if (creatingSuicideBomberSpawner == true)
        {
            double width = SuicideBomberSpawner.width;
            double height = SuicideBomberSpawner.height;
            double tempsize = size * zoom;
            width*=zoom;
            height*=zoom;
            bufferGraphics.setColor(teamColors[team]);
            bufferGraphics.fillOval((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(width));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawOval((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(width));
            bufferGraphics.fillOval((int)(mousex - (double)6/(double)50*tempsize), (int)(mousey - (double)6/(double)50*tempsize), (int)((double)12/(double)50*tempsize), (int)((double)12/(double)50*tempsize));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
        if (creatingHealthField == true)
        {
            double width = HealthField.width;
            double height = HealthField.height;
            double tempsize = size * zoom;
            width*=zoom;
            height*=zoom;
            double range;
            range = HealthField.teamrange[team];
            range *= zoom;
            bufferGraphics.setColor(teamColors[team]);
            bufferGraphics.fillRect((int)(mousex - width/2),(int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawLine((int)(mousex - ((double)3/(double)50)*tempsize), (int)(mousey), (int)(mousex + ((double)3/(double)50)*tempsize), (int)(mousey));
            bufferGraphics.drawLine((int)(mousex), (int)(mousey -((double)3/(double)50)*tempsize), (int)(mousex), (int)(mousey+((double)3/(double)50)*tempsize));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawOval((int)(mousex - range), (int)(mousey - range), (int)(range*2), (int)(range*2));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
        if (creatingDamageField == true)
        {
            double width = DamageField.width;
            double height = DamageField.height;
            double tempsize = size * zoom;
            width*=zoom;
            height*=zoom;
            double range;
            range = DamageField.teamrange[team];
            range *= zoom;
            bufferGraphics.setColor(teamColors[team]);
            bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.setColor(Color.red);
            bufferGraphics.drawLine((int)(mousex - ((double)3/(double)50)*tempsize), (int)(mousey), (int)(mousex + ((double)3/(double)50)*tempsize), (int)(mousey));
            bufferGraphics.drawLine((int)(mousex), (int)(mousey -((double)3/(double)50)*tempsize), (int)(mousex), (int)(mousey+((double)3/(double)50)*tempsize));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawOval((int)(mousex - range), (int)(mousey - range), (int)(range*2), (int)(range*2));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
        if (creatingLaserUnitSpawner == true)
        {
            double width = LaserUnitSpawner.width;
            double height = LaserUnitSpawner.height;
            double tempsize = size * zoom;
            width*=zoom;
            height*=zoom;
            bufferGraphics.setColor(teamColors[team]);
            bufferGraphics.fillOval((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(width));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawOval((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(width));
            bufferGraphics.fillRect((int)(mousex - (double)6/(double)50*tempsize), (int)(mousey - (double)6/(double)50*tempsize), (int)((double)12/(double)50*tempsize), (int)((double)12/(double)50*tempsize));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
        if (creatingTerritoryBeacon == true)
        {
            double width = TerritoryBeacon.width;
            double height = TerritoryBeacon.height;
            double tempsize = size * zoom;
            width*=zoom;
            height*=zoom;
            bufferGraphics.setColor(teamColors[team]);
            bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.setColor(Color.black);
            bufferGraphics.drawRect((int)(mousex - width/2), (int)(mousey - height/2), (int)(width), (int)(height));
            bufferGraphics.drawRect((int)(mousex + ((double)1/(double)10)*tempsize - width/2), (int)(mousey - width/2), (int)(width - ((double)1/(double)5)*tempsize), (int)(height));
            bufferGraphics.drawRect((int)(mousex - width/2), (int)(mousey + ((double)1/(double)10)*tempsize - width/2), (int)(width), (int)(height - ((double)1/(double)5)*tempsize));
            if (over)
            {
                bufferGraphics.setColor(new Color(255,0,0,150));
                bufferGraphics.fillRect((int)(mousex - width/2), (int)(mousey - height/2), (int)width, (int)height);
            }
        }
    }
    public void drawLines()
    {
    	double shift = 0;
        drawTerritory();
        if (zoom * size > 5)
        {
	        bufferGraphics.setColor(Color.black);
	        if (ongrid)
	        {
	        	shift = 0;
	        }
	        else
	        {
	        	shift = size/2 * zoom;
	        }
	        for (int i = 0; i < dimensiony + 1; i++)
	        {
	            bufferGraphics.drawLine((int)centerdistancex - (int)shift,(int)(i*size * zoom) + (int)centerdistancey + (int)shift,(int)(dimensionx*size * zoom) + (int)centerdistancex + (int)(shift), (int)(i*size * zoom) + (int)centerdistancey + (int)shift);
	        }
	        for (int i = 0; i < dimensionx + 1; i++)
	        {
	            bufferGraphics.drawLine((int)(i*size * zoom) + (int)centerdistancex + (int)shift,(int)centerdistancey - (int)(shift),(int)(i*size * zoom) + (int)centerdistancex + (int)shift, (int)(dimensiony*size * zoom) + (int)centerdistancey + (int)shift);
	        }
        }
        bufferGraphics.setColor(Color.black);
        bufferGraphics.drawRect((int)centerdistancex - (int)shift, (int)centerdistancey - (int)shift, (int)(dimensionx*size * zoom) + (int)(shift*2), (int)(dimensiony*size * zoom) + (int)(shift*2));
        bufferGraphics.drawRect((int)centerdistancex - (int)(size * zoom), (int)centerdistancey - (int)(size * zoom), (int)(dimensionx*size * zoom + 2 * size * zoom), (int)(dimensiony*size * zoom + 2 * size * zoom));
        bufferGraphics.setColor(teamColors[team]);
        bufferGraphics.drawRect((int)centerdistancex - (int)(size*zoom) - 1, (int)centerdistancey - (int)(size*zoom) - 1, (int)(dimensionx*size*zoom + 2 * size*zoom) + 2, (int)(dimensiony*size*zoom + 2 * size*zoom) + 2);
        
        
    }
    public void drawTerritory()
    {
    	centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
        centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
        for (int x = 0; x < dimensionx + 1; x++)
        {
            for (int y = 0; y < dimensiony + 1; y++)
            {
                boolean draw = true;
                if (territory[x][y] == -1)
                {
                    draw = false;
                }
                else if (territory[x][y] == -2)
                {
                    bufferGraphics.setColor(new Color(255,0,0));
                }
                else
                {
                	bufferGraphics.setColor(teamColors[territory[x][y]]);
                }
                if (draw == true)
                {
                	bufferGraphics.fillRect((int)(x*size*zoom-(size*zoom/2) + (int)centerdistancex), (int)(y*size*zoom-(size*zoom/2) + (int)centerdistancey), (int)(size*zoom), (int)(size*zoom));
                }
                if (territory[x][y] == team && territorysquares)
                {
                	bufferGraphics.setColor(Color.black);
                	bufferGraphics.drawRect((int)(x*size*zoom-(int)(10*zoom) + (int)centerdistancex), (int)(y*size*zoom-(int)(10*zoom) + (int)centerdistancey), (int)(20*zoom), (int)(20*zoom));
                }
            }
        }
    }
    public void overOther(ArrayList<Point>combination)
    {
    	centerdistancex = mySize.width/2 - ((dimensionx+horizontalshift)*size*zoom)/2;
        centerdistancey = mySize.height/2 - ((dimensiony+verticalshift)*size*zoom)/2;
        double tempmousex = (double)((mousex - centerdistancex)/zoom);
        double tempmousey = (double)((mousey - centerdistancey)/zoom);
        over = false;
        double width = ((double)3/(double)5)*size;
        for (int i = 0; i < combination.size(); i++)
        {
            Point l = combination.get(i);
            if (!l.unit)
            {
            if (tempmousex - width/2 <= l.otherx && tempmousex + width/2 >= l.x && tempmousey - width/2 <= l.othery && tempmousey + width/2 >= l.y)
            {
                over = true;
            }  
            }
        }
        for (int i = 0; i < myResources.size(); i++)
        {
            Point l = myResources.get(i);
            if (tempmousex - width/2 <= l.otherx && tempmousex + width/2 >= l.x && tempmousey - width/2 <= l.othery && tempmousey + width/2 >= l.y)
            {
                over = true;
            }  
        }
        tempmousex /= size;
        tempmousey /= size;
        tempmousex = Math.round(tempmousex);
        tempmousey = Math.round(tempmousey);
        if (tempmousex > dimensionx || tempmousey > dimensiony || tempmousex < 0 || tempmousey < 0)
        {
        	over = true;
        }
        else
        {
        if (territory[(int)tempmousex][(int)tempmousey] != team)//out of bounds exception
        {
        	over = true;
        }
        }
        int cost = 0;
        if (creatingLaserTower == true)
        {
            cost = LaserTower.cost;
        }
        if (creatingSuicideBomberSpawner == true)
        {
            cost = SuicideBomberSpawner.cost;
        }        
        if (creatingBarrier == true)
        {
            cost = Barrier.cost;
        }
        if (creatingHealthField == true)
        {
            cost = HealthField.cost;
        }
        if (creatingDamageField == true)
        {
            cost = DamageField.cost;
        }
        if (creatingLaserUnitSpawner == true)
        {
            cost = LaserUnitSpawner.cost;
        }
        if (creatingTerritoryBeacon == true)
        {
            cost = TerritoryBeacon.cost;
        }
        if (teamresources[team] - cost < 0)// out of bounds exception
        {
        	over = true;
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
    public void createTerritory()
    {
        for (int x = 0; x < dimensionx + 1; x++)
        {
            for (int y = 0; y < dimensiony + 1; y++)
            {
                territory[x][y] = -1;//out of bounds exception
            }
        }
        for (int i = 0; i < myTerritoryBeacons.size(); i++)
        {
            TerritoryBeacon l = (TerritoryBeacon)myTerritoryBeacons.get(i);
            int tempx = (int)(l.centerx / size);
            int tempy = (int)(l.centery / size); 
                for (int x = (int)tempx - TerritoryBeacon.teamrange[l.team]; x < tempx + TerritoryBeacon.teamrange[l.team] + 1; x++)
                {
                    for (int y = (int)tempy - TerritoryBeacon.teamrange[l.team]; y < tempy + TerritoryBeacon.teamrange[l.team] + 1; y++)
                    {
                        if (x >= 0 && y >= 0 && x <= dimensionx && y <= dimensiony)
                        {
                            if (territory[x][y] == -1)
                            {
                                territory[x][y] = l.team;   
                            }
                            else if (territory[x][y] == l.team)
                            {
                            	
                            }
                            else
                            {
                                territory[x][y] = -2;
                            }
                        }
                    }
                }
            
        }
        
    }
    public void resourcegain()
    {
    	int[] teamcontrol = new int[teamnum];
        int xposition;
        int yposition;
        for (int i = 0; i < myResources.size(); i++)
        {
            Point l = myResources.get(i);
            xposition = (int)(l.centerx / size);
            yposition = (int)(l.centery / size);
            if (territory[xposition][yposition] == -1)
            {
            }
            else if (territory[xposition][yposition] == -2)
            {
            }
            else
            {
            	teamcontrol[territory[xposition][yposition]] ++;
            }
        }
        for (int i = 0; i < teamgain.length; i++)
        {
        	teamgain[i] = 10 + 2*teamcontrol[i];
        }
    }
    public void initializevalues()
    {
    	//initialize arrays
    	Barrier.teammaxhealth = new double[teamnum];
		DamageField.teammaxhealth = new double[teamnum];
	    DamageField.teamdamage = new double[teamnum];
	    DamageField.teamrange = new double[teamnum];
	    HealthField.teammaxhealth = new double[teamnum];
	    HealthField.teamheal = new double[teamnum];
	    HealthField.teamrange = new double[teamnum];
	    LaserTower.teamdamage = new double[teamnum];
	    LaserTower.teamrange = new double[teamnum];
	    LaserTower.teammaxhealth = new double[teamnum];
	    LaserUnit.teamspeed = new double[teamnum];
	    LaserUnit.teammaxhealth = new double[teamnum];
	    LaserUnit.teamdamage = new double[teamnum];
	    LaserUnit.teamrange = new double[teamnum];
	    LaserUnitSpawner.teamcountertop = new double[teamnum];
	    LaserUnitSpawner.teammaxhealth = new double[teamnum];
	    SuicideBomber.teamdamage = new double[teamnum];
	    SuicideBomber.teammaxhealth = new double[teamnum];
	    SuicideBomber.teamspeed = new double[teamnum];
	    SuicideBomberSpawner.teammaxhealth = new double[teamnum];
	    SuicideBomberSpawner.teamcountertop = new double[teamnum];
	    TerritoryBeacon.teammaxhealth = new double[teamnum];
	    TerritoryBeacon.teamrange = new int[teamnum];
    	//end of initialize arrays
    	for (int i = 0; i < teamnum; i++)
    	{
    		
    		Barrier.teammaxhealth[i] = 5000;
    		DamageField.teammaxhealth[i] = 500;
    	    DamageField.teamdamage[i] = 0.1;
    	    DamageField.teamrange[i] = 4 * size;
    	    HealthField.teammaxhealth[i] = 500;
    	    HealthField.teamheal[i] = 0.5;
    	    HealthField.teamrange[i] = 3 * size;
    	    LaserTower.teamdamage[i] = 1;
    	    LaserTower.teamrange[i] = 5 * size;
    	    LaserTower.teammaxhealth[i] = 500;
    	    LaserUnit.teamspeed[i] = (double)1/(double)50*size;
    	    LaserUnit.teammaxhealth[i] = 100;
    	    LaserUnit.teamdamage[i] = 0.5;
    	    LaserUnit.teamrange[i] = 2 * size;
    	    LaserUnitSpawner.teamcountertop[i] = 150;
    	    LaserUnitSpawner.teammaxhealth[i] = 500;
    	    SuicideBomber.teamdamage[i] = 100;
    	    SuicideBomber.teammaxhealth[i] = 100;
    	    SuicideBomber.teamspeed[i] = (double)3/(double)50*size;
    	    SuicideBomberSpawner.teammaxhealth[i] = 500;
    	    SuicideBomberSpawner.teamcountertop[i] = 150;
    	    TerritoryBeacon.teammaxhealth[i] = 500;
    	    TerritoryBeacon.teamrange[i] = 3;
    	    
    	}
    }
    public void checkdeath()
    {
    	teamunits = new int[teamnum];
    	for (int i = 0; i < combination.size(); i++)
    	{
    		Point l = combination.get(i);
    		teamunits[l.team]++;
    	}
    	for (int i = 0; i < teamnum; i++)
    	{
    		if (teamunits[i] == 0)
    		{
    			teamdeath[i] = true;
    			if (i == team)
    			{
    			team++;
                if (team == teamnum)
                {
                	team = 0;
                }
                if (teamdeath[team] == true)
                {
                do
                {
                	team++;
                	 if (team == teamnum)
                     {
                     	team = 0;
                     }
                }while(teamdeath[team] == true);
    			}
    			}
    		}
    	}
    }
    public void checkwinner()
    {
    	for (int i = 0; i < teamdeath.length; i++)
    	{
    		teamwinner[i] = true;
    		for (int j = 0; j < teamdeath.length; j++)
    		{
    			if (j != i && teamdeath[j] == false)
    			{
    				teamwinner[i] = false;
    			}
    		}
    	}
    	for (int i = 0; i < teamwinner.length; i++)
    	{
    		if (teamwinner[i] == true)
    		{
    			winner = true;
    		}
    	}
    }
    public void painter()
    {
    	
    	if (Menu.showing("Game Menu") && finishedInitializing)
    	{
    		runningpainter();
    	}
    	repaint();
    }
    public void update()
    {
    	Dimension current = getSize();
    	if ((current.width != mySize.width || current.height != mySize.height) && current.width > 0  && current.height > 0) 
        {
            offscreen = createImage(current.width + 1, current.height + 1);
            bufferGraphics = offscreen.getGraphics();
        }
        mySize = getSize();
    	if (Menu.showing("Game Menu") && finishedInitializing)
    	{
    		runningupdate();
    	}
    }
    public void menupainter()
    {
    	if (boundkey == -1)
    	{
    		bufferGraphics.setColor(Color.white);
    	}
    	else
    	{
    		bufferGraphics.setColor(Color.red);
    	}
		bufferGraphics.fillRect(0,0,mySize.width,mySize.height);
    	for (int i = 0; i < myMenuButtons.size(); i++)
    	{
    		Button b = myMenuButtons.get(i);
    		b.draw(bufferGraphics, mySize, 0, teamresources);
    	}
    	bufferGraphics.setColor(Color.black);
    	Font f = new Font("Arial", Font.BOLD, 72);
		bufferGraphics.setFont(f);
    	if (Menu.showing("Main Menu"))
    	{
    		 bufferGraphics.drawString("Main Menu", mySize.width/2-185, 100);
    	}
    	if (Menu.showing("Options Menu"))
    	{
    		 bufferGraphics.drawString("Options", mySize.width/2-135, 100);
    	}
    	if (Menu.showing("Key Bindings Menu"))
    	{
    		 bufferGraphics.drawString("Key Bindings", mySize.width/2-225, 100);
    	}
    	if (Menu.showing("Instructions Menu"))
    	{
    		 bufferGraphics.drawString("Instructions", mySize.width/2-200, 100);
    	}
    	if (Menu.showing("Multiplayer Menu"))
    	{
    		 bufferGraphics.drawString("Multiplayer",  mySize.width/2-190, 100);
    	}
    	Font r = new Font("Arial", Font.PLAIN, 14);
		bufferGraphics.setFont(r);
		if (Menu.showing("Options Menu"))
		{
			bufferGraphics.drawString("Width: " + dimensionx, 185, mySize.height - 70);
			bufferGraphics.drawString("Height: " + dimensiony, 365, mySize.height - 70);
			bufferGraphics.drawString("Beacons: " + createdbeacons, 545, mySize.height - 70);
			bufferGraphics.drawString("Teams: " + teamnum, 725, mySize.height - 70);
			bufferGraphics.drawString("Resource Spots: " + resourcenum, 905, mySize.height - 70);
			bufferGraphics.drawString("Starting Resources: " + resourcesgiven, 1085, mySize.height - 70);
		}
		if (Menu.showing("Key Bindings Menu"))
		{
			bufferGraphics.drawString("" + keys[6], 800, mySize.height - 25);
			bufferGraphics.drawString("" + keys[2], 800, mySize.height - 75);
			bufferGraphics.drawString("" + keys[7], 800, mySize.height - 125);
			bufferGraphics.drawString("" + keys[3], 800, mySize.height - 175);
			bufferGraphics.drawString("" + keys[4], 800, mySize.height - 225);
			bufferGraphics.drawString("" + keys[5], 800, mySize.height - 275);
			bufferGraphics.drawString("" + keys[1], 800, mySize.height - 325);
			bufferGraphics.drawString("" + keys[0], 800, mySize.height - 375);
		}
		if (Menu.showing("Instructions Menu"))
		{
			bufferGraphics.drawString("use the scroll wheel to zoom",mySize.width/2-85,200);
			bufferGraphics.drawString("use the arrow keys to pan",mySize.width/2-75,250);
			bufferGraphics.drawString("place towers by pressing the key that corrisponds to that tower in the key bindings menu, then click on the spot where you want to place it", mySize.width/2 - 425, 300);
			bufferGraphics.drawString("towers can only be placed on your own territory", mySize.width/2 - 145, 350);
			bufferGraphics.drawString("towers can be upgraded by pressing the buttons on the bottom of the screen", mySize.width/2 - 235, 400);
			bufferGraphics.drawString("units can be ordered by dragging the cursor over them, then right clicking on the spot where you want them to go", mySize.width/2 - 350, 450);
		}
    }
    public void writeToFile()
    {
    	try 
    	{
			BufferedWriter out = new BufferedWriter(new FileWriter("data.txt"));
			out.write(dimensionx + ",");
			out.write(dimensiony + ",");
			out.write(createdbeacons + ",");
			out.write(teamnum + ",");
			out.write(resourcenum + ",");
			out.write(resourcesgiven + ",");
			out.write(keys[0] + ",");
			out.write(keys[1] + ",");
			out.write(keys[2] + ",");
			out.write(keys[3] + ",");
			out.write(keys[4] + ",");
			out.write(keys[5] + ",");
			out.write(keys[6] + ",");
			out.write(keys[7] + ",");
			if (territorysquares)
			{
				out.write("true,");
			}
			else
			{
				out.write("false,");
			}
			if (ongrid)
			{
				out.write("true,");
			}
			else
			{
				out.write("false,");
			}
			out.close();
		} 
    	catch (IOException t) 
		{
			
		}
    }
}
