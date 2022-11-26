package com.oracle.weblogic.lifecycle.provisioning.wls;

import com.oracle.weblogic.lifecycle.provisioning.api.Selector;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({Selector.class, DomainProvisioningResourceSelector.class})
@PerLookup
@Service
public class DomainProvisioningResourceSelector extends AbstractScopedProvisioningResourceSelector {
   public DomainProvisioningResourceSelector() {
      super("domain");
   }
}
