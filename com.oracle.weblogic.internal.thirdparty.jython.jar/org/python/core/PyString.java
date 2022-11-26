package org.python.core;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.core.buffer.BaseBuffer;
import org.python.core.buffer.SimpleStringBuffer;
import org.python.core.stringlib.FieldNameIterator;
import org.python.core.stringlib.InternalFormat;
import org.python.core.stringlib.MarkupIterator;
import org.python.core.stringlib.TextFormatter;
import org.python.core.util.StringUtil;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "str",
   base = PyBaseString.class,
   doc = "str(object) -> string\n\nReturn a nice string representation of the object.\nIf the argument is a string, the return value is the same object."
)
public class PyString extends PyBaseString implements BufferProtocol {
   public static final PyType TYPE;
   protected String string;
   protected transient boolean interned;
   private Reference export;
   private static char[] hexdigit;
   private static ucnhashAPI pucnHash;
   private static final int SWAP_CASE = 32;
   private static final String UF_RE = "(?:(?:(?:\\d+\\.?|\\.\\d)\\d*(?:[eE][+-]?\\d+)?)|[infatyINFATY]+)";
   private static Pattern floatPattern;
   private static Pattern complexPattern;

   public String getString() {
      return this.string;
   }

   public PyString() {
      this("", true);
   }

   protected PyString(PyType subType, String string, boolean isBytes) {
      super(subType);
      this.interned = false;
      if (string == null) {
         throw new IllegalArgumentException("Cannot create PyString from null");
      } else if (!isBytes && !isBytes(string)) {
         throw new IllegalArgumentException("Cannot create PyString with non-byte value");
      } else {
         this.string = string;
      }
   }

   public PyString(PyType subType, String string) {
      this(subType, string, false);
   }

   public PyString(String string) {
      this(TYPE, string);
   }

   public PyString(char c) {
      this(TYPE, String.valueOf(c));
   }

   PyString(StringBuilder buffer) {
      this(TYPE, buffer.toString());
   }

   private PyString(String string, boolean isBytes) {
      super(TYPE);
      this.interned = false;
      if (!isBytes && !isBytes(string)) {
         throw new IllegalArgumentException("Cannot create PyString with non-byte value");
      } else {
         this.string = string;
      }
   }

   private static boolean isBytes(String s) {
      int k = s.length();
      if (k == 0) {
         return true;
      } else {
         char c;
         for(c = 0; k > 8; c |= s.charAt(k)) {
            --k;
            c |= s.charAt(k);
            --k;
            c |= s.charAt(k);
            --k;
            c |= s.charAt(k);
            --k;
            c |= s.charAt(k);
            --k;
            c |= s.charAt(k);
            --k;
            c |= s.charAt(k);
            --k;
            c |= s.charAt(k);
            --k;
         }

         while(k > 0) {
            --k;
            c |= s.charAt(k);
         }

         return c < 256;
      }
   }

   public static PyString fromInterned(String interned) {
      PyString str = new PyString(TYPE, interned);
      str.interned = true;
      return str;
   }

   public boolean isBasicPlane() {
      return true;
   }

   @ExposedNew
   static PyObject str_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("str", args, keywords, new String[]{"object"}, 0);
      PyObject S = ap.getPyObject(0, (PyObject)null);
      String str;
      if (S == null) {
         str = "";
      } else {
         PyObject S = S.__str__();
         if (S instanceof PyUnicode) {
            str = codecs.encode((PyUnicode)S, (String)null, (String)null);
         } else {
            str = S.toString();
         }
      }

      return (PyObject)(new_.for_type == subtype ? new PyString(str) : new PyStringDerived(subtype, str));
   }

   public int[] toCodePoints() {
      int n = this.getString().length();
      int[] codePoints = new int[n];

      for(int i = 0; i < n; ++i) {
         codePoints[i] = this.getString().charAt(i);
      }

      return codePoints;
   }

   public synchronized PyBuffer getBuffer(int flags) {
      BaseBuffer pybuf = this.getExistingBuffer(flags);
      if (pybuf == null) {
         pybuf = new SimpleStringBuffer(flags, this, this.getString());
         this.export = new SoftReference(pybuf);
      }

      return (PyBuffer)pybuf;
   }

   private BaseBuffer getExistingBuffer(int flags) {
      BaseBuffer pybuf = null;
      if (this.export != null) {
         pybuf = (BaseBuffer)this.export.get();
         if (pybuf != null) {
            pybuf = pybuf.getBufferAgain(flags);
         }
      }

      return pybuf;
   }

   public String substring(int start, int end) {
      return this.getString().substring(start, end);
   }

   public PyString __str__() {
      return this.str___str__();
   }

   final PyString str___str__() {
      return this.getClass() == PyString.class ? this : new PyString(this.getString(), true);
   }

   public PyUnicode __unicode__() {
      return new PyUnicode(this);
   }

   public int __len__() {
      return this.str___len__();
   }

   final int str___len__() {
      return this.getString().length();
   }

   public String toString() {
      return this.getString();
   }

   public String internedString() {
      if (this.interned) {
         return this.getString();
      } else {
         this.string = this.getString().intern();
         this.interned = true;
         return this.getString();
      }
   }

   public PyString __repr__() {
      return this.str___repr__();
   }

   final PyString str___repr__() {
      return new PyString(encode_UnicodeEscape(this.getString(), true));
   }

   public static String encode_UnicodeEscape(String str, boolean use_quotes) {
      char quote = use_quotes ? 63 : 0;
      return encode_UnicodeEscape(str, (char)quote);
   }

   public static String encode_UnicodeEscape(String str, char quote) {
      boolean use_quotes;
      switch (quote) {
         case 34:
         case 39:
            use_quotes = true;
            break;
         case 63:
            use_quotes = true;
            quote = str.indexOf(39) >= 0 && str.indexOf(34) == -1 ? 34 : 39;
            break;
         default:
            use_quotes = false;
      }

      int size = str.length();
      StringBuilder v = new StringBuilder(size + (size >> 2) + 2);
      if (use_quotes) {
         v.append((char)quote);
      }

      int i = 0;

      while(true) {
         while(size-- > 0) {
            int ch = str.charAt(i++);
            if (use_quotes && ch == quote || ch == '\\') {
               v.append('\\');
               v.append((char)ch);
            } else {
               if (size > 0 && ch >= '\ud800' && ch < '\udc00') {
                  char ch2 = str.charAt(i++);
                  --size;
                  if (ch2 >= '\udc00' && ch2 <= '\udfff') {
                     int ucs = ((ch & 1023) << 10 | ch2 & 1023) + 65536;
                     v.append('\\');
                     v.append('U');
                     v.append(hexdigit[ucs >> 28 & 15]);
                     v.append(hexdigit[ucs >> 24 & 15]);
                     v.append(hexdigit[ucs >> 20 & 15]);
                     v.append(hexdigit[ucs >> 16 & 15]);
                     v.append(hexdigit[ucs >> 12 & 15]);
                     v.append(hexdigit[ucs >> 8 & 15]);
                     v.append(hexdigit[ucs >> 4 & 15]);
                     v.append(hexdigit[ucs & 15]);
                     continue;
                  }

                  --i;
                  ++size;
               }

               if (ch >= 256) {
                  v.append('\\');
                  v.append('u');
                  v.append(hexdigit[ch >> 12 & 15]);
                  v.append(hexdigit[ch >> 8 & 15]);
                  v.append(hexdigit[ch >> 4 & 15]);
                  v.append(hexdigit[ch & 15]);
               } else if (ch == '\t') {
                  v.append("\\t");
               } else if (ch == '\n') {
                  v.append("\\n");
               } else if (ch == '\r') {
                  v.append("\\r");
               } else if (ch >= ' ' && ch < 127) {
                  v.append((char)ch);
               } else {
                  v.append('\\');
                  v.append('x');
                  v.append(hexdigit[ch >> 4 & 15]);
                  v.append(hexdigit[ch & 15]);
               }
            }
         }

         if (use_quotes) {
            v.append((char)quote);
         }

         return v.length() > size ? v.toString() : str;
      }
   }

   public static String decode_UnicodeEscape(String str, int start, int end, String errors, boolean unicode) {
      StringBuilder v = new StringBuilder(end - start);
      int s = start;

      while(true) {
         while(s < end) {
            char ch = str.charAt(s);
            if (ch != '\\') {
               v.append(ch);
               ++s;
            } else {
               int loopStart = s++;
               if (s == end) {
                  s = codecs.insertReplacementAndGetResume(v, errors, "unicodeescape", str, loopStart, s + 1, "\\ at end of string");
               } else {
                  ch = str.charAt(s++);
                  int startName;
                  switch (ch) {
                     case '\n':
                        break;
                     case '"':
                        v.append('"');
                        break;
                     case '\'':
                        v.append('\'');
                        break;
                     case '0':
                     case '1':
                     case '2':
                     case '3':
                     case '4':
                     case '5':
                     case '6':
                     case '7':
                        int x = Character.digit(ch, 8);

                        for(startName = 0; startName < 2 && s < end; ++s) {
                           ch = str.charAt(s);
                           if (ch < '0' || ch > '7') {
                              break;
                           }

                           x = (x << 3) + Character.digit(ch, 8);
                           ++startName;
                        }

                        v.append((char)x);
                        break;
                     case 'N':
                        if (!unicode) {
                           v.append('\\');
                           v.append('N');
                        } else {
                           if (pucnHash == null) {
                              PyObject mod = imp.importName("ucnhash", true);
                              mod = mod.__call__();
                              pucnHash = (ucnhashAPI)mod.__tojava__(Object.class);
                              if (pucnHash.getCchMax() < 0) {
                                 throw Py.UnicodeError("Unicode names not loaded");
                              }
                           }

                           if (str.charAt(s) != '{') {
                              s = codecs.insertReplacementAndGetResume(v, errors, "unicodeescape", str, loopStart, s + 1, "malformed \\N character escape");
                           } else {
                              startName = s + 1;
                              int endBrace = startName;

                              for(int maxLen = pucnHash.getCchMax(); endBrace < end && str.charAt(endBrace) != '}' && endBrace - startName <= maxLen; ++endBrace) {
                              }

                              if (endBrace != end && str.charAt(endBrace) == '}') {
                                 int value = pucnHash.getValue(str, startName, endBrace);
                                 if (storeUnicodeCharacter(value, v)) {
                                    s = endBrace + 1;
                                 } else {
                                    s = codecs.insertReplacementAndGetResume(v, errors, "unicodeescape", str, loopStart, endBrace + 1, "illegal Unicode character");
                                 }
                                 continue;
                              }

                              s = codecs.insertReplacementAndGetResume(v, errors, "unicodeescape", str, loopStart, endBrace, "malformed \\N character escape");
                           }
                        }
                        break;
                     case 'U':
                        if (!unicode) {
                           v.append('\\');
                           v.append('U');
                        } else {
                           s = hexescape(v, errors, 8, s, str, end, "truncated \\UXXXXXXXX");
                        }
                        break;
                     case '\\':
                        v.append('\\');
                        break;
                     case 'a':
                        v.append('\u0007');
                        break;
                     case 'b':
                        v.append('\b');
                        break;
                     case 'f':
                        v.append('\f');
                        break;
                     case 'n':
                        v.append('\n');
                        break;
                     case 'r':
                        v.append('\r');
                        break;
                     case 't':
                        v.append('\t');
                        break;
                     case 'u':
                        if (!unicode) {
                           v.append('\\');
                           v.append('u');
                        } else {
                           s = hexescape(v, errors, 4, s, str, end, "truncated \\uXXXX");
                        }
                        break;
                     case 'v':
                        v.append('\u000b');
                        break;
                     case 'x':
                        s = hexescape(v, errors, 2, s, str, end, "truncated \\xXX");
                        break;
                     default:
                        v.append('\\');
                        v.append(str.charAt(s - 1));
                  }
               }
            }
         }

         return v.toString();
      }
   }

   private static int hexescape(StringBuilder partialDecode, String errors, int digits, int hexDigitStart, String str, int size, String errorMessage) {
      if (hexDigitStart + digits > size) {
         return codecs.insertReplacementAndGetResume(partialDecode, errors, "unicodeescape", str, hexDigitStart - 2, size, errorMessage);
      } else {
         int i = 0;

         int x;
         for(x = 0; i < digits; ++i) {
            char c = str.charAt(hexDigitStart + i);
            int d = Character.digit(c, 16);
            if (d == -1) {
               return codecs.insertReplacementAndGetResume(partialDecode, errors, "unicodeescape", str, hexDigitStart - 2, hexDigitStart + i + 1, errorMessage);
            }

            x = x << 4 & -16;
            if (c >= '0' && c <= '9') {
               x += c - 48;
            } else if (c >= 'a' && c <= 'f') {
               x += 10 + c - 97;
            } else {
               x += 10 + c - 65;
            }
         }

         if (storeUnicodeCharacter(x, partialDecode)) {
            return hexDigitStart + i;
         } else {
            return codecs.insertReplacementAndGetResume(partialDecode, errors, "unicodeescape", str, hexDigitStart - 2, hexDigitStart + i + 1, "illegal Unicode character");
         }
      }
   }

   private static boolean storeUnicodeCharacter(int value, StringBuilder partialDecode) {
      if (value >= 0 && (value < 55296 || value > 57343)) {
         if (value <= 1114111) {
            partialDecode.appendCodePoint(value);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   final PyObject str___getitem__(PyObject index) {
      PyObject ret = this.seq___finditem__(index);
      if (ret == null) {
         throw Py.IndexError("string index out of range");
      } else {
         return ret;
      }
   }

   final PyObject str___getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.seq___getslice__(start, stop, step);
   }

   public int __cmp__(PyObject other) {
      return this.str___cmp__(other);
   }

   final int str___cmp__(PyObject other) {
      if (!(other instanceof PyString)) {
         return -2;
      } else {
         int c = this.getString().compareTo(((PyString)other).getString());
         return c < 0 ? -1 : (c > 0 ? 1 : 0);
      }
   }

   public PyObject __eq__(PyObject other) {
      return other instanceof PyShadowString ? other.__eq__(this) : this.str___eq__(other);
   }

   final PyObject str___eq__(PyObject other) {
      String s = coerce(other);
      if (s == null) {
         return null;
      } else {
         return this.getString().equals(s) ? Py.True : Py.False;
      }
   }

   public PyObject __ne__(PyObject other) {
      return this.str___ne__(other);
   }

   final PyObject str___ne__(PyObject other) {
      String s = coerce(other);
      if (s == null) {
         return null;
      } else {
         return this.getString().equals(s) ? Py.False : Py.True;
      }
   }

   public PyObject __lt__(PyObject other) {
      return this.str___lt__(other);
   }

   final PyObject str___lt__(PyObject other) {
      String s = coerce(other);
      if (s == null) {
         return null;
      } else {
         return this.getString().compareTo(s) < 0 ? Py.True : Py.False;
      }
   }

   public PyObject __le__(PyObject other) {
      return this.str___le__(other);
   }

   final PyObject str___le__(PyObject other) {
      String s = coerce(other);
      if (s == null) {
         return null;
      } else {
         return this.getString().compareTo(s) <= 0 ? Py.True : Py.False;
      }
   }

   public PyObject __gt__(PyObject other) {
      return this.str___gt__(other);
   }

   final PyObject str___gt__(PyObject other) {
      String s = coerce(other);
      if (s == null) {
         return null;
      } else {
         return this.getString().compareTo(s) > 0 ? Py.True : Py.False;
      }
   }

   public PyObject __ge__(PyObject other) {
      return this.str___ge__(other);
   }

   final PyObject str___ge__(PyObject other) {
      String s = coerce(other);
      if (s == null) {
         return null;
      } else {
         return this.getString().compareTo(s) >= 0 ? Py.True : Py.False;
      }
   }

   private static String coerce(PyObject o) {
      return o instanceof PyString ? o.toString() : null;
   }

   public int hashCode() {
      return this.str___hash__();
   }

   final int str___hash__() {
      return this.getString().hashCode();
   }

   public byte[] toBytes() {
      return StringUtil.toBytes(this.getString());
   }

   public Object __tojava__(Class c) {
      if (c.isAssignableFrom(String.class)) {
         return c == CharSequence.class ? this : this.getString();
      } else if ((c == Character.TYPE || c == Character.class) && this.getString().length() == 1) {
         return new Character(this.getString().charAt(0));
      } else {
         if (c.isArray()) {
            if (c.getComponentType() == Byte.TYPE) {
               return this.toBytes();
            }

            if (c.getComponentType() == Character.TYPE) {
               return this.getString().toCharArray();
            }
         }

         if (!c.isAssignableFrom(Collection.class)) {
            return c.isInstance(this) ? this : Py.NoConversion;
         } else {
            List list = new ArrayList();

            for(int i = 0; i < this.__len__(); ++i) {
               list.add(this.pyget(i).__tojava__(String.class));
            }

            return list;
         }
      }
   }

   protected PyObject pyget(int i) {
      return Py.makeCharacter(this.string.charAt(i));
   }

   public int getInt(int i) {
      return this.string.charAt(i);
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (step > 0 && stop < start) {
         stop = start;
      }

      if (step == 1) {
         return this.fromSubstring(start, stop);
      } else {
         int n = sliceLength(start, stop, (long)step);
         char[] new_chars = new char[n];
         int j = 0;

         for(int i = start; j < n; i += step) {
            new_chars[j++] = this.getString().charAt(i);
         }

         return this.createInstance(new String(new_chars), true);
      }
   }

   public PyString createInstance(String str) {
      return new PyString(str);
   }

   protected PyString createInstance(String str, boolean isBasic) {
      return new PyString(str);
   }

   private static String asUTF16StringOrNull(PyObject obj) {
      if (obj instanceof PyString) {
         return ((PyString)obj).getString();
      } else if (obj instanceof BufferProtocol) {
         PyBuffer buf = ((BufferProtocol)obj).getBuffer(284);
         Throwable var2 = null;

         String var3;
         try {
            var3 = buf.toString();
         } catch (Throwable var12) {
            var2 = var12;
            throw var12;
         } finally {
            if (buf != null) {
               if (var2 != null) {
                  try {
                     buf.close();
                  } catch (Throwable var11) {
                     var2.addSuppressed(var11);
                  }
               } else {
                  buf.close();
               }
            }

         }

         return var3;
      } else {
         return null;
      }
   }

   private static String asStringOrNull(PyObject obj) {
      return obj instanceof PyUnicode ? null : asUTF16StringOrNull(obj);
   }

   private static String asStringOrError(PyObject obj) throws PyException {
      String ret = obj instanceof PyUnicode ? null : asUTF16StringOrNull(obj);
      if (ret != null) {
         return ret;
      } else {
         throw Py.TypeError("expected str, bytearray or other buffer compatible object");
      }
   }

   private static String asStringNullOrError(PyObject obj, String name) throws PyException {
      if (obj != null && obj != Py.None) {
         String ret = obj instanceof PyUnicode ? null : asUTF16StringOrNull(obj);
         if (ret != null) {
            return ret;
         } else if (name == null) {
            throw Py.TypeError("expected None, str or buffer compatible object");
         } else {
            throw Py.TypeError(name + " arg must be None, str or buffer compatible object");
         }
      } else {
         return null;
      }
   }

   protected static String asUTF16StringOrError(PyObject obj) {
      String ret = asUTF16StringOrNull(obj);
      if (ret != null) {
         return ret;
      } else {
         throw Py.TypeError("expected str, bytearray, unicode or buffer compatible object");
      }
   }

   public boolean __contains__(PyObject o) {
      return this.str___contains__(o);
   }

   final boolean str___contains__(PyObject o) {
      String other = asUTF16StringOrError(o);
      return this.getString().indexOf(other) >= 0;
   }

   protected PyObject repeat(int count) {
      if (count < 0) {
         count = 0;
      }

      int s = this.getString().length();
      if ((long)s * (long)count > 2147483647L) {
         throw Py.OverflowError("max str len is 2147483647");
      } else {
         char[] new_chars = new char[s * count];

         for(int i = 0; i < count; ++i) {
            this.getString().getChars(0, s, new_chars, i * s);
         }

         return this.createInstance(new String(new_chars));
      }
   }

   public PyObject __mul__(PyObject o) {
      return this.str___mul__(o);
   }

   final PyObject str___mul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject o) {
      return this.str___rmul__(o);
   }

   final PyObject str___rmul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __add__(PyObject other) {
      return this.str___add__(other);
   }

   final PyObject str___add__(PyObject other) {
      String otherStr = asStringOrNull(other);
      if (otherStr != null) {
         return new PyString(this.getString().concat(otherStr), true);
      } else {
         return other instanceof PyUnicode ? this.decode().__add__(other) : null;
      }
   }

   final PyTuple str___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyString(this.getString())});
   }

   public PyTuple __getnewargs__() {
      return this.str___getnewargs__();
   }

   public PyObject __mod__(PyObject other) {
      return this.str___mod__(other);
   }

   public PyObject str___mod__(PyObject other) {
      StringFormatter fmt = new StringFormatter(this.getString(), false);
      return fmt.format(other);
   }

   public PyObject __int__() {
      try {
         return Py.newInteger(this.atoi(10));
      } catch (PyException var2) {
         if (var2.match(Py.OverflowError)) {
            return this.atol(10);
         } else {
            throw var2;
         }
      }
   }

   public PyObject __long__() {
      return this.atol(10);
   }

   public PyFloat __float__() {
      return new PyFloat(this.atof());
   }

   public PyObject __pos__() {
      throw Py.TypeError("bad operand type for unary +");
   }

   public PyObject __neg__() {
      throw Py.TypeError("bad operand type for unary -");
   }

   public PyObject __invert__() {
      throw Py.TypeError("bad operand type for unary ~");
   }

   public PyComplex __complex__() {
      return this.atocx();
   }

   public String lower() {
      return this.str_lower();
   }

   final String str_lower() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         char c = s.charAt(0);
         return this._isupper(c) ? String.valueOf((char)(c ^ 32)) : s;
      } else {
         char[] buf = new char[n];

         for(int i = 0; i < n; ++i) {
            char c = s.charAt(i);
            buf[i] = this._isupper(c) ? (char)(c ^ 32) : c;
         }

         return new String(buf);
      }
   }

   public String upper() {
      return this.str_upper();
   }

   final String str_upper() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         char c = s.charAt(0);
         return this._islower(c) ? String.valueOf((char)(c ^ 32)) : s;
      } else {
         char[] buf = new char[n];

         for(int i = 0; i < n; ++i) {
            char c = s.charAt(i);
            buf[i] = this._islower(c) ? (char)(c ^ 32) : c;
         }

         return new String(buf);
      }
   }

   public String title() {
      return this.str_title();
   }

   final String str_title() {
      char[] chars = this.getString().toCharArray();
      int n = chars.length;
      boolean previous_is_cased = false;

      for(int i = 0; i < n; ++i) {
         char ch = chars[i];
         if (this._isalpha(ch)) {
            if (previous_is_cased) {
               if (this._isupper(ch)) {
                  chars[i] = (char)(ch ^ 32);
               }
            } else if (this._islower(ch)) {
               chars[i] = (char)(ch ^ 32);
            }

            previous_is_cased = true;
         } else {
            previous_is_cased = false;
         }
      }

      return new String(chars);
   }

   public String swapcase() {
      return this.str_swapcase();
   }

   final String str_swapcase() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         char c = s.charAt(0);
         return this._isalpha(c) ? String.valueOf((char)(c ^ 32)) : s;
      } else {
         char[] buf = new char[n];

         for(int i = 0; i < n; ++i) {
            char c = s.charAt(i);
            buf[i] = this._isalpha(c) ? (char)(c ^ 32) : c;
         }

         return new String(buf);
      }
   }

   public String strip() {
      return this._strip();
   }

   public String strip(String stripChars) {
      return this._strip(stripChars);
   }

   public PyObject strip(PyObject stripChars) {
      return this.str_strip(stripChars);
   }

   final PyObject str_strip(PyObject chars) {
      if (chars instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_strip(chars);
      } else {
         String stripChars = asStringNullOrError(chars, "strip");
         return new PyString(this._strip(stripChars), true);
      }
   }

   protected final String _strip() {
      String s = this.getString();
      int right = _stripRight(s);
      if (right < 0) {
         return "";
      } else {
         int left = _stripLeft(s, right);
         return s.substring(left, right + 1);
      }
   }

   protected final String _strip(String stripChars) {
      if (stripChars == null) {
         return this._strip();
      } else {
         String s = this.getString();
         int right = _stripRight(s, stripChars);
         if (right < 0) {
            return "";
         } else {
            int left = _stripLeft(s, stripChars, right);
            return s.substring(left, right + 1);
         }
      }
   }

   private static final int _stripLeft(String s, int right) {
      for(int left = 0; left < right; ++left) {
         if (!Character.isWhitespace(s.charAt(left))) {
            return left;
         }
      }

      return right;
   }

   private static final int _stripLeft(String s, String stripChars, int right) {
      for(int left = 0; left < right; ++left) {
         if (stripChars.indexOf(s.charAt(left)) < 0) {
            return left;
         }
      }

      return right;
   }

   private static final int _stripRight(String s) {
      int right = s.length();

      do {
         --right;
         if (right < 0) {
            return -1;
         }
      } while(Character.isWhitespace(s.charAt(right)));

      return right;
   }

   private static final int _stripRight(String s, String stripChars) {
      int right = s.length();

      do {
         --right;
         if (right < 0) {
            return -1;
         }
      } while(stripChars.indexOf(s.charAt(right)) >= 0);

      return right;
   }

   public String lstrip() {
      return this._lstrip();
   }

   public String lstrip(String stripChars) {
      return this._lstrip(stripChars);
   }

   public PyObject lstrip(PyObject stripChars) {
      return this.str_lstrip(stripChars);
   }

   final PyObject str_lstrip(PyObject chars) {
      if (chars instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_lstrip(chars);
      } else {
         String stripChars = asStringNullOrError(chars, "lstrip");
         return new PyString(this._lstrip(stripChars), true);
      }
   }

   protected final String _lstrip() {
      String s = this.getString();
      int left = _stripLeft(s, s.length());
      return s.substring(left);
   }

   protected final String _lstrip(String stripChars) {
      if (stripChars == null) {
         return this._lstrip();
      } else {
         String s = this.getString();
         int left = _stripLeft(s, stripChars, s.length());
         return s.substring(left);
      }
   }

   public String rstrip() {
      return this._rstrip();
   }

   public String rstrip(String stripChars) {
      return this._rstrip(stripChars);
   }

   public PyObject rstrip(PyObject stripChars) {
      return this.str_rstrip(stripChars);
   }

   final PyObject str_rstrip(PyObject chars) {
      if (chars instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_rstrip(chars);
      } else {
         String stripChars = asStringNullOrError(chars, "rstrip");
         return new PyString(this._rstrip(stripChars), true);
      }
   }

   protected final String _rstrip() {
      String s = this.getString();
      int right = _stripRight(s);
      return right < 0 ? "" : s.substring(0, right + 1);
   }

   protected final String _rstrip(String stripChars) {
      if (stripChars == null) {
         return this._rstrip();
      } else {
         String s = this.getString();
         int right = _stripRight(s, stripChars);
         return s.substring(0, right + 1);
      }
   }

   public PyList split() {
      return this._split((String)null, -1);
   }

   public PyList split(String sep) {
      return this._split(sep, -1);
   }

   public PyList split(String sep, int maxsplit) {
      return this._split(sep, maxsplit);
   }

   public PyList split(PyObject sep) {
      return this.str_split(sep, -1);
   }

   public PyList split(PyObject sep, int maxsplit) {
      return this.str_split(sep, maxsplit);
   }

   final PyList str_split(PyObject sepObj, int maxsplit) {
      if (sepObj instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_split(sepObj, maxsplit);
      } else {
         String sep = asStringNullOrError(sepObj, "split");
         return this._split(sep, maxsplit);
      }
   }

   protected final PyList _split(String sep, int maxsplit) {
      if (sep == null) {
         return this.splitfields(maxsplit);
      } else if (sep.length() == 0) {
         throw Py.ValueError("empty separator");
      } else {
         return this.splitfields(sep, maxsplit);
      }
   }

   private PyList splitfields(int maxsplit) {
      PyList list = new PyList();
      String s = this.getString();
      int length = s.length();
      int start = 0;
      int splits = 0;
      if (maxsplit < 0) {
         maxsplit = length;
      }

      while(start < length) {
         while(start < length && Character.isWhitespace(s.charAt(start))) {
            ++start;
         }

         if (start >= length) {
            break;
         }

         int index;
         if (splits >= maxsplit) {
            index = length;
         } else {
            for(index = start; index < length && !Character.isWhitespace(s.charAt(index)); ++index) {
            }
         }

         list.append(this.fromSubstring(start, index));
         ++splits;
         start = index;
      }

      return list;
   }

   private PyList splitfields(String sep, int maxsplit) {
      PyList list = new PyList();
      String s = this.getString();
      int length = s.length();
      int sepLength = sep.length();
      if (maxsplit < 0) {
         maxsplit = length + 1;
      }

      if (maxsplit == 0) {
         list.append(this);
      } else {
         int start;
         int index;
         if (sepLength == 0) {
            start = maxsplit > length ? length + 1 : maxsplit;
            list.append(this.createInstance(""));

            for(index = 0; index < start - 1; ++index) {
               list.append(this.fromSubstring(index, index + 1));
            }

            list.append(this.fromSubstring(index, length));
         } else {
            start = 0;

            for(index = 0; index < maxsplit; ++index) {
               int index = s.indexOf(sep, start);
               if (index < 0) {
                  break;
               }

               list.append(this.fromSubstring(start, index));
               start = index + sepLength;
            }

            list.append(this.fromSubstring(start, length));
         }
      }

      return list;
   }

   public PyList rsplit() {
      return this._rsplit((String)null, -1);
   }

   public PyList rsplit(String sep) {
      return this._rsplit(sep, -1);
   }

   public PyList rsplit(String sep, int maxsplit) {
      return this._rsplit(sep, maxsplit);
   }

   public PyList rsplit(PyObject sep) {
      return this.str_rsplit(sep, -1);
   }

   public PyList rsplit(PyObject sep, int maxsplit) {
      return this.str_rsplit(sep, maxsplit);
   }

   final PyList str_rsplit(PyObject sepObj, int maxsplit) {
      if (sepObj instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_rsplit(sepObj, maxsplit);
      } else {
         String sep = asStringNullOrError(sepObj, "rsplit");
         return this._rsplit(sep, maxsplit);
      }
   }

   protected final PyList _rsplit(String sep, int maxsplit) {
      if (sep == null) {
         return this.rsplitfields(maxsplit);
      } else if (sep.length() == 0) {
         throw Py.ValueError("empty separator");
      } else {
         return this.rsplitfields(sep, maxsplit);
      }
   }

   private PyList rsplitfields(int maxsplit) {
      PyList list = new PyList();
      String s = this.getString();
      int length = s.length();
      int end = length - 1;
      int splits = 0;
      if (maxsplit < 0) {
         maxsplit = length;
      }

      while(end >= 0) {
         while(end >= 0 && Character.isWhitespace(s.charAt(end))) {
            --end;
         }

         if (end < 0) {
            break;
         }

         int index;
         if (splits >= maxsplit) {
            index = -1;
         } else {
            for(index = end; index >= 0 && !Character.isWhitespace(s.charAt(index)); --index) {
            }
         }

         list.append(this.fromSubstring(index + 1, end + 1));
         ++splits;
         end = index;
      }

      list.reverse();
      return list;
   }

   private PyList rsplitfields(String sep, int maxsplit) {
      PyList list = new PyList();
      String s = this.getString();
      int length = s.length();
      int sepLength = sep.length();
      if (maxsplit < 0) {
         maxsplit = length + 1;
      }

      if (maxsplit == 0) {
         list.append(this);
      } else {
         if (sepLength == 0) {
            throw Py.ValueError("empty separator");
         }

         int end = length;

         for(int splits = 0; splits < maxsplit; ++splits) {
            int index = s.lastIndexOf(sep, end - sepLength);
            if (index < 0) {
               break;
            }

            list.append(this.fromSubstring(index + sepLength, end));
            end = index;
         }

         list.append(this.fromSubstring(0, end));
      }

      list.reverse();
      return list;
   }

   public PyTuple partition(PyObject sepObj) {
      return this.str_partition(sepObj);
   }

   final PyTuple str_partition(PyObject sepObj) {
      if (sepObj instanceof PyUnicode) {
         return this.unicodePartition(sepObj);
      } else {
         String sep = asStringOrError(sepObj);
         if (sep.length() == 0) {
            throw Py.ValueError("empty separator");
         } else {
            int index = this.getString().indexOf(sep);
            return index != -1 ? new PyTuple(new PyObject[]{this.fromSubstring(0, index), sepObj, this.fromSubstring(index + sep.length(), this.getString().length())}) : new PyTuple(new PyObject[]{this, Py.EmptyString, Py.EmptyString});
         }
      }
   }

   final PyTuple unicodePartition(PyObject sepObj) {
      PyUnicode strObj = this.__unicode__();
      String str = strObj.getString();
      String sep = sepObj.asString();
      PyObject sepObj = sepObj.__unicode__();
      if (sep.length() == 0) {
         throw Py.ValueError("empty separator");
      } else {
         int index = str.indexOf(sep);
         if (index != -1) {
            return new PyTuple(new PyObject[]{strObj.fromSubstring(0, index), sepObj, strObj.fromSubstring(index + sep.length(), str.length())});
         } else {
            PyUnicode emptyUnicode = Py.newUnicode("");
            return new PyTuple(new PyObject[]{this, emptyUnicode, emptyUnicode});
         }
      }
   }

   public PyTuple rpartition(PyObject sepObj) {
      return this.str_rpartition(sepObj);
   }

   final PyTuple str_rpartition(PyObject sepObj) {
      if (sepObj instanceof PyUnicode) {
         return this.unicodeRpartition(sepObj);
      } else {
         String sep = asStringOrError(sepObj);
         if (sep.length() == 0) {
            throw Py.ValueError("empty separator");
         } else {
            int index = this.getString().lastIndexOf(sep);
            return index != -1 ? new PyTuple(new PyObject[]{this.fromSubstring(0, index), sepObj, this.fromSubstring(index + sep.length(), this.getString().length())}) : new PyTuple(new PyObject[]{Py.EmptyString, Py.EmptyString, this});
         }
      }
   }

   final PyTuple unicodeRpartition(PyObject sepObj) {
      PyUnicode strObj = this.__unicode__();
      String str = strObj.getString();
      String sep = sepObj.asString();
      PyObject sepObj = sepObj.__unicode__();
      if (sep.length() == 0) {
         throw Py.ValueError("empty separator");
      } else {
         int index = str.lastIndexOf(sep);
         if (index != -1) {
            return new PyTuple(new PyObject[]{strObj.fromSubstring(0, index), sepObj, strObj.fromSubstring(index + sep.length(), str.length())});
         } else {
            PyUnicode emptyUnicode = Py.newUnicode("");
            return new PyTuple(new PyObject[]{emptyUnicode, emptyUnicode, this});
         }
      }
   }

   public PyList splitlines() {
      return this.str_splitlines(false);
   }

   public PyList splitlines(boolean keepends) {
      return this.str_splitlines(keepends);
   }

   final PyList str_splitlines(boolean keepends) {
      PyList list = new PyList();
      char[] chars = this.getString().toCharArray();
      int n = chars.length;
      int j = 0;

      for(int i = 0; i < n; j = i) {
         while(i < n && chars[i] != '\n' && chars[i] != '\r' && Character.getType(chars[i]) != 13) {
            ++i;
         }

         int eol = i;
         if (i < n) {
            if (chars[i] == '\r' && i + 1 < n && chars[i + 1] == '\n') {
               i += 2;
            } else {
               ++i;
            }

            if (keepends) {
               eol = i;
            }
         }

         list.append(this.fromSubstring(j, eol));
      }

      if (j < n) {
         list.append(this.fromSubstring(j, n));
      }

      return list;
   }

   protected PyString fromSubstring(int begin, int end) {
      return new PyString(this.getString().substring(begin, end), true);
   }

   public int index(PyObject sub) {
      return this.str_index(sub, (PyObject)null, (PyObject)null);
   }

   public int index(PyObject sub, PyObject start) throws PyException {
      return this.str_index(sub, start, (PyObject)null);
   }

   public int index(PyObject sub, PyObject start, PyObject end) throws PyException {
      return this.checkIndex(this.str_index(sub, start, end));
   }

   public int index(String sub) {
      return this.index((String)sub, (PyObject)null, (PyObject)null);
   }

   public int index(String sub, PyObject start) {
      return this.index((String)sub, start, (PyObject)null);
   }

   public int index(String sub, PyObject start, PyObject end) {
      return this.checkIndex(this._find(sub, start, end));
   }

   final int str_index(PyObject subObj, PyObject start, PyObject end) {
      return this.checkIndex(this.str_find(subObj, start, end));
   }

   public int rindex(PyObject sub) {
      return this.str_rindex(sub, (PyObject)null, (PyObject)null);
   }

   public int rindex(PyObject sub, PyObject start) throws PyException {
      return this.str_rindex(sub, start, (PyObject)null);
   }

   public int rindex(PyObject sub, PyObject start, PyObject end) throws PyException {
      return this.checkIndex(this.str_rindex(sub, start, end));
   }

   public int rindex(String sub) {
      return this.rindex((String)sub, (PyObject)null, (PyObject)null);
   }

   public int rindex(String sub, PyObject start) {
      return this.rindex((String)sub, start, (PyObject)null);
   }

   public int rindex(String sub, PyObject start, PyObject end) {
      return this.checkIndex(this._rfind(sub, start, end));
   }

   final int str_rindex(PyObject subObj, PyObject start, PyObject end) {
      return this.checkIndex(this.str_rfind(subObj, start, end));
   }

   protected final int checkIndex(int index) throws PyException {
      if (index >= 0) {
         return index;
      } else {
         throw Py.ValueError("substring not found");
      }
   }

   public int count(PyObject sub) {
      return this.count((PyObject)sub, (PyObject)null, (PyObject)null);
   }

   public int count(PyObject sub, PyObject start) {
      return this.count((PyObject)sub, start, (PyObject)null);
   }

   public int count(PyObject sub, PyObject start, PyObject end) {
      return this.str_count(sub, start, end);
   }

   public int count(String sub) {
      return this.count((String)sub, (PyObject)null, (PyObject)null);
   }

   public int count(String sub, PyObject start) {
      return this.count((String)sub, start, (PyObject)null);
   }

   public int count(String sub, PyObject start, PyObject end) {
      return this._count(sub, start, end);
   }

   final int str_count(PyObject subObj, PyObject start, PyObject end) {
      if (subObj instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_count(subObj, start, end);
      } else {
         String sub = asStringOrError(subObj);
         return this._count(sub, start, end);
      }
   }

   protected final int _count(String sub, PyObject startObj, PyObject endObj) {
      int[] indices = this.translateIndices(startObj, endObj);
      int subLen = sub.length();
      int start;
      int end;
      int limit;
      if (subLen == 0) {
         start = indices[2];
         end = indices[3];
         limit = this.__len__();
         return end >= 0 && end >= start && start <= limit ? Math.min(end, limit) - Math.max(start, 0) + 1 : 0;
      } else {
         start = indices[0];
         end = indices[1];
         limit = end - subLen;

         int count;
         int index;
         for(count = 0; start <= limit; start = index + subLen) {
            index = this.getString().indexOf(sub, start);
            if (index < 0 || index > limit) {
               break;
            }

            ++count;
         }

         return count;
      }
   }

   public int find(PyObject sub) {
      return this.find((PyObject)sub, (PyObject)null, (PyObject)null);
   }

   public int find(PyObject sub, PyObject start) {
      return this.find((PyObject)sub, start, (PyObject)null);
   }

   public int find(PyObject sub, PyObject start, PyObject end) {
      return this.str_find(sub, start, end);
   }

   public int find(String sub) {
      return this.find((String)sub, (PyObject)null, (PyObject)null);
   }

   public int find(String sub, PyObject start) {
      return this.find((String)sub, start, (PyObject)null);
   }

   public int find(String sub, PyObject start, PyObject end) {
      return this._find(sub, start, end);
   }

   final int str_find(PyObject subObj, PyObject start, PyObject end) {
      if (subObj instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_find(subObj, start, end);
      } else {
         String sub = asStringOrError(subObj);
         return this._find(sub, start, end);
      }
   }

   protected final int _find(String sub, PyObject startObj, PyObject endObj) {
      int[] indices = this.translateIndices(startObj, endObj);
      int subLen = sub.length();
      int start;
      int end;
      if (subLen == 0) {
         start = indices[2];
         end = indices[3];
         return end >= 0 && end >= start && start <= this.__len__() ? indices[0] : -1;
      } else {
         start = indices[0];
         end = indices[1];
         int found = this.getString().indexOf(sub, start);
         return found >= 0 && found + subLen <= end ? found : -1;
      }
   }

   public int rfind(PyObject sub) {
      return this.rfind((PyObject)sub, (PyObject)null, (PyObject)null);
   }

   public int rfind(PyObject sub, PyObject start) {
      return this.rfind((PyObject)sub, start, (PyObject)null);
   }

   public int rfind(PyObject sub, PyObject start, PyObject end) {
      return this.str_rfind(sub, start, end);
   }

   public int rfind(String sub) {
      return this.rfind((String)sub, (PyObject)null, (PyObject)null);
   }

   public int rfind(String sub, PyObject start) {
      return this.rfind((String)sub, start, (PyObject)null);
   }

   public int rfind(String sub, PyObject start, PyObject end) {
      return this._rfind(sub, start, end);
   }

   final int str_rfind(PyObject subObj, PyObject start, PyObject end) {
      if (subObj instanceof PyUnicode) {
         return ((PyUnicode)this.decode()).unicode_rfind(subObj, start, end);
      } else {
         String sub = asStringOrError(subObj);
         return this._rfind(sub, start, end);
      }
   }

   protected final int _rfind(String sub, PyObject startObj, PyObject endObj) {
      int[] indices = this.translateIndices(startObj, endObj);
      int subLen = sub.length();
      int start;
      int end;
      if (subLen == 0) {
         start = indices[2];
         end = indices[3];
         return end >= 0 && end >= start && start <= this.__len__() ? indices[1] : -1;
      } else {
         start = indices[0];
         end = indices[1];
         int found = this.getString().lastIndexOf(sub, end - subLen);
         return found >= start ? found : -1;
      }
   }

   public double atof() {
      double x = 0.0;
      Matcher m = getFloatPattern().matcher(this.getString());
      boolean valid = m.matches();
      String fmt;
      if (valid) {
         fmt = m.group(1);

         try {
            char lastChar = fmt.charAt(fmt.length() - 1);
            if (Character.isLetter(lastChar)) {
               x = atofSpecials(m.group(1));
            } else {
               x = Double.parseDouble(m.group(1));
            }
         } catch (NumberFormatException var7) {
            valid = false;
         }
      }

      if (valid) {
         return x;
      } else {
         fmt = "invalid literal for float: %s";
         throw Py.ValueError(String.format(fmt, this.getString().trim()));
      }
   }

   private static synchronized Pattern getFloatPattern() {
      if (floatPattern == null) {
         floatPattern = Pattern.compile("\\s*([+-]?(?:(?:(?:\\d+\\.?|\\.\\d)\\d*(?:[eE][+-]?\\d+)?)|[infatyINFATY]+))\\s*");
      }

      return floatPattern;
   }

   private static synchronized Pattern getComplexPattern() {
      if (complexPattern == null) {
         complexPattern = Pattern.compile("\\s*(?<a>\\(\\s*)?(?<x>[+-]?(?:(?:(?:\\d+\\.?|\\.\\d)\\d*(?:[eE][+-]?\\d+)?)|[infatyINFATY]+)?)(?<y>[+-](?:(?:(?:\\d+\\.?|\\.\\d)\\d*(?:[eE][+-]?\\d+)?)|[infatyINFATY]+)?)?(?<j>[jJ])?\\s*(?<b>\\)\\s*)?");
      }

      return complexPattern;
   }

   private static double atofSpecials(String s) throws NumberFormatException {
      switch (s.toLowerCase()) {
         case "nan":
         case "+nan":
         case "-nan":
            return Double.NaN;
         case "inf":
         case "+inf":
         case "infinity":
         case "+infinity":
            return Double.POSITIVE_INFINITY;
         case "-inf":
         case "-infinity":
            return Double.NEGATIVE_INFINITY;
         default:
            throw new NumberFormatException();
      }
   }

   private PyComplex atocx() {
      double x = 0.0;
      double y = 0.0;
      Matcher m = getComplexPattern().matcher(this.getString());
      boolean valid = m.matches();
      String xs;
      if (valid) {
         if (m.group("a") != null != (m.group("b") != null)) {
            valid = false;
         } else {
            try {
               xs = m.group("x");
               String ys = m.group("y");
               if (m.group("j") != null) {
                  if (ys != null) {
                     y = toComplexPart(ys);
                     x = toComplexPart(xs);
                  } else if (xs != null) {
                     y = toComplexPart(xs);
                  } else {
                     y = 1.0;
                  }
               } else {
                  x = Double.parseDouble(xs);
                  if (ys != null) {
                     throw new NumberFormatException();
                  }
               }
            } catch (NumberFormatException var9) {
               valid = false;
            }
         }
      }

      if (valid) {
         return new PyComplex(x, y);
      } else {
         xs = "complex() arg is a malformed string: %s";
         throw Py.ValueError(String.format(xs, this.getString().trim()));
      }
   }

   private static double toComplexPart(String s) throws NumberFormatException {
      if (s.length() == 0) {
         return 1.0;
      } else {
         char lastChar = s.charAt(s.length() - 1);
         if (Character.isLetter(lastChar)) {
            return atofSpecials(s);
         } else if (lastChar == '+') {
            return 1.0;
         } else {
            return lastChar == '-' ? -1.0 : Double.parseDouble(s);
         }
      }
   }

   private BigInteger asciiToBigInteger(int base, boolean isLong) {
      String str = this.getString();
      int b = 0;

      int e;
      for(e = str.length(); b < e && Character.isWhitespace(str.charAt(b)); ++b) {
      }

      while(e > b && Character.isWhitespace(str.charAt(e - 1))) {
         --e;
      }

      char sign = 0;
      if (b < e) {
         sign = str.charAt(b);
         if (sign == '-' || sign == '+') {
            ++b;

            while(b < e && Character.isWhitespace(str.charAt(b))) {
               ++b;
            }
         }

         if (base == 16) {
            if (str.charAt(b) == '0' && b < e - 1 && Character.toUpperCase(str.charAt(b + 1)) == 'X') {
               b += 2;
            }
         } else if (base == 0) {
            if (str.charAt(b) == '0') {
               if (b < e - 1 && Character.toUpperCase(str.charAt(b + 1)) == 'X') {
                  base = 16;
                  b += 2;
               } else if (b < e - 1 && Character.toUpperCase(str.charAt(b + 1)) == 'O') {
                  base = 8;
                  b += 2;
               } else if (b < e - 1 && Character.toUpperCase(str.charAt(b + 1)) == 'B') {
                  base = 2;
                  b += 2;
               } else {
                  base = 8;
               }
            }
         } else if (base == 8) {
            if (b < e - 1 && Character.toUpperCase(str.charAt(b + 1)) == 'O') {
               b += 2;
            }
         } else if (base == 2 && b < e - 1 && Character.toUpperCase(str.charAt(b + 1)) == 'B') {
            b += 2;
         }
      }

      if (base == 0) {
         base = 10;
      }

      if (isLong && base < 22 && e > b && (str.charAt(e - 1) == 'L' || str.charAt(e - 1) == 'l')) {
         --e;
      }

      String s = str;
      if (b > 0 || e < str.length()) {
         s = str.substring(b, e);
      }

      BigInteger bi;
      if (sign == '-') {
         bi = new BigInteger("-" + s, base);
      } else {
         bi = new BigInteger(s, base);
      }

      return bi;
   }

   public int atoi() {
      return this.atoi(10);
   }

   public int atoi(int base) {
      if ((base == 0 || base >= 2) && base <= 36) {
         try {
            BigInteger bi = this.asciiToBigInteger(base, false);
            if (bi.compareTo(PyInteger.MAX_INT) <= 0 && bi.compareTo(PyInteger.MIN_INT) >= 0) {
               return bi.intValue();
            } else {
               throw Py.OverflowError("long int too large to convert to int");
            }
         } catch (NumberFormatException var3) {
            throw Py.ValueError("invalid literal for int() with base " + base + ": '" + this.getString() + "'");
         } catch (StringIndexOutOfBoundsException var4) {
            throw Py.ValueError("invalid literal for int() with base " + base + ": '" + this.getString() + "'");
         }
      } else {
         throw Py.ValueError("invalid base for atoi()");
      }
   }

   public PyLong atol() {
      return this.atol(10);
   }

   public PyLong atol(int base) {
      if ((base == 0 || base >= 2) && base <= 36) {
         try {
            BigInteger bi = this.asciiToBigInteger(base, true);
            return new PyLong(bi);
         } catch (NumberFormatException var3) {
            if (this instanceof PyUnicode) {
               throw Py.UnicodeEncodeError("decimal", "codec can't encode character", 0, 0, "invalid decimal Unicode string");
            } else {
               throw Py.ValueError("invalid literal for long() with base " + base + ": '" + this.getString() + "'");
            }
         } catch (StringIndexOutOfBoundsException var4) {
            throw Py.ValueError("invalid literal for long() with base " + base + ": '" + this.getString() + "'");
         }
      } else {
         throw Py.ValueError("invalid base for long literal:" + base);
      }
   }

   private static String padding(int n, char pad) {
      char[] chars = new char[n];

      for(int i = 0; i < n; ++i) {
         chars[i] = pad;
      }

      return new String(chars);
   }

   private static char parse_fillchar(String function, String fillchar) {
      if (fillchar == null) {
         return ' ';
      } else if (fillchar.length() != 1) {
         throw Py.TypeError(function + "() argument 2 must be char, not str");
      } else {
         return fillchar.charAt(0);
      }
   }

   public String ljust(int width) {
      return this.str_ljust(width, (String)null);
   }

   public String ljust(int width, String padding) {
      return this.str_ljust(width, padding);
   }

   final String str_ljust(int width, String fillchar) {
      char pad = parse_fillchar("ljust", fillchar);
      int n = width - this.getString().length();
      return n <= 0 ? this.getString() : this.getString() + padding(n, pad);
   }

   public String rjust(int width) {
      return this.str_rjust(width, (String)null);
   }

   final String str_rjust(int width, String fillchar) {
      char pad = parse_fillchar("rjust", fillchar);
      int n = width - this.getString().length();
      return n <= 0 ? this.getString() : padding(n, pad) + this.getString();
   }

   public String center(int width) {
      return this.str_center(width, (String)null);
   }

   final String str_center(int width, String fillchar) {
      char pad = parse_fillchar("center", fillchar);
      int n = width - this.getString().length();
      if (n <= 0) {
         return this.getString();
      } else {
         int half = n / 2;
         if (n % 2 > 0 && width % 2 > 0) {
            ++half;
         }

         return padding(half, pad) + this.getString() + padding(n - half, pad);
      }
   }

   public String zfill(int width) {
      return this.str_zfill(width);
   }

   final String str_zfill(int width) {
      String s = this.getString();
      int n = s.length();
      if (n >= width) {
         return s;
      } else {
         char[] chars = new char[width];
         int nzeros = width - n;
         int i = 0;
         int sStart = 0;
         if (n > 0) {
            char start = s.charAt(0);
            if (start == '+' || start == '-') {
               chars[0] = start;
               ++i;
               ++nzeros;
               sStart = 1;
            }
         }

         while(i < nzeros) {
            chars[i] = '0';
            ++i;
         }

         s.getChars(sStart, s.length(), chars, i);
         return new String(chars);
      }
   }

   public String expandtabs() {
      return this.str_expandtabs(8);
   }

   public String expandtabs(int tabsize) {
      return this.str_expandtabs(tabsize);
   }

   final String str_expandtabs(int tabsize) {
      String s = this.getString();
      StringBuilder buf = new StringBuilder((int)((double)s.length() * 1.5));
      char[] chars = s.toCharArray();
      int n = chars.length;
      int position = 0;

      for(int i = 0; i < n; ++i) {
         char c = chars[i];
         if (c == '\t') {
            int spaces = tabsize - position % tabsize;
            position += spaces;

            while(spaces-- > 0) {
               buf.append(' ');
            }
         } else {
            if (c == '\n' || c == '\r') {
               position = -1;
            }

            buf.append(c);
            ++position;
         }
      }

      return buf.toString();
   }

   public String capitalize() {
      return this.str_capitalize();
   }

   final String str_capitalize() {
      String s = this.getString();
      int n = s.length();
      if (n == 0) {
         return s;
      } else {
         char[] buf = new char[n];
         char c = s.charAt(0);
         buf[0] = this._islower(c) ? (char)(c ^ 32) : c;

         for(int i = 1; i < n; ++i) {
            c = s.charAt(i);
            buf[i] = this._isupper(c) ? (char)(c ^ 32) : c;
         }

         return new String(buf);
      }
   }

   public PyString replace(PyObject oldPiece, PyObject newPiece) {
      return this.str_replace(oldPiece, newPiece, -1);
   }

   public PyString replace(PyObject oldPiece, PyObject newPiece, int count) {
      return this.str_replace(oldPiece, newPiece, count);
   }

   final PyString str_replace(PyObject oldPieceObj, PyObject newPieceObj, int count) {
      if (!(oldPieceObj instanceof PyUnicode) && !(newPieceObj instanceof PyUnicode)) {
         String oldPiece = asStringOrError(oldPieceObj);
         String newPiece = asStringOrError(newPieceObj);
         return this._replace(oldPiece, newPiece, count);
      } else {
         return ((PyUnicode)this.decode()).unicode_replace(oldPieceObj, newPieceObj, count);
      }
   }

   protected final PyString _replace(String oldPiece, String newPiece, int count) {
      String s = this.getString();
      int len = s.length();
      int oldLen = oldPiece.length();
      int newLen = newPiece.length();
      if (len == 0) {
         return count < 0 && oldLen == 0 ? this.createInstance(newPiece, true) : this.createInstance(s, true);
      } else if (oldLen == 0 && newLen != 0 && count != 0) {
         StringBuilder buffer = new StringBuilder();
         int i = 0;
         buffer.append(newPiece);

         while(i < len && (count < 0 || i < count - 1)) {
            buffer.append(s.charAt(i)).append(newPiece);
            ++i;
         }

         buffer.append(s.substring(i));
         return this.createInstance(buffer.toString(), true);
      } else {
         if (count < 0) {
            count = oldLen == 0 ? len + 1 : len;
         }

         return this.createInstance(newPiece).join(this.splitfields(oldPiece, count));
      }
   }

   public PyString join(PyObject seq) {
      return this.str_join(seq);
   }

   final PyString str_join(PyObject obj) {
      PySequence seq = fastSequence(obj, "");
      int seqLen = seq.__len__();
      if (seqLen == 0) {
         return Py.EmptyString;
      } else {
         PyObject item;
         if (seqLen == 1) {
            item = seq.pyget(0);
            if (item.getType() == TYPE || item.getType() == PyUnicode.TYPE) {
               return (PyString)item;
            }
         }

         int i = 0;
         long size = 0L;

         for(int sepLen = this.getString().length(); i < seqLen; ++i) {
            item = seq.pyget(i);
            if (!(item instanceof PyString)) {
               throw Py.TypeError(String.format("sequence item %d: expected string, %.80s found", i, item.getType().fastGetName()));
            }

            if (item instanceof PyUnicode) {
               return this.unicodeJoin(seq);
            }

            if (i != 0) {
               size += (long)sepLen;
            }

            size += (long)((PyString)item).getString().length();
            if (size > 2147483647L) {
               throw Py.OverflowError("join() result is too long for a Python string");
            }
         }

         StringBuilder buf = new StringBuilder((int)size);

         for(i = 0; i < seqLen; ++i) {
            item = seq.pyget(i);
            if (i != 0) {
               buf.append(this.getString());
            }

            buf.append(((PyString)item).getString());
         }

         return new PyString(buf.toString(), true);
      }
   }

   final PyUnicode unicodeJoin(PyObject obj) {
      PySequence seq = fastSequence(obj, "");
      int seqLen = seq.__len__();
      if (seqLen == 0) {
         return new PyUnicode();
      } else {
         PyObject item;
         if (seqLen == 1) {
            item = seq.pyget(0);
            if (item.getType() == PyUnicode.TYPE) {
               return (PyUnicode)item;
            }
         }

         String sep = null;
         if (seqLen > 1) {
            if (this instanceof PyUnicode) {
               sep = this.getString();
            } else {
               sep = ((PyUnicode)this.decode()).getString();
               seqLen = seq.__len__();
            }
         }

         long size = 0L;
         int sepLen = this.getString().length();
         StringBuilder buf = new StringBuilder();

         for(int i = 0; i < seqLen; ++i) {
            item = seq.pyget(i);
            if (!(item instanceof PyString)) {
               throw Py.TypeError(String.format("sequence item %d: expected string or Unicode, %.80s found", i, item.getType().fastGetName()));
            }

            if (!(item instanceof PyUnicode)) {
               item = ((PyString)item).decode();
               seqLen = seq.__len__();
            }

            String itemString = ((PyUnicode)item).getString();
            if (i != 0) {
               size += (long)sepLen;
               buf.append(sep);
            }

            size += (long)itemString.length();
            if (size > 2147483647L) {
               throw Py.OverflowError("join() result is too long for a Python string");
            }

            buf.append(itemString);
         }

         return new PyUnicode(buf.toString());
      }
   }

   public boolean startswith(PyObject prefix) {
      return this.str_startswith(prefix, (PyObject)null, (PyObject)null);
   }

   public boolean startswith(PyObject prefix, PyObject start) {
      return this.str_startswith(prefix, start, (PyObject)null);
   }

   public boolean startswith(PyObject prefix, PyObject start, PyObject end) {
      return this.str_startswith(prefix, start, end);
   }

   final boolean str_startswith(PyObject prefix, PyObject startObj, PyObject endObj) {
      int[] indices = this.translateIndices(startObj, endObj);
      int start = indices[0];
      int sliceLen = indices[1] - start;
      if (prefix instanceof PyTuple) {
         PyObject[] var12 = ((PyTuple)prefix).getArray();
         int var8 = var12.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            PyObject prefixObj = var12[var9];
            String s = asUTF16StringOrError(prefixObj);
            if (sliceLen >= s.length() && this.getString().startsWith(s, start)) {
               return true;
            }
         }

         return false;
      } else {
         String s = asUTF16StringOrError(prefix);
         return sliceLen >= s.length() && this.getString().startsWith(s, start);
      }
   }

   public boolean endswith(PyObject suffix) {
      return this.str_endswith(suffix, (PyObject)null, (PyObject)null);
   }

   public boolean endswith(PyObject suffix, PyObject start) {
      return this.str_endswith(suffix, start, (PyObject)null);
   }

   public boolean endswith(PyObject suffix, PyObject start, PyObject end) {
      return this.str_endswith(suffix, start, end);
   }

   final boolean str_endswith(PyObject suffix, PyObject startObj, PyObject endObj) {
      int[] indices = this.translateIndices(startObj, endObj);
      String substr = this.getString().substring(indices[0], indices[1]);
      if (!(suffix instanceof PyTuple)) {
         String s = asUTF16StringOrError(suffix);
         return substr.endsWith(s);
      } else {
         PyObject[] var6 = ((PyTuple)suffix).getArray();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            PyObject suffixObj = var6[var8];
            String s = asUTF16StringOrError(suffixObj);
            if (substr.endsWith(s)) {
               return true;
            }
         }

         return false;
      }
   }

   protected int[] translateIndices(PyObject startObj, PyObject endObj) {
      int n = this.__len__();
      int[] result = new int[4];
      int start;
      if (startObj != null && startObj != Py.None) {
         start = startObj.asIndex((PyObject)null);
         if (start < 0) {
            start += n;
         }

         result[2] = start;
      } else {
         start = 0;
      }

      int end;
      if (endObj != null && endObj != Py.None) {
         end = endObj.asIndex((PyObject)null);
         if (end < 0) {
            result[3] = end += n;
            if (end < 0) {
               end = 0;
            } else {
               result[1] = end;
            }
         } else {
            result[3] = end;
            if (end > n) {
               end = n;
               result[1] = n;
            } else {
               result[1] = end;
            }
         }
      } else {
         end = n;
         result[1] = result[3] = n;
      }

      if (start < 0) {
         int start = false;
      } else if (start > end) {
         result[0] = end;
      } else {
         result[0] = start;
      }

      return result;
   }

   public String translate(PyObject table) {
      return this.translate((PyObject)table, (PyObject)null);
   }

   public String translate(PyObject table, PyObject deletechars) {
      return this.str_translate(table, deletechars);
   }

   public String translate(String table) {
      return this._translate(table, (String)null);
   }

   public String translate(String table, String deletechars) {
      return this._translate(table, deletechars);
   }

   final String str_translate(PyObject tableObj, PyObject deletecharsObj) {
      String table = asStringNullOrError(tableObj, (String)null);
      String deletechars = asStringNullOrError(deletecharsObj, (String)null);
      return this._translate(table, deletechars);
   }

   private final String _translate(String table, String deletechars) {
      if (table != null && table.length() != 256) {
         throw Py.ValueError("translation table must be 256 characters long");
      } else {
         StringBuilder buf = new StringBuilder(this.getString().length());

         for(int i = 0; i < this.getString().length(); ++i) {
            char c = this.getString().charAt(i);
            if (deletechars == null || deletechars.indexOf(c) < 0) {
               if (table == null) {
                  buf.append(c);
               } else {
                  try {
                     buf.append(table.charAt(c));
                  } catch (IndexOutOfBoundsException var7) {
                     throw Py.TypeError("translate() only works for 8-bit character strings");
                  }
               }
            }
         }

         return buf.toString();
      }
   }

   public boolean islower() {
      return this.str_islower();
   }

   final boolean str_islower() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._islower(s.charAt(0));
      } else {
         boolean cased = false;

         for(int i = 0; i < n; ++i) {
            char ch = s.charAt(i);
            if (this._isupper(ch)) {
               return false;
            }

            if (!cased && this._islower(ch)) {
               cased = true;
            }
         }

         return cased;
      }
   }

   private boolean _islower(char ch) {
      if (ch < 256) {
         return BaseBytes.islower((byte)ch);
      } else {
         throw new IllegalArgumentException("non-byte character in PyString");
      }
   }

   public boolean isupper() {
      return this.str_isupper();
   }

   final boolean str_isupper() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._isupper(s.charAt(0));
      } else {
         boolean cased = false;

         for(int i = 0; i < n; ++i) {
            char ch = s.charAt(i);
            if (this._islower(ch)) {
               return false;
            }

            if (!cased && this._isupper(ch)) {
               cased = true;
            }
         }

         return cased;
      }
   }

   private boolean _isupper(char ch) {
      if (ch < 256) {
         return BaseBytes.isupper((byte)ch);
      } else {
         throw new IllegalArgumentException("non-byte character in PyString");
      }
   }

   public boolean isalpha() {
      return this.str_isalpha();
   }

   final boolean str_isalpha() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._isalpha(s.charAt(0));
      } else {
         for(int i = 0; i < n; ++i) {
            if (!this._isalpha(s.charAt(i))) {
               return false;
            }
         }

         return n > 0;
      }
   }

   private boolean _isalpha(char ch) {
      if (ch < 256) {
         return BaseBytes.isalpha((byte)ch);
      } else {
         throw new IllegalArgumentException("non-byte character in PyString");
      }
   }

   public boolean isalnum() {
      return this.str_isalnum();
   }

   final boolean str_isalnum() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._isalnum(s.charAt(0));
      } else {
         for(int i = 0; i < n; ++i) {
            if (!this._isalnum(s.charAt(i))) {
               return false;
            }
         }

         return n > 0;
      }
   }

   private boolean _isalnum(char ch) {
      if (ch < 256) {
         return BaseBytes.isalnum((byte)ch);
      } else {
         throw new IllegalArgumentException("non-byte character in PyString");
      }
   }

   public boolean isdecimal() {
      return this.str_isdecimal();
   }

   final boolean str_isdecimal() {
      return this.str_isdigit();
   }

   private boolean _isdecimal(char ch) {
      return Character.getType(ch) == 9;
   }

   public boolean isdigit() {
      return this.str_isdigit();
   }

   final boolean str_isdigit() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._isdigit(s.charAt(0));
      } else {
         for(int i = 0; i < n; ++i) {
            if (!this._isdigit(s.charAt(i))) {
               return false;
            }
         }

         return n > 0;
      }
   }

   private boolean _isdigit(char ch) {
      if (ch < 256) {
         return BaseBytes.isdigit((byte)ch);
      } else {
         throw new IllegalArgumentException("non-byte character in PyString");
      }
   }

   public boolean isnumeric() {
      return this.str_isnumeric();
   }

   final boolean str_isnumeric() {
      return this.str_isdigit();
   }

   public boolean istitle() {
      return this.str_istitle();
   }

   final boolean str_istitle() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._isupper(s.charAt(0));
      } else {
         boolean cased = false;
         boolean previous_is_cased = false;

         for(int i = 0; i < n; ++i) {
            char ch = s.charAt(i);
            if (this._isupper(ch)) {
               if (previous_is_cased) {
                  return false;
               }

               previous_is_cased = true;
               cased = true;
            } else if (this._islower(ch)) {
               if (!previous_is_cased) {
                  return false;
               }

               previous_is_cased = true;
               cased = true;
            } else {
               previous_is_cased = false;
            }
         }

         return cased;
      }
   }

   public boolean isspace() {
      return this.str_isspace();
   }

   final boolean str_isspace() {
      String s = this.getString();
      int n = s.length();
      if (n == 1) {
         return this._isspace(s.charAt(0));
      } else {
         for(int i = 0; i < n; ++i) {
            if (!this._isspace(s.charAt(i))) {
               return false;
            }
         }

         return n > 0;
      }
   }

   private boolean _isspace(char ch) {
      if (ch < 256) {
         return BaseBytes.isspace((byte)ch);
      } else {
         throw new IllegalArgumentException("non-byte character in PyString");
      }
   }

   public boolean isunicode() {
      return this.str_isunicode();
   }

   final boolean str_isunicode() {
      Py.warning(Py.DeprecationWarning, "isunicode is deprecated.");
      int n = this.getString().length();

      for(int i = 0; i < n; ++i) {
         char ch = this.getString().charAt(i);
         if (ch > 255) {
            return true;
         }
      }

      return false;
   }

   public String encode() {
      return this.encode((String)null, (String)null);
   }

   public String encode(String encoding) {
      return this.encode(encoding, (String)null);
   }

   public String encode(String encoding, String errors) {
      return codecs.encode(this, encoding, errors);
   }

   final String str_encode(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("encode", args, keywords, "encoding", "errors");
      String encoding = ap.getString(0, (String)null);
      String errors = ap.getString(1, (String)null);
      return this.encode(encoding, errors);
   }

   public PyObject decode() {
      return this.decode((String)null, (String)null);
   }

   public PyObject decode(String encoding) {
      return this.decode(encoding, (String)null);
   }

   public PyObject decode(String encoding, String errors) {
      return codecs.decode(this, encoding, errors);
   }

   final PyObject str_decode(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("decode", args, keywords, "encoding", "errors");
      String encoding = ap.getString(0, (String)null);
      String errors = ap.getString(1, (String)null);
      return this.decode(encoding, errors);
   }

   final PyObject str__formatter_parser() {
      return new MarkupIterator(this);
   }

   final PyObject str__formatter_field_name_split() {
      FieldNameIterator iterator = new FieldNameIterator(this);
      return new PyTuple(new PyObject[]{iterator.pyHead(), iterator});
   }

   final PyObject str_format(PyObject[] args, String[] keywords) {
      try {
         return new PyString(this.buildFormattedString(args, keywords, (MarkupIterator)null, (String)null));
      } catch (IllegalArgumentException var4) {
         throw Py.ValueError(var4.getMessage());
      }
   }

   protected String buildFormattedString(PyObject[] args, String[] keywords, MarkupIterator enclosingIterator, String value) {
      MarkupIterator it;
      if (enclosingIterator == null) {
         it = new MarkupIterator(this);
      } else {
         it = new MarkupIterator(enclosingIterator, value);
      }

      StringBuilder result = new StringBuilder();

      while(true) {
         MarkupIterator.Chunk chunk = it.nextChunk();
         if (chunk == null) {
            return result.toString();
         }

         result.append(chunk.literalText);
         if (chunk.fieldName != null) {
            PyObject fieldObj = this.getFieldObject(chunk.fieldName, it.isBytes(), args, keywords);
            if (fieldObj != null) {
               if ("r".equals(chunk.conversion)) {
                  fieldObj = ((PyObject)fieldObj).__repr__();
               } else if ("s".equals(chunk.conversion)) {
                  fieldObj = ((PyObject)fieldObj).__str__();
               } else if (chunk.conversion != null) {
                  throw Py.ValueError("Unknown conversion specifier " + chunk.conversion);
               }

               if (fieldObj instanceof PyUnicode && !(this instanceof PyUnicode)) {
                  fieldObj = ((PyUnicode)fieldObj).__str__();
               }

               String formatSpec = chunk.formatSpec;
               if (chunk.formatSpecNeedsExpanding) {
                  if (enclosingIterator != null) {
                     throw Py.ValueError("Max string recursion exceeded");
                  }

                  formatSpec = this.buildFormattedString(args, keywords, it, formatSpec);
               }

               this.renderField((PyObject)fieldObj, formatSpec, result);
            }
         }
      }
   }

   private PyObject getFieldObject(String fieldName, boolean bytes, PyObject[] args, String[] keywords) {
      FieldNameIterator iterator = new FieldNameIterator(fieldName, bytes);
      PyObject head = iterator.pyHead();
      PyObject obj = null;
      int positionalCount = args.length - keywords.length;
      int i;
      if (head.isIndex()) {
         i = head.asIndex();
         if (i >= positionalCount) {
            throw Py.IndexError("tuple index out of range");
         }

         obj = args[i];
      } else {
         for(i = 0; i < keywords.length; ++i) {
            if (keywords[i].equals(head.asString())) {
               obj = args[positionalCount + i];
               break;
            }
         }

         if (obj == null) {
            throw Py.KeyError(head);
         }
      }

      while(obj != null) {
         FieldNameIterator.Chunk chunk = iterator.nextChunk();
         if (chunk == null) {
            break;
         }

         Object key = chunk.value;
         if (chunk.is_attr) {
            obj = obj.__getattr__((String)key);
         } else if (key instanceof Integer) {
            obj = obj.__getitem__((Integer)key);
         } else {
            obj = obj.__getitem__(new PyString(key.toString()));
         }
      }

      return obj;
   }

   private void renderField(PyObject fieldObj, String formatSpec, StringBuilder result) {
      PyString formatSpecStr = formatSpec == null ? Py.EmptyString : new PyString(formatSpec);
      result.append(fieldObj.__format__(formatSpecStr).asString());
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.str___format__(formatSpec);
   }

   final PyObject str___format__(PyObject formatSpec) {
      InternalFormat.Spec spec = InternalFormat.fromText(formatSpec, "__format__");
      TextFormatter f = prepareFormatter(spec);
      if (f == null) {
         throw InternalFormat.Formatter.unknownFormat(spec.type, "string");
      } else {
         boolean unicode = this instanceof PyUnicode || formatSpec instanceof PyUnicode;
         f.setBytes(!unicode);
         f.format(this.getString());
         return f.pad().getPyResult();
      }
   }

   static TextFormatter prepareFormatter(InternalFormat.Spec spec) throws PyException {
      switch (spec.type) {
         case 's':
         case '\uffff':
            if (spec.grouping) {
               throw InternalFormat.Formatter.notAllowed("Grouping", "string", spec.type);
            } else if (InternalFormat.Spec.specified(spec.sign)) {
               throw InternalFormat.Formatter.signNotAllowed("string", '\u0000');
            } else if (spec.alternate) {
               throw InternalFormat.Formatter.alternateFormNotAllowed("string");
            } else {
               if (spec.align == '=') {
                  throw InternalFormat.Formatter.alignmentNotAllowed('=', "string");
               }

               spec = spec.withDefaults(InternalFormat.Spec.STRING);
               return new TextFormatter(spec);
            }
         default:
            return null;
      }
   }

   public String asString(int index) throws PyObject.ConversionException {
      return this.getString();
   }

   public String asString() {
      return this.getString();
   }

   public int asInt() {
      this.asNumberCheck("__int__", "an integer");
      return super.asInt();
   }

   public long asLong() {
      this.asNumberCheck("__long__", "an integer");
      return super.asLong();
   }

   public double asDouble() {
      this.asNumberCheck("__float__", "a float");
      return super.asDouble();
   }

   private void asNumberCheck(String methodName, String description) {
      PyType type = this.getType();
      if (type == TYPE || type == PyUnicode.TYPE || type.lookup(methodName) == null) {
         throw Py.TypeError(description + " is required");
      }
   }

   public String asName(int index) throws PyObject.ConversionException {
      return this.internedString();
   }

   protected String unsupportedopMessage(String op, PyObject o2) {
      return op.equals("+") ? "cannot concatenate ''{1}'' and ''{2}'' objects" : super.unsupportedopMessage(op, o2);
   }

   public char charAt(int index) {
      return this.string.charAt(index);
   }

   public int length() {
      return this.string.length();
   }

   public CharSequence subSequence(int start, int end) {
      return this.string.subSequence(start, end);
   }

   static {
      PyType.addBuilder(PyString.class, new PyExposer());
      TYPE = PyType.fromClass(PyString.class);
      hexdigit = "0123456789abcdef".toCharArray();
      pucnHash = null;
      floatPattern = null;
      complexPattern = null;
   }

   private static class str___str___exposer extends PyBuiltinMethodNarrow {
      public str___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public str___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str___str__();
      }
   }

   private static class str___len___exposer extends PyBuiltinMethodNarrow {
      public str___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public str___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyString)this.self).str___len__());
      }
   }

   private static class str___repr___exposer extends PyBuiltinMethodNarrow {
      public str___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public str___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str___repr__();
      }
   }

   private static class str___getitem___exposer extends PyBuiltinMethodNarrow {
      public str___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public str___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str___getitem__(var1);
      }
   }

   private static class str___getslice___exposer extends PyBuiltinMethodNarrow {
      public str___getslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "";
      }

      public str___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyString)this.self).str___getslice__(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyString)this.self).str___getslice__(var1, var2, (PyObject)null);
      }
   }

   private static class str___cmp___exposer extends PyBuiltinMethodNarrow {
      public str___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public str___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyString)this.self).str___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("str.__cmp__(x,y) requires y to be 'str', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class str___eq___exposer extends PyBuiltinMethodNarrow {
      public str___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public str___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___ne___exposer extends PyBuiltinMethodNarrow {
      public str___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public str___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___lt___exposer extends PyBuiltinMethodNarrow {
      public str___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public str___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___le___exposer extends PyBuiltinMethodNarrow {
      public str___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public str___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___gt___exposer extends PyBuiltinMethodNarrow {
      public str___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public str___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___ge___exposer extends PyBuiltinMethodNarrow {
      public str___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public str___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___hash___exposer extends PyBuiltinMethodNarrow {
      public str___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public str___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyString)this.self).str___hash__());
      }
   }

   private static class str___contains___exposer extends PyBuiltinMethodNarrow {
      public str___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public str___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyString)this.self).str___contains__(var1));
      }
   }

   private static class str___mul___exposer extends PyBuiltinMethodNarrow {
      public str___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public str___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___rmul___exposer extends PyBuiltinMethodNarrow {
      public str___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public str___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___add___exposer extends PyBuiltinMethodNarrow {
      public str___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public str___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyString)this.self).str___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class str___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public str___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public str___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str___getnewargs__();
      }
   }

   private static class str___mod___exposer extends PyBuiltinMethodNarrow {
      public str___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public str___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mod__(y) <==> x%y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str___mod__(var1);
      }
   }

   private static class str_lower_exposer extends PyBuiltinMethodNarrow {
      public str_lower_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.lower() -> string\n\nReturn a copy of the string S converted to lowercase.";
      }

      public str_lower_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.lower() -> string\n\nReturn a copy of the string S converted to lowercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_lower_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_lower();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_upper_exposer extends PyBuiltinMethodNarrow {
      public str_upper_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.upper() -> string\n\nReturn a copy of the string S converted to uppercase.";
      }

      public str_upper_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.upper() -> string\n\nReturn a copy of the string S converted to uppercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_upper_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_upper();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_title_exposer extends PyBuiltinMethodNarrow {
      public str_title_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.title() -> string\n\nReturn a titlecased version of S, i.e. words start with uppercase\ncharacters, all remaining cased characters have lowercase.";
      }

      public str_title_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.title() -> string\n\nReturn a titlecased version of S, i.e. words start with uppercase\ncharacters, all remaining cased characters have lowercase.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_title_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_title();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_swapcase_exposer extends PyBuiltinMethodNarrow {
      public str_swapcase_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.swapcase() -> string\n\nReturn a copy of the string S with uppercase characters\nconverted to lowercase and vice versa.";
      }

      public str_swapcase_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.swapcase() -> string\n\nReturn a copy of the string S with uppercase characters\nconverted to lowercase and vice versa.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_swapcase_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_swapcase();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_strip_exposer extends PyBuiltinMethodNarrow {
      public str_strip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.strip([chars]) -> string or unicode\n\nReturn a copy of the string S with leading and trailing\nwhitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is unicode, S will be converted to unicode before stripping";
      }

      public str_strip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.strip([chars]) -> string or unicode\n\nReturn a copy of the string S with leading and trailing\nwhitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is unicode, S will be converted to unicode before stripping";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_strip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_strip(var1);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str_strip((PyObject)null);
      }
   }

   private static class str_lstrip_exposer extends PyBuiltinMethodNarrow {
      public str_lstrip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.lstrip([chars]) -> string or unicode\n\nReturn a copy of the string S with leading whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is unicode, S will be converted to unicode before stripping";
      }

      public str_lstrip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.lstrip([chars]) -> string or unicode\n\nReturn a copy of the string S with leading whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is unicode, S will be converted to unicode before stripping";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_lstrip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_lstrip(var1);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str_lstrip((PyObject)null);
      }
   }

   private static class str_rstrip_exposer extends PyBuiltinMethodNarrow {
      public str_rstrip_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.rstrip([chars]) -> string or unicode\n\nReturn a copy of the string S with trailing whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is unicode, S will be converted to unicode before stripping";
      }

      public str_rstrip_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rstrip([chars]) -> string or unicode\n\nReturn a copy of the string S with trailing whitespace removed.\nIf chars is given and not None, remove characters in chars instead.\nIf chars is unicode, S will be converted to unicode before stripping";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_rstrip_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_rstrip(var1);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str_rstrip((PyObject)null);
      }
   }

   private static class str_split_exposer extends PyBuiltinMethodNarrow {
      public str_split_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "S.split([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in the string S, using sep as the\ndelimiter string.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified or is None, any\nwhitespace string is a separator and empty strings are removed\nfrom the result.";
      }

      public str_split_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.split([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in the string S, using sep as the\ndelimiter string.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified or is None, any\nwhitespace string is a separator and empty strings are removed\nfrom the result.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_split_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyString)this.self).str_split(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_split(var1, -1);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str_split((PyObject)null, -1);
      }
   }

   private static class str_rsplit_exposer extends PyBuiltinMethodNarrow {
      public str_rsplit_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "S.split([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in the string S, using sep as the\ndelimiter string.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified or is None, any\nwhitespace string is a separator and empty strings are removed\nfrom the result.";
      }

      public str_rsplit_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.split([sep [,maxsplit]]) -> list of strings\n\nReturn a list of the words in the string S, using sep as the\ndelimiter string.  If maxsplit is given, at most maxsplit\nsplits are done. If sep is not specified or is None, any\nwhitespace string is a separator and empty strings are removed\nfrom the result.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_rsplit_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyString)this.self).str_rsplit(var1, Py.py2int(var2));
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_rsplit(var1, -1);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str_rsplit((PyObject)null, -1);
      }
   }

   private static class str_partition_exposer extends PyBuiltinMethodNarrow {
      public str_partition_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.partition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, and return the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, return S and two empty strings.";
      }

      public str_partition_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.partition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, and return the part before it,\nthe separator itself, and the part after it.  If the separator is not\nfound, return S and two empty strings.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_partition_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_partition(var1);
      }
   }

   private static class str_rpartition_exposer extends PyBuiltinMethodNarrow {
      public str_rpartition_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.rpartition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, starting at the end of S, and return\nthe part before it, the separator itself, and the part after it.  If the\nseparator is not found, return two empty strings and S.";
      }

      public str_rpartition_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rpartition(sep) -> (head, sep, tail)\n\nSearch for the separator sep in S, starting at the end of S, and return\nthe part before it, the separator itself, and the part after it.  If the\nseparator is not found, return two empty strings and S.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_rpartition_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_rpartition(var1);
      }
   }

   private static class str_splitlines_exposer extends PyBuiltinMethodNarrow {
      public str_splitlines_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.splitlines([keepends]) -> list of strings\n\nReturn a list of the lines in S, breaking at line boundaries.\nLine breaks are not included in the resulting list unless keepends\nis given and true.";
      }

      public str_splitlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.splitlines([keepends]) -> list of strings\n\nReturn a list of the lines in S, breaking at line boundaries.\nLine breaks are not included in the resulting list unless keepends\nis given and true.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_splitlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_splitlines(Py.py2boolean(var1));
      }

      public PyObject __call__() {
         return ((PyString)this.self).str_splitlines((boolean)0);
      }
   }

   private static class str_index_exposer extends PyBuiltinMethodNarrow {
      public str_index_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.index(sub [,start [,end]]) -> int\n\nLike S.find() but raise ValueError when the substring is not found.";
      }

      public str_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.index(sub [,start [,end]]) -> int\n\nLike S.find() but raise ValueError when the substring is not found.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyString)this.self).str_index(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyString)this.self).str_index(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyString)this.self).str_index(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_rindex_exposer extends PyBuiltinMethodNarrow {
      public str_rindex_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.rindex(sub [,start [,end]]) -> int\n\nLike S.rfind() but raise ValueError when the substring is not found.";
      }

      public str_rindex_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rindex(sub [,start [,end]]) -> int\n\nLike S.rfind() but raise ValueError when the substring is not found.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_rindex_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyString)this.self).str_rindex(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyString)this.self).str_rindex(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyString)this.self).str_rindex(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_count_exposer extends PyBuiltinMethodNarrow {
      public str_count_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.count(sub[, start[, end]]) -> int\n\nReturn the number of non-overlapping occurrences of substring sub in\nstring S[start:end].  Optional arguments start and end are interpreted\nas in slice notation.";
      }

      public str_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.count(sub[, start[, end]]) -> int\n\nReturn the number of non-overlapping occurrences of substring sub in\nstring S[start:end].  Optional arguments start and end are interpreted\nas in slice notation.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyString)this.self).str_count(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyString)this.self).str_count(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyString)this.self).str_count(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_find_exposer extends PyBuiltinMethodNarrow {
      public str_find_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.find(sub [,start [,end]]) -> int\n\nReturn the lowest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public str_find_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.find(sub [,start [,end]]) -> int\n\nReturn the lowest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_find_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyString)this.self).str_find(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyString)this.self).str_find(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyString)this.self).str_find(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_rfind_exposer extends PyBuiltinMethodNarrow {
      public str_rfind_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.rfind(sub [,start [,end]]) -> int\n\nReturn the highest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public str_rfind_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rfind(sub [,start [,end]]) -> int\n\nReturn the highest index in S where substring sub is found,\nsuch that sub is contained within s[start:end].  Optional\narguments start and end are interpreted as in slice notation.\n\nReturn -1 on failure.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_rfind_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyString)this.self).str_rfind(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyString)this.self).str_rfind(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyString)this.self).str_rfind(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_ljust_exposer extends PyBuiltinMethodNarrow {
      public str_ljust_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "S.ljust(width[, fillchar]) -> string\n\nReturn S left-justified in a string of length width. Padding is\ndone using the specified fill character (default is a space).";
      }

      public str_ljust_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.ljust(width[, fillchar]) -> string\n\nReturn S left-justified in a string of length width. Padding is\ndone using the specified fill character (default is a space).";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_ljust_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         String var10000 = ((PyString)this.self).str_ljust(Py.py2int(var1), var2.asStringOrNull());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((PyString)this.self).str_ljust(Py.py2int(var1), (String)null);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_rjust_exposer extends PyBuiltinMethodNarrow {
      public str_rjust_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "S.rjust(width[, fillchar]) -> string\n\nReturn S right-justified in a string of length width. Padding is\ndone using the specified fill character (default is a space)";
      }

      public str_rjust_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.rjust(width[, fillchar]) -> string\n\nReturn S right-justified in a string of length width. Padding is\ndone using the specified fill character (default is a space)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_rjust_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         String var10000 = ((PyString)this.self).str_rjust(Py.py2int(var1), var2.asStringOrNull());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((PyString)this.self).str_rjust(Py.py2int(var1), (String)null);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_center_exposer extends PyBuiltinMethodNarrow {
      public str_center_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "S.center(width[, fillchar]) -> string\n\nReturn S centered in a string of length width. Padding is\ndone using the specified fill character (default is a space)";
      }

      public str_center_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.center(width[, fillchar]) -> string\n\nReturn S centered in a string of length width. Padding is\ndone using the specified fill character (default is a space)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_center_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         String var10000 = ((PyString)this.self).str_center(Py.py2int(var1), var2.asStringOrNull());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((PyString)this.self).str_center(Py.py2int(var1), (String)null);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_zfill_exposer extends PyBuiltinMethodNarrow {
      public str_zfill_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.zfill(width) -> string\n\nPad a numeric string S with zeros on the left, to fill a field\nof the specified width.  The string S is never truncated.";
      }

      public str_zfill_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.zfill(width) -> string\n\nPad a numeric string S with zeros on the left, to fill a field\nof the specified width.  The string S is never truncated.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_zfill_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((PyString)this.self).str_zfill(Py.py2int(var1));
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_expandtabs_exposer extends PyBuiltinMethodNarrow {
      public str_expandtabs_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "S.expandtabs([tabsize]) -> string\n\nReturn a copy of S where all tab characters are expanded using spaces.\nIf tabsize is not given, a tab size of 8 characters is assumed.";
      }

      public str_expandtabs_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.expandtabs([tabsize]) -> string\n\nReturn a copy of S where all tab characters are expanded using spaces.\nIf tabsize is not given, a tab size of 8 characters is assumed.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_expandtabs_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((PyString)this.self).str_expandtabs(Py.py2int(var1));
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_expandtabs(8);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_capitalize_exposer extends PyBuiltinMethodNarrow {
      public str_capitalize_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.capitalize() -> string\n\nReturn a copy of the string S with only its first character\ncapitalized.";
      }

      public str_capitalize_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.capitalize() -> string\n\nReturn a copy of the string S with only its first character\ncapitalized.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_capitalize_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_capitalize();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_replace_exposer extends PyBuiltinMethodNarrow {
      public str_replace_exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "S.replace(old, new[, count]) -> string\n\nReturn a copy of string S with all occurrences of substring\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced.";
      }

      public str_replace_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.replace(old, new[, count]) -> string\n\nReturn a copy of string S with all occurrences of substring\nold replaced by new.  If the optional argument count is\ngiven, only the first count occurrences are replaced.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_replace_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyString)this.self).str_replace(var1, var2, Py.py2int(var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyString)this.self).str_replace(var1, var2, -1);
      }
   }

   private static class str_join_exposer extends PyBuiltinMethodNarrow {
      public str_join_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.join(iterable) -> string\n\nReturn a string which is the concatenation of the strings in the\niterable.  The separator between elements is S.";
      }

      public str_join_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.join(iterable) -> string\n\nReturn a string which is the concatenation of the strings in the\niterable.  The separator between elements is S.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_join_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str_join(var1);
      }
   }

   private static class str_startswith_exposer extends PyBuiltinMethodNarrow {
      public str_startswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.startswith(prefix[, start[, end]]) -> bool\n\nReturn True if S starts with the specified prefix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nprefix can also be a tuple of strings to try.";
      }

      public str_startswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.startswith(prefix[, start[, end]]) -> bool\n\nReturn True if S starts with the specified prefix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nprefix can also be a tuple of strings to try.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_startswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyString)this.self).str_startswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyString)this.self).str_startswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyString)this.self).str_startswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_endswith_exposer extends PyBuiltinMethodNarrow {
      public str_endswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "S.endswith(suffix[, start[, end]]) -> bool\n\nReturn True if S ends with the specified suffix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nsuffix can also be a tuple of strings to try.";
      }

      public str_endswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.endswith(suffix[, start[, end]]) -> bool\n\nReturn True if S ends with the specified suffix, False otherwise.\nWith optional start, test S beginning at that position.\nWith optional end, stop comparing S at that position.\nsuffix can also be a tuple of strings to try.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_endswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyString)this.self).str_endswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyString)this.self).str_endswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyString)this.self).str_endswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class str_translate_exposer extends PyBuiltinMethodNarrow {
      public str_translate_exposer(String var1) {
         super(var1, 1, 3);
         super.doc = "S.translate(table [,deletechars]) -> string\n\nReturn a copy of the string S, where all characters occurring\nin the optional argument deletechars are removed, and the\nremaining characters have been mapped through the given\ntranslation table, which must be a string of length 256.";
      }

      public str_translate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.translate(table [,deletechars]) -> string\n\nReturn a copy of the string S, where all characters occurring\nin the optional argument deletechars are removed, and the\nremaining characters have been mapped through the given\ntranslation table, which must be a string of length 256.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_translate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         String var10000 = ((PyString)this.self).str_translate(var1, var2);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((PyString)this.self).str_translate(var1, (PyObject)null);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }

      public PyObject __call__() {
         String var10000 = ((PyString)this.self).str_translate((PyObject)null, (PyObject)null);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_islower_exposer extends PyBuiltinMethodNarrow {
      public str_islower_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.islower() -> bool\n\nReturn True if all cased characters in S are lowercase and there is\nat least one cased character in S, False otherwise.";
      }

      public str_islower_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.islower() -> bool\n\nReturn True if all cased characters in S are lowercase and there is\nat least one cased character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_islower_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_islower());
      }
   }

   private static class str_isupper_exposer extends PyBuiltinMethodNarrow {
      public str_isupper_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isupper() -> bool\n\nReturn True if all cased characters in S are uppercase and there is\nat least one cased character in S, False otherwise.";
      }

      public str_isupper_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isupper() -> bool\n\nReturn True if all cased characters in S are uppercase and there is\nat least one cased character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isupper_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isupper());
      }
   }

   private static class str_isalpha_exposer extends PyBuiltinMethodNarrow {
      public str_isalpha_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isalpha() -> bool\n\nReturn True if all characters in S are alphabetic\nand there is at least one character in S, False otherwise.";
      }

      public str_isalpha_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isalpha() -> bool\n\nReturn True if all characters in S are alphabetic\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isalpha_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isalpha());
      }
   }

   private static class str_isalnum_exposer extends PyBuiltinMethodNarrow {
      public str_isalnum_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isalnum() -> bool\n\nReturn True if all characters in S are alphanumeric\nand there is at least one character in S, False otherwise.";
      }

      public str_isalnum_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isalnum() -> bool\n\nReturn True if all characters in S are alphanumeric\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isalnum_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isalnum());
      }
   }

   private static class str_isdecimal_exposer extends PyBuiltinMethodNarrow {
      public str_isdecimal_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isdecimal() -> bool\n\nReturn True if there are only decimal characters in S,\nFalse otherwise.";
      }

      public str_isdecimal_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isdecimal() -> bool\n\nReturn True if there are only decimal characters in S,\nFalse otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isdecimal_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isdecimal());
      }
   }

   private static class str_isdigit_exposer extends PyBuiltinMethodNarrow {
      public str_isdigit_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isdigit() -> bool\n\nReturn True if all characters in S are digits\nand there is at least one character in S, False otherwise.";
      }

      public str_isdigit_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isdigit() -> bool\n\nReturn True if all characters in S are digits\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isdigit_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isdigit());
      }
   }

   private static class str_isnumeric_exposer extends PyBuiltinMethodNarrow {
      public str_isnumeric_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isnumeric() -> bool\n\nReturn True if there are only numeric characters in S,\nFalse otherwise.";
      }

      public str_isnumeric_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isnumeric() -> bool\n\nReturn True if there are only numeric characters in S,\nFalse otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isnumeric_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isnumeric());
      }
   }

   private static class str_istitle_exposer extends PyBuiltinMethodNarrow {
      public str_istitle_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.istitle() -> bool\n\nReturn True if S is a titlecased string and there is at least one\ncharacter in S, i.e. uppercase characters may only follow uncased\ncharacters and lowercase characters only cased ones. Return False\notherwise.";
      }

      public str_istitle_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.istitle() -> bool\n\nReturn True if S is a titlecased string and there is at least one\ncharacter in S, i.e. uppercase characters may only follow uncased\ncharacters and lowercase characters only cased ones. Return False\notherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_istitle_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_istitle());
      }
   }

   private static class str_isspace_exposer extends PyBuiltinMethodNarrow {
      public str_isspace_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "S.isspace() -> bool\n\nReturn True if all characters in S are whitespace\nand there is at least one character in S, False otherwise.";
      }

      public str_isspace_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.isspace() -> bool\n\nReturn True if all characters in S are whitespace\nand there is at least one character in S, False otherwise.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isspace_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isspace());
      }
   }

   private static class str_isunicode_exposer extends PyBuiltinMethodNarrow {
      public str_isunicode_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "isunicode is deprecated.";
      }

      public str_isunicode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "isunicode is deprecated.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_isunicode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyString)this.self).str_isunicode());
      }
   }

   private static class str_encode_exposer extends PyBuiltinMethod {
      public str_encode_exposer(String var1) {
         super(var1);
         super.doc = "S.encode([encoding[,errors]]) -> object\n\nEncodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeEncodeError. Other possible values are 'ignore', 'replace' and\n'xmlcharrefreplace' as well as any other name registered with\ncodecs.register_error that is able to handle UnicodeEncodeErrors.";
      }

      public str_encode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.encode([encoding[,errors]]) -> object\n\nEncodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeEncodeError. Other possible values are 'ignore', 'replace' and\n'xmlcharrefreplace' as well as any other name registered with\ncodecs.register_error that is able to handle UnicodeEncodeErrors.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_encode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         String var10000 = ((PyString)this.self).str_encode(var1, var2);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class str_decode_exposer extends PyBuiltinMethod {
      public str_decode_exposer(String var1) {
         super(var1);
         super.doc = "S.decode([encoding[,errors]]) -> object\n\nDecodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError. Other possible values are 'ignore' and 'replace'\nas well as any other name registered with codecs.register_error that is\nable to handle UnicodeDecodeErrors.";
      }

      public str_decode_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.decode([encoding[,errors]]) -> object\n\nDecodes S using the codec registered for encoding. encoding defaults\nto the default encoding. errors may be given to set a different error\nhandling scheme. Default is 'strict' meaning that encoding errors raise\na UnicodeDecodeError. Other possible values are 'ignore' and 'replace'\nas well as any other name registered with codecs.register_error that is\nable to handle UnicodeDecodeErrors.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_decode_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyString)this.self).str_decode(var1, var2);
      }
   }

   private static class str__formatter_parser_exposer extends PyBuiltinMethodNarrow {
      public str__formatter_parser_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public str__formatter_parser_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str__formatter_parser_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str__formatter_parser();
      }
   }

   private static class str__formatter_field_name_split_exposer extends PyBuiltinMethodNarrow {
      public str__formatter_field_name_split_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public str__formatter_field_name_split_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str__formatter_field_name_split_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyString)this.self).str__formatter_field_name_split();
      }
   }

   private static class str_format_exposer extends PyBuiltinMethod {
      public str_format_exposer(String var1) {
         super(var1);
         super.doc = "S.format(*args, **kwargs) -> string\n\nReturn a formatted version of S, using substitutions from args and kwargs.\nThe substitutions are identified by braces ('{' and '}').";
      }

      public str_format_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.format(*args, **kwargs) -> string\n\nReturn a formatted version of S, using substitutions from args and kwargs.\nThe substitutions are identified by braces ('{' and '}').";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str_format_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyString)this.self).str_format(var1, var2);
      }
   }

   private static class str___format___exposer extends PyBuiltinMethodNarrow {
      public str___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "S.__format__(format_spec) -> string\n\nReturn a formatted version of S as described by format_spec.";
      }

      public str___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "S.__format__(format_spec) -> string\n\nReturn a formatted version of S as described by format_spec.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new str___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyString)this.self).str___format__(var1);
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyString.str_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new str___str___exposer("__str__"), new str___len___exposer("__len__"), new str___repr___exposer("__repr__"), new str___getitem___exposer("__getitem__"), new str___getslice___exposer("__getslice__"), new str___cmp___exposer("__cmp__"), new str___eq___exposer("__eq__"), new str___ne___exposer("__ne__"), new str___lt___exposer("__lt__"), new str___le___exposer("__le__"), new str___gt___exposer("__gt__"), new str___ge___exposer("__ge__"), new str___hash___exposer("__hash__"), new str___contains___exposer("__contains__"), new str___mul___exposer("__mul__"), new str___rmul___exposer("__rmul__"), new str___add___exposer("__add__"), new str___getnewargs___exposer("__getnewargs__"), new str___mod___exposer("__mod__"), new str_lower_exposer("lower"), new str_upper_exposer("upper"), new str_title_exposer("title"), new str_swapcase_exposer("swapcase"), new str_strip_exposer("strip"), new str_lstrip_exposer("lstrip"), new str_rstrip_exposer("rstrip"), new str_split_exposer("split"), new str_rsplit_exposer("rsplit"), new str_partition_exposer("partition"), new str_rpartition_exposer("rpartition"), new str_splitlines_exposer("splitlines"), new str_index_exposer("index"), new str_rindex_exposer("rindex"), new str_count_exposer("count"), new str_find_exposer("find"), new str_rfind_exposer("rfind"), new str_ljust_exposer("ljust"), new str_rjust_exposer("rjust"), new str_center_exposer("center"), new str_zfill_exposer("zfill"), new str_expandtabs_exposer("expandtabs"), new str_capitalize_exposer("capitalize"), new str_replace_exposer("replace"), new str_join_exposer("join"), new str_startswith_exposer("startswith"), new str_endswith_exposer("endswith"), new str_translate_exposer("translate"), new str_islower_exposer("islower"), new str_isupper_exposer("isupper"), new str_isalpha_exposer("isalpha"), new str_isalnum_exposer("isalnum"), new str_isdecimal_exposer("isdecimal"), new str_isdigit_exposer("isdigit"), new str_isnumeric_exposer("isnumeric"), new str_istitle_exposer("istitle"), new str_isspace_exposer("isspace"), new str_isunicode_exposer("isunicode"), new str_encode_exposer("encode"), new str_decode_exposer("decode"), new str__formatter_parser_exposer("_formatter_parser"), new str__formatter_field_name_split_exposer("_formatter_field_name_split"), new str_format_exposer("format"), new str___format___exposer("__format__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("str", PyString.class, PyBaseString.class, (boolean)1, "str(object) -> string\n\nReturn a nice string representation of the object.\nIf the argument is a string, the return value is the same object.", var1, var2, new exposed___new__());
      }
   }
}
