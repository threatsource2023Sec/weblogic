package org.opensaml.saml.saml2.profile;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.AbstractNameIdentifierGenerator;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSAML2NameIDGenerator extends AbstractNameIdentifierGenerator implements SAML2NameIDGenerator {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractSAML2NameIDGenerator.class);
   @Nonnull
   private final SAMLObjectBuilder nameBuilder;
   @Nonnull
   private Function requestLookupStrategy;

   protected AbstractSAML2NameIDGenerator() {
      this.nameBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameID.DEFAULT_ELEMENT_NAME);
      this.requestLookupStrategy = Functions.compose(new MessageLookup(AuthnRequest.class), new InboundMessageContextLookup());
   }

   public void setRequestLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requestLookupStrategy = (Function)Constraint.isNotNull(strategy, "AuthnRequest lookup strategy cannot be null");
   }

   @Nullable
   protected NameID doGenerate(@Nonnull ProfileRequestContext profileRequestContext) throws SAMLException {
      String identifier = this.getIdentifier(profileRequestContext);
      if (identifier == null) {
         this.log.debug("No identifier to use");
         return null;
      } else {
         this.log.debug("Generating NameID {} with Format {}", identifier, this.getFormat());
         NameID nameIdentifier = (NameID)this.nameBuilder.buildObject();
         nameIdentifier.setValue(identifier);
         nameIdentifier.setFormat(this.getFormat());
         nameIdentifier.setNameQualifier(this.getEffectiveIdPNameQualifier(profileRequestContext));
         nameIdentifier.setSPNameQualifier(this.getEffectiveSPNameQualifier(profileRequestContext));
         nameIdentifier.setSPProvidedID(this.getSPProvidedID());
         return nameIdentifier;
      }
   }

   @Nullable
   protected String getEffectiveSPNameQualifier(@Nonnull ProfileRequestContext profileRequestContext) {
      AuthnRequest request = (AuthnRequest)this.requestLookupStrategy.apply(profileRequestContext);
      if (request != null && request.getNameIDPolicy() != null) {
         String qual = request.getNameIDPolicy().getSPNameQualifier();
         if (!Strings.isNullOrEmpty(qual)) {
            return qual;
         }
      }

      return super.getEffectiveSPNameQualifier(profileRequestContext);
   }
}
