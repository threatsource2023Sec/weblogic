package com.oracle.weblogic.diagnostics.expressions;

import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FunctionDescriptor {
   public static final String CATEGORY_VALUE_NAME = "category";
   public static final String PREFIX_VALUE_NAME = "prefix";
   public static final String SMART_RULE_VALUE_NAME = "smartRule";
   /** @deprecated */
   @Deprecated
   public static final String NAMESPACE_VALUE_NAME = "namespace";

   public static MethodDescriptor createDescriptor(MethodDescriptor md, String ns) {
      MethodDescriptor descriptorCopy = new MethodDescriptor(md.getMethod(), md.getParameterDescriptors());
      if (ns != null) {
         descriptorCopy.setValue("namespace", ns);
         descriptorCopy.setValue("prefix", ns);
      }

      descriptorCopy.setValue("smartRule", isSmartRule(md.getMethod()));
      descriptorCopy.setHidden(hasExcludeAnnotation(md.getMethod()));
      descriptorCopy.setValue("category", getFunctionCategory(md.getMethod()));
      return descriptorCopy;
   }

   static String getFunctionCategory(Method method) {
      Function fnAnnotation = (Function)method.getAnnotation(Function.class);
      String category = fnAnnotation != null ? fnAnnotation.category() : null;
      if (category == null || category.isEmpty()) {
         FunctionProvider fpAnnotation = (FunctionProvider)method.getDeclaringClass().getAnnotation(FunctionProvider.class);
         category = fpAnnotation != null ? fpAnnotation.category() : "";
      }

      return category;
   }

   static boolean hasFunctionAnnotation(Method serviceMethod) {
      Function fnAnnotation = (Function)serviceMethod.getAnnotation(Function.class);
      return fnAnnotation != null;
   }

   static boolean hasSmartRuleAnnotation(Method serviceMethod) {
      Function fnAnnotation = (Function)serviceMethod.getAnnotation(Function.class);
      return fnAnnotation != null ? fnAnnotation.smartRule() : false;
   }

   static boolean isPublicStaticMethod(Method method) {
      int modifiers = method.getModifiers();
      return Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers);
   }

   static boolean isPredicate(Method m) {
      Class returnTypeClass = m.getReturnType();
      boolean isBooleanReturnType = Boolean.class.isAssignableFrom(returnTypeClass) || Boolean.TYPE.isAssignableFrom(returnTypeClass);
      return isBooleanReturnType;
   }

   static boolean isSmartRule(Method fnMethod) {
      return isPublicStaticMethod(fnMethod) && hasSmartRuleAnnotation(fnMethod) && isPredicate(fnMethod);
   }

   static boolean isFunctionOrSmartRule(Method method) {
      return isFunction(method) || isSmartRule(method);
   }

   static boolean isFunction(Method method) {
      return hasFunctionAnnotation(method) && isPublicStaticMethod(method);
   }

   static boolean hasExcludeAnnotation(Method propertyMethod) {
      Exclude annotation = propertyMethod == null ? null : (Exclude)propertyMethod.getAnnotation(Exclude.class);
      return annotation != null;
   }
}
