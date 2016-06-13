package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */
public class AcceptState implements State {

    private DocProc documentProcessor;

    public AcceptState(DocProc documentProcessor) {
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void create() {
        System.out.println("Вы не можете создать документ");
    }

    @Override
    public void sign() {
        System.out.println("Вы не можете подписывать документ");
    }

    @Override
    public void send() {
        System.out.println("Вы не можете отправлять документ");
    }

    @Override
    public void read() {
        System.out.println(("Вы не можете получить подтверждение о прочтении"));
    }

    @Override
    public void accept() {
        System.out.println("Документ уже подтвержден");
    }

    @Override
    public void archive() {
        System.out.println("Документ отправлен в хранилище!!!");
        documentProcessor.setState(documentProcessor.getEmptyState());
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
