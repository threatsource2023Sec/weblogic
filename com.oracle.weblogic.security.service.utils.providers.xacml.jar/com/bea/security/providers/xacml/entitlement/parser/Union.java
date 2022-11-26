package com.bea.security.providers.xacml.entitlement.parser;

public class Union extends BinaryOp {
   Union(Expression left) {
      super(left);
   }

   public Union(Expression left, Expression right) {
      super(left, right);
   }

   protected char getPersistantTypeId() {
      return '|';
   }
}
