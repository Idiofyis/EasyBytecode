# EasyBytecode

- **Latest Release**
  * **[1.0](https://github.com/Idiofyis/EasyBytecode/releases/tag/1.0)**

## Overview

EasyBytecode simplifies the annoying steps required when working with Bytecode through the ASM library. Things like decompiling and compiling jars, refactoring, and structural analysis are made simple with this library.

## User Guide

EasyBytecode is a simple solution to common boilerplate in projects that use bytecode. Everything is simple. EasyBytecode supports the use of injection libraries through the Jakarta API, but it is not necessary.

### Load Program
To begin manipulating a jar, simply invoke the static "of" method from the ProgramFactory class.
```java
File jar = new File("my/directory/program.jar);
Program program = ProgramFactory.of(jar);
```
From there, the EasyBytecode will decompile and wrap the Bytecode instructions.

### Refactoring
- Classes
  * **
  ```java
  NameRefactorService nameRefactorService = new NameRefactorService(program, new MethodFactory(new InstructionFactory()), new FieldAnalysisFactory());
  // All classes are now their names in complete uppercase, eg Main -> MAIN
  nameRefactorService.refactorClasses((klass) -> klass.name().toUpperCase());

  // Alternatively, you can refactor specific classes like this
  nameRefactorService.refactor(Map.of("dev/idiofyis/test/Main", "MAIN"));
  ```
- Methods
  * **
  ```java
  NameRefactorService nameRefactorService = new NameRefactorService(program, new MethodFactory(new InstructionFactory()), new FieldAnalysisFactory());
  // All methods are now their names in complete uppercase, eg main -> MAIN
  nameRefactorService.refactorMethods((method) -> method.name().toUpperCase());

  // Alternatively, you can refactor specific methods like this, however you have to specify the descriptor after the name, in this case the string parameter
  nameRefactorService.refactor(Map.of("dev/idiofyis/Printer.print(Ljava/lang/String;)", "pront"));
  ```

### Compile Modified Program

After your desired modifications are finished, you can use the ProgramCompiler to simplify compiling and saving the modified program.

```java
// Using the Compiler builder, you can inject several tweakers and configure how the compiler verifies the bytecode.
ProgramCompiler compiler = ProgramCompiler.builder()...build();
compiler.compileAndWrite(new File("my/directory/output.jar"), program);
```
