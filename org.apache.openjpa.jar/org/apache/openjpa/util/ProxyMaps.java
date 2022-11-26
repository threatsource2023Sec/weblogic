package org.apache.openjpa.util;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ProxyMaps extends Proxies {
   public static void beforeClear(ProxyMap map) {
      dirty(map, true);
      Iterator itr = map.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         removed(map, entry.getKey(), true);
         removed(map, entry.getValue(), false);
      }

   }

   public static Set keySet(ProxyMap map) {
      ProxyEntrySet entries = (ProxyEntrySet)map.entrySet();
      entries.setView(0);
      return entries;
   }

   public static Collection values(ProxyMap map) {
      ProxyEntrySet entries = (ProxyEntrySet)map.entrySet();
      entries.setView(1);
      return entries;
   }

   public static Set afterEntrySet(ProxyMap map, Set entries) {
      return new ProxyEntrySetImpl(map, entries);
   }

   public static boolean beforePut(ProxyMap map, Object key, Object value) {
      assertAllowedType(key, map.getKeyType());
      assertAllowedType(value, map.getValueType());
      dirty(map, false);
      return map.containsKey(key);
   }

   public static Object afterPut(ProxyMap map, Object key, Object value, Object ret, boolean before) {
      if (before) {
         if (map.getChangeTracker() != null) {
            ((MapChangeTracker)map.getChangeTracker()).changed(key, ret, value);
         }

         removed(map, ret, false);
      } else if (map.getChangeTracker() != null) {
         ((MapChangeTracker)map.getChangeTracker()).added(key, value);
      }

      return ret;
   }

   public static boolean beforeSetProperty(ProxyMap map, String key, String value) {
      return beforePut(map, key, value);
   }

   public static Object afterSetProperty(ProxyMap map, String key, String value, Object ret, boolean before) {
      return afterPut(map, key, value, ret, before);
   }

   public static void beforeLoad(ProxyMap map, InputStream in) {
      dirty(map, true);
   }

   public static void beforeLoadFromXML(ProxyMap map, InputStream in) {
      dirty(map, true);
   }

   public static void putAll(ProxyMap map, Map values) {
      Iterator itr = values.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         map.put(entry.getKey(), entry.getValue());
      }

   }

   public static boolean beforeRemove(ProxyMap map, Object key) {
      dirty(map, false);
      return map.containsKey(key);
   }

   public static Object afterRemove(ProxyMap map, Object key, Object ret, boolean before) {
      if (before) {
         if (map.getChangeTracker() != null) {
            ((MapChangeTracker)map.getChangeTracker()).removed(key, ret);
         }

         removed(map, key, true);
         removed(map, ret, false);
      }

      return ret;
   }

   private static class ProxyEntrySetImpl extends AbstractSet implements ProxyEntrySet {
      private final ProxyMap _map;
      private final Set _entries;
      private int _view = 2;

      public ProxyEntrySetImpl(ProxyMap map, Set entries) {
         this._map = map;
         this._entries = entries;
      }

      public int getView() {
         return this._view;
      }

      public void setView(int view) {
         this._view = view;
      }

      public int size() {
         return this._entries.size();
      }

      public boolean remove(Object o) {
         if (this._view != 0) {
            throw new UnsupportedOperationException();
         } else if (!this._map.containsKey(o)) {
            return false;
         } else {
            this._map.remove(o);
            return true;
         }
      }

      public Iterator iterator() {
         final Iterator itr = this._entries.iterator();
         return new Iterator() {
            private Map.Entry _last = null;

            public boolean hasNext() {
               return itr.hasNext();
            }

            public Object next() {
               // $FF: Couldn't be decompiled
            }

            public void remove() {
               Proxies.dirty(ProxyEntrySetImpl.this._map, false);
               itr.remove();
               if (ProxyEntrySetImpl.this._map.getChangeTracker() != null) {
                  ((MapChangeTracker)ProxyEntrySetImpl.this._map.getChangeTracker()).removed(this._last.getKey(), this._last.getValue());
               }

               Proxies.removed(ProxyEntrySetImpl.this._map, this._last.getKey(), true);
               Proxies.removed(ProxyEntrySetImpl.this._map, this._last.getValue(), false);
            }
         };
      }

      protected Object writeReplace() throws ObjectStreamException {
         switch (this._view) {
            case 0:
               return this._map.keySet();
            case 1:
               return this._map.values();
            default:
               return this._map.entrySet();
         }
      }

      // $FF: synthetic method
      static int access$000(ProxyEntrySetImpl x0) {
         return x0._view;
      }
   }

   public interface ProxyEntrySet extends Set {
      int VIEW_KEYS = 0;
      int VIEW_VALUES = 1;
      int VIEW_ENTRIES = 2;

      void setView(int var1);
   }
}
