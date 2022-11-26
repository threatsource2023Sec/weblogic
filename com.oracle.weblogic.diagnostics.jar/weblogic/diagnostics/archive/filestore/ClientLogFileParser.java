package weblogic.diagnostics.archive.filestore;

import antlr.LLkParser;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import java.util.Locale;
import weblogic.logging.LogEntry;
import weblogic.logging.SeverityI18N;
import weblogic.logging.WLLevel;
import weblogic.logging.WLLogRecord;

public class ClientLogFileParser extends LLkParser implements ClientLogFileParserTokenTypes {
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "BEGIN_LOG_RECORD", "LOGFIELD", "NEWLINE", "ESCAPED_START", "ESCAPED_END", "START_DELIMITER", "END_DELIMITER"};

   protected ClientLogFileParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public ClientLogFileParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected ClientLogFileParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public ClientLogFileParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public ClientLogFileParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final LogEntry getNextClientLogEntry() throws RecognitionException, TokenStreamException {
      LogEntry le = null;
      Token a = null;
      Token b = null;
      Token c = null;
      Token d = null;
      Token e = null;

      try {
         a = this.LT(1);
         this.match(5);
         if (a.getColumn() != 1) {
            throw new SemanticException("a.getColumn() == 1");
         }

         b = this.LT(1);
         this.match(5);
         c = this.LT(1);
         this.match(5);
         d = this.LT(1);
         this.match(5);
         e = this.LT(1);
         this.match(5);
         String timestamp = a.getText();
         String severity = b.getText();
         String subsystem = c.getText();
         String msgId = d.getText();
         String message = e.getText();
         int sl = SeverityI18N.severityStringToNum(severity, Locale.getDefault());
         WLLogRecord lr = new WLLogRecord(WLLevel.getLevel(sl), message);
         lr.setId(msgId);
         lr.setLoggerName(subsystem);
         le = lr;
      } catch (SemanticException var14) {
         this.consumeUntil(5);
      } catch (RecognitionException var15) {
         this.reportError(var15.toString());
         this.consumeUntil(5);
      }

      return le;
   }
}
