package io.github.mosser.arduinoml.kernel.visitable;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

public interface Visitable {

	public void accept(Visitor visitor);

}
