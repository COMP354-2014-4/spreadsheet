package spreadsheet;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Set;

import javax.swing.JFileChooser;
import utils.Formula;


/**
 * @author Justin Dupuis
 * @version 2014-02-09
 * 
 * This class represents the GUI for the spreadsheet application.
 * A grid must be passed to create the object, but it can be blank
 *
 */
public class SSGUI implements ActionListener, ListSelectionListener, TableModelListener, KeyListener{
	//Constants
	public final String NOTANEXCEPTIONALINPUT = "0";
	
	//Tools
	private Toolkit toolKit;

	//main components
	private JFrame frmWindow;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	//Top Panel Components
	JPanel panNorthPanel;
	JTextField txtInputBox;
	JLabel lblInput;
	JButton btnUpdate;

	//Control components
	private JMenuBar mnbMenu;
	private JToolBar tbrToolBar;

	//File Menu
	public JMenu mnuFile;		//PRIVATE, is now public for test only
	public JMenuItem mniNew;	//PRIVATE, is now public for test only
	public JMenuItem mniLoad;	//PRIVATE, is now public for test only
	public JMenuItem mniSave;	//PRIVATE, is now public for test only
	public JMenuItem mniSaveAs;	//PRIVATE, is now public for test only

	//Edit Menu
	public JMenu mnuEdit;		//PRIVATE, is now public for test only
	public JMenuItem mniCut;	//PRIVATE, is now public for test only
	public JMenuItem mniCopy;	//PRIVATE, is now public for test only
	public JMenuItem mniPaste;	//PRIVATE, is now public for test only

	//Help Menu
	private JMenu mnuHelp;
	private JMenuItem mniAbout;

	//ToolBar buttons
	public JButton btnNew;		//PRIVATE, is now public for test only
	public JButton btnLoad;		//PRIVATE, is now public for test only
	public JButton btnSave;		//PRIVATE, is now public for test only
	public JButton btnSaveAs;	//PRIVATE, is now public for test only
	public JButton btnCopy;		//PRIVATE, is now public for test only
	public JButton btnCut;		//PRIVATE, is now public for test only
	public JButton btnPaste;	//PRIVATE, is now public for test only

	//Center panel components
	JScrollPane scrTblScrollPane;
	public SSTable tblGrid;		//PRIVATE, is now public for test only

	//bottom panel components
	public JTextField txtMessageBox;//PRIVATE, is now public for test only

	//window dimensions
	private int intScreenWidth;
	private int intScreenHeight;

	//Back-end data objects
	private Grid grid;
	public String clipBoard = "0"; //PRIVATE, is now public for test only

	//Save location
	public String strFileLocation = ""; //PRIVATE, is now public for test only

	/**
	 * Default constructor, which acccepts the grid to be displayed
	 * 
	 * @param gridObject The back-end data stored in the grid
	 */
	public SSGUI(Grid gridObject){

		//instantiates and connects backend data grid.
		grid = gridObject;
		grid.setGUI(this);

		//attempt to adopt the system look and feel
		setLookAndFeel();

		//instantiate tools
		toolKit = Toolkit.getDefaultToolkit();  

		//create window
		frmWindow = new JFrame("Spreadsheet App - Comp 354 - Team 3");

		//create primary window sections
		tbrToolBar = new JToolBar();
		pnlCenter = new JPanel();
		pnlSouth = new JPanel();

		//Top Panel Components
		lblInput = new JLabel(" Input:  ");
		txtInputBox = new JTextField();
		btnUpdate = new JButton("Update");
		panNorthPanel = new JPanel();

		//Menu Bar
		mnbMenu = new JMenuBar();	//Menu Items

		//File Menu
		mnuFile = new JMenu("File");
		mniNew = new JMenuItem("New");
		mniLoad = new JMenuItem("Load");
		mniSave = new JMenuItem("Save");
		mniSaveAs = new JMenuItem("Save As");

		//Edit Menu
		mnuEdit = new JMenu("Edit");
		mniCut = new JMenuItem("Cut");
		mniCopy = new JMenuItem("Copy");
		mniPaste = new JMenuItem("Paste");

		//Help Menu
		mnuHelp = new JMenu("Help");
		mniAbout = new JMenuItem("About");

		// Build toolbar buttons
		btnNew = new JButton("New");
		btnLoad = new JButton("Load");
		btnSave = new JButton("Save");
		btnSaveAs = new JButton("Save As");
		btnCopy = new JButton("Copy");
		btnCut = new JButton("Cut");
		btnPaste = new JButton("Paste");

		// Add all listeners
		mniNew.addActionListener(this);
		mniLoad.addActionListener(this);
		mniSave.addActionListener(this);
		mniSaveAs.addActionListener(this);
		mniCut.addActionListener(this);
		mniCopy.addActionListener(this);
		mniPaste.addActionListener(this);
		btnNew.addActionListener(this);
		btnLoad.addActionListener(this);
		btnSave.addActionListener(this);
		btnSaveAs.addActionListener(this);
		btnCopy.addActionListener(this);
		btnCut.addActionListener(this);
		btnPaste.addActionListener(this);
		btnUpdate.addActionListener(this);

		//Allows keyboard input to be captured (ex: enter key)
		txtInputBox.addKeyListener(this);

		//Build Center Panel
		panNorthPanel.setLayout(new BorderLayout());
		panNorthPanel.add(txtInputBox,BorderLayout.CENTER);
		panNorthPanel.add(tbrToolBar,BorderLayout.NORTH);
		panNorthPanel.add(lblInput,BorderLayout.WEST);
		panNorthPanel.add(btnUpdate,BorderLayout.EAST);

		//Build center panel	
		tblGrid = new SSTable(grid);//uses the default values on load
		tblGrid.setFillsViewportHeight(true);
		tblGrid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblGrid.getModel().addTableModelListener(this);
		tblGrid.getSelectionModel().addListSelectionListener(this);
		tblGrid.getColumnModel().getSelectionModel().addListSelectionListener(this);
		this.tblGrid.addKeyListener(this);

		//Build Scrollbars
		scrTblScrollPane = new JScrollPane(tblGrid,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrTblScrollPane.setRowHeader(this.tblGrid.getRowHeader());
		scrTblScrollPane.setBackground(new Color(255,255,255));

		//Build bottom panel components
		txtMessageBox = new JTextField();

		//set window properties
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.setLayout(new BorderLayout());

		//set the spreadsheet's screen dimensions
		intScreenWidth = ((int) toolKit.getScreenSize().getWidth());  
		intScreenHeight = ((int) toolKit.getScreenSize().getHeight());   
		
		frmWindow.setPreferredSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());

		//setup menu
		mnbMenu.setPreferredSize(new Dimension(this.intScreenWidth,30));

		//build file menu
		mnuFile.add(mniNew);
		mnuFile.add(mniLoad);
		mnuFile.add(mniSave);
		mnuFile.add(mniSaveAs);

		//build edit menu
		mnuEdit.add(mniCut);
		mnuEdit.add(mniCopy);
		mnuEdit.add(mniPaste);

		//build help menu
		mnuHelp.add(mniAbout);
		mniAbout.addActionListener(this);

		//add menus to menu bar
		mnbMenu.add(mnuFile);
		mnbMenu.add(mnuEdit);
		mnbMenu.add(mnuHelp);

		//Setup center panel
		pnlCenter.setBackground(new Color(255,255,255));
		pnlCenter.add(scrTblScrollPane);

		//Setup bottom panel
		pnlSouth.setPreferredSize(new Dimension(this.intScreenWidth,30));
		txtMessageBox.setEditable(false);
		pnlSouth.setBorder(new BevelBorder(BevelBorder.LOWERED));

		//add buttons to toolbar
		tbrToolBar.add(btnNew);
		tbrToolBar.add(btnLoad);
		tbrToolBar.add(btnSave);
		tbrToolBar.add(btnSaveAs);
		tbrToolBar.addSeparator();
		tbrToolBar.add(btnCopy);
		tbrToolBar.add(btnCut);
		tbrToolBar.add(btnPaste);

		//Add primary window sections to window
		frmWindow.setJMenuBar(this.mnbMenu);
		frmWindow.add(panNorthPanel, BorderLayout.NORTH);
		frmWindow.add(scrTblScrollPane,BorderLayout.CENTER);
		frmWindow.add(txtMessageBox,BorderLayout.SOUTH);

		//display
		frmWindow.pack();
		frmWindow.setVisible(true);		
	}

	/**
	 * Set up the GUI table
	 */
	public void initializeTable() {
		tblGrid = new SSTable(grid);//uses the default values on load
		tblGrid.setFillsViewportHeight(true);
		tblGrid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblGrid.getModel().addTableModelListener(this);
		tblGrid.getSelectionModel().addListSelectionListener(this);
		tblGrid.getColumnModel().getSelectionModel().addListSelectionListener(this);
		tblGrid.addKeyListener(this);
		scrTblScrollPane.setViewportView(tblGrid);
	}

	/**
	 * Display a message in the message bar
	 *  
	 * @param strMessage The message to display
	 */
	public void displayMessage(String strMessage){
		this.txtMessageBox.setText(strMessage);
	}

	/**
	 * Create a new spreadsheet
	 */
	public void newSpreadsheet(){
		grid.clear();
		initializeTable();
		strFileLocation = ""; // clears the save file location
	}

	/**
	 * Load a spreadsheet from a file
	 */
	public void loadSpreadsheet(){

		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".sav file","sav");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(frmWindow);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			newSpreadsheet();// To erase previous spreadsheet data before loading new data
			strFileLocation = fc.getSelectedFile().getAbsolutePath();
			for(int i = 0; i != 10; ++i) // Looping ensures nested references get processed correctly
				grid.load(strFileLocation);
		}
	}

	/**
	 * Save the spreadsheet to a file already selected, or prompt for location if file not yet saved
	 */
	public void saveSpreadsheet(){

		if(strFileLocation.equals(""))
			saveAsSpreadsheet();
		else
			grid.save(strFileLocation);
	}

	/**
	 * Select a file and save the spreadsheet it
	 */
	public void saveAsSpreadsheet(){

		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".sav file","sav");
		fc.setFileFilter(filter);
		int returnVal = fc.showSaveDialog(frmWindow); // Different from loadSpreadsheet()
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			strFileLocation = fc.getSelectedFile().getAbsolutePath();
			grid.save(strFileLocation + ".sav"); // Also different from loadSpreadsheet()
		}
	}

	/**
	 * cut selected cell, deleting the frontend and backend representations and updating the clipBoard object
	 */
	public void cut(){
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			return;
		}
		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;
		if(tblGrid.getValueAt(row-1, col-1) == null){
			return;
		}
		if(tblGrid.getValueAt(row-1, col-1).equals("")){
			return;
		}
		clipBoard = grid.getCell(colConvert, row).getValue();
		grid.removeCell(colConvert, row);
		tblGrid.setValueAt("", tblGrid.getSelectedRow(), tblGrid.getSelectedColumn());
		
	}

	/**
	 * copy selected cell, updating the clipBoard object.
	 */	
	public void copy(){
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			return;
		}
		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;
		if(tblGrid.getValueAt(row-1, col-1) == null){
			return;
		}
		if(tblGrid.getValueAt(row-1, col-1).equals("")){
			return;
		}
		clipBoard = grid.getCell(colConvert, row).getValue();
	}

	/**
	 * paste to selected cell, setting the cell to the clipBoard object's value and inputting the value.
	 */
	public void paste(){
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			return;
		}
		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;
		if(!((""+tblGrid.getValueAt(row-1, col-1)).equals(""+(grid.getCell(colConvert, row).getEvaluatedValue())))){
			if(clipBoard.equals("0")){
			}	
			else{
			grid.getCell(colConvert, row).setValue(clipBoard);
			}
		}
		if(clipBoard.equals("0")){
		}
		else{
		grid.getCell(colConvert, row).setValue(clipBoard);
		}
	}
	
	//Is called when delete key is pressed on a selected cell.
	//The cell is then deleted.
	public void deleteCell()
	{
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			return;
		}
		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;
		
		grid.removeCell(colConvert, row);
		tblGrid.setValueAt("", tblGrid.getSelectedRow(), tblGrid.getSelectedColumn());
	}

	/**
	 * Capture GUI interactions. need to add the actionlistener to anything that triggers events
	 * 
	 * @param e The triggering event
	 */
	public void actionPerformed(ActionEvent e){
		final Object objSourceClass = e.getSource(); //store the class of the source

		if(objSourceClass.equals(this.btnNew) || objSourceClass.equals(this.mniNew)){
			newSpreadsheet();

		}else if(objSourceClass.equals(this.btnLoad) || objSourceClass.equals(this.mniLoad)){
			loadSpreadsheet();

		}else if(objSourceClass.equals(this.btnSave) || objSourceClass.equals(this.mniSave)){
			saveSpreadsheet();

		}else if(objSourceClass.equals(this.btnSaveAs) || objSourceClass.equals(this.mniSaveAs)){
			saveAsSpreadsheet();

		}else if(objSourceClass.equals(this.mniAbout)){
			JOptionPane.showMessageDialog(new JFrame(),
				"This is Team 3's Spreadsheet.  We have included a number of features for manipulating spreadsheet data.\n"
				+ "These include: New, Load, Save, Save As, Cut, Copy, Paste, Update, and Delete.\n"
				+ "Please enjoy the functionality!", "Team 3, 2014, proprietary copyright all rights included.",
				JOptionPane.PLAIN_MESSAGE);

		}else if(objSourceClass.equals(this.btnCopy) || objSourceClass.equals(this.mniCopy)){
			copy();

		}else if(objSourceClass.equals(this.btnPaste) || objSourceClass.equals(this.mniPaste)){
			paste();

		}else if(objSourceClass.equals(this.btnCut) || objSourceClass.equals(this.mniCut)){
			cut();

		}else if(objSourceClass.equals(this.btnUpdate)){
			updateFromInput();
		}
	}

	/**
	 * Update the currently selected cell to whatever text is typed into the input box
	 */
	private void updateFromInput(){
		tblGrid.setValueAt(this.txtInputBox.getText(), tblGrid.getSelectedRow(), tblGrid.getSelectedColumn());
	}
	
	/**
	 * KeyListener key press event handler
	 */
	public void keyPressed(KeyEvent e){

		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;

		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			updateFromInput();
		}
		
		if(e.getKeyChar() == KeyEvent.VK_DELETE){
			if(grid.getCell(colConvert, row).getEvaluatedValue() != 0.0) //Only delete if cell is non-empty
			{
				deleteCell();
			}
		}
	}
	/**
	 * KeyListener key release event handler, not used.
	 */
	public void keyReleased(KeyEvent e){
		//Nothing required
	}
	
	/**
	 * KeyListener key typed event handler, not used.
	 */
	public void keyTyped(KeyEvent e){
		//Nothing required
	}


	/**
	 * Captures de-selection action from the JTable cells, 
	 * handles this by communicating the cell's current value to back-end for verification
	 * 
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			return;
		}

		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;

		Cell x = grid.checkCell(colConvert, row);
		if(x == null){
			txtInputBox.setText("");
		}
		else{
			txtInputBox.setText("" + grid.checkCell(colConvert, row).getValue());
		}
		if(tblGrid.getValueAt(row-1, col-1) == null)
		{
			return;
		}
		if(tblGrid.getValueAt(row-1, col-1).equals(""))
		{
			return;
		}
	}

	/**
	 * Handles changes in cell selection, etc...
	 */
	public void tableChanged(TableModelEvent e) {
		tblGrid.getModel().removeTableModelListener(this);
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			tblGrid.getModel().addTableModelListener(this);
			return;
		}

		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;		
		if(tblGrid.getValueAt(row-1, col-1) == null)
		{
			tblGrid.getModel().addTableModelListener(this);
			return;
		}
		if(tblGrid.getValueAt(row-1, col-1).equals(""))
		{
			//If the user removes the content of a cell, the backend grid is checked
			//and if that cell had a value, it is deleted.
			if(grid.getCell(colConvert, row).getEvaluatedValue() != 0.0)
			{
				deleteCell();
			}
			tblGrid.getModel().addTableModelListener(this);
			return;
		}
		boolean isANumber = Cell.isNumeric(""+tblGrid.getValueAt(row-1, col-1));
		boolean isAcceptedChar = Cell.isValidChar(""+tblGrid.getValueAt(row-1, col-1));
		

		if(!((""+tblGrid.getValueAt(row-1, col-1)).equals(""+(grid.getCell(colConvert, row).getEvaluatedValue()))))
		{
			if(isANumber||isAcceptedChar){
				grid.getCell(colConvert, row).setValue(""+tblGrid.getValueAt(row-1, col-1));
			}
			else{
			txtMessageBox.setText("That is not valid input, please type either a formula or a number.");
			}
			if(isAcceptedChar){
				try{
				Formula.listReferencedCells(grid.getCell(colConvert, row));
				}
				catch(Exception a){
				txtMessageBox.setText(a.getMessage());
				}
			}
		}

		tblGrid.getModel().addTableModelListener(this);
	}

	/**
	 * Sets the look and feel to the current OS
	 */
	private void setLookAndFeel(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			//Doesn't matter if it doesn't work, the spreadsheet will simply have a different aesthetic
		}
	}

}