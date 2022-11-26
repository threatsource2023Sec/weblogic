package org.apache.xmlbeans.impl.piccolo.util;

public final class IndexedObjectImpl implements IndexedObject {
   private int index;
   private Object object;

   public IndexedObjectImpl(int index, Object object) {
      this.index = index;
      this.object = object;
   }

   public final int getIndex() {
      return this.index;
   }

   public final void setIndex(int index) {
      this.index = index;
   }

   public final Object getObject() {
      return this.object;
   }

   public final void setObject(Object object) {
      this.object = object;
   }

   public final Object clone() {
      return new IndexedObjectImpl(this.index, this.object);
   }

   public final boolean equals(Object o) {
      if (!(o instanceof IndexedObject)) {
         return false;
      } else {
         IndexedObject i = (IndexedObject)o;
         return this.index == i.getIndex() && this.object.equals(i.getObject());
      }
   }
}
