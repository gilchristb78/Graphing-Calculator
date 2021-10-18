public class MultiplyExpression implements Expression {
    Expression child1;
    Expression child2;

    public MultiplyExpression(Expression c1, Expression c2){
        child1 = c1;
        child2 = c2;
    }
    public double evaluate(double x) {
        return child1.evaluate(x) * child2.evaluate(x);
    }
    public void convertToString (StringBuilder stringBuilder, int indentLevel){
        indent(stringBuilder,indentLevel);
        stringBuilder.append("*");
        stringBuilder.append('\n');
        stringBuilder.append(child1.convertToString(indentLevel+1));
        stringBuilder.append('\n');
        stringBuilder.append(child2.convertToString(indentLevel+1));
        if(indentLevel == 0)
            stringBuilder.append('\n');
    }

    public String convertToString (int indentLevel) {
        final StringBuilder stringBuilder = new StringBuilder();
        convertToString(stringBuilder, indentLevel);
        return stringBuilder.toString();
    }

    /**
     * Static helper method to indent a specified number of times from the left margin, by
     * 	 * appending tab characters to the specified StringBuilder.
     * 	 * @param sb the StringBuilder to which to append tab characters.
     * @param indentLevel the number of tabs to append.
     */
    public static void indent (StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append('\t');
        }
    }


}
