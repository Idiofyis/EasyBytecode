package dev.idiofyis.bytecode.tool.refactor.mapping;

import dev.idiofyis.bytecode.structure.method.MethodAnalysis;

public interface MethodMappingGenerator {
    String generateMapping(MethodAnalysis methodAnalysis);
}