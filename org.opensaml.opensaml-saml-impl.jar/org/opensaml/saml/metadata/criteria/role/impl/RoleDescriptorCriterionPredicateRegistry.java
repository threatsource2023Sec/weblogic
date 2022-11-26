package org.opensaml.saml.metadata.criteria.role.impl;

import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;

public class RoleDescriptorCriterionPredicateRegistry extends CriterionPredicateRegistry {
   public RoleDescriptorCriterionPredicateRegistry() {
      this.loadMappings("/roledescriptor-criterion-predicate-registry.properties");
   }
}
