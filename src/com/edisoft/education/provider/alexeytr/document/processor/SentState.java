package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */
public class SentState implements State {

    private DocProc documentProcessor;

    public SentState(DocProc documentProcessor) {
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void create() {
        System.out.println("Вы не можете создать документ");
    }

    public void sign() {
        System.out.println("Вы не можете подписывать документ");
    }

    @Override
    public void send() {
        System.out.println("Документ уже отправлен");
    }

    @Override
    public void read() {
        System.out.println("Документ прочтен!!!");
        documentProcessor.setState(documentProcessor.getReadState());
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
