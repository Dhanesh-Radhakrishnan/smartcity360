package lk.jiat.smartcity360.interpreter;

import java.util.Map;

/** AbstractExpression. The Map is the Context (the facts being checked). */
public interface Expression {
    boolean interpret(Map<String, Object> context);
}