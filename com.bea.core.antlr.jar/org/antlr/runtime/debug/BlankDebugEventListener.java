package org.antlr.runtime.debug;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public class BlankDebugEventListener implements DebugEventListener {
   public void enterRule(String grammarFileName, String ruleName) {
   }

   public void exitRule(String grammarFileName, String ruleName) {
   }

   public void enterAlt(int alt) {
   }

   public void enterSubRule(int decisionNumber) {
   }

   public void exitSubRule(int decisionNumber) {
   }

   public void enterDecision(int decisionNumber, boolean couldBacktrack) {
   }

   public void exitDecision(int decisionNumber) {
   }

   public void location(int line, int pos) {
   }

   public void consumeToken(Token token) {
   }

   public void consumeHiddenToken(Token token) {
   }

   public void LT(int i, Token t) {
   }

   public void mark(int i) {
   }

   public void rewind(int i) {
   }

   public void rewind() {
   }

   public void beginBacktrack(int level) {
   }

   public void endBacktrack(int level, boolean successful) {
   }

   public void recognitionException(RecognitionException e) {
   }

   public void beginResync() {
   }

   public void endResync() {
   }

   public void semanticPredicate(boolean result, String predicate) {
   }

   public void commence() {
   }

   public void terminate() {
   }

   public void consumeNode(Object t) {
   }

   public void LT(int i, Object t) {
   }

   public void nilNode(Object t) {
   }

   public void errorNode(Object t) {
   }

   public void createNode(Object t) {
   }

   public void createNode(Object node, Token token) {
   }

   public void becomeRoot(Object newRoot, Object oldRoot) {
   }

   public void addChild(Object root, Object child) {
   }

   public void setTokenBoundaries(Object t, int tokenStartIndex, int tokenStopIndex) {
   }
}
