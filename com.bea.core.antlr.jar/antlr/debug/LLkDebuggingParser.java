package antlr.debug;

import antlr.LLkParser;
import antlr.MismatchedTokenException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.Utils;
import antlr.collections.impl.BitSet;
import java.lang.reflect.Constructor;

public class LLkDebuggingParser extends LLkParser implements DebuggingParser {
   protected ParserEventSupport parserEventSupport = new ParserEventSupport(this);
   private boolean _notDebugMode = false;
   protected String[] ruleNames;
   protected String[] semPredNames;
   // $FF: synthetic field
   static Class class$antlr$debug$LLkDebuggingParser;
   // $FF: synthetic field
   static Class class$antlr$TokenStream;
   // $FF: synthetic field
   static Class class$antlr$TokenBuffer;

   public LLkDebuggingParser(int var1) {
      super(var1);
   }

   public LLkDebuggingParser(ParserSharedInputState var1, int var2) {
      super(var1, var2);
   }

   public LLkDebuggingParser(TokenBuffer var1, int var2) {
      super(var1, var2);
   }

   public LLkDebuggingParser(TokenStream var1, int var2) {
      super(var1, var2);
   }

   public void addMessageListener(MessageListener var1) {
      this.parserEventSupport.addMessageListener(var1);
   }

   public void addParserListener(ParserListener var1) {
      this.parserEventSupport.addParserListener(var1);
   }

   public void addParserMatchListener(ParserMatchListener var1) {
      this.parserEventSupport.addParserMatchListener(var1);
   }

   public void addParserTokenListener(ParserTokenListener var1) {
      this.parserEventSupport.addParserTokenListener(var1);
   }

   public void addSemanticPredicateListener(SemanticPredicateListener var1) {
      this.parserEventSupport.addSemanticPredicateListener(var1);
   }

   public void addSyntacticPredicateListener(SyntacticPredicateListener var1) {
      this.parserEventSupport.addSyntacticPredicateListener(var1);
   }

   public void addTraceListener(TraceListener var1) {
      this.parserEventSupport.addTraceListener(var1);
   }

   public void consume() throws TokenStreamException {
      boolean var1 = true;
      int var2 = this.LA(1);
      super.consume();
      this.parserEventSupport.fireConsume(var2);
   }

   protected void fireEnterRule(int var1, int var2) {
      if (this.isDebugMode()) {
         this.parserEventSupport.fireEnterRule(var1, this.inputState.guessing, var2);
      }

   }

   protected void fireExitRule(int var1, int var2) {
      if (this.isDebugMode()) {
         this.parserEventSupport.fireExitRule(var1, this.inputState.guessing, var2);
      }

   }

   protected boolean fireSemanticPredicateEvaluated(int var1, int var2, boolean var3) {
      return this.isDebugMode() ? this.parserEventSupport.fireSemanticPredicateEvaluated(var1, var2, var3, this.inputState.guessing) : var3;
   }

   protected void fireSyntacticPredicateFailed() {
      if (this.isDebugMode()) {
         this.parserEventSupport.fireSyntacticPredicateFailed(this.inputState.guessing);
      }

   }

   protected void fireSyntacticPredicateStarted() {
      if (this.isDebugMode()) {
         this.parserEventSupport.fireSyntacticPredicateStarted(this.inputState.guessing);
      }

   }

   protected void fireSyntacticPredicateSucceeded() {
      if (this.isDebugMode()) {
         this.parserEventSupport.fireSyntacticPredicateSucceeded(this.inputState.guessing);
      }

   }

   public String getRuleName(int var1) {
      return this.ruleNames[var1];
   }

   public String getSemPredName(int var1) {
      return this.semPredNames[var1];
   }

   public synchronized void goToSleep() {
      try {
         this.wait();
      } catch (InterruptedException var2) {
      }

   }

   public boolean isDebugMode() {
      return !this._notDebugMode;
   }

   public boolean isGuessing() {
      return this.inputState.guessing > 0;
   }

   public int LA(int var1) throws TokenStreamException {
      int var2 = super.LA(var1);
      this.parserEventSupport.fireLA(var1, var2);
      return var2;
   }

   public void match(int var1) throws MismatchedTokenException, TokenStreamException {
      String var2 = this.LT(1).getText();
      int var3 = this.LA(1);

      try {
         super.match(var1);
         this.parserEventSupport.fireMatch(var1, var2, this.inputState.guessing);
      } catch (MismatchedTokenException var5) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatch(var3, var1, var2, this.inputState.guessing);
         }

         throw var5;
      }
   }

   public void match(BitSet var1) throws MismatchedTokenException, TokenStreamException {
      String var2 = this.LT(1).getText();
      int var3 = this.LA(1);

      try {
         super.match(var1);
         this.parserEventSupport.fireMatch(var3, var1, var2, this.inputState.guessing);
      } catch (MismatchedTokenException var5) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatch(var3, var1, var2, this.inputState.guessing);
         }

         throw var5;
      }
   }

   public void matchNot(int var1) throws MismatchedTokenException, TokenStreamException {
      String var2 = this.LT(1).getText();
      int var3 = this.LA(1);

      try {
         super.matchNot(var1);
         this.parserEventSupport.fireMatchNot(var3, var1, var2, this.inputState.guessing);
      } catch (MismatchedTokenException var5) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatchNot(var3, var1, var2, this.inputState.guessing);
         }

         throw var5;
      }
   }

   public void removeMessageListener(MessageListener var1) {
      this.parserEventSupport.removeMessageListener(var1);
   }

   public void removeParserListener(ParserListener var1) {
      this.parserEventSupport.removeParserListener(var1);
   }

   public void removeParserMatchListener(ParserMatchListener var1) {
      this.parserEventSupport.removeParserMatchListener(var1);
   }

   public void removeParserTokenListener(ParserTokenListener var1) {
      this.parserEventSupport.removeParserTokenListener(var1);
   }

   public void removeSemanticPredicateListener(SemanticPredicateListener var1) {
      this.parserEventSupport.removeSemanticPredicateListener(var1);
   }

   public void removeSyntacticPredicateListener(SyntacticPredicateListener var1) {
      this.parserEventSupport.removeSyntacticPredicateListener(var1);
   }

   public void removeTraceListener(TraceListener var1) {
      this.parserEventSupport.removeTraceListener(var1);
   }

   public void reportError(RecognitionException var1) {
      this.parserEventSupport.fireReportError((Exception)var1);
      super.reportError(var1);
   }

   public void reportError(String var1) {
      this.parserEventSupport.fireReportError(var1);
      super.reportError(var1);
   }

   public void reportWarning(String var1) {
      this.parserEventSupport.fireReportWarning(var1);
      super.reportWarning(var1);
   }

   public void setDebugMode(boolean var1) {
      this._notDebugMode = !var1;
   }

   public void setupDebugging(TokenBuffer var1) {
      this.setupDebugging((TokenStream)null, var1);
   }

   public void setupDebugging(TokenStream var1) {
      this.setupDebugging(var1, (TokenBuffer)null);
   }

   protected void setupDebugging(TokenStream var1, TokenBuffer var2) {
      this.setDebugMode(true);

      try {
         try {
            Utils.loadClass("javax.swing.JButton");
         } catch (ClassNotFoundException var5) {
            System.err.println("Swing is required to use ParseView, but is not present in your CLASSPATH");
            System.exit(1);
         }

         Class var3 = Utils.loadClass("antlr.parseview.ParseView");
         Constructor var4 = var3.getConstructor(class$antlr$debug$LLkDebuggingParser == null ? (class$antlr$debug$LLkDebuggingParser = class$("antlr.debug.LLkDebuggingParser")) : class$antlr$debug$LLkDebuggingParser, class$antlr$TokenStream == null ? (class$antlr$TokenStream = class$("antlr.TokenStream")) : class$antlr$TokenStream, class$antlr$TokenBuffer == null ? (class$antlr$TokenBuffer = class$("antlr.TokenBuffer")) : class$antlr$TokenBuffer);
         var4.newInstance(this, var1, var2);
      } catch (Exception var6) {
         System.err.println("Error initializing ParseView: " + var6);
         System.err.println("Please report this to Scott Stanchfield, thetick@magelang.com");
         System.exit(1);
      }

   }

   public synchronized void wakeUp() {
      this.notify();
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
