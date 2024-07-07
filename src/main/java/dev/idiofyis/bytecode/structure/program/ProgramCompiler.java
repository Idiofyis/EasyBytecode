package dev.idiofyis.bytecode.structure.program;

import dev.idiofyis.bytecode.structure.klass.ClassAnalysis;
import dev.idiofyis.bytecode.structure.program.tweaker.*;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public final class ProgramCompiler {

    private final ManifestTweaker manifestTweaker;

    private final ClassNameTweaker classNameTweaker;

    private final ClassWatermarker watermarker;

    private final ClassByteTweaker byteTweaker;

    private final int verify;

    private final String jarComment;

    private ProgramCompiler(final ManifestTweaker manifestTweaker, ClassNameTweaker classNameTweaker, ClassWatermarker watermarker, ClassByteTweaker byteTweaker, int verify, String jarComment) {
        this.manifestTweaker = manifestTweaker;
        this.classNameTweaker = classNameTweaker;
        this.watermarker = watermarker;
        this.byteTweaker = byteTweaker;
        this.verify = verify;
        this.jarComment = jarComment;
    }

    public void compileAndWrite(File output, Program program) throws IOException {
        // Ensure Manifest is accurate
        if (program.main().isPresent()) {
            program.setMainClass(program.main().get());
        }

        FileOutputStream fos = new FileOutputStream(output);

        Manifest manifest = this.manifestTweaker.tweak(program.jar().getManifest());


        JarOutputStream jos = new JarOutputStream(fos, manifest);
        for (ClassAnalysis klass : program.classes()) {
            ZipEntry zipEntry = new ZipEntry(this.classNameTweaker.tweak(klass) + ".class");
            ClassWriter writer = new FallbackClassWriter(this.verify);

            try {
                klass.node().accept(writer);
            } catch (Exception e) {
                throw new RuntimeException("Class Verification Failed: " + e.getMessage());
            }

            this.watermarker.writeWatermark(writer, klass);

            final byte[] bytes = this.byteTweaker.tweak(writer.toByteArray(), klass);

            jos.putNextEntry(zipEntry);
            jos.write(bytes);
            jos.closeEntry();
            jos.flush();
        }

        if (this.jarComment != null) {
            jos.setComment(this.jarComment);
        }
        jos.close();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ManifestTweaker manifestTweaker;

        private ClassNameTweaker classNameTweaker;

        private ClassWatermarker watermarker;

        private ClassByteTweaker byteTweaker;

        private int verify;
        private String jarComment;

        private Builder() {
            this.classNameTweaker = ClassNameTweaker.DEFAULT;
            this.manifestTweaker = ManifestTweaker.DEFAULT;
            this.watermarker = ClassWatermarker.DEFAULT;
            this.byteTweaker = ClassByteTweaker.DEFAULT;
        }

        public Builder withManifestTweaker(ManifestTweaker manifestTweaker) {
            this.manifestTweaker = manifestTweaker;
            return this;
        }

        public Builder withClassNameTweaker(ClassNameTweaker classNameTweaker) {
            this.classNameTweaker = classNameTweaker;
            return this;
        }

        public Builder withWatermarker(ClassWatermarker watermarker) {
            this.watermarker = watermarker;
            return this;
        }

        public Builder withByteTweaker(ClassByteTweaker byteTweaker) {
            this.byteTweaker = byteTweaker;
            return this;
        }

        public Builder verifyClasses() {
            this.verify = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
            return this;
        }

        public Builder withJarComment(String jarComment) {
            this.jarComment = jarComment;
            return this;
        }

        public ProgramCompiler build() {
            return new ProgramCompiler(this.manifestTweaker, classNameTweaker, this.watermarker, byteTweaker, verify, jarComment);
        }

    }

}