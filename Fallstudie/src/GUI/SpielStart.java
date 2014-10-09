package GUI;

public class SpielStart
{

	public static void main(String[] args)
	{
		
		
		GUI win = new GUI("Hanswurst");				
		int[] tmpA = {3,3,4};
		boolean[][] tmpB = {{false,false,false,false,false,false,false,false},
				{false,true,false,true,false,true,false,true},
				{true,true,true,true,true,true,true,true}};
		win.wechselSpieler("Hans", 40000, 10, tmpA, 100, 100, 50, 2, 2, 3, tmpB);
	}

}
