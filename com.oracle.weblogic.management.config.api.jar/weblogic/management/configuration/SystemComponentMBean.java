package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface SystemComponentMBean extends ManagedExternalServerMBean {
   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getComponentType();

   void setComponentType(String var1);

   SystemComponentStartMBean getSystemComponentStart();
}
