package com.bea.common.security.xacml.attr;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class RFC822NameAttribute extends AttributeValue {
   private String value;
   private String localPart;
   private String domainPart;

   public RFC822NameAttribute(String value) throws InvalidAttributeException {
      this.value = value;
      int idx = value.indexOf(64);
      if (idx > 0 && idx < value.length()) {
         this.localPart = value.substring(0, idx);
         this.domainPart = value.substring(idx + 1);
      } else {
         throw new InvalidAttributeException(ApiLogger.getInvalidRFC822Name(value));
      }
   }

   public Type getType() {
      return Type.RFC822_NAME;
   }

   public String getValue() {
      return this.value;
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getValue());
   }

   public String toString() {
      return this.value;
   }

   public String getLocalPart() {
      return this.localPart;
   }

   public String getDomainPart() {
      return this.domainPart;
   }

   public int compareTo(RFC822NameAttribute other) {
      int comp = this.domainPart.compareToIgnoreCase(other.domainPart);
      return comp != 0 ? comp : this.localPart.compareTo(other.localPart);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof RFC822NameAttribute)) {
         return false;
      } else {
         RFC822NameAttribute other = (RFC822NameAttribute)o;
         return this.compareTo(other) == 0;
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.localPart);
      result = HashCodeUtil.hash(result, this.domainPart.toLowerCase());
      return result;
   }

   public boolean add(RFC822NameAttribute o) {
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

         public RFC822NameAttribute next() {
            this.nextNotCalled = false;
            return RFC822NameAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
