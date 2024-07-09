package dev.idiofyis.bytecode.mapping;

import dev.idiofyis.bytecode.structure.field.FieldAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;

public interface FieldMappingGenerator {
    String generateMapping(FieldAnalysis fieldAnalysis);
}