package weblogic.xml.xpath.dom.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class AttributeIterator implements Iterator {
   private Node mNext;
   private NamedNodeMap mNamedNodeMap = null;
   private int mIndex = 0;

   public AttributeIterator(NamedNodeMap map) {
      this.mNamedNodeMap = map;
      this.peekNext();
   }

   public boolean hasNext() {
      return this.mNext != null;
   }

   public Object next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         Node out = this.mNext;
         this.peekNext();
         return out;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   private void peekNext() {
      do {
         if (this.mIndex < this.mNamedNodeMap.getLength()) {
            this.mNext = this.mNamedNodeMap.item(this.mIndex++);
         } else {
            this.mNext = null;
         }
      } while(this.mNext != null && "xmlns".equals(this.mNext.getPrefix()));

   }
}
