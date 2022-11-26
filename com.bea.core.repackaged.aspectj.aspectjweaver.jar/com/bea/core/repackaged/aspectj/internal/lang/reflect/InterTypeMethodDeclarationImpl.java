package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.InterTypeMethodDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class InterTypeMethodDeclarationImpl extends InterTypeDeclarationImpl implements InterTypeMethodDeclaration {
   private String name;
   private Method baseMethod;
   private int parameterAdjustmentFactor = 1;
   private AjType[] parameterTypes;
   private Type[] genericParameterTypes;
   private AjType returnType;
   private Type genericReturnType;
   private AjType[] exceptionTypes;

   public InterTypeMethodDeclarationImpl(AjType decType, String target, int mods, String name, Method itdInterMethod) {
      super(decType, target, mods);
      this.name = name;
      this.baseMethod = itdInterMethod;
   }

   public InterTypeMethodDeclarationImpl(AjType decType, AjType targetType, Method base, int modifiers) {
      super(decType, targetType, modifiers);
      this.parameterAdjustmentFactor = 0;
      this.name = base.getName();
      this.baseMethod = base;
   }

   public String getName() {
      return this.name;
   }

   public AjType getReturnType() {
      return AjTypeSystem.getAjType(this.baseMethod.getReturnType());
   }

   public Type getGenericReturnType() {
      Type gRet = this.baseMethod.getGenericReturnType();
      return (Type)(gRet instanceof Class ? AjTypeSystem.getAjType((Class)gRet) : gRet);
   }

   public AjType[] getParameterTypes() {
      Class[] baseTypes = this.baseMethod.getParameterTypes();
      AjType[] ret = new AjType[baseTypes.length - this.parameterAdjustmentFactor];

      for(int i = this.parameterAdjustmentFactor; i < baseTypes.length; ++i) {
         ret[i - this.parameterAdjustmentFactor] = AjTypeSystem.getAjType(baseTypes[i]);
      }

      return ret;
   }

   public Type[] getGenericParameterTypes() {
      Type[] baseTypes = this.baseMethod.getGenericParameterTypes();
      Type[] ret = new AjType[baseTypes.length - this.parameterAdjustmentFactor];

      for(int i = this.parameterAdjustmentFactor; i < baseTypes.length; ++i) {
         if (baseTypes[i] instanceof Class) {
            ret[i - this.parameterAdjustmentFactor] = AjTypeSystem.getAjType((Class)baseTypes[i]);
         } else {
            ret[i - this.parameterAdjustmentFactor] = baseTypes[i];
         }
      }

      return ret;
   }

   public TypeVariable[] getTypeParameters() {
      return this.baseMethod.getTypeParameters();
   }

   public AjType[] getExceptionTypes() {
      Class[] baseTypes = this.baseMethod.getExceptionTypes();
      AjType[] ret = new AjType[baseTypes.length];

      for(int i = 0; i < baseTypes.length; ++i) {
         ret[i] = AjTypeSystem.getAjType(baseTypes[i]);
      }

      return ret;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(Modifier.toString(this.getModifiers()));
      sb.append(" ");
      sb.append(this.getReturnType().toString());
      sb.append(" ");
      sb.append(this.targetTypeName);
      sb.append(".");
      sb.append(this.getName());
      sb.append("(");
      AjType[] pTypes = this.getParameterTypes();

      for(int i = 0; i < pTypes.length - 1; ++i) {
         sb.append(pTypes[i].toString());
         sb.append(", ");
      }

      if (pTypes.length > 0) {
         sb.append(pTypes[pTypes.length - 1].toString());
      }

      sb.append(")");
      return sb.toString();
   }
}
