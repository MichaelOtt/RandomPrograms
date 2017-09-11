import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MovesTester 
{
	static ArrayList<Integer>moves = new ArrayList<Integer>();
	public static void main (String [] args) throws IOException
	{
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
	 9 = B
	 10 = Bi
	 11 = F
	 12 = Fi
	 13 = turn cube right
	 14 = turn cube left
	 15 = turn cube up
	 16 = turn cube down
	 */
		
		ArrayList<Integer>original = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++)
		{
			String a=JOptionPane.showInputDialog(null,"type in move "+ (i+1));
			original.add(new Integer(Integer.parseInt(a)));
		}

		for (int i = 0; i < original.size(); i++)
		{
			moves.add(new Integer(original.get(i).intValue()));
		}
		for (int i = moves.size()-1; i >= 0; i--)
		{
			Integer inty = moves.get(i);
			int value = inty.intValue();
			if (value >= 13)
			{
				if (value == 14)
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
				else if (value == 13)
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
				if (value == 16)
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
				else if (value == 15)
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
		for (int i = 0; i < original.size(); i++)
		{
			System.out.print(original.get(i).intValue() + ", ");
		}
		System.out.println();
		for (int i = 0; i < moves.size(); i++)
		{
			System.out.print(moves.get(i).intValue() + ", ");
		}
	}
	public static boolean isOdd(int value)
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
	public static void changeInt(int index, int after)
	{
		moves.add(index, new Integer(after));
		moves.remove(index + 1);
	}
}
