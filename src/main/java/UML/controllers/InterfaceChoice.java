
package UML.controllers;

/**
    Author: Chris, Cory, Dominic, Drew, Tyler.
    Date: 09/24/2020
    Purpose: Provide the user a way to select between using the command line view
    or through the graphical interface. 
 */

import UML.model.*;
import UML.views.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import java.awt.event.*;
import java.awt.FlowLayout;

public class InterfaceChoice 
{
    private JFrame window = new JFrame("UML");
    private JPanel radioPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JButton okButton = new JButton("Choose selected");
    private JButton closeButton = new JButton("Close");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton cliChoice = new JRadioButton("CLI");
    private JRadioButton guiChoice = new JRadioButton("GUI");

    public InterfaceChoice()
    {
        //Setup the window. 
        windowSetup();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(200, 300);
        window.setLayout(new FlowLayout());

        //add radios to panel.
        radioPanel.add(cliChoice);
        radioPanel.add(guiChoice);

        //add buttons to panel.
        buttonPanel.add(okButton);
        buttonPanel.add(closeButton);

        //Add panels to main window.
        window.add(radioPanel);
        window.add(buttonPanel);
        window.pack();
        window.setVisible(true);
        
    }

    private void windowSetup()
    {
        //add the action commands where needed.
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ButtonClickListener());
        closeButton.setActionCommand("Close");
        cliChoice.setSelected(true);
        buttonGroup.add(cliChoice);
        buttonGroup.add(guiChoice);

    }

    private class ButtonClickListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String cmd = e.getActionCommand();
            if(cmd.equals("OK"))
            {
                //if the choice is cli, load it. otherwise, load the gui. 
                if(cliChoice.isSelected())
                {

                    Store s = new Store();
                    View v = new CommandlineView();
                    Controller c = new Controller(s, v);
                    window.setVisible(false);
                    CLI cli = new CLI(s, v, c);
                }
                else
                {
                    Store s = new Store();
                    GraphicalView v = new GraphicalView();
                    Controller c = new Controller(s, v);
                    window.setVisible(false);
                    v.start();
                    c.addListeners();
                }
            }
            else 
            {
                System.exit(0);
            }
        }
    }
}