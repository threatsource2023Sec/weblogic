package org.python.antlr.runtime.tree;

import java.util.regex.Pattern;
import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.BitSet;
import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.MismatchedTreeNodeException;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;

public class TreeParser extends BaseRecognizer {
   public static final int DOWN = 2;
   public static final int UP = 3;
   static String dotdot = ".*[^.]\\.\\.[^.].*";
   static String doubleEtc = ".*\\.\\.\\.\\s+\\.\\.\\..*";
   static Pattern dotdotPattern;
   static Pattern doubleEtcPattern;
   protected TreeNodeStream input;

   public TreeParser(TreeNodeStream input) {
      this.setTreeNodeStream(input);
   }

   public TreeParser(TreeNodeStream input, RecognizerSharedState state) {
      super(state);
      this.setTreeNodeStream(input);
   }

   public void reset() {
      super.reset();
      if (this.input != null) {
         this.input.seek(0);
      }

   }

   public void setTreeNodeStream(TreeNodeStream input) {
      this.input = input;
   }

   public TreeNodeStream getTreeNodeStream() {
      return this.input;
   }

   public String getSourceName() {
      return this.input.getSourceName();
   }

   protected Object getCurrentInputSymbol(IntStream input) {
      return ((TreeNodeStream)input).LT(1);
   }

   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow) {
      String tokenText = "<missing " + this.getTokenNames()[expectedTokenType] + ">";
      return new CommonTree(new CommonToken(expectedTokenType, tokenText));
   }

   public void matchAny(IntStream ignore) {
      this.state.errorRecovery = false;
      this.state.failed = false;
      Object look = this.input.LT(1);
      if (this.input.getTreeAdaptor().getChildCount(look) == 0) {
         this.input.consume();
      } else {
         int level = 0;
         int tokenType = this.input.getTreeAdaptor().getType(look);

         while(tokenType != -1 && (tokenType != 3 || level != 0)) {
            this.input.consume();
            look = this.input.LT(1);
            tokenType = this.input.getTreeAdaptor().getType(look);
            if (tokenType == 2) {
               ++level;
            } else if (tokenType == 3) {
               --level;
            }
         }

         this.input.consume();
      }
   }

   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      throw new MismatchedTreeNodeException(ttype, (TreeNodeStream)input);
   }

   public String getErrorHeader(RecognitionException e) {
      return this.getGrammarFileName() + ": node from " + (e.approximateLineInfo ? "after " : "") + "line " + e.line + ":" + e.charPositionInLine;
   }

   public String getErrorMessage(RecognitionException e, String[] tokenNames) {
      if (this instanceof TreeParser) {
         TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
         e.token = adaptor.getToken(e.node);
         if (e.token == null) {
            e.token = new CommonToken(adaptor.getType(e.node), adaptor.getText(e.node));
         }
      }

      return super.getErrorMessage(e, tokenNames);
   }

   public void traceIn(String ruleName, int ruleIndex) {
      super.traceIn(ruleName, ruleIndex, this.input.LT(1));
   }

   public void traceOut(String ruleName, int ruleIndex) {
      super.traceOut(ruleName, ruleIndex, this.input.LT(1));
   }

   static {
      dotdotPattern = Pattern.compile(dotdot);
      doubleEtcPattern = Pattern.compile(doubleEtc);
   }
}
