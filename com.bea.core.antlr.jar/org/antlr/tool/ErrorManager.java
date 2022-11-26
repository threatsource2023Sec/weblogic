package org.antlr.tool;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.antlr.Tool;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.DecisionProbe;
import org.antlr.misc.BitSet;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.STMessage;

public class ErrorManager {
   public static final int MSG_CANNOT_WRITE_FILE = 1;
   public static final int MSG_CANNOT_CLOSE_FILE = 2;
   public static final int MSG_CANNOT_FIND_TOKENS_FILE = 3;
   public static final int MSG_ERROR_READING_TOKENS_FILE = 4;
   public static final int MSG_DIR_NOT_FOUND = 5;
   public static final int MSG_OUTPUT_DIR_IS_FILE = 6;
   public static final int MSG_CANNOT_OPEN_FILE = 7;
   public static final int MSG_FILE_AND_GRAMMAR_NAME_DIFFER = 8;
   public static final int MSG_FILENAME_EXTENSION_ERROR = 9;
   public static final int MSG_INTERNAL_ERROR = 10;
   public static final int MSG_INTERNAL_WARNING = 11;
   public static final int MSG_ERROR_CREATING_ARTIFICIAL_RULE = 12;
   public static final int MSG_TOKENS_FILE_SYNTAX_ERROR = 13;
   public static final int MSG_CANNOT_GEN_DOT_FILE = 14;
   public static final int MSG_BAD_AST_STRUCTURE = 15;
   public static final int MSG_BAD_ACTION_AST_STRUCTURE = 16;
   public static final int MSG_MISSING_CODE_GEN_TEMPLATES = 20;
   public static final int MSG_MISSING_CYCLIC_DFA_CODE_GEN_TEMPLATES = 21;
   public static final int MSG_CODE_GEN_TEMPLATES_INCOMPLETE = 22;
   public static final int MSG_CANNOT_CREATE_TARGET_GENERATOR = 23;
   public static final int MSG_STRING_TEMPLATE_ERROR = 24;
   public static final int MSG_SYNTAX_ERROR = 100;
   public static final int MSG_RULE_REDEFINITION = 101;
   public static final int MSG_LEXER_RULES_NOT_ALLOWED = 102;
   public static final int MSG_PARSER_RULES_NOT_ALLOWED = 103;
   public static final int MSG_CANNOT_FIND_ATTRIBUTE_NAME_IN_DECL = 104;
   public static final int MSG_NO_TOKEN_DEFINITION = 105;
   public static final int MSG_UNDEFINED_RULE_REF = 106;
   public static final int MSG_LITERAL_NOT_ASSOCIATED_WITH_LEXER_RULE = 107;
   public static final int MSG_CANNOT_ALIAS_TOKENS_IN_LEXER = 108;
   public static final int MSG_ATTRIBUTE_REF_NOT_IN_RULE = 111;
   public static final int MSG_INVALID_RULE_SCOPE_ATTRIBUTE_REF = 112;
   public static final int MSG_UNKNOWN_ATTRIBUTE_IN_SCOPE = 113;
   public static final int MSG_UNKNOWN_SIMPLE_ATTRIBUTE = 114;
   public static final int MSG_INVALID_RULE_PARAMETER_REF = 115;
   public static final int MSG_UNKNOWN_RULE_ATTRIBUTE = 116;
   public static final int MSG_ISOLATED_RULE_SCOPE = 117;
   public static final int MSG_SYMBOL_CONFLICTS_WITH_GLOBAL_SCOPE = 118;
   public static final int MSG_LABEL_CONFLICTS_WITH_RULE = 119;
   public static final int MSG_LABEL_CONFLICTS_WITH_TOKEN = 120;
   public static final int MSG_LABEL_CONFLICTS_WITH_RULE_SCOPE_ATTRIBUTE = 121;
   public static final int MSG_LABEL_CONFLICTS_WITH_RULE_ARG_RETVAL = 122;
   public static final int MSG_ATTRIBUTE_CONFLICTS_WITH_RULE = 123;
   public static final int MSG_ATTRIBUTE_CONFLICTS_WITH_RULE_ARG_RETVAL = 124;
   public static final int MSG_LABEL_TYPE_CONFLICT = 125;
   public static final int MSG_ARG_RETVAL_CONFLICT = 126;
   public static final int MSG_NONUNIQUE_REF = 127;
   public static final int MSG_FORWARD_ELEMENT_REF = 128;
   public static final int MSG_MISSING_RULE_ARGS = 129;
   public static final int MSG_RULE_HAS_NO_ARGS = 130;
   public static final int MSG_ARGS_ON_TOKEN_REF = 131;
   public static final int MSG_RULE_REF_AMBIG_WITH_RULE_IN_ALT = 132;
   public static final int MSG_ILLEGAL_OPTION = 133;
   public static final int MSG_LIST_LABEL_INVALID_UNLESS_RETVAL_STRUCT = 134;
   public static final int MSG_UNDEFINED_TOKEN_REF_IN_REWRITE = 135;
   public static final int MSG_REWRITE_ELEMENT_NOT_PRESENT_ON_LHS = 136;
   public static final int MSG_UNDEFINED_LABEL_REF_IN_REWRITE = 137;
   public static final int MSG_NO_GRAMMAR_START_RULE = 138;
   public static final int MSG_EMPTY_COMPLEMENT = 139;
   public static final int MSG_UNKNOWN_DYNAMIC_SCOPE = 140;
   public static final int MSG_UNKNOWN_DYNAMIC_SCOPE_ATTRIBUTE = 141;
   public static final int MSG_ISOLATED_RULE_ATTRIBUTE = 142;
   public static final int MSG_INVALID_ACTION_SCOPE = 143;
   public static final int MSG_ACTION_REDEFINITION = 144;
   public static final int MSG_DOUBLE_QUOTES_ILLEGAL = 145;
   public static final int MSG_INVALID_TEMPLATE_ACTION = 146;
   public static final int MSG_MISSING_ATTRIBUTE_NAME = 147;
   public static final int MSG_ARG_INIT_VALUES_ILLEGAL = 148;
   public static final int MSG_REWRITE_OR_OP_WITH_NO_OUTPUT_OPTION = 149;
   public static final int MSG_NO_RULES = 150;
   public static final int MSG_WRITE_TO_READONLY_ATTR = 151;
   public static final int MSG_MISSING_AST_TYPE_IN_TREE_GRAMMAR = 152;
   public static final int MSG_REWRITE_FOR_MULTI_ELEMENT_ALT = 153;
   public static final int MSG_RULE_INVALID_SET = 154;
   public static final int MSG_HETERO_ILLEGAL_IN_REWRITE_ALT = 155;
   public static final int MSG_NO_SUCH_GRAMMAR_SCOPE = 156;
   public static final int MSG_NO_SUCH_RULE_IN_SCOPE = 157;
   public static final int MSG_TOKEN_ALIAS_CONFLICT = 158;
   public static final int MSG_TOKEN_ALIAS_REASSIGNMENT = 159;
   public static final int MSG_TOKEN_VOCAB_IN_DELEGATE = 160;
   public static final int MSG_INVALID_IMPORT = 161;
   public static final int MSG_IMPORTED_TOKENS_RULE_EMPTY = 162;
   public static final int MSG_IMPORT_NAME_CLASH = 163;
   public static final int MSG_AST_OP_WITH_NON_AST_OUTPUT_OPTION = 164;
   public static final int MSG_AST_OP_IN_ALT_WITH_REWRITE = 165;
   public static final int MSG_WILDCARD_AS_ROOT = 166;
   public static final int MSG_CONFLICTING_OPTION_IN_TREE_FILTER = 167;
   public static final int MSG_ILLEGAL_OPTION_VALUE = 168;
   public static final int MSG_ALL_OPS_NEED_SAME_ASSOC = 169;
   public static final int MSG_RANGE_OP_ILLEGAL = 170;
   public static final int MSG_GRAMMAR_NONDETERMINISM = 200;
   public static final int MSG_UNREACHABLE_ALTS = 201;
   public static final int MSG_DANGLING_STATE = 202;
   public static final int MSG_INSUFFICIENT_PREDICATES = 203;
   public static final int MSG_DUPLICATE_SET_ENTRY = 204;
   public static final int MSG_ANALYSIS_ABORTED = 205;
   public static final int MSG_RECURSION_OVERLOW = 206;
   public static final int MSG_LEFT_RECURSION = 207;
   public static final int MSG_UNREACHABLE_TOKENS = 208;
   public static final int MSG_TOKEN_NONDETERMINISM = 209;
   public static final int MSG_LEFT_RECURSION_CYCLES = 210;
   public static final int MSG_NONREGULAR_DECISION = 211;
   public static final int MSG_CIRCULAR_DEPENDENCY = 212;
   public static final int MAX_MESSAGE_NUMBER = 212;
   public static final BitSet ERRORS_FORCING_NO_ANALYSIS = new BitSet() {
      {
         this.add(101);
         this.add(106);
         this.add(210);
         this.add(149);
         this.add(150);
         this.add(156);
         this.add(157);
         this.add(102);
         this.add(166);
         this.add(212);
      }
   };
   public static final BitSet ERRORS_FORCING_NO_CODEGEN = new BitSet() {
      {
         this.add(211);
         this.add(206);
         this.add(201);
         this.add(8);
         this.add(161);
         this.add(164);
         this.add(212);
      }
   };
   public static final Map emitSingleError = new HashMap() {
      {
         this.put("danglingState", new HashSet());
      }
   };
   private static Locale locale;
   private static String formatName;
   private static final Map threadToListenerMap = new WeakHashMap();
   private static final Map threadToErrorStateMap = new WeakHashMap();
   private static final Map threadToToolMap = new WeakHashMap();
   private static STGroup messages;
   private static STGroup format;
   private static final String[] idToMessageTemplateName = new String[213];
   static ANTLRErrorListener theDefaultErrorListener = new ANTLRErrorListener() {
      public void info(String msg) {
         if (ErrorManager.formatWantsSingleLineMessage()) {
            msg = msg.replaceAll("\n", " ");
         }

         System.err.println(msg);
      }

      public void error(Message msg) {
         String outputMsg = msg.toString();
         if (ErrorManager.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replaceAll("\n", " ");
         }

         System.err.println(outputMsg);
      }

      public void warning(Message msg) {
         String outputMsg = msg.toString();
         if (ErrorManager.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replaceAll("\n", " ");
         }

         System.err.println(outputMsg);
      }

      public void error(ToolMessage msg) {
         String outputMsg = msg.toString();
         if (ErrorManager.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replaceAll("\n", " ");
         }

         System.err.println(outputMsg);
      }
   };
   static STErrorListener initSTListener = new STErrorListener() {
      public void compileTimeError(STMessage msg) {
         System.err.println("ErrorManager init error: " + msg);
      }

      public void runTimeError(STMessage msg) {
         System.err.println("ErrorManager init error: " + msg);
      }

      public void IOError(STMessage msg) {
         System.err.println("ErrorManager init error: " + msg);
      }

      public void internalError(STMessage msg) {
         System.err.println("ErrorManager init error: " + msg);
      }
   };
   static STErrorListener blankSTListener = new STErrorListener() {
      public void compileTimeError(STMessage msg) {
      }

      public void runTimeError(STMessage msg) {
      }

      public void IOError(STMessage msg) {
      }

      public void internalError(STMessage msg) {
      }
   };
   static STErrorListener theDefaultSTListener = new STErrorListener() {
      public void compileTimeError(STMessage msg) {
         ErrorManager.error(24, msg.toString(), (Throwable)msg.cause);
      }

      public void runTimeError(STMessage msg) {
         switch (msg.error) {
            case NO_SUCH_ATTRIBUTE:
            case NO_SUCH_ATTRIBUTE_PASS_THROUGH:
            case NO_SUCH_PROPERTY:
               ErrorManager.warning(24, msg.toString());
               return;
            default:
               ErrorManager.error(24, msg.toString(), (Throwable)msg.cause);
         }
      }

      public void IOError(STMessage msg) {
         ErrorManager.error(24, msg.toString(), (Throwable)msg.cause);
      }

      public void internalError(STMessage msg) {
         ErrorManager.error(24, msg.toString(), (Throwable)msg.cause);
      }
   };

   public static STErrorListener getSTErrorListener() {
      return theDefaultSTListener;
   }

   public static void setLocale(Locale locale) {
      ErrorManager.locale = locale;
      String language = locale.getLanguage();
      String fileName = "org/antlr/tool/templates/messages/languages/" + language + ".stg";

      try {
         messages = new STGroupFile(fileName);
      } catch (IllegalArgumentException var4) {
         if (language.equals(Locale.US.getLanguage())) {
            rawError("ANTLR installation corrupted; cannot find English messages file " + fileName);
            panic();
         } else {
            setLocale(Locale.US);
         }
      }

      messages.setListener(blankSTListener);
      boolean messagesOK = verifyMessages();
      if (!messagesOK && language.equals(Locale.US.getLanguage())) {
         rawError("ANTLR installation corrupted; English messages file " + language + ".stg incomplete");
         panic();
      } else if (!messagesOK) {
         setLocale(Locale.US);
      }

   }

   public static void setFormat(String formatName) {
      ErrorManager.formatName = formatName;
      String fileName = "org/antlr/tool/templates/messages/formats/" + formatName + ".stg";
      format = new STGroupFile(fileName);
      format.setListener(initSTListener);
      if (!format.isDefined("message")) {
         if (formatName.equals("antlr")) {
            rawError("no such message format file " + fileName + " retrying with default ANTLR format");
            setFormat("antlr");
            return;
         }

         setFormat("antlr");
      }

      format.setListener(blankSTListener);
      boolean formatOK = verifyFormat();
      if (!formatOK && formatName.equals("antlr")) {
         rawError("ANTLR installation corrupted; ANTLR messages format file " + formatName + ".stg incomplete");
         panic();
      } else if (!formatOK) {
         setFormat("antlr");
      }

   }

   public static void setErrorListener(ANTLRErrorListener listener) {
      threadToListenerMap.put(Thread.currentThread(), listener);
   }

   public static void removeErrorListener() {
      threadToListenerMap.remove(Thread.currentThread());
   }

   public static void setTool(Tool tool) {
      threadToToolMap.put(Thread.currentThread(), tool);
   }

   public static ST getMessage(int msgID) {
      String msgName = idToMessageTemplateName[msgID];
      return messages.getInstanceOf(msgName);
   }

   public static String getMessageType(int msgID) {
      if (getErrorState().warningMsgIDs.member(msgID)) {
         return messages.getInstanceOf("warning").render();
      } else if (getErrorState().errorMsgIDs.member(msgID)) {
         return messages.getInstanceOf("error").render();
      } else {
         assertTrue(false, "Assertion failed! Message ID " + msgID + " created but is not present in errorMsgIDs or warningMsgIDs.");
         return "";
      }
   }

   public static ST getLocationFormat() {
      return format.getInstanceOf("location");
   }

   public static ST getReportFormat() {
      return format.getInstanceOf("report");
   }

   public static ST getMessageFormat() {
      return format.getInstanceOf("message");
   }

   public static boolean formatWantsSingleLineMessage() {
      return format.getInstanceOf("wantsSingleLineMessage").render().equals("true");
   }

   public static ANTLRErrorListener getErrorListener() {
      ANTLRErrorListener el = (ANTLRErrorListener)threadToListenerMap.get(Thread.currentThread());
      return el == null ? theDefaultErrorListener : el;
   }

   public static ErrorState getErrorState() {
      ErrorState ec = (ErrorState)threadToErrorStateMap.get(Thread.currentThread());
      if (ec == null) {
         ec = new ErrorState();
         threadToErrorStateMap.put(Thread.currentThread(), ec);
      }

      return ec;
   }

   public static int getNumErrors() {
      return getErrorState().errors;
   }

   public static void resetErrorState() {
      threadToListenerMap.clear();
      ErrorState ec = new ErrorState();
      threadToErrorStateMap.put(Thread.currentThread(), ec);
   }

   public static void info(String msg) {
      ++getErrorState().infos;
      getErrorListener().info(msg);
   }

   public static void error(int msgID) {
      ++getErrorState().errors;
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error(new ToolMessage(msgID));
   }

   public static void error(int msgID, Throwable e) {
      ++getErrorState().errors;
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error(new ToolMessage(msgID, e));
   }

   public static void error(int msgID, Object arg) {
      ++getErrorState().errors;
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error(new ToolMessage(msgID, arg));
   }

   public static void error(int msgID, Object arg, Object arg2) {
      ++getErrorState().errors;
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error(new ToolMessage(msgID, arg, arg2));
   }

   public static void error(int msgID, Object arg, Throwable e) {
      ++getErrorState().errors;
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error(new ToolMessage(msgID, arg, e));
   }

   public static void warning(int msgID, Object arg) {
      ++getErrorState().warnings;
      getErrorState().warningMsgIDs.add(msgID);
      getErrorListener().warning(new ToolMessage(msgID, arg));
   }

   public static void nondeterminism(DecisionProbe probe, DFAState d) {
      ++getErrorState().warnings;
      Message msg = new GrammarNonDeterminismMessage(probe, d);
      getErrorState().warningMsgIDs.add(msg.msgID);
      getErrorListener().warning(msg);
   }

   public static void danglingState(DecisionProbe probe, DFAState d) {
      ++getErrorState().errors;
      Message msg = new GrammarDanglingStateMessage(probe, d);
      getErrorState().errorMsgIDs.add(msg.msgID);
      Set seen = (Set)emitSingleError.get("danglingState");
      if (!seen.contains(d.dfa.decisionNumber + "|" + d.getAltSet())) {
         getErrorListener().error((Message)msg);
         seen.add(d.dfa.decisionNumber + "|" + d.getAltSet());
      }

   }

   public static void analysisAborted(DecisionProbe probe) {
      ++getErrorState().warnings;
      Message msg = new GrammarAnalysisAbortedMessage(probe);
      getErrorState().warningMsgIDs.add(msg.msgID);
      getErrorListener().warning(msg);
   }

   public static void unreachableAlts(DecisionProbe probe, List alts) {
      ++getErrorState().errors;
      Message msg = new GrammarUnreachableAltsMessage(probe, alts);
      getErrorState().errorMsgIDs.add(msg.msgID);
      getErrorListener().error((Message)msg);
   }

   public static void insufficientPredicates(DecisionProbe probe, DFAState d, Map altToUncoveredLocations) {
      ++getErrorState().warnings;
      Message msg = new GrammarInsufficientPredicatesMessage(probe, d, altToUncoveredLocations);
      getErrorState().warningMsgIDs.add(msg.msgID);
      getErrorListener().warning(msg);
   }

   public static void nonLLStarDecision(DecisionProbe probe) {
      ++getErrorState().errors;
      Message msg = new NonRegularDecisionMessage(probe, probe.getNonDeterministicAlts());
      getErrorState().errorMsgIDs.add(msg.msgID);
      getErrorListener().error((Message)msg);
   }

   public static void recursionOverflow(DecisionProbe probe, DFAState sampleBadState, int alt, Collection targetRules, Collection callSiteStates) {
      ++getErrorState().errors;
      Message msg = new RecursionOverflowMessage(probe, sampleBadState, alt, targetRules, callSiteStates);
      getErrorState().errorMsgIDs.add(msg.msgID);
      getErrorListener().error((Message)msg);
   }

   public static void leftRecursionCycles(Collection cycles) {
      ++getErrorState().errors;
      Message msg = new LeftRecursionCyclesMessage(cycles);
      getErrorState().errorMsgIDs.add(msg.msgID);
      getErrorListener().error((Message)msg);
   }

   public static void grammarError(int msgID, Grammar g, Token token, Object arg, Object arg2) {
      ++getErrorState().errors;
      Message msg = new GrammarSemanticsMessage(msgID, g, token, arg, arg2);
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error((Message)msg);
   }

   public static void grammarError(int msgID, Grammar g, Token token, Object arg) {
      grammarError(msgID, g, token, arg, (Object)null);
   }

   public static void grammarError(int msgID, Grammar g, Token token) {
      grammarError(msgID, g, token, (Object)null, (Object)null);
   }

   public static void grammarWarning(int msgID, Grammar g, Token token, Object arg, Object arg2) {
      ++getErrorState().warnings;
      Message msg = new GrammarSemanticsMessage(msgID, g, token, arg, arg2);
      getErrorState().warningMsgIDs.add(msgID);
      getErrorListener().warning(msg);
   }

   public static void grammarWarning(int msgID, Grammar g, Token token, Object arg) {
      grammarWarning(msgID, g, token, arg, (Object)null);
   }

   public static void grammarWarning(int msgID, Grammar g, Token token) {
      grammarWarning(msgID, g, token, (Object)null, (Object)null);
   }

   public static void syntaxError(int msgID, Grammar grammar, Token token, Object arg, RecognitionException re) {
      ++getErrorState().errors;
      getErrorState().errorMsgIDs.add(msgID);
      getErrorListener().error((Message)(new GrammarSyntaxMessage(msgID, grammar, token, arg, re)));
   }

   public static void internalError(Object error, Throwable e) {
      StackTraceElement location = getLastNonErrorManagerCodeLocation(e);
      String msg = "Exception " + e + "@" + location + ": " + error;
      error(10, (Object)msg);
   }

   public static void internalError(Object error) {
      StackTraceElement location = getLastNonErrorManagerCodeLocation(new Exception());
      String msg = location + ": " + error;
      error(10, (Object)msg);
   }

   public static boolean doNotAttemptAnalysis() {
      return !getErrorState().errorMsgIDs.and(ERRORS_FORCING_NO_ANALYSIS).isNil();
   }

   public static boolean doNotAttemptCodeGen() {
      return doNotAttemptAnalysis() || !getErrorState().errorMsgIDs.and(ERRORS_FORCING_NO_CODEGEN).isNil();
   }

   private static StackTraceElement getLastNonErrorManagerCodeLocation(Throwable e) {
      StackTraceElement[] stack = e.getStackTrace();

      int i;
      StackTraceElement t;
      for(i = 0; i < stack.length; ++i) {
         t = stack[i];
         if (t.toString().indexOf("ErrorManager") < 0) {
            break;
         }
      }

      t = stack[i];
      return t;
   }

   public static void assertTrue(boolean condition, String message) {
      if (!condition) {
         internalError(message);
      }

   }

   protected static boolean initIdToMessageNameMapping() {
      for(int i = 0; i < idToMessageTemplateName.length; ++i) {
         idToMessageTemplateName[i] = "INVALID MESSAGE ID: " + i;
      }

      Field[] fields = ErrorManager.class.getFields();

      for(int i = 0; i < fields.length; ++i) {
         Field f = fields[i];
         String fieldName = f.getName();
         if (fieldName.startsWith("MSG_")) {
            String templateName = fieldName.substring("MSG_".length(), fieldName.length());

            int msgID;
            try {
               msgID = f.getInt(ErrorManager.class);
            } catch (IllegalAccessException var7) {
               System.err.println("cannot get const value for " + f.getName());
               continue;
            }

            if (fieldName.startsWith("MSG_")) {
               idToMessageTemplateName[msgID] = templateName;
            }
         }
      }

      return true;
   }

   protected static boolean verifyMessages() {
      boolean ok = true;
      Field[] fields = ErrorManager.class.getFields();

      for(int i = 0; i < fields.length; ++i) {
         Field f = fields[i];
         String fieldName = f.getName();
         String templateName = fieldName.substring("MSG_".length(), fieldName.length());
         if (fieldName.startsWith("MSG_") && !messages.isDefined(templateName)) {
            System.err.println("Message " + templateName + " in locale " + locale + " not found");
            ok = false;
         }
      }

      if (!messages.isDefined("warning")) {
         System.err.println("Message template 'warning' not found in locale " + locale);
         ok = false;
      }

      if (!messages.isDefined("error")) {
         System.err.println("Message template 'error' not found in locale " + locale);
         ok = false;
      }

      return ok;
   }

   protected static boolean verifyFormat() {
      boolean ok = true;
      if (!format.isDefined("location")) {
         System.err.println("Format template 'location' not found in " + formatName);
         ok = false;
      }

      if (!format.isDefined("message")) {
         System.err.println("Format template 'message' not found in " + formatName);
         ok = false;
      }

      if (!format.isDefined("report")) {
         System.err.println("Format template 'report' not found in " + formatName);
         ok = false;
      }

      return ok;
   }

   static void rawError(String msg) {
      System.err.println(msg);
   }

   static void rawError(String msg, Throwable e) {
      rawError(msg);
      e.printStackTrace(System.err);
   }

   public static void panic() {
      Tool tool = (Tool)threadToToolMap.get(Thread.currentThread());
      if (tool == null) {
         throw new Error("ANTLR ErrorManager panic");
      } else {
         tool.panic();
      }
   }

   static {
      initIdToMessageNameMapping();
      setLocale(Locale.getDefault());
      setFormat("antlr");
   }

   public static class ErrorState {
      public int errors;
      public int warnings;
      public int infos;
      public BitSet errorMsgIDs = new BitSet();
      public BitSet warningMsgIDs = new BitSet();
   }
}
