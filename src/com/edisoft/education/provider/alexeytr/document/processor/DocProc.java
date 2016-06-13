package com.edisoft.education.provider.alexeytr.document.processor;

import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.DocumentProcessorInterface;
import com.edisoft.education.core.document.processor.DocumentTypes;
import com.edisoft.education.core.document.processor.SignatureInterface;
import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.core.storage.StorageInterface;
import com.edisoft.education.provider.alexeytr.storage.StorageFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Capert
 */
public class DocProc implements DocumentProcessorInterface {

    private DocumentInterface doc = new Document();

    private State emptyState;
    private State draftState;
    private State signState;
    private State sendState;
    private State readState;
    private State acceptState;
    private State signedByReceiverState;

    private State state;

    public DocProc() {
        emptyState = new EmptyState(this);
        draftState = new DraftState(this);
        signState = new SignedState(this);
        sendState = new SentState(this);
        readState = new ReadState(this);
        acceptState = new AcceptState(this);
        signedByReceiverState = new SignedByReceiverState(this);
        state = emptyState;
    }

    void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean create(DocumentTypes type, Long number, String data) {
        if (state == getEmptyState()) {
            try {
                doc.setType(type);
                doc.setNumber(number);
                doc.setData(data);
                state.create();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean update(String data) {
        if (state == getDraftState()) {
            try {
                doc.setData(data);
                state.update();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean delete() {
        if (state == getDraftState()) {
            try {
                doc = new Document();
                state.delete();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean store(StorageInterface storage) {
        if (state == getDraftState()) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                StorageFileInterface file = new StorageFile();
                file.setName(doc.getType().toString() + "_" + doc.getNumber().toString() + ".draft");
                oos.writeObject(doc);
                file.setContent(baos.toByteArray());
                if (storage.getFileByName(file.getName().toString()) != null) {
                    storage.removeFile(file);
                }
                storage.storeFile(file);
                doc = new Document();
                state.store();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean load(StorageInterface storage, DocumentTypes type, Long number) {
        if (state == getEmptyState()) {
            try {
                StorageFileInterface file = new StorageFile();
                file.setName(type.toString() + "_" + number.toString());
                file.setContent(storage.getFileByName(type.toString() + "_" + number.toString() + ".draft").getContent());
                ByteArrayInputStream baos = new ByteArrayInputStream(file.getContent());
                ObjectInputStream oin = new ObjectInputStream(baos);
                doc = (Document) oin.readObject();
                state.load();
                baos.close();
                oin.close();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean sign(String signer, byte[] signatureData) {
        if (state == getDraftState() | state == getSignState()) {
            try {
                SignatureInterface sign = new Signature();
                sign.setSigner("подпись_отправителя_" + signer);
                sign.setData(signatureData);
                doc.addSignature(sign);
                state.sign();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else if (state == getReadState() | state == getSignedByReceiverState()) {
            try {
                SignatureInterface sign = new Signature();
                sign.setSigner("подпись_получателя_" + signer);
                sign.setData(signatureData);
                doc.addSignature(sign);
                state.sign();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean send() {
        if (state == getDraftState() | state == getSignState()) {
            try {
                state.send();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean read() {
        if (state == getSendState()) {
            try {
                state.read();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean reject() {
        if (state == getReadState()) {
            try {
                for (SignatureInterface s : doc.getSignatures()) {
                    doc.deleteSignature(s);
                }
                state.reject();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean accept() {
        if (state == getReadState()) {
            try {
                state.accept();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public boolean archive(StorageInterface storage) {
        if (state == getAcceptState()) {
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                StorageFileInterface file = new StorageFile();
                file.setName(doc.getType().toString() + "_" + doc.getNumber().toString() + ".document");
                System.out.println(file.getName());
                oos.writeObject(doc);
                file.setContent(baos.toByteArray());
                if (storage.getFileByName(file.getName().toString()) != null) {
                    storage.removeFile(file);
                }
                storage.storeFile(file);
                doc = new Document();
                state.archive();
                return true;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e);
                return false;
            }
        } else {
            System.out.println(false);
            return false;
        }
    }

    @Override
    public DocumentInterface showCurrentDocument() {
        DocumentInterface docCopy = new Document();
        docCopy.setType(doc.getType());
        docCopy.setNumber(doc.getNumber());
        docCopy.setData(doc.getData());
        for (SignatureInterface s: doc.getSignatures()) {
            docCopy.addSignature(s);
        }
        return docCopy;
    }

    public State getState() {
        return state;
    }

    public State getDraftState() {
        return draftState;
    }

    public State getSignState() {
        return signState;
    }

    public State getSendState() {
        return sendState;
    }

    public State getReadState() {
        return readState;
    }

    public State getAcceptState() {
        return acceptState;
    }

    public State getEmptyState() {
        return emptyState;
    }

    public State getSignedByReceiverState() {
        return signedByReceiverState;
    }
}
