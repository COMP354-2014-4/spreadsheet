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

	SSCellRenderer cellSelected = null;
	SSCellEditor cellEditor = null;

	JViewport vptRowHeaderViewPort;
	JViewport vptColumnHeaderViewPort;
	int intCellPadding = 3; //The default padding used on cells and headers

	JTableHeader header;

	// The default rows and columns have been reduced in order to increase program efficiency
	static final int intDefaultRows = 50;
	static final int intDefaultColumns = 26;

	public Grid grid;	//PRIVATE, is now public for test only

	int intNumRows;
	int intNumColumns;

	//Border for the row and column headers
	Border bdrHeaderBorder  = BorderFactory.createLineBorder(Color.black);

	/**
	 * Create a blank spreadsheet with the default number of rows and columns
	 */
	public SSTable() {
		this(SSTable.intDefaultRows,SSTable.intDefaultColumns);
	}

	/**
	 * Create a spreadsheet based on a grid object
	 * 
	 * @param g The grid object to be used when generating the spreadsheet
	 */
	public SSTable(Grid g){
		this(SSTable.intDefaultRows,SSTable.intDefaultColumns);
		this.grid = g;
		this.header.setBorder(this.bdrHeaderBorder);
		g.setSSTable(this);

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
		header.setReorderingAllowed(false);

		//Set up JTable properties
		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowHeight(this.intCellHeight);
		this.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

		//Set up cell renderer
		try {
			cellSelected = new SSCellRenderer(this.intCellHeight,this.intCellWidth);
			setDefaultRenderer(Class.forName("java.lang.Object" ), cellSelected );
			setDefaultEditor(Class.forName("java.lang.Object" ), cellEditor );
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: Renderer or Editor could not be set");
		}

	}

	/**
	 * JTables have column headers, but not row headers, so this method
	 * creates them based on cell properties
	 */
	private void createRowLabels(){
		// Create row header JPanel
		JPanel pnlRowHeaders = new JPanel();
		pnlRowHeaders.setBackground(new Color(200,200,255));
		pnlRowHeaders.setBorder(this.bdrHeaderBorder);

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
			lblRow.setBorder(BorderFactory.createLineBorder(new Color(200,200,255)));
			lblRow.setFont(this.getFont());
			lblRow.setBounds(0, i*dimPanelSize.height, dimPanelSize.width, dimPanelSize.height);
			//lblRow.setBackground(new Color(255,255,255));

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
