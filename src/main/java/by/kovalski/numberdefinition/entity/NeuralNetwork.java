package by.kovalski.numberdefinition.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static by.kovalski.numberdefinition.util.NeuralNetworkUtils.*;

public class NeuralNetwork {

    public List<Layer> hiddenLayers;
    public Layer outputLayer;

    public NeuralNetwork(int inputLayerDimension, int outputLayerNeuronsCount, Function<Double, Double> outputActivationFunction,
                         List<Integer> hiddenLayersDimensions, List<Function<Double, Double>> hiddenActivationFunctions) {

        hiddenLayers = new ArrayList<>();
        if (!hiddenLayersDimensions.isEmpty()) {
            hiddenLayers.add(new Layer(createNeurons(hiddenLayersDimensions.getFirst(), inputLayerDimension, hiddenActivationFunctions.getFirst())));
            for (int h = 1; h < hiddenLayersDimensions.size(); h++)
                hiddenLayers.add(new Layer(createNeurons(hiddenLayersDimensions.get(h), hiddenLayersDimensions.get(h - 1), hiddenActivationFunctions.get(h))));
        }

        int outputWeightsCount = !hiddenLayersDimensions.isEmpty() ? hiddenLayersDimensions.getLast() : inputLayerDimension;
        outputLayer = new Layer(createNeurons(outputLayerNeuronsCount, outputWeightsCount, outputActivationFunction));
    }

    public NeuralNetwork(List<Layer> hiddenLayers, Layer outputLayer) {
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
    }

    public List<Layer> getHiddenLayers() {
        return hiddenLayers;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public List<Double> makePropagateForward(List<Double> functionSignal) {
        if (!hiddenLayers.isEmpty())
            for (Layer hiddenLayer : hiddenLayers) {
                functionSignal = setInputSignalAndInducedLocalField(hiddenLayer, functionSignal);
            }
        return setInputSignalAndInducedLocalField(outputLayer, functionSignal);
    }

    private List<Double> setInputSignalAndInducedLocalField(Layer layer, List<Double> functionSignal) {
        layer.setInputSignals(functionSignal);

        for (Neuron neuron : layer.getNeurons()) {
            neuron.setInducedLocalField(functionSignal);
        }
        return layer.produceSignals();
    }

    public void train(List<TestCase> dataset, double learningRateParameter, int numberOfEpochs) {
        for (int e = 1; e <= numberOfEpochs; e++) {
            for (TestCase testCase : dataset) {
                List<Double> outputSignal = makePropagateForward(testCase.getInput());
                List<Double> expected = new ArrayList<>();
                for (int i = 0; i < outputLayer.getNeurons().size(); i++) {
                    expected.add(i == testCase.getOutput() ? 1.0 : 0.0);
                }
                List<Double> errorSignal = getErrorSignal(expected, outputSignal);
                makePropagateBackward(errorSignal, learningRateParameter);
            }
            System.out.println("epoch " + e + " finished");
        }
    }

    private void makePropagateBackward(List<Double> errorSignal, double learningRateParameter) {
        outputLayer.calculateAndSetLocalGradients(errorSignal);
        outputLayer.adjustWeightsAndBias(learningRateParameter);
        if (hiddenLayers.isEmpty()) {
            return;
        }
        Layer previousLayer = outputLayer;

        for (int i = hiddenLayers.size() - 1; i >= 0; i--) {
            hiddenLayers.get(i).calculateAndSetLocalGradients(previousLayer);
            hiddenLayers.get(i).adjustWeightsAndBias(learningRateParameter);
            previousLayer = hiddenLayers.get(i);
        }
    }
}
