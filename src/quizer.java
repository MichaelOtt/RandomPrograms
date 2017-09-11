import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;

public class quizer 
{
	public static void main (String [] args) throws IOException
	{
		String question = JOptionPane.showInputDialog(null,"make new quiz? yes or no");
		if (question.equals("yes"))
		{
			String name = JOptionPane.showInputDialog(null,"name your quiz");
			boolean done = false;
			while (!done)
			{
			try
			{
				BufferedReader in = new BufferedReader(new FileReader("Quizes/" + name + ".txt"));
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				name = JOptionPane.showInputDialog(null,"that quiz already exists");
			}
			catch (Exception e)
			{
				done = true;
			}
			}
			BufferedWriter out = new BufferedWriter(new FileWriter("Quizes/" + name + ".txt"));
			String a=JOptionPane.showInputDialog(null,"type in number of questions");
			int questionnum = Integer.parseInt(a);
			String side1 [] = new String [questionnum];
			String side2 [] = new String [questionnum];
			
			for (int i = 0; i < questionnum; i ++)
			{
				side1[i]=JOptionPane.showInputDialog(null,"type in side 1 for card " + (i + 1));
				side2[i]=JOptionPane.showInputDialog(null,"type in side 2 for card " + (i + 1));
			}
			for (int i = 0; i < questionnum; i++)
			{
				out.write(side1[i]);
				out.write(",,,");
				out.write(side2[i]);
				out.write(",,,");
			}
			out.close();
		}
		else
		{
			
		}
		boolean done = false;
		String name = JOptionPane.showInputDialog(null,"quiz name you will use");
		while (!done)
		{
			try
			{
				BufferedReader in = new BufferedReader(new FileReader("Quizes/" + name + ".txt"));
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				done = true;
			}
			catch (Exception e)
			{
				name = JOptionPane.showInputDialog(null,"that quiz doesn't exist");
			}
		}
		
		BufferedReader in = new BufferedReader(new FileReader("Quizes/" + name + ".txt"));
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		
		int lastused = 0;
		String s = "";
		Scanner sc = new Scanner (System.in);
		String fileline = in.readLine();
		String Vocab [] = fileline.split(",,,");
		int spot = 0;
		for (int i = 0; i < Vocab.length; i ++)
		{
			if (spot == 0)
			{
				System.out.println("------------------------");
			}
			spot++;
			if (spot == 2)
			{
				spot = 0;
			}
			System.out.println(Vocab[i]);
		}
		System.out.println("------------------------");
		int questionnum = Vocab.length / 2;
		String side1 [] = new String [questionnum];
		String side2 [] = new String [questionnum];
		int u = 0;
		int vocabplace = 0;
		int cardplace = 0;

		while (u < questionnum)
		{
			u++;
			side1[cardplace] = Vocab[vocabplace];
			vocabplace++;
			side2[cardplace] = Vocab[vocabplace];
			vocabplace++;
			cardplace++;
		}
		/*
		*/
		while (1 == 1)
		{
			int randomizequestion;
			int randomizeside = (int)(Math.random() * 2);
			do 
			{
			randomizequestion = (int)(Math.random() * (questionnum));
			} while (randomizequestion == lastused);
			lastused = randomizequestion;
			if (randomizeside == 0)
			{
				System.out.println("question: " + side1[randomizequestion]);
			}
			else
			{
				System.out.println("question: " + side2[randomizequestion]);
			}
				s = bufferRead.readLine();
				if (s.equals("edit"))
				{
					side1[lastused]=JOptionPane.showInputDialog(null,"type in side 1 for card " + (lastused + 1));
					side2[lastused]=JOptionPane.showInputDialog(null,"type in side 2 for card " + (lastused + 1));
					BufferedWriter out = new BufferedWriter(new FileWriter("Quizes/" + name + ".txt"));
					for (int i = 0; i < questionnum; i++)
					{
						out.write(side1[i]);
						out.write(",,,");
						out.write(side2[i]);
						out.write(",,,");
					}
					out.close();
				}
				else 
				{}
			if (randomizeside == 0)
			{
				System.out.println("answer: " + side2[randomizequestion]);
			}
			else
			{
				System.out.println("answer: " + side1[randomizequestion]);
			}
				s = bufferRead.readLine();
				if (s.equals("edit"))
				{
					side1[lastused]=JOptionPane.showInputDialog(null,"type in side 1 for card " + (lastused + 1));
					side2[lastused]=JOptionPane.showInputDialog(null,"type in side 2 for card " + (lastused + 1));
					BufferedWriter out = new BufferedWriter(new FileWriter("Quizes/" + name + ".txt"));
					for (int i = 0; i < questionnum; i++)
					{
						out.write(side1[i]);
						out.write(",,,");
						out.write(side2[i]);
						out.write(",,,");
					}
					out.close();
				}
				else 
				{}
		}
	}
	public static void editcard()
	{

	}
}
