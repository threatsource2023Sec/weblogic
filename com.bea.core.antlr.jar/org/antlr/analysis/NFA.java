package org.antlr.analysis;

import org.antlr.tool.Grammar;
import org.antlr.tool.NFAFactory;

public class NFA {
   public static final int INVALID_ALT_NUMBER = -1;
   public Grammar grammar;
   protected NFAFactory factory = null;
   public boolean complete;

   public NFA(Grammar g) {
      this.grammar = g;
   }

   public int getNewNFAStateNumber() {
      return this.grammar.composite.getNewNFAStateNumber();
   }

   public void addState(NFAState state) {
      this.grammar.composite.addState(state);
   }

   public NFAState getState(int s) {
      return this.grammar.composite.getState(s);
   }

   public NFAFactory getFactory() {
      return this.factory;
   }

   public void setFactory(NFAFactory factory) {
      this.factory = factory;
   }
}
