package weblogic.diagnostics.archive.filestore;

public interface AccessLogLexerTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int NEWLINE = 4;
   int SPECIAL = 5;
   int WS = 6;
   int NOT_WS = 7;
   int LOGFIELD = 8;
}
