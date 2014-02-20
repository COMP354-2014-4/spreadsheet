package spreadsheet;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author Justin Dupuis
 * @version 2014-02-09
 *
 * Represents the GUI table used to render the grid
 * 
 * Included all inhereted constructors just in case...
 */
public class SSTable extends JTable {
	final int intCellWidth = 60;
	final int intCellHeight = 21;

	JViewport vptRowHeaderViewPort;
	JViewport vptColumnHeaderViewPort;
	int intCellPadding = 3; //The default padding used on cells and headers
	
	JTableHeader header;
	
	static final int intDefaultRows = 5000;
	static final int intDefaultColumns = 26*27;//ends at ZZ (27 because the firsts 26 values are single digit)
	
	Grid grid;
	
	int intNumRows;
	int intNumColumns;
	
	Border bdrHeaderBorder  = (Border)UIManager.getDefaults().get("TableHeader.cellBorder");
	
	/**
	 * Create a blank spreadsheet with the default number of rows and columns
	 */
	public SSTable() {
		this(SSTable.intDefaultRows,SSTable.intDefaultColumns);
		//TODO: Generate Grid object for default table
		grid = new Grid(intDefaultColumns, intDefaultRows);
	}
	
	/**
	 * Create a spreadsheet based on a grid object
	 * 
	 * @param g The grid object to be used when generating the spreadsheet
	 */
	public SSTable(Grid g){
		//TODO: This generates the table using default values but then should be filled in with the specified grid
		//---> This is because the grid is a hash and only stores what was entered. The defaults determine what COULD be entered
		this(SSTable.intDefaultRows,SSTable.intDefaultColumns);
		this.grid = g;
		
		setupSSTable();
		createRowLabels();
		this.setFont(new Font("Times", Font.PLAIN, 10));
	}
	
	/**
	 * Create a spreadsheet by specifying the number of rows and columns
	 * 
	 * @param intRows The number of rows to create
	 * @param intColumns The number of columns to create
	 */
	public SSTable(int intRows, int intColumns){
		super(intRows,intColumns);
		
		this.intNumRows = intRows;
		this.intNumColumns = intColumns;
		
		setupSSTable();
		createRowLabels();
		this.setFont(new Font("Times", Font.PLAIN, 10));
	}
	
	/**
	 * Sets up the SS table for use as a spreadsheet
	 */
	private void setupSSTable(){
		
		//Set up header
		this.header = this.getTableHeader();
		header.setDefaultRenderer(new SSTableRenderer(this));
		
		//Set up JTable properties
		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.setRowHeight(this.intCellHeight);
		this.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

		//Set up cell renderer
		try {
		    setDefaultRenderer(Class.forName("java.lang.Object" ), new SSCellRenderer(this.intCellHeight,this.intCellWidth) );
		} catch (ClassNotFoundException e) {
		    System.out.println("ERROR: Renderer could not be set");
		}
	}
	
	/**
	 * JTables have column headers, but not row headers, so this method
	 * creates them based on cell properties
	 */
	private void createRowLabels(){
		// Create row header JPanel
		JPanel pnlRowHeaders = new JPanel();
		pnlRowHeaders.setBackground(new Color(255,255,255));
		
		FontMetrics metrics = getFontMetrics(this.getFont());

		//set the row label panel size based on the number of row labels required and their dimensions
		Dimension dimPanelSize = new Dimension(
							metrics.stringWidth("999")+intCellPadding*2,
							this.intCellHeight*intNumRows
				);
		pnlRowHeaders.setPreferredSize(dimPanelSize);

		// Add labels
		dimPanelSize.height = this.intCellHeight;
		
		for (int i = 1; i <= this.intNumRows; i++) {
		  JLabel lblRow = new JLabel(Integer.toString(i), SwingConstants.CENTER);
		  lblRow.setFont(this.getFont());
		  lblRow.setBorder(BorderFactory.createLineBorder(Color.white));
		  lblRow.setBounds(0, i*dimPanelSize.height, dimPanelSize.width, dimPanelSize.height);
		  lblRow.setBackground(new Color(255,255,255));
		  
		  pnlRowHeaders.add(lblRow);
		}

		dimPanelSize.height = this.intCellHeight*this.intNumRows;
		
		this.vptRowHeaderViewPort = new JViewport();
		this.vptRowHeaderViewPort.setViewSize(dimPanelSize);
		this.vptRowHeaderViewPort.setView(pnlRowHeaders);
		
	}
	
	/**
	 * Returns the associated viewport for display
	 * 
	 * @return The viewport
	 */
	public JViewport getRowHeader(){
		return vptRowHeaderViewPort;
	}
}
