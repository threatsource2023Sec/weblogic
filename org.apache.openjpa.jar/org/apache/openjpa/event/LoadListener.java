package org.apache.openjpa.event;

public interface LoadListener {
   void afterLoad(LifecycleEvent var1);

   void afterRefresh(LifecycleEvent var1);
}
