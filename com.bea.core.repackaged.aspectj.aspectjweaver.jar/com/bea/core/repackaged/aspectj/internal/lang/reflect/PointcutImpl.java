package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.Pointcut;
import com.bea.core.repackaged.aspectj.lang.reflect.PointcutExpression;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class PointcutImpl implements Pointcut {
   private final String name;
   private final PointcutExpression pc;
   private final Method baseMethod;
   private final AjType declaringType;
   private String[] parameterNames = new String[0];

   protected PointcutImpl(String name, String pc, Method method, AjType declaringType, String pNames) {
      this.name = name;
      this.pc = new PointcutExpressionImpl(pc);
      this.baseMethod = method;
      this.declaringType = declaringType;
      this.parameterNames = this.splitOnComma(pNames);
   }

   public PointcutExpression getPointcutExpression() {
      return this.pc;
   }

   public String getName() {
      return this.name;
   }

   public int getModifiers() {
      return this.baseMethod.getModifiers();
   }

   public AjType[] getParameterTypes() {
      Class[] baseParamTypes = this.baseMethod.getParameterTypes();
      AjType[] ajParamTypes = new AjType[baseParamTypes.length];

      for(int i = 0; i < ajParamTypes.length; ++i) {
         ajParamTypes[i] = AjTypeSystem.getAjType(baseParamTypes[i]);
      }

      return ajParamTypes;
   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public String[] getParameterNames() {
      return this.parameterNames;
   }

   private String[] splitOnComma(String s) {
      StringTokenizer strTok = new StringTokenizer(s, ",");
      String[] ret = new String[strTok.countTokens()];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = strTok.nextToken().trim();
      }

      return ret;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getName());
      sb.append("(");
      AjType[] ptypes = this.getParameterTypes();

      for(int i = 0; i < ptypes.length; ++i) {
         sb.append(ptypes[i].getName());
         if (this.parameterNames != null && this.parameterNames[i] != null) {
            sb.append(" ");
            sb.append(this.parameterNames[i]);
         }

         if (i + 1 < ptypes.length) {
            sb.append(",");
         }
      }

      sb.append(") : ");
      sb.append(this.getPointcutExpression().asString());
      return sb.toString();
   }
}
