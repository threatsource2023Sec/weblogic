package org.antlr.analysis;

public class StateCluster {
   public NFAState left;
   public NFAState right;

   public StateCluster(NFAState left, NFAState right) {
      this.left = left;
      this.right = right;
   }
}
