/*  Name: Tarunyaa Sivakumar
*   PennKey: tarunyaa
*   Recitation: 203
*
*   Executions: N/A
*
*   Description:
*   Interface that contains methods relating to allowing the user to interact with
*   the game. This includes allowing the user to input a value in a box, ensuring
*   that the user doesn't violate any rules, and checking if the user has won the
*   game. Two helper functions are also created in this interface.
*/

public interface EventListener {
    //methods
    public void inputValInBox(double xPress, double yPress, int rowPressed,
    int colPressed);
    public boolean checkLaterRuleViolation();
    public boolean checkIfWin();
    
    //helper functions
    public int[] figuringOutRowsColumns(double xPress, double yPress,
    int rowPressed, int colPressed);
    public boolean checkingIllegalCharacter(int userValInt);
}
