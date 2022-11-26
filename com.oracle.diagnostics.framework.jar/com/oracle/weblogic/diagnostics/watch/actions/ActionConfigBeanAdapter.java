package com.oracle.weblogic.diagnostics.watch.actions;

public abstract class ActionConfigBeanAdapter implements ActionConfigBean {
   private String name;
   private boolean enabled;
   private String actionType;
   private int timeout;

   public ActionConfigBeanAdapter(String actionServiceName) {
      this.actionType = actionServiceName;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public String getType() {
      return this.actionType;
   }

   public int getTimeout() {
      return this.timeout;
   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }
}
