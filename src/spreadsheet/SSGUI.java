package spreadsheet;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;

/**
 * @author Justin Dupuis
 * @version 2014-02-09
 * 
 * This class represents the GUI for the spreadsheet application.
 * A grid must be passed to create the object, but it can be blank
 *
 */
public class SSGUI implements ActionListener{
	//Tools
	private Toolkit toolKit;
	
	//main components
	private JFrame frmWindow;
	private JPanel pnlCenter;
	private JPanel pnlSouth;
	
	//Control components
	private JMenuBar mnbMenu;
	private JToolBar tbrToolBar;
	
	//File Menu
	private JMenu mnuFile;
	private JMenuItem mniNew;
	private JMenuItem mniLoad;
	private JMenuItem mniSave;
	private JMenuItem mniSaveAs;
	
	//Edit Menu
	JMenu mnuEdit;
	private JMenuItem mniCut;
	private JMenuItem mniCopy;
	private JMenuItem mniPaste;
	
	//Help Menu
	private JMenu mnuHelp;
	private JMenuItem mniAbout;
	
	//ToolBar buttons
	private JButton btnNew;
	private JButton btnLoad;
	private JButton btnSave;
	private JButton btnSaveAs;
	private JButton btnCopy;
	private JButton btnCut;
	private JButton btnPaste;
	
	//Center panel components
	JScrollPane scrTblScrollPane;
	private SSTable tblGrid;
	
	//bottom panel components
	private JTextField txtMessageBox;
	
	//window dimensions
	private int intScreenWidth;
	private int intScreenHeight;
	
	//Back-end data objects
	private Grid grid = new Grid();
	
	//Save location
	private String strFileLocation = ""; // Set to null instead?

	/**
	 * Default constructor, which acccepts the grid to be displayed
	 * 
	 * @param gridObject The back-end data stored in the grid
	 */
	public SSGUI(Grid gridObject){
		
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
		
		// Add all the action listeners
		mniNew.addActionListener(this);
		mniLoad.addActionListener(this);
		mniSave.addActionListener(this);
		mniSaveAs.addActionListener(this);
				
		btnNew.addActionListener(this);
		btnLoad.addActionListener(this);
		btnSave.addActionListener(this);
		btnSaveAs.addActionListener(this);
		btnCopy.addActionListener(this);
		btnCut.addActionListener(this);
		btnPaste.addActionListener(this);
		
		//Build center panel		
		tblGrid = new SSTable(gridObject);//uses the default values on load
		tblGrid.setFillsViewportHeight(true);
		tblGrid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrTblScrollPane = new JScrollPane(tblGrid,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrTblScrollPane.setColumnHeaderView(tblGrid.getTableHeader());
		scrTblScrollPane.setRowHeader(this.tblGrid.getRowHeader());
		scrTblScrollPane.setBackground(new Color(255,255,255));
		
		//Build bottom panel components
		txtMessageBox = new JTextField();
		
		//set window properties
		frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWindow.setLayout(new BorderLayout());
		
		//adopt fullscreen
		intScreenWidth = ((int) toolKit.getScreenSize().getWidth());  
		intScreenHeight = ((int) toolKit.getScreenSize().getHeight());  
		frmWindow.setPreferredSize(new Dimension(intScreenWidth,intScreenHeight)); 
		
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
		frmWindow.add(tbrToolBar,BorderLayout.NORTH);
		frmWindow.add(scrTblScrollPane,BorderLayout.CENTER);
		frmWindow.add(txtMessageBox,BorderLayout.SOUTH);
		
		//display
		frmWindow.pack();
		frmWindow.setVisible(true);		
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
	 * Updates to use a new Grid object
	 * 
	 * @param gridUpdate The grid to display
	 */
	public void updateTable(Grid gridUpdate){
		this.grid = gridUpdate;
		//THIS IS CAUSING A LOT OF PROBLEMS
	}
	
	/**
	 * Create a new spreadsheet
	 */
	public void newSpreadsheet(){
		//Should work in theory
		
		//JOptionPane.showMessageDialog(null,"NEW SPREADSHEET");
		grid.clear();
		strFileLocation = null; // clears the save file location
		
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
			strFileLocation = fc.getSelectedFile().getAbsolutePath();
			grid.load(strFileLocation);
			
		}
	}
	
	/**
	 * Save the spreadsheet to a file already selected, or prompt for location if file not yet saved
	 */
	public void saveSpreadsheet(){
		
		if(strFileLocation.equals("")) // Compare to null?
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
			grid.save(strFileLocation); // Also different from loadSpreadsheet()
		}
	}
	
	/**
	 * cut cells
	 */
	public void cut(){
		
	}
	
	/**
	 * copy cells
	 */	
	public void copy(){
		
		System.out.println("row: " +(tblGrid.cellSelected.getSelected_Row()) + " column: " +tblGrid.cellSelected.getSelected_Col());
		
	}
	
	/**
	 * paste cells
	 */
	public void paste(){
		
	}
	
	/**
	 * Capture GUI interactions. need to add the actionlistener to anything that triggers events
	 * 
	 * @param e The actionevent
	 */
	public void actionPerformed(ActionEvent e){
		final Object objSourceClass = e.getSource(); //store the class of the source
		
		/*
		 * Template for handling events from each component
		 */
		if(objSourceClass.equals(this.btnNew) || objSourceClass.equals(this.mniNew)){
			newSpreadsheet();
		}
		if(objSourceClass.equals(this.btnLoad) || objSourceClass.equals(this.mniLoad)){
			loadSpreadsheet();
		}
		if(objSourceClass.equals(this.btnSave) || objSourceClass.equals(this.mniSave)){
			saveSpreadsheet();
		}
		if(objSourceClass.equals(this.btnSaveAs) || objSourceClass.equals(this.mniSaveAs)){
			saveAsSpreadsheet();
		}
		if(objSourceClass.equals(this.btnCopy))
		{
			copy();
		}
	}
	
	/**
	 * Sets the look and feel to the current OS
	 */
	private void setLookAndFeel(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			//Doesn't matter if it doesn't work
		}
	}
}
