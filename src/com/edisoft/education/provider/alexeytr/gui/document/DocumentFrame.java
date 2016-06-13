package com.edisoft.education.provider.alexeytr.gui.document;

import com.edisoft.education.core.Observer;
import com.edisoft.education.core.document.processor.DocumentInterface;
import com.edisoft.education.core.document.processor.SignatureInterface;
import com.edisoft.education.core.storage.ObservableStorageInterface;
import com.edisoft.education.core.storage.StorageControllerInterface;
import com.edisoft.education.core.storage.StorageFileInterface;
import com.edisoft.education.provider.alexeytr.document.processor.Document;
import com.edisoft.education.provider.alexeytr.storage.StorageAdapter;
import com.edisoft.education.provider.alexeytr.storage.StorageFile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Capert
 */
public class DocumentFrame extends JFrame implements Observer {

    private JList list;
    private JScrollPane pane;
    private JFrame frame;
    private JTextArea textArea;


    public DocumentFrame() {
        super("Наблюдатель документов");
        list = new JList();
        pane = new JScrollPane(list);
        add(pane);
        setSize(400, 400);
        setVisible(true);

    }

    @Override
    public void update(ObservableStorageInterface observable, Object arg) {
        if (observable instanceof ObservableStorageInterface) {
            ArrayList names = new ArrayList();
            ObservableStorageInterface storage = observable;
            StorageControllerInterface adapter = new StorageAdapter(storage);
            for (String f : adapter.getStoredFiles()) {
                if (f.endsWith("draft") || f.endsWith("document"))
                    names.add(f);
            }
            list.setListData(names.toArray());
            list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JList list = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        int index = list.locationToIndex(evt.getPoint());
                        DocumentInterface doc;
                        StorageFileInterface file = new StorageFile();
                        try {
                            file.setName(names.get(index).toString());
                            file.setContent(storage.getFileByName(names.get(index).toString()).getContent());
                            ByteArrayInputStream baos = new ByteArrayInputStream(file.getContent());
                            ObjectInputStream oin = new ObjectInputStream(baos);
                            doc = (Document) oin.readObject();
                            frame = new JFrame();
                            frame.setSize(400, 400);
                            frame.setVisible(true);
                            textArea = new JTextArea();
                            JScrollPane paneDoc = new JScrollPane(textArea);
                            frame.add(paneDoc);
                            textArea.append("Тип документа: " + doc.getType() + "\n");
                            textArea.append("Содержимое документа: " + doc.getData() + "\n");
                            textArea.append("Номер документа: " + doc.getNumber() + "\n");
                            if (doc.isSigned() == true) {
                                textArea.append("Подписи документа:" + "\n");
                                for (SignatureInterface s : doc.getSignatures()) {
                                    textArea.append(s.getSigner() + "   " + new String(s.getData(), "UTF-8") + "\n");
                                }
                            } else {
                                textArea.append("Документ не содержит подписей" + "\n");
                            }
                            baos.close();
                            oin.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            });
        }
    }
}