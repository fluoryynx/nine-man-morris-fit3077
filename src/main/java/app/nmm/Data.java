package app.nmm;

import javafx.util.Pair;

public class Data {
    private Pair<Integer, String> winner;
    private Pair<Integer, String> loser;

    public Data (Pair<Integer, String> winner, Pair<Integer, String> loser){
        this.winner = winner;
        this.loser = loser;
    }

    public Pair<Integer, String> getWinner(){
        return winner;
    }

    public Pair<Integer, String> getLoser(){
        return loser;
    }

    @Override
    public String toString() {
        return "Data{" +
                "winner=" + winner +
                ", loser=" + loser +
                '}';
    }
}
