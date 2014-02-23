package spreadsheet;

import java.awt.*;

import javax.swing.table.*;
import javax.swing.*;

/**
 * @author Justin Dupuis
 * @version 2014-02-09
 *
 * Used for formatting the table
 */
class SSTableRenderer implements TableCellRenderer {
	DefaultTableCellRenderer renderer;
	Font fntHeader;

	/*
	 * Constructor
	 */
	public SSTableRenderer(JTable table) {
	    this.renderer = new DefaultTableCellRenderer();
	    this.renderer.setHorizontalAlignment(JLabel.CENTER);
	    this.renderer.setBackground(new Color(200,200,255));

		fntHeader = table.getFont();
		fntHeader = fntHeader.deriveFont(Font.BOLD);
		
		this.renderer.setFont(fntHeader);
	}
	
	@Override
    public Component getTableCellRendererComponent(
	    JTable table, Object value, boolean isSelected,
	    boolean hasFocus, int row, int column) {
	    return renderer.getTableCellRendererComponent(
	        table, value, isSelected, hasFocus, row, column);
	    }
}
