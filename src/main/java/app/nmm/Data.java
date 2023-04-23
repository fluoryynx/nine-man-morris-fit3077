package app.nmm;

import javafx.util.Pair;

/**
 * Data static class
 * A payload class. At any given time the data can be saved and passed to other controller
 */
public class Data {
    public static Pair<Integer, String> winner;
    public static Pair<Integer, String> loser;
    public static String mode;

    public Data (Pair<Integer, String> winner, Pair<Integer, String> loser, String mode){
        Data.winner = winner;
        Data.loser = loser;
        Data.mode = mode;
    }


    public static Pair<Integer, String> getWinner(){
        return winner;
    }

    public static Pair<Integer, String> getLoser(){
        return loser;
    }

    public static String getMode(){
        return mode;
    }

    @Override
    public String toString() {
        return "Data{" +
                "winner=" + winner +
                ", loser=" + loser +
                '}';
    }
}
