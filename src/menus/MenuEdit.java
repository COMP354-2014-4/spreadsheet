package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @author Norman Mirotchnick
 * @date   2014-03-15 
 * @description Menu class to control the edit items
 *
 */

public class MenuEdit {
  //Edit Menu
  public JMenu mnuEdit = new JMenu("Edit");   //PRIVATE, is now public for test only
  
  //Edit Menu items
  public JMenuItem mniCut = new JMenuItem("Cut");  //PRIVATE, is now public for test only
  public JMenuItem mniCopy = new JMenuItem("Copy"); //PRIVATE, is now public for test only
  public JMenuItem mniPaste = new JMenuItem("Paste");  //PRIVATE, is now public for test only

  //build edit menu
  mnuEdit.add(mniCut);
  mnuEdit.add(mniCopy);
  mnuEdit.add(mniPaste);
}
