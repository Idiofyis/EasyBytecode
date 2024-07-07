package dev.idiofyis.bytecode.structure.method.instruction.instructions;

import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import org.objectweb.asm.tree.MethodInsnNode;

public record MethodInstruction(MethodInsnNode node, int opcode, InstructionBlock block) implements Instruction<MethodInsnNode> {
    @Override
    public int hashCode() {
        return node().hashCode();
    }
}