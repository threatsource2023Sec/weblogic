package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.util.ReflectionUtil;
import java.io.Serializable;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ActionSource2AttachedObjectHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

public final class ActionListenerHandler extends ActionListenerHandlerBase implements ActionSource2AttachedObjectHandler {
   private final TagAttribute binding = this.getAttribute("binding");
   private String listenerType;
   private final TagAttribute typeAttribute = this.getAttribute("type");

   public ActionListenerHandler(TagConfig config) {
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

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      ActionSource as = (ActionSource)parent;
      ValueExpression b = null;
      if (this.binding != null) {
         b = this.binding.getValueExpression(ctx, ActionListener.class);
      }

      ActionListener listener = new LazyActionListener(this.listenerType, b);
      as.addActionListener(listener);
   }

   private void checkType(String type) {
      try {
         ReflectionUtil.forName(type);
      } catch (ClassNotFoundException var3) {
         throw new TagAttributeException(this.typeAttribute, "Couldn't qualify ActionListener", var3);
      }
   }

   private static final class LazyActionListener implements ActionListener, Serializable {
      private static final long serialVersionUID = -9202120013153262119L;
      private final String type;
      private final ValueExpression binding;

      public LazyActionListener(String type, ValueExpression binding) {
         this.type = type;
         this.binding = binding;
      }

      public void processAction(ActionEvent event) throws AbortProcessingException {
         ActionListener instance = null;
         FacesContext faces = FacesContext.getCurrentInstance();
         if (faces != null) {
            if (this.binding != null) {
               instance = (ActionListener)this.binding.getValue(faces.getELContext());
            }

            if (instance == null && this.type != null) {
               try {
                  instance = (ActionListener)ReflectionUtil.forName(this.type).newInstance();
               } catch (Exception var5) {
                  throw new AbortProcessingException("Couldn't Lazily instantiate ValueChangeListener", var5);
               }

               if (this.binding != null) {
                  this.binding.setValue(faces.getELContext(), instance);
               }
            }

            if (instance != null) {
               instance.processAction(event);
            }

         }
      }
   }
}
