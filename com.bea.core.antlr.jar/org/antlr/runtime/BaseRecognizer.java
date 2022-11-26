package org.antlr.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRecognizer {
   public static final int MEMO_RULE_FAILED = -2;
   public static final int MEMO_RULE_UNKNOWN = -1;
   public static final int INITIAL_FOLLOW_STACK_SIZE = 100;
   public static final int DEFAULT_TOKEN_CHANNEL = 0;
   public static final int HIDDEN = 99;
   public static final String NEXT_TOKEN_RULE_NAME = "nextToken";
   protected RecognizerSharedState state;

   public BaseRecognizer() {
      this.state = new RecognizerSharedState();
   }

   public BaseRecognizer(RecognizerSharedState state) {
      if (state == null) {
         state = new RecognizerSharedState();
      }

      this.state = state;
   }

   public void reset() {
      if (this.state != null) {
         this.state._fsp = -1;
         this.state.errorRecovery = false;
         this.state.lastErrorIndex = -1;
         this.state.failed = false;
         this.state.syntaxErrors = 0;
         this.state.backtracking = 0;

         for(int i = 0; this.state.ruleMemo != null && i < this.state.ruleMemo.length; ++i) {
            this.state.ruleMemo[i] = null;
         }

      }
   }

   public Object match(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      Object matchedSymbol = this.getCurrentInputSymbol(input);
      if (input.LA(1) == ttype) {
         input.consume();
         this.state.errorRecovery = false;
         this.state.failed = false;
         return matchedSymbol;
      } else if (this.state.backtracking > 0) {
         this.state.failed = true;
         return matchedSymbol;
      } else {
         matchedSymbol = this.recoverFromMismatchedToken(input, ttype, follow);
         return matchedSymbol;
      }
   }

   public void matchAny(IntStream input) {
      this.state.errorRecovery = false;
      this.state.failed = false;
      input.consume();
   }

   public boolean mismatchIsUnwantedToken(IntStream input, int ttype) {
      return input.LA(2) == ttype;
   }

   public boolean mismatchIsMissingToken(IntStream input, BitSet follow) {
      if (follow == null) {
         return false;
      } else {
         if (follow.member(1)) {
            BitSet viableTokensFollowingThisRule = this.computeContextSensitiveRuleFOLLOW();
            follow = follow.or(viableTokensFollowingThisRule);
            if (this.state._fsp >= 0) {
               follow.remove(1);
            }
         }

         return follow.member(input.LA(1)) || follow.member(1);
      }
   }

   public void reportError(RecognitionException e) {
      if (!this.state.errorRecovery) {
         ++this.state.syntaxErrors;
         this.state.errorRecovery = true;
         this.displayRecognitionError(this.getTokenNames(), e);
      }
   }

   public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
      String hdr = this.getErrorHeader(e);
      String msg = this.getErrorMessage(e, tokenNames);
      this.emitErrorMessage(hdr + " " + msg);
   }

   public String getErrorMessage(RecognitionException e, String[] tokenNames) {
      String msg = e.getMessage();
      String tokenName;
      if (e instanceof UnwantedTokenException) {
         UnwantedTokenException ute = (UnwantedTokenException)e;
         if (ute.expecting == -1) {
            tokenName = "EOF";
         } else {
            tokenName = tokenNames[ute.expecting];
         }

         msg = "extraneous input " + this.getTokenErrorDisplay(ute.getUnexpectedToken()) + " expecting " + tokenName;
      } else if (e instanceof MissingTokenException) {
         MissingTokenException mte = (MissingTokenException)e;
         if (mte.expecting == -1) {
            tokenName = "EOF";
         } else {
            tokenName = tokenNames[mte.expecting];
         }

         msg = "missing " + tokenName + " at " + this.getTokenErrorDisplay(e.token);
      } else if (e instanceof MismatchedTokenException) {
         MismatchedTokenException mte = (MismatchedTokenException)e;
         if (mte.expecting == -1) {
            tokenName = "EOF";
         } else {
            tokenName = tokenNames[mte.expecting];
         }

         msg = "mismatched input " + this.getTokenErrorDisplay(e.token) + " expecting " + tokenName;
      } else if (e instanceof MismatchedTreeNodeException) {
         MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
         if (mtne.expecting == -1) {
            tokenName = "EOF";
         } else {
            tokenName = tokenNames[mtne.expecting];
         }

         msg = "mismatched tree node: " + mtne.node + " expecting " + tokenName;
      } else if (e instanceof NoViableAltException) {
         msg = "no viable alternative at input " + this.getTokenErrorDisplay(e.token);
      } else if (e instanceof EarlyExitException) {
         msg = "required (...)+ loop did not match anything at input " + this.getTokenErrorDisplay(e.token);
      } else if (e instanceof MismatchedSetException) {
         MismatchedSetException mse = (MismatchedSetException)e;
         msg = "mismatched input " + this.getTokenErrorDisplay(e.token) + " expecting set " + mse.expecting;
      } else if (e instanceof MismatchedNotSetException) {
         MismatchedNotSetException mse = (MismatchedNotSetException)e;
         msg = "mismatched input " + this.getTokenErrorDisplay(e.token) + " expecting set " + mse.expecting;
      } else if (e instanceof FailedPredicateException) {
         FailedPredicateException fpe = (FailedPredicateException)e;
         msg = "rule " + fpe.ruleName + " failed predicate: {" + fpe.predicateText + "}?";
      }

      return msg;
   }

   public int getNumberOfSyntaxErrors() {
      return this.state.syntaxErrors;
   }

   public String getErrorHeader(RecognitionException e) {
      return this.getSourceName() != null ? this.getSourceName() + " line " + e.line + ":" + e.charPositionInLine : "line " + e.line + ":" + e.charPositionInLine;
   }

   public String getTokenErrorDisplay(Token t) {
      String s = t.getText();
      if (s == null) {
         if (t.getType() == -1) {
            s = "<EOF>";
         } else {
            s = "<" + t.getType() + ">";
         }
      }

      s = s.replaceAll("\n", "\\\\n");
      s = s.replaceAll("\r", "\\\\r");
      s = s.replaceAll("\t", "\\\\t");
      return "'" + s + "'";
   }

   public void emitErrorMessage(String msg) {
      System.err.println(msg);
   }

   public void recover(IntStream input, RecognitionException re) {
      if (this.state.lastErrorIndex == input.index()) {
         input.consume();
      }

      this.state.lastErrorIndex = input.index();
      BitSet followSet = this.computeErrorRecoverySet();
      this.beginResync();
      this.consumeUntil(input, followSet);
      this.endResync();
   }

   public void beginResync() {
   }

   public void endResync() {
   }

   protected BitSet computeErrorRecoverySet() {
      return this.combineFollows(false);
   }

   protected BitSet computeContextSensitiveRuleFOLLOW() {
      return this.combineFollows(true);
   }

   protected BitSet combineFollows(boolean exact) {
      int top = this.state._fsp;
      BitSet followSet = new BitSet();

      for(int i = top; i >= 0; --i) {
         BitSet localFollowSet = this.state.following[i];
         followSet.orInPlace(localFollowSet);
         if (exact) {
            if (!localFollowSet.member(1)) {
               break;
            }

            if (i > 0) {
               followSet.remove(1);
            }
         }
      }

      return followSet;
   }

   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      RecognitionException e = null;
      Object inserted;
      if (this.mismatchIsUnwantedToken(input, ttype)) {
         RecognitionException e = new UnwantedTokenException(ttype, input);
         this.beginResync();
         input.consume();
         this.endResync();
         this.reportError(e);
         inserted = this.getCurrentInputSymbol(input);
         input.consume();
         return inserted;
      } else if (this.mismatchIsMissingToken(input, follow)) {
         inserted = this.getMissingSymbol(input, e, ttype, follow);
         RecognitionException e = new MissingTokenException(ttype, input, inserted);
         this.reportError(e);
         return inserted;
      } else {
         e = new MismatchedTokenException(ttype, input);
         throw e;
      }
   }

   public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
      if (this.mismatchIsMissingToken(input, follow)) {
         this.reportError(e);
         return this.getMissingSymbol(input, e, 0, follow);
      } else {
         throw e;
      }
   }

   protected Object getCurrentInputSymbol(IntStream input) {
      return null;
   }

   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow) {
      return null;
   }

   public void consumeUntil(IntStream input, int tokenType) {
      for(int ttype = input.LA(1); ttype != -1 && ttype != tokenType; ttype = input.LA(1)) {
         input.consume();
      }

   }

   public void consumeUntil(IntStream input, BitSet set) {
      for(int ttype = input.LA(1); ttype != -1 && !set.member(ttype); ttype = input.LA(1)) {
         input.consume();
      }

   }

   protected void pushFollow(BitSet fset) {
      if (this.state._fsp + 1 >= this.state.following.length) {
         BitSet[] f = new BitSet[this.state.following.length * 2];
         System.arraycopy(this.state.following, 0, f, 0, this.state.following.length);
         this.state.following = f;
      }

      this.state.following[++this.state._fsp] = fset;
   }

   public List getRuleInvocationStack() {
      String parserClassName = this.getClass().getName();
      return getRuleInvocationStack(new Throwable(), parserClassName);
   }

   public static List getRuleInvocationStack(Throwable e, String recognizerClassName) {
      List rules = new ArrayList();
      StackTraceElement[] stack = e.getStackTrace();

      for(int i = stack.length - 1; i >= 0; --i) {
         StackTraceElement t = stack[i];
         if (!t.getClassName().startsWith("org.antlr.runtime.") && !t.getMethodName().equals("nextToken") && t.getClassName().equals(recognizerClassName)) {
            rules.add(t.getMethodName());
         }
      }

      return rules;
   }

   public int getBacktrackingLevel() {
      return this.state.backtracking;
   }

   public void setBacktrackingLevel(int n) {
      this.state.backtracking = n;
   }

   public boolean failed() {
      return this.state.failed;
   }

   public String[] getTokenNames() {
      return null;
   }

   public String getGrammarFileName() {
      return null;
   }

   public abstract String getSourceName();

   public List toStrings(List tokens) {
      if (tokens == null) {
         return null;
      } else {
         List strings = new ArrayList(tokens.size());

         for(int i = 0; i < tokens.size(); ++i) {
            strings.add(((Token)tokens.get(i)).getText());
         }

         return strings;
      }
   }

   public int getRuleMemoization(int ruleIndex, int ruleStartIndex) {
      if (this.state.ruleMemo[ruleIndex] == null) {
         this.state.ruleMemo[ruleIndex] = new HashMap();
      }

      Integer stopIndexI = (Integer)this.state.ruleMemo[ruleIndex].get(ruleStartIndex);
      return stopIndexI == null ? -1 : stopIndexI;
   }

   public boolean alreadyParsedRule(IntStream input, int ruleIndex) {
      int stopIndex = this.getRuleMemoization(ruleIndex, input.index());
      if (stopIndex == -1) {
         return false;
      } else {
         if (stopIndex == -2) {
            this.state.failed = true;
         } else {
            input.seek(stopIndex + 1);
         }

         return true;
      }
   }

   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex) {
      int stopTokenIndex = this.state.failed ? -2 : input.index() - 1;
      if (this.state.ruleMemo == null) {
         System.err.println("!!!!!!!!! memo array is null for " + this.getGrammarFileName());
      }

      if (ruleIndex >= this.state.ruleMemo.length) {
         System.err.println("!!!!!!!!! memo size is " + this.state.ruleMemo.length + ", but rule index is " + ruleIndex);
      }

      if (this.state.ruleMemo[ruleIndex] != null) {
         this.state.ruleMemo[ruleIndex].put(ruleStartIndex, stopTokenIndex);
      }

   }

   public int getRuleMemoizationCacheSize() {
      int n = 0;

      for(int i = 0; this.state.ruleMemo != null && i < this.state.ruleMemo.length; ++i) {
         Map ruleMap = this.state.ruleMemo[i];
         if (ruleMap != null) {
            n += ruleMap.size();
         }
      }

      return n;
   }

   public void traceIn(String ruleName, int ruleIndex, Object inputSymbol) {
      System.out.print("enter " + ruleName + " " + inputSymbol);
      if (this.state.backtracking > 0) {
         System.out.print(" backtracking=" + this.state.backtracking);
      }

      System.out.println();
   }

   public void traceOut(String ruleName, int ruleIndex, Object inputSymbol) {
      System.out.print("exit " + ruleName + " " + inputSymbol);
      if (this.state.backtracking > 0) {
         System.out.print(" backtracking=" + this.state.backtracking);
         if (this.state.failed) {
            System.out.print(" failed");
         } else {
            System.out.print(" succeeded");
         }
      }

      System.out.println();
   }
}
