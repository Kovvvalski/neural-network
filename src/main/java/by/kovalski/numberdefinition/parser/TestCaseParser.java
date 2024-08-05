package by.kovalski.numberdefinition.parser;

import by.kovalski.numberdefinition.entity.TestCase;

import java.util.List;

@FunctionalInterface
public interface TestCaseParser {
    List<TestCase> ParseTestCases(String path);
}
