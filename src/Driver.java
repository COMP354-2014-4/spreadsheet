import spreadsheet.Grid;
import utils.Formula;


public class Driver {

	public static void main(String[] args) {
		Grid grid = new Grid();
		if(grid.getSelectedCell() != null){
			grid.getSelectedCell().setValue("=B1+3");
			grid.getSelectedCell().display();
			grid.getCell("B", 1).setValue("=2+5");
			grid.getCell("B", 1).display();
			grid.getSelectedCell().display();
			
		}else{
			System.out.print("what");
		}
	}

}
