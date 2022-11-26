package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class JavaObjectAttribute extends AttributeValue {
   private transient Object value;

   public JavaObjectAttribute(Object value) {
      this.value = value;
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return this.value != null ? this.value.toString() : "null";
   }

   public void encodeValue(PrintStream ps) {
      throw new UnsupportedOperationException();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof JavaObjectAttribute)) {
         return false;
      } else {
         JavaObjectAttribute other = (JavaObjectAttribute)o;
         return this.value == other.value || this.value != null && this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value != null ? this.value.hashCode() : 0;
   }

   public int compareTo(JavaObjectAttribute other) {
      if (this.value == other.value) {
         return 0;
      } else if (this.value == null) {
         return -1;
      } else if (this.value instanceof Comparable) {
         return ((Comparable)this.value).compareTo(other.value);
      } else if (this.value.equals(other.value)) {
         return 0;
      } else {
         int result = System.identityHashCode(this.value) - System.identityHashCode(other.value);
         if (result == 0) {
            return other.value == null ? 1 : this.value.toString().compareTo(other.value.toString());
         } else {
            return result;
         }
      }
   }

   public boolean add(JavaObjectAttribute o) {
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

         public JavaObjectAttribute next() {
            this.nextNotCalled = false;
            return JavaObjectAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
