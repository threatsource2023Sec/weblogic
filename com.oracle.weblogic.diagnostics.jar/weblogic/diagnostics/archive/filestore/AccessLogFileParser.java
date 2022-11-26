package weblogic.diagnostics.archive.filestore;

import antlr.LLkParser;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import java.util.ArrayList;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;

public class AccessLogFileParser extends LLkParser implements AccessLogFileParserTokenTypes {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "NEWLINE", "SPECIAL", "WS", "NOT_WS", "LOGFIELD"};

   public void reportError(RecognitionException e) {
      if (DEBUG.isDebugEnabled()) {
         super.reportError(e);
      }

   }

   protected AccessLogFileParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public AccessLogFileParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected AccessLogFileParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public AccessLogFileParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public AccessLogFileParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final DataRecord getNextAccessLogEntry() throws RecognitionException, TokenStreamException {
      DataRecord rval = null;
      Token field = null;
      ArrayList list = new ArrayList();
      list.add(new Long(0L));

      while(this.LA(1) == 8) {
         field = this.LT(1);
         this.match(8);
         list.add(field.getText());
      }

      this.match(4);
      int size = list.size();
      if (size > 1) {
         Object[] data = new Object[size];
         data = (Object[])list.toArray(data);
         rval = new DataRecord(data);
      }

      return rval;
   }
}
