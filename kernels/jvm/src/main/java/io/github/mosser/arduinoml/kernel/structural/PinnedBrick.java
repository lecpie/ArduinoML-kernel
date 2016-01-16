package io.github.mosser.arduinoml.kernel.structural;

public abstract class PinnedBrick extends Brick {
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
}
