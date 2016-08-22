import java.util.*;
import javax.swing.*;

/**
 * Abstract parent object for inheritence with StandardRoom and FamilyRoom classes.
 * Represents common parameters and functionality across room types.
 * 
 * @author Richard Kilgour
 * @version 19/11/2015 Version 1.1 
 */

public abstract class BaseRoom
{
    private boolean booked;       //Is the room booked?
    private boolean enSuite;      //Is the room ensuite?    
    private int number;           //Room number    
    private Customer customer;    //Reference to the customer in this room
    private JOptionPane frame;
    
    
    /**
     * Default constructor for BaseRoom class
     */
    public BaseRoom()
    {
        booked = false;
        enSuite = true;
        number = 0;    
        customer = null;
    }
    
    /**
     * Constructor for Base Room class with booked, enSuite, number and customer parameters
     * 
     * @param enSuite   boolean if room is enSuite
     * @param number    hotel room number
     * @param customer  customer
     */
    public BaseRoom(int number, boolean enSuite, Customer customer)
    {
        booked = false;
        this.enSuite = enSuite;
        this.number = number;         
        if (customer != null)
        {
            this.customer = customer;
        }
        else
        {
            this.customer = null;
        }
    }
    
    /**
     * returns the room type
     * 
     * @return the type of room
     */
    public abstract String getType ();
    
    /**
     * Checks to see if there is a customer
     * Returns null or customer booked into room
     * 
     * @return  customer
     */
    public Customer getCustomer()
    {
        if (customer != null)
        {
            return customer;
        }
        else
        {
            Customer emptyCustomer = new Customer("No Person", 0, 0);
            return emptyCustomer;
        }
    }
    
    /**
     * Checks to see if there is a customer
     * Returns true if a customer exists else false
     * 
     * @return  true if the room has a customer
     */
    public Boolean hasCustomer()
    {
        if (customer == null || customer.getName().contains("No Person"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Checks to see if a room is booked
     * Adds a customer booking to the room or fails and returns an error 
     */
    public void makeBooking (Customer customer)
    {
        if (!booked)
        {
            this.customer = customer;
            booked = true;
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "Room already booked");
        }        
    }
    
    
    /**
     * Checks to see if there is a booking
     * Cancels the system booking or fails and returns an error
     */
    public void endBooking ()
    {
        if (booked)
        {
            booked = false;
            customer = new Customer ("No Person", 0, 0); 
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "Room wasn't booked");
        }        
    }
    
    /**
     * Returns the booked property
     * 
     * @return  room booking  
     */
    public boolean getBooked()
    {
        return booked;
    }
    
    /**
     * Returns room number
     * 
     * @return  room number
     */
    public int getNumber()
    {
        return number;
    }
    
    /**
     * Returns whether room is enSuite or not
     * 
     * @return  enSuite status
     */
    public boolean getEnSuite()
    {
        return enSuite;
    }
    
    /**
     * Abstract method for getting room details information about the rooms state
     */
    public abstract String getRoomDetails();
    
     /**
     * Abstract method for getting the max occupancy of the room
     */
    public abstract int GetMaxOccupancy();
    
    /**
     * Prints the room booking information to the output terminal
     */
    public void print()
    {
        System.out.println(getRoomDetails());
    }
}