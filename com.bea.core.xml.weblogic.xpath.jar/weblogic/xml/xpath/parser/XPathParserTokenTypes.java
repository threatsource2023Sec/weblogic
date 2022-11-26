package weblogic.xml.xpath.parser;

public interface XPathParserTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int SLASH = 4;
   int DOUBLE_SLASH = 5;
   int DOUBLE_DOT = 6;
   int DOT = 7;
   int ATSIGN = 8;
   int NCNAME = 9;
   int DOUBLE_COLON = 10;
   int ASTERISK = 11;
   int LPAREN = 13;
   int LITERAL = 14;
   int RPAREN = 15;
   int LITERAL_comment = 16;
   int LITERAL_text = 17;
   int LITERAL_node = 18;
   int COLON = 19;
   int NUMBER = 20;
   int DOLLAR = 21;
   int COMMA = 22;
   int PIPE = 23;
   int LBRACKET = 24;
   int RBRACKET = 25;
   int LITERAL_or = 26;
   int LITERAL_and = 27;
   int EQUALS = 28;
   int NOT_EQUAL = 29;
   int LESS_THAN = 30;
   int LESS_EQUAL = 31;
   int GREATER_THAN = 32;
   int GREATER_EQUAL = 33;
   int ADDITION = 34;
   int SUBTRACTION = 35;
   int LITERAL_div = 36;
   int LITERAL_mod = 37;
   int DASH = 38;
   int WS = 39;
   int DIGIT = 40;
   int LETTER = 41;
   int SINGLE_QUOTE_LITERAL = 42;
   int DOUBLE_QUOTE_LITERAL = 43;
}
