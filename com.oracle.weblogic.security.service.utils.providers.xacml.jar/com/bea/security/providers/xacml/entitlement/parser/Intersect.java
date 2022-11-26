package com.bea.security.providers.xacml.entitlement.parser;

public class Intersect extends BinaryOp {
   Intersect(Expression left) {
      super(left);
   }

   public Intersect(Expression left, Expression right) {
      super(left, right);
   }

   protected char getPersistantTypeId() {
      return '&';
   }
}
