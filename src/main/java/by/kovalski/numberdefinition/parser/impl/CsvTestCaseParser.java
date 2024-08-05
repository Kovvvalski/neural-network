package by.kovalski.numberdefinition.parser.impl;

import by.kovalski.numberdefinition.entity.TestCase;
import by.kovalski.numberdefinition.parser.TestCaseParser;
import by.kovalski.numberdefinition.reader.DataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvTestCaseParser implements TestCaseParser {
    private static final String DELIMITER = ",";

    private DataReader dataReader;

    public CsvTestCaseParser(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public DataReader getDataReader() {
        return dataReader;
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public List<TestCase> ParseTestCases(String path) {
        List<String> data;
        try {
            data = dataReader.readData(path);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        List<TestCase> dataSet = new ArrayList<>();
        for (String line : data) {
            String[] parsed = line.split(DELIMITER);
            List<Double> image = new ArrayList<>();
            List<Double> output = new ArrayList<>();
            int number = Integer.parseInt(parsed[0]);
            for (int i = 1; i < parsed.length; i++) {
                image.add(Double.parseDouble(parsed[i]) == 0.0 ? 0.0 : 1.0);
            }
            dataSet.add(new TestCase(image, number));
        }
        return dataSet;
    }
}
