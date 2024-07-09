package dev.idiofyis.bytecode.structure.method.instruction.instructions;

import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import org.objectweb.asm.tree.FieldInsnNode;

public record FieldInstruction(FieldInsnNode node, int opcode, InstructionBlock block) implements Instruction<FieldInsnNode> {
    @Override
    public int hashCode() {
        return node.hashCode();
    }
}