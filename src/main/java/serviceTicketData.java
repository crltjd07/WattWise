package main.java;
import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
/*
CLASS THAT HANDLES SERVICE TICKET DATA OPERATIONS INCLUDING 
CREATING, LOADING, AND MANAGING TICKETS IN MULTIPLE FILES
*/
public class serviceTicketData {
    // FILES FOR STORING TICKET DATA IN DIFFERENT VERSIONS
    protected File data_recent = new File("data\\serviceTicketData.txt");
    protected File data_backup = new File("data\\serviceTicketDataOld.txt");
    protected File data_legacy = new File("data\\serviceTicketDataLegacy.txt");
    Object[][] rowData = new Object[0][7];
    Scanner input, inputBackup, inputLegacy;
    /*
    METHOD TO SUBMIT A NEW TICKET TO THE SYSTEM
    TAKES INPUT FIELDS AS PARAMETERS:
    JTextField name - NAME OF REPORTER
    JTextField contact - CONTACT INFORMATION
    JComboBox<String> issue - TYPE OF ISSUE
    JComboBox<String> priority - PRIORITY LEVEL
    JTextArea textAreaForIssue - DESCRIPTION OF ISSUE
    WRITES DATA TO RECENT, BACKUP AND LEGACY FILES
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void submitTicket(JTextField name, JTextField contact, JComboBox < String > issue, JComboBox < String > priority, JTextArea textAreaForIssue) throws Exception {
        input = new Scanner(data_recent);
        inputBackup = new Scanner(data_backup);
        inputLegacy = new Scanner(data_legacy);
        StringBuilder oldData = new StringBuilder();
        StringBuilder oldDataBackup = new StringBuilder();
        StringBuilder oldDataLegacy = new StringBuilder();
        while (input.hasNextLine()) {
            oldData.append(input.nextLine() + "\n");
        }
        while (inputBackup.hasNextLine()) {
            oldDataBackup.append(inputBackup.nextLine() + "\n");
        }
        while (inputLegacy.hasNextLine()) {
            oldDataLegacy.append(inputLegacy.nextLine() + "\n");
        }
        input.close();
        inputBackup.close();
        PrintWriter recentWriter = new PrintWriter(data_recent);
        recentWriter.println(oldData.toString());
        recentWriter.println("Time Stamp:: " + new java.util.Date());
        recentWriter.println("Reporter Name:: " + name.getText());
        recentWriter.println("Contact No.:: " + contact.getText());
        recentWriter.println("Issue Type:: " + issue.getSelectedItem().toString());
        recentWriter.println("Priority:: " + priority.getSelectedItem().toString());
        recentWriter.println("Description:: " + textAreaForIssue.getText());
        recentWriter.println("---END OF TICKET---");
        recentWriter.println();
        recentWriter.close();
        PrintWriter backupWriter = new PrintWriter(data_backup);
        backupWriter.println(oldDataBackup.toString());
        backupWriter.println("Time Stamp:: " + new java.util.Date());
        backupWriter.println("Reporter Name:: " + name.getText());
        backupWriter.println("Contact No.:: " + contact.getText());
        backupWriter.println("Issue Type:: " + issue.getSelectedItem().toString());
        backupWriter.println("Priority:: " + priority.getSelectedItem().toString());
        backupWriter.println("Description:: " + textAreaForIssue.getText());
        backupWriter.println("---END OF TICKET---");
        backupWriter.println();
        backupWriter.close();
        PrintWriter legacyWriter = new PrintWriter(data_legacy);
        legacyWriter.println(oldDataLegacy.toString());
        legacyWriter.println("Time Stamp:: " + new java.util.Date());
        legacyWriter.println("Reporter Name:: " + name.getText());
        legacyWriter.println("Contact No.:: " + contact.getText());
        legacyWriter.println("Issue Type:: " + issue.getSelectedItem().toString());
        legacyWriter.println("Priority:: " + priority.getSelectedItem().toString());
        legacyWriter.println("Description:: " + textAreaForIssue.getText());
        legacyWriter.println("---END OF TICKET---");
        legacyWriter.println();
        legacyWriter.close();
    }
    /*
    METHOD TO LOAD TICKETS FROM FILE INTO MEMORY
    COUNTS TICKETS AND CREATES ARRAY OF TICKET DATA
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void loadTicketsFromFile() throws Exception {
        if (!data_recent.exists()) {
            dataCheck(data_recent);
        }
        input = new Scanner(data_recent);
        int ticketCount = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.equals("---END OF TICKET---")) {
                ticketCount++;
            }
        }
        input.close();
        Object[][] tempData = new Object[ticketCount][7];
        StringBuilder descriptionBuilder = new StringBuilder("");
        input = new Scanner(data_recent);
        int row = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.startsWith("Time Stamp:: ")) {
                tempData[row][1] = line.substring(13).trim();
            } else if (line.startsWith("Reporter Name:: ")) {
                tempData[row][2] = line.substring(16).trim();
            } else if (line.startsWith("Contact No.:: ")) {
                tempData[row][3] = line.substring(14).trim();
            } else if (line.startsWith("Issue Type:: ")) {
                tempData[row][4] = line.substring(13).trim();
            } else if (line.startsWith("Priority:: ")) {
                tempData[row][5] = line.substring(11).trim();
            } else if (line.startsWith("Description:: ")) {
                descriptionBuilder.setLength(0);
                descriptionBuilder.append(line.substring(14).trim());
                while (input.hasNextLine()) {
                    String nextLine = input.nextLine().trim();
                    if (nextLine.equals("---END OF TICKET---")) {
                        break;
                    }
                    descriptionBuilder.append("\n" + nextLine);
                }
                tempData[row][6] = descriptionBuilder.toString();
                tempData[row][0] = "WattWiseCT-" + (row + 1);
                row++;
            }
        }
        input.close();
        rowData = tempData;
    }
    /*
    METHOD TO OVERWRITE TICKETS IN FILE WITH DATA FROM TABLE
    TAKES JTable table AS PARAMETER CONTAINING TICKET DATA
    CREATES BACKUP BEFORE OVERWRITING MAIN FILE
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void overwriteTicketsFromTable(JTable table) throws Exception {
        input = new Scanner(data_recent);
        PrintWriter backupWriter = new PrintWriter(data_backup);
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            backupWriter.println(line);
        }
        backupWriter.close();
        PrintWriter recentWriter = new PrintWriter(data_recent);
        recentWriter.println("WATTWISE ENERGY SOLUTIONS v.1.0.0 [APP VERSION]");
        recentWriter.println("Java Desktop Utility for Energy Awareness and Optimization");
        recentWriter.println();
        recentWriter.println("CRITICAL INFORMATION [DO NOT DELETE/ALTER ANYTHING BELOW!]");
        recentWriter.println();
        for (int i = 0; i < table.getRowCount(); i++) {
            recentWriter.println("Time Stamp:: " + table.getValueAt(i, 1));
            recentWriter.println("Reporter Name:: " + table.getValueAt(i, 2));
            recentWriter.println("Contact No.:: " + table.getValueAt(i, 3));
            recentWriter.println("Issue Type:: " + table.getValueAt(i, 4));
            recentWriter.println("Priority:: " + table.getValueAt(i, 5));
            recentWriter.println("Description:: " + table.getValueAt(i, 6));
            recentWriter.println("---END OF TICKET---");
            recentWriter.println();
        }
        recentWriter.close();
    }
    /*
    METHOD TO CHECK IF DATA FILE EXISTS AND CREATE IT IF NOT
    TAKES File file AS PARAMETER TO CHECK
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void dataCheck(File file) throws Exception {
        if (!file.exists()) {
            PrintWriter createFileData = new PrintWriter(file);
            createFileData.println("WATTWISE ENERGY SOLUTIONS v.1.0.0 [APP VERSION]");
            createFileData.println("Java Desktop Utility for Energy Awareness and Optimization");
            createFileData.println();
            createFileData.println("CRITICAL INFORMATION [DO NOT DELETE/ALTER ANYTHING BELOW!]");
            createFileData.println();
            createFileData.close();
        }
    }
    /*
    METHOD TO CREATE LEGACY FILE IF IT DOES NOT EXIST
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void createLegacy() throws Exception {
        if (!data_legacy.exists()) {
            PrintWriter createFileData = new PrintWriter(data_legacy);
            createFileData.close();
        }
    }
}