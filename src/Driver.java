import spreadsheet.Grid;


public class Driver {

	public static void main(String[] args) {
		Grid grid = new Grid();
		if(grid.getSelectedCell() != null){
			grid.getSelectedCell().setValue("=B1+3");
			grid.getSelectedCell().display();
			grid.getCell("B", 1).setValue("=2+5");
			grid.getCell("B", 1).display();
			grid.getSelectedCell().setValue("=2+(C1)");
			grid.getCell("B", 1).setValue("=3+5");
			grid.getSelectedCell().display();
			
		}else{
			System.out.print("what");
		}
	}

}
