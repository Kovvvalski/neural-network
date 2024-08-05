package by.kovalski.numberdefinition.parser.impl;

import by.kovalski.numberdefinition.entity.Layer;
import by.kovalski.numberdefinition.entity.Neuron;
import by.kovalski.numberdefinition.parser.LayerParser;
import by.kovalski.numberdefinition.reader.DataReader;
import by.kovalski.numberdefinition.util.ActivationFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

public class LayerParserImpl implements LayerParser {
    private static final String DELIMITER = ",";
    private final DataReader dataReader;

    public LayerParserImpl(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public Layer parseLayer(String path) throws IllegalFormatException {
        List<Neuron> neurons = new ArrayList<>();
        try {
            List<String> parsed = dataReader.readData(path);
            for (String line : parsed) {
                String[] numbers = line.split(DELIMITER);
                List<Double> weights = new ArrayList<>();
                for (int i = 0; i < numbers.length - 1; i++) {
                    weights.add(Double.parseDouble(numbers[i]));
                }
                double bias = Double.parseDouble(numbers[numbers.length - 1]);
                neurons.add(new Neuron(ActivationFunctions.SIGMOID_FUNCTION, weights, bias));
            }
        } catch (IOException e) {
            e.printStackTrace(); // log
        }
        return new Layer(neurons);
    }
}
