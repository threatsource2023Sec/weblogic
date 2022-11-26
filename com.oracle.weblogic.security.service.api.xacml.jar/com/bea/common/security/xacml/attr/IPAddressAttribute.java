package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class IPAddressAttribute extends AttributeValue {
   private String value;

   public IPAddressAttribute(String value) throws InvalidAttributeException {
      this.value = value;
   }

   public Type getType() {
      return Type.IP_ADDRESS;
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

   public int compareTo(IPAddressAttribute other) {
      return this.value.compareTo(other.value);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof IPAddressAttribute)) {
         return false;
      } else {
         IPAddressAttribute other = (IPAddressAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public boolean add(IPAddressAttribute o) {
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

         public IPAddressAttribute next() {
            this.nextNotCalled = false;
            return IPAddressAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
