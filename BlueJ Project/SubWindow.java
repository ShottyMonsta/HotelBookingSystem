import javax.swing.*;
/**
 * Sub-Windows are windows called by the main menu. Closing them does not quit the program. In addition, they include code to request main menu to refresh all windows.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class SubWindow extends BaseWindow
{
    private MainMenu parentFrame;//stores the parent frame which created this frame.
    protected int frameID; //0: booking window, 1: cancel window
    
    /**
     * Constructor for Sub-Window class.
     * 
     * @param windowTitle title of window
     * @param parentFrame a reference to the main menu
     * @param frameID ID of the frame, used by the main menu to track subwindows.
     */
    public SubWindow(String windowTitle, MainMenu parentFrame, int frameID){
        super(windowTitle);
        this.parentFrame=parentFrame;
        this.frameID=frameID;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeSelf();
            }
        });
    }
    
    protected void refreshInfo() {
        //DUMMY
    }
    
    protected void callParentRefresh(){
        parentFrame.refreshAll();
    }
    
    protected void closeSelf(){
        parentFrame.destroySubWindow(frameID);
        dispose();
    }
    
    protected JScrollPane requestList(int frameID, String searchArgs){
        return parentFrame.makeScrollArea(this, frameID, searchArgs);
    }









}
