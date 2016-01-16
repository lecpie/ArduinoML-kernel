package io.github.mosser.arduinoml.kernel;

public class CompilationError extends RuntimeException {
    public CompilationError(String msg) {
        super(msg);
    }
}
