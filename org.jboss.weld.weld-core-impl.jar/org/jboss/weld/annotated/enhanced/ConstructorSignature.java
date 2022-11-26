package org.jboss.weld.annotated.enhanced;

import java.io.Serializable;

public interface ConstructorSignature extends Serializable {
   String[] getParameterTypes();
}
