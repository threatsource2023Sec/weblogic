package org.python.core;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.python.util.Generic;

public class IdImpl {
   private WeakIdentityMap idMap = new WeakIdentityMap();
   private long sequentialId;

   public synchronized long id(PyObject o) {
      Object id = JyAttribute.getAttr(o, (byte)2);
      if (id != null) {
         return (Long)id;
      } else {
         Object javaProxy = o.getJavaProxy();
         long result;
         if (javaProxy != null) {
            result = this.java_obj_id(javaProxy);
         } else {
            result = this.java_obj_id(o);
         }

         JyAttribute.setAttr(o, (byte)2, result);
         return result;
      }
   }

   public String idstr(PyObject o) {
      return String.format("0x%x", this.id(o));
   }

   public synchronized long java_obj_id(Object o) {
      Long cand = (Long)this.idMap.get(o);
      if (cand == null) {
         long new_id = ++this.sequentialId;
         this.idMap.put(o, new_id);
         return new_id;
      } else {
         return cand;
      }
   }

   public static class WeakIdentityMap {
      private transient ReferenceQueue idKeys = new ReferenceQueue();
      private Map objHashcodeToPyId = Generic.map();

      private void cleanup() {
         Reference k;
         while((k = this.idKeys.poll()) != null) {
            this.objHashcodeToPyId.remove(k);
         }

      }

      public int _internal_map_size() {
         return this.objHashcodeToPyId.size();
      }

      public void put(Object key, Object val) {
         this.cleanup();
         this.objHashcodeToPyId.put(new WeakIdKey(key), val);
      }

      public Object get(Object key) {
         this.cleanup();
         return this.objHashcodeToPyId.get(new WeakIdKey(key));
      }

      public void remove(Object key) {
         this.cleanup();
         this.objHashcodeToPyId.remove(new WeakIdKey(key));
      }

      private class WeakIdKey extends WeakReference {
         private final int hashcode;

         WeakIdKey(Object obj) {
            super(obj, WeakIdentityMap.this.idKeys);
            this.hashcode = System.identityHashCode(obj);
         }

         public int hashCode() {
            return this.hashcode;
         }

         public boolean equals(Object other) {
            Object obj = this.get();
            if (obj != null) {
               return obj == ((WeakIdKey)other).get();
            } else {
               return this == other;
            }
         }
      }
   }
}
