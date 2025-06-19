package main.java;
import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
/*
CLASS THAT HANDLES DATA OPERATIONS FOR DIGITAL WORKFLOW SYSTEM
MANAGES THREE TYPES OF DATA FILES: RECENT, BACKUP, AND LEGACY
PROVIDES METHODS FOR SUBMITTING, LOADING, AND OVERWRITING WORKFLOW DATA
*/
public class digitalWorkflowData {
    // FILE OBJECTS FOR DIFFERENT DATA VERSIONS
    protected File data_recent = new File("data\\digitalWorkflowData.txt");
    protected File data_backup = new File("data\\digitalWorkflowDataOld.txt");
    protected File data_legacy = new File("data\\digitalWorkflowDataLegacy.txt");
    Object[][] rowData = new Object[0][7];
    Scanner input, inputBackup, inputLegacy;
    /*
    METHOD TO SUBMIT NEW WORKFLOW DATA TO ALL THREE FILES
    TAKES INPUT FROM SWING COMPONENTS AND APPENDS TO FILES
    PARAMETERS:
    - staff: COMBOBOX CONTAINING STAFF INFORMATION
    - task: COMBOBOX CONTAINING TASK TYPES
    - location: TEXTFIELD WITH LOCATION DATA
    - status: COMBOBOX WITH STATUS INFORMATION
    - notes: TEXTAREA WITH ADDITIONAL NOTES
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void submitDigitalWorkflow(JComboBox < String > staff, JComboBox < String > task, JTextField location, JComboBox < String > status, JTextArea notes) throws Exception {
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
        recentWriter.println("Assigned Staff:: " + staff.getSelectedItem().toString());
        recentWriter.println("Task Type:: " + task.getSelectedItem().toString());
        recentWriter.println("Location:: " + location.getText());
        recentWriter.println("Status:: " + status.getSelectedItem().toString());
        recentWriter.println("Time Assigned:: " + new java.util.Date());
        recentWriter.println("Note:: " + notes.getText());
        recentWriter.println("---END OF TASK DATA---");
        recentWriter.println();
        recentWriter.close();
        PrintWriter backupWriter = new PrintWriter(data_backup);
        backupWriter.println(oldDataBackup.toString());
        backupWriter.println("Assigned Staff:: " + staff.getSelectedItem().toString());
        backupWriter.println("Task Type:: " + task.getSelectedItem().toString());
        backupWriter.println("Location:: " + location.getText());
        backupWriter.println("Status:: " + status.getSelectedItem().toString());
        backupWriter.println("Time Assigned:: " + new java.util.Date());
        backupWriter.println("Note:: " + notes.getText());
        backupWriter.println("---END OF TASK DATA---");
        backupWriter.println();
        backupWriter.close();
        PrintWriter legacyWriter = new PrintWriter(data_legacy);
        legacyWriter.println(oldDataLegacy.toString());
        legacyWriter.println("Assigned Staff:: " + staff.getSelectedItem().toString());
        legacyWriter.println("Task Type:: " + task.getSelectedItem().toString());
        legacyWriter.println("Location:: " + location.getText());
        legacyWriter.println("Status:: " + status.getSelectedItem().toString());
        legacyWriter.println("Time Assigned:: " + new java.util.Date());
        legacyWriter.println("Note:: " + notes.getText());
        legacyWriter.println("---END OF TASK DATA---");
        legacyWriter.println();
        legacyWriter.close();
    }
    /*
    METHOD TO LOAD WORKFLOW DATA FROM FILE INTO MEMORY
    READS THE RECENT DATA FILE AND PARSES INTO 2D OBJECT ARRAY
    EACH ROW REPRESENTS A TASK WITH 7 COLUMNS OF DATA
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void loadWorkflowFromFile() throws Exception {
        if (!data_recent.exists()) {
            dataCheck(data_recent);
        }
        input = new Scanner(data_recent);
        int count = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.equals("---END OF TASK DATA---")) {
                count++;
            }
        }
        input.close();
        Object[][] tempData = new Object[count][7];
        input = new Scanner(data_recent);
        int row = 0;
        StringBuilder descriptionBuilder = new StringBuilder();
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.startsWith("Assigned Staff:: ")) {
                tempData[row][1] = line.substring(17).trim();
            } else if (line.startsWith("Task Type:: ")) {
                tempData[row][2] = line.substring(12).trim();
            } else if (line.startsWith("Location:: ")) {
                tempData[row][3] = line.substring(11).trim();
            } else if (line.startsWith("Status:: ")) {
                tempData[row][4] = line.substring(9).trim();
            } else if (line.startsWith("Time Assigned:: ")) {
                tempData[row][5] = line.substring(16).trim();
            } else if (line.startsWith("Note:: ")) {
                descriptionBuilder.setLength(0);
                descriptionBuilder.append(line.substring(7).trim());
                while (input.hasNextLine()) {
                    String nextLine = input.nextLine();
                    if (nextLine.trim().equals("---END OF TASK DATA---")) {
                        break;
                    }
                    descriptionBuilder.append("\n").append(nextLine);
                }
                tempData[row][6] = descriptionBuilder.toString().trim();
                tempData[row][0] = "WattWiseDW-" + (row + 1);
                row++;
            }
        }
        input.close();
        rowData = tempData;
    }
    /*
    METHOD TO OVERWRITE WORKFLOW DATA FROM TABLE TO FILE
    FIRST CREATES BACKUP OF EXISTING DATA
    THEN WRITES TABLE CONTENTS TO RECENT DATA FILE
    PARAMETERS:
    - table: JTABLECONTAINING WORKFLOW DATA TO WRITE
    THROWS EXCEPTION IF FILE OPERATIONS FAIL
    */
    protected void overwriteWorkflowFromTable(JTable table) throws Exception {
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
            recentWriter.println("Assigned Staff:: " + table.getValueAt(i, 1));
            recentWriter.println("Task Type:: " + table.getValueAt(i, 2));
            recentWriter.println("Location:: " + table.getValueAt(i, 3));
            recentWriter.println("Status:: " + table.getValueAt(i, 4));
            recentWriter.println("Time Assigned:: " + table.getValueAt(i, 5));
            recentWriter.println("Note:: " + table.getValueAt(i, 6));
            recentWriter.println("---END OF TASK DATA---");
            recentWriter.println();
        }
        recentWriter.close();
    }
    /*
    METHOD TO CHECK IF DATA FILE EXISTS AND CREATE DEFAULT IF NOT
    PARAMETERS:
    - file: FILE OBJECT TO CHECK
    THROWS EXCEPTION IF FILE CREATION FAILS
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
    METHOD TO CREATE EMPTY LEGACY FILE IF IT DOESN'T EXIST
    THROWS EXCEPTION IF FILE CREATION FAILS
    */
    protected void createLegacy() throws Exception {
        if (!data_legacy.exists()) {
            PrintWriter createFileData = new PrintWriter(data_legacy);
            createFileData.close();
        }
    }
}