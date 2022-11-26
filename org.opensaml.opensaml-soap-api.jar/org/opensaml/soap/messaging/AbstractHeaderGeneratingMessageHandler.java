package org.opensaml.soap.messaging;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;

public abstract class AbstractHeaderGeneratingMessageHandler extends AbstractMessageHandler {
   private boolean mustUnderstand;
   @Nullable
   private Predicate mustUnderstandStrategy;
   private boolean effectiveMustUnderstand;
   @Nullable
   private String targetNode;
   @Nullable
   private Function targetNodeStrategy;
   private String effectiveTargetNode;

   public void setMustUnderstand(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.mustUnderstand = flag;
   }

   public void setMustUnderstandStrategy(@Nullable Predicate strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.mustUnderstandStrategy = strategy;
   }

   protected boolean isEffectiveMustUnderstand() {
      return this.effectiveMustUnderstand;
   }

   public void setTargetNode(@Nullable String node) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.targetNode = StringSupport.trimOrNull(node);
   }

   public void setTargetNodeStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.targetNodeStrategy = strategy;
   }

   protected String getEffectiveTargetNode() {
      return this.effectiveTargetNode;
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         if (this.mustUnderstandStrategy != null) {
            this.effectiveMustUnderstand = this.mustUnderstandStrategy.apply(messageContext);
         } else {
            this.effectiveMustUnderstand = this.mustUnderstand;
         }

         if (this.targetNodeStrategy != null) {
            this.effectiveTargetNode = (String)this.targetNodeStrategy.apply(messageContext);
         } else {
            this.effectiveTargetNode = this.targetNode;
         }

         return true;
      }
   }

   protected void decorateGeneratedHeader(@Nonnull MessageContext messageContext, @Nonnull XMLObject header) {
      if (this.isEffectiveMustUnderstand()) {
         SOAPMessagingSupport.addMustUnderstand(messageContext, header, true);
      }

      if (this.getEffectiveTargetNode() != null) {
         SOAPMessagingSupport.addTargetNode(messageContext, header, this.getEffectiveTargetNode());
      }

   }
}
