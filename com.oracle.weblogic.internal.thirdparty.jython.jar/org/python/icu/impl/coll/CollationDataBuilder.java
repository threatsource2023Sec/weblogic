package org.python.icu.impl.coll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.python.icu.impl.Norm2AllModes;
import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.impl.Trie2;
import org.python.icu.impl.Trie2Writable;
import org.python.icu.lang.UCharacter;
import org.python.icu.text.UnicodeSet;
import org.python.icu.text.UnicodeSetIterator;
import org.python.icu.util.CharsTrie;
import org.python.icu.util.CharsTrieBuilder;
import org.python.icu.util.StringTrieBuilder;

final class CollationDataBuilder {
   private static final int IS_BUILDER_JAMO_CE32 = 256;
   protected Normalizer2Impl nfcImpl;
   protected CollationData base;
   protected CollationSettings baseSettings;
   protected Trie2Writable trie;
   protected UVector32 ce32s;
   protected UVector64 ce64s;
   protected ArrayList conditionalCE32s;
   protected UnicodeSet contextChars = new UnicodeSet();
   protected StringBuilder contexts = new StringBuilder();
   protected UnicodeSet unsafeBackwardSet = new UnicodeSet();
   protected boolean modified;
   protected boolean fastLatinEnabled;
   protected CollationFastLatinBuilder fastLatinBuilder;
   protected DataBuilderCollationIterator collIter;

   CollationDataBuilder() {
      this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
      this.base = null;
      this.baseSettings = null;
      this.trie = null;
      this.ce32s = new UVector32();
      this.ce64s = new UVector64();
      this.conditionalCE32s = new ArrayList();
      this.modified = false;
      this.fastLatinEnabled = false;
      this.fastLatinBuilder = null;
      this.collIter = null;
      this.ce32s.addElement(0);
   }

   void initForTailoring(CollationData b) {
      if (this.trie != null) {
         throw new IllegalStateException("attempt to reuse a CollationDataBuilder");
      } else if (b == null) {
         throw new IllegalArgumentException("null CollationData");
      } else {
         this.base = b;
         this.trie = new Trie2Writable(192, -195323);

         int hangulCE32;
         for(hangulCE32 = 192; hangulCE32 <= 255; ++hangulCE32) {
            this.trie.set(hangulCE32, 192);
         }

         hangulCE32 = Collation.makeCE32FromTagAndIndex(12, 0);
         this.trie.setRange(44032, 55203, hangulCE32, true);
         this.unsafeBackwardSet.addAll(b.unsafeBackwardSet);
      }
   }

   boolean isCompressibleLeadByte(int b) {
      return this.base.isCompressibleLeadByte(b);
   }

   boolean isCompressiblePrimary(long p) {
      return this.isCompressibleLeadByte((int)p >>> 24);
   }

   boolean hasMappings() {
      return this.modified;
   }

   boolean isAssigned(int c) {
      return Collation.isAssignedCE32(this.trie.get(c));
   }

   void add(CharSequence prefix, CharSequence s, long[] ces, int cesLength) {
      int ce32 = this.encodeCEs(ces, cesLength);
      this.addCE32(prefix, s, ce32);
   }

   int encodeCEs(long[] ces, int cesLength) {
      if (cesLength >= 0 && cesLength <= 31) {
         if (!this.isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
         } else if (cesLength == 0) {
            return encodeOneCEAsCE32(0L);
         } else if (cesLength == 1) {
            return this.encodeOneCE(ces[0]);
         } else {
            if (cesLength == 2) {
               long ce0 = ces[0];
               long ce1 = ces[1];
               long p0 = ce0 >>> 32;
               if ((ce0 & 72057594037862655L) == 83886080L && (ce1 & -4278190081L) == 1280L && p0 != 0L) {
                  return (int)p0 | ((int)ce0 & '\uff00') << 8 | (int)ce1 >> 16 & '\uff00' | 192 | 4;
               }
            }

            int[] newCE32s = new int[31];

            for(int i = 0; i != cesLength; ++i) {
               int ce32 = encodeOneCEAsCE32(ces[i]);
               if (ce32 == 1) {
                  return this.encodeExpansion(ces, 0, cesLength);
               }

               newCE32s[i] = ce32;
            }

            return this.encodeExpansion32(newCE32s, 0, cesLength);
         }
      } else {
         throw new IllegalArgumentException("mapping to too many CEs");
      }
   }

   void addCE32(CharSequence prefix, CharSequence s, int ce32) {
      if (s.length() == 0) {
         throw new IllegalArgumentException("mapping from empty string");
      } else if (!this.isMutable()) {
         throw new IllegalStateException("attempt to add mappings after build()");
      } else {
         int c = Character.codePointAt(s, 0);
         int cLength = Character.charCount(c);
         int oldCE32 = this.trie.get(c);
         boolean hasContext = prefix.length() != 0 || s.length() > cLength;
         if (oldCE32 == 192) {
            int baseCE32 = this.base.getFinalCE32(this.base.getCE32(c));
            if (hasContext || Collation.ce32HasContext(baseCE32)) {
               oldCE32 = this.copyFromBaseCE32(c, baseCE32, true);
               this.trie.set(c, oldCE32);
            }
         }

         ConditionalCE32 cond;
         if (!hasContext) {
            if (!isBuilderContextCE32(oldCE32)) {
               this.trie.set(c, ce32);
            } else {
               cond = this.getConditionalCE32ForCE32(oldCE32);
               cond.builtCE32 = 1;
               cond.ce32 = ce32;
            }
         } else {
            if (!isBuilderContextCE32(oldCE32)) {
               int index = this.addConditionalCE32("\u0000", oldCE32);
               int contextCE32 = makeBuilderContextCE32(index);
               this.trie.set(c, contextCE32);
               this.contextChars.add(c);
               cond = this.getConditionalCE32(index);
            } else {
               cond = this.getConditionalCE32ForCE32(oldCE32);
               cond.builtCE32 = 1;
            }

            CharSequence suffix = s.subSequence(cLength, s.length());
            String context = (new StringBuilder()).append((char)prefix.length()).append(prefix).append(suffix).toString();
            this.unsafeBackwardSet.addAll(suffix);

            while(true) {
               int next = cond.next;
               if (next < 0) {
                  int index = this.addConditionalCE32(context, ce32);
                  cond.next = index;
                  break;
               }

               ConditionalCE32 nextCond = this.getConditionalCE32(next);
               int cmp = context.compareTo(nextCond.context);
               if (cmp < 0) {
                  int index = this.addConditionalCE32(context, ce32);
                  cond.next = index;
                  this.getConditionalCE32(index).next = next;
                  break;
               }

               if (cmp == 0) {
                  nextCond.ce32 = ce32;
                  break;
               }

               cond = nextCond;
            }
         }

         this.modified = true;
      }
   }

   void copyFrom(CollationDataBuilder src, CEModifier modifier) {
      if (!this.isMutable()) {
         throw new IllegalStateException("attempt to copyFrom() after build()");
      } else {
         CopyHelper helper = new CopyHelper(src, this, modifier);
         Iterator trieIterator = src.trie.iterator();

         Trie2.Range range;
         while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
            enumRangeForCopy(range.startCodePoint, range.endCodePoint, range.value, helper);
         }

         this.modified |= src.modified;
      }
   }

   void optimize(UnicodeSet set) {
      if (!set.isEmpty()) {
         UnicodeSetIterator iter = new UnicodeSetIterator(set);

         while(iter.next() && iter.codepoint != UnicodeSetIterator.IS_STRING) {
            int c = iter.codepoint;
            int ce32 = this.trie.get(c);
            if (ce32 == 192) {
               ce32 = this.base.getFinalCE32(this.base.getCE32(c));
               ce32 = this.copyFromBaseCE32(c, ce32, true);
               this.trie.set(c, ce32);
            }
         }

         this.modified = true;
      }
   }

   void suppressContractions(UnicodeSet set) {
      if (!set.isEmpty()) {
         UnicodeSetIterator iter = new UnicodeSetIterator(set);

         while(iter.next() && iter.codepoint != UnicodeSetIterator.IS_STRING) {
            int c = iter.codepoint;
            int ce32 = this.trie.get(c);
            if (ce32 == 192) {
               ce32 = this.base.getFinalCE32(this.base.getCE32(c));
               if (Collation.ce32HasContext(ce32)) {
                  ce32 = this.copyFromBaseCE32(c, ce32, false);
                  this.trie.set(c, ce32);
               }
            } else if (isBuilderContextCE32(ce32)) {
               ce32 = this.getConditionalCE32ForCE32(ce32).ce32;
               this.trie.set(c, ce32);
               this.contextChars.remove(c);
            }
         }

         this.modified = true;
      }
   }

   void enableFastLatin() {
      this.fastLatinEnabled = true;
   }

   void build(CollationData data) {
      this.buildMappings(data);
      if (this.base != null) {
         data.numericPrimary = this.base.numericPrimary;
         data.compressibleBytes = this.base.compressibleBytes;
         data.numScripts = this.base.numScripts;
         data.scriptsIndex = this.base.scriptsIndex;
         data.scriptStarts = this.base.scriptStarts;
      }

      this.buildFastLatinTable(data);
   }

   int getCEs(CharSequence s, long[] ces, int cesLength) {
      return this.getCEs(s, 0, ces, cesLength);
   }

   int getCEs(CharSequence prefix, CharSequence s, long[] ces, int cesLength) {
      int prefixLength = prefix.length();
      return prefixLength == 0 ? this.getCEs(s, 0, ces, cesLength) : this.getCEs((new StringBuilder(prefix)).append(s), prefixLength, ces, cesLength);
   }

   protected int getCE32FromOffsetCE32(boolean fromBase, int c, int ce32) {
      int i = Collation.indexFromCE32(ce32);
      long dataCE = fromBase ? this.base.ces[i] : this.ce64s.elementAti(i);
      long p = Collation.getThreeBytePrimaryForOffsetData(c, dataCE);
      return Collation.makeLongPrimaryCE32(p);
   }

   protected int addCE(long ce) {
      int length = this.ce64s.size();

      for(int i = 0; i < length; ++i) {
         if (ce == this.ce64s.elementAti(i)) {
            return i;
         }
      }

      this.ce64s.addElement(ce);
      return length;
   }

   protected int addCE32(int ce32) {
      int length = this.ce32s.size();

      for(int i = 0; i < length; ++i) {
         if (ce32 == this.ce32s.elementAti(i)) {
            return i;
         }
      }

      this.ce32s.addElement(ce32);
      return length;
   }

   protected int addConditionalCE32(String context, int ce32) {
      assert context.length() != 0;

      int index = this.conditionalCE32s.size();
      if (index > 524287) {
         throw new IndexOutOfBoundsException("too many context-sensitive mappings");
      } else {
         ConditionalCE32 cond = new ConditionalCE32(context, ce32);
         this.conditionalCE32s.add(cond);
         return index;
      }
   }

   protected ConditionalCE32 getConditionalCE32(int index) {
      return (ConditionalCE32)this.conditionalCE32s.get(index);
   }

   protected ConditionalCE32 getConditionalCE32ForCE32(int ce32) {
      return this.getConditionalCE32(Collation.indexFromCE32(ce32));
   }

   protected static int makeBuilderContextCE32(int index) {
      return Collation.makeCE32FromTagAndIndex(7, index);
   }

   protected static boolean isBuilderContextCE32(int ce32) {
      return Collation.hasCE32Tag(ce32, 7);
   }

   protected static int encodeOneCEAsCE32(long ce) {
      long p = ce >>> 32;
      int lower32 = (int)ce;
      int t = lower32 & '\uffff';

      assert (t & '쀀') != 49152;

      if ((ce & 281470698455295L) == 0L) {
         return (int)p | lower32 >>> 16 | t >> 8;
      } else if ((ce & 1099511627775L) == 83887360L) {
         return Collation.makeLongPrimaryCE32(p);
      } else {
         return p == 0L && (t & 255) == 0 ? Collation.makeLongSecondaryCE32(lower32) : 1;
      }
   }

   protected int encodeOneCE(long ce) {
      int ce32 = encodeOneCEAsCE32(ce);
      if (ce32 != 1) {
         return ce32;
      } else {
         int index = this.addCE(ce);
         if (index > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
         } else {
            return Collation.makeCE32FromTagIndexAndLength(6, index, 1);
         }
      }
   }

   protected int encodeExpansion(long[] ces, int start, int length) {
      long first = ces[start];
      int ce64sMax = this.ce64s.size() - length;

      int i;
      int j;
      label44:
      for(i = 0; i <= ce64sMax; ++i) {
         if (first == this.ce64s.elementAti(i)) {
            if (i > 524287) {
               throw new IndexOutOfBoundsException("too many mappings");
            }

            for(j = 1; j != length; ++j) {
               if (this.ce64s.elementAti(i + j) != ces[start + j]) {
                  continue label44;
               }
            }

            return Collation.makeCE32FromTagIndexAndLength(6, i, length);
         }
      }

      i = this.ce64s.size();
      if (i > 524287) {
         throw new IndexOutOfBoundsException("too many mappings");
      } else {
         for(j = 0; j < length; ++j) {
            this.ce64s.addElement(ces[start + j]);
         }

         return Collation.makeCE32FromTagIndexAndLength(6, i, length);
      }
   }

   protected int encodeExpansion32(int[] newCE32s, int start, int length) {
      int first = newCE32s[start];
      int ce32sMax = this.ce32s.size() - length;

      int i;
      int j;
      label44:
      for(i = 0; i <= ce32sMax; ++i) {
         if (first == this.ce32s.elementAti(i)) {
            if (i > 524287) {
               throw new IndexOutOfBoundsException("too many mappings");
            }

            for(j = 1; j != length; ++j) {
               if (this.ce32s.elementAti(i + j) != newCE32s[start + j]) {
                  continue label44;
               }
            }

            return Collation.makeCE32FromTagIndexAndLength(5, i, length);
         }
      }

      i = this.ce32s.size();
      if (i > 524287) {
         throw new IndexOutOfBoundsException("too many mappings");
      } else {
         for(j = 0; j < length; ++j) {
            this.ce32s.addElement(newCE32s[start + j]);
         }

         return Collation.makeCE32FromTagIndexAndLength(5, i, length);
      }
   }

   protected int copyFromBaseCE32(int var1, int var2, boolean var3) {
      // $FF: Couldn't be decompiled
   }

   protected int copyContractionsFromBaseCE32(StringBuilder context, int c, int ce32, ConditionalCE32 cond) {
      int trieIndex = Collation.indexFromCE32(ce32);
      int index;
      if ((ce32 & 256) != 0) {
         assert context.length() > 1;

         index = -1;
      } else {
         ce32 = this.base.getCE32FromContexts(trieIndex);

         assert !Collation.isContractionCE32(ce32);

         ce32 = this.copyFromBaseCE32(c, ce32, true);
         cond.next = index = this.addConditionalCE32(context.toString(), ce32);
         cond = this.getConditionalCE32(index);
      }

      int suffixStart = context.length();
      CharsTrie.Iterator suffixes = CharsTrie.iterator(this.base.contexts, trieIndex + 2, 0);

      while(suffixes.hasNext()) {
         CharsTrie.Entry entry = suffixes.next();
         context.append(entry.chars);
         ce32 = this.copyFromBaseCE32(c, entry.value, true);
         cond.next = index = this.addConditionalCE32(context.toString(), ce32);
         cond = this.getConditionalCE32(index);
         context.setLength(suffixStart);
      }

      assert index >= 0;

      return index;
   }

   private static void enumRangeForCopy(int start, int end, int value, CopyHelper helper) {
      if (value != -1 && value != 192) {
         helper.copyRangeCE32(start, end, value);
      }

   }

   protected boolean getJamoCE32s(int[] var1) {
      // $FF: Couldn't be decompiled
   }

   protected void setDigitTags() {
      UnicodeSet digits = new UnicodeSet("[:Nd:]");
      UnicodeSetIterator iter = new UnicodeSetIterator(digits);

      while(iter.next()) {
         assert iter.codepoint != UnicodeSetIterator.IS_STRING;

         int c = iter.codepoint;
         int ce32 = this.trie.get(c);
         if (ce32 != 192 && ce32 != -1) {
            int index = this.addCE32(ce32);
            if (index > 524287) {
               throw new IndexOutOfBoundsException("too many mappings");
            }

            ce32 = Collation.makeCE32FromTagIndexAndLength(10, index, UCharacter.digit(c));
            this.trie.set(c, ce32);
         }
      }

   }

   protected void setLeadSurrogates() {
      for(char lead = '\ud800'; lead < '\udc00'; ++lead) {
         int leadValue = -1;
         Iterator trieIterator = this.trie.iteratorForLeadSurrogate(lead);

         while(trieIterator.hasNext()) {
            Trie2.Range range = (Trie2.Range)trieIterator.next();
            int value = range.value;
            short value;
            if (value == -1) {
               value = 0;
            } else {
               if (value != 192) {
                  leadValue = 512;
                  break;
               }

               value = 256;
            }

            if (leadValue < 0) {
               leadValue = value;
            } else if (leadValue != value) {
               leadValue = 512;
               break;
            }
         }

         this.trie.setForLeadSurrogateCodeUnit(lead, Collation.makeCE32FromTagAndIndex(13, 0) | leadValue);
      }

   }

   protected void buildMappings(CollationData data) {
      if (!this.isMutable()) {
         throw new IllegalStateException("attempt to build() after build()");
      } else {
         this.buildContexts();
         int[] jamoCE32s = new int[67];
         int jamoIndex = -1;
         int c;
         int hangulCE32;
         int c;
         if (this.getJamoCE32s(jamoCE32s)) {
            jamoIndex = this.ce32s.size();

            for(c = 0; c < 67; ++c) {
               this.ce32s.addElement(jamoCE32s[c]);
            }

            boolean isAnyJamoVTSpecial = false;

            for(hangulCE32 = 19; hangulCE32 < 67; ++hangulCE32) {
               if (Collation.isSpecialCE32(jamoCE32s[hangulCE32])) {
                  isAnyJamoVTSpecial = true;
                  break;
               }
            }

            hangulCE32 = Collation.makeCE32FromTagAndIndex(12, 0);
            c = 44032;

            for(int i = 0; i < 19; ++i) {
               int ce32 = hangulCE32;
               if (!isAnyJamoVTSpecial && !Collation.isSpecialCE32(jamoCE32s[i])) {
                  ce32 = hangulCE32 | 256;
               }

               int limit = c + 588;
               this.trie.setRange(c, limit - 1, ce32, true);
               c = limit;
            }
         } else {
            for(c = 44032; c < 55204; c = c) {
               hangulCE32 = this.base.getCE32(c);

               assert Collation.hasCE32Tag(hangulCE32, 12);

               c = c + 588;
               this.trie.setRange(c, c - 1, hangulCE32, true);
            }
         }

         this.setDigitTags();
         this.setLeadSurrogates();
         this.ce32s.setElementAt(this.trie.get(0), 0);
         this.trie.set(0, Collation.makeCE32FromTagAndIndex(11, 0));
         data.trie = this.trie.toTrie2_32();
         c = 65536;

         for(char lead = '\ud800'; lead < '\udc00'; c += 1024) {
            if (this.unsafeBackwardSet.containsSome(c, c + 1023)) {
               this.unsafeBackwardSet.add(lead);
            }

            ++lead;
         }

         this.unsafeBackwardSet.freeze();
         data.ce32s = this.ce32s.getBuffer();
         data.ces = this.ce64s.getBuffer();
         data.contexts = this.contexts.toString();
         data.base = this.base;
         if (jamoIndex >= 0) {
            data.jamoCE32s = jamoCE32s;
         } else {
            data.jamoCE32s = this.base.jamoCE32s;
         }

         data.unsafeBackwardSet = this.unsafeBackwardSet;
      }
   }

   protected void clearContexts() {
      this.contexts.setLength(0);

      int ce32;
      for(UnicodeSetIterator iter = new UnicodeSetIterator(this.contextChars); iter.next(); this.getConditionalCE32ForCE32(ce32).builtCE32 = 1) {
         assert iter.codepoint != UnicodeSetIterator.IS_STRING;

         ce32 = this.trie.get(iter.codepoint);

         assert isBuilderContextCE32(ce32);
      }

   }

   protected void buildContexts() {
      this.contexts.setLength(0);
      UnicodeSetIterator iter = new UnicodeSetIterator(this.contextChars);

      while(iter.next()) {
         assert iter.codepoint != UnicodeSetIterator.IS_STRING;

         int c = iter.codepoint;
         int ce32 = this.trie.get(c);
         if (!isBuilderContextCE32(ce32)) {
            throw new AssertionError("Impossible: No context data for c in contextChars.");
         }

         ConditionalCE32 cond = this.getConditionalCE32ForCE32(ce32);
         ce32 = this.buildContext(cond);
         this.trie.set(c, ce32);
      }

   }

   protected int buildContext(ConditionalCE32 head) {
      assert !head.hasContext();

      assert head.next >= 0;

      CharsTrieBuilder prefixBuilder = new CharsTrieBuilder();
      CharsTrieBuilder contractionBuilder = new CharsTrieBuilder();

      for(ConditionalCE32 cond = head; $assertionsDisabled || cond == head || cond.hasContext(); cond = this.getConditionalCE32(cond.next)) {
         int prefixLength = cond.prefixLength();
         StringBuilder prefix = (new StringBuilder()).append(cond.context, 0, prefixLength + 1);
         String prefixString = prefix.toString();
         ConditionalCE32 firstCond = cond;

         ConditionalCE32 lastCond;
         for(lastCond = cond; cond.next >= 0 && (cond = this.getConditionalCE32(cond.next)).context.startsWith(prefixString); lastCond = cond) {
         }

         int suffixStart = prefixLength + 1;
         int ce32;
         if (lastCond.context.length() == suffixStart) {
            assert firstCond == lastCond;

            ce32 = lastCond.ce32;
            cond = lastCond;
         } else {
            contractionBuilder.clear();
            int emptySuffixCE32 = 1;
            int flags = 0;
            int index;
            if (firstCond.context.length() == suffixStart) {
               emptySuffixCE32 = firstCond.ce32;
               cond = this.getConditionalCE32(firstCond.next);
            } else {
               flags |= 256;
               cond = head;

               while(true) {
                  index = cond.prefixLength();
                  if (index == prefixLength) {
                     cond = firstCond;
                     break;
                  }

                  if (cond.defaultCE32 != 1 && (index == 0 || prefixString.regionMatches(prefix.length() - index, cond.context, 1, index))) {
                     emptySuffixCE32 = cond.defaultCE32;
                  }

                  cond = this.getConditionalCE32(cond.next);
               }
            }

            flags |= 512;

            while(true) {
               String suffix = cond.context.substring(suffixStart);
               int fcd16 = this.nfcImpl.getFCD16(suffix.codePointAt(0));
               if (fcd16 <= 255) {
                  flags &= -513;
               }

               fcd16 = this.nfcImpl.getFCD16(suffix.codePointBefore(suffix.length()));
               if (fcd16 > 255) {
                  flags |= 1024;
               }

               contractionBuilder.add(suffix, cond.ce32);
               if (cond == lastCond) {
                  index = this.addContextTrie(emptySuffixCE32, contractionBuilder);
                  if (index > 524287) {
                     throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                  }

                  ce32 = Collation.makeCE32FromTagAndIndex(9, index) | flags;
                  break;
               }

               cond = this.getConditionalCE32(cond.next);
            }
         }

         assert cond == lastCond;

         firstCond.defaultCE32 = ce32;
         if (prefixLength == 0) {
            if (cond.next < 0) {
               return ce32;
            }
         } else {
            prefix.delete(0, 1);
            prefix.reverse();
            prefixBuilder.add(prefix, ce32);
            if (cond.next < 0) {
               assert head.defaultCE32 != 1;

               int index = this.addContextTrie(head.defaultCE32, prefixBuilder);
               if (index > 524287) {
                  throw new IndexOutOfBoundsException("too many context-sensitive mappings");
               }

               return Collation.makeCE32FromTagAndIndex(8, index);
            }
         }
      }

      throw new AssertionError();
   }

   protected int addContextTrie(int defaultCE32, CharsTrieBuilder trieBuilder) {
      StringBuilder context = new StringBuilder();
      context.append((char)(defaultCE32 >> 16)).append((char)defaultCE32);
      context.append(trieBuilder.buildCharSequence(StringTrieBuilder.Option.SMALL));
      int index = this.contexts.indexOf(context.toString());
      if (index < 0) {
         index = this.contexts.length();
         this.contexts.append(context);
      }

      return index;
   }

   protected void buildFastLatinTable(CollationData data) {
      if (this.fastLatinEnabled) {
         this.fastLatinBuilder = new CollationFastLatinBuilder();
         if (this.fastLatinBuilder.forData(data)) {
            char[] header = this.fastLatinBuilder.getHeader();
            char[] table = this.fastLatinBuilder.getTable();
            if (this.base != null && Arrays.equals(header, this.base.fastLatinTableHeader) && Arrays.equals(table, this.base.fastLatinTable)) {
               this.fastLatinBuilder = null;
               header = this.base.fastLatinTableHeader;
               table = this.base.fastLatinTable;
            }

            data.fastLatinTableHeader = header;
            data.fastLatinTable = table;
         } else {
            this.fastLatinBuilder = null;
         }

      }
   }

   protected int getCEs(CharSequence s, int start, long[] ces, int cesLength) {
      if (this.collIter == null) {
         this.collIter = new DataBuilderCollationIterator(this, new CollationData(this.nfcImpl));
         if (this.collIter == null) {
            return 0;
         }
      }

      return this.collIter.fetchCEs(s, start, ces, cesLength);
   }

   protected static int jamoCpFromIndex(int i) {
      if (i < 19) {
         return 4352 + i;
      } else {
         i -= 19;
         if (i < 21) {
            return 4449 + i;
         } else {
            i -= 21;
            return 4520 + i;
         }
      }
   }

   protected final boolean isMutable() {
      return this.trie != null && this.unsafeBackwardSet != null && !this.unsafeBackwardSet.isFrozen();
   }

   private static final class DataBuilderCollationIterator extends CollationIterator {
      protected final CollationDataBuilder builder;
      protected final CollationData builderData;
      protected final int[] jamoCE32s = new int[67];
      protected CharSequence s;
      protected int pos;

      DataBuilderCollationIterator(CollationDataBuilder b, CollationData newData) {
         super(newData, false);
         this.builder = b;
         this.builderData = newData;
         this.builderData.base = this.builder.base;

         for(int j = 0; j < 67; ++j) {
            int jamo = CollationDataBuilder.jamoCpFromIndex(j);
            this.jamoCE32s[j] = Collation.makeCE32FromTagAndIndex(7, jamo) | 256;
         }

         this.builderData.jamoCE32s = this.jamoCE32s;
      }

      int fetchCEs(CharSequence str, int start, long[] ces, int cesLength) {
         this.builderData.ce32s = this.builder.ce32s.getBuffer();
         this.builderData.ces = this.builder.ce64s.getBuffer();
         this.builderData.contexts = this.builder.contexts.toString();
         this.reset();
         this.s = str;
         this.pos = start;

         while(this.pos < this.s.length()) {
            this.clearCEs();
            int c = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(c);
            int ce32 = this.builder.trie.get(c);
            CollationData d;
            if (ce32 == 192) {
               d = this.builder.base;
               ce32 = this.builder.base.getCE32(c);
            } else {
               d = this.builderData;
            }

            this.appendCEsFromCE32(d, c, ce32, true);

            for(int i = 0; i < this.getCEsLength(); ++i) {
               long ce = this.getCE(i);
               if (ce != 0L) {
                  if (cesLength < 31) {
                     ces[cesLength] = ce;
                  }

                  ++cesLength;
               }
            }
         }

         return cesLength;
      }

      public void resetToOffset(int newOffset) {
         this.reset();
         this.pos = newOffset;
      }

      public int getOffset() {
         return this.pos;
      }

      public int nextCodePoint() {
         if (this.pos == this.s.length()) {
            return -1;
         } else {
            int c = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(c);
            return c;
         }
      }

      public int previousCodePoint() {
         if (this.pos == 0) {
            return -1;
         } else {
            int c = Character.codePointBefore(this.s, this.pos);
            this.pos -= Character.charCount(c);
            return c;
         }
      }

      protected void forwardNumCodePoints(int num) {
         this.pos = Character.offsetByCodePoints(this.s, this.pos, num);
      }

      protected void backwardNumCodePoints(int num) {
         this.pos = Character.offsetByCodePoints(this.s, this.pos, -num);
      }

      protected int getDataCE32(int c) {
         return this.builder.trie.get(c);
      }

      protected int getCE32FromBuilderData(int ce32) {
         assert Collation.hasCE32Tag(ce32, 7);

         if ((ce32 & 256) != 0) {
            int jamo = Collation.indexFromCE32(ce32);
            return this.builder.trie.get(jamo);
         } else {
            ConditionalCE32 cond = this.builder.getConditionalCE32ForCE32(ce32);
            if (cond.builtCE32 == 1) {
               try {
                  cond.builtCE32 = this.builder.buildContext(cond);
               } catch (IndexOutOfBoundsException var4) {
                  this.builder.clearContexts();
                  cond.builtCE32 = this.builder.buildContext(cond);
               }

               this.builderData.contexts = this.builder.contexts.toString();
            }

            return cond.builtCE32;
         }
      }
   }

   private static final class CopyHelper {
      CollationDataBuilder src;
      CollationDataBuilder dest;
      CEModifier modifier;
      long[] modifiedCEs = new long[31];

      CopyHelper(CollationDataBuilder s, CollationDataBuilder d, CEModifier m) {
         this.src = s;
         this.dest = d;
         this.modifier = m;
      }

      void copyRangeCE32(int start, int end, int ce32) {
         ce32 = this.copyCE32(ce32);
         this.dest.trie.setRange(start, end, ce32, true);
         if (CollationDataBuilder.isBuilderContextCE32(ce32)) {
            this.dest.contextChars.add(start, end);
         }

      }

      int copyCE32(int ce32) {
         if (!Collation.isSpecialCE32(ce32)) {
            long ce = this.modifier.modifyCE32(ce32);
            if (ce != 4311744768L) {
               ce32 = this.dest.encodeOneCE(ce);
            }
         } else {
            int tag = Collation.tagFromCE32(ce32);
            int srcIndex;
            int length;
            boolean isModified;
            int i;
            long srcCE;
            if (tag == 5) {
               int[] srcCE32s = this.src.ce32s.getBuffer();
               srcIndex = Collation.indexFromCE32(ce32);
               length = Collation.lengthFromCE32(ce32);
               isModified = false;

               for(i = 0; i < length; ++i) {
                  ce32 = srcCE32s[srcIndex + i];
                  if (!Collation.isSpecialCE32(ce32) && (srcCE = this.modifier.modifyCE32(ce32)) != 4311744768L) {
                     if (!isModified) {
                        for(int j = 0; j < i; ++j) {
                           this.modifiedCEs[j] = Collation.ceFromCE32(srcCE32s[srcIndex + j]);
                        }

                        isModified = true;
                     }

                     this.modifiedCEs[i] = srcCE;
                  } else if (isModified) {
                     this.modifiedCEs[i] = Collation.ceFromCE32(ce32);
                  }
               }

               if (isModified) {
                  ce32 = this.dest.encodeCEs(this.modifiedCEs, length);
               } else {
                  ce32 = this.dest.encodeExpansion32(srcCE32s, srcIndex, length);
               }
            } else if (tag == 6) {
               long[] srcCEs = this.src.ce64s.getBuffer();
               srcIndex = Collation.indexFromCE32(ce32);
               length = Collation.lengthFromCE32(ce32);
               isModified = false;

               for(i = 0; i < length; ++i) {
                  srcCE = srcCEs[srcIndex + i];
                  long ce = this.modifier.modifyCE(srcCE);
                  if (ce == 4311744768L) {
                     if (isModified) {
                        this.modifiedCEs[i] = srcCE;
                     }
                  } else {
                     if (!isModified) {
                        for(int j = 0; j < i; ++j) {
                           this.modifiedCEs[j] = srcCEs[srcIndex + j];
                        }

                        isModified = true;
                     }

                     this.modifiedCEs[i] = ce;
                  }
               }

               if (isModified) {
                  ce32 = this.dest.encodeCEs(this.modifiedCEs, length);
               } else {
                  ce32 = this.dest.encodeExpansion(srcCEs, srcIndex, length);
               }
            } else if (tag == 7) {
               ConditionalCE32 cond = this.src.getConditionalCE32ForCE32(ce32);

               assert !cond.hasContext();

               srcIndex = this.dest.addConditionalCE32(cond.context, this.copyCE32(cond.ce32));

               ConditionalCE32 prevDestCond;
               for(ce32 = CollationDataBuilder.makeBuilderContextCE32(srcIndex); cond.next >= 0; prevDestCond.next = srcIndex) {
                  cond = this.src.getConditionalCE32(cond.next);
                  prevDestCond = this.dest.getConditionalCE32(srcIndex);
                  srcIndex = this.dest.addConditionalCE32(cond.context, this.copyCE32(cond.ce32));
                  int suffixStart = cond.prefixLength() + 1;
                  this.dest.unsafeBackwardSet.addAll((CharSequence)cond.context.substring(suffixStart));
               }
            } else {
               assert tag == 1 || tag == 2 || tag == 4 || tag == 12;
            }
         }

         return ce32;
      }
   }

   private static final class ConditionalCE32 {
      String context;
      int ce32;
      int defaultCE32;
      int builtCE32;
      int next;

      ConditionalCE32(String ct, int ce) {
         this.context = ct;
         this.ce32 = ce;
         this.defaultCE32 = 1;
         this.builtCE32 = 1;
         this.next = -1;
      }

      boolean hasContext() {
         return this.context.length() > 1;
      }

      int prefixLength() {
         return this.context.charAt(0);
      }
   }

   interface CEModifier {
      long modifyCE32(int var1);

      long modifyCE(long var1);
   }
}
