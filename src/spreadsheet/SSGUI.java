package spreadsheet;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFileChooser;

import spreadsheet.Cell.Formatting;
import utils.Formula;


/**
 * @author Justin Dupuis
 * @version 2014-02-09
 * 
 * This class represents the GUI for the spreadsheet application.
 * A grid must be passed to create the object, but it can be blank
 *
 */
public class SSGUI implements ActionListener, KeyListener{
	//Constants
	public final String NOTANEXCEPTIONALINPUT = "0";
	
	//event stuff
	ColEventListener colListener = new ColEventListener();
	RowEventListener rowListener = new RowEventListener();
	int lastCol = 0;
	int lastRow =0;
	boolean typingInTable = false;
	
	//Tools
	private Toolkit toolKit;

	//main components
	private JFrame frmWindow;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	//Top Panel Components
	JPanel panNorthPanel;
	public JTextField txtInputBox;	//PRIVATE, is now public for test only
	JLabel lblInput;
	public JButton btnUpdate;		//PRIVATE, is now public for test only

	//Control components
	private JMenuBar mnbMenu; // See Menus.java
	private JToolBar tbrToolBar;

	//File Menu -- See MenuFile.java
	public JMenu mnuFile;		//PRIVATE, is now public for test only
	public JMenuItem mniNew;	//PRIVATE, is now public for test only
	public JMenuItem mniLoad;	//PRIVATE, is now public for test only
	public JMenuItem mniSave;	//PRIVATE, is now public for test only
	public JMenuItem mniSaveAs;	//PRIVATE, is now public for test only

	//Edit Menu-- See MenuEdit.java
	public JMenu mnuEdit;		//PRIVATE, is now public for test only
	public JMenuItem mniCut;	//PRIVATE, is now public for test only
	public JMenuItem mniCopy;	//PRIVATE, is now public for test only
	public JMenuItem mniPaste;	//PRIVATE, is now public for test only
	public JMenuItem mniUndo; //PRIVATE, is now public for test only
	public JMenuItem mniRedo; //PRIVATE, is now public for test only

  //Format Menu -- See MenuFormat.java
  private JMenu mnuFormat;
  private JMenuItem mniReal;
  private JMenuItem mniMonetary;
  private JMenuItem mniScientific;
  private JMenuItem mniInteger;
  //private JMenuItem mniText;
	
	//Help Menu -- See MenuHelp.java
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
	public JButton btnUndo; //PRIVATE, is now public for test only
	public JButton btnRedo; //PRIVATE, is now public for test only

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
	private int clip_x =-1;
	private int clip_y =-1;
	private int oldSelectedRow = 1;
	private int oldSelectedCol = 1;
	private boolean changed = false;
	private UndoRedo undoRedoStacks = new UndoRedo();

	//Save location
	public String strFileLocation = ""; //PRIVATE, is now public for test only

	/**
	 * Default constructor, which accepts the grid to be displayed
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

		//Menu Bar -- See Menus.java
		mnbMenu = new JMenuBar();	//Menu Items

		//File Menu -- See MenuFile.java
		mnuFile = new JMenu("File");
		mniNew = new JMenuItem("New");
		mniLoad = new JMenuItem("Load");
		mniSave = new JMenuItem("Save");
		mniSaveAs = new JMenuItem("Save As");

		//Edit Menu -- See MenuEdit.java
		mnuEdit = new JMenu("Edit");
		mniCut = new JMenuItem("Cut");
		mniCopy = new JMenuItem("Copy");
		mniPaste = new JMenuItem("Paste");
		mniUndo = new JMenuItem("Undo");
		mniRedo = new JMenuItem("Redo");
		
    //Format Menu -- See MenuFormat.java
    mnuFormat = new JMenu("Format");
    mniReal = new JMenuItem("Real");
    mniMonetary = new JMenuItem("Monetary");
    mniScientific = new JMenuItem("Scientific");
    mniInteger = new JMenuItem("Integer");
    //mniText = new JMenuItem("Text");

		//Help Menu -- See MenuHelp.java
		mnuHelp = new JMenu("Help");
		mniAbout = new JMenuItem("About");
		
		//Set menu Mnemonics
		mnuFile.setMnemonic('F');
		mnuEdit.setMnemonic('E');
		mnuFormat.setMnemonic('M');
		mnuHelp.setMnemonic('H');
    

		// Build toolbar buttons
		btnNew = new JButton("New");
		btnLoad = new JButton("Load");
		btnSave = new JButton("Save");
		btnSaveAs = new JButton("Save As");
		btnCopy = new JButton("Copy");
		btnCut = new JButton("Cut");
		btnPaste = new JButton("Paste");
		btnUndo = new JButton("Undo");
		btnRedo = new JButton("Redo");

		//Add menu shortcuts
    //CTRL+N  New
    mniNew.setAccelerator(KeyStroke.getKeyStroke('N',KeyEvent.CTRL_DOWN_MASK));
    //CTRL+L  Load
    mniLoad.setAccelerator(KeyStroke.getKeyStroke('L',KeyEvent.CTRL_DOWN_MASK));
    //CTRL+S  Save
    mniSave.setAccelerator(KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK));
    //CTRL+SHIFT+S  Save As
    mniSaveAs.setAccelerator(KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
    //CTRL+C  Copy
    mniCopy.setAccelerator(KeyStroke.getKeyStroke('C',KeyEvent.CTRL_DOWN_MASK));		
    //CTRL+X  Cut
		mniCut.setAccelerator(KeyStroke.getKeyStroke('X',KeyEvent.CTRL_DOWN_MASK));
    //CTRL+V  Paste
		mniPaste.setAccelerator(KeyStroke.getKeyStroke('V',KeyEvent.CTRL_DOWN_MASK));
    //CTRL+Z  Undo
		mniUndo.setAccelerator(KeyStroke.getKeyStroke('Z',KeyEvent.CTRL_DOWN_MASK));
    //CTRL+Y  Redo
		mniRedo.setAccelerator(KeyStroke.getKeyStroke('Y',KeyEvent.CTRL_DOWN_MASK));
		
		// Add all listeners
		////File Menu
		mniNew.addActionListener(this);
		mniLoad.addActionListener(this);
		mniSave.addActionListener(this);
		mniSaveAs.addActionListener(this);
		////Edit Menu
		mniCut.addActionListener(this);
		mniCopy.addActionListener(this);
		mniPaste.addActionListener(this);
    mniUndo.addActionListener(this);
    mniRedo.addActionListener(this);
		////Format Menu
		mniReal.addActionListener(this);
		mniMonetary.addActionListener(this);
		mniScientific.addActionListener(this);
		mniInteger.addActionListener(this);
		//mniText.addActionListener(this);
		////ButtonBar
		btnNew.addActionListener(this);
		btnLoad.addActionListener(this);
		btnSave.addActionListener(this);
		btnSaveAs.addActionListener(this);
		btnCopy.addActionListener(this);
		btnCut.addActionListener(this);
		btnPaste.addActionListener(this);
		btnUndo.addActionListener(this);
		btnRedo.addActionListener(this);
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
		tblGrid.getSelectionModel().addListSelectionListener(rowListener);
		tblGrid.getColumnModel().getSelectionModel().addListSelectionListener(colListener);
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

		//build file menu -- See MenuFile.java
		mnuFile.add(mniNew);
		mnuFile.add(mniLoad);
		mnuFile.add(mniSave);
		mnuFile.add(mniSaveAs);

		//build edit menu -- See MenuEdit.java
		mnuEdit.add(mniCut);
		mnuEdit.add(mniCopy);
		mnuEdit.add(mniPaste);
		//-added undo/redo options (NM - 2014-03-19)
    mnuEdit.add(mniUndo);
    mnuEdit.add(mniRedo);

		
    //build format menu -- See MenuFormat.java
    mnuFormat.add(mniReal);
    mnuFormat.add(mniMonetary);
    mnuFormat.add(mniScientific);
    mnuFormat.add(mniInteger);
    //mnuFormat.add(mniText);

		//build help menu -- See MenuHelp.java
		mnuHelp.add(mniAbout);
		mniAbout.addActionListener(this);

		//add menus to menu bar  -- See Menus.java
		mnbMenu.add(mnuFile);
		mnbMenu.add(mnuEdit);
		mnbMenu.add(mnuFormat);
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
    tbrToolBar.addSeparator();
    tbrToolBar.add(btnUndo);
    tbrToolBar.add(btnRedo);

		//Add primary window sections to window
		frmWindow.setJMenuBar(this.mnbMenu);
		frmWindow.add(panNorthPanel, BorderLayout.NORTH);
		frmWindow.add(scrTblScrollPane,BorderLayout.CENTER);
		frmWindow.add(txtMessageBox,BorderLayout.SOUTH);

		//display
		frmWindow.pack();
		frmWindow.setVisible(true);
		
    //set application focus to cell A1
		tblGrid.requestFocus();
    tblGrid.changeSelection(0, 0, false, false);
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
	 * Set up the GUI table
	 */
	public void initializeTable() {
		tblGrid = new SSTable(grid);//uses the default values on load
		tblGrid.setFillsViewportHeight(true);
		tblGrid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblGrid.getSelectionModel().addListSelectionListener(rowListener);
		tblGrid.getColumnModel().getSelectionModel().addListSelectionListener(colListener);
		tblGrid.addKeyListener(this);
		scrTblScrollPane.setViewportView(tblGrid);
	}

	/**

	/**
	 * Create a new spreadsheet
	 */
	public void newSpreadsheet(){
		grid.clear();
		initializeTable();
		strFileLocation = ""; // clears the save file location
		undoRedoStacks.clearUndoRedo();  //clears the undo redo stacks
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
			undoRedoStacks.clearUndoRedo();  //clears the undo redo stacks
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
			grid.save(strFileLocation.matches(".*\\.sav")? strFileLocation: strFileLocation+ ".sav"); // Also different from loadSpreadsheet()
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
		clip_x = col;
		clip_y = row;
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
		clip_x = col;
		clip_y = row;
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
			grid.getCell(colConvert, row).setValue(Formula.applyOffset(col - clip_x,row - clip_y, clipBoard));
			}
		}
		if(clipBoard.equals("0")){
		}
		else{
		grid.getCell(colConvert, row).setValue(Formula.applyOffset(col - clip_x,row - clip_y, clipBoard));
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
   * set format for selected cell
   */
	public void setCellFormat(String format){
    //check if selected cell contains negative col or row
    if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
      return;
    }
    
    //get the column integer
    int col = tblGrid.getSelectedColumn()+1;
    //convert column integer to string
    String colConvert = Grid.numToCol(col);
    //get the row integer
    int row = tblGrid.getSelectedRow()+1;
  
    //if value of this cell is null return
    if(tblGrid.getValueAt(row-1, col-1) == null){
      return;
    }
    
    //set the format for the cell
    grid.getCell(colConvert, row).setCellFormat(Formatting.valueOf(format.toUpperCase()));
    
    //update GUI
    updateFromInput(row,col);
	}
	
	/**
	 * Method to handle undo action listener
	 */
	public void undo() {
    //message for status bar
    String message = "Performing Undo";
    this.txtMessageBox.setText(message);
	  
	  //send grid to trap current state of cell for redo stack and get previous state of cell from undo stack
	  Cell undoneCell = new Cell(undoRedoStacks.undoAction(grid));
	  
	  //if -1 is returned if there are no more cells in undostack
	  if(undoneCell.getRow() != -1) {  
      //get the cell column string
      String colString = undoneCell.getCol();
      //get the cell column integer
      int col = Grid.colToNumber(undoneCell.getCol());
      //get the cell row integer
      int row = undoneCell.getRow();
      //get the cell value string
      String value = undoneCell.getValue();
      //get the cell formatting
      Formatting format= undoneCell.getCellFormat();
      
      //restore cell's value and format from undo stack
      grid.selectCell(colString, row).setValue(value);
      grid.selectCell(colString, row).setCellFormat(format);

      //set focus of cursor to cell that was just undone
      tblGrid.changeSelection(row-1, col-1, false, false);
      
      //if value of cell is 0
      if(value.equalsIgnoreCase("0")) {
        //delete cell from hashtable
        deleteCell();
        //set txtInputBox text to empty
        txtInputBox.setText("");
      } else {
        //set txtInputBox text to value that was returned
        txtInputBox.setText(value);
      }
      
      //update GUI
      updateFromInput(row,col);
    } else {
      message = "Sorry, no more undos :(";
      this.txtMessageBox.setText(message);
      //show popup with sound
      Toolkit.getDefaultToolkit().beep();
      JOptionPane.showMessageDialog(new JFrame(),
          "Sorry, no more undos available", "Undo Limit Reached",
          JOptionPane.WARNING_MESSAGE);
    }
	}
	
  /**
   * Method to handle redo action listener
   */
	public void redo() {
    //message for status bar
	  String message = "Performing Redo";
    this.txtMessageBox.setText(message);

	  //send grid to trap current state of cell for undo stack and get previous state of cell from redo stack
	  Cell redoneCell = new Cell(undoRedoStacks.redoAction(grid));
	  
	  //if -1 is returned if there are no more cells in redostack
    if(redoneCell.getRow() != -1) {  
      //get the cell column string
      String colString = redoneCell.getCol();
      //get the column integer
      int col = Grid.colToNumber(redoneCell.getCol());
      //get the row integer
      int row = redoneCell.getRow();
      //get the cell value string
      String value = redoneCell.getValue();
      //get the cell formatting
      Formatting format= redoneCell.getCellFormat();

      //restore cell's value and format from redo stack
      grid.selectCell(colString, row).setValue(value);
      grid.selectCell(colString, row).setCellFormat(format);
      
      //set focus of cursor to cell that was just redone
      tblGrid.changeSelection(row-1, col-1, false, false);
      
      //if value of cell is 0
      if(value.equalsIgnoreCase("0")) {
        //delete cell from hashtable
        deleteCell();
        //set txtInputBox text to empty
        txtInputBox.setText("");
      } else {
        //set txtInputBox text to value that was returned
        txtInputBox.setText(value);
      }
      
      //update GUI
      updateFromInput(row,col);
    } else {
      message = "Sorry, no more redos :(";
      this.txtMessageBox.setText(message);
      //show popup with sound
      Toolkit.getDefaultToolkit().beep();
      JOptionPane.showMessageDialog(new JFrame(),
          "Sorry, no more redos available", "Redo Limit Reached",
          JOptionPane.WARNING_MESSAGE);
    }
  }
  
	 /**
   * Method to handle no undo redo action listener
   */
  public void noUndoRedo() {
    //work with previously selected cell, not currently selected
    Cell prevCell = grid.getCell(Grid.numToCol(oldSelectedCol), oldSelectedRow);
    
    //check if previously selected cell contains negative col or row
    if(prevCell.getRow() < 0 || Grid.colToNumber(prevCell.getCol()) < 0){
      return;
    }
    
    //get the column integer
    int col = Grid.colToNumber(prevCell.getCol());
    //convert column integer to string
    String colConvert = Grid.numToCol(col);
    //get the row integer
    int row = prevCell.getRow();
    
    //perform default noUndoRedo action on previous cell
    undoRedoStacks.noUndoRedoAction(grid.getCell(colConvert, row));
  }

	

	/**
	 * Capture GUI interactions. need to add the actionlistener to anything that triggers events
	 * 
	 * @param e The triggering event
	 */
	public void actionPerformed(ActionEvent e){
		final Object objSourceClass = e.getSource(); //store the class of the source
		
		//Undo/Redo actions
		if(objSourceClass.equals(this.btnUndo) || objSourceClass.equals(this.mniUndo)) {
		  undo();
		} else if(objSourceClass.equals(this.btnRedo) || objSourceClass.equals(this.mniRedo)) {
		  redo();
		} else {
		  noUndoRedo();
		}

		//File menu operations
		if(objSourceClass.equals(this.btnNew) || objSourceClass.equals(this.mniNew)){
			newSpreadsheet();

		}else if(objSourceClass.equals(this.btnLoad) || objSourceClass.equals(this.mniLoad)){
			loadSpreadsheet();

		}else if(objSourceClass.equals(this.btnSave) || objSourceClass.equals(this.mniSave)){
			saveSpreadsheet();

		}else if(objSourceClass.equals(this.btnSaveAs) || objSourceClass.equals(this.mniSaveAs)){
			saveAsSpreadsheet();

	  //Help menu operations
		}else if(objSourceClass.equals(this.mniAbout)){
			JOptionPane.showMessageDialog(new JFrame(),
				"This is Team 3's Spreadsheet.  We have included a number of features for manipulating spreadsheet data.\n"
				+ "These include: New, Load, Save, Save As, Cut, Copy, Paste, Update, and Delete.\n"
				+ "Please enjoy the functionality!", "Team 3, 2014, proprietary copyright all rights included.",
				JOptionPane.PLAIN_MESSAGE);

		
	  //Edit menu operations
		}else if(objSourceClass.equals(this.btnCopy) || objSourceClass.equals(this.mniCopy)){
			copy();

		}else if(objSourceClass.equals(this.btnPaste) || objSourceClass.equals(this.mniPaste)){
			paste();

		}else if(objSourceClass.equals(this.btnCut) || objSourceClass.equals(this.mniCut)){
			cut();

		//Format menu operations - UNCOMMENT Button Actions WHEN Buttons are created
	  }else if(/*objSourceClass.equals(this.btnFormatReal) || */ objSourceClass.equals(this.mniReal)){
      setCellFormat("Real");
      
    }else if(/*objSourceClass.equals(this.btnFormatMonetary) || */ objSourceClass.equals(this.mniMonetary)){
      setCellFormat("Monetary");
      
    }else if(/*objSourceClass.equals(this.btnFormatScientific) || */ objSourceClass.equals(this.mniScientific)){
      setCellFormat("Scientific");
      
    }else if(/*objSourceClass.equals(this.btnFormatInteger) || */ objSourceClass.equals(this.mniInteger)){
      setCellFormat("Integer");
      
    //}else if(/*objSourceClass.equals(this.btnFormatText) || */ objSourceClass.equals(this.mniText)){
      //setCellFormat("Test");
			
    //Button bar update button operation
		}else if(objSourceClass.equals(this.btnUpdate)){
			updateFromInput(tblGrid.getSelectedRow()+1,tblGrid.getSelectedColumn()+1);
		}
	}

	/**
	 * Update the currently selected cell to whatever text is typed into the input box
	 */
	private void updateFromInput(int row,int col){
		grid.getCell(Grid.numToCol(col), row).setValue(txtInputBox.getText());
		String output = "";
		if(grid.getCell(Grid.numToCol(col), row).isValidValue()){
			if(grid.getCell(Grid.numToCol(col), row).isStringValue())
				output = grid.getCell(Grid.numToCol(col), row).getValue();
			else
				output = grid.getCell(Grid.numToCol(col), row).getFormatedValue();}
		else
		{
			output = "#ERR";
			displayMessage(grid.getCell(Grid.numToCol(col), row).getError());
		}
		tblGrid.setValueAt(output, row-1, col-1);
	}
	
	/**
	 * KeyListener key press event handler
	 */
	public void keyPressed(KeyEvent e){
		//Nothing required
	}
	/**
	 * KeyListener key release event handler, not used.
	 */
	public void keyReleased(KeyEvent e){
		if(e.getSource().getClass() == SSTable.class && e.getKeyCode() >36 && e.getKeyCode() <41){
			valueChanged();
		}
	}
	
	/**
	 * KeyListener key typed event handler, not used.
	 */
	public void keyTyped(KeyEvent e){
		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;

		//if ENTER or TAB
		if(e.getKeyChar() == KeyEvent.VK_ENTER || e.getKeyChar() == KeyEvent.VK_TAB) {
		  valueChanged();
		//If DELETE  
		} else if(e.getKeyChar() == KeyEvent.VK_DELETE) {
			if(grid.getCell(colConvert, row).getEvaluatedValue() != 0.0) { //Only delete if cell is non-empty
        noUndoRedo();
			  deleteCell();
			}
		//If BACKSPACE	
		} else if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			if(e.getSource().getClass() == SSTable.class) {
				if(changed) {
					if(txtInputBox.getText() != null && !txtInputBox.getText().equals("")) {
						txtInputBox.setText(txtInputBox.getText().substring(0,txtInputBox.getText().length()-1));
					}
				} else {
					txtInputBox.setText("");
					tblGrid.setValueAt("",row-1,col-1);
					changed = true;
				}
			} else { 
			  changed = true;
			}
		//check for keybindings to menu items (CTRL+something)
		} else if(e.isControlDown()) {
		  System.out.println("CTRL+something pressed");
		//default case
		} else {
      if(!changed && e.getSource().getClass() == SSTable.class) {
				txtInputBox.setText(e.getKeyChar()+"");
				tblGrid.setValueAt("",row-1,col-1);
				changed = true;
				oldSelectedCol = col;
				oldSelectedRow = row;
			} else if(e.getSource().getClass() == SSTable.class) {
				txtInputBox.setText(txtInputBox.getText() + e.getKeyChar());
				changed = true;
			} else { 
			  changed = true;
			}
		}
	}


	/**
	 * Captures de-selection action from the JTable cells, 
	 * handles this by communicating the cell's current value to back-end for verification
	 * 
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged() {
		if(tblGrid.getSelectedRow() < 0 || tblGrid.getSelectedColumn() < 0){
			return;
		}

		int col = tblGrid.getSelectedColumn()+1;
		String colConvert = Grid.numToCol(col);
		int row = tblGrid.getSelectedRow()+1;
		if(changed)
		{
      //record cell state to Undo/redo stack before applying change (Added-NM-2014-03-19)
      noUndoRedo();
		  
		  updateFromInput(oldSelectedRow,oldSelectedCol);
			changed = false;
		} else if(tblGrid.getValueAt(row-1, col-1)!=null && tblGrid.getValueAt(row-1, col-1).equals("#ERR"))
		{
			displayMessage(grid.getCell(Grid.numToCol(col), row).getError());
		}
		oldSelectedRow = row;
		oldSelectedCol = col;
		Cell x = grid.checkCell(colConvert, row);
		if(x == null){
			txtInputBox.setText("");
		}
		else{
			txtInputBox.setText("" + grid.checkCell(colConvert, row).getValue());
		}
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
	
	private class ColEventListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(tblGrid.getSelectedColumn() == lastCol && tblGrid.getSelectedRow() == lastRow)
			{
				SSGUI.this.valueChanged();	
			}
			lastCol = tblGrid.getSelectedColumn();
			
		}
	}
	
	private class RowEventListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(tblGrid.getSelectedColumn() == lastCol && tblGrid.getSelectedRow() == lastRow && tblGrid.getSelectedColumn()+1 == oldSelectedCol)
			{
				SSGUI.this.valueChanged();
			}
			lastRow = tblGrid.getSelectedRow();
		}
	}

}