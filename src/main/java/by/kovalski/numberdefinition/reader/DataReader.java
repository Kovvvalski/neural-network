package by.kovalski.numberdefinition.reader;

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface DataReader {
    List<String> readData(String path) throws IOException;
}
