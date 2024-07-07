package dev.idiofyis.bytecode.structure.method.instruction;

import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.instruction.instructions.LabelInstruction;

import java.util.Collection;

public record InstructionBlock(LabelInstruction label, Collection<Instruction<?>> instructions, MethodAnalysis owner) {}