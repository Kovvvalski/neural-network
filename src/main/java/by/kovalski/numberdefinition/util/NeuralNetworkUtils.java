package by.kovalski.numberdefinition.util;

import by.kovalski.numberdefinition.entity.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class NeuralNetworkUtils {
    static final double MIN_RANDOM_VALUE = -1.0;
    static final double MAX_RANDOM_VALUE = 1.0;

    public static List<Neuron> createNeurons(int neuronsCount, int weightsCount, Function<Double, Double> activationFunction) {
        List<Neuron> neurons = new ArrayList<>();

        for (int i = 0; i < neuronsCount; i++) {
            List<Double> weights = createRandomWeights(weightsCount);
            neurons.add(new Neuron(activationFunction, weights, createRandomValue(i)));
        }
        return neurons;
    }

    public static double createRandomValue(int currentIndex) {
        double random = ThreadLocalRandom.current().nextDouble(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
        return currentIndex % 2 == 0 ? -random : random;
    }

    public static List<Double> createRandomWeights(int weightsCount) {
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < weightsCount; i++)
            weights.add(createRandomValue(i));

        return weights;
    }

    public static List<Double> getErrorSignal(List<Double> desiredResponse, List<Double> outputSignal) {
        List<Double> errorSignal = new ArrayList<>();

        for (int i = 0; i < desiredResponse.size(); i++) {
            errorSignal.add(desiredResponse.get(i) - outputSignal.get(i));
        }
        return errorSignal;
    }
}
