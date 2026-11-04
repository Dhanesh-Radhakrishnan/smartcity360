package lk.jiat.smartcity360.interpreter;

import java.util.Map;

/** TerminalExpression: one check such as noise_db > 55 */
public class ComparisonExpression implements Expression {
    private final String variable;
    private final String operator;
    private final Object value;   // Double or String

    public ComparisonExpression(String variable, String operator, Object value) {
        this.variable = variable;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean interpret(Map<String, Object> context) {
        Object actual = context.get(variable);
        if (actual == null) {
            throw new IllegalArgumentException("Missing value for '" + variable + "'");
        }
        if (value instanceof Double expected) {
            double a = ((Number) actual).doubleValue();
            return switch (operator) {
                case "==" -> a == expected;
                case "!=" -> a != expected;
                case ">"  -> a > expected;
                case "<"  -> a < expected;
                case ">=" -> a >= expected;
                default   -> a <= expected;   // "<="
            };
        }
        boolean same = String.valueOf(actual).equalsIgnoreCase((String) value);
        return operator.equals("==") == same;
    }
}