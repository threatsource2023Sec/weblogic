package org.jboss.weld.serialization.spi;

import java.io.Serializable;

public interface BeanIdentifier extends Serializable {
   String BEAN_ID_SEPARATOR = "%";

   String asString();
}
