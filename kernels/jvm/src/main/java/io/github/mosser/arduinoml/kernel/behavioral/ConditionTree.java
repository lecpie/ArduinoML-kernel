package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;

/**
 * Created by lecpie on 1/30/16.
 */
public class ConditionTree extends Condition {
    private BinaryOperator nextOperator;
    private Condition next;

    public Condition getNext() {
        return next;
    }

    public void setNext(Condition next) {
        this.next = next;
    }

    public BinaryOperator getNextOperator() {
        return nextOperator;
    }

    public void setNextOperator(BinaryOperator nextOperator) {
        this.nextOperator = nextOperator;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
