package com.sun.faces.facelets.tag.jsf;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

public final class RenderPropertyRule extends MetaRule {
   public static final RenderPropertyRule Instance = new RenderPropertyRule();

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      if ("hideNoSelectionOption".equals(name)) {
         return (Metadata)(attribute.isLiteral() ? new HideNoSelectionLiteralMetadata(attribute.getValue()) : new HideNoSelectionExpressionMetadata(attribute));
      } else {
         return null;
      }
   }

   static final class HideNoSelectionExpressionMetadata extends Metadata {
      private final TagAttribute attr;

      public HideNoSelectionExpressionMetadata(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((UIComponent)instance).setValueExpression("hideNoSelectionOption", this.attr.getValueExpression(ctx, Boolean.class));
      }
   }

   static final class HideNoSelectionLiteralMetadata extends Metadata {
      private final String hideOption;

      public HideNoSelectionLiteralMetadata(String hideOption) {
         this.hideOption = hideOption;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         Map attributes = ((UIInput)instance).getAttributes();
         attributes.put("hideNoSelectionOption", Boolean.valueOf(this.hideOption));
      }
   }
}
