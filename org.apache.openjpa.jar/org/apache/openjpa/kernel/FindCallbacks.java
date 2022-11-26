package org.apache.openjpa.kernel;

public interface FindCallbacks {
   Object processArgument(Object var1);

   Object processReturn(Object var1, OpenJPAStateManager var2);
}
