package org.apache.openjpa.event;

public interface UpdateListener {
   void beforeUpdate(LifecycleEvent var1);

   void afterUpdatePerformed(LifecycleEvent var1);
}
