package com.oracle.weblogic.lifecycle.provisioning.spi;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ProvisioningComponentFactory {
   Set getProvisioningComponents() throws ProvisioningException;
}
