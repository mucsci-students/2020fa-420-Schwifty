package UML.views;
/*
    Author: Chris, Dominic, Tyler, Cory and Drew
    Date: 10/25/2020
    Purpose: Handles drawing lines and shapes for relationships.
 */

import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class DrawPanel extends JPanel
{
    private View view;

    public DrawPanel(View v)
    {
        this.view = v;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(Map.Entry<ArrayList<String>, String> relationship : view.getRelationships().entrySet())
        {
            //Get the locations of the two related panels.
            Dimension fromLoc = view.getLoc(relationship.getKey().get(0));
            Dimension toLoc = view.getLoc(relationship.getKey().get(1));
            
            //Get the panels from the view to get the sizes.
            Map<String, JPanel> panels = view.getPanels();
            Dimension fromSize = panels.get(relationship.getKey().get(0)).getSize();
            Dimension toSize = panels.get(relationship.getKey().get(1)).getSize();

            //Find the points that make up a line that connects the closesst points on the two panels in question.
            int[] line = getClosest((int)fromLoc.getWidth(), (int)(fromLoc.getWidth() + fromSize.getWidth()), 
                                                (int)fromLoc.getHeight(), (int)(fromLoc.getHeight() + fromSize.getHeight()), 
                                                (int)toLoc.getWidth(), (int)(toLoc.getWidth() + toSize.getWidth()),
                                                (int)toLoc.getHeight(), (int)(toLoc.getHeight() + toSize.getHeight()));                

            if(relationship.getValue().equals("REALIZATION"))
            {
                //Do a dotted line for realizatioon.
                g.setColor(Color.BLACK);

                //Must use Graphics2D to make line dashed.
                Graphics2D g2d = (Graphics2D)g;
                
                //Antialiasing ON
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

                //To make line dashed, need to set stroke.
                BasicStroke defaultStroke = (BasicStroke) g2d.getStroke();
                BasicStroke dashLine = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
                g2d.setStroke(dashLine);
                g.drawLine(line[0], line[1], line[2], line[3]);
                //Set back to basic stroke
                g2d.setStroke(defaultStroke);
                drawCorrectShape(g2d, line[0], line[1], line[2], line[3], relationship.getValue());
            }
            else
            {
                //Do a basic line for all other types of relationships.
                g.setColor(Color.BLACK);
                g.drawLine(line[0], line[1], line[2], line[3]);
                drawCorrectShape(g, line[0], line[1], line[2], line[3], relationship.getValue());
            }
        }
    
    }

    /**
     * Draws the correct shape based on the relationship.
     */
    private void drawCorrectShape(Graphics g, int x, int y, int r, int s, String relationship)
    {
        //Open diamond.
        if(relationship.equals("AGGREGATION"))
        {
            drawDiamond(g, x, y, r, s, false);
        }
        //Filled diamond.
        else if(relationship.equals("COMPOSITION"))
        {
            drawDiamond(g, x, y, r, s, true);
        }
        //Open arrow.
        else if(relationship.equals("GENERALIZATION"))
        {
            drawTriangle(g, x, y, r, s);
        }
        //Filled arrow.
        else if(relationship.equals("REALIZATION"))
        {
            drawTriangle(g, x, y, r, s);
        }
    }

    /**
     * Draws a triangle attached to the class panel.
     */
    private void drawTriangle(Graphics g2d, int x, int y, int r, int s)
    {
        //Find theta using the arctan.
        double theta = Math.atan2(s - y, r - x);

        //Theta may be negative, so we adjust accordingly.
        if(theta < 0)
            theta+= 2 * Math.PI;
        int[] xValues = {0, 15, 15};
        int[] yValues = {0, 15, -15};

        //This matrix holds the x values in the top row and the y values in the bottom.
        int[][] pointMatrix = {xValues, yValues};

        //The matrix in which to store the result.
        int[][] resultMatrix = new int[pointMatrix.length][pointMatrix[0].length];
        int size = 3;

        //Just multiply to get the same affect as doing a matric multiplication with the rotation matrix.
        for(int count = 0; count < size; count++)
        {
            resultMatrix[0][count] = (int)Math.round(Math.cos(theta) * pointMatrix[0][count]) - (int)Math.round(Math.sin(theta) * pointMatrix[1][count]);
            resultMatrix[1][count] = (int)Math.round(Math.sin(theta) * pointMatrix[0][count]) + (int)Math.round(Math.cos(theta) * pointMatrix[1][count]);
        }
        
        //Add x and y appropriately to get the absolute positions for the shapes.
        for(int count = 0; count < size; count++)
        {
            resultMatrix[0][count] += x;
            resultMatrix[1][count] += y;
        }

        GraphicalView v = (GraphicalView)view;
        Color c = v.getDrawPanel().getBackground();
        g2d.setColor(c);
        g2d.fillPolygon(resultMatrix[0], resultMatrix[1], 3);
        //Draw the border.
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(resultMatrix[0], resultMatrix[1], 3);
    }

    /**
     * Draws a diamond attached to the class panel.
     */
    private void drawDiamond(Graphics g2d, int x, int y, int r, int s, boolean fill)
    {
        //Find theta using the arctan.
        double theta = Math.atan2(s - y, r - x);

        //Theta may be negative, so we adjust accordingly.
        if(theta < 0)
            theta+= 2 * Math.PI;
        int[] xValues = {0, 15, 30, 15};
        int[] yValues = {0, 15, 0, -15};

        //This matrix holds the x values in the top row and the y values in the bottom.
        int[][] pointMatrix = {xValues, yValues};

        //The matrix in which to store the result.
        int[][] resultMatrix = new int[pointMatrix.length][pointMatrix[0].length];
        int size = 4;

        //Just multiply to get the same affect as doing a matric multiplication with the rotation matrix.
        for(int count = 0; count < size; count++)
        {
            resultMatrix[0][count] = (int)Math.round(Math.cos(theta) * pointMatrix[0][count]) - (int)Math.round(Math.sin(theta) * pointMatrix[1][count]);
            resultMatrix[1][count] = (int)Math.round(Math.sin(theta) * pointMatrix[0][count]) + (int)Math.round(Math.cos(theta) * pointMatrix[1][count]);
        }

        //Add x and y appropriately to get the absolute positions for the shapes.
        for(int count = 0; count < size; count++)
        {
            resultMatrix[0][count] += x;
            resultMatrix[1][count] += y;
        }

        //Fill the shape for composition, and leave it open for aggregation.
        if(fill)
        {
            g2d.setColor(Color.BLACK);
            g2d.fillPolygon(resultMatrix[0], resultMatrix[1], 4);
        }
        else
        {
            GraphicalView v = (GraphicalView) view;
            Color c = v.getDrawPanel().getBackground();
            g2d.setColor(c);
            g2d.fillPolygon(resultMatrix[0], resultMatrix[1], 4);
            //Draw border.
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(resultMatrix[0], resultMatrix[1], 4);
        }
    }

    /**
     * Get's shortest line points between two class panels.
     */
    public int[] getClosest(int fromLeft, int fromRight, int fromUp, int fromDown, int toLeft, int toRight, int toUp, int toDown)
    {
        //fromX, fromY, toX, toY.
        int[] x = new int[4];
        //Smaller from on top or bottom.
        if(fromLeft > toLeft && fromRight < toRight)
        {
            x[0] = ((fromRight - fromLeft) / 2) + fromLeft;
            x[2] = x[0];
            if(fromDown < toUp)
            {
                x[1] = fromDown;
                x[3] = toUp;
            }
            else
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
        }
        //Larger from on top or bottom.
        else if(toLeft > fromLeft && toRight < fromRight)
        {
            x[0] = ((toRight - toLeft) / 2) + toLeft;
            x[2] = x[0];
            if(fromDown < toUp)
            {
                x[1] = fromDown;
                x[3] = toUp;
            }
            else
            {
                x[1] = fromUp;
                x[3] = toDown;
            }
        }
        //Smaller from on the side.
        else if(fromUp > toUp && fromDown < toDown)
        {
            x[1] = ((fromDown - fromUp) / 2) + fromUp;
            x[3] = x[1];
            if(fromLeft > toRight)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            else
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
        }
        //Larger from on the side.
        else if(toUp > fromUp && toDown < fromDown)
        {
            x[1] = ((toDown - toUp) / 2) + toUp;
            x[3] = x[1];
            if(fromLeft > toRight)
            {
                x[0] = fromLeft;
                x[2] = toRight;
            }
            else
            {
                x[0] = fromRight;
                x[2] = toLeft;
            }
        }
        //From is to the right.
        else if(fromLeft > toRight)
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
