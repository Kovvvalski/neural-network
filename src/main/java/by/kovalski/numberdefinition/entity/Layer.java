package by.kovalski.numberdefinition.entity;

import java.util.ArrayList;
import java.util.List;

import static by.kovalski.numberdefinition.util.ActivationFunctions.*;

public class Layer {

    private final List<Neuron> neurons;
    private List<Double> inputSignals;

    public Layer(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public List<Double> produceSignals() {
        List<Double> currentSignals = new ArrayList<>();

        for (Neuron neuron : neurons) {
            currentSignals.add(neuron.getActivationPotential());
        }

        return currentSignals;
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public List<Double> getInputSignals() {
        return inputSignals;
    }

    public void setInputSignals(List<Double> inputSignals) {
        this.inputSignals = inputSignals;
    }

    public void calculateAndSetLocalGradients(List<Double> errorSignal) {
        if (neurons.size() != errorSignal.size()) {
            throw new IllegalArgumentException("Neurons and error signals do not match");
        }
        for (int i = 0; i < neurons.size(); i++) {
            neurons.get(i).setLocalGradient(errorSignal.get(i) *
                    SIGMOID_FUNCTION_D.apply(neurons.get(i).getInducedLocalField()));
        }
    }

    public void calculateAndSetLocalGradients(Layer previousLayer) {
        for (int i = 0; i < neurons.size(); i++) {
            List<Double> associatedWeights = getAssociatedWeights(previousLayer, i);
            double innerSum = getInnerSum(associatedWeights, previousLayer);
            neurons.get(i).setLocalGradient(innerSum * SIGMOID_FUNCTION_D.apply(neurons.get(i).getInducedLocalField()));
        }
    }

    private List<Double> getAssociatedWeights(Layer previousLayer, int neuronPositionInCurrentLayer) {
        List<Double> associatedWeights = new ArrayList<>();

        for (Neuron neuron : previousLayer.neurons) {
            associatedWeights.add(neuron.getWeights().get(neuronPositionInCurrentLayer));
        }
        return associatedWeights;
    }

    private double getInnerSum(List<Double> associatedWeights, Layer previousLayer) {
        double innerSum = 0.0;

        for (int i = 0; i < associatedWeights.size(); i++) {
            innerSum += associatedWeights.get(i) * previousLayer.neurons.get(i).getLocalGradient();
        }
        return innerSum;
    }

    public void adjustWeightsAndBias(double learningRateParameter) {
        for (Neuron neuron : neurons) {
            neuron.adjustWeightsAndBias(learningRateParameter, inputSignals);
        }
    }
}
