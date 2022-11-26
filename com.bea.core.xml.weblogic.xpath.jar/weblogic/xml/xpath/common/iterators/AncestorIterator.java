package weblogic.xml.xpath.common.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import weblogic.xml.xpath.common.Interrogator;

public final class AncestorIterator implements Iterator {
   private Interrogator mInterrogator;
   private Object mNext = null;

   public AncestorIterator(Interrogator i, Object first) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator.");
      } else {
         this.mInterrogator = i;
         this.mNext = first;
      }
   }

   public boolean hasNext() {
      return this.mNext != null;
   }

   public Object next() {
      if (this.mNext == null) {
         throw new NoSuchElementException();
      } else {
         Object out = this.mNext;
         this.mNext = this.mInterrogator.getParent(out);
         return out;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
