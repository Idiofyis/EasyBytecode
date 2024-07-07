package dev.idiofyis.bytecode.test.refactor;

import dev.idiofyis.bytecode.tool.refactor.NameRefactorService;
import dev.idiofyis.bytecode.structure.klass.ClassAnalysisFactory;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionFactory;
import dev.idiofyis.bytecode.structure.program.Program;
import dev.idiofyis.bytecode.structure.program.ProgramCompiler;
import dev.idiofyis.bytecode.structure.program.ProgramFactory;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        ProgramFactory programFactory = new ProgramFactory(new ClassAnalysisFactory(new MethodFactory(new InstructionFactory())));
        File file = new File("workspace/main.jar");
        Program program = programFactory.createProgram(file);

        NameRefactorService nameRefactorService = new NameRefactorService(program);
        nameRefactorService.refactorName("main", "a/b/c/AppropriateName");

        ProgramCompiler compiler = ProgramCompiler.builder().build();
        compiler.compileAndWrite(new File("workspace/output.jar"), program);
    }
}
