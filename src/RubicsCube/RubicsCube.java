package RubicsCube;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


/*
sides
    4
0   1   2   3
    5
    
    
 moves
 1 = U
 2 = Ui
 3 = D
 4 = Di
 5 = R
 6 = Ri
 7 = L
 8 = Li
 9 = F
 10 = Fi
 11 = B
 12 = Bi
 13 = turn cube right
 14 = turn cube left
 15 = turn cube up
 16 = turn cube down
 */
import java.util.ArrayList;
public class RubicsCube extends JApplet implements KeyListener
{
	
	public int[][][] cube = new int[6][3][3];
	ArrayList<Cube_Piece>Cube_Pieces;
	ArrayList<Integer>moves;
	ArrayList<Integer>robotmoves;
	public Color[] colors = new Color[7];
	public boolean shifting = false;
	public int xspot=0, yspot=0, sidespot=0;
	public int key;
	public Dimension mySize;
	public boolean primed;
	public int mousex = 0, mousey = 0;
	public int scrambleamount = 50;
	public void init()
	{
		colors[0] = Color.gray;
		colors[1] = Color.green;
		colors[2] = Color.red;
		colors[3] = Color.blue;
		colors[4] = new Color(255,125,0);
		colors[5] = Color.white;
		colors[6] = Color.yellow;
		for (int sides = 0; sides < 6; sides++)
		{
			for (int x = 0; x < 3; x++)
			{
				for (int y = 0; y < 3; y++)
				{
					cube[sides][x][y] = sides + 1;
				}
			}
		}
		Cube_Pieces = new ArrayList<Cube_Piece>();
		moves = new ArrayList<Integer>();
		robotmoves = new ArrayList<Integer>();
		addKeyListener(this);
		//create all cubes
		for (int i = 0; i < 4; i++)
		{
			create_cube_piece(1,0,null);
			create_cube_piece(2,0,null);
			create_cube_piece(2,1,null);
			create_cube_piece(2,2,null);
			create_cube_piece(1,2,null);
			turnCubeRight();
		}
	}
	public void mouseDragged(MouseEvent e)
	{	
	}
	public void paintCubePieces(Graphics g)
	{
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			int offsety = i%5 * 140;
			int offsetx = i/5 * 160;
			//background stuff
			if (c.corner == false)
			{
				g.setColor(Color.orange);
			}
			else
			{
				g.setColor(Color.blue);
			}
			g.fillRect(585 + offsetx, 25 + offsety, 90, 130);
			if (c.tag != null)
			{
				g.setColor(Color.green);
			}
			else
			{
				g.setColor(Color.white);
			}
			g.fillRect(590 + offsetx, 30 + offsety, 80, 120);
			//end of background stuff
			g.setColor(colors[c.colors[0]]);
			g.fillRect(600 + offsetx,50 + offsety,10,10);
			g.setColor(colors[c.colors[1]]);
			g.fillRect(615 + offsetx,50 + offsety,10,10);
			g.setColor(colors[c.colors[2]]);
			g.fillRect(630 + offsetx,50 + offsety,10,10);
			g.setColor(colors[c.colors[3]]);
			g.fillRect(645 + offsetx,50 + offsety,10,10);
			g.setColor(colors[c.colors[4]]);
			g.fillRect(615 + offsetx,35 + offsety,10,10);
			g.setColor(colors[c.colors[5]]);
			g.fillRect(615 + offsetx,65 + offsety,10,10);
			g.setColor(Color.black);
			g.drawRect(600 + offsetx,50 + offsety,10,10);
			g.drawRect(615 + offsetx,50 + offsety,10,10);
			g.drawRect(630 + offsetx,50 + offsety,10,10);
			g.drawRect(645 + offsetx,50 + offsety,10,10);
			g.drawRect(615 + offsetx,35 + offsety,10,10);
			g.drawRect(615 + offsetx,65 + offsety,10,10);
			g.drawString("x: "+ c.x, 615 + offsetx, 100 + offsety);
			g.drawString("y: "+ c.y, 615 + offsetx, 120 + offsety);
			g.drawString("z: "+ c.z, 615 + offsetx, 140 + offsety);
			

		}
	}
	public void paint(Graphics g)
    {		
		mySize = getSize();
		g.setColor(Color.white);
		if (primed)
		{
			g.setColor(Color.red);
		}
		g.fillRect(0, 0, mySize.width, mySize.height);
		paintCubePieces(g);
		for (int sides = 0; sides < 6; sides++)
		{
			for (int x = 0; x < 3; x++)
			{
				for (int y = 0; y < 3; y++)
				{
					int shiftright = 0;
					int shiftdown = 0;
					switch (sides)
    				{
						case 0: 
							shiftdown = 135;
							shiftright = 20;
							break;
						case 1: 
							shiftdown = 135;
							shiftright = 135;
							break;
						case 2:
							shiftdown = 135;
							shiftright = 250;
							break;
						case 3:
							shiftdown = 135;
							shiftright = 365;
							break;
						case 4:
							shiftdown = 20;
							shiftright = 135;
							break;
						case 5:
							shiftdown = 250;
							shiftright = 135;
							break;
    				}
					g.setColor(colors[cube[sides][x][y]]);
					g.fillRect(x * 35 + shiftright, y * 35 + shiftdown, 25, 25);
					g.setColor(Color.black);
					g.drawRect(x * 35 + shiftright, y * 35 + shiftdown, 25, 25);
					if (x == xspot && y == yspot && sides == sidespot)
					{
						if (shifting)
						{
							g.setColor(Color.red);
						}
						else
						{
							g.setColor(Color.blue);
						}
						g.drawRect(x * 35 + shiftright - 1, y * 35 + shiftdown - 1, 27, 27);
						g.drawRect(x * 35 + shiftright - 2, y * 35 + shiftdown - 2, 29, 29);
					}
				}
			}
		}
    }
	public void keyPressed(KeyEvent e)
	{
		key = e.getKeyCode();
		if (key == 8)
		{
			for (int i = 0; i < 4; i++)
			{
				create_cube_piece(1,0,null);
				create_cube_piece(2,0,null);
				create_cube_piece(2,1,null);
				create_cube_piece(2,2,null);
				create_cube_piece(1,2,null);
				turnCubeRight();
			}
		}
		if (key == e.VK_S)
		{
			solve();
		}
		if (key == e.VK_SPACE)
		{
			shifting = !shifting;
		}
		if (key == e.VK_SHIFT)
		{
			primed = !primed;
		}
		if (key == e.VK_ENTER)
		{
			scramble(scrambleamount);
		}
		if (key == e.VK_U)
		{
			if (primed)
			{
				shiftTopRight();
			}
			else
			{
				shiftTopLeft();
			}
		}
		if (key == e.VK_D)
		{
			if (primed)
			{
				shiftBottomLeft();
			}
			else
			{
				shiftBottomRight();
			}
		}
		if (key == e.VK_L)
		{
			if (primed)
			{
				shiftLeftUp();
			}
			else
			{
				shiftLeftDown();
			}
		}
		if (key == e.VK_R)
		{
			if (primed)
			{
				shiftRightDown();
			}
			else
			{
				shiftRightUp();
			}
		}
		if (key == e.VK_F)
		{
			if (primed)
			{
				shiftFrontLeft();
			}
			else
			{
				shiftFrontRight();
			}
		}
		if (key == e.VK_B)
		{
			if (primed)
			{
				shiftBackRight();
			}
			else
			{
				shiftBackLeft();
			}
		}
		if (shifting)
		{
			if (key == 38)//up
			{
				if (xspot == 0)
				{
					if (sidespot == 1 || sidespot == 4 || sidespot == 5)
					{
						shiftLeftUp();
					}
					else if (sidespot == 3)
					{
						shiftRightDown();
					}
					else if (sidespot == 0)
					{
						shiftBackRight();
					}
					else if (sidespot == 2)
					{
						shiftFrontLeft();
					}
					else
					{
						
					}
					shifting = false;
				}
				if (xspot == 2)
				{
					if (sidespot == 1 || sidespot == 4 || sidespot == 5)
					{
						shiftRightUp();
					}
					else if (sidespot == 3)
					{
						shiftLeftDown();
					}
					else if (sidespot == 0)
					{
						shiftFrontRight();
					}
					else if (sidespot == 2)
					{
						shiftBackLeft();
					}
					else
					{
						
					}
				}
				shifting = false;
			}
			if (key == 40)//down
			{
				if (xspot == 0)
				{
					if (sidespot == 1 || sidespot == 4 || sidespot == 5)
					{
						shiftLeftDown();
					}
					else if (sidespot == 3)
					{
						shiftRightUp();
					}
					else if (sidespot == 0)
					{
						shiftBackLeft();
					}
					else if (sidespot == 2)
					{
						shiftFrontRight();
					}
					else
					{
						
					}
				}
				if (xspot == 2)
				{
					if (sidespot == 1 || sidespot == 4 || sidespot == 5)
					{
						shiftRightDown();
					}
					else if (sidespot == 3)
					{
						shiftLeftUp();
					}
					else if (sidespot == 0)
					{
						shiftFrontLeft();
					}
					else if (sidespot == 2)
					{
						shiftBackRight();
					}
					else
					{
						
					}
				}
				shifting = false;
			}
			if (key == 37)//left
			{
				if (yspot == 0)
				{
					if (sidespot == 0 || sidespot == 1 || sidespot == 2 || sidespot == 3)
					{
						shiftTopLeft();
					}
					else if (sidespot == 4)
					{
						shiftBackLeft();
					}
					else if (sidespot == 5)
					{
						shiftFrontRight();
					}
					else
					{
						
					}
				}
				if (yspot == 2)
				{
					if (sidespot == 0 || sidespot == 1 || sidespot == 2 || sidespot == 3)
					{
						shiftBottomLeft();
					}
					else if (sidespot == 4)
					{
						shiftFrontLeft();
					}
					else if (sidespot == 5)
					{
						shiftBackRight();
					}
					else
					{
						
					}
				}
				shifting = false;
			}
			if (key == 39)//right
			{
				if (yspot == 0)
				{
					if (sidespot == 0 || sidespot == 1 || sidespot == 2 || sidespot == 3)
					{
						shiftTopRight();
					}
					else if (sidespot == 4)
					{
						shiftBackRight();
					}
					else if (sidespot == 5)
					{
						shiftFrontLeft();
					}
					else
					{
						
					}
				}
				if (yspot == 2)
				{
					if (sidespot == 0 || sidespot == 1 || sidespot == 2 || sidespot == 3)
					{
						shiftBottomRight();
					}
					else if (sidespot == 4)
					{
						shiftFrontRight();
					}
					else if (sidespot == 5)
					{
						shiftBackLeft();
					}
					else
					{
						
					}
				}
				shifting = false;
			}
		}
		else
		{
		if (key == 38)//38 = up
		{
			if (!primed)
			{
			yspot--;
			if (yspot == -1)
			{
				if (sidespot == 1)
				{
					yspot = 2;
					sidespot = 4;
				}
				else if (sidespot == 5)
				{
					yspot = 2;
					sidespot = 1;
				}
				else
				{
					yspot = 0;
				}
			}
			}
			else
			{
				turnCubeUp();
			}
		}
		if (key == 40)//40 = down
		{
			if (!primed)
			{
			yspot++;
			if (yspot == 3)
			{
				if (sidespot == 1)
				{
					yspot = 0;
					sidespot = 5;
				}
				else if (sidespot == 4)
				{
					yspot = 0;
					sidespot = 1;
				}
				else
				{
					yspot = 2;
				}
			}
			}
			else
			{
				turnCubeDown();
			}
		}
		if (key == 37)//37 = left
		{
			if (!primed)
			{
			xspot--;
			if (xspot == -1)
			{
				if (sidespot == 3)
				{
					sidespot--;
					xspot = 2;
				}
				else if (sidespot == 2)
				{
					sidespot--;
					xspot = 2;
				}
				else if (sidespot == 1)
				{
					sidespot--;
					xspot = 2;
				}
				else
				{
					xspot = 0;
				}
			}
			}
			else
			{
				turnCubeLeft();
			}
		}
		if (key == 39)//39 = right
		{
			if (!primed)
			{
			xspot++;
			if (xspot == 3)
			{
				if (sidespot == 0)
				{
					xspot = 0;
					sidespot++;
				}
				else if (sidespot == 1)
				{
					xspot = 0;
					sidespot++;
				}
				else if (sidespot == 2)
				{
					xspot = 0;
					sidespot++;
				}
				else
				{
					xspot = 2;
				}
			}
			}
			else
			{
				turnCubeRight();
			}
		}
		if (key == e.VK_ESCAPE)
		{
			for (int sides = 0; sides < 6; sides++)
			{
				for (int x = 0; x < 3; x++)
				{
					for (int y = 0; y < 3; y++)
					{
						cube[sides][x][y] = sides + 1;
					}
				}
			}
		}
		
		}
		repaint();
	}
	public void keyReleased(KeyEvent e)
	{
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void shiftRightUp()
	{
		int tempfirst = cube[1][2][0];
		int tempsecond = cube[1][2][1];
		int tempthird = cube[1][2][2];
		
		cube[1][2][0] = cube[5][2][0];
		cube[1][2][1] = cube[5][2][1];
		cube[1][2][2] = cube[5][2][2];
		
		cube[5][2][0] = cube[3][0][2];
		cube[5][2][1] = cube[3][0][1];
		cube[5][2][2] = cube[3][0][0];
		
		cube[3][0][2] = cube[4][2][0];
		cube[3][0][1] = cube[4][2][1];
		cube[3][0][0] = cube[4][2][2];
		
		cube[4][2][0] = tempfirst;
		cube[4][2][1] = tempsecond;
		cube[4][2][2] = tempthird;

		shiftSideRight(2);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.y == 3)
			{
				c.rotateup();
			}
		}
	}
	public void shiftRightDown()
	{
		int tempfirst = cube[1][2][0];
		int tempsecond = cube[1][2][1];
		int tempthird = cube[1][2][2];
		
		cube[1][2][0] = cube[4][2][0];
		cube[1][2][1] = cube[4][2][1];
		cube[1][2][2] = cube[4][2][2];
		
		cube[4][2][0] = cube[3][0][2];
		cube[4][2][1] = cube[3][0][1];
		cube[4][2][2] = cube[3][0][0];
		
		cube[3][0][2] = cube[5][2][0];
		cube[3][0][1] = cube[5][2][1];
		cube[3][0][0] = cube[5][2][2];
		
		cube[5][2][0] = tempfirst;
		cube[5][2][1] = tempsecond;
		cube[5][2][2] = tempthird;
		
		shiftSideLeft(2);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.y == 3)
			{
				c.rotatedown();
			}
		}
	}
	public void shiftLeftUp()
	{
		int tempfirst = cube[1][0][0];
		int tempsecond = cube[1][0][1];
		int tempthird = cube[1][0][2];
		
		cube[1][0][0] = cube[5][0][0];
		cube[1][0][1] = cube[5][0][1];
		cube[1][0][2] = cube[5][0][2];
		
		cube[5][0][0] = cube[3][2][2];
		cube[5][0][1] = cube[3][2][1];
		cube[5][0][2] = cube[3][2][0];
		
		cube[3][2][2] = cube[4][0][0];
		cube[3][2][1] = cube[4][0][1];
		cube[3][2][0] = cube[4][0][2];
		
		cube[4][0][0] = tempfirst;
		cube[4][0][1] = tempsecond;
		cube[4][0][2] = tempthird;
		
		shiftSideLeft(0);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.y == 1)
			{
				c.rotateup();
			}
		}
	}
	public void shiftLeftDown()
	{
		int tempfirst = cube[1][0][0];
		int tempsecond = cube[1][0][1];
		int tempthird = cube[1][0][2];
		
		cube[1][0][0] = cube[4][0][0];
		cube[1][0][1] = cube[4][0][1];
		cube[1][0][2] = cube[4][0][2];
		
		cube[4][0][0] = cube[3][2][2];
		cube[4][0][1] = cube[3][2][1];
		cube[4][0][2] = cube[3][2][0];
		
		cube[3][2][2] = cube[5][0][0];
		cube[3][2][1] = cube[5][0][1];
		cube[3][2][0] = cube[5][0][2];
		
		cube[5][0][0] = tempfirst;
		cube[5][0][1] = tempsecond;
		cube[5][0][2] = tempthird;
		
		shiftSideRight(0);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.y == 1)
			{
				c.rotatedown();
			}
		}
	}
	public void shiftTopLeft()
	{
		int tempfirst = cube[0][0][0];
		int tempsecond = cube[0][1][0];
		int tempthird = cube[0][2][0];
		
		cube[0][0][0] = cube[1][0][0];
		cube[0][1][0] = cube[1][1][0];
		cube[0][2][0] = cube[1][2][0];
		
		cube[1][0][0] = cube[2][0][0];
		cube[1][1][0] = cube[2][1][0];
		cube[1][2][0] = cube[2][2][0];
		
		cube[2][0][0] = cube[3][0][0];
		cube[2][1][0] = cube[3][1][0];
		cube[2][2][0] = cube[3][2][0];
		
		cube[3][0][0] = tempfirst;
		cube[3][1][0] = tempsecond;
		cube[3][2][0] = tempthird;
		
		shiftSideRight(4);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.z == 1)
			{
				c.rotateleft();
			}
		}
	}
	public void shiftTopRight()
	{
		int tempfirst = cube[0][0][0];
		int tempsecond = cube[0][1][0];
		int tempthird = cube[0][2][0];
		
		cube[0][0][0] = cube[3][0][0];
		cube[0][1][0] = cube[3][1][0];
		cube[0][2][0] = cube[3][2][0];
		
		cube[3][0][0] = cube[2][0][0];
		cube[3][1][0] = cube[2][1][0];
		cube[3][2][0] = cube[2][2][0];
		
		cube[2][0][0] = cube[1][0][0];
		cube[2][1][0] = cube[1][1][0];
		cube[2][2][0] = cube[1][2][0];
		
		cube[1][0][0] = tempfirst;
		cube[1][1][0] = tempsecond;
		cube[1][2][0] = tempthird;
		
		shiftSideLeft(4);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.z == 1)
			{
				c.rotateright();
			}
		}
	}
	public void shiftBottomLeft()
	{
		int tempfirst = cube[0][0][2];
		int tempsecond = cube[0][1][2];
		int tempthird = cube[0][2][2];
		
		cube[0][0][2] = cube[1][0][2];
		cube[0][1][2] = cube[1][1][2];
		cube[0][2][2] = cube[1][2][2];
		
		cube[1][0][2] = cube[2][0][2];
		cube[1][1][2] = cube[2][1][2];
		cube[1][2][2] = cube[2][2][2];
		
		cube[2][0][2] = cube[3][0][2];
		cube[2][1][2] = cube[3][1][2];
		cube[2][2][2] = cube[3][2][2];
		
		cube[3][0][2] = tempfirst;
		cube[3][1][2] = tempsecond;
		cube[3][2][2] = tempthird;
		
		shiftSideLeft(5);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.z == 3)
			{
				c.rotateleft();
			}
		}
	}
	public void shiftBottomRight()
	{
		int tempfirst = cube[0][0][2];
		int tempsecond = cube[0][1][2];
		int tempthird = cube[0][2][2];
		
		cube[0][0][2] = cube[3][0][2];
		cube[0][1][2] = cube[3][1][2];
		cube[0][2][2] = cube[3][2][2];
		
		cube[3][0][2] = cube[2][0][2];
		cube[3][1][2] = cube[2][1][2];
		cube[3][2][2] = cube[2][2][2];
		
		cube[2][0][2] = cube[1][0][2];
		cube[2][1][2] = cube[1][1][2];
		cube[2][2][2] = cube[1][2][2];
		
		cube[1][0][2] = tempfirst;
		cube[1][1][2] = tempsecond;
		cube[1][2][2] = tempthird;
		
		shiftSideRight(5);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.z == 3)
			{
				c.rotateright();
			}
		}
	}
	public void shiftFrontLeft()
	{
		int tempfirst = cube[4][0][2];
		int tempsecond = cube[4][1][2];
		int tempthird = cube[4][2][2];
		
		cube[4][0][2] = cube[2][0][0];
		cube[4][1][2] = cube[2][0][1];
		cube[4][2][2] = cube[2][0][2];
		
		cube[2][0][0] = cube[5][2][0];
		cube[2][0][1] = cube[5][1][0];
		cube[2][0][2] = cube[5][0][0];
		
		cube[5][0][0] = cube[0][2][0];
		cube[5][1][0] = cube[0][2][1];
		cube[5][2][0] = cube[0][2][2];
		
		cube[0][2][0] = tempthird;
		cube[0][2][1] = tempsecond;
		cube[0][2][2] = tempfirst;
		shiftSideLeft(1);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.x == 3)
			{
				c.rotatecounterclockwise();
			}
		}
	}
	public void shiftFrontRight()
	{
		int tempfirst = cube[4][0][2];
		int tempsecond = cube[4][1][2];
		int tempthird = cube[4][2][2];
		
		cube[4][0][2] = cube[0][2][2];
		cube[4][1][2] = cube[0][2][1];
		cube[4][2][2] = cube[0][2][0];
		
		cube[0][2][2] = cube[5][2][0];
		cube[0][2][1] = cube[5][1][0];
		cube[0][2][0] = cube[5][0][0];
		
		cube[5][2][0] = cube[2][0][0];
		cube[5][1][0] = cube[2][0][1];
		cube[5][0][0] = cube[2][0][2];
		
		cube[2][0][0] = tempfirst;
		cube[2][0][1] = tempsecond;
		cube[2][0][2] = tempthird;
		
		shiftSideRight(1);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.x == 3)
			{
				c.rotateclockwise();
			}
		}
	}
	public void shiftBackLeft()
	{
		int tempfirst = cube[4][0][0];
		int tempsecond = cube[4][1][0];
		int tempthird = cube[4][2][0];
		
		cube[4][0][0] = cube[2][2][0];
		cube[4][1][0] = cube[2][2][1];
		cube[4][2][0] = cube[2][2][2];
		
		cube[2][2][0] = cube[5][2][2];
		cube[2][2][1] = cube[5][1][2];
		cube[2][2][2] = cube[5][0][2];
		
		cube[5][0][2] = cube[0][0][0];
		cube[5][1][2] = cube[0][0][1];
		cube[5][2][2] = cube[0][0][2];
		
		cube[0][0][0] = tempthird;
		cube[0][0][1] = tempsecond;
		cube[0][0][2] = tempfirst;
		
		shiftSideRight(3);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.x == 1)
			{
				c.rotatecounterclockwise();
			}
		}
	}
	public void shiftBackRight()
	{
		int tempfirst = cube[4][0][0];
		int tempsecond = cube[4][1][0];
		int tempthird = cube[4][2][0];
		
		cube[4][0][0] = cube[0][0][2];
		cube[4][1][0] = cube[0][0][1];
		cube[4][2][0] = cube[0][0][0];
		
		cube[0][0][2] = cube[5][2][2];
		cube[0][0][1] = cube[5][1][2];
		cube[0][0][0] = cube[5][0][2];
		
		cube[5][2][2] = cube[2][2][0];
		cube[5][1][2] = cube[2][2][1];
		cube[5][0][2] = cube[2][2][2];
		
		cube[2][2][0] = tempfirst;
		cube[2][2][1] = tempsecond;
		cube[2][2][2] = tempthird;
		
		shiftSideLeft(3);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.x == 1)
			{
				c.rotateclockwise();
			}
		}
	}
	public void shiftSideRight(int side)
	{
		int tempfirst = cube[side][0][0];
		int tempsecond = cube[side][1][0];
		int tempthird = cube[side][2][0];
		
		cube[side][2][0] = cube[side][0][0];
		cube[side][1][0] = cube[side][0][1];
		cube[side][0][0] = cube[side][0][2];
		
		cube[side][0][0] = cube[side][0][2];
		cube[side][0][1] = cube[side][1][2];
		cube[side][0][2] = cube[side][2][2];
		
		cube[side][0][2] = cube[side][2][2];
		cube[side][1][2] = cube[side][2][1];
		cube[side][2][2] = cube[side][2][0];
		
		cube[side][2][0] = tempfirst;
		cube[side][2][1] = tempsecond;
		cube[side][2][2] = tempthird;

	}
	public void shiftSideLeft(int side)
	{
		int tempfirst = cube[side][0][0];
		int tempsecond = cube[side][1][0];
		int tempthird = cube[side][2][0];
		
		cube[side][0][0] = cube[side][2][0];
		cube[side][1][0] = cube[side][2][1];
		cube[side][2][0] = cube[side][2][2];
		
		cube[side][2][0] = cube[side][2][2];
		cube[side][2][1] = cube[side][1][2];
		cube[side][2][2] = cube[side][0][2];
		
		cube[side][2][2] = cube[side][0][2];
		cube[side][1][2] = cube[side][0][1];
		cube[side][0][2] = cube[side][0][0];
		
		cube[side][0][2] = tempfirst;
		cube[side][0][1] = tempsecond;
		cube[side][0][0] = tempthird;
		
		
	}
	public void scramble(int times)
	{
		for (int i = 0; i < times; i++)
		{
			int randomize = (int)(Math.random()*12);
			switch(randomize)
			{
			case 0: shiftBackRight();
			break;
			case 1: shiftBackLeft();
			break;
			case 2: shiftFrontRight();
			break;
			case 3: shiftFrontLeft();
			break;
			case 4: shiftRightUp();
			break;
			case 5: shiftRightDown();
			break;
			case 6:	shiftLeftUp();
			break;
			case 7: shiftLeftDown();
			break;
			case 8: shiftTopLeft();
			break;
			case 9: shiftTopRight();
			break;
			case 10: shiftBottomRight();
			break;
			case 11: shiftBottomLeft();
			break;
			}
		}
	}
	public void turnCubeRight()
	{
		int tempcube[][] = cube[0];
		cube[0] = cube[3];
		cube[3] = cube[2];
		cube[2] = cube[1];
		cube[1] = tempcube;
		shiftSideLeft(4);
		shiftSideRight(5);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotateright();
		}
		moves.add(new Integer(13));
	}
	public void turnCubeLeft()
	{
		int tempcube[][] = cube[0];
		cube[0] = cube[1];
		cube[1] = cube[2];
		cube[2] = cube[3];
		cube[3] = tempcube;
		shiftSideRight(4);
		shiftSideLeft(5);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotateleft();
		}
		moves.add(new Integer(14));
	}
	public void U()
	{
		shiftTopLeft();
		moves.add(new Integer(1));
	}
	public void Ui()
	{
		shiftTopRight();
		moves.add(new Integer(2));
	}
	public void D()
	{
		shiftBottomRight();
		moves.add(new Integer(3));
	}
	public void Di()
	{
		shiftBottomLeft();
		moves.add(new Integer(4));
	}
	public void R()
	{
		shiftRightUp();	
		moves.add(new Integer(5));
	}
	public void Ri()
	{
		shiftRightDown();
		moves.add(new Integer(6));
	}
	public void L()
	{
		shiftLeftDown();
		moves.add(new Integer(7));
	}
	public void Li()
	{
		shiftLeftUp();
		moves.add(new Integer(8));
	}
	public void F()
	{
		shiftFrontRight();
		moves.add(new Integer(9));
	}
	public void Fi()
	{
		shiftFrontLeft();
		moves.add(new Integer(10));
	}
	public void B()
	{
		shiftBackLeft();
		moves.add(new Integer(11));
	}
	public void Bi()
	{
		shiftBackRight();
		moves.add(new Integer(12));
	}
	public void turnCubeUp()
	{
		int tempcube[][] = cube[4];
		cube[4] = cube[1];
		cube[1] = cube[5];
		cube[5] = cube[3];
		cube[3] = tempcube;
		flipSide(5);
		flipSide(3);		
		shiftSideLeft(0);
		shiftSideRight(2);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotateup();
		}
		moves.add(new Integer(15));
	}
	public void turnCubeDown()
	{
		shiftSideRight(0);
		shiftSideLeft(2);
		int tempcube[][] = cube[4];
		cube[4] = cube[3];
		cube[3] = cube[5];
		cube[5] = cube[1];
		cube[1] = tempcube;
		flipSide(4);
		flipSide(3);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotatedown();
		}
		moves.add(new Integer(16));
	}
	public void swapSquare(int side, int firstx, int firsty, int secondx, int secondy)
	{
		int temp = cube[side][firstx][firsty];
		cube[side][firstx][firsty] = cube[side][secondx][secondy];
		cube[side][secondx][secondy] = temp;
	}
	public void flipSide(int side)
	{
		swapSquare(side,0,0,2,2);
		swapSquare(side,0,1,2,1);
		swapSquare(side,0,2,2,0);
		swapSquare(side,1,0,1,2);
	}
	public void tag_edge_piece(int firstcolor, int secondcolor, String tag)
	{
		int[] returned = new int[2];
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (!c.corner)
			{
				returned = c.getEdgeColors();
				boolean first = (firstcolor == returned[0] || firstcolor == returned[1]);
				boolean second = (secondcolor == returned[0] || secondcolor == returned[1]);
				if (first && second)
				{
					c.tag = tag;
				}
			}
		}
	}
	public void tag_corner_piece(int firstcolor, int secondcolor, int thirdcolor, String tag)
	{
		int[] returned = new int[3];
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (c.corner)
			{
				returned = c.getCornerColors();
				boolean first = (firstcolor == returned[0] || firstcolor == returned[1] || firstcolor == returned[2]);
				boolean second = (secondcolor == returned[0] || secondcolor == returned[1] || secondcolor == returned[2]);
				boolean third = (thirdcolor == returned[0] || thirdcolor == returned[1] || thirdcolor == returned[2]);
				if (first && second && third)
				{
					c.tag = tag;
				}
			}
		}
	}
	public Cube_Piece find_cube_piece(String tag)
	{
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			if (tag.equals(c.tag))
			{
				return c;
			}
		}
		return null;
	}
	public void untag()
	{
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.tag = null;
		}
	}
	public void create_cube_piece(int x, int y, String tag)
	{
		int spotx, spoty, spotz;
		int[] colors = new int[6];
		spotx = 3;
		spoty = x + 1;
		spotz = y + 1;
		colors[1] = cube[1][x][y];
		if (y == 0)
		{
			colors[4] = cube[4][x][2];
		}
		if (y == 2)
		{
			colors[5] = cube[5][x][0];
		}
		if (x == 0)
		{
			colors[0] = cube[0][2][y];
		}
		if (x == 2)
		{
			colors[2] = cube[2][0][y];
		}
		Cube_Pieces.add(new Cube_Piece(spotx, spoty, spotz, colors, tag));
	}
	public int findside(int color)
	{
		for (int i = 0; i < 6; i++)
		{
			if (cube[i][1][1] == color)
			{
				return i;
			}
		}
		return 0;
	}
	public void moveside(int startside, int finishside)
	{
		if (startside == finishside)
		{
			
		}
		else
		{
			boolean startonside = false;
			boolean finishonside = false;
			if (startside == 0 || startside == 1 || startside == 2 || startside == 3)
			{
				startonside = true;
			}
			if (finishside == 0 || finishside == 1 || finishside == 2 || finishside == 3)
			{
				finishonside = true;
			}
			if (finishonside && startonside)
			{
				while (startside != finishside)
				{
					turnCubeRight();
					startside++;
					if (startside == 4)
					{
						startside = 0;
					}
				}
			}
			else if (!finishonside && !startonside)
			{
				turnCubeUp();
				turnCubeUp();
			}
			else if (!finishonside && startonside)
			{
				while(startside != 1)
				{
					turnCubeRight();
					startside++;
					if (startside == 4)
					{
						startside = 0;
					}
				}
				if (finishside == 4)
				{
					turnCubeUp();
				}
				else
				{
					turnCubeDown();
				}
			}
			else if (finishonside && !startonside)
			{
				if (startside == 5)
				{
					turnCubeUp();
				}
				else
				{
					turnCubeDown();
				}
				startside = 1;
				while (startside != finishside)
				{
					turnCubeRight();
					startside++;
					if (startside == 4)
					{
						startside = 0;
					}
				}
			}
		}
	}
	public void solve()
	{
		int frontface = cube[1][1][1];
		int topface = cube[4][1][1];
		int arraysize = 0;
		//reset moves
		moves = new ArrayList<Integer>();
		robotmoves = new ArrayList<Integer>();
		//top cross
		tag_edge_piece(1,5,"green");
		tag_edge_piece(2,5,"red");
		tag_edge_piece(3,5,"blue");
		tag_edge_piece(4,5,"orange");
		moveside(findside(5),4);
		moveside(findside(1),1);
		Cube_Piece c = find_cube_piece("green");
		topcross(c);
		turnCubeLeft();
		c = find_cube_piece("red");
		topcross(c);
		turnCubeLeft();
		c = find_cube_piece("blue");
		topcross(c);
		turnCubeLeft();
		c = find_cube_piece("orange");
		topcross(c);
		untag();
		
		//top corners
		tag_corner_piece(1,2,5,"green-red");
		tag_corner_piece(2,3,5,"red-blue");
		tag_corner_piece(3,4,5,"blue-orange");
		tag_corner_piece(4,1,5,"orange-green");
		turnCubeLeft();
		c = find_cube_piece("green-red");
		topcorners(c);
		turnCubeLeft();
		c = find_cube_piece("red-blue");
		topcorners(c);
		turnCubeLeft();
		c = find_cube_piece("blue-orange");
		topcorners(c);
		turnCubeLeft();
		c = find_cube_piece("orange-green");
		topcorners(c);
		untag();
		
		//middle edges
		turnCubeUp();
		turnCubeUp();
		tag_edge_piece(1,2,"red-green");
		tag_edge_piece(4,1,"green-orange");
		tag_edge_piece(3,4,"orange-blue");
		tag_edge_piece(2,3,"blue-red");
		c = find_cube_piece("red-green");
		middleedges(c);
		turnCubeLeft();
		c = find_cube_piece("green-orange");
		middleedges(c);
		turnCubeLeft();
		c = find_cube_piece("orange-blue");
		middleedges(c);
		turnCubeLeft();
		c = find_cube_piece("blue-red");
		middleedges(c);
		untag();
		
		//bottom cross
		tag_edge_piece(1,6,"green");
		tag_edge_piece(4,6,"orange");
		tag_edge_piece(3,6,"blue");
		tag_edge_piece(2,6,"red");
		bottomcross();
		untag();
		bottomedges();
		
		//bottom corners
		tag_corner_piece(1,4,6,"green-orange");
		tag_corner_piece(4,3,6,"orange-blue");
		tag_corner_piece(3,2,6,"blue-red");
		tag_corner_piece(2,1,6,"red-green");
		bottomcorners();
		untag();
		
		//finish
		printMoves();
		System.out.println();
		shortenMoves();
		printMoves();
		arraysize = moves.size();
		System.out.println();
		moveside(findside(topface),4);
		moveside(findside(frontface),1);
		if (moves.size() != arraysize)
		{
			for (int i = arraysize; arraysize < moves.size();)
			{
				moves.remove(i);
			}
		}
		printMoves();
		System.out.println();
		undoMoves();
		movesForRobot();
		printRobotMoves();
		filter180();
		System.out.println();
		printRobotMoves();
		System.out.println();
		System.out.print("{");
		for (int i = 0; i < robotmoves.size(); i++)
		{
			System.out.print(robotmoves.get(i).intValue());
			if (i != robotmoves.size()-1)
			{
				System.out.print(", ");
			}
		}
		System.out.print("}");
		System.out.println();
		System.out.println();
		
	}
	public void topcross(Cube_Piece c)
	{
		if (c.z == 1)
		{
			if (c.colors[4] == 5 && (c.tag).equals("green"))
			{
				while (c.x != 3)
				{
					U();
				}
				return;
			}
			else if (c.y == 1)
			{
				L();
				L();
				D();
			}
			else if (c.y == 2 && c.x == 1)
			{
				B();
				B();
				D();
				D();
			}
			else if (c.y == 2 && c.x == 3)
			{
				F();
				F();
			}
			else if (c.y  == 3)
			{
				R();
				R();
				Di();
			}
		}
		else if (c.z == 2)
		{
			if (c.x == 1 && c.y == 1)
			{
				if (c.colors[3] == 5)
				{
					Li();
					D();
					L();
				}
				else
				{
					B();
					D();
					D();
					Bi();
					
				}
			}
			else if (c.x == 1 && c.y == 3)
			{
				if (c.colors[2] == 5)
				{
					Bi();
					D();
					D();
					B();
					
				}
				else
				{
					R();
					Di();
					Ri();
					
				}
			}
			else if (c.x == 3 && c.y == 3)
			{
				if (c.colors[1] == 5)
				{
					Ri();
					Di();
					R();
					
				}
				else
				{
					F();
				}
			}
			else if (c.x == 3 && c.y == 1)
			{
				if (c.colors[0] == 5)
				{
					Fi();
				}
				else
				{
					L();
					D();
					Li();
				}
			}
		}
		else
		{
			if (c.y == 1)
			{
				D();
			}
			else if (c.y == 2 && c.x == 1)
			{
				D();
				D();
			}
			else if (c.y == 2 && c.x == 3)
			{
			}
			else if (c.y  == 3)
			{
				Di();
			}
		}
		if (c.colors[5] == 5)
		{
			F();
			F();
		}
		else
		{
			Di();
			Li();
			F();
			L();
		}
	}
	public void topcorners(Cube_Piece c)
	{
		if (c.z == 1)
		{
			if (c.x == 1 && c.y == 1)
			{
				Li();
				D();
				L();
			}
			else if (c.x == 1 && c.y == 3)
			{
				Bi();
				D();
				B();
			}
			else if (c.x == 3 && c.y == 3)
			{
				Ri();
				D();
				R();
			}
			else if (c.x == 3 && c.y == 1)
			{
				Fi();
				D();
				F();
			}
		}
		else
		{
			
		}
		while(c.x != 3 || c.y != 3)
		{
			D();
		}
		if (c.colors[1] == 5)
		{
			top_corner_from_front();
		}
		else if (c.colors[2] == 5)
		{
			top_corner_from_right();
		}
		else
		{
			top_corner_from_bottom();
		}
	}
	public void middleedges(Cube_Piece c)
	{
		if (cube[1][1][1] == cube[1][2][1] && cube[2][1][1] == cube[2][0][1])
		{
			
		}
		else
		{
			int originalside = cube[1][1][1];
			if (c.z == 1)
			{
				
			}
			else
			{
				while (c.x != 3 || c.y != 3)
				{
					turnCubeRight();
				}
				edge_from_left();
				moveside(findside(originalside),1);
			}
			while (c.x != 3)
			{
				U();
			}
			if (c.colors[1] == originalside)
			{
				edge_from_left();
			}
			else
			{
				Ui();
				edge_from_right();
			}
		}
	}
	public void bottomcross()
	{
		
		Cube_Piece edge1 = find_cube_piece("green");
		Cube_Piece edge2 = find_cube_piece("red");
		Cube_Piece edge3 = find_cube_piece("blue");
		Cube_Piece edge4 = find_cube_piece("orange");
		boolean done = false;
		Cube_Piece[] facingup = new Cube_Piece[4];
		int spot = 0;
		while (!done)
		{
			int upcount = 0;
			spot = 0;
			facingup = new Cube_Piece[4];
			if (edge1.colors[4] == 6)
			{
				upcount++;
				facingup[spot] = edge1;
				spot++;
			}
			if (edge2.colors[4] == 6)
			{
				upcount++;
				facingup[spot] = edge2;
				spot++;
			}
			if (edge3.colors[4] == 6)
			{
				upcount++;
				facingup[spot] = edge3;
				spot++;
			}
			if (edge4.colors[4] == 6)
			{
				upcount++;
				facingup[spot] = edge4;
				spot++;
			}
			if (upcount < 2)
			{
				bottom_cross();
			}
			else if (upcount == 4)
			{
				done = true;
			}
			else
			{
				if (facingup[0].x == facingup[1].x)
				{
					bottom_cross();
					done = true;
				}
				else if (facingup[0].y == facingup[1].y)
				{
					turnCubeRight();
					bottom_cross();
					done = true;
				}
				else
				{
					while (!((facingup[0].x == 1 && facingup[1].y == 1) || (facingup[1].x == 1 && facingup[0].y == 1)))
					{
						turnCubeRight();
					}
					bottom_cross();
					bottom_cross();
					done = true;
				}
			}
		}
	}
	public void bottomedges()
	{
		int[] counts = new int[4];
		counts[0] = scanedges();
		U();
		counts[1] = scanedges();
		U();
		counts[2] = scanedges();
		U();
		counts[3] = scanedges();
		U();
		int highestcount = 0;
		int spot = 0;
		for (int i = 0; i < counts.length; i++)
		{
			if (counts[i] > highestcount)
			{
				highestcount = counts[i];
				spot = i;
			}
		}
		if (highestcount == 4)
		{
			for (int i = 0; i < spot; i++)
			{
				U();
			}
		}
		else if (highestcount == 2)
		{
			for (int i = 0; i < spot; i++)
			{
				U();
			}
			if (cube[1][1][0] == cube[1][1][1])
			{
				while (cube[1][1][0] == cube[1][1][1])
				{
					turnCubeRight();
				}
				bottom_cross_edges();
			}
			else
			{
				while (cube[1][1][0] != cube[1][1][1])
				{
					turnCubeLeft();
				}
				turnCubeRight();
				bottom_cross_edges();
			}
		}
		else if (highestcount == 1)
		{
			for (int i = 0; i < spot; i++)
			{
				U();
			}
			if (cube[1][1][0] == cube[1][1][1])
			{
				turnCubeRight();
				bottom_cross_edges();
			}
			else
			{
				while (cube[1][1][0] != cube[1][1][1])
				{
					turnCubeLeft();
				}
				turnCubeRight();
				bottom_cross_edges();
			}
			U();
			turnCubeLeft();
			bottom_cross_edges();
		}
	}
	public int scanedges()
	{

		int count = 0;
		int highestcount = 0;
		for (int i = 0; i < 5; i++)
		{
			if (cube[1][1][0] == cube[1][1][1])
			{
				count++;
			}
			else 
			{
				count = 0;
			}
			if (count > highestcount)
			{
				highestcount = count;
			}
			turnCubeRight();
		}
		if (highestcount == 5)
		{
			highestcount = 4;
		}
		return highestcount;
	}
	public void bottomcorners()
	{
		
		boolean done = false;
		Cube_Piece green_orange = find_cube_piece("green-orange");
		Cube_Piece orange_blue = find_cube_piece("orange-blue");
		Cube_Piece blue_red = find_cube_piece("blue-red");
		Cube_Piece red_green = find_cube_piece("red-green");
		while (!done)
		{
			moveside(findside(1),1);
			int count = 0;
			int spot = 0;
			if (green_orange.x == 3 && green_orange.y == 3)
			{
				count++;
				spot = 0;
			}
			if (orange_blue.x == 1 && orange_blue.y == 3)
			{
				count++;
				spot = 1;
			}
			if (blue_red.x == 1 && blue_red.y == 1)
			{
				count++;
				spot = 2;
			}
			if (red_green.x == 3 && red_green.y == 1)
			{
				count++;
				spot = 3;
			}
			if (count == 0)
			{
				bottom_corners();
			}
			else if (count == 1)
			{
				for (int i = 0; i < spot; i++)
				{
					turnCubeLeft();
				}
				bottom_corners();
			}
			else
			{
				done = true;
			}
		}
		int onfront = 0;
		int onright = 0;
		for (int i = 0; i < 4; i++)
		{
			if (cube[4][2][2] == 6)
			{
				
			}
			else
			{
				if (cube[1][2][0] == 6)
				{
					onfront++;
				}
				else
				{
					onright++;
				}
			}
			turnCubeLeft();
		}
		for (int i = 0; i < 4; i++)
		{
			if (cube[4][2][2] == 6)
			{
				
			}
			else
			{
				if (onfront > onright)
				{
					while (cube[4][2][2] != 6)
					{
						orient_bottom_corners_clockwise();
					}
				}
				else
				{
					while (cube[4][2][2] != 6)
					{
						orient_bottom_corners_counterclockwise();
					}
				}
			}
			U();
		}
	}
/*
 moves
 1 = U
 2 = Ui
 3 = D
 4 = Di
 5 = R
 6 = Ri
 7 = L
 8 = Li
 9 = F
 10 = Fi
 11 = B
 12 = Bi
 13 = turn cube right
 14 = turn cube left
 15 = turn cube up
 16 = turn cube down
*/
	public void shortenMoves()
	{
		for (int i = moves.size()-1; i >= 0; i--)
		{
			Integer inty = moves.get(i);
			int value = inty.intValue();
			if (value >= 13)
			{
				if (value == 13)
				{
					for (int j = i+1; j < moves.size(); j++)
					{
						if (moves.get(j).intValue() >= 5 && moves.get(j).intValue() <= 8)
						{
							changeInt(j,moves.get(j).intValue()+4);
						}
						else if (moves.get(j).intValue() >= 9 && moves.get(j).intValue() <= 10)
						{
							changeInt(j,moves.get(j).intValue()-2);
						}
						else if (moves.get(j).intValue() >= 11 && moves.get(j).intValue() <= 12)
						{
							changeInt(j,moves.get(j).intValue()-6);
						}
					}
					
				}
				else if (value == 14)
				{
					for (int j = i+1; j < moves.size(); j++)
					{
						if (moves.get(j).intValue() >= 5 && moves.get(j).intValue() <= 6)
						{
							changeInt(j,moves.get(j).intValue()+6);
						}
						else if (moves.get(j).intValue() >= 7 && moves.get(j).intValue() <= 8)
						{
							changeInt(j,moves.get(j).intValue()+2);
						}
						else if (moves.get(j).intValue() >= 9 && moves.get(j).intValue() <= 12)
						{
							changeInt(j,moves.get(j).intValue()-4);
						}
					}
				}
				if (value == 15)
				{
					for (int j = i+1; j < moves.size(); j++)
					{
						if (moves.get(j).intValue() >= 1 && moves.get(j).intValue() <= 4)
						{
							changeInt(j,moves.get(j).intValue()+8);
						}
						else if (moves.get(j).intValue() >= 9 && moves.get(j).intValue() <= 10)
						{
							changeInt(j,moves.get(j).intValue()-6);
						}
						else if (moves.get(j).intValue() >= 11 && moves.get(j).intValue() <= 12)
						{
							changeInt(j,moves.get(j).intValue()-10);
						}
					}
					
				}
				else if (value == 16)
				{
					for (int j = i+1; j < moves.size(); j++)
					{
						if (moves.get(j).intValue() >= 1 && moves.get(j).intValue() <= 2)
						{
							changeInt(j,moves.get(j).intValue()+10);
						}
						else if (moves.get(j).intValue() >= 3 && moves.get(j).intValue() <= 4)
						{
							changeInt(j,moves.get(j).intValue()+6);
						}
						else if (moves.get(j).intValue() >= 9 && moves.get(j).intValue() <= 12)
						{
							changeInt(j,moves.get(j).intValue()-8);
						}
					}
				}
				moves.remove(i);
				
			}
		}
		boolean done = false;
		while (!done)
		{	
			int lastnum = 0;
			boolean doubled = false;
			ArrayList<Integer>before = new ArrayList<Integer>();
			for (int i = 0; i < moves.size(); i++)
			{
				before.add(new Integer(moves.get(i).intValue()));
			}
			for (int i = 0; i < moves.size(); i++)
			{
				if (i != 0)
				{
					lastnum = moves.get(i-1).intValue();
				}
				Integer inty = moves.get(i);
				int value = inty.intValue();
				if (value > 0 && i != 0)
				{
					if (value == lastnum && !doubled)
					{
						doubled = true;
					}
					else if (doubled && value == lastnum)
					{
						if (isOdd(value))
						{
							moves.add(i-2, new Integer(value+1));
							moves.remove(i+1);
							moves.remove(i);
							moves.remove(i-1);
							i-=3;
						}
						else
						{
							moves.add(i-2, new Integer(value-1));
							moves.remove(i+1);
							moves.remove(i);
							moves.remove(i-1);
							i-=3;
						}
						doubled = false;
					}
					else if (isOdd(value) && value+1 == lastnum)
					{
						moves.remove(i);
						moves.remove(i-1);
						i-=2;
						doubled = false;
					}
					else if (!isOdd(value) && value-1 == lastnum)
					{
						moves.remove(i);
						moves.remove(i-1);
						i-=2;
						doubled = false;
					}
					else
					{
						doubled = false;
					}
				}
			}
			if (moves.size() == before.size())
			{
				boolean same = true;
				for (int i = 0; i < moves.size(); i++)
				{
					if (moves.get(i).intValue() == before.get(i).intValue())
					{
						
					}
					else
					{
						same = false;
					}
				}
				if (same == true)
				{
					done = true;
				}
				else
				{
					done = false;
				}
			}
		}

	}
	/*
	D = 5
	F = 1
	L = 2
	B = 3
	R = 4
	U = 6
	*/
	public void robotturnCubeRight()
	{
		int tempcube[][] = cube[0];
		cube[0] = cube[3];
		cube[3] = cube[2];
		cube[2] = cube[1];
		cube[1] = tempcube;
		shiftSideLeft(4);
		shiftSideRight(5);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotateright();
		}
		robotmoves.add(new Integer(10));
	}
	public void robotturnCubeLeft()
	{
		int tempcube[][] = cube[0];
		cube[0] = cube[1];
		cube[1] = cube[2];
		cube[2] = cube[3];
		cube[3] = tempcube;
		shiftSideRight(4);
		shiftSideLeft(5);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotateleft();
		}
		robotmoves.add(new Integer(1));
	}
	public void robotturnCubeDown()
	{
		shiftSideRight(0);
		shiftSideLeft(2);
		int tempcube[][] = cube[4];
		cube[4] = cube[3];
		cube[3] = cube[5];
		cube[5] = cube[1];
		cube[1] = tempcube;
		flipSide(4);
		flipSide(3);
		for (int i = 0; i < Cube_Pieces.size(); i++)
		{
			Cube_Piece c = Cube_Pieces.get(i);
			c.rotatedown();
		}
		robotmoves.add(new Integer(4));
	}
	public void turntobottom(int side)
	{
		switch (side)
		{
		case 0:
			robotturnCubeRight();
			robotturnCubeDown();
			break;
		case 1:
			robotturnCubeDown();
			break;
		case 2:
			robotturnCubeLeft();
			robotturnCubeDown();
			break;
		case 3:
			robotturnCubeLeft();
			robotturnCubeLeft();
			robotturnCubeDown();
			break;
		case 4:
			robotturnCubeDown();
			robotturnCubeDown();
			break;
		case 5:
			break;
		}
	}
	public void robotD()
	{
		shiftBottomRight();
		robotmoves.add(new Integer(6));
	}
	public void robotDi()
	{
		shiftBottomLeft();
		robotmoves.add(new Integer(3));
	}
	/*
	 moves
	 1 = U
	 2 = Ui // 1
	 3 = D
	 4 = Di // 2
	 5 = R
	 6 = Ri // 3
	 7 = L
	 8 = Li // 4
	 9 = F
	 10 = Fi // 5
	 11 = B
	 12 = Bi // 6
	 13 = turn cube right
	 14 = turn cube left
	 15 = turn cube up
	 16 = turn cube down
	*/
	public void movesForRobot()
	{
		int sides[] = new int[6];
		for (int i = 0; i < 6; i++)
		{
			sides [i]  = cube[i][1][1];
		}
		for (int i = 0; i < moves.size(); i++)
		{
			int inty = (moves.get(i)+1)/2;
			switch (inty)
			{
			case 1:
				turntobottom(findside(sides[4]));
				break;
			case 2:
				turntobottom(findside(sides[5]));
				break;
			case 3:
				turntobottom(findside(sides[2]));
				break;
			case 4:
				turntobottom(findside(sides[0]));
				break;
			case 5:
				turntobottom(findside(sides[1]));
				break;
			case 6:
				turntobottom(findside(sides[3]));
				break;
			}
			if (isOdd(moves.get(i)))
			{
				robotD();
			}
			else
			{
				robotDi();
			}
		}
	}
	public void filter180()
	{
		int before = 0;
		for (int i = 0; i < robotmoves.size(); i++)
		{
			int inty = robotmoves.get(i).intValue();
			if (inty == before && (inty == 3 || inty == 6 || inty == 10 || inty == 1))
			{
				if (inty == 3 || inty == 6)
				{
					robotmoves.remove(i-1);
					robotmoves.remove(i-1);
					robotmoves.add(i-1, new Integer(5));
					i--;
					//5
				}
				else
				{
					robotmoves.remove(i-1);
					robotmoves.remove(i-1);
					robotmoves.add(i-1, new Integer(9));
					i--;
					//9
				}
				before = 0;
			}
			else
			{
				before = inty;
			}
		}
	}
	public void printMoves()
	{
		for (int i = 0; i < moves.size(); i++)
		{
			String move = new String();
			switch(moves.get(i).intValue())
			{
			case 1:
				move = "U";
				break;
			case 2:
				move = "Ui";
				break;
			case 3:
				move = "D";
				break;
			case 4:
				move = "Di";
				break;
			case 5:
				move = "R";
				break;
			case 6:
				move = "Ri";
				break;
			case 7:
				move = "L";
				break;
			case 8:
				move = "Li";
				break;
			case 9:
				move = "F";
				break;
			case 10:
				move = "Fi";
				break;
			case 11:
				move = "B";
				break;
			case 12:
				move = "Bi";
				break;
			case 13:
				move = "Right";
				break;
			case 14:
				move = "Left";
				break;
			case 15:
				move = "Up";
				break;
			case 16:
				move = "Down";
				break;
			}
			System.out.print(move + ", ");
		}
	}
	public void printRobotMoves()
	{
		for (int i = 0; i < robotmoves.size(); i++)
		{
			String move = new String();
			switch(robotmoves.get(i).intValue())
			{
			case 5:
				move = "180 Bottom";
				break;
			case 9:
				move = "Turn 180";
				break;
			case 6:
				move = "D";
				break;
			case 3:
				move = "Di";
				break;
			case 10:
				move = "Right";
				break;
			case 1:
				move = "Left";
				break;
			case 4:
				move = "Down";
				break;
			
			}
			System.out.print(move + "(" + robotmoves.get(i).intValue() + ")" + ", " );
		}
		System.out.println();
		System.out.print(robotmoves.size());
	}
	public void undoMoves()
	{
		for (int i = moves.size() - 1; i >= 0; i--)
		{
			switch (moves.get(i))
			{
			case 1:
				shiftTopRight();
				break;
			case 2:
				shiftTopLeft();
				break;
			case 3:
				shiftBottomLeft();
				break;
			case 4:
				shiftBottomRight();
				break;
			case 5:
				shiftRightDown();
				break;
			case 6:
				shiftRightUp();
				break;
			case 7:
				shiftLeftUp();
				break;
			case 8:
				shiftLeftDown();
				break;
			case 9:
				shiftFrontLeft();
				break;
			case 10:
				shiftFrontRight();
				break;
			case 11:
				shiftBackRight();
				break;
			case 12:
				shiftBackLeft();
				break;
			}
		}
	}
	public void changeInt(int index, int after)
	{
		moves.add(index, new Integer(after));
		moves.remove(index + 1);
	}
	public boolean isOdd(int value)
	{
		if (value%2 == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public void top_corner_from_right()//corner will be on the right
	{
		Ri();
		Di();
		R();
	}
	public void top_corner_from_front()//corner will be on the right
	{
		F();
		D();
		Fi();
	}
	public void top_corner_from_bottom()//corner will be on the right
	{
		Di();
		Ri();
		D();
		D();
		R();
		top_corner_from_front();
	}
	public void edge_from_right()//move from left
	{
		Ui();
		Fi();
		U();
		F();
		
		U();
		R();
		Ui();
		Ri();
	}
	public void edge_from_left()//move from right
	{
		U();
		R();
		Ui();
		Ri();
		
		Ui();
		Fi();
		U();
		F();
	}
	public void bottom_cross()
	{
		F();
		R();
		U();
		Ri();
		Ui();
		Fi();
	}
	public void bottom_cross_edges()
	{
		R();
		U();
		Ri();
		U();
		R();
		U();
		U();
		Ri();
		U();
	}
	public void bottom_corners()
	{
		U();
		R();
		Ui();
		Li();
		U();
		Ri();
		Ui();
		L();
	}
	public void orient_bottom_corners_counterclockwise()//move counterclockwise
	{
		Ri();
		Di();
		R();
		D();
	}
	public void orient_bottom_corners_clockwise()//move clockwise
	{
		Ri();
		D();
		R();
		Di();
	}
}
