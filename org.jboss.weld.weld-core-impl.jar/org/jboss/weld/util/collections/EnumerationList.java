package org.jboss.weld.util.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Enumeration;

public class EnumerationList extends AbstractList {
   private final ArrayList list = new ArrayList();

   public EnumerationList(Enumeration enumeration) {
      while(enumeration.hasMoreElements()) {
         this.list.add(enumeration.nextElement());
      }

      this.list.trimToSize();
   }

   public Object get(int index) {
      return this.list.get(index);
   }

   public int size() {
      return this.list.size();
   }
}
