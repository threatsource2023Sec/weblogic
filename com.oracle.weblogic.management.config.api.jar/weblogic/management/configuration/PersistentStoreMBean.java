package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface PersistentStoreMBean extends DynamicDeploymentMBean {
   TargetMBean[] getTargets();

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean addTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getLogicalName();

   void setLogicalName(String var1) throws InvalidAttributeValueException;

   String getXAResourceName();

   void setXAResourceName(String var1) throws InvalidAttributeValueException;
}
