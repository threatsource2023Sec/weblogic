package org.antlr.stringtemplate.language;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cat extends AbstractList {
   protected List elements = new ArrayList();

   public Iterator iterator() {
      return super.iterator();
   }

   public Object get(int index) {
      return this.elements.get(index);
   }

   public int size() {
      return this.elements.size();
   }

   public Cat(List attributes) {
      for(int i = 0; i < attributes.size(); ++i) {
         Object attribute = attributes.get(i);
         attribute = ASTExpr.convertAnythingIteratableToIterator(attribute);
         if (attribute instanceof Iterator) {
            Iterator it = (Iterator)attribute;

            while(it.hasNext()) {
               Object o = it.next();
               this.elements.add(o);
            }
         } else {
            this.elements.add(attribute);
         }
      }

   }

   public String toString() {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < this.elements.size(); ++i) {
         Object o = this.elements.get(i);
         buf.append(o);
      }

      return buf.toString();
   }
}
