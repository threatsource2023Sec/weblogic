package org.apache.openjpa.lib.util;

import java.util.Map;

public interface SizedMap extends Map {
   int getMaxSize();

   void setMaxSize(int var1);

   boolean isFull();

   void overflowRemoved(Object var1, Object var2);
}
