public class ParenthesisExpression implements Expression{
    Expression child;

    public ParenthesisExpression(Expression c){
        child = c;
    }
    public double evaluate(double x) {
        return child.evaluate(x);
    }

    public void convertToString (StringBuilder stringBuilder, int indentLevel){
        indent(stringBuilder,indentLevel);
        stringBuilder.append("()");
        stringBuilder.append('\n');
        stringBuilder.append(child.convertToString(indentLevel+1));
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
