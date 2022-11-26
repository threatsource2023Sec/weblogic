package org.apache.openjpa.jdbc.schema;

import java.io.Serializable;

class ReferenceCounter implements Serializable {
   private int _count = 0;

   public int getRefCount() {
      return this._count;
   }

   public void ref() {
      ++this._count;
   }

   public void deref() {
      --this._count;
   }
}
