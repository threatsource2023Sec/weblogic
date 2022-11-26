package org.apache.openjpa.util;

import java.io.Serializable;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;

public abstract class OpenJPAId implements Comparable, Serializable {
   private static ConcurrentReferenceHashMap _typeCache = new ConcurrentReferenceHashMap(1, 0);
   protected Class type;
   protected boolean subs = true;
   private transient int _typeHash = 0;

   protected OpenJPAId() {
   }

   protected OpenJPAId(Class type) {
      this.type = type;
   }

   protected OpenJPAId(Class type, boolean subs) {
      this.type = type;
      this.subs = subs;
   }

   public Class getType() {
      return this.type;
   }

   public boolean hasSubclasses() {
      return this.subs;
   }

   public void setManagedInstanceType(Class type) {
      this.setManagedInstanceType(type, false);
   }

   public void setManagedInstanceType(Class type, boolean subs) {
      this.type = type;
      this.subs = subs;
   }

   public abstract Object getIdObject();

   protected abstract int idHash();

   protected abstract boolean idEquals(OpenJPAId var1);

   public int hashCode() {
      if (this._typeHash == 0) {
         Integer typeHashInt = (Integer)_typeCache.get(this.type);
         if (typeHashInt == null) {
            Class base = this.type;

            for(Class superclass = base.getSuperclass(); superclass != null && superclass != Object.class; superclass = base.getSuperclass()) {
               base = base.getSuperclass();
            }

            this._typeHash = base.hashCode();
            _typeCache.put(this.type, new Integer(this._typeHash));
         } else {
            this._typeHash = typeHashInt;
         }
      }

      return this._typeHash ^ this.idHash();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         OpenJPAId id = (OpenJPAId)o;
         return this.idEquals(id) && (id.type.isAssignableFrom(this.type) || this.subs && this.type.isAssignableFrom(id.type));
      } else {
         return false;
      }
   }

   public String toString() {
      return this.type.getName() + "-" + this.getIdObject();
   }

   public int compareTo(Object other) {
      if (other == this) {
         return 0;
      } else {
         return other == null ? 1 : ((Comparable)this.getIdObject()).compareTo(((OpenJPAId)other).getIdObject());
      }
   }
}
