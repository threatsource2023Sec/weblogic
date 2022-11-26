package com.solarmetric.manage;

import java.util.Collection;

public interface Watchable {
   Collection getStatistics();

   boolean addListener(WatchableListener var1);

   boolean removeListener(WatchableListener var1);

   void clearListeners();
}
