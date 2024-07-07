package dev.idiofyis.bytecode.structure.program.tweaker;

import java.util.jar.Manifest;

public interface ManifestTweaker {
    ManifestTweaker DEFAULT = (manifest) -> manifest;

    Manifest tweak(Manifest manifest);
}