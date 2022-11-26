package org.opensaml.saml.criterion;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;

public final class ArtifactCriterion implements Criterion {
   @Nonnull
   private final SAMLArtifact artifact;

   public ArtifactCriterion(@Nonnull SAMLArtifact newArtifact) {
      this.artifact = (SAMLArtifact)Constraint.isNotNull(newArtifact, "SAMLArtifact cannot be null");
   }

   @Nonnull
   public SAMLArtifact getArtifact() {
      return this.artifact;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ArtifactCriterion [artifact=");
      builder.append(Hex.encodeHex(this.artifact.getArtifactBytes(), true));
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.artifact.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof ArtifactCriterion ? Objects.equals(this.artifact, ((ArtifactCriterion)obj).artifact) : false;
      }
   }
}
