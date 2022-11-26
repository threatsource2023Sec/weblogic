package weblogic.diagnostics.instrumentation.action;

public interface MethodDescriptorLexerTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int WS = 4;
   int LPAREN = 5;
   int RPAREN = 6;
   int BYTE = 7;
   int BOOLEAN = 8;
   int CHAR = 9;
   int DOUBLE = 10;
   int FLOAT = 11;
   int INT = 12;
   int LONG = 13;
   int SHORT = 14;
   int VOID = 15;
   int ARRAY_PREFIX = 16;
   int REFERENCE_TYPE_PREFIX = 17;
   int SEMI_COLON = 18;
   int CLASS_NAME = 19;
}
