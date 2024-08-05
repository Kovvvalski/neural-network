package by.kovalski.numberdefinition.entity;

import java.util.List;
import java.util.function.Function;

public class Neuron {

    private final List<Double> weights;
    private double bias;
    private final Function<Double, Double> activationFunction;
    private double inducedLocalField;
    private double localGradient;


    public Neuron(Function<Double, Double> activationFunction, List<Double> weights, double bias) {
        this.weights = weights;
        this.bias = bias;
        this.activationFunction = activationFunction;
        inducedLocalField = 0.0;
    }


    public List<Double> getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public Function<Double, Double> getActivationFunction() {
        return activationFunction;
    }

    public double getInducedLocalField() {
        return inducedLocalField;
    }

    public void setInducedLocalField(List<Double> inputSignal) {
        if (inputSignal.size() != weights.size()) {
            throw new IllegalArgumentException("Input signal size does not match output signal size");
        }
        inducedLocalField = 0.0;
        for (int i = 0; i < inputSignal.size(); i++) {
            inducedLocalField += inputSignal.get(i) * weights.get(i);
        }
        inducedLocalField += bias;
    }

    public double getLocalGradient() {
        return localGradient;
    }

    public void setLocalGradient(double localGradient) {
        this.localGradient = localGradient;
    }

    public double getActivationPotential() {
        return activationFunction.apply(inducedLocalField);
    }

    public void adjustWeightsAndBias(Double learningRateParameter, List<Double> inputSignals) {
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) + learningRateParameter * localGradient * inputSignals.get(i));
            //bias += learningRateParameter * localGradient; works better
        }
    }
}

