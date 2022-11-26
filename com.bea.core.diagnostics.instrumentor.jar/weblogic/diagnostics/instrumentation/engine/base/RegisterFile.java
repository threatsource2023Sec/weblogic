package weblogic.diagnostics.instrumentation.engine.base;

class RegisterFile implements Cloneable {
   static final int AROUND_REGISTER_COUNT = 3;
   private int argumentsRegister = -1;
   private int returnValueRegister = -1;
   private int returnAddressRegister = -1;
   private int scratchRegister = -1;
   private int exceptionRegister = -1;
   private int joinpointRegister = -1;
   private int enabledFlagRegister = -1;
   private int actionsRegister = -1;
   private int actionStatesRegister = -1;
   private int localHolderRegister = -1;
   private boolean withinExecuteAdvice = false;
   private boolean localHolderReassigned = false;
   private RegisterFile template = null;
   private boolean localHolderRequired;

   public void setWithinExecuteAdvice() {
      this.withinExecuteAdvice = true;
   }

   public boolean getWithinExecuteAdvice() {
      return this.withinExecuteAdvice;
   }

   boolean getLocalHolderRequired() {
      return this.localHolderRequired;
   }

   int getArgumentsRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.argumentsRegister;
      }
   }

   void resetArgumentsRegister() {
      this.argumentsRegister = -1;
   }

   int getReturnValueRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.returnValueRegister;
      }
   }

   void resetReturnValueRegister() {
      this.returnValueRegister = -1;
   }

   int getReturnAddressRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.returnAddressRegister;
      }
   }

   int getScratchRegister() {
      return this.scratchRegister;
   }

   int getExceptionRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.exceptionRegister;
      }
   }

   int getJoinpointRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.joinpointRegister;
      }
   }

   int getEnabledFlagRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.enabledFlagRegister;
      }
   }

   int getActionsRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.actionsRegister;
      }
   }

   int getActionStatesRegister() {
      if (this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.actionStatesRegister;
      }
   }

   int getLocalHolderRegister() {
      if (!this.localHolderRequired) {
         throw new RuntimeException("register should not be used here");
      } else {
         return this.localHolderRegister;
      }
   }

   void resetLocalHolderRegister() {
      this.localHolderRegister = -1;
   }

   protected Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   protected RegisterFile duplicate() {
      RegisterFile r = null;

      try {
         r = (RegisterFile)this.clone();
         r.setTemplate(this);
      } catch (CloneNotSupportedException var3) {
      }

      return r;
   }

   private RegisterFile() {
   }

   RegisterFile(boolean localHolderRequired) {
      this.localHolderRequired = localHolderRequired;
   }

   int assignSharedRegisters(int firstAvailableRegister) {
      if (this.localHolderRequired) {
         this.localHolderRegister = firstAvailableRegister++;
      } else {
         this.joinpointRegister = firstAvailableRegister++;
         this.argumentsRegister = firstAvailableRegister++;
         this.returnValueRegister = firstAvailableRegister++;
         this.returnAddressRegister = firstAvailableRegister++;
         this.scratchRegister = firstAvailableRegister++;
         this.exceptionRegister = firstAvailableRegister++;
      }

      return firstAvailableRegister;
   }

   int assignAroundRegisters(int firstAvailableRegister, boolean assignEnabledFlagRegister) {
      if (this.localHolderRequired) {
         return firstAvailableRegister;
      } else {
         if (assignEnabledFlagRegister) {
            this.enabledFlagRegister = firstAvailableRegister++;
            this.actionsRegister = firstAvailableRegister++;
            this.actionStatesRegister = firstAvailableRegister++;
         }

         return firstAvailableRegister;
      }
   }

   int reassignLocalHolderRegister(int firstAvailableRegister) {
      if (this.localHolderReassigned) {
         return firstAvailableRegister;
      } else {
         if (this.template == null) {
            this.localHolderReassigned = true;
            this.localHolderRegister = firstAvailableRegister++;
         } else {
            this.localHolderReassigned = true;
            firstAvailableRegister = this.template.reassignLocalHolderRegister(firstAvailableRegister);
            this.localHolderRegister = this.template.getLocalHolderRegister();
         }

         return firstAvailableRegister;
      }
   }

   private void setTemplate(RegisterFile template) {
      this.template = template;
   }
}
