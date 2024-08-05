package by.kovalski.numberdefinition.parser;

import by.kovalski.numberdefinition.entity.Layer;

import java.util.IllegalFormatException;

@FunctionalInterface
public interface LayerParser {
    Layer parseLayer(String path) throws IllegalFormatException;
}
