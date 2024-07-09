package dev.idiofyis.bytecode.tool.refactor;

import dev.idiofyis.bytecode.mapping.ClassMappingGenerator;
import dev.idiofyis.bytecode.mapping.FieldMappingGenerator;
import dev.idiofyis.bytecode.mapping.MethodMappingGenerator;
import dev.idiofyis.bytecode.structure.field.FieldAnalysis;
import dev.idiofyis.bytecode.structure.field.FieldAnalysisFactory;
import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodFactory;
import dev.idiofyis.bytecode.structure.program.Program;
import jakarta.inject.Inject;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.SimpleRemapper;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

public final class NameRefactorService {

    private final Program program;
    private final MethodFactory methodFactory;
    private final FieldAnalysisFactory fieldFactory;

    @Inject
    public NameRefactorService(Program program, MethodFactory methodFactory, FieldAnalysisFactory fieldFactory) {
        this.program = program;
        this.methodFactory = methodFactory;
        this.fieldFactory = fieldFactory;
    }

    public void refactorClasses(ClassMappingGenerator mappingGenerator) {
        Map<String, String> map = new HashMap<>(this.program.classes().size());
        for (ClassAnalysis klass : program.classes()) {
            map.put(klass.name(), mappingGenerator.generateMapping(klass));
        }
        refactorClasses(map);
    }

    public void refactorClasses(Map<String, String> map) {
        final SimpleRemapper remapper = new SimpleRemapper(map);
        for (ClassAnalysis klass : program.classes()) {
            ClassNode node = klass.node();
            ClassNode copy = new ClassNode();
            ClassRemapper adapter = new ClassRemapper(copy, remapper);
            node.accept(adapter);

            copy.sourceFile = null;
            klass.setClass(copy, this.methodFactory, this.fieldFactory);
        }
    }

    public void refactorMethods(Map<MethodAnalysis, String> map) {
        Map<String, String> remapping = new HashMap<>(map.size());
        for (Map.Entry<MethodAnalysis, String> en : map.entrySet()) {
            MethodAnalysis m = en.getKey();
            String owner = m.owner().node().name;
            String name = m.node().name;
            String desc = m.node().desc;
            remapping.put(toMethodMapping(owner, name, desc), map.get(m));
        }
        refactorClasses(remapping);
    }


    public void refactorMethods(MethodMappingGenerator mappingGenerator) {
        Map<String, String> remapping = new HashMap<>();
        for (ClassAnalysis c : program.classes()) {
            for (MethodAnalysis m : c.methods()) {
                String owner = m.owner().node().name;
                String name = m.node().name;
                String desc = m.node().desc;
                remapping.put(toMethodMapping(owner, name, desc), mappingGenerator.generateMapping(m));
            }
        }
        refactorClasses(remapping);
    }

    public void refactorFields(FieldMappingGenerator mappingGenerator) {
        Map<String, String> remapping = new HashMap<>();
        for (ClassAnalysis c : program.classes()) {
            for (FieldAnalysis f : c.fields()) {
                String owner = f.owner().node().name;
                String name = f.node().name;
                remapping.put(toFieldMapping(owner, name), mappingGenerator.generateMapping(f));
            }
        }
        refactorClasses(remapping);
    }


    // I couldnt find this anywhere in ASM documentation
    // But if you look at the source of SimpleRemapper, this is how it stores a method mapping
    private String toMethodMapping(String owner, String name, String desc) {
        return owner + '.' + name + desc;
    }

    private String toFieldMapping(String owner, String name) {
        return owner + '.' + name;
    }

}