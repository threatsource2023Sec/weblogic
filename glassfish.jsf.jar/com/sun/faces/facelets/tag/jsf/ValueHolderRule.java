package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.el.LegacyValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectBoolean;
import javax.faces.component.ValueHolder;
import javax.faces.convert.Converter;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

final class ValueHolderRule extends MetaRule {
   public static final ValueHolderRule Instance = new ValueHolderRule();

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      if (meta.isTargetInstanceOf(ValueHolder.class)) {
         if ("converter".equals(name)) {
            if (attribute.isLiteral()) {
               return new LiteralConverterMetadata(attribute.getValue());
            }

            return new DynamicConverterMetadata2(attribute);
         }

         if ("value".equals(name)) {
            return new DynamicValueExpressionMetadata(attribute);
         }
      }

      return null;
   }

   static final class DynamicValueBindingMetadata extends Metadata {
      private final TagAttribute attr;

      public DynamicValueBindingMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).setValueBinding("value", new LegacyValueBinding(this.attr.getValueExpression(ctx, Object.class)));
      }
   }

   static final class DynamicValueExpressionMetadata extends Metadata {
      private final TagAttribute attr;

      public DynamicValueExpressionMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         UIComponent c = (UIComponent)instance;
         c.setValueExpression("value", this.attr.getValueExpression(ctx, c instanceof UISelectBoolean ? Boolean.class : Object.class));
      }
   }

   static final class DynamicConverterMetadata2 extends Metadata {
      private final TagAttribute attr;

      public DynamicConverterMetadata2(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).setValueExpression("converter", this.attr.getValueExpression(ctx, Converter.class));
      }
   }

   static final class DynamicConverterMetadata extends Metadata {
      private final TagAttribute attr;

      public DynamicConverterMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).setValueBinding("converter", new LegacyValueBinding(this.attr.getValueExpression(ctx, Converter.class)));
      }
   }

   static final class LiteralConverterMetadata extends Metadata {
      private final String converterId;

      public LiteralConverterMetadata(String converterId) {
         this.converterId = converterId;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((ValueHolder)instance).setConverter(ctx.getFacesContext().getApplication().createConverter(this.converterId));
      }
   }
}
