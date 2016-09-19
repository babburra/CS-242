package GUI;

import Pieces.Logic;
import Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by yutong on 9/18/16.
 */
public class GameGUI {
    
    public GameGUI(){
        JFrame gameBoard = new JFrame();
        java.util.List<ChessPiece> pieces = new ArrayList<>();
        Logic logic = new Logic();
        gameBoard.setTitle("Chess");
        gameBoard.setVisible(true);
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setLocation(100, 100);
        gameBoard.setSize(800, 800);
        gameBoard.setLayout(new GridLayout(8,8));
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece p = logic.getPiece(i, j);
                if (p != null)
                    pieces.add(new ChessPiece(p));
            }
        }
        JPanel grid[][] = new JPanel[8][8];
        JButton button[][] = new JButton[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){

                button[i][j] = new JButton();
                button[i][j].setPreferredSize(new Dimension(100, 100));
                if(i % 2 == j % 2) {
                    button[i][j].setBackground(Color.white);
                }
                else {
                    button[i][j].setBackground(Color.lightGray);
                }

                grid[i][j] = new JPanel();
                grid[i][j].add(button[i][j]);
                gameBoard.add(grid[i][j]);
            }

        }
        for (ChessPiece p : pieces){
            int cur_x = 7-p.getLocation().getRow();
            int cur_y = p.getLocation().getCol();
            button[cur_x][cur_y].setIcon(new ImageIcon(p.getImage()));
        }
    }


}
