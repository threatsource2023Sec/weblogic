package org.glassfish.hk2.configuration.hub.api;

import java.beans.PropertyChangeEvent;

public interface WriteableType extends Type {
   Instance addInstance(String var1, Object var2);

   Instance addInstance(String var1, Object var2, Object var3);

   Instance removeInstance(String var1);

   PropertyChangeEvent[] modifyInstance(String var1, Object var2, PropertyChangeEvent... var3);
}
