package com.edisoft.education.provider.alexeytr.storage;

import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.core.storage.StorageInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Capert
 */
public class StorageDecoratorStat implements StorageInterface {
    private final StorageInterface storage;

    private Map<Methods, Map<Integer, Integer>> stat;
    public static final int countSuc = 1;
    public static final int countAll = 0;

    public StorageDecoratorStat(StorageInterface storage) {
        this.storage = storage;
        startStatistic();
    }


    public enum Methods {INITIALIZE, STORE_FILE, REMOVE_FILE, GET_FILE_BY_NAME, GET_FILES}


    @Override
    public void initialize(String baseDirectory) throws RuntimeException {
        addStatAll(Methods.INITIALIZE);
        try {
            storage.initialize(baseDirectory);
            addStatSuc(Methods.INITIALIZE);
            printStat();
        } catch (Exception e){
            printStat();
            throw e;
        }
    }


    @Override
    public void storeFile(StorageFileInterface file) throws RuntimeException {
        addStatAll(Methods.STORE_FILE);
        try {
            storage.storeFile(file);
            addStatSuc(Methods.STORE_FILE);
            printStat();
        }catch (Exception e){
            printStat();
            throw e;
        }
    }

    @Override
    public void removeFile(StorageFileInterface file) throws RuntimeException {
        addStatAll(Methods.REMOVE_FILE);
        try {
            storage.removeFile(file);
            addStatSuc(Methods.REMOVE_FILE);
            printStat();
        } catch (Exception e){
            printStat();
            throw e;
        }
    }

    @Override
    public StorageFileInterface getFileByName(String fileName) throws RuntimeException {
        addStatAll(Methods.GET_FILE_BY_NAME);
        try {
            StorageFileInterface file = storage.getFileByName(fileName);
            addStatSuc(Methods.GET_FILE_BY_NAME);
            printStat();
            return file;
        } catch (Exception e){
            printStat();
            throw e;
        }
    }


    @Override
    public Collection<StorageFileInterface> getFiles() throws RuntimeException {
        addStatAll(Methods.GET_FILES);
        try {
            Collection<StorageFileInterface> files = storage.getFiles();
            addStatSuc(Methods.GET_FILES);
            printStat();
            return files;
        } catch (Exception e){
            printStat();
            throw e;
        }

    }

    private void startStatistic() {
        stat = new HashMap<>();
        stat.put(Methods.INITIALIZE, newStatMap());
        stat.put(Methods.STORE_FILE, newStatMap());
        stat.put(Methods.REMOVE_FILE, newStatMap());
        stat.put(Methods.GET_FILE_BY_NAME, newStatMap());
        stat.put(Methods.GET_FILES, newStatMap());
    }

    private Map<Integer, Integer> newStatMap() {
        Map<Integer, Integer> statMap = new HashMap<>();
        statMap.put(countAll, 0);
        statMap.put(countSuc, 0);
        return statMap;
    }

    private void addStatAll(Methods meth) {
        stat.get(meth).put(countAll, stat.get(meth).get(countAll) + 1);
    }

    private void addStatSuc(Methods meth) {
        stat.get(meth).put(countSuc, stat.get(meth).get(countSuc) + 1);
    }

    public void printStat() {
        System.out.println("------------------------------------------");
        for (Methods meth : stat.keySet()) {
            Map<Integer, Integer> st = stat.get(meth);
            int all = st.get(countAll);
            int suc = st.get(countSuc);
            String result = "%1$s()...........%2$d/%3$d(%4$d";
            if (all > 0) {
                result = String.format(result, meth.toString(), suc, all, suc * 100 / all) + "%)";
        } else {
                result = String.format(result, meth.toString(), suc, all, 0 ) + "%)";
            }
            System.out.println(result);
        }
        System.out.println("------------------------------------------");
    }
}