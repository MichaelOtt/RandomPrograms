
public class test1 
{
	public static void main (String [] args)
	{
		int width = 175;
		int place = 10;
		char chararray [] = {'`','1','2','3','4','5','6','7','8','9','0','-','_','=','+','~','q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m','!','@','#','$','%','^','&','*','(',')','[',']','{','}','|',':',';','<','>','.','?','/','Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};
		while (1==1)
		{
		place = (int) (Math.random() * (width + 1));
		int whichchar = (int)(Math.random() * chararray.length);
		for (int i = 0; i < width; i++)
		{
			if (i == place)
			{
				System.out.print(chararray[whichchar]);
			}
			else
			{
				System.out.print(" ");
			}
		}
		System.out.println("");
		}
	}
}
