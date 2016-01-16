package io.github.mosser.arduinoml.kernel.language;

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public interface Expression extends Typed {
    void expression(Visitor visitor);
}
