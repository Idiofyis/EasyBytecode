package dev.idiofyis.bytecode.structure.field;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;

public record FieldAnalysis(ClassAnalysis owner, FieldNode node) {
    public String name() {
        return node.name;
    }
}