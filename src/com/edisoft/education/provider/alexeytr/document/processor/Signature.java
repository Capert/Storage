package com.edisoft.education.provider.alexeytr.document.processor;

import com.edisoft.education.core.document.processor.SignatureInterface;
/**
 * Created by Capert
 */
public class Signature implements SignatureInterface{
    private String signer;
    private byte[] data;

    @Override
    public void setSigner(String signerName) {
        signer = signerName;
    }

    @Override
    public String getSigner() {
        return signer;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return data;
    }
}
