package UML;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.*;
public class UMLWindow 
{
   private JFrame window;
   private Menu menu;
   public UMLWindow(ArrayList<Class> storage)
   {
       window = new JFrame("UML");
       menu = new Menu();
       menu.SetClassStore(storage);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.setSize(800,750);
       menu.createMenu(window);
       window.setJMenuBar(menu.getMenuBar());
       window.setVisible(true);
   }
   
   public JFrame getMainWindow()
   {
      return window;
   }

}