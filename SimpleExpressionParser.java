import java.util.function.*;

/**
 * TODO: Implement this class.
 */
public class SimpleExpressionParser implements ExpressionParser {
	/*
	 * Grammar:
	 * //S -> A | P
	 * A -> A+M | A-M | M
	 * M -> M*E | M/E | E
	 * E -> P^E | P
	 * P -> (S) | L | V
	 * L -> <float>
	 * V -> x
	 */


	public Expression parse (String str) throws ExpressionParseException {
		str = str.replaceAll(" ", "");
		Expression expression = parseStartExpression(str);
		if (expression == null) {
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}
		return expression;
	}
	
	protected Expression parseStartExpression (String str) {
		Expression expression = null;
		expression = parseAddExpression(str); // check +/-
		if(expression == null)
			expression = parseParenthesisExpression(str); //if +/- dont work go to ()
		return expression;
	}

	protected Expression parseAddExpression (String str) {
		Expression expression = null;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '+' &&
					(parseAddExpression(str.substring(0, i)) != null) /* before + */ &&
					(parseMultiplyExpression(str.substring(i+1)) != null)) /* after + */ {
				expression = new AddExpression(parseAddExpression(str.substring(0, i)),parseMultiplyExpression(str.substring(i+1)));
				return expression;
			}
		}

		expression = parseSubExpression(str); // if no + check -
		if(expression == null) //if no - check *
			expression = parseMultiplyExpression(str);

		return expression;
	}

	protected Expression parseSubExpression (String str) {
		Expression expression = null;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '-' &&
					parseAddExpression(str.substring(0, i)) != null /* before + */ &&
					parseMultiplyExpression(str.substring(i+1)) != null) /* after + */ {
				expression = new SubExpression(parseAddExpression(str.substring(0, i)),parseMultiplyExpression(str.substring(i+1)));
				return expression;
			}
		}
		return null;
	}

	protected Expression parseMultiplyExpression (String str) {
		Expression expression = null;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '*' &&
					parseMultiplyExpression(str.substring(0, i)) != null /* before + */ &&
					parseExponentExpression(str.substring(i+1)) != null) /* after + */ {
				expression = new MultiplyExpression(parseMultiplyExpression(str.substring(0, i)),parseExponentExpression(str.substring(i+1)));
				return expression;
			}
		}

		expression = parseDivideExpression(str);
		if (expression == null)
			expression = parseExponentExpression(str);
		return expression;
	}

	protected Expression parseDivideExpression (String str) {
		Expression expression = null;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '/' &&
					parseMultiplyExpression(str.substring(0, i)) != null /* before + */ &&
					parseExponentExpression(str.substring(i+1)) != null) /* after + */ {
				expression = new DivideExpression(parseMultiplyExpression(str.substring(0, i)),parseExponentExpression(str.substring(i+1)));
				return expression;
			}
		}
		return null;
	}

	protected Expression parseExponentExpression (String str) {
		Expression expression = null;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '^' &&
					parseParenthesisExpression(str.substring(0, i)) != null /* before + */ &&
					parseExponentExpression(str.substring(i+1)) != null) /* after + */ {
				expression = new ExponentialExpression(parseParenthesisExpression(str.substring(0, i)),parseExponentExpression(str.substring(i+1)));
				return expression;
			}
		}
		expression = parseParenthesisExpression(str);
		return expression;
	}

	protected Expression parseParenthesisExpression (String str) {
		Expression expression = null;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(')
				for (int j = str.length() - 1; j > i; j--) {
					if (str.charAt(j) == ')' &&
							parseStartExpression(str.substring(i+1,j)) != null){
						expression = new ParenthesisExpression(parseStartExpression(str.substring(i+1,j)));
						return expression;
					}
				}
		}
		expression = parseLiteralExpression(str);
		if(expression == null)
			expression = parseVariableExpression(str);
		return expression;
	}

	protected Expression parseVariableExpression (String str) {
		if (str.equals("x")) {
			return new VariableExpression();
		}
		return null;
	}

	protected LiteralExpression parseLiteralExpression (String str) {
		// From https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double/22936891:
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
		    ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
		    "[+-]?(" +         // Optional sign character
		    "NaN|" +           // "NaN" string
		    "Infinity|" +      // "Infinity" string

		    // A decimal floating-point string representing a finite positive
		    // number without a leading sign has at most five basic pieces:
		    // Digits . Digits ExponentPart FloatTypeSuffix
		    // 
		    // Since this method allows integer-only strings as input
		    // in addition to strings of floating-point literals, the
		    // two sub-patterns below are simplifications of the grammar
		    // productions from the Java Language Specification, 2nd 
		    // edition, section 3.10.2.

		    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
		    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

		    // . Digits ExponentPart_opt FloatTypeSuffix_opt
		    "(\\.("+Digits+")("+Exp+")?)|"+

		    // Hexadecimal strings
		    "((" +
		    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
		    "(0[xX]" + HexDigits + "(\\.)?)|" +

		    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
		    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

		    ")[pP][+-]?" + Digits + "))" +
		    "[fFdD]?))" +
		    "[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (str.matches(fpRegex)) {

			return new LiteralExpression(str);
		}
		return null;
	}
}
