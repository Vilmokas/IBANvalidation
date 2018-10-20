package com.IBANvalidation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IBANvalidation{
    private JTextField numberTextField;
    private JButton checkButton;
    private JPanel IBANvalidationView;
    private JTextField textField2;
    private JButton browseButton;
    private JButton checkFileButton;
    private JFileChooser jFileChooser;

    public IBANvalidation() {
        checkButton.addActionListener(new checkButtonPressed());
        browseButton.addActionListener(new browseButtonPressed());
        checkFileButton.addActionListener(new checkFileButtonPressed());

        jFileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
    }

    private class checkButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String number = numberTextField.getText();
            Number ibanNumber = new Number(number);

            JOptionPane.showMessageDialog(null, "IBAN " + number + " is " + String.valueOf(ibanNumber.validateNumber()), "IBAN validation", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class browseButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = jFileChooser.showOpenDialog(new JFrame());

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jFileChooser.getSelectedFile();
                textField2.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private class checkFileButtonPressed implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String path = textField2.getText();
            // Open the file
            FileInputStream fstream = null;
            try {
                fstream = new FileInputStream(path);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            //Read File Line By Line
            // Print the content on the console

            PrintWriter writer = null;
            try {
                Path p = Paths.get(path);
                String file = p.getFileName().toString();
                file = file.replaceFirst("[.][^.]+$", "");
                System.out.println(file);
                writer = new PrintWriter(file + ".out", "UTF-8");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }


            String strLine;
            try {
                while ((strLine = br.readLine()) != null)   {
                    // Print the content on the console
                    Number ibanNumber = new Number(strLine);
                    System.out.println (strLine + ";" + String.valueOf(ibanNumber.validateNumber()));
                    writer.println(strLine + ";" + String.valueOf(ibanNumber.validateNumber()));
                }
                writer.close();
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }

            //Close the input stream
            try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("IBANvalidation");
        frame.setContentPane(new IBANvalidation().IBANvalidationView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
