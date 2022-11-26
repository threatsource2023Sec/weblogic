package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public abstract class AttributeValue extends Bag implements Serializable, Comparable {
   protected AttributeValue() {
   }

   public AttributeValue(URI dataType) {
      super(dataType);
   }

   public AttributeValue(Type type) {
      super(type);
   }

   public abstract void encodeValue(PrintStream var1);

   public abstract Object getValue();

   public boolean isBag() {
      return false;
   }

   public Object[] toArray() {
      Object[] a = (Object[])((Object[])Array.newInstance(this.getClass().getComponentType(), 1));
      a[0] = this;
      return a;
   }

   public Object[] toArray(Object[] a) {
      if (a.length < 1) {
         a = (Object[])((Object[])Array.newInstance(a.getClass().getComponentType(), 1));
      }

      a[0] = this;
      return a;
   }

   public int size() {
      return 1;
   }

   public boolean isEmpty() {
      return false;
   }

   public boolean contains(Object value) {
      return this.equals(value);
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean containsAll(Collection arg0) {
      if (arg0.size() == 0) {
         return true;
      } else {
         Iterator it = arg0.iterator();
         return it.hasNext() && this.equals(it.next()) && !it.hasNext();
      }
   }

   public boolean removeAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeInt(this.hash);
      stream.writeObject(this.dataType);
      stream.writeObject(this.type);
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.hash = stream.readInt();
      this.dataType = (URI)stream.readObject();
      this.type = (Type)stream.readObject();
   }
}
