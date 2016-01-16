package io.github.mosser.arduinoml.kernel.language;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public interface Visitable {

	public void accept(Visitor visitor);

}
