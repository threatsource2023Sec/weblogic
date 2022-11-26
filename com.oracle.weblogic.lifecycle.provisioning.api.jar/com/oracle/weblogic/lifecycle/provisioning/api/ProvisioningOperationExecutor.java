package com.oracle.weblogic.lifecycle.provisioning.api;

import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ProvisioningOperationExecutor {
   Set executeProvisioningOperation(ProvisioningOperation var1) throws ProvisioningException;
}
