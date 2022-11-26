package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;

public class UnsupportedPointcutPrimitiveException extends RuntimeException {
   private static final long serialVersionUID = 3258689888517043251L;
   private PointcutPrimitive unsupportedPrimitive;
   private String pointcutExpression;

   public UnsupportedPointcutPrimitiveException(String pcExpression, PointcutPrimitive primitive) {
      super(WeaverMessages.format("unsupportedPointcutPrimitive", pcExpression, primitive.getName()));
      this.pointcutExpression = pcExpression;
      this.unsupportedPrimitive = primitive;
   }

   public PointcutPrimitive getUnsupportedPrimitive() {
      return this.unsupportedPrimitive;
   }

   public String getInvalidPointcutExpression() {
      return this.pointcutExpression;
   }
}
