package by.kovalski.numberdefinition.writer;


import by.kovalski.numberdefinition.entity.Layer;

@FunctionalInterface
public interface LayerConfigWriter {
    void writeConfiguration(Layer layer, String path);
}
