package org.jboss.weld.security;

import java.security.PrivilegedAction;

public class GetClassLoaderAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetClassLoaderAction(Class javaClass) {
      super(javaClass);
   }

   public ClassLoader run() {
      return this.javaClass.getClassLoader();
   }
}
