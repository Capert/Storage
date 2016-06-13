import java.io.*;
import java.util.Arrays;


import com.edisoft.education.core.InstanceFactoryInterface;
import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.DocumentProcessorInterface;
import com.edisoft.education.core.document.processor.DocumentTypes;
import com.edisoft.education.core.storage.ObservableStorageInterface;
import com.edisoft.education.core.storage.StorageControllerInterface;
import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.gui.storage.StorageControllerFrame;
import com.edisoft.education.provider.alexeytr.InstanceFactory;
import com.edisoft.education.provider.alexeytr.document.processor.DocProc;
import com.edisoft.education.provider.alexeytr.document.processor.Signature;
import com.edisoft.education.provider.alexeytr.gui.document.DocumentFrame;
import com.edisoft.education.provider.alexeytr.gui.storage.StorageFrame;
import com.edisoft.education.provider.alexeytr.storage.*;

/**
 * Created by Capert
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /*Storage storage = new Storage();
        storage.initialize("C:/1/2/3");*/
        /*InstanceFactoryInterface factory = new InstanceFactory();
        ObservableStorageInterface storage = factory.newObservableStorage();
        //storage.registerObserver(new StorageFrame());
        storage.registerObserver(new DocumentFrame());
        storage.initialize("C:/1/2/3");
        StorageDecoratorStat decoratorStat = new StorageDecoratorStat(storage);
        StorageControllerInterface storageControllerInterface = new StorageAdapter(decoratorStat);
        new StorageControllerFrame(storageControllerInterface);*/

        DocProc pr = new DocProc();
        //pr.load(storage, DocumentTypes.ORDER, 1L);
        pr.create(DocumentTypes.ORDER, 1L, "New");
        //pr.store(storage);
        System.out.println(pr.getState());
        pr.sign("Jack", "jack".getBytes());
        System.out.println(pr.getState());
        pr.sign("Frank","tred".getBytes());
        System.out.println(pr.getState());
        pr.sign("Andy", "and".getBytes());
        System.out.println(pr.getState());
        System.out.println(pr.showCurrentDocument());
        DocumentInterface d = pr.showCurrentDocument();
        d.setType(DocumentTypes.RECADV);
        d.setData("QWERTY");
        d.setNumber(54L);
        System.out.println(d.getType());
        System.out.println(pr.showCurrentDocument().getType());
        System.out.println(d.getData());
        System.out.println(pr.showCurrentDocument().getData());
        System.out.println(d.getNumber());
        System.out.println(pr.showCurrentDocument().getNumber());
        System.out.println(d.getSignatures());
        System.out.println(pr.showCurrentDocument().getSignatures());
     /*   pr.send();
        System.out.println(pr.getState());
        pr.read();
        System.out.println(pr.getState());
        pr.accept();
        System.out.println(pr.getState());
        //pr.store(storage);
        pr.archive(storage);*/
    }
}
