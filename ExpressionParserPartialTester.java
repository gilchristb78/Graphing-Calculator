import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 4; you should definitely add more tests!
 */
public class ExpressionParserPartialTester {
	private ExpressionParser _parser;

	@Before
	/**
	 * Instantiates the actors and movies graphs
	 */
	public void setUp () throws IOException {
		_parser = new SimpleExpressionParser();
	}

	@Test
	/**
	 * Just verifies that the SimpleExpressionParser could be instantiated without crashing.
	 */
	public void finishedLoading () {
		assertTrue(true);
		// Yay! We didn't crash
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression1 () throws ExpressionParseException {
		final String expressionStr = "x+x";
		final String parseTreeStr = "+\n\tx\n\tx\n";

		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression2 () throws ExpressionParseException {
		final String expressionStr = "13*x";
		final String parseTreeStr = "*\n\t13.0\n\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression3 () throws ExpressionParseException {
		final String expressionStr = "4*(x-5*x)";
		final String parseTreeStr = "*\n\t4.0\n\t()\n\t\t-\n\t\t\tx\n\t\t\t*\n\t\t\t\t5.0\n\t\t\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}

	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression4 () throws ExpressionParseException {
		final String expressionStr = "5";
		final String parseTreeStr = "5.0\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}
	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression5 () throws ExpressionParseException {
		final String expressionStr = "((13*x))";
		final String parseTreeStr = "()\n\t()\n\t\t*\n\t\t\t13.0\n\t\t\tx\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}
	@Test
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testExpression6 () throws ExpressionParseException {
		final String expressionStr = "2^2";
		final String parseTreeStr = "^\n\t2.0\n\t2.0\n";
		assertEquals(parseTreeStr, _parser.parse(expressionStr).convertToString(0));
	}



	@Test(expected = ExpressionParseException.class) 
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException1 () throws ExpressionParseException {
		final String expressionStr = "1+2+";
		_parser.parse(expressionStr);
	}

	@Test(expected = ExpressionParseException.class) 
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException2 () throws ExpressionParseException {
		final String expressionStr = "((()))";
		_parser.parse(expressionStr);
	}

	@Test(expected = ExpressionParseException.class) 
	/**
	 * Verifies that a specific expression is parsed into the correct parse tree.
	 */
	public void testException3 () throws ExpressionParseException {
		final String expressionStr = "()()";
		_parser.parse(expressionStr);
	}

        @Test
        /**
         * Verifies that a specific expression is evaluated correctly.
         */
        public void testEvaluate1 () throws ExpressionParseException {
                final String expressionStr = "4*(x+5*x)";
                assertEquals(72, (int) _parser.parse(expressionStr).evaluate(3));
        }

        @Test
        /**
         * Verifies that a specific expression is evaluated correctly.
         */
        public void testEvaluate2 () throws ExpressionParseException {
                final String expressionStr = "x";
                assertEquals(1, (int) _parser.parse(expressionStr).evaluate(1));
				assertEquals(5, (int) _parser.parse(expressionStr).evaluate(5));
				assertEquals(7, (int) _parser.parse(expressionStr).evaluate(7));
        }

		@Test
		/**
		 * Verifies that a specific expression is parsed into the correct parse tree.
		 */
		public void testEvaluate3 () throws ExpressionParseException {
			final String expressionStr = "x^2";
			assertEquals(1, (int) _parser.parse(expressionStr).evaluate(1));
			assertEquals(25, (int) _parser.parse(expressionStr).evaluate(5));
			assertEquals(49, (int) _parser.parse(expressionStr).evaluate(7));
		}

		@Test
		/**
		 * Verifies that a specific expression is parsed into the correct parse tree.
		 */
		public void testEvaluate4 () throws ExpressionParseException {
			final String expressionStr = "4-3*5";
			assertEquals(-11, (int) _parser.parse(expressionStr).evaluate(1));
		}

		@Test
		/**
		 * Verifies that a specific expression is parsed into the correct parse tree.
		 */
		public void testEvaluate5 () throws ExpressionParseException {
			final String expressionStr = "9/3*3";
			assertEquals(9, (int) _parser.parse(expressionStr).evaluate(123));
		}


}
