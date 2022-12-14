package org.python.icu.impl.coll;

import org.python.icu.text.UCharacterIterator;

public class IterCollationIterator extends CollationIterator {
   protected UCharacterIterator iter;

   public IterCollationIterator(CollationData d, boolean numeric, UCharacterIterator ui) {
      super(d, numeric);
      this.iter = ui;
   }

   public void resetToOffset(int newOffset) {
      this.reset();
      this.iter.setIndex(newOffset);
   }

   public int getOffset() {
      return this.iter.getIndex();
   }

   public int nextCodePoint() {
      return this.iter.nextCodePoint();
   }

   public int previousCodePoint() {
      return this.iter.previousCodePoint();
   }

   protected long handleNextCE32() {
      int c = this.iter.next();
      return c < 0 ? -4294967104L : this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead((char)c));
   }

   protected char handleGetTrailSurrogate() {
      int trail = this.iter.next();
      if (!isTrailSurrogate(trail) && trail >= 0) {
         this.iter.previous();
      }

      return (char)trail;
   }

   protected void forwardNumCodePoints(int num) {
      this.iter.moveCodePointIndex(num);
   }

   protected void backwardNumCodePoints(int num) {
      this.iter.moveCodePointIndex(-num);
   }
}
