package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @author Norman Mirotchnick
 * @date   2014-03-15 
 * @description Menu class to control the format items
 *
 */

public class MenuFormat {
  //Format Menu
  public JMenu mnuFormat = new JMenu("Format");         //PRIVATE, is now public for test only
  
  //Format Menu items
  public JMenuItem mniReal = new JMenuItem("Real");     //default format - PRIVATE, is now public for test only
  public JMenuItem mniMonetary = new JMenuItem("Monetary"); //monetary format, which prints a dollar sign and uses only two decimal places - PRIVATE, is now public for test only
  public JMenuItem mniScientific = new JMenuItem("Scientific"); //scientific format - PRIVATE, is now public for test only
  public JMenuItem mniInteger = new JMenuItem("Integer");  //integer format, where the decimal part is truncated - PRIVATE, is now public for test only
  // public JMenuItem mniText = new JMenuItem("Text"); //string format, displays cell value and does not evaluate - PRIVATE, is now public for test only

}
