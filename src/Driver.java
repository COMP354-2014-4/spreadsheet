import java.util.Scanner;
import spreadsheet.Cell;
import spreadsheet.Grid;


public class Driver {
	private static boolean _run = true;
	private static Grid _grid = new Grid();

	public static void main(String[] args) {
		System.out.println("Welcome to the COMP354 spreadsheet. You can type help to display a list of possible commands");
		
		Scanner sc = new Scanner(System.in);
		while(_run){
			Cell selectedCell = _grid.getSelectedCell();
			System.out.print(selectedCell.getCol() + selectedCell.getRow() + "> ");
			String line = sc.nextLine();
			String[] command = line.split(" ");
			if(command.length == 1){
				Driver.evalCommand(command[0]);
			}else if(command.length == 2){
				Driver.evalCommand(command[0], command[1]);
			}else{
				System.out.println("Invalid command");			
			}
			
		}
		sc.close();
		System.out.println("Thanks for using the COMP354 spreadsheet!");
		
	}
	
	public static void evalCommand(String command){
		if(command.equalsIgnoreCase("help")){//HELP COMMAND
			System.out.println("List of available commands");
			System.out.println("--------------------------");
			System.out.println("help\t\t | Display the list of commands");
			System.out.println("clear\t\t | Clears the current grid");
			System.out.println("put {value}\t | Put the value inside of the cell");
			System.out.println("select {cell}\t | Changes the cursor to the chosen cell.Arg should be the name of a cell ex: A1");
			System.out.println("display\t\t | Display the chosen grid");
			System.out.println("display {cell}\t | Display the chosen Cell");
			System.out.println("save {filename}\t | Save the grid to a file");
			System.out.println("load {filename}\t | Load the grid from a file");
			System.out.println("exit \t\t | Exit the program");
			
		}else if(command.equalsIgnoreCase("clear")){//CLEAR COMMAND
			_grid.clear();
		}else if(command.equalsIgnoreCase("display")){//DISPLAY COMMAND
			_grid.Display();
		}else if(command.equalsIgnoreCase("exit")){//EXIT COMMAND
			_run = false;
		}else{
			System.out.println("Invalid command");
		}
	
}
	
	public static void evalCommand(String command, String arg){
			if(command.equalsIgnoreCase("put")){//PUT {VALUE} COMMAND
				_grid.getSelectedCell().setValue(arg);
				
			}else if(command.equalsIgnoreCase("select")){//SELECT {CELL} COMMAND
				String[] coord = cellNameToColRow(arg);
				if(coord != null){
					_grid.selectCell(coord[0], Integer.parseInt(coord[1]));
				}else{
					System.out.println("Invalid Cell name");
				}
				
			}else if(command.equalsIgnoreCase("display")){//DISPLAY {CELL} COMMAND
				String[] coord = cellNameToColRow(arg);
				if(coord != null){
					Cell cellToDisplay = _grid.getCell(coord[0], Integer.parseInt(coord[1]));
					if(cellToDisplay != null){
						System.out.print(arg + ": ");
						cellToDisplay.display();
					}else{
						System.out.println("The cell you want to display is out of bound");
					}
				}else{
					System.out.println("Invalid Cell name");
				}
				
			}else if(command.equalsIgnoreCase("save")){//SAVE {FILENAME} COMMAND
				_grid.save(arg);
			}else if(command.equalsIgnoreCase("load")){//LOAD {FILENAME} COMMAND
				_grid.load(arg);
			}else{//INVALID COMMAND
				System.out.println("Invalid command");
			}
		
	}
	
	public static String[] cellNameToColRow(String name){
		if(!name.matches("^[A-Z]+[0-9]+$"))
			return null;
		int firstNumPos = name.indexOf("0");
		
		for(int i = 1; i <= 9; i++ ){
			int secNumPos = name.indexOf(""+ i);
			if( (secNumPos < firstNumPos && secNumPos != -1) || firstNumPos == -1)
				firstNumPos = secNumPos;
			
		}
		return new String[]{name.substring(0, firstNumPos), name.substring(firstNumPos)};
	}

}
