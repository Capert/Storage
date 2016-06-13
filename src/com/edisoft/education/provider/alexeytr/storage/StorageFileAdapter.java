package com.edisoft.education.provider.alexeytr.storage;

import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.DocumentTypes;
import com.edisoft.education.core.document.processor.SignatureInterface;
import com.edisoft.education.core.storage.StorageFileInterface;

import java.util.List;

/**
 * Created by Capert
 */
public class StorageFileAdapter implements DocumentInterface, StorageFileInterface {
    private DocumentInterface doc;

    public StorageFileAdapter(DocumentInterface doc){
        this.doc = doc;
    }

    @Override
    public void setType(DocumentTypes type) {
        doc.setType(type);
    }

    @Override
    public DocumentTypes getType() {
        return doc.getType();
    }

    @Override
    public void setNumber(Long number) {
doc.setNumber(number);
    }

    @Override
    public Long getNumber() {
        return doc.getNumber();
    }

    @Override
    public void setData(String data) {
doc.setData(data);
    }

    @Override
    public String getData() {
        return doc.getData();
    }

    @Override
    public boolean isSigned() {
        return false;
    }

    @Override
    public void addSignature(SignatureInterface signature) {
doc.addSignature(signature);
    }

    @Override
    public boolean deleteSignature(SignatureInterface signature) {
        doc.deleteSignature(signature);
        return true;
    }

    @Override
    public List<SignatureInterface> getSignatures() {
        return doc.getSignatures();
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setContent(byte[] content) {

    }

    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    @Override
    public StorageFileInterface copy() {
        return null;
    }
}
