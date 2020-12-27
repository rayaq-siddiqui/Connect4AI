package connectFour;

//Possible values:
// " o " (empty slot)
// "RED"
// "BLU"

public class Slot 
{
	public String value;
	
	public Slot()
	{
		value = " o ";
	}
	
	public boolean isOpen()
	{
		if (value.equals(" o "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int owner()
	{
		if (isOpen()== true)
		{
			return 0;
		}
		else if (value.equals("RED"))
		{
			return 1;
		}
		else if (value.equals("BLU"))
		{
			return 2;
		}
		else
		{
			System.out.println("ERROR in SLOT class.  Illegal value for slot obj.");
			return -1;
		}
	}
	
	public Slot clone()
	{
		Slot ns = new Slot();
		ns.value = value;
		return ns;
	}
}
