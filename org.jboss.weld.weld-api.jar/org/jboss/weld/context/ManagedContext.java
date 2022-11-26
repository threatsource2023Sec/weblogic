package org.jboss.weld.context;

public interface ManagedContext extends WeldAlterableContext {
   void activate();

   void deactivate();

   void invalidate();
}
