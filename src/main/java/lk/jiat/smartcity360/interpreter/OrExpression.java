package lk.jiat.smartcity360.interpreter;

import java.util.Map;

/** NonterminalExpression: true if either side is true */
public class OrExpression implements Expression {
    private final Expression left, right;

    public OrExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Map<String, Object> context) {
        return left.interpret(context) || right.interpret(context);
    }
}