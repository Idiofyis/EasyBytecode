package dev.idiofyis.bytecode.structure.klass;

import dev.idiofyis.bytecode.structure.field.FieldAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;

public record ClassAnalysis(ClassNode node, Collection<MethodAnalysis> methods, Collection<FieldAnalysis> fields) {

    public String name() {
        return this.node.name;
    }

    public String computeDotName() {
        return this.name().replace('/', '.');
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }
}