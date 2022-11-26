package weblogic.xml.stream.events;

import java.util.Iterator;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;

/** @deprecated */
@Deprecated
public class AttributeIteratorImpl implements AttributeIterator {
   private Iterator iterator;
   private Attribute peeked;
   private boolean peekedSet;

   public AttributeIteratorImpl(Iterator iterator) {
      this.iterator = iterator;
      this.peekedSet = false;
   }

   public boolean hasNext() {
      return this.peekedSet ? this.peekedSet : this.iterator.hasNext();
   }

   public Attribute next() {
      if (this.peekedSet) {
         this.peekedSet = false;
         return this.peeked;
      } else {
         return (Attribute)this.iterator.next();
      }
   }

   public Attribute peek() {
      this.peekedSet = true;
      this.peeked = (Attribute)this.iterator.next();
      return this.peeked;
   }

   public void skip() {
      this.next();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof AttributeIterator)) {
         return false;
      } else {
         AttributeIterator i = (AttributeIterator)obj;
         if (!i.hasNext() && !this.hasNext()) {
            return true;
         } else if (this.hasNext() && !i.hasNext()) {
            return false;
         } else {
            do {
               if (!this.hasNext()) {
                  if (i.hasNext()) {
                     return false;
                  }

                  return true;
               }
            } while(this.next().equals(i.next()));

            return false;
         }
      }
   }
}
