package org.glassfish.tyrus.core;

public class ServiceConfigurationError extends Error {
   private static final long serialVersionUID = -8532392338326428074L;

   public ServiceConfigurationError(String msg) {
      super(msg);
   }

   public ServiceConfigurationError(Throwable x) {
      super(x);
   }
}
