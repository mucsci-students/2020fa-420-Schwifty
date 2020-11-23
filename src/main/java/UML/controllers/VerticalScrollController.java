package UML.controllers;

import UML.views.*;
import UML.model.*;

import java.awt.*;
import javax.swing.*;
import UML.model.Store;
import UML.views.View;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import UML.views.DrawPanel;
import UML.views.PanelBuilder;
import javax.swing.JLabel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.LayoutManager;
import java.awt.FlowLayout;


public class VerticalScrollController implements AdjustmentListener
{
    private Store store;
	private View view;
    private Controller controller;
    
	
    public VerticalScrollController(Store s, View v, Controller c) 
    {
		this.view = v;
		this.store = s;
		this.controller = c;
    }
    
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        int val = e.getValue();

        System.out.println("TEST");


        for(JPanel p : view.getPanels().values())
        {
            //Get new location.
            //int newX = found.getX();
            //int newY = found.getY() + (e.getY() - startDragY);
            p.setLocation(50, 50);
        }
    }
    
}

/**
 *          for(JPanel p : view.getPanels().values())
            {
                //Get new location.
                int newX = found.getX();
                int newY = found.getY() + (e.getY() - startDragY);
                p.setLocation(50, 50);
            }
 */