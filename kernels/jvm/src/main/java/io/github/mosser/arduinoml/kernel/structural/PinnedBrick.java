package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.behavioral.Type;
import io.github.mosser.arduinoml.kernel.language.Typed;

public abstract class PinnedBrick extends Brick implements Typed {
    private int pin;
    private boolean analogMode;

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isAnalogMode() {
        return analogMode;
    }

    public void setAnalogMode(boolean analogMode) {
        this.analogMode = analogMode;
    }

    public Type getType() {
        return isAnalogMode() ? Type.INTEGER : Type.DIGITAL;
    }
}
