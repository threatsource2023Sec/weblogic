package org.apache.openjpa.event;

public interface DetachListener {
   void beforeDetach(LifecycleEvent var1);

   void afterDetach(LifecycleEvent var1);
}
