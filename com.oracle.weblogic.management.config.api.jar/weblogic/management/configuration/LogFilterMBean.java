package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface LogFilterMBean extends ConfigurationMBean {
   int getSeverityLevel();

   void setSeverityLevel(int var1);

   String[] getSubsystemNames();

   void setSubsystemNames(String[] var1);

   String[] getUserIds();

   void setUserIds(String[] var1);

   String getFilterExpression();

   void setFilterExpression(String var1) throws InvalidAttributeValueException;

   Object getQuery();
}
