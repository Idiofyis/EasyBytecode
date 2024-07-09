package dev.idiofyis.bytecode.structure.method.instruction.instructions;

import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

public record LDCInstruction(int opcode, LdcInsnNode node, InstructionBlock block) implements Instruction<LdcInsnNode> {
    @Override
    public int hashCode() {
        return node.hashCode();
    }

    @Override
    public String toString() {
        return node.cst.toString();
    }
}