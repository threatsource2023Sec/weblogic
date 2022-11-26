package org.antlr.runtime.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.ParseTree;

public class ParseTreeBuilder extends BlankDebugEventListener {
   public static final String EPSILON_PAYLOAD = "<epsilon>";
   Stack callStack = new Stack();
   List hiddenTokens = new ArrayList();
   int backtracking = 0;

   public ParseTreeBuilder(String grammarName) {
      ParseTree root = this.create("<grammar " + grammarName + ">");
      this.callStack.push(root);
   }

   public ParseTree getTree() {
      return (ParseTree)this.callStack.elementAt(0);
   }

   public ParseTree create(Object payload) {
      return new ParseTree(payload);
   }

   public ParseTree epsilonNode() {
      return this.create("<epsilon>");
   }

   public void enterDecision(int d, boolean couldBacktrack) {
      ++this.backtracking;
   }

   public void exitDecision(int i) {
      --this.backtracking;
   }

   public void enterRule(String filename, String ruleName) {
      if (this.backtracking <= 0) {
         ParseTree parentRuleNode = (ParseTree)this.callStack.peek();
         ParseTree ruleNode = this.create(ruleName);
         parentRuleNode.addChild(ruleNode);
         this.callStack.push(ruleNode);
      }
   }

   public void exitRule(String filename, String ruleName) {
      if (this.backtracking <= 0) {
         ParseTree ruleNode = (ParseTree)this.callStack.peek();
         if (ruleNode.getChildCount() == 0) {
            ruleNode.addChild(this.epsilonNode());
         }

         this.callStack.pop();
      }
   }

   public void consumeToken(Token token) {
      if (this.backtracking <= 0) {
         ParseTree ruleNode = (ParseTree)this.callStack.peek();
         ParseTree elementNode = this.create(token);
         elementNode.hiddenTokens = this.hiddenTokens;
         this.hiddenTokens = new ArrayList();
         ruleNode.addChild(elementNode);
      }
   }

   public void consumeHiddenToken(Token token) {
      if (this.backtracking <= 0) {
         this.hiddenTokens.add(token);
      }
   }

   public void recognitionException(RecognitionException e) {
      if (this.backtracking <= 0) {
         ParseTree ruleNode = (ParseTree)this.callStack.peek();
         ParseTree errorNode = this.create(e);
         ruleNode.addChild(errorNode);
      }
   }
}
