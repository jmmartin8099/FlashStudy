package com.example.jmmar.flashstudy;

/**
 * Created by jmmar on 12/28/2017.
 */

public class IndexCard {
    private String term;
    private String def;

    public IndexCard(){
        term = null;
        def = null;
    }

    public IndexCard(String term, String def) {
        this.term = term;
        this.def = def;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    @Override
    public String toString() {
        return "Term: " + term + "\nDefiniton: " + def;
    }
}
