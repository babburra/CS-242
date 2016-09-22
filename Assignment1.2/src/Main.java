import GUI.GameGUI;

import javax.swing.*;

/**
 * Created by yutong on 9/14/16.
 */
public class Main {
    public static void main(String []args){
        boolean cp = false;
        int n = JOptionPane.showConfirmDialog(
                null,
                "Use Customise Pieces?",
                "",
                JOptionPane.YES_NO_OPTION);
        if (n == 0)
            cp = true;
        GameGUI game = new GameGUI(cp);
    }
}
