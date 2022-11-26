package org.mozilla.javascript;

import java.util.Enumeration;
import java.util.Hashtable;

class IdEnumeration implements Enumeration {
   private Object next;
   private Scriptable obj;
   private int index;
   private Object[] array;
   private Hashtable used = new Hashtable(27);

   IdEnumeration(Scriptable var1) {
      this.changeObject(var1);
      this.next = this.getNext();
   }

   private void changeObject(Scriptable var1) {
      this.obj = var1;
      if (this.obj != null) {
         this.array = var1.getIds();
         if (this.array.length == 0) {
            this.changeObject(this.obj.getPrototype());
         }
      }

      this.index = 0;
   }

   private Object getNext() {
      if (this.obj == null) {
         return null;
      } else {
         Object var1;
         do {
            while(true) {
               if (this.index == this.array.length) {
                  this.changeObject(this.obj.getPrototype());
                  if (this.obj == null) {
                     return null;
                  }
               }

               var1 = this.array[this.index++];
               if (var1 instanceof String) {
                  if (!this.obj.has((String)var1, this.obj)) {
                     continue;
                  }
               } else if (!this.obj.has(((Number)var1).intValue(), this.obj)) {
                  continue;
               }
               break;
            }
         } while(this.used.containsKey(var1));

         return ScriptRuntime.toString(var1);
      }
   }

   public boolean hasMoreElements() {
      return this.next != null;
   }

   public Object nextElement() {
      Object var1 = this.next;
      this.used.put(this.next, this.next);
      this.next = this.getNext();
      return var1;
   }
}
