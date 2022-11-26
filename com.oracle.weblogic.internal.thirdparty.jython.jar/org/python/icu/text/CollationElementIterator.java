package org.python.icu.text;

import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Map;
import org.python.icu.impl.CharacterIteratorWrapper;
import org.python.icu.impl.coll.CollationData;
import org.python.icu.impl.coll.CollationIterator;
import org.python.icu.impl.coll.CollationSettings;
import org.python.icu.impl.coll.ContractionsAndExpansions;
import org.python.icu.impl.coll.FCDIterCollationIterator;
import org.python.icu.impl.coll.FCDUTF16CollationIterator;
import org.python.icu.impl.coll.IterCollationIterator;
import org.python.icu.impl.coll.UTF16CollationIterator;
import org.python.icu.impl.coll.UVector32;

public final class CollationElementIterator {
   private CollationIterator iter_;
   private RuleBasedCollator rbc_;
   private int otherHalf_;
   private byte dir_;
   private UVector32 offsets_;
   private String string_;
   public static final int NULLORDER = -1;
   public static final int IGNORABLE = 0;

   public static final int primaryOrder(int ce) {
      return ce >>> 16 & '\uffff';
   }

   public static final int secondaryOrder(int ce) {
      return ce >>> 8 & 255;
   }

   public static final int tertiaryOrder(int ce) {
      return ce & 255;
   }

   private static final int getFirstHalf(long p, int lower32) {
      return (int)p & -65536 | lower32 >> 16 & '\uff00' | lower32 >> 8 & 255;
   }

   private static final int getSecondHalf(long p, int lower32) {
      return (int)p << 16 | lower32 >> 8 & '\uff00' | lower32 & 63;
   }

   private static final boolean ceNeedsTwoParts(long ce) {
      return (ce & 281470698455103L) != 0L;
   }

   private CollationElementIterator(RuleBasedCollator collator) {
      this.iter_ = null;
      this.rbc_ = collator;
      this.otherHalf_ = 0;
      this.dir_ = 0;
      this.offsets_ = null;
   }

   CollationElementIterator(String source, RuleBasedCollator collator) {
      this(collator);
      this.setText(source);
   }

   CollationElementIterator(CharacterIterator source, RuleBasedCollator collator) {
      this(collator);
      this.setText(source);
   }

   CollationElementIterator(UCharacterIterator source, RuleBasedCollator collator) {
      this(collator);
      this.setText(source);
   }

   public int getOffset() {
      if (this.dir_ < 0 && this.offsets_ != null && !this.offsets_.isEmpty()) {
         int i = this.iter_.getCEsLength();
         if (this.otherHalf_ != 0) {
            ++i;
         }

         assert i < this.offsets_.size();

         return this.offsets_.elementAti(i);
      } else {
         return this.iter_.getOffset();
      }
   }

   public int next() {
      if (this.dir_ > 1) {
         if (this.otherHalf_ != 0) {
            int oh = this.otherHalf_;
            this.otherHalf_ = 0;
            return oh;
         }
      } else if (this.dir_ == 1) {
         this.dir_ = 2;
      } else {
         if (this.dir_ != 0) {
            throw new IllegalStateException("Illegal change of direction");
         }

         this.dir_ = 2;
      }

      this.iter_.clearCEsIfNoneRemaining();
      long ce = this.iter_.nextCE();
      if (ce == 4311744768L) {
         return -1;
      } else {
         long p = ce >>> 32;
         int lower32 = (int)ce;
         int firstHalf = getFirstHalf(p, lower32);
         int secondHalf = getSecondHalf(p, lower32);
         if (secondHalf != 0) {
            this.otherHalf_ = secondHalf | 192;
         }

         return firstHalf;
      }
   }

   public int previous() {
      int limitOffset;
      if (this.dir_ < 0) {
         if (this.otherHalf_ != 0) {
            limitOffset = this.otherHalf_;
            this.otherHalf_ = 0;
            return limitOffset;
         }
      } else if (this.dir_ == 0) {
         this.iter_.resetToOffset(this.string_.length());
         this.dir_ = -1;
      } else {
         if (this.dir_ != 1) {
            throw new IllegalStateException("Illegal change of direction");
         }

         this.dir_ = -1;
      }

      if (this.offsets_ == null) {
         this.offsets_ = new UVector32();
      }

      limitOffset = this.iter_.getCEsLength() == 0 ? this.iter_.getOffset() : 0;
      long ce = this.iter_.previousCE(this.offsets_);
      if (ce == 4311744768L) {
         return -1;
      } else {
         long p = ce >>> 32;
         int lower32 = (int)ce;
         int firstHalf = getFirstHalf(p, lower32);
         int secondHalf = getSecondHalf(p, lower32);
         if (secondHalf != 0) {
            if (this.offsets_.isEmpty()) {
               this.offsets_.addElement(this.iter_.getOffset());
               this.offsets_.addElement(limitOffset);
            }

            this.otherHalf_ = firstHalf;
            return secondHalf | 192;
         } else {
            return firstHalf;
         }
      }
   }

   public void reset() {
      this.iter_.resetToOffset(0);
      this.otherHalf_ = 0;
      this.dir_ = 0;
   }

   public void setOffset(int newOffset) {
      if (0 < newOffset && newOffset < this.string_.length()) {
         int offset = newOffset;

         int lastSafeOffset;
         do {
            lastSafeOffset = this.string_.charAt(offset);
            if (!this.rbc_.isUnsafe(lastSafeOffset) || Character.isHighSurrogate((char)lastSafeOffset) && !this.rbc_.isUnsafe(this.string_.codePointAt(offset))) {
               break;
            }

            --offset;
         } while(offset > 0);

         if (offset < newOffset) {
            lastSafeOffset = offset;

            do {
               this.iter_.resetToOffset(lastSafeOffset);

               do {
                  this.iter_.nextCE();
               } while((offset = this.iter_.getOffset()) == lastSafeOffset);

               if (offset <= newOffset) {
                  lastSafeOffset = offset;
               }
            } while(offset < newOffset);

            newOffset = lastSafeOffset;
         }
      }

      this.iter_.resetToOffset(newOffset);
      this.otherHalf_ = 0;
      this.dir_ = 1;
   }

   public void setText(String source) {
      this.string_ = source;
      boolean numeric = ((CollationSettings)this.rbc_.settings.readOnly()).isNumeric();
      Object newIter;
      if (((CollationSettings)this.rbc_.settings.readOnly()).dontCheckFCD()) {
         newIter = new UTF16CollationIterator(this.rbc_.data, numeric, this.string_, 0);
      } else {
         newIter = new FCDUTF16CollationIterator(this.rbc_.data, numeric, this.string_, 0);
      }

      this.iter_ = (CollationIterator)newIter;
      this.otherHalf_ = 0;
      this.dir_ = 0;
   }

   public void setText(UCharacterIterator source) {
      this.string_ = source.getText();

      UCharacterIterator src;
      try {
         src = (UCharacterIterator)source.clone();
      } catch (CloneNotSupportedException var5) {
         this.setText(source.getText());
         return;
      }

      src.setToStart();
      boolean numeric = ((CollationSettings)this.rbc_.settings.readOnly()).isNumeric();
      Object newIter;
      if (((CollationSettings)this.rbc_.settings.readOnly()).dontCheckFCD()) {
         newIter = new IterCollationIterator(this.rbc_.data, numeric, src);
      } else {
         newIter = new FCDIterCollationIterator(this.rbc_.data, numeric, src, 0);
      }

      this.iter_ = (CollationIterator)newIter;
      this.otherHalf_ = 0;
      this.dir_ = 0;
   }

   public void setText(CharacterIterator source) {
      UCharacterIterator src = new CharacterIteratorWrapper(source);
      src.setToStart();
      this.string_ = src.getText();
      boolean numeric = ((CollationSettings)this.rbc_.settings.readOnly()).isNumeric();
      Object newIter;
      if (((CollationSettings)this.rbc_.settings.readOnly()).dontCheckFCD()) {
         newIter = new IterCollationIterator(this.rbc_.data, numeric, src);
      } else {
         newIter = new FCDIterCollationIterator(this.rbc_.data, numeric, src, 0);
      }

      this.iter_ = (CollationIterator)newIter;
      this.otherHalf_ = 0;
      this.dir_ = 0;
   }

   static final Map computeMaxExpansions(CollationData data) {
      Map maxExpansions = new HashMap();
      MaxExpSink sink = new MaxExpSink(maxExpansions);
      (new ContractionsAndExpansions((UnicodeSet)null, (UnicodeSet)null, sink, true)).forData(data);
      return maxExpansions;
   }

   public int getMaxExpansion(int ce) {
      return getMaxExpansion(this.rbc_.tailoring.maxExpansions, ce);
   }

   static int getMaxExpansion(Map maxExpansions, int order) {
      if (order == 0) {
         return 1;
      } else {
         Integer max;
         if (maxExpansions != null && (max = (Integer)maxExpansions.get(order)) != null) {
            return max;
         } else {
            return (order & 192) == 192 ? 2 : 1;
         }
      }
   }

   private byte normalizeDir() {
      return this.dir_ == 1 ? 0 : this.dir_;
   }

   public boolean equals(Object that) {
      if (that == this) {
         return true;
      } else if (!(that instanceof CollationElementIterator)) {
         return false;
      } else {
         CollationElementIterator thatceiter = (CollationElementIterator)that;
         return this.rbc_.equals(thatceiter.rbc_) && this.otherHalf_ == thatceiter.otherHalf_ && this.normalizeDir() == thatceiter.normalizeDir() && this.string_.equals(thatceiter.string_) && this.iter_.equals(thatceiter.iter_);
      }
   }

   /** @deprecated */
   @Deprecated
   public int hashCode() {
      assert false : "hashCode not designed";

      return 42;
   }

   /** @deprecated */
   @Deprecated
   public RuleBasedCollator getRuleBasedCollator() {
      return this.rbc_;
   }

   private static final class MaxExpSink implements ContractionsAndExpansions.CESink {
      private Map maxExpansions;

      MaxExpSink(Map h) {
         this.maxExpansions = h;
      }

      public void handleCE(long ce) {
      }

      public void handleExpansion(long[] ces, int start, int length) {
         if (length > 1) {
            int count = 0;

            for(int i = 0; i < length; ++i) {
               count += CollationElementIterator.ceNeedsTwoParts(ces[start + i]) ? 2 : 1;
            }

            long ce = ces[start + length - 1];
            long p = ce >>> 32;
            int lower32 = (int)ce;
            int lastHalf = CollationElementIterator.getSecondHalf(p, lower32);
            if (lastHalf == 0) {
               lastHalf = CollationElementIterator.getFirstHalf(p, lower32);

               assert lastHalf != 0;
            } else {
               lastHalf |= 192;
            }

            Integer oldCount = (Integer)this.maxExpansions.get(lastHalf);
            if (oldCount == null || count > oldCount) {
               this.maxExpansions.put(lastHalf, count);
            }

         }
      }
   }
}
