package com.edisoft.education.provider.alexeytr.storage;

import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.core.storage.StorageInterface;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by Capert
 */

public class StorageDecorator implements StorageInterface {
    private final StorageInterface storage;

    public StorageDecorator(StorageInterface storage) {
        this.storage = storage;
    }

    @Override
    public void initialize(String baseDirectory) throws RuntimeException {
        if (baseDirectory == null)
            throw new RuntimeException("Неверное имя!!!");
        File basedir = new File(baseDirectory);
        if (!basedir.isDirectory())
            throw new RuntimeException("Каталог не существует!!!");

        Path path = Paths.get(baseDirectory);
        if (path.isAbsolute() == false)
            throw new RuntimeException("Указан неабсолютный путь");
        if (path.getNameCount() < 3)
            throw new RuntimeException("Недостаточный уровень вложенности");
        if (baseDirectory.contains("null"))
            throw new RuntimeException("Недопускается имя каталога null");
        if (baseDirectory.length() > 200)
            throw new RuntimeException("Слишком большой путь, допускается не более 200 символов");
        if (checkPathName(baseDirectory) == false)
            throw new RuntimeException("Путь содержит недопустимые символы (допустимы латинских буквы любого регистра, цифры, -, _");
        storage.initialize(baseDirectory);
    }

    @Override
    public void storeFile(StorageFileInterface file) throws RuntimeException {
        if (file == null)
            throw new RuntimeException("Неверный аргумент!!!");
        if (file.getName() == null)
            throw new RuntimeException("Фаила с таким именем не существует");

        if (!file.getName().contains("."))
            throw new RuntimeException("Невозможно сохранить фаил без расширения");
        if (file.getName().endsWith("log") | file.getName().endsWith("backup"))
            throw new RuntimeException("Невозможно сохранять фаилы с расширениями log и backup");
        if (file.getName().contains("null"))
            throw new RuntimeException("Недопустимо сохранение файла с именем null");
        if (file.getName().length() > 50)
            throw new RuntimeException("Длина имени файла не должна превышать 50 символов");
        if (checkFileName(file.getName()) == false)
            throw new RuntimeException("имя файла может состоять только из латинских букв любого регистра, цифр, -, _");
        storage.storeFile(file);
    }

    @Override
    public void removeFile(StorageFileInterface file) throws RuntimeException {
        if (file == null)
            throw new RuntimeException("Неверный аргумент!!!");
        if (file.getName() == null)
            throw new RuntimeException("Фаила с таким именем не существует");
        if (file.getName().endsWith("log") | file.getName().endsWith("backup"))
            throw new RuntimeException("Невозможно удалять фаилы с расширениями log или backup");
        if (file.getName().contains("null"))
            throw new RuntimeException("Недопустимо удаление файла с именем null");
        if (file.getName().length() > 50)
            throw new RuntimeException("Длина имени файла не должна превышать 50 символов");
        if (checkFileName(file.getName()) == false)
            throw new RuntimeException("имя файла может состоять только из латинских букв любого регистра, цифр, -, _");
        storage.removeFile(file);
    }

    @Override
    public StorageFileInterface getFileByName(String fileName) throws RuntimeException {
        if (fileName == null)
            throw new RuntimeException("Неверное имя!!!");

        if (fileName.contains("null"))
            throw new RuntimeException("Недопустимо обращение к файлу с именем null");
        if (fileName.length() > 50)
            throw new RuntimeException("Длина имени файла не должна превышать 50 символов");
        if (checkFileName(fileName) == false)
            throw new RuntimeException("имя файла может состоять только из латинских букв любого регистра, цифр, -, _");
        return storage.getFileByName(fileName);
    }

    @Override
    public Collection<StorageFileInterface> getFiles() throws RuntimeException {
        return storage.getFiles();
    }

    public static boolean checkPathName(String userNameString) {

        char[] symbols = userNameString.toCharArray();
        String validationString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-:\\/";

        for (char c : symbols) {
            if (validationString.indexOf(c) == -1)
                return false;
        }

        return true;
    }

    public static boolean checkFileName(String userNameString) {

        char[] symbols = userNameString.toCharArray();
        String validationString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-.";

        for (char c : symbols) {
            if (validationString.indexOf(c) == -1) return false;
        }

        return true;
    }
}
