import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.Line2D;

/**
 * This class is used for both Booking and Cancel windows because they are very similar.
 * The boolean variable "isBooking" denotes which type the current instance is.
 * 
 * @author (Thomas Cook, Payam Ghoreshi) 
 * @version (a version number or a date)
 */
public class BookingMenu extends SubWindow implements ActionListener
{
    //The room in questions index in the room array
    private int roomIndex;
    
    //Buttons
    private JButton confirmCancelButton;
    
    //Labels
    private Component roomDetailsTitle;
    private Component roomDetailsLabel;
    private Component customerDetailsLabel;
    private Component customerFirstNameLabel;
    private Component customerLastNameLabel;
    private Component customerGroupSizeLabel;
    private Component customerLengthStayLabel;
    
    //Fixed labels (for cancel window)
    private JLabel customerFirstNameFixed;
    private JLabel customerLastNameFixed;
    private JLabel customerGroupSizeFixed;
    private JLabel customerLengthStayFixed;
    
    //Fields (for booking window)
    private JTextField customerFirstNameField;
    private JTextField customerLastNameField;
    private JTextField customerGroupSizeField;
    private JTextField customerLengthStayField;
    
    //Layout and windows
    private SpringLayout layout;
    private JPanel pane;
    
    //Booking or cancelling?
    private Boolean isBooking;
    
    /**
     * Constructor
     * 
     * @param type the type of window
     * @param frameID the frames ID
     * @param roomIndex the rooms index in the hotel rooms array
     * @param parentFrame the windows parent frame
     * 
     * @return  enSuite status
     */
    public BookingMenu (String type, int frameID, int roomIndex, MainMenu parentFrame)
    {
        super("Hotel Wanderlust Chain - " +type, parentFrame, frameID);//booking window
        
        this.roomIndex = roomIndex;
        
        if (type.contains("Make"))
        {
          isBooking = true; 
        }
        else
        {
          isBooking = false;
        }     
        
        makeLayout();
        
        makeButtons();
        makeLabels();
        
        addButtons();
        addLabels();
        
        if (isBooking)
        {
          makeFields();
          addFields();
        }
        else
        {
          makeTextAreas();
          addFixed();
        } 
               
        
        positionGUI();
        
        repaint();
        
        showFrame();
    }

    private void makeLayout()
    {
        pane = new JPanel();
        layout = new SpringLayout();
        pane.setLayout(layout);
    }
    
    private void makeButtons()
    {
        if (isBooking)
        {
            confirmCancelButton = new JButton ("Confirm Booking");
        }
        else
        {
            confirmCancelButton = new JButton ("Cancel Booking");
        }
        
        confirmCancelButton.addActionListener(this);
    }
    
    private void makeLabels()
    {
        customerDetailsLabel = new JLabel ("Customer Details");
        roomDetailsTitle = new JLabel ("Room No.   Room Type   En-Suite   Customer Name   No. Guests   Length of Stay");
        roomDetailsLabel = new JLabel (Main.getHotel().getRoom(roomIndex).getRoomDetails());
        customerFirstNameLabel = new JLabel ("Customer First Name:   ");
        customerLastNameLabel = new JLabel ("Customer Last Name:   ");
        customerGroupSizeLabel = new JLabel ("Customer Group Size:   ");
        customerLengthStayLabel = new JLabel ("Length of Stay:   ");
    }
    
    /**
     * Paints decorating lines
     */
    @Override public void paint(Graphics g)
    {
        //Get the current size of this component
        Dimension left = new Dimension(relativeHorizontal(5), relativeVertical(14));
        Dimension right = new Dimension(relativeHorizontal(95), relativeVertical(14));
        //draw in black
        g.setColor(Color.BLACK);
        //draw a centered horizontal line
        g.drawLine(left.width, left.height, right.width, right.height);
    }
 
    private void makeTextAreas()
    {     
        String[] customerName = Main.getHotel().getRoom(roomIndex).getCustomer().getName().split(" ");
        String firstName = customerName[0];
        String lastName = customerName[1];
        customerFirstNameFixed = new JLabel (firstName);
        customerLastNameFixed = new JLabel (lastName);
        customerGroupSizeFixed = new JLabel (Integer.toString( Main.getHotel().getRoom(roomIndex).getCustomer().getNumGuests()) );
        customerLengthStayFixed = new JLabel (Integer.toString (Main.getHotel().getRoom(roomIndex).getCustomer().getLengthOfStay()) );
    }
 
    private void makeFields()
    {
        customerFirstNameField = new JTextField (20);
        customerLastNameField = new JTextField (20);
        customerGroupSizeField = new JTextField (20);
        customerLengthStayField = new JTextField (20);
    }

    private void addButtons()
    {
        pane.add(confirmCancelButton);
    }      

    private void addLabels()
    {
        pane.add(customerDetailsLabel);
        pane.add(roomDetailsTitle);
        pane.add(roomDetailsLabel);
        pane.add(customerFirstNameLabel);
        pane.add(customerLastNameLabel);
        pane.add(customerGroupSizeLabel);
        pane.add(customerLengthStayLabel);
    }      

    private void addFields()
    {
        pane.add(customerFirstNameField);
        pane.add(customerLastNameField);
        pane.add(customerGroupSizeField);
        pane.add(customerLengthStayField);
    }     

    private void addFixed()
    {
        pane.add(customerFirstNameFixed);
        pane.add(customerLastNameFixed);
        pane.add(customerGroupSizeFixed);
        pane.add(customerLengthStayFixed);
    } 

    /**
     * Performs an action when an event is recieved from an action listener
     * 
     * @param ae the event that was performed
     */
    public void actionPerformed (ActionEvent ae)
    {
        //Check if fields are valid
        if (checkFields()){
            //If book was pressed
            if (isBooking)
            {            
                //Check if fields are valid
                Main.getHotel().bookRoom(roomIndex, customerFirstNameField.getText() +" " +customerLastNameField.getText(), Integer.parseInt(customerGroupSizeField.getText()), Integer.parseInt(customerLengthStayField.getText()));
                //Show success pop up
                JOptionPane.showMessageDialog(this, "Room successfully booked.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            //Else if cancel was pressed
            else
            {
                //Make a "cancel" booking
                Main.getHotel().cancelBooking(roomIndex);                
                //JFrame successFrame = new JFrame ();
                JOptionPane.showMessageDialog(this, "Booking successfully cancelled.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            //close!
            callParentRefresh();
            closeSelf();
        }
    }
    

    private boolean containsDigits (String string)
    {
        StringBuilder stringBuilder = new StringBuilder();
        boolean digitFound = false;
        for (char c : string.toCharArray())
        {
            if (Character.isDigit(c))
            {
                return true;
            }
        }
        return false;        
    }
    

    private boolean isEmpty (String string)
    {
        if (string == null || string.isEmpty())
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    private boolean checkFields ()
    {
        //If the window is a cancel window, it will return true as cancel windows don't need any error checking
        if (!isBooking){
            return true;
        }
        //Check if fields are of correct type
        else if ( isEmpty(customerFirstNameField.getText()) || containsDigits(customerFirstNameField.getText()) )
        {
            //Make error window
            JOptionPane.showMessageDialog(this, "You cannot enter digits or leave the field blank (customer first name)", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ( isEmpty(customerLastNameField.getText()) || containsDigits(customerLastNameField.getText()) )
        {
            //Make error window
            JOptionPane.showMessageDialog(this, "You cannot enter digits or leave the field blank (customer last name)", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ( isEmpty(customerGroupSizeField.getText()) || !containsDigits(customerGroupSizeField.getText()) )
        {
            //Make error window
            JOptionPane.showMessageDialog(this, "You cannot enter letters or leave the field blank (customer group size)", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if ( Integer.parseInt(customerGroupSizeField.getText()) > Main.getHotel().getRoom(roomIndex).GetMaxOccupancy())
        {
              //Make error window
            JOptionPane.showMessageDialog(this, "You cannot exceed the maximum occupancy of a room...", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
        {
            return true;
        }
    }

    private void positionGUI()
    {         
        final int confirmCancelButtonHorizontal = 5;
        final int confirmCancelButtonVertical = 5;
        
        final int roomDetailsTitleHorizontal = 5;
        final int roomDetailsTitleVertical = 3;
        
        final int roomDetailsLabelHorizontal = 5;
        final int roomDetailsLabelVertical = 8;
        
        final int customerDetailsLabelHorizontal = 40;
        final int customerDetailsLabelVertical = 25;
        
        final int customerFirstNameLabelHorizontal = 12;
        final int customerFirstNameLabelVertical = 35;
        
        final int customerLastNameLabelHorizontal = 12;
        final int customerLastNameLabelVertical = 45;
        
        final int customerGroupSizeLabelHorizontal = 12;
        final int customerGroupSizeLabelVertical = 55;
        
        final int customerLengthStayLabelHorizontal = 12;
        final int customerLengthStayLabelVertical = 65;
            
            final int customerFirstNameFieldHorizontal = 40;
            final int customerFirstNameFieldVertical = 35;
            
            final int customerLastNameFieldHorizontal = 40;
            final int customerLastNameFieldVertical = 45;
            
            final int customerGroupSizeFieldHorizontal = 40;
            final int customerGroupSizeFieldVertical = 55;
            
            final int customerLengthStayFieldHorizontal = 40;
            final int customerLengthStayFieldVertical = 65;
            
            final int customerFirstNameFixedHorizontal = 40;
            final int customerFirstNameFixedVertical = 35;
            
            final int customerLastNameFixedHorizontal = 40;
            final int customerLastNameFixedVertical = 45;
            
            final int customerGroupSizeFixedHorizontal = 40;
            final int customerGroupSizeFixedVertical = 55;
            
            final int customerLengthStayFixedHorizontal = 40;
            final int customerLengthStayFixedVertical = 65;
            
        
        //Confirm/Cancel button
        layout.putConstraint(SpringLayout.EAST, confirmCancelButton, -relativeHorizontal(confirmCancelButtonHorizontal), SpringLayout.EAST, pane);
        layout.putConstraint(SpringLayout.SOUTH, confirmCancelButton, -relativeVertical(confirmCancelButtonVertical), SpringLayout.SOUTH, pane);
        
         //roomDetailsTitle label
        layout.putConstraint(SpringLayout.WEST, roomDetailsTitle, relativeHorizontal(roomDetailsTitleHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, roomDetailsTitle, relativeVertical(roomDetailsTitleVertical), SpringLayout.NORTH, pane);
        
        //roomDetails label
        layout.putConstraint(SpringLayout.WEST, roomDetailsLabel, relativeHorizontal(roomDetailsLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, roomDetailsLabel, relativeVertical(roomDetailsLabelVertical), SpringLayout.NORTH, pane);
        
        //customer first name label
        layout.putConstraint(SpringLayout.WEST, customerDetailsLabel, relativeHorizontal(customerDetailsLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, customerDetailsLabel, relativeVertical(customerDetailsLabelVertical), SpringLayout.NORTH, pane);
        
        //customer first name label
        layout.putConstraint(SpringLayout.WEST, customerFirstNameLabel, relativeHorizontal(customerFirstNameLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, customerFirstNameLabel, relativeVertical(customerFirstNameLabelVertical), SpringLayout.NORTH, pane);
        
        //customer last name label
        layout.putConstraint(SpringLayout.WEST, customerLastNameLabel, relativeHorizontal(customerLastNameLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, customerLastNameLabel, relativeVertical(customerLastNameLabelVertical), SpringLayout.NORTH, pane);
        
        //Customer group size label
        layout.putConstraint(SpringLayout.WEST, customerGroupSizeLabel, relativeHorizontal(customerGroupSizeLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, customerGroupSizeLabel, relativeVertical(customerGroupSizeLabelVertical), SpringLayout.NORTH, pane);
        
        //Customer length of stay label
        layout.putConstraint(SpringLayout.WEST, customerLengthStayLabel, relativeHorizontal(customerLengthStayLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, customerLengthStayLabel, relativeVertical(customerLengthStayLabelVertical), SpringLayout.NORTH, pane);
        
        if (isBooking)
        {
            //Customer first name field
            layout.putConstraint(SpringLayout.WEST, customerFirstNameField, relativeHorizontal(customerFirstNameFieldHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerFirstNameField, relativeVertical(customerFirstNameFieldVertical), SpringLayout.NORTH, pane);
            
            //Customer last name field
            layout.putConstraint(SpringLayout.WEST, customerLastNameField, relativeHorizontal(customerLastNameFieldHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerLastNameField, relativeVertical(customerLastNameFieldVertical), SpringLayout.NORTH, pane);
            
            //Customer group size field
            layout.putConstraint(SpringLayout.WEST, customerGroupSizeField, relativeHorizontal(customerGroupSizeFieldHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerGroupSizeField, relativeVertical(customerGroupSizeFieldVertical), SpringLayout.NORTH, pane);
            
            //Customer length stay field
            layout.putConstraint(SpringLayout.WEST, customerLengthStayField, relativeHorizontal(customerLengthStayFieldHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerLengthStayField, relativeVertical(customerLengthStayFieldVertical), SpringLayout.NORTH, pane);  
        }
        else
        {
            //Customer first name field
            layout.putConstraint(SpringLayout.WEST, customerFirstNameFixed, relativeHorizontal(customerFirstNameFixedHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerFirstNameFixed, relativeVertical(customerFirstNameFixedVertical), SpringLayout.NORTH, pane);
            
            //Customer last name field
            layout.putConstraint(SpringLayout.WEST, customerLastNameFixed, relativeHorizontal(customerLastNameFixedHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerLastNameFixed, relativeVertical(customerLastNameFixedVertical), SpringLayout.NORTH, pane);
            
            //Customer group size field
            layout.putConstraint(SpringLayout.WEST, customerGroupSizeFixed, relativeHorizontal(customerGroupSizeFixedHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerGroupSizeFixed, relativeVertical(customerGroupSizeFixedVertical), SpringLayout.NORTH, pane);    
            
            //Customer length stay field
            layout.putConstraint(SpringLayout.WEST, customerLengthStayFixed, relativeHorizontal(customerLengthStayFixedHorizontal), SpringLayout.WEST, pane);
            layout.putConstraint(SpringLayout.NORTH, customerLengthStayFixed, relativeVertical(customerLengthStayFixedVertical), SpringLayout.NORTH, pane); 
        }

            
    } 
        
    private void showFrame()
    {
      //Centre the frame
      setLocationRelativeTo(null);      
      //Add pane
      add(pane);      
      //Set size
      setSize(500, 400);
      //Show
      setVisible(true);
      setResizable(false);
    }   

    private int relativeHorizontal (int percent)
    {
        return (500 / 100) * percent;
    }

    private int relativeVertical (int percent)
    {
        return (400 / 100) * percent;
    }
}
