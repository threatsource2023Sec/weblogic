package kodo.profile;

import java.io.ObjectStreamException;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.ProxyCollections;
import org.apache.openjpa.util.ProxyMaps;

public class ProfilingProxies {
   public static Iterator iterator(final ProfilingProxy proxy, final Iterator itr) {
      return (Iterator)(itr instanceof ProfilingProxyIterator ? itr : new ProfilingProxyIterator() {
         public boolean hasNext() {
            boolean hasnext = itr.hasNext();
            if (proxy.getStats() != null && !hasnext) {
               proxy.getStats().setSize(proxy.size());
            }

            return hasnext;
         }

         public Object next() {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementAccessed();
            }

            return itr.next();
         }

         public void remove() {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementRemoveCalled();
            }

            itr.remove();
         }
      });
   }

   public static ListIterator listIterator(final ProfilingProxy proxy, final ListIterator itr) {
      return (ListIterator)(itr instanceof ProfilingProxyListIterator ? itr : new ProfilingProxyListIterator() {
         public boolean hasNext() {
            boolean hasnext = itr.hasNext();
            if (proxy.getStats() != null && !hasnext) {
               proxy.getStats().setSize(proxy.size());
            }

            return hasnext;
         }

         public int nextIndex() {
            return itr.nextIndex();
         }

         public Object next() {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementAccessed();
            }

            return itr.next();
         }

         public boolean hasPrevious() {
            return itr.hasPrevious();
         }

         public int previousIndex() {
            return itr.previousIndex();
         }

         public Object previous() {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementAccessed();
            }

            return itr.previous();
         }

         public void set(Object o) {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementSetCalled();
            }

            itr.set(o);
         }

         public void add(Object o) {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementAddCalled();
            }

            itr.add(o);
         }

         public void remove() {
            if (proxy.getStats() != null) {
               proxy.getStats().incrementRemoveCalled();
            }

            itr.remove();
         }
      });
   }

   public static Set entrySet(ProfilingProxy proxy, Set set) {
      if (!(set instanceof ProxyMaps.ProxyEntrySet)) {
         throw new InternalException(set.getClass().getName());
      } else {
         return new ProfilingProxyEntrySet(proxy, (ProxyMaps.ProxyEntrySet)set);
      }
   }

   public interface ProfilingProxyListIterator extends ProfilingProxyIterator, ProxyCollections.ProxyListIterator {
   }

   public interface ProfilingProxyIterator extends ProxyCollections.ProxyIterator {
   }

   private static class ProfilingProxyEntrySet extends AbstractSet implements ProxyMaps.ProxyEntrySet {
      private final ProfilingProxy _proxy;
      private final ProxyMaps.ProxyEntrySet _set;

      public ProfilingProxyEntrySet(ProfilingProxy proxy, ProxyMaps.ProxyEntrySet set) {
         this._proxy = proxy;
         this._set = set;
      }

      public void setView(int view) {
         this._set.setView(view);
      }

      public int size() {
         int size = this._set.size();
         if (this._proxy.getStats() != null) {
            this._proxy.getStats().setSize(size);
            this._proxy.getStats().setSizeCalled();
         }

         return size;
      }

      public boolean remove(Object o) {
         if (this._proxy.getStats() != null) {
            this._proxy.getStats().incrementRemoveCalled();
         }

         return this._set.remove(o);
      }

      public Iterator iterator() {
         final Iterator itr = this._set.iterator();
         return new Iterator() {
            public boolean hasNext() {
               boolean hasnext = itr.hasNext();
               if (ProfilingProxyEntrySet.this._proxy.getStats() != null && !hasnext) {
                  ProfilingProxyEntrySet.this._proxy.getStats().setSize(ProfilingProxyEntrySet.this._proxy.size());
               }

               return hasnext;
            }

            public Object next() {
               if (ProfilingProxyEntrySet.this._proxy.getStats() != null) {
                  ProfilingProxyEntrySet.this._proxy.getStats().incrementAccessed();
               }

               return itr.next();
            }

            public void remove() {
               if (ProfilingProxyEntrySet.this._proxy.getStats() != null) {
                  ProfilingProxyEntrySet.this._proxy.getStats().incrementRemoveCalled();
               }

               itr.remove();
            }
         };
      }

      protected Object writeReplace() throws ObjectStreamException {
         return this._set;
      }
   }
}
