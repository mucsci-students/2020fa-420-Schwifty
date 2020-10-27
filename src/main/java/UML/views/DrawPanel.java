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
import javax.lang.model.util.ElementScanner6;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import UML.controllers.MouseClickAndDragController; 
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;


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
            if(relationship.getValue().equals("REALIZATION"))
            {
                //Do a dotted line
                g.setColor(Color.BLACK);
                Graphics2D g2d = (Graphics2D)g;
                BasicStroke defaultStroke = (BasicStroke) g2d.getStroke();
                BasicStroke dashLine = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
                g2d.setStroke(dashLine);
                g.drawLine(line[0], line[1], line[2], line[3]);
                g2d.setStroke(defaultStroke);
                drawCorrectShape(g2d, line[0], line[1], line[2], line[3], relationship.getValue());
            }
            else
            {
                g.setColor(Color.BLACK);
                g.drawLine(line[0], line[1], line[2], line[3]);
                drawCorrectShape(g, line[0], line[1], line[2], line[3], relationship.getValue());
            }
        }
    
    }

    /**
     * Draws the correct shape based on the relationship.
     */
    public void drawCorrectShape(Graphics g, int x, int y, int r, int s, String relationship)
    {
        //Open diamond.
        if(relationship.equals("AGGREGATION"))
        {
            g.setColor(Color.BLACK);
            drawDiamond(g, x, y, r, s, false);
        }
        //Filled diamond.
        else if(relationship.equals("COMPOSITION"))
        {
            g.setColor(Color.BLACK);
            drawDiamond(g, x, y, r, s, true);
        }
        //Open arrow
        else if(relationship.equals("GENERALIZATION"))
        {
            g.setColor(Color.BLACK);
            drawTriangle(g, x, y, r, s);
        }
        //Filled arrow
        else if(relationship.equals("REALIZATION"))
        {
            g.setColor(Color.BLACK);
            drawTriangle(g, x, y, r, s);
        }
    }

    /**
     * Draws a triangle attached to the class panel.
     */
    private void drawTriangle(Graphics g2d, int x, int y, int r, int s)
    {
        //Above
        if(y > s)
        {
            g2d.drawPolygon(new int[] {x, x - 15, x + 15}, new int[] {y, y - 15, y - 15}, 3);
        }
        //Right
        else if(x > r)
        {
            g2d.drawPolygon(new int[] {x, x - 15, x - 15}, new int[] {y, y + 15, y - 15}, 3);
        }
        //Below
        else if(s > y)
        {
            g2d.drawPolygon(new int[] {x, x - 15, x + 15}, new int[] {y, y + 15, y + 15}, 3);
        }
        //Left
        else
        {
            g2d.drawPolygon(new int[] {x, x + 15, x + 15}, new int[] {y, y + 15, y - 15}, 3);
        }
    }
    /**
     * Draws a diamond attached to the class panel.
     */
    private void drawDiamond(Graphics g2d, int x, int y, int r, int s, boolean fill)
    {
        if(!fill)
        {
            //Above
            if(s > y)
            {
                g2d.drawPolygon(new int[] {x - 15, x, x + 15, x}, new int[] {y + 15, y, y + 15, y + 30}, 4);
            }
            //Right
            else if(x > r)
            {
                g2d.drawPolygon(new int[] {x - 30, x - 15, x, x - 15}, new int[] {y + 15, y + 30, y + 15, y}, 4);
            }
            //Below
            else if(y > s)
            {
                g2d.drawPolygon(new int[] {x - 15, x, x + 15, x}, new int[] {y - 15, y - 30, y - 15, y}, 4);
            }
            //Left
            else
            {
                g2d.drawPolygon(new int[] {x, x + 15, x + 30, x + 15}, new int[] {y + 15, y + 30, y + 15, y}, 4);
            }
        }   
        else
        {
            //Above
            if(s > y)
            {
                g2d.fillPolygon(new int[] {x - 15, x, x + 15, x}, new int[] {y + 15, y, y + 15, y + 30}, 4);
            }
            //Right
            else if(x > r)
            {
                g2d.fillPolygon(new int[] {x - 30, x - 15, x, x - 15}, new int[] {y + 15, y + 30, y + 15, y}, 4);
            }
            //Below
            else if(y > s)
            {
                g2d.fillPolygon(new int[] {x - 15, x, x + 15, x}, new int[] {y - 15, y - 30, y - 15, y}, 4);
            }
            //Left
            else
            {
                g2d.fillPolygon(new int[] {x, x + 15, x + 30, x + 15}, new int[] {y + 15, y + 30, y + 15, y}, 4);
            }
        }

    }

    /**
     * Get's shortest line points between two class panels.
     */
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
            //Down and to the right.
            else if(fromUp > toDown)
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
            else
            {
                //Directly to the right.
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
            //Down and to the left.
            else if(fromUp > toDown)
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
            else
            {
                //Directly to the left.
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
            //Up and to the right.
            else if(fromRight < toLeft)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            else
            {
                //Directly above.
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
            //Down and to the left.
            else if(fromRight < toLeft)
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
            else
            {
                //Directly below.
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
