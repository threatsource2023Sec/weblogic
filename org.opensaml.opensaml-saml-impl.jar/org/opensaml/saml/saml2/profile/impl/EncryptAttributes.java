package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptAttributes extends AbstractEncryptAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EncryptAttributes.class);
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(StatusResponseType.class), new OutboundMessageContextLookup());
   @Nullable
   private Response response;

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   @Nullable
   protected EncryptionParameters getApplicableParameters(@Nullable EncryptionContext ctx) {
      return ctx != null ? ctx.getAttributeEncryptionParameters() : null;
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      StatusResponseType message = (StatusResponseType)this.responseLookupStrategy.apply(profileRequestContext);
      if (message != null) {
         if (message instanceof Response) {
            this.response = (Response)message;
         } else if (message instanceof ArtifactResponse && ((ArtifactResponse)message).getMessage() instanceof Response) {
            this.response = (Response)((ArtifactResponse)message).getMessage();
         }
      }

      if (this.response != null && !this.response.getAssertions().isEmpty()) {
         return super.doPreExecute(profileRequestContext);
      } else {
         this.log.debug("{} Response was not present or contained no assertions, nothing to do", this.getLogPrefix());
         return false;
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      Iterator var2 = this.response.getAssertions().iterator();

      while(var2.hasNext()) {
         Assertion assertion = (Assertion)var2.next();
         Iterator var4 = assertion.getAttributeStatements().iterator();

         while(var4.hasNext()) {
            AttributeStatement statement = (AttributeStatement)var4.next();
            List accumulator = new ArrayList(statement.getAttributes().size());
            Iterator var7 = statement.getAttributes().iterator();

            while(var7.hasNext()) {
               Attribute attribute = (Attribute)var7.next();

               try {
                  if (this.log.isDebugEnabled()) {
                     try {
                        Element dom = XMLObjectSupport.marshall(attribute);
                        this.log.debug("{} Attribute before encryption:\n{}", this.getLogPrefix(), SerializeSupport.prettyPrintXML(dom));
                     } catch (MarshallingException var10) {
                        this.log.error("{} Unable to marshall Attribute for logging purposes", this.getLogPrefix(), var10);
                     }
                  }

                  accumulator.add(this.getEncrypter().encrypt(attribute));
               } catch (EncryptionException var11) {
                  this.log.warn("{} Error encrypting attribute", this.getLogPrefix(), var11);
                  ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
                  return;
               }
            }

            statement.getEncryptedAttributes().addAll(accumulator);
            statement.getAttributes().clear();
         }
      }

   }
}
