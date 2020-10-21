package UML.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
public interface PanelBuilder
{
    void setClassData(String classData);
    void setParentWindow(DrawPanel window);
    JPanel buildClassPanel();
}
