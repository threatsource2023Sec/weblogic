package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class AnyURIAttribute extends AttributeValue {
   private URI value;

   public AnyURIAttribute(URI value) {
      this.value = value;
   }

   public Type getType() {
      return Type.ANY_URI;
   }

   public URI getValue() {
      return this.value;
   }

   public int compareTo(AnyURIAttribute attr) {
      return this.value.compareTo(attr.value);
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
      } else if (!(o instanceof AnyURIAttribute)) {
         return false;
      } else {
         AnyURIAttribute other = (AnyURIAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public boolean add(AnyURIAttribute o) {
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

         public AnyURIAttribute next() {
            this.nextNotCalled = false;
            return AnyURIAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
