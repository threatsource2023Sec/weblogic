package org.jboss.weld.security;

public abstract class AbstractReflectionAction {
   protected final Class javaClass;

   public AbstractReflectionAction(Class javaClass) {
      this.javaClass = javaClass;
   }
}
