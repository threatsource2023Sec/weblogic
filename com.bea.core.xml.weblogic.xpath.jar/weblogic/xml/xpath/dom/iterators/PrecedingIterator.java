package weblogic.xml.xpath.dom.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import weblogic.xml.xpath.dom.Utils;

public final class PrecedingIterator implements Iterator {
   private Node mCurrent = null;
   private int mDepth = 0;

   public PrecedingIterator(Node node) {
      while(this.mCurrent == null) {
         this.mCurrent = node.getPreviousSibling();
         if (this.mCurrent == null) {
            node = node.getParentNode();
            if (node == null) {
               break;
            }
         } else if (Utils.isUsable(this.mCurrent)) {
            for(this.mDepth = 1; (node = this.mCurrent.getLastChild()) != null && Utils.isUsable(node); ++this.mDepth) {
               this.mCurrent = node;
            }
         }
      }

   }

   public boolean hasNext() {
      return this.mCurrent != null;
   }

   public Object next() {
      if (this.mCurrent == null) {
         throw new NoSuchElementException();
      } else {
         Node out = this.mCurrent;
         Node node = null;
         this.mCurrent = this.mCurrent.getPreviousSibling();
         if (this.mCurrent != null) {
            while((node = this.mCurrent.getLastChild()) != null && Utils.isUsable(node)) {
               this.mCurrent = node;
               ++this.mDepth;
            }

            return out;
         } else if (--this.mDepth > 0) {
            this.mCurrent = out.getParentNode();
            return out;
         } else {
            this.mCurrent = out;

            while(this.mCurrent != null && (node = this.mCurrent.getParentNode()) != null) {
               this.mCurrent = node.getPreviousSibling();
               if (this.mCurrent != null) {
                  for(this.mDepth = 1; (node = this.mCurrent.getLastChild()) != null && Utils.isUsable(node); ++this.mDepth) {
                     this.mCurrent = node;
                  }

                  return out;
               }
            }

            this.mCurrent = null;
            return out;
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
