package org.python.icu.text;

import java.text.CharacterIterator;
import java.util.BitSet;
import org.python.icu.impl.CharacterIteration;

abstract class DictionaryBreakEngine implements LanguageBreakEngine {
   UnicodeSet fSet = new UnicodeSet();
   private BitSet fTypes = new BitSet(32);

   public DictionaryBreakEngine(Integer... breakTypes) {
      Integer[] var2 = breakTypes;
      int var3 = breakTypes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Integer type = var2[var4];
         this.fTypes.set(type);
      }

   }

   public boolean handles(int c, int breakType) {
      return this.fTypes.get(breakType) && this.fSet.contains(c);
   }

   public int findBreaks(CharacterIterator text, int startPos, int endPos, boolean reverse, int breakType, DequeI foundBreaks) {
      int result = false;
      int start = text.getIndex();
      int c = CharacterIteration.current32(text);
      int current;
      int rangeStart;
      int rangeEnd;
      if (reverse) {
         boolean isDict;
         for(isDict = this.fSet.contains(c); (current = text.getIndex()) > startPos && isDict; isDict = this.fSet.contains(c)) {
            c = CharacterIteration.previous32(text);
         }

         rangeStart = current < startPos ? startPos : current + (isDict ? 0 : 1);
         rangeEnd = start + 1;
      } else {
         while(true) {
            if ((current = text.getIndex()) >= endPos || !this.fSet.contains(c)) {
               rangeStart = start;
               rangeEnd = current;
               break;
            }

            CharacterIteration.next32(text);
            c = CharacterIteration.current32(text);
         }
      }

      int result = this.divideUpDictionaryRange(text, rangeStart, rangeEnd, foundBreaks);
      text.setIndex(current);
      return result;
   }

   void setCharacters(UnicodeSet set) {
      this.fSet = new UnicodeSet(set);
      this.fSet.compact();
   }

   abstract int divideUpDictionaryRange(CharacterIterator var1, int var2, int var3, DequeI var4);

   static class DequeI {
      private int[] data = new int[50];
      private int lastIdx = 4;
      private int firstIdx = 4;

      int size() {
         return this.firstIdx - this.lastIdx;
      }

      boolean isEmpty() {
         return this.size() == 0;
      }

      private void grow() {
         int[] newData = new int[this.data.length * 2];
         System.arraycopy(this.data, 0, newData, 0, this.data.length);
         this.data = newData;
      }

      void offer(int v) {
         assert this.lastIdx > 0;

         this.data[--this.lastIdx] = v;
      }

      void push(int v) {
         if (this.firstIdx >= this.data.length) {
            this.grow();
         }

         this.data[this.firstIdx++] = v;
      }

      int pop() {
         assert this.size() > 0;

         return this.data[--this.firstIdx];
      }

      int peek() {
         assert this.size() > 0;

         return this.data[this.firstIdx - 1];
      }

      int peekLast() {
         assert this.size() > 0;

         return this.data[this.lastIdx];
      }

      int pollLast() {
         assert this.size() > 0;

         return this.data[this.lastIdx++];
      }

      boolean contains(int v) {
         for(int i = this.lastIdx; i < this.firstIdx; ++i) {
            if (this.data[i] == v) {
               return true;
            }
         }

         return false;
      }
   }

   static class PossibleWord {
      private static final int POSSIBLE_WORD_LIST_MAX = 20;
      private int[] lengths = new int[20];
      private int[] count = new int[1];
      private int prefix;
      private int offset = -1;
      private int mark;
      private int current;

      public PossibleWord() {
      }

      public int candidates(CharacterIterator fIter, DictionaryMatcher dict, int rangeEnd) {
         int start = fIter.getIndex();
         if (start != this.offset) {
            this.offset = start;
            this.prefix = dict.matches(fIter, rangeEnd - start, this.lengths, this.count, this.lengths.length);
            if (this.count[0] <= 0) {
               fIter.setIndex(start);
            }
         }

         if (this.count[0] > 0) {
            fIter.setIndex(start + this.lengths[this.count[0] - 1]);
         }

         this.current = this.count[0] - 1;
         this.mark = this.current;
         return this.count[0];
      }

      public int acceptMarked(CharacterIterator fIter) {
         fIter.setIndex(this.offset + this.lengths[this.mark]);
         return this.lengths[this.mark];
      }

      public boolean backUp(CharacterIterator fIter) {
         if (this.current > 0) {
            fIter.setIndex(this.offset + this.lengths[--this.current]);
            return true;
         } else {
            return false;
         }
      }

      public int longestPrefix() {
         return this.prefix;
      }

      public void markCurrent() {
         this.mark = this.current;
      }
   }
}
