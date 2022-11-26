package com.bea.diagnostics.notifications;

import weblogic.diagnostics.debug.DebugLogger;

public abstract class NotificationServiceAdapter implements NotificationService {
   private String name;
   private boolean enabled = true;
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsNotifications");

   public NotificationServiceAdapter() {
   }

   public NotificationServiceAdapter(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   protected void setName(String name) {
      this.name = name;
   }

   public abstract String getType();

   public synchronized boolean isEnabled() {
      return this.enabled;
   }

   public synchronized void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public void initialize() {
   }

   public void destroy() {
      this.setEnabled(false);
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.destroy();
   }
}
