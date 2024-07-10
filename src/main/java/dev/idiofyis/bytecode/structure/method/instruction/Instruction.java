package dev.idiofyis.bytecode.structure.method.instruction;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;

public final class Instruction<T extends AbstractInsnNode> {

    private InstructionBlock block;

    private final T node;

    public Instruction(InstructionBlock block, T node) {
        this.block = block;
        this.node = node;
    }

    public InstructionBlock block() {
        return block;
    }

    public void setBlock(InstructionBlock block) {
        this.block = block;
    }

    public T node() {
        return node;
    }
}
