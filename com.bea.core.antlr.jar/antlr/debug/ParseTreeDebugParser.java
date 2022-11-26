package antlr.debug;

import antlr.CommonToken;
import antlr.LLkParser;
import antlr.MismatchedTokenException;
import antlr.ParseTree;
import antlr.ParseTreeRule;
import antlr.ParseTreeToken;
import antlr.ParserSharedInputState;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.util.Stack;

public class ParseTreeDebugParser extends LLkParser {
   protected Stack currentParseTreeRoot = new Stack();
   protected ParseTreeRule mostRecentParseTreeRoot = null;
   protected int numberOfDerivationSteps = 1;

   public ParseTreeDebugParser(int var1) {
      super(var1);
   }

   public ParseTreeDebugParser(ParserSharedInputState var1, int var2) {
      super(var1, var2);
   }

   public ParseTreeDebugParser(TokenBuffer var1, int var2) {
      super(var1, var2);
   }

   public ParseTreeDebugParser(TokenStream var1, int var2) {
      super(var1, var2);
   }

   public ParseTree getParseTree() {
      return this.mostRecentParseTreeRoot;
   }

   public int getNumberOfDerivationSteps() {
      return this.numberOfDerivationSteps;
   }

   public void match(int var1) throws MismatchedTokenException, TokenStreamException {
      this.addCurrentTokenToParseTree();
      super.match(var1);
   }

   public void match(BitSet var1) throws MismatchedTokenException, TokenStreamException {
      this.addCurrentTokenToParseTree();
      super.match(var1);
   }

   public void matchNot(int var1) throws MismatchedTokenException, TokenStreamException {
      this.addCurrentTokenToParseTree();
      super.matchNot(var1);
   }

   protected void addCurrentTokenToParseTree() throws TokenStreamException {
      if (this.inputState.guessing <= 0) {
         ParseTreeRule var1 = (ParseTreeRule)this.currentParseTreeRoot.peek();
         ParseTreeToken var2 = null;
         if (this.LA(1) == 1) {
            var2 = new ParseTreeToken(new CommonToken("EOF"));
         } else {
            var2 = new ParseTreeToken(this.LT(1));
         }

         var1.addChild(var2);
      }
   }

   public void traceIn(String var1) throws TokenStreamException {
      if (this.inputState.guessing <= 0) {
         ParseTreeRule var2 = new ParseTreeRule(var1);
         if (this.currentParseTreeRoot.size() > 0) {
            ParseTreeRule var3 = (ParseTreeRule)this.currentParseTreeRoot.peek();
            var3.addChild(var2);
         }

         this.currentParseTreeRoot.push(var2);
         ++this.numberOfDerivationSteps;
      }
   }

   public void traceOut(String var1) throws TokenStreamException {
      if (this.inputState.guessing <= 0) {
         this.mostRecentParseTreeRoot = (ParseTreeRule)this.currentParseTreeRoot.pop();
      }
   }
}
