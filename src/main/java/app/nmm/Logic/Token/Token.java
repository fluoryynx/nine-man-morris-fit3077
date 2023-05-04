package app.nmm.Logic.Token;

public class Token {

    private Boolean isMill;
    private String colour;

    /**
     * construtor
     * @param isMill
     * @param colour
     */
    public Token(Boolean isMill,String colour){
        this.isMill = false;
        this.colour= colour;
    }

    // getters and setters
    public Boolean getIsMill(){
        return this.isMill;
    }

    public String getColour(){
        return this.colour;
    }

    public void setIsMill(Boolean newIsMill){
        this.isMill=newIsMill;
    }

}

