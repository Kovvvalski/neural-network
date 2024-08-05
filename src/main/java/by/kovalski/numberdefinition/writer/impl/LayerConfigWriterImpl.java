package by.kovalski.numberdefinition.writer.impl;


import by.kovalski.numberdefinition.entity.Layer;
import by.kovalski.numberdefinition.entity.Neuron;
import by.kovalski.numberdefinition.writer.LayerConfigWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LayerConfigWriterImpl implements LayerConfigWriter {
    @Override
    public void writeConfiguration(Layer layer, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Neuron neuron : layer.getNeurons()) {
                List<Double> weights = neuron.getWeights();
                for (int i = 0; i < weights.size(); i++) {
                    writer.write(String.valueOf(weights.get(i)));
                    writer.write(",");
                }
                writer.write(String.valueOf(neuron.getBias()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // log
        }
    }
}
