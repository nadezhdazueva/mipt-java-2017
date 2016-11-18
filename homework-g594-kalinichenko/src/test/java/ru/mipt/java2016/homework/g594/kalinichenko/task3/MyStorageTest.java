package ru.mipt.java2016.homework.g594.kalinichenko.task3;
import ru.mipt.java2016.homework.base.task2.KeyValueStorage;
import ru.mipt.java2016.homework.tests.task2.AbstractSingleFileStorageTest;
import ru.mipt.java2016.homework.tests.task2.Student;
import ru.mipt.java2016.homework.tests.task2.StudentKey;

/**
 * Created by masya on 30.10.16.
 */
public class MyStorageTest  extends AbstractSingleFileStorageTest
{
    @Override
    protected KeyValueStorage<String, String> buildStringsStorage(String path) {
        return new MyStorage(path, new MyStringSerializer(), new MyStringSerializer());
    }

    @Override
    protected KeyValueStorage<Integer, Double> buildNumbersStorage(String path) {
        return new MyStorage(path, new MyIntSerializer(), new MyDoubleSerializer());
    }

    @Override
    protected KeyValueStorage<StudentKey, Student> buildPojoStorage(String path) {
        return new MyStorage(path, new MyStudentKeySerializer(), new MyStudentSerializer());
    }
}

