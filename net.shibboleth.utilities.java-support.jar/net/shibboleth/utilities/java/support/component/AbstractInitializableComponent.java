package net.shibboleth.utilities.java.support.component;

public abstract class AbstractInitializableComponent implements DestructableComponent, InitializableComponent {
   private boolean isDestroyed;
   private boolean isInitialized;

   public final boolean isDestroyed() {
      return this.isDestroyed;
   }

   public boolean isInitialized() {
      return this.isInitialized;
   }

   public final synchronized void destroy() {
      if (!this.isDestroyed) {
         this.doDestroy();
         this.isDestroyed = true;
      }
   }

   public final synchronized void initialize() throws ComponentInitializationException {
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (!this.isInitialized()) {
         this.doInitialize();
         this.isInitialized = true;
      }
   }

   protected void doDestroy() {
   }

   protected void doInitialize() throws ComponentInitializationException {
   }
}
