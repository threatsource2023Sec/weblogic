package org.python.icu.text;

import java.nio.CharBuffer;
import java.text.CharacterIterator;
import org.python.icu.impl.Norm2AllModes;
import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.impl.UCaseProps;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.ICUCloneNotSupportedException;

public final class Normalizer implements Cloneable {
   private UCharacterIterator text;
   private Normalizer2 norm2;
   private Mode mode;
   private int options;
   private int currentIndex;
   private int nextIndex;
   private StringBuilder buffer;
   private int bufferPos;
   /** @deprecated */
   @Deprecated
   public static final int UNICODE_3_2 = 32;
   /** @deprecated */
   @Deprecated
   public static final int DONE = -1;
   /** @deprecated */
   @Deprecated
   public static final Mode NONE = new NONEMode();
   /** @deprecated */
   @Deprecated
   public static final Mode NFD = new NFDMode();
   /** @deprecated */
   @Deprecated
   public static final Mode NFKD = new NFKDMode();
   /** @deprecated */
   @Deprecated
   public static final Mode NFC = new NFCMode();
   /** @deprecated */
   @Deprecated
   public static final Mode DEFAULT;
   /** @deprecated */
   @Deprecated
   public static final Mode NFKC;
   /** @deprecated */
   @Deprecated
   public static final Mode FCD;
   /** @deprecated */
   @Deprecated
   public static final Mode NO_OP;
   /** @deprecated */
   @Deprecated
   public static final Mode COMPOSE;
   /** @deprecated */
   @Deprecated
   public static final Mode COMPOSE_COMPAT;
   /** @deprecated */
   @Deprecated
   public static final Mode DECOMP;
   /** @deprecated */
   @Deprecated
   public static final Mode DECOMP_COMPAT;
   /** @deprecated */
   @Deprecated
   public static final int IGNORE_HANGUL = 1;
   public static final QuickCheckResult NO;
   public static final QuickCheckResult YES;
   public static final QuickCheckResult MAYBE;
   public static final int FOLD_CASE_DEFAULT = 0;
   public static final int INPUT_IS_FCD = 131072;
   public static final int COMPARE_IGNORE_CASE = 65536;
   public static final int COMPARE_CODE_POINT_ORDER = 32768;
   public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
   /** @deprecated */
   @Deprecated
   public static final int COMPARE_NORM_OPTIONS_SHIFT = 20;
   private static final int COMPARE_EQUIV = 524288;

   /** @deprecated */
   @Deprecated
   public Normalizer(String str, Mode mode, int opt) {
      this.text = UCharacterIterator.getInstance(str);
      this.mode = mode;
      this.options = opt;
      this.norm2 = mode.getNormalizer2(opt);
      this.buffer = new StringBuilder();
   }

   /** @deprecated */
   @Deprecated
   public Normalizer(CharacterIterator iter, Mode mode, int opt) {
      this.text = UCharacterIterator.getInstance((CharacterIterator)iter.clone());
      this.mode = mode;
      this.options = opt;
      this.norm2 = mode.getNormalizer2(opt);
      this.buffer = new StringBuilder();
   }

   /** @deprecated */
   @Deprecated
   public Normalizer(UCharacterIterator iter, Mode mode, int options) {
      try {
         this.text = (UCharacterIterator)iter.clone();
         this.mode = mode;
         this.options = options;
         this.norm2 = mode.getNormalizer2(options);
         this.buffer = new StringBuilder();
      } catch (CloneNotSupportedException var5) {
         throw new ICUCloneNotSupportedException(var5);
      }
   }

   /** @deprecated */
   @Deprecated
   public Object clone() {
      try {
         Normalizer copy = (Normalizer)super.clone();
         copy.text = (UCharacterIterator)this.text.clone();
         copy.mode = this.mode;
         copy.options = this.options;
         copy.norm2 = this.norm2;
         copy.buffer = new StringBuilder(this.buffer);
         copy.bufferPos = this.bufferPos;
         copy.currentIndex = this.currentIndex;
         copy.nextIndex = this.nextIndex;
         return copy;
      } catch (CloneNotSupportedException var2) {
         throw new ICUCloneNotSupportedException(var2);
      }
   }

   private static final Normalizer2 getComposeNormalizer2(boolean compat, int options) {
      return (compat ? NFKC : NFC).getNormalizer2(options);
   }

   private static final Normalizer2 getDecomposeNormalizer2(boolean compat, int options) {
      return (compat ? NFKD : NFD).getNormalizer2(options);
   }

   /** @deprecated */
   @Deprecated
   public static String compose(String str, boolean compat) {
      return compose(str, compat, 0);
   }

   /** @deprecated */
   @Deprecated
   public static String compose(String str, boolean compat, int options) {
      return getComposeNormalizer2(compat, options).normalize(str);
   }

   /** @deprecated */
   @Deprecated
   public static int compose(char[] source, char[] target, boolean compat, int options) {
      return compose(source, 0, source.length, target, 0, target.length, compat, options);
   }

   /** @deprecated */
   @Deprecated
   public static int compose(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, boolean compat, int options) {
      CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
      CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
      getComposeNormalizer2(compat, options).normalize(srcBuffer, (Appendable)app);
      return app.length();
   }

   /** @deprecated */
   @Deprecated
   public static String decompose(String str, boolean compat) {
      return decompose(str, compat, 0);
   }

   /** @deprecated */
   @Deprecated
   public static String decompose(String str, boolean compat, int options) {
      return getDecomposeNormalizer2(compat, options).normalize(str);
   }

   /** @deprecated */
   @Deprecated
   public static int decompose(char[] source, char[] target, boolean compat, int options) {
      return decompose(source, 0, source.length, target, 0, target.length, compat, options);
   }

   /** @deprecated */
   @Deprecated
   public static int decompose(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, boolean compat, int options) {
      CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
      CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
      getDecomposeNormalizer2(compat, options).normalize(srcBuffer, (Appendable)app);
      return app.length();
   }

   /** @deprecated */
   @Deprecated
   public static String normalize(String str, Mode mode, int options) {
      return mode.getNormalizer2(options).normalize(str);
   }

   /** @deprecated */
   @Deprecated
   public static String normalize(String src, Mode mode) {
      return normalize(src, mode, 0);
   }

   /** @deprecated */
   @Deprecated
   public static int normalize(char[] source, char[] target, Mode mode, int options) {
      return normalize(source, 0, source.length, target, 0, target.length, mode, options);
   }

   /** @deprecated */
   @Deprecated
   public static int normalize(char[] src, int srcStart, int srcLimit, char[] dest, int destStart, int destLimit, Mode mode, int options) {
      CharBuffer srcBuffer = CharBuffer.wrap(src, srcStart, srcLimit - srcStart);
      CharsAppendable app = new CharsAppendable(dest, destStart, destLimit);
      mode.getNormalizer2(options).normalize(srcBuffer, (Appendable)app);
      return app.length();
   }

   /** @deprecated */
   @Deprecated
   public static String normalize(int char32, Mode mode, int options) {
      if (mode == NFD && options == 0) {
         String decomposition = Normalizer2.getNFCInstance().getDecomposition(char32);
         if (decomposition == null) {
            decomposition = UTF16.valueOf(char32);
         }

         return decomposition;
      } else {
         return normalize(UTF16.valueOf(char32), mode, options);
      }
   }

   /** @deprecated */
   @Deprecated
   public static String normalize(int char32, Mode mode) {
      return normalize(char32, mode, 0);
   }

   /** @deprecated */
   @Deprecated
   public static QuickCheckResult quickCheck(String source, Mode mode) {
      return quickCheck((String)source, mode, 0);
   }

   /** @deprecated */
   @Deprecated
   public static QuickCheckResult quickCheck(String source, Mode mode, int options) {
      return mode.getNormalizer2(options).quickCheck(source);
   }

   /** @deprecated */
   @Deprecated
   public static QuickCheckResult quickCheck(char[] source, Mode mode, int options) {
      return quickCheck(source, 0, source.length, mode, options);
   }

   /** @deprecated */
   @Deprecated
   public static QuickCheckResult quickCheck(char[] source, int start, int limit, Mode mode, int options) {
      CharBuffer srcBuffer = CharBuffer.wrap(source, start, limit - start);
      return mode.getNormalizer2(options).quickCheck(srcBuffer);
   }

   /** @deprecated */
   @Deprecated
   public static boolean isNormalized(char[] src, int start, int limit, Mode mode, int options) {
      CharBuffer srcBuffer = CharBuffer.wrap(src, start, limit - start);
      return mode.getNormalizer2(options).isNormalized(srcBuffer);
   }

   /** @deprecated */
   @Deprecated
   public static boolean isNormalized(String str, Mode mode, int options) {
      return mode.getNormalizer2(options).isNormalized(str);
   }

   /** @deprecated */
   @Deprecated
   public static boolean isNormalized(int char32, Mode mode, int options) {
      return isNormalized(UTF16.valueOf(char32), mode, options);
   }

   public static int compare(char[] s1, int s1Start, int s1Limit, char[] s2, int s2Start, int s2Limit, int options) {
      if (s1 != null && s1Start >= 0 && s1Limit >= 0 && s2 != null && s2Start >= 0 && s2Limit >= 0 && s1Limit >= s1Start && s2Limit >= s2Start) {
         return internalCompare(CharBuffer.wrap(s1, s1Start, s1Limit - s1Start), CharBuffer.wrap(s2, s2Start, s2Limit - s2Start), options);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static int compare(String s1, String s2, int options) {
      return internalCompare(s1, s2, options);
   }

   public static int compare(char[] s1, char[] s2, int options) {
      return internalCompare(CharBuffer.wrap(s1), CharBuffer.wrap(s2), options);
   }

   public static int compare(int char32a, int char32b, int options) {
      return internalCompare(UTF16.valueOf(char32a), UTF16.valueOf(char32b), options | 131072);
   }

   public static int compare(int char32a, String str2, int options) {
      return internalCompare(UTF16.valueOf(char32a), str2, options);
   }

   /** @deprecated */
   @Deprecated
   public static int concatenate(char[] left, int leftStart, int leftLimit, char[] right, int rightStart, int rightLimit, char[] dest, int destStart, int destLimit, Mode mode, int options) {
      if (dest == null) {
         throw new IllegalArgumentException();
      } else if (right == dest && rightStart < destLimit && destStart < rightLimit) {
         throw new IllegalArgumentException("overlapping right and dst ranges");
      } else {
         StringBuilder destBuilder = new StringBuilder(leftLimit - leftStart + rightLimit - rightStart + 16);
         destBuilder.append(left, leftStart, leftLimit - leftStart);
         CharBuffer rightBuffer = CharBuffer.wrap(right, rightStart, rightLimit - rightStart);
         mode.getNormalizer2(options).append(destBuilder, rightBuffer);
         int destLength = destBuilder.length();
         if (destLength <= destLimit - destStart) {
            destBuilder.getChars(0, destLength, dest, destStart);
            return destLength;
         } else {
            throw new IndexOutOfBoundsException(Integer.toString(destLength));
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public static String concatenate(char[] left, char[] right, Mode mode, int options) {
      StringBuilder dest = (new StringBuilder(left.length + right.length + 16)).append(left);
      return mode.getNormalizer2(options).append(dest, CharBuffer.wrap(right)).toString();
   }

   /** @deprecated */
   @Deprecated
   public static String concatenate(String left, String right, Mode mode, int options) {
      StringBuilder dest = (new StringBuilder(left.length() + right.length() + 16)).append(left);
      return mode.getNormalizer2(options).append(dest, right).toString();
   }

   /** @deprecated */
   @Deprecated
   public static int getFC_NFKC_Closure(int c, char[] dest) {
      String closure = getFC_NFKC_Closure(c);
      int length = closure.length();
      if (length != 0 && dest != null && length <= dest.length) {
         closure.getChars(0, length, dest, 0);
      }

      return length;
   }

   /** @deprecated */
   @Deprecated
   public static String getFC_NFKC_Closure(int c) {
      Normalizer2 nfkc = Normalizer.NFKCModeImpl.INSTANCE.normalizer2;
      UCaseProps csp = UCaseProps.INSTANCE;
      StringBuilder folded = new StringBuilder();
      int folded1Length = csp.toFullFolding(c, folded, 0);
      if (folded1Length < 0) {
         Normalizer2Impl nfkcImpl = ((Norm2AllModes.Normalizer2WithImpl)nfkc).impl;
         if (nfkcImpl.getCompQuickCheck(nfkcImpl.getNorm16(c)) != 0) {
            return "";
         }

         folded.appendCodePoint(c);
      } else if (folded1Length > 31) {
         folded.appendCodePoint(folded1Length);
      }

      String kc1 = nfkc.normalize(folded);
      String kc2 = nfkc.normalize(UCharacter.foldCase(kc1, 0));
      return kc1.equals(kc2) ? "" : kc2;
   }

   /** @deprecated */
   @Deprecated
   public int current() {
      return this.bufferPos >= this.buffer.length() && !this.nextNormalize() ? -1 : this.buffer.codePointAt(this.bufferPos);
   }

   /** @deprecated */
   @Deprecated
   public int next() {
      if (this.bufferPos >= this.buffer.length() && !this.nextNormalize()) {
         return -1;
      } else {
         int c = this.buffer.codePointAt(this.bufferPos);
         this.bufferPos += Character.charCount(c);
         return c;
      }
   }

   /** @deprecated */
   @Deprecated
   public int previous() {
      if (this.bufferPos <= 0 && !this.previousNormalize()) {
         return -1;
      } else {
         int c = this.buffer.codePointBefore(this.bufferPos);
         this.bufferPos -= Character.charCount(c);
         return c;
      }
   }

   /** @deprecated */
   @Deprecated
   public void reset() {
      this.text.setToStart();
      this.currentIndex = this.nextIndex = 0;
      this.clearBuffer();
   }

   /** @deprecated */
   @Deprecated
   public void setIndexOnly(int index) {
      this.text.setIndex(index);
      this.currentIndex = this.nextIndex = index;
      this.clearBuffer();
   }

   /** @deprecated */
   @Deprecated
   public int setIndex(int index) {
      this.setIndexOnly(index);
      return this.current();
   }

   /** @deprecated */
   @Deprecated
   public int getBeginIndex() {
      return 0;
   }

   /** @deprecated */
   @Deprecated
   public int getEndIndex() {
      return this.endIndex();
   }

   /** @deprecated */
   @Deprecated
   public int first() {
      this.reset();
      return this.next();
   }

   /** @deprecated */
   @Deprecated
   public int last() {
      this.text.setToLimit();
      this.currentIndex = this.nextIndex = this.text.getIndex();
      this.clearBuffer();
      return this.previous();
   }

   /** @deprecated */
   @Deprecated
   public int getIndex() {
      return this.bufferPos < this.buffer.length() ? this.currentIndex : this.nextIndex;
   }

   /** @deprecated */
   @Deprecated
   public int startIndex() {
      return 0;
   }

   /** @deprecated */
   @Deprecated
   public int endIndex() {
      return this.text.getLength();
   }

   /** @deprecated */
   @Deprecated
   public void setMode(Mode newMode) {
      this.mode = newMode;
      this.norm2 = this.mode.getNormalizer2(this.options);
   }

   /** @deprecated */
   @Deprecated
   public Mode getMode() {
      return this.mode;
   }

   /** @deprecated */
   @Deprecated
   public void setOption(int option, boolean value) {
      if (value) {
         this.options |= option;
      } else {
         this.options &= ~option;
      }

      this.norm2 = this.mode.getNormalizer2(this.options);
   }

   /** @deprecated */
   @Deprecated
   public int getOption(int option) {
      return (this.options & option) != 0 ? 1 : 0;
   }

   /** @deprecated */
   @Deprecated
   public int getText(char[] fillIn) {
      return this.text.getText(fillIn);
   }

   /** @deprecated */
   @Deprecated
   public int getLength() {
      return this.text.getLength();
   }

   /** @deprecated */
   @Deprecated
   public String getText() {
      return this.text.getText();
   }

   /** @deprecated */
   @Deprecated
   public void setText(StringBuffer newText) {
      UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
      if (newIter == null) {
         throw new IllegalStateException("Could not create a new UCharacterIterator");
      } else {
         this.text = newIter;
         this.reset();
      }
   }

   /** @deprecated */
   @Deprecated
   public void setText(char[] newText) {
      UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
      if (newIter == null) {
         throw new IllegalStateException("Could not create a new UCharacterIterator");
      } else {
         this.text = newIter;
         this.reset();
      }
   }

   /** @deprecated */
   @Deprecated
   public void setText(String newText) {
      UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
      if (newIter == null) {
         throw new IllegalStateException("Could not create a new UCharacterIterator");
      } else {
         this.text = newIter;
         this.reset();
      }
   }

   /** @deprecated */
   @Deprecated
   public void setText(CharacterIterator newText) {
      UCharacterIterator newIter = UCharacterIterator.getInstance(newText);
      if (newIter == null) {
         throw new IllegalStateException("Could not create a new UCharacterIterator");
      } else {
         this.text = newIter;
         this.reset();
      }
   }

   /** @deprecated */
   @Deprecated
   public void setText(UCharacterIterator newText) {
      try {
         UCharacterIterator newIter = (UCharacterIterator)newText.clone();
         if (newIter == null) {
            throw new IllegalStateException("Could not create a new UCharacterIterator");
         } else {
            this.text = newIter;
            this.reset();
         }
      } catch (CloneNotSupportedException var3) {
         throw new ICUCloneNotSupportedException("Could not clone the UCharacterIterator", var3);
      }
   }

   private void clearBuffer() {
      this.buffer.setLength(0);
      this.bufferPos = 0;
   }

   private boolean nextNormalize() {
      this.clearBuffer();
      this.currentIndex = this.nextIndex;
      this.text.setIndex(this.nextIndex);
      int c = this.text.nextCodePoint();
      if (c < 0) {
         return false;
      } else {
         StringBuilder segment = (new StringBuilder()).appendCodePoint(c);

         while((c = this.text.nextCodePoint()) >= 0) {
            if (this.norm2.hasBoundaryBefore(c)) {
               this.text.moveCodePointIndex(-1);
               break;
            }

            segment.appendCodePoint(c);
         }

         this.nextIndex = this.text.getIndex();
         this.norm2.normalize(segment, (StringBuilder)this.buffer);
         return this.buffer.length() != 0;
      }
   }

   private boolean previousNormalize() {
      this.clearBuffer();
      this.nextIndex = this.currentIndex;
      this.text.setIndex(this.currentIndex);
      StringBuilder segment = new StringBuilder();

      int c;
      while((c = this.text.previousCodePoint()) >= 0) {
         if (c <= 65535) {
            segment.insert(0, (char)c);
         } else {
            segment.insert(0, Character.toChars(c));
         }

         if (this.norm2.hasBoundaryBefore(c)) {
            break;
         }
      }

      this.currentIndex = this.text.getIndex();
      this.norm2.normalize(segment, (StringBuilder)this.buffer);
      this.bufferPos = this.buffer.length();
      return this.buffer.length() != 0;
   }

   private static int internalCompare(CharSequence s1, CharSequence s2, int options) {
      int normOptions = options >>> 20;
      options |= 524288;
      if ((options & 131072) == 0 || (options & 1) != 0) {
         Normalizer2 n2;
         if ((options & 1) != 0) {
            n2 = NFD.getNormalizer2(normOptions);
         } else {
            n2 = FCD.getNormalizer2(normOptions);
         }

         int spanQCYes1 = n2.spanQuickCheckYes((CharSequence)s1);
         int spanQCYes2 = n2.spanQuickCheckYes((CharSequence)s2);
         StringBuilder fcd2;
         if (spanQCYes1 < ((CharSequence)s1).length()) {
            fcd2 = (new StringBuilder(((CharSequence)s1).length() + 16)).append((CharSequence)s1, 0, spanQCYes1);
            s1 = n2.normalizeSecondAndAppend(fcd2, ((CharSequence)s1).subSequence(spanQCYes1, ((CharSequence)s1).length()));
         }

         if (spanQCYes2 < ((CharSequence)s2).length()) {
            fcd2 = (new StringBuilder(((CharSequence)s2).length() + 16)).append((CharSequence)s2, 0, spanQCYes2);
            s2 = n2.normalizeSecondAndAppend(fcd2, ((CharSequence)s2).subSequence(spanQCYes2, ((CharSequence)s2).length()));
         }
      }

      return cmpEquivFold((CharSequence)s1, (CharSequence)s2, options);
   }

   private static final CmpEquivLevel[] createCmpEquivLevelStack() {
      return new CmpEquivLevel[]{new CmpEquivLevel(), new CmpEquivLevel()};
   }

   static int cmpEquivFold(CharSequence cs1, CharSequence cs2, int options) {
      CmpEquivLevel[] stack1 = null;
      CmpEquivLevel[] stack2 = null;
      Normalizer2Impl nfcImpl;
      if ((options & 524288) != 0) {
         nfcImpl = Norm2AllModes.getNFCInstance().impl;
      } else {
         nfcImpl = null;
      }

      UCaseProps csp;
      StringBuilder fold1;
      StringBuilder fold2;
      if ((options & 65536) != 0) {
         csp = UCaseProps.INSTANCE;
         fold1 = new StringBuilder();
         fold2 = new StringBuilder();
      } else {
         csp = null;
         fold2 = null;
         fold1 = null;
      }

      int s1 = 0;
      int limit1 = ((CharSequence)cs1).length();
      int s2 = 0;
      int limit2 = ((CharSequence)cs2).length();
      int level2 = 0;
      int level1 = 0;
      int c2 = -1;
      int c1 = -1;

      while(true) {
         while(true) {
            if (c1 < 0) {
               while(true) {
                  if (s1 != limit1) {
                     c1 = ((CharSequence)cs1).charAt(s1++);
                     break;
                  }

                  if (level1 == 0) {
                     c1 = -1;
                     break;
                  }

                  do {
                     --level1;
                     cs1 = stack1[level1].cs;
                  } while(cs1 == null);

                  s1 = stack1[level1].s;
                  limit1 = ((CharSequence)cs1).length();
               }
            }

            if (c2 < 0) {
               while(true) {
                  if (s2 != limit2) {
                     c2 = ((CharSequence)cs2).charAt(s2++);
                     break;
                  }

                  if (level2 == 0) {
                     c2 = -1;
                     break;
                  }

                  do {
                     --level2;
                     cs2 = stack2[level2].cs;
                  } while(cs2 == null);

                  s2 = stack2[level2].s;
                  limit2 = ((CharSequence)cs2).length();
               }
            }

            if (c1 != c2) {
               if (c1 < 0) {
                  return -1;
               }

               if (c2 < 0) {
                  return 1;
               }

               int cp1 = c1;
               char c;
               if (UTF16.isSurrogate((char)c1)) {
                  if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c1)) {
                     if (s1 != limit1 && Character.isLowSurrogate(c = ((CharSequence)cs1).charAt(s1))) {
                        cp1 = Character.toCodePoint((char)c1, c);
                     }
                  } else if (0 <= s1 - 2 && Character.isHighSurrogate(c = ((CharSequence)cs1).charAt(s1 - 2))) {
                     cp1 = Character.toCodePoint(c, (char)c1);
                  }
               }

               int cp2 = c2;
               if (UTF16.isSurrogate((char)c2)) {
                  if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2)) {
                     if (s2 != limit2 && Character.isLowSurrogate(c = ((CharSequence)cs2).charAt(s2))) {
                        cp2 = Character.toCodePoint((char)c2, c);
                     }
                  } else if (0 <= s2 - 2 && Character.isHighSurrogate(c = ((CharSequence)cs2).charAt(s2 - 2))) {
                     cp2 = Character.toCodePoint(c, (char)c2);
                  }
               }

               int length;
               if (level1 == 0 && (options & 65536) != 0 && (length = csp.toFullFolding(cp1, fold1, options)) >= 0) {
                  if (UTF16.isSurrogate((char)c1)) {
                     if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c1)) {
                        ++s1;
                     } else {
                        --s2;
                        c2 = ((CharSequence)cs2).charAt(s2 - 1);
                     }
                  }

                  if (stack1 == null) {
                     stack1 = createCmpEquivLevelStack();
                  }

                  stack1[0].cs = (CharSequence)cs1;
                  stack1[0].s = s1;
                  ++level1;
                  if (length <= 31) {
                     fold1.delete(0, fold1.length() - length);
                  } else {
                     fold1.setLength(0);
                     fold1.appendCodePoint(length);
                  }

                  cs1 = fold1;
                  s1 = 0;
                  limit1 = fold1.length();
                  c1 = -1;
               } else if (level2 == 0 && (options & 65536) != 0 && (length = csp.toFullFolding(cp2, fold2, options)) >= 0) {
                  if (UTF16.isSurrogate((char)c2)) {
                     if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2)) {
                        ++s2;
                     } else {
                        --s1;
                        c1 = ((CharSequence)cs1).charAt(s1 - 1);
                     }
                  }

                  if (stack2 == null) {
                     stack2 = createCmpEquivLevelStack();
                  }

                  stack2[0].cs = (CharSequence)cs2;
                  stack2[0].s = s2;
                  ++level2;
                  if (length <= 31) {
                     fold2.delete(0, fold2.length() - length);
                  } else {
                     fold2.setLength(0);
                     fold2.appendCodePoint(length);
                  }

                  cs2 = fold2;
                  s2 = 0;
                  limit2 = fold2.length();
                  c2 = -1;
               } else {
                  String decomp1;
                  if (level1 < 2 && (options & 524288) != 0 && (decomp1 = nfcImpl.getDecomposition(cp1)) != null) {
                     if (UTF16.isSurrogate((char)c1)) {
                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c1)) {
                           ++s1;
                        } else {
                           --s2;
                           c2 = ((CharSequence)cs2).charAt(s2 - 1);
                        }
                     }

                     if (stack1 == null) {
                        stack1 = createCmpEquivLevelStack();
                     }

                     stack1[level1].cs = (CharSequence)cs1;
                     stack1[level1].s = s1;
                     ++level1;
                     if (level1 < 2) {
                        stack1[level1++].cs = null;
                     }

                     cs1 = decomp1;
                     s1 = 0;
                     limit1 = decomp1.length();
                     c1 = -1;
                  } else {
                     String decomp2;
                     if (level2 >= 2 || (options & 524288) == 0 || (decomp2 = nfcImpl.getDecomposition(cp2)) == null) {
                        if (c1 >= 55296 && c2 >= 55296 && (options & '耀') != 0) {
                           if ((c1 > 56319 || s1 == limit1 || !Character.isLowSurrogate(((CharSequence)cs1).charAt(s1))) && (!Character.isLowSurrogate((char)c1) || 0 == s1 - 1 || !Character.isHighSurrogate(((CharSequence)cs1).charAt(s1 - 2)))) {
                              c1 -= 10240;
                           }

                           if ((c2 > 56319 || s2 == limit2 || !Character.isLowSurrogate(((CharSequence)cs2).charAt(s2))) && (!Character.isLowSurrogate((char)c2) || 0 == s2 - 1 || !Character.isHighSurrogate(((CharSequence)cs2).charAt(s2 - 2)))) {
                              c2 -= 10240;
                           }
                        }

                        return c1 - c2;
                     }

                     if (UTF16.isSurrogate((char)c2)) {
                        if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2)) {
                           ++s2;
                        } else {
                           --s1;
                           c1 = ((CharSequence)cs1).charAt(s1 - 1);
                        }
                     }

                     if (stack2 == null) {
                        stack2 = createCmpEquivLevelStack();
                     }

                     stack2[level2].cs = (CharSequence)cs2;
                     stack2[level2].s = s2;
                     ++level2;
                     if (level2 < 2) {
                        stack2[level2++].cs = null;
                     }

                     cs2 = decomp2;
                     s2 = 0;
                     limit2 = decomp2.length();
                     c2 = -1;
                  }
               }
            } else {
               if (c1 < 0) {
                  return 0;
               }

               c2 = -1;
               c1 = -1;
            }
         }
      }
   }

   static {
      DEFAULT = NFC;
      NFKC = new NFKCMode();
      FCD = new FCDMode();
      NO_OP = NONE;
      COMPOSE = NFC;
      COMPOSE_COMPAT = NFKC;
      DECOMP = NFD;
      DECOMP_COMPAT = NFKD;
      NO = new QuickCheckResult(0);
      YES = new QuickCheckResult(1);
      MAYBE = new QuickCheckResult(2);
   }

   private static final class CharsAppendable implements Appendable {
      private final char[] chars;
      private final int start;
      private final int limit;
      private int offset;

      public CharsAppendable(char[] dest, int destStart, int destLimit) {
         this.chars = dest;
         this.start = this.offset = destStart;
         this.limit = destLimit;
      }

      public int length() {
         int len = this.offset - this.start;
         if (this.offset <= this.limit) {
            return len;
         } else {
            throw new IndexOutOfBoundsException(Integer.toString(len));
         }
      }

      public Appendable append(char c) {
         if (this.offset < this.limit) {
            this.chars[this.offset] = c;
         }

         ++this.offset;
         return this;
      }

      public Appendable append(CharSequence s) {
         return this.append(s, 0, s.length());
      }

      public Appendable append(CharSequence s, int sStart, int sLimit) {
         int len = sLimit - sStart;
         if (len <= this.limit - this.offset) {
            while(sStart < sLimit) {
               this.chars[this.offset++] = s.charAt(sStart++);
            }
         } else {
            this.offset += len;
         }

         return this;
      }
   }

   private static final class CmpEquivLevel {
      CharSequence cs;
      int s;

      private CmpEquivLevel() {
      }

      // $FF: synthetic method
      CmpEquivLevel(Object x0) {
         this();
      }
   }

   public static final class QuickCheckResult {
      private QuickCheckResult(int value) {
      }

      // $FF: synthetic method
      QuickCheckResult(int x0, Object x1) {
         this(x0);
      }
   }

   private static final class FCDMode extends Mode {
      private FCDMode() {
      }

      protected Normalizer2 getNormalizer2(int options) {
         return (options & 32) != 0 ? Normalizer.FCD32ModeImpl.INSTANCE.normalizer2 : Normalizer.FCDModeImpl.INSTANCE.normalizer2;
      }

      // $FF: synthetic method
      FCDMode(Object x0) {
         this();
      }
   }

   private static final class NFKCMode extends Mode {
      private NFKCMode() {
      }

      protected Normalizer2 getNormalizer2(int options) {
         return (options & 32) != 0 ? Normalizer.NFKC32ModeImpl.INSTANCE.normalizer2 : Normalizer.NFKCModeImpl.INSTANCE.normalizer2;
      }

      // $FF: synthetic method
      NFKCMode(Object x0) {
         this();
      }
   }

   private static final class NFCMode extends Mode {
      private NFCMode() {
      }

      protected Normalizer2 getNormalizer2(int options) {
         return (options & 32) != 0 ? Normalizer.NFC32ModeImpl.INSTANCE.normalizer2 : Normalizer.NFCModeImpl.INSTANCE.normalizer2;
      }

      // $FF: synthetic method
      NFCMode(Object x0) {
         this();
      }
   }

   private static final class NFKDMode extends Mode {
      private NFKDMode() {
      }

      protected Normalizer2 getNormalizer2(int options) {
         return (options & 32) != 0 ? Normalizer.NFKD32ModeImpl.INSTANCE.normalizer2 : Normalizer.NFKDModeImpl.INSTANCE.normalizer2;
      }

      // $FF: synthetic method
      NFKDMode(Object x0) {
         this();
      }
   }

   private static final class NFDMode extends Mode {
      private NFDMode() {
      }

      protected Normalizer2 getNormalizer2(int options) {
         return (options & 32) != 0 ? Normalizer.NFD32ModeImpl.INSTANCE.normalizer2 : Normalizer.NFDModeImpl.INSTANCE.normalizer2;
      }

      // $FF: synthetic method
      NFDMode(Object x0) {
         this();
      }
   }

   private static final class NONEMode extends Mode {
      private NONEMode() {
      }

      protected Normalizer2 getNormalizer2(int options) {
         return Norm2AllModes.NOOP_NORMALIZER2;
      }

      // $FF: synthetic method
      NONEMode(Object x0) {
         this();
      }
   }

   /** @deprecated */
   @Deprecated
   public abstract static class Mode {
      /** @deprecated */
      @Deprecated
      protected Mode() {
      }

      /** @deprecated */
      @Deprecated
      protected abstract Normalizer2 getNormalizer2(int var1);
   }

   private static final class FCD32ModeImpl {
      private static final ModeImpl INSTANCE;

      static {
         INSTANCE = new ModeImpl(new FilteredNormalizer2(Norm2AllModes.getFCDNormalizer2(), Normalizer.Unicode32.INSTANCE));
      }
   }

   private static final class NFKC32ModeImpl {
      private static final ModeImpl INSTANCE;

      static {
         INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKCInstance(), Normalizer.Unicode32.INSTANCE));
      }
   }

   private static final class NFC32ModeImpl {
      private static final ModeImpl INSTANCE;

      static {
         INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFCInstance(), Normalizer.Unicode32.INSTANCE));
      }
   }

   private static final class NFKD32ModeImpl {
      private static final ModeImpl INSTANCE;

      static {
         INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFKDInstance(), Normalizer.Unicode32.INSTANCE));
      }
   }

   private static final class NFD32ModeImpl {
      private static final ModeImpl INSTANCE;

      static {
         INSTANCE = new ModeImpl(new FilteredNormalizer2(Normalizer2.getNFDInstance(), Normalizer.Unicode32.INSTANCE));
      }
   }

   private static final class Unicode32 {
      private static final UnicodeSet INSTANCE = (new UnicodeSet("[:age=3.2:]")).freeze();
   }

   private static final class FCDModeImpl {
      private static final ModeImpl INSTANCE = new ModeImpl(Norm2AllModes.getFCDNormalizer2());
   }

   private static final class NFKCModeImpl {
      private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKCInstance());
   }

   private static final class NFCModeImpl {
      private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFCInstance());
   }

   private static final class NFKDModeImpl {
      private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFKDInstance());
   }

   private static final class NFDModeImpl {
      private static final ModeImpl INSTANCE = new ModeImpl(Normalizer2.getNFDInstance());
   }

   private static final class ModeImpl {
      private final Normalizer2 normalizer2;

      private ModeImpl(Normalizer2 n2) {
         this.normalizer2 = n2;
      }

      // $FF: synthetic method
      ModeImpl(Normalizer2 x0, Object x1) {
         this(x0);
      }
   }
}
