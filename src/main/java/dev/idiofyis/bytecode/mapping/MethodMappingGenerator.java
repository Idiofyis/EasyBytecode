package dev.idiofyis.bytecode.mapping;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;

public interface MethodMappingGenerator {
    String generateMapping(MethodAnalysis methodAnalysis);
}