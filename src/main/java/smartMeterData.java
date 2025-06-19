package main.java;
import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
/*  
THIS CLASS HANDLES ALL DATA OPERATIONS FOR SMART METER SETTINGS  
IT MANAGES KWH RANGES, RATES, AND PROVIDES UTILITY METHODS  
*/
public class smartMeterData {
    // FILE PATHS FOR STORING METER SETTINGS AND HISTORY  
    protected File data = new File("data\\smartMeterSetting.txt");
    protected File savePrompt = new File("data\\smartMeterHistory.txt");
    // ARRAYS TO STORE RATE VALUES AND METER RANGES  
    protected String[] setRate = new String[9];
    protected String[] meter = new String[setRate.length + 1]; // +1 for the default option
    protected int selectedIndex;
    WattWise_UIHelper ui = new WattWise_UIHelper();
    // LOADS KWH RANGE VALUES FROM DATA FILE INTO METER ARRAY  
    // THROWS EXCEPTION IF FILE OPERATIONS FAIL  
    protected void checkKWhRange() throws Exception {
        Scanner input = new Scanner(data);
        boolean found = false;
        int i = 1;
        meter[0] = "---Select Range---";
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.equalsIgnoreCase("KWh Range::")) {
                found = true;
                continue;
            }
            if (found && i < meter.length) {
                if (line.equalsIgnoreCase("Set::") || line.isEmpty()) break;
                else meter[i++] = line;
            }
        }
        input.close();
        if (i != meter.length) {
            errorAndReload();
            checkKWhRange();
        }
    }
    // LOADS KWH RATE VALUES FROM DATA FILE INTO SETRATE ARRAY  
    // THROWS EXCEPTION IF FILE OPERATIONS FAIL  
    protected void checkKWhRate() throws Exception {
        Scanner input = new Scanner(data);
        boolean found = false;
        int i = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.equalsIgnoreCase("Set::")) {
                found = true;
                continue;
            }
            if (found && i < setRate.length) {
                if (line.isEmpty()) {
                    break;
                } else {
                    setRate[i++] = line;
                }
            }
        }
        input.close();
        if (i != setRate.length) {
            errorAndReload();
            checkKWhRate();
        }
    }
    // DISPLAYS ERROR MESSAGE AND RESTORES DEFAULT SETTINGS  
    protected void errorAndReload() {
        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:" + "\n[MSG] An error just occurred." + "\n[MSG] System resets. Repairing...", ui.dialogBox, JOptionPane.ERROR_MESSAGE);
        try {
            fileRestart();
        } catch (Exception e) {
            //ignore
        }
    }
    // RESTORES DEFAULT SETTINGS TO DATA FILE  
    // THROWS EXCEPTION IF FILE OPERATIONS FAIL  
    protected void fileRestart() throws Exception {
        PrintWriter createFileData = new PrintWriter(data);
        createFileData.println("WATTWISE ENERGY SOLUTIONS v.1.0.0 [APP VERSION]");
        createFileData.println("Java Desktop Utility for Energy Awareness and Optimization");
        createFileData.println();
        createFileData.println("CRITICAL INFORMATION [DO NOT DELETE/ALTER ANYTHING BELOW!]");
        createFileData.println();
        createFileData.println("KWh Range::");
        String[] range = {
            "0 - 50",
            "51 - 70",
            "71 - 100",
            "101 - 200",
            "201 - 300",
            "301 - 400",
            "401 - 500",
            "501 - 1000",
            "1001 - 1500"
        };
        for (int i = 0; i < range.length; i++) createFileData.println(range[i]);
        createFileData.println();
        createFileData.println("Set::");
        double[] data = {
            9.81,
            9.67,
            9.57,
            9.45,
            9.76,
            10.07,
            10.63,
            10.64,
            10.69
        };
        for (int i = 0; i < data.length; i++) createFileData.println(data[i]);
        createFileData.close();
    }
    // SAVES METER HISTORY TO FILE  
    // PARAMETER TEXTAREA: CONTAINS THE HISTORY DATA TO SAVE  
    // THROWS EXCEPTION IF FILE OPERATIONS FAIL  
    protected void smartMeterHistorySave(JTextArea textArea) throws Exception {
        PrintWriter createFileData = new PrintWriter(savePrompt);
        createFileData.println(textArea.getText());
        createFileData.println("------------------------------------------------");
        createFileData.println("LAST EDIT HISTORY: " + new java.util.Date(savePrompt.lastModified()));
        createFileData.close();
        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] File saved successfully!\n", ui.dialogBox, JOptionPane.INFORMATION_MESSAGE);
    }
    // CALCULATES ENERGY CONSUMPTION COST  
    // PARAMETER CONSUME: THE AMOUNT OF KWH CONSUMED  
    // RETURNS: THE CALCULATED BILL AMOUNT  
    protected double solveConsume(double consume) {
        double bill = Double.parseDouble(setRate[selectedIndex - 1]) * consume;
        return bill;
    }
    // CHECKS IF A STRING IS A VALID NUMBER  
    // PARAMETER FIELD: THE STRING TO VALIDATE  
    // RETURNS: TRUE IF VALID NUMBER, FALSE OTHERWISE  
    protected boolean isNumber(String field) {
        try {
            Double.parseDouble(field);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}