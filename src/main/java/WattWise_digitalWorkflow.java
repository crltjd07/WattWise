package main.java;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
/*
THIS CLASS PROVIDES A DIGITAL WORKFLOW MANAGEMENT INTERFACE FOR WATTWISE ENTERPRISE.
IT ALLOWS STAFF TO CREATE, VIEW, EDIT, AND DELETE WORKFLOW TASKS.
*/
public class WattWise_digitalWorkflow extends JFrame {
    private WattWise_UIHelper ui = new WattWise_UIHelper(); // HELPER CLASS FOR UI ELEMENTS
    private digitalWorkflowData workflowData = new digitalWorkflowData(); // HANDLES WORKFLOW DATA OPERATIONS
    /*
    CONSTRUCTOR SETS UP THE MAIN WORKFLOW MANAGEMENT INTERFACE.
    THROWS EXCEPTION IF FILE OPERATIONS FAIL DURING INITIALIZATION.
    */
    WattWise_digitalWorkflow() throws Exception {
        // INITIALIZE DATA FILES
        workflowData.dataCheck(workflowData.data_recent);
        workflowData.dataCheck(workflowData.data_backup);
        workflowData.createLegacy();
        // SET UP MAIN FRAME PROPERTIES
        this.setTitle("WattWise for Enterprise | Digital Workflow Management Tool");
        this.setSize(1300, 600);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // MAIN PANEL CONTAINER
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.setBackground(Color.LIGHT_GRAY);
        // TITLE PANEL
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(ui.appUIColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel title = new JLabel("DIGITAL WORKFLOW MANAGEMENT TOOL");
        title.setFont(ui.serviceTitle);
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        adminPanel.add(titlePanel, BorderLayout.NORTH);
        // TABLE COLUMN NAMES
        String[] columnNames = {
            "Query No.",
            "Assigned Staff",
            "Task Type",
            "Location",
            "Status",
            "Time Assigned",
            "Notes"
        };
        // LOAD EXISTING WORKFLOW DATA
        try {
            workflowData.loadWorkflowFromFile();
        } catch (Exception e1) {
            //ignore
        }
        // SET UP TABLE WITH LOADED DATA
        DefaultTableModel model = new DefaultTableModel(workflowData.rowData, columnNames);
        JTable table = new JTable(model);
        table.setFont(ui.monospaceContent);
        table.setRowHeight(30);
        JScrollPane tableScroll = new JScrollPane(table);
        adminPanel.add(tableScroll, BorderLayout.CENTER);
        // CONTROL PANEL WITH ACTION BUTTONS
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setBackground(ui.appUIColor);
        // INITIALIZE BUTTONS
        JButton saveButton = new JButton("📩 Save Table (Edit Mode)");
        saveButton.setMnemonic(KeyEvent.VK_S);
        JButton staffButton = new JButton("🏢 Add Staff");
        staffButton.setMnemonic(KeyEvent.VK_F);
        JButton refreshButton = new JButton("📈 Refresh Panel");
        refreshButton.setMnemonic(KeyEvent.VK_E);
        JButton showButton = new JButton("📄 Show Form Panel");
        showButton.setMnemonic(KeyEvent.VK_P);
        JButton deleteButton = new JButton("🗑️ Delete Row (AutoSave)");
        deleteButton.setMnemonic(KeyEvent.VK_DELETE);
        JButton restoreBackupButton = new JButton("🔁 Restore");
        restoreBackupButton.setMnemonic(KeyEvent.VK_R);
        JButton backButton = new JButton("↘️ Back to Home");
        backButton.setMnemonic(KeyEvent.VK_ESCAPE);
        // ADD BUTTONS TO CONTROL PANEL
        controlPanel.add(saveButton);
        controlPanel.add(staffButton);
        controlPanel.add(refreshButton);
        controlPanel.add(showButton);
        controlPanel.add(deleteButton);
        controlPanel.add(restoreBackupButton);
        controlPanel.add(backButton);
        adminPanel.add(controlPanel, BorderLayout.SOUTH);
        //START OF FORM SECTION
        JPanel addFlow = new JPanel(new BorderLayout());
        addFlow.setBackground(ui.appUIColorNormal);
        JPanel workflowPanel = new JPanel(new BorderLayout());
        workflowPanel.setOpaque(false);
        // FORM TITLE SECTION
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        northPanel.setOpaque(false);
        JPanel gridNorthPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        gridNorthPanel.setOpaque(false);
        JLabel titleAddFlow = new JLabel("WORKFLOW MANAGEMENT");
        titleAddFlow.setFont(new Font("Bank Gothic Light BT", Font.BOLD, 30));
        titleAddFlow.setForeground(Color.WHITE);
        gridNorthPanel.add(titleAddFlow);
        northPanel.add(gridNorthPanel);
        workflowPanel.add(northPanel, BorderLayout.NORTH);
        // MAIN FORM CONTROLS
        JPanel form = new JPanel(new BorderLayout());
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JPanel gridForm = new JPanel(new GridLayout(0, 1, 0, 0));
        // STAFF LIST HANDLING
        File staffList = new File("data\\WattWiseCompleteStaffList.txt");
        if (!staffList.exists()) {
            PrintWriter staffListCreate = new PrintWriter(staffList);
            staffListCreate.close();
        }
        // COUNT EXISTING STAFF ENTRIES
        Scanner countScan = new Scanner(staffList);
        int size = 1;
        while (countScan.hasNextLine()) {
            String line = countScan.nextLine().trim();
            if (!line.isEmpty()) size++;
        }
        countScan.close();
        // LOAD STAFF INTO COMBOBOX
        Scanner staffScan = new Scanner(staffList);
        String[] staffString = new String[size];
        int index = 0;
        while (staffScan.hasNextLine() && index < size) {
            String line = staffScan.nextLine().trim();
            if (!line.isEmpty()) {
                staffString[index + 1] = line;
                index++;
            }
        }
        staffString[0] = "---Select Staff---";
        staffScan.close();
        // STAFF SELECTION FIELD
        JPanel assignedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        assignedPanel.setOpaque(false);
        assignedPanel.add(new JLabel("Assigned Staff: "));
        JComboBox < String > staffField = new JComboBox < > (staffString);
        staffField.setFont(ui.monospaceContent);
        assignedPanel.add(staffField);
        gridForm.add(assignedPanel);
        // TASK TYPE SELECTION
        String[] taskString = {
            "---Select Task---",
            "Meter Reading",
            "Meter Installation",
            "Meter Replacement",
            "Smart Meter Calibration",
            "Meter Disconnection",
            "Meter Reconnection",
            "Tamper Detection/Inspection",
            "Remote Meter Diagnosis",
            "Demand Reset/Load Profile Download",
            "Power Line Inspection",
            "Pole Installation",
            "Pole Replacement",
            "Line Clearance (Tree Trimming)",
            "Underground Cable Check",
            "Transformer Maintenance",
            "Capacitor Bank Maintenance",
            "Switchgear Operation",
            "Substation Equipment Check",
            "Grid Balancing Task",
            "Outage Reporting",
            "Outage Investigation",
            "Fault Isolation",
            "Emergency Crew Dispatch",
            "Power Restoration",
            "Temporary Power Setup",
            "Lightning Damage Inspection",
            "Accident Site Safety Securing",
            "Preventive Line Maintenance",
            "Preventive Substation Maintenance",
            "Load Balancing Check",
            "Voltage Regulation Test",
            "Relay Testing",
            "Thermographic Inspection",
            "New Connection Setup",
            "Customer Complaint Handling",
            "Billing Dispute Investigation",
            "Tariff Explanation Visit",
            "Final Reading",
            "Meter Transfer",
            "Energy Theft Investigation",
            "Safety Inspection",
            "Regulatory Compliance Audit",
            "Environmental Inspection",
            "Unauthorized Connection Check",
            "Smart Meter Data Sync",
            "Remote Disconnection/Reconnection",
            "Remote Firmware Update",
            "Real-time Data Validation",
            "On-Site Crew Training",
            "Safety Drill Task",
            "Equipment Inventory Audit",
            "Vehicle Maintenance Task",
            "Site Access Coordination"
        };
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskPanel.setOpaque(false);
        taskPanel.add(new JLabel("Task Type: "));
        JComboBox < String > taskField = new JComboBox < > (taskString);
        taskField.setFont(ui.monospaceContent);
        taskPanel.add(taskField);
        gridForm.add(taskPanel);
        // LOCATION FIELD
        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        locationPanel.setOpaque(false);
        locationPanel.add(new JLabel("Location: "));
        JTextField locationField = new JTextField(20);
        locationField.setOpaque(false);
        locationField.setFont(ui.monospaceContent);
        locationPanel.add(locationField);
        gridForm.add(locationPanel);
        // STATUS SELECTION
        String[] statusString = {
            "---Select Status---",
            "Full-time",
            "Part-Time",
            "Contract",
            "Temporary"
        };
        JPanel statusTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusTypePanel.setOpaque(false);
        statusTypePanel.add(new JLabel("Status: "));
        JComboBox < String > statusField = new JComboBox < > (statusString);
        statusField.setFont(ui.monospaceContent);
        statusTypePanel.add(statusField);
        gridForm.add(statusTypePanel);
        form.add(gridForm, BorderLayout.NORTH);
        // NOTES FIELD
        JTextArea noteArea = new JTextArea(7, 15);
        noteArea.setOpaque(false);
        noteArea.setFont(ui.monospaceContent);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        form.add(new JScrollPane(noteArea), BorderLayout.SOUTH);
        // SCROLL PANE FOR FORM
        JScrollPane scrollPane = new JScrollPane(form, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        workflowPanel.add(scrollPane, BorderLayout.CENTER);
        // FORM FOOTER WITH ACTION BUTTONS
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
                    staffField.setSelectedIndex(0);
                    taskField.setSelectedIndex(0);
                    locationField.setText("");
                    statusField.setSelectedIndex(0);
                    noteArea.setText("");
                    certify.setSelected(false);
                }
            }
        });
        // SUBMIT BUTTON ACTION
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (staffField.getSelectedIndex() == 0 || taskField.getSelectedIndex() == 0 || locationField.getText().trim().isEmpty() || statusField.getSelectedIndex() == 0 || noteArea.getText().trim().isEmpty() || !certify.isSelected()) {
                    JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Fill out all required fields.\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        workflowData.submitDigitalWorkflow(staffField, taskField, locationField, statusField, noteArea);
                        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Ticket saved!\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new WattWise_digitalWorkflow().setVisible(true);
                    } catch (Exception e1) {
                        //ignore
                    }
                }
            }
        });
        southPanel.add(buttonPanel, BorderLayout.EAST);
        workflowPanel.add(southPanel, BorderLayout.SOUTH);
        addFlow.add(workflowPanel, BorderLayout.CENTER);
        addFlow.setVisible(false);
        adminPanel.add(addFlow, BorderLayout.WEST);
        //END OF FORM SECTION
        // SAVE BUTTON ACTION - SAVES TABLE EDITS
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Save Changes?\n" + "WattWise will still provide a backup file.", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        workflowData.overwriteWorkflowFromTable(table);
                    } catch (Exception e1) {
                        //ignore
                    }
                    JOptionPane.showMessageDialog(null, "Changes saved successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        // STAFF BUTTON ACTION - ADDS NEW STAFF MEMBER
        staffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "[MSG] Add Staff on database.\n" + "[MSG] Format says: DE LA CRUZ, Juan, Cortez\n" + "[MSG] 'DE LA CRUZ' is FAMILY NAME.\n" + "[MSG] 'Juan' is GIVEN NAME.\n" + "[MSG] 'Cortez is MIDDLE NAME.\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                try {
                    if (name != null)
                        if (JOptionPane.showConfirmDialog(null, "You have entered: " + name + "\n[MSG] Proceed on saving?", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, ui.iconSmall) == JOptionPane.YES_OPTION) {
                            Scanner input = new Scanner(staffList);
                            StringBuilder inputData = new StringBuilder();
                            while (input.hasNextLine()) {
                                inputData.append(input.nextLine() + "\n");
                            }
                            input.close();
                            PrintWriter recentWriter = new PrintWriter(staffList);
                            recentWriter.println(inputData.toString());
                            recentWriter.println(name);
                            recentWriter.close();
                            dispose();
                            new WattWise_digitalWorkflow().setVisible(true);
                        }
                } catch (Exception e5) {
                    //ignore
                }
            }
        });
        // REFRESH BUTTON ACTION - RELOADS INTERFACE
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new WattWise_digitalWorkflow().setVisible(true);
                } catch (Exception e1) {
                    //ignore
                }
            }
        });
        // SHOW/HIDE FORM PANEL BUTTON ACTION
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!addFlow.isShowing()) {
                    addFlow.setVisible(true);
                    showButton.setText("📄 Hide Form Panel");
                } else {
                    addFlow.setVisible(false);
                    showButton.setText("📄 Show Form Panel");
                }
            }
        });
        // DELETE BUTTON ACTION - REMOVES SELECTED ROW
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1 && JOptionPane.showConfirmDialog(null, "Delete selected ticket?", ui.dialogBox, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                    try {
                        workflowData.overwriteWorkflowFromTable(table);
                        JOptionPane.showMessageDialog(null, "Ticket deleted and file updated.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        //ignore
                    }
                }
            }
        });
        // RESTORE BACKUP BUTTON ACTION - RECOVERS DATA FROM BACKUP
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
                                Scanner input = new Scanner(workflowData.data_backup);
                                PrintWriter writer = new PrintWriter(workflowData.data_recent);
                                while (input.hasNextLine()) {
                                    writer.println(input.nextLine());
                                }
                                input.close();
                                writer.close();
                                workflowData.loadWorkflowFromFile();
                                dispose();
                                new WattWise_digitalWorkflow().setVisible(true);
                                JOptionPane.showMessageDialog(null, "Backup restored successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case 1:
                            if (JOptionPane.showConfirmDialog(null, "You have entered:\n'" + choose[1] + "'.\n\n" + "Confirm by clicking 'YES'", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                                Scanner input = new Scanner(workflowData.data_legacy);
                                PrintWriter writer = new PrintWriter(workflowData.data_recent);
                                while (input.hasNextLine()) {
                                    writer.println(input.nextLine());
                                }
                                input.close();
                                writer.close();
                                workflowData.loadWorkflowFromFile();
                                dispose();
                                new WattWise_digitalWorkflow().setVisible(true);
                                JOptionPane.showMessageDialog(null, "Backup restored successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                    }
                } catch (Exception ex) {
                    //ignore
                }
            }
        });
        // BACK BUTTON ACTION - RETURNS TO HOME SCREEN
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