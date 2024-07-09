package dev.idiofyis.bytecode.mapping;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;

public interface ClassMappingGenerator {
    String generateMapping(ClassAnalysis classAnalysis);
}