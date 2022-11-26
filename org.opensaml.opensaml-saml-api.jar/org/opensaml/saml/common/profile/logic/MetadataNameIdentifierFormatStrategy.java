package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataNameIdentifierFormatStrategy implements Function {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(MetadataNameIdentifierFormatStrategy.class);
   @Nonnull
   private Function ssoDescriptorLookupStrategy = new MetadataLookupStrategy();

   public void setSSODescriptorLookupStrategy(@Nonnull Function strategy) {
      this.ssoDescriptorLookupStrategy = (Function)Constraint.isNotNull(strategy, "SSODescriptor lookup strategy cannot be null");
   }

   @Nullable
   public List apply(@Nullable ProfileRequestContext input) {
      SSODescriptor role = (SSODescriptor)this.ssoDescriptorLookupStrategy.apply(input);
      if (role != null) {
         List strings = new ArrayList();
         Iterator var4 = role.getNameIDFormats().iterator();

         while(var4.hasNext()) {
            NameIDFormat nif = (NameIDFormat)var4.next();
            if (nif.getFormat() != null) {
               if ("urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified".equals(nif.getFormat())) {
                  this.log.warn("Ignoring NameIDFormat metadata that includes the 'unspecified' format");
                  return Collections.emptyList();
               }

               strings.add(nif.getFormat());
            }
         }

         this.log.debug("Metadata specifies the following formats: {}", strings);
         return strings;
      } else {
         return Collections.emptyList();
      }
   }

   private class MetadataLookupStrategy implements Function {
      private MetadataLookupStrategy() {
      }

      @Nullable
      public SSODescriptor apply(@Nullable ProfileRequestContext input) {
         if (input != null && input.getInboundMessageContext() != null) {
            SAMLPeerEntityContext peerCtx = (SAMLPeerEntityContext)input.getInboundMessageContext().getSubcontext(SAMLPeerEntityContext.class);
            if (peerCtx != null) {
               SAMLMetadataContext mdCtx = (SAMLMetadataContext)peerCtx.getSubcontext(SAMLMetadataContext.class);
               if (mdCtx != null && mdCtx.getRoleDescriptor() != null && mdCtx.getRoleDescriptor() instanceof SSODescriptor) {
                  return (SSODescriptor)mdCtx.getRoleDescriptor();
               }

               MetadataNameIdentifierFormatStrategy.this.log.debug("No SAMLMetadataContext or SSODescriptor role available");
            } else {
               MetadataNameIdentifierFormatStrategy.this.log.debug("No SAMLPeerEntityContext available");
            }
         } else {
            MetadataNameIdentifierFormatStrategy.this.log.debug("No inbound message context available");
         }

         return null;
      }

      // $FF: synthetic method
      MetadataLookupStrategy(Object x1) {
         this();
      }
   }
}
