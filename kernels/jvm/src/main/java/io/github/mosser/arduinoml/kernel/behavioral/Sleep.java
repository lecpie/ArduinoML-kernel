package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.language.Actionable;

/**
 * Created by lecpie on 1/26/16.
 */
public class Sleep implements Actionable {
    int delay;

    @Override
    public void action(Visitor visitor) {
        visitor.action(this);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
