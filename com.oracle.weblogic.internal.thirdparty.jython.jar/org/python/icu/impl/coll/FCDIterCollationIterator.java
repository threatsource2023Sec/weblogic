package org.python.icu.impl.coll;

import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.text.UCharacterIterator;

public final class FCDIterCollationIterator extends IterCollationIterator {
   private State state;
   private int start;
   private int pos;
   private int limit;
   private final Normalizer2Impl nfcImpl;
   private StringBuilder s;
   private StringBuilder normalized;

   public FCDIterCollationIterator(CollationData data, boolean numeric, UCharacterIterator ui, int startIndex) {
      super(data, numeric, ui);
      this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
      this.start = startIndex;
      this.nfcImpl = data.nfcImpl;
   }

   public void resetToOffset(int newOffset) {
      super.resetToOffset(newOffset);
      this.start = newOffset;
      this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
   }

   public int getOffset() {
      if (this.state.compareTo(FCDIterCollationIterator.State.ITER_CHECK_BWD) <= 0) {
         return this.iter.getIndex();
      } else if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
         return this.pos;
      } else {
         return this.pos == 0 ? this.start : this.limit;
      }
   }

   public int nextCodePoint() {
      while(true) {
         int c;
         if (this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD) {
            c = this.iter.next();
            if (c < 0) {
               return c;
            }

            if (CollationFCD.hasTccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || CollationFCD.hasLccc(this.iter.current()))) {
               this.iter.previous();
               if (this.nextSegment()) {
                  continue;
               }

               return -1;
            }

            if (isLeadSurrogate(c)) {
               int trail = this.iter.next();
               if (isTrailSurrogate(trail)) {
                  return Character.toCodePoint((char)c, (char)trail);
               }

               if (trail >= 0) {
                  this.iter.previous();
               }
            }

            return c;
         } else {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
               c = this.iter.nextCodePoint();
               this.pos += Character.charCount(c);

               assert c >= 0;

               return c;
            }

            if (this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
               c = this.normalized.codePointAt(this.pos);
               this.pos += Character.charCount(c);
               return c;
            }

            this.switchToForward();
         }
      }
   }

   public int previousCodePoint() {
      while(true) {
         int c;
         if (this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD) {
            c = this.iter.previous();
            if (c < 0) {
               this.start = this.pos = 0;
               this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
               return -1;
            }

            if (CollationFCD.hasLccc(c)) {
               int prev = -1;
               if (CollationFCD.maybeTibetanCompositeVowel(c) || CollationFCD.hasTccc(prev = this.iter.previous())) {
                  this.iter.next();
                  if (prev >= 0) {
                     this.iter.next();
                  }

                  if (this.previousSegment()) {
                     continue;
                  }

                  return -1;
               }

               if (isTrailSurrogate(c)) {
                  if (prev < 0) {
                     prev = this.iter.previous();
                  }

                  if (isLeadSurrogate(prev)) {
                     return Character.toCodePoint((char)prev, (char)c);
                  }
               }

               if (prev >= 0) {
                  this.iter.next();
               }
            }

            return c;
         } else {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos != this.start) {
               c = this.iter.previousCodePoint();
               this.pos -= Character.charCount(c);

               assert c >= 0;

               return c;
            }

            if (this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != 0) {
               c = this.normalized.codePointBefore(this.pos);
               this.pos -= Character.charCount(c);
               return c;
            }

            this.switchToBackward();
         }
      }
   }

   protected long handleNextCE32() {
      while(true) {
         int c;
         while(true) {
            if (this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD) {
               c = this.iter.next();
               if (c < 0) {
                  return -4294967104L;
               }

               if (!CollationFCD.hasTccc(c) || !CollationFCD.maybeTibetanCompositeVowel(c) && !CollationFCD.hasLccc(this.iter.current())) {
                  break;
               }

               this.iter.previous();
               if (!this.nextSegment()) {
                  int c = true;
                  return 192L;
               }
            } else {
               if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                  c = this.iter.next();
                  ++this.pos;

                  assert c >= 0;
                  break;
               }

               if (this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                  c = this.normalized.charAt(this.pos++);
                  break;
               }

               this.switchToForward();
            }
         }

         return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead((char)c));
      }
   }

   protected char handleGetTrailSurrogate() {
      int trail;
      if (this.state.compareTo(FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) <= 0) {
         trail = this.iter.next();
         if (isTrailSurrogate(trail)) {
            if (this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
               ++this.pos;
            }
         } else if (trail >= 0) {
            this.iter.previous();
         }

         return (char)trail;
      } else {
         assert this.pos < this.normalized.length();

         if (Character.isLowSurrogate((char)(trail = this.normalized.charAt(this.pos)))) {
            ++this.pos;
         }

         return (char)trail;
      }
   }

   protected void forwardNumCodePoints(int num) {
      while(num > 0 && this.nextCodePoint() >= 0) {
         --num;
      }

   }

   protected void backwardNumCodePoints(int num) {
      while(num > 0 && this.previousCodePoint() >= 0) {
         --num;
      }

   }

   private void switchToForward() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD || this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos == this.limit || this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == this.normalized.length();

      if (this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD) {
         this.start = this.pos = this.iter.getIndex();
         if (this.pos == this.limit) {
            this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
         } else {
            this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
         }
      } else {
         if (this.state != FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
            if (this.state == FCDIterCollationIterator.State.IN_NORM_ITER_AT_START) {
               this.iter.moveIndex(this.limit - this.start);
            }

            this.start = this.limit;
         }

         this.state = FCDIterCollationIterator.State.ITER_CHECK_FWD;
      }

   }

   private boolean nextSegment() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD;

      this.pos = this.iter.getIndex();
      if (this.s == null) {
         this.s = new StringBuilder();
      } else {
         this.s.setLength(0);
      }

      int prevCC = 0;

      do {
         int c = this.iter.nextCodePoint();
         if (c < 0) {
            break;
         }

         int fcd16 = this.nfcImpl.getFCD16(c);
         int leadCC = fcd16 >> 8;
         if (leadCC == 0 && this.s.length() != 0) {
            this.iter.previousCodePoint();
            break;
         }

         this.s.appendCodePoint(c);
         if (leadCC != 0 && (prevCC > leadCC || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
            while(true) {
               c = this.iter.nextCodePoint();
               if (c < 0) {
                  break;
               }

               if (this.nfcImpl.getFCD16(c) <= 255) {
                  this.iter.previousCodePoint();
                  break;
               }

               this.s.appendCodePoint(c);
            }

            this.normalize(this.s);
            this.start = this.pos;
            this.limit = this.pos + this.s.length();
            this.state = FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT;
            this.pos = 0;
            return true;
         }

         prevCC = fcd16 & 255;
      } while(prevCC != 0);

      this.limit = this.pos + this.s.length();

      assert this.pos != this.limit;

      this.iter.moveIndex(-this.s.length());
      this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
      return true;
   }

   private void switchToBackward() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD || this.state == FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT && this.pos == this.start || this.state.compareTo(FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == 0;

      if (this.state == FCDIterCollationIterator.State.ITER_CHECK_FWD) {
         this.limit = this.pos = this.iter.getIndex();
         if (this.pos == this.start) {
            this.state = FCDIterCollationIterator.State.ITER_CHECK_BWD;
         } else {
            this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
         }
      } else {
         if (this.state != FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT) {
            if (this.state == FCDIterCollationIterator.State.IN_NORM_ITER_AT_LIMIT) {
               this.iter.moveIndex(this.start - this.limit);
            }

            this.limit = this.start;
         }

         this.state = FCDIterCollationIterator.State.ITER_CHECK_BWD;
      }

   }

   private boolean previousSegment() {
      assert this.state == FCDIterCollationIterator.State.ITER_CHECK_BWD;

      this.pos = this.iter.getIndex();
      if (this.s == null) {
         this.s = new StringBuilder();
      } else {
         this.s.setLength(0);
      }

      int nextCC = 0;

      do {
         int c = this.iter.previousCodePoint();
         if (c < 0) {
            break;
         }

         int fcd16 = this.nfcImpl.getFCD16(c);
         int trailCC = fcd16 & 255;
         if (trailCC == 0 && this.s.length() != 0) {
            this.iter.nextCodePoint();
            break;
         }

         this.s.appendCodePoint(c);
         if (trailCC != 0 && (nextCC != 0 && trailCC > nextCC || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
            while(fcd16 > 255) {
               c = this.iter.previousCodePoint();
               if (c < 0) {
                  break;
               }

               fcd16 = this.nfcImpl.getFCD16(c);
               if (fcd16 == 0) {
                  this.iter.nextCodePoint();
                  break;
               }

               this.s.appendCodePoint(c);
            }

            this.s.reverse();
            this.normalize(this.s);
            this.limit = this.pos;
            this.start = this.pos - this.s.length();
            this.state = FCDIterCollationIterator.State.IN_NORM_ITER_AT_START;
            this.pos = this.normalized.length();
            return true;
         }

         nextCC = fcd16 >> 8;
      } while(nextCC != 0);

      this.start = this.pos - this.s.length();

      assert this.pos != this.start;

      this.iter.moveIndex(this.s.length());
      this.state = FCDIterCollationIterator.State.ITER_IN_FCD_SEGMENT;
      return true;
   }

   private void normalize(CharSequence s) {
      if (this.normalized == null) {
         this.normalized = new StringBuilder();
      }

      this.nfcImpl.decompose(s, this.normalized);
   }

   private static enum State {
      ITER_CHECK_FWD,
      ITER_CHECK_BWD,
      ITER_IN_FCD_SEGMENT,
      IN_NORM_ITER_AT_LIMIT,
      IN_NORM_ITER_AT_START;
   }
}
