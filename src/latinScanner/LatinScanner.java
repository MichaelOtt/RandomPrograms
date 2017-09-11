package latinScanner;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.lang.*;
public class LatinScanner 
{
	static ArrayList<String>lines;
	static ArrayList<String>originalLines;
	static ArrayList<String>latinNarrowed;
	static ArrayList<String>latinUnknown;
	public static void main (String [] args) throws IOException
	{
		boolean done = false;
		String choice = JOptionPane.showInputDialog(null,"scan or spondees");
		if (choice.equals("scan"))
		{
			String line = JOptionPane.showInputDialog(null,"type line to scan");
			System.out.println(line);
			scanLine(line);
		}
		else
		{
			String name = JOptionPane.showInputDialog(null,"name of file to scan");
			while (!done)
			{
				if (name == null)
				{
					
				}
				else
				{
					try
					{
						
						BufferedReader in = new BufferedReader(new FileReader("/Users/MichaelOtt/Desktop/Programming/personal programs/bin/Latin/" + name + ".txt"));
						readLines(in);
						done = true;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						name = JOptionPane.showInputDialog(null,"that file doesn't exist");
					}
				}
			}
		}
		
	}
	public static void scanLine (String s)
	{
		String newLine = s.trim();
		newLine = removeSpecialMarks(newLine);
		
		newLine = newLine.toLowerCase();
		newLine = changeQuirks(newLine);
		
		System.out.println(newLine);
		newLine = convertToNumbers(newLine);
		System.out.println(newLine);
		newLine = fixElisions(newLine);
		System.out.println(newLine);
		newLine = removeSpaces(newLine);
		System.out.println(newLine);
		newLine = longByPosition(newLine);
		System.out.println(newLine);
		newLine = removeConsonants(newLine);
		System.out.println(newLine);
		newLine = removeEnd(newLine);
		System.out.println(newLine);
		//System.out.println(newLine.length());
		ArrayList<String> possibilities = meterPossibilities(newLine.length());
		ArrayList<String> narrowed = narrowedPossibilities(newLine, possibilities);
		//System.out.println();
		for (int j = 0; j < narrowed.size(); j++)
		{
			String narrowedLine = narrowed.get(j);
			System.out.println(narrowedLine);
		}
		System.out.println();
		for (int j = 0; j < narrowed.size(); j++)
		{
			String narrowedLine = narrowed.get(j);
			if (narrowedLine.charAt(narrowedLine.length()-1) == '3')
			{
				narrowedLine = narrowedLine.substring(0,narrowedLine.length()-3);
				int index = 1;
				while (index < narrowedLine.length())
				{
					if (narrowedLine.charAt(index)=='2')
					{
						index+=2;
						System.out.print("S/");
					}
					else
					{
						index+=3;
						System.out.print("D/");
					}
				}
				System.out.println("D/S");
			}
			//System.out.println(narrowedLine);
		}
		System.out.println();
	}
	public static void readLines(BufferedReader in)
	{
		originalLines = new ArrayList<String>();
		lines = new ArrayList<String>();
		latinNarrowed = new ArrayList<String>();
		latinUnknown = new ArrayList<String>();
		String fileline = "";
		do
		{
		
			try 
			{
				fileline = in.readLine();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lines.add(fileline);
			originalLines.add(fileline);
		}while (fileline != null);
		lines.remove(lines.size()-1);
		originalLines.remove(originalLines.size()-1);
		for (int i = 0; i < lines.size(); i++)
		{
			System.out.println(lines.get(i));
		}
		System.out.println();
		for (int i = 0; i < lines.size(); i++)
		{
			String newLine = lines.get(i).trim();
			if (newLine.length() == 0) continue;
			newLine = removeNumbering(newLine);
			newLine = newLine.trim();
			newLine = removeSpecialMarks(newLine);
			newLine = newLine.toLowerCase();
			newLine = changeQuirks(newLine);
			
			System.out.println(newLine);
			newLine = convertToNumbers(newLine);
			//System.out.println(newLine);
			newLine = fixElisions(newLine);
			//System.out.println(newLine);
			newLine = removeSpaces(newLine);
			//System.out.println(newLine);
			newLine = longByPosition(newLine);
			//System.out.println(newLine);
			newLine = removeConsonants(newLine);
			//System.out.println(newLine);
			newLine = removeEnd(newLine);
			System.out.println(newLine);
			//System.out.println(newLine.length());
			ArrayList<String> possibilities = meterPossibilities(newLine.length());
			ArrayList<String> narrowed = narrowedPossibilities(newLine, possibilities);
			boolean spondeeLine = true;
			for (int j = 0; j < narrowed.size(); j++)
			{
				String narrowedLine = narrowed.get(j);
				System.out.println(narrowedLine);
				if (narrowedLine.charAt(narrowedLine.length()-1) == '3')
				{
					//latinNarrowed.add(originalLines.get(i));
					spondeeLine = false;
				}
			}
			if (spondeeLine)
			{
				if (narrowed.size() != 0)
				{
					latinNarrowed.add(originalLines.get(i));
					System.out.println("                                          narrowed");
				}
				else
				{
					latinUnknown.add(originalLines.get(i));
					System.out.println("                                          unknown");
				}
			}
			lines.set(i, newLine);
			System.out.println();
		}
		for (int i = 0; i < latinNarrowed.size(); i++)
		{
			System.out.println(latinNarrowed.get(i).trim());
		}
		System.out.println();
		for (int i = 0; i < latinUnknown.size(); i++)
		{
			System.out.println(latinUnknown.get(i).trim());
		}
	}
	public static String replaceString(String s, String replace, int index)
	{
		return s.substring(0,index) + replace + s.substring(index+1);
	}
	public static String replaceString(String s, String replace, int index, int index2)
	{
		return s.substring(0,index) + replace + s.substring(index2+1);
	}
	public static String removeEnd(String line)
	{
		return line.substring(0,line.length()-2);
	}
	public static ArrayList<String> narrowedPossibilities(String line, ArrayList<String>possibilities)
	{
		ArrayList<String>narrowed = new ArrayList<String>();
		for (int i = 0; i < possibilities.size(); i++)
		{
			String possLine = possibilities.get(i);
			boolean correct = true;
			for (int j = 0; j < possLine.length(); j++)
			{
				if (line.charAt(j) == '1')
				{
					
				}
				else
				{
					if (line.charAt(j) != possLine.charAt(j))
					{
						correct = false;
						break;
					}
				}
			}
			if (correct)
			{
				narrowed.add(possLine);
			}
		}
		return narrowed;
	}
	public static ArrayList<String> meterPossibilities(int length)
	{
		ArrayList<String> possibilities = new ArrayList<String>();
		if (length < 10 || length > 15)
		{
			return possibilities;
		}
		final String SPONDEE = "22";
		final String DACTYL = "233";
		int spondeeNum = 15-length;
		int dactylNum = length-10;
		String line = "";
		if (spondeeNum < dactylNum)
		{
			if (spondeeNum == 2)
			{
				int spot1 = 0;
				int spot2 = 1;
				for (int j = 0; j < 10; j++)
				{
					for (int i = 0; i < 5; i++)
					{
						if (i == spot1 || i == spot2)
						{
							line = line + SPONDEE;
						}
						else
						{
							line = line + DACTYL;
						}
					}
					possibilities.add(line);
					line = "";
					if (spot2 < 4)
					{
						spot2++;
					}
					else
					{
						spot1++;
						spot2 = spot1 + 1;
					}
				}
			}
			else if (spondeeNum == 1)
			{
				for (int i = 0; i < 5; i++)
				{
					for (int j = 0; j < 5; j++)
					{
						if (j == i)
						{
							line = line + SPONDEE;
						}
						else
						{
							line = line + DACTYL;
						}
					}
					possibilities.add(line);
					line = "";
				}
			}
			else
			{
				for (int i = 0; i < 5; i++)
				{
					line = line + DACTYL;
				}
				possibilities.add(line);
			}
		}
		else
		{
			if (dactylNum == 2)
			{
				int spot1 = 0;
				int spot2 = 1;
				for (int j = 0; j < 10; j++)
				{
					for (int i = 0; i < 5; i++)
					{
						if (i == spot1 || i == spot2)
						{
							line = line + DACTYL;
						}
						else
						{
							line = line + SPONDEE;
						}
					}
					possibilities.add(line);
					line = "";
					if (spot2 < 4)
					{
						spot2++;
					}
					else
					{
						spot1++;
						spot2 = spot1 + 1;
					}
				}
			}
			else if (dactylNum == 1)
			{
				for (int i = 0; i < 5; i++)
				{
					for (int j = 0; j < 5; j++)
					{
						if (j == i)
						{
							line = line + DACTYL;
						}
						else
						{
							line = line + SPONDEE;
						}
					}
					possibilities.add(line);
					line = "";
				}
			}
			else
			{
				for (int i = 0; i < 5; i++)
				{
					line = line + SPONDEE;
				}
				possibilities.add(line);
			}
		}
		return possibilities;
	}
	public static String removeSpecialMarks(String line)
	{
		String[] marks = {".",",",";",":","'","!","?","(",")","[","]"};
		for (int i = 0 ;i < marks.length; i++)
		{
			line = line.replace(marks[i], "");
		}
		//System.out.println(line);
		return line;
	}
	public static String removeNumbering(String line)
	{
		String[] numbers = {"1","2","3","4","5","6","7","8","9","0"};
		for (int i = 0 ;i < numbers.length; i++)
		{
			line = line.replace(numbers[i], "");
		}
		//System.out.println(line);
		return line;
	}
	public static String convertToNumbers(String line)
	{
		for (int i = 0; i < line.length(); i++)
		{
			if (isVowelOrNum(line.charAt(i)))
			{
				if (isVowel(line.charAt(i)))
				{
					line = replaceString(line,"1",i);
				}
				else
				{
					
				}
			}
			else if (line.charAt(i) == ' ')
			{
				line = replaceString(line,"9",i);
			}
			else
			{
				line = replaceString(line,"0",i);
			}
		}
		return line;
	}
	public static String changeQuirks(String line)
	{
		//ArrayList<Integer>indexes = findSpaces(line);
		//possibly change i to j in first word
		if (line.charAt(0) == 'i')
		{
			if (isVowel(line.charAt(1)))
			{
				line = replaceString(line,"0",0);
			}
		}
		if (line.charAt(0) == 'u')
		{
			if (isVowel(line.charAt(1)))
			{
				line = replaceString(line,"0",0);
			}
		}
		if (line.charAt(0) == 'v')
		{
			if (!isVowel(line.charAt(1)))
			{
				line = replaceString(line,"1",0);
			}
		}
		//deal with spaces
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i)==' ')
			{
				//fix i at start of words
				if (line.charAt(i+1) == 'i')
				{
					if (isVowel(line.charAt(i+2)))
					{
						line = replaceString(line,"0",i+1);
					}
				}
				if (line.charAt(i+1) == 'u')
				{
					if (isVowel(line.charAt(i+2)))
					{
						line = replaceString(line,"0",i+1);
					}
				}
				if (line.charAt(i+1) == 'v')
				{
					if (!isVowel(line.charAt(i+2)))
					{
						line = replaceString(line,"1",i+1);
					}
				}
			}
		}
		for (int i = 1; i < line.length(); i++)
		{
			String quirk = String.valueOf(line.charAt(i-1)) + String.valueOf(line.charAt(i));
			quirk = fixDigraphs(quirk);
			
			if ((quirk.equals("qu") || quirk.equals("gu") || quirk.equals("su")) && i < line.length()-1 && isVowel(line.charAt(i+1)))
			{
				quirk = "0";
			}
			line = replaceString(line, quirk, i-1, i);
		}
		for (int i = line.length()-1; i >= 0; i--)
		{
			line = fixUandV(line, i);
		}
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i)==' ')
			{
				//get rid of h at beginnings
				if (line.charAt(i+1) == 'h')
				{
					line = replaceString(line,"",i+1);
				}//get rid of m at ends
				if (line.charAt(i-1) == 'm' && isVowel(line.charAt(i-2)) && isVowel(line.charAt(i+1)))
				{
					line = replaceString(line,"",i-1);
				}
			}
		}
		for (int i = 1; i < line.length(); i++)
		{
			String quirk = String.valueOf(line.charAt(i-1)) + String.valueOf(line.charAt(i));
			quirk = fixDipthongs(quirk);
			line = replaceString(line, quirk, i-1, i);
		}
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i) == 'x')
			{
				line = replaceString(line, "00", i);
			}
		}
		return line;
	}
	public static String removeConsonants(String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i)=='0')
			{
				s = replaceString(s, "", i);
				i--;
			}
		}
		return s;
	}
	/*public static ArrayList<Integer> decrementSpaces(ArrayList<Integer> intArr)
	{
		for (int i = 0; i < intArr.size(); i++)
		{
			int newInt = intArr.get(i)-1;
			intArr.set(i,newInt);
		}
		return intArr;
	}
	public static ArrayList<Integer> incrementSpaces(ArrayList<Integer> intArr)
	{
		for (int i = 0; i < intArr.size(); i++)
		{
			int newInt = intArr.get(i)+1;
			intArr.set(i,newInt);
		}
		return intArr;
	}*/
	/*public static ArrayList<Integer> findSpaces(String line)
	{
		ArrayList<Integer>indexes = new ArrayList<Integer>();
		for (int i = 0; i < line.length(); i++)
		{
			if (line.charAt(i) == ' ')
			{
				indexes.add(i);
			}
		}
		for (int i = 0 ;i < indexes.size(); i++)
		{
			System.out.print(indexes.get(i) + ", ");
		}
		System.out.println();
		return indexes;
	}*/
	public static String fixDipthongs(String s)
	{
		String[] dipthongs = {"ae","au","ei","eu","oe"};
		for (int i = 0; i < dipthongs.length; i++)
		{
			if (s.equals(dipthongs[i]))
			{
				s = "2";
			}
		}
		return s;
	}
	public static String fixUandV(String line, int i)
	{
		if (line.charAt(i) == 'u' || line.charAt(i) == 'v')
		{
			if (i != 0 && i != line.length()-1)
			{
				if (!isVowelOrNum(line.charAt(i+1)))
				{
					line = replaceString(line, "u", i);
				}
				else
				{
					if (!isVowelOrNum(line.charAt(i-1)))
					{
						if (isVowelOrNum(line.charAt(i-2)))
						{
							line = replaceString(line, "v", i);
						}
						else
						{
							line = replaceString(line, "u", i);
						}
					}
					else
					{
						line = replaceString(line, "v", i);
					}
				}
				
			}
		}
		return line;
	}
	public static String fixDigraphs(String s)
	{
		if (s.equals("ch") || s.equals("ph") || s.equals("th"))
		{
			s = "0";
		}
		/*if (s.equals("qu") || s.equals("gu") || s.equals("su"))
		{
			s = "0";
		}*/
		String[] stops = {"b","c","d","g","p","f","t"};
		String[] liquids = {"l","r"};
		for (int i = 0; i < stops.length; i++)
		{
			for (int j = 0; j < liquids.length; j++)
			{
				String quirk = stops[i]+liquids[j];
				if (s.equals(quirk))
				{
					s = "0";
				}
			}
		}
		return s;
	}
	public static String fixElisions(String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == '9')
			{
				if (isVowelNum(s.charAt(i-1)) && isVowelNum(s.charAt(i+1)))
				{
					s = replaceString(s,"",i-1);
				}
			}
		}
		return s;
	}
	public static String removeSpaces(String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i)=='9')
			{
				s = replaceString(s,"",i);
			}
		}
		return s;
	}
	public static String longByPosition(String s)
	{
		for (int i = 0; i < s.length()-2; i++)
		{
			if (s.charAt(i)=='1')
			{
				if (s.charAt(i+1)=='0' && s.charAt(i+2)=='0')
				{
					s = replaceString(s,"2",i);
				}
			}
		}
		return s;
	}
	public static boolean isVowel(char c)
	{
		char[] vowels = {'a','e','i','o','u','y'};
		for (int i = 0; i < vowels.length; i++)
		{
			if (vowels[i] == c)
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isVowelOrNum(char c)
	{
		char[] vowels = {'a','e','i','o','u','y','1','2','3'};
		for (int i = 0; i < vowels.length; i++)
		{
			if (vowels[i] == c)
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isVowelNum(char c)
	{
		char[] vowels = {'1','2','3'};
		for (int i = 0; i < vowels.length; i++)
		{
			if (vowels[i] == c)
			{
				return true;
			}
		}
		return false;
	}

}
