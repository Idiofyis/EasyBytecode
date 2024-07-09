package dev.idiofyis.bytecode.test.refactor;

import dev.idiofyis.bytecode.structure.field.FieldAnalysisFactory;
import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.tool.refactor.NameRefactorService;
import dev.idiofyis.bytecode.structure.klass.ClassAnalysisFactory;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionFactory;
import dev.idiofyis.bytecode.structure.program.Program;
import dev.idiofyis.bytecode.structure.program.ProgramCompiler;
import dev.idiofyis.bytecode.structure.program.ProgramFactory;

import java.io.File;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        InstructionFactory instructionFactory = new InstructionFactory();
        MethodFactory methodFactory = new MethodFactory(instructionFactory);
        FieldAnalysisFactory fieldAnalysisFactory = new FieldAnalysisFactory();

        ProgramFactory programFactory = new ProgramFactory(new ClassAnalysisFactory(methodFactory));
        File file = new File("workspace/main.jar");
        Program program = programFactory.createProgram(file);

        NameRefactorService nameRefactorService = new NameRefactorService(program, methodFactory, fieldAnalysisFactory);
        nameRefactorService.refactorMethods((m) -> {
            if (m.node().name.equals("e")) {
                return "o";
            }
            return m.node().name;
        });

        ProgramCompiler compiler = ProgramCompiler.builder().build();
        compiler.compileAndWrite(new File("workspace/output.jar"), program);
    }
}
