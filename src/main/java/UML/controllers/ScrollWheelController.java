package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 12/2/2020
    Purpose: Handles scroll wheel events for the GUI.
 */

import javax.swing.*;
import UML.model.Store;
import UML.views.View;
import UML.views.GraphicalView;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JPanel;
import java.util.Map;
import java.awt.Point;

public class ScrollWheelController implements MouseWheelListener
{
    public Store store;
    public View view;
    public Controller controller;
    public Map<String, JPanel> classPanels;
    public int numOfNotches;

    public ScrollWheelController(Store s, View v, Controller c)
    {
        this.store = s;
        this.view = v;
        this.controller = c;
        numOfNotches = 0;
        classPanels = view.getPanels();
    }


    @Override
    public void mouseWheelMoved (MouseWheelEvent e)
    {
        //Gets -1 or 1 based direction of the scroll.
        int notches = e.getWheelRotation();
        GraphicalView v = (GraphicalView) view;
        //Gets the font size that is going to be changed unless it is being made to large or small.
        int fontSize = v.getFontSize();
        if((v.getFontSize() >= 6 && notches == 1) || (v.getFontSize() <= 20 && notches == -1))
        {
            //Set the font size which is used for changing the class panel sizes.
            v.setFontSize(fontSize - notches);

            //Get the position of the mouse used for zooming.
            Point val = e.getLocationOnScreen();
            double num = val.getX();
            double num2 = val.getY();

            //Resizes and moves panels to appear like zooming in or out.
            for(JPanel panel : classPanels.values())
            {
                JPanel p = (JPanel) panel.getComponent(1);
                JLabel label = (JLabel) p.getComponent(0);
                
                //Resize the panel based on the scroll.
                v.resizePanel(store.findClass(label.getText()).toString(), panel.getX(), panel.getY());          
                
                //Get the center of the panel.
                double xOffset = panel.getWidth() / 2;
                double yOffset = panel.getHeight() / 2;
                double x = panel.getX() + xOffset;
                double y = panel.getY() + yOffset;

                //Find the distance between the panel and the cursor.
                double distance = Math.sqrt(Math.pow(num - x, 2) + Math.pow(num2 - y, 2));

                //Negative or positive determined by relative position of cursor and panel.
                double valX = num - x > 0 ? -1 : 1;
                double valY = num2 - y > 0 ? -1 : 1;

                if(num - x == 0)
                    valX = 0;
                if(num - y == 0)
                    valY = 0;

                //Scale the values to make the movement fluid.
                valX *= distance / 50;
                valY *= distance / 50;

                //Translate the panel.
                panel.setLocation((int) (x - xOffset - (notches * valX)), (int) (y - yOffset - (notches * valY)));
                
            }
        }   
    }
}
