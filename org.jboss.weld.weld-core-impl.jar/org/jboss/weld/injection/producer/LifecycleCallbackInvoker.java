package org.jboss.weld.injection.producer;

public interface LifecycleCallbackInvoker {
   void postConstruct(Object var1, Instantiator var2);

   void preDestroy(Object var1, Instantiator var2);

   boolean hasPreDestroyMethods();

   boolean hasPostConstructMethods();

   default boolean hasPostConstructCallback() {
      return this.hasPostConstructMethods();
   }
}
