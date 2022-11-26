package com.sun.faces.facelets.tag;

import com.sun.faces.facelets.el.LegacyMethodBinding;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.el.MethodExpression;
import javax.faces.el.MethodBinding;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

public final class MethodRule extends MetaRule {
   private final String methodName;
   private final Class returnTypeClass;
   private final Class[] params;

   public MethodRule(String methodName, Class returnTypeClass, Class[] params) {
      this.methodName = methodName;
      this.returnTypeClass = returnTypeClass;
      this.params = params;
   }

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      if (!name.equals(this.methodName)) {
         return null;
      } else {
         Method method;
         if (MethodBinding.class.equals(meta.getPropertyType(name))) {
            method = meta.getWriteMethod(name);
            if (method != null) {
               return new MethodBindingMetadata(method, attribute, this.returnTypeClass, this.params);
            }
         } else if (MethodExpression.class.equals(meta.getPropertyType(name))) {
            method = meta.getWriteMethod(name);
            if (method != null) {
               return new MethodExpressionMetadata(method, attribute, this.returnTypeClass, this.params);
            }
         }

         return null;
      }
   }

   private static class MethodExpressionMetadata extends Metadata {
      private final Method _method;
      private final TagAttribute _attribute;
      private Class[] _paramList;
      private Class _returnType;

      public MethodExpressionMetadata(Method method, TagAttribute attribute, Class returnType, Class[] paramList) {
         this._method = method;
         this._attribute = attribute;
         this._paramList = paramList;
         this._returnType = returnType;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         MethodExpression expr = this._attribute.getMethodExpression(ctx, this._returnType, this._paramList);

         try {
            this._method.invoke(instance, expr);
         } catch (InvocationTargetException var5) {
            throw new TagAttributeException(this._attribute, var5.getCause());
         } catch (IllegalArgumentException | IllegalAccessException var6) {
            throw new TagAttributeException(this._attribute, var6);
         }
      }
   }

   private static class MethodBindingMetadata extends Metadata {
      private final Method _method;
      private final TagAttribute _attribute;
      private Class[] _paramList;
      private Class _returnType;

      public MethodBindingMetadata(Method method, TagAttribute attribute, Class returnType, Class[] paramList) {
         this._method = method;
         this._attribute = attribute;
         this._paramList = paramList;
         this._returnType = returnType;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         MethodExpression expr = this._attribute.getMethodExpression(ctx, this._returnType, this._paramList);

         try {
            this._method.invoke(instance, new LegacyMethodBinding(expr));
         } catch (InvocationTargetException var5) {
            throw new TagAttributeException(this._attribute, var5.getCause());
         } catch (Exception var6) {
            throw new TagAttributeException(this._attribute, var6);
         }
      }
   }
}
