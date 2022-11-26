package org.apache.openjpa.meta;

import java.io.Serializable;
import java.util.Comparator;

public class InheritanceComparator implements Comparator, Serializable {
   private Class _base = Object.class;

   public void setBase(Class base) {
      this._base = base;
   }

   public Class getBase() {
      return this._base;
   }

   protected Class toClass(Object elem) {
      return (Class)elem;
   }

   public int compare(Object o1, Object o2) {
      if (o1 == o2) {
         return 0;
      } else if (o1 == null) {
         return -1;
      } else if (o2 == null) {
         return 1;
      } else {
         Class c1 = this.toClass(o1);
         Class c2 = this.toClass(o2);
         if (c1 == c2) {
            return 0;
         } else if (c1 == null) {
            return -1;
         } else if (c2 == null) {
            return 1;
         } else {
            int i1 = this.levels(c1);
            int i2 = this.levels(c2);
            if (i1 == i2) {
               if (c1.isAssignableFrom(c2)) {
                  return -1;
               } else {
                  return c2.isAssignableFrom(c1) ? 1 : c1.getName().compareTo(c2.getName());
               }
            } else if (i1 < i2) {
               return -1;
            } else {
               return i1 > i2 ? 1 : 0;
            }
         }
      }
   }

   private int levels(Class to) {
      if (to.isInterface()) {
         return to.getInterfaces().length;
      } else {
         for(int i = 0; to != null; to = to.getSuperclass()) {
            if (to == this._base) {
               return i;
            }

            ++i;
         }

         return Integer.MAX_VALUE;
      }
   }
}
