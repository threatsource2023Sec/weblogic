package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public class DebugEventRepeater implements DebugEventListener {
   protected DebugEventListener listener;

   public DebugEventRepeater(DebugEventListener listener) {
      this.listener = listener;
   }

   public void enterRule(String grammarFileName, String ruleName) {
      this.listener.enterRule(grammarFileName, ruleName);
   }

   public void exitRule(String grammarFileName, String ruleName) {
      this.listener.exitRule(grammarFileName, ruleName);
   }

   public void enterAlt(int alt) {
      this.listener.enterAlt(alt);
   }

   public void enterSubRule(int decisionNumber) {
      this.listener.enterSubRule(decisionNumber);
   }

   public void exitSubRule(int decisionNumber) {
      this.listener.exitSubRule(decisionNumber);
   }

   public void enterDecision(int decisionNumber, boolean couldBacktrack) {
      this.listener.enterDecision(decisionNumber, couldBacktrack);
   }

   public void exitDecision(int decisionNumber) {
      this.listener.exitDecision(decisionNumber);
   }

   public void location(int line, int pos) {
      this.listener.location(line, pos);
   }

   public void consumeToken(Token token) {
      this.listener.consumeToken(token);
   }

   public void consumeHiddenToken(Token token) {
      this.listener.consumeHiddenToken(token);
   }

   public void LT(int i, Token t) {
      this.listener.LT(i, t);
   }

   public void mark(int i) {
      this.listener.mark(i);
   }

   public void rewind(int i) {
      this.listener.rewind(i);
   }

   public void rewind() {
      this.listener.rewind();
   }

   public void beginBacktrack(int level) {
      this.listener.beginBacktrack(level);
   }

   public void endBacktrack(int level, boolean successful) {
      this.listener.endBacktrack(level, successful);
   }

   public void recognitionException(RecognitionException e) {
      this.listener.recognitionException(e);
   }

   public void beginResync() {
      this.listener.beginResync();
   }

   public void endResync() {
      this.listener.endResync();
   }

   public void semanticPredicate(boolean result, String predicate) {
      this.listener.semanticPredicate(result, predicate);
   }

   public void commence() {
      this.listener.commence();
   }

   public void terminate() {
      this.listener.terminate();
   }

   public void consumeNode(Object t) {
      this.listener.consumeNode(t);
   }

   public void LT(int i, Object t) {
      this.listener.LT(i, t);
   }

   public void nilNode(Object t) {
      this.listener.nilNode(t);
   }

   public void errorNode(Object t) {
      this.listener.errorNode(t);
   }

   public void createNode(Object t) {
      this.listener.createNode(t);
   }

   public void createNode(Object node, Token token) {
      this.listener.createNode(node, token);
   }

   public void becomeRoot(Object newRoot, Object oldRoot) {
      this.listener.becomeRoot(newRoot, oldRoot);
   }

   public void addChild(Object root, Object child) {
      this.listener.addChild(root, child);
   }

   public void setTokenBoundaries(Object t, int tokenStartIndex, int tokenStopIndex) {
      this.listener.setTokenBoundaries(t, tokenStartIndex, tokenStopIndex);
   }
}
