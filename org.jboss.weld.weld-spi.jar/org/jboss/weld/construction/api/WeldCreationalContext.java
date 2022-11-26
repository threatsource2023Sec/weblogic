package org.jboss.weld.construction.api;

import javax.enterprise.context.spi.CreationalContext;

public interface WeldCreationalContext extends CreationalContext {
   void setConstructorInterceptionSuppressed(boolean var1);

   boolean isConstructorInterceptionSuppressed();

   void registerAroundConstructCallback(AroundConstructCallback var1);
}
