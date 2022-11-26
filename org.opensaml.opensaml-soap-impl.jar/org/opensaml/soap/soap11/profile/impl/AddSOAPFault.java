package org.opensaml.soap.soap11.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.CurrentOrPreviousEventLookup;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.Detail;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.FaultActor;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddSOAPFault extends AbstractProfileAction {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(AddSOAPFault.class);
   @Nullable
   private Function contextFaultStrategy;
   @Nonnull
   private Predicate detailedErrorsCondition = Predicates.alwaysFalse();
   @Nullable
   private Function faultCodeLookupStrategy;
   @Nullable
   private Function faultStringLookupStrategy;
   @Nonnull
   @NonnullElements
   private QName defaultFaultCode;
   @Nullable
   private String faultString;
   private boolean detailedErrors;
   private boolean nullifyOutboundMessage;

   public AddSOAPFault() {
      this.defaultFaultCode = FaultCode.SERVER;
      this.detailedErrors = false;
      this.contextFaultStrategy = new MessageContextFaultStrategy();
      this.nullifyOutboundMessage = true;
   }

   public void setNullifyOutboundMessage(boolean flag) {
      this.nullifyOutboundMessage = flag;
   }

   public void setContextFaultStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.contextFaultStrategy = strategy;
   }

   public void setDetailedErrorsCondition(@Nonnull Predicate condition) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.detailedErrorsCondition = (Predicate)Constraint.isNotNull(condition, "Detailed errors condition cannot be null");
   }

   public void setFaultCodeLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.faultCodeLookupStrategy = strategy;
   }

   public void setFaultStringLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.faultStringLookupStrategy = strategy;
   }

   public void setFaultCode(@Nonnull QName code) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.defaultFaultCode = (QName)Constraint.isNotNull(code, "Faultcode cannot be null");
   }

   public void setFaultString(@Nullable String message) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.faultString = StringSupport.trimOrNull(message);
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      this.detailedErrors = this.detailedErrorsCondition.apply(profileRequestContext);
      this.log.debug("{} Detailed errors are {}", this.getLogPrefix(), this.detailedErrors ? "enabled" : "disabled");
      if (profileRequestContext.getOutboundMessageContext() != null && this.nullifyOutboundMessage) {
         profileRequestContext.getOutboundMessageContext().setMessage((Object)null);
      } else {
         profileRequestContext.setOutboundMessageContext(new MessageContext());
      }

      return super.doPreExecute(profileRequestContext);
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      Fault fault = this.resolveContextFault(profileRequestContext);
      if (fault == null) {
         fault = this.buildNewMappedFault(profileRequestContext);
      }

      SOAPMessagingSupport.registerSOAP11Fault(profileRequestContext.getOutboundMessageContext(), fault);
   }

   @Nullable
   private Fault resolveContextFault(ProfileRequestContext profileRequestContext) {
      if (this.contextFaultStrategy == null) {
         return null;
      } else {
         Fault fault = (Fault)this.contextFaultStrategy.apply(profileRequestContext);
         if (fault != null) {
            this.log.debug("{} Resolved Fault instance via context strategy", this.getLogPrefix());
            if (fault.getCode() == null) {
               this.log.debug("{} Resolved Fault contained no FaultCode, using configured default", this.getLogPrefix());
               XMLObjectBuilder faultCodeBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(FaultCode.DEFAULT_ELEMENT_NAME);
               FaultCode code = (FaultCode)faultCodeBuilder.buildObject(FaultCode.DEFAULT_ELEMENT_NAME);
               code.setValue(this.defaultFaultCode);
               fault.setCode(code);
            }

            if (!this.detailedErrors) {
               this.log.debug("{} Removing any detailed error info from context Fault instance", this.getLogPrefix());
               if (this.faultString != null) {
                  this.buildFaultString(fault, this.faultString);
               } else {
                  fault.setMessage((FaultString)null);
               }

               fault.setDetail((Detail)null);
               fault.setActor((FaultActor)null);
            }
         } else {
            this.log.debug("{} Failed to resolve any Fault instance via context strategy", this.getLogPrefix());
         }

         return fault;
      }
   }

   @Nonnull
   private Fault buildNewMappedFault(ProfileRequestContext profileRequestContext) {
      XMLObjectBuilder faultBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Fault.DEFAULT_ELEMENT_NAME);
      XMLObjectBuilder faultCodeBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(FaultCode.DEFAULT_ELEMENT_NAME);
      Fault fault = (Fault)faultBuilder.buildObject(Fault.DEFAULT_ELEMENT_NAME);
      FaultCode code = (FaultCode)faultCodeBuilder.buildObject(FaultCode.DEFAULT_ELEMENT_NAME);
      if (this.faultCodeLookupStrategy != null) {
         QName fc = (QName)this.faultCodeLookupStrategy.apply(profileRequestContext);
         if (fc == null) {
            code.setValue(this.defaultFaultCode);
         } else {
            code.setValue(fc);
         }
      } else {
         code.setValue(this.defaultFaultCode);
      }

      fault.setCode(code);
      if (this.detailedErrors && this.faultStringLookupStrategy != null) {
         if (this.faultStringLookupStrategy != null) {
            String message = (String)this.faultStringLookupStrategy.apply(profileRequestContext);
            if (message != null) {
               this.log.debug("{} Current state of request was mappable, setting faultstring to mapped value", this.getLogPrefix());
               this.buildFaultString(fault, message);
            } else if (this.faultString != null) {
               this.log.debug("{} Current state of request was not mappable, setting faultstring to defaulted value", this.getLogPrefix());
               this.buildFaultString(fault, this.faultString);
            }
         }
      } else if (this.faultString != null) {
         this.log.debug("{} Setting faultstring to defaulted value", this.getLogPrefix());
         this.buildFaultString(fault, this.faultString);
      }

      return fault;
   }

   private void buildFaultString(@Nonnull Fault fault, @Nonnull @NotEmpty String message) {
      XMLObjectBuilder faultStringBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(FaultString.DEFAULT_ELEMENT_NAME);
      FaultString fs = (FaultString)faultStringBuilder.buildObject(FaultString.DEFAULT_ELEMENT_NAME);
      fs.setValue(message);
      fault.setMessage(fs);
   }

   public static class MessageContextFaultStrategy implements Function {
      private Logger log = LoggerFactory.getLogger(MessageContextFaultStrategy.class);

      @Nullable
      public Fault apply(@Nullable ProfileRequestContext input) {
         if (input == null) {
            return null;
         } else {
            Fault fault = null;
            if (input.getOutboundMessageContext() != null) {
               fault = SOAPMessagingSupport.getSOAP11Fault(input.getOutboundMessageContext());
               if (fault != null) {
                  this.log.debug("Found registered SOAP fault in outbound message context");
                  return fault;
               }
            }

            if (input.getInboundMessageContext() != null) {
               fault = SOAPMessagingSupport.getSOAP11Fault(input.getInboundMessageContext());
               if (fault != null) {
                  this.log.debug("Found registered SOAP fault in inbound message context");
                  return fault;
               }
            }

            return null;
         }
      }
   }

   public static class FaultCodeMappingFunction implements Function {
      @Nonnull
      @NonnullElements
      private Map codeMappings;
      @Nonnull
      private Function eventContextLookupStrategy;

      public FaultCodeMappingFunction(@Nonnull @NonnullElements Map mappings) {
         Constraint.isNotNull(mappings, "Faultcode mappings cannot be null");
         this.codeMappings = new HashMap(mappings.size());
         Iterator var2 = mappings.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            String event = StringSupport.trimOrNull((String)entry.getKey());
            if (event != null && entry.getValue() != null) {
               this.codeMappings.put(event, entry.getValue());
            }
         }

         this.eventContextLookupStrategy = new CurrentOrPreviousEventLookup();
      }

      public void setEventContextLookupStrategy(@Nonnull Function strategy) {
         this.eventContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "EventContext lookup strategy cannot be null");
      }

      @Nullable
      public QName apply(@Nullable ProfileRequestContext input) {
         EventContext eventCtx = (EventContext)this.eventContextLookupStrategy.apply(input);
         return eventCtx != null && eventCtx.getEvent() != null ? (QName)this.codeMappings.get(eventCtx.getEvent().toString()) : null;
      }
   }
}
