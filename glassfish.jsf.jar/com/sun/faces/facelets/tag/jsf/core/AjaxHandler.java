package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.component.behavior.AjaxBehaviors;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagHandler;
import com.sun.faces.renderkit.RenderKitUtils;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.AttachedObjectTarget;
import javax.faces.view.BehaviorHolderAttachedObjectHandler;
import javax.faces.view.BehaviorHolderAttachedObjectTarget;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.CompositeFaceletHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandler;

public final class AjaxHandler extends TagHandlerImpl implements BehaviorHolderAttachedObjectHandler {
   private final TagAttribute event = this.getAttribute("event");
   private final TagAttribute execute = this.getAttribute("execute");
   private final TagAttribute render = this.getAttribute("render");
   private final TagAttribute onevent = this.getAttribute("onevent");
   private final TagAttribute onerror = this.getAttribute("onerror");
   private final TagAttribute disabled = this.getAttribute("disabled");
   private final TagAttribute immediate = this.getAttribute("immediate");
   private final TagAttribute resetValues = this.getAttribute("resetValues");
   private final TagAttribute listener = this.getAttribute("listener");
   private final TagAttribute delay = this.getAttribute("delay");
   private final boolean wrapping = this.isWrapping();

   public AjaxHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      String eventName = this.getEventName();
      if (this.wrapping) {
         this.applyWrapping(ctx, parent, eventName);
      } else {
         this.applyNested(ctx, parent, eventName);
      }

   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      this.applyAttachedObject(ctx, parent, this.getEventName());
   }

   public String getFor() {
      return null;
   }

   public String getEventName() {
      FacesContext context = FacesContext.getCurrentInstance();
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      return this.event != null ? this.event.getValue(ctx) : null;
   }

   private boolean isWrapping() {
      return this.nextHandler instanceof TagHandler || this.nextHandler instanceof CompositeFaceletHandler;
   }

   private void applyWrapping(FaceletContext ctx, UIComponent parent, String eventName) throws IOException {
      RenderKitUtils.installJsfJsIfNecessary(ctx.getFacesContext());
      AjaxBehavior ajaxBehavior = this.createAjaxBehavior(ctx, eventName);
      FacesContext context = ctx.getFacesContext();
      AjaxBehaviors ajaxBehaviors = AjaxBehaviors.getAjaxBehaviors(context, true);
      ajaxBehaviors.pushBehavior(context, ajaxBehavior, eventName);
      this.nextHandler.apply(ctx, parent);
      ajaxBehaviors.popBehavior();
   }

   private void applyNested(FaceletContext ctx, UIComponent parent, String eventName) {
      if (ComponentHandler.isNew(parent)) {
         if (UIComponent.isCompositeComponent(parent)) {
            boolean tagApplied = false;
            if (parent instanceof ClientBehaviorHolder) {
               this.applyAttachedObject(ctx, parent, eventName);
               tagApplied = true;
            }

            BeanInfo componentBeanInfo = (BeanInfo)parent.getAttributes().get("javax.faces.component.BEANINFO_KEY");
            if (null == componentBeanInfo) {
               throw new TagException(this.tag, "Error: enclosing composite component does not have BeanInfo attribute");
            }

            BeanDescriptor componentDescriptor = componentBeanInfo.getBeanDescriptor();
            if (null == componentDescriptor) {
               throw new TagException(this.tag, "Error: enclosing composite component BeanInfo does not have BeanDescriptor");
            }

            List targetList = (List)componentDescriptor.getValue("javax.faces.view.AttachedObjectTargets");
            if (null == targetList && !tagApplied) {
               throw new TagException(this.tag, "Error: enclosing composite component does not support behavior events");
            }

            boolean supportedEvent = false;
            Iterator var9 = targetList.iterator();

            label63: {
               BehaviorHolderAttachedObjectTarget behaviorTarget;
               do {
                  AttachedObjectTarget target;
                  do {
                     if (!var9.hasNext()) {
                        break label63;
                     }

                     target = (AttachedObjectTarget)var9.next();
                  } while(!(target instanceof BehaviorHolderAttachedObjectTarget));

                  behaviorTarget = (BehaviorHolderAttachedObjectTarget)target;
               } while((null == eventName || !eventName.equals(behaviorTarget.getName())) && (null != eventName || !behaviorTarget.isDefaultEvent()));

               supportedEvent = true;
            }

            if (supportedEvent) {
               CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this);
            } else if (!tagApplied) {
               throw new TagException(this.tag, "Error: enclosing composite component does not support event " + eventName);
            }
         } else {
            if (!(parent instanceof ClientBehaviorHolder)) {
               throw new TagException(this.tag, "Unable to attach <f:ajax> to non-ClientBehaviorHolder parent");
            }

            this.applyAttachedObject(ctx, parent, eventName);
         }

      }
   }

   private void applyAttachedObject(FaceletContext ctx, UIComponent parent, String eventName) {
      ClientBehaviorHolder bHolder = (ClientBehaviorHolder)parent;
      if (null == eventName) {
         eventName = bHolder.getDefaultEventName();
         if (null == eventName) {
            throw new TagException(this.tag, "Event attribute could not be determined: " + eventName);
         }
      } else {
         Collection eventNames = bHolder.getEventNames();
         if (!eventNames.contains(eventName)) {
            throw new TagException(this.tag, this.getUnsupportedEventMessage(eventName, eventNames, parent));
         }
      }

      AjaxBehavior ajaxBehavior = this.createAjaxBehavior(ctx, eventName);
      bHolder.addClientBehavior(eventName, ajaxBehavior);
      RenderKitUtils.installJsfJsIfNecessary(ctx.getFacesContext());
   }

   private AjaxBehavior createAjaxBehavior(FaceletContext ctx, String eventName) {
      Application application = ctx.getFacesContext().getApplication();
      AjaxBehavior behavior = (AjaxBehavior)application.createBehavior("javax.faces.behavior.Ajax");
      this.setBehaviorAttribute(ctx, behavior, this.onevent, String.class);
      this.setBehaviorAttribute(ctx, behavior, this.onerror, String.class);
      this.setBehaviorAttribute(ctx, behavior, this.disabled, Boolean.class);
      this.setBehaviorAttribute(ctx, behavior, this.immediate, Boolean.class);
      this.setBehaviorAttribute(ctx, behavior, this.resetValues, Boolean.class);
      this.setBehaviorAttribute(ctx, behavior, this.execute, Object.class);
      this.setBehaviorAttribute(ctx, behavior, this.render, Object.class);
      this.setBehaviorAttribute(ctx, behavior, this.delay, String.class);
      if (null != this.listener) {
         behavior.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(this.listener.getMethodExpression(ctx, Object.class, new Class[]{AjaxBehaviorEvent.class}), this.listener.getMethodExpression(ctx, Object.class, new Class[0])));
      }

      return behavior;
   }

   private void setBehaviorAttribute(FaceletContext ctx, AjaxBehavior behavior, TagAttribute attr, Class type) {
      if (attr != null) {
         behavior.setValueExpression(attr.getLocalName(), attr.getValueExpression(ctx, type));
      }

   }

   private String getUnsupportedEventMessage(String eventName, Collection eventNames, UIComponent parent) {
      StringBuilder builder = new StringBuilder(100);
      builder.append("'");
      builder.append(eventName);
      builder.append("' is not a supported event for ");
      builder.append(parent.getClass().getSimpleName());
      builder.append(".  Please specify one of these supported event names: ");
      Collection sortedEventNames = new TreeSet(eventNames);
      Iterator iter = sortedEventNames.iterator();
      boolean hasNext = iter.hasNext();

      while(hasNext) {
         builder.append((String)iter.next());
         hasNext = iter.hasNext();
         if (hasNext) {
            builder.append(", ");
         }
      }

      builder.append(".");
      return builder.toString();
   }
}
