package org.antlr.runtime.debug;

import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public class DebugEventHub implements DebugEventListener {
   protected List listeners = new ArrayList();

   public DebugEventHub(DebugEventListener listener) {
      this.listeners.add(listener);
   }

   public DebugEventHub(DebugEventListener a, DebugEventListener b) {
      this.listeners.add(a);
      this.listeners.add(b);
   }

   public void addListener(DebugEventListener listener) {
      this.listeners.add(listener);
   }

   public void enterRule(String grammarFileName, String ruleName) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.enterRule(grammarFileName, ruleName);
      }

   }

   public void exitRule(String grammarFileName, String ruleName) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.exitRule(grammarFileName, ruleName);
      }

   }

   public void enterAlt(int alt) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.enterAlt(alt);
      }

   }

   public void enterSubRule(int decisionNumber) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.enterSubRule(decisionNumber);
      }

   }

   public void exitSubRule(int decisionNumber) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.exitSubRule(decisionNumber);
      }

   }

   public void enterDecision(int decisionNumber, boolean couldBacktrack) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.enterDecision(decisionNumber, couldBacktrack);
      }

   }

   public void exitDecision(int decisionNumber) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.exitDecision(decisionNumber);
      }

   }

   public void location(int line, int pos) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.location(line, pos);
      }

   }

   public void consumeToken(Token token) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.consumeToken(token);
      }

   }

   public void consumeHiddenToken(Token token) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.consumeHiddenToken(token);
      }

   }

   public void LT(int index, Token t) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.LT(index, t);
      }

   }

   public void mark(int index) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.mark(index);
      }

   }

   public void rewind(int index) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.rewind(index);
      }

   }

   public void rewind() {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.rewind();
      }

   }

   public void beginBacktrack(int level) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.beginBacktrack(level);
      }

   }

   public void endBacktrack(int level, boolean successful) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.endBacktrack(level, successful);
      }

   }

   public void recognitionException(RecognitionException e) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.recognitionException(e);
      }

   }

   public void beginResync() {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.beginResync();
      }

   }

   public void endResync() {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.endResync();
      }

   }

   public void semanticPredicate(boolean result, String predicate) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.semanticPredicate(result, predicate);
      }

   }

   public void commence() {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.commence();
      }

   }

   public void terminate() {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.terminate();
      }

   }

   public void consumeNode(Object t) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.consumeNode(t);
      }

   }

   public void LT(int index, Object t) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.LT(index, t);
      }

   }

   public void nilNode(Object t) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.nilNode(t);
      }

   }

   public void errorNode(Object t) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.errorNode(t);
      }

   }

   public void createNode(Object t) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.createNode(t);
      }

   }

   public void createNode(Object node, Token token) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.createNode(node, token);
      }

   }

   public void becomeRoot(Object newRoot, Object oldRoot) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.becomeRoot(newRoot, oldRoot);
      }

   }

   public void addChild(Object root, Object child) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.addChild(root, child);
      }

   }

   public void setTokenBoundaries(Object t, int tokenStartIndex, int tokenStopIndex) {
      for(int i = 0; i < this.listeners.size(); ++i) {
         DebugEventListener listener = (DebugEventListener)this.listeners.get(i);
         listener.setTokenBoundaries(t, tokenStartIndex, tokenStopIndex);
      }

   }
}
