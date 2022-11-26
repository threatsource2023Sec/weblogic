package weblogic.xml.xpath.dom.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import weblogic.xml.xpath.dom.Utils;

public abstract class BaseNodeIterator implements Iterator {
   protected Node mCurrent = null;

   public BaseNodeIterator(Node first) {
      this.mCurrent = first;
   }

   public boolean hasNext() {
      return this.mCurrent != null;
   }

   public Object next() {
      if (this.mCurrent == null) {
         throw new NoSuchElementException();
      } else {
         Node saveCurrent = this.mCurrent;

         do {
            this.mCurrent = this.nextNode();
         } while(this.mCurrent != null && !Utils.isUsable(this.mCurrent));

         return saveCurrent;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   protected abstract Node nextNode();
}
