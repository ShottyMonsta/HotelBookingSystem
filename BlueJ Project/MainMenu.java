import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


/**
 * This class is used for displaying the main menu of the hotel booking system
 * 
 * @author (Thomas Cook, Payam Ghoreshi) 
 * @version (a version number or a date)
 */
public class MainMenu extends BaseWindow
{
    //Error frame
    private JOptionPane errorFrame;
    //Buttons
    private JButton roomsButton;
    private JButton bookingsButton;
    private JButton searchButton;
    private ArrayList<JButton> cancelBookingButtons;
    
    //Labels
    private Component scrollAreaTitles;
    private Component searchLabel;
    private JLabel availableRoomsLabel;
    
    //Fields
    private JTextField searchField;
    
    //Scroll area
    private JScrollPane scrollArea;
    private JPanel area;
    
    //Layout and windows
    private SpringLayout layout;
    private JPanel pane;
    
    //Room containers
    private ArrayList<Container> roomContainers;
    
    //Subwindow System
    private static final int MAX_WINDOWS = 5;
    private boolean[] frameIsEnabled;
    private ArrayList<SubWindow> subWindows;
    
    /**
    * Default constructor creates the GUI
    */
    public MainMenu ()
    {
      super("Hotel Wanderlust Chain - Main Menu");
      
      frameIsEnabled = new boolean[MAX_WINDOWS];
      subWindows = new ArrayList<SubWindow>(MAX_WINDOWS);
      for (int i=0;i<MAX_WINDOWS;i++){
          subWindows.add(null);
      }
      
      makeLayout();
      
      makeButtons();
      makeLabels();
      makeFields();
      scrollArea=makeScrollArea(this, -1, "DUMMY");//Main Menu Frame ID is -1

      
      addButtons();
      addLabels();
      addFields();
      addScrollArea();

      
      positionGUI();
      
      showFrame();
    }
    
    /**
    * Refreshes all windows, including sub windows
    */
    public void refreshAll()
    {
        refreshMainMenu();
        for (int i=0;i<MAX_WINDOWS;i++){
            if(frameIsEnabled[i]){
                subWindows.get(i).refreshInfo();
            }
        }
    }
    
    /**
    * Refreshes the main menu by removing the existing scroll area and making a new one
    * then repainting the GUI and updating the available rooms
    */
    public void refreshMainMenu()
    {
        pane.remove(scrollArea);
        scrollArea = makeScrollArea(this, -1, "DUMMY");//Main Menu Frame ID is -1
        pane.add(scrollArea);
        positionGUI();
        pane.repaint();
        availableRoomsLabel.setText("Available Rooms: " +Main.getHotel().getAvailableRooms());
        validate();
    }

    private void makeLayout()
    {
        pane = new JPanel();
        layout = new SpringLayout();
        pane.setLayout(layout);
    }

    private void makeButtons()
    {
        roomsButton = new JButton ("List All Rooms");
        bookingsButton = new JButton ("List All Bookings");
        searchButton = new JButton ("Search");
        //Add event listeners to the buttons
        searchButton.addActionListener(this);
        bookingsButton.addActionListener(this);
        roomsButton.addActionListener(this);
    }

    private void makeLabels()
    {
        scrollAreaTitles = new JLabel ("Room No.   Room Type   En-Suite   Customer Name   No. Guests   Length of Stay");
        searchLabel = new JLabel ("Search by Customer Name: ");
        availableRoomsLabel = new JLabel ("Available Rooms: " +Main.getHotel().getAvailableRooms());
    }    

    private void makeFields()
    {
        searchField = new JTextField (20);
    }
    
    /**
    * Returns a scroll area (used in all list windows and main menu).
    * The contents of which is dependant on the frame of reference
    * 
    * @param frame main menu
    * @param frameID the frame id of the frame calling this method
    * @param searchArgs search arguments used in search results window
    * @return scroll area
    */
    public JScrollPane makeScrollArea(BaseWindow frame, int frameID, String searchArgs)
    {       
        area = new JPanel();
        area.setLayout (new BoxLayout(area, BoxLayout.PAGE_AXIS));
        
        cancelBookingButtons = new ArrayList<JButton>();
        
        for (int i = 0; i < Main.getHotel().getRooms().size(); i++)
        {
            boolean showRow=false;
            if (frameID==2 || frameID==-1){ // LIST ALL ROOMS or Main Menu (main menu ID is -1)
                showRow=true;
            }
            else if (frameID==3){ //LIST ALL BOOKINGS
                if (Main.getHotel().getRoom(i).getBooked()){
                    showRow=true;//only show row if the room is booked.
                }
            }
            else if (frameID==4){
                //BOOKMARKL
                if (Main.getHotel().getRooms().get(i).getCustomer().getName().toLowerCase().contains(searchArgs.toLowerCase())){
                    if (!Main.getHotel().getRooms().get(i).getCustomer().getName().equals("No Person")){//"No Person" is the default name for rooms in which no one is booked.
                        showRow=true;//only show row if the room matches searchArgs.
                    }
                }
           }
            //===
            if (showRow){
                //Create a new row
                JPanel row = new JPanel ();
                row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
                //row.setSize(new Dimension(640, 50));
                
                //Create the text for that row
                String rowText = Main.getHotel().getRoom(i).getRoomDetails();
                JTextArea roomData = new JTextArea (rowText);
                
                
                //Create the booking/cancelling button for that row
                BookButton bookButton;
                
                //Make the button display the correct string
                if (!Main.getHotel().getRoom(i).hasCustomer())
                {
                    bookButton = new BookButton ("Book", i, false, this);
                }
                else
                {
                    bookButton = new BookButton ("Cancel", i, true, this);
                }
                
                //Add event listener to the book/cancel button
                //bookButton.addActionListener(frame);
                
                //Add the room data and booking button
                row.add(roomData);
                row.add(bookButton);
                
                //Add the book cancel button to the button list so we can track it's state
                cancelBookingButtons.add(bookButton);
                
                //Add this row to the scrollable area
                area.add(row);
            }
        }       
        
        //Create the scroll area
        return new JScrollPane(area);
    }
    

    private void addButtons()
    {
        pane.add(roomsButton);
        pane.add(bookingsButton);
        pane.add(searchButton);
    }  
    
    private void addLabels()
    {
        pane.add(scrollAreaTitles);
        pane.add(searchLabel);
        pane.add(availableRoomsLabel);
    }   
    
    private void addFields()
    {
        pane.add(searchField);
    }   
    
    private void addScrollArea ()
    {
        pane.add(scrollArea);
    }

    private void createSubWindow (int frameID)
    {
        createSubWindow(frameID, 0, ""); 
    }
    
    private void createSubWindow (int frameID, int roomIndex){
        createSubWindow(frameID, roomIndex, "");
    }
    
    private void createSubWindow (int frameID, String searchArgs){
        createSubWindow(frameID, 0, searchArgs);
    }

    private void createSubWindow (int frameID, int roomIndex, String searchArgs)
    {
        //BOOKMARKZ
        if (frameIsEnabled[frameID]){//If frame is already enabled, refocus or recreate
            if(frameID==0 || frameID==1){//if the window is booking or cancel windows, destroy them and create new ones instead of refreshing their info.
                if (frameID==0){
                    subWindows.get(frameID).closeSelf();
                    subWindows.set(frameID, new BookingMenu("Make Booking", 0, roomIndex, this));
                }
                else{
                    subWindows.get(frameID).closeSelf();
                    subWindows.set(frameID, new BookingMenu("Cancel Booking", 1, roomIndex, this));
                }
                frameIsEnabled[frameID]=true;
            }
            else{
                subWindows.get(frameID).setState(java.awt.Frame.NORMAL);//Maximize Window
                subWindows.get(frameID).toFront();//Move window to Front
            }
        }
        else{//if frame is not enabled, create it.
            if (frameID==0){//0: booking window
                subWindows.set(frameID, new BookingMenu("Make Booking", 0, roomIndex, this));
            }
            else if (frameID==1){//1: cancel window
                subWindows.set(frameID, new BookingMenu("Cancel Booking", 1, roomIndex, this));
            }
            else if (frameID==2){
                subWindows.set(frameID, new ListWindow("List All Rooms", this, frameID));
            }
            else if (frameID==3){
                subWindows.set(frameID, new ListWindow("List All Bookings", this, frameID));
            }
            else if (frameID==4){
                subWindows.set(frameID, new ListWindow("Search Results", this, frameID, searchArgs));
            }
            frameIsEnabled[frameID]=true;
        }
    }
    
    /**
    * Destroys the sub menu with the id frameID
    * 
    * @param frameID the frame to be destroyed's id
    */
    public void destroySubWindow (int frameID)
    {
        frameIsEnabled[frameID]=false;
        subWindows.set(frameID, null);
    }
    
    /**
     * Performs an action when an event is recieved from an action listener
     * 
     * @param ae the event that was performed
     */
    public void actionPerformed (ActionEvent ae)
    {
        //Else if search was pressed do the search algorithm
        if (ae.getActionCommand().equals("Search"))
        {
            if (searchField.getText().equals("")){
                JOptionPane.showMessageDialog(this, "You haven't entered anything.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            else{
                boolean foundOnce=false;
                boolean foundTwice=false;
                BaseRoom lastFound = null;
                //ArrayList<BaseRoom> possibleMatches = new ArrayList<BaseRoom>();
                
                //for (BaseRoom b : Main.getHotel().getRooms())
                for (int i=0;i<Main.getHotel().getRooms().size();i++)
                {
                    //BOOKMARKL
                    BaseRoom room = Main.getHotel().getRooms().get(i);
                    if (!room.getCustomer().getName().equals("No Person")){//"No Person" is the default name for rooms in which no one is booked.
                        if (room.getCustomer().getName().toLowerCase().contains(searchField.getText().toLowerCase()))
                        {
                            if (foundOnce){
                                foundTwice=true;
                                break;
                            }
                            foundOnce=true;
                            lastFound=room;
                        }
                    }
                }
                if (!foundOnce)
                {
                    JOptionPane.showMessageDialog(this, "No customers found!", "Failure", JOptionPane.ERROR_MESSAGE);
                }
                else if (foundTwice)
                {
                    createSubWindow(4,searchField.getText());
                }
                else if (!foundTwice && foundOnce)
                {
                    callBookButtonAction(lastFound.getBooked(), Main.getHotel().getRoomPosition(lastFound.getNumber())); //OPEN THAT CUSTOMERS CANCEL WINDOW
                }
            }
            
        }
        else if (ae.getActionCommand().equals("List All Rooms"))
        {
            createSubWindow (2); //2: list window
        }
        else if (ae.getActionCommand().equals("List All Bookings"))
        {
            if (Main.getHotel().getAvailableRooms() == Main.getHotel().getRooms().size())
            {
                JOptionPane.showMessageDialog(this, "No Bookings Found", "Failure", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                createSubWindow (3); //2: list window
            }            
        }
    }    
    
    private void positionGUI()
    {       
        //Layout dimensions
        int roomButtonHorizontal = 25;
        int roomButtonVertical = 7;
        
        int bookingsButtonHorizontal = 50;
        int bookingsButtonVertical = 7;
        
        int searchLabelHorizontal = 5;
        int searchLabelVertical = 23;
        
        int searchFieldHorizontal = 40;
        int searchFieldVertical = 23;
        
        int seachButtonHorizontal = 80;
        int searchButtonVertical = 23;
        
        int scrollAreaTitlesHorizontal = 5;
        int scrollAreaTitlesVertical = 35;
        
        int availableRoomsLabelHorizontal = 5;
        int availableRoomsLabelVertical = 5;
        
        int scrollAreaWest = 5;
        int scrollAreaEast = 5;
        int scrollAreaNorth = 40;
        int scrollAreaSouth = 16;
        
        //Rooms button
        layout.putConstraint(SpringLayout.WEST, roomsButton, relativeHorizontal(roomButtonHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, roomsButton, relativeVertical(roomButtonVertical), SpringLayout.NORTH, pane);
        
        //Bookings button
        layout.putConstraint(SpringLayout.WEST, bookingsButton, relativeHorizontal(bookingsButtonHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, bookingsButton, relativeVertical(bookingsButtonVertical), SpringLayout.NORTH, pane);
        
        //Search label
        layout.putConstraint(SpringLayout.WEST, searchLabel, relativeHorizontal(searchLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, searchLabel, relativeVertical(searchLabelVertical), SpringLayout.NORTH, pane);
        
        //Search field
        layout.putConstraint(SpringLayout.WEST, searchField, relativeHorizontal(searchFieldHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, searchField, relativeVertical(searchFieldVertical), SpringLayout.NORTH, pane);
        
        //Search button
        layout.putConstraint(SpringLayout.WEST, searchButton, relativeHorizontal(seachButtonHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, searchButton, relativeVertical(searchButtonVertical), SpringLayout.NORTH, pane);
        
        //scrollAreaTitles label
        layout.putConstraint(SpringLayout.WEST, scrollAreaTitles, relativeHorizontal(scrollAreaTitlesHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.NORTH, scrollAreaTitles, relativeVertical(scrollAreaTitlesVertical), SpringLayout.NORTH, pane);
        
        //Available rooms label
        layout.putConstraint(SpringLayout.WEST, availableRoomsLabel, relativeHorizontal(availableRoomsLabelHorizontal), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.SOUTH, availableRoomsLabel, -relativeVertical(availableRoomsLabelVertical), SpringLayout.SOUTH, pane);
        
        //scroll area
        layout.putConstraint(SpringLayout.WEST, scrollArea, relativeHorizontal(scrollAreaWest), SpringLayout.WEST, pane);
        layout.putConstraint(SpringLayout.EAST, scrollArea, -relativeHorizontal(scrollAreaEast), SpringLayout.EAST, pane);
        layout.putConstraint(SpringLayout.NORTH, scrollArea, relativeVertical(scrollAreaNorth), SpringLayout.NORTH, pane);
        layout.putConstraint(SpringLayout.SOUTH, scrollArea, -relativeVertical(scrollAreaSouth), SpringLayout.SOUTH, pane);
        
    } 

    private void showFrame()
    {
      //Clicking close on Main Menu runs the quit function
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter()
      {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(errorFrame, "Are you sure you want to quit?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        Main.quit();
                    }
                    catch (FileNotFoundException e)
                    {
                       //JOptionPane.showMessageDialog(errorFrame, "Could not write file to disk.", "Error", JOptionPane.ERROR_MESSAGE);
                       if (JOptionPane.showConfirmDialog(errorFrame, "Could not write file to disk, are you sure you want to quit?", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION)
                       {
                            System.exit(0);
                       }
                    }
                }
                
            }
        });
      //Centre the frame
      setLocationRelativeTo(null);
      
      //Add pane
      add(pane);
           
      //Set size
      setSize(640, 480);
      
      setResizable(false);
      //Show
      setVisible(true);
    }   
    
    
    private int relativeHorizontal (int percent)
    {
        return (640 / 100) * percent;
    }
    private int relativeVertical (int percent)
    {
        return (480 / 100) * percent;
    }
    
    /**
    * Creates the correct subwindow depending on the button that was pressed
    * 
    * @param isBooked is the room booked for the button that was pressed?
    * @param roomPos the rooms position in the room array for the button that was pressed
    */
    public void callBookButtonAction(boolean isBooked, int roomPos){
        if (isBooked){
            createSubWindow (1, roomPos); //1: cancel window
        }
        else{
            createSubWindow (0, roomPos); //0: booking window
        }
    }
}
