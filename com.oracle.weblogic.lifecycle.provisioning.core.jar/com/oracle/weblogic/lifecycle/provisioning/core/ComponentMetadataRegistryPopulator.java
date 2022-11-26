package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

/** @deprecated */
@Contract
@Deprecated
public interface ComponentMetadataRegistryPopulator {
   /** @deprecated */
   @Deprecated
   Set getComponentMetadata() throws ProvisioningException;
}
