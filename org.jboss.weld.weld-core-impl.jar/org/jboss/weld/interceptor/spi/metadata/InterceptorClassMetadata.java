package org.jboss.weld.interceptor.spi.metadata;

import java.io.Serializable;

public interface InterceptorClassMetadata extends InterceptorMetadata {
   InterceptorFactory getInterceptorFactory();

   Class getJavaClass();

   default Serializable getKey() {
      return this.getJavaClass();
   }
}
