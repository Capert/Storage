package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */
public interface State {

    public void create();
    public void sign();
    public void send();
    public void read();
    public void accept();
    public void archive();
    public void update();
    public void delete();
    public void reject();
    public void store();
    public void load();

}


