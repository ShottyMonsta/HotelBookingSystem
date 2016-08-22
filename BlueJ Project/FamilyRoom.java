/**
 * Concrete child object that inherits BaseRoom abstract class.
 * Represents a family sized room in the booking system.
 * Stores details and functionality of a Family Room. 
 * 
 * @author Thomas Cook
 * @version 19/11/2015 Version 1.1 
 */

public class FamilyRoom extends BaseRoom
{
    private int maxOccupancy; //Integer for maximum family room occupancy 
    
    /**
     * Default constructor for FamilyRoom class
     */
    public FamilyRoom ()
    {
        super ();
        maxOccupancy = 4;
    }
    
    /**
     * Constructor for FamilyRoom class with roomNumber, enSuite and customer arguments called
     * from abstract BaseRoom and maxOccupancy argument 
     * 
     * @param number        hotel room number
     * @param enSuite       boolean for enSuite
     * @param customer      customer
     */
    public FamilyRoom (int number, boolean enSuite, Customer customer)
    {
        super (number, enSuite, customer);
        maxOccupancy = 4;
    }
    
    /**
     * Returns the maximum occupancy
     * 
     * @return  maximum occupancy
     */
    public int GetMaxOccupancy ()
    {
        return maxOccupancy;
    }    

    /**
     * Returns the room type
     * 
     * @return room type
     */
    public String getType()
    {
        return "Family";
    }
    
    /**
     * Returns the room details
     * 
     * @return room details as String
     */
     public String getRoomDetails()
    {
        String roomDetails = "           " +getNumber() + "           " +getType() +"             ";
        if (getEnSuite())
        {
            roomDetails += "Yes";
        }
        else
        {
            roomDetails += "No";
        }
        //"Room No.|   Room Type|   En-Suite|   Customer Name|   No. Guests|   Length of Stay");
        roomDetails += "";
        if (getCustomer().getName().length() < 16)
        {
            for (int i = 0; i < (16 - getCustomer().getName().length()); i++)
            {
                roomDetails += "  ";
            }
        }
        roomDetails += getCustomer().getName() +"                     " +getCustomer().getNumGuests() +"                          " +getCustomer().getLengthOfStay();
        return roomDetails;
    }
}