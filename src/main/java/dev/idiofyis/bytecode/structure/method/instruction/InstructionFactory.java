package dev.idiofyis.bytecode.structure.method.instruction;

import dev.idiofyis.bytecode.structure.method.instruction.instructions.*;
import dev.idiofyis.bytecode.tool.string.ProgramStrings;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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
                if (programStrings != null) {
                    programStrings.cache(s, m);
                }
            }
            return new LDCInstruction(Opcodes.LDC, (LdcInsnNode) node, m);
        });
        instructionFunction.put(MethodInsnNode.class, (node, m) -> new MethodInstruction(((MethodInsnNode) node), node.getOpcode(), m));
        instructionFunction.put(LabelNode.class, (node, block) -> new LabelInstruction((LabelNode) node, node.getOpcode(), block));
        instructionFunction.put(InvokeDynamicInsnNode.class, (node, block) -> new IndyInstruction((InvokeDynamicInsnNode) node, node.getOpcode(), block));
        instructionFunction.put(FieldInsnNode.class, (node, block) -> new FieldInstruction((FieldInsnNode) node, node.getOpcode(), block));
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