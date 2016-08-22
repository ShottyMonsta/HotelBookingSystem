import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * This class is used to implement an action listener into a JFrame.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class BaseWindow extends JFrame implements ActionListener{
    
    /**
     * Constructor for Base Window class.
     * 
     * @param title title of window
     */
    public BaseWindow(String title){
        super (title);
    }
}
