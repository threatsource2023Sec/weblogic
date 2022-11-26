package org.python.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.python.core.stringlib.FieldNameIterator;
import org.python.core.stringlib.MarkupIterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.google.common.base.CharMatcher;
import org.python.modules._codecs;
import org.python.util.Generic;

@Untraversable
@ExposedType(
   name = "unicode",
   base = PyBaseString.class,
   doc = "unicode(object='') -> unicode object\nunicode(string[, encoding[, errors]]) -> unicode object\n\nCreate a new Unicode object from the given encoded string.\nencoding defaults to the current default string encoding.\nerrors can be 'strict', 'replace' or 'ignore' and defaults to 'strict'."
)
public class PyUnicode extends PyString implements Iterable {
   private static final boolean DEBUG_NON_BMP_METHODS = false;
   public static final PyType TYPE;
   private final IndexTranslator translator;
   static final IndexTranslator BASIC;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;

   public PyUnicode() {
      this(TYPE, "", true);
   }

   public PyUnicode(String string) {
      this(TYPE, string, false);
   }

   public PyUnicode(String string, boolean isBasic) {
      this(TYPE, string, isBasic);
   }

   public PyUnicode(PyType subtype, String string) {
      this(subtype, string, false);
   }

   public PyUnicode(PyString pystring) {
      this(TYPE, pystring);
   }

   public PyUnicode(PyType subtype, PyString pystring) {
      this(subtype, pystring instanceof PyUnicode ? pystring.string : pystring.decode().toString(), pystring.isBasicPlane());
   }

   public PyUnicode(char c) {
      this(TYPE, String.valueOf(c), true);
   }

   public PyUnicode(int codepoint) {
      this(TYPE, new String(new int[]{codepoint}, 0, 1));
   }

   public PyUnicode(int[] codepoints) {
      this(new String(codepoints, 0, codepoints.length));
   }

   PyUnicode(StringBuilder buffer) {
      this(TYPE, buffer.toString());
   }

   private static StringBuilder fromCodePoints(Iterator iter) {
      StringBuilder buffer = new StringBuilder();

      while(iter.hasNext()) {
         buffer.appendCodePoint((Integer)iter.next());
      }

      return buffer;
   }

   public PyUnicode(Iterator iter) {
      this(fromCodePoints(iter));
   }

   public PyUnicode(Collection ucs4) {
      this(ucs4.iterator());
   }

   private PyUnicode(PyType subtype, String string, boolean isBasic) {
      super(subtype, "");
      this.string = string;
      this.translator = isBasic ? BASIC : this.chooseIndexTranslator();
   }

   public int[] toCodePoints() {
      int n = this.getCodePointCount();
      int[] codePoints = new int[n];
      int i = 0;

      for(Iterator iter = this.newSubsequenceIterator(); iter.hasNext(); ++i) {
         codePoints[i] = (Integer)iter.next();
      }

      return codePoints;
   }

   private static int[] getSupplementaryCounts(String string) {
      int n = string.length();

      int p;
      for(p = 0; p < n && !Character.isSurrogate(string.charAt(p)); ++p) {
      }

      if (p == n) {
         return null;
      } else {
         int q = p;
         int k = p >> 4;
         int[] count = new int[1 + (n >> 4)];

         while(p < n - 1) {
            p += calcAdvance(string, p);
            ++q;
            if ((q & 15) == 0) {
               count[k++] = p - q;
               break;
            }
         }

         int total;
         while(p + 32 < n) {
            for(total = 0; total < 16; ++total) {
               p += calcAdvance(string, p);
            }

            q += 16;
            count[k++] = p - q;
         }

         while(p < n - 1) {
            p += calcAdvance(string, p);
            ++q;
            if ((q & 15) == 0) {
               count[k++] = p - q;
            }
         }

         if (p < n) {
            char c = string.charAt(p++);
            if (Character.isSurrogate(c)) {
               throw unpairedSurrogate(p - 1, c);
            }

            ++q;
         }

         for(total = p - q; k < count.length; count[k++] = total) {
         }

         return count;
      }
   }

   private static int calcAdvance(String string, int p) throws PyException {
      char c = string.charAt(p);
      if (c >= '\ud800') {
         if (c < '\udc00') {
            if (Character.isLowSurrogate(string.charAt(p + 1))) {
               return 2;
            }

            throw unpairedSurrogate(p, c);
         }

         if (c <= '\udfff') {
            throw unpairedSurrogate(p, c);
         }
      }

      return 1;
   }

   private static PyException unpairedSurrogate(int p, int c) {
      String fmt = "unpaired surrogate %#4x at code unit %d";
      String msg = String.format(fmt, c, p);
      return Py.ValueError(msg);
   }

   private IndexTranslator chooseIndexTranslator() {
      int[] count = getSupplementaryCounts(this.string);
      return (IndexTranslator)(count == null ? BASIC : new Supplementary(count));
   }

   protected int[] translateIndices(PyObject start, PyObject end) {
      int[] indices = super.translateIndices(start, end);
      indices[0] = this.translator.utf16Index(indices[0]);
      indices[1] = this.translator.utf16Index(indices[1]);
      return indices;
   }

   public String substring(int start, int end) {
      return super.substring(this.translator.utf16Index(start), this.translator.utf16Index(end));
   }

   public static PyUnicode fromInterned(String interned) {
      PyUnicode uni = new PyUnicode(TYPE, interned);
      uni.interned = true;
      return uni;
   }

   public boolean isBasicPlane() {
      return this.translator == BASIC;
   }

   public int getCodePointCount() {
      return this.string.length() - this.translator.suppCount();
   }

   public static String checkEncoding(String s) {
      return s != null && !CharMatcher.ascii().matchesAllOf(s) ? codecs.PyUnicode_EncodeASCII(s, s.length(), (String)null) : s;
   }

   @ExposedNew
   static final PyObject unicode_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("unicode", args, keywords, new String[]{"string", "encoding", "errors"}, 0);
      PyObject S = ap.getPyObject(0, (PyObject)null);
      String encoding = checkEncoding(ap.getString(1, (String)null));
      String errors = checkEncoding(ap.getString(2, (String)null));
      if (new_.for_type == subtype) {
         if (S == null) {
            return new PyUnicode("");
         } else if (S instanceof PyUnicode) {
            return new PyUnicode(((PyUnicode)S).getString());
         } else if (S instanceof PyString) {
            if (S.getType() != PyString.TYPE && encoding == null && errors == null) {
               return S.__unicode__();
            } else {
               PyObject decoded = codecs.decode((PyString)S, encoding, errors);
               if (decoded instanceof PyUnicode) {
                  return new PyUnicode((PyUnicode)decoded);
               } else {
                  throw Py.TypeError("decoder did not return an unicode object (type=" + decoded.getType().fastGetName() + ")");
               }
            }
         } else {
            return S.__unicode__();
         }
      } else if (S == null) {
         return new PyUnicodeDerived(subtype, Py.EmptyString);
      } else {
         return S instanceof PyUnicode ? new PyUnicodeDerived(subtype, (PyUnicode)S) : new PyUnicodeDerived(subtype, S.__str__());
      }
   }

   public PyString createInstance(String str) {
      return new PyUnicode(str);
   }

   protected PyString createInstance(String string, boolean isBasic) {
      return new PyUnicode(string, isBasic);
   }

   public PyObject __mod__(PyObject other) {
      return this.unicode___mod__(other);
   }

   final PyObject unicode___mod__(PyObject other) {
      StringFormatter fmt = new StringFormatter(this.getString(), true);
      return fmt.format(other);
   }

   public PyUnicode __unicode__() {
      return this;
   }

   public PyString __str__() {
      return this.unicode___str__();
   }

   final PyString unicode___str__() {
      return new PyString(this.encode());
   }

   public int __len__() {
      return this.unicode___len__();
   }

   final int unicode___len__() {
      return this.getCodePointCount();
   }

   public PyString __repr__() {
      return this.unicode___repr__();
   }

   final PyString unicode___repr__() {
      return new PyString("u" + encode_UnicodeEscape(this.getString(), true));
   }

   final PyObject unicode___getitem__(PyObject index) {
      return this.str___getitem__(index);
   }

   final PyObject unicode___getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.seq___getslice__(start, stop, step);
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (this.isBasicPlane()) {
         return super.getslice(start, stop, step);
      } else {
         if (step > 0 && stop < start) {
            stop = start;
         }

         StringBuilder buffer = new StringBuilder(sliceLength(start, stop, (long)step));
         Iterator iter = this.newSubsequenceIterator(start, stop, step);

         while(iter.hasNext()) {
            buffer.appendCodePoint((Integer)iter.next());
         }

         return this.createInstance(buffer.toString());
      }
   }

   final int unicode___cmp__(PyObject other) {
      return this.str___cmp__(other);
   }

   final PyObject unicode___eq__(PyObject other) {
      return this.str___eq__(other);
   }

   final PyObject unicode___ne__(PyObject other) {
      return this.str___ne__(other);
   }

   final int unicode___hash__() {
      return this.str___hash__();
   }

   protected PyObject pyget(int i) {
      int codepoint = this.getString().codePointAt(this.translator.utf16Index(i));
      return Py.makeCharacter(codepoint, true);
   }

   public int getInt(int i) {
      return this.getString().codePointAt(this.translator.utf16Index(i));
   }

   public Iterator newSubsequenceIterator() {
      return (Iterator)(this.isBasicPlane() ? new SubsequenceIteratorBasic() : new SubsequenceIteratorImpl());
   }

   public Iterator newSubsequenceIterator(int start, int stop, int step) {
      if (this.isBasicPlane()) {
         return (Iterator)(step < 0 ? new SteppedIterator(step * -1, new ReversedIterator(new SubsequenceIteratorBasic(stop + 1, start + 1, 1))) : new SubsequenceIteratorBasic(start, stop, step));
      } else {
         return (Iterator)(step < 0 ? new SteppedIterator(step * -1, new ReversedIterator(new SubsequenceIteratorImpl(stop + 1, start + 1, 1))) : new SubsequenceIteratorImpl(start, stop, step));
      }
   }

   private PyUnicode coerceToUnicode(PyObject o) {
      if (o instanceof PyUnicode) {
         return (PyUnicode)o;
      } else if (o instanceof PyString) {
         return new PyUnicode(((PyString)o).getString(), true);
      } else if (o instanceof BufferProtocol) {
         PyBuffer buf = ((BufferProtocol)o).getBuffer(284);
         Throwable var3 = null;

         PyUnicode var4;
         try {
            var4 = new PyUnicode(buf.toString(), true);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (buf != null) {
               if (var3 != null) {
                  try {
                     buf.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  buf.close();
               }
            }

         }

         return var4;
      } else {
         if (o == null) {
            o = Py.None;
         }

         throw Py.TypeError("coercing to Unicode: need string or buffer, " + o.getType().fastGetName() + " found");
      }
   }

   private PyUnicode coerceToUnicodeOrNull(PyObject o) {
      return o != null && o != Py.None ? this.coerceToUnicode(o) : null;
   }

   final boolean unicode___contains__(PyObject o) {
      return this.str___contains__(o);
   }

   final PyObject unicode___mul__(PyObject o) {
      return this.str___mul__(o);
   }

   final PyObject unicode___rmul__(PyObject o) {
      return this.str___rmul__(o);
   }

   public PyObject __add__(PyObject other) {
      return this.unicode___add__(other);
   }

   final PyObject unicode___add__(PyObject other) {
      PyUnicode otherUnicode;
      if (other instanceof PyUnicode) {
         otherUnicode = (PyUnicode)other;
      } else {
         if (!(other instanceof PyString)) {
            return null;
         }

         otherUnicode = (PyUnicode)((PyString)other).decode();
      }

      return new PyUnicode(this.getString().concat(otherUnicode.getString()));
   }

   final PyObject unicode_lower() {
      return new PyUnicode(this.getString().toLowerCase());
   }

   final PyObject unicode_upper() {
      return new PyUnicode(this.getString().toUpperCase());
   }

   final PyObject unicode_title() {
      StringBuilder buffer = new StringBuilder(this.getString().length());
      boolean previous_is_cased = false;
      Iterator iter = this.newSubsequenceIterator();

      while(true) {
         while(iter.hasNext()) {
            int codePoint = (Integer)iter.next();
            if (previous_is_cased) {
               buffer.appendCodePoint(Character.toLowerCase(codePoint));
            } else {
               buffer.appendCodePoint(Character.toTitleCase(codePoint));
            }

            if (!Character.isLowerCase(codePoint) && !Character.isUpperCase(codePoint) && !Character.isTitleCase(codePoint)) {
               previous_is_cased = false;
            } else {
               previous_is_cased = true;
            }
         }

         return new PyUnicode(buffer);
      }
   }

   final PyObject unicode_swapcase() {
      StringBuilder buffer = new StringBuilder(this.getString().length());
      Iterator iter = this.newSubsequenceIterator();

      while(iter.hasNext()) {
         int codePoint = (Integer)iter.next();
         if (Character.isUpperCase(codePoint)) {
            buffer.appendCodePoint(Character.toLowerCase(codePoint));
         } else if (Character.isLowerCase(codePoint)) {
            buffer.appendCodePoint(Character.toUpperCase(codePoint));
         } else {
            buffer.appendCodePoint(codePoint);
         }
      }

      return new PyUnicode(buffer);
   }

   private PyUnicode coerceStripSepToUnicode(PyObject o) {
      if (o == null) {
         return null;
      } else if (o instanceof PyUnicode) {
         return (PyUnicode)o;
      } else if (o instanceof PyString) {
         return new PyUnicode(((PyString)o).decode().toString());
      } else if (o == Py.None) {
         return null;
      } else {
         throw Py.TypeError("strip arg must be None, unicode or str");
      }
   }

   final PyObject unicode_strip(PyObject sepObj) {
      PyUnicode sep = this.coerceStripSepToUnicode(sepObj);
      if (this.isBasicPlane()) {
         if (sep == null) {
            return new PyUnicode(this._strip());
         }

         if (sep.isBasicPlane()) {
            return new PyUnicode(this._strip(sep.getString()));
         }
      }

      return new PyUnicode(new ReversedIterator(new StripIterator(sep, new ReversedIterator(new StripIterator(sep, this.newSubsequenceIterator())))));
   }

   final PyObject unicode_lstrip(PyObject sepObj) {
      PyUnicode sep = this.coerceStripSepToUnicode(sepObj);
      if (this.isBasicPlane()) {
         if (sep == null) {
            return new PyUnicode(this._lstrip());
         }

         if (sep.isBasicPlane()) {
            return new PyUnicode(this._lstrip(sep.getString()));
         }
      }

      return new PyUnicode(new StripIterator(sep, this.newSubsequenceIterator()));
   }

   final PyObject unicode_rstrip(PyObject sepObj) {
      PyUnicode sep = this.coerceStripSepToUnicode(sepObj);
      if (this.isBasicPlane()) {
         if (sep == null) {
            return new PyUnicode(this._rstrip());
         }

         if (sep.isBasicPlane()) {
            return new PyUnicode(this._rstrip(sep.getString()));
         }
      }

      return new PyUnicode(new ReversedIterator(new StripIterator(sep, new ReversedIterator(this.newSubsequenceIterator()))));
   }

   public PyTuple partition(PyObject sep) {
      return this.unicode_partition(sep);
   }

   final PyTuple unicode_partition(PyObject sep) {
      return this.unicodePartition(this.coerceToUnicode(sep));
   }

   private SplitIterator newSplitIterator(PyUnicode sep, int maxsplit) {
      if (sep == null) {
         return new WhitespaceSplitIterator(maxsplit);
      } else if (sep.getCodePointCount() == 0) {
         throw Py.ValueError("empty separator");
      } else {
         return new SepSplitIterator(sep, maxsplit);
      }
   }

   public PyTuple rpartition(PyObject sep) {
      return this.unicode_rpartition(sep);
   }

   final PyTuple unicode_rpartition(PyObject sep) {
      return this.unicodeRpartition(this.coerceToUnicode(sep));
   }

   final PyList unicode_split(PyObject sepObj, int maxsplit) {
      PyUnicode sep = this.coerceToUnicodeOrNull(sepObj);
      return sep != null ? this._split(sep.getString(), maxsplit) : this._split((String)null, maxsplit);
   }

   final PyList unicode_rsplit(PyObject sepObj, int maxsplit) {
      PyUnicode sep = this.coerceToUnicodeOrNull(sepObj);
      return sep != null ? this._rsplit(sep.getString(), maxsplit) : this._rsplit((String)null, maxsplit);
   }

   final PyList unicode_splitlines(boolean keepends) {
      return new PyList(new LineSplitIterator(keepends));
   }

   protected PyString fromSubstring(int begin, int end) {
      if (!$assertionsDisabled && !this.isBasicPlane()) {
         throw new AssertionError();
      } else {
         return new PyUnicode(this.getString().substring(begin, end), true);
      }
   }

   final int unicode_index(PyObject subObj, PyObject start, PyObject end) {
      PyUnicode sub = this.coerceToUnicode(subObj);
      return this.checkIndex(this._find(sub.getString(), start, end));
   }

   final int unicode_rindex(PyObject subObj, PyObject start, PyObject end) {
      PyUnicode sub = this.coerceToUnicode(subObj);
      return this.checkIndex(this._rfind(sub.getString(), start, end));
   }

   final int unicode_count(PyObject subObj, PyObject start, PyObject end) {
      PyUnicode sub = this.coerceToUnicode(subObj);
      if (this.isBasicPlane()) {
         return this._count(sub.getString(), start, end);
      } else {
         int[] indices = super.translateIndices(start, end);
         int count = 0;
         Iterator mainIter = this.newSubsequenceIterator(indices[0], indices[1], 1);

         while(mainIter.hasNext()) {
            int matched = sub.getCodePointCount();

            for(Iterator subIter = sub.newSubsequenceIterator(); mainIter.hasNext() && subIter.hasNext() && mainIter.next() == subIter.next(); --matched) {
            }

            if (matched == 0) {
               ++count;
            }
         }

         return count;
      }
   }

   final int unicode_find(PyObject subObj, PyObject start, PyObject end) {
      int found = this._find(this.coerceToUnicode(subObj).getString(), start, end);
      return found < 0 ? -1 : this.translator.codePointIndex(found);
   }

   final int unicode_rfind(PyObject subObj, PyObject start, PyObject end) {
      int found = this._rfind(this.coerceToUnicode(subObj).getString(), start, end);
      return found < 0 ? -1 : this.translator.codePointIndex(found);
   }

   private static String padding(int n, int pad) {
      StringBuilder buffer = new StringBuilder(n);

      for(int i = 0; i < n; ++i) {
         buffer.appendCodePoint(pad);
      }

      return buffer.toString();
   }

   private static int parse_fillchar(String function, String fillchar) {
      if (fillchar == null) {
         return 32;
      } else if (fillchar.codePointCount(0, fillchar.length()) != 1) {
         throw Py.TypeError(function + "() argument 2 must be char, not str");
      } else {
         return fillchar.codePointAt(0);
      }
   }

   final PyObject unicode_ljust(int width, String padding) {
      int n = width - this.getCodePointCount();
      return n <= 0 ? new PyUnicode(this.getString()) : new PyUnicode(this.getString() + padding(n, parse_fillchar("ljust", padding)));
   }

   final PyObject unicode_rjust(int width, String padding) {
      int n = width - this.getCodePointCount();
      return n <= 0 ? new PyUnicode(this.getString()) : new PyUnicode(padding(n, parse_fillchar("ljust", padding)) + this.getString());
   }

   final PyObject unicode_center(int width, String padding) {
      int n = width - this.getCodePointCount();
      if (n <= 0) {
         return new PyUnicode(this.getString());
      } else {
         int half = n / 2;
         if (n % 2 > 0 && width % 2 > 0) {
            ++half;
         }

         int pad = parse_fillchar("center", padding);
         return new PyUnicode(padding(half, pad) + this.getString() + padding(n - half, pad));
      }
   }

   final PyObject unicode_zfill(int width) {
      int n = this.getCodePointCount();
      if (n >= width) {
         return new PyUnicode(this.getString());
      } else if (this.isBasicPlane()) {
         return new PyUnicode(this.str_zfill(width));
      } else {
         StringBuilder buffer = new StringBuilder(width);
         int nzeros = width - n;
         boolean first = true;
         boolean leadingSign = false;
         Iterator iter = this.newSubsequenceIterator();

         while(true) {
            while(iter.hasNext()) {
               int codePoint = (Integer)iter.next();
               if (first) {
                  first = false;
                  if (codePoint == 43 || codePoint == 45) {
                     buffer.appendCodePoint(codePoint);
                     leadingSign = true;
                  }

                  for(int i = 0; i < nzeros; ++i) {
                     buffer.appendCodePoint(48);
                  }

                  if (!leadingSign) {
                     buffer.appendCodePoint(codePoint);
                  }
               } else {
                  buffer.appendCodePoint(codePoint);
               }
            }

            if (first) {
               for(int i = 0; i < nzeros; ++i) {
                  buffer.appendCodePoint(48);
               }
            }

            return new PyUnicode(buffer);
         }
      }
   }

   final PyObject unicode_expandtabs(int tabsize) {
      return new PyUnicode(this.str_expandtabs(tabsize));
   }

   final PyObject unicode_capitalize() {
      if (this.getString().length() == 0) {
         return this;
      } else {
         StringBuilder buffer = new StringBuilder(this.getString().length());
         boolean first = true;
         Iterator iter = this.newSubsequenceIterator();

         while(iter.hasNext()) {
            if (first) {
               buffer.appendCodePoint(Character.toUpperCase((Integer)iter.next()));
               first = false;
            } else {
               buffer.appendCodePoint(Character.toLowerCase((Integer)iter.next()));
            }
         }

         return new PyUnicode(buffer);
      }
   }

   final PyString unicode_replace(PyObject oldPieceObj, PyObject newPieceObj, int count) {
      PyUnicode newPiece = this.coerceToUnicode(newPieceObj);
      PyUnicode oldPiece = this.coerceToUnicode(oldPieceObj);
      if (this.isBasicPlane() && newPiece.isBasicPlane() && oldPiece.isBasicPlane()) {
         return this._replace(oldPiece.getString(), newPiece.getString(), count);
      } else {
         StringBuilder buffer = new StringBuilder();
         int numSplits;
         if (oldPiece.getCodePointCount() == 0) {
            Iterator iter = this.newSubsequenceIterator();

            for(numSplits = 1; (count == -1 || numSplits < count) && iter.hasNext(); ++numSplits) {
               if (numSplits == 1) {
                  buffer.append(newPiece.getString());
               }

               buffer.appendCodePoint((Integer)iter.next());
               buffer.append(newPiece.getString());
            }

            while(iter.hasNext()) {
               buffer.appendCodePoint((Integer)iter.next());
            }

            return new PyUnicode(buffer);
         } else {
            SplitIterator iter = this.newSplitIterator(oldPiece, count);

            for(numSplits = 0; iter.hasNext(); ++numSplits) {
               buffer.append(((PyUnicode)iter.next()).getString());
               if (iter.hasNext()) {
                  buffer.append(newPiece.getString());
               }
            }

            if (iter.getEndsWithSeparator() && (count == -1 || numSplits <= count)) {
               buffer.append(newPiece.getString());
            }

            return new PyUnicode(buffer);
         }
      }
   }

   public PyString join(PyObject seq) {
      return this.unicode_join(seq);
   }

   final PyUnicode unicode_join(PyObject seq) {
      return this.unicodeJoin(seq);
   }

   final boolean unicode_startswith(PyObject prefix, PyObject start, PyObject end) {
      return this.str_startswith(prefix, start, end);
   }

   final boolean unicode_endswith(PyObject suffix, PyObject start, PyObject end) {
      return this.str_endswith(suffix, start, end);
   }

   final PyObject unicode_translate(PyObject table) {
      return _codecs.translateCharmap(this, "ignore", table);
   }

   final boolean unicode_islower() {
      boolean cased = false;
      Iterator iter = this.newSubsequenceIterator();

      while(iter.hasNext()) {
         int codepoint = (Integer)iter.next();
         if (Character.isUpperCase(codepoint) || Character.isTitleCase(codepoint)) {
            return false;
         }

         if (!cased && Character.isLowerCase(codepoint)) {
            cased = true;
         }
      }

      return cased;
   }

   final boolean unicode_isupper() {
      boolean cased = false;
      Iterator iter = this.newSubsequenceIterator();

      while(iter.hasNext()) {
         int codepoint = (Integer)iter.next();
         if (Character.isLowerCase(codepoint) || Character.isTitleCase(codepoint)) {
            return false;
         }

         if (!cased && Character.isUpperCase(codepoint)) {
            cased = true;
         }
      }

      return cased;
   }

   final boolean unicode_isalpha() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         Iterator iter = this.newSubsequenceIterator();

         do {
            if (!iter.hasNext()) {
               return true;
            }
         } while(Character.isLetter((Integer)iter.next()));

         return false;
      }
   }

   final boolean unicode_isalnum() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         Iterator iter = this.newSubsequenceIterator();

         int codePoint;
         do {
            if (!iter.hasNext()) {
               return true;
            }

            codePoint = (Integer)iter.next();
         } while(Character.isLetterOrDigit(codePoint) || Character.getType(codePoint) == 10);

         return false;
      }
   }

   final boolean unicode_isdecimal() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         Iterator iter = this.newSubsequenceIterator();

         do {
            if (!iter.hasNext()) {
               return true;
            }
         } while(Character.getType((Integer)iter.next()) == 9);

         return false;
      }
   }

   final boolean unicode_isdigit() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         Iterator iter = this.newSubsequenceIterator();

         do {
            if (!iter.hasNext()) {
               return true;
            }
         } while(Character.isDigit((Integer)iter.next()));

         return false;
      }
   }

   final boolean unicode_isnumeric() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         Iterator iter = this.newSubsequenceIterator();

         int type;
         do {
            if (!iter.hasNext()) {
               return true;
            }

            type = Character.getType((Integer)iter.next());
         } while(type == 9 || type == 10 || type == 11);

         return false;
      }
   }

   final boolean unicode_istitle() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         boolean cased = false;
         boolean previous_is_cased = false;
         Iterator iter = this.newSubsequenceIterator();

         while(true) {
            while(iter.hasNext()) {
               int codePoint = (Integer)iter.next();
               if (!Character.isUpperCase(codePoint) && !Character.isTitleCase(codePoint)) {
                  if (Character.isLowerCase(codePoint)) {
                     if (!previous_is_cased) {
                        return false;
                     }

                     previous_is_cased = true;
                     cased = true;
                  } else {
                     previous_is_cased = false;
                  }
               } else {
                  if (previous_is_cased) {
                     return false;
                  }

                  previous_is_cased = true;
                  cased = true;
               }
            }

            return cased;
         }
      }
   }

   final boolean unicode_isspace() {
      if (this.getCodePointCount() == 0) {
         return false;
      } else {
         Iterator iter = this.newSubsequenceIterator();

         do {
            if (!iter.hasNext()) {
               return true;
            }
         } while(Character.isWhitespace((Integer)iter.next()));

         return false;
      }
   }

   final boolean unicode_isunicode() {
      Py.warning(Py.DeprecationWarning, "isunicode is deprecated.");
      return true;
   }

   final String unicode_encode(PyObject[] args, String[] keywords) {
      return this.str_encode(args, keywords);
   }

   final PyObject unicode_decode(PyObject[] args, String[] keywords) {
      return this.str_decode(args, keywords);
   }

   final PyTuple unicode___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyUnicode(this.getString())});
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.unicode___format__(formatSpec);
   }

   final PyObject unicode___format__(PyObject formatSpec) {
      return this.str___format__(formatSpec);
   }

   final PyObject unicode__formatter_parser() {
      return new MarkupIterator(this);
   }

   final PyObject unicode__formatter_field_name_split() {
      FieldNameIterator iterator = new FieldNameIterator(this);
      return new PyTuple(new PyObject[]{iterator.pyHead(), iterator});
   }

   final PyObject unicode_format(PyObject[] args, String[] keywords) {
      try {
         return new PyUnicode(this.buildFormattedString(args, keywords, (MarkupIterator)null, (String)null));
      } catch (IllegalArgumentException var4) {
         throw Py.ValueError(var4.getMessage());
      }
   }

   public Iterator iterator() {
      return this.newSubsequenceIterator();
   }

   public PyComplex __complex__() {
      return (new PyString(this.encodeDecimal())).__complex__();
   }

   public int atoi(int base) {
      return (new PyString(this.encodeDecimal())).atoi(base);
   }

   public PyLong atol(int base) {
      return (new PyString(this.encodeDecimal())).atol(base);
   }

   public double atof() {
      return (new PyString(this.encodeDecimal())).atof();
   }

   private String encodeDecimal() {
      if (this.isBasicPlane()) {
         return this.encodeDecimalBasic();
      } else {
         StringBuilder sb = new StringBuilder();
         int i = 0;

         for(Iterator iter = this.newSubsequenceIterator(); iter.hasNext(); ++i) {
            int codePoint = (Integer)iter.next();
            if (Character.isWhitespace(codePoint)) {
               sb.append(' ');
            } else {
               int digit = Character.digit(codePoint, 10);
               if (digit >= 0) {
                  sb.append(digit);
               } else if (0 < codePoint && codePoint < 256) {
                  sb.appendCodePoint(codePoint);
               } else {
                  codecs.encoding_error("strict", "decimal", this.getString(), i, i + 1, "invalid decimal Unicode string");
               }
            }
         }

         return sb.toString();
      }
   }

   private String encodeDecimalBasic() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < this.getString().length(); ++i) {
         char ch = this.getString().charAt(i);
         if (Character.isWhitespace(ch)) {
            sb.append(' ');
         } else {
            int digit = Character.digit(ch, 10);
            if (digit >= 0) {
               sb.append(digit);
            } else if (0 < ch && ch < 256) {
               sb.append(ch);
            } else {
               codecs.encoding_error("strict", "decimal", this.getString(), i, i + 1, "invalid decimal Unicode string");
            }
         }
      }

      return sb.toString();
   }

   static {
      PyType.addBuilder(PyUnicode.class, new PyExposer());
      $assertionsDisabled = !PyUnicode.class.desiredAssertionStatus();
      TYPE = PyType.fromClass(PyUnicode.class);
      BASIC = new IndexTranslator() {
         public int suppCount() {
            return 0;
         }

         public int codePointIndex(int u) {
            return u;
         }

         public int utf16Index(int i) {
            return i;
         }
      };
   }

   private class SepSplitIterator extends SplitIterator {
      private final PyUnicode sep;

      SepSplitIterator(PyUnicode sep, int maxsplit) {
         super(maxsplit);
         this.sep = sep;
      }

      public PyUnicode next() {
         StringBuilder buffer = new StringBuilder();
         this.addLookahead(buffer);
         if (this.numSplits == this.maxsplit) {
            while(this.iter.hasNext()) {
               buffer.appendCodePoint((Integer)this.iter.next());
            }

            return new PyUnicode(buffer);
         } else {
            boolean inSeparator = true;

            while(this.iter.hasNext()) {
               inSeparator = true;
               Iterator sepIter = this.sep.newSubsequenceIterator();

               while(sepIter.hasNext()) {
                  int codepoint = (Integer)this.iter.next();
                  if (codepoint != (Integer)sepIter.next()) {
                     this.addLookahead(buffer);
                     buffer.appendCodePoint(codepoint);
                     inSeparator = false;
                     break;
                  }

                  this.lookahead.add(codepoint);
               }

               if (inSeparator) {
                  this.lookahead.clear();
                  break;
               }
            }

            ++this.numSplits;
            this.completeSeparator = inSeparator;
            return new PyUnicode(buffer);
         }
      }
   }

   private class LineSplitIterator implements Iterator {
      private final PeekIterator iter = new PeekIterator(PyUnicode.this.newSubsequenceIterator());
      private final boolean keepends;

      LineSplitIterator(boolean keepends) {
         this.keepends = keepends;
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public PyObject next() {
         StringBuilder buffer = new StringBuilder();

         while(this.iter.hasNext()) {
            int codepoint = (Integer)this.iter.next();
            if (codepoint == 13 && this.iter.peek() != null && (Integer)this.iter.peek() == 10) {
               if (this.keepends) {
                  buffer.appendCodePoint(codepoint);
                  buffer.appendCodePoint((Integer)this.iter.next());
               } else {
                  this.iter.next();
               }
               break;
            }

            if (codepoint == 10 || codepoint == 13 || Character.getType(codepoint) == 13) {
               if (this.keepends) {
                  buffer.appendCodePoint(codepoint);
               }
               break;
            }

            buffer.appendCodePoint(codepoint);
         }

         return new PyUnicode(buffer);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class ReversedIterator implements Iterator {
      private final List reversed = Generic.list();
      private final Iterator iter;

      ReversedIterator(Iterator iter) {
         while(iter.hasNext()) {
            this.reversed.add(iter.next());
         }

         Collections.reverse(this.reversed);
         this.iter = this.reversed.iterator();
      }

      public boolean hasNext() {
         return this.iter.hasNext();
      }

      public Object next() {
         return this.iter.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class PeekIterator implements Iterator {
      private Object lookahead = null;
      private final Iterator iter;

      public PeekIterator(Iterator iter) {
         this.iter = iter;
         this.next();
      }

      public Object peek() {
         return this.lookahead;
      }

      public boolean hasNext() {
         return this.lookahead != null;
      }

      public Object next() {
         Object peeked = this.lookahead;
         this.lookahead = this.iter.hasNext() ? this.iter.next() : null;
         return peeked;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private class WhitespaceSplitIterator extends SplitIterator {
      WhitespaceSplitIterator(int maxsplit) {
         super(maxsplit);
      }

      public PyUnicode next() {
         StringBuilder buffer = new StringBuilder();
         this.addLookahead(buffer);
         if (this.numSplits == this.maxsplit) {
            while(this.iter.hasNext()) {
               buffer.appendCodePoint((Integer)this.iter.next());
            }

            return new PyUnicode(buffer);
         } else {
            boolean inSeparator = false;

            for(boolean atBeginning = this.numSplits == 0; this.iter.hasNext(); atBeginning = false) {
               int codepoint = (Integer)this.iter.next();
               if (Character.isWhitespace(codepoint)) {
                  this.completeSeparator = true;
                  if (!atBeginning) {
                     inSeparator = true;
                  }
               } else {
                  if (inSeparator) {
                     this.completeSeparator = false;
                     this.lookahead.add(codepoint);
                     break;
                  }

                  this.completeSeparator = false;
                  buffer.appendCodePoint(codepoint);
               }
            }

            ++this.numSplits;
            return new PyUnicode(buffer);
         }
      }
   }

   private abstract class SplitIterator implements Iterator {
      protected final int maxsplit;
      protected final Iterator iter = PyUnicode.this.newSubsequenceIterator();
      protected final LinkedList lookahead = new LinkedList();
      protected int numSplits = 0;
      protected boolean completeSeparator = false;

      SplitIterator(int maxsplit) {
         this.maxsplit = maxsplit;
      }

      public boolean hasNext() {
         return this.lookahead.peek() != null || this.iter.hasNext() && (this.maxsplit == -1 || this.numSplits <= this.maxsplit);
      }

      protected void addLookahead(StringBuilder buffer) {
         Iterator var2 = this.lookahead.iterator();

         while(var2.hasNext()) {
            int codepoint = (Integer)var2.next();
            buffer.appendCodePoint(codepoint);
         }

         this.lookahead.clear();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public boolean getEndsWithSeparator() {
         return this.completeSeparator && !this.hasNext();
      }
   }

   private static class StripIterator implements Iterator {
      private final Iterator iter;
      private int lookahead = -1;

      public StripIterator(PyUnicode sep, Iterator iter) {
         this.iter = iter;
         if (sep != null) {
            Set sepSet = Generic.set();
            Iterator sepIter = sep.newSubsequenceIterator();

            while(sepIter.hasNext()) {
               sepSet.add(sepIter.next());
            }

            while(iter.hasNext()) {
               int codePoint = (Integer)iter.next();
               if (!sepSet.contains(codePoint)) {
                  this.lookahead = codePoint;
                  return;
               }
            }
         } else {
            while(iter.hasNext()) {
               int codePoint = (Integer)iter.next();
               if (!Character.isWhitespace(codePoint)) {
                  this.lookahead = codePoint;
                  return;
               }
            }
         }

      }

      public boolean hasNext() {
         return this.lookahead != -1;
      }

      public Integer next() {
         int old = this.lookahead;
         if (this.iter.hasNext()) {
            this.lookahead = (Integer)this.iter.next();
         } else {
            this.lookahead = -1;
         }

         return old;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class SteppedIterator implements Iterator {
      private final Iterator iter;
      private final int step;
      private Object lookahead = null;

      public SteppedIterator(int step, Iterator iter) {
         this.iter = iter;
         this.step = step;
         this.lookahead = this.advance();
      }

      private Object advance() {
         if (!this.iter.hasNext()) {
            return null;
         } else {
            Object elem = this.iter.next();

            for(int i = 1; i < this.step && this.iter.hasNext(); ++i) {
               this.iter.next();
            }

            return elem;
         }
      }

      public boolean hasNext() {
         return this.lookahead != null;
      }

      public Object next() {
         Object old = this.lookahead;
         if (this.iter.hasNext()) {
            this.lookahead = this.iter.next();

            for(int i = 1; i < this.step && this.iter.hasNext(); ++i) {
               this.iter.next();
            }
         } else {
            this.lookahead = null;
         }

         return old;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private class SubsequenceIteratorBasic implements Iterator {
      protected int current;
      protected int stop;
      protected int step;

      SubsequenceIteratorBasic(int start, int stop, int step) {
         this.current = start;
         this.stop = stop;
         this.step = step;
      }

      SubsequenceIteratorBasic() {
         this(0, PyUnicode.this.getCodePointCount(), 1);
      }

      public boolean hasNext() {
         return this.current < this.stop;
      }

      public Integer next() {
         int codePoint = this.nextCodePoint();

         for(int j = 1; j < this.step && this.hasNext(); ++j) {
            this.nextCodePoint();
         }

         return codePoint;
      }

      protected int nextCodePoint() {
         return PyUnicode.this.getString().charAt(this.current++);
      }

      public void remove() {
         throw new UnsupportedOperationException("Not supported on PyUnicode objects (immutable)");
      }
   }

   private class SubsequenceIteratorImpl extends SubsequenceIteratorBasic {
      private int k;

      SubsequenceIteratorImpl(int start, int stop, int step) {
         super(start, stop, step);
         this.k = PyUnicode.this.translator.utf16Index(this.current);
      }

      SubsequenceIteratorImpl() {
         this(0, PyUnicode.this.getCodePointCount(), 1);
      }

      protected int nextCodePoint() {
         int W1 = PyUnicode.this.getString().charAt(this.k);
         int U;
         if (W1 >= '\ud800' && W1 < '\udc00') {
            int W2 = PyUnicode.this.getString().charAt(this.k + 1);
            U = ((W1 & 1023) << 10 | W2 & 1023) + 65536;
            this.k += 2;
         } else {
            U = W1;
            ++this.k;
         }

         ++this.current;
         return U;
      }
   }

   private final class Supplementary implements IndexTranslator {
      final int[] count;
      static final int LOG2M = 4;
      static final int M = 16;
      static final int MASK = 15;

      Supplementary(int[] count) {
         this.count = count;
      }

      public int codePointIndex(int u) {
         int k2 = (u >> 4) + 1;
         int c2 = this.count[k2 - 1];
         int k1 = Math.max(0, u - c2) >> 4;
         int c1 = k1 == 0 ? 0 : this.count[k1 - 1];

         while(c2 != c1) {
            int k = (k1 + k2) / 2;
            if (k == k1) {
               k = (k1 << 4) + c1;

               while(k < u) {
                  if (Character.isHighSurrogate(PyUnicode.this.string.charAt(k++))) {
                     ++c1;
                     if (c1 == c2) {
                        break;
                     }

                     ++k;
                  }
               }

               return u - c1;
            }

            int c = this.count[k - 1];
            if ((k << 4) + c > u) {
               k2 = k;
               c2 = c;
            } else {
               k1 = k;
               c1 = c;
            }
         }

         return u - c1;
      }

      public int utf16Index(int i) {
         int k = i >> 4;
         int d = k == 0 ? 0 : this.count[k - 1];
         int e = this.count[k];
         if (d == e) {
            return i + d;
         } else {
            for(int q = i & -16; q < i; ++q) {
               if (Character.isHighSurrogate(PyUnicode.this.string.charAt(q + d))) {
                  ++d;
                  if (d == e) {
                     break;
                  }
               }
            }

            return i + d;
         }
      }

      public int suppCount() {
         return this.count[this.count.length - 1];
      }
   }

   private interface IndexTranslator extends Serializable {
      int suppCount();

      int codePointIndex(int var1);

      int utf16Index(int var1);
   }

   private static class unicode___mod___exposer extends PyBuiltinMethodNarrow {
      public unicode___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public unicode___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode___mod__(var1);
      }
   }

   private static class unicode___str___exposer extends PyBuiltinMethodNarrow {
      public unicode___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public unicode___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode___str__();
      }
   }

   private static class unicode___len___exposer extends PyBuiltinMethodNarrow {
      public unicode___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public unicode___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyUnicode)this.self).unicode___len__());
      }
   }

   private static class unicode___repr___exposer extends PyBuiltinMethodNarrow {
      public unicode___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public unicode___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode___repr__();
      }
   }

   private static class unicode___getitem___exposer extends PyBuiltinMethodNarrow {
      public unicode___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public unicode___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode___getitem__(var1);
      }
   }

   private static class unicode___getslice___exposer extends PyBuiltinMethodNarrow {
      public unicode___getslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyUnicode)this.self).unicode___getslice__(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode___getslice__(var1, var2, (PyObject)null);
      }
   }

   private static class unicode___cmp___exposer extends PyBuiltinMethodNarrow {
      public unicode___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyUnicode)this.self).unicode___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("unicode.__cmp__(x,y) requires y to be 'unicode', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class unicode___eq___exposer extends PyBuiltinMethodNarrow {
      public unicode___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyUnicode)this.self).unicode___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class unicode___ne___exposer extends PyBuiltinMethodNarrow {
      public unicode___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyUnicode)this.self).unicode___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class unicode___hash___exposer extends PyBuiltinMethodNarrow {
      public unicode___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public unicode___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyUnicode)this.self).unicode___hash__());
      }
   }

   private static class unicode___contains___exposer extends PyBuiltinMethodNarrow {
      public unicode___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public unicode___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyUnicode)this.self).unicode___contains__(var1));
      }
   }

   private static class unicode___mul___exposer extends PyBuiltinMethodNarrow {
      public unicode___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyUnicode)this.self).unicode___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class unicode___rmul___exposer extends PyBuiltinMethodNarrow {
      public unicode___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyUnicode)this.self).unicode___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class unicode___add___exposer extends PyBuiltinMethodNarrow {
      public unicode___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyUnicode)this.self).unicode___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class unicode_lower_exposer extends PyBuiltinMethodNarrow {
      public unicode_lower_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.lower() -> unicode\n\nReturn a copy of the string S converted to lowercase.";
      }

      public unicode_lower_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.lower() -> unicode\n\nReturn a copy of the string S converted to lowercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_lower_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_lower();
      }
   }

   private static class unicode_upper_exposer extends PyBuiltinMethodNarrow {
      public unicode_upper_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.upper() -> unicode\n\nReturn a copy of S converted to uppercase.";
      }

      public unicode_upper_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.upper() -> unicode\n\nReturn a copy of S converted to uppercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_upper_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_upper();
      }
   }

   private static class unicode_title_exposer extends PyBuiltinMethodNarrow {
      public unicode_title_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.title() -> unicode\n\nReturn a titlecased version of S, i.e. words start with title case\ncharacters, all remaining cased characters have lower case.";
      }

      public unicode_title_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.title() -> unicode\n\nReturn a titlecased version of S, i.e. words start with title case\ncharacters, all remaining cased characters have lower case.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_title_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_title();
      }
   }

   private static class unicode_swapcase_exposer extends PyBuiltinMethodNarrow {
      public unicode_swapcase_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.swapcase() -> unicode\n\nReturn a copy of S with uppercase characters converted to lowercase\nand vice versa.";
      }

      public unicode_swapcase_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.swapcase() -> unicode\n\nReturn a copy of S with uppercase characters converted to lowercase\nand vice versa.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_swapcase_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_swapcase();
      }
   }

   private static class unicode_strip_exposer extends PyBuiltinMethodNarrow {
      public unicode_strip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.strip([chars]) -> unicode\n\nReturn a copy of the string S with leading and trailing\nwhitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is a str, it will be converted to unicode before stripping";
      }

      public unicode_strip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.strip([chars]) -> unicode\n\nReturn a copy of the string S with leading and trailing\nwhitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is a str, it will be converted to unicode before stripping";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_strip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_strip(var1);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_strip((PyObject)null);
      }
   }

   private static class unicode_lstrip_exposer extends PyBuiltinMethodNarrow {
      public unicode_lstrip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.lstrip([chars]) -> unicode\n\nReturn a copy of the string S with leading whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is a str, it will be converted to unicode before stripping";
      }

      public unicode_lstrip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.lstrip([chars]) -> unicode\n\nReturn a copy of the string S with leading whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is a str, it will be converted to unicode before stripping";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_lstrip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_lstrip(var1);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_lstrip((PyObject)null);
      }
   }

   private static class unicode_rstrip_exposer extends PyBuiltinMethodNarrow {
      public unicode_rstrip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.rstrip([chars]) -> unicode\n\nReturn a copy of the string S with trailing whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is a str, it will be converted to unicode before stripping";
      }

      public unicode_rstrip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rstrip([chars]) -> unicode\n\nReturn a copy of the string S with trailing whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is a str, it will be converted to unicode before stripping";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_rstrip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_rstrip(var1);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_rstrip((PyObject)null);
      }
   }

   private static class unicode_partition_exposer extends PyBuiltinMethodNarrow {
      public unicode_partition_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.partition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, and return the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, return S and two empty strings.";
      }

      public unicode_partition_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.partition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, and return the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, return S and two empty strings.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_partition_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_partition(var1);
      }
   }

   private static class unicode_rpartition_exposer extends PyBuiltinMethodNarrow {
      public unicode_rpartition_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.rpartition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, starting at the end of S, and return\nthe part before it, the separator itself, and the part after it.  If the\nseparator is not found, return two empty strings and S.";
      }

      public unicode_rpartition_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rpartition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, starting at the end of S, and return\nthe part before it, the separator itself, and the part after it.  If the\nseparator is not found, return two empty strings and S.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_rpartition_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_rpartition(var1);
      }
   }

   private static class unicode_split_exposer extends PyBuiltinMethodNarrow {
      public unicode_split_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "S.split([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in S, using sep as the\ndelimiter string.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified or is None, any\nwhitespace string is a separator and empty strings are\nremoved from the result.";
      }

      public unicode_split_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.split([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in S, using sep as the\ndelimiter string.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified or is None, any\nwhitespace string is a separator and empty strings are\nremoved from the result.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_split_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode_split(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_split(var1, -1);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_split((PyObject)null, -1);
      }
   }

   private static class unicode_rsplit_exposer extends PyBuiltinMethodNarrow {
      public unicode_rsplit_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "S.rsplit([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in S, using sep as the\ndelimiter string, starting at the end of the string and\nworking to the front.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified, any whitespace string\nis a separator.";
      }

      public unicode_rsplit_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rsplit([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in S, using sep as the\ndelimiter string, starting at the end of the string and\nworking to the front.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified, any whitespace string\nis a separator.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_rsplit_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode_rsplit(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_rsplit(var1, -1);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_rsplit((PyObject)null, -1);
      }
   }

   private static class unicode_splitlines_exposer extends PyBuiltinMethodNarrow {
      public unicode_splitlines_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode_splitlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_splitlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_splitlines(Py.py2boolean(var1));
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_splitlines((boolean)0);
      }
   }

   private static class unicode_index_exposer extends PyBuiltinMethodNarrow {
      public unicode_index_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.index(sub [,start [,end]]) -> int\n\nLike S.find() but raise ValueError when the substring is not found.";
      }

      public unicode_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.index(sub [,start [,end]]) -> int\n\nLike S.find() but raise ValueError when the substring is not found.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyUnicode)this.self).unicode_index(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyUnicode)this.self).unicode_index(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyUnicode)this.self).unicode_index(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_rindex_exposer extends PyBuiltinMethodNarrow {
      public unicode_rindex_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.index(sub [,start [,end]]) -> int\n\nLike S.find() but raise ValueError when the substring is not found.";
      }

      public unicode_rindex_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.index(sub [,start [,end]]) -> int\n\nLike S.find() but raise ValueError when the substring is not found.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_rindex_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyUnicode)this.self).unicode_rindex(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyUnicode)this.self).unicode_rindex(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyUnicode)this.self).unicode_rindex(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_count_exposer extends PyBuiltinMethodNarrow {
      public unicode_count_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.count(sub[, start[, end]]) -> int\n\nReturn the number of non-overlapping occurrences of substring sub in\nUnicode string S[start:end].  Optional arguments start and end are\ninterpreted as in slice notation.";
      }

      public unicode_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.count(sub[, start[, end]]) -> int\n\nReturn the number of non-overlapping occurrences of substring sub in\nUnicode string S[start:end].  Optional arguments start and end are\ninterpreted as in slice notation.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyUnicode)this.self).unicode_count(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyUnicode)this.self).unicode_count(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyUnicode)this.self).unicode_count(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_find_exposer extends PyBuiltinMethodNarrow {
      public unicode_find_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.find(sub [,start [,end]]) -> int\n\nReturn the lowest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public unicode_find_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.find(sub [,start [,end]]) -> int\n\nReturn the lowest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_find_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyUnicode)this.self).unicode_find(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyUnicode)this.self).unicode_find(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyUnicode)this.self).unicode_find(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_rfind_exposer extends PyBuiltinMethodNarrow {
      public unicode_rfind_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.rfind(sub [,start [,end]]) -> int\n\nReturn the highest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public unicode_rfind_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rfind(sub [,start [,end]]) -> int\n\nReturn the highest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_rfind_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyUnicode)this.self).unicode_rfind(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyUnicode)this.self).unicode_rfind(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyUnicode)this.self).unicode_rfind(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_ljust_exposer extends PyBuiltinMethodNarrow {
      public unicode_ljust_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode_ljust_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_ljust_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode_ljust(Py.py2int(var1), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_ljust(Py.py2int(var1), (String)null);
      }
   }

   private static class unicode_rjust_exposer extends PyBuiltinMethodNarrow {
      public unicode_rjust_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode_rjust_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_rjust_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode_rjust(Py.py2int(var1), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_rjust(Py.py2int(var1), (String)null);
      }
   }

   private static class unicode_center_exposer extends PyBuiltinMethodNarrow {
      public unicode_center_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode_center_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_center_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode_center(Py.py2int(var1), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_center(Py.py2int(var1), (String)null);
      }
   }

   private static class unicode_zfill_exposer extends PyBuiltinMethodNarrow {
      public unicode_zfill_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.zfill(width) -> unicode\n\nPad a numeric string S with zeros on the left, to fill a field\nof the specified width. The string S is never truncated.";
      }

      public unicode_zfill_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.zfill(width) -> unicode\n\nPad a numeric string S with zeros on the left, to fill a field\nof the specified width. The string S is never truncated.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_zfill_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_zfill(Py.py2int(var1));
      }
   }

   private static class unicode_expandtabs_exposer extends PyBuiltinMethodNarrow {
      public unicode_expandtabs_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public unicode_expandtabs_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_expandtabs_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_expandtabs(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_expandtabs(8);
      }
   }

   private static class unicode_capitalize_exposer extends PyBuiltinMethodNarrow {
      public unicode_capitalize_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.capitalize() -> unicode\n\nReturn a capitalized version of S, i.e. make the first character\nhave upper case and the rest lower case.";
      }

      public unicode_capitalize_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.capitalize() -> unicode\n\nReturn a capitalized version of S, i.e. make the first character\nhave upper case and the rest lower case.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_capitalize_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode_capitalize();
      }
   }

   private static class unicode_replace_exposer extends PyBuiltinMethodNarrow {
      public unicode_replace_exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "S.replace(old, new[, count]) -> unicode\n\nReturn a copy of S with all occurrences of substring\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced.";
      }

      public unicode_replace_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.replace(old, new[, count]) -> unicode\n\nReturn a copy of S with all occurrences of substring\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_replace_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyUnicode)this.self).unicode_replace(var1, var2, Py.py2int(var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyUnicode)this.self).unicode_replace(var1, var2, -1);
      }
   }

   private static class unicode_join_exposer extends PyBuiltinMethodNarrow {
      public unicode_join_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.join(iterable) -> unicode\n\nReturn a string which is the concatenation of the strings in the\niterable.  The separator between elements is S.";
      }

      public unicode_join_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.join(iterable) -> unicode\n\nReturn a string which is the concatenation of the strings in the\niterable.  The separator between elements is S.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_join_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_join(var1);
      }
   }

   private static class unicode_startswith_exposer extends PyBuiltinMethodNarrow {
      public unicode_startswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.startswith(prefix[, start[, end]]) -> bool\n\nReturn True if S starts with the specified prefix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nprefix can also be a tuple of strings to try.";
      }

      public unicode_startswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.startswith(prefix[, start[, end]]) -> bool\n\nReturn True if S starts with the specified prefix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nprefix can also be a tuple of strings to try.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_startswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyUnicode)this.self).unicode_startswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyUnicode)this.self).unicode_startswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyUnicode)this.self).unicode_startswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_endswith_exposer extends PyBuiltinMethodNarrow {
      public unicode_endswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.endswith(suffix[, start[, end]]) -> bool\n\nReturn True if S ends with the specified suffix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nsuffix can also be a tuple of strings to try.";
      }

      public unicode_endswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.endswith(suffix[, start[, end]]) -> bool\n\nReturn True if S ends with the specified suffix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nsuffix can also be a tuple of strings to try.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_endswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyUnicode)this.self).unicode_endswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyUnicode)this.self).unicode_endswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyUnicode)this.self).unicode_endswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class unicode_translate_exposer extends PyBuiltinMethodNarrow {
      public unicode_translate_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.translate(table) -> unicode\n\nReturn a copy of the string S, where all characters have been mapped\nthrough the given translation table, which must be a mapping of\nUnicode ordinals to Unicode ordinals, Unicode strings or None.\nUnmapped characters are left untouched. Characters mapped to None\nare deleted.";
      }

      public unicode_translate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.translate(table) -> unicode\n\nReturn a copy of the string S, where all characters have been mapped\nthrough the given translation table, which must be a mapping of\nUnicode ordinals to Unicode ordinals, Unicode strings or None.\nUnmapped characters are left untouched. Characters mapped to None\nare deleted.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_translate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode_translate(var1);
      }
   }

   private static class unicode_islower_exposer extends PyBuiltinMethodNarrow {
      public unicode_islower_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.islower() -> bool\n\nReturn True if all cased characters in S are lowercase and there is\nat least one cased character in S, False otherwise.";
      }

      public unicode_islower_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.islower() -> bool\n\nReturn True if all cased characters in S are lowercase and there is\nat least one cased character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_islower_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_islower());
      }
   }

   private static class unicode_isupper_exposer extends PyBuiltinMethodNarrow {
      public unicode_isupper_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isupper() -> bool\n\nReturn True if all cased characters in S are uppercase and there is\nat least one cased character in S, False otherwise.";
      }

      public unicode_isupper_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isupper() -> bool\n\nReturn True if all cased characters in S are uppercase and there is\nat least one cased character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isupper_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isupper());
      }
   }

   private static class unicode_isalpha_exposer extends PyBuiltinMethodNarrow {
      public unicode_isalpha_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isalpha() -> bool\n\nReturn True if all characters in S are alphabetic\nand there is at least one character in S, False otherwise.";
      }

      public unicode_isalpha_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isalpha() -> bool\n\nReturn True if all characters in S are alphabetic\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isalpha_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isalpha());
      }
   }

   private static class unicode_isalnum_exposer extends PyBuiltinMethodNarrow {
      public unicode_isalnum_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isalnum() -> bool\n\nReturn True if all characters in S are alphanumeric\nand there is at least one character in S, False otherwise.";
      }

      public unicode_isalnum_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isalnum() -> bool\n\nReturn True if all characters in S are alphanumeric\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isalnum_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isalnum());
      }
   }

   private static class unicode_isdecimal_exposer extends PyBuiltinMethodNarrow {
      public unicode_isdecimal_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isdecimal() -> bool\n\nReturn True if there are only decimal characters in S,\nFalse otherwise.";
      }

      public unicode_isdecimal_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isdecimal() -> bool\n\nReturn True if there are only decimal characters in S,\nFalse otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isdecimal_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isdecimal());
      }
   }

   private static class unicode_isdigit_exposer extends PyBuiltinMethodNarrow {
      public unicode_isdigit_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isdigit() -> bool\n\nReturn True if all characters in S are digits\nand there is at least one character in S, False otherwise.";
      }

      public unicode_isdigit_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isdigit() -> bool\n\nReturn True if all characters in S are digits\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isdigit_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isdigit());
      }
   }

   private static class unicode_isnumeric_exposer extends PyBuiltinMethodNarrow {
      public unicode_isnumeric_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isnumeric() -> bool\n\nReturn True if there are only numeric characters in S,\nFalse otherwise.";
      }

      public unicode_isnumeric_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isnumeric() -> bool\n\nReturn True if there are only numeric characters in S,\nFalse otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isnumeric_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isnumeric());
      }
   }

   private static class unicode_istitle_exposer extends PyBuiltinMethodNarrow {
      public unicode_istitle_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.istitle() -> bool\n\nReturn True if S is a titlecased string and there is at least one\ncharacter in S, i.e. upper- and titlecase characters may only\nfollow uncased characters and lowercase characters only cased ones.\nReturn False otherwise.";
      }

      public unicode_istitle_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.istitle() -> bool\n\nReturn True if S is a titlecased string and there is at least one\ncharacter in S, i.e. upper- and titlecase characters may only\nfollow uncased characters and lowercase characters only cased ones.\nReturn False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_istitle_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_istitle());
      }
   }

   private static class unicode_isspace_exposer extends PyBuiltinMethodNarrow {
      public unicode_isspace_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isspace() -> bool\n\nReturn True if all characters in S are whitespace\nand there is at least one character in S, False otherwise.";
      }

      public unicode_isspace_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isspace() -> bool\n\nReturn True if all characters in S are whitespace\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isspace_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isspace());
      }
   }

   private static class unicode_isunicode_exposer extends PyBuiltinMethodNarrow {
      public unicode_isunicode_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "isunicode is deprecated.";
      }

      public unicode_isunicode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "isunicode is deprecated.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_isunicode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyUnicode)this.self).unicode_isunicode());
      }
   }

   private static class unicode_encode_exposer extends PyBuiltinMethod {
      public unicode_encode_exposer(String var1) {
         super(var1);
         super.doc = "S.encode([encoding[,errors]]) -> string or unicode\n\nEncodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeEncodeError. Other possible values are 'ignore', 'replace' and\n'xmlcharrefreplace' as well as any other name registered with\ncodecs.register_error that can handle UnicodeEncodeErrors.";
      }

      public unicode_encode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.encode([encoding[,errors]]) -> string or unicode\n\nEncodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeEncodeError. Other possible values are 'ignore', 'replace' and\n'xmlcharrefreplace' as well as any other name registered with\ncodecs.register_error that can handle UnicodeEncodeErrors.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_encode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         String var10000 = ((PyUnicode)this.self).unicode_encode(var1, var2);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class unicode_decode_exposer extends PyBuiltinMethod {
      public unicode_decode_exposer(String var1) {
         super(var1);
         super.doc = "S.decode([encoding[,errors]]) -> string or unicode\n\nDecodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError. Other possible values are 'ignore' and 'replace'\nas well as any other name registerd with codecs.register_error that is\nable to handle UnicodeDecodeErrors.";
      }

      public unicode_decode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.decode([encoding[,errors]]) -> string or unicode\n\nDecodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError. Other possible values are 'ignore' and 'replace'\nas well as any other name registerd with codecs.register_error that is\nable to handle UnicodeDecodeErrors.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_decode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyUnicode)this.self).unicode_decode(var1, var2);
      }
   }

   private static class unicode___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public unicode___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public unicode___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode___getnewargs__();
      }
   }

   private static class unicode___format___exposer extends PyBuiltinMethodNarrow {
      public unicode___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.__format__(format_spec) -> unicode\n\nReturn a formatted version of S as described by format_spec.";
      }

      public unicode___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.__format__(format_spec) -> unicode\n\nReturn a formatted version of S as described by format_spec.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyUnicode)this.self).unicode___format__(var1);
      }
   }

   private static class unicode__formatter_parser_exposer extends PyBuiltinMethodNarrow {
      public unicode__formatter_parser_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public unicode__formatter_parser_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode__formatter_parser_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode__formatter_parser();
      }
   }

   private static class unicode__formatter_field_name_split_exposer extends PyBuiltinMethodNarrow {
      public unicode__formatter_field_name_split_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public unicode__formatter_field_name_split_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode__formatter_field_name_split_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyUnicode)this.self).unicode__formatter_field_name_split();
      }
   }

   private static class unicode_format_exposer extends PyBuiltinMethod {
      public unicode_format_exposer(String var1) {
         super(var1);
         super.doc = "S.format(*args, **kwargs) -> unicode\n\nReturn a formatted version of S, using substitutions from args and kwargs.\nThe substitutions are identified by braces ('{' and '}').";
      }

      public unicode_format_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.format(*args, **kwargs) -> unicode\n\nReturn a formatted version of S, using substitutions from args and kwargs.\nThe substitutions are identified by braces ('{' and '}').";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new unicode_format_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyUnicode)this.self).unicode_format(var1, var2);
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyUnicode.unicode_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new unicode___mod___exposer("__mod__"), new unicode___str___exposer("__str__"), new unicode___len___exposer("__len__"), new unicode___repr___exposer("__repr__"), new unicode___getitem___exposer("__getitem__"), new unicode___getslice___exposer("__getslice__"), new unicode___cmp___exposer("__cmp__"), new unicode___eq___exposer("__eq__"), new unicode___ne___exposer("__ne__"), new unicode___hash___exposer("__hash__"), new unicode___contains___exposer("__contains__"), new unicode___mul___exposer("__mul__"), new unicode___rmul___exposer("__rmul__"), new unicode___add___exposer("__add__"), new unicode_lower_exposer("lower"), new unicode_upper_exposer("upper"), new unicode_title_exposer("title"), new unicode_swapcase_exposer("swapcase"), new unicode_strip_exposer("strip"), new unicode_lstrip_exposer("lstrip"), new unicode_rstrip_exposer("rstrip"), new unicode_partition_exposer("partition"), new unicode_rpartition_exposer("rpartition"), new unicode_split_exposer("split"), new unicode_rsplit_exposer("rsplit"), new unicode_splitlines_exposer("splitlines"), new unicode_index_exposer("index"), new unicode_rindex_exposer("rindex"), new unicode_count_exposer("count"), new unicode_find_exposer("find"), new unicode_rfind_exposer("rfind"), new unicode_ljust_exposer("ljust"), new unicode_rjust_exposer("rjust"), new unicode_center_exposer("center"), new unicode_zfill_exposer("zfill"), new unicode_expandtabs_exposer("expandtabs"), new unicode_capitalize_exposer("capitalize"), new unicode_replace_exposer("replace"), new unicode_join_exposer("join"), new unicode_startswith_exposer("startswith"), new unicode_endswith_exposer("endswith"), new unicode_translate_exposer("translate"), new unicode_islower_exposer("islower"), new unicode_isupper_exposer("isupper"), new unicode_isalpha_exposer("isalpha"), new unicode_isalnum_exposer("isalnum"), new unicode_isdecimal_exposer("isdecimal"), new unicode_isdigit_exposer("isdigit"), new unicode_isnumeric_exposer("isnumeric"), new unicode_istitle_exposer("istitle"), new unicode_isspace_exposer("isspace"), new unicode_isunicode_exposer("isunicode"), new unicode_encode_exposer("encode"), new unicode_decode_exposer("decode"), new unicode___getnewargs___exposer("__getnewargs__"), new unicode___format___exposer("__format__"), new unicode__formatter_parser_exposer("_formatter_parser"), new unicode__formatter_field_name_split_exposer("_formatter_field_name_split"), new unicode_format_exposer("format")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("unicode", PyUnicode.class, PyBaseString.class, (boolean)1, "unicode(object='') -> unicode object\nunicode(string[, encoding[, errors]]) -> unicode object\n\nCreate a new Unicode object from the given encoded string.\nencoding defaults to the current default string encoding.\nerrors can be 'strict', 'replace' or 'ignore' and defaults to 'strict'.", var1, var2, new exposed___new__());
      }
   }
}
