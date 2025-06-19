package main.java;
import javax.swing.*;
import java.awt.*;
/*
CLASS THAT PROVIDES UI HELPER FUNCTIONS AND RESOURCES FOR WATTWISE APPLICATION
CONTAINS COMMON UI ELEMENTS LIKE COLORS, FONTS, ICONS, AND FRAME MANAGEMENT METHODS
DESIGNED TO PROMOTE CONSISTENT LOOK AND FEEL ACROSS APPLICATION
*/
public class WattWise_UIHelper {
    // APPLICATION COLOR SCHEME
    public Color appUIColor = new Color(0x992924);
    public Color appUIColorNormal = new Color(0xB74742);
    public Color appUIColorLight = new Color(0xff7F66);
    // FONT DEFINITIONS FOR DIFFERENT UI ELEMENTS
    public Font unicodeTitle = new Font("Arial Unicode MS", Font.BOLD, 20);
    public Font monospaceContent = new Font("Monospaced", Font.PLAIN, 15);
    public Font forButton = new Font("Arial Rounded MS", Font.BOLD, 15);
    public Font serviceTitle = new Font("Bank Gothic Light BT", Font.BOLD, 30);
    // REFERENCE TO APPLICATION FRAMES
    protected JFrame smartMeterFrame;
    protected JFrame customerServiceFrame;
    protected JFrame outageNotificationFrame;
    protected JFrame digitalWorkflowFrame;
    protected JFrame submitTicket;
    // APPLICATION ICONS AND IMAGES
    protected ImageIcon icon = new ImageIcon("src\\main\\resources\\wattWiseIcon.png");
    protected ImageIcon iconSmall = new ImageIcon("src\\main\\resources\\wattWiseIconSmall.png");
    protected ImageIcon logo = new ImageIcon("src\\main\\resources\\wattWiseLogo.png");
    // COMMON UI COMPONENTS
    protected JLabel warningLabel = new JLabel();
    protected String dialogBox = new String("WattWise for Enterprise | Processing...");
    // LOGIN FORM COMPONENTS
    protected JTextField usernameInput;
    protected JPasswordField passwordInput;
    /*
    CUSTOM PANEL CLASS FOR DISPLAYING BACKGROUND IMAGES
    USED THROUGHOUT APPLICATION FOR CONSISTENT BACKGROUNDS
    */
    protected class backgroundImagePanel extends JPanel {
        private Image image;
        /*
        CONSTRUCTOR THAT LOADS BACKGROUND IMAGE FROM SPECIFIED PATH
        PARAMETERS:
        - imagePath: STRING PATH TO IMAGE FILE
        */
        public backgroundImagePanel(String imagePath) {
            try {
                image = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                //ignore
            }
            setLayout(new BorderLayout());
        }
        /*
        OVERRIDDEN METHOD TO PAINT BACKGROUND IMAGE
        PARAMETERS:
        - g: GRAPHICS CONTEXT FOR PAINTING
        */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
    /*
    METHOD TO DISPLAY ERROR MESSAGE AND TERMINATE APPLICATION
    USED FOR CRITICAL ERRORS THAT REQUIRE APPLICATION RESTART
    */
    protected void catchError() {
        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] An error occurred.\n" + "[MSG] Please restart the app after this prompt.", dialogBox, JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    /*
    METHOD TO MANAGE FRAME TRANSITIONS BETWEEN APPLICATION SCREENS
    PARAMETERS:
    - serviceFrame: CURRENT FRAME REFERENCE
    - serviceFrameCreate: NEW FRAME TO CREATE/SWITCH TO
    RETURNS:
    - JFrame: THE ACTIVE FRAME AFTER TRANSITION
    */
    public JFrame goToAnotherFrame(JFrame serviceFrame, JFrame serviceFrameCreate) {
        if (serviceFrame == null || !serviceFrame.isShowing()) {
            serviceFrame = serviceFrameCreate;
            serviceFrame.setVisible(true);
        } else {
            serviceFrame.toFront();
            serviceFrame.requestFocus();
        }
        return serviceFrame;
    }
}