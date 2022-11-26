package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface TransactionLogStoreMBean extends PersistentStoreMBean {
   /** @deprecated */
   @Deprecated
   TargetMBean[] getTargets();

   /** @deprecated */
   @Deprecated
   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean addTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   boolean removeTarget(TargetMBean var1) throws InvalidAttributeValueException, DistributedManagementException;
}
