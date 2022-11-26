package antlr.collections.impl;

import java.util.Enumeration;
import java.util.NoSuchElementException;

final class LLEnumeration implements Enumeration {
   LLCell cursor;
   LList list;

   public LLEnumeration(LList var1) {
      this.list = var1;
      this.cursor = this.list.head;
   }

   public boolean hasMoreElements() {
      return this.cursor != null;
   }

   public Object nextElement() {
      if (!this.hasMoreElements()) {
         throw new NoSuchElementException();
      } else {
         LLCell var1 = this.cursor;
         this.cursor = this.cursor.next;
         return var1.data;
      }
   }
}
