package com.oracle.weblogic.lifecycle.provisioning.api;

import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

/** @deprecated */
@Contract
@Deprecated
public interface Executor {
   /** @deprecated */
   @Deprecated
   Set execute(ProvisioningOperation var1) throws ProvisioningException;
}
