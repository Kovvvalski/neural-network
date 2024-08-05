package by.kovalski.numberdefinition.main;

import by.kovalski.numberdefinition.entity.Layer;
import by.kovalski.numberdefinition.entity.NeuralNetwork;
import by.kovalski.numberdefinition.entity.TestCase;
import by.kovalski.numberdefinition.parser.LayerParser;
import by.kovalski.numberdefinition.parser.TestCaseParser;
import by.kovalski.numberdefinition.parser.impl.CsvTestCaseParser;
import by.kovalski.numberdefinition.parser.impl.LayerParserImpl;
import by.kovalski.numberdefinition.reader.impl.DataReaderImpl;

import java.util.List;


public class LoadAndTest {
    private static final String PATH_TO_TRAIN_DS = "MNIST_CSV/mnist_train.csv";
    private static final String PATH_TO_TEST_DS = "MNIST_CSV/mnist_test.csv";

    public static void main(String[] args) {

        LayerParser layerParser = new LayerParserImpl(new DataReaderImpl());
        TestCaseParser parser = new CsvTestCaseParser(new DataReaderImpl());
        Layer layer1 = layerParser.parseLayer("src/main/resources/1_layer_config.csv");
        Layer layer2 = layerParser.parseLayer("src/main/resources/2_layer_config.csv");
        Layer outputLayer = layerParser.parseLayer("src/main/resources/output_layer_config.csv");
        NeuralNetwork neuralNetwork = new NeuralNetwork(List.of(layer1, layer2), outputLayer);
        List<TestCase> testCases = parser.ParseTestCases(PATH_TO_TRAIN_DS);
        int notPredicted = 0;
        for (TestCase test : testCases) {
            List<Double> outputSignal = neuralNetwork.makePropagateForward(test.getInput());
            int predictedDigit = outputSignal.indexOf(outputSignal.stream().mapToDouble(o -> o).max().orElse(0));
            if (test.getOutput() != predictedDigit) {
                System.out.println("Not correct predict: " + test.getOutput());
                notPredicted++;
            } else {
                System.out.println("Correct: " + test.getOutput());
            }
        }
        System.out.println((1.0 - (double) notPredicted / testCases.size()) * 100 + "% of accuracy");
    }
}

