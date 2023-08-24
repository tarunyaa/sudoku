/*  Name: Tarunyaa Sivakumar
*   PennKey: tarunyaa
*   Recitation: 203
*
*   Executions: java SudokuDemo <fileName.txt>
*
*   Description:
*   Class that contains the main method. This class demonstrates an example of a
*   sudoku game and it calls upon methods and objects in the Sudoku and box class.
*/

public class SudokuDemo {
    public static void main(String[] args) {
        //takes fileName as command-line input
        Sudoku currSudoku = new Sudoku(args[0]);
        currSudoku.loadPuzzle();
        currSudoku.checkInitialRuleViolation();
        currSudoku.draw();

        //while the game hasn't been won
        while (!(currSudoku.checkIfWin())) {
            
            //is a mouse is pressed, the location of press is registered 
            if (PennDraw.mousePressed()) { 
                int colPressed = 0;
                int rowPressed = 0;
                double xPress = PennDraw.mouseX();
                double yPress = PennDraw.mouseY();
                //then a value is inputted in that location
                currSudoku.inputValInBox(xPress, yPress, rowPressed, colPressed);
                
            }
        }
        
    }
}

