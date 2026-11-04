package lk.jiat.smartcity360.interpreter;

import java.util.Map;

/** NonterminalExpression: true only if both sides are true */
public class AndExpression implements Expression {
    private final Expression left, right;

    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Map<String, Object> context) {
        return left.interpret(context) && right.interpret(context);
    }
}