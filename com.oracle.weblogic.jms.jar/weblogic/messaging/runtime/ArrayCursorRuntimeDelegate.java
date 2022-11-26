package weblogic.messaging.runtime;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;

public abstract class ArrayCursorRuntimeDelegate extends CursorRuntimeImpl {
   public ArrayCursorRuntimeDelegate(String mbeanName, RuntimeMBean parent) throws ManagementException {
      super(mbeanName, parent);
   }
}
