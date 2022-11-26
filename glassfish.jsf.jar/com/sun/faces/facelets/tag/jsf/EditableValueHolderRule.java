package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.el.LegacyMethodBinding;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

public final class EditableValueHolderRule extends MetaRule {
   private static final Class[] VALIDATOR_SIG = new Class[]{FacesContext.class, UIComponent.class, Object.class};
   private static final Class[] VALUECHANGE_SIG = new Class[]{ValueChangeEvent.class};
   public static final EditableValueHolderRule Instance = new EditableValueHolderRule();

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      if (meta.isTargetInstanceOf(EditableValueHolder.class)) {
         if ("validator".equals(name)) {
            if (attribute.isLiteral()) {
               return new LiteralValidatorMetadata(attribute.getValue());
            }

            return new ValidatorExpressionMetadata(attribute);
         }

         if ("valueChangeListener".equals(name)) {
            return new ValueChangedExpressionMetadata(attribute);
         }
      }

      return null;
   }

   static final class ValidatorBindingMetadata extends Metadata {
      private final TagAttribute attr;

      public ValidatorBindingMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((EditableValueHolder)instance).setValidator(new LegacyMethodBinding(this.attr.getMethodExpression(ctx, (Class)null, EditableValueHolderRule.VALIDATOR_SIG)));
      }
   }

   static final class ValidatorExpressionMetadata extends Metadata {
      private final TagAttribute attr;

      public ValidatorExpressionMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((EditableValueHolder)instance).addValidator(new MethodExpressionValidator(this.attr.getMethodExpression(ctx, (Class)null, EditableValueHolderRule.VALIDATOR_SIG)));
      }
   }

   static final class ValueChangedBindingMetadata extends Metadata {
      private final TagAttribute attr;

      public ValueChangedBindingMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((EditableValueHolder)instance).setValueChangeListener(new LegacyMethodBinding(this.attr.getMethodExpression(ctx, (Class)null, EditableValueHolderRule.VALUECHANGE_SIG)));
      }
   }

   static final class ValueChangedExpressionMetadata extends Metadata {
      private final TagAttribute attr;

      public ValueChangedExpressionMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((EditableValueHolder)instance).addValueChangeListener(new MethodExpressionValueChangeListener(this.attr.getMethodExpression(ctx, (Class)null, EditableValueHolderRule.VALUECHANGE_SIG)));
      }
   }

   static final class LiteralValidatorMetadata extends Metadata {
      private final String validatorId;

      public LiteralValidatorMetadata(String validatorId) {
         this.validatorId = validatorId;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((EditableValueHolder)instance).addValidator(ctx.getFacesContext().getApplication().createValidator(this.validatorId));
      }
   }
}
