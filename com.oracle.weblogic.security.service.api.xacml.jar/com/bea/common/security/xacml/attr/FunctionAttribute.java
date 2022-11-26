package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class FunctionAttribute extends AttributeValue {
   private URI value;

   public FunctionAttribute(URI value) {
      this.value = value;
   }

   public Type getType() {
      return Type.FUNCTION;
   }

   public URI getValue() {
      return this.value;
   }

   public void encode(Map nsMap, OutputStream out) {
      throw new UnsupportedOperationException();
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getValue());
   }

   public String toString() {
      return this.value.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof FunctionAttribute)) {
         return false;
      } else {
         FunctionAttribute other = (FunctionAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public int compareTo(FunctionAttribute other) {
      return this.value.compareTo(other.value);
   }

   public boolean add(FunctionAttribute o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      return new Iterator() {
         boolean nextNotCalled = true;

         public boolean hasNext() {
            return this.nextNotCalled;
         }

         public FunctionAttribute next() {
            this.nextNotCalled = false;
            return FunctionAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
