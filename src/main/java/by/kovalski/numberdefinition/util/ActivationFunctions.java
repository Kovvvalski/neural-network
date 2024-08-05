package by.kovalski.numberdefinition.util;


import java.util.function.Function;

public class ActivationFunctions {
    private static final double A = 1.0;

    public static final Function<Double, Double> SIGMOID_FUNCTION = x -> 1.0 / (1.0 + Math.pow(Math.E, -A * x));

    public static final Function<Double, Double> SIGMOID_FUNCTION_D = x -> {
        double factor = A * Math.pow(Math.E, -A * x);
        return factor * Math.pow(SIGMOID_FUNCTION.apply(x), 2.0);
    };
}
