package weblogic.xml.babel.helpers;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class Sorter extends Hashtable {
   private Vector v;

   public Enumeration elements() {
      if (this.size() == 0) {
         return super.elements();
      } else if (this.v != null && this.v.size() == this.size()) {
         return this.v.elements();
      } else {
         Object[] keys = new Object[this.size()];
         int i = 0;

         for(Enumeration e = this.keys(); e.hasMoreElements(); ++i) {
            keys[i] = e.nextElement();
         }

         boolean isString = keys[0] instanceof String;

         for(i = 1; i < keys.length; ++i) {
            for(int j = i - 1; j >= 0; --j) {
               boolean doSwap;
               if (isString) {
                  doSwap = ((String)keys[j]).compareTo((String)keys[j + 1]) > 0;
               } else {
                  doSwap = this.compare((TestCase)keys[j], (TestCase)keys[j + 1]) > 0;
               }

               if (!doSwap) {
                  break;
               }

               Object temp = keys[j];
               keys[j] = keys[j + 1];
               keys[j + 1] = temp;
            }
         }

         this.v = new Vector(keys.length);

         for(i = 0; i < keys.length; ++i) {
            this.v.addElement(this.get(keys[i]));
         }

         return this.v.elements();
      }
   }

   private int compare(TestCase left, TestCase right) {
      if (left == right) {
         return 0;
      } else {
         int temp;
         if ((temp = left.sections.compareTo(right.sections)) != 0) {
            return temp;
         } else if ((temp = left.id.compareTo(right.id)) != 0) {
            return temp;
         } else {
            throw new IllegalArgumentException();
         }
      }
   }
}
