package org.antlr.stringtemplate.language;

public interface TemplateParserTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int LITERAL = 4;
   int NEWLINE = 5;
   int ACTION = 6;
   int IF = 7;
   int ELSEIF = 8;
   int ELSE = 9;
   int ENDIF = 10;
   int REGION_REF = 11;
   int REGION_DEF = 12;
   int EXPR = 13;
   int TEMPLATE = 14;
   int IF_EXPR = 15;
   int ESC_CHAR = 16;
   int ESC = 17;
   int HEX = 18;
   int SUBTEMPLATE = 19;
   int NESTED_PARENS = 20;
   int INDENT = 21;
   int COMMENT = 22;
   int LINE_BREAK = 23;
}
