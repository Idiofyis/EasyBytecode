package dev.idiofyis.bytecode.structure.program;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public record Program(String name, JarFile jar, Collection<ClassAnalysis> classes, Optional<ClassAnalysis> main,
                      Collection<JarEntry> resources) {
    /**
     * Updates the main class in the Jar's manifest.
     *
     * @param classAnalysis
     */
    public void setMainClass(ClassAnalysis classAnalysis) throws IOException {
        jar.getManifest().getMainAttributes().putValue("Main-Class", classAnalysis.computeDotName());
    }

    public void addClass(ClassAnalysis classAnalysis) {
        this.classes.add(classAnalysis);
    }
}