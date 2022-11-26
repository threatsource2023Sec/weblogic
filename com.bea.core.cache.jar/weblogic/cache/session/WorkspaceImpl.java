package weblogic.cache.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkspaceImpl implements Workspace {
   private boolean _cleared;
   private boolean _wasRemoved;
   private boolean _wasNull;
   private Map _adds;
   private Map _updates;
   private Map _removes;

   public boolean isEmpty() {
      return (this._adds == null || this._adds.isEmpty()) && (this._updates == null || this._updates.isEmpty()) && (this._removes == null || this._removes.isEmpty());
   }

   public Object get(Object key) {
      this._wasRemoved = false;
      this._wasNull = false;
      Object ret;
      if (this._updates != null) {
         ret = this._updates.get(key);
         if (ret != null) {
            return ret;
         }

         if (this._updates.containsKey(key)) {
            this._wasNull = true;
            return null;
         }
      }

      if (this._adds != null) {
         ret = this._adds.get(key);
         if (ret != null) {
            return ret;
         }

         if (this._adds.containsKey(key)) {
            this._wasNull = true;
            return null;
         }
      }

      if (this._removes != null) {
         ret = this._removes.get(key);
         if (ret != null) {
            this._wasRemoved = true;
            return ret;
         }

         if (this._removes.containsKey(key)) {
            this._wasRemoved = true;
            this._wasNull = true;
            return null;
         }
      }

      return null;
   }

   public boolean wasNull() {
      return this._wasNull;
   }

   public boolean wasRemoved() {
      return this._wasRemoved;
   }

   public Map getAdds() {
      return this._adds == null ? Collections.EMPTY_MAP : this._adds;
   }

   public Map getUpdates() {
      return this._updates == null ? Collections.EMPTY_MAP : this._updates;
   }

   public void put(Object key, Object val) {
      if (this._adds == null) {
         this._adds = new HashMap();
      }

      this._adds.put(key, val);
   }

   public void put(Object key, Object val, Object prev) {
      if (this._adds != null && this._adds.containsKey(key)) {
         this._adds.put(key, val);
      } else {
         if (this._removes != null) {
            this._removes.remove(key);
         }

         if (this._updates == null) {
            this._updates = new HashMap();
         }

         this._updates.put(key, val);
      }

   }

   public Map getRemoves() {
      return this._removes == null ? Collections.EMPTY_MAP : this._removes;
   }

   public void remove(Object key, Object prev) {
      if (this._adds != null && this._adds.containsKey(key)) {
         this._adds.remove(key);
      } else {
         if (this._updates != null) {
            this._updates.remove(key);
         }

         if (this._removes == null) {
            this._removes = new HashMap();
         }

         this._removes.put(key, prev);
      }

   }

   public boolean isCleared() {
      return this._cleared;
   }

   public void clear() {
      this.reset();
      this._cleared = true;
   }

   public void reset() {
      this._cleared = false;
      if (this._adds != null) {
         this._adds.clear();
      }

      if (this._updates != null) {
         this._updates.clear();
      }

      if (this._removes != null) {
         this._removes.clear();
      }

   }
}
