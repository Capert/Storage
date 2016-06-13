package com.edisoft.education.provider.alexeytr;
import com.edisoft.education.core.DefaultInstanceFactory;
import com.edisoft.education.core.InstanceFactoryInterface;
import com.edisoft.education.core.Observer;
import com.edisoft.education.core.StatisticHandlerProxy;
import com.edisoft.education.core.document.processor.DocumentProcessorInterface;
import com.edisoft.education.core.storage.ObservableStorageInterface;
import com.edisoft.education.core.storage.StorageControllerInterface;
import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.core.storage.StorageInterface;
import com.edisoft.education.provider.alexeytr.document.processor.DocProc;
import com.edisoft.education.provider.alexeytr.gui.document.DocumentFrame;
import com.edisoft.education.provider.alexeytr.gui.storage.StorageFrame;
import com.edisoft.education.provider.alexeytr.storage.*;

import java.lang.reflect.Proxy;

/**
 * Created by Capert
 */
public class InstanceFactory extends DefaultInstanceFactory implements InstanceFactoryInterface {
    @Override
    public StorageInterface newStorage() {
        return new Storage();
    }

    @Override
    public ObservableStorageInterface newObservableStorage() {
        return new Storage();
    }

    @Override
    public StorageInterface newStorageSecureDecorator(StorageInterface storage) {
        return new StorageDecorator(storage);
    }

    @Override
    public StorageInterface newStorageInvocationCounterDecorator(StorageInterface storage) {
        return new StorageDecoratorStat(storage);
    }

    @Override
    public StorageFileInterface newStorageFile() {
        return new StorageFile();
    }

    @Override
    public StorageControllerInterface newStorageAdapter(StorageInterface storage) {
        return new StorageAdapter(storage);
    }

    @Override
    public Observer newStorageStateObserver() {
        return new StorageFrame();
    }

    @Override
    public DocumentProcessorInterface newDocumentProcessor() {
        return new DocProc();
    }

    @Override
    public Observer newStorageDocumentsObserver() {
        return new DocumentFrame();
    }

    @Override
    public <T> T getStatistic(T object) {
        return super.getStatistic(object);
    }
}
