package org.opensaml.saml.saml2.wssecurity.messaging.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.assertion.AssertionValidationException;
import org.opensaml.saml.common.assertion.ValidationContext;
import org.opensaml.saml.common.assertion.ValidationResult;
import org.opensaml.saml.saml2.assertion.SAML20AssertionValidator;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.wssecurity.SAML20AssertionToken;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.wssecurity.Security;
import org.opensaml.soap.wssecurity.WSSecurityConstants;
import org.opensaml.soap.wssecurity.messaging.WSSecurityContext;
import org.opensaml.soap.wssecurity.messaging.Token.ValidationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSSecuritySAML20AssertionTokenSecurityHandler extends AbstractMessageHandler {
   private final Logger log = LoggerFactory.getLogger(WSSecuritySAML20AssertionTokenSecurityHandler.class);
   @NonnullAfterInit
   private HttpServletRequest httpServletRequest;
   private boolean invalidFatal;
   @Nullable
   private SAML20AssertionValidator assertionValidator;
   @Nullable
   private Function assertionValidatorLookup;
   @NonnullAfterInit
   private Function validationContextBuilder;

   public WSSecuritySAML20AssertionTokenSecurityHandler() {
      this.setInvalidFatal(true);
      this.setValidationContextBuilder(new DefaultSAML20AssertionValidationContextBuilder());
   }

   @NonnullAfterInit
   public Function getValidationContextBuilder() {
      return this.validationContextBuilder;
   }

   public void setValidationContextBuilder(@Nonnull Function builder) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.validationContextBuilder = (Function)Constraint.isNotNull(builder, "Validation context builder may not be null");
   }

   @NonnullAfterInit
   public HttpServletRequest getHttpServletRequest() {
      return this.httpServletRequest;
   }

   public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpServletRequest = (HttpServletRequest)Constraint.isNotNull(request, "HttpServletRequest cannot be null");
   }

   public boolean isInvalidFatal() {
      return this.invalidFatal;
   }

   public void setInvalidFatal(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.invalidFatal = flag;
   }

   @Nullable
   public SAML20AssertionValidator getAssertionValidator() {
      return this.assertionValidator;
   }

   public void setAssertionValidator(@Nullable SAML20AssertionValidator validator) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.assertionValidator = validator;
   }

   @Nullable
   public Function getAssertionValidatorLookup() {
      return this.assertionValidatorLookup;
   }

   public void setAssertionValidatorLookup(@Nullable Function function) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.assertionValidatorLookup = function;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.getValidationContextBuilder() == null) {
         throw new ComponentInitializationException("ValidationContext builder cannot be null");
      } else if (this.getHttpServletRequest() == null) {
         throw new ComponentInitializationException("HttpServletRequest cannot be null");
      } else {
         if (this.getAssertionValidator() == null) {
            if (this.getAssertionValidatorLookup() == null) {
               throw new ComponentInitializationException("Both Assertion validator and lookup function were null");
            }

            this.log.info("Assertion validator is null, must be resovleable via the lookup function");
         }

      }
   }

   protected void doDestroy() {
      this.httpServletRequest = null;
      super.doDestroy();
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!SOAPMessagingSupport.isSOAPMessage(messageContext)) {
         this.log.info("Message context does not contain a SOAP envelope. Skipping rule...");
      } else {
         List assertions = this.resolveAssertions(messageContext);
         if (assertions != null && !assertions.isEmpty()) {
            WSSecurityContext wsContext = (WSSecurityContext)messageContext.getSubcontext(WSSecurityContext.class, true);
            Iterator var4 = assertions.iterator();

            while(var4.hasNext()) {
               Assertion assertion = (Assertion)var4.next();
               SAML20AssertionValidator validator = this.resolveValidator(messageContext, assertion);
               if (validator == null) {
                  this.log.warn("No SAML20AssertionValidator was available, terminating");
                  SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal processing error", (String)null, (List)null, (Map)null);
                  throw new MessageHandlerException("No SAML20AssertionValidator was available");
               }

               ValidationContext validationContext = this.buildValidationContext(messageContext, assertion);

               try {
                  ValidationResult validationResult = validator.validate(assertion, validationContext);
                  SAML20AssertionToken token = new SAML20AssertionToken(assertion);
                  this.processResult(validationContext, validationResult, token, messageContext);
                  wsContext.getTokens().add(token);
               } catch (AssertionValidationException var10) {
                  this.log.warn("There was a problem determining Assertion validity: {}", var10.getMessage());
                  SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal security token processing error", (String)null, (List)null, (Map)null);
                  throw new MessageHandlerException("Error determining SAML 2.0 Assertion validity", var10);
               }
            }

         } else {
            this.log.info("Inbound SOAP envelope contained no Assertion tokens. Skipping further processing");
         }
      }
   }

   protected void processResult(@Nonnull ValidationContext validationContext, @Nonnull ValidationResult validationResult, @Nonnull SAML20AssertionToken token, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("Assertion token validation result was: {}", validationResult);
      String validationMsg = validationContext.getValidationFailureMessage();
      if (Strings.isNullOrEmpty(validationMsg)) {
         validationMsg = "unspecified";
      }

      switch (validationResult) {
         case VALID:
            token.setValidationStatus(ValidationStatus.VALID);
            token.setSubjectConfirmation((SubjectConfirmation)validationContext.getDynamicParameters().get("saml2.ConfirmedSubjectConfirmation"));
            break;
         case INVALID:
            this.log.warn("Assertion token validation was INVALID.  Reason: {}", validationMsg);
            if (this.isInvalidFatal()) {
               SOAPMessagingSupport.registerSOAP11Fault(messageContext, WSSecurityConstants.SOAP_FAULT_INVALID_SECURITY_TOKEN, "The SAML 2.0 Assertion token was invalid", (String)null, (List)null, (Map)null);
               throw new MessageHandlerException("Assertion token validation result was INVALID");
            }

            token.setValidationStatus(ValidationStatus.INVALID);
            token.setSubjectConfirmation((SubjectConfirmation)validationContext.getDynamicParameters().get("saml2.ConfirmedSubjectConfirmation"));
            break;
         case INDETERMINATE:
            this.log.warn("Assertion token validation was INDETERMINATE. Reason: {}", validationMsg);
            if (this.isInvalidFatal()) {
               SOAPMessagingSupport.registerSOAP11Fault(messageContext, WSSecurityConstants.SOAP_FAULT_INVALID_SECURITY_TOKEN, "The SAML 2.0 Assertion token's validity could not be determined", (String)null, (List)null, (Map)null);
               throw new MessageHandlerException("Assertion token validation result was INDETERMINATE");
            }

            token.setValidationStatus(ValidationStatus.INDETERMINATE);
            token.setSubjectConfirmation((SubjectConfirmation)validationContext.getDynamicParameters().get("saml2.ConfirmedSubjectConfirmation"));
            break;
         default:
            this.log.warn("Assertion validation result indicated an unknown value: {}", validationResult);
            SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal processing error", (String)null, (List)null, (Map)null);
            throw new IllegalArgumentException("Assertion validation result indicated an unknown value: " + validationResult);
      }

   }

   @Nullable
   protected SAML20AssertionValidator resolveValidator(@Nonnull MessageContext messageContext, @Nonnull Assertion assertion) {
      if (this.getAssertionValidatorLookup() != null) {
         this.log.debug("Attempting to resolve SAML 2 Assertion validator via lookup function");
         SAML20AssertionValidator validator = (SAML20AssertionValidator)this.getAssertionValidatorLookup().apply(new Pair(messageContext, assertion));
         if (validator != null) {
            this.log.debug("Resolved SAML 2 Assertion validator via lookup function");
            return validator;
         }
      }

      if (this.getAssertionValidator() != null) {
         this.log.debug("Resolved locally configured SAML 2 Assertion validator");
         return this.getAssertionValidator();
      } else {
         this.log.debug("No SAML 2 Assertion validator could be resolved");
         return null;
      }
   }

   @Nonnull
   protected ValidationContext buildValidationContext(@Nonnull MessageContext messageContext, @Nonnull Assertion assertion) throws MessageHandlerException {
      ValidationContext validationContext = (ValidationContext)this.getValidationContextBuilder().apply(new SAML20AssertionTokenValidationInput(messageContext, this.getHttpServletRequest(), assertion));
      if (validationContext == null) {
         this.log.warn("ValidationContext produced was null");
         SOAPMessagingSupport.registerSOAP11Fault(messageContext, FaultCode.SERVER, "Internal processing error", (String)null, (List)null, (Map)null);
         throw new MessageHandlerException("No ValidationContext was produced");
      } else {
         return validationContext;
      }
   }

   @Nonnull
   protected List resolveAssertions(@Nonnull MessageContext messageContext) {
      List securityHeaders = SOAPMessagingSupport.getInboundHeaderBlock(messageContext, Security.ELEMENT_NAME);
      if (securityHeaders != null && !securityHeaders.isEmpty()) {
         LazyList assertions = new LazyList();
         Iterator var4 = securityHeaders.iterator();

         while(true) {
            List xmlObjects;
            do {
               do {
                  if (!var4.hasNext()) {
                     return assertions;
                  }

                  XMLObject header = (XMLObject)var4.next();
                  Security securityHeader = (Security)header;
                  xmlObjects = securityHeader.getUnknownXMLObjects(Assertion.DEFAULT_ELEMENT_NAME);
               } while(xmlObjects == null);
            } while(xmlObjects.isEmpty());

            Iterator var8 = xmlObjects.iterator();

            while(var8.hasNext()) {
               XMLObject xmlObject = (XMLObject)var8.next();
               assertions.add((Assertion)xmlObject);
            }
         }
      } else {
         this.log.debug("No WS-Security Security header found in inbound SOAP message. Skipping further processing.");
         return Collections.emptyList();
      }
   }
}
