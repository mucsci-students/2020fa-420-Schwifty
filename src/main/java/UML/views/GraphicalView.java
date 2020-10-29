package UML.views;

/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an implementation of the GUI view.
 */
import UML.model.Class;
import javax.swing.JTextArea;
import javax.swing.MenuElement;
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
import java.awt.FlowLayout;
import UML.controllers.FieldClickController;
import UML.controllers.MouseClickAndDragController;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class GraphicalView implements View {

    // Holds menu bar used to navigate program.
    private JMenuBar mb;

    // Displays each individual class.
    private Map<String, JPanel> classPanels;

    // The window for the GUI.
    private JFrame window;

    private JMenu fileMenu;
    private JMenu classMenu;
    private JMenu stateMenu;
    private JMenu relationshipMenu;
    private Graphics graphics;
    private DrawPanel dp;
    private ConcurrentHashMap<ArrayList<String>, String> relationships;

    public GraphicalView() {
        this.classPanels = new ConcurrentHashMap<String, JPanel>();
        this.relationships = new ConcurrentHashMap<ArrayList<String>, String>();
    }

    // ================================================================================================================================================
    // Getters
    // ================================================================================================================================================

    /**
     * Returns the map containing the class panels.
     */
    @Override
    public Map<String, JPanel> getPanels() {
        return this.classPanels;
    }

    /**
     * Returns the dimension storing the location of a specific class panel.
     */
    @Override
    public Dimension getLoc(String name) {
        JPanel panel = classPanels.get(name);
        int x = panel.getX();
        int y = panel.getY();
        Dimension loc = new Dimension(x, y);
        return loc;
    }

    // ================================================================================================================================================
    // Panel chnages.
    // ================================================================================================================================================

    /**
     * Creates a class panel to be displayed.
     */
    @Override
    public void createClass(String classToString, int x, int y) {
        ClassPanelBuilder classPanelBuilder = new ClassPanelBuilder(classToString, dp);
        JPanel newClassPanel = classPanelBuilder.makeNewClassPanel();
        classPanels.put(classToString, newClassPanel);
        resizePanel(classToString, x, y);
        dp.add(newClassPanel);
    }

    /**
     * Deletes a class panel that was being displayed.
     */
    @Override
    public void deleteClass(String name) {
        deleteClassPanel(name);
        deleteRelationships(name);
    }

    /**
     * Returns the map containing all the information about relationships in the view.
     */
    public Map<ArrayList<String>, String> getRelationships() {
        return relationships;
    }

    /**
     * Adds a relationship in the map that represents relationships between class panels.
     */
    @Override
    public void addRelationship(String from, String to, String type) {
        ArrayList<String> toAdd = new ArrayList<String>();
        toAdd.add(from);
        toAdd.add(to);
        relationships.put(toAdd, type);
    }

    /**
     * Deletes a relationship between two class panels.
     */
    @Override
    public void deleteRelationship(String from, String to) {
        for (ArrayList<String> classes : getRelationships().keySet()) {
            if (classes.get(0).equals(from) && classes.get(1).equals(to)) {
                relationships.remove(classes);
            }
        }
    }

    /**
     * Deletes all relationships from or a to a specified class.
     */
    private void deleteRelationships(String name) {
        for (ArrayList<String> classes : getRelationships().keySet()) {
            if (classes.get(0).equals(name)) {
                relationships.remove(classes);
            } else if (classes.get(1).equals(name)) {
                relationships.remove(classes);
            }
        }
    }

    /**
     * Updates a class panel that is already being displayed on the window.
     */
    @Override
    public void updateClass(String oldString, String newString) {
        for (ArrayList<String> classes : getRelationships().keySet()) {
            if (classes.get(0).equals(oldString)) {
                String value = relationships.get(classes);
                relationships.remove(classes);
                ArrayList<String> toPut = new ArrayList<String>();
                toPut.add(newString);
                toPut.add(classes.get(1));
                relationships.put(toPut, value);
            } else if (classes.get(1).equals(oldString)) {
                String value = relationships.get(classes);
                relationships.remove(classes);
                ArrayList<String> toPut = new ArrayList<String>();
                toPut.add(classes.get(0));
                toPut.add(newString);
                relationships.put(toPut, value);
            }
        }
        JPanel panel = classPanels.get(oldString);
        int x = panel.getX();
        int y = panel.getY();
        Dimension loc = getLoc(oldString);
        classPanels.remove(oldString);
        classPanels.put(newString, panel);
        // Just sets TextArea.
        windowUpdateHelper(newString, loc);
        resizePanel(newString, (int) loc.getWidth(), (int) loc.getHeight());
        resizePanel(newString, x, y);
        refresh();
    }

    /**
     * Helps update the window.
     */
    private void windowUpdateHelper(String classInfo, Dimension loc) {
        JPanel aPanel = classPanels.get(classInfo);
        JTextArea textArea = (JTextArea) aPanel.getComponents()[0];
        int stop = classInfo.indexOf("Relationships To Others: ");
        String newText =  classInfo.substring(0, stop);
        textArea.setText(newText);
    }

    // ================================================================================================================================================
    // Getting user input.
    // ================================================================================================================================================

    /**
     * Gets a choice from the GUI user.
     */
    @Override
    public String getChoiceFromUser(String msgOne, String msgTwo, ArrayList<String> options) {
        Object[] optionsToArray = options.toArray();
        String strToRtn = (String) JOptionPane.showInputDialog(dp, msgOne, msgTwo, JOptionPane.PLAIN_MESSAGE, null,
                optionsToArray, null);
        return strToRtn;
    }

    /**
     * Creates a String from the GUI user.
     */
    @Override
    public String getInputFromUser(String prompt) {
        String strToRtn = JOptionPane.showInputDialog(dp, prompt, "", JOptionPane.PLAIN_MESSAGE);

        return strToRtn;
    }

    /**
     * Refreshes class panel window.
     */
    @Override
    public void display(ArrayList<String> toStrings) {

        for (Map.Entry<String, JPanel> panel : classPanels.entrySet()) {
            dp.remove(panel.getValue());
        }

        classPanels.clear();

        for (String s : toStrings) {
            makeNewClassPanel(s);
        }
        refresh();
    }

    // ================================================================================================================================================
    // Save and Load.
    // ================================================================================================================================================

    /**
     * Saves a JSON representation of the UML diagram.
     */
    @Override
    public String save() {
        JFileChooser fc = new JFileChooser();
        // If there is a currently loaded file.

        // Bring up file panel for the user to save as(automatically will choose file
        // type though in saveandload).
        int returnValue = fc.showSaveDialog(dp);
        // Based on the user imput, save the file.

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        // if no filename is found, retrun an empty string
        return "";
    }

    /**
     * Loads a JSON representation of the UML diagram.
     */
    @Override
    public String load() {
        // Make a filechooser
        JFileChooser fc = new JFileChooser();
        int returnValue = fc.showOpenDialog(dp);
        // If the user selected to open this file, open it.
        // TODO: Consider filtering this information to only inlcude JSON filetypes
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        return "";

    }

    /**
     * Will exit the window but not close application.
     */
    @Override
    public void exit() {
        // Get rid of window. Make sure call save and load in controller.

    }

    // ================================================================================================================================================
    // Window setup.
    // ================================================================================================================================================

    /**
     * Constructrs a UMLWindow object.
     */
    public void makeWindow() {
        window = new JFrame("UML");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 800);
        window.setPreferredSize(new Dimension(800, 800));
        createMenu();
        window.add(mb);
        window.setJMenuBar(mb);
        dp = new DrawPanel(this);
        window.add(dp);
        window.setVisible(true);
        dp.setVisible(true);
        // dp.setBackground(Color.BLUE);
        dp.setPreferredSize(new Dimension(800, 800));
        graphics = dp.getGraphics();
        graphics.setColor(Color.WHITE);
        dp.setLayout(null);
    }

    /**
     * Creates the menubar.
     */
    public void createMenu() {
        // Set up the menu with helpers and add them to the main window.
        mb = new JMenuBar();
        createFileMenu(mb);
        createClassMenu(mb);
        createStateMenu(mb);
        createRelationshipMenu(mb);
        mb.setVisible(true);
    }

    /**
     * Returns the main window.
     */
    @Override
    public JFrame getMainWindow() {
        return window;
    }

    /**
     * Creates a file menu.
     */
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
    private void createStateMenu(JMenuBar mb)
    {
        stateMenu = new JMenu("State");

    
        JMenuItem undo = new JMenuItem("Undo");

        JMenuItem redo = new JMenuItem("Redo");

        JMenuItem[] arr = {undo, redo};
        String[] text = { "Undo", "Redo"};
        String[] command = { "Undo", "Redo" };

        for (int count = 0; count < 2; ++count) {
            stateMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
        }
        mb.add(stateMenu);
    }

    /**
     * Creates the relationship menu options by taking in the menu bar and adding
     * them to it.
     */
    private void createRelationshipMenu(JMenuBar mb) {
        relationshipMenu = new JMenu("Relate");
        // Create JMenuItems for each type of relationship and deleting a relationship.
        JMenuItem realization = new JMenuItem("Realization");
        JMenuItem aggregation = new JMenuItem("Aggregation");
        JMenuItem composition = new JMenuItem("Composition");
        JMenuItem generalization = new JMenuItem("Generalization");
        JMenuItem deleteRelate = new JMenuItem("Delete Relationship");
        // Create arrays of JMenuItems and Strings to use for loop for setting up
        // relationship menu.
        JMenuItem[] arr = { realization, aggregation, composition, generalization, deleteRelate };
        String[] names = { "Realization", "Aggregation", "Composition", "Generalization", "DeleteRelationship" };

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

    // ================================================================================================================================================
    // Create, delete, resize class panels + refresh the window.
    // ================================================================================================================================================

    /**
     * Creates a panel on the window to display information about a class.
     */
    public void makeNewClassPanel(String aClass) {
        JPanel classPanel = new JPanel();
        JTextArea classText = new JTextArea(aClass);
        classText.setEditable(false);
        classPanel.add(classText);
        classPanels.put(aClass, classPanel);
        classPanel.setSize(classText.getSize());
        Border blackline = BorderFactory.createLineBorder(Color.black);
        classText.setBorder(blackline);
        classPanel.setVisible(true);
        dp.add(classPanel);
    }

    /**
     * Deletes a class panel from the window.
     */
    public void deleteClassPanel(String aClass) {
        JPanel panel = classPanels.get(aClass);
        dp.remove(panel);
        classPanels.remove(aClass);
        refresh();
    }

    /**
     * Resizes panels to adjust to text of panel
     */
    public void resizePanel(String classToString, int x, int y) {
        JPanel panel = classPanels.get(classToString);
        Scanner lineScanner = new Scanner(classToString);
        int longest = 0;
        int height = 0;
        while (lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine();
            Scanner scanner = new Scanner(line);
            int localBest = 0;
            while (scanner.hasNext()) {
                localBest++;
                scanner.next();
            }
            scanner.close();
            ++height;
            if (localBest > longest)
                longest = localBest;
        }
        lineScanner.close();
        panel.setLocation(x, y);
        panel.setBounds(x, y, (longest * 20) + 150, height * 20);
        refresh();
    }

    /**
     * Refreshes the window.
     */
    public void refresh() {
        ArrayList<Dimension> dimensions = new ArrayList<Dimension>();
        for (Map.Entry<String, JPanel> panel : classPanels.entrySet()) {
            dimensions.add(new Dimension(panel.getValue().getX(), panel.getValue().getY()));
        }
        dp.revalidate();
        dp.repaint();
        int counter = 0;
        for (Map.Entry<String, JPanel> panel : classPanels.entrySet()) {
            panel.getValue().setLocation((int) dimensions.get(counter).getWidth(),
                    (int) dimensions.get(counter).getHeight());
            ++counter;
        }
    }
    
    /**
     * Updates the relationships visually in the window .
     */

    /**
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
    */
    
    /**
     * Displays an error message.
     */
    @Override
    public void showError(String error) {
        JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);

    }

    // ================================================================================================================================================
    // Listener adding.
    // ================================================================================================================================================

    /**
     * Adds action lisnters for buttons.
     */
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener stateListener,
            ActionListener relationshipListener) {
        addFileListeners(fileListener);
        addClassListeners(classListener);
        addStateListeners(stateListener);
        addRelationshipListeners(relationshipListener);
    }

    /**
     * Creates the initial window.
     */
    @Override
    public void start() {
        makeWindow();
        refresh();
    }

    /**
     * Adds the listeners for the file buttons.
     */
    private void addFileListeners(ActionListener fileListener) 
    {
        for (Component item : fileMenu.getMenuComponents()) {
            JMenuItem menuItem = (JMenuItem) item;
            menuItem.addActionListener(fileListener);
        }
    }

    /**
     * Adds the listeners for the class buttons.
     */
    private void addClassListeners(ActionListener classListener) 
    {
        for (Component item : classMenu.getMenuComponents()) {
            JMenuItem menuItem = (JMenuItem) item;
            menuItem.addActionListener(classListener);
        }
    }

    /**
     * Adds the listeners for the state buttons.
     */
    private void addStateListeners(ActionListener fieldListener) 
    {
        for (Component item : stateMenu.getMenuComponents()) {
            JMenuItem menuItem = (JMenuItem) item;
            menuItem.addActionListener(fieldListener);
        }
    }

    /**
     * Adds the listeners for the relationship buttons.
     */
    private void addRelationshipListeners(ActionListener relationshipListener) 
    {
        for (Component item : relationshipMenu.getMenuComponents()) {
            JMenuItem menuItem = (JMenuItem) item;
            menuItem.addActionListener(relationshipListener);
        }
    }

    /**
     * Adds listener for specified class panel.
     */
    @Override
    public void addListener(MouseClickAndDragController mouseListener, String classText) 
    {
        JPanel panel = classPanels.get(classText);
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseListener);
    }

    /**
     * Adds listener for editing a class panel.
     */
    @Override
    public void addPanelListener(FieldClickController fieldController, String classText) 
    {
        JPanel panel = classPanels.get(classText);
        JMenuBar menuBar = null;
        for(Component c : panel.getComponents())
        {
            if(c instanceof JMenuBar)
            {
                menuBar = (JMenuBar)c;
            }
        }
        JMenu menu = (JMenu) menuBar.getMenu(0);
        for(int count = 0; count < 10; count++)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(count);
            menuItem.addActionListener(fieldController);
        }
    }

    // ================================================================================================================================================
    // "Do Nothing" methods
    // ================================================================================================================================================

    @Override
    public void showHelp() {
        // Do nothing.
    }

    @Override
    public void addListener(ActionListener listener) {
        // Do nothing.
    }

}
