package weblogic.diagnostics.query;

public interface QueryExpressionParserTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int CONSTANT_BOOLEAN = 4;
   int CONSTANT_NUMBER = 5;
   int STRING_LITERAL = 6;
   int VARIABLE_NAME = 7;
   int SET_NODE = 8;
   int NESTED_LOGICAL = 9;
   int NESTED_ARITHMETIC = 10;
   int AND = 11;
   int OR = 12;
   int NOT = 13;
   int LIKE = 14;
   int MATCHES = 15;
   int IN = 16;
   int END_OF_QUERY = 17;
   int LT = 18;
   int GT = 19;
   int LE = 20;
   int GE = 21;
   int EQ = 22;
   int NE = 23;
   int LPAREN = 24;
   int RPAREN = 25;
   int BITWISE_OR = 26;
   int BITWISE_AND = 27;
   int COMMA = 28;
   int WS = 29;
   int SPACE = 30;
   int PLUS = 31;
   int MINUS = 32;
   int DIGIT = 33;
   int ASCII_VARNAME_START = 34;
   int UNICODE_CHAR = 35;
   int ALIAS_DELIMITER_START = 36;
   int DOT = 37;
   int NUMBER_SUFFIXES = 38;
   int DOUBLE_SUFFIX = 39;
   int FLOAT_SUFFIX = 40;
   int LONG_SUFFIX = 41;
   int EXPONENT = 42;
}
