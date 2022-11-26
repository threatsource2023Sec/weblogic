package com.bea.common.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;

public class AttributeValue extends PolicySchemaObject implements Expression {
   private static final long serialVersionUID = 8454151321995109714L;
   private com.bea.common.security.xacml.attr.AttributeValue av;

   public AttributeValue(com.bea.common.security.xacml.attr.AttributeValue av) {
      this.av = av;
   }

   public String getElementName() {
      return "AttributeValue";
   }

   public void encodeAttributes(PrintStream ps) {
      this.encodeDataType(ps);
   }

   public boolean hasBody() {
      return true;
   }

   public void encodeBody(PrintStream ps) {
      this.encodeValue(ps);
   }

   void encodeDataType(PrintStream ps) {
      if (this.av != null) {
         ps.print(" DataType=\"");

         try {
            ps.print(this.av.getType().getDataType().toString());
         } catch (URISyntaxException var3) {
         }

         ps.print("\"");
      }

   }

   protected void encodeValue(PrintStream ps) {
      if (this.av != null) {
         this.av.encodeValue(ps);
      }

   }

   public URI getDataType() throws URISyntaxException {
      return this.av.getDataType();
   }

   public com.bea.common.security.xacml.attr.AttributeValue getValue() {
      return this.av;
   }

   public boolean equals(Object other) {
      if (!(other instanceof AttributeValue)) {
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
