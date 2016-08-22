import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
/**
 * List Windows are all sub-windows that contain lists, including List All Room Details, List All Bookings, and Search Results
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ListWindow extends SubWindow implements ActionListener
{
    private JScrollPane scrollArea;
    private JPanel pane;
    private SpringLayout layout;
    private String searchArgs;
    
    /**
     * Constructor for Base Window class.
     * 
     * @param title title of window
     */
    public ListWindow(String title, MainMenu parentFrame, int frameID)
    {
      this(title, parentFrame, frameID, "");
    }
    
    /**
     * Constructor for ListWindow class.
     * 
     * @param windowTitle title of window
     * @param parentFrame a reference to the main menu
     * @param frameID ID of the frame, used by the main menu to track subwindows.
     * @param searchArgs Search Arguments used in case the instantiated window is a "search results" window.
     */
    public ListWindow(String title, MainMenu parentFrame, int frameID, String searchArgs)
    {
      super(title, parentFrame, frameID);
      makeFrame(frameID, searchArgs);
    }
    
    private void makeFrame(int frameID, String searchArgs)
    {
        this.searchArgs=searchArgs;
        
        layout = new SpringLayout();
        setLayout(layout);
        
        JLabel titles = new JLabel ("   Room No.   Room Type   En-Suite   Customer Name   No. Guests   Length of Stay");
        add(titles);
        scrollArea = requestList(frameID, searchArgs);
        //scrollArea.setPreferredSize(new Dimension (300, 300));
        add(scrollArea);
        
        
        //scroll area
        updateGUI();
        
        //===
        setLocationRelativeTo(null);
        setSize(640, 480);
        setMinimumSize(new Dimension(640, 200));
        setVisible(true);
        //refreshInfo();
    }
    
    private void updateGUI(){
        layout.putConstraint(SpringLayout.WEST, scrollArea, 0, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.EAST, scrollArea, 0, SpringLayout.EAST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, scrollArea, 20, SpringLayout.NORTH, this.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, scrollArea, 0, SpringLayout.SOUTH, this.getContentPane());
    }
    
    /**
     * actionPerformed function.
     * 
     */
    public void actionPerformed (ActionEvent ae)
    {
    }
    
    /**
     * Refreshes the window's content to show the changes to the database
     * 
     */
    @Override
    public void refreshInfo() {
        //System.out.println("refreshing");
        remove(scrollArea);
        scrollArea = requestList(frameID, searchArgs);
        add(scrollArea);
        updateGUI();
        repaint();
        validate();
    }
}
