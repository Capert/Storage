package com.edisoft.education.provider.alexeytr.storage;

import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.storage.StorageFileInterface;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Capert
 */

public class StorageFile implements StorageFileInterface {
    private String filename;
    private byte[] cont;

    @Override
    public void setName(String name) {
        filename = name;
    }

    @Override
    public String getName() {
        return filename;
    }

    @Override
    public void setContent(byte[] content) {
        cont = content;
    }

    @Override
    public byte[] getContent() {
        return cont;
    }

    @Override
    public StorageFileInterface copy() {
        StorageFile file = new StorageFile();
        file.setName(filename);
        file.setContent(Arrays.copyOf(cont, cont.length));

        return file;
    }
}
