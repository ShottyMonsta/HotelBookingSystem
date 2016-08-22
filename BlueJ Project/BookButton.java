import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * Class for "Book" and "Cancel" buttons. Includes assocaited room information.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BookButton extends JButton implements ActionListener
{
    // instance variables - replace the example below with your own
    private int roomPos;
    private boolean isBooked;//shows if the room associated to button is booked.
    private MainMenu parent;

   /**
    * Constructor for BookButton class.
    * 
    * @param title button text
    * @param roomPos position of the room associated to the button in the hotel's database.
    * @param isBooked true if the room associated to the button is booked.
    * @param parent a reference to the main menu
    */
    public BookButton(String title, int roomPos, boolean isBooked, MainMenu parent)
    {
        super(title);
        this.roomPos=roomPos;
        this.isBooked=isBooked;
        this.addActionListener(this);
        this.parent=parent;
    }
    
    /**
    * Returns whether or not the room associated to the button is booked.
    * 
    * @return boolean value that is true if the room associated to the button is booked.
    */
    public boolean getBooked(){
        return isBooked;
    }
    
    /**
    * Returns position of the room associated to the button in the hotel's database.
    * 
    * @return position of the room associated to the button in the hotel's database.
    */
    public int getRoomPos(){
        return roomPos;
    }
    
    /**
    * actionperformed function
    * 
    */
    public void actionPerformed (ActionEvent ae){
        parent.callBookButtonAction(isBooked, roomPos);
    }
}
