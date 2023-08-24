/*  Name: Tarunyaa Sivakumar
*   PennKey: tarunyaa
*   Recitation: 203
*
*   Executions: N/A
*
*   Description:
*   Creates a class that represents boxes in the sudoku. Each box object has the
*   attributes of it's value, row and column in the sudoku as well as whether it
*   was part of the orignal sudoku or user entered.
*/

public class Box {
    private int boxRow;
    private int boxCol;
    private int boxVal;
    private boolean wasUserEntered;
    
    //constructor
    public Box(int boxRow, int boxCol, int boxVal, boolean wasUserEntered) {
        this.boxRow = boxRow;
        this.boxCol = boxCol;
        this.boxVal = boxVal;
        this.wasUserEntered = wasUserEntered;
    }
    
    /**
    * Inputs: N/A
    * Outputs: boolean
    * Description: getter function of wasItUserEntered field
    */
    public boolean wasItUserEntered() {
        return wasUserEntered;
    }
    
    /**
    * Inputs: N/A
    * Outputs: int
    * Description: getter function of boxVal field
    */
    public int getValue() {
        return boxVal;
    }
    
    /**
    * Inputs: N/A
    * Outputs: void
    * Description: draws a number in certain row and column
    */
    public void drawNumber() {
        String boxValStr = ""; //check if its fine to initialise here
        if (boxVal != -16) {
            boxValStr += boxVal;
            PennDraw.setFontSize(30);
            PennDraw.text(boxCol / 9d + 1d / 18d, 1 - (boxRow / 9d +  1d / 18d),
            boxValStr);
        }
        
    }
    
}
