package org.jboss.weld.injection.producer;

import org.jboss.weld.util.reflection.Reflections;

public class NoopLifecycleCallbackInvoker implements LifecycleCallbackInvoker {
   public static final NoopLifecycleCallbackInvoker INSTANCE = new NoopLifecycleCallbackInvoker();

   public static NoopLifecycleCallbackInvoker getInstance() {
      return (NoopLifecycleCallbackInvoker)Reflections.cast(INSTANCE);
   }

   public void postConstruct(Object instance, Instantiator instantiator) {
   }

   public void preDestroy(Object instance, Instantiator instantiator) {
   }

   public boolean hasPreDestroyMethods() {
      return true;
   }

   public boolean hasPostConstructMethods() {
      return true;
   }

   public boolean hasPostConstructCallback() {
      return false;
   }
}
