package UML.views;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/26/2020
    Purpose: Interface for building panels
 */
import javax.swing.JPanel;

public interface PanelBuilder
{
    void setClassData(String classData);
    void setParentWindow(DrawPanel window);
    JPanel buildClassPanel();
}
