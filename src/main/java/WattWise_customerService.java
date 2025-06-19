package main.java;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.Scanner;
/*  
THIS CLASS REPRESENTS THE CUSTOMER SERVICE TICKET MANAGEMENT SYSTEM  
IT PROVIDES AN INTERFACE TO VIEW, EDIT AND MANAGE SERVICE TICKETS  
*/
public class WattWise_customerService extends JFrame {
    private WattWise_UIHelper ui = new WattWise_UIHelper();
    private serviceTicketData ticketData = new serviceTicketData();
    // CONSTRUCTOR FOR CUSTOMER SERVICE TICKET MANAGEMENT SYSTEM  
    // THROWS EXCEPTION IF DATA OPERATIONS FAIL  
    WattWise_customerService() throws Exception {
        ticketData.dataCheck(ticketData.data_recent);
        ticketData.dataCheck(ticketData.data_backup);
        ticketData.createLegacy();
        this.setTitle("WattWise for Enterprise | Customer Service Ticketing");
        this.setSize(1300, 500);
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
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        JLabel title = new JLabel("CUSTOMER SERVICE TICKET MANAGEMENT SYSTEM");
        title.setFont(new Font("Bank Gothic Light BT", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        adminPanel.add(titlePanel, BorderLayout.NORTH);
        // DEFINE TABLE COLUMN NAMES  
        String[] columnNames = {
            "Query No.",
            "Time Stamp",
            "Reporter Name",
            "Contact No.",
            "Issue Type",
            "Priority",
            "Description"
        };
        // LOAD TICKET DATA FROM FILE  
        try {
            ticketData.loadTicketsFromFile();
        } catch (Exception e1) {
            //ignore
        }
        // CREATE TABLE WITH LOADED DATA  
        DefaultTableModel model = new DefaultTableModel(ticketData.rowData, columnNames);
        JTable table = new JTable(model);
        table.setFont(ui.monospaceContent);
        table.setRowHeight(30);
        JScrollPane tableScroll = new JScrollPane(table);
        adminPanel.add(tableScroll, BorderLayout.CENTER);
        // CREATE CONTROL PANEL WITH ACTION BUTTONS  
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setBackground(ui.appUIColor);
        JButton saveButton = new JButton("📩 Save Table (Edit Mode)");
        saveButton.setMnemonic(KeyEvent.VK_S);
        JButton refreshButton = new JButton("📈 Refresh Table");
        refreshButton.setMnemonic(KeyEvent.VK_E);
        JButton deleteButton = new JButton("🗑️ Delete Row (AutoSave)");
        deleteButton.setMnemonic(KeyEvent.VK_DELETE);
        JButton restoreBackupButton = new JButton("🔁 Restore");
        restoreBackupButton.setMnemonic(KeyEvent.VK_R);
        JButton backButton = new JButton("↘️ Back to Home");
        backButton.setMnemonic(KeyEvent.VK_ESCAPE);
        controlPanel.add(saveButton);
        controlPanel.add(refreshButton);
        controlPanel.add(deleteButton);
        controlPanel.add(restoreBackupButton);
        controlPanel.add(backButton);
        adminPanel.add(controlPanel, BorderLayout.SOUTH);
        // SAVE BUTTON ACTION LISTENER  
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Save Changes?\n" + "WattWise will still provide a backup file.", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                    try {
                        ticketData.overwriteTicketsFromTable(table);
                    } catch (Exception e1) {
                        //ignore
                    }
                    JOptionPane.showMessageDialog(null, "Changes saved successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        // REFRESH BUTTON ACTION LISTENER  
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new WattWise_customerService().setVisible(true);
                } catch (Exception e1) {
                    //ignore
                }
            }
        });
        // DELETE BUTTON ACTION LISTENER  
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1 && JOptionPane.showConfirmDialog(null, "Delete selected ticket?", ui.dialogBox, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                    try {
                        ticketData.overwriteTicketsFromTable(table);
                        JOptionPane.showMessageDialog(null, "Ticket deleted and file updated.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        //ignore
                    }
                }
            }
        });
        // RESTORE BACKUP BUTTON ACTION LISTENER  
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
                                Scanner input = new Scanner(ticketData.data_backup);
                                PrintWriter writer = new PrintWriter(ticketData.data_recent);
                                while (input.hasNextLine()) {
                                    writer.println(input.nextLine());
                                }
                                input.close();
                                writer.close();
                                ticketData.loadTicketsFromFile();
                                dispose();
                                new WattWise_customerService().setVisible(true);
                                JOptionPane.showMessageDialog(null, "Backup restored successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case 1:
                            if (JOptionPane.showConfirmDialog(null, "You have entered:\n'" + choose[1] + "'.\n\n" + "Confirm by clicking 'YES'", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
                                Scanner input = new Scanner(ticketData.data_legacy);
                                PrintWriter writer = new PrintWriter(ticketData.data_recent);
                                while (input.hasNextLine()) {
                                    writer.println(input.nextLine());
                                }
                                input.close();
                                writer.close();
                                ticketData.loadTicketsFromFile();
                                dispose();
                                new WattWise_customerService().setVisible(true);
                                JOptionPane.showMessageDialog(null, "Backup restored successfully.", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                    }
                } catch (Exception ex) {
                    //ignore
                }
            }
        });
        // BACK BUTTON ACTION LISTENER  
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