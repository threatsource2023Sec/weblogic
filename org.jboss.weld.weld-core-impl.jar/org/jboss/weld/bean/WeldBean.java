package org.jboss.weld.bean;

import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.serialization.spi.BeanIdentifier;

public interface WeldBean extends Bean {
   BeanIdentifier getIdentifier();

   default Integer getPriority() {
      return null;
   }
}
