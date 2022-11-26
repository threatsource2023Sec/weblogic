package com.bea.staxb.buildtime.internal.bts;

import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import weblogic.utils.collections.LRUCacheHashMap;

public class MethodName implements Serializable {
   private String mMethodName;
   private JavaTypeName[] mParamTypes;
   private static final Map cache = Collections.synchronizedMap(new LRUCacheHashMap(200));
   private static final long serialVersionUID = 1L;

   public static MethodName create(JMethod m) {
      JParameter[] params = m.getParameters();
      if (params != null && params.length != 0) {
         if (params.length == 1) {
            JavaTypeName typeName = JavaTypeName.forJClass(params[0].getType());
            JavaTypeName[] types = (JavaTypeName[])cache.get(typeName);
            if (types == null) {
               types = new JavaTypeName[params.length];
               types[0] = typeName;
               cache.put(typeName, types);
            }

            return new MethodName(m.getSimpleName(), types);
         } else {
            JavaTypeName[] types = new JavaTypeName[params.length];

            for(int i = 0; i < types.length; ++i) {
               types[i] = JavaTypeName.forJClass(params[i].getType());
            }

            return new MethodName(m.getSimpleName(), types);
         }
      } else {
         return new MethodName(m.getSimpleName());
      }
   }

   public static MethodName create(String methodName) {
      return new MethodName(methodName);
   }

   public static MethodName create(String methodName, JavaTypeName paramType) {
      return create(methodName, new JavaTypeName[]{paramType});
   }

   public static MethodName create(String methodName, JavaTypeName[] paramTypes) {
      return paramTypes != null && paramTypes.length != 0 ? new MethodName(methodName, paramTypes) : new MethodName(methodName);
   }

   static JavaTypeName[] namesForStrings(String[] names) {
      JavaTypeName[] out = new JavaTypeName[names.length];

      for(int i = 0; i < out.length; ++i) {
         out[i] = JavaTypeName.forString(names[i]);
      }

      return out;
   }

   private MethodName(String methodName, JavaTypeName[] types) {
      this.mMethodName = methodName != null ? methodName.intern() : null;
      this.mParamTypes = types;
   }

   private MethodName(String methodName) {
      this.mMethodName = methodName != null ? methodName.intern() : null;
   }

   public String getSimpleName() {
      return this.mMethodName;
   }

   public JavaTypeName[] getParamTypes() {
      return this.mParamTypes;
   }

   public Method getMethodOn(Class containingClass) throws ClassNotFoundException, NoSuchMethodException {
      if (containingClass == null) {
         throw new IllegalArgumentException("null class");
      } else {
         Class[] types = null;
         if (this.mParamTypes != null && this.mParamTypes.length > 0) {
            types = new Class[this.mParamTypes.length];

            for(int i = 0; i < types.length; ++i) {
               types[i] = this.mParamTypes[i].loadClassIn(containingClass.getClassLoader());
            }
         }

         return containingClass.getMethod(this.mMethodName, types);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof MethodName)) {
         return false;
      } else {
         MethodName methodName = (MethodName)o;
         if (this.mMethodName != null) {
            if (this.mMethodName.equals(methodName.mMethodName)) {
               return Arrays.equals(this.mParamTypes, methodName.mParamTypes);
            }
         } else if (methodName.mMethodName == null) {
            return Arrays.equals(this.mParamTypes, methodName.mParamTypes);
         }

         return false;
      }
   }

   public int hashCode() {
      return this.mMethodName != null ? this.mMethodName.hashCode() : 0;
   }

   public String toString() {
      return "MethodName{methodName='" + this.mMethodName + "', paramTypes=" + (this.mParamTypes == null ? null : Arrays.asList(this.mParamTypes)) + "}";
   }
}
