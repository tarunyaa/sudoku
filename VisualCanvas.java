/*  Name: Tarunyaa Sivakumar
*   PennKey: tarunyaa
*   Recitation: 203
*
*   Executions: N/A
*
*   Description:
*   Interface that contains constants and methods that relate to the visual aspects
*   of the game. This includes loading the game, ensuring that the loaded game
*   doesn't break its rules and drawing the game on the UI.
*/

public interface VisualCanvas {
    //constants
    public static final int GRIDSIZE = 9;
    public static final int SUBGRIDSIZE = 3;
    
    //methods
    public void loadPuzzle();
    public void checkInitialRuleViolation();
    public void draw();
}
