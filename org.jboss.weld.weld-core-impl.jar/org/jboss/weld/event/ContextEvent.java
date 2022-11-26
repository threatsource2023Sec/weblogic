package org.jboss.weld.event;

import java.io.Serializable;

public final class ContextEvent implements Serializable {
   private static final long serialVersionUID = -1197351184144276424L;
   public static final ContextEvent APPLICATION_INITIALIZED = new ContextEvent("Application context initialized.");
   public static final ContextEvent APPLICATION_BEFORE_DESTROYED = new ContextEvent("Application context is about to be destroyed.");
   public static final ContextEvent APPLICATION_DESTROYED = new ContextEvent("Application context destroyed.");
   public static final Object REQUEST_INITIALIZED_EJB = new ContextEvent("Request context initialized for EJB invocation");
   public static final Object REQUEST_BEFORE_DESTROYED_EJB = new ContextEvent("Request context is about to be destroyed after EJB invocation");
   public static final Object REQUEST_DESTROYED_EJB = new ContextEvent("Request context destroyed after EJB invocation");
   private final String message;

   ContextEvent(String message) {
      this.message = message;
   }

   public String toString() {
      return this.message;
   }
}
