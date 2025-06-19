package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
/*
THIS CLASS HANDLES PASSWORD CHANGE FUNCTIONALITY IN THE WATTWISE APPLICATION
IT PROVIDES A GUI INTERFACE FOR USERS TO CHANGE THEIR PASSWORD
AND UPDATES THE PASSWORD IN THE DATA FILE
*/
public class WattWise_changePassword extends JFrame {
    private File data = new File("data\\data.txt");
    private JPanel centerPanel, centerPanelContainer, passwordOuterPanel, passwordInnerPanel;
    WattWise_UIHelper ui = new WattWise_UIHelper();
    private JPasswordField newPasswordInput = new JPasswordField(15);
    private JPasswordField confirmPasswordInput = new JPasswordField(15);
    /*
    CONSTRUCTOR FOR THE PASSWORD CHANGE WINDOW
    INITIALIZES THE UI COMPONENTS AND SETS UP EVENT HANDLERS
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    public WattWise_changePassword() throws Exception {
        fileExists();
        this.setTitle("WattWise for Enterprise | Change Password");
        this.setSize(400, 600);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBackground(Color.BLACK);
        centerPanelContainer = new JPanel(new GridLayout(0, 1, 0, 20));
        centerPanelContainer.setOpaque(false);
        JLabel logoLabel = new JLabel(ui.logo);
        passwordOuterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        passwordOuterPanel.setOpaque(false);
        passwordInnerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        passwordInnerPanel.setOpaque(false);
        JLabel newPasswordLabel = new JLabel("New Password: ");
        newPasswordLabel.setForeground(Color.WHITE);
        newPasswordInput.setFont(new Font("Arial Unicode MS", Font.BOLD, 15));
        newPasswordInput.setBackground(Color.WHITE);
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password: ");
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordInput.setFont(new Font("Arial Unicode MS", Font.BOLD, 15));
        confirmPasswordInput.setBackground(Color.WHITE);
        JButton changeButton = new JButton("CHANGE");
        changeButton.setMnemonic(KeyEvent.VK_C);
        changeButton.setBackground(Color.WHITE);
        /*
        ACTION LISTENER FOR HANDLING PASSWORD CHANGE EVENTS
        VALIDATES INPUTS AND INITIATES PASSWORD UPDATE
        */
        ActionListener changeAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newPass = new String(newPasswordInput.getPassword()).trim();
                String confirmPass = new String(confirmPasswordInput.getPassword()).trim();
                if (newPass.isEmpty() || confirmPass.isEmpty()) {
                    ui.warningLabel.setText("MSG: Fill all password fields!");
                    setWarningLabel(passwordInnerPanel);
                    return;
                }
                if (!newPass.equals(confirmPass)) {
                    ui.warningLabel.setText("MSG: Passwords do not match!");
                    setWarningLabel(passwordInnerPanel);
                    return;
                }
                if (newPass.length() < 8) {
                    ui.warningLabel.setText("MSG: Min. password length [8]!");
                    setWarningLabel(passwordInnerPanel);
                    return;
                }
                try {
                    if (updatePassword(newPass)) {
                        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n[MSG] Password successfully updated.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                } catch (Exception ex) {
                    showErrorAndExit();
                }
            }
        };
        changeButton.addActionListener(changeAction);
        confirmPasswordInput.addActionListener(changeAction);
        passwordInnerPanel.add(newPasswordLabel);
        passwordInnerPanel.add(newPasswordInput);
        passwordInnerPanel.add(confirmPasswordLabel);
        passwordInnerPanel.add(confirmPasswordInput);
        passwordInnerPanel.add(changeButton);
        passwordInnerPanel.add(ui.warningLabel);
        passwordOuterPanel.add(passwordInnerPanel);
        centerPanelContainer.add(logoLabel);
        centerPanelContainer.add(passwordOuterPanel);
        centerPanel.add(centerPanelContainer);
        this.add(centerPanel, BorderLayout.CENTER);
    }
    /*
    METHOD TO DISPLAY WARNING MESSAGES IN THE UI
    TAKES A TARGET PANEL AS PARAMETER TO ADD THE WARNING LABEL TO
    */
    private void setWarningLabel(JPanel targetPanel) {
        ui.warningLabel.setForeground(Color.WHITE);
        targetPanel.add(ui.warningLabel);
        targetPanel.revalidate();
        targetPanel.repaint();
    }
    /*
    METHOD TO HANDLE CRITICAL ERRORS
    DISPLAYS ERROR MESSAGE AND ATTEMPTS TO RECOVER BY RESETTING DATA FILE
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
    METHOD TO ENSURE DATA FILE EXISTS
    CREATES DEFAULT DATA FILE IF IT DOESN'T EXIST
    THROWS EXCEPTION IF FILE CREATION FAILS
    */
    private void fileExists() throws Exception {
        if (!data.exists()) {
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
    METHOD TO UPDATE PASSWORD IN DATA FILE
    TAKES NEW PASSWORD STRING AS PARAMETER
    RETURNS TRUE IF UPDATE IS SUCCESSFUL, FALSE OTHERWISE
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    private boolean updatePassword(String newPassword) throws Exception {
        File tempFile = new File("temp_data.txt");
        Scanner input = new Scanner(data);
        PrintWriter writer = new PrintWriter(tempFile);
        boolean updated = false;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.toLowerCase().startsWith("password::")) {
                writer.println("Password:: " + newPassword);
                updated = true;
            } else {
                writer.println(line);
            }
        }
        input.close();
        writer.close();
        if (updated) {
            if (data.delete() && tempFile.renameTo(data)) {
                return true;
            }
        }
        return false;
    }
}