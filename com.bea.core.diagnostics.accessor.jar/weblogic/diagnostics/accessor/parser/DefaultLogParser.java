package weblogic.diagnostics.accessor.parser;

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

public class DefaultLogParser extends LLkParser implements DefaultLogParserTokenTypes {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "BEGIN_LOG_RECORD", "LOGFIELD", "NEWLINE", "ESCAPED_START", "ESCAPED_END", "START_DELIMITER", "END_DELIMITER"};

   public void reportError(RecognitionException e) {
      if (DEBUG.isDebugEnabled()) {
         super.reportError(e);
      }

   }

   protected DefaultLogParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public DefaultLogParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected DefaultLogParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public DefaultLogParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public DefaultLogParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final DataRecord getNextLogEntry() throws RecognitionException, TokenStreamException {
      DataRecord rval = null;
      Token x = null;
      Token field = null;
      ArrayList list = new ArrayList();
      list.add(new Long(0L));
      x = this.LT(1);
      this.match(4);

      while(this.LA(1) == 5) {
         field = this.LT(1);
         this.match(5);
         list.add(field.getText());
      }

      int size = list.size();
      if (size > 1) {
         Object[] data = new Object[size];
         data = (Object[])list.toArray(data);
         rval = new DataRecord(data);
      }

      return rval;
   }
}
