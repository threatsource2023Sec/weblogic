package org.jboss.weld.contexts.beanstore;

public interface BoundBeanStore extends BeanStore {
   boolean detach();

   boolean attach();

   boolean isAttached();
}
