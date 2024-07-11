package dev.idiofyis.bytecode.structure.program;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.klass.ClassAnalysisFactory;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ProgramFactory {

    private final ClassAnalysisFactory classFactory;

    public ProgramFactory(ClassAnalysisFactory classFactory) {
        this.classFactory = classFactory;
    }

    /**
     *
     * Automatically resolves the dependencies for a factory.
     *
     * @return A new program factory.
     */
    public static Program of(File file) throws Exception {
        return new ProgramFactory(new ClassAnalysisFactory(new MethodFactory(new InstructionFactory(null)))).createProgram(file);
    }

    public Program createProgram(File file) throws Exception {
        final JarFile jarFile = new JarFile(file);
        final Object mainName = jarFile.getManifest().getMainAttributes().getValue("Main-Class");

        final Collection<ClassAnalysis> classes = new HashSet<>();
        final Collection<JarEntry> resources = new HashSet<>();

        jarFile.entries().asIterator().forEachRemaining(entry -> {
            if (entry.isDirectory()) {
                return;
            }
            if (entry.getName().endsWith(".class")) {
                ClassAnalysis clazz;
                try {
                    clazz = this.classFactory.createClassAnalysis(entry, jarFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                classes.add(clazz);
            } else {
                resources.add(entry);
            }
        });

        return new Program(file.getName(), jarFile, classes,
                classes.stream()
                .filter(clazz -> clazz.computeDotName().equals(mainName))
                .findFirst(),
                resources
        );
    }

}
