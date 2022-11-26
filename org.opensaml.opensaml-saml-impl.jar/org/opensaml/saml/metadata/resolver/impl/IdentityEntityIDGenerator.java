package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;

public class IdentityEntityIDGenerator implements Function {
   public String apply(CriteriaSet input) {
      if (input == null) {
         return null;
      } else {
         EntityIdCriterion entityIDCrit = (EntityIdCriterion)input.get(EntityIdCriterion.class);
         return entityIDCrit == null ? null : entityIDCrit.getEntityId();
      }
   }
}
