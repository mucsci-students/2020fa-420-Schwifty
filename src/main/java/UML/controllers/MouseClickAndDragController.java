package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/15/2020
    Purpose: Handles mouse click and drag events for the GUI.
 */

import javax.swing.*;
import UML.model.Store;
import UML.views.View;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import UML.views.DrawPanel;
import javax.swing.JLabel;

import java.awt.Dimension;

public class MouseClickAndDragController implements MouseListener, MouseMotionListener
{
    private int startDragX, startDragY;
    /*check about layout of grid for panels or freely placing */
    private Store store;
	private View view;
    private Controller controller;
    
	
	public MouseClickAndDragController(Store s, View v, Controller c) {
		this.view = v;
		this.store = s;
		this.controller = c;
	}
	@Override
    public void mousePressed(MouseEvent e) 
    {
        startDragX = e.getX();
        startDragY = e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        Object source = e.getSource();
        if(source instanceof JPanel)
        {
            JPanel found = (JPanel)source;
            JPanel panel = (JPanel)found.getComponent(1);
            JLabel text = (JLabel)panel.getComponent(0);
            //Get the class name from the test on the top panel.
            String className = text.getText().trim();

            UML.model.Class c = store.findClass(className);

            //Get new location.
            int newX = found.getX() + (e.getX() - startDragX);
            int newY = found.getY() + (e.getY() - startDragY);
            c.setLocation(new Dimension(newX, newY));

            //Set the location of the panel.
            found.setLocation(newX, newY);
            view.getMainWindow().repaint();
            DrawPanel dp = new DrawPanel(view);

            //Paint the panel.
            dp.paintComponent(view.getMainWindow().getGraphics());
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // Do nothing
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        //  Do nothing
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //  Do nothing
        //JOptionPane.showMessageDialog(null,"mouse clicked!");
    }
    @Override
    public void mouseMoved(MouseEvent arg0) {
        //  Do nothing
    }
    @Override
    public void mouseReleased(MouseEvent e) {
       // Do nothing
    }
}
