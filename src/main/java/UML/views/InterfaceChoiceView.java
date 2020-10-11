package UML.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.FlowLayout;
import UML.controllers.*;

public class InterfaceChoiceView implements View {

    private JFrame window = new JFrame("UML");
    private JPanel radioPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JButton okButton = new JButton("Choose selected");
    private JButton closeButton = new JButton("Close");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton cliChoice = new JRadioButton("CLI");
    private JRadioButton guiChoice = new JRadioButton("GUI");

    public InterfaceChoiceView() {
        // Setup the window.
        windowSetup();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(200, 300);
        window.setLayout(new FlowLayout());

        // add radios to panel.
        radioPanel.add(cliChoice);
        radioPanel.add(guiChoice);

        // add buttons to panel.
        buttonPanel.add(okButton);
        buttonPanel.add(closeButton);

        // Add panels to main window.
        window.add(radioPanel);
        window.add(buttonPanel);
        window.pack();
        window.setVisible(true);

    }

    private void windowSetup() {
        // add the action commands where needed.
        okButton.setActionCommand("OK");
        // okButton.addActionListener(new ButtonClickListener());
        closeButton.setActionCommand("Close");
        cliChoice.setSelected(true);
        buttonGroup.add(cliChoice);
        buttonGroup.add(guiChoice);
    }

    public void addListener(ActionListener listener) {
        okButton.addActionListener(listener);
    }



    @Override
    public void updateClass(String oldName, String newName) {
        //Do nothing.
    }

    @Override
    public void createClass(String name) {
        //Do nothing.
    }

    @Override
    public void deleteClass(String name) {
        //Do nothing.
    }

    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) {
        return cliChoice.isSelected() == true ? "true" : "false";
    }

    @Override
    public String getInputFromUser(String prompt) {
        //Do nothing.
        return null;
    }

    @Override
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener,
            ActionListener relationshipListener) {
        //Do nothing.
    }

    @Override
    public void display(ArrayList<String> str) {
        //Do nothing.
    }

    @Override
    public void showError(String error) {
        //Do nothing.
    }

    @Override
    public String save() {
        //Do nothing.
        return null;
    }

    @Override
    public String load() {
        //Do nothing.
        return null;
    }

    @Override
    public void exit() {
        window.setVisible(false);

    }

    @Override
    public void start() {
        //Do nothing.
    }

    @Override
    public void showHelp() {
        //Do nothing.
    }

    @Override
    public void showPrompt() {
        //Do nothing.
    }

}
