package dev.idiofyis.bytecode.test.strings;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysisFactory;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionFactory;
import dev.idiofyis.bytecode.structure.program.Program;
import dev.idiofyis.bytecode.structure.program.ProgramFactory;
import dev.idiofyis.bytecode.tool.string.ProgramStrings;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ProgramStrings strings = new ProgramStrings();

        ProgramFactory programFactory = new ProgramFactory(new ClassAnalysisFactory(new MethodFactory(new InstructionFactory(strings))));
        File file = new File("workspace/main.jar");
        Program program = programFactory.createProgram(file);

        System.out.println("Dumping strings");
        for (ProgramStrings.StringSource string : strings.strings()) {
            System.out.println(string);
        }
    }
}
