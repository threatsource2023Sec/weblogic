package org.apache.openjpa.event;

public interface DirtyListener {
   void beforeDirty(LifecycleEvent var1);

   void afterDirty(LifecycleEvent var1);

   void beforeDirtyFlushed(LifecycleEvent var1);

   void afterDirtyFlushed(LifecycleEvent var1);
}
