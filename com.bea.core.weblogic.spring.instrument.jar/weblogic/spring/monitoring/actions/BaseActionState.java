package weblogic.spring.monitoring.actions;

import weblogic.diagnostics.instrumentation.DiagnosticActionState;

public class BaseActionState implements DiagnosticActionState {
   private static int seqNum;
   private int id = genId();
   private Object springBean;
   private boolean succeeded = true;

   public int getId() {
      return this.id;
   }

   public Object getSpringBean() {
      return this.springBean;
   }

   public void setSpringBean(Object springBean) {
      this.springBean = springBean;
   }

   public void setSucceeded(boolean succeeded) {
      this.succeeded = succeeded;
   }

   public boolean getSucceeded() {
      return this.succeeded;
   }

   private static synchronized int genId() {
      ++seqNum;
      return seqNum;
   }
}
