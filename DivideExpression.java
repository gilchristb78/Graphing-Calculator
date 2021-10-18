public class DivideExpression implements Expression{
    Expression child1;
    Expression child2;

    public DivideExpression(Expression c1, Expression c2){
        child1 = c1;
        child2 = c2;
    }
    public double evaluate(double x) {
        return child1.evaluate(x) / child2.evaluate(x);
    }
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
    }
    public String convertToString(int indentLevel) {
        return null;
    }


}
