package org.antlr.runtime.tree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

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
      TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
      return adaptor.create(new CommonToken(expectedTokenType, tokenText));
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

   public boolean inContext(String context) {
      return inContext(this.input.getTreeAdaptor(), this.getTokenNames(), this.input.LT(1), context);
   }

   public static boolean inContext(TreeAdaptor adaptor, String[] tokenNames, Object t, String context) {
      Matcher dotdotMatcher = dotdotPattern.matcher(context);
      Matcher doubleEtcMatcher = doubleEtcPattern.matcher(context);
      if (dotdotMatcher.find()) {
         throw new IllegalArgumentException("invalid syntax: ..");
      } else if (doubleEtcMatcher.find()) {
         throw new IllegalArgumentException("invalid syntax: ... ...");
      } else {
         context = context.replaceAll("\\.\\.\\.", " ... ");
         context = context.trim();
         String[] nodes = context.split("\\s+");
         int ni = nodes.length - 1;

         for(t = adaptor.getParent(t); ni >= 0 && t != null; t = adaptor.getParent(t)) {
            String goal;
            if (nodes[ni].equals("...")) {
               if (ni == 0) {
                  return true;
               }

               goal = nodes[ni - 1];
               Object ancestor = getAncestor(adaptor, tokenNames, t, goal);
               if (ancestor == null) {
                  return false;
               }

               t = ancestor;
               --ni;
            }

            goal = tokenNames[adaptor.getType(t)];
            if (!goal.equals(nodes[ni])) {
               return false;
            }

            --ni;
         }

         return t != null || ni < 0;
      }
   }

   protected static Object getAncestor(TreeAdaptor adaptor, String[] tokenNames, Object t, String goal) {
      while(t != null) {
         String name = tokenNames[adaptor.getType(t)];
         if (name.equals(goal)) {
            return t;
         }

         t = adaptor.getParent(t);
      }

      return null;
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
