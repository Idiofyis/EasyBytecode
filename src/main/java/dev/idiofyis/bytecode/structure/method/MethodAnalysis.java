package dev.idiofyis.bytecode.structure.method;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import org.objectweb.asm.tree.MethodNode;

import java.util.Collection;

public record MethodAnalysis(ClassAnalysis owner, MethodNode node, Collection<Instruction<?>> instructions, Collection<InstructionBlock> blocks) {
    public String comboName() {
        return this.node.name + this.node.desc;
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }
}