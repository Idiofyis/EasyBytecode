package dev.idiofyis.bytecode.structure.method.instruction.instructions;

import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import org.objectweb.asm.tree.LabelNode;

public final class LabelInstruction implements Instruction<LabelNode> {


    private final LabelNode node;

    private final int opcode;

    private InstructionBlock block;

    public LabelInstruction(LabelNode node, int opcode, InstructionBlock block) {
        this.node = node;
        this.opcode = opcode;
        this.block = block;
    }

    @Override
    public int opcode() {
        return this.opcode;
    }

    @Override
    public LabelNode node() {
        return this.node;
    }

    @Override
    public InstructionBlock block() {
        return this.block;
    }

    public void setBlock(InstructionBlock block) {
        if (this.block != null) {
            throw new RuntimeException("WTF!");
        }
        this.block = block;
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }
}