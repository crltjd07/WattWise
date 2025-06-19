package main.java;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.Scanner;
/*
CLASS THAT HANDLES OUTAGE NOTIFICATION INTERFACE
PROVIDES GUI FOR REPORTING AND MANAGING POWER OUTAGES
EXTENDS JFRAME TO CREATE APPLICATION WINDOW
*/
public class WattWise_outageNotification extends JFrame {
    // HELPER CLASS FOR UI COMPONENTS AND STYLING
    private WattWise_UIHelper ui = new WattWise_UIHelper();
    // DATA HANDLER FOR OUTAGE NOTIFICATIONS
    private outageNotificationData outageData = new outageNotificationData();
    /*
    CONSTRUCTOR THAT SETS UP THE OUTAGE NOTIFICATION INTERFACE
    INITIALIZES DATA FILES AND CREATES GUI COMPONENTS
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    WattWise_outageNotification() throws Exception {
        // INITIALIZE DATA FILES
        outageData.dataCheck(outageData.data_recent);
        outageData.dataCheck(outageData.data_backup);
        outageData.createLegacy();
        // SET UP MAIN WINDOW PROPERTIES
        this.setTitle("WattWise for Enterprise | Outage Notification and Status Tracker");
        this.setSize(1500, 700);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // CREATE MAIN PANEL WITH BORDER LAYOUT
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.setBackground(Color.LIGHT_GRAY);
        // CREATE TITLE PANEL
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(ui.appUIColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel title = new JLabel("OUTAGE NOTIFICATION AND STATUS TRACKER");
        title.setFont(ui.serviceTitle);
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        adminPanel.add(titlePanel, BorderLayout.NORTH);
        // TABLE COLUMN NAMES
        String[] columnNames = {
            "Query No.",
            "Location / Area",
            "Start Time",
            "Estimated End Time",
            "Status",
            "Severity",
            "Time Reported",
            "Remark / Notes"
        };
        // LOAD EXISTING OUTAGE DATA
        try {
            outageData.loadNotificationFromFile();
        } catch (Exception e1) {
            //ignore
        }
        // CREATE TABLE WITH LOADED DATA
        DefaultTableModel model = new DefaultTableModel(outageData.rowData, columnNames);
        JTable table = new JTable(model);
        // STYLE THE TABLE
        table.setFont(ui.monospaceContent);
        table.setRowHeight(30);
        JScrollPane tableScroll = new JScrollPane(table);
        adminPanel.add(tableScroll, BorderLayout.CENTER);
        // CREATE CONTROL PANEL WITH BUTTONS
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setBackground(ui.appUIColor);
        // CREATE CONTROL BUTTONS
        JButton saveButton = new JButton("📩 Save Table (Edit Mode)");
        saveButton.setMnemonic(KeyEvent.VK_S);
        JButton refreshButton = new JButton("📈 Refresh Panel");
        refreshButton.setMnemonic(KeyEvent.VK_E);
        JButton deleteButton = new JButton("🗑️ Delete Row (AutoSave)");
        deleteButton.setMnemonic(KeyEvent.VK_DELETE);
        JButton restoreBackupButton = new JButton("🔁 Restore");
        restoreBackupButton.setMnemonic(KeyEvent.VK_R);
        JButton backButton = new JButton("↘️ Back to Home");
        backButton.setMnemonic(KeyEvent.VK_ESCAPE);
        // ADD BUTTONS TO CONTROL PANEL
        controlPanel.add(saveButton);
        controlPanel.add(refreshButton);
        controlPanel.add(deleteButton);
        controlPanel.add(restoreBackupButton);
        controlPanel.add(backButton);
        adminPanel.add(controlPanel, BorderLayout.SOUTH);
        // START OF REPORT FORM SECTION
        JPanel addReport = new JPanel(new BorderLayout());
        addReport.setBackground(ui.appUIColorNormal);
        JPanel ticketPanel = new JPanel(new BorderLayout());
        ticketPanel.setOpaque(false);
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        northPanel.setOpaque(false);
        JPanel gridNorthPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        gridNorthPanel.setOpaque(false);
        JLabel titleAddReport = new JLabel("REPORT FORM");
        titleAddReport.setFont(new Font("Bank Gothic Light BT", Font.BOLD, 30));
        titleAddReport.setForeground(Color.WHITE);
        gridNorthPanel.add(titleAddReport);
        northPanel.add(gridNorthPanel);
        ticketPanel.add(northPanel, BorderLayout.NORTH);
        // CREATE FORM COMPONENTS
        JPanel form = new JPanel(new BorderLayout());
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JPanel gridForm = new JPanel(new GridLayout(0, 1, 0, 0));
        // OUTAGE INFORMATION SECTION
        JPanel firstPartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstPartTitle.setOpaque(false);
        JLabel reporterInfo = new JLabel("OUTAGE INFORMATION");
        reporterInfo.setFont(new Font("Lucida Sans", Font.BOLD, 20));
        reporterInfo.setForeground(ui.appUIColorNormal);
        firstPartTitle.add(reporterInfo);
        gridForm.add(firstPartTitle);
        // AREA FIELD
        JPanel areaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        areaPanel.setOpaque(false);
        areaPanel.add(new JLabel("Area of Incidence: "));
        JTextField areaField = new JTextField(15);
        areaField.setOpaque(false);
        areaField.setFont(ui.monospaceContent);
        areaPanel.add(areaField);
        gridForm.add(areaPanel);
        // START TIME FIELD
        JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startTimePanel.setOpaque(false);
        startTimePanel.add(new JLabel("Time of Incidence: "));
        JTextField startTimeField = new JTextField("YYYY-MM-DD HH:MM AM/PM", 22);
        startTimeField.setOpaque(false);
        startTimeField.setFont(ui.monospaceContent);
        startTimePanel.add(startTimeField);
        gridForm.add(startTimePanel);
        // ESTIMATED END TIME FIELD
        JPanel estimateEndPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        estimateEndPanel.setOpaque(false);
        estimateEndPanel.add(new JLabel("Restoration Time Period: "));
        JTextField estimateEndField = new JTextField("YYYY-MM-DD HH:MM AM/PM", 22);
        estimateEndField.setOpaque(false);
        estimateEndField.setFont(ui.monospaceContent);
        estimateEndPanel.add(estimateEndField);
        gridForm.add(estimateEndPanel);
        // ISSUE DETAILS SECTION
        JPanel secondPartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secondPartTitle.setOpaque(false);
        JLabel issueDetails = new JLabel("ISSUE DETAILS");
        issueDetails.setFont(new Font("Lucida Sans", Font.BOLD, 20));
        issueDetails.setForeground(ui.appUIColorNormal);
        secondPartTitle.add(issueDetails);
        gridForm.add(secondPartTitle);
        // STATUS COMBO BOX
        String[] statusString = {
            "---Select Status---",
            "Reported",
            "In Progress",
            "Resolved"
        };
        JPanel statusTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusTypePanel.setOpaque(false);
        statusTypePanel.add(new JLabel("Current Status: "));
        JComboBox < String > status = new JComboBox < > (statusString);
        status.setFont(ui.monospaceContent);
        statusTypePanel.add(status);
        gridForm.add(statusTypePanel);
        // SEVERITY COMBO BOX
        String[] severityString = {
            "---Select Severity---",
            "Low",
            "Medium",
            "High"
        };
        JPanel severityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        severityPanel.setOpaque(false);
        severityPanel.add(new JLabel("Level of Severity: "));
        JComboBox < String > severity = new JComboBox < > (severityString);
        severity.setFont(ui.monospaceContent);
        severityPanel.add(severity);
        gridForm.add(severityPanel);
        form.add(gridForm, BorderLayout.NORTH);
        // REMARKS SECTION
        JPanel thirdPartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        thirdPartTitle.setOpaque(false);
        JLabel issueDescription = new JLabel("OTHER REMARKS");
        issueDescription.setFont(new Font("Lucida Sans", Font.BOLD, 20));
        issueDescription.setForeground(ui.appUIColorNormal);
        thirdPartTitle.add(issueDescription);
        form.add(thirdPartTitle, BorderLayout.CENTER);
        // REMARKS TEXT AREA
        JTextArea remarkArea = new JTextArea(7, 15);
        remarkArea.setOpaque(false);
        remarkArea.setFont(ui.monospaceContent);
        remarkArea.setLineWrap(true);
        remarkArea.setWrapStyleWord(true);
        form.add(new JScrollPane(remarkArea), BorderLayout.SOUTH);
        // SCROLL PANE FOR FORM
        JScrollPane scrollPane = new JScrollPane(form, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ticketPanel.add(scrollPane, BorderLayout.CENTER);
        // FORM FOOTER WITH SUBMIT BUTTONS
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.setBackground(ui.appUIColor);
        // CERTIFICATION CHECKBOX
        JPanel certifyPanel = new JPanel();
        certifyPanel.setOpaque(false);
        JCheckBox certify = new JCheckBox("I confirm the details above are accurate.");
        certify.setForeground(Color.WHITE);
        certify.setOpaque(false);
        certifyPanel.add(certify);
        southPanel.add(certifyPanel, BorderLayout.CENTER);
        // FORM BUTTONS
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton resetButton = new JButton("RESET");
        buttonPanel.add(resetButton);
        JButton submitButton = new JButton("SUBMIT");
        buttonPanel.add(submitButton);
        // RESET BUTTON ACTION
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Reset button clicked. Do you want" + "\nto continue?", ui.dialogBox, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    areaField.setText("");
                    startTimeField.setText("YYYY-MM-DD HH:MM AM/PM");
                    estimateEndField.setText("YYYY-MM-DD HH:MM AM/PM");
                    status.setSelectedIndex(0);
                    severity.setSelectedIndex(0);
                    certify.setSelected(false);
                    remarkArea.setText("");
                }
            }
        });
        // SUBMIT BUTTON ACTION
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (areaField.getText().trim().isEmpty() || startTimeField.getText().trim().isEmpty() || startTimeField.getText().length() < 19 || estimateEndField.getText().trim().isEmpty() || estimateEndField.getText().length() < 19 || status.getSelectedIndex() == 0 || severity.getSelectedIndex() == 0 || remarkArea.getText().trim().isEmpty() || !certify.isSelected()) {
                    JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Fill out all required fields.\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        outageData.submitOutageNotification(areaField, startTimeField, estimateEndField, status, severity, remarkArea);
                        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Ticket saved!\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new WattWise_outageNotification().setVisible(true);
                    } catch (Exception e1) {
                        //ignore
                    }
                }
            }
        });
        southPanel.add(buttonPanel, BorderLayout.EAST);
        ticketPanel.add(southPanel, BorderLayout.SOUTH);
        addReport.add(ticketPanel, BorderLayout.CENTER);
        adminPanel.add(addReport, BorderLayout.WEST);
        // END OF FORM SECTION
        // SAVE BUTTON ACTION
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Save Changes?\n" + "WattWise will still provide a backup file.", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        outageData.overwriteNotificationFromTable(table);
                    } catch (Exception e1) {
                        //ignore
                    }
                    JOptionPane.showMessageDialog(null, "Changes saved successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        // REFRESH BUTTON ACTION
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new WattWise_outageNotification().setVisible(true);
                } catch (Exception e1) {
                    //ignore
                }
            }
        });
        // DELETE BUTTON ACTION
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1 && JOptionPane.showConfirmDialog(null, "Delete selected ticket?", ui.dialogBox, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                    try {
                        outageData.overwriteNotificationFromTable(table);
                        JOptionPane.showMessageDialog(null, "Ticket deleted and file updated.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        //ignore
                    }
                }
            }
        });
        // RESTORE BUTTON ACTION
        restoreBackupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Object[] choose = new Object[2];
                    choose[0] = "🔁 Restore Last Session";
                    choose[1] = "❗ Restore All Data";
                    int destination = JOptionPane.showOptionDialog(null, "Choose type of data restoration.", ui.dialogBox, JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, ui.iconSmall, choose, null);
                    switch (destination) {
                        case 0:
                            if (JOptionPane.showConfirmDialog(null, "You have entered:\n'" + choose[0] + "'.\n\n" + "Confirm by clicking 'YES'", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                                Scanner input = new Scanner(outageData.data_backup);
                                PrintWriter writer = new PrintWriter(outageData.data_recent);
                                while (input.hasNextLine()) {
                                    writer.println(input.nextLine());
                                }
                                input.close();
                                writer.close();
                                outageData.loadNotificationFromFile();
                                dispose();
                                new WattWise_outageNotification().setVisible(true);
                                JOptionPane.showMessageDialog(null, "Backup restored successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case 1:
                            if (JOptionPane.showConfirmDialog(null, "You have entered:\n'" + choose[1] + "'.\n\n" + "Confirm by clicking 'YES'", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                                Scanner input = new Scanner(outageData.data_legacy);
                                PrintWriter writer = new PrintWriter(outageData.data_recent);
                                while (input.hasNextLine()) {
                                    writer.println(input.nextLine());
                                }
                                input.close();
                                writer.close();
                                outageData.loadNotificationFromFile();
                                dispose();
                                new WattWise_outageNotification().setVisible(true);
                                JOptionPane.showMessageDialog(null, "Backup restored successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                    }
                } catch (Exception ex) {
                    //ignore
                }
            }
        });
        // BACK BUTTON ACTION
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane("Are you sure you want to exit?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(ui.dialogBox);
                dialog.setVisible(true);
                int confirm = (int) optionPane.getValue();
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        this.add(adminPanel);
    }
}