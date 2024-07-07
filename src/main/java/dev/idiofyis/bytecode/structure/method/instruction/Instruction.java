package dev.idiofyis.bytecode.structure.method.instruction;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;

public interface Instruction<T extends AbstractInsnNode> {
    int opcode();
    T node();
    InstructionBlock block();
}
