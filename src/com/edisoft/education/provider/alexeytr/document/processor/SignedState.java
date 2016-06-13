package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */
public class SignedState implements State {
    private DocProc documentProcessor;

    public SignedState(DocProc documentProcessor) {
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void create() {
        System.out.println("Вы не можете создать документ");
    }

    @Override
    public void sign() {
        System.out.println("Документ подписан!!!");
        documentProcessor.setState(documentProcessor.getSignState());
    }

    @Override
    public void send() {
        System.out.println("Документ отправлен!!!");
        documentProcessor.setState(documentProcessor.getSendState());
    }

    @Override
    public void read() {
        System.out.println("Вы не можете прочесть документ");
    }

    @Override
    public void accept() {
        System.out.println("Вы не можете принять документ");
    }

    @Override
    public void archive() {
        System.out.println("Вы не можете отправить документ в архив");
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

    }

    @Override
    public void store() {

    }

    @Override
    public void load() {

    }

}