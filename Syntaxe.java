package syntaxe;

public class Syntaxe {
    String syntaxe;

    Syntaxe union;

    public Syntaxe (String n) {
        setSyntaxe(n);
    }

    public String getSyntaxe() {
        return syntaxe;
    }

    public void setSyntaxe(String syntaxe) {
        this.syntaxe = syntaxe;
    }

    /*public Syntaxe getPrevious() {
        return previous;
    }*/
    
    public Syntaxe getUnion() {
        return union;
    }

    public void setUnion(Syntaxe u) {
        this.union = u;
    }

    /*public void setPrevious(Syntaxe previous) {
        this.previous = previous;
    }*/
}