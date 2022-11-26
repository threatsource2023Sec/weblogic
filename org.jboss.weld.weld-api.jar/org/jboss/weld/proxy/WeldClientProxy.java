package org.jboss.weld.proxy;

import javax.enterprise.inject.spi.Bean;

public interface WeldClientProxy extends WeldConstruct {
   Metadata getMetadata();

   public interface Metadata {
      Bean getBean();

      Object getContextualInstance();
   }
}
