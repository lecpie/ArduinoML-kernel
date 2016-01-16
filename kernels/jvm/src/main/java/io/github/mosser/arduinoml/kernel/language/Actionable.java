package io.github.mosser.arduinoml.kernel.language;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public interface Actionable {
    void action(Visitor visitor);
}
