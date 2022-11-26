package weblogic.diagnostics.archive.filestore;

public interface LogLexerTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int BEGIN_LOG_RECORD = 4;
   int LOGFIELD = 5;
   int NEWLINE = 6;
   int ESCAPED_START = 7;
   int ESCAPED_END = 8;
   int START_DELIMITER = 9;
   int END_DELIMITER = 10;
}
