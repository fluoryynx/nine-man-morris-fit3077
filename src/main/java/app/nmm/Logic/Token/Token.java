package app.nmm.Logic.Token;

public class Token {

    private Boolean isMill;
    private String id;

    public Token(Boolean isMill,String id){
        this.isMill = isMill;
        this.id= id;
    }

    public Boolean getIsMill(){
        return this.isMill;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String newId){
        this.id=newId;
    }

    public void setIsMill(Boolean newIsMill){
        this.isMill=newIsMill;
    }

}

