package dev.idiofyis.bytecode.structure.field;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;

public record FieldAnalysis(ClassAnalysis owner, String name) {}