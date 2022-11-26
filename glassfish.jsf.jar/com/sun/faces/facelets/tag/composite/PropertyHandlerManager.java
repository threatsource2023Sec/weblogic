package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.el.TagValueExpression;
import com.sun.faces.util.Util;
import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.application.ProjectStage;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

class PropertyHandlerManager {
   private static final Map ALL_HANDLERS = new HashMap(12, 1.0F);
   private static final String[] DEV_ONLY_ATTRIBUTES;
   private Map managedHandlers;
   private PropertyHandler genericHandler = new ObjectValueExpressionPropertyHandler();

   private PropertyHandlerManager(Map managedHandlers) {
      this.managedHandlers = managedHandlers;
   }

   static PropertyHandlerManager getInstance(String[] attributes) {
      Map handlers = new HashMap(attributes.length, 1.0F);
      String[] var2 = attributes;
      int var3 = attributes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String attribute = var2[var4];
         handlers.put(attribute, ALL_HANDLERS.get(attribute));
      }

      return new PropertyHandlerManager(handlers);
   }

   PropertyHandler getHandler(FaceletContext ctx, String name) {
      if (!ctx.getFacesContext().isProjectStage(ProjectStage.Development) && Arrays.binarySearch(DEV_ONLY_ATTRIBUTES, name) >= 0) {
         return null;
      } else {
         PropertyHandler h = (PropertyHandler)this.managedHandlers.get(name);
         return h != null ? h : this.genericHandler;
      }
   }

   static {
      ALL_HANDLERS.put("targets", new StringValueExpressionPropertyHandler());
      ALL_HANDLERS.put("targetAttributeName", new StringValueExpressionPropertyHandler());
      ALL_HANDLERS.put("method-signature", new StringValueExpressionPropertyHandler());
      ALL_HANDLERS.put("type", new StringValueExpressionPropertyHandler());
      ALL_HANDLERS.put("default", new DefaultPropertyHandler());
      ALL_HANDLERS.put("displayName", new DisplayNamePropertyHandler());
      ALL_HANDLERS.put("shortDescription", new ShortDescriptionPropertyHandler());
      ALL_HANDLERS.put("expert", new ExpertPropertyHandler());
      ALL_HANDLERS.put("hidden", new HiddenPropertyHandler());
      ALL_HANDLERS.put("preferred", new PreferredPropertyHandler());
      ALL_HANDLERS.put("required", new BooleanValueExpressionPropertyHandler());
      ALL_HANDLERS.put("name", new NamePropertyHandler());
      ALL_HANDLERS.put("componentType", new ComponentTypePropertyHandler());
      DEV_ONLY_ATTRIBUTES = new String[]{"displayName", "shortDescription", "export", "hidden", "preferred"};
      Arrays.sort(DEV_ONLY_ATTRIBUTES);
   }

   private static class BooleanValueExpressionPropertyHandler extends TypedValueExpressionPropertyHandler {
      private BooleanValueExpressionPropertyHandler() {
         super(null);
      }

      public Class getEvalType() {
         return Boolean.class;
      }

      // $FF: synthetic method
      BooleanValueExpressionPropertyHandler(Object x0) {
         this();
      }
   }

   private static final class DisplayNamePropertyHandler extends StringFeatureDescriptorPropertyHandler {
      private DisplayNamePropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         ValueExpression ve = attribute.getValueExpression(ctx, this.getEvalType());
         target.setDisplayName((String)ve.getValue(ctx));
      }

      // $FF: synthetic method
      DisplayNamePropertyHandler(Object x0) {
         this();
      }
   }

   private static final class ExpertPropertyHandler extends BooleanFeatureDescriptorPropertyHandler {
      private ExpertPropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         ValueExpression ve = attribute.getValueExpression(ctx, this.getEvalType());
         target.setExpert((Boolean)ve.getValue(ctx));
      }

      // $FF: synthetic method
      ExpertPropertyHandler(Object x0) {
         this();
      }
   }

   private static final class HiddenPropertyHandler extends BooleanFeatureDescriptorPropertyHandler {
      private HiddenPropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         ValueExpression ve = attribute.getValueExpression(ctx, this.getEvalType());
         target.setHidden((Boolean)ve.getValue(ctx));
      }

      // $FF: synthetic method
      HiddenPropertyHandler(Object x0) {
         this();
      }
   }

   private static final class PreferredPropertyHandler extends BooleanFeatureDescriptorPropertyHandler {
      private PreferredPropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         ValueExpression ve = attribute.getValueExpression(ctx, this.getEvalType());
         target.setPreferred((Boolean)ve.getValue(ctx));
      }

      // $FF: synthetic method
      PreferredPropertyHandler(Object x0) {
         this();
      }
   }

   private static class ComponentTypePropertyHandler extends StringValueExpressionPropertyHandler {
      private ComponentTypePropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         super.apply(ctx, "javax.faces.component.COMPOSITE_COMPONENT_TYPE", target, attribute);
      }

      // $FF: synthetic method
      ComponentTypePropertyHandler(Object x0) {
         this();
      }
   }

   private static class DefaultPropertyHandler implements PropertyHandler {
      private DefaultPropertyHandler() {
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         Class type = Object.class;
         Object obj = target.getValue("type");
         if (null != obj && !(obj instanceof Class)) {
            TagValueExpression typeVE = (TagValueExpression)obj;
            Object value = typeVE.getValue(ctx);
            if (value instanceof Class) {
               type = (Class)value;
            } else if (value != null) {
               try {
                  type = Util.loadClass(String.valueOf(value), this);
               } catch (ClassNotFoundException var10) {
                  throw new IllegalArgumentException(var10);
               }
            }
         } else {
            type = null != obj ? (Class)obj : Object.class;
         }

         target.setValue(propName, attribute.getValueExpression(ctx, type));
      }

      // $FF: synthetic method
      DefaultPropertyHandler(Object x0) {
         this();
      }
   }

   private static class ObjectValueExpressionPropertyHandler extends TypedValueExpressionPropertyHandler {
      private ObjectValueExpressionPropertyHandler() {
         super(null);
      }

      public Class getEvalType() {
         return Object.class;
      }

      // $FF: synthetic method
      ObjectValueExpressionPropertyHandler(Object x0) {
         this();
      }
   }

   private static class StringValueExpressionPropertyHandler extends TypedValueExpressionPropertyHandler {
      private StringValueExpressionPropertyHandler() {
         super(null);
      }

      public Class getEvalType() {
         return String.class;
      }

      // $FF: synthetic method
      StringValueExpressionPropertyHandler(Object x0) {
         this();
      }
   }

   private static final class ShortDescriptionPropertyHandler extends StringFeatureDescriptorPropertyHandler {
      private ShortDescriptionPropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         ValueExpression ve = attribute.getValueExpression(ctx, this.getEvalType());
         String v = (String)ve.getValue(ctx);
         if (v != null) {
            target.setShortDescription((String)ve.getValue(ctx));
         }

      }

      // $FF: synthetic method
      ShortDescriptionPropertyHandler(Object x0) {
         this();
      }
   }

   private static final class NamePropertyHandler extends StringFeatureDescriptorPropertyHandler {
      private NamePropertyHandler() {
         super(null);
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         ValueExpression ve = attribute.getValueExpression(ctx, this.getEvalType());
         String v = (String)ve.getValue(ctx);
         if (v != null) {
            target.setShortDescription((String)ve.getValue(ctx));
         }

      }

      // $FF: synthetic method
      NamePropertyHandler(Object x0) {
         this();
      }
   }

   private abstract static class TypedValueExpressionPropertyHandler implements TypedPropertyHandler {
      private TypedValueExpressionPropertyHandler() {
      }

      public void apply(FaceletContext ctx, String propName, FeatureDescriptor target, TagAttribute attribute) {
         target.setValue(propName, attribute.getValueExpression(ctx, this.getEvalType()));
      }

      public abstract Class getEvalType();

      // $FF: synthetic method
      TypedValueExpressionPropertyHandler(Object x0) {
         this();
      }
   }

   private abstract static class StringFeatureDescriptorPropertyHandler implements TypedPropertyHandler {
      private StringFeatureDescriptorPropertyHandler() {
      }

      public Class getEvalType() {
         return String.class;
      }

      // $FF: synthetic method
      StringFeatureDescriptorPropertyHandler(Object x0) {
         this();
      }
   }

   private abstract static class BooleanFeatureDescriptorPropertyHandler implements TypedPropertyHandler {
      private BooleanFeatureDescriptorPropertyHandler() {
      }

      public Class getEvalType() {
         return Boolean.class;
      }

      // $FF: synthetic method
      BooleanFeatureDescriptorPropertyHandler(Object x0) {
         this();
      }
   }
}
