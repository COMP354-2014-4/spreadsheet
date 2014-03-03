package spreadsheet;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author Justin Dupuis
 * @version 2014-02-09
 * 
 * Used for formatting the cells
 */
public class SSCellRenderer extends JLabel implements TableCellRenderer  {

	private EmptyBorder bdrNotSelected; // The border when not selected
    private LineBorder  bdrSelected;	// The border when selected
    private Dimension   dimCellSize;	// The default dimensions of the cells
    private int cellSelected_Row = 0;
    private Integer cellSelected_Col = 0;
    private String cellContents;
    private Grid dummyGrid;

    /**
     * Constructor that accepts the height and width of the cells for rendering
     * 
     * @param intHeight Cell height
     * @param intWidth Cell width
     */
    public SSCellRenderer(int intHeight, int intWidth) {
		super();
		bdrNotSelected  = new EmptyBorder(1, 2, 1, 2);
		bdrSelected = new LineBorder(Color.blue);
		
		dimCellSize = new Dimension();
		dimCellSize.height = intHeight;
		dimCellSize.width  = intWidth;
		
		this.setSize(dimCellSize);
		this.setOpaque(true);
		this.setHorizontalAlignment(SwingConstants.CENTER);
    };

    /**
     * returns the table cell renderer
     */
    public Component getTableCellRendererComponent (JTable table, Object cell, boolean isSelected,
	    boolean hasFocus, int row, int column) {
    	
    	//Set formatting for all cells
  	  	this.setToolTipText("Double-click to edit.");
  	  	this.setFont(new Font("Times", Font.PLAIN, 11));
		this.setForeground(new Color(0,0,0)); //text color
		
		//set the text to display
		if(cell != null /*&& (double) cell != 0.0)   ignore empty value cells              */){
	  		this.setText(""+cell);
	  		cellContents = ""+cell;
		}else{
			this.setText("");
			cellContents = "";
		}
    	
  		//Highlights a cell when selected
		if (isSelected) {
		  setBorder(bdrSelected);
		  this.setBackground(new Color(240,240,255));
		  cellSelected_Col = column + 1;
		  cellSelected_Row = row + 1;
		  
		} else {
		  setBorder(bdrNotSelected);
		  this.setBackground(new Color(255,255,255));
		}
	
		return this;
    }
    
    public  String getSelected_Col()
    {
    	return dummyGrid.numToCol(cellSelected_Col);
    }
    
    public int getSelected_Row()
    {
    	return cellSelected_Row;
    }
    
    public String getCellContents()
    {
    	return cellContents;
    }
}