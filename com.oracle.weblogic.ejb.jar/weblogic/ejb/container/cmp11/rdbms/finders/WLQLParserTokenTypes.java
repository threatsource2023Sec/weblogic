package weblogic.ejb.container.cmp11.rdbms.finders;

public interface WLQLParserTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int LPAREN = 4;
   int RPAREN = 5;
   int AND = 6;
   int OR = 7;
   int NOT = 8;
   int EQUALS = 9;
   int LT = 10;
   int GT = 11;
   int LTEQ = 12;
   int GTEQ = 13;
   int LITERAL_like = 14;
   int LITERAL_isNull = 15;
   int LITERAL_isNotNull = 16;
   int LITERAL_orderBy = 17;
   int VARIABLE = 18;
   int SPECIAL = 19;
   int ID = 20;
   int STRING = 21;
   int BACKSTRING = 22;
   int NUMBER = 23;
   int SLASH = 24;
   int BACKTICK = 25;
   int SSTRING = 26;
   int DSTRING = 27;
   int DASH = 28;
   int DOT = 29;
   int INT = 30;
   int REAL = 31;
   int UNICODE_RANGE = 32;
   int WS = 33;
   int COMMENT = 34;
   int DIGIT = 35;
   int LETTER = 36;
}
