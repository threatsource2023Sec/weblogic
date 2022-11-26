package com.bea.security.providers.xacml.entitlement.parser;

public class Complement extends UnaryOp {
   Complement() {
   }

   public Complement(Expression op) {
      super(op);
   }

   protected char getPersistantTypeId() {
      return '~';
   }
}
