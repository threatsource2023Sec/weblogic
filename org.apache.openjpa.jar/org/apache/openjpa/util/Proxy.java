package org.apache.openjpa.util;

import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface Proxy {
   void setOwner(OpenJPAStateManager var1, int var2);

   OpenJPAStateManager getOwner();

   int getOwnerField();

   ChangeTracker getChangeTracker();

   Object copy(Object var1);
}
