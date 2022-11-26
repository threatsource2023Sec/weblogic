package org.opensaml.saml.saml1.profile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.AbstractNameIdentifierGenerator;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSAML1NameIdentifierGenerator extends AbstractNameIdentifierGenerator implements SAML1NameIdentifierGenerator {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractSAML1NameIdentifierGenerator.class);
   @Nonnull
   private final SAMLObjectBuilder nameBuilder;

   protected AbstractSAML1NameIdentifierGenerator() {
      this.nameBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIdentifier.DEFAULT_ELEMENT_NAME);
   }

   @Nullable
   protected NameIdentifier doGenerate(@Nonnull ProfileRequestContext profileRequestContext) throws SAMLException {
      String identifier = this.getIdentifier(profileRequestContext);
      if (identifier == null) {
         this.log.debug("No identifier to use");
         return null;
      } else {
         this.log.debug("Generating NameIdentifier {} with Format {}", identifier, this.getFormat());
         NameIdentifier nameIdentifier = (NameIdentifier)this.nameBuilder.buildObject();
         nameIdentifier.setValue(identifier);
         nameIdentifier.setFormat(this.getFormat());
         nameIdentifier.setNameQualifier(this.getEffectiveIdPNameQualifier(profileRequestContext));
         if (this.getSPNameQualifier() != null) {
            this.log.warn("SPNameQualifier not supported for SAML 1 NameIdentifiers, omitting it");
         }

         if (this.getSPProvidedID() != null) {
            this.log.warn("SPProvidedID not supported for SAML 1 NameIdentifiers, omitting it");
         }

         return nameIdentifier;
      }
   }
}
