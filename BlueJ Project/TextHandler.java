import java.util.*;
import java.io.*;

/**
 * TextHandler manages the text IO for the application.
 * Implements scanner from a local file to manipulate information.
 * 
 * @author Thomas Cook
 * @version 19/11/2015 Version 1.1
 */

public class TextHandler
{
    private File file;      //file object
    private Scanner input;  //scanner object 
    private PrintWriter output; //PrintWriter object

    private void openStream() throws FileNotFoundException
    {
        input = new Scanner(file);
    }
    
    private void makeLink() throws FileNotFoundException
    {
        output = new PrintWriter(file);
    } 

    /**
     * Opens connection to the hotel data file and populates a list with the data
     * Returns an array list of strings for the hotel information. 
     * 
     * @return  hotelData   hotel information from file
     * @throws read error
     */
    public ArrayList<String> readFromFile() throws FileNotFoundException
    {
        file = new File("hotelData.txt"); //hotel data file IO creation
        
        try
        {
            openStream();
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        
        ArrayList<String> hotelData = new ArrayList<String>();
        
        while (input.hasNext())
        {
            hotelData.add(input.nextLine());
        }
        
        closeStream();
        return hotelData;      
    }
    
    /**
     * Writes hotel data to output text file.
     * 
     * @throws write error
     */
    public void writeToFile(ArrayList<BaseRoom> rooms) throws FileNotFoundException
    {
        file = new File("hotelData1.txt"); //hotel data file IO creation
        
        try
        {
            makeLink();
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        
       //Room Type, Room Number, enSuite, Occupied, CurrentOccupancy, Length of stay, name
        for (int i=0;i<rooms.size();i++){
            output.print(rooms.get(i).getType()+" "+rooms.get(i).getNumber()+" "+rooms.get(i).getEnSuite()+" "+rooms.get(i).getBooked()+" "+rooms.get(i).getCustomer().getNumGuests()+" "+rooms.get(i).getCustomer().getLengthOfStay()+" "+rooms.get(i).getCustomer().getName());
            output.println();
        }
        output.close();    
           
    }

    private void closeStream()
    {       
        try
        {
            input.close();
        }
        catch ( NullPointerException e)
        {
            System.err.println(" Scanner not assigned.");   
        }
    }
 
    private void closeLink()
    {       
        try
        {
            output.close();
        }
        catch ( NullPointerException e)
        {
            //
        }
    }
}
