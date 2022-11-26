package org.apache.openjpa.lib.rop;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListResultList extends AbstractResultList {
   private final List _list;
   private boolean _closed = false;

   public ListResultList(List list) {
      this._list = list;
   }

   public List getDelegate() {
      return this._list;
   }

   public boolean isProviderOpen() {
      return false;
   }

   public boolean isClosed() {
      return this._closed;
   }

   public void close() {
      this._closed = true;
   }

   public boolean contains(Object o) {
      this.assertOpen();
      return this._list.contains(o);
   }

   public boolean containsAll(Collection c) {
      this.assertOpen();
      return this._list.containsAll(c);
   }

   public Object get(int index) {
      this.assertOpen();
      return this._list.get(index);
   }

   public int indexOf(Object o) {
      this.assertOpen();
      return this._list.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      this.assertOpen();
      return this._list.lastIndexOf(o);
   }

   public int size() {
      this.assertOpen();
      return this._list.size();
   }

   public boolean isEmpty() {
      this.assertOpen();
      return this._list.isEmpty();
   }

   public Iterator iterator() {
      return this.listIterator();
   }

   public ListIterator listIterator() {
      return new ResultListIterator(this._list.listIterator(), this);
   }

   public ListIterator listIterator(int index) {
      return new ResultListIterator(this._list.listIterator(index), this);
   }

   public Object[] toArray() {
      this.assertOpen();
      return this._list.toArray();
   }

   public Object[] toArray(Object[] a) {
      this.assertOpen();
      return this._list.toArray(a);
   }

   public Object writeReplace() {
      return this._list;
   }
}
