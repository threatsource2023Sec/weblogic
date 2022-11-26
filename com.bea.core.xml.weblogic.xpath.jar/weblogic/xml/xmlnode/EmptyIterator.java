package weblogic.xml.xmlnode;

import java.util.Iterator;

public class EmptyIterator implements Iterator {
   public static final Iterator iterator = new EmptyIterator();

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      return null;
   }

   public void remove() {
   }
}
