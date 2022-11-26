package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerifyChannelBindings extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(VerifyChannelBindings.class);
   @Nonnull
   private Function channelBindingsLookupStrategy1 = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class), new InboundMessageContextLookup());
   @Nonnull
   private Function channelBindingsLookupStrategy2 = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class), Functions.compose(new ChildContextLookup(SOAP11Context.class), new InboundMessageContextLookup()));
   @Nonnull
   private Function channelBindingsCreationStrategy = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class, true), new OutboundMessageContextLookup());
   @Nullable
   private ChannelBindingsContext channelBindingsContext1;
   @Nullable
   private ChannelBindingsContext channelBindingsContext2;

   public void setChannelBindingsLookupStrategy1(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.channelBindingsLookupStrategy1 = (Function)Constraint.isNotNull(strategy, "First ChannelBindingsContext lookup strategy cannot be null");
   }

   public void setChannelBindingsLookupStrategy2(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.channelBindingsLookupStrategy2 = (Function)Constraint.isNotNull(strategy, "Second ChannelBindingsContext lookup strategy cannot be null");
   }

   public void setChannelBindingsCreationStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.channelBindingsCreationStrategy = (Function)Constraint.isNotNull(strategy, "ChannelBindingsContext creation strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (!super.doPreExecute(profileRequestContext)) {
         return false;
      } else {
         this.channelBindingsContext1 = (ChannelBindingsContext)this.channelBindingsLookupStrategy1.apply(profileRequestContext);
         this.channelBindingsContext2 = (ChannelBindingsContext)this.channelBindingsLookupStrategy2.apply(profileRequestContext);
         if (this.channelBindingsContext1 != null && this.channelBindingsContext1.getChannelBindings().isEmpty()) {
            this.channelBindingsContext1 = null;
         }

         if (this.channelBindingsContext2 != null && this.channelBindingsContext2.getChannelBindings().isEmpty()) {
            this.channelBindingsContext2 = null;
         }

         if (this.channelBindingsContext1 == null && this.channelBindingsContext2 == null) {
            this.log.debug("{} No channel bindings found to verify, nothing to do", this.getLogPrefix());
            return false;
         } else {
            return true;
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (this.channelBindingsContext1 != null && this.channelBindingsContext2 != null) {
         ChannelBindings matched = null;
         Iterator var3 = this.channelBindingsContext1.getChannelBindings().iterator();

         while(var3.hasNext()) {
            ChannelBindings cb1 = (ChannelBindings)var3.next();
            Iterator var5 = this.channelBindingsContext2.getChannelBindings().iterator();

            while(var5.hasNext()) {
               ChannelBindings cb2 = (ChannelBindings)var5.next();
               if (Objects.equals(cb1.getType(), cb2.getType())) {
                  String cb1Data = StringSupport.trimOrNull(cb1.getValue());
                  String cb2Data = StringSupport.trimOrNull(cb2.getValue());
                  if (Objects.equals(cb1Data, cb2Data)) {
                     matched = cb1;
                     break;
                  }
               }
            }

            if (matched != null) {
               break;
            }
         }

         if (matched == null) {
            this.log.warn("{} Unable to verify channel bindings sent for comparison", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "ChannelBindingsError");
         } else {
            this.log.debug("{} Saving matched channel bindings for later use", this.getLogPrefix());
            ChannelBindingsContext cbCtx = (ChannelBindingsContext)this.channelBindingsCreationStrategy.apply(profileRequestContext);
            if (cbCtx != null) {
               cbCtx.getChannelBindings().add(matched);
            } else {
               this.log.error("{} Unable to create ChannelBindingContext to store result", this.getLogPrefix());
               ActionSupport.buildEvent(profileRequestContext, "ChannelBindingsError");
            }

         }
      } else {
         this.log.warn("{} Unable to verify channel bindings sent for comparison", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "ChannelBindingsError");
      }
   }
}
