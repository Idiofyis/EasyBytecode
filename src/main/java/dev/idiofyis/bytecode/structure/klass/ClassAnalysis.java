package dev.idiofyis.bytecode.structure.klass;

import dev.idiofyis.bytecode.structure.field.FieldAnalysis;
import dev.idiofyis.bytecode.structure.field.FieldAnalysisFactory;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Collection;
import java.util.HashSet;

public final class ClassAnalysis {

    private ClassNode node;
    private Collection<MethodAnalysis> methods;
    private Collection<FieldAnalysis> fields;

    public ClassAnalysis(ClassNode node, Collection<MethodAnalysis> methods, Collection<FieldAnalysis> fields) {
        this.node = node;
        this.methods = methods;
        this.fields = fields;
    }

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

    // Essentially creates a new analysis but under the same object
    public void setClass(ClassNode node, MethodFactory mFactory, FieldAnalysisFactory fFactory) {
        this.node = node;
        this.methods = new HashSet<>();
        for (MethodNode method : this.node.methods) {
            methods.add(mFactory.createMethodAnalysis(method, this));
        }

        this.fields = new HashSet<>();
        for (FieldNode field : this.node.fields) {
            this.fields.add(fFactory.createFieldAnalysis(this, field));
        }
    }

    public ClassNode node() {
        return this.node;
    }

    public Collection<FieldAnalysis> fields() {
        return fields;
    }

    public Collection<MethodAnalysis> methods() {
        return methods;
    }
}