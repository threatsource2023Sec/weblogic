package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JMXMBean extends ConfigurationMBean {
   boolean isRuntimeMBeanServerEnabled();

   void setRuntimeMBeanServerEnabled(boolean var1);

   boolean isDomainMBeanServerEnabled();

   void setDomainMBeanServerEnabled(boolean var1);

   boolean isEditMBeanServerEnabled();

   void setEditMBeanServerEnabled(boolean var1);

   boolean isCompatibilityMBeanServerEnabled();

   void setCompatibilityMBeanServerEnabled(boolean var1);

   boolean isManagementEJBEnabled();

   void setManagementEJBEnabled(boolean var1);

   boolean isPlatformMBeanServerEnabled();

   void setPlatformMBeanServerEnabled(boolean var1);

   int getInvocationTimeoutSeconds();

   void setInvocationTimeoutSeconds(int var1) throws InvalidAttributeValueException;

   boolean isPlatformMBeanServerUsed();

   void setPlatformMBeanServerUsed(boolean var1);

   boolean isManagedServerNotificationsEnabled();

   void setManagedServerNotificationsEnabled(boolean var1);

   boolean isManagementAppletCreateEnabled();

   void setManagementAppletCreateEnabled(boolean var1);
}
