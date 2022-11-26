package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;

public class CharacterAttribute extends AttributeValue {
   private Character value;

   public CharacterAttribute(String value) throws InvalidAttributeException {
      if (value.length() != 1) {
         throw new InvalidAttributeException("Character data-type is restricted to single character values");
      } else {
         this.value = new Character(value.charAt(0));
      }
   }

   public CharacterAttribute(Character value) {
      this.value = value;
   }

   public Type getType() {
      return Type.CHARACTER;
   }

   public Character getValue() {
      return this.value;
   }

   public int compareTo(CharacterAttribute other) {
      return this.value.compareTo(other.value);
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
      } else if (!(o instanceof CharacterAttribute)) {
         return false;
      } else {
         CharacterAttribute other = (CharacterAttribute)o;
         return this.value.equals(other.value);
      }
   }

   public int internalHashCode() {
      return this.value.hashCode();
   }

   public boolean add(CharacterAttribute o) {
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

         public CharacterAttribute next() {
            this.nextNotCalled = false;
            return CharacterAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
