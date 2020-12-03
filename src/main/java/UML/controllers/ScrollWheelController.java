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
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import UML.views.DrawPanel;
import javax.swing.JPanel;
import java.util.Map;
import java.awt.Font;

import java.awt.Dimension;

public class ScrollWheelController implements MouseWheelListener
{
    public Store store;
    public View view;
    public Controller controller;
    public Map<String, JPanel> classPanels;
    public int numOfNotches;

    public ScrollWheelController(Store s, View v, Controller c, Map<String, JPanel> panels)
    {
        this.store = s;
        this.view = v;
        this.controller = c;
        classPanels = panels;
        numOfNotches = 0;
    }

    @Override
    public void mouseWheelMoved (MouseWheelEvent e)
    {
        int notches = e.getWheelRotation();
        for(JPanel panel : classPanels.values())
        {
            if(true)
            {
                numOfNotches += notches;
                Dimension size = panel.getSize();
                double height = size.getHeight();
                double width = size.getWidth();  

                GraphicalView v = (GraphicalView) view;
                int fontSize = v.getFontSize();
                v.setFontSize(fontSize + notches);

                JPanel p = (JPanel) panel.getComponent(1);
                JLabel label = (JLabel) p.getComponent(0);

                v.resizePanel(store.findClass(label.getText()).toString(), panel.getX(), panel.getY());
                
            }
            
        }
    }
}
