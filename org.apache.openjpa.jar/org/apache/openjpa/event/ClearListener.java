package org.apache.openjpa.event;

public interface ClearListener {
   void beforeClear(LifecycleEvent var1);

   void afterClear(LifecycleEvent var1);
}
