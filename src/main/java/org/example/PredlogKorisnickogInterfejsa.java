package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PredlogKorisnickogInterfejsa extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JButton buttonOpenTop;
    private JButton buttonOpenBottom;
    private JButton saveNewText;
    private JButton putTextTopToNew;
    private JButton putTextBottomToNew;
    private JTextArea textAreaTop;
    private JTextArea textAreaBottom;
    private JTextArea textAreaNew;
    private  boolean topOrBottom;
    String directory; // The default directory to display in the FileDialog

    //Decides if the text should be put on top or bottom
    public JTextArea TopOrBottomFinder(boolean finder){
        if (finder == true) {
            return textAreaTop;
        }
        else{return textAreaBottom;}
    }

    public PredlogKorisnickogInterfejsa() {
        setContentPane(contentPane);
        setModal(true);
        //    getRootPane().setDefaultButton(buttonOpen);

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });

        buttonOpenTop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonOpenTop();
            }
        });

        buttonOpenBottom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonOpenBottom();
            }
        });


        // call onCancel() when cross is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onButtonClose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void onButtonOpenTop() {
        topOrBottom = true; //gives the decider feedback text should appear on top
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        loadAndDisplayFile(directory, f.getFile()); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onButtonOpenBottom() {
        topOrBottom = false; //gives the decider feedback text should appear on top
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFile(directory, f.getFile());
        f.dispose();
    }

    private void onButtonClose() {
        // add your code here if necessary
        dispose();
    }

    public void loadAndDisplayFile(String directory, String filename) {
        JTextArea textArea = TopOrBottomFinder(topOrBottom); // Gives the value of text area to variable
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;
        // Read and display the file contents. Since we're reading text, we
        // use a FileReader instead of a FileInputStream.
        try {
            file = new File(directory, filename); // Create a file object
            in = new FileReader(file); // And a char stream to read it
            char[] buffer = new char[4096]; // Read 4K characters at a time
            int len; // How many chars read each time
            textArea.setText(""); // Clear the text area
            while ((len = in.read(buffer)) != -1) { // Read a batch of chars
                String s = new String(buffer, 0, len); // Convert to a string
                textArea.append(s); // And display them
            }
            this.setTitle("FileViewer: " + filename); // Set the window title
            textArea.setCaretPosition(0); // Go to start of file
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            textArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) {
        PredlogKorisnickogInterfejsa dialog = new PredlogKorisnickogInterfejsa();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
