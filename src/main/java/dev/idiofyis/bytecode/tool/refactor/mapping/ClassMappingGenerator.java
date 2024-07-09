package dev.idiofyis.bytecode.tool.refactor.mapping;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;

public interface ClassMappingGenerator {
    String generateMapping(ClassAnalysis classAnalysis);
}