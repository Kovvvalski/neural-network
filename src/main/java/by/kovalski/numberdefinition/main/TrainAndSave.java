package by.kovalski.numberdefinition.main;

import by.kovalski.numberdefinition.entity.NeuralNetwork;
import by.kovalski.numberdefinition.parser.TestCaseParser;
import by.kovalski.numberdefinition.parser.impl.CsvTestCaseParser;
import by.kovalski.numberdefinition.reader.impl.DataReaderImpl;
import by.kovalski.numberdefinition.util.ActivationFunctions;
import by.kovalski.numberdefinition.writer.LayerConfigWriter;
import by.kovalski.numberdefinition.writer.impl.LayerConfigWriterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TrainAndSave {
    private static final String PATH_TO_TRAIN_DS = "MNIST_CSV/mnist_train.csv";
    public static void main(String[] args) {
        List<Integer> hiddenLayersDimensions = new ArrayList<>(); // Массив для хранения количества нейронов на каждом скрытом слое
        List<Function<Double, Double>> hiddenActivationFunctions = new ArrayList<>(); // Массив для хранения функций активации на каждом скрытом слое
        hiddenLayersDimensions.add(100); // У нас один скрытый слой на котором 80 нейронов
        hiddenLayersDimensions.add(100); // У нас один скрытый слой на котором 80 нейронов
        hiddenActivationFunctions.add(ActivationFunctions.SIGMOID_FUNCTION); // И для всех нейронов этого скрытого слоя используется сигмоидальная функция активации
        hiddenActivationFunctions.add(ActivationFunctions.SIGMOID_FUNCTION); // И для всех нейронов этого скрытого слоя используется сигмоидальная функция активации
        TestCaseParser parser = new CsvTestCaseParser(new DataReaderImpl());
        NeuralNetwork neuralNetwork = new NeuralNetwork(784, 10, ActivationFunctions.SIGMOID_FUNCTION, hiddenLayersDimensions, hiddenActivationFunctions);
        neuralNetwork.train(parser.ParseTestCases(PATH_TO_TRAIN_DS), 0.2, 5); // Запускаем обучение

        LayerConfigWriter layerConfigWriter = new LayerConfigWriterImpl();
        layerConfigWriter.writeConfiguration(neuralNetwork.getHiddenLayers().get(0), "src/main/resources/1_layer_config.csv");
        layerConfigWriter.writeConfiguration(neuralNetwork.getHiddenLayers().get(1), "src/main/resources/2_layer_config.csv");
        layerConfigWriter.writeConfiguration(neuralNetwork.getOutputLayer(), "src/main/resources/output_layer_config.csv");
    }
}
