package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagHandler;
import com.sun.faces.facelets.util.ReflectionUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.view.EditableValueHolderAttachedObjectHandler;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class ValueChangeListenerHandler extends TagHandlerImpl implements EditableValueHolderAttachedObjectHandler {
   private final TagAttribute binding = this.getAttribute("binding");
   private final String listenerType;
   private final TagAttribute typeAttribute = this.getAttribute("type");

   public ValueChangeListenerHandler(TagConfig config) {
      super(config);
      if (null != this.typeAttribute) {
         String stringType = null;
         if (!this.typeAttribute.isLiteral()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            stringType = (String)this.typeAttribute.getValueExpression(ctx, String.class).getValue(ctx);
         } else {
            stringType = this.typeAttribute.getValue();
         }

         this.checkType(stringType);
         this.listenerType = stringType;
      } else {
         this.listenerType = null;
      }

   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent != null && ComponentHandler.isNew(parent)) {
         if (parent instanceof EditableValueHolder) {
            this.applyAttachedObject(ctx.getFacesContext(), parent);
         } else {
            if (!parent.getAttributes().containsKey("javax.faces.application.Resource.ComponentResource")) {
               throw new TagException(this.tag, "Parent is not of type EditableValueHolder, type is: " + parent);
            }

            CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this);
         }

      }
   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      EditableValueHolder evh = (EditableValueHolder)parent;
      ValueExpression b = null;
      if (this.binding != null) {
         b = this.binding.getValueExpression(ctx, ValueChangeListener.class);
      }

      ValueChangeListener listener = new LazyValueChangeListener(this.listenerType, b);
      evh.addValueChangeListener(listener);
   }

   public String getFor() {
      String result = null;
      TagAttribute attr = this.getAttribute("for");
      if (null != attr) {
         if (attr.isLiteral()) {
            result = attr.getValue();
         } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            result = (String)attr.getValueExpression(ctx, String.class).getValue(ctx);
         }
      }

      return result;
   }

   private void checkType(String type) {
      try {
         ReflectionUtil.forName(type);
      } catch (ClassNotFoundException var3) {
         throw new TagAttributeException(this.typeAttribute, "Couldn't qualify ActionListener", var3);
      }
   }

   private static class LazyValueChangeListener implements ValueChangeListener, Serializable {
      private static final long serialVersionUID = 7613811124326963180L;
      private final String type;
      private final ValueExpression binding;

      public LazyValueChangeListener(String type, ValueExpression binding) {
         this.type = type;
         this.binding = binding;
      }

      public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
         ValueChangeListener instance = null;
         FacesContext faces = FacesContext.getCurrentInstance();
         if (faces != null) {
            if (this.binding != null) {
               instance = (ValueChangeListener)this.binding.getValue(faces.getELContext());
            }

            if (instance == null && this.type != null) {
               try {
                  instance = (ValueChangeListener)ReflectionUtil.forName(this.type).newInstance();
               } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var5) {
                  throw new AbortProcessingException("Couldn't Lazily instantiate ValueChangeListener", var5);
               }

               if (this.binding != null) {
                  this.binding.setValue(faces.getELContext(), instance);
               }
            }

            if (instance != null) {
               instance.processValueChange(event);
            }

         }
      }
   }
}
