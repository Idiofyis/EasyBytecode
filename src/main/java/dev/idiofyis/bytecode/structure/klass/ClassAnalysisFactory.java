package dev.idiofyis.bytecode.structure.klass;

import dev.idiofyis.bytecode.structure.field.FieldAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassAnalysisFactory {

    private final MethodFactory methodFactory;

    public ClassAnalysisFactory(MethodFactory methodFactory) {
        this.methodFactory = methodFactory;
    }


    public ClassAnalysis createClassAnalysis(JarEntry entry, JarFile jarFile) throws IOException {
        byte[] bytes = jarFile.getInputStream(entry).readAllBytes();
        ClassReader classReader = new ClassReader(bytes);
        ClassNode clazz = new ClassNode();
        classReader.accept(clazz, 0);

        Collection<MethodAnalysis> methods = new HashSet<>();
        Collection<FieldAnalysis> fields = new HashSet<>();

        final ClassAnalysis analysis = new ClassAnalysis(clazz, methods, fields);
        for (MethodNode method : clazz.methods) {
            MethodAnalysis methodAnalysis = methodFactory.createMethodAnalysis(method, analysis);
            methods.add(methodAnalysis);
        }

        return analysis;
    }
}
