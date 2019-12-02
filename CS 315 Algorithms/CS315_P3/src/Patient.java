import java.text.ParseException;
import java.util.StringTokenizer;


public class Patient {

	int patientID;
	String firstName;
	String lastName;
	int age;
	boolean pregnant;
	int vacineBatchNumber;
	
	
	public Patient() {
		this.patientID = -1;
		this.firstName = "";
		this.lastName = "";
		this.age = -1;
		this.pregnant = false;
		vacineBatchNumber=-1;
	}
	
	public Patient(String s) throws ParseException {
		this.vacineBatchNumber=-1;
		parsePatient(s);
	}
	
	public int getID()
	{
		return patientID;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public int getBatch()
	{
		return vacineBatchNumber; 
	}
	
	public boolean isPregnant()
	{
		return pregnant; 
	}
	
	public void setVacineBatchNumber(int batch) 
	{
		this.vacineBatchNumber = batch;
	}
	
	public String toString()
	{
		return "" + patientID + "," + lastName + "," + firstName + "," + age + "," + pregnant + "," + vacineBatchNumber;
	}
	
	public void parsePatient(String s) throws ParseException 
	{

		//Split on comma
		StringTokenizer stok = new StringTokenizer(s,",");
		if (stok.countTokens() != 5)
			throw new ParseException("Invalid Patient record: " + s, 0);


		//Parse Elements
		this.patientID = Integer.parseInt(stok.nextToken().trim());
		this.lastName = stok.nextToken().trim();
		this.firstName = stok.nextToken().trim();		
		this.age = Integer.parseInt(stok.nextToken().trim());	
		this.pregnant = Boolean.parseBoolean(stok.nextToken().trim());
	}
	
	public static void main(String [] args)
	{
	
		Patient p;
		try {
			p = new Patient("1138,ross,bob,23,false");
			System.out.println(p);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
