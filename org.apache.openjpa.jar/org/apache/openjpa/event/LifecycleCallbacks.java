package org.apache.openjpa.event;

import java.io.Serializable;

public interface LifecycleCallbacks extends Serializable {
   boolean hasCallback(Object var1, int var2);

   void makeCallback(Object var1, Object var2, int var3) throws Exception;
}
