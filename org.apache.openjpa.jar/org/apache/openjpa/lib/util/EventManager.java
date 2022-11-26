package org.apache.openjpa.lib.util;

import java.util.Collection;

public interface EventManager {
   void addListener(Object var1);

   boolean removeListener(Object var1);

   boolean hasListener(Object var1);

   boolean hasListeners();

   Collection getListeners();

   Exception[] fireEvent(Object var1);
}
