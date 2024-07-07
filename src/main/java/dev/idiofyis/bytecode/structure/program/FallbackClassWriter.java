package dev.idiofyis.bytecode.structure.program;

import org.objectweb.asm.ClassWriter;

final class FallbackClassWriter extends ClassWriter {

    public FallbackClassWriter(int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        try {
            return super.getCommonSuperClass(type1, type2);
        } catch (Exception e) {
            return "java/lang/Object";
        }
    }


}
