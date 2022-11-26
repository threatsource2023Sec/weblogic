package org.apache.xmlbeans.impl.store;

import java.io.PrintStream;
import java.lang.ref.SoftReference;

public final class CharUtil {
   private static int CHARUTIL_INITIAL_BUFSIZE = 32768;
   private static ThreadLocal tl_charUtil = new ThreadLocal() {
      protected Object initialValue() {
         return new SoftReference(new CharUtil(CharUtil.CHARUTIL_INITIAL_BUFSIZE));
      }
   };
   private CharIterator _charIter = new CharIterator();
   private static final int MAX_COPY = 64;
   private int _charBufSize;
   private int _currentOffset;
   private char[] _currentBuffer;
   public int _offSrc;
   public int _cchSrc;

   public CharUtil(int charBufSize) {
      this._charBufSize = charBufSize;
   }

   public CharIterator getCharIterator(Object src, int off, int cch) {
      this._charIter.init(src, off, cch);
      return this._charIter;
   }

   public CharIterator getCharIterator(Object src, int off, int cch, int start) {
      this._charIter.init(src, off, cch, start);
      return this._charIter;
   }

   public static CharUtil getThreadLocalCharUtil() {
      SoftReference softRef = (SoftReference)tl_charUtil.get();
      CharUtil charUtil = (CharUtil)softRef.get();
      if (charUtil == null) {
         charUtil = new CharUtil(CHARUTIL_INITIAL_BUFSIZE);
         tl_charUtil.set(new SoftReference(charUtil));
      }

      return charUtil;
   }

   public static void getString(StringBuffer sb, Object src, int off, int cch) {
      assert isValid(src, off, cch);

      if (cch != 0) {
         if (src instanceof char[]) {
            sb.append((char[])((char[])src), off, cch);
         } else if (src instanceof String) {
            String s = (String)src;
            if (off == 0 && cch == s.length()) {
               sb.append((String)src);
            } else {
               sb.append(s.substring(off, off + cch));
            }
         } else {
            ((CharJoin)src).getString(sb, off, cch);
         }

      }
   }

   public static void getChars(char[] chars, int start, Object src, int off, int cch) {
      assert isValid(src, off, cch);

      assert chars != null && start >= 0 && start <= chars.length;

      if (cch != 0) {
         if (src instanceof char[]) {
            System.arraycopy((char[])((char[])src), off, chars, start, cch);
         } else if (src instanceof String) {
            ((String)src).getChars(off, off + cch, chars, start);
         } else {
            ((CharJoin)src).getChars(chars, start, off, cch);
         }

      }
   }

   public static String getString(Object src, int off, int cch) {
      assert isValid(src, off, cch);

      if (cch == 0) {
         return "";
      } else if (src instanceof char[]) {
         return new String((char[])((char[])src), off, cch);
      } else if (src instanceof String) {
         String s = (String)src;
         return off == 0 && cch == s.length() ? s : s.substring(off, off + cch);
      } else {
         StringBuffer sb = new StringBuffer();
         ((CharJoin)src).getString(sb, off, cch);
         return sb.toString();
      }
   }

   public static final boolean isWhiteSpace(char ch) {
      switch (ch) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            return true;
         default:
            return false;
      }
   }

   public final boolean isWhiteSpace(Object src, int off, int cch) {
      assert isValid(src, off, cch);

      if (cch <= 0) {
         return true;
      } else if (src instanceof char[]) {
         for(char[] chars = (char[])((char[])src); cch > 0; --cch) {
            if (!isWhiteSpace(chars[off++])) {
               return false;
            }
         }

         return true;
      } else if (src instanceof String) {
         for(String s = (String)src; cch > 0; --cch) {
            if (!isWhiteSpace(s.charAt(off++))) {
               return false;
            }
         }

         return true;
      } else {
         boolean isWhite = true;
         this._charIter.init(src, off, cch);

         while(this._charIter.hasNext()) {
            if (!isWhiteSpace(this._charIter.next())) {
               isWhite = false;
               break;
            }
         }

         this._charIter.release();
         return isWhite;
      }
   }

   public Object stripLeft(Object src, int off, int cch) {
      assert isValid(src, off, cch);

      if (cch > 0) {
         if (src instanceof char[]) {
            for(char[] chars = (char[])((char[])src); cch > 0 && isWhiteSpace(chars[off]); ++off) {
               --cch;
            }
         } else if (src instanceof String) {
            for(String s = (String)src; cch > 0 && isWhiteSpace(s.charAt(off)); ++off) {
               --cch;
            }
         } else {
            int count = 0;
            this._charIter.init(src, off, cch);

            while(this._charIter.hasNext() && isWhiteSpace(this._charIter.next())) {
               ++count;
            }

            this._charIter.release();
            off += count;
         }
      }

      if (cch == 0) {
         this._offSrc = 0;
         this._cchSrc = 0;
         return null;
      } else {
         this._offSrc = off;
         this._cchSrc = cch;
         return src;
      }
   }

   public Object stripRight(Object src, int off, int cch) {
      assert isValid(src, off, cch);

      if (cch > 0) {
         this._charIter.init(src, off, cch, cch);

         while(this._charIter.hasPrev() && isWhiteSpace(this._charIter.prev())) {
            --cch;
         }

         this._charIter.release();
      }

      if (cch == 0) {
         this._offSrc = 0;
         this._cchSrc = 0;
         return null;
      } else {
         this._offSrc = off;
         this._cchSrc = cch;
         return src;
      }
   }

   public Object insertChars(int posInsert, Object src, int off, int cch, Object srcInsert, int offInsert, int cchInsert) {
      assert isValid(src, off, cch);

      assert isValid(srcInsert, offInsert, cchInsert);

      assert posInsert >= 0 && posInsert <= cch;

      if (cchInsert == 0) {
         this._cchSrc = cch;
         this._offSrc = off;
         return src;
      } else if (cch == 0) {
         this._cchSrc = cchInsert;
         this._offSrc = offInsert;
         return srcInsert;
      } else {
         this._cchSrc = cch + cchInsert;
         Object newSrc;
         if (this._cchSrc <= 64 && this.canAllocate(this._cchSrc)) {
            char[] c = this.allocate(this._cchSrc);
            getChars(c, this._offSrc, src, off, posInsert);
            getChars(c, this._offSrc + posInsert, srcInsert, offInsert, cchInsert);
            getChars(c, this._offSrc + posInsert + cchInsert, src, off + posInsert, cch - posInsert);
            newSrc = c;
         } else {
            this._offSrc = 0;
            CharJoin newJoin;
            if (posInsert == 0) {
               newJoin = new CharJoin(srcInsert, offInsert, cchInsert, src, off);
            } else if (posInsert == cch) {
               newJoin = new CharJoin(src, off, cch, srcInsert, offInsert);
            } else {
               CharJoin j = new CharJoin(src, off, posInsert, srcInsert, offInsert);
               newJoin = new CharJoin(j, 0, posInsert + cchInsert, src, off + posInsert);
            }

            if (newJoin._depth > 64) {
               newSrc = this.saveChars(newJoin, this._offSrc, this._cchSrc);
            } else {
               newSrc = newJoin;
            }
         }

         assert isValid(newSrc, this._offSrc, this._cchSrc);

         return newSrc;
      }
   }

   public Object removeChars(int posRemove, int cchRemove, Object src, int off, int cch) {
      assert isValid(src, off, cch);

      assert posRemove >= 0 && posRemove <= cch;

      assert cchRemove >= 0 && posRemove + cchRemove <= cch;

      this._cchSrc = cch - cchRemove;
      Object newSrc;
      if (this._cchSrc == 0) {
         newSrc = null;
         this._offSrc = 0;
      } else if (posRemove == 0) {
         newSrc = src;
         this._offSrc = off + cchRemove;
      } else if (posRemove + cchRemove == cch) {
         newSrc = src;
         this._offSrc = off;
      } else {
         int cchAfter = cch - cchRemove;
         if (cchAfter <= 64 && this.canAllocate(cchAfter)) {
            char[] chars = this.allocate(cchAfter);
            getChars(chars, this._offSrc, src, off, posRemove);
            getChars(chars, this._offSrc + posRemove, src, off + posRemove + cchRemove, cch - posRemove - cchRemove);
            newSrc = chars;
            this._offSrc = this._offSrc;
         } else {
            CharJoin j = new CharJoin(src, off, posRemove, src, off + posRemove + cchRemove);
            if (j._depth > 64) {
               newSrc = this.saveChars(j, 0, this._cchSrc);
            } else {
               newSrc = j;
               this._offSrc = 0;
            }
         }
      }

      assert isValid(newSrc, this._offSrc, this._cchSrc);

      return newSrc;
   }

   private static int sizeof(Object src) {
      assert src == null || src instanceof String || src instanceof char[];

      if (src instanceof char[]) {
         return ((char[])((char[])src)).length;
      } else {
         return src == null ? 0 : ((String)src).length();
      }
   }

   private boolean canAllocate(int cch) {
      return this._currentBuffer == null || this._currentBuffer.length - this._currentOffset >= cch;
   }

   private char[] allocate(int cch) {
      assert this._currentBuffer == null || this._currentBuffer.length - this._currentOffset > 0;

      if (this._currentBuffer == null) {
         this._currentBuffer = new char[Math.max(cch, this._charBufSize)];
         this._currentOffset = 0;
      }

      this._offSrc = this._currentOffset;
      this._cchSrc = Math.min(this._currentBuffer.length - this._currentOffset, cch);
      char[] retBuf = this._currentBuffer;

      assert this._currentOffset + this._cchSrc <= this._currentBuffer.length;

      if ((this._currentOffset += this._cchSrc) == this._currentBuffer.length) {
         this._currentBuffer = null;
         this._currentOffset = 0;
      }

      return retBuf;
   }

   public Object saveChars(Object srcSave, int offSave, int cchSave) {
      return this.saveChars(srcSave, offSave, cchSave, (Object)null, 0, 0);
   }

   public Object saveChars(Object srcSave, int offSave, int cchSave, Object srcPrev, int offPrev, int cchPrev) {
      assert isValid(srcSave, offSave, cchSave);

      assert isValid(srcPrev, offPrev, cchPrev);

      char[] srcAlloc = this.allocate(cchSave);
      int offAlloc = this._offSrc;
      int cchAlloc = this._cchSrc;

      assert cchAlloc <= cchSave;

      getChars(srcAlloc, offAlloc, srcSave, offSave, cchAlloc);
      int cchNew = cchAlloc + cchPrev;
      Object srcNew;
      int offNew;
      CharJoin j;
      if (cchPrev == 0) {
         srcNew = srcAlloc;
         offNew = offAlloc;
      } else if (srcPrev == srcAlloc && offPrev + cchPrev == offAlloc) {
         assert srcPrev instanceof char[];

         srcNew = srcPrev;
         offNew = offPrev;
      } else if (srcPrev instanceof CharJoin && (j = (CharJoin)srcPrev)._srcRight == srcAlloc && offPrev + cchPrev - j._cchLeft + j._offRight == offAlloc) {
         assert j._srcRight instanceof char[];

         srcNew = srcPrev;
         offNew = offPrev;
      } else {
         j = new CharJoin(srcPrev, offPrev, cchPrev, srcAlloc, offAlloc);
         offNew = 0;
         srcNew = j._depth > 64 ? this.saveChars(j, 0, cchNew) : j;
      }

      int cchMore = cchSave - cchAlloc;
      if (cchMore > 0) {
         srcAlloc = this.allocate(cchMore);
         offAlloc = this._offSrc;
         cchAlloc = this._cchSrc;

         assert cchAlloc == cchMore;

         assert offAlloc == 0;

         getChars(srcAlloc, offAlloc, srcSave, offSave + (cchSave - cchMore), cchMore);
         j = new CharJoin(srcNew, offNew, cchNew, srcAlloc, offAlloc);
         offNew = 0;
         cchNew += cchMore;
         srcNew = j._depth > 64 ? this.saveChars(j, 0, cchNew) : j;
      }

      this._offSrc = offNew;
      this._cchSrc = cchNew;

      assert isValid(srcNew, this._offSrc, this._cchSrc);

      return srcNew;
   }

   private static void dumpText(PrintStream o, String s) {
      o.print("\"");

      for(int i = 0; i < s.length(); ++i) {
         char ch = s.charAt(i);
         if (i == 36) {
            o.print("...");
            break;
         }

         if (ch == '\n') {
            o.print("\\n");
         } else if (ch == '\r') {
            o.print("\\r");
         } else if (ch == '\t') {
            o.print("\\t");
         } else if (ch == '\f') {
            o.print("\\f");
         } else if (ch == '\f') {
            o.print("\\f");
         } else if (ch == '"') {
            o.print("\\\"");
         } else {
            o.print(ch);
         }
      }

      o.print("\"");
   }

   public static void dump(Object src, int off, int cch) {
      dumpChars(System.out, src, off, cch);
      System.out.println();
   }

   public static void dumpChars(PrintStream p, Object src, int off, int cch) {
      p.print("off=" + off + ", cch=" + cch + ", ");
      if (src == null) {
         p.print("<null-src>");
      } else if (src instanceof String) {
         String s = (String)src;
         p.print("String");
         if ((off != 0 || cch != s.length()) && (off < 0 || off > s.length() || off + cch < 0 || off + cch > s.length())) {
            p.print(" (Error)");
            return;
         }

         dumpText(p, s.substring(off, off + cch));
      } else if (!(src instanceof char[])) {
         if (src instanceof CharJoin) {
            p.print("CharJoin");
            ((CharJoin)src).dumpChars(p, off, cch);
         } else {
            p.print("Unknown text source");
         }
      } else {
         char[] chars = (char[])((char[])src);
         p.print("char[]");
         if ((off != 0 || cch != chars.length) && (off < 0 || off > chars.length || off + cch < 0 || off + cch > chars.length)) {
            p.print(" (Error)");
            return;
         }

         dumpText(p, new String(chars, off, cch));
      }

   }

   public static boolean isValid(Object src, int off, int cch) {
      if (cch >= 0 && off >= 0) {
         if (src == null) {
            return off == 0 && cch == 0;
         } else if (src instanceof char[]) {
            char[] c = (char[])((char[])src);
            return off <= c.length && off + cch <= c.length;
         } else if (!(src instanceof String)) {
            return src instanceof CharJoin ? ((CharJoin)src).isValid(off, cch) : false;
         } else {
            String s = (String)src;
            return off <= s.length() && off + cch <= s.length();
         }
      } else {
         return false;
      }
   }

   public static final class CharIterator {
      private Object _srcRoot;
      private int _offRoot;
      private int _cchRoot;
      private int _pos;
      private int _minPos;
      private int _maxPos;
      private int _offLeaf;
      private String _srcLeafString;
      private char[] _srcLeafChars;

      public void init(Object src, int off, int cch) {
         this.init(src, off, cch, 0);
      }

      public void init(Object src, int off, int cch, int startPos) {
         assert CharUtil.isValid(src, off, cch);

         this.release();
         this._srcRoot = src;
         this._offRoot = off;
         this._cchRoot = cch;
         this._minPos = this._maxPos = -1;
         this.movePos(startPos);
      }

      public void release() {
         this._srcRoot = null;
         this._srcLeafString = null;
         this._srcLeafChars = null;
      }

      public boolean hasNext() {
         return this._pos < this._cchRoot;
      }

      public boolean hasPrev() {
         return this._pos > 0;
      }

      public char next() {
         assert this.hasNext();

         char ch = this.currentChar();
         this.movePos(this._pos + 1);
         return ch;
      }

      public char prev() {
         assert this.hasPrev();

         this.movePos(this._pos - 1);
         return this.currentChar();
      }

      public void movePos(int newPos) {
         assert newPos >= 0 && newPos <= this._cchRoot;

         if (newPos < this._minPos || newPos > this._maxPos) {
            Object src = this._srcRoot;
            int off = this._offRoot + newPos;
            int cch = this._cchRoot;
            this._offLeaf = this._offRoot;

            while(true) {
               if (!(src instanceof CharJoin)) {
                  this._minPos = newPos - (off - this._offLeaf);
                  this._maxPos = this._minPos + cch;
                  if (newPos < this._cchRoot) {
                     --this._maxPos;
                  }

                  this._srcLeafChars = null;
                  this._srcLeafString = null;
                  if (src instanceof char[]) {
                     this._srcLeafChars = (char[])((char[])src);
                  } else {
                     this._srcLeafString = (String)src;
                  }

                  assert newPos >= this._minPos && newPos <= this._maxPos;
                  break;
               }

               CharJoin j = (CharJoin)src;
               if (off < j._cchLeft) {
                  src = j._srcLeft;
                  this._offLeaf = j._offLeft;
                  off += j._offLeft;
                  cch = j._cchLeft;
               } else {
                  src = j._srcRight;
                  this._offLeaf = j._offRight;
                  off -= j._cchLeft - j._offRight;
                  cch -= j._cchLeft;
               }
            }
         }

         this._pos = newPos;
      }

      private char currentChar() {
         int i = this._offLeaf + this._pos - this._minPos;
         return this._srcLeafChars == null ? this._srcLeafString.charAt(i) : this._srcLeafChars[i];
      }
   }

   public static final class CharJoin {
      public final Object _srcLeft;
      public final int _offLeft;
      public final int _cchLeft;
      public final Object _srcRight;
      public final int _offRight;
      public final int _depth;
      static final int MAX_DEPTH = 64;

      public CharJoin(Object srcLeft, int offLeft, int cchLeft, Object srcRight, int offRight) {
         this._srcLeft = srcLeft;
         this._offLeft = offLeft;
         this._cchLeft = cchLeft;
         this._srcRight = srcRight;
         this._offRight = offRight;
         int depth = 0;
         if (srcLeft instanceof CharJoin) {
            depth = ((CharJoin)srcLeft)._depth;
         }

         if (srcRight instanceof CharJoin) {
            int rightDepth = ((CharJoin)srcRight)._depth;
            if (rightDepth > depth) {
               depth = rightDepth;
            }
         }

         this._depth = depth + 1;

         assert this._depth <= 66;

      }

      private int cchRight(int off, int cch) {
         return Math.max(0, cch - this._cchLeft - off);
      }

      public int depth() {
         int depth = 0;
         if (this._srcLeft instanceof CharJoin) {
            depth = ((CharJoin)this._srcLeft).depth();
         }

         if (this._srcRight instanceof CharJoin) {
            depth = Math.max(((CharJoin)this._srcRight).depth(), depth);
         }

         return depth + 1;
      }

      public boolean isValid(int off, int cch) {
         if (this._depth > 2) {
            return true;
         } else {
            assert this._depth == this.depth();

            if (off >= 0 && cch >= 0) {
               if (!CharUtil.isValid(this._srcLeft, this._offLeft, this._cchLeft)) {
                  return false;
               } else {
                  return CharUtil.isValid(this._srcRight, this._offRight, this.cchRight(off, cch));
               }
            } else {
               return false;
            }
         }
      }

      private void getString(StringBuffer sb, int off, int cch) {
         assert cch > 0;

         if (off < this._cchLeft) {
            int cchL = Math.min(this._cchLeft - off, cch);
            CharUtil.getString(sb, this._srcLeft, this._offLeft + off, cchL);
            if (cch > cchL) {
               CharUtil.getString(sb, this._srcRight, this._offRight, cch - cchL);
            }
         } else {
            CharUtil.getString(sb, this._srcRight, this._offRight + off - this._cchLeft, cch);
         }

      }

      private void getChars(char[] chars, int start, int off, int cch) {
         assert cch > 0;

         if (off < this._cchLeft) {
            int cchL = Math.min(this._cchLeft - off, cch);
            CharUtil.getChars(chars, start, this._srcLeft, this._offLeft + off, cchL);
            if (cch > cchL) {
               CharUtil.getChars(chars, start + cchL, this._srcRight, this._offRight, cch - cchL);
            }
         } else {
            CharUtil.getChars(chars, start, this._srcRight, this._offRight + off - this._cchLeft, cch);
         }

      }

      private void dumpChars(int off, int cch) {
         this.dumpChars(System.out, off, cch);
      }

      private void dumpChars(PrintStream p, int off, int cch) {
         p.print("( ");
         CharUtil.dumpChars(p, this._srcLeft, this._offLeft, this._cchLeft);
         p.print(", ");
         CharUtil.dumpChars(p, this._srcRight, this._offRight, this.cchRight(off, cch));
         p.print(" )");
      }
   }
}
