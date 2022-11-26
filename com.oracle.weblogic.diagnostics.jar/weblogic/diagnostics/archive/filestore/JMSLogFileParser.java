package weblogic.diagnostics.archive.filestore;

import antlr.LLkParser;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;

public class JMSLogFileParser extends LLkParser implements JMSLogFileParserTokenTypes {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "BEGIN_LOG_RECORD", "LOGFIELD", "NEWLINE", "ESCAPED_START", "ESCAPED_END", "START_DELIMITER", "END_DELIMITER"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public void reportError(RecognitionException e) {
      if (DEBUG.isDebugEnabled()) {
         super.reportError(e);
      }

   }

   protected JMSLogFileParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public JMSLogFileParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected JMSLogFileParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public JMSLogFileParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public JMSLogFileParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final DataRecord getNextLogEntry() throws RecognitionException, TokenStreamException {
      DataRecord rval = null;
      Token x = null;
      Token a = null;
      Token b = null;
      Token c = null;
      Token d = null;
      Token e = null;
      Token f = null;
      Token g = null;
      Token h = null;
      Token i = null;
      Token j = null;
      Token k = null;
      Token l = null;
      Token m = null;

      try {
         x = this.LT(1);
         this.match(4);
         a = this.LT(1);
         this.match(5);
         b = this.LT(1);
         this.match(5);
         c = this.LT(1);
         this.match(5);
         d = this.LT(1);
         this.match(5);
         e = this.LT(1);
         this.match(5);
         f = this.LT(1);
         this.match(5);
         g = this.LT(1);
         this.match(5);
         h = this.LT(1);
         this.match(5);
         i = this.LT(1);
         this.match(5);
         j = this.LT(1);
         this.match(5);
         k = this.LT(1);
         this.match(5);
         l = this.LT(1);
         this.match(5);
         m = this.LT(1);
         this.match(5);
         Object[] le = new Object[ArchiveConstants.JMSLOG_ARCHIVE_COLUMNS_COUNT];
         int ind = 1;
         le[ind++] = a.getText();
         le[ind++] = b.getText();
         le[ind++] = c.getText();
         le[ind++] = d.getText();
         le[ind++] = e.getText();
         le[ind++] = f.getText();
         le[ind++] = g.getText();
         le[ind++] = h.getText();
         le[ind++] = i.getText();
         le[ind++] = j.getText();
         le[ind++] = k.getText();
         le[ind++] = l.getText();
         le[ind++] = m.getText();
         rval = new DataRecord(le);
      } catch (RecognitionException var18) {
         this.reportError(var18);
         this.recover(var18, _tokenSet_0);
      }

      return rval;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }
}
