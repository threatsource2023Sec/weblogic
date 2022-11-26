package weblogic.servlet.ejb2jsp;

public interface EJB2JSPLexerTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int TOKEN = 4;
   int METHOD_DECLARATION = 5;
   int ARGLIST = 6;
   int ARG = 7;
   int COMMENT = 8;
   int STANDARD_COMMENT = 9;
   int STANDARD_COMMENT_CONTENT = 10;
   int SLASH_COMMENT = 11;
   int SLASH_COMMENT_CONTENT = 12;
   int WS = 13;
   int COMMA = 14;
   int DOT = 15;
   int DASH = 16;
   int STAR = 17;
   int DIGIT = 18;
   int LETTER = 19;
   int BRACE = 20;
   int WORD = 21;
   int IMPORT = 22;
   int CODE = 23;
}
