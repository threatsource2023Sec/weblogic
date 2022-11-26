package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class JavaClassAttribute extends AttributeValue {
   private Class value;

   public JavaClassAttribute(Class value) {
      this.value = value;
   }

   public Type getType() {
      return Type.CLASS;
   }

   public Class getValue() {
      return this.value;
   }

   public String toString() {
      return this.value.getName();
   }

   public void encodeValue(PrintStream ps) {
      throw new UnsupportedOperationException();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof JavaClassAttribute)) {
         return false;
      } else {
         JavaClassAttribute other = (JavaClassAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public int compareTo(JavaClassAttribute other) {
      return this.value.getName().compareTo(other.value.getName());
   }

   public boolean add(JavaClassAttribute o) {
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

         public JavaClassAttribute next() {
            this.nextNotCalled = false;
            return JavaClassAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
