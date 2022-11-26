package com.bea.common.security.xacml.attr;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.Type;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GenericBag extends Bag {
   private Collection contents;

   protected GenericBag() {
      this((Collection)(new ArrayList()));
   }

   protected GenericBag(Collection contents) {
      this.type = this.getType();
      this.contents = contents;
   }

   public GenericBag(Type type) {
      this(type, new ArrayList());
   }

   public GenericBag(Type type, Collection contents) {
      super(getBagType(type));
      this.contents = contents;
   }

   public void encodeValue(PrintStream ps) {
      throw new UnsupportedOperationException();
   }

   private static Type getBagType(Type t) {
      return t.isBag() ? t : new Type(t, true);
   }

   public boolean isBag() {
      return true;
   }

   public Collection getValue() {
      return this.contents;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof GenericBag)) {
         return false;
      } else {
         GenericBag other = (GenericBag)o;
         return !this.getType().equals(other.getType()) ? false : CollectionUtil.equals(this.contents, other.contents);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.getType());
      result = HashCodeUtil.hash(result, this.contents);
      return result;
   }

   public int size() {
      return this.contents.size();
   }

   public boolean isEmpty() {
      return this.contents.isEmpty();
   }

   public boolean contains(Object o) {
      return this.contents.contains(o);
   }

   public Iterator iterator() {
      return this.contents.iterator();
   }

   public Object[] toArray() {
      return this.contents.toArray();
   }

   public Object[] toArray(Object[] arg0) {
      return this.contents.toArray(arg0);
   }

   public boolean add(AttributeValue arg0) {
      return this.contents.add(arg0);
   }

   public boolean remove(Object o) {
      return this.contents.remove(o);
   }

   public boolean containsAll(Collection arg0) {
      return this.contents.containsAll(arg0);
   }

   public boolean addAll(Collection arg0) {
      return this.contents.addAll(arg0);
   }

   public boolean removeAll(Collection arg0) {
      return this.contents.removeAll(arg0);
   }

   public boolean retainAll(Collection arg0) {
      return this.contents.retainAll(arg0);
   }

   public void clear() {
      this.contents.clear();
   }
}
