package org.apache.openjpa.event;

public interface PersistListener {
   void beforePersist(LifecycleEvent var1);

   void afterPersist(LifecycleEvent var1);
}
