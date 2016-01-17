package io.github.mosser.arduinoml.kernel.language;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

/**
 * Created by lecpie on 1/17/16.
 */
public interface Includable {
    void include (Visitor visitor);
}
