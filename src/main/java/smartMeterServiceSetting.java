package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.Scanner;
/*  
THIS CLASS REPRESENTS THE SMART METER SERVICE SETTINGS WINDOW  
IT ALLOWS USERS TO MODIFY KWH RANGE AND RATE PER KWH VALUES  
*/
public class smartMeterServiceSetting extends JFrame {
	private WattWise_UIHelper ui = new WattWise_UIHelper();
    private smartMeterData meterData = new smartMeterData();
    // ARRAYS TO STORE KWH RANGE VALUES  
    protected String[] forRange = new String[meterData.setRate.length];
    // TEXT FIELDS FOR KWH RANGE INPUT  
    protected JTextField[] forRangeField = new JTextField[forRange.length];
    // ARRAYS TO STORE RATE PER KWH VALUES  
    protected String[] forRate = new String[meterData.setRate.length];
    // TEXT FIELDS FOR RATE PER KWH INPUT  
    protected JTextField[] forRateField = new JTextField[forRate.length];
    // CONSTRUCTOR FOR SMART METER SERVICE SETTING WINDOW  
    // THROWS EXCEPTION IF FILE OPERATIONS FAIL  
    smartMeterServiceSetting() throws Exception {
        this.setTitle("Smart Meter Data Management System | Settings");
        this.setSize(500, 450);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // CREATE PANEL WITH GRID LAYOUT FOR INPUT FIELDS  
        JPanel table = new JPanel(new GridLayout(0, 2, 10, 10));
        table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        table.add(new JLabel("KWh Range", SwingConstants.CENTER));
        table.add(new JLabel("Rate per KWh (in Philippine Peso)", SwingConstants.CENTER));
        // READ EXISTING VALUES FROM DATA FILE  
        Scanner input = new Scanner(meterData.data);
        boolean foundKWhSection = false;
        boolean foundRateSection = false;
        int rangeIndex = 0, rateIndex = 0;
        // PARSE THE DATA FILE TO EXISTING KWH AND RATE VALUES  
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.equalsIgnoreCase("KWh Range::")) {
                foundKWhSection = true;
                continue;
            }
            if (line.equalsIgnoreCase("Set::")) {
                foundKWhSection = false;
                foundRateSection = true;
                continue;
            }
            if (foundKWhSection && rangeIndex < forRange.length) {
                forRange[rangeIndex] = line;
                forRangeField[rangeIndex] = new JTextField(forRange[rangeIndex]);
                rangeIndex++;
            } else if (foundRateSection && rateIndex < forRate.length) {
                forRate[rateIndex] = line;
                forRateField[rateIndex] = new JTextField(forRate[rateIndex]);
                rateIndex++;
            }
        }
        input.close();
        // ADD TEXT FIELDS TO THE PANEL  
        for (int i = 0; i < forRange.length; i++) {
            table.add(forRangeField[i]);
            table.add(forRateField[i]);
        }
        // CREATE SAVE BUTTON AND ITS ACTION LISTENER  
        JPanel saveButton = new JPanel();
        JButton save = new JButton("Save Changes");
        save.setMnemonic(KeyEvent.VK_ENTER);
        saveButton.add(save);
        save.addActionListener(new ActionListener() {
            // HANDLES SAVE BUTTON CLICK EVENT  
            public void actionPerformed(ActionEvent e) {
                try {
                    // WRITE UPDATED VALUES BACK TO DATA FILE  
                    PrintWriter save = new PrintWriter(meterData.data);
                    save.println("WATTWISE ENERGY SOLUTIONS v.1.0.0 [APP VERSION]");
                    save.println("Java Desktop Utility for Energy Awareness and Optimization");
                    save.println();
                    save.println("CRITICAL INFORMATION [DO NOT DELETE/ALTER ANYTHING BELOW!]");
                    save.println();
                    save.println("KWh Range::");
                    for (int i = 0; i < forRangeField.length; i++) {
                        save.println(forRangeField[i].getText().trim());
                    }
                    save.println();
                    save.println("Set::");
                    for (int i = 0; i < forRateField.length; i++) {
                        if (meterData.isNumber(forRateField[i].getText().trim())) save.println(forRateField[i].getText().trim());
                        else {
                            meterData.errorAndReload();
                            break;
                        }
                    }
                    save.close();
                    // CLOSE CURRENT WINDOW AND OPEN SMART METER SERVICE WINDOW  
                    dispose();
                    new WattWise_smartMeterService().setVisible(true);
                } catch (Exception e1) {
                    //ignore
                }
            }
        });
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(saveButton, BorderLayout.SOUTH);
    }
}