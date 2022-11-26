package org.apache.openjpa.event;

public interface AttachListener {
   void beforeAttach(LifecycleEvent var1);

   void afterAttach(LifecycleEvent var1);
}
