package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */
public class ReadState implements State {

    private DocProc documentProcessor;

    public ReadState(DocProc documentProcessor) {
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void create() {
        System.out.println("Вы не можете создать документ");
    }

    @Override
    public void sign() {
        System.out.println("Документ подписан получателем!!!");
        documentProcessor.setState(documentProcessor.getSignedByReceiverState());
    }

    @Override
    public void send() {
        System.out.println("Вы не можете отправлять документ");
    }

    @Override
    public void read() {
        System.out.println(("Документ уже был прочитан"));
    }

    @Override
    public void accept() {
        System.out.println("Документ подтвержден!!!");
        documentProcessor.setState(documentProcessor.getAcceptState());
    }

    @Override
    public void archive() {
        System.out.println("Вы не можете отправлять документ в хранилище");
    }

    @Override
    public void update() {
        System.out.println("Вы не можете обновить документ!!!");
    }

    @Override
    public void delete() {
        System.out.println("Невозможно удалить документ!!!");
    }

    @Override
    public void reject() {
        System.out.println("Подписи удалены");
        documentProcessor.setState(documentProcessor.getDraftState());
    }

    @Override
    public void store() {

    }

    @Override
    public void load() {

    }
}
