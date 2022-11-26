package weblogic.utils.collections;

import weblogic.utils.Debug;

public class AggregateKey {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final int DEFAULT_SIZE = 5;
   private static final int GROWTH_FACTOR = 2;
   private Object[] objects;
   private int idx = 0;
   private boolean frozen = false;

   public AggregateKey(Object o) {
      this.objects = new Object[5];
      this.objects[this.idx++] = o;
   }

   public AggregateKey(Object one, Object two) {
      this.objects = new Object[]{one, two};
      this.idx = 2;
   }

   public AggregateKey(Object[] objects) {
      this.objects = objects;
   }

   public boolean equals(Object o) {
      this.frozen = true;
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else {
         try {
            AggregateKey other = (AggregateKey)o;
            other.frozen = true;
            if (this.objects.length != other.objects.length) {
               return false;
            } else {
               for(int i = 0; i < this.objects.length; ++i) {
                  if (this.objects[i] == null) {
                     if (other.objects[i] != null) {
                        return false;
                     }
                  } else if (!this.objects[i].equals(other.objects[i])) {
                     return false;
                  }
               }

               return true;
            }
         } catch (ClassCastException var4) {
            return false;
         }
      }
   }

   public int hashCode() {
      this.frozen = true;
      int hash = 0;

      for(int i = 0; i < this.idx; ++i) {
         if (this.objects[i] != null) {
            hash ^= this.objects[i].hashCode();
         }
      }

      return hash;
   }

   public void addObject(Object o) {
      Debug.assertion(!this.frozen);

      while(true) {
         while(true) {
            try {
               this.objects[this.idx] = o;
               ++this.idx;
            } catch (ArrayIndexOutOfBoundsException var4) {
               Object[] newobjects = new Object[this.idx + 2];
               System.arraycopy(this.objects, 0, newobjects, 0, this.idx);
               this.objects = newobjects;
            }
         }
      }
   }
}
