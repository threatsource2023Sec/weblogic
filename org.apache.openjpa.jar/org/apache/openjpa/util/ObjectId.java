package org.apache.openjpa.util;

public final class ObjectId extends OpenJPAId {
   private Object _key;

   public ObjectId(Class cls, Object key) {
      super(cls);
      this._key = key;
   }

   public ObjectId(Class cls, Object key, boolean subs) {
      super(cls, subs);
      this._key = key;
   }

   public Object getId() {
      return this._key;
   }

   void setId(Object id) {
      this._key = id;
   }

   public Object getIdObject() {
      return this._key;
   }

   protected int idHash() {
      return this._key == null ? 0 : this._key.hashCode();
   }

   protected boolean idEquals(OpenJPAId o) {
      Object key = ((ObjectId)o)._key;
      return this._key == null ? key == null : this._key.equals(key);
   }
}
