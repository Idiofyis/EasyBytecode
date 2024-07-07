package dev.idiofyis.bytecode.tool.refactor;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.field.FieldAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.instructions.MethodInstruction;
import dev.idiofyis.bytecode.structure.program.Program;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class NameRefactorService {

    private final Program program;

    @Inject
    public NameRefactorService(Program program) {
        this.program = program;
    }

    public ClassAnalysis refactorName(String oldName, String name) throws IOException {
        // TODO: This is slow as shit (probably?), analysis should, in the future, pre compute all references
        ClassAnalysis refactored = null;
        for (ClassAnalysis klass : program.classes()) {
            if (klass.node().name.equals(oldName)) {
                klass.node().name = name;
                refactored = klass;
            }
            for (MethodAnalysis method : klass.methods()) {
                for (Instruction<?> instruction : method.instructions()) {
                    if (instruction instanceof MethodInstruction inst) {
                        if (inst.node().owner.equals(oldName)) {
                            inst.node().owner = name;
                        }
                    }
                }
            }
        }
        return refactored;
    }

    public void totalRefactor(Function<ClassAnalysis, String> nameFunction) {
        Map<ClassAnalysis, String> nameMap = new HashMap<>();

        // Incase the function uses random instead object traits
        for (ClassAnalysis klass : program.classes()) {
            nameMap.put(klass, nameFunction.apply(klass));
        }
        for (ClassAnalysis klass : program.classes()) {
            for (MethodAnalysis method : klass.methods()) {
                for (Instruction<?> instruction : method.instructions()) {
                    if (instruction instanceof MethodInstruction inst) {

                        for (ClassAnalysis classAnalysis : program.classes()) {
                            if (inst.node().owner.equals(classAnalysis.name())) {
                                inst.node().owner = nameMap.get(classAnalysis);
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (ClassAnalysis klass : program.classes()) {
            klass.node().name = nameMap.get(klass);
        }
    }

    public void refactorName(MethodAnalysis methodAnalysis, String name) {

    }

    public void refactorName(FieldAnalysis fieldAnalysis, String name) {

    }

}