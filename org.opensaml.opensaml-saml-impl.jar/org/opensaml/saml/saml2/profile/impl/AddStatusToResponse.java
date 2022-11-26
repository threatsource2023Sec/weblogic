package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.CurrentOrPreviousEventLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusMessage;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddStatusToResponse extends AbstractProfileAction {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(AddStatusToResponse.class);
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(StatusResponseType.class), new OutboundMessageContextLookup());
   @Nonnull
   private Predicate detailedErrorsCondition = Predicates.alwaysFalse();
   @Nullable
   private Function statusCodesLookupStrategy;
   @Nullable
   private Function statusMessageLookupStrategy;
   @Nonnull
   @NonnullElements
   private List defaultStatusCodes = Collections.emptyList();
   @Nullable
   private String statusMessage;
   private boolean detailedErrors = false;
   @Nullable
   private StatusResponseType response;

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   public void setDetailedErrorsCondition(@Nonnull Predicate condition) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.detailedErrorsCondition = (Predicate)Constraint.isNotNull(condition, "Detailed errors condition cannot be null");
   }

   public void setStatusCodesLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.statusCodesLookupStrategy = strategy;
   }

   public void setStatusMessageLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.statusMessageLookupStrategy = strategy;
   }

   public void setStatusCodes(@Nonnull @NonnullElements List codes) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      Constraint.isNotNull(codes, "Status code list cannot be null");
      this.defaultStatusCodes = new ArrayList(Collections2.filter(codes, Predicates.notNull()));
   }

   public void setStatusMessage(@Nullable String message) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.statusMessage = StringSupport.trimOrNull(message);
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      this.response = (StatusResponseType)this.responseLookupStrategy.apply(profileRequestContext);
      if (this.response == null) {
         this.log.debug("{} Response message was not returned by lookup strategy", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else {
         this.detailedErrors = this.detailedErrorsCondition.apply(profileRequestContext);
         this.log.debug("{} Detailed errors are {}", this.getLogPrefix(), this.detailedErrors ? "enabled" : "disabled");
         return super.doPreExecute(profileRequestContext);
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SAMLObjectBuilder statusBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Status.TYPE_NAME);
      Status status = (Status)statusBuilder.buildObject();
      this.response.setStatus(status);
      if (this.statusCodesLookupStrategy != null) {
         List codes = (List)this.statusCodesLookupStrategy.apply(profileRequestContext);
         if (codes != null && !codes.isEmpty()) {
            this.buildStatusCode(status, codes);
         } else {
            this.buildStatusCode(status, this.defaultStatusCodes);
         }
      } else {
         this.buildStatusCode(status, this.defaultStatusCodes);
      }

      if (this.detailedErrors && this.statusMessageLookupStrategy != null) {
         if (this.statusMessageLookupStrategy != null) {
            String message = (String)this.statusMessageLookupStrategy.apply(profileRequestContext);
            if (message != null) {
               this.log.debug("{} Current state of request was mappable, setting StatusMessage to mapped value", this.getLogPrefix());
               this.buildStatusMessage(status, message);
            } else if (this.statusMessage != null) {
               this.log.debug("{} Current state of request was not mappable, setting StatusMessage to defaulted value", this.getLogPrefix());
               this.buildStatusMessage(status, this.statusMessage);
            }
         }
      } else if (this.statusMessage != null) {
         this.log.debug("{} Setting StatusMessage to defaulted value", this.getLogPrefix());
         this.buildStatusMessage(status, this.statusMessage);
      }

   }

   private void buildStatusCode(@Nonnull Status status, @Nonnull @NonnullElements List codes) {
      SAMLObjectBuilder statusCodeBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(StatusCode.TYPE_NAME);
      StatusCode statusCode = (StatusCode)statusCodeBuilder.buildObject();
      status.setStatusCode(statusCode);
      if (codes.isEmpty()) {
         statusCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Responder");
      } else {
         statusCode.setValue((String)codes.get(0));
         Iterator i = codes.iterator();
         i.next();

         while(i.hasNext()) {
            StatusCode subcode = (StatusCode)statusCodeBuilder.buildObject();
            subcode.setValue((String)i.next());
            statusCode.setStatusCode(subcode);
            statusCode = subcode;
         }
      }

   }

   private void buildStatusMessage(@Nonnull Status status, @Nonnull @NotEmpty String message) {
      SAMLObjectBuilder statusMessageBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(StatusMessage.DEFAULT_ELEMENT_NAME);
      StatusMessage sm = (StatusMessage)statusMessageBuilder.buildObject();
      sm.setMessage(message);
      status.setStatusMessage(sm);
   }

   public static class StatusCodeMappingFunction implements Function {
      @Nonnull
      @NonnullElements
      private Map codeMappings;
      @Nonnull
      private Function eventContextLookupStrategy;

      public StatusCodeMappingFunction(@Nonnull @NonnullElements Map mappings) {
         Constraint.isNotNull(mappings, "Status code mappings cannot be null");
         this.codeMappings = new HashMap(mappings.size());
         Iterator var2 = mappings.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            String event = StringSupport.trimOrNull((String)entry.getKey());
            if (event != null && entry.getValue() != null) {
               this.codeMappings.put(event, new ArrayList(Collections2.filter((Collection)entry.getValue(), Predicates.notNull())));
            }
         }

         this.eventContextLookupStrategy = new CurrentOrPreviousEventLookup();
      }

      public void setEventContextLookupStrategy(@Nonnull Function strategy) {
         this.eventContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "EventContext lookup strategy cannot be null");
      }

      @Nullable
      public List apply(@Nullable ProfileRequestContext input) {
         EventContext eventCtx = (EventContext)this.eventContextLookupStrategy.apply(input);
         return eventCtx != null && eventCtx.getEvent() != null ? (List)this.codeMappings.get(eventCtx.getEvent().toString()) : Collections.emptyList();
      }
   }
}
