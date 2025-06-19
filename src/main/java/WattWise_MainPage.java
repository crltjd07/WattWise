package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * MAIN PAGE CLASS FOR WATTWISE ENTERPRISE APPLICATION
 * THIS CLASS EXTENDS JFRAME TO CREATE THE PRIMARY USER INTERFACE
 * IMPLEMENTS OBJECT ORIENTED DESIGN PRINCIPLES INCLUDING ENCAPSULATION AND INHERITANCE
 * PROVIDES NAVIGATION TO ALL APPLICATION MODULES AND SERVICES
 * MANAGES USER INTERFACE COMPONENTS AND EVENT HANDLING FOR MAIN DASHBOARD
 */
public class WattWise_MainPage extends JFrame {
    // PRIVATE PANEL COMPONENTS FOR LAYOUT MANAGEMENT USING ENCAPSULATION
    private JPanel northPanel,
    southPanel,
    westPanel,
    eastPanel,
    centerPanel;
    // PRIVATE MENU COMPONENTS FOR NAVIGATION USING ENCAPSULATION
    private JMenu aboutUs,
    start,
    instruction,
    credits;
    // PROTECTED MENU BAR COMPONENTS FOR INHERITANCE ACCESS
    protected JMenuBar menuBarLeft, menuBarRight;
    // PROTECTED BUTTON COMPONENT FOR INHERITANCE ACCESS
    protected JButton chooseService;
    // PRIVATE MENU ITEM COMPONENTS FOR ENCAPSULATION
    private JMenuItem whatIs, howIs, whoIs;
    // PRIVATE TEXT AREA AND SCROLL PANE FOR CONTENT DISPLAY
    private JTextArea content;
    private JScrollPane scrollPane;
    // COMPOSITION RELATIONSHIP WITH UI HELPER CLASS
    WattWise_UIHelper ui;
    /**
     * CONSTRUCTOR FOR MAIN PAGE CLASS
     * INITIALIZES ALL USER INTERFACE COMPONENTS AND LAYOUT
     * SETS UP EVENT LISTENERS FOR USER INTERACTIONS
     * CONFIGURES WINDOW PROPERTIES AND VISUAL APPEARANCE
     * DEMONSTRATES CONSTRUCTOR OVERLOADING PRINCIPLE
     */
    WattWise_MainPage() {
        // INSTANTIATE UI HELPER OBJECT FOR COMPOSITION RELATIONSHIP
        ui = new WattWise_UIHelper();
        // SET MAIN WINDOW PROPERTIES FOR ENCAPSULATION
        this.setTitle("WattWise for Enterprise | Main Page");
        this.setSize(1200, 750);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // INITIALIZE NORTH PANEL FOR TOP NAVIGATION BAR
        northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(ui.appUIColorNormal);
        // CREATE LEFT MENU BAR WITH FLOW LAYOUT
        menuBarLeft = new JMenuBar();
        menuBarLeft.setOpaque(false);
        menuBarLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        // CREATE RIGHT MENU BAR WITH FLOW LAYOUT
        menuBarRight = new JMenuBar();
        menuBarRight.setOpaque(false);
        menuBarRight.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        // CREATE ABOUT US MENU WITH KEYBOARD SHORTCUT
        aboutUs = new JMenu("About Us");
        aboutUs.setForeground(Color.WHITE);
        aboutUs.setMnemonic(KeyEvent.VK_A);
        menuBarLeft.add(aboutUs);
        // CREATE WHAT IS MENU ITEM WITH ICON AND SHORTCUT
        whatIs = new JMenuItem("What is WattWise?");
        whatIs.setMnemonic(KeyEvent.VK_W);
        whatIs.setIcon(new ImageIcon("src\\main\\resources\\aboutUsIcon.png"));
        whatIs.setBackground(ui.appUIColorLight);
        aboutUs.add(whatIs);
        // ADD LEFT MENU BAR TO NORTH PANEL
        northPanel.add(menuBarLeft, BorderLayout.WEST);
        /**
         * ANONYMOUS INNER CLASS IMPLEMENTING ACTION LISTENER INTERFACE
         * HANDLES NAVIGATION BETWEEN DIFFERENT APPLICATION MODULES
         * USES POLYMORPHISM TO HANDLE DIFFERENT EVENT SOURCES
         * DEMONSTRATES EVENT DRIVEN PROGRAMMING PRINCIPLES
         */
        ActionListener goTo = new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR HANDLING BUTTON AND MENU ITEM CLICKS
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT CONTAINING SOURCE INFORMATION
             * RETURN: VOID - NO RETURN VALUE
             * USES EXCEPTION HANDLING AND CONDITIONAL LOGIC FOR NAVIGATION
             */
            public void actionPerformed(ActionEvent e) {
                // DECLARE VARIABLES FOR SERVICE NAME IDENTIFICATION
                String serviceName = "", serviceNameList = "";
                try {
                    // TRY TO CAST EVENT SOURCE TO BUTTON OR MENU ITEM
                    try {
                        serviceName = ((JButton) e.getSource()).getText();
                    } catch (Exception e1) {
                        serviceNameList = ((JMenuItem) e.getSource()).getText();
                    }
                    // NAVIGATE TO SMART METER DATA MANAGEMENT SYSTEM
                    if (serviceName.contains("Smart Meter Data Management System") || serviceNameList.contains("Smart Meter Data Management System")) {
                        ui.smartMeterFrame = ui.goToAnotherFrame(ui.smartMeterFrame, new WattWise_smartMeterService());
                    }
                    // NAVIGATE TO CUSTOMER SERVICE TICKETING SYSTEM
                    else if (serviceName.contains("Customer Service Ticketing") || serviceNameList.contains("Customer Service Ticketing")) {
                        ui.customerServiceFrame = ui.goToAnotherFrame(ui.customerServiceFrame, new WattWise_customerService());
                    }
                    // NAVIGATE TO OUTAGE NOTIFICATION AND STATUS TRACKER
                    else if (serviceName.contains("Outage Notification and Status Tracker") || serviceNameList.contains("Outage Notification and Status Tracker")) {
                        ui.outageNotificationFrame = ui.goToAnotherFrame(ui.outageNotificationFrame, new WattWise_outageNotification());
                    }
                    // NAVIGATE TO DIGITAL WORKFLOW MANAGEMENT TOOL
                    else if (serviceName.contains("Digital Workflow Management Tool") || serviceNameList.contains("Digital Workflow Management Tool")) {
                        ui.digitalWorkflowFrame = ui.goToAnotherFrame(ui.digitalWorkflowFrame, new WattWise_digitalWorkflow());
                    }
                } catch (Exception e2) {
                    // IGNORE EXCEPTIONS FOR ROBUST ERROR HANDLING
                }
            }
        };
        // CREATE START MENU FOR SERVICE NAVIGATION
        start = new JMenu("Start");
        start.setForeground(Color.WHITE);
        start.setMnemonic(KeyEvent.VK_S);
        menuBarRight.add(start);
        // CREATE MENU ITEM FOR SMART METER DATA MANAGEMENT SYSTEM
        JMenuItem option1 = new JMenuItem("Smart Meter Data Management System");
        option1.setMnemonic(KeyEvent.VK_M);
        option1.setBackground(ui.appUIColorLight);
        option1.setIcon(new ImageIcon("src\\main\\resources\\smartMeterIcon.png"));
        option1.addActionListener(goTo);
        start.add(option1);
        // CREATE MENU ITEM FOR CUSTOMER SERVICE TICKETING
        JMenuItem option2 = new JMenuItem("Customer Service Ticketing");
        option2.setMnemonic(KeyEvent.VK_C);
        option2.setBackground(ui.appUIColorLight);
        option2.setIcon(new ImageIcon("src\\main\\resources\\customerServiceIcon.png"));
        option2.addActionListener(goTo);
        start.add(option2);
        // CREATE MENU ITEM FOR OUTAGE NOTIFICATION AND STATUS TRACKER
        JMenuItem option3 = new JMenuItem("Outage Notification and Status Tracker");
        option3.setMnemonic(KeyEvent.VK_O);
        option3.setBackground(ui.appUIColorLight);
        option3.setIcon(new ImageIcon("src\\main\\resources\\outageNotificationLogo.png"));
        option3.addActionListener(goTo);
        start.add(option3);
        // CREATE MENU ITEM FOR DIGITAL WORKFLOW MANAGEMENT TOOL
        JMenuItem option4 = new JMenuItem("Digital Workflow Management Tool");
        option4.setMnemonic(KeyEvent.VK_D);
        option4.setBackground(ui.appUIColorLight);
        option4.setIcon(new ImageIcon("src\\main\\resources\\digitalWorkflowLogo.png"));
        option4.addActionListener(goTo);
        start.add(option4);
        // CREATE INSTRUCTION MENU FOR USER GUIDANCE
        instruction = new JMenu("Instruction");
        instruction.setForeground(Color.WHITE);
        instruction.setMnemonic(KeyEvent.VK_I);
        menuBarRight.add(instruction);
        // CREATE HOW TO USE MENU ITEM
        howIs = new JMenuItem("How to use WattWise for Enterprise?");
        howIs.setMnemonic(KeyEvent.VK_H);
        howIs.setBackground(ui.appUIColorLight);
        howIs.setIcon(new ImageIcon("src\\main\\resources\\howIsLogo.png"));
        instruction.add(howIs);
        // CREATE CREDITS MENU FOR DEVELOPER INFORMATION
        credits = new JMenu("Credits");
        credits.setMnemonic(KeyEvent.VK_E);
        credits.setForeground(Color.WHITE);
        menuBarRight.add(credits);
        // CREATE ABOUT DEVELOPER MENU ITEM
        whoIs = new JMenuItem("About the Developer :)");
        whoIs.setMnemonic(KeyEvent.VK_B);
        whoIs.setBackground(ui.appUIColorLight);
        whoIs.setIcon(new ImageIcon("src\\main\\resources\\whoIsLogo.png"));
        credits.add(whoIs);
        // ADD RIGHT MENU BAR TO NORTH PANEL
        northPanel.add(menuBarRight, BorderLayout.EAST);
        // INITIALIZE SOUTH PANEL FOR BOTTOM NAVIGATION
        southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 10));
        southPanel.setBackground(ui.appUIColorNormal);
        // CREATE CHANGE PASSWORD BUTTON WITH ICON
        JButton changePassword = new JButton("🔑 Change Password");
        changePassword.setMnemonic(KeyEvent.VK_P);
        southPanel.add(changePassword);
        /**
         * ANONYMOUS INNER CLASS FOR CHANGE PASSWORD BUTTON ACTION
         * HANDLES USER CONFIRMATION AND PASSWORD CHANGE NAVIGATION
         * DEMONSTRATES NESTED CLASS USAGE AND EVENT HANDLING
         */
        changePassword.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR CHANGE PASSWORD BUTTON
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM BUTTON CLICK
             * RETURN: VOID - NO RETURN VALUE
             * SHOWS CONFIRMATION DIALOG BEFORE OPENING PASSWORD CHANGE FORM
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    // CREATE USER CONFIRMATION DIALOG
                    WattWise_userConfirm confirmForm = new WattWise_userConfirm(WattWise_MainPage.this);
                    boolean confirmed = confirmForm.showAndConfirm();
                    // OPEN PASSWORD CHANGE FORM IF CONFIRMED
                    if (confirmed) {
                        new WattWise_changePassword().setVisible(true);
                    }
                } catch (Exception e1) {
                    // IGNORE EXCEPTIONS FOR ROBUST ERROR HANDLING
                }
            }
        });
        // CREATE SUBMIT TICKET BUTTON WITH ICON
        JButton submitATicket = new JButton("🎫 Submit A Ticket");
        submitATicket.setMnemonic(KeyEvent.VK_T);
        southPanel.add(submitATicket);
        /**
         * ANONYMOUS INNER CLASS FOR SUBMIT TICKET BUTTON ACTION
         * HANDLES NAVIGATION TO TICKET SUBMISSION FORM
         * DEMONSTRATES COMPOSITION RELATIONSHIP WITH UI HELPER
         */
        submitATicket.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR SUBMIT TICKET BUTTON
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM BUTTON CLICK
             * RETURN: VOID - NO RETURN VALUE
             * OPENS TICKET SUBMISSION FORM USING UI HELPER METHOD
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    ui.submitTicket = ui.goToAnotherFrame(ui.submitTicket, new WattWise_ticketSubmit());
                } catch (Exception e1) {
                    // IGNORE EXCEPTIONS FOR ROBUST ERROR HANDLING
                }
            }
        });
        // CREATE EXIT APPLICATION BUTTON WITH ICON
        JButton exitApplication = new JButton("🔒 Exit Application");
        exitApplication.setMnemonic(KeyEvent.VK_W);
        southPanel.add(exitApplication);
        /**
         * ANONYMOUS INNER CLASS FOR EXIT APPLICATION BUTTON ACTION
         * HANDLES APPLICATION EXIT WITH USER CONFIRMATION
         * DEMONSTRATES DIALOG BOX USAGE AND SYSTEM EXIT
         */
        exitApplication.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR EXIT APPLICATION BUTTON
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM BUTTON CLICK
             * RETURN: VOID - NO RETURN VALUE
             * SHOWS CONFIRMATION DIALOG BEFORE TERMINATING APPLICATION
             */
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "WattWise Energy Solutions says:\n" + "[MSG] Are you sure you want to exit the application?\n" + "[MSG] All unsaved work will not be saved.", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        // INITIALIZE WEST PANEL FOR LOGO AND SERVICE BUTTONS
        westPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        westPanel.setBackground(ui.appUIColor);
        // CREATE LOGO LABEL AND ADD TO WEST PANEL
        JLabel logoLabel = new JLabel(ui.logo);
        westPanel.add(logoLabel);
        // CREATE MENU PANEL FOR SERVICE BUTTONS
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        menu.setOpaque(false);
        // CREATE INNER MENU PANEL WITH GRID LAYOUT
        JPanel innerMenu = new JPanel(new GridLayout(0, 1, 0, 10));
        innerMenu.setOpaque(false);
        // CREATE SERVICE TITLE LABEL
        JLabel serviceTitle = new JLabel("OFFERED SERVICES");
        serviceTitle.setForeground(Color.WHITE);
        serviceTitle.setFont(new Font("Arial Rounded MS", Font.BOLD, 20));
        innerMenu.add(serviceTitle);
        // CREATE SMART METER DATA MANAGEMENT SYSTEM BUTTON
        JButton SmartMeterDataManagementSystem = new JButton("Smart Meter Data Management System");
        SmartMeterDataManagementSystem.setMnemonic(KeyEvent.VK_M);
        SmartMeterDataManagementSystem.setBackground(ui.appUIColorLight);
        SmartMeterDataManagementSystem.setFont(ui.forButton);
        SmartMeterDataManagementSystem.setMargin(new Insets(10, 0, 10, 0));
        SmartMeterDataManagementSystem.addActionListener(goTo);
        innerMenu.add(SmartMeterDataManagementSystem);
        // CREATE CUSTOMER SERVICE TICKETING BUTTON
        JButton CustomerServiceTicketing = new JButton("Customer Service Ticketing");
        CustomerServiceTicketing.setMnemonic(KeyEvent.VK_C);
        CustomerServiceTicketing.setBackground(ui.appUIColorLight);
        CustomerServiceTicketing.setFont(ui.forButton);
        CustomerServiceTicketing.addActionListener(goTo);
        innerMenu.add(CustomerServiceTicketing);
        // CREATE OUTAGE NOTIFICATION AND STATUS TRACKER BUTTON
        JButton OutageNotificationAndStatusTracker = new JButton("Outage Notification and Status Tracker");
        OutageNotificationAndStatusTracker.setMnemonic(KeyEvent.VK_O);
        OutageNotificationAndStatusTracker.setBackground(ui.appUIColorLight);
        OutageNotificationAndStatusTracker.setFont(ui.forButton);
        OutageNotificationAndStatusTracker.addActionListener(goTo);
        innerMenu.add(OutageNotificationAndStatusTracker);
        // CREATE DIGITAL WORKFLOW MANAGEMENT TOOL BUTTON
        JButton DigitalWorkflowManagementTool = new JButton("Digital Workflow Management Tool");
        DigitalWorkflowManagementTool.setMnemonic(KeyEvent.VK_D);
        DigitalWorkflowManagementTool.setBackground(ui.appUIColorLight);
        DigitalWorkflowManagementTool.setFont(ui.forButton);
        DigitalWorkflowManagementTool.addActionListener(goTo);
        innerMenu.add(DigitalWorkflowManagementTool);
        // ADD INNER MENU TO MENU PANEL
        menu.add(innerMenu);
        westPanel.add(menu);
        // INITIALIZE EAST PANEL FOR EXIT BUTTON
        eastPanel = new JPanel(new BorderLayout());
        eastPanel.setBackground(ui.appUIColorNormal);
        // CREATE CLOSE BUTTON WITH ICON
        JButton closeButton = new JButton();
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setBackground(ui.appUIColorNormal);
        closeButton.setBorderPainted(false);
        closeButton.setIcon(new ImageIcon("src\\main\\resources\\exitIcon.png"));
        // ATTACH SIGN OUT FUNCTIONALITY TO CLOSE BUTTON
        signOut(closeButton);
        // CREATE BUTTON MARGIN PANEL FOR LAYOUT
        JPanel buttonMargin = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonMargin.setOpaque(false);
        // CREATE BUTTON STACK PANEL
        JPanel buttonStack = new JPanel();
        buttonStack.setLayout(new GridLayout(0, 1));
        buttonStack.setOpaque(false);
        buttonStack.add(closeButton);
        buttonMargin.add(buttonStack);
        // ADD BUTTON MARGIN TO EAST PANEL
        eastPanel.add(buttonMargin, BorderLayout.SOUTH);
        // CREATE CENTER PANEL WITH BACKGROUND IMAGE
        centerPanel = ui.new backgroundImagePanel("src\\main\\resources\\romanPetrovOnUnsplash.jpg");
        /**
         * ANONYMOUS INNER CLASS FOR WHAT IS MENU ITEM ACTION
         * HANDLES DISPLAY OF APPLICATION INFORMATION
         * DEMONSTRATES DYNAMIC CONTENT CREATION AND LAYOUT MANAGEMENT
         */
        whatIs.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR WHAT IS MENU ITEM
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM MENU ITEM CLICK
             * RETURN: VOID - NO RETURN VALUE
             * DISPLAYS DETAILED APPLICATION INFORMATION IN SCROLLABLE TEXT AREA
             */
            public void actionPerformed(ActionEvent e) {
                // CLEAR EXISTING CONTENT FROM CENTER PANEL
                centerPanel.removeAll();
                // CREATE NEW TEXT AREA FOR CONTENT DISPLAY
                content = new JTextArea();
                content.setText("Welcome to WATTWISE v.1.0.0 [APP VERSION]!\n\n" + "-------------------------------------------------------\n\n" + "Originally developed as a console-based Java program, WattWise Energy\n" + "Solutions was designed with a simple mission: to help consumers and\n" + "electric utility companies in the Philippines manage, understand, and\n" + "optimize electricity usage.\n\n" + "Now evolved into a full-featured Java desktop application with GUI\n" + "support, WattWise offers a more interactive, intuitive, and professional\n" + "user experience—while retaining all the powerful features that made the\n" + "original version useful.\n\n" + "What This Application Offers:\n\n" + "-----------------------------\n\n" + "This upgraded version introduces a cleaner interface using Java Swing\n" + "components, organized menus, pop-up dialogs, and clickable navigation.\n" + "Below is a breakdown of the core modules:\n\n" + "⚙️ 1. Smart Meter Data Management System\n" + "- Centralizes data from digital meters for easy access.\n" + "- Helps utilities and enterprises monitor real-time consumption trends.\n\n" + "📨 2. Customer Service Ticketing\n" + "- Allows users to file service requests, issues, or complaints.\n" + "- Backend dashboard lets admins view, sort, and respond to tickets.\n\n" + "🌩️ 3. Outage Notification & Status Tracker\n" + "- Shows scheduled and unexpected power outages.\n" + "- Includes location mapping and estimated restoration time.\n\n" + "🗂️ 4. Digital Workflow Management Tool\n" + "- Organizes internal tasks like inspections or field maintenance.\n" + "- Assign deadlines, statuses, and descriptions for team members.\n\n" + "Key Features of the App Version:\n\n" + "-----------------------------\n\n" + "✔ Uses Java Swing for GUI elements like buttons, text areas, and menus.\n" + "✔ Includes user-friendly prompts and tooltips for guidance.\n" + "✔ Improved error handling and input validation.\n" + "✔ Border layout and consistent theming for better usability.\n" + "✔ Portable and lightweight—no internet required to operate.\n\n" + "Purpose and Vision:\n\n" + "-----------------------------\n\n" + "WattWise Energy Solutions aims to bridge technology and sustainability\n" + "by offering Filipino consumers access to digital tools that empower them\n" + "to understand their electricity consumption. From a text-based tool used\n" + "in classrooms to a desktop app used for advocacy and planning—WES is a\n" + "testament to the power of code for social good.\n\n" + "Disclaimer:\n\n" + "-----------------------------\n\n" + "- All billing estimates are based on public data and are for educational use.\n" + "- This tool is not an official billing or diagnostic app by any utility provider.\n" + "- The rates used are based on June 2024 data from Meralco.\n\n" + "Thank you for using WattWise Energy Solutions.\n" + "Smart energy. Sustainable future. Digitally empowered.\n");
                // CONFIGURE TEXT AREA PROPERTIES
                content.setEditable(false);
                content.setFont(ui.monospaceContent);
                content.setBackground(Color.BLACK);
                content.setForeground(Color.WHITE);
                content.setMargin(new Insets(10, 50, 10, 50));
                content.setLineWrap(true);
                content.setWrapStyleWord(true);
                content.setCaretPosition(0);
                // CREATE SCROLL PANE FOR TEXT AREA
                scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                // ADD SCROLL PANE TO CENTER PANEL AND REFRESH
                centerPanel.add(scrollPane);
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        });
        /**
         * ANONYMOUS INNER CLASS FOR HOW TO USE MENU ITEM ACTION
         * HANDLES DISPLAY OF USAGE INSTRUCTIONS
         * DEMONSTRATES CONTENT MANAGEMENT AND USER GUIDANCE
         */
        howIs.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR HOW TO USE MENU ITEM
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM MENU ITEM CLICK
             * RETURN: VOID - NO RETURN VALUE
             * DISPLAYS STEP BY STEP USAGE INSTRUCTIONS IN SCROLLABLE TEXT AREA
             */
            public void actionPerformed(ActionEvent e) {
                // CLEAR EXISTING CONTENT FROM CENTER PANEL
                centerPanel.removeAll();
                // CREATE NEW TEXT AREA FOR INSTRUCTION CONTENT
                content = new JTextArea();
                content.setText("🛈 How to Use WattWise for Enterprise:\n\n" + "Before accessing the main page, all users are required to log in through a secure login interface.\n" + "This ensures that access is limited to verified personnel or administrators.\n\n" + "Once logged in, the WattWise for Enterprise homepage provides navigation options for energy-related services.\n" + "Here's a step-by-step guide on how to use each module:\n\n" + "1️ START MENU OPTIONS:\n" + "Navigate to the \"Start\" menu in the top-right corner to select any of the following modules:\n\n" + "• Smart Meter Data Management System\n" + "- View, analyze, and interpret energy usage from digital meters.\n\n" + "• Customer Service Ticketing\n" + "- File service-related issues and monitor resolution status.\n\n" + "• Outage Notification and Status Tracker\n" + "- Check current or scheduled outages and receive live updates.\n\n" + "• Digital Workflow Management Tool\n" + "- Assign, manage, and monitor internal operational tasks or field work.\n\n" + "2️ SIDE PANEL BUTTONS:\n" + "These provide quick access to the same modules using large, visible buttons.\n\n" + "3️ EXITING THE APP:\n" + "Click the power icon in the bottom-right corner to safely close the application.\n\n" + "💡 Tips:\n" + "✔ Make sure you have admin rights to access ticket dashboards.\n" + "✔ You do not need an internet connection to run the app after login.\n" + "✔ All data is stored locally and safely.\n\n" + "Ready to empower your enterprise with smarter energy decisions?\n" + "Start exploring the modules now!");
                // CONFIGURE INSTRUCTION TEXT AREA PROPERTIES
                content.setEditable(false);
                content.setFont(ui.monospaceContent);
                content.setBackground(new Color(0, 0, 0, 100));
                content.setForeground(Color.WHITE);
                content.setMargin(new Insets(10, 50, 10, 50));
                content.setLineWrap(true);
                content.setWrapStyleWord(true);
                content.setCaretPosition(0);
                // CREATE SCROLL PANE FOR INSTRUCTION TEXT AREA
                scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(100, 20, 100, 20));
                scrollPane.setOpaque(false);
                // ADD SCROLL PANE TO CENTER PANEL AND REFRESH
                centerPanel.add(scrollPane);
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        });
        /**
         * ANONYMOUS INNER CLASS FOR ABOUT DEVELOPER MENU ITEM ACTION
         * HANDLES DISPLAY OF DEVELOPER INFORMATION AND CREDITS
         * DEMONSTRATES PROFESSIONAL ATTRIBUTION AND CONTACT INFORMATION
         */
        whoIs.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR ABOUT DEVELOPER MENU ITEM
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM MENU ITEM CLICK
             * RETURN: VOID - NO RETURN VALUE
             * DISPLAYS DEVELOPER INFORMATION AND APPLICATION CREDITS
             */
            public void actionPerformed(ActionEvent e) {
                // CLEAR EXISTING CONTENT FROM CENTER PANEL
                centerPanel.removeAll();
                // CREATE NEW TEXT AREA FOR DEVELOPER INFORMATION
                content = new JTextArea();
                content.setText("👨‍💻 About the Developer\n\n" + "This application was proudly developed by Carl Francis T. Encallado,\n" + "a passionate Computer Engineering student with a focus on\n" + "software development, energy systems, and digital transformation.\n\n" + "🌱 Driven by the goal of sustainability and smart living,\n" + "Carl Francis T. Encallado (carl tejada) created WattWise for Enterprise to empower\n" + "electric utility providers and organizations in managing\n" + "critical electricity-related operations.\n\n" + "🔧 Tools Used:\n" + "- Java (Swing GUI)\n" + "- Object-Oriented Programming\n" + "- Eclipse IDE for Java Developers\n\n" + "💡 Vision:\n" + "To use technology as a force for energy efficiency, transparency,\n" + "and consumer empowerment across the Philippines and beyond.\n\n" + "📬 Contact:\n" + "If you'd like to collaborate, suggest improvements, or just say hi,\n" + "feel free to reach out at: carlfrancisencallado@gmail.com\n\n" + "Thank you for supporting WattWise Energy Solutions!");
                // CONFIGURE DEVELOPER INFORMATION TEXT AREA PROPERTIES
                content.setEditable(false);
                content.setFont(ui.monospaceContent);
                content.setBackground(new Color(0, 0, 0, 100));
                content.setForeground(Color.WHITE);
                content.setMargin(new Insets(10, 50, 10, 50));
                content.setLineWrap(true);
                content.setWrapStyleWord(true);
                content.setCaretPosition(0);
                // CREATE SCROLL PANE FOR DEVELOPER INFORMATION
                scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(100, 20, 100, 20));
                scrollPane.setOpaque(false);
                // ADD SCROLL PANE TO CENTER PANEL AND REFRESH
                centerPanel.add(scrollPane);
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        });
        // ADD ALL PANELS TO MAIN FRAME USING BORDER LAYOUT
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(centerPanel, BorderLayout.CENTER);
        // MAKE FRAME VISIBLE TO USER
        this.setVisible(true);
    }
    /**
     * PUBLIC METHOD TO ATTACH SIGN OUT FUNCTIONALITY TO A BUTTON
     * PARAMETER: JBUTTON BUTTON - THE BUTTON TO ATTACH SIGN OUT ACTION
     * RETURN: VOID - NO RETURN VALUE
     * DEMONSTRATES METHOD OVERLOADING AND REUSABLE CODE PRINCIPLES
     * HANDLES USER CONFIRMATION AND NAVIGATION BACK TO LOGIN PAGE
     */
    public void signOut(JButton button) {
        /**
         * ANONYMOUS INNER CLASS FOR SIGN OUT BUTTON ACTION
         * HANDLES USER LOGOUT WITH CONFIRMATION DIALOG
         * DEMONSTRATES SECURE SESSION MANAGEMENT AND USER EXPERIENCE
         */
        button.addActionListener(new ActionListener() {
            /**
             * ACTION PERFORMED METHOD FOR SIGN OUT BUTTON
             * PARAMETER: ACTIONEVENT E - THE EVENT OBJECT FROM BUTTON CLICK
             * RETURN: VOID - NO RETURN VALUE
             * SHOWS CONFIRMATION DIALOG BEFORE CLOSING CURRENT FRAME AND OPENING LOGIN
             */
            public void actionPerformed(ActionEvent e) {
                // SHOW CONFIRMATION DIALOG FOR SIGN OUT ACTION
                if (JOptionPane.showConfirmDialog(null, "WattWise Energy Solutions says:\n" + "[MSG] Are you sure you want to sign out?\n" + "[MSG] All unsaved work will not be saved.", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    // DISPOSE CURRENT FRAME TO FREE RESOURCES
                    dispose();
                    try {
                        // CREATE NEW LOGIN PAGE INSTANCE AND MAKE VISIBLE
                        new WattWise_LogInPage().setVisible(true);
                    } catch (Exception e1) {
                        // IGNORE EXCEPTIONS FOR ROBUST ERROR HANDLING
                    }
                }
            }
        });
    }
}