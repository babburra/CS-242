package Main;

/**
 * Created by yutong on 9/7/16.
 */
public class Location{
    private int row, col;

    public Location(){
        row = 0;
        col = 0;
    }

    public void setLocation(int i, int j){
        row = i;
        col = j;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    public boolean equals(Object O){
        if (O instanceof Location){
            Location loc = (Location) O;
            return (this.row == loc.row) && (this.col == loc.col);
        }
        return false;
    }
}
