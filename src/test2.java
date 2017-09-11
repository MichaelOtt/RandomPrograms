
public class test2 
{
	public static void main (String [] args)
	{
		int width = 175;
		int whichchar;
		char chararray [] = {'`','1','2','3','4','5','6','7','8','9','0','-','_','=','+','~','q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m','!','@','#','$','%','^','&','*','(',')','[',']','{','}','|',':',';','<','>','.','?','/','Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};
		while (1==1)
		{
		for (int i = 0; i < width; i++)
		{
				whichchar = (int)(Math.random() * chararray.length);

				System.out.print(chararray[whichchar]);

		}
		System.out.println("");
		}
	}
}
