package weblogic.cache.session;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import weblogic.cache.CacheEntry;
import weblogic.cache.CacheMap;
import weblogic.cache.util.DelegatingCacheMap;

public abstract class AbstractWorkspaceMapAdapter extends DelegatingCacheMap {
   public AbstractWorkspaceMapAdapter(CacheMap delegate) {
      super(delegate);
   }

   protected abstract Workspace getWorkspace();

   public Object get(Object key) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdGet(key, (Workspace)null);
      } else {
         Object ret = workspace.get(key);
         if (workspace.wasRemoved()) {
            return null;
         } else {
            return ret == null && !workspace.wasNull() && !workspace.isCleared() ? this.getReadValueMiss(key, workspace) : ret;
         }
      }
   }

   protected Object stdGet(Object key, Workspace workspace) {
      return super.get(key);
   }

   protected Object getReadValueMiss(Object key, Workspace workspace) {
      return super.get(key);
   }

   protected CacheEntry getReadMiss(Object key, Workspace workspace) {
      return super.getEntry(key);
   }

   protected CacheEntry getWriteMiss(Object key, Workspace workspace) {
      return super.getEntry(key);
   }

   public Object put(Object key, Object val) {
      Workspace workspace = this.getWorkspace();
      return workspace == null ? this.stdPut(key, val, (Workspace)null) : this.put(key, val, workspace);
   }

   protected Object stdPut(Object key, Object val, Workspace workspace) {
      return super.put(key, val);
   }

   private Object put(Object key, Object val, Workspace workspace) {
      Object prev = workspace.get(key);
      boolean isnew = prev == null && !workspace.wasNull();
      if (isnew && !workspace.isCleared()) {
         Map.Entry entry = this.getWriteMiss(key, workspace);
         if (entry != null) {
            prev = entry.getValue();
            isnew = false;
         }
      }

      if (isnew) {
         workspace.put(key, val);
      } else {
         workspace.put(key, val, prev);
      }

      return workspace.wasRemoved() ? null : prev;
   }

   public Object remove(Object key) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdRemove(key, workspace);
      } else {
         Object prev = workspace.get(key);
         if (workspace.wasRemoved()) {
            return null;
         } else {
            boolean isnew = prev == null && !workspace.wasNull();
            if (isnew && !workspace.isCleared()) {
               Map.Entry entry = this.getWriteMiss(key, workspace);
               if (entry != null) {
                  prev = entry.getValue();
                  isnew = false;
               }
            }

            if (!isnew) {
               workspace.remove(key, prev);
            }

            return prev;
         }
      }
   }

   protected Object stdRemove(Object key, Workspace workspace) {
      return super.remove(key);
   }

   public void putAll(Map map) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         this.stdPutAll(map, (Workspace)null);
      } else {
         Iterator itr = map.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            this.put(entry.getKey(), entry.getValue(), workspace);
         }
      }

   }

   protected void stdPutAll(Map map, Workspace workspace) {
      super.putAll(map);
   }

   public void clear() {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         this.stdClear((Workspace)null);
      } else {
         workspace.clear();
      }

   }

   protected void stdClear(Workspace workspace) {
      super.clear();
   }

   public CacheEntry getEntry(Object key) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdGetEntry(key, (Workspace)null);
      } else {
         Object val = workspace.get(key);
         if (workspace.wasRemoved()) {
            return null;
         } else {
            CacheEntry entry = this.getReadMiss(key, workspace);
            if (entry == null) {
               return val == null && !workspace.wasNull() ? null : new Entry(this, key);
            } else {
               return new Entry(this, entry);
            }
         }
      }
   }

   protected CacheEntry stdGetEntry(Object key, Workspace workspace) {
      return super.getEntry(key);
   }

   public Object putIfAbsent(Object key, Object value) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdPutIfAbsent(key, value, (Workspace)null);
      } else {
         Object prev = workspace.get(key);
         boolean isnew = prev == null && !workspace.wasNull();
         if (isnew && !workspace.isCleared()) {
            Map.Entry entry = this.getWriteMiss(key, workspace);
            if (entry != null) {
               prev = entry.getValue();
               isnew = false;
            }
         }

         if (isnew || workspace.wasRemoved()) {
            workspace.put(key, value, prev);
         }

         return workspace.wasRemoved() ? null : prev;
      }
   }

   protected Object stdPutIfAbsent(Object key, Object value, Workspace workspace) {
      return super.putIfAbsent(key, value);
   }

   public boolean remove(Object key, Object value) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdRemove(key, value, (Workspace)null);
      } else {
         Object prev = workspace.get(key);
         if (workspace.wasRemoved()) {
            return false;
         } else {
            boolean isnew = prev == null && !workspace.wasNull();
            if (isnew && !workspace.isCleared()) {
               Map.Entry entry = this.getWriteMiss(key, workspace);
               if (entry != null) {
                  prev = entry.getValue();
                  isnew = false;
               }
            }

            if (!isnew && eq(prev, value)) {
               workspace.remove(key, prev);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private static boolean eq(Object o1, Object o2) {
      if (o1 == o2) {
         return true;
      } else if (o1 == null) {
         return o2 == null;
      } else {
         return o1.equals(o2);
      }
   }

   protected boolean stdRemove(Object key, Object value, Workspace workspace) {
      return super.remove(key, value);
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdReplace(key, oldValue, newValue, (Workspace)null);
      } else {
         Object prev = workspace.get(key);
         if (workspace.wasRemoved()) {
            return false;
         } else {
            boolean isnew = prev == null && !workspace.wasNull();
            if (isnew && !workspace.isCleared()) {
               Map.Entry entry = this.getWriteMiss(key, workspace);
               if (entry != null) {
                  prev = entry.getValue();
                  isnew = false;
               }
            }

            if (!isnew && eq(prev, oldValue)) {
               workspace.put(key, newValue, prev);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   protected boolean stdReplace(Object key, Object oldValue, Object newValue, Workspace workspace) {
      return super.replace(key, oldValue, newValue);
   }

   public Object replace(Object key, Object newValue) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdReplace(key, newValue, (Workspace)null);
      } else {
         Object prev = workspace.get(key);
         if (workspace.wasRemoved()) {
            return null;
         } else {
            boolean isnew = prev == null && !workspace.wasNull();
            if (isnew && !workspace.isCleared()) {
               Map.Entry entry = this.getWriteMiss(key, workspace);
               if (entry != null) {
                  prev = entry.getValue();
                  isnew = false;
               }
            }

            if (isnew) {
               return null;
            } else {
               workspace.put(key, newValue, prev);
               return prev;
            }
         }
      }
   }

   protected Object stdReplace(Object key, Object newValue, Workspace workspace) {
      return super.replace(key, newValue);
   }

   public int size() {
      Workspace workspace = this.getWorkspace();
      int size = this.stdSize(workspace);
      if (workspace != null) {
         if (workspace.isCleared()) {
            size = 0;
         }

         size += workspace.getAdds().size() - workspace.getRemoves().size();
      }

      return size;
   }

   protected int stdSize(Workspace workspace) {
      return super.size();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean containsKey(Object key) {
      Workspace workspace = this.getWorkspace();
      if (workspace == null) {
         return this.stdContainsKey(key, (Workspace)null);
      } else {
         Object val = workspace.get(key);
         if (workspace.wasRemoved()) {
            return false;
         } else if (val == null && !workspace.wasNull()) {
            return !workspace.isCleared() && this.stdContainsKey(key, workspace);
         } else {
            return true;
         }
      }
   }

   protected boolean stdContainsKey(Object key, Workspace workspace) {
      return super.containsKey(key);
   }

   public boolean containsValue(Object value) {
      Workspace workspace = this.getWorkspace();
      return workspace == null ? this.stdContainsValue(value, (Workspace)null) : this.values().contains(value);
   }

   protected boolean stdContainsValue(Object value, Workspace workspace) {
      return super.containsValue(value);
   }

   public Set keySet() {
      return new AbstractSet() {
         public int size() {
            return AbstractWorkspaceMapAdapter.this.size();
         }

         public boolean contains(Object o) {
            return AbstractWorkspaceMapAdapter.this.containsKey(o);
         }

         public boolean remove(Object o) {
            if (!AbstractWorkspaceMapAdapter.this.containsKey(o)) {
               return false;
            } else {
               AbstractWorkspaceMapAdapter.this.remove(o);
               return true;
            }
         }

         public Iterator iterator() {
            final Iterator itr = (AbstractWorkspaceMapAdapter.this.new EntrySet()).iterator();
            return new Iterator() {
               public boolean hasNext() {
                  return itr.hasNext();
               }

               public Object next() {
                  return ((Map.Entry)itr.next()).getKey();
               }

               public void remove() {
                  itr.remove();
               }
            };
         }
      };
   }

   public Collection values() {
      return new AbstractCollection() {
         public int size() {
            return AbstractWorkspaceMapAdapter.this.size();
         }

         public Iterator iterator() {
            final Iterator itr = (AbstractWorkspaceMapAdapter.this.new EntrySet()).iterator();
            return new Iterator() {
               public boolean hasNext() {
                  return itr.hasNext();
               }

               public Object next() {
                  return ((Map.Entry)itr.next()).getValue();
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }
      };
   }

   public Set entrySet() {
      return new EntrySet();
   }

   protected Set stdEntrySet(Workspace workspace) {
      return super.entrySet();
   }

   private class EntrySet extends AbstractSet {
      private EntrySet() {
      }

      public int size() {
         return AbstractWorkspaceMapAdapter.this.size();
      }

      public boolean contains(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry entry = (Map.Entry)o;
            Object val = AbstractWorkspaceMapAdapter.this.get(entry.getKey());
            return val == null && !AbstractWorkspaceMapAdapter.this.containsKey(entry.getKey()) ? false : AbstractWorkspaceMapAdapter.eq(val, entry.getValue());
         }
      }

      public boolean remove(Object o) {
         if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry entry = (Map.Entry)o;
            return AbstractWorkspaceMapAdapter.this.remove(entry.getKey(), entry.getValue());
         }
      }

      public Iterator iterator() {
         final Workspace workspace = AbstractWorkspaceMapAdapter.this.getWorkspace();
         final Iterator itr = AbstractWorkspaceMapAdapter.this.stdEntrySet(workspace).iterator();
         return workspace == null ? itr : new Iterator() {
            private Iterator _itr = itr;
            private Map.Entry _last;
            private Map.Entry _next;
            private boolean _adds;

            public boolean hasNext() {
               if (this._next == null) {
                  this.setNext();
               }

               return this._next != null;
            }

            public Map.Entry next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  this._last = this._next;
                  this._next = null;
                  return this._last;
               }
            }

            public void remove() {
               if (this._last == null) {
                  throw new IllegalStateException();
               } else {
                  AbstractWorkspaceMapAdapter.this.remove(this._last.getKey());
                  this._last = null;
               }
            }

            private void setNext() {
               this._next = this.findNext(this._itr, !this._adds);
               if (this._next == null && !this._adds) {
                  this._itr = (new ArrayList(workspace.getAdds().entrySet())).iterator();
                  this._adds = true;
                  this._next = this.findNext(this._itr, false);
               }

            }

            private Map.Entry findNext(Iterator itrx, boolean filter) {
               while(true) {
                  if (itrx.hasNext()) {
                     Map.Entry entry = (Map.Entry)itrx.next();
                     if (!filter) {
                        return new Entry(AbstractWorkspaceMapAdapter.this, entry.getKey());
                     }

                     if (workspace.getRemoves().containsKey(entry.getKey())) {
                        continue;
                     }

                     return new Entry(AbstractWorkspaceMapAdapter.this, (CacheEntry)entry);
                  }

                  return null;
               }
            }
         };
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private static class Entry implements CacheEntry {
      private transient CacheMap _cache;
      private final Object _key;
      private final CacheEntry _entry;
      private Object _value;

      public Entry(CacheMap cache, CacheEntry entry) {
         this._cache = cache;
         this._key = entry.getKey();
         this._entry = entry;
      }

      public Entry(CacheMap cache, Object key) {
         this._cache = cache;
         this._key = key;
         this._entry = null;
      }

      public Object getKey() {
         return this._key;
      }

      public Object getValue() {
         return this._cache != null ? this._cache.get(this._key) : this._value;
      }

      public Object setValue(Object val) {
         if (this._cache != null) {
            return this._cache.put(this._key, val);
         } else {
            Object ret = this._value;
            this._value = val;
            return ret;
         }
      }

      public long getExpirationTime() {
         return this._entry != null ? this._entry.getExpirationTime() : Long.MAX_VALUE;
      }

      public long getVersion() {
         return this._entry != null ? this._entry.getVersion() : 0L;
      }

      public void setVersion(long version) {
         if (this._entry != null) {
            this._entry.setVersion(version);
         }

      }

      public long getCreationTime() {
         return this._entry != null ? this._entry.getCreationTime() : 0L;
      }

      public long getLastAccessTime() {
         return this._entry != null ? this._entry.getLastAccessTime() : 0L;
      }

      public long getLastUpdateTime() {
         return this._entry != null ? this._entry.getLastUpdateTime() : 0L;
      }

      public boolean isDiscarded() {
         return this._entry != null && this._entry.isDiscarded();
      }

      public void discard() {
         if (this._entry != null) {
            this._entry.discard();
         }

      }

      public void touch() {
         if (this._entry != null) {
            this._entry.touch();
         }

      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof CacheEntry)) {
            return false;
         } else {
            CacheEntry entry = (CacheEntry)o;
            return AbstractWorkspaceMapAdapter.eq(this._key, entry.getKey()) && AbstractWorkspaceMapAdapter.eq(this.getValue(), entry.getValue()) && this.getVersion() == entry.getVersion();
         }
      }

      public int hashCode() {
         int keyh = this._key == null ? 0 : this._key.hashCode();
         Object val = this.getValue();
         int valh = val == null ? 0 : val.hashCode();
         long vers = this.getVersion();
         int versh = (int)(vers ^ vers >>> 32);
         return keyh ^ valh ^ versh;
      }

      public String toString() {
         return this._key + "->" + this.getValue() + "[" + this.getVersion() + "]";
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         this._value = this.getValue();
         out.defaultWriteObject();
      }
   }
}
