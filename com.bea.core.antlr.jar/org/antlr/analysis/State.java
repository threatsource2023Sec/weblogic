package org.antlr.analysis;

public abstract class State {
   public static final int INVALID_STATE_NUMBER = -1;
   public int stateNumber = -1;
   protected boolean acceptState = false;

   public abstract int getNumberOfTransitions();

   public abstract void addTransition(Transition var1);

   public abstract Transition transition(int var1);

   public boolean isAcceptState() {
      return this.acceptState;
   }

   public void setAcceptState(boolean acceptState) {
      this.acceptState = acceptState;
   }
}
