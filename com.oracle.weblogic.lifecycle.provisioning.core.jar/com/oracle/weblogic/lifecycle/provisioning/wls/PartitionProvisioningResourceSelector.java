package com.oracle.weblogic.lifecycle.provisioning.wls;

import com.oracle.weblogic.lifecycle.provisioning.api.Selector;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({Selector.class, PartitionProvisioningResourceSelector.class})
@PerLookup
@Service
public final class PartitionProvisioningResourceSelector extends AbstractScopedProvisioningResourceSelector {
   public PartitionProvisioningResourceSelector() {
      super("partition");
   }

   protected final boolean select(String provisioningResourceURIString, String resourceName) {
      return super.select(provisioningResourceURIString, resourceName) || provisioningResourceURIString != null && resourceName != null && resourceName.startsWith("_partition/") && resourceName.length() > "_partition/".length() && provisioningResourceURIString.endsWith(resourceName.substring("_partition/".length()));
   }
}
