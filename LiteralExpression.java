public class LiteralExpression implements Expression {

    double val;

    public double evaluate(double x) {
        return val;
    }

    public LiteralExpression(String str){
        val = Float.parseFloat(str);
    }

    public void convertToString (StringBuilder stringBuilder, int indentLevel){
        indent(stringBuilder,indentLevel);
        stringBuilder.append(val);
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
