package weblogic.application.descriptor.parser;

public interface XPathParserTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int SLASH = 4;
   int NCNAME = 5;
   int LBRACKET = 6;
   int COMMA = 7;
   int RBRACKET = 8;
   int EQUALS = 9;
   int NUMERIC_LITERAL = 10;
   int STRING_LITERAL = 11;
   int WS = 12;
   int DIGIT = 13;
   int LETTER = 14;
   int DASH = 15;
   int SINGLE_QUOTE_LITERAL = 16;
   int DOUBLE_QUOTE_LITERAL = 17;
}
