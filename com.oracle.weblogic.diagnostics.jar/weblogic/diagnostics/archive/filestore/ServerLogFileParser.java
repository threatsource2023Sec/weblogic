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
import java.util.Map;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.i18n.logging.Severities;

public class ServerLogFileParser extends LLkParser implements ServerLogFileParserTokenTypes {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private LogLexer logLexer;
   private boolean compatibilityMode;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "BEGIN_LOG_RECORD", "LOGFIELD", "NEWLINE", "ESCAPED_START", "ESCAPED_END", "START_DELIMITER", "END_DELIMITER"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public void reportError(RecognitionException e) {
      if (DEBUG.isDebugEnabled()) {
         super.reportError(e);
      }

   }

   public void setLogLexer(LogLexer lexer) {
      this.logLexer = lexer;
   }

   public void setCompatibilityMode(boolean value) {
      this.compatibilityMode = value;
   }

   protected ServerLogFileParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.logLexer = null;
      this.compatibilityMode = false;
      this.tokenNames = _tokenNames;
   }

   public ServerLogFileParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected ServerLogFileParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.logLexer = null;
      this.compatibilityMode = false;
      this.tokenNames = _tokenNames;
   }

   public ServerLogFileParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public ServerLogFileParser(ParserSharedInputState state) {
      super(state, 1);
      this.logLexer = null;
      this.compatibilityMode = false;
      this.tokenNames = _tokenNames;
   }

   public final String getLogField(boolean greedyMode) throws RecognitionException, TokenStreamException {
      String logField = null;
      Token a = null;

      try {
         this.logLexer.setGreedyMode(greedyMode);
         a = this.LT(1);
         this.match(5);
         logField = a.getText();
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_0);
      }

      return logField;
   }

   public final DataRecord getNextServerLogEntry() throws RecognitionException, TokenStreamException {
      DataRecord rval = null;
      Token x = null;

      try {
         x = this.LT(1);
         this.match(4);
         Object[] le = new Object[ArchiveConstants.SERVERLOG_ARCHIVE_COLUMNS_COUNT];
         int ind = 1;
         le[ind++] = this.getLogField(false);
         String severityName = (String)((String)(le[ind++] = this.getLogField(false)));
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         le[ind++] = this.getLogField(false);
         String suppAttrs = this.compatibilityMode ? "" : this.getLogField(false);
         le[ind++] = this.getLogField(false);
         String msgBody = this.getLogField(true);
         msgBody = msgBody != null ? msgBody.trim() : "";
         int lastIndex = msgBody.lastIndexOf(">");
         if (lastIndex > 0) {
            msgBody = msgBody.substring(0, lastIndex);
         }

         le[ind++] = msgBody;
         le[ind++] = suppAttrs;
         Map props = ServerLogRecordParser.parseSupplementalAttributes(suppAttrs);
         String severity = (String)props.get(LoggingSupplementalAttribute.SUPP_ATTR_SEVERITY_VALUE.getAttributeName());

         try {
            le[ind++] = severity != null && !severity.isEmpty() ? Integer.parseInt(severity) : Severities.severityStringToNum(severityName);
         } catch (NumberFormatException var14) {
            le[ind++] = Severities.severityStringToNum(severityName);
         }

         String pid = (String)props.get(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_ID.getAttributeName());
         pid = pid == null ? "" : pid;
         le[ind++] = pid;
         String pname = (String)props.get(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_NAME.getAttributeName());
         pname = pname == null ? "" : pname;
         le[ind++] = pname;
         String rid = (String)props.get(LoggingSupplementalAttribute.SUPP_ATTR_RID.getAttributeName());
         rid = rid == null ? "" : rid;
         le[ind++] = rid;
         rval = new DataRecord(le);
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(var15, _tokenSet_0);
      }

      return rval;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }
}
