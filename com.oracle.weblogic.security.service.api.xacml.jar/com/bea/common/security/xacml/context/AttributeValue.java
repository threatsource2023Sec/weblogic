package com.bea.common.security.xacml.context;

import java.io.PrintStream;

public class AttributeValue extends ContextSchemaObject {
   private static final long serialVersionUID = 4804597100991398558L;
   private com.bea.common.security.xacml.attr.AttributeValue av;

   public AttributeValue(com.bea.common.security.xacml.attr.AttributeValue av) {
      this.av = av;
   }

   public String getElementName() {
      return "AttributeValue";
   }

   public boolean hasBody() {
      return true;
   }

   public void encodeBody(PrintStream ps) {
      this.encodeValue(ps);
   }

   protected void encodeValue(PrintStream ps) {
      if (this.av != null) {
         this.av.encodeValue(ps);
      }

   }

   public com.bea.common.security.xacml.attr.AttributeValue getValue() {
      return this.av;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AttributeValue)) {
         return false;
      } else {
         AttributeValue o = (AttributeValue)other;
         return this.av == o.av || this.av != null && this.av.equals(o.av);
      }
   }

   public int internalHashCode() {
      return this.av.hashCode();
   }
}
