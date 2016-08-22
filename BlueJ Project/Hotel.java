import java.util.*;
import javax.swing.*;


/**
 * Hotel class represents hotel in the booking system.
 * Holds information relevant to the hotel for use in room bookings. 
 * 
 * @author Payam Ghoreyshi, Richard Kilgour, Jamie Smiles, Thomas Cook
 * @version 19/11/2015 Version 1.1 
 */
public class Hotel
{
    private String name;                                            //hotel name
    private ArrayList<BaseRoom> rooms = new ArrayList<BaseRoom>();  //array list of rooms
    private JOptionPane frame;
    
    /**
     * Constructor for Hotel with name and rooms array
     * 
     * @param name  hotels name
     * @param rooms rooms array
     */
    public Hotel(String name, ArrayList<BaseRoom> rooms)
    {
        this.rooms = rooms;
        this.name = name;
    }
    
    /**
     * Returns the room array position
     * 
     * @param  roomNum  room number
     * @return          room array position                          
     */
    public int getRoomPosition (int roomNum)
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            if (rooms.get(i).getNumber()==roomNum)
            {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Adds a new room to the array using roomNum, customerName, and customerNumGuest parameters.
     * Checks array position and adds room or fails and handles an error. 
     * 
     * @param roomPos               room position in array
     * @param customerName          customer name
     * @param customerNumGuest      number of guests
     * @param customerLengthOfStay  length of the customer stay
     */
    public void bookRoom (int roomPos, String customerName, int customerNumGuests, int customerLengthOfStay)
    {
        Customer customerToAdd = new Customer (customerName, customerNumGuests, customerLengthOfStay);
        rooms.get(roomPos).makeBooking(customerToAdd);
    }
    
    /**
     * Removes a customer from the room and cancels the booking
     * 
     * @param roomPos           room number
     */
    public void cancelBooking (int roomPos)
    {
        rooms.get(roomPos).endBooking();
    }
    
    /**
     * Returns the hotels rooms
     * 
     * @return list of hotel rooms
     */
    public ArrayList<BaseRoom> getRooms()
    {
        return rooms;
    }
    
    /**
     * Returns the number of available rooms
     * 
     * @return the amount of available rooms
     */
    public int getAvailableRooms ()
    {
        int availableRooms = 0;
        for (BaseRoom b : rooms)
        {
            if (!b.getBooked())
            {
                availableRooms += 1;
            }
        }
        return availableRooms;
    }
    
    /**
     * Returns the room at a given index
     * 
     * @return a room at a given index
     */
    public BaseRoom getRoom (int roomIndex)
    {
        return rooms.get(roomIndex);
    }
    

}
