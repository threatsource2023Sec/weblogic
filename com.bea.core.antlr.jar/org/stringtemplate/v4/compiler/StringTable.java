package org.stringtemplate.v4.compiler;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class StringTable {
   protected LinkedHashMap table = new LinkedHashMap();
   protected int i = -1;

   public int add(String s) {
      Integer I = (Integer)this.table.get(s);
      if (I != null) {
         return I;
      } else {
         ++this.i;
         this.table.put(s, this.i);
         return this.i;
      }
   }

   public String[] toArray() {
      String[] a = new String[this.table.size()];
      int i = 0;

      String s;
      for(Iterator var3 = this.table.keySet().iterator(); var3.hasNext(); a[i++] = s) {
         s = (String)var3.next();
      }

      return a;
   }
}
