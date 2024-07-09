package dev.idiofyis.bytecode.structure.field;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.Collection;
import java.util.HashSet;

public final class FieldAnalysisFactory {

    public FieldAnalysis createFieldAnalysis(ClassAnalysis analysis, FieldNode node) {
        return new FieldAnalysis(analysis, node);
    }

}