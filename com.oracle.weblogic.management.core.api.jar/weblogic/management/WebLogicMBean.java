package weblogic.management;

import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistration;
import javax.management.NotificationBroadcaster;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ConfigurationException;

@Contract
public interface WebLogicMBean extends DynamicMBean, MBeanRegistration, NotificationBroadcaster {
   String getName();

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getType();

   /** @deprecated */
   @Deprecated
   WebLogicObjectName getObjectName();

   /** @deprecated */
   @Deprecated
   MBeanInfo getMBeanInfo();

   boolean isCachingDisabled();

   WebLogicMBean getParent();

   void setParent(WebLogicMBean var1) throws ConfigurationException;

   /** @deprecated */
   @Deprecated
   boolean isRegistered();
}
