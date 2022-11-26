package org.apache.openjpa.event;

public interface StoreListener {
   void beforeStore(LifecycleEvent var1);

   void afterStore(LifecycleEvent var1);
}
