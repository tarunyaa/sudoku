/**********************************************************************
 *  Project readme.txt template
 **********************************************************************/


Name: Tarunyaa
PennKey: tarunyaa


/**********************************************************************
 *  Please list all help, collaboration, and outside resources
 *  you used here. 
 *
 *  If you did not get any help in outside of TA office hours,
 *  and did not use any materials outside of the standard
 *  course materials and piazza, write the following statement below:
 *  "I did not receive any help outside of TA office hours.  I
 *  did not collaborate with anyone, and I did not use any
 *  resources beyond the standard course materials."
 **********************************************************************/

 I did not receive any help outside of TA office hours.  I
 did not collaborate with anyone, and I did not use any
 resources beyond the standard course materials.

/**********************************************************************
 *  How to run the program:            
 **********************************************************************/
 run the SudokuDemo class, there is a command line argument
 Execution: java SudokuDemo <fileName.txt>
 The text file should contain the sudoku that you want to solve

/**********************************************************************
 *  Any additional features you added beyond the assignment specifications
 **********************************************************************/
 
 N/A

/**********************************************************************
 *   Brief description of each file and its purpose                                    
 **********************************************************************/
 1. box.java:
    Creates a class that represents boxes in the sudoku. Each box object has the 
    attributes of it's value, row and column in the sudoku as well as whether it 
    was part of the orignal sudoku or user-entered.
    
    Purpose: Allows for each box in the sudoku to be an object, easy to instantiate
    and change the values of the box and see what value each box contains if 
    we know which row and column it's in.

 2. Sudoku.java
    Class that implements the interfaces, VisualCanvas and EventListener. 
    Contains the main methods used to build the Sudoku puzzle then enter values 
    into it, while outputting certain error messages and progressing the game 
    forward.
    
    Purpose: It's the class with all the methods that define how the game works.

 3. VisualCanvas.java
    Interface that contains constants and methods that relate to the visual aspects 
    of the game. This includes loading the game, ensuring that the loaded game 
    doesn't break its rules and drawing the game on the UI.
    
    Purpose: Visual aspects of the game.
 
 4. EventListener.java
    Interface that contains methods relating to allowing the user to interact with
    the game. This includes allowing the user to input a value in a box, ensuring
    that the user doesn't violate any rules, and checking if the user has won the 
    game. Two helper functions are also created in this interface.
    
    Purpose: Event handling mode of the game.

 5. SudokuDemo.java
    Class that contains the main method. This class demonstrates an example of a 
    sudoku game and it calls upon methods and objects in the Sudoku and box class.
    
    Purpose: To run an example Sudoku.

    Note: Two interfaces were used because it helps to have all the methods 
    together in one place. Moreover, if I were creating multiple games, the same
    methods could be used for it. 