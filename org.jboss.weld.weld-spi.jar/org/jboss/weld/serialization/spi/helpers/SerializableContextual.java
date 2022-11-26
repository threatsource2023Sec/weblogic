package org.jboss.weld.serialization.spi.helpers;

import java.io.Serializable;
import javax.enterprise.context.spi.Contextual;

public interface SerializableContextual extends Serializable, Contextual {
   Contextual get();
}
