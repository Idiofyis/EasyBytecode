package dev.idiofyis.bytecode.structure.method.instruction;

import dev.idiofyis.bytecode.structure.method.instruction.instructions.LDCInstruction;
import dev.idiofyis.bytecode.structure.method.instruction.instructions.LabelInstruction;
import dev.idiofyis.bytecode.structure.method.instruction.instructions.MethodInstruction;
import dev.idiofyis.bytecode.tool.string.ProgramStrings;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class InstructionFactory {

    // Intelij refuses to let me use pattern switching regardless of the java version and the enabling of preview features
    private final Map<Class<?>, BiFunction<AbstractInsnNode, InstructionBlock, Instruction<?>>> instructionFunction;

    private final BiFunction<AbstractInsnNode, InstructionBlock, Instruction<?>> defaultFunction;

    private final ProgramStrings programStrings;

    public InstructionFactory() {
        this(null);
    }

    public InstructionFactory(ProgramStrings programStrings) {
        this.programStrings = programStrings;
        instructionFunction = new HashMap<>();
        instructionFunction.put(LdcInsnNode.class, (node, m) -> {
            if (((LdcInsnNode)node).cst instanceof String s) {
                programStrings.cache(s, m);
            }
            return new LDCInstruction(((LdcInsnNode) node).cst, Opcodes.LDC, (LdcInsnNode) node, m);
        });
        instructionFunction.put(MethodInsnNode.class, (node, m) -> new MethodInstruction(((MethodInsnNode) node), node.getOpcode(), m));
        instructionFunction.put(LabelInstruction.class, (node, block) -> new LabelInstruction((LabelNode) node, node.getOpcode(), block));
        defaultFunction = (node, m) -> new Instruction<>() {
            @Override
            public int opcode() {
                return node.getOpcode();
            }

            @Override
            public AbstractInsnNode node() {
                return node;
            }

            @Override
            public InstructionBlock block() {
                return m;
            }
        };
    };

    public Instruction<?> createInstruction(InstructionBlock block, AbstractInsnNode node) {
        return instructionFunction.getOrDefault(node.getClass(), defaultFunction).apply(node, block);
    }
}