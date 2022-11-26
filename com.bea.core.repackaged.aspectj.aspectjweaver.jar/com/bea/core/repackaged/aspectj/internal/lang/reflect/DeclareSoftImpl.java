package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareSoft;
import com.bea.core.repackaged.aspectj.lang.reflect.PointcutExpression;

public class DeclareSoftImpl implements DeclareSoft {
   private AjType declaringType;
   private PointcutExpression pointcut;
   private AjType exceptionType;
   private String missingTypeName;

   public DeclareSoftImpl(AjType declaringType, String pcut, String exceptionTypeName) {
      this.declaringType = declaringType;
      this.pointcut = new PointcutExpressionImpl(pcut);

      try {
         ClassLoader cl = declaringType.getJavaClass().getClassLoader();
         this.exceptionType = AjTypeSystem.getAjType(Class.forName(exceptionTypeName, false, cl));
      } catch (ClassNotFoundException var5) {
         this.missingTypeName = exceptionTypeName;
      }

   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public AjType getSoftenedExceptionType() throws ClassNotFoundException {
      if (this.missingTypeName != null) {
         throw new ClassNotFoundException(this.missingTypeName);
      } else {
         return this.exceptionType;
      }
   }

   public PointcutExpression getPointcutExpression() {
      return this.pointcut;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("declare soft : ");
      if (this.missingTypeName != null) {
         sb.append(this.exceptionType.getName());
      } else {
         sb.append(this.missingTypeName);
      }

      sb.append(" : ");
      sb.append(this.getPointcutExpression().asString());
      return sb.toString();
   }
}
