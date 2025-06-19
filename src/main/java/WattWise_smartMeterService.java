/*  
THIS CLASS REPRESENTS THE SMART METER DATA MANAGEMENT SYSTEM GUI FOR WATTWISE ENTERPRISE APPLICATION.  
IT HANDLES METER READING INPUT, CALCULATIONS, AND DATA MANAGEMENT OPERATIONS.  
*/
package main.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
public class WattWise_smartMeterService extends JFrame {
    // UI HELPER CLASS FOR STYLING AND COMPONENTS  
    private WattWise_UIHelper ui = new WattWise_UIHelper();
    // SMART METER DATA HANDLER CLASS  
    private smartMeterData meterData = new smartMeterData();
    /*  
    CONSTRUCTOR: INITIALIZES THE SMART METER SERVICE GUI.  
    THROWS EXCEPTION IF FILE OPERATIONS FAIL.  
    */
    WattWise_smartMeterService() throws Exception {
        // CHECK AND INITIALIZE DATA FILES  
        if (!meterData.data.exists()) {
            meterData.fileRestart();
        }
        meterData.checkKWhRange();
        meterData.checkKWhRate();
        // SET UP MAIN FRAME PROPERTIES  
        this.setTitle("WattWise for Enterprise | Smart Meter Data Management System");
        this.setSize(500, 700);
        this.setResizable(false);
        this.setIconImage(ui.icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // MAIN PANEL WITH BACKGROUND IMAGE  
        JPanel smartMeterPanel = ui.new backgroundImagePanel("src\\main\\resources\\freSonneveldOnUnsplash.jpg");
        smartMeterPanel.setLayout(new BorderLayout());
        // TITLE PANEL  
        JPanel centerTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        centerTitle.setOpaque(false);
        JLabel title = new JLabel("Smart Meter Data Management System");
        title.setFont(ui.unicodeTitle);
        title.setForeground(Color.WHITE);
        centerTitle.add(title);
        smartMeterPanel.add(centerTitle, BorderLayout.NORTH);
        // CENTER CONTENT PANEL  
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        // USER INPUT PANEL WITH METER SELECTION AND READING INPUT  
        JPanel userInputPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        JPanel meterLabelPanel = new JPanel(new GridLayout(0, 2, 0, 0));
        meterLabelPanel.setBackground(Color.BLACK);
        JLabel meterLabel = new JLabel("Select Meter:");
        meterLabel.setForeground(Color.WHITE);
        JComboBox < String > meterSelector = new JComboBox < > (meterData.meter);
        meterSelector.setBackground(ui.appUIColorLight);
        meterLabelPanel.add(meterLabel);
        meterLabelPanel.add(meterSelector);
        JPanel meterReadingPanel = new JPanel(new GridLayout(0, 2, 0, 0));
        meterReadingPanel.setBackground(Color.BLACK);
        JLabel meterReading = new JLabel("Enter Meter Reading (in kilo-watt):");
        meterReading.setForeground(Color.WHITE);
        JTextField KWhGained = new JTextField(5);
        KWhGained.setBackground(ui.appUIColorLight);
        meterReadingPanel.add(meterReading);
        meterReadingPanel.add(KWhGained);
        userInputPanel.add(meterLabelPanel);
        userInputPanel.add(meterReadingPanel);
        center.add(userInputPanel, BorderLayout.NORTH);
        // RESULTS DISPLAY AREA  
        JTextArea meterResult = new JTextArea();;
        // LOAD SAVED DATA IF EXISTS  
        if (!meterData.savePrompt.exists()) {
            meterResult.setText("Usage data will appear here...");
        } else {
            Scanner input = new Scanner(meterData.savePrompt);
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                meterResult.append(line + "\n");
            }
            input.close();
        }
        // FORMAT RESULTS DISPLAY  
        meterResult.setFont(ui.monospaceContent);
        meterResult.setMargin(new Insets(15, 20, 15, 20));
        meterResult.setBackground(Color.BLACK);
        meterResult.setEditable(false);
        meterResult.setForeground(Color.WHITE);
        meterResult.setLineWrap(true);
        meterResult.setWrapStyleWord(true);
        // METER SELECTION EVENT HANDLER  
        meterSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                meterData.selectedIndex = meterSelector.getSelectedIndex();
                if (meterData.selectedIndex == 0) {
                    meterResult.setText("Usage data will appear here...\n\n");
                } else {
                    meterResult.append("\n\nSelected Range: " + meterData.meter[meterData.selectedIndex] + "\nRate per KWh (in Philippine Peso): " + meterData.setRate[meterData.selectedIndex - 1]);
                    meterResult.setCaretPosition(meterResult.getDocument().getLength());
                }
            }
        });
        // SCROLL PANE FOR RESULTS  
        JScrollPane scroll = new JScrollPane(meterResult);
        center.add(scroll, BorderLayout.CENTER);
        // CALCULATE BUTTON PANEL  
        JPanel solvePanel = new JPanel(new FlowLayout());
        solvePanel.setOpaque(false);
        JButton solve = new JButton("RESOLVE");
        solve.setFont(ui.forButton);
        solve.setMnemonic(KeyEvent.VK_ENTER);
        solve.setBackground(ui.appUIColorNormal);
        solve.setForeground(Color.WHITE);
        solvePanel.add(solve);
        center.add(solvePanel, BorderLayout.SOUTH);
        smartMeterPanel.add(center, BorderLayout.CENTER);
        // BOTTOM BUTTON PANEL  
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton edit = new JButton("Edit Data");
        edit.setMnemonic(KeyEvent.VK_E);
        JButton
        export = new JButton("Save history as (.txt)");
        export.setMnemonic(KeyEvent.VK_H);
        JButton back = new JButton("Back");
        back.setMnemonic(KeyEvent.VK_ESCAPE);
        buttonPanel.add(edit);
        buttonPanel.add(
            export);
        buttonPanel.add(back);
        // CALCULATE BUTTON ACTION HANDLER  
        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selected = meterData.selectedIndex;
                if (selected == 0) {
                    JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Please select a valid meter\n" + "[MSG] range before calculating.", ui.dialogBox, JOptionPane.WARNING_MESSAGE);
                    KWhGained.setText("");
                    return;
                }
                try {
                    double consume = Double.parseDouble(KWhGained.getText().trim());
                    double answer = meterData.solveConsume(consume);
                    String range = meterData.meter[selected];
                    int dashIndex = range.indexOf('-');
                    String lowStr = range.substring(0, dashIndex).trim();
                    String highStr = range.substring(dashIndex + 1).trim();
                    double lowVal = Double.parseDouble(lowStr);
                    double highVal = Double.parseDouble(highStr);
                    if (consume >= lowVal && consume <= highVal) {
                        meterResult.append("\n\n" + "------------------------------------------------" + "\nBilling Amount (in Philippine Peso):: " + answer + "\n\n---CHECK GIVEN DATA---\n" + "Selected Range: " + meterData.meter[selected] + "\nRate per KWh (in Philippine Peso): " + meterData.setRate[selected - 1] + "\nEnter Meter Reading (in kilo-watt): " + consume);
                        meterResult.setCaretPosition(meterResult.getDocument().getLength());
                    } else {
                        JOptionPane.showMessageDialog(null, "WattWise Energy Solutions:\n" + "[MSG] Your entered consumption does not match\n" + "[MSG] the selected range. Please re-check your\n" + "[MSG] input or selected range.", ui.dialogBox, JOptionPane.WARNING_MESSAGE);
                    }
                    KWhGained.setText("");
                } catch (Exception ex) {
                    dispose();
                    meterData.errorAndReload();
                    try {
                        new WattWise_smartMeterService().setVisible(true);
                    } catch (Exception e1) {
                        //ignore
                    }
                }
            }
        });
        // EDIT BUTTON ACTION HANDLER  
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    meterResult.append("\n\nWattWise Energy Solutions says:\n" + "[MSG] Are you sure you want to proceed?\n" + "[MSG] You are about to enter Smart Meter Settings.");
                    meterResult.setCaretPosition(meterResult.getDocument().getLength());
                    JOptionPane optionPane = new JOptionPane("Are you sure you want to proceed?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                    JDialog dialog = optionPane.createDialog(ui.dialogBox);
                    dialog.setLocation(500, 250);
                    dialog.setVisible(true);
                    int confirm = (int) optionPane.getValue();
                    if (confirm == JOptionPane.YES_OPTION) {
                        dispose();
                        new smartMeterServiceSetting().setVisible(true);;
                    } else {
                        meterResult.append("\n[MSG] Edit mode cancelled by user.");
                        meterResult.setCaretPosition(meterResult.getDocument().getLength());
                    }
                } catch (Exception e3) {
                    //ignore
                }
            }
        });
        // EXPORT BUTTON ACTION HANDLER  
        export.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to save history as (.txt)?", ui.dialogBox, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        meterData.smartMeterHistorySave(meterResult);
                        dispose();
                        new WattWise_smartMeterService().setVisible(true);
                    } catch (Exception e4) {
                        //ignore
                    }
                } else {
                    meterData.savePrompt.delete();
                    dispose();
                    try {
                        new WattWise_smartMeterService().setVisible(true);
                    } catch (Exception e1) {
                        //ignore
                    }
                }
            }
        });
        // BACK BUTTON ACTION HANDLER  
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                meterResult.append("\n\nWattWise Energy Solutions says:\n" + "[MSG] Are you sure you want to exit?\n" + "[MSG] All unsaved work will not be saved.\n" + "[MSG] This cannot be undone.");
                meterResult.setCaretPosition(meterResult.getDocument().getLength());
                JOptionPane optionPane = new JOptionPane("Are you sure you want to exit?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(ui.dialogBox);
                dialog.setLocation(500, 250);
                dialog.setVisible(true);
                int confirm = (int) optionPane.getValue();
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                } else {
                    meterResult.append("\n[MSG] Exit cancelled by user.");
                    meterResult.setCaretPosition(meterResult.getDocument().getLength());
                }
            }
        });
        smartMeterPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(smartMeterPanel);
    }
}