package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.core.xml.util.XMLObjectSupport.CloneOutputOption;
import org.opensaml.saml.common.SAMLObject;

public class BasicSAMLArtifactMapEntry implements SAMLArtifactMap.SAMLArtifactMapEntry {
   @Nonnull
   @NotEmpty
   private final String artifact;
   @Nonnull
   @NotEmpty
   private final String issuer;
   @Nonnull
   @NotEmpty
   private final String relyingParty;
   @Nonnull
   private final SAMLObject message;

   public BasicSAMLArtifactMapEntry(@Nonnull @NotEmpty String samlArtifact, @Nonnull @NotEmpty String issuerId, @Nonnull @NotEmpty String relyingPartyId, @Nonnull SAMLObject samlMessage) throws MarshallingException, UnmarshallingException {
      this.artifact = samlArtifact;
      this.issuer = issuerId;
      this.relyingParty = relyingPartyId;
      if (!samlMessage.hasParent()) {
         this.message = samlMessage;
      } else {
         this.message = (SAMLObject)XMLObjectSupport.cloneXMLObject(samlMessage, CloneOutputOption.RootDOMInNewDocument);
      }

   }

   @Nonnull
   @NotEmpty
   public String getArtifact() {
      return this.artifact;
   }

   @Nonnull
   @NotEmpty
   public String getIssuerId() {
      return this.issuer;
   }

   @Nonnull
   @NotEmpty
   public String getRelyingPartyId() {
      return this.relyingParty;
   }

   @Nonnull
   public SAMLObject getSamlMessage() {
      return this.message;
   }
}
