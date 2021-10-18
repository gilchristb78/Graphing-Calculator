public class VariableExpression implements  Expression{
    char var;
    public double evaluate(double x) {
        return x;
    }
    public void convertToString (StringBuilder stringBuilder, int indentLevel){
        indent(stringBuilder,indentLevel);
        stringBuilder.append(var);
        if(indentLevel == 0)
            stringBuilder.append('\n');
    }
    public VariableExpression(){
        var = 'x';
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
