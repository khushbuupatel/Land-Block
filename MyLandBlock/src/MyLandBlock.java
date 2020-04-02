import java.util.Scanner;

// import whatever packages are needed here

/**
 * ProgFunAssignment1 is a java application that allows the end users to add
 * house(s) on a block of land by implementing certain validation checks
 * 
 * @author Khushbu
 *
 */
public class MyLandBlock {

	/**
	 * This is the main entry point of the application. It takes input from the end
	 * user for the land block size (rows X columns). Prompts the user to Add a
	 * house, Display the land block, Clear the land block or Quit the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Initialize scanner class object to take input from user using keyboard
		Scanner sc = new Scanner(System.in);

		// Variables to store the rows and columns for the land size
		int blockRows, blockColumns;
		// Variable to store the option selected by user
		int selectedOption;
		// Variables to store the house position and size entered by user
		int rowPos, colPos, rows, columns;

		// Prompts the user to enter no of rows & columns until it is GREATER than 2 or
		// LESS THAN OR EQUAL to 10
		do {
			System.out.println("Please enter no of rows for the land block such that (2 < rows <= 10):");
			blockRows = sc.nextInt();
		} while (blockRows <= 2 || blockRows > 10);

		do {
			System.out.println("Please enter no of columns for the block such that(2 < columns <= 10):");
			blockColumns = sc.nextInt();
		} while (blockColumns <= 2 || blockColumns > 10);

		// Creates object for MyBlock class and initializes the land block by passing
		// arguments to the constructor
		MyBlock block = new MyBlock(blockRows, blockColumns);

		do {
			// Displays the menu to the end user
			System.out.println("Select one of the following option:");
			System.out.println("1. Add a house");
			System.out.println("2. Display the block");
			System.out.println("3. Clear the block");
			System.out.println("4. Quit");

			// Reads the input from the end user
			selectedOption = sc.nextInt();

			// Based on the selectedOption perform the appropriate action
			switch (selectedOption) {
			case 1:
				// Takes the coordinates where the house is to be added from user
				System.out.println("Enter the coordinates of the house");
				System.out.print("x: ");
				rowPos = sc.nextInt();
				System.out.print("y: ");
				colPos = sc.nextInt();

				// Takes the row and column size of the house to be added from user
				System.out.println("Enter the row size for house: ");
				rows = sc.nextInt();
				System.out.println("Enter the column size for house: ");
				columns = sc.nextInt();

				// Calls the addHouse method with the above parameters to add the house and
				// displays the land block if the house is added successfully
				if (block.addHouse(rowPos, colPos, rows, columns))
					block.displayBlock();
				else
					System.out.println("House cannot be constructed");

				break;
			case 2:
				// Displays the land block
				block.displayBlock();
				break;
			case 3:
				// Clears the land block
				block.clearBlock();
				break;
			case 4:
				// Gracefully exits from the program
				exitProgram(sc);
				break;
			default:
				System.out.println("Please select a valid option");
			}

			// Display the menu after every step performed until the end user selects to
			// exit from the program (i.e Option 4)
		} while (selectedOption != 4);
	}

	/**
	 * This method is used to exit from the application
	 * 
	 * @param sc Scanner class object to close the scanner before exiting from the
	 *           program
	 */
	private static void exitProgram(Scanner sc) {
		sc.close();
		System.out.println("Quitting...");
		System.exit(0);
	}
}

/**
 * 
 * MyBlock class contains the implementation of diplayBlock(), clearBlock() and
 * addHouse() methods used in the main method
 *
 */
class MyBlock {

	// landBlock stores the rows and columns of the Land block
	private int[][] landBlock;
	private int blockRows, blockColumns;

	// Boolean variable 'vacant' to store if the land block is empty or not
	private boolean vacant;
	// houseNo initialized with 1 and will be incremented every time a new house is
	// added
	int houseNo = 1;

	/**
	 * Parameterized constructor to initialize the block with user defined size
	 * (rows X columns). It sets the variable vacant to true as the land block is
	 * empty when the application starts
	 * 
	 * @param rows
	 * @param columns
	 */
	public MyBlock(int rows, int columns) {

		blockRows = rows;
		blockColumns = columns;

		// initializes the block with user entered rows and columns
		landBlock = new int[blockRows][blockColumns];
		// sets boolean 'vacant' to true as the block is empty
		vacant = true;
	}

	/**
	 * This method displays the Land block to the user
	 */
	public void displayBlock() {

		// the outer for loop iterates over the 2-D block row wise
		for (int[] row : landBlock) {
			// the inner for loop prints the column wise value with " " in between each
			// element
			for (int column : row) {
				System.out.print(column + " ");
			}
			// prints column values in new line for the next row
			System.out.println();
		}
	}

	/**
	 * This method clears the land block by removing any previously added house
	 */
	public void clearBlock() {

		// fills the land block with 0 to make it empty
		buildBlockOrHouse(0, 0, blockRows, blockColumns, 0);
		displayBlock();

		// sets vacant to true as the land block is cleared
		vacant = true;
	}

	/**
	 * This method adds the house on the land block if the following rules are not
	 * violated:- R1: The house should not be on the border of the land block R2:
	 * The house should be one column or one row away from other houses R3: No part
	 * of house should go outside of the block R4: Minimum house size is 1 X 1
	 * 
	 * @param rowPos       row coordinate where the house is to be added
	 * @param colPos       column coordinate where the house is to be added
	 * @param houseRows    no of rows of the house
	 * @param houseColumns no of columns of the house
	 */
	public boolean addHouse(int rowPos, int colPos, int houseRows, int houseColumns) {

		// To identify if the house can be added or not (initially setting it to false)
		boolean houseCanBeAdded = false;

		// Validates Rule 1, Rule 3 and Rule 4
		// Checks if the no of rows and columns of the house are greater than 0 (i.e.
		// R4)
		if (houseRows > 0 && houseColumns > 0) {

			// TRUE if the rows and columns of the house to be added is NOT ON or BEYOND the
			// Land borders (i.e R1 & R3)
			boolean validHouseRows = (rowPos > 0 && rowPos < (blockRows - 1)) && ((rowPos + houseRows) < (blockRows));
			boolean validHouseColumns = (colPos > 0 && colPos < (blockColumns - 1))
					&& ((colPos + houseColumns) < (blockColumns));

			// House size and coordinates are valid if validHouseRows=TRUE and
			// validHouseColumns=TRUE
			houseCanBeAdded = validHouseRows && validHouseColumns;

			// If the land block is vacant and houseCanBeAdded = TRUE
			if (vacant && houseCanBeAdded) {

				// Then directly build the house and set 'vacant' to false as house is added
				buildBlockOrHouse(rowPos, colPos, houseRows, houseColumns, houseNo++);
				vacant = false;

				// Else if the land is not vacant and house can be added then check if the Rule
				// 2 is satisfied (House along with its border does not overlap other house(s))
			} else if (houseCanBeAdded) {

				// Outer loop starts-with (rowPos-1) and ends-with (rowPos + houseRows) to
				// include the upper and lower rows of the house to be added
				for (int rows = rowPos - 1; rows <= (rowPos + houseRows); rows++) {

					// Inner for loop starts-with (colPos-1) and ends-with (colPos + houseColumns)
					// to include the left and right columns of the house to be added
					for (int columns = colPos - 1; columns <= (colPos + houseColumns); columns++) {

						// If position is already occupied then set houseCanBeAdded to false and return
						if (landBlock[rows][columns] != 0) {
							houseCanBeAdded = false;
							return houseCanBeAdded;
						}
					}
				}

				// Build the house as the house can be added as all the rules are satisfied
				houseCanBeAdded = true;
				buildBlockOrHouse(rowPos, colPos, houseRows, houseColumns, houseNo++);

			} // if else if end
		} // Outer if end

		return houseCanBeAdded;
	}

	/**
	 * This method builds the house on the land block or clears the land block by
	 * adding 0
	 * 
	 * @param rowPos             row coordinate where the house is to be added
	 * @param colPos             column coordinate where the house is to be added
	 * @param rows               no of rows of the house
	 * @param columns            no of columns of the house
	 * @param noToBeAddedInBlock house no that is to be added 1,2.. etc (Adds 0 if
	 *                           the block is cleared)
	 */
	private void buildBlockOrHouse(int rowPos, int colPos, int rows, int columns, int noToBeAddedInBlock) {
		int columnPos = colPos;

		// loop through no of rows to be added in the house
		for (int i = 0; i < rows; i++, rowPos++) {
			// loop through no of columns to be added in the house
			for (int j = 0; j < columns; j++, columnPos++) {
				// build the house on the row and column position specified
				landBlock[rowPos][columnPos] = noToBeAddedInBlock;
			}

			// assign the columnPos again with passed colPos before moving to next row
			columnPos = colPos;
		}
	}

}