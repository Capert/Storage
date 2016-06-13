package com.edisoft.education.provider.alexeytr.document.processor;

import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.DocumentTypes;
import com.edisoft.education.core.document.processor.SignatureInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Capert
 */
public class Document implements DocumentInterface {
    private DocumentTypes type;
    private Long number;
    private String data;
    private ArrayList<SignatureInterface> signatures;

    public Document() {
        signatures = new ArrayList<>();
    }

    @Override
    public void setType(DocumentTypes type) {
        this.type = type;
    }

    @Override
    public DocumentTypes getType() {
        return type;
    }

    @Override
    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public Long getNumber() {
        return number;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public boolean isSigned() {
        return !signatures.isEmpty();
    }

    @Override
    public void addSignature(SignatureInterface signature) {
        signatures.add(signature);
    }

    @Override
    public boolean deleteSignature(SignatureInterface signature) {
        if (signatures.contains(signature)) {
            signatures.remove(signature);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<SignatureInterface> getSignatures() {
        return signatures;
    }
}
