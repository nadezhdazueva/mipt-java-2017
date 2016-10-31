package ru.mipt.java2016.homework.g595.zueva.task2.task2;

/*сreated by Nadya*/

import org.apache.commons.io.IOExceptionWithCause;
import ru.mipt.java2016.homework.base.task2.KeyValueStorage;
import ru.mipt.java2016.homework.g595.zueva.task2.task2.Serializer;
import java.util.Iterator;
import java.util.Map;
import java.io.*;
import java.util.HashMap;


public class My_KV_Storage<K, V> implements KeyValueStorage<K, V> {
    private static final String VALIDATION_STRING;

    static {
        VALIDATION_STRING = "My strange storage";
    }

    public String FileName;
    public HashMap<K, V> CurrentStorage;
    public Serializer<K> keySerializer;
    public Serializer<V> valueSerializer;
    public boolean ifOpen = false;

    /*Конструктор класс хранилища*/
    public My_KV_Storage(String newFileName,
                         Serializer newKeySerialisation,
                         Serializer newValueSerialisation) throws Exception {
        keySerializer = newKeySerialisation;
        valueSerializer = newValueSerialisation;
        FileName = newFileName + "/note.txt";
        CurrentStorage = new HashMap<K, V>();
        ifOpen = true;
        /*проверка, возможно ли открыть и создать файл*/
        File destination = new File(FileName);
        if (!destination.exists()) {
            destination.createNewFile();

            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(FileName))) {
                out.writeUTF(VALIDATION_STRING);
                out.writeInt(0b0);
            } catch (Exception e) {
                throw new Exception("Cannot write to file");
            }
        }
        /* Существует ли наша валидирующая строка в файле && проверка, возможно ли читать из файла*/
        try (DataInputStream in = new DataInputStream(new FileInputStream(FileName))) {
            if (!in.readUTF().equals(VALIDATION_STRING)) {
                throw new IllegalStateException("Unknown validation");
            }
            int quantity = in.readInt();
            for (int i = 0; i < quantity; ++i) {

                K keyToInsert = keySerializer.readFromStream(in);
                V valueToInsert = valueSerializer.readFromStream(in);
                CurrentStorage.put(keyToInsert, valueToInsert);
            }
        } catch (Exception exception) {
            throw new Exception("Cannot read from file");
        }
    }
    @Override
    public void close() throws IOExceptionWithCause {
        isFileClosed();
        try
                (DataOutputStream out = new DataOutputStream(new FileOutputStream(FileName))) {
            out.writeUTF(VALIDATION_STRING);
            out.writeInt(CurrentStorage.size());
            for (Map.Entry<K, V> i : CurrentStorage.entrySet()) {
                keySerializer.writeToStream(out, i.getKey());
                valueSerializer.writeToStream(out, i.getValue());
            }
            ifOpen = false;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to save and close storage");
        }
    }
    @Override
    public V read(K key) {
        isFileClosed();
        return CurrentStorage.get(key);
    }

    @Override
    public boolean exists(K key) {
        isFileClosed();
        return CurrentStorage.keySet().contains(key);
    }

    @Override
    public void write(K key, V value) {
        isFileClosed();
        CurrentStorage.put(key, value);
    }

    @Override
    public void delete(K key) {
        isFileClosed();
        CurrentStorage.remove(key);
    }

    @Override
    public Iterator<K> readKeys() {
        isFileClosed();
        return CurrentStorage.keySet().iterator();
    }

    @Override
    public int size() {
        isFileClosed();
        return CurrentStorage.size();
    }


    public void isFileClosed() {
        if (!ifOpen) {
            throw new IllegalStateException("Storage had been already closed");
        }
    }
}
