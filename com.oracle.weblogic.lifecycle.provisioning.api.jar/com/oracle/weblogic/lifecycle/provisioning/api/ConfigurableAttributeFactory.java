package com.oracle.weblogic.lifecycle.provisioning.api;

import java.util.Collection;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConfigurableAttributeFactory {
   Collection getConfigurableAttributes(ProvisioningEvent var1);
}
