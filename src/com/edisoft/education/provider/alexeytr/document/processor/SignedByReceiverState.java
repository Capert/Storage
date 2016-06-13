package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */
public class SignedByReceiverState implements State {
    private DocProc documentProcessor;

    public SignedByReceiverState (DocProc documentProcessor) {
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void create() {

    }

    @Override
    public void sign() {
        System.out.println("Документ подписан получателем!!!");
        documentProcessor.setState(documentProcessor.getSignedByReceiverState());
    }

    @Override
    public void send() {

    }

    @Override
    public void read() {

    }

    @Override
    public void accept() {
        System.out.println("Документ подтвержден!!!");
        documentProcessor.setState(documentProcessor.getAcceptState());
    }

    @Override
    public void archive() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void reject() {

    }

    @Override
    public void store() {

    }

    @Override
    public void load() {

    }
}
