package org.jboss.weld.contexts;

import org.jboss.weld.context.ManagedContext;
import org.jboss.weld.logging.ContextLogger;

public abstract class AbstractManagedContext extends AbstractContext implements ManagedContext {
   private final ThreadLocal state = new ThreadLocal();

   public AbstractManagedContext(String contextId, boolean multithreaded) {
      super(contextId, multithreaded);
   }

   public boolean isActive() {
      ManagedState managedState = (ManagedState)this.state.get();
      return managedState != null ? managedState.isActive() : false;
   }

   protected void setActive(boolean active) {
      this.getManagedState().setActive(active);
   }

   public void invalidate() {
      this.getManagedState().setValid(false);
   }

   public void activate() {
      this.setActive(true);
   }

   public boolean isValid() {
      ManagedState managedState = (ManagedState)this.state.get();
      return managedState != null ? managedState.isValid() : false;
   }

   public void deactivate() {
      if (!this.isValid()) {
         this.destroy();
      }

      this.removeState();
   }

   protected void removeState() {
      ContextLogger.LOG.tracev("State thread-local removed: {0}", this);
      this.state.remove();
   }

   private ManagedState getManagedState() {
      ManagedState managedState = (ManagedState)this.state.get();
      if (managedState == null) {
         managedState = new ManagedState();
         this.state.set(managedState);
      }

      return managedState;
   }

   private static class ManagedState {
      private boolean isActive;
      private boolean isValid;

      private ManagedState() {
         this.isActive = false;
         this.isValid = true;
      }

      boolean isActive() {
         return this.isActive;
      }

      void setActive(boolean isActive) {
         this.isActive = isActive;
      }

      boolean isValid() {
         return this.isValid;
      }

      void setValid(boolean isValid) {
         this.isValid = isValid;
      }

      // $FF: synthetic method
      ManagedState(Object x0) {
         this();
      }
   }
}
