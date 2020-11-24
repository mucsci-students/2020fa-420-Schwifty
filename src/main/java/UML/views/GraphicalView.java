package UML.views;

/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Provides an implementation of the GUI view.
 */
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import UML.controllers.CreateFieldController;
import UML.controllers.CreateMethodController;
import UML.controllers.EditClassController;
import UML.controllers.CreateRelationshipController;
import UML.controllers.DeleteRelationshipController;
import UML.controllers.EditFieldController;
import UML.controllers.EditMethodController;
import UML.controllers.MouseClickAndDragController;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import UML.model.Store;
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
     * TODO: Maybe make a do nothing method.
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
        // Consider filtering this information to only inlcude JSON filetypes
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        return "";
    }

    /**
     * Will exit the window but not close application.
     */
    @Override
    public void exit() 
    {
        // Get rid of window. Make sure call save and load in controller.
    }


    // ================================================================================================================================================
    // Visibility setting.
    // ================================================================================================================================================

    /**
     * Sets the GUI window to no longer be visible. 
     */
    @Override
    public void setGUIInvisible() 
    {
        window.setVisible(false);
	}

    /**
     * Sets the GUI window visible. 
     */
	@Override
    public void setGUIVisible() 
    {
		window.setVisible(true);
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

        classMenu.add(crtClass);
        crtClass.setToolTipText("Create Class");
        crtClass.setActionCommand("Create");
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
        JMenuItem CLI = new JMenuItem("CLI");

        JMenuItem[] arr = {undo, redo, CLI};
        String[] text = { "Undo", "Redo", "Show CLI"};
        String[] command = { "Undo", "Redo", "CLI"};

        for (int count = 0; count < 3; ++count) {
            stateMenu.add(arr[count]);
            arr[count].setToolTipText(text[count]);
            arr[count].setActionCommand(command[count]);
        }
        mb.add(stateMenu);
    }

    // ================================================================================================================================================
    // Create, delete, resize class panels + refresh the window.
    // ================================================================================================================================================

    /**
     * Creates a panel on the window to display information about a class.
     * TODO: Discuss removal of this method, appears to no longer be needed. 
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

        //Get class name length.
        int start = classToString.indexOf("name: ") + 6;
        int stop = classToString.indexOf("Field ");
        int nameLength = stop - 32 - start;

        //Get field length and height.
        int[] fieldSize = getSizes("Field Names: ","Methods: ", classToString);

        //Get method length and height.
        int[] methodSize = getSizes("Methods: ","Relationships To Others: ", classToString);

        panel.setLocation(x, y);
        JPanel innerPanel = (JPanel)panel.getComponent(4);
        JLabel name = (JLabel) innerPanel.getComponent(0);
        JLabel fields = (JLabel) innerPanel.getComponent(1);
        JLabel methods = (JLabel) innerPanel.getComponent(2);

        int width = (Math.max(nameLength, Math.max(fieldSize[0], methodSize[0])) + 10) * 7;

        panel.setBounds(x, y, width, (fieldSize[1] + 4) * 15 + (methodSize[1] + 3) * 15 + 30);
        
        name.setBounds(innerPanel.getX(), innerPanel.getY(), width, 20);

        fields.setBounds(innerPanel.getX(), innerPanel.getY() + 20, width, (fieldSize[1] + 3) * 15);

        methods.setBounds(innerPanel.getX(), innerPanel.getY() + (fieldSize[1] + 3) * 15 + 20, width, (methodSize[1] + 3) * 15);

        refresh();
    }

    /**
     * Returns the max length and the height of a blcok of text.
     */
    private int[] getSizes(String begin, String end, String classToString)
    {
        //Specify the block we care about by giving beginning and end substrings.
        int start = classToString.indexOf(begin);
        int stop = classToString.indexOf(end);
        String scan = classToString.substring(start, stop - 32);
        Scanner scanner = new Scanner(scan);

        int longest = 0;
        int height = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            int localBest = 0;
            while (lineScanner.hasNext()) {
                localBest += lineScanner.next().length();
                localBest++;
            }
            lineScanner.close();
            ++height;
            if (localBest > longest)
                longest = localBest;
        }
        scanner.close();
        int[] toReturn = {longest, height};
        return toReturn;
    }


    /**
     * Refreshes the window.
     */
    public void refresh() 
    {
        dp.revalidate();
        dp.repaint();
    }
    
    /**
     * Displays an error message.
     */
    @Override
    public void showError(String error) 
    {
        JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ================================================================================================================================================
    // Listener adding.
    // ================================================================================================================================================

    /**
     * Adds action lisnters for buttons.
     */
    public void addListeners(ActionListener fileListener, ActionListener classListener, ActionListener stateListener) {
        addFileListeners(fileListener);
        addClassListeners(classListener);
        addStateListeners(stateListener);
    }

    /**
     * Creates the initial window.
     */
    @Override
    public void start() 
    {
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
    public void addPanelListener(ActionListener listener, String classText) 
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
        
        //Add the listener to the correct MenuItem.
        if(listener instanceof CreateFieldController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(0);
            menuItem.addActionListener(listener);
        }
        else if(listener instanceof EditFieldController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(1);
            menuItem.addActionListener(listener);
        }
        else if(listener instanceof CreateMethodController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(2);
            menuItem.addActionListener(listener);
        }
        else if(listener instanceof EditMethodController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(3);
            menuItem.addActionListener(listener);
        }
        else if(listener instanceof CreateRelationshipController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(4);
            menuItem.addActionListener(listener);
        }
        else if(listener instanceof DeleteRelationshipController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(5);
            menuItem.addActionListener(listener);
        }
        else if(listener instanceof EditClassController)
        {
            JMenuItem menuItem = (JMenuItem) menu.getItem(6);
            menuItem.addActionListener(listener);
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

    @Override
    public void display(String toStrings) {
        //Do nothing.
    }
}
