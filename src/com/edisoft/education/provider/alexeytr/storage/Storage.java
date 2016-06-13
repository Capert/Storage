package com.edisoft.education.provider.alexeytr.storage;

import com.edisoft.education.core.Observer;
import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.DocumentTypes;
import com.edisoft.education.core.document.processor.SignatureInterface;
import com.edisoft.education.core.storage.ObservableStorageInterface;
import com.edisoft.education.core.storage.StorageFileInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Capert
 */

public class Storage implements ObservableStorageInterface {
    private String base;
    private ArrayList<Observer> observers;

    public Storage() {
        observers = new ArrayList<>();
    }

    @Override
    public void initialize(String baseDirectory) throws RuntimeException {
        if (this.base!= null) {
            throw new RuntimeException("Хранилище уже проинициализовано!!!");
        }
        base = baseDirectory;
        notifyObservers();
    }

    @Override
    public void storeFile(StorageFileInterface file) throws RuntimeException {
        if (base == null)
            throw new RuntimeException("Хранилище не проинициализировано!!!");
        String fileName = file.getName();
        Path path = Paths.get(base, fileName);

        try {
            Files.createFile(path);
            Files.write(path, file.getContent());
            notifyObservers();
        } catch (IOException e) {
            throw new RuntimeException("Невозможно записать фаил!!!");
        }
    }


    @Override
    public void removeFile(StorageFileInterface file) throws RuntimeException {
        if (base == null)
            throw new RuntimeException("Хранилище не проинициализировано!!!");
        String fileName = file.getName();
        Path path = Paths.get(base, fileName);
        if (!Files.isRegularFile(path))
            throw new RuntimeException("Фаила не существует!!!");
        try {
            Files.delete(path);
            notifyObservers();
        } catch (IOException e) {
            throw new RuntimeException("Невозможно удалить фаил");
        }
    }

    @Override
    public StorageFileInterface getFileByName(String fileName) throws RuntimeException {
        if (base == null)
            throw new RuntimeException("Хранилище не проинициализировано!!!");
        Path path = Paths.get(base, fileName);
        StorageFileInterface file;
        try {
            file = new StorageFile();
            file.setName(fileName);
            file.setContent(Files.readAllBytes(path));

        } catch (NoSuchFileException e) {
            file = null;
        } catch (IOException e) {
            throw new RuntimeException("Невозможно прочитать фаил!!!");
        }
        return file;
    }

    @Override
    public Collection<StorageFileInterface> getFiles() throws RuntimeException {
        if (base == null)
            throw new RuntimeException("Хранилище не проинициализировано!!!");
        List<StorageFileInterface> listWithFileNames = new ArrayList<>();
        File f = new File(base);
        for (String file : f.list()) {
            if (Files.isRegularFile(Paths.get(base, file))) {
                listWithFileNames.add(getFileByName(file));
            }
        }
        return listWithFileNames;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update(this, null);
        }
    }
}
