package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * 
 * @author Norman Mirotchnick
 * @date   2014-03-15 
 * @description Menu class to control the help items
 *
 */

public class MenuHelp {
  //Help Menu
  private JMenu mnuHelp = new JMenu("Help");
  
  //Help Menu items
  private JMenuItem mniAbout = new JMenuItem("About");
  
  //build help menu
  mnuHelp.add(mniAbout);
  mniAbout.addActionListener(this);

}
