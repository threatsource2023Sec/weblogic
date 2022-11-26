package com.bea.core.jatmi.internal;

class PrepareOpt {
   public boolean do1pc = false;
   public boolean isRecovery;

   public PrepareOpt(boolean isRecovery) {
      this.isRecovery = isRecovery;
   }
}
