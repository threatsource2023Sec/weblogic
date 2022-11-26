package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.lang.reflect.PointcutExpression;

public class DeclareErrorOrWarningImpl implements DeclareErrorOrWarning {
   private PointcutExpression pc;
   private String msg;
   private boolean isError;
   private AjType declaringType;

   public DeclareErrorOrWarningImpl(String pointcut, String message, boolean isError, AjType decType) {
      this.pc = new PointcutExpressionImpl(pointcut);
      this.msg = message;
      this.isError = isError;
      this.declaringType = decType;
   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public PointcutExpression getPointcutExpression() {
      return this.pc;
   }

   public String getMessage() {
      return this.msg;
   }

   public boolean isError() {
      return this.isError;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("declare ");
      sb.append(this.isError() ? "error : " : "warning : ");
      sb.append(this.getPointcutExpression().asString());
      sb.append(" : ");
      sb.append("\"");
      sb.append(this.getMessage());
      sb.append("\"");
      return sb.toString();
   }
}
