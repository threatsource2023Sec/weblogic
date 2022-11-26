package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;

public interface TargetInfoMBean extends ConfigurationMBean {
   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getName();

   TargetMBean[] getTargets();

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   void addTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   void removeTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getModuleType();

   void setModuleType(String var1);

   String getCompatibilityName();

   void setCompatibilityName(String var1);
}
