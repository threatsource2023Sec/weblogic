package com.bea.security.providers.xacml.entitlement.parser;

public class Difference extends BinaryOp {
   Difference(Expression left) {
      super(left);
   }

   public Difference(Expression left, Expression right) {
      super(left, right);
   }

   protected char getPersistantTypeId() {
      return '-';
   }
}
