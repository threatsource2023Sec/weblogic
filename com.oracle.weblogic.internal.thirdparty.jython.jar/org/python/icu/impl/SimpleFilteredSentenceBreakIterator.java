package org.python.icu.impl;

import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Iterator;
import org.python.icu.text.BreakIterator;
import org.python.icu.text.FilteredBreakIteratorBuilder;
import org.python.icu.text.UCharacterIterator;
import org.python.icu.util.BytesTrie;
import org.python.icu.util.CharsTrie;
import org.python.icu.util.CharsTrieBuilder;
import org.python.icu.util.StringTrieBuilder;
import org.python.icu.util.ULocale;

public class SimpleFilteredSentenceBreakIterator extends BreakIterator {
   private BreakIterator delegate;
   private UCharacterIterator text;
   private CharsTrie backwardsTrie;
   private CharsTrie forwardsPartialTrie;

   public SimpleFilteredSentenceBreakIterator(BreakIterator adoptBreakIterator, CharsTrie forwardsPartialTrie, CharsTrie backwardsTrie) {
      this.delegate = adoptBreakIterator;
      this.forwardsPartialTrie = forwardsPartialTrie;
      this.backwardsTrie = backwardsTrie;
   }

   private final void resetState() {
      this.text = UCharacterIterator.getInstance((CharacterIterator)this.delegate.getText().clone());
   }

   private final boolean breakExceptionAt(int n) {
      int bestPosn = -1;
      int bestValue = -1;
      this.text.setIndex(n);
      this.backwardsTrie.reset();
      int uch;
      if (this.text.previousCodePoint() != 32) {
         uch = this.text.nextCodePoint();
      }

      BytesTrie.Result r = BytesTrie.Result.INTERMEDIATE_VALUE;

      while((uch = this.text.previousCodePoint()) != -1 && (r = this.backwardsTrie.nextForCodePoint(uch)).hasNext()) {
         if (r.hasValue()) {
            bestPosn = this.text.getIndex();
            bestValue = this.backwardsTrie.getValue();
         }
      }

      if (r.matches()) {
         bestValue = this.backwardsTrie.getValue();
         bestPosn = this.text.getIndex();
      }

      if (bestPosn >= 0) {
         if (bestValue == 2) {
            return true;
         }

         if (bestValue == 1 && this.forwardsPartialTrie != null) {
            this.forwardsPartialTrie.reset();
            BytesTrie.Result rfwd = BytesTrie.Result.INTERMEDIATE_VALUE;
            this.text.setIndex(bestPosn);

            while((uch = this.text.nextCodePoint()) != -1 && (rfwd = this.forwardsPartialTrie.nextForCodePoint(uch)).hasNext()) {
            }

            if (rfwd.matches()) {
               return true;
            }
         }
      }

      return false;
   }

   private final int internalNext(int n) {
      if (n != -1 && this.backwardsTrie != null) {
         this.resetState();

         for(int textLen = this.text.getLength(); n != -1 && n != textLen; n = this.delegate.next()) {
            if (!this.breakExceptionAt(n)) {
               return n;
            }
         }

         return n;
      } else {
         return n;
      }
   }

   private final int internalPrev(int n) {
      if (n != 0 && n != -1 && this.backwardsTrie != null) {
         this.resetState();

         while(n != -1 && n != 0) {
            if (!this.breakExceptionAt(n)) {
               return n;
            }

            n = this.delegate.previous();
         }

         return n;
      } else {
         return n;
      }
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         SimpleFilteredSentenceBreakIterator other = (SimpleFilteredSentenceBreakIterator)obj;
         return this.delegate.equals(other.delegate) && this.text.equals(other.text) && this.backwardsTrie.equals(other.backwardsTrie) && this.forwardsPartialTrie.equals(other.forwardsPartialTrie);
      }
   }

   public int hashCode() {
      return this.forwardsPartialTrie.hashCode() * 39 + this.backwardsTrie.hashCode() * 11 + this.delegate.hashCode();
   }

   public Object clone() {
      SimpleFilteredSentenceBreakIterator other = (SimpleFilteredSentenceBreakIterator)super.clone();
      return other;
   }

   public int first() {
      return this.delegate.first();
   }

   public int preceding(int offset) {
      return this.internalPrev(this.delegate.preceding(offset));
   }

   public int previous() {
      return this.internalPrev(this.delegate.previous());
   }

   public int current() {
      return this.delegate.current();
   }

   public boolean isBoundary(int offset) {
      if (!this.delegate.isBoundary(offset)) {
         return false;
      } else if (this.backwardsTrie == null) {
         return true;
      } else {
         this.resetState();
         return !this.breakExceptionAt(offset);
      }
   }

   public int next() {
      return this.internalNext(this.delegate.next());
   }

   public int next(int n) {
      return this.internalNext(this.delegate.next(n));
   }

   public int following(int offset) {
      return this.internalNext(this.delegate.following(offset));
   }

   public int last() {
      return this.delegate.last();
   }

   public CharacterIterator getText() {
      return this.delegate.getText();
   }

   public void setText(CharacterIterator newText) {
      this.delegate.setText(newText);
   }

   public static class Builder extends FilteredBreakIteratorBuilder {
      private HashSet filterSet = new HashSet();
      static final int PARTIAL = 1;
      static final int MATCH = 2;
      static final int SuppressInReverse = 1;
      static final int AddToForward = 2;

      public Builder(ULocale loc) {
         ICUResourceBundle rb = ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/brkitr", loc, ICUResourceBundle.OpenType.LOCALE_ROOT);
         ICUResourceBundle breaks = rb.findWithFallback("exceptions/SentenceBreak");
         if (breaks != null) {
            int index = 0;

            for(int size = breaks.getSize(); index < size; ++index) {
               ICUResourceBundle b = (ICUResourceBundle)breaks.get(index);
               String br = b.getString();
               this.filterSet.add(br);
            }
         }

      }

      public Builder() {
         this.filterSet = new HashSet();
      }

      public boolean suppressBreakAfter(String str) {
         if (this.filterSet == null) {
            this.filterSet = new HashSet();
         }

         return this.filterSet.add(str);
      }

      public boolean unsuppressBreakAfter(String str) {
         return this.filterSet == null ? false : this.filterSet.remove(str);
      }

      public BreakIterator build(BreakIterator adoptBreakIterator) {
         if (this.filterSet.isEmpty()) {
            return adoptBreakIterator;
         } else {
            CharsTrieBuilder builder = new CharsTrieBuilder();
            CharsTrieBuilder builder2 = new CharsTrieBuilder();
            int revCount = 0;
            int fwdCount = 0;
            int subCount = this.filterSet.size();
            String[] ustrs = new String[subCount];
            int[] partials = new int[subCount];
            CharsTrie backwardsTrie = null;
            CharsTrie forwardsPartialTrie = null;
            int i = 0;

            for(Iterator var12 = this.filterSet.iterator(); var12.hasNext(); ++i) {
               String s = (String)var12.next();
               ustrs[i] = s;
               partials[i] = 0;
            }

            for(i = 0; i < subCount; ++i) {
               int nn = ustrs[i].indexOf(46);
               if (nn > -1 && nn + 1 != ustrs[i].length()) {
                  int sameAs = -1;

                  for(int j = 0; j < subCount; ++j) {
                     if (j != i && ustrs[i].regionMatches(0, ustrs[j], 0, nn + 1)) {
                        if (partials[j] == 0) {
                           partials[j] = 3;
                        } else if ((partials[j] & 1) != 0) {
                           sameAs = j;
                        }
                     }
                  }

                  if (sameAs == -1 && partials[i] == 0) {
                     StringBuilder prefix = new StringBuilder(ustrs[i].substring(0, nn + 1));
                     prefix.reverse();
                     builder.add(prefix, 1);
                     ++revCount;
                     partials[i] = 3;
                  }
               }
            }

            for(i = 0; i < subCount; ++i) {
               if (partials[i] == 0) {
                  StringBuilder reversed = (new StringBuilder(ustrs[i])).reverse();
                  builder.add(reversed, 2);
                  ++revCount;
               } else {
                  builder2.add(ustrs[i], 2);
                  ++fwdCount;
               }
            }

            if (revCount > 0) {
               backwardsTrie = builder.build(StringTrieBuilder.Option.FAST);
            }

            if (fwdCount > 0) {
               forwardsPartialTrie = builder2.build(StringTrieBuilder.Option.FAST);
            }

            return new SimpleFilteredSentenceBreakIterator(adoptBreakIterator, forwardsPartialTrie, backwardsTrie);
         }
      }
   }
}
