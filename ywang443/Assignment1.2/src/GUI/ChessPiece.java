package GUI;
import Pieces.Location;
import Pieces.Piece;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yutong on 9/14/16.
 */
public class ChessPiece {
    private Piece piece;
    private ChessPiece(){

    }
    public ChessPiece(Piece piece) {
        this.piece = piece;
    }
    public Location getLocation() {
        return piece.getLocation();
    }
    private static final Map<String, BufferedImage> imageMap = new HashMap<>();

    /**
     *
     * @param fileName filename of a piece
     * @return a stream of bufferedimage of a piece
     */
    private static BufferedImage readImage(String fileName) {
        try {
            return ImageIO.read(new ChessPiece().getClass().getResourceAsStream("../Images/" + fileName + ".png"));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     *
     * @param piece target piece
     * @return bufferedimage of a piece
     */
    private static BufferedImage getImage(Piece piece) {
        String className = piece.getClass().getSimpleName();
        String color = "";
        if (piece.getOwner())
            color = "_white";
        else
            color = "_black";
        String fileName = className + color;

        if (!imageMap.containsKey(fileName)) {
            imageMap.put(fileName, readImage(fileName));
        }
        //System.out.print(fileName);
        return imageMap.get(fileName);
    }

    /**
     *
     * @return bufferedimage of a piece
     */
    public BufferedImage getImage(){
        return getImage(piece);
    }

}
