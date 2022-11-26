package org.opensaml.core.xml.util;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.opensaml.core.xml.XMLObject;

class ListView extends AbstractList {
   private final IndexedXMLObjectChildrenList backingList;
   @Nonnull
   private final QName index;
   @Nonnull
   private List indexList;

   public ListView(@Nonnull IndexedXMLObjectChildrenList newBackingList, @Nonnull QName newIndex) {
      this.backingList = newBackingList;
      this.index = newIndex;
      this.indexList = this.backingList.get(this.index);
   }

   public boolean add(@Nullable XMLObject o) {
      boolean result = this.backingList.add(o);
      this.indexList = this.backingList.get(this.index);
      return result;
   }

   public void add(int newIndex, @Nullable XMLObject element) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(@Nonnull Collection c) {
      boolean result = this.backingList.addAll(c);
      this.indexList = this.backingList.get(this.index);
      return result;
   }

   public boolean addAll(int i, @Nonnull Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      LazyList copy = new LazyList();
      copy.addAll(this.indexList);
      this.backingList.removeAll(copy);
      this.indexList = this.backingList.get(this.index);
   }

   public boolean contains(Object element) {
      return this.indexList.contains(element);
   }

   public boolean containsAll(Collection c) {
      return this.indexList.containsAll(c);
   }

   public XMLObject get(int newIndex) {
      return (XMLObject)this.indexList.get(newIndex);
   }

   public int indexOf(Object o) {
      return this.indexList.indexOf(o);
   }

   public boolean isEmpty() {
      return this.indexList.isEmpty();
   }

   public int lastIndexOf(Object o) {
      return this.indexList.lastIndexOf(o);
   }

   @Nonnull
   public XMLObject remove(int newIndex) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(@Nullable Object o) {
      boolean result = this.backingList.remove(o);
      this.indexList = this.backingList.get(this.index);
      return result;
   }

   public boolean removeAll(Collection c) {
      boolean result = this.backingList.removeAll(c);
      this.indexList = this.backingList.get(this.index);
      return result;
   }

   public boolean retainAll(Collection c) {
      boolean result = this.backingList.retainAll(c);
      this.indexList = this.backingList.get(this.index);
      return result;
   }

   public XMLObject set(int newIndex, @Nonnull XMLObject element) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.indexList.size();
   }

   public Object[] toArray() {
      return this.indexList.toArray();
   }

   public Object[] toArray(@Nonnull Object[] a) {
      return this.indexList.toArray(a);
   }
}
