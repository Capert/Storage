package com.edisoft.education.provider.alexeytr.storage;

import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.DocumentTypes;
import com.edisoft.education.core.document.processor.SignatureInterface;
import com.edisoft.education.core.storage.StorageControllerInterface;
import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.core.storage.StorageInterface;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Capert
 */

public class StorageAdapter implements StorageControllerInterface {

    private StorageInterface storage;

    public StorageAdapter(StorageInterface storage) {
        this.storage = storage;
    }

    @Override
    public Collection<String> findFilesByExtension(String extension) {
        Collection<String> collection = new ArrayList<>();
        for (StorageFileInterface file : storage.getFiles()) {
            if (file.getName().endsWith(extension))
                collection.add(file.getName());
        }
        return collection;
    }

    @Override
    public Collection<String> findFilesByNamePart(String namePart) {
        Collection<String> collection = new ArrayList<>();
        for (StorageFileInterface file : storage.getFiles()) {
            if (file.getName().contains(namePart))
                collection.add(file.getName());
        }
        return collection;
    }

    @Override
    public Collection<String> findFilesByContentPart(String contentPart) {
        Collection<String> collection = new ArrayList<>();
        for (StorageFileInterface file : storage.getFiles()) {
            String content = new String(file.getContent());
            if (content.contains(contentPart))
                collection.add(file.getName());
        }
        return collection;
    }

    @Override
    public boolean createFile(String name, byte[] content) {
        StorageFileInterface file = new StorageFile();
        file.setName(name);
        file.setContent(content);
        try {
            storage.storeFile(file);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
            return false;
        }
    }

    @Override
    public byte[] getFileContent(String name) {
        StorageFileInterface file = new StorageFile();
        file.setName(name);
        try {
            file.setContent(storage.getFileByName(name).getContent());
            return file.getContent();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        }
        return null;
    }


    @Override

    public boolean updateFile(String name, byte[] newContent) {
        StorageFileInterface file = new StorageFile();
        file.setName(name);
        try {
            System.out.println(file.getContent());
            file.setContent(storage.getFileByName(name).getContent());
            System.out.println(file.getContent());
            file.setContent(newContent);
            System.out.println(file.getContent());
            deleteFile(name);
            storage.storeFile(file);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
            return false;
        }
    }

    @Override

    public boolean renameFile(String name, String newName) {
        StorageFileInterface file = new StorageFile();
        try {
            file.setName(storage.getFileByName(name).getName());
            file.setContent(storage.getFileByName(name).getContent());
            System.out.println(file.getName() + " " + file.getContent());
            file.setName(newName);
            deleteFile(name);
            storage.storeFile(file);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
            return false;
        }
    }

    @Override
    public boolean deleteFile(String name) {
        StorageFileInterface file = new StorageFile();
        file.setName(name);
        try {
            storage.removeFile(file);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
            return false;
        }
    }


    @Override
    public boolean copyFile(String name) {
        int num = 1;
        StorageFileInterface file = new StorageFile();
        file.setName(name);
        try {
            file.setContent(storage.getFileByName(name).getContent());
            StorageFileInterface fileCopy = file.copy();
            System.out.println(fileCopy.getName() + fileCopy.getContent());
            fileCopy.setName(name.replace(".", COPY_SUFF + "."));
            if (storage.getFileByName(fileCopy.getName()) != null) {
                while (storage.getFileByName(fileCopy.getName()) != null)
                    fileCopy.setName(name.replace(".", COPY_SUFF + "_" + num++ + "."));
                storage.storeFile(fileCopy);
            } else {
                storage.storeFile(fileCopy);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
            return false;
        }
    }

    @Override
    public boolean backupFile(String name) {
        StorageFileInterface file = new StorageFile();
        file.setName(name);
        try {
            file.setContent(storage.getFileByName(name).getContent());
            System.out.println(file.getName() + file.getContent());
            StorageFileInterface fileCopy = file.copy();
            System.out.println(fileCopy.getName() + fileCopy.getContent());
            fileCopy.setName(name.substring(0, name.indexOf(".")) + "." + BACKUP_EXTENSION);
            if (storage.getFileByName(fileCopy.getName()) != null) {
                deleteFile(fileCopy.getName());
                storage.storeFile(fileCopy);
            } else
                storage.storeFile(fileCopy);


            return true;
        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
            return false;
        }
    }

    @Override
    public Collection<String> getStoredFiles() {
        Collection<String> collection = new ArrayList<>();
        for (StorageFileInterface file : storage.getFiles()) {
            collection.add(file.getName());
        }
        return collection;
    }

    @Override
    public boolean cleanStorage() {
        for (StorageFileInterface file : storage.getFiles())
            try {
                storage.removeFile(file);
            } catch (Exception e) {
                System.out.println("Ошибка" + e);
            }
        return storage.getFiles().isEmpty();
    }
}
