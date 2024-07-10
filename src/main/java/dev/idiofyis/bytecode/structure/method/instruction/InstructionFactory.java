package dev.idiofyis.bytecode.structure.method.instruction;

import dev.idiofyis.bytecode.tool.string.ProgramStrings;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class InstructionFactory {

    private final ProgramStrings programStrings;

    private final BiConsumer<InstructionBlock, AbstractInsnNode> preConsumer;

    public InstructionFactory() {
        this(null);
    }

    public InstructionFactory(ProgramStrings programStrings) {
        this.programStrings = programStrings;
        if (programStrings != null) {
            preConsumer = (block, node) -> {
                if (node instanceof LdcInsnNode ldc) {
                    if (ldc.cst instanceof String s) {
                        programStrings.cache(s, block);
                    }
                }
            };
        } else {
            preConsumer = (X, Y) -> {};
        }
    }

    public <T extends AbstractInsnNode> Instruction<T> createInstruction(InstructionBlock block, T node) {
        // perhaps this is slower than simply doing the literal 2 checks instead
        // but idk :3
        this.preConsumer.accept(block, node);
        return new Instruction<>(block, node);
    }
}