package UML.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import UML.controllers.CreateFieldController;
import UML.controllers.CreateMethodController;
import UML.controllers.CreateRelationshipController;
import UML.controllers.DeleteRelationshipController;
import UML.controllers.EditClassController;
import UML.controllers.EditFieldController;
import UML.controllers.EditMethodController;
import UML.controllers.MouseClickAndDragController;
import UML.controllers.ScrollWheelController;
import java.awt.event.MouseWheelListener;
import java.awt.Font;

public class GraphicalView implements View {

    //Holds menu bar used to navigate program.
    private JMenuBar mb;

    //Displays each individual class.
    private Map<String, JPanel> classPanels;

    //The window for the GUI.
    private JFrame window;

    //The file menu used to save and load.
    private JMenu fileMenu;

    //The menu used to create a class.
    private JMenu classMenu;

    //The menu used to adjust the state.
    private JMenu stateMenu;

    //The base of the GUI view.
    private DrawPanel dp;

    //The pane that allows scrolling.
    private JScrollPane jsp;

    //A map containing the relationships in the view.
    private ConcurrentHashMap<ArrayList<String>, String> relationships;

    //The current font size for the class panels.
    private int fontSize;


    public GraphicalView() {
        this.classPanels = new ConcurrentHashMap<String, JPanel>();
        this.relationships = new ConcurrentHashMap<ArrayList<String>, String>();
        fontSize = 10;
        
    }

    
    // ================================================================================================================================================
    // Setters
    // ================================================================================================================================================
        
    /**
     * Sets the current font size.
     */
    public void setFontSize(int font)
    {
        fontSize = font;
    }

    // ================================================================================================================================================
    // Getters
    // ================================================================================================================================================
    
    /**
     * Returns the font size.
     */
    public int getFontSize()
    {
        return this.fontSize;
    }

    /**
     * Returns the JScrollPane. 
     */
    public JScrollPane getScrollPane()
    {
        return this.jsp;
    }

    /**
     * Returns the DrawPanel.
     */
    public DrawPanel getDrawPanel()
    {
        return this.dp;
    }
    
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
    public String save() 
    {
        //Gets the directory this application was run from...also where the .json files will be saved.
        String userDir = System.getProperty("user.dir");

        //build the file chooser passing in the userDir. 
        JFileChooser fc = new JFileChooser(userDir);
        
        //Filter to only show .json files. 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files","json");
        
        //set the filechooser to use the created filter.
        fc.setFileFilter(filter);
        // Bring up file panel for the user to save as(automatically will choose file
        // type though in saveandload).
        int returnValue = fc.showSaveDialog(dp);
        // Based on the user imput, save the file.

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        else if(returnValue == JFileChooser.CANCEL_OPTION)
        {
            return "No file selected.";
        }
        // if no filename is found, retrun an empty string
        return "";
    }

    /**
     * Loads a JSON representation of the UML diagram.
     */
    @Override
    public String load() 
    {
        //Gets the directory this application was run from...also where the .json files will be saved.
        String userDir = System.getProperty("user.dir");

        // Make a filechooser
        JFileChooser fc = new JFileChooser(userDir);
            
        //Filter to only show .json files. 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files","json");
        
        //set the filechooser to use the created filter.
        fc.setFileFilter(filter);

        int returnValue = fc.showOpenDialog(dp);
        // If the user selected to open this file, open it.
        // Consider filtering this information to only inlcude JSON filetypes
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        } 
        else if(returnValue == JFileChooser.CANCEL_OPTION)
        {
            return "No file selected.";
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

    /**
     * Changes the background color of the gui.
     */
    @Override
    public void changeBackground()
    {
        JColorChooser cc = new JColorChooser();
        
        Color color = cc.showDialog(window,"Choose a background color", Color.PINK);
        
        dp.setBackground(color);
        jsp.setBackground(color);
    }
    

    // ================================================================================================================================================
    // Window setup.
    // ================================================================================================================================================

    /**
     * Constructrs a UMLWindow object.
     */
    public void makeWindow() {
        window = new JFrame("UML");
        window.setLayout(new BorderLayout());
        
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
        dp.setPreferredSize(new Dimension(10000, 10000));
        dp.setLayout(null);

        //Initialize the scroll pane.
        jsp = new JScrollPane(dp);

        JPanel newPanel = new JPanel(new BorderLayout());
        window.add(newPanel, BorderLayout.CENTER);
        newPanel.add(jsp, BorderLayout.CENTER);

        //Repaint and revalidate.
        dp.repaint();;
        jsp.repaint();
        window.repaint();
        dp.revalidate();
        jsp.revalidate();
        window.revalidate();

        dp.setBackground(Color.PINK);
        jsp.setBackground(Color.PINK);

        //Make the scroll bar scroll faster.
        JScrollBar jBar = (JScrollBar) jsp.getVerticalScrollBar();
        jBar.setUnitIncrement(10);
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
        JMenuItem color = new JMenuItem("Color");

        JMenuItem[] arr = {undo, redo, CLI, color};
        String[] text = { "Undo", "Redo", "Show CLI", "Change Color"};
        String[] command = { "Undo", "Redo", "CLI", "Color"};

        for (int count = 0; count < 4; ++count) {
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
     * Deletes a class panel from the window.
     */
    public void deleteClassPanel(String aClass) {
        JPanel panel = classPanels.get(aClass);
        dp.remove(panel);
        classPanels.remove(aClass);
        refresh();
    }

    /**
     * Resizes panels to adjust to text of panel.
     */
    public void resizePanel(String classToString, int x, int y) {
        JPanel panel = classPanels.get(classToString);
        panel.setLocation(x, y);

        //Get the JLabels.
        JPanel innerPanel = (JPanel)panel.getComponent(1);
        JLabel name = (JLabel) innerPanel.getComponent(0);
        JLabel fields = (JLabel) innerPanel.getComponent(1);
        JLabel methods = (JLabel) innerPanel.getComponent(2);

        //Set the font size based on the field.
        name.setFont(new Font("Ariel", 0, fontSize + 4));
        fields.setFont(new Font("Ariel", 0, fontSize));
        methods.setFont(new Font("Ariel", 0, fontSize));

        //Get the preferred height for each JLabel.
        int nameHeight = (int) (name.getPreferredSize().getHeight() + 9);
        int fieldHeight = (int) (fields.getPreferredSize().getHeight() + 10);
        int methodHeight = (int) (methods.getPreferredSize().getHeight() + 10);

        //Calculate total height needed.
        int height = nameHeight + fieldHeight + methodHeight;

        //Calculate total width needed for panel.
        int width = (Math.max((int)name.getPreferredSize().getWidth(), Math.max((int)fields.getPreferredSize().getWidth(), (int)methods.getPreferredSize().getWidth())) + 35);

        panel.setMinimumSize(new Dimension(50, 50));

        panel.setBounds(x, y, width, height);

        name.setBounds(innerPanel.getX(), innerPanel.getY(), width, nameHeight);

        fields.setBounds(innerPanel.getX(), innerPanel.getY() + nameHeight, width, fieldHeight);

        methods.setBounds(innerPanel.getX(), innerPanel.getY() + nameHeight + fieldHeight, width, methodHeight);

        refresh();
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
     * Adds ScrollWheelController to the panel.
     */
    @Override
    public void addListener(ScrollWheelController mouseWheelListener) 
    {
        MouseWheelListener[] mwl = dp.getMouseWheelListeners();
        for(MouseWheelListener listener : mwl)
        {
            dp.removeMouseWheelListener(listener);
        }
        dp.addMouseWheelListener(mouseWheelListener);       
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
