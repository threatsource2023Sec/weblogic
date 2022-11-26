package com.oracle.weblogic.lifecycle.provisioning.core;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ConfigurableAttributeValueProvider {
   String getConfigurableAttributeValue(String var1, String var2);

   boolean containsConfigurableAttributeValue(String var1, String var2);
}
