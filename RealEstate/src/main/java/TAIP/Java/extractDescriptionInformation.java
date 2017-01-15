package TAIP.Java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class extractDescriptionInformation {

	Boolean hasYard=false;
	Boolean hasPool=false;
	Boolean hasGarage=false;
	Boolean hasFireplace=false;
	int numberOfFloors=1;
	double numberOfRooms=0;
	int numberOfSquareFoots=0;
	List<String> rooms= Arrays.asList("kitchen", "bedroom", "bathroom","living","master suite");
	
	public List<String> readDescriptions(String filename) throws IOException
	{
		List<String> descriptions=new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    
		    String line = br.readLine();

		    while (line != null) {
		        descriptions.add(line);
		        line = br.readLine();
		    }
		 
		} finally {
		    br.close();
		}
		return descriptions;
	 
	}
	public void getNumberOfRooms(String s)
	{
		for(String room:rooms)
		{
		if(s.contains(room))
		{
		
			char c=s.charAt(s.indexOf(room)-2);
			if(Character.isDigit(c))
			{
				int k=3;
				while(Character.isDigit(s.charAt(s.indexOf(room)-k))||s.charAt(s.indexOf(room)-k)=='.') k++;
				String rooms=s.substring(s.indexOf(room)-(--k),s.indexOf(room)-1);
			    numberOfRooms+=Double.parseDouble(rooms);
			}
			else
				numberOfRooms+=1;
			//s=s.substring(0,s.indexOf(room))+s.substring(s.indexOf(room)+room.length());
		}}
		
	}
	
	public void getNumberOfFloors(String s)
	{
		
		if(s.contains("floors"))
		{
			if(Character.isDigit(s.charAt(s.indexOf("floors")-2)))
			numberOfFloors+=Character.getNumericValue(s.charAt(s.indexOf("floors")-2));
			
		}
		
	}
	
	public void getNumberOfSquareFoots(String s)
	{
		
		if(s.contains("square foot"))
		{
			if(Character.isDigit(s.charAt(s.indexOf("square foot")-2)))
				{int k=3;
				while(Character.isDigit(s.charAt(s.indexOf("square foot")-k))) k++;
				String squarefoots=s.substring(s.indexOf("square foot")-(--k),s.indexOf("square foot")-1);
			    numberOfSquareFoots+=Integer.parseInt(squarefoots);
				}
			
		}
		
	}
	
	public void getInfo(String s)
	{
		if(s.contains("yard"))hasYard=true;
		if(s.contains("pool"))hasPool=true;
		if(s.contains("fireplace"))hasFireplace=true;	
	}
	
	@Override
	public String toString()
	{
		return "hasYard:"+hasYard+" hasPool:"+hasPool+" hasGarage:"+hasGarage+" hasFireplace:"+hasFireplace+" numberOfRooms:"+numberOfRooms+" numberOfFloors:" +numberOfFloors+" numberOfSquareFoots:"+numberOfSquareFoots;
	}
	
	public static void main(String args[]) throws IOException
	{
		extractDescriptionInformation extract=new extractDescriptionInformation();
		List<String> descriptions=extract.readDescriptions("D:\\descriptions.txt");
		int i=2;
		extract.getNumberOfRooms(descriptions.get(i));
		extract.getNumberOfFloors(descriptions.get(i));
		extract.getNumberOfSquareFoots(descriptions.get(i));
		extract.getInfo(descriptions.get(i));
		System.out.println(extract);
	}
	
}