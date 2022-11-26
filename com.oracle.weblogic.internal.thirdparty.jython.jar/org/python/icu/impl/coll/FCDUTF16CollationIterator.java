package org.python.icu.impl.coll;

import org.python.icu.impl.Normalizer2Impl;

public final class FCDUTF16CollationIterator extends UTF16CollationIterator {
   private CharSequence rawSeq;
   private static final int rawStart = 0;
   private int segmentStart;
   private int segmentLimit;
   private int rawLimit;
   private final Normalizer2Impl nfcImpl;
   private StringBuilder normalized;
   private int checkDir;

   public FCDUTF16CollationIterator(CollationData d) {
      super(d);
      this.nfcImpl = d.nfcImpl;
   }

   public FCDUTF16CollationIterator(CollationData data, boolean numeric, CharSequence s, int p) {
      super(data, numeric, s, p);
      this.rawSeq = s;
      this.segmentStart = p;
      this.rawLimit = s.length();
      this.nfcImpl = data.nfcImpl;
      this.checkDir = 1;
   }

   public boolean equals(Object other) {
      if (other instanceof CollationIterator && this.equals(other) && other instanceof FCDUTF16CollationIterator) {
         FCDUTF16CollationIterator o = (FCDUTF16CollationIterator)other;
         if (this.checkDir != o.checkDir) {
            return false;
         } else if (this.checkDir == 0 && this.seq == this.rawSeq != (o.seq == o.rawSeq)) {
            return false;
         } else if (this.checkDir == 0 && this.seq != this.rawSeq) {
            return this.segmentStart - 0 == o.segmentStart - 0 && this.pos - this.start == o.pos - o.start;
         } else {
            return this.pos - 0 == o.pos - 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 42;
   }

   public void resetToOffset(int newOffset) {
      this.reset();
      this.seq = this.rawSeq;
      this.start = this.segmentStart = this.pos = 0 + newOffset;
      this.limit = this.rawLimit;
      this.checkDir = 1;
   }

   public int getOffset() {
      if (this.checkDir == 0 && this.seq != this.rawSeq) {
         return this.pos == this.start ? this.segmentStart - 0 : this.segmentLimit - 0;
      } else {
         return this.pos - 0;
      }
   }

   public void setText(boolean numeric, CharSequence s, int p) {
      super.setText(numeric, s, p);
      this.rawSeq = s;
      this.segmentStart = p;
      this.rawLimit = this.limit = s.length();
      this.checkDir = 1;
   }

   public int nextCodePoint() {
      char c;
      while(true) {
         if (this.checkDir > 0) {
            if (this.pos == this.limit) {
               return -1;
            }

            c = this.seq.charAt(this.pos++);
            if (CollationFCD.hasTccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || this.pos != this.limit && CollationFCD.hasLccc(this.seq.charAt(this.pos)))) {
               --this.pos;
               this.nextSegment();
               c = this.seq.charAt(this.pos++);
            }
            break;
         }

         if (this.checkDir == 0 && this.pos != this.limit) {
            c = this.seq.charAt(this.pos++);
            break;
         }

         this.switchToForward();
      }

      char trail;
      if (Character.isHighSurrogate(c) && this.pos != this.limit && Character.isLowSurrogate(trail = this.seq.charAt(this.pos))) {
         ++this.pos;
         return Character.toCodePoint(c, trail);
      } else {
         return c;
      }
   }

   public int previousCodePoint() {
      char c;
      while(true) {
         if (this.checkDir < 0) {
            if (this.pos == this.start) {
               return -1;
            }

            c = this.seq.charAt(--this.pos);
            if (CollationFCD.hasLccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || this.pos != this.start && CollationFCD.hasTccc(this.seq.charAt(this.pos - 1)))) {
               ++this.pos;
               this.previousSegment();
               c = this.seq.charAt(--this.pos);
            }
            break;
         }

         if (this.checkDir == 0 && this.pos != this.start) {
            c = this.seq.charAt(--this.pos);
            break;
         }

         this.switchToBackward();
      }

      char lead;
      if (Character.isLowSurrogate(c) && this.pos != this.start && Character.isHighSurrogate(lead = this.seq.charAt(this.pos - 1))) {
         --this.pos;
         return Character.toCodePoint(lead, c);
      } else {
         return c;
      }
   }

   protected long handleNextCE32() {
      while(true) {
         char c;
         if (this.checkDir > 0) {
            if (this.pos == this.limit) {
               return -4294967104L;
            }

            c = this.seq.charAt(this.pos++);
            if (CollationFCD.hasTccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || this.pos != this.limit && CollationFCD.hasLccc(this.seq.charAt(this.pos)))) {
               --this.pos;
               this.nextSegment();
               c = this.seq.charAt(this.pos++);
            }
         } else {
            if (this.checkDir != 0 || this.pos == this.limit) {
               this.switchToForward();
               continue;
            }

            c = this.seq.charAt(this.pos++);
         }

         return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
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
      assert this.checkDir < 0 && this.seq == this.rawSeq || this.checkDir == 0 && this.pos == this.limit;

      if (this.checkDir < 0) {
         this.start = this.segmentStart = this.pos;
         if (this.pos == this.segmentLimit) {
            this.limit = this.rawLimit;
            this.checkDir = 1;
         } else {
            this.checkDir = 0;
         }
      } else {
         if (this.seq != this.rawSeq) {
            this.seq = this.rawSeq;
            this.pos = this.start = this.segmentStart = this.segmentLimit;
         }

         this.limit = this.rawLimit;
         this.checkDir = 1;
      }

   }

   private void nextSegment() {
      assert this.checkDir > 0 && this.seq == this.rawSeq && this.pos != this.limit;

      int p = this.pos;
      int prevCC = 0;

      while(true) {
         int q = p;
         int c = Character.codePointAt(this.seq, p);
         p += Character.charCount(c);
         int fcd16 = this.nfcImpl.getFCD16(c);
         int leadCC = fcd16 >> 8;
         if (leadCC == 0 && q != this.pos) {
            this.limit = this.segmentLimit = q;
            break;
         }

         if (leadCC != 0 && (prevCC > leadCC || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
            while(p != this.rawLimit) {
               c = Character.codePointAt(this.seq, p);
               p += Character.charCount(c);
               if (this.nfcImpl.getFCD16(c) <= 255) {
                  break;
               }
            }

            this.normalize(this.pos, p);
            this.pos = this.start;
            break;
         }

         prevCC = fcd16 & 255;
         if (p == this.rawLimit || prevCC == 0) {
            this.limit = this.segmentLimit = p;
            break;
         }
      }

      assert this.pos != this.limit;

      this.checkDir = 0;
   }

   private void switchToBackward() {
      assert this.checkDir > 0 && this.seq == this.rawSeq || this.checkDir == 0 && this.pos == this.start;

      if (this.checkDir > 0) {
         this.limit = this.segmentLimit = this.pos;
         if (this.pos == this.segmentStart) {
            this.start = 0;
            this.checkDir = -1;
         } else {
            this.checkDir = 0;
         }
      } else {
         if (this.seq != this.rawSeq) {
            this.seq = this.rawSeq;
            this.pos = this.limit = this.segmentLimit = this.segmentStart;
         }

         this.start = 0;
         this.checkDir = -1;
      }

   }

   private void previousSegment() {
      assert this.checkDir < 0 && this.seq == this.rawSeq && this.pos != this.start;

      int p = this.pos;
      int nextCC = 0;

      while(true) {
         int q = p;
         int c = Character.codePointBefore(this.seq, p);
         p -= Character.charCount(c);
         int fcd16 = this.nfcImpl.getFCD16(c);
         int trailCC = fcd16 & 255;
         if (trailCC == 0 && q != this.pos) {
            this.start = this.segmentStart = q;
            break;
         }

         if (trailCC != 0 && (nextCC != 0 && trailCC > nextCC || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
            while(fcd16 > 255 && p != 0) {
               c = Character.codePointBefore(this.seq, p);
               p -= Character.charCount(c);
               if ((fcd16 = this.nfcImpl.getFCD16(c)) == 0) {
                  break;
               }
            }

            this.normalize(p, this.pos);
            this.pos = this.limit;
            break;
         }

         nextCC = fcd16 >> 8;
         if (p == 0 || nextCC == 0) {
            this.start = this.segmentStart = p;
            break;
         }
      }

      assert this.pos != this.start;

      this.checkDir = 0;
   }

   private void normalize(int from, int to) {
      if (this.normalized == null) {
         this.normalized = new StringBuilder();
      }

      this.nfcImpl.decompose(this.rawSeq, from, to, this.normalized, to - from);
      this.segmentStart = from;
      this.segmentLimit = to;
      this.seq = this.normalized;
      this.start = 0;
      this.limit = this.start + this.normalized.length();
   }
}
