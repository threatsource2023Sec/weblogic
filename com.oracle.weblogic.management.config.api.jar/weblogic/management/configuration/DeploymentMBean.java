package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface DeploymentMBean extends ConfigurationMBean {
   int DEFAULT_ORDER = 1000;
   int MIN_ORDER = 0;
   int MAX_ORDER = Integer.MAX_VALUE;

   TargetMBean[] getTargets();

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean addTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getDeploymentOrder();

   void setDeploymentOrder(int var1);
}
