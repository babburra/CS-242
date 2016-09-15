package GUI;

import Pieces.Logic;
import Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by yutong on 9/14/16.
 */
public class Game {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private List<ChessPiece> pieces;
    private Logic logic;
    private final static int Chess_Size = 80;
    private final static Dimension OuterDimension = new Dimension(640, 640);
    private final static Dimension BoardDimension = new Dimension(640, 640);

    /**
     * constructor of the game
     */
    public Game(){
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setResizable(false);
        this.gameFrame.setSize(OuterDimension);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setVisible(true);
        this.gameFrame.setLocation(100, 100);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.logic = new Logic();
        this.pieces = new ArrayList<ChessPiece>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece p = logic.getPiece(i, j);
                if (p != null)
                    pieces.add(new ChessPiece(p));
            }
        }
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
    }

    private class BoardPanel extends JPanel{

        BoardPanel(){
            super(new GridLayout(8,8));
            setLocation(0,0);
            setBackground(Color.WHITE);
            setPreferredSize(BoardDimension);
            validate();

        }

        /**
         * draw the chessboard and chess pieces
         * @param g current graphics
         */
        @Override
        public void paintComponent(Graphics g) {
            tileColor(g);
            for (ChessPiece p : pieces) {
                drawPiece(g, p);
            }
        }

        /**
         *
         * @param g current graphics
         * @param p target piece to be drawn
         */
        private void drawPiece(Graphics g, ChessPiece p) {
            int cur_x = (7-p.getLocation().getRow()) * Chess_Size;
            int cur_y = (p.getLocation().getCol()) * Chess_Size;
            g.drawImage(p.getImage(), cur_y, cur_x, null);
        }

        /**
         * color the tiles
         * @param g current graphics
         */
        private void tileColor(Graphics g){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if ((i % 2 != 0 && j % 2 == 0)) {
                        g.fillRect(i*Chess_Size, j*Chess_Size, Chess_Size, Chess_Size);
                    }

                }
            }
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if ((i % 2 == 0 && j % 2 != 0)) {
                        g.fillRect(i*Chess_Size, j*Chess_Size, Chess_Size, Chess_Size);
                    }
                }
            }
        }

    }
}
