package org.opensaml.security.credential.criteria.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableEntityIDCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableEntityIDCredentialCriterion.class);
   private final String entityID;

   public EvaluableEntityIDCredentialCriterion(@Nonnull EntityIdCriterion criteria) {
      this.entityID = ((EntityIdCriterion)Constraint.isNotNull(criteria, "Criterion instance may not be null")).getEntityId();
   }

   public EvaluableEntityIDCredentialCriterion(@Nonnull String entity) {
      String trimmed = StringSupport.trimOrNull(entity);
      Constraint.isNotNull(trimmed, "EntityID criteria cannot be null or empty");
      this.entityID = trimmed;
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else if (Strings.isNullOrEmpty(target.getEntityId())) {
         this.log.info("Could not evaluate criteria, credential contained no entityID");
         return this.isUnevaluableSatisfies();
      } else {
         return this.entityID.equals(target.getEntityId());
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluableEntityIDCredentialCriterion [entityID=");
      builder.append(this.entityID);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.entityID.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EvaluableEntityIDCredentialCriterion ? this.entityID.equals(((EvaluableEntityIDCredentialCriterion)obj).entityID) : false;
      }
   }
}
