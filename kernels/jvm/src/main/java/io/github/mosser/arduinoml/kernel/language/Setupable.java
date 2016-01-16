package io.github.mosser.arduinoml.kernel.language;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public interface Setupable {
    void setup(Visitor visitor);
}
