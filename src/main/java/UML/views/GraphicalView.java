package UML.views;

import UML.model.Class;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.border.Border;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.GridLayout;

public class GraphicalView implements View {

    // Holds menu bar used to navigate program.
    private JMenuBar mb;

    // The UML editor window.
    private JFrame parentWindow;

    // Displays each individual class.
    private Map<String, JPanel> classPanels;

    // The window for the GUI.
    private JFrame window;

    private JMenu fileMenu;
    private JMenu classMenu;
    private JMenu fieldMenu;
    private JMenu relationshipMenu;

    public GraphicalView() {
        this.classPanels = new HashMap<String, JPanel>();
    }

    @Override
    public void createClass(String name) {
        makeNewClassPanel(name);
        refresh();
    }

    @Override
    public void deleteClass(String name) {
        deleteClassPanel(name);
    }

    @Override
    public void updateClass(String oldString, String newString) {
        JPanel panel = classPanels.get(oldString);
        classPanels.remove(oldString);
        classPanels.put(newString, panel);
        windowUpdateHelper(newString);
    }

    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) {
        Object[] optionsToArray = options.toArray();
        String strToRtn = (String) JOptionPane.showInputDialog(parentWindow, msgOne, msgTwo, JOptionPane.PLAIN_MESSAGE,
                null, optionsToArray, null);
        return strToRtn;
    }

    @Override
    public String getInputFromUser(String prompt) {
        String strToRtn = JOptionPane.showInputDialog(parentWindow, prompt, JOptionPane.PLAIN_MESSAGE);

        return strToRtn;
    }

    @Override
    public void display(ArrayList<String> toStrings) {

        for (Map.Entry<String, JPanel> panel : classPanels.entrySet()) {
            parentWindow.remove(panel.getValue());
        }

        classPanels.clear();

        for (String s : toStrings) {
            makeNewClassPanel(s);
        }

        refresh();

    }

    @Override
    public String save() {
        // TODO Auto-generated method stub
        JFileChooser fc = new JFileChooser();
        // If there is a currently loaded file.

        // Bring up file panel for the user to save as(automatically will choose file
        // type though in saveandload).
        int returnValue = fc.showSaveDialog(parentWindow);
        // Based on the user imput, save the file.

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        // if no filename is found, retrun an empty string
        return "";
    }

    @Override
    public String load() {
        // Make a filechooser
        JFileChooser fc = new JFileChooser();
        int returnValue = fc.showOpenDialog(parentWindow);
        // If the user selected to open this file, open it.
        // TODO: Consider filtering this information to only inlcude JSON filetypes
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        return "";

    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub

    }

    private void windowUpdateHelper(String classInfo) {
        JPanel panel = classPanels.get(classInfo);
        JTextArea textArea = (JTextArea) panel.getComponents()[0];
        textArea.setText(classInfo);
        refresh();
    }

    /**
     * Constructrs a UMLWindow object.
     */
    public void makeWindow() {
        window = new JFrame("UML");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(4, 4));
        window.setSize(800, 800);
        window.setVisible(true);
        parentWindow = window;
        createMenu();
        parentWindow.add(mb);
        window.setJMenuBar(mb);
        
    }

    public void createMenu() {
        // Set up the menu with helpers and add them to the main window.
        mb = new JMenuBar();
        createFileMenu(mb);
        createClassMenu(mb);
        createAtrributeMenu(mb);
        createRelationshipMenu(mb);
        mb.setVisible(true);  
    }

    public JFrame getMainWindow() {
        return window;
    }

    /* Creates a file menu. */
    private void createFileMenu(JMenuBar mb) {
        fileMenu = new JMenu("File");
        // Sub-Menus.
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as...");
        JMenuItem load = new JMenuItem("Load...");
        JMenuItem exit = new JMenuItem("Exit");

        JMenuItem[] arr = { save, saveAs, load, exit };
        String[] text = { "Save edited file", "Save newly created file", "Load selected projectLoad selected project",
                "Exit application", "Exit application" };
        String[] command = { "Save", "SaveAs", "Load" };

        for (int count = 0; count < 4; ++count) {
            fileMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            if (count < 3) {
                arr[count].setActionCommand(command[count]);
            } else
                arr[count].addActionListener((event) -> System.exit(0));
        }

        mb.add(fileMenu);
    }

    /**
     * Creates the class menu options by taking in the menu bar and adding them to
     * it.
     */
    private void createClassMenu(JMenuBar mb) {

        classMenu = new JMenu("Class");

        // Create sub menus.
        JMenuItem crtClass = new JMenuItem("Create class");
        JMenuItem deleteClass = new JMenuItem("Delete class");
        JMenuItem rnClass = new JMenuItem("Rename class");

        JMenuItem[] arr = { crtClass, deleteClass, rnClass };
        String[] text = { "Create Class", "Delete Class", "Rename Class" };
        String[] command = { "Create", "Delete", "Rename" };

        for (int count = 0; count < 3; ++count) {
            classMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
        }
        mb.add(classMenu);
    }

    /**
     * Creates the field menu options by taking in the menu bar and adding them to
     * it.
     */
    private void createAtrributeMenu(JMenuBar mb)// change spelling later on
    {
        fieldMenu = new JMenu("Field");

        // Create field sub-menu.
        JMenuItem crtField = new JMenuItem("Create field");
        JMenuItem deleteField = new JMenuItem("Delete field");
        JMenuItem rnField = new JMenuItem("Rename field");
        // Add option to change the field's type.
        JMenuItem chgFieldType = new JMenuItem("Change field type");

        // Create method buttons
        JMenuItem crtMethod = new JMenuItem("Create method");
        JMenuItem deleteMethod = new JMenuItem("Delete method");
        JMenuItem rnMethod = new JMenuItem("Rename method");

        JMenuItem[] arr = { crtField, deleteField, rnField, chgFieldType, crtMethod, deleteMethod, rnMethod };
        String[] text = { "Create new field", "Delete a named field", "Rename a selected field",
                "Changes the field's type", "Create new method", "Delete a named method", "Rename a selected method" };
        String[] command = { "CreateField", "DeleteField", "RenameField", "ChangeFieldType", "CreateMethod",
                "DeleteMethod", "RenameMethod" };

        for (int count = 0; count < 7; ++count) {
            fieldMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
        }
        mb.add(fieldMenu);
    }

    /**
     * Creates the relationship menu options by taking in the menu bar and adding
     * them to it.
     */
    private void createRelationshipMenu(JMenuBar mb) {
        relationshipMenu = new JMenu("Relate");
        // Create JMenuItems for each type of relationship and deleting a relationship.
        JMenuItem association = new JMenuItem("Association");
        JMenuItem aggregation = new JMenuItem("Aggregation");
        JMenuItem composition = new JMenuItem("Composition");
        JMenuItem generalization = new JMenuItem("Generalization");
        JMenuItem deleteRelate = new JMenuItem("Delete Relationship");
        // Create arrays of JMenuItems and Strings to use for loop for setting up
        // relationship menu.
        JMenuItem[] arr = { association, aggregation, composition, generalization, deleteRelate };
        String[] names = { "Association", "Aggregation", "Composition", "Generalization", "DeleteRelationship" };

        for (int count = 0; count < 5; ++count) {
            relationshipMenu.add(arr[count]);
            if (count < 4)
                arr[count].setToolTipText("Creates selected relationship between two classes");
            else
                arr[count].setToolTipText("Deletes selected relationship between two classes");

            arr[count].setActionCommand(names[count]);
        }
        mb.add(relationshipMenu);
    }

    /**
     * Creates a panel on the window to display information about a class.
     */
    public void makeNewClassPanel(String aClass) 
    {
        JPanel classPanel = new JPanel();
        JTextArea classText = new JTextArea(aClass);
        classText.setEditable(false);
        classPanel.add(classText);
        classPanels.put(aClass, classPanel);
        classPanel.setSize(classText.getSize());
        Border blackline = BorderFactory.createLineBorder(Color.black);
        classText.setBorder(blackline);
        classPanel.setVisible(true);
        parentWindow.add(classPanel);
    }

    public void deleteClassPanel(String aClass) {
        JPanel panel = classPanels.get(aClass);
        classPanels.remove(aClass);
        parentWindow.remove(panel);
        refresh();

    }

    public void refresh() {
        parentWindow.revalidate();
        parentWindow.repaint();
    }

    /**
     * Updates the relationships visually in the window .
     *
     */
    public void updateDisplayRelationship(String classOne, String classTwo) {
        JPanel panelOne = classPanels.get(classOne);
        JTextArea textArea = (JTextArea) panelOne.getComponents()[0];
        textArea.setText(classOne.toString());
        classPanels.remove(classOne);
        classPanels.put(classOne, panelOne);

        JPanel panelTwo = classPanels.get(classTwo);
        JTextArea textAreaTwo = (JTextArea) panelTwo.getComponents()[0];
        textAreaTwo.setText(classTwo.toString());
        classPanels.remove(classTwo);
        classPanels.put(classTwo, panelTwo);
        refresh();
    }

    /**
     * Gets text from user input(text box)
     */
    private String getTextFromInput(String message) {
        String strToRtn = JOptionPane.showInputDialog(parentWindow, message, JOptionPane.PLAIN_MESSAGE);

        return strToRtn;
    }

    @Override
    public void showError(String error) {
        // TODO Auto-generated method stub

    }

    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener fieldListener,
            ActionListener relationshipListener) {
        addFileListeners(fileListener);
        addClassListeners(classListener);
        addFieldListeners(fieldListener);
        addRelationshipListeners(relationshipListener);
    }

    @Override
    public void start()
    {
        makeWindow();
        refresh();
    }

    private void addFileListeners(ActionListener fileListener) {
        for (Component item : fileMenu.getMenuComponents()) 
        {
            JMenuItem menuItem = (JMenuItem)item;
            menuItem.addActionListener(fileListener);
        }
    }

    private void addClassListeners(ActionListener classListener)
    {
        for (Component item : classMenu.getMenuComponents()) 
        {
            JMenuItem menuItem = (JMenuItem)item;
            menuItem.addActionListener(classListener);
        }
    }

    private void addFieldListeners(ActionListener fieldListener)
    {
        for (Component item : fieldMenu.getMenuComponents()) 
        {
            JMenuItem menuItem = (JMenuItem)item;
            menuItem.addActionListener(fieldListener);
        }
    }
    private void addRelationshipListeners(ActionListener relationshipListener)
    {
        for (Component item : relationshipMenu.getMenuComponents()) 
        {
            JMenuItem menuItem = (JMenuItem)item;
            menuItem.addActionListener(relationshipListener);
        }
    }
}
