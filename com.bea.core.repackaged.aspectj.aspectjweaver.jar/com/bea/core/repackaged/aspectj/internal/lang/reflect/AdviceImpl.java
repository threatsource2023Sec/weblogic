package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.annotation.AdviceName;
import com.bea.core.repackaged.aspectj.lang.reflect.Advice;
import com.bea.core.repackaged.aspectj.lang.reflect.AdviceKind;
import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.PointcutExpression;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class AdviceImpl implements Advice {
   private static final String AJC_INTERNAL = "com.bea.core.repackaged.aspectj.runtime.internal";
   private final AdviceKind kind;
   private final Method adviceMethod;
   private PointcutExpression pointcutExpression;
   private boolean hasExtraParam;
   private Type[] genericParameterTypes;
   private AjType[] parameterTypes;
   private AjType[] exceptionTypes;

   protected AdviceImpl(Method method, String pointcut, AdviceKind type) {
      this.hasExtraParam = false;
      this.kind = type;
      this.adviceMethod = method;
      this.pointcutExpression = new PointcutExpressionImpl(pointcut);
   }

   protected AdviceImpl(Method method, String pointcut, AdviceKind type, String extraParamName) {
      this(method, pointcut, type);
      this.hasExtraParam = true;
   }

   public AjType getDeclaringType() {
      return AjTypeSystem.getAjType(this.adviceMethod.getDeclaringClass());
   }

   public Type[] getGenericParameterTypes() {
      if (this.genericParameterTypes == null) {
         Type[] genTypes = this.adviceMethod.getGenericParameterTypes();
         int syntheticCount = 0;
         Type[] arr$ = genTypes;
         int len$ = genTypes.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Type t = arr$[i$];
            if (t instanceof Class && ((Class)t).getPackage().getName().equals("com.bea.core.repackaged.aspectj.runtime.internal")) {
               ++syntheticCount;
            }
         }

         this.genericParameterTypes = new Type[genTypes.length - syntheticCount];

         for(int i = 0; i < this.genericParameterTypes.length; ++i) {
            if (genTypes[i] instanceof Class) {
               this.genericParameterTypes[i] = AjTypeSystem.getAjType((Class)genTypes[i]);
            } else {
               this.genericParameterTypes[i] = genTypes[i];
            }
         }
      }

      return this.genericParameterTypes;
   }

   public AjType[] getParameterTypes() {
      if (this.parameterTypes == null) {
         Class[] ptypes = this.adviceMethod.getParameterTypes();
         int syntheticCount = 0;
         Class[] arr$ = ptypes;
         int len$ = ptypes.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Class c = arr$[i$];
            if (c.getPackage().getName().equals("com.bea.core.repackaged.aspectj.runtime.internal")) {
               ++syntheticCount;
            }
         }

         this.parameterTypes = new AjType[ptypes.length - syntheticCount];

         for(int i = 0; i < this.parameterTypes.length; ++i) {
            this.parameterTypes[i] = AjTypeSystem.getAjType(ptypes[i]);
         }
      }

      return this.parameterTypes;
   }

   public AjType[] getExceptionTypes() {
      if (this.exceptionTypes == null) {
         Class[] exTypes = this.adviceMethod.getExceptionTypes();
         this.exceptionTypes = new AjType[exTypes.length];

         for(int i = 0; i < exTypes.length; ++i) {
            this.exceptionTypes[i] = AjTypeSystem.getAjType(exTypes[i]);
         }
      }

      return this.exceptionTypes;
   }

   public AdviceKind getKind() {
      return this.kind;
   }

   public String getName() {
      String adviceName = this.adviceMethod.getName();
      if (adviceName.startsWith("ajc$")) {
         adviceName = "";
         AdviceName name = (AdviceName)this.adviceMethod.getAnnotation(AdviceName.class);
         if (name != null) {
            adviceName = name.value();
         }
      }

      return adviceName;
   }

   public PointcutExpression getPointcutExpression() {
      return this.pointcutExpression;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.getName().length() > 0) {
         sb.append("@AdviceName(\"");
         sb.append(this.getName());
         sb.append("\") ");
      }

      if (this.getKind() == AdviceKind.AROUND) {
         sb.append(this.adviceMethod.getGenericReturnType().toString());
         sb.append(" ");
      }

      switch (this.getKind()) {
         case AFTER:
            sb.append("after(");
            break;
         case AFTER_RETURNING:
            sb.append("after(");
            break;
         case AFTER_THROWING:
            sb.append("after(");
            break;
         case AROUND:
            sb.append("around(");
            break;
         case BEFORE:
            sb.append("before(");
      }

      AjType[] ptypes = this.getParameterTypes();
      int len = ptypes.length;
      if (this.hasExtraParam) {
         --len;
      }

      for(int i = 0; i < len; ++i) {
         sb.append(ptypes[i].getName());
         if (i + 1 < len) {
            sb.append(",");
         }
      }

      sb.append(") ");
      switch (this.getKind()) {
         case AFTER_RETURNING:
            sb.append("returning");
            if (this.hasExtraParam) {
               sb.append("(");
               sb.append(ptypes[len - 1].getName());
               sb.append(") ");
            }
         case AFTER_THROWING:
            sb.append("throwing");
            if (this.hasExtraParam) {
               sb.append("(");
               sb.append(ptypes[len - 1].getName());
               sb.append(") ");
            }
      }

      AjType[] exTypes = this.getExceptionTypes();
      if (exTypes.length > 0) {
         sb.append("throws ");

         for(int i = 0; i < exTypes.length; ++i) {
            sb.append(exTypes[i].getName());
            if (i + 1 < exTypes.length) {
               sb.append(",");
            }
         }

         sb.append(" ");
      }

      sb.append(": ");
      sb.append(this.getPointcutExpression().asString());
      return sb.toString();
   }
}
