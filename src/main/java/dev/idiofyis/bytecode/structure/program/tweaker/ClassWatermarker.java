package dev.idiofyis.bytecode.structure.program.tweaker;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import org.objectweb.asm.ClassWriter;

public interface ClassWatermarker {

    ClassWatermarker DEFAULT = (x, y) -> {};

    void writeWatermark(ClassWriter classWriter, ClassAnalysis classAnalysis);
}
