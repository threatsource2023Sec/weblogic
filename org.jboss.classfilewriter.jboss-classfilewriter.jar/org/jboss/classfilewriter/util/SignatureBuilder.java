package org.jboss.classfilewriter.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

public class SignatureBuilder {
   public static String fieldAttribute(Type type) {
      StringBuilder builder = new StringBuilder();
      fieldAttribute(type, builder);
      return builder.toString();
   }

   private static void fieldAttribute(Type type, StringBuilder builder) {
      if (type instanceof Class) {
         classType((Class)type, builder);
      } else if (type instanceof ParameterizedType) {
         ParameterizedType ptype = (ParameterizedType)type;
         parametizedType(ptype, builder);
      } else if (type instanceof WildcardType) {
         WildcardType ptype = (WildcardType)type;
         wildcardType(ptype, builder);
      }

   }

   private static void wildcardType(WildcardType type, StringBuilder builder) {
      builder.append('*');
   }

   public static void parametizedType(ParameterizedType type, StringBuilder builder) {
      fieldAttribute(type.getRawType(), builder);
      builder.append('<');
      Type[] var2 = type.getActualTypeArguments();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type t = var2[var4];
         fieldAttribute(t, builder);
         builder.append(';');
      }

      builder.append(">;");
   }

   private static void classType(Class clazz, StringBuilder builder) {
      if (clazz.isMemberClass()) {
         classType(clazz.getDeclaringClass(), builder);
         builder.append('.');
         builder.append(clazz.getSimpleName());
      } else {
         builder.append("L");
         builder.append(clazz.getName().replace('.', '/'));
      }

   }

   private SignatureBuilder() {
   }
}
