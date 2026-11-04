package lk.jiat.smartcity360.interpreter;

import java.util.Set;

/**
 * Client: turns rule text into an Expression tree.
 * Split on OR first, then AND, so AND automatically binds tighter than OR.
 */
public class RuleParser {
    private static final Set<String> OPS = Set.of("==", "!=", ">", "<", ">=", "<=");

    public static Expression parse(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Rule text is empty");
        }
        Expression orTree = null;
        for (String orPart : text.trim().split("(?i)\\s+OR\\s+")) {
            Expression andTree = null;
            for (String part : orPart.trim().split("(?i)\\s+AND\\s+")) {
                Expression leaf = comparison(part.trim());
                andTree = (andTree == null) ? leaf : new AndExpression(andTree, leaf);
            }
            orTree = (orTree == null) ? andTree : new OrExpression(orTree, andTree);
        }
        return orTree;
    }

    private static Expression comparison(String s) {
        String[] p = s.split("\\s+");
        if (p.length != 3) {
            throw new IllegalArgumentException("Expected 'variable operator value' but got '" + s + "'");
        }
        if (!OPS.contains(p[1])) {
            throw new IllegalArgumentException("Bad operator '" + p[1] + "'");
        }
        Object value;
        try {
            value = Double.parseDouble(p[2]);
        } catch (NumberFormatException e) {
            value = p[2];
            if (!p[1].equals("==") && !p[1].equals("!=")) {
                throw new IllegalArgumentException("Text can only use == or !=");
            }
        }
        return new ComparisonExpression(p[0].toLowerCase(), p[1], value);
    }
}