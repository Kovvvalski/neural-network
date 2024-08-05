package by.kovalski.numberdefinition.reader.impl;

import by.kovalski.numberdefinition.reader.DataReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReaderImpl implements DataReader {
    @Override
    public List<String> readData(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
}
