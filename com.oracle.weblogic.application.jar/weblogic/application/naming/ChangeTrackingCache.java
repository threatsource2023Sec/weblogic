package weblogic.application.naming;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChangeTrackingCache {
   Map map;

   public void clear() {
      if (this.map != null) {
         this.map.clear();
      }

   }

   public void put(String key, Object value) {
      if (this.map == null) {
         this.map = new HashMap();
      }

      Record record = (Record)this.map.get(key);
      if (record == null) {
         record = new Record();
         this.map.put(key, record);
      }

      record.hasChanged = record.hasChanged || value != record.value;
      record.value = value;
   }

   public Object get(String key) {
      if (this.map == null) {
         return null;
      } else {
         Record r = (Record)this.map.get(key);
         return r == null ? null : r.value;
      }
   }

   public Collection changedValues(boolean markChanged) {
      if (this.map != null && !this.map.isEmpty()) {
         List values = new LinkedList();
         Iterator var3 = this.map.values().iterator();

         while(var3.hasNext()) {
            Record r = (Record)var3.next();
            if (r.hasChanged) {
               values.add(r.value);
               r.hasChanged = markChanged;
            }
         }

         return values;
      } else {
         return Collections.emptyList();
      }
   }

   public Set getKeys() {
      return this.map == null ? Collections.emptySet() : this.map.keySet();
   }

   private static class Record {
      boolean hasChanged;
      Object value;

      private Record() {
         this.hasChanged = true;
      }

      // $FF: synthetic method
      Record(Object x0) {
         this();
      }
   }
}
