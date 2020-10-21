package UML.views;

import UML.model.Class;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.border.Border;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import UML.controllers.MouseClickAndDragController; 
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import java.awt.Graphics;

public class DrawPanel extends JPanel 
{
    private View view;

    public DrawPanel(View v)
    {
        this.view = v;
    }

    public void paintComponent(Graphics g) 
    {
        g.setColor(Color.BLACK);
        for(Map.Entry<ArrayList<String>, String> relationship : view.getRelationships().entrySet())
        {
            Dimension fromLoc = view.getLoc(relationship.getKey().get(0));
            Dimension toLoc = view.getLoc(relationship.getKey().get(1));
            Map<String, JPanel> panels = view.getPanels();
            Dimension fromSize = panels.get(relationship.getKey().get(0)).getSize();
            Dimension toSize = panels.get(relationship.getKey().get(1)).getSize();
            int[] line = getClosest((int)fromLoc.getWidth(), (int)(fromLoc.getWidth() + fromSize.getWidth()), 
                                                (int)fromLoc.getHeight(), (int)(fromLoc.getHeight() + fromSize.getHeight()), 
                                                (int)toLoc.getWidth(), (int)(toLoc.getWidth() + toSize.getWidth()),
                                                (int)toLoc.getHeight(), (int)(toLoc.getHeight() + toSize.getHeight()));
            g.drawLine(line[0], line[1], line[2], line[3]);
        }
    
    }
     
    public int[] getClosest(int fromLeft, int fromRight, int fromUp, int fromDown, int toLeft, int toRight, int toUp, int toDown)
    {
        //fromX, fromY, toX, toY.
        int[] x = new int[4];
        //From is to the right.
        if(fromLeft > toRight)
        {   
            x[0] = fromLeft;
            x[2] = toRight;
            //Up and to the right.
            if(fromDown < toUp)
            {
                x[1] = fromDown;
                x[3] = toUp;
            }
            else if(fromUp > toDown)
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
            else
            {
                if(fromUp < toUp)
                {
                    x[1] = ((fromDown - toUp) / 2) + toUp;
                    x[3] = ((fromDown - toUp) / 2) + toUp;
                }
                else
                {
                    x[1] = ((toDown - fromUp) / 2) + fromUp;
                    x[3] = ((toDown - fromUp) / 2) + fromUp;
                }
            }
        }
        //From is to the left.
        else if(fromRight < toLeft)
        {
            x[0] = fromRight;
            x[2] = toLeft;
            //Up and to the left.
            if(fromDown < toUp)
            {
                x[1] = fromDown;
                x[3] = toUp;
            }
            else if(fromUp > toDown)
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
            else
            {
                if(fromUp < toUp)
                {
                    x[1] = ((fromDown - toUp) / 2) + toUp;
                    x[3] = ((fromDown - toUp) / 2) + toUp;
                }
                else
                {
                    x[1] = ((toDown - fromUp) / 2) + fromUp;
                    x[3] = ((toDown - fromUp) / 2) + fromUp;
                }
            }
        }
        //From is above.
        else if(fromDown < toUp)
        {
            x[1] = fromDown;
            x[3] = toUp;
            //Up and to the left.
            if(fromLeft > toRight)
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
            else if(fromRight < toLeft)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            else
            {
                if(fromLeft < toLeft)
                {
                    x[0] = ((fromRight - toLeft) / 2) + toLeft;
                    x[2] = ((fromRight - toLeft) / 2) + toLeft;
                }
                else
                {
                    x[0] = ((toRight - fromLeft) / 2) + fromLeft;
                    x[2] = ((toRight - fromLeft) / 2) + fromLeft;
                }
            }
        }
        //From is below
        else if(fromUp > toDown)
        {
            x[1] = fromUp;
            x[3] = toDown;
            //Down and to the right.
            if(fromLeft > toRight)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            else if(fromRight < toLeft)
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
            else
            {
                if(fromLeft < toLeft)
                {
                    x[0] = ((fromRight - toLeft) / 2) + toLeft;
                    x[2] = ((fromRight - toLeft) / 2) + toLeft;
                }
                else {
                    x[0] = ((toRight - fromLeft) / 2) + fromLeft;
                    x[2] = ((toRight - fromLeft) / 2) + fromLeft;
                }
            }
        }
        else 
        {
            x[0] = fromLeft;
            x[1] = fromUp;
            x[2] = toLeft;
            x[3] = toUp;
        }

        return x;
    }
}
