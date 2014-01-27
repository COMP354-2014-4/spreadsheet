import java.util.Scanner;
import spreadsheet.Cell;
import spreadsheet.Grid;


public class Driver {

	public static void main(String[] args) {
		Grid grid = new Grid();
		/*System.out.println("Welcome to the COMP354 spreadsheet. You can type help to display a list of possible commands");
		
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		while(run)
			Cell selectedCell = grid.getSelectedCell();
			System.out.print(, grid.get);
			sc.next();
			
		}
		*/
		if(grid.getSelectedCell() != null){
			grid.getSelectedCell().setValue("=B1+3");
			grid.getSelectedCell().display();
			grid.getCell("B", 1).setValue("=D1");
			grid.getCell("D", 1).setValue("=2+E1");
			grid.getCell("E", 1).setValue("=2+D1");
			//grid.getCell("F", 1).setValue("=2()");
			//grid.getCell("F", 1).display();
			grid.getCell("E", 1).display();
			grid.getSelectedCell().display();
			
		}else{
			System.out.print("what");
		}
	}

}
