package org.jboss.weld.context;

public interface BoundContext extends WeldAlterableContext {
   boolean associate(Object var1);

   boolean dissociate(Object var1);
}
