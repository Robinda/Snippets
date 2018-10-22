package com.journaldev.recyclerviewdividersandselectors;

/**
 * Created by anupamchugh on 12/08/17.
 */

public class Model {

    String text;
    boolean colored;

    public Model(String text, boolean colored) {
        this.text = text;
        this.colored = colored;
    }

    public Boolean getColored() {
        return this.colored;
    }

    public void setColored(Boolean isColored) {
        this.colored = isColored;
    }
}
