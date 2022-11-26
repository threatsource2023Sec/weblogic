package com.oracle.weblogic.lifecycle.provisioning.api;

import java.lang.reflect.Method;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Selector {
   boolean select(Method var1, ProvisioningResource var2);
}
