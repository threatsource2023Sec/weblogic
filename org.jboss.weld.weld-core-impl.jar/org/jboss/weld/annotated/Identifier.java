package org.jboss.weld.annotated;

import java.io.Serializable;

public interface Identifier extends Serializable {
   String ID_SEPARATOR = "|";

   String asString();
}
