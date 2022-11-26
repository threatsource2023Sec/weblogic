package org.jboss.weld.security;

public abstract class AbstractGenericReflectionAction {
   protected final Class javaClass;

   public AbstractGenericReflectionAction(Class javaClass) {
      this.javaClass = javaClass;
   }
}
