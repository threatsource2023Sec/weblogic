package kodo.profile;

import com.solarmetric.profile.ProfilingAgent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kodo.kernel.KodoStoreContext;
import org.apache.openjpa.kernel.QueryImpl;
import org.apache.openjpa.lib.rop.EagerResultList;
import org.apache.openjpa.lib.rop.ResultList;

public class ProfilingResultList implements ResultList {
   private final ResultList _list;
   private KodoStoreContext _ctx;
   private QueryImpl _query;
   private boolean _containsCalled = false;
   private boolean _sizeCalled = false;
   private boolean _indexOfCalled = false;
   private int _size = -1;
   private int _accessed = 0;

   public ProfilingResultList(ResultList list, QueryImpl query, KodoStoreContext ctx) {
      this._list = list;
      this._ctx = ctx;
      this._query = query;
      if (list instanceof EagerResultList) {
         this._size = list.size();
      }

   }

   public ResultList getDelegate() {
      return this._list;
   }

   public boolean isProviderOpen() {
      return this._list.isProviderOpen();
   }

   public boolean isClosed() {
      return this._list.isClosed();
   }

   public void close() {
      this._list.close();
      ResultListSummaryInfo info = new ResultListSummaryInfo(this._query.getCandidateType().getName(), this._query.getQueryString(), this._containsCalled, this._sizeCalled, this._indexOfCalled, this._size, this._accessed);
      ProfilingAgent agent = this._ctx.getProfilingAgent();
      agent.handleEvent(new ResultListSummaryEvent(this._ctx, info));
   }

   public boolean contains(Object o) {
      this._containsCalled = true;
      return this._list.contains(o);
   }

   public boolean containsAll(Collection c) {
      this._containsCalled = true;
      return this._list.containsAll(c);
   }

   public Object get(int index) {
      ++this._accessed;
      return this._list.get(index);
   }

   public int indexOf(Object o) {
      this._indexOfCalled = true;
      return this._list.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      this._indexOfCalled = true;
      return this._list.lastIndexOf(o);
   }

   public int size() {
      this._sizeCalled = true;
      this._size = this._list.size();
      return this._size;
   }

   public boolean isEmpty() {
      return this._list.isEmpty();
   }

   public Iterator iterator() {
      return new Itr(this._list.listIterator());
   }

   public ListIterator listIterator() {
      return new Itr(this._list.listIterator());
   }

   public ListIterator listIterator(int index) {
      return new Itr(this._list.listIterator(index));
   }

   public Object[] toArray() {
      Object[] retval = this._list.toArray();
      this._size = retval.length;
      this._accessed = this._size;
      return retval;
   }

   public Object[] toArray(Object[] a) {
      Object[] retval = this._list.toArray(a);
      this._size = retval.length;
      this._accessed = this._size;
      return retval;
   }

   public Object writeReplace() {
      return this._list;
   }

   public String toString() {
      return this._list.toString();
   }

   public int hashCode() {
      return this._list.hashCode();
   }

   public boolean equals(Object other) {
      return this._list.equals(other);
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }

   public void add(int index, Object element) {
      this._list.add(index, element);
   }

   public boolean add(Object o) {
      return this._list.add(o);
   }

   public boolean addAll(Collection c) {
      return this._list.addAll(c);
   }

   public boolean addAll(int index, Collection c) {
      return this._list.addAll(index, c);
   }

   public Object remove(int index) {
      return this._list.remove(index);
   }

   public boolean remove(Object o) {
      return this._list.remove(o);
   }

   public boolean removeAll(Collection c) {
      return this._list.removeAll(c);
   }

   public boolean retainAll(Collection c) {
      return this._list.retainAll(c);
   }

   public Object set(int index, Object element) {
      return this._list.set(index, element);
   }

   public void clear() {
      this._list.clear();
   }

   public List subList(int from, int to) {
      return this._list.subList(from, to);
   }

   private class Itr implements ListIterator {
      ListIterator _iter;

      public Itr(ListIterator iter) {
         this._iter = iter;
      }

      public int nextIndex() {
         return this._iter.nextIndex();
      }

      public int previousIndex() {
         return this._iter.previousIndex();
      }

      public boolean hasNext() {
         boolean hasnext = this._iter.hasNext();
         if (!hasnext) {
            ProfilingResultList.this._size = ProfilingResultList.this._list.size();
         }

         return hasnext;
      }

      public boolean hasPrevious() {
         return this._iter.hasPrevious();
      }

      public Object previous() {
         ProfilingResultList.this._accessed++;
         return this._iter.previous();
      }

      public Object next() {
         ProfilingResultList.this._accessed++;
         return this._iter.next();
      }

      public void add(Object o) {
         this._iter.add(o);
      }

      public void remove() {
         this._iter.remove();
      }

      public void set(Object o) {
         this._iter.set(o);
      }
   }
}
