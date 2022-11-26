package org.jboss.weld.serialization;

import java.io.Serializable;

public interface SerializableHolder extends Serializable {
   Object get();
}
