package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.el.LegacyValueBinding;
import com.sun.faces.util.FacesLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

final class ComponentRule extends MetaRule {
   private static final Logger log;
   public static final ComponentRule Instance;

   public ComponentRule() {
   }

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      if (meta.isTargetInstanceOf(UIComponent.class)) {
         if (!attribute.isLiteral()) {
            Class type = meta.getPropertyType(name);
            if (type == null) {
               type = Object.class;
            }

            return new ValueExpressionMetadata(name, type, attribute);
         }

         if (meta.getWriteMethod(name) == null) {
            warnAttr(attribute, meta.getTargetClass(), name);
            return new LiteralAttributeMetadata(name, attribute.getValue());
         }
      }

      return null;
   }

   private static void warnAttr(TagAttribute attr, Class type, String n) {
      if (log.isLoggable(Level.FINER)) {
         log.finer(attr + " Property '" + n + "' is not on type: " + type.getName());
      }

   }

   static {
      log = FacesLogger.FACELETS_COMPONENT.getLogger();
      Instance = new ComponentRule();
   }

   static final class ValueBindingMetadata extends Metadata {
      private final String name;
      private final TagAttribute attr;
      private final Class type;

      public ValueBindingMetadata(String name, Class type, TagAttribute attr) {
         this.name = name;
         this.attr = attr;
         this.type = type;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).setValueBinding(this.name, new LegacyValueBinding(this.attr.getValueExpression(ctx, this.type)));
      }
   }

   static final class ValueExpressionMetadata extends Metadata {
      private final String name;
      private final TagAttribute attr;
      private final Class type;

      public ValueExpressionMetadata(String name, Class type, TagAttribute attr) {
         this.name = name;
         this.attr = attr;
         this.type = type;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).setValueExpression(this.name, this.attr.getValueExpression(ctx, this.type));
      }
   }

   static final class LiteralAttributeMetadata extends Metadata {
      private final String name;
      private final String value;

      public LiteralAttributeMetadata(String name, String value) {
         this.value = value;
         this.name = name;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).getAttributes().put(this.name, this.value);
      }
   }
}
