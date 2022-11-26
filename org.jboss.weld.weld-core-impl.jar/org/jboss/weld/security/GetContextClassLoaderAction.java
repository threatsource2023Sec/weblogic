package org.jboss.weld.security;

import java.security.PrivilegedAction;

public class GetContextClassLoaderAction implements PrivilegedAction {
   public static final GetContextClassLoaderAction INSTANCE = new GetContextClassLoaderAction();

   private GetContextClassLoaderAction() {
   }

   public ClassLoader run() {
      return Thread.currentThread().getContextClassLoader();
   }
}
