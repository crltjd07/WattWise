/*  
THIS CLASS REPRESENTS THE LOGIN PAGE OF THE WATTWISE ENTERPRISE APPLICATION.  
IT HANDLES USER AUTHENTICATION VIA A GUI INTERFACE AND TRANSITIONS TO THE MAIN PAGE ON SUCCESS.  
*/
package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
public class WattWise_LogInPage extends JFrame {
    // FILE TO STORE USER CREDENTIALS  
    private File data = new File("data\\data.txt");
    // GUI PANELS FOR LAYOUT ORGANIZATION  
    private JPanel westPanel,
    westPanelContainer,
    usernameOuterPanel,
    passwordOuterPanel,
    usernameInnerPanel,
    passwordInnerPanel,
    centerPanel;
    // HELPER CLASS FOR UI STYLING AND COMPONENTS  
    WattWise_UIHelper ui = new WattWise_UIHelper();
    /*  
    CONSTRUCTOR: INITIALIZES THE LOGIN PAGE GUI.  
    THROWS EXCEPTION IF FILE OPERATIONS FAIL.  
    */
    WattWise_LogInPage() throws Exception {
        fileExists();
        this.setTitle("WattWise for Enterprise | Log In");
        this.setSize(1000, 650);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // WEST PANEL (LOGIN FORM)  
        westPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        westPanel.setBackground(ui.appUIColor);
        westPanelContainer = new JPanel(new GridLayout(0, 1, 0, 20));
        westPanelContainer.setOpaque(false);
        JLabel logoLabel = new JLabel(ui.logo);
        // USERNAME INPUT SECTION  
        usernameOuterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        usernameOuterPanel.setOpaque(false);
        usernameInnerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        usernameInnerPanel.setOpaque(false);
        JLabel usernameLabel = new JLabel("Enter Username: ");
        usernameLabel.setForeground(Color.WHITE);
        ui.usernameInput = new JTextField(15);
        ui.usernameInput.setFont(new Font("Arial Unicode MS", Font.BOLD, 15));
        ui.usernameInput.setForeground(Color.WHITE);
        ui.usernameInput.setBackground(ui.appUIColorLight);
        JButton usernameButton = new JButton("NEXT");
        usernameButton.setMnemonic(KeyEvent.VK_N);
        usernameButton.setBackground(ui.appUIColorNormal);
        usernameButton.setForeground(Color.WHITE);
        // ACTION LISTENER FOR USERNAME VALIDATION  
        ActionListener usernameAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!ui.usernameInput.getText().trim().isEmpty()) {
                    try {
                        if (checkAccount()) {
                            westPanelContainer.remove(usernameOuterPanel);
                            westPanelContainer.add(passwordOuterPanel);
                            westPanelContainer.revalidate();
                            westPanelContainer.repaint();
                        }
                    } catch (Exception e1) {
                        showErrorAndExit();
                    }
                } else {
                    ui.warningLabel.setText("MSG: Type username first!");
                    setWarningLabel(usernameInnerPanel);
                }
            }
        };
        usernameButton.addActionListener(usernameAction);
        ui.usernameInput.addActionListener(usernameAction);
        // PASSWORD INPUT SECTION  
        passwordOuterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        passwordOuterPanel.setOpaque(false);
        passwordInnerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        passwordInnerPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Enter Password: ");
        passwordLabel.setForeground(Color.WHITE);
        ui.passwordInput = new JPasswordField(15);
        ui.passwordInput.setFont(new Font("Arial Unicode MS", Font.BOLD, 15));
        ui.passwordInput.setForeground(Color.WHITE);
        ui.passwordInput.setBackground(ui.appUIColorLight);
        JButton passwordButton = new JButton("LOG IN");
        passwordButton.setMnemonic(KeyEvent.VK_L);
        passwordButton.setBackground(ui.appUIColorNormal);
        passwordButton.setForeground(Color.WHITE);
        // ACTION LISTENER FOR PASSWORD VALIDATION  
        ActionListener passwordAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!new String(ui.passwordInput.getPassword()).trim().isEmpty()) {
                    try {
                        if (checkPassword()) {
                            dispose();
                            new WattWise_MainPage();
                        };
                    } catch (Exception e2) {
                        showErrorAndExit();
                    }
                } else {
                    ui.warningLabel.setText("MSG: Type password first!");
                    setWarningLabel(passwordInnerPanel);
                }
            }
        };
        passwordButton.addActionListener(passwordAction);
        ui.passwordInput.addActionListener(passwordAction);
        // ASSEMBLE USERNAME PANEL  
        usernameInnerPanel.add(usernameLabel);
        usernameInnerPanel.add(ui.usernameInput);
        usernameInnerPanel.add(usernameButton);
        usernameInnerPanel.add(ui.warningLabel);
        usernameOuterPanel.add(usernameInnerPanel);
        westPanelContainer.add(logoLabel);
        westPanelContainer.add(usernameOuterPanel);
        // ASSEMBLE PASSWORD PANEL  
        passwordInnerPanel.add(passwordLabel);
        passwordInnerPanel.add(ui.passwordInput);
        passwordInnerPanel.add(passwordButton);
        passwordInnerPanel.add(ui.warningLabel);
        passwordOuterPanel.add(passwordInnerPanel);
        westPanel.add(westPanelContainer);
        // CENTER PANEL (APPLICATION DESCRIPTION)  
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(ui.appUIColorNormal);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 80, 20));
        JTextArea content = new JTextArea();
        content.setText("Welcome to WATTWISE v.1.0.0 [APP VERSION]!\n\n" + "-------------------------------------------------------\n\n" + "Originally developed as a console-based Java program, WattWise Energy\n" + "Solutions was designed with a simple mission: to help consumers and\n" + "electric utility companies in the Philippines manage, understand, and\n" + "optimize electricity usage.\n\n" + "Now evolved into a full-featured Java desktop application with GUI\n" + "support, WattWise offers a more interactive, intuitive, and professional\n" + "user experience—while retaining all the powerful features that made the\n" + "original version useful.\n\n" + "What This Application Offers:\n\n" + "-----------------------------\n\n" + "This upgraded version introduces a cleaner interface using Java Swing\n" + "components, organized menus, pop-up dialogs, and clickable navigation.\n" + "Below is a breakdown of the core modules:\n\n" + "⚙️ 1. Smart Meter Data Management System\n" + "- Centralizes data from digital meters for easy access.\n" + "- Helps utilities and enterprises monitor real-time consumption trends.\n\n" + "📨 2. Customer Service Ticketing\n" + "- Allows users to file service requests, issues, or complaints.\n" + "- Backend dashboard lets admins view, sort, and respond to tickets.\n\n" + "🌩️ 3. Outage Notification & Status Tracker\n" + "- Shows scheduled and unexpected power outages.\n" + "- Includes location mapping and estimated restoration time.\n\n" + "🗂️ 4. Digital Workflow Management Tool\n" + "- Organizes internal tasks like inspections or field maintenance.\n" + "- Assign deadlines, statuses, and descriptions for team members.\n\n" + "Key Features of the App Version:\n\n" + "-----------------------------\n\n" + "✔ Uses Java Swing for GUI elements like buttons, text areas, and menus.\n" + "✔ Includes user-friendly prompts and tooltips for guidance.\n" + "✔ Improved error handling and input validation.\n" + "✔ Border layout and consistent theming for better usability.\n" + "✔ Portable and lightweight—no internet required to operate.\n\n" + "Purpose and Vision:\n\n" + "-----------------------------\n\n" + "WattWise Energy Solutions aims to bridge technology and sustainability\n" + "by offering Filipino consumers access to digital tools that empower them\n" + "to understand their electricity consumption. From a text-based tool used\n" + "in classrooms to a desktop app used for advocacy and planning—WES is a\n" + "testament to the power of code for social good.\n\n" + "Disclaimer:\n\n" + "-----------------------------\n\n" + "- All billing estimates are based on public data and are for educational use.\n" + "- This tool is not an official billing or diagnostic app by any utility provider.\n" + "- The rates used are based on June 2024 data from Meralco.\n\n" + "Thank you for using WattWise Energy Solutions.\n" + "Smart energy. Sustainable future. Digitally empowered.\n");
        content.setEditable(false);
        content.setFont(ui.monospaceContent);
        content.setBackground(Color.BLACK);
        content.setForeground(Color.WHITE);
        content.setMargin(new Insets(10, 50, 10, 50));
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setCaretPosition(0);
        JScrollPane mainScrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(mainScrollPane);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    /*  
    DISPLAYS A WARNING LABEL ON THE TARGET PANEL.  
    PARAMETER: TARGETPANEL (JPANEL) - THE PANEL TO DISPLAY THE WARNING ON.  
    */
    private void setWarningLabel(JPanel targetPanel) {
        ui.warningLabel.setForeground(Color.WHITE);
        targetPanel.add(ui.warningLabel);
        targetPanel.revalidate();
        targetPanel.repaint();
    }
    /*  
    SHOWS AN ERROR MESSAGE AND EXITS THE APPLICATION.  
    DELETES THE DATA FILE TO ENSURE A CLEAN RESTART.  
    */
    private void showErrorAndExit() {
        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] An error just occurred.\n" + "[MSG] Repairing...", ui.dialogBox, JOptionPane.ERROR_MESSAGE);
        data.delete();
        try {
            fileExists();
            System.exit(0);
        } catch (Exception e3) {
            System.exit(0);
        }
    }
    /*  
    CHECKS IF THE DATA FILE EXISTS. IF NOT, CREATES A DEFAULT FILE WITH ADMIN CREDENTIALS.  
    THROWS EXCEPTION IF FILE CREATION FAILS.  
    */
    private void fileExists() throws Exception {
        if (!data.exists()) {
            JOptionPane.showMessageDialog(null, "WattWise Energy Solutions says:\n" + "[MSG] File 'data.txt' not found.\n" + "[MSG] Default mode for account will be initialized.", ui.dialogBox, JOptionPane.ERROR_MESSAGE);
            PrintWriter createFileData = new PrintWriter(data);
            createFileData.println("WATTWISE ENERGY SOLUTIONS v.1.0.0 [APP VERSION]");
            createFileData.println("Java Desktop Utility for Energy Awareness and Optimization");
            createFileData.println();
            createFileData.println("PERSONAL INFORMATION [DO NOT DELETE/ALTER ANYTHING BELOW!]");
            createFileData.print("Username:: ");
            createFileData.println("admin");
            createFileData.print("Password:: ");
            createFileData.println("admin");
            createFileData.close();
        }
    }
    /*  
    VALIDATES THE USERNAME AGAINST THE STORED DATA.  
    RETURNS: BOOLEAN (TRUE IF USERNAME MATCHES, FALSE OTHERWISE).  
    THROWS EXCEPTION IF FILE READING FAILS.  
    */
    private boolean checkAccount() throws Exception {
        Scanner input = new Scanner(data);
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.toLowerCase().startsWith("username::")) {
                int delimiterIndex = line.indexOf("::");
                if (delimiterIndex != -1) {
                    String foundUsername = line.substring(delimiterIndex + 2).trim();
                    if (foundUsername.isEmpty()) {
                        break;
                    }
                    if (foundUsername.equals(ui.usernameInput.getText().trim())) {
                        input.close();
                        return true;
                    } else {
                        ui.warningLabel.setText("MSG: User not found!");
                        setWarningLabel(usernameInnerPanel);
                        input.close();
                        ui.usernameInput.setText("");
                        return false;
                    }
                }
            }
        }
        input.close();
        showErrorAndExit();
        return false;
    }
    /*  
    VALIDATES THE PASSWORD AGAINST THE STORED DATA.  
    RETURNS: BOOLEAN (TRUE IF PASSWORD MATCHES, FALSE OTHERWISE).  
    THROWS EXCEPTION IF FILE READING FAILS.  
    */
    private boolean checkPassword() throws Exception {
        Scanner input = new Scanner(data);
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.toLowerCase().startsWith("password::")) {
                int delimiterIndex = line.indexOf("::");
                if (delimiterIndex != -1) {
                    String foundPassword = line.substring(delimiterIndex + 2).trim();
                    String enteredPassword = new String(ui.passwordInput.getPassword()).trim();
                    if (foundPassword.isEmpty()) {
                        break;
                    }
                    if (foundPassword.equals(enteredPassword)) {
                        input.close();
                        return true;
                    } else {
                        ui.warningLabel.setText("MSG: Incorrect password!");
                        setWarningLabel(passwordInnerPanel);
                        input.close();
                        ui.passwordInput.setText("");
                        return false;
                    }
                }
            }
        }
        input.close();
        showErrorAndExit();
        return false;
    }
}