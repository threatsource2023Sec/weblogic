package weblogic.jms.common;

import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;

public interface JMSServerListener {
   int ACTION_REMOVE = 0;
   int ACTION_ADD = 1;
   int ACTION_CHANGE = 2;

   void prepareUpdate(DomainMBean var1, JMSServerMBean var2, int var3) throws BeanUpdateRejectedException;

   void rollbackUpdate();

   void activateUpdate();
}
