/*  Name: Tarunyaa Sivakumar
*   PennKey: tarunyaa
*   Recitation: 203
*
*   Executions: N/A
*
*   Description:
*   Class that implements the interfaces, VisualCanvas and EventListener.
*   Contains the main methods used to build the Sudoku puzzle then enter values
*   into it, while outputting certain error messages and progressing the game
*   forward.
*/

public class Sudoku implements VisualCanvas, EventListener {
    
    private String fileName;
    private Box [][] puzzleArray;
    
    public Sudoku(String fileName) {
        this.fileName = fileName;
    }
    
    /**
    * Inputs: N/A
    * Outputs: void
    * Description: loads the original sudoku onto the UI
    */
    @Override
    public void loadPuzzle() {
        puzzleArray = new Box[GRIDSIZE][GRIDSIZE];
        In inStream = new In(fileName);
        String line = "";
        //lesser than GRIDSIZE condition ensures that only 9 x 9 characters are read
        for (int row = 0; row < GRIDSIZE; row++) {
            line = inStream.readLine();
            for (int column = 0; column < GRIDSIZE; column++) {
                int boxValToPlace = (int) line.charAt(column) - 48;
                Box j = new Box(row, column, boxValToPlace, false);
                puzzleArray[row][column] = j;
            }
            
        }
        
    }
    
    /**
    * Inputs: N/A
    * Outputs: void
    * Description: throws illegal argument exceptions if the original sudoku is
    * incorrectly formatted
    */
    @Override
    public void checkInitialRuleViolation() {
        //checking if any numbers are repeated along the same row
        //array that increases in count everytime when a number (index + 1) shows up
        int[] noOfRepetitionsRow = new int[GRIDSIZE];
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE; column++) {
                int boxValCurr = (puzzleArray[row][column]).getValue();
                if (boxValCurr < 10 &&  boxValCurr > 0) {
                    noOfRepetitionsRow[boxValCurr - 1]++;
                }
            }
            for (int i = 0; i < GRIDSIZE; i++) {
                if (noOfRepetitionsRow[i] > 1) {
                    throw new IllegalArgumentException("Number " + (i + 1) +
                    " is repeated along the row " + (row + 1));
                }
            }
            
            for (int i = 0; i < GRIDSIZE; i++) {
                //resetting array values to 0 for the next row
                noOfRepetitionsRow[i] = 0;
            }
        }
        
        //checking if any numbers are repeated along the same column
        int[] noOfRepetitionsColumn = new int[GRIDSIZE];
        for (int column = 0; column < GRIDSIZE; column++) {
            for (int row = 0; row < GRIDSIZE; row++) {
                int boxValCurr = (puzzleArray[row][column]).getValue();
                if (boxValCurr < 10 &&  boxValCurr > 0) {
                    noOfRepetitionsColumn[boxValCurr - 1]++;
                }
            }
            for (int i = 0; i < GRIDSIZE; i++) {
                if (noOfRepetitionsColumn[i] > 1) {
                    throw new IllegalArgumentException("Number " + (i + 1) +
                    " is repeated along the column " + (column + 1));
                }
            }
            
            for (int i = 0; i < GRIDSIZE; i++) {
                //resetting array values to 0 for the next column
                noOfRepetitionsColumn[i] = 0;
            }
        }
        
        //checking if numbers are repeated in the same subGrid
        int[] noOfRepetitionsSG = new int[GRIDSIZE];
        for (int j = 0; j < SUBGRIDSIZE; j++) {
            int startRow = SUBGRIDSIZE * j;
            for (int k = 0; k < SUBGRIDSIZE; k++) {
                int startCol = SUBGRIDSIZE * k;
                
                for (int row = startRow; row < (startRow + 3); row++) {
                    for (int column = startCol; column < (startCol + 3); column++) {
                        int boxValCurr = (puzzleArray[row][column]).getValue();
                        if (boxValCurr < 10 && boxValCurr > 0) {
                            noOfRepetitionsSG[boxValCurr - 1]++;
                        }
                    }
                }
                
                for (int i = 0; i < GRIDSIZE; i++) {
                    if (noOfRepetitionsSG[i] > 1) {
                        throw new IllegalArgumentException("Number " + (i + 1) +
                        " is repeated along the subgrid " + (k + 1) +
                        " to the left and " + (j + 1) + " from the top");
                    }
                }
                
                for (int i = 0; i < GRIDSIZE; i++) {
                    //resetting array values to 0 for the next subgrid
                    noOfRepetitionsSG[i] = 0;
                }
                
            }
        }
        
        //checking if any illegal characters are in the original sudoku
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE; column++) {
                int val = (puzzleArray[row][column]).getValue();
                if (!(val < 10 && val > 0 || val == -16)) {
                    throw new IllegalArgumentException("Characters beside from" +
                    "digits 1-9 are entered.");
                }
            }
            
        }
        
    }
    
    /**
    * Inputs: N/A
    * Outputs: boolean
    * Description: checking for rule violations when a user enters a value and
    * showing appropriate error messages
    */
    @Override
    public boolean checkLaterRuleViolation() {
        boolean wasRuleViolated = false;
        
        //checking if any numbers are repeated along the same row
        int[] noOfRepetitionsRow = new int[GRIDSIZE];
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE; column++) {
                int boxValCurr = (puzzleArray[row][column]).getValue();
                if (boxValCurr < 10 &&  boxValCurr > 0) {
                    noOfRepetitionsRow[boxValCurr - 1]++;
                }
            }
            for (int i = 0; i < GRIDSIZE; i++) {
                if (noOfRepetitionsRow[i] > 1) {
                    PennDraw.setPenColor(PennDraw.RED);
                    PennDraw.setPenRadius(0.01);
                    PennDraw.setFontSize(12);
                    PennDraw.line(0, (9 - row) / 9d, 1, (9 - row) / 9d);
                    PennDraw.line(0, ((9 - row) - 1) / 9d, 1, ((9 - row) - 1) / 9d);
                    PennDraw.text(0.5, 1.1, "Number " + (i + 1) + " is repeated" +
                    " along the row " + (row + 1));
                    PennDraw.setFontBoldItalic();
                    PennDraw.text(0.5, 1.05, "Press the mouse or any key to " +
                    "delete this value and continue.");
                    PennDraw.setFontPlain();
                    wasRuleViolated = true;
                    
                }
            }
            
            for (int i = 0; i < GRIDSIZE; i++) {
                noOfRepetitionsRow[i] = 0;
            }
            
        }
        
        //checking if any numbers are repeated along the same column
        int[] noOfRepetitionsColumn = new int[GRIDSIZE];
        for (int column = 0; column < GRIDSIZE; column++) {
            for (int row = 0; row < GRIDSIZE; row++) {
                int boxValCurr = (puzzleArray[row][column]).getValue();
                if (boxValCurr < 10 &&  boxValCurr > 0) {
                    noOfRepetitionsColumn[boxValCurr - 1]++;
                }
            }
            
            for (int i = 0; i < GRIDSIZE; i++) {
                if (noOfRepetitionsColumn[i] > 1) {
                    PennDraw.setPenColor(PennDraw.RED);
                    PennDraw.setPenRadius(0.01);
                    PennDraw.setFontSize(12);
                    PennDraw.line((1 + column) / 9d, 0, (1 + column) / 9d, 1);
                    PennDraw.line(((1 + column) - 1) / 9d, 0, ((1 + column) - 1) /
                    9d, 1);
                    PennDraw.text(0.5, 1.15, "Number " + (i + 1) + " is repeated" +
                    " along the column " + (column + 1));
                    PennDraw.setFontBoldItalic();
                    PennDraw.text(0.5, 1.05, "Press the mouse or any key to " +
                    "delete this value and continue.");
                    PennDraw.setFontPlain();
                    wasRuleViolated = true;
                }
            }
            
            for (int i = 0; i < GRIDSIZE; i++) {
                noOfRepetitionsColumn[i] = 0;
            }
            
        }
        
        //checking if numbers are repeated in the same subGrid
        int[] noOfRepetitionsSG = new int[GRIDSIZE];
        for (int j = 0; j < SUBGRIDSIZE; j++) {
            int startRow = 3 * j;
            for (int k = 0; k < SUBGRIDSIZE; k++) {
                int startCol = 3 * k;
                
                for (int row = startRow; row < (startRow + 3); row++) {
                    for (int column = startCol; column < (startCol + 3); column++) {
                        int boxValCurr = (puzzleArray[row][column]).getValue();
                        if (boxValCurr < 10 &&  boxValCurr > 0) {
                            noOfRepetitionsSG[boxValCurr - 1]++;
                        }
                    }
                }
                
                for (int i = 0; i < GRIDSIZE; i++) {
                    if (noOfRepetitionsSG[i] > 1) {
                        PennDraw.setPenColor(PennDraw.RED);
                        PennDraw.setPenRadius(0.01);
                        PennDraw.setFontSize(12);
                        PennDraw.line(startCol / 9d, (9 - startRow) / 9d,
                        (startCol + 3) / 9d, (9 - startRow) / 9d);
                        PennDraw.line(startCol / 9d, (9 - startRow) / 9d,
                        startCol / 9d, (9 - startRow - 3) / 9d);
                        PennDraw.line(startCol / 9d, (9 - startRow - 3) / 9d,
                        (startCol + 3) / 9d, (9 - startRow - 3) / 9d);
                        PennDraw.line((startCol + 3) / 9d, (9 - startRow) / 9d,
                        (startCol + 3) / 9d, (9 - startRow - 3) / 9d);
                        PennDraw.text(0.5, 1.2, "Number " + (i + 1) +
                        " is repeated along the subgrid " + (k + 1) +
                        " to the left and " + (j + 1) + " from the top");
                        PennDraw.setFontBoldItalic();
                        PennDraw.text(0.5, 1.05, "Press the mouse or any key to " +
                        "delete this value and continue.");
                        PennDraw.setFontPlain();
                        wasRuleViolated = true;
                    }
                }
                
                for (int i = 0; i < GRIDSIZE; i++) {
                    noOfRepetitionsSG[i] = 0;
                }
                
            }
        }
        
        return wasRuleViolated;
        
    }
    
    /**
    * Inputs: int userValInt
    * Outputs: boolean
    * Description: checking if the user entered a value that wasn't a number b/w 1-9
    */
    @Override
    public boolean checkingIllegalCharacter(int userValInt) {
        if (!(userValInt < 10 && userValInt > 0 || userValInt == -16)) {
            PennDraw.setFontSize(16);
            PennDraw.setPenColor(PennDraw.RED);
            PennDraw.text(0.5, 1.1, "You must enter a value between 1 and 9.");
            return true;
            } else {
            return false;
        }
    }
    
    /**
    * Inputs: N/A
    * Outputs: void
    * Description: drawing the entire sudoku board
    */
    @Override
    public void draw() {
        PennDraw.clear(PennDraw.WHITE); //sudoku board color
        PennDraw.setPenColor(PennDraw.BLACK); //rules message color
        
        //this scale is set to create space for certain messages to be displayed
        PennDraw.setYscale(-0.25, 1.25);
        
        //displaying rules of the game on the UI
        PennDraw.setFontBold();
        PennDraw.setFontSize(12);
        PennDraw.text(0.5, -0.05, "RULES");
        PennDraw.setFontPlain();
        PennDraw.text(0.5, -0.1, "Click the mouse at the box you want to input" +
        " a value.");
        PennDraw.text(0.5, -0.15, "Then, type a value between 1 and 9.");
        PennDraw.text(0.5, -0.2, "To replace, click again at the box.");
        
        PennDraw.setPenColor(PennDraw.BLUE); //number and lines color
        
        //drawing the subGrid columns and rows (thick)
        PennDraw.setPenRadius(0.005);
        for (double i = 0; i <= 1; i += 1d / 3d) {
            PennDraw.line(0, i, 1, i);
            PennDraw.line(i, 0, i, 1);
        }
        
        //drawing the grid columns and rows (thin)
        PennDraw.setPenRadius(0.001);
        for (double i = 0; i <= 1; i += 1d / 9d) {
            PennDraw.line(0, i, 1, i);
            PennDraw.line(i, 0, i, 1);
        }
        
        //numbers drawn black (entered by the user) and blue (original sudoku)
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE; column++) {
                if ((puzzleArray[row][column]).wasItUserEntered()) {
                    PennDraw.setPenColor(PennDraw.BLACK);
                }
                (puzzleArray[row][column]).drawNumber();
                PennDraw.setPenColor(PennDraw.BLUE);
            }
        }
        
    }
    
    /**
    * Inputs: int colPressed, int rowPressed, double xPress, double yPress
    * Outputs: integer array
    * Description: figures out which row and column was pressed
    */
    @Override
    public int[] figuringOutRowsColumns(double xPress, double yPress, int
    rowPressed, int colPressed) {
        int[] rowColArray = new int[2];
        //modifies colPressed, rowPressed variables
        for (int i = 0; i < GRIDSIZE; i++) {
            if ((xPress > (i / 9d)) && (xPress < (i + 1) / 9d)) {
                colPressed = i;
            }
        }
        
        for (int j = 0; j < GRIDSIZE; j++) {
            if ((yPress > ((8 - j) / 9d)) && (yPress < ((8 - j) + 1) / 9d)) {
                rowPressed = j;
            }
        }
        rowColArray[0] = rowPressed;
        rowColArray[1] = colPressed;
        
        return rowColArray;
        
    }
    
    /**
    * Inputs: double xPress, double yPress, int rowPressed, int colPressed
    * Outputs: void
    * Description: allows user to input a value into a box
    */
    @Override
    public void inputValInBox(double xPress, double yPress, int rowPressed, int
    colPressed) {
        int[] rowColArray = new int[2];
        rowColArray = figuringOutRowsColumns(xPress, yPress, rowPressed, colPressed);
        rowPressed = rowColArray[0];
        colPressed = rowColArray[1];
        
        //checking if clicked box was whitespace or already has replacable value
        if ((puzzleArray[rowPressed][colPressed]).getValue() == -16 ||
        (puzzleArray[rowPressed][colPressed]).wasItUserEntered()) {
            while (true) {
                //in case of 2 or more consecutive mouse clicks
                if (PennDraw.mousePressed()) {
                    xPress = PennDraw.mouseX();
                    yPress = PennDraw.mouseY();
                    rowColArray = figuringOutRowsColumns(xPress, yPress, rowPressed,
                    colPressed);
                    rowPressed = rowColArray[0];
                    colPressed = rowColArray[1];
                    
                }
                
                //if a key was typed, enter that value
                if (PennDraw.hasNextKeyTyped()) {
                    //to replace the value of a box
                    if ((puzzleArray[rowPressed][colPressed]).wasItUserEntered()) {
                        Box k = new Box(rowPressed, colPressed, -16, true);
                        puzzleArray[rowPressed][colPressed] = k;
                        draw();
                    }
                    
                    //loading the key that was pressed, as an integer
                    String userVal = "";
                    userVal += PennDraw.nextKeyTyped();
                    int userValInt = (int) userVal.charAt(0) - 48;
                    
                    //checking if the number that was inputted by the user is valid
                    boolean checkingIllegalCharacterWasRun = false;
                    while (checkingIllegalCharacter(userValInt)) {
                        //allows user to enter a valid value instead
                        if (PennDraw.hasNextKeyTyped()) {
                            userVal = "";
                            userVal += PennDraw.nextKeyTyped();
                            userValInt = (int) userVal.charAt(0) - 48;
                            
                            //for user to click away to new box and enter an value
                            } else if (PennDraw.mousePressed()) {
                            draw();
                            xPress = PennDraw.mouseX();
                            yPress = PennDraw.mouseY();
                            inputValInBox(xPress, yPress, rowPressed, colPressed);
                            return;
                        }
                        checkingIllegalCharacterWasRun = true;
                    }
                    
                    //if the illegal character message showed up, deleting it
                    if (checkingIllegalCharacterWasRun) {
                        Box k = new Box(rowPressed, colPressed, -16, true);
                        puzzleArray[rowPressed][colPressed] = k;
                        draw();
                    }
                    
                    //adding the valid user entered value to the sudoku
                    Box j = new Box(rowPressed, colPressed, userValInt, true);
                    puzzleArray[rowPressed][colPressed] = j;
                    
                    //checking that the user entered value isn't repeated
                    if (checkLaterRuleViolation()) {
                        //draws the number that violates the rule in red
                        PennDraw.setPenColor(PennDraw.RED);
                        (puzzleArray[rowPressed][colPressed]).drawNumber();
                        while (true) {
                            //to remove incorrect value
                            if (PennDraw.mousePressed() ||
                            PennDraw.hasNextKeyTyped()) {
                                //gives the box a whitespace value
                                Box l = new Box(rowPressed, colPressed, -16, true);
                                puzzleArray[rowPressed][colPressed] = l;
                                draw();
                                return;
                            }
                        }
                        
                        } else {
                        PennDraw.setPenColor(PennDraw.BLACK);
                        (puzzleArray[rowPressed][colPressed]).drawNumber();
                        
                        //if there is no rule violation, check if the game is won
                        if (checkIfWin()) {
                            PennDraw.setFontBold();
                            PennDraw.setFontSize(40);
                            PennDraw.text(0.5, 1.1, "You've won!");
                            return;
                        }
                    }
                    
                }
                
            }
            
        }
    }
    
    /**
    * Inputs: N/A
    * Outputs: boolean
    * Description: checks if the user has won
    */
    @Override
    public boolean checkIfWin() {
        boolean allBoxHasValue = true;
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int column = 0; column < GRIDSIZE; column++) {
                //checks if each box isn't a whitespace
                allBoxHasValue = ((puzzleArray[row][column]).getValue() != -16) &&
                allBoxHasValue;
            }
        }
        //checks if each box isn't a whitespace and also doesn't violate any rules
        return allBoxHasValue && !(checkLaterRuleViolation());
    }
}
