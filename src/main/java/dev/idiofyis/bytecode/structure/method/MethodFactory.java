package dev.idiofyis.bytecode.structure.method;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionFactory;
import dev.idiofyis.bytecode.structure.method.instruction.instructions.LabelInstruction;
import dev.idiofyis.bytecode.util.CollectionUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Singleton
public final class MethodFactory {

    private final InstructionFactory instructionFactory;

    @Inject
    public MethodFactory(InstructionFactory instructionFactory) {
        this.instructionFactory = instructionFactory;
    }

    public MethodAnalysis createMethodAnalysis(MethodNode methodNode, ClassAnalysis classAnalysis) {
        Collection<InstructionBlock> blocks = new HashSet<>();
        Collection<Instruction<?>> totalInstructions = new HashSet<>();
        MethodAnalysis analysis = new MethodAnalysis(classAnalysis, methodNode, totalInstructions, blocks);

        for (Collection<AbstractInsnNode> block : CollectionUtils.trimTill(methodNode.instructions, HashSet::new, HashSet::new, (o) -> o instanceof LabelNode)) {
            Iterator<AbstractInsnNode> it = block.iterator();

            AbstractInsnNode node = it.next();
            if (node instanceof LabelNode label) {
                Collection<Instruction<?>> instructions = new HashSet<>();
                LabelInstruction labelInstruction = new LabelInstruction(label, label.getOpcode(), null);
                InstructionBlock instructionBlock = new InstructionBlock(labelInstruction, instructions, analysis);

                int i = 1;
                while (it.hasNext()) {
                    Instruction<?> in = this.instructionFactory.createInstruction(instructionBlock, it.next());
                    instructions.add(in);
                    totalInstructions.add(in);
                }
                labelInstruction.setBlock(instructionBlock);

                blocks.add(instructionBlock);
            } else {
                Collection<Instruction<?>> instructions = new HashSet<>();
                InstructionBlock instructionBlock = new InstructionBlock(null, instructions, analysis);

                while (it.hasNext()) {
                    AbstractInsnNode n = it.next();
                    Instruction<?> in = this.instructionFactory.createInstruction(instructionBlock, n);
                    instructions.add(in);
                    totalInstructions.add(in);
                }
                blocks.add(instructionBlock);
            }
        }

        return analysis;
    }
}