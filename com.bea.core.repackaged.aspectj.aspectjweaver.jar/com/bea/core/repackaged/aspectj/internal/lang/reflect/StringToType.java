package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.StringTokenizer;

public class StringToType {
   public static Type[] commaSeparatedListToTypeArray(String typeNames, Class classScope) throws ClassNotFoundException {
      StringTokenizer strTok = new StringTokenizer(typeNames, ",");
      Type[] ret = new Type[strTok.countTokens()];

      String typeName;
      for(int index = 0; strTok.hasMoreTokens(); ret[index++] = stringToType(typeName, classScope)) {
         typeName = strTok.nextToken().trim();
      }

      return ret;
   }

   public static Type stringToType(String typeName, Class classScope) throws ClassNotFoundException {
      try {
         return (Type)(typeName.indexOf("<") == -1 ? AjTypeSystem.getAjType(Class.forName(typeName, false, classScope.getClassLoader())) : makeParameterizedType(typeName, classScope));
      } catch (ClassNotFoundException var5) {
         TypeVariable[] tVars = classScope.getTypeParameters();

         for(int i = 0; i < tVars.length; ++i) {
            if (tVars[i].getName().equals(typeName)) {
               return tVars[i];
            }
         }

         throw new ClassNotFoundException(typeName);
      }
   }

   private static Type makeParameterizedType(String typeName, Class classScope) throws ClassNotFoundException {
      int paramStart = typeName.indexOf(60);
      String baseName = typeName.substring(0, paramStart);
      final Class baseClass = Class.forName(baseName, false, classScope.getClassLoader());
      int paramEnd = typeName.lastIndexOf(62);
      String params = typeName.substring(paramStart + 1, paramEnd);
      final Type[] typeParams = commaSeparatedListToTypeArray(params, classScope);
      return new ParameterizedType() {
         public Type[] getActualTypeArguments() {
            return typeParams;
         }

         public Type getRawType() {
            return baseClass;
         }

         public Type getOwnerType() {
            return baseClass.getEnclosingClass();
         }
      };
   }
}
