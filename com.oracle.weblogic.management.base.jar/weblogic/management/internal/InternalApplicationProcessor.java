package weblogic.management.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.UpdateException;

@Contract
public interface InternalApplicationProcessor {
   void updateConfiguration(PartitionMBean var1) throws UpdateException;

   void updateConfiguration(DomainMBean var1) throws UpdateException;
}
