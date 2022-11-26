package org.python.icu.impl.coll;

import java.util.Iterator;
import org.python.icu.impl.Trie2;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.CharsTrie;

public final class ContractionsAndExpansions {
   private CollationData data;
   private UnicodeSet contractions;
   private UnicodeSet expansions;
   private CESink sink;
   private boolean addPrefixes;
   private int checkTailored = 0;
   private UnicodeSet tailored = new UnicodeSet();
   private UnicodeSet ranges;
   private StringBuilder unreversedPrefix = new StringBuilder();
   private String suffix;
   private long[] ces = new long[31];

   public ContractionsAndExpansions(UnicodeSet con, UnicodeSet exp, CESink s, boolean prefixes) {
      this.contractions = con;
      this.expansions = exp;
      this.sink = s;
      this.addPrefixes = prefixes;
   }

   public void forData(CollationData d) {
      if (d.base != null) {
         this.checkTailored = -1;
      }

      this.data = d;
      Iterator trieIterator = this.data.trie.iterator();

      Trie2.Range range;
      while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
         this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
      }

      if (d.base != null) {
         this.tailored.freeze();
         this.checkTailored = 1;
         this.data = d.base;
         trieIterator = this.data.trie.iterator();

         while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
         }

      }
   }

   private void enumCnERange(int start, int end, int ce32, ContractionsAndExpansions cne) {
      if (cne.checkTailored != 0) {
         if (cne.checkTailored < 0) {
            if (ce32 == 192) {
               return;
            }

            cne.tailored.add(start, end);
         } else if (start == end) {
            if (cne.tailored.contains(start)) {
               return;
            }
         } else if (cne.tailored.containsSome(start, end)) {
            if (cne.ranges == null) {
               cne.ranges = new UnicodeSet();
            }

            cne.ranges.set(start, end).removeAll(cne.tailored);
            int count = cne.ranges.getRangeCount();

            for(int i = 0; i < count; ++i) {
               cne.handleCE32(cne.ranges.getRangeStart(i), cne.ranges.getRangeEnd(i), ce32);
            }
         }
      }

      cne.handleCE32(start, end, ce32);
   }

   public void forCodePoint(CollationData d, int c) {
      int ce32 = d.getCE32(c);
      if (ce32 == 192) {
         d = d.base;
         ce32 = d.getCE32(c);
      }

      this.data = d;
      this.handleCE32(c, c, ce32);
   }

   private void handleCE32(int var1, int var2, int var3) {
      // $FF: Couldn't be decompiled
   }

   private void handlePrefixes(int start, int end, int ce32) {
      int index = Collation.indexFromCE32(ce32);
      ce32 = this.data.getCE32FromContexts(index);
      this.handleCE32(start, end, ce32);
      if (this.addPrefixes) {
         CharsTrie.Iterator prefixes = (new CharsTrie(this.data.contexts, index + 2)).iterator();

         while(prefixes.hasNext()) {
            CharsTrie.Entry e = prefixes.next();
            this.setPrefix(e.chars);
            this.addStrings(start, end, this.contractions);
            this.addStrings(start, end, this.expansions);
            this.handleCE32(start, end, e.value);
         }

         this.resetPrefix();
      }
   }

   void handleContractions(int start, int end, int ce32) {
      int index = Collation.indexFromCE32(ce32);
      if ((ce32 & 256) != 0) {
         assert this.unreversedPrefix.length() != 0;
      } else {
         ce32 = this.data.getCE32FromContexts(index);

         assert !Collation.isContractionCE32(ce32);

         this.handleCE32(start, end, ce32);
      }

      CharsTrie.Entry e;
      for(CharsTrie.Iterator suffixes = (new CharsTrie(this.data.contexts, index + 2)).iterator(); suffixes.hasNext(); this.handleCE32(start, end, e.value)) {
         e = suffixes.next();
         this.suffix = e.chars.toString();
         this.addStrings(start, end, this.contractions);
         if (this.unreversedPrefix.length() != 0) {
            this.addStrings(start, end, this.expansions);
         }
      }

      this.suffix = null;
   }

   void addExpansions(int start, int end) {
      if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
         if (this.expansions != null) {
            this.expansions.add(start, end);
         }
      } else {
         this.addStrings(start, end, this.expansions);
      }

   }

   void addStrings(int start, int end, UnicodeSet set) {
      if (set != null) {
         StringBuilder s = new StringBuilder(this.unreversedPrefix);

         do {
            s.appendCodePoint(start);
            if (this.suffix != null) {
               s.append(this.suffix);
            }

            set.add((CharSequence)s);
            s.setLength(this.unreversedPrefix.length());
            ++start;
         } while(start <= end);

      }
   }

   private void setPrefix(CharSequence pfx) {
      this.unreversedPrefix.setLength(0);
      this.unreversedPrefix.append(pfx).reverse();
   }

   private void resetPrefix() {
      this.unreversedPrefix.setLength(0);
   }

   public interface CESink {
      void handleCE(long var1);

      void handleExpansion(long[] var1, int var2, int var3);
   }
}
