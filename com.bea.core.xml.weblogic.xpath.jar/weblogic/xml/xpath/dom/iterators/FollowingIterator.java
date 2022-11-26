package weblogic.xml.xpath.dom.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import weblogic.xml.xpath.dom.Utils;

public final class FollowingIterator implements Iterator {
   private Node mCurrent = null;

   public FollowingIterator(Node node) {
      do {
         this.mCurrent = node.getNextSibling();
         if (this.mCurrent == null) {
            node = node.getParentNode();
            if (node == null) {
               break;
            }
         }
      } while(this.mCurrent == null || !Utils.isUsable(this.mCurrent));

   }

   public boolean hasNext() {
      return this.mCurrent != null;
   }

   public Object next() {
      if (this.mCurrent == null) {
         throw new NoSuchElementException();
      } else {
         Node out = this.mCurrent;

         do {
            Node look = this.mCurrent.getFirstChild();
            if (look == null) {
               look = this.mCurrent.getNextSibling();
               if (look == null) {
                  do {
                     look = this.mCurrent.getParentNode();
                     if (look == null) {
                        this.mCurrent = null;
                        return out;
                     }

                     this.mCurrent = look;
                     look = this.mCurrent.getNextSibling();
                  } while(look == null);
               }
            }

            this.mCurrent = look;
         } while(!Utils.isUsable(this.mCurrent));

         return out;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
