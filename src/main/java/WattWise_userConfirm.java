package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
/*
CLASS THAT HANDLES USER CONFIRMATION DIALOG FOR WATTWISE APPLICATION
PROVIDES USERNAME AND PASSWORD VALIDATION AGAINST STORED CREDENTIALS
EXTENDS JDIALOG TO CREATE MODAL CONFIRMATION WINDOW
*/
public class WattWise_userConfirm extends JDialog {
    // FILE CONTAINING USER CREDENTIALS
    private File data = new File("data\\data.txt");
    // UI PANELS FOR DIALOG LAYOUT
    private JPanel centerPanel,
    centerPanelContainer,
    usernameOuterPanel,
    passwordOuterPanel,
    usernameInnerPanel,
    passwordInnerPanel;
    // HELPER CLASS FOR UI CONSISTENCY
    WattWise_UIHelper ui = new WattWise_UIHelper();
    private boolean confirmed = false;
    /*
    CONSTRUCTOR THAT SETS UP USER CONFIRMATION DIALOG
    PARAMETERS:
    - parentFrameForDialog: FRAME TO CENTER DIALOG ON
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    public WattWise_userConfirm(Frame parentFrameForDialog) throws Exception {
        fileExists();
        this.setTitle("WattWise for Enterprise | User Confirmation");
        this.setModal(true);
        this.setSize(400, 520);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(parentFrameForDialog);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // INITIALIZE MAIN PANELS
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBackground(Color.BLACK);
        centerPanelContainer = new JPanel(new GridLayout(0, 1, 0, 20));
        centerPanelContainer.setOpaque(false);
        // ADD APPLICATION LOGO
        JLabel logoLabel = new JLabel(ui.logo);
        // SETUP USERNAME CONFIRMATION SECTION
        usernameOuterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        usernameOuterPanel.setOpaque(false);
        usernameInnerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        usernameInnerPanel.setOpaque(false);
        JLabel usernameLabel = new JLabel("Confirm Username: ");
        usernameLabel.setForeground(Color.WHITE);
        ui.usernameInput = new JTextField(15);
        ui.usernameInput.setFont(new Font("Arial Unicode MS", Font.BOLD, 15));
        ui.usernameInput.setBackground(Color.WHITE);
        JButton usernameButton = new JButton("NEXT");
        usernameButton.setMnemonic(KeyEvent.VK_N);
        usernameButton.setBackground(Color.WHITE);
        // USERNAME VALIDATION ACTION
        ActionListener usernameAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!ui.usernameInput.getText().trim().isEmpty()) {
                    try {
                        if (checkAccount()) {
                            centerPanelContainer.remove(usernameOuterPanel);
                            centerPanelContainer.add(passwordOuterPanel);
                            centerPanelContainer.revalidate();
                            centerPanelContainer.repaint();
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
        // SETUP PASSWORD CONFIRMATION SECTION
        passwordOuterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        passwordOuterPanel.setOpaque(false);
        passwordInnerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        passwordInnerPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Confirm Password: ");
        passwordLabel.setForeground(Color.WHITE);
        ui.passwordInput = new JPasswordField(15);
        ui.passwordInput.setFont(new Font("Arial Unicode MS", Font.BOLD, 15));
        ui.passwordInput.setBackground(Color.WHITE);
        JButton passwordButton = new JButton("LOG IN");
        passwordButton.setMnemonic(KeyEvent.VK_L);
        passwordButton.setBackground(Color.WHITE);
        // PASSWORD VALIDATION ACTION
        ActionListener passwordAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!new String(ui.passwordInput.getPassword()).trim().isEmpty()) {
                    try {
                        if (checkPassword()) {
                            confirmed = true;
                            dispose();
                            return;
                        }
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
        // BUILD USERNAME PANEL HIERARCHY
        usernameInnerPanel.add(usernameLabel);
        usernameInnerPanel.add(ui.usernameInput);
        usernameInnerPanel.add(usernameButton);
        usernameInnerPanel.add(ui.warningLabel);
        usernameOuterPanel.add(usernameInnerPanel);
        centerPanelContainer.add(logoLabel);
        centerPanelContainer.add(usernameOuterPanel);
        // BUILD PASSWORD PANEL HIERARCHY
        passwordInnerPanel.add(passwordLabel);
        passwordInnerPanel.add(ui.passwordInput);
        passwordInnerPanel.add(passwordButton);
        passwordInnerPanel.add(ui.warningLabel);
        passwordOuterPanel.add(passwordInnerPanel);
        // FINALIZE DIALOG LAYOUT
        centerPanel.add(centerPanelContainer);
        this.add(centerPanel, BorderLayout.CENTER);
    }
    /*
    METHOD TO UPDATE WARNING LABEL IN SPECIFIED PANEL
    PARAMETERS:
    - targetPanel: PANEL TO ADD WARNING LABEL TO
    */
    private void setWarningLabel(JPanel targetPanel) {
        ui.warningLabel.setForeground(Color.WHITE);
        targetPanel.add(ui.warningLabel);
        targetPanel.revalidate();
        targetPanel.repaint();
    }
    /*
    METHOD TO HANDLE CRITICAL ERRORS
    DELETES DATA FILE AND EXITS APPLICATION
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
    METHOD TO CHECK IF DATA FILE EXISTS
    CREATES DEFAULT FILE WITH ADMIN CREDENTIALS IF NOT FOUND
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
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
    METHOD TO VALIDATE USERNAME AGAINST STORED VALUE
    RETURNS:
    - boolean: TRUE IF USERNAME MATCHES, FALSE OTHERWISE
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
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
    METHOD TO VALIDATE PASSWORD AGAINST STORED VALUE
    RETURNS:
    - boolean: TRUE IF PASSWORD MATCHES, FALSE OTHERWISE
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
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
    /*
    METHOD TO SHOW DIALOG AND RETURN CONFIRMATION STATUS
    RETURNS:
    - boolean: TRUE IF USER SUCCESSFULLY CONFIRMED, FALSE OTHERWISE
    */
    public boolean showAndConfirm() {
        this.setVisible(true);
        return confirmed;
    }
}