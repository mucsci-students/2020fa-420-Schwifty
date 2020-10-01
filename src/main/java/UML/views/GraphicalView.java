package UML.views;

import UML.model.Class;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
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
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.border.Border;

import UML.controllers.ClassClickController;
import UML.controllers.FieldClickController;
import UML.controllers.FileClickController;
import UML.controllers.RelationshipClickController;

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

    public GraphicalView() {
        makeWindow();
        createMenu();
        this.classPanels = new HashMap<String, JPanel>();
    }

    @Override
    public void createClass(String name) {
        // TODO Auto-generated method stub
        makeNewClassPanel(name);
    }

    @Override
    public void deleteClass(String name) {
        // TODO Auto-generated method stub
        deleteClassPanel(name);
    }

    @Override
    public void updateClass(String oldString, String newString) {
        // TODO Auto-generated method stub
        JPanel panel = classPanels.get(oldString);
        classPanels.remove(oldString);
        classPanels.put(newString, panel);
        windowUpdateHelper(newString);
    }

    @Override
    public void createRelationship(String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRelationship(String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateRelationship(String name) {
        // TODO Auto-generated method stub

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
    public void display() {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(File fileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void load(File fileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub

    }
    
    private void windowUpdateHelper(String classInfo)
    {
        JPanel panel = classPanels.get(classInfo);
        JTextArea textArea = (JTextArea)panel.getComponents()[0];
        textArea.setText(classInfo);
        refresh();
    }
    /**
     * Constructrs a UMLWindow object.
     */
    public void makeWindow() {
        window = new JFrame("UML");
        window.setLayout(new GridLayout(5, 5));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 750);
        createMenu();
        window.setJMenuBar(mb);
        window.setVisible(true);
    }

    public void createMenu() {
        // Set up the menu with helpers and add them to the main window.
        parentWindow.setLayout(new GridLayout(5, 5));
        mb = new JMenuBar();
        createFileMenu(mb);
        createClassMenu(mb);
        createAtrributeMenu(mb);
        createRelationshipMenu(mb);
        window.add(mb);
    }

    public JFrame getMainWindow() {
        return window;
    }

    /* Creates a file menu. */
    private void createFileMenu(JMenuBar mb) {
        JMenu file = new JMenu("File");
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
            file.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            if (count < 3) {
                arr[count].setActionCommand(command[count]);
                arr[count].addActionListener(new FileClickController(store, this));
            } else
                arr[count].addActionListener((event) -> System.exit(0));
        }

        mb.add(file);
    }

    /**
     * Creates the class menu options by taking in the menu bar and adding them to
     * it.
     */
    private void createClassMenu(JMenuBar mb) {

        JMenu classes = new JMenu("Class");

        // Create sub menus.
        JMenuItem crtClass = new JMenuItem("Create class");
        JMenuItem deleteClass = new JMenuItem("Delete class");
        JMenuItem rnClass = new JMenuItem("Rename class");

        JMenuItem[] arr = { crtClass, deleteClass, rnClass };
        String[] text = { "Create Class", "Delete Class", "Rename Class" };
        String[] command = { "Create", "Delete", "Rename" };

        for (int count = 0; count < 3; ++count) {
            classes.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
            arr[count].addActionListener(new ClassButtonClickController());
        }
        mb.add(classes);
    }

    /**
     * Creates the field menu options by taking in the menu bar and adding them to
     * it.
     */
    private void createAtrributeMenu(JMenuBar mb)// change spelling later on
    {
        JMenu fields = new JMenu("Field");

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
            fields.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
            arr[count].addActionListener(new FieldButtonClickController());
        }
        mb.add(fields);
    }

    /**
     * Creates the relationship menu options by taking in the menu bar and adding
     * them to it.
     */
    private void createRelationshipMenu(JMenuBar mb) {
        JMenu relate = new JMenu("Relate");
        // Create JMenuItems for each type of relationship and deleting a relationship.
        JMenuItem association = new JMenuItem("Association");
        JMenuItem aggregation = new JMenuItem("Aggregation");
        JMenuItem composition = new JMenuItem("Composition");
        JMenuItem generalization = new JMenuItem("Generalization");
        JMenuItem deleteRelate = new JMenuItem("Delete Relationship");
        // Create arrays of JMenuItems and Strings to use for loop for setting up
        // relationship menu.
        JMenuItem[] arr = { association, aggregation, composition, generalization, deleteRelate };
        String[] names = { "Association", "Aggregation", "Composition", "Generalization", "Delete Relationship" };

        for (int count = 0; count < 5; ++count) {
            relate.add(arr[count]);
            if (count < 4)
                arr[count].setToolTipText("Creates selected relationship between two classes");
            else
                arr[count].setToolTipText("Deletes selected relationship between two classes");

            arr[count].setActionCommand(names[count]);
            arr[count].addActionListener(new RelationshipButtonClickController());
        }
        mb.add(relate);
    }

    /**
     * Creates a panel on the window to display information about a class.
     */
    public void makeNewClassPanel(String aClass) {
        JPanel classPanel = new JPanel();
        classPanel.setSize(20, 200);
        classPanel.setVisible(true);
        parentWindow.add(classPanel);
        JTextArea classText = new JTextArea(aClass);
        classText.setEditable(false);
        classPanel.add(classText);
        classPanels.put(aClass, classPanel);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        classText.setBorder(blackline);
    }

    public void deleteClassPanel(String aClass) 
    {
        JPanel panel = classPanels.get(aClass);
        classPanels.remove(aClass);
        parentWindow.remove(panel);
        refresh();

    }

    public void refresh()
    {
        parentWindow.revalidate();
        parentWindow.repaint();
    }

    /**
     * Updates the relationships visually in the window .
     *
     */
    public void updateDisplayRelationship(Class classOne, Class classTwo) {
        JPanel panelOne = classPanels.get(classOne.getName());
        JTextArea textArea = (JTextArea) panelOne.getComponents()[0];
        textArea.setText(classOne.toString());
        classPanels.remove(classOne.getName());
        classPanels.put(classOne.getName(), panelOne);

        JPanel panelTwo = classPanels.get(classTwo.getName());
        JTextArea textAreaTwo = (JTextArea) panelTwo.getComponents()[0];
        textAreaTwo.setText(classTwo.toString());
        classPanels.remove(classTwo.getName());
        classPanels.put(classTwo.getName(), panelTwo);
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

}
