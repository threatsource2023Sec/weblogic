package weblogic.diagnostics.archive.filestore;

import antlr.LLkParser;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import com.bea.logging.LoggingSupplementalAttribute;
import java.util.ArrayList;
import java.util.Map;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;

public class DataSourceLogFileParser extends LLkParser implements DataSourceLogFileParserTokenTypes {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final boolean DEBUG = false;
   private static final int SUPP_ATTR_INDEX = 11;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "BEGIN_LOG_RECORD", "LOGFIELD", "NEWLINE", "ESCAPED_START", "ESCAPED_END", "START_DELIMITER", "END_DELIMITER"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public void reportError(RecognitionException e) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         super.reportError(e);
      }

   }

   protected DataSourceLogFileParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public DataSourceLogFileParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected DataSourceLogFileParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public DataSourceLogFileParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public DataSourceLogFileParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
   }

   public final DataRecord getNextLogEntry() throws RecognitionException, TokenStreamException {
      DataRecord rval = null;
      Token x = null;
      Token logField = null;
      ArrayList tokens = new ArrayList(ArchiveConstants.DATASOURCELOG_ARCHIVE_COLUMNS_COUNT);

      try {
         x = this.LT(1);
         this.match(4);

         while(this.LA(1) == 5) {
            logField = this.LT(1);
            this.match(5);
            tokens.add(logField.getText());
         }

         Object[] le = new Object[ArchiveConstants.DATASOURCELOG_ARCHIVE_COLUMNS_COUNT];
         int idx = false;

         int idx;
         for(idx = 1; idx < tokens.size(); ++idx) {
            le[idx] = tokens.get(idx - 1);
         }

         String suppAttrs = (String)tokens.get(idx - 1);
         le[idx] = suppAttrs;
         if (suppAttrs != null && !suppAttrs.isEmpty()) {
            Map props = ServerLogRecordParser.parseSupplementalAttributes(suppAttrs);
            String pid = (String)props.get(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_ID.getAttributeName());
            pid = pid == null ? "" : pid;
            ++idx;
            le[idx] = pid;
            String pname = (String)props.get(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_NAME.getAttributeName());
            pname = pname == null ? "" : pname;
            ++idx;
            le[idx] = pname;
         }

         rval = new DataRecord(le);
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(var11, _tokenSet_0);
      }

      return rval;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }
}
