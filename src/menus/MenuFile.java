package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @author Norman Mirotchnick
 * @date   2014-03-15 
 * @description Menu class to control the file items
 *
 */

public class MenuFile {
  //File Menu
  public JMenu mnuFile = new JMenu("File");   //PRIVATE, is now public for test only
  
  //File Menu items
  public JMenuItem mniNew = new JMenuItem("New");  //PRIVATE, is now public for test only
  public JMenuItem mniLoad = new JMenuItem("Load"); //PRIVATE, is now public for test only
  public JMenuItem mniSave = new JMenuItem("Save"); //PRIVATE, is now public for test only
  public JMenuItem mniSaveAs = new JMenuItem("Save As"); //PRIVATE, is now public for test only

  //build file menu
  mnuFile.add(mniNew);
  mnuFile.add(mniLoad);
  mnuFile.add(mniSave);
  mnuFile.add(mniSaveAs);
  
}
