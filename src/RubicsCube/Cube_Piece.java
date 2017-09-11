package RubicsCube;
/*
sides
    4
0   1   2   3
    5
 */

public class Cube_Piece
{
	public int x,y,z;//from top right back corner, starts at 1 goes to 3
	public int[] colors = new int[6];//0-5, -1 = none
	public String tag = new String();
	public boolean corner = false;
	public Cube_Piece(int x, int y, int z, int[] colors, String tag)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.colors = colors;
		this.tag = tag;
		int num = 0;
		for (int i = 0; i < colors.length; i++)
		{
			if (colors[i] != 0)
			{
				num++;
			}
		}
		if (num == 3)
		{
			corner = true;
		}
	}
	public void rotatedown()
	{
		if (x != 2 && z != 2)
		{
			if (x == 1 && z == 1)
			{
				x = 3;
			}
			else if (x == 3 && z == 1)
			{
				z = 3;
			}
			else if (x == 3 && z == 3)
			{
				x = 1;
			}
			else if (x == 1 && z == 3)
			{
				z = 1;
			}
		}
		else
		{
			if (x == 2 && z == 1)
			{
				x = 3;
				z = 2;
			}
			else if (x == 3 && z == 2)
			{
				z = 3;
				x = 2;
			}
			else if (x == 2 && z == 3)
			{
				x = 1;
				z = 2;
			}
			else if (x == 1 && z == 2)
			{
				z = 1;
				x = 2;
			}
		}
		int tempside = colors[1];
		colors[1] = colors[4];
		colors[4] = colors[3];
		colors[3] = colors[5];
		colors[5] = tempside;
	}
	public void rotateup()
	{
		if (x != 2 && z != 2)
		{
			if (x == 1 && z == 1)
			{
				z = 3;
			}
			else if (x == 3 && z == 1)
			{
				x = 1;
			}
			else if (x == 3 && z == 3)
			{
				z = 1;
			}
			else if (x == 1 && z == 3)
			{
				x = 3;
			}
		}
		else
		{
			if (x == 2 && z == 1)
			{
				x = 1;
				z = 2;
			}
			else if (x == 1 && z == 2)
			{
				z = 3;
				x = 2;
			}
			else if (x == 2 && z == 3)
			{
				x = 3;
				z = 2;
			}
			else if (x == 3 && z == 2)
			{
				z = 1;
				x = 2;
			}
		}
		int tempside = colors[1];
		colors[1] = colors[5];
		colors[5] = colors[3];
		colors[3] = colors[4];
		colors[4] = tempside;
	}
	public void rotateleft()
	{
		if (x != 2 && y != 2)
		{
			if (x == 1 && y == 1)
			{
				y = 3;
			}
			else if (x == 3 && y == 1)
			{
				x = 1;
			}
			else if (x == 3 && y == 3)
			{
				y = 1;
			}
			else if (x == 1 && y == 3)
			{
				x = 3;
			}
		}
		else
		{
			if (x == 2 && y == 1)
			{
				x = 1;
				y = 2;
			}
			else if (x == 1 && y == 2)
			{
				y = 3;
				x = 2;
			}
			else if (x == 2 && y == 3)
			{
				x = 3;
				y = 2;
			}
			else if (x == 3 && y == 2)
			{
				y = 1;
				x = 2;
			}
		}
		int tempside = colors[1];
		colors[1] = colors[2];
		colors[2] = colors[3];
		colors[3] = colors[0];
		colors[0] = tempside;
	}
	public void rotateright()
	{
		if (x != 2 && y != 2)
		{
			if (x == 1 && y == 1)
			{
				x = 3;
			}
			else if (x == 3 && y == 1)
			{
				y = 3;
			}
			else if (x == 3 && y == 3)
			{
				x = 1;
			}
			else if (x == 1 && y == 3)
			{
				y = 1;
			}
		}
		else
		{
			if (x == 2 && y == 1)
			{
				x = 3;
				y = 2;
			}
			else if (x == 3 && y == 2)
			{
				y = 3;
				x = 2;
			}
			else if (x == 2 && y == 3)
			{
				x = 1;
				y = 2;
			}
			else if (x == 1 && y == 2)
			{
				y = 1;
				x = 2;
			}
		}
		int tempside = colors[1];
		colors[1] = colors[0];
		colors[0] = colors[3];
		colors[3] = colors[2];
		colors[2] = tempside;
	}
	public void rotateclockwise()
	{
		if (z != 2 && y != 2)
		{
			if (z == 1 && y == 1)
			{
				y = 3;
			}
			else if (z == 3 && y == 1)
			{
				z = 1;
			}
			else if (z == 3 && y == 3)
			{
				y = 1;
			}
			else if (z == 1 && y == 3)
			{
				z = 3;
			}
		}
		else
		{
			if (z == 2 && y == 1)
			{
				z = 1;
				y = 2;
			}
			else if (z == 1 && y == 2)
			{
				y = 3;
				z = 2;
			}
			else if (z == 2 && y == 3)
			{
				z = 3;
				y = 2;
			}
			else if (z == 3 && y == 2)
			{
				y = 1;
				z = 2;
			}
		}
		int tempside = colors[0];
		colors[0] = colors[5];
		colors[5] = colors[2];
		colors[2] = colors[4];
		colors[4] = tempside;
	}
	public void rotatecounterclockwise()
	{
		if (z != 2 && y != 2)
		{
			if (z == 1 && y == 1)
			{
				z = 3;
			}
			else if (z == 3 && y == 1)
			{
				y = 3;
			}
			else if (z == 3 && y == 3)
			{
				z = 1;
			}
			else if (z == 1 && y == 3)
			{
				y = 1;
			}
		}
		else
		{
			if (z == 2 && y == 1)
			{
				z = 3;
				y = 2;
			}
			else if (z == 3 && y == 2)
			{
				y = 3;
				z = 2;
			}
			else if (z == 2 && y == 3)
			{
				z = 1;
				y = 2;
			}
			else if (z == 1 && y == 2)
			{
				y = 1;
				z = 2;
			}
		}
		int tempside = colors[0];
		colors[0] = colors[4];
		colors[4] = colors[2];
		colors[2] = colors[5];
		colors[5] = tempside;
	}
	public int[] getCornerColors()
	{
		int place = 0;
		int[] returned = new int[3];
		for (int i = 0; i < colors.length; i++)
		{
			if (colors[i] != 0)
			{
				returned[place] = colors[i];
				place++;
			}
		}
		return returned;
	}
	public int[] getEdgeColors()
	{
		int place = 0;
		int[] returned = new int[2];
		for (int i = 0; i < colors.length; i++)
		{
			if (colors[i] != 0)
			{
				returned[place] = colors[i];
				place++;
			}
		}
		return returned;
	}
}
