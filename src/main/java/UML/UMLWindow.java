

import javax.swing.JOptionPane;
import javax.swing.*;
public class UMLWindow 
{
   private JFrame window;
   private Menu menu;
   public UMLWindow()
   {
       window = new JFrame("UML");
       menu = new Menu();
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