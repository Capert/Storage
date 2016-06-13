package com.edisoft.education.provider.alexeytr.gui.storage;

import com.edisoft.education.core.Observable;
import com.edisoft.education.core.Observer;
import com.edisoft.education.core.storage.ObservableStorageInterface;
import com.edisoft.education.core.storage.StorageFileInterface;

import javax.swing.*;

/**
 * Created by Capert
 */
public class StorageFrame extends JFrame implements Observer {

    private JTextArea textArea;

    public StorageFrame() {
        super("Наблюдатель");
        textArea = new JTextArea();
        add(textArea);
        setSize(400, 400);
        setVisible(true);
    }

    @Override
    public void update(ObservableStorageInterface observable, Object arg) {
        if (observable instanceof ObservableStorageInterface) {
            ObservableStorageInterface storage = observable;
            String name = "";
            for (StorageFileInterface f : storage.getFiles()) {
                name += f.getName() + "\n";
            }
            textArea.setText(name);
        }
    }
}
