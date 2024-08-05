package by.kovalski.numberdefinition.entity;

import java.util.List;

public class TestCase {
    private final List<Double> input;
    private int output;

    public TestCase(List<Double> input, int output) {
        this.input = input;
        this.output = output;
    }

    public List<Double> getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }
}
