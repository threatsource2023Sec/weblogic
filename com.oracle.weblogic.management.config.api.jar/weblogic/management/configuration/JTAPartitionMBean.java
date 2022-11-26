package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface JTAPartitionMBean extends ConfigurationMBean {
   int getTimeoutSeconds();

   void setTimeoutSeconds(int var1) throws InvalidAttributeValueException, DistributedManagementException;
}
