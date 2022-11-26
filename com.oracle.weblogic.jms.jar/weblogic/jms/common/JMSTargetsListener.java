package weblogic.jms.common;

import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;

public interface JMSTargetsListener {
   int ACTION_REMOVE = 0;
   int ACTION_ADD = 1;
   int ACTION_CHANGE = 2;

   void prepareUpdate(DomainMBean var1, TargetMBean var2, int var3, boolean var4) throws BeanUpdateRejectedException;

   void rollbackUpdate();

   void activateUpdate();
}
