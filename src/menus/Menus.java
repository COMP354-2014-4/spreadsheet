package menus;

/* Import statements */
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
 * 
 * @author Norman Mirotchnick
 * @date   2014-03-15 
 * @description Master menu class that outside classes call to get menus
 *
 */

public class Menus {
  //Menu bar object
  private JMenuBar mnbMenu = new JMenuBar();
  
  //add menus to menu bar
  mnbMenu.add(mnuFile);
  mnbMenu.add(mnuEdit);
  mnbMenu.add(mnuHelp);

}
