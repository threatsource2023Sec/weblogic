package com.oracle.weblogic.lifecycle.provisioning.api;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ProvisioningComponentRepository {
   ConfigurableAttribute getConfigurableAttribute(String var1, String var2) throws ProvisioningException;

   Set getSelectableProvisioningComponentNames() throws ProvisioningException;

   Set getDependentAndAffiliatedProvisioningComponentNames(String var1) throws ProvisioningException;

   Set computeDependentAndAffiliatedProvisioningComponentNames(Iterable var1) throws ProvisioningException;
}
