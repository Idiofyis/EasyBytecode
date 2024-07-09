package dev.idiofyis.bytecode.structure.method.instruction.instructions;

import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;

public record IndyInstruction(InvokeDynamicInsnNode node, int opcode, InstructionBlock block) implements Instruction<InvokeDynamicInsnNode> {
    @Override
    public int hashCode() {
        return node.hashCode();
    }
}