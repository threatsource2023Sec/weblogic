package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SAMLObject;

public class ExpiringSAMLArtifactMapEntry extends BasicSAMLArtifactMapEntry {
   private long expiration;

   public ExpiringSAMLArtifactMapEntry(@Nonnull @NotEmpty String samlArtifact, @Nonnull @NotEmpty String issuerId, @Nonnull @NotEmpty String relyingPartyId, @Nonnull SAMLObject samlMessage) throws MarshallingException, UnmarshallingException {
      super(samlArtifact, issuerId, relyingPartyId, samlMessage);
   }

   public long getExpiration() {
      return this.expiration;
   }

   public void setExpiration(long exp) {
      this.expiration = exp;
   }

   public boolean isValid() {
      return System.currentTimeMillis() < this.expiration;
   }

   public boolean isValid(long effectiveTime) {
      return effectiveTime < this.expiration;
   }
}
