package org.jboss.weld;

public enum ContainerState {
   STOPPED(false),
   STARTING(false),
   DISCOVERED(false),
   DEPLOYED(true),
   VALIDATED(true),
   INITIALIZED(true),
   SHUTDOWN(false);

   final boolean available;

   private ContainerState(boolean available) {
      this.available = available;
   }

   public boolean isAvailable() {
      return this.available;
   }
}
