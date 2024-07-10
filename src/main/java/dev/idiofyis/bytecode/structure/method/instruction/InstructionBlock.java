package dev.idiofyis.bytecode.structure.method.instruction;

import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import org.objectweb.asm.tree.LabelNode;

import java.util.Collection;

public record InstructionBlock(Instruction<LabelNode> label, Collection<Instruction<?>> instructions, MethodAnalysis owner) {}