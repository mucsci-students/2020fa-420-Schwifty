
import javax.swing.JOptionPane;
import javax.swing.*;
public class umlWindow 
{
   public umlWindow()
   {
      window();
   }
   public void window() 
   {
        JFrame window = new JFrame("UML");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800,750);
        window.setVisible(true);
        Menu menu = new Menu(window);
   }

}