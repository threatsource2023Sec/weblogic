package com.oracle.weblogic.diagnostics.watch.actions;

public abstract class ActionAdapter implements Action {
   private String actionType;
   private boolean canceled;

   public ActionAdapter(String actionServiceName) {
      this.actionType = actionServiceName;
   }

   public String getType() {
      return this.actionType;
   }

   public void cancel() {
      this.canceled = true;
   }

   public void reset() {
      this.canceled = false;
   }

   protected boolean isCanceled() {
      return this.canceled;
   }
}
