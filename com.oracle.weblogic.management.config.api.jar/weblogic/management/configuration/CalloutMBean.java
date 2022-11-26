package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface CalloutMBean extends ConfigurationMBean {
   String getHookPoint();

   void setHookPoint(String var1);

   String getClassName();

   void setClassName(String var1) throws InvalidAttributeValueException;

   String getArgument();

   void setArgument(String var1);
}
