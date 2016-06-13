package com.edisoft.education.provider.alexeytr.document.processor;

/**
 * Created by Capert
 */

public class DraftState implements State {
    private DocProc documentProcessor;

    public DraftState(DocProc documentProcessor) {
        this.documentProcessor = documentProcessor;
    }

    @Override
    public void create() {
        System.out.println("Вы не можете создать документ");
    }

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
        System.out.println("Документ успешно обновлён!!!");
        documentProcessor.setState(documentProcessor.getDraftState());
    }

    @Override
    public void delete() {
        System.out.println("Документ успешно удалён!!!");
        documentProcessor.setState(documentProcessor.getEmptyState());
    }

    @Override
    public void reject() {

    }

    @Override
    public void store() {
        System.out.println("Черновик успешно сохранён!!!");
        documentProcessor.setState(documentProcessor.getEmptyState());
    }

    @Override
    public void load() {

    }
}
