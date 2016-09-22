package GUI;

import Pieces.Location;
import Pieces.Logic;
import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by yutong on 9/18/16.
 */
public class GameGUI {
    private int score_white = 0;
    private int score_black = 0;
    private Logic logic;
    private boolean turn = true;
    private JFrame gameBoard;
    private JMenuBar menuBar = new JMenuBar();
    private boolean customize;
    private JButton moved;
    private JButton dest;
    private Icon oldIcon;
    public GameGUI(boolean cp){
        customize = cp;
        populateMenuBar(menuBar);
        logic = new Logic(cp);
        gameBoard = new JFrame();
        java.util.List<ChessPiece> pieces = new ArrayList<>();
        gameBoard.setJMenuBar(menuBar);
        gameBoard.setTitle("Chess");
        gameBoard.setVisible(true);
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setResizable(false);
        gameBoard.setLocation(0, 0);
        gameBoard.setSize(800, 800);
        gameBoard.setLayout(new GridLayout(8, 8));
        ActionListener prsBtn = new btnPressed();

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
                button[i][j].putClientProperty("row", i);
                button[i][j].putClientProperty("col", j);
                if(i % 2 == j % 2) {
                    button[i][j].setBackground(Color.white);
                }
                else {
                    button[i][j].setBackground(Color.lightGray);
                }
                grid[i][j] = new JPanel();
                grid[i][j].add(button[i][j]);
                button[i][j].addActionListener(prsBtn);
                gameBoard.add(grid[i][j]);
            }
        }
        for (ChessPiece p : pieces){
            int cur_x = p.getLocation().getRow();
            int cur_y = p.getLocation().getCol();
            button[cur_x][cur_y].setIcon(new ImageIcon(p.getImage()));
            button[cur_x][cur_y].putClientProperty("piece", p.getPiece());
        }
    }

    /**
     * populate the menubar
     * @param menuBar menubar about to be populated
     */
    private void populateMenuBar(JMenuBar menuBar) {
        menuBar.add(createMenu());
    }

    /**
     * create the menubar
     * @return return the created menubar
     */
    private JMenu createMenu() {
        final JMenu menu = new JMenu("Menu");
        final JMenuItem newGame = new JMenuItem("New Game");
        final JMenuItem forfeit = new JMenuItem("Forfeit");
        final JMenuItem undo = new JMenuItem("Undo");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.setVisible(false);
                GameGUI game = new GameGUI(customize);
            }
        });
        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logic.board.setState(Board.State.stalemate);
                gameBoard.setFocusable(false);
            }
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Undo();
                int i = (int) moved.getClientProperty("row");
                int j = (int) moved.getClientProperty("col");
                moved.putClientProperty("piece", logic.getPiece(i, j));
                moved.setIcon(oldIcon);
                dest.setIcon(null);
            }
        });

        menu.add(newGame);
        menu.add(forfeit);
        menu.add(undo);
        return menu;
    }

    /**
     * Undo a step
     */
    private void Undo() {

        turn = !turn;
        logic.getBoard().undo();
    }

    private JButton selected = null;

    /**
     * action listener for the board
     */
    private class btnPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            //System.out.println("pressed " + btn.getClientProperty("row") + "," + btn.getClientProperty("col"));
            //pressed an empty button
            if (selected == null && btn.getClientProperty("piece") == null){
                return;
            }
            if (selected == null && btn.getClientProperty("piece") != null){
                Piece piece = (Piece) btn.getClientProperty("piece");
                // not one's turn
                if (piece.getOwner() != turn)
                    return;
                selected = btn;
            }
            else{
                int cur_x = (int) btn.getClientProperty("row");
                int cur_y = (int) btn.getClientProperty("col");
                Piece p = (Piece) selected.getClientProperty("piece");
                if (p == null) {
                    System.out.print("null");
                }
                int x = p.getLocation().getRow();
                int y = p.getLocation().getCol();
                if (p != null && logic.getBoard().movePiece(p, new Location(cur_x, cur_y), p.getOwner())) {
                    System.out.println(p.getClass().getSimpleName() + " (" + x + "," + y + ")->(" + cur_x + "," + cur_y + ")");
                    btn.putClientProperty("row", cur_x);
                    btn.putClientProperty("col", cur_y);
                    btn.putClientProperty("piece", p);
                    oldIcon = selected.getIcon();
                    btn.setIcon(oldIcon);
                    selected.setIcon(null);
                    dest = btn;
                    moved = selected;
                    turn = !turn;

                    if (logic.getBoard().getState() == Board.State.checkmate) {
                        //System.out.println("Checkmate!");
                        if (turn)
                            score_black++;
                        else
                            score_white++;
                        JOptionPane.showMessageDialog(null, "Checkmate!\n"+"Score:" + score_white + ":" + score_black);
                        //System.out.println("Score:" + score_white + ":" + score_black);
                        gameBoard.setFocusable(false);
                        return;
                    }
                    if (logic.getBoard().getState() == Board.State.stalemate){
                        JOptionPane.showMessageDialog(null, "Stalemate!\n"+"Score:" + score_white + ":" + score_black);
                        gameBoard.setFocusable(false);
                        return;
                    }
                }
                selected = null;
            }
        }
    }
}
