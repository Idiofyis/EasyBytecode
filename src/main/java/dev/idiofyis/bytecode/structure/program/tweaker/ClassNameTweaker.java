package dev.idiofyis.bytecode.structure.program.tweaker;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;

public interface ClassNameTweaker {

    ClassNameTweaker DEFAULT = ClassAnalysis::name;

    String tweak(ClassAnalysis classAnalysis);
}