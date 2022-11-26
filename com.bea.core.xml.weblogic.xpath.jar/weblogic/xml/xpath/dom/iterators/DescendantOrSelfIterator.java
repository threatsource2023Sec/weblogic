package weblogic.xml.xpath.dom.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;

public final class DescendantOrSelfIterator implements Iterator {
   private Node mCurrent = null;
   private Node mRoot = null;

   public DescendantOrSelfIterator(Node node) {
      this.mRoot = this.mCurrent = node;
   }

   public boolean hasNext() {
      return this.mCurrent != null;
   }

   public Object next() {
      if (this.mCurrent == null) {
         throw new NoSuchElementException();
      } else {
         Node out = this.mCurrent;
         this.mCurrent = out.getFirstChild();
         if (this.mCurrent != null) {
            return out;
         } else if (out == this.mRoot) {
            this.mCurrent = null;
            return out;
         } else {
            this.mCurrent = out.getNextSibling();
            if (this.mCurrent != null) {
               return out;
            } else {
               for(Node node = out.getParentNode(); node != this.mRoot; node = node.getParentNode()) {
                  this.mCurrent = node.getNextSibling();
                  if (this.mCurrent != null) {
                     return out;
                  }
               }

               this.mCurrent = null;
               return out;
            }
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
