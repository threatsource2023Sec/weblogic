package org.python.icu.text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.icu.impl.Assert;
import org.python.icu.impl.CharTrie;
import org.python.icu.impl.CharacterIteration;
import org.python.icu.impl.ICUBinary;
import org.python.icu.impl.ICUDebug;
import org.python.icu.lang.UCharacter;

public class RuleBasedBreakIterator extends BreakIterator {
   private static final int START_STATE = 1;
   private static final int STOP_STATE = 0;
   private static final int RBBI_START = 0;
   private static final int RBBI_RUN = 1;
   private static final int RBBI_END = 2;
   private CharacterIterator fText;
   RBBIDataWrapper fRData;
   private int fLastRuleStatusIndex;
   private boolean fLastStatusIndexValid;
   private int fDictionaryCharCount;
   private static final String RBBI_DEBUG_ARG = "rbbi";
   private static final boolean TRACE = ICUDebug.enabled("rbbi") && ICUDebug.value("rbbi").indexOf("trace") >= 0;
   private int fBreakType;
   private static final UnhandledBreakEngine gUnhandledBreakEngine = new UnhandledBreakEngine();
   private static final List gAllBreakEngines = new ArrayList();
   private List fBreakEngines;
   private int[] fCachedBreakPositions;
   private int fPositionInCache;
   static final String fDebugEnv;
   private static final int kMaxLookaheads = 8;
   private LookAheadResults fLookAheadMatches;

   private RuleBasedBreakIterator() {
      this.fText = new java.text.StringCharacterIterator("");
      this.fBreakType = 2;
      this.fLookAheadMatches = new LookAheadResults();
      this.fLastStatusIndexValid = true;
      this.fDictionaryCharCount = 0;
      synchronized(gAllBreakEngines) {
         this.fBreakEngines = new ArrayList(gAllBreakEngines);
      }
   }

   public static RuleBasedBreakIterator getInstanceFromCompiledRules(InputStream is) throws IOException {
      RuleBasedBreakIterator This = new RuleBasedBreakIterator();
      This.fRData = RBBIDataWrapper.get(ICUBinary.getByteBufferFromInputStreamAndCloseStream(is));
      return This;
   }

   /** @deprecated */
   @Deprecated
   public static RuleBasedBreakIterator getInstanceFromCompiledRules(ByteBuffer bytes) throws IOException {
      RuleBasedBreakIterator This = new RuleBasedBreakIterator();
      This.fRData = RBBIDataWrapper.get(bytes);
      return This;
   }

   public RuleBasedBreakIterator(String rules) {
      this();

      try {
         ByteArrayOutputStream ruleOS = new ByteArrayOutputStream();
         compileRules(rules, ruleOS);
         this.fRData = RBBIDataWrapper.get(ByteBuffer.wrap(ruleOS.toByteArray()));
      } catch (IOException var4) {
         RuntimeException rte = new RuntimeException("RuleBasedBreakIterator rule compilation internal error: " + var4.getMessage());
         throw rte;
      }
   }

   public Object clone() {
      RuleBasedBreakIterator result = (RuleBasedBreakIterator)super.clone();
      if (this.fText != null) {
         result.fText = (CharacterIterator)((CharacterIterator)this.fText.clone());
      }

      synchronized(gAllBreakEngines) {
         result.fBreakEngines = new ArrayList(gAllBreakEngines);
      }

      result.fLookAheadMatches = new LookAheadResults();
      if (this.fCachedBreakPositions != null) {
         result.fCachedBreakPositions = (int[])this.fCachedBreakPositions.clone();
      }

      return result;
   }

   public boolean equals(Object that) {
      if (that == null) {
         return false;
      } else if (this == that) {
         return true;
      } else {
         try {
            RuleBasedBreakIterator other = (RuleBasedBreakIterator)that;
            if (this.fRData == other.fRData || this.fRData != null && other.fRData != null) {
               if (this.fRData != null && other.fRData != null && !this.fRData.fRuleSource.equals(other.fRData.fRuleSource)) {
                  return false;
               } else if (this.fText == null && other.fText == null) {
                  return true;
               } else {
                  return this.fText != null && other.fText != null ? this.fText.equals(other.fText) : false;
               }
            } else {
               return false;
            }
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public String toString() {
      String retStr = "";
      if (this.fRData != null) {
         retStr = this.fRData.fRuleSource;
      }

      return retStr;
   }

   public int hashCode() {
      return this.fRData.fRuleSource.hashCode();
   }

   private void reset() {
      this.fCachedBreakPositions = null;
      this.fDictionaryCharCount = 0;
      this.fPositionInCache = 0;
   }

   /** @deprecated */
   @Deprecated
   public void dump(PrintStream out) {
      if (out == null) {
         out = System.out;
      }

      this.fRData.dump(out);
   }

   public static void compileRules(String rules, OutputStream ruleBinary) throws IOException {
      RBBIRuleBuilder.compileRules(rules, ruleBinary);
   }

   public int first() {
      this.fCachedBreakPositions = null;
      this.fDictionaryCharCount = 0;
      this.fPositionInCache = 0;
      this.fLastRuleStatusIndex = 0;
      this.fLastStatusIndexValid = true;
      if (this.fText == null) {
         return -1;
      } else {
         this.fText.first();
         return this.fText.getIndex();
      }
   }

   public int last() {
      this.fCachedBreakPositions = null;
      this.fDictionaryCharCount = 0;
      this.fPositionInCache = 0;
      if (this.fText == null) {
         this.fLastRuleStatusIndex = 0;
         this.fLastStatusIndexValid = true;
         return -1;
      } else {
         this.fLastStatusIndexValid = false;
         int pos = this.fText.getEndIndex();
         this.fText.setIndex(pos);
         return pos;
      }
   }

   public int next(int n) {
      int result;
      for(result = this.current(); n > 0; --n) {
         result = this.next();
      }

      while(n < 0) {
         result = this.previous();
         ++n;
      }

      return result;
   }

   public int next() {
      int startPos;
      if (this.fCachedBreakPositions != null) {
         if (this.fPositionInCache < this.fCachedBreakPositions.length - 1) {
            ++this.fPositionInCache;
            startPos = this.fCachedBreakPositions[this.fPositionInCache];
            this.fText.setIndex(startPos);
            return startPos;
         }

         this.reset();
      }

      startPos = this.current();
      this.fDictionaryCharCount = 0;
      int result = this.handleNext(this.fRData.fFTable);
      if (this.fDictionaryCharCount > 0) {
         result = this.checkDictionary(startPos, result, false);
      }

      return result;
   }

   private int checkDictionary(int startPos, int endPos, boolean reverse) {
      this.reset();
      if (endPos - startPos <= 1) {
         return reverse ? startPos : endPos;
      } else {
         this.fText.setIndex(reverse ? endPos : startPos);
         if (reverse) {
            CharacterIteration.previous32(this.fText);
         }

         int rangeStart = startPos;
         int rangeEnd = endPos;
         DictionaryBreakEngine.DequeI breaks = new DictionaryBreakEngine.DequeI();
         int foundBreakCount = 0;
         int c = CharacterIteration.current32(this.fText);
         int category = (short)this.fRData.fTrie.getCodePointValue(c);
         if ((category & 16384) != 0) {
            if (reverse) {
               do {
                  CharacterIteration.next32(this.fText);
                  c = CharacterIteration.current32(this.fText);
                  category = (short)this.fRData.fTrie.getCodePointValue(c);
               } while(c != Integer.MAX_VALUE && (category & 16384) != 0);

               rangeEnd = this.fText.getIndex();
               if (c == Integer.MAX_VALUE) {
                  c = CharacterIteration.previous32(this.fText);
               } else {
                  c = CharacterIteration.previous32(this.fText);
               }
            } else {
               do {
                  c = CharacterIteration.previous32(this.fText);
                  category = (short)this.fRData.fTrie.getCodePointValue(c);
               } while(c != Integer.MAX_VALUE && (category & 16384) != 0);

               if (c == Integer.MAX_VALUE) {
                  c = CharacterIteration.current32(this.fText);
               } else {
                  CharacterIteration.next32(this.fText);
                  c = CharacterIteration.current32(this.fText);
               }

               rangeStart = this.fText.getIndex();
            }

            category = (short)this.fRData.fTrie.getCodePointValue(c);
         }

         if (reverse) {
            this.fText.setIndex(rangeStart);
            c = CharacterIteration.current32(this.fText);
            category = (short)this.fRData.fTrie.getCodePointValue(c);
         }

         LanguageBreakEngine lbe = null;

         while(true) {
            int current;
            while((current = this.fText.getIndex()) >= rangeEnd || (category & 16384) != 0) {
               int i;
               if (current >= rangeEnd) {
                  if (foundBreakCount <= 0) {
                     this.fText.setIndex(reverse ? startPos : endPos);
                     return reverse ? startPos : endPos;
                  }

                  if (foundBreakCount != breaks.size()) {
                     System.out.println("oops, foundBreakCount != breaks.size().  LBE = " + lbe.getClass());
                  }

                  assert foundBreakCount == breaks.size();

                  if (startPos < breaks.peekLast()) {
                     breaks.offer(startPos);
                  }

                  if (endPos > breaks.peek()) {
                     breaks.push(endPos);
                  }

                  this.fCachedBreakPositions = new int[breaks.size()];

                  for(i = 0; breaks.size() > 0; this.fCachedBreakPositions[i++] = breaks.pollLast()) {
                  }

                  if (reverse) {
                     return this.preceding(endPos);
                  }

                  return this.following(startPos);
               }

               lbe = this.getLanguageBreakEngine(c);
               if (lbe != null) {
                  i = this.fText.getIndex();
                  foundBreakCount += lbe.findBreaks(this.fText, rangeStart, rangeEnd, false, this.fBreakType, breaks);

                  assert this.fText.getIndex() > i;
               }

               c = CharacterIteration.current32(this.fText);
               category = (short)this.fRData.fTrie.getCodePointValue(c);
            }

            CharacterIteration.next32(this.fText);
            c = CharacterIteration.current32(this.fText);
            category = (short)this.fRData.fTrie.getCodePointValue(c);
         }
      }
   }

   public int previous() {
      CharacterIterator text = this.getText();
      this.fLastStatusIndexValid = false;
      int start;
      if (this.fCachedBreakPositions != null) {
         if (this.fPositionInCache > 0) {
            --this.fPositionInCache;
            if (this.fPositionInCache <= 0) {
               this.fLastStatusIndexValid = false;
            }

            start = this.fCachedBreakPositions[this.fPositionInCache];
            text.setIndex(start);
            return start;
         }

         this.reset();
      }

      int startPos = this.current();
      if (this.fText != null && startPos != this.fText.getBeginIndex()) {
         int result;
         if (this.fRData.fSRTable == null && this.fRData.fSFTable == null) {
            start = this.current();
            CharacterIteration.previous32(this.fText);
            int lastResult = this.handlePrevious(this.fRData.fRTable);
            if (lastResult == -1) {
               lastResult = this.fText.getBeginIndex();
               this.fText.setIndex(lastResult);
            }

            int lastTag = 0;
            boolean breakTagValid = false;

            while(true) {
               result = this.next();
               if (result == -1 || result >= start) {
                  this.fText.setIndex(lastResult);
                  this.fLastRuleStatusIndex = lastTag;
                  this.fLastStatusIndexValid = breakTagValid;
                  return lastResult;
               }

               lastResult = result;
               lastTag = this.fLastRuleStatusIndex;
               breakTagValid = true;
            }
         } else {
            result = this.handlePrevious(this.fRData.fRTable);
            if (this.fDictionaryCharCount > 0) {
               result = this.checkDictionary(result, startPos, true);
            }

            return result;
         }
      } else {
         this.fLastRuleStatusIndex = 0;
         this.fLastStatusIndexValid = true;
         return -1;
      }
   }

   public int following(int offset) {
      CharacterIterator text = this.getText();
      if (this.fCachedBreakPositions != null && offset >= this.fCachedBreakPositions[0] && offset < this.fCachedBreakPositions[this.fCachedBreakPositions.length - 1]) {
         for(this.fPositionInCache = 0; this.fPositionInCache < this.fCachedBreakPositions.length && offset >= this.fCachedBreakPositions[this.fPositionInCache]; ++this.fPositionInCache) {
         }

         text.setIndex(this.fCachedBreakPositions[this.fPositionInCache]);
         return text.getIndex();
      } else {
         this.fCachedBreakPositions = null;
         return this.rulesFollowing(offset);
      }
   }

   private int rulesFollowing(int offset) {
      this.fLastRuleStatusIndex = 0;
      this.fLastStatusIndexValid = true;
      if (this.fText != null && offset < this.fText.getEndIndex()) {
         if (offset < this.fText.getBeginIndex()) {
            return this.first();
         } else {
            int result = false;
            int result;
            if (this.fRData.fSRTable != null) {
               this.fText.setIndex(offset);
               CharacterIteration.next32(this.fText);
               this.handlePrevious(this.fRData.fSRTable);

               for(result = this.next(); result <= offset; result = this.next()) {
               }

               return result;
            } else if (this.fRData.fSFTable != null) {
               this.fText.setIndex(offset);
               CharacterIteration.previous32(this.fText);
               this.handleNext(this.fRData.fSFTable);

               for(int oldresult = this.previous(); oldresult > offset; oldresult = result) {
                  result = this.previous();
                  if (result <= offset) {
                     return oldresult;
                  }
               }

               result = this.next();
               if (result <= offset) {
                  return this.next();
               } else {
                  return result;
               }
            } else {
               this.fText.setIndex(offset);
               if (offset == this.fText.getBeginIndex()) {
                  return this.next();
               } else {
                  for(result = this.previous(); result != -1 && result <= offset; result = this.next()) {
                  }

                  return result;
               }
            }
         }
      } else {
         this.last();
         return this.next();
      }
   }

   public int preceding(int offset) {
      CharacterIterator text = this.getText();
      if (this.fCachedBreakPositions != null && offset > this.fCachedBreakPositions[0] && offset <= this.fCachedBreakPositions[this.fCachedBreakPositions.length - 1]) {
         for(this.fPositionInCache = 0; this.fPositionInCache < this.fCachedBreakPositions.length && offset > this.fCachedBreakPositions[this.fPositionInCache]; ++this.fPositionInCache) {
         }

         --this.fPositionInCache;
         text.setIndex(this.fCachedBreakPositions[this.fPositionInCache]);
         return text.getIndex();
      } else {
         this.fCachedBreakPositions = null;
         return this.rulesPreceding(offset);
      }
   }

   private int rulesPreceding(int offset) {
      if (this.fText != null && offset <= this.fText.getEndIndex()) {
         if (offset < this.fText.getBeginIndex()) {
            return this.first();
         } else {
            int result;
            if (this.fRData.fSFTable == null) {
               if (this.fRData.fSRTable != null) {
                  this.fText.setIndex(offset);
                  CharacterIteration.next32(this.fText);
                  this.handlePrevious(this.fRData.fSRTable);

                  for(int oldresult = this.next(); oldresult < offset; oldresult = result) {
                     result = this.next();
                     if (result >= offset) {
                        return oldresult;
                     }
                  }

                  result = this.previous();
                  if (result >= offset) {
                     return this.previous();
                  } else {
                     return result;
                  }
               } else {
                  this.fText.setIndex(offset);
                  return this.previous();
               }
            } else {
               this.fText.setIndex(offset);
               CharacterIteration.previous32(this.fText);
               this.handleNext(this.fRData.fSFTable);

               for(result = this.previous(); result >= offset; result = this.previous()) {
               }

               return result;
            }
         }
      } else {
         return this.last();
      }
   }

   protected static final void checkOffset(int offset, CharacterIterator text) {
      if (offset < text.getBeginIndex() || offset > text.getEndIndex()) {
         throw new IllegalArgumentException("offset out of bounds");
      }
   }

   public boolean isBoundary(int offset) {
      checkOffset(offset, this.fText);
      if (offset == this.fText.getBeginIndex()) {
         this.first();
         return true;
      } else if (offset == this.fText.getEndIndex()) {
         this.last();
         return true;
      } else {
         this.fText.setIndex(offset);
         CharacterIteration.previous32(this.fText);
         int pos = this.fText.getIndex();
         boolean result = this.following(pos) == offset;
         return result;
      }
   }

   public int current() {
      return this.fText != null ? this.fText.getIndex() : -1;
   }

   private void makeRuleStatusValid() {
      if (!this.fLastStatusIndexValid) {
         int curr = this.current();
         if (curr != -1 && curr != this.fText.getBeginIndex()) {
            int pa = this.fText.getIndex();
            this.first();

            int pb;
            for(pb = this.current(); this.fText.getIndex() < pa; pb = this.next()) {
            }

            Assert.assrt(pa == pb);
         } else {
            this.fLastRuleStatusIndex = 0;
            this.fLastStatusIndexValid = true;
         }

         Assert.assrt(this.fLastStatusIndexValid);
         Assert.assrt(this.fLastRuleStatusIndex >= 0 && this.fLastRuleStatusIndex < this.fRData.fStatusTable.length);
      }

   }

   public int getRuleStatus() {
      this.makeRuleStatusValid();
      int idx = this.fLastRuleStatusIndex + this.fRData.fStatusTable[this.fLastRuleStatusIndex];
      int tagVal = this.fRData.fStatusTable[idx];
      return tagVal;
   }

   public int getRuleStatusVec(int[] fillInArray) {
      this.makeRuleStatusValid();
      int numStatusVals = this.fRData.fStatusTable[this.fLastRuleStatusIndex];
      if (fillInArray != null) {
         int numToCopy = Math.min(numStatusVals, fillInArray.length);

         for(int i = 0; i < numToCopy; ++i) {
            fillInArray[i] = this.fRData.fStatusTable[this.fLastRuleStatusIndex + i + 1];
         }
      }

      return numStatusVals;
   }

   public CharacterIterator getText() {
      return this.fText;
   }

   public void setText(CharacterIterator newText) {
      this.fText = newText;
      this.first();
   }

   void setBreakType(int type) {
      this.fBreakType = type;
   }

   int getBreakType() {
      return this.fBreakType;
   }

   private LanguageBreakEngine getLanguageBreakEngine(int c) {
      Iterator var2 = this.fBreakEngines.iterator();

      while(var2.hasNext()) {
         LanguageBreakEngine candidate = (LanguageBreakEngine)var2.next();
         if (candidate.handles(c, this.fBreakType)) {
            return candidate;
         }
      }

      synchronized(gAllBreakEngines) {
         Iterator var9 = gAllBreakEngines.iterator();

         while(var9.hasNext()) {
            LanguageBreakEngine candidate = (LanguageBreakEngine)var9.next();
            if (candidate.handles(c, this.fBreakType)) {
               this.fBreakEngines.add(candidate);
               return candidate;
            }
         }

         int script = UCharacter.getIntPropertyValue(c, 4106);
         if (script == 22 || script == 20) {
            script = 17;
         }

         Object eng;
         try {
            switch (script) {
               case 17:
                  if (this.getBreakType() == 1) {
                     eng = new CjkBreakEngine(false);
                  } else {
                     gUnhandledBreakEngine.handleChar(c, this.getBreakType());
                     eng = gUnhandledBreakEngine;
                  }
                  break;
               case 18:
                  if (this.getBreakType() == 1) {
                     eng = new CjkBreakEngine(true);
                  } else {
                     gUnhandledBreakEngine.handleChar(c, this.getBreakType());
                     eng = gUnhandledBreakEngine;
                  }
                  break;
               case 23:
                  eng = new KhmerBreakEngine();
                  break;
               case 24:
                  eng = new LaoBreakEngine();
                  break;
               case 28:
                  eng = new BurmeseBreakEngine();
                  break;
               case 38:
                  eng = new ThaiBreakEngine();
                  break;
               default:
                  gUnhandledBreakEngine.handleChar(c, this.getBreakType());
                  eng = gUnhandledBreakEngine;
            }
         } catch (IOException var7) {
            eng = null;
         }

         if (eng != null && eng != gUnhandledBreakEngine) {
            gAllBreakEngines.add(eng);
            this.fBreakEngines.add(eng);
         }

         return (LanguageBreakEngine)eng;
      }
   }

   private int handleNext(short[] stateTable) {
      if (TRACE) {
         System.out.println("Handle Next   pos      char  state category");
      }

      this.fLastStatusIndexValid = true;
      this.fLastRuleStatusIndex = 0;
      CharacterIterator text = this.fText;
      CharTrie trie = this.fRData.fTrie;
      int c = text.current();
      if (c >= 55296) {
         c = CharacterIteration.nextTrail32(text, c);
         if (c == Integer.MAX_VALUE) {
            return -1;
         }
      }

      int initialPosition = text.getIndex();
      int result = initialPosition;
      int state = 1;
      int row = this.fRData.getRowIndex(state);
      short category = 3;
      int flagsState = this.fRData.getStateTableFlags(stateTable);
      int mode = 1;
      if ((flagsState & 2) != 0) {
         category = 2;
         mode = 0;
         if (TRACE) {
            System.out.print("            " + RBBIDataWrapper.intToString(text.getIndex(), 5));
            System.out.print(RBBIDataWrapper.intToHexString(c, 10));
            System.out.println(RBBIDataWrapper.intToString(state, 7) + RBBIDataWrapper.intToString(category, 6));
         }
      }

      this.fLookAheadMatches.reset();

      while(state != 0) {
         if (c == Integer.MAX_VALUE) {
            if (mode == 2) {
               break;
            }

            mode = 2;
            category = 1;
         } else if (mode == 1) {
            category = (short)trie.getCodePointValue(c);
            if ((category & 16384) != 0) {
               ++this.fDictionaryCharCount;
               category &= -16385;
            }

            if (TRACE) {
               System.out.print("            " + RBBIDataWrapper.intToString(text.getIndex(), 5));
               System.out.print(RBBIDataWrapper.intToHexString(c, 10));
               System.out.println(RBBIDataWrapper.intToString(state, 7) + RBBIDataWrapper.intToString(category, 6));
            }

            c = text.next();
            if (c >= 55296) {
               c = CharacterIteration.nextTrail32(text, c);
            }
         } else {
            mode = 1;
         }

         state = stateTable[row + 4 + category];
         row = this.fRData.getRowIndex(state);
         if (stateTable[row + 0] == -1) {
            result = text.getIndex();
            if (c >= 65536 && c <= 1114111) {
               --result;
            }

            this.fLastRuleStatusIndex = stateTable[row + 2];
         }

         int completedRule = stateTable[row + 0];
         if (completedRule > 0) {
            int lookaheadResult = this.fLookAheadMatches.getPosition(completedRule);
            if (lookaheadResult >= 0) {
               this.fLastRuleStatusIndex = stateTable[row + 2];
               text.setIndex(lookaheadResult);
               return lookaheadResult;
            }
         }

         int rule = stateTable[row + 1];
         if (rule != 0) {
            int pos = text.getIndex();
            if (c >= 65536 && c <= 1114111) {
               --pos;
            }

            this.fLookAheadMatches.setPosition(rule, pos);
         }
      }

      if (result == initialPosition) {
         if (TRACE) {
            System.out.println("Iterator did not move. Advancing by 1.");
         }

         text.setIndex(initialPosition);
         CharacterIteration.next32(text);
         result = text.getIndex();
      } else {
         text.setIndex(result);
      }

      if (TRACE) {
         System.out.println("result = " + result);
      }

      return result;
   }

   private int handlePrevious(short[] stateTable) {
      if (this.fText != null && stateTable != null) {
         int category = false;
         int result = false;
         int initialPosition = false;
         this.fLookAheadMatches.reset();
         this.fLastStatusIndexValid = false;
         this.fLastRuleStatusIndex = 0;
         int initialPosition = this.fText.getIndex();
         int result = initialPosition;
         int c = CharacterIteration.previous32(this.fText);
         int state = 1;
         int row = this.fRData.getRowIndex(state);
         int category = 3;
         int mode = 1;
         if ((this.fRData.getStateTableFlags(stateTable) & 2) != 0) {
            category = 2;
            mode = 0;
         }

         if (TRACE) {
            System.out.println("Handle Prev   pos   char  state category ");
         }

         while(true) {
            if (c == Integer.MAX_VALUE) {
               if (mode == 2 || this.fRData.fHeader.fVersion == 1) {
                  if (result == initialPosition) {
                     this.fText.setIndex(initialPosition);
                     CharacterIteration.previous32(this.fText);
                  }
                  break;
               }

               mode = 2;
               category = 1;
            }

            if (mode == 1) {
               category = (short)this.fRData.fTrie.getCodePointValue(c);
               if ((category & 16384) != 0) {
                  ++this.fDictionaryCharCount;
                  category &= -16385;
               }
            }

            if (TRACE) {
               System.out.print("             " + this.fText.getIndex() + "   ");
               if (32 <= c && c < 127) {
                  System.out.print("  " + c + "  ");
               } else {
                  System.out.print(" " + Integer.toHexString(c) + " ");
               }

               System.out.println(" " + state + "  " + category + " ");
            }

            state = stateTable[row + 4 + category];
            row = this.fRData.getRowIndex(state);
            if (stateTable[row + 0] == -1) {
               result = this.fText.getIndex();
            }

            int completedRule = stateTable[row + 0];
            if (completedRule > 0) {
               int lookaheadResult = this.fLookAheadMatches.getPosition(completedRule);
               if (lookaheadResult >= 0) {
                  result = lookaheadResult;
                  break;
               }
            }

            int rule = stateTable[row + 1];
            if (rule != 0) {
               int pos = this.fText.getIndex();
               this.fLookAheadMatches.setPosition(rule, pos);
            }

            if (state == 0) {
               break;
            }

            if (mode == 1) {
               c = CharacterIteration.previous32(this.fText);
            } else if (mode == 0) {
               mode = 1;
            }
         }

         if (result == initialPosition) {
            this.fText.setIndex(initialPosition);
            CharacterIteration.previous32(this.fText);
            result = this.fText.getIndex();
         }

         this.fText.setIndex(result);
         if (TRACE) {
            System.out.println("Result = " + result);
         }

         return result;
      } else {
         return 0;
      }
   }

   static {
      gAllBreakEngines.add(gUnhandledBreakEngine);
      fDebugEnv = ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null;
   }

   private static class LookAheadResults {
      int fUsedSlotLimit = 0;
      int[] fPositions = new int[8];
      int[] fKeys = new int[8];

      LookAheadResults() {
      }

      int getPosition(int key) {
         for(int i = 0; i < this.fUsedSlotLimit; ++i) {
            if (this.fKeys[i] == key) {
               return this.fPositions[i];
            }
         }

         assert false;

         return -1;
      }

      void setPosition(int key, int position) {
         int i;
         for(i = 0; i < this.fUsedSlotLimit; ++i) {
            if (this.fKeys[i] == key) {
               this.fPositions[i] = position;
               return;
            }
         }

         if (i >= 8) {
            assert false;

            i = 7;
         }

         this.fKeys[i] = key;
         this.fPositions[i] = position;

         assert this.fUsedSlotLimit == i;

         this.fUsedSlotLimit = i + 1;
      }

      void reset() {
         this.fUsedSlotLimit = 0;
      }
   }
}
