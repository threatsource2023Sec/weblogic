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
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptAssertions extends AbstractEncryptAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EncryptAssertions.class);
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
      return ctx != null ? ctx.getAssertionEncryptionParameters() : null;
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
      List accumulator = new ArrayList(this.response.getAssertions().size());
      Iterator var3 = this.response.getAssertions().iterator();

      while(var3.hasNext()) {
         Assertion assertion = (Assertion)var3.next();

         try {
            if (this.log.isDebugEnabled()) {
               try {
                  Element dom = XMLObjectSupport.marshall(assertion);
                  this.log.debug("{} Assertion before encryption:\n{}", this.getLogPrefix(), SerializeSupport.prettyPrintXML(dom));
               } catch (MarshallingException var6) {
                  this.log.error("{} Unable to marshall message for logging purposes", this.getLogPrefix(), var6);
               }
            }

            accumulator.add(this.getEncrypter().encrypt(assertion));
         } catch (EncryptionException var7) {
            this.log.warn("{} Error encrypting assertion", this.getLogPrefix(), var7);
            ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
            return;
         }
      }

      this.response.getEncryptedAssertions().addAll(accumulator);
      this.response.getAssertions().clear();
   }
}
