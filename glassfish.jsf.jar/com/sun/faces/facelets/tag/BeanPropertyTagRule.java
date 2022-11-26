package com.sun.faces.facelets.tag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

final class BeanPropertyTagRule extends MetaRule {
   public static final BeanPropertyTagRule Instance = new BeanPropertyTagRule();

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      Method m = meta.getWriteMethod(name);
      if (m != null) {
         return (Metadata)(attribute.isLiteral() ? new LiteralPropertyMetadata(m, attribute) : new DynamicPropertyMetadata(m, attribute));
      } else {
         return null;
      }
   }

   static final class DynamicPropertyMetadata extends Metadata {
      private final Method method;
      private final TagAttribute attribute;
      private final Class type;

      public DynamicPropertyMetadata(Method method, TagAttribute attribute) {
         this.method = method;
         this.type = method.getParameterTypes()[0];
         this.attribute = attribute;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         try {
            this.method.invoke(instance, this.attribute.getObject(ctx, this.type));
         } catch (InvocationTargetException var4) {
            throw new TagAttributeException(this.attribute, var4.getCause());
         } catch (IllegalArgumentException | IllegalAccessException var5) {
            throw new TagAttributeException(this.attribute, var5);
         }
      }
   }

   static final class LiteralPropertyMetadata extends Metadata {
      private final Method method;
      private final TagAttribute attribute;
      private Object[] value;

      public LiteralPropertyMetadata(Method method, TagAttribute attribute) {
         this.method = method;
         this.attribute = attribute;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         if (this.value == null) {
            String str = this.attribute.getValue();
            this.value = new Object[]{ctx.getExpressionFactory().coerceToType(str, this.method.getParameterTypes()[0])};
         }

         try {
            this.method.invoke(instance, this.value);
         } catch (InvocationTargetException var4) {
            throw new TagAttributeException(this.attribute, var4.getCause());
         } catch (IllegalArgumentException | IllegalAccessException var5) {
            throw new TagAttributeException(this.attribute, var5);
         }
      }
   }
}
