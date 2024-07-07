package dev.idiofyis.bytecode.structure.program.tweaker;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;

public interface ClassByteTweaker {

    ClassByteTweaker DEFAULT = (b, c) -> b;

    byte[] tweak(byte[] bytes, ClassAnalysis classAnalysis);
}