package com.bea.common.security.xacml.attr;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.utils.Pair;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class X500NameAttribute extends AttributeValue {
   private final String value;
   private transient List components;

   public X500NameAttribute(String value) throws InvalidAttributeException {
      this.value = value;
      this.calculateComponents();
   }

   private void calculateComponents() throws InvalidAttributeException {
      this.components = new ArrayList();
      StringTokenizer st = new StringTokenizer(this.value, ",");

      while(st.hasMoreTokens()) {
         String comp = st.nextToken().trim();
         if (comp != null && comp.length() != 0) {
            int idx = comp.indexOf(61);
            if (idx > 0 && idx < comp.length() - 1) {
               this.components.add(new Pair(comp.substring(0, idx).toLowerCase(), comp.substring(idx + 1)));
               continue;
            }

            throw new InvalidAttributeException("Illegal X500 value: " + this.value);
         }

         throw new InvalidAttributeException(ApiLogger.getIllegalX500Value(this.value));
      }

   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();

      try {
         this.calculateComponents();
      } catch (InvalidAttributeException var3) {
      }

   }

   public Type getType() {
      return Type.X500_NAME;
   }

   public String getValue() {
      return this.value;
   }

   public List getComponents() {
      return this.components;
   }

   public String toString() {
      return this.value;
   }

   public void encodeValue(PrintStream ps) {
      ps.print(this.getValue());
   }

   public int compareTo(X500NameAttribute other) {
      ListIterator lt = this.components.listIterator(this.components.size());
      ListIterator lo = other.components.listIterator(other.components.size());

      int comp;
      do {
         if (!lt.hasPrevious()) {
            return lo.hasPrevious() ? -1 : 0;
         }

         if (!lo.hasPrevious()) {
            return 1;
         }

         Pair pt = (Pair)lt.previous();
         Pair po = (Pair)lo.previous();
         comp = ((String)pt.getLeft()).compareTo((String)po.getLeft());
         if (comp != 0) {
            return comp;
         }

         comp = ((String)pt.getRight()).compareTo((String)po.getRight());
      } while(comp == 0);

      return comp;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof X500NameAttribute)) {
         return false;
      } else {
         X500NameAttribute other = (X500NameAttribute)o;
         return this.compareTo(other) == 0;
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.components);
      return result;
   }

   public boolean add(X500NameAttribute o) {
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

         public X500NameAttribute next() {
            this.nextNotCalled = false;
            return X500NameAttribute.this;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }
}
