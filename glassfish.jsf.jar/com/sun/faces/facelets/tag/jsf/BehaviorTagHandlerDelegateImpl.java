package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.tag.MetaRulesetImpl;
import com.sun.faces.util.Util;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.AttachedObjectTarget;
import javax.faces.view.BehaviorHolderAttachedObjectTarget;
import javax.faces.view.facelets.BehaviorHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandlerDelegate;

class BehaviorTagHandlerDelegateImpl extends TagHandlerDelegate implements AttachedObjectHandler {
   private BehaviorHandler owner;

   public BehaviorTagHandlerDelegateImpl(BehaviorHandler owner) {
      this.owner = owner;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent != null && parent.getParent() == null) {
         ComponentSupport.copyPassthroughAttributes(ctx, parent, this.owner.getTag());
         if (UIComponent.isCompositeComponent(parent)) {
            BeanInfo componentBeanInfo = (BeanInfo)parent.getAttributes().get("javax.faces.component.BEANINFO_KEY");
            if (null == componentBeanInfo) {
               throw new TagException(this.owner.getTag(), "Error: enclosing composite component does not have BeanInfo attribute");
            }

            BeanDescriptor componentDescriptor = componentBeanInfo.getBeanDescriptor();
            if (null == componentDescriptor) {
               throw new TagException(this.owner.getTag(), "Error: enclosing composite component BeanInfo does not have BeanDescriptor");
            }

            List targetList = (List)componentDescriptor.getValue("javax.faces.view.AttachedObjectTargets");
            if (null == targetList) {
               throw new TagException(this.owner.getTag(), "Error: enclosing composite component does not support behavior events");
            }

            String eventName = this.owner.getEventName();
            boolean supportedEvent = false;
            Iterator var8 = targetList.iterator();

            label56: {
               BehaviorHolderAttachedObjectTarget behaviorTarget;
               do {
                  AttachedObjectTarget target;
                  do {
                     if (!var8.hasNext()) {
                        break label56;
                     }

                     target = (AttachedObjectTarget)var8.next();
                  } while(!(target instanceof BehaviorHolderAttachedObjectTarget));

                  behaviorTarget = (BehaviorHolderAttachedObjectTarget)target;
               } while((null == eventName || !eventName.equals(behaviorTarget.getName())) && (null != eventName || !behaviorTarget.isDefaultEvent()));

               supportedEvent = true;
            }

            if (!supportedEvent) {
               throw new TagException(this.owner.getTag(), "Error: enclosing composite component does not support event " + eventName);
            }

            CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this.owner);
         } else {
            if (!(parent instanceof ClientBehaviorHolder)) {
               throw new TagException(this.owner.getTag(), "Parent not an instance of ClientBehaviorHolder: " + parent);
            }

            this.owner.applyAttachedObject(ctx.getFacesContext(), parent);
         }

      }
   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      ClientBehaviorHolder behaviorHolder = (ClientBehaviorHolder)parent;
      ValueExpression bindingExpr = null;
      Behavior behavior = null;
      if (null != this.owner.getBinding()) {
         bindingExpr = this.owner.getBinding().getValueExpression(ctx, Behavior.class);
         behavior = (Behavior)bindingExpr.getValue(ctx);
      }

      if (null == behavior) {
         if (null == this.owner.getBehaviorId()) {
            throw new TagException(this.owner.getTag(), "No behaviorId defined");
         }

         behavior = ctx.getFacesContext().getApplication().createBehavior(this.owner.getBehaviorId());
         if (null == behavior) {
            throw new TagException(this.owner.getTag(), "No Faces behavior defined for Id " + this.owner.getBehaviorId());
         }

         if (null != bindingExpr) {
            bindingExpr.setValue(ctx, behavior);
         }
      }

      this.owner.setAttributes(ctx, behavior);
      if (behavior instanceof ClientBehavior) {
         behaviorHolder.addClientBehavior(this.getEventName(behaviorHolder), (ClientBehavior)behavior);
      }

   }

   public MetaRuleset createMetaRuleset(Class type) {
      Util.notNull("type", type);
      MetaRuleset m = new MetaRulesetImpl(this.owner.getTag(), type);
      MetaRuleset m = m.ignore("event");
      return m.ignore("binding").ignore("for");
   }

   public String getFor() {
      String result = null;
      TagAttribute attr = this.owner.getTagAttribute("for");
      if (null != attr) {
         result = attr.getValue();
      }

      return result;
   }

   private String getEventName(ClientBehaviorHolder holder) {
      String eventName;
      if (null != this.owner.getEvent()) {
         eventName = this.owner.getEvent().getValue();
      } else {
         eventName = holder.getDefaultEventName();
      }

      if (null == eventName) {
         throw new TagException(this.owner.getTag(), "The event name is not defined");
      } else {
         return eventName;
      }
   }
}
