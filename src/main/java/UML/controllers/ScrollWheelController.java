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
        int notches = e.getWheelRotation();
        GraphicalView v = (GraphicalView) view;
        int fontSize = v.getFontSize();
        v.setFontSize(fontSize + notches);
        for(JPanel panel : classPanels.values())
        {
            JPanel p = (JPanel) panel.getComponent(1);
            JLabel label = (JLabel) p.getComponent(0);

            v.resizePanel(store.findClass(label.getText()).toString(), panel.getX(), panel.getY());            
        }
    }
}
