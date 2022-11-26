package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface SystemComponentConfigurationMBean extends ConfigurationMBean {
   String getComponentType();

   void setComponentType(String var1);

   String getSourcePath();

   void setSourcePath(String var1);

   SystemComponentMBean[] getSystemComponents();

   void setSystemComponents(SystemComponentMBean[] var1) throws InvalidAttributeValueException;

   void addSystemComponent(SystemComponentMBean var1) throws InvalidAttributeValueException;

   void removeSystemComponent(SystemComponentMBean var1) throws InvalidAttributeValueException;
}
