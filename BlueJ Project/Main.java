import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * Main class is the entry point for this program
 * It instantiates a new hotel from information read from a local text file.
 * It also instantiates the GUI
 * 
 * @author Payam Ghoreyshi, Richard Kilgour, Jamie Smiles, Thomas Cook
 * @version 19/11/2015 Version 1.1 
 */

public class Main
{
    //GUI
    private static MainMenu menu;
    //The hotel
    private static Hotel hotel;
    //All rooms raw text data
    private static ArrayList<String> roomData;
    //List of hotel rooms
    private static ArrayList<BaseRoom> hotelRooms;
    //Text handler
    private static TextHandler textIO;

    
    /**
     * Initialises text handling class to read information from the hotel rooms file.
     * Reads information from file and breaks information into usable strings. 
     * Parses room type string information to add a new room.
     * Generates a new GUI instance and hotel class.
     * 
     * @param command line arguments
     */
    public static void main(String[] args)
    {        
        //Get the hotel data from the text file
        getHotelData();
        
        //Populate the hotel rooms list 
        populateHotel();               
              
        //Init hotel
        initHotel();
        
        //Init GUI
        initGUI();    
    }
    
    /**
     * Writes the updated hotel data to a text file then quits the program
     */
    public static void quit() throws FileNotFoundException{
        try
        {
            textIO.writeToFile(hotel.getRooms());
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        System.exit(0);
    }
    
    /**
     * Gets the hotel data from the text file
     * Catches a file not found exception and display an error then exits the program
     */
    private static void getHotelData()
    {
        textIO = new TextHandler();
        try
        {
            roomData = textIO.readFromFile();
        }
        catch (FileNotFoundException e)
        {
           JOptionPane.showMessageDialog(menu, "Hotel Data not found.", "Error", JOptionPane.ERROR_MESSAGE);
           System.exit(0);
        }
    }
    
    
    /**
     * Reads information from file and breaks information into usable strings. 
     * Parses room type string information to add a new room.
     */
    private static void populateHotel ()
    {
        //Create room array
        hotelRooms = new ArrayList<BaseRoom> ();
        
        //Loop for roomData.size times and add rooms to hotel rooms list
        for (int i = 0; i < roomData.size(); i++)
        {

            //Split this lines string into component parts
            String[] result = roomData.get(i).split(" ");
            
            //Hardcoded data positions
            //Customer
            Customer customer;
            if (Boolean.parseBoolean(result[3])==true){
                customer = new Customer (result[6] + " " + result[7], Integer.parseInt(result[4]), Integer.parseInt(result[5]));
            }
            else{
                customer = null;
            }
            
            //Get the room number
            int roomNumber = Integer.parseInt(result[1]);
            
            //Get if the room is ensuite
            boolean isEnSuite = Boolean.parseBoolean(result[2]);
            
            //If room is standard, make standard room
            if (result[0].contains("Standard"))
            {
                StandardRoom standard = new StandardRoom (roomNumber, isEnSuite, customer);
                hotelRooms.add (standard);
            }
            //Else make family room
            else
            {
                FamilyRoom family = new FamilyRoom (roomNumber, isEnSuite, customer);
                hotelRooms.add (family);
            } 
        }
    }
    
    /**
     * Creates a new hotel
     */
    private static void initHotel()
    {
        hotel = new Hotel ("Hotel Wanderlust", hotelRooms);
    }
    
    /**
     * Initializes the GUI
     */
    private static void initGUI()
    {
        menu = new MainMenu();
    }
    
    public static Hotel getHotel ()
    {
        return hotel;
    }
    
}