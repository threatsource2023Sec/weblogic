package org.jboss.weld.ejb.api;

import java.io.Serializable;

public interface SessionObjectReference extends Serializable {
   Object getBusinessObject(Class var1);

   void remove();

   boolean isRemoved();
}
