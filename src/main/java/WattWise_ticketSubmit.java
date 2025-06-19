package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/*  
THIS CLASS REPRESENTS THE SERVICE TICKET SUBMISSION FORM  
IT ALLOWS USERS TO REPORT TECHNICAL ISSUES AND CONCERNS  
*/
public class WattWise_ticketSubmit extends JFrame {
    private WattWise_UIHelper ui = new WattWise_UIHelper();
    private serviceTicketData ticketData = new serviceTicketData();
    // CONSTRUCTOR FOR TICKET SUBMISSION FORM  
    // THROWS EXCEPTION IF DATA OPERATIONS FAIL  
    WattWise_ticketSubmit() throws Exception {
        JOptionPane.showMessageDialog(null, "You are about to open the\nSERVICE TICKET SUBMISSION FORM.", "WattWise for Enterprise | Service Ticket Form Prompt", JOptionPane.PLAIN_MESSAGE);
        ticketForm();
    }
    // CREATES AND DISPLAYS THE TICKET SUBMISSION FORM  
    // THROWS EXCEPTION IF DATA OPERATIONS FAIL  
    void ticketForm() throws Exception {
        ticketData.dataCheck(ticketData.data_recent);
        ticketData.dataCheck(ticketData.data_backup);
        ticketData.createLegacy();
        this.setTitle("WattWise for Enterprise | Service Ticket Submission Form");
        this.setSize(700, 600);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel ticketPanel = new JPanel(new BorderLayout());
        ticketPanel.setBackground(Color.LIGHT_GRAY);
        // CREATE NORTH PANEL WITH FORM TITLE AND INSTRUCTIONS  
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        northPanel.setBackground(ui.appUIColor);
        JPanel gridNorthPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        gridNorthPanel.setOpaque(false);
        JLabel title = new JLabel("SERVICE TICKET SUBMISSION FORM");
        title.setFont(new Font("Bank Gothic Light BT", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        gridNorthPanel.add(title);
        String[] lines = {
            "This form is intended to report technical issues or concerns encountered. ",
            "Please provide accurate and detailed information to ensure a swift response."
        };
        JPanel lineWrap = new JPanel(new GridLayout(0, 1, 0, 0));
        lineWrap.setOpaque(false);
        for (int i = 0; i < lines.length; i++) {
            JLabel line = new JLabel(lines[i]);
            line.setFont(new Font("Corbel", Font.PLAIN, 16));
            line.setForeground(Color.WHITE);
            lineWrap.add(line);
        }
        gridNorthPanel.add(lineWrap);
        northPanel.add(gridNorthPanel);
        ticketPanel.add(northPanel, BorderLayout.NORTH);
        // CREATE MAIN FORM PANEL  
        JPanel form = new JPanel(new BorderLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JPanel gridForm = new JPanel(new GridLayout(0, 2, 0, 0));
        // REPORTER INFORMATION SECTION  
        JPanel firstPartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel reporterInfo = new JLabel("REPORTER INFORMATION");
        reporterInfo.setFont(new Font("Lucida Sans", Font.BOLD, 20));
        reporterInfo.setForeground(ui.appUIColorNormal);
        firstPartTitle.add(reporterInfo);
        gridForm.add(firstPartTitle);
        gridForm.add(new JLabel());
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Reporter Name: "));
        JTextField nameField = new JTextField(15);
        nameField.setOpaque(false);
        nameField.setFont(ui.monospaceContent);
        namePanel.add(nameField);
        gridForm.add(namePanel);
        JPanel contactPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contactPanel.add(new JLabel("Contact No: "));
        JTextField contactField = new JTextField(15);
        contactField.setOpaque(false);
        contactField.setFont(ui.monospaceContent);
        contactPanel.add(contactField);
        gridForm.add(contactPanel);
        // ISSUE DETAILS SECTION  
        JPanel secondPartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel issueDetails = new JLabel("ISSUE DETAILS");
        issueDetails.setFont(new Font("Lucida Sans", Font.BOLD, 20));
        issueDetails.setForeground(ui.appUIColorNormal);
        secondPartTitle.add(issueDetails);
        gridForm.add(secondPartTitle);
        gridForm.add(new JLabel());
        String[] issueString = {
            "---Select Issue---",
            "Power Outage",
            "Meter Issue",
            "Billing Concern",
            "Other"
        };
        JPanel issueTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        issueTypePanel.add(new JLabel("Issue Type: "));
        JComboBox < String > issue = new JComboBox < > (issueString);
        issue.setFont(ui.monospaceContent);
        issueTypePanel.add(issue);
        gridForm.add(issueTypePanel);
        String[] priorityString = {
            "---Select Priority---",
            "Low",
            "Medium",
            "High"
        };
        JPanel priorityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        priorityPanel.add(new JLabel("Priority: "));
        JComboBox < String > priority = new JComboBox < > (priorityString);
        priority.setFont(ui.monospaceContent);
        priorityPanel.add(priority);
        gridForm.add(priorityPanel);
        form.add(gridForm, BorderLayout.NORTH);
        // ISSUE DESCRIPTION SECTION  
        JPanel thirdPartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        JLabel issueDescription = new JLabel("ISSUE DESCRIPTION");
        issueDescription.setFont(new Font("Lucida Sans", Font.BOLD, 20));
        issueDescription.setForeground(ui.appUIColorNormal);
        thirdPartTitle.add(issueDescription);
        form.add(thirdPartTitle, BorderLayout.CENTER);
        JTextArea issueArea = new JTextArea(7, 20);
        issueArea.setOpaque(false);
        issueArea.setText("Customer Account No. xxx-xxx-xxxx\n\n" + "[Issue description starts here...]");
        issueArea.setFont(ui.monospaceContent);
        issueArea.setLineWrap(true);
        issueArea.setWrapStyleWord(true);
        form.add(new JScrollPane(issueArea), BorderLayout.SOUTH);
        ticketPanel.add(new JScrollPane(form), BorderLayout.CENTER);
        // CREATE SOUTH PANEL WITH SUBMIT/RESET BUTTONS  
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(ui.appUIColor);
        JPanel certifyPanel = new JPanel();
        certifyPanel.setOpaque(false);
        JCheckBox certify = new JCheckBox("I confirm the details above are accurate.");
        certify.setForeground(Color.WHITE);
        certify.setOpaque(false);
        certifyPanel.add(certify);
        southPanel.add(certifyPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton resetButton = new JButton("RESET");
        buttonPanel.add(resetButton);
        JButton submitButton = new JButton("SUBMIT");
        buttonPanel.add(submitButton);
        // RESET BUTTON ACTION LISTENER  
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Reset button clicked. Do you want" + "\nto continue?", ui.dialogBox, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    nameField.setText("");
                    contactField.setText("");
                    issue.setSelectedIndex(0);
                    priority.setSelectedIndex(0);
                    certify.setSelected(false);
                    issueArea.setText("Customer Account No. xxx-xxx-xxxx\n\n" + "[Issue description starts here...]");
                }
            }
        });
        // SUBMIT BUTTON ACTION LISTENER  
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().trim().isEmpty() || contactField.getText().trim().isEmpty() || issue.getSelectedIndex() == 0 || priority.getSelectedIndex() == 0 || issueArea.getText().trim().length() <= 34 || !certify.isSelected()) {
                    JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Fill out all required fields.\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        ticketData.submitTicket(nameField, contactField, issue, priority, issueArea);
                    } catch (Exception e1) {
                        //ignore
                    }
                    JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Ticket saved!\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        southPanel.add(buttonPanel, BorderLayout.EAST);
        ticketPanel.add(southPanel, BorderLayout.SOUTH);
        this.add(ticketPanel);
    }
}