package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class DNSAddressAttribute extends AttributeValue {
   private String value;

   public DNSAddressAttribute(String value) {
      this.value = value;
   }

   public Type getType() {
      return Type.DNS_ADDRESS;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      return this.value;
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getValue());
   }

   public int compareTo(DNSAddressAttribute other) {
      return this.value.compareTo(other.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DNSAddressAttribute)) {
         return false;
      } else {
         DNSAddressAttribute other = (DNSAddressAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public boolean add(DNSAddressAttribute o) {
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

         public DNSAddressAttribute next() {
            this.nextNotCalled = false;
            return DNSAddressAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
