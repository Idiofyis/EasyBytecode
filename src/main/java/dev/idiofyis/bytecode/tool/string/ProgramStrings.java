package dev.idiofyis.bytecode.tool.string;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.method.MethodAnalysis;
import dev.idiofyis.bytecode.structure.method.instruction.Instruction;
import dev.idiofyis.bytecode.structure.method.instruction.InstructionBlock;
import dev.idiofyis.bytecode.structure.program.Program;
import org.objectweb.asm.tree.LdcInsnNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

public final class ProgramStrings {

    private final Collection<StringSource> cache;

    public ProgramStrings() {
        this.cache = new HashSet<>();
    }

    public Collection<StringSource> strings() {
        return cache;
    }

    /**
     *
     * @deprecated This class should now be used as an instance and will cache strings when they are initially registered.
     */
    @Deprecated
    public static Collection<StringSource> stringsToCollection(Program program, Supplier<Collection<StringSource>> collectionSupplier) {
        Collection<StringSource> collection = collectionSupplier.get();

        for (ClassAnalysis klass : program.classes()) {
            for (MethodAnalysis method : klass.methods()) {
                for (InstructionBlock block : method.blocks()) {
                    for (Instruction instruction : block.instructions()) {
                        if (instruction.node() instanceof LdcInsnNode ldc) {
                            if (ldc.cst instanceof String s) {
                                collection.add(new StringSource(block, s));
                            }
                        }
                    }
                }
            }
        }

        return collection;
    }

    public void cache(String s, InstructionBlock block) {
        cache.add(new StringSource(block, s));
    }

    public record StringSource(InstructionBlock block, String s) {
        @Override
        public String toString() {
            return "Class: " + block.owner().owner().name() + ", Method: " + block.owner().node().name + ", String: " + s;
        }
    }

}