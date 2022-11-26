package antlr.debug;

import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.MismatchedCharException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.collections.impl.BitSet;

public abstract class DebuggingCharScanner extends CharScanner implements DebuggingParser {
   private ParserEventSupport parserEventSupport = new ParserEventSupport(this);
   private boolean _notDebugMode = false;
   protected String[] ruleNames;
   protected String[] semPredNames;

   public DebuggingCharScanner(InputBuffer var1) {
      super(var1);
   }

   public DebuggingCharScanner(LexerSharedInputState var1) {
      super(var1);
   }

   public void addMessageListener(MessageListener var1) {
      this.parserEventSupport.addMessageListener(var1);
   }

   public void addNewLineListener(NewLineListener var1) {
      this.parserEventSupport.addNewLineListener(var1);
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

   public void consume() throws CharStreamException {
      int var1 = -99;

      try {
         var1 = this.LA(1);
      } catch (CharStreamException var3) {
      }

      super.consume();
      this.parserEventSupport.fireConsume(var1);
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

   public char LA(int var1) throws CharStreamException {
      char var2 = super.LA(var1);
      this.parserEventSupport.fireLA(var1, var2);
      return var2;
   }

   protected Token makeToken(int var1) {
      return super.makeToken(var1);
   }

   public void match(char var1) throws MismatchedCharException, CharStreamException {
      char var2 = this.LA(1);

      try {
         super.match(var1);
         this.parserEventSupport.fireMatch(var1, this.inputState.guessing);
      } catch (MismatchedCharException var4) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatch(var2, var1, this.inputState.guessing);
         }

         throw var4;
      }
   }

   public void match(BitSet var1) throws MismatchedCharException, CharStreamException {
      String var2 = this.text.toString();
      char var3 = this.LA(1);

      try {
         super.match(var1);
         this.parserEventSupport.fireMatch(var3, var1, var2, this.inputState.guessing);
      } catch (MismatchedCharException var5) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatch(var3, var1, var2, this.inputState.guessing);
         }

         throw var5;
      }
   }

   public void match(String var1) throws MismatchedCharException, CharStreamException {
      StringBuffer var2 = new StringBuffer("");
      int var3 = var1.length();

      try {
         for(int var4 = 1; var4 <= var3; ++var4) {
            var2.append(super.LA(var4));
         }
      } catch (Exception var6) {
      }

      try {
         super.match(var1);
         this.parserEventSupport.fireMatch(var1, this.inputState.guessing);
      } catch (MismatchedCharException var5) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatch(var2.toString(), var1, this.inputState.guessing);
         }

         throw var5;
      }
   }

   public void matchNot(char var1) throws MismatchedCharException, CharStreamException {
      char var2 = this.LA(1);

      try {
         super.matchNot(var1);
         this.parserEventSupport.fireMatchNot(var2, var1, this.inputState.guessing);
      } catch (MismatchedCharException var4) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatchNot(var2, var1, this.inputState.guessing);
         }

         throw var4;
      }
   }

   public void matchRange(char var1, char var2) throws MismatchedCharException, CharStreamException {
      char var3 = this.LA(1);

      try {
         super.matchRange(var1, var2);
         this.parserEventSupport.fireMatch(var3, "" + var1 + var2, this.inputState.guessing);
      } catch (MismatchedCharException var5) {
         if (this.inputState.guessing == 0) {
            this.parserEventSupport.fireMismatch(var3, "" + var1 + var2, this.inputState.guessing);
         }

         throw var5;
      }
   }

   public void newline() {
      super.newline();
      this.parserEventSupport.fireNewLine(this.getLine());
   }

   public void removeMessageListener(MessageListener var1) {
      this.parserEventSupport.removeMessageListener(var1);
   }

   public void removeNewLineListener(NewLineListener var1) {
      this.parserEventSupport.removeNewLineListener(var1);
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

   public void reportError(MismatchedCharException var1) {
      this.parserEventSupport.fireReportError((Exception)var1);
      super.reportError((RecognitionException)var1);
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

   public void setupDebugging() {
   }

   public synchronized void wakeUp() {
      this.notify();
   }
}
