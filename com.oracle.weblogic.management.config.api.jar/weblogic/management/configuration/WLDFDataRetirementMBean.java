package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;

public interface WLDFDataRetirementMBean extends ConfigurationMBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   String getArchiveName();

   void setArchiveName(String var1) throws InvalidAttributeValueException, ManagementException;

   int getRetirementTime();

   void setRetirementTime(int var1);

   int getRetirementPeriod();

   void setRetirementPeriod(int var1);
}
