package towers;

import java.util.ArrayList;

public class Menu 
{
	public boolean showing;
	public String name;
	public static ArrayList<Menu>myMenus = new ArrayList<Menu>();
	public Menu(boolean showing, String name)
	{
		this.showing = showing;
		this.name = name;
	}
	public static Menu assignButton(String nameString)
	{
		boolean gotname = false;
		Menu temp = new Menu(false, "null");
		for (int i = 0; i < myMenus.size(); i++)
		{
			Menu m = myMenus.get(i);
			if (m.name.equals(nameString))
			{
				gotname = true;
				temp = m;
			}
			else
			{
				
			}
		}
		if (gotname)
		{
			return temp;
		}
		else
		{
			return myMenus.get(-1);
		}
	}
	public static void closeMenu(String name)
	{
		for (int i = 0; i < myMenus.size(); i++)
		{
			Menu m = myMenus.get(i);
			if (m.name.equals(name))
			{
				m.showing = false;
			}
		}
	}
	public static void openMenu(String name)
	{
		for (int i = 0; i < myMenus.size(); i++)
		{
			Menu m = myMenus.get(i);
			if (m.name.equals(name))
			{
				m.showing = true;
			}
		}
	}
	public static boolean showing(String name)
	{
		boolean gotname = false;
		Menu temp = new Menu(false, "null");
		for (int i = 0; i < myMenus.size(); i++)
		{
			Menu m = myMenus.get(i);
			if (m.name.equals(name))
			{
				gotname = true;
				temp = m;
			}
			else
			{
				
			}
		}
		if (gotname)
		{
			return temp.showing;
		}
		else
		{
			return temp.showing;
		}
	}
}
