package app.nmm.Logic.Token;

public class Token {

    private Boolean isMillHorizontal;
    private Boolean isMillVertical;
    private String colour;

    /**
     * construtor
     * @param colour
     */
    public Token(String colour){
        this.isMillHorizontal = false;
        this.isMillVertical = false;
        this.colour= colour;
    }

    // getters and setters
    public Boolean getIsMillHorizontal(){
        return this.isMillHorizontal;
    }

    public Boolean getMillVertical() {return isMillVertical;}

    public void setMillHorizontal(Boolean millHorizontal) {
        isMillHorizontal = millHorizontal;
    }

    public void setMillVertical(Boolean millVertical) {
        isMillVertical = millVertical;
    }
    public Boolean getIsMill(){return  isMillHorizontal || isMillVertical; }
    public String getColour(){
        return this.colour;
    }



}

