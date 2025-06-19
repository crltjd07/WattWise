package main.java;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/*
THIS CLASS MANAGES OUTAGE NOTIFICATION DATA INCLUDING READING, WRITING, AND UPDATING RECORDS IN TEXT FILES.
IT HANDLES RECENT DATA, BACKUP DATA, AND LEGACY DATA SEPARATELY.
*/
public class outageNotificationData {

    // FILES USED TO STORE OUTAGE NOTIFICATION DATA
    protected File data_recent = new File("data\\outageNotificationData.txt"); // CURRENT DATA FILE
    protected File data_backup = new File("data\\outageNotificationDataOld.txt"); // BACKUP DATA FILE
    protected File data_legacy = new File("data\\outageNotificationDataLegacy.txt"); // LEGACY DATA FILE
    Object[][] rowData = new Object[0][8]; // STORES OUTAGE DATA IN A 2D ARRAY FORMAT
    Scanner input, inputBackup, inputLegacy; // SCANNERS TO READ DATA FROM FILES

    /*
    THIS METHOD SUBMITS A NEW OUTAGE NOTIFICATION BY WRITING DATA TO RECENT, BACKUP, AND LEGACY FILES.
    PARAMETERS:
    - area: TEXTFIELD CONTAINING THE AREA OF INCIDENCE
    - start: TEXTFIELD CONTAINING THE START TIME OF INCIDENCE
    - end: TEXTFIELD CONTAINING THE RESTORATION TIME PERIOD
    - status: COMBOBOX CONTAINING THE STATUS OF THE OUTAGE
    - severity: COMBOBOX CONTAINING THE SEVERITY LEVEL OF THE OUTAGE
    - remarkArea: TEXTAREA CONTAINING ADDITIONAL REMARKS
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void submitOutageNotification(JTextField area,
            JTextField start,
            JTextField end,
            JComboBox<String> status,
            JComboBox<String> severity,
            JTextArea remarkArea) throws Exception {
        
        input = new Scanner(data_recent);
        inputBackup = new Scanner(data_backup);
        inputLegacy = new Scanner(data_legacy);
        StringBuilder oldData = new StringBuilder(); // STORES EXISTING RECENT DATA
        StringBuilder oldDataBackup = new StringBuilder(); // STORES EXISTING BACKUP DATA
        StringBuilder oldDataLegacy = new StringBuilder(); // STORES EXISTING LEGACY DATA

        // READ EXISTING RECENT DATA
        while(input.hasNextLine()) {
            oldData.append(input.nextLine() + "\n");
        }
        // READ EXISTING BACKUP DATA
        while(inputBackup.hasNextLine()) {
            oldDataBackup.append(inputBackup.nextLine() + "\n");
        }
        // READ EXISTING LEGACY DATA
        while(inputLegacy.hasNextLine()) {
            oldDataLegacy.append(inputLegacy.nextLine() + "\n");
        }
        input.close();
        inputBackup.close();
        
        // WRITE UPDATED RECENT DATA INCLUDING NEW ENTRY
        PrintWriter recentWriter = new PrintWriter(data_recent);
        recentWriter.println(oldData.toString());
        recentWriter.println("Area of Incidence:: " + area.getText());
        recentWriter.println("Time of Incidence:: " + start.getText());
        recentWriter.println("Restoration Time Period:: " + end.getText());
        recentWriter.println("Status:: " + status.getSelectedItem().toString());
        recentWriter.println("Severity:: " + severity.getSelectedItem().toString());
        recentWriter.println("Time Reported:: " + new java.util.Date());
        recentWriter.println("Remark:: " + remarkArea.getText());
        recentWriter.println("---END OF OUTAGE REPORT---");
        recentWriter.println();
        recentWriter.close();
        
        // WRITE UPDATED BACKUP DATA INCLUDING NEW ENTRY
        PrintWriter backupWriter = new PrintWriter(data_backup);
        backupWriter.println(oldDataBackup.toString());
        backupWriter.println("Area of Incidence:: " + area.getText());
        backupWriter.println("Time of Incidence:: " + start.getText());
        backupWriter.println("Restoration Time Period:: " + end.getText());
        backupWriter.println("Status:: " + status.getSelectedItem().toString());
        backupWriter.println("Severity:: " + severity.getSelectedItem().toString());
        backupWriter.println("Time Reported:: " + new java.util.Date());
        backupWriter.println("Remark:: " + remarkArea.getText());
        backupWriter.println("---END OF OUTAGE REPORT---");
        backupWriter.println();
        backupWriter.close();
        
        // WRITE UPDATED LEGACY DATA INCLUDING NEW ENTRY
        PrintWriter legacyWriter = new PrintWriter(data_legacy);
        legacyWriter.println(oldDataLegacy.toString());
        legacyWriter.println("Area of Incidence:: " + area.getText());
        legacyWriter.println("Time of Incidence:: " + start.getText());
        legacyWriter.println("Restoration Time Period:: " + end.getText());
        legacyWriter.println("Status:: " + status.getSelectedItem().toString());
        legacyWriter.println("Severity:: " + severity.getSelectedItem().toString());
        legacyWriter.println("Time Reported:: " + new java.util.Date());
        legacyWriter.println("Remark:: " + remarkArea.getText());
        legacyWriter.println("---END OF OUTAGE REPORT---");
        legacyWriter.println();
        legacyWriter.close();
    }
    
    /*
    THIS METHOD LOADS OUTAGE NOTIFICATION DATA FROM THE RECENT FILE INTO A 2D ARRAY.
    IT COUNTS THE NUMBER OF REPORTS AND EXTRACTS DATA FOR DISPLAY IN A TABLE.
    THROWS EXCEPTION IF FILE OPERATIONS FAIL.
    */
    protected void loadNotificationFromFile() throws Exception {
        if (!data_recent.exists()) {
            dataCheck(data_recent); // CREATE FILE IF IT DOES NOT EXIST
        }

        input = new Scanner(data_recent);
        int ticketCount = 0; // COUNTS THE NUMBER OF OUTAGE REPORTS

        // COUNT TOTAL REPORTS IN FILE
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.equals("---END OF OUTAGE REPORT---")) {
                ticketCount++;
            }
        }
        input.close();

        Object[][] tempData = new Object[ticketCount][8]; // TEMPORARY STORAGE FOR REPORT DATA

        input = new Scanner(data_recent);
        int row = 0; // TRACKS CURRENT ROW IN TEMPDATA
        StringBuilder descriptionBuilder = new StringBuilder(); // STORES MULTI-LINE REMARKS

        // EXTRACT DATA FROM FILE AND STORE IN TEMPDATA
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            
            if (line.startsWith("Area of Incidence:: ")) {
                tempData[row][1] = line.substring(20).trim();
            } else if (line.startsWith("Time of Incidence:: ")) {
                tempData[row][2] = line.substring(20).trim();
            } else if (line.startsWith("Restoration Time Period:: ")) {
                tempData[row][3] = line.substring(26).trim();
            } else if (line.startsWith("Status:: ")) {
                tempData[row][4] = line.substring(9).trim();
            } else if (line.startsWith("Severity:: ")) {
                tempData[row][5] = line.substring(11).trim();
            } else if (line.startsWith("Time Reported:: ")) {
                tempData[row][6] = line.substring(16).trim();
            } else if (line.startsWith("Remark:: ")) {
                descriptionBuilder.setLength(0);
                descriptionBuilder.append(line.substring(9).trim());

                // HANDLE MULTI-LINE REMARKS
                while (input.hasNextLine()) {
                    String nextLine = input.nextLine();
                    if (nextLine.trim().equals("---END OF OUTAGE REPORT---")) {
                        break;
                    }
                    descriptionBuilder.append("\n").append(nextLine);
                }

                tempData[row][7] = descriptionBuilder.toString().trim();
                tempData[row][0] = "WattWiseON-" + (row + 1); // GENERATE REPORT ID
                row++;
            }
        }

        input.close();
        rowData = tempData; // UPDATE CLASS-LEVEL DATA STORAGE
    }

    /*
    THIS METHOD OVERWRITES THE RECENT DATA FILE WITH UPDATED DATA FROM A JTABLE.
    IT FIRST CREATES A BACKUP OF THE CURRENT DATA.
    PARAMETERS:
    - table: JTABLECONTAINING UPDATED OUTAGE DATA
    THROWS EXCEPTION IF FILE OPERATIONS FAIL.
    */
    protected void overwriteNotificationFromTable(JTable table) throws Exception {
        input = new Scanner(data_recent);
        PrintWriter backupWriter = new PrintWriter(data_backup);
        
        // CREATE BACKUP OF CURRENT DATA
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            backupWriter.println(line);
        }
        
        backupWriter.close();
        
        // WRITE UPDATED DATA FROM TABLE TO RECENT FILE
        PrintWriter recentWriter = new PrintWriter(data_recent);
        recentWriter.println("WATTWISE ENERGY SOLUTIONS v.1.0.0 [APP VERSION]");
        recentWriter.println("Java Desktop Utility for Energy Awareness and Optimization");
        recentWriter.println();
        recentWriter.println("CRITICAL INFORMATION [DO NOT DELETE/ALTER ANYTHING BELOW!]");
        recentWriter.println();

        for (int i = 0; i < table.getRowCount(); i++) {
            recentWriter.println("Area of Incidence:: " + table.getValueAt(i, 1));
            recentWriter.println("Time of Incidence:: " + table.getValueAt(i, 2));
            recentWriter.println("Restoration Time Period:: " + table.getValueAt(i, 3));
            recentWriter.println("Status:: " + table.getValueAt(i, 4));
            recentWriter.println("Severity:: " + table.getValueAt(i, 5));
            recentWriter.println("Time Reported:: " + table.getValueAt(i, 6));
            recentWriter.println("Remark:: " + table.getValueAt(i, 7));
            recentWriter.println("---END OF OUTAGE REPORT---");
            recentWriter.println();
        }

        recentWriter.close();
    }

    /*
    THIS METHOD CHECKS IF A FILE EXISTS AND CREATES IT WITH DEFAULT HEADERS IF IT DOES NOT.
    PARAMETERS:
    - file: THE FILE TO CHECK
    THROWS EXCEPTION IF FILE CREATION FAILS.
    */
    protected void dataCheck(File file) throws Exception {
        if(!file.exists()) {
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
    THIS METHOD CREATES A LEGACY DATA FILE IF IT DOES NOT EXIST.
    THROWS EXCEPTION IF FILE CREATION FAILS.
    */
    protected void createLegacy() throws Exception {
        if(!data_legacy.exists()) {
            PrintWriter createFileData = new PrintWriter(data_legacy);
            createFileData.close();
        }
    }
}