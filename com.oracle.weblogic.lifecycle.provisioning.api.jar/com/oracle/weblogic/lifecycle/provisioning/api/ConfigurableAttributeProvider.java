package com.oracle.weblogic.lifecycle.provisioning.api;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConfigurableAttributeProvider {
   Map getConfigurableAttributes(ProvisioningOperation var1) throws ProvisioningException;
}
