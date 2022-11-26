package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.el.LegacyValueBinding;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagHandler;
import java.io.IOException;
import java.io.Serializable;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.ActionSource2;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ActionSource2AttachedObjectHandler;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public class SetPropertyActionListenerHandler extends TagHandlerImpl implements ActionSource2AttachedObjectHandler {
   private final TagAttribute value = this.getRequiredAttribute("value");
   private final TagAttribute target = this.getRequiredAttribute("target");

   public SetPropertyActionListenerHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (null != parent && ComponentHandler.isNew(parent)) {
         if (parent instanceof ActionSource) {
            this.applyAttachedObject(ctx.getFacesContext(), parent);
         } else {
            if (!UIComponent.isCompositeComponent(parent)) {
               throw new TagException(this.tag, "Parent is not of type ActionSource, type is: " + parent);
            }

            if (null == this.getFor()) {
               throw new TagException(this.tag, "actionListener tags nested within composite components must have a non-null \"for\" attribute");
            }

            CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this);
         }

      }
   }

   public void applyAttachedObject(FacesContext context, UIComponent parent) {
      FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
      ActionSource src = (ActionSource)parent;
      ValueExpression valueExpr = this.value.getValueExpression(ctx, Object.class);
      ValueExpression targetExpr = this.target.getValueExpression(ctx, Object.class);
      Object listener;
      if (src instanceof ActionSource2) {
         listener = new SetPropertyListener(valueExpr, targetExpr);
      } else {
         listener = new LegacySetPropertyListener(new LegacyValueBinding(valueExpr), new LegacyValueBinding(targetExpr));
      }

      src.addActionListener((ActionListener)listener);
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

   private static class SetPropertyListener implements ActionListener, Serializable {
      private static final long serialVersionUID = -2760242070551459725L;
      private ValueExpression value;
      private ValueExpression target;

      public SetPropertyListener() {
      }

      public SetPropertyListener(ValueExpression value, ValueExpression target) {
         this.value = value;
         this.target = target;
      }

      public void processAction(ActionEvent evt) throws AbortProcessingException {
         FacesContext faces = FacesContext.getCurrentInstance();
         ELContext el = faces.getELContext();
         Object valueObj = this.value.getValue(el);
         if (valueObj != null) {
            ExpressionFactory factory = faces.getApplication().getExpressionFactory();
            valueObj = factory.coerceToType(valueObj, this.target.getType(el));
         }

         this.target.setValue(el, valueObj);
      }
   }

   private static class LegacySetPropertyListener implements ActionListener, Serializable {
      private static final long serialVersionUID = 3004987947382293693L;
      private ValueBinding value;
      private ValueBinding target;

      public LegacySetPropertyListener() {
      }

      public LegacySetPropertyListener(ValueBinding value, ValueBinding target) {
         this.value = value;
         this.target = target;
      }

      public void processAction(ActionEvent evt) throws AbortProcessingException {
         FacesContext faces = FacesContext.getCurrentInstance();
         Object valueObj = this.value.getValue(faces);
         this.target.setValue(faces, valueObj);
      }
   }
}
