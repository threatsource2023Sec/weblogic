package org.apache.openjpa.event;

public interface DeleteListener {
   void beforeDelete(LifecycleEvent var1);

   void afterDelete(LifecycleEvent var1);
}
