import java.util.*;

/**
 * Customer class for Hotel Booking project. 
 * Stores main account name, number of guests, and room number. 
 * 
 * @author Jamie Smiles 
 * @version 04/11/2015 Version 1.1
 */

public class Customer
{    
    private String name;    //String for main customer on account
    private int numGuests;  //Integer for total number of guests under booking
    private int lengthOfStay;

    /**
     * Constructor for Customer class with name and number of guest parameters.
     * 
     * @param name         customer name
     * @param numGuests    number of guests
     * @param lenghtOfStay the length of the customers stay
     */
    public Customer(String name, int numGuests, int lengthOfStay)
    {
        this.name = name;
        this.numGuests = numGuests;
        this.lengthOfStay = lengthOfStay;
    }
    
    /**
     * Returns the customer name
     * 
     * @return  customer name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the number of guests
     * 
     * @return  number of guests
     */
    public int getNumGuests()
    {
        return numGuests;
    }
    
    /**
     * Returns the length of stay
     * 
     * @return  length of stay
     */
    public int getLengthOfStay()
    {
        return lengthOfStay;
    }
    
     
    /**
     * Returns the customer data
     * 
     * @return customer data
     */
    public String printData()
    {
        String customerData = "Customer Name: " + name + ", Total Guests: " + numGuests;
        return customerData;
    }    
}
