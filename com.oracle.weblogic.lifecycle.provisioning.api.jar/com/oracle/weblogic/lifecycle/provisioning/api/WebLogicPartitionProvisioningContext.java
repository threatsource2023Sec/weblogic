package com.oracle.weblogic.lifecycle.provisioning.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WebLogicPartitionProvisioningContext extends ProvisioningContext {
   String getPartitionName();
}
