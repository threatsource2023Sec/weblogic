package org.opensaml.saml.metadata.criteria.entity.impl;

import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;

public class EntityDescriptorCriterionPredicateRegistry extends CriterionPredicateRegistry {
   public EntityDescriptorCriterionPredicateRegistry() {
      this.loadMappings("/entitydescriptor-criterion-predicate-registry.properties");
   }
}
