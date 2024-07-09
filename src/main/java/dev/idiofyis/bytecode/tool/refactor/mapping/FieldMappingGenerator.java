package dev.idiofyis.bytecode.tool.refactor.mapping;

import dev.idiofyis.bytecode.structure.field.FieldAnalysis;

public interface FieldMappingGenerator {
    String generateMapping(FieldAnalysis fieldAnalysis);
}