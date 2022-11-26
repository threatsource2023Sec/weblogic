package org.python.core;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import org.python.core.util.StringUtil;

public class codecs {
   public static final String BACKSLASHREPLACE = "backslashreplace";
   public static final String IGNORE = "ignore";
   public static final String REPLACE = "replace";
   public static final String XMLCHARREFREPLACE = "xmlcharrefreplace";
   private static char Py_UNICODE_REPLACEMENT_CHARACTER = '�';
   static char[] hexdigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final byte[] utf7_category = new byte[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 3, 3};
   private static final String B64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
   private static final byte[] BASE64_VALUE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -2, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};
   private static byte[] utf8_code_length = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 0, 0};
   private static char[] hexdigit = "0123456789ABCDEF".toCharArray();

   public static String getDefaultEncoding() {
      return Py.getSystemState().getCodecState().getDefaultEncoding();
   }

   public static void setDefaultEncoding(String encoding) {
      Py.getSystemState().getCodecState().setDefaultEncoding(encoding);
   }

   public static PyObject lookup_error(String handlerName) {
      return Py.getSystemState().getCodecState().lookup_error(handlerName);
   }

   public static void register_error(String name, PyObject error) {
      Py.getSystemState().getCodecState().register_error(name, error);
   }

   public static void register(PyObject search_function) {
      Py.getSystemState().getCodecState().register(search_function);
   }

   public static PyTuple lookup(String encoding) {
      return Py.getSystemState().getCodecState().lookup(encoding);
   }

   private static String normalizestring(String string) {
      return string.toLowerCase().replace(' ', '-');
   }

   public static PyObject decode(PyString v, String encoding, String errors) {
      if (encoding == null) {
         encoding = getDefaultEncoding();
      } else {
         encoding = normalizestring(encoding);
      }

      if (errors != null) {
         errors = errors.intern();
      }

      if (encoding.equals("ascii")) {
         return wrapDecodeResult(PyUnicode_DecodeASCII(v.toString(), v.__len__(), errors));
      } else {
         PyObject decoder;
         try {
            decoder = lookup(encoding).__getitem__(1);
         } catch (PyException var5) {
            if (var5.match(Py.LookupError)) {
               if (encoding.equals("utf-8")) {
                  return wrapDecodeResult(PyUnicode_DecodeUTF8(v.toString(), errors));
               }

               if (encoding.equals("utf-7")) {
                  return wrapDecodeResult(PyUnicode_DecodeUTF7(v.toString(), errors));
               }

               if (encoding.equals("latin-1")) {
                  return wrapDecodeResult(PyUnicode_DecodeLatin1(v.toString(), v.__len__(), errors));
               }
            }

            throw var5;
         }

         PyObject result;
         if (errors != null) {
            result = decoder.__call__((PyObject)v, (PyObject)(new PyString(errors)));
         } else {
            result = decoder.__call__((PyObject)v);
         }

         if (result instanceof PyTuple && result.__len__() == 2) {
            return result.__getitem__(0);
         } else {
            throw Py.TypeError("decoder must return a tuple (object,integer)");
         }
      }
   }

   private static PyUnicode wrapDecodeResult(String result) {
      return new PyUnicode(result, true);
   }

   public static String encode(PyString v, String encoding, String errors) {
      if (encoding == null) {
         encoding = getDefaultEncoding();
      } else {
         encoding = normalizestring(encoding);
      }

      if (errors != null) {
         errors = errors.intern();
      }

      if (encoding.equals("latin-1")) {
         return PyUnicode_EncodeLatin1(v.toString(), v.__len__(), errors);
      } else if (encoding.equals("ascii")) {
         return PyUnicode_EncodeASCII(v.toString(), v.__len__(), errors);
      } else {
         PyObject encoder;
         try {
            encoder = lookup(encoding).__getitem__(0);
         } catch (PyException var6) {
            if (var6.match(Py.LookupError)) {
               if (encoding.equals("utf-8")) {
                  return PyUnicode_EncodeUTF8(v.toString(), errors);
               }

               if (encoding.equals("utf-7")) {
                  return PyUnicode_EncodeUTF7(v.toString(), false, false, errors);
               }
            }

            throw var6;
         }

         PyObject result;
         if (errors != null) {
            result = encoder.__call__((PyObject)v, (PyObject)(new PyString(errors)));
         } else {
            result = encoder.__call__((PyObject)v);
         }

         if (result instanceof PyTuple && result.__len__() == 2) {
            PyObject encoded = result.__getitem__(0);
            if (encoded instanceof PyString) {
               return encoded.toString();
            } else {
               throw Py.TypeError("encoder did not return a string/unicode object (type=" + encoded.getType().fastGetName() + ")");
            }
         } else {
            throw Py.TypeError("encoder must return a tuple (object,integer)");
         }
      }
   }

   public static PyObject strict_errors(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("strict_errors", args, kws, "exc");
      PyObject exc = ap.getPyObject(0);
      if (Py.isInstance(exc, Py.UnicodeDecodeError)) {
         throw new PyException(Py.UnicodeDecodeError, exc);
      } else if (Py.isInstance(exc, Py.UnicodeEncodeError)) {
         throw new PyException(Py.UnicodeEncodeError, exc);
      } else if (Py.isInstance(exc, Py.UnicodeTranslateError)) {
         throw new PyException(Py.UnicodeTranslateError, exc);
      } else {
         throw wrong_exception_type(exc);
      }
   }

   public static PyObject ignore_errors(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("ignore_errors", args, kws, "exc");
      PyObject exc = ap.getPyObject(0);
      if (!isUnicodeError(exc)) {
         throw wrong_exception_type(exc);
      } else {
         PyObject end = exc.__getattr__("end");
         return new PyTuple(new PyObject[]{Py.EmptyUnicode, end});
      }
   }

   private static boolean isUnicodeError(PyObject exc) {
      return Py.isInstance(exc, Py.UnicodeDecodeError) || Py.isInstance(exc, Py.UnicodeEncodeError) || Py.isInstance(exc, Py.UnicodeTranslateError);
   }

   public static PyObject replace_errors(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("replace_errors", args, kws, "exc");
      PyObject exc = ap.getPyObject(0);
      int end;
      if (Py.isInstance(exc, Py.UnicodeEncodeError)) {
         end = exceptions.getEnd(exc, true);
         return new PyTuple(new PyObject[]{new PyUnicode("?"), Py.newInteger(end)});
      } else if (Py.isInstance(exc, Py.UnicodeDecodeError)) {
         end = exceptions.getEnd(exc, false);
         return new PyTuple(new PyObject[]{new PyUnicode(Py_UNICODE_REPLACEMENT_CHARACTER), Py.newInteger(end)});
      } else if (Py.isInstance(exc, Py.UnicodeTranslateError)) {
         end = exceptions.getEnd(exc, true);
         return new PyTuple(new PyObject[]{new PyUnicode(Py_UNICODE_REPLACEMENT_CHARACTER), Py.newInteger(end)});
      } else {
         throw wrong_exception_type(exc);
      }
   }

   public static PyObject xmlcharrefreplace_errors(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("xmlcharrefreplace_errors", args, kws, "exc");
      PyObject exc = ap.getPyObject(0);
      if (!Py.isInstance(exc, Py.UnicodeEncodeError)) {
         throw wrong_exception_type(exc);
      } else {
         int start = ((PyInteger)exc.__getattr__("start")).getValue();
         int end = ((PyInteger)exc.__getattr__("end")).getValue();
         String object = exc.__getattr__("object").toString();
         StringBuilder replacement = new StringBuilder();
         xmlcharrefreplace_internal(start, end, object, replacement);
         return new PyTuple(new PyObject[]{Py.java2py(replacement.toString()), exc.__getattr__("end")});
      }
   }

   public static StringBuilder xmlcharrefreplace(int start, int end, String toReplace) {
      StringBuilder replacement = new StringBuilder();
      xmlcharrefreplace_internal(start, end, toReplace, replacement);
      return replacement;
   }

   private static void xmlcharrefreplace_internal(int start, int end, String object, StringBuilder replacement) {
      for(int i = start; i < end; ++i) {
         replacement.append("&#");
         char cur = object.charAt(i);
         int digits;
         int base;
         if (cur < '\n') {
            digits = 1;
            base = 1;
         } else if (cur < 'd') {
            digits = 2;
            base = 10;
         } else if (cur < 1000) {
            digits = 3;
            base = 100;
         } else if (cur < 10000) {
            digits = 4;
            base = 1000;
         } else if (cur < 100000) {
            digits = 5;
            base = 10000;
         } else if (cur < 1000000) {
            digits = 6;
            base = 100000;
         } else {
            digits = 7;
            base = 1000000;
         }

         while(digits-- > 0) {
            replacement.append((char)(48 + cur / base));
            cur = (char)(cur % base);
            base /= 10;
         }

         replacement.append(';');
      }

   }

   private static PyException wrong_exception_type(PyObject exc) {
      PyObject excClass = exc.__getattr__("__class__");
      PyObject className = excClass.__getattr__("__name__");
      return new PyException(Py.TypeError, "Don't know how to handle " + className + " in error callback");
   }

   public static PyObject backslashreplace_errors(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("backslashreplace_errors", args, kws, "exc");
      PyObject exc = ap.getPyObject(0);
      if (!Py.isInstance(exc, Py.UnicodeEncodeError)) {
         throw wrong_exception_type(exc);
      } else {
         int start = ((PyInteger)exc.__getattr__("start")).getValue();
         int end = ((PyInteger)exc.__getattr__("end")).getValue();
         String object = exc.__getattr__("object").toString();
         StringBuilder replacement = new StringBuilder();
         backslashreplace_internal(start, end, object, replacement);
         return new PyTuple(new PyObject[]{Py.java2py(replacement.toString()), exc.__getattr__("end")});
      }
   }

   public static StringBuilder backslashreplace(int start, int end, String toReplace) {
      StringBuilder replacement = new StringBuilder();
      backslashreplace_internal(start, end, toReplace, replacement);
      return replacement;
   }

   private static void backslashreplace_internal(int start, int end, String object, StringBuilder replacement) {
      Iterator iter = new StringSubsequenceIterator(object, start, end, 1);

      while(iter.hasNext()) {
         int c = (Integer)iter.next();
         replacement.append('\\');
         if (c >= 65536) {
            replacement.append('U');
            replacement.append(hexdigits[c >> 28 & 15]);
            replacement.append(hexdigits[c >> 24 & 15]);
            replacement.append(hexdigits[c >> 20 & 15]);
            replacement.append(hexdigits[c >> 16 & 15]);
            replacement.append(hexdigits[c >> 12 & 15]);
            replacement.append(hexdigits[c >> 8 & 15]);
         } else if (c >= 256) {
            replacement.append('u');
            replacement.append(hexdigits[c >> 12 & 15]);
            replacement.append(hexdigits[c >> 8 & 15]);
         } else {
            replacement.append('x');
         }

         replacement.append(hexdigits[c >> 4 & 15]);
         replacement.append(hexdigits[c & 15]);
      }

   }

   private static boolean ENCODE_DIRECT(int c, boolean directO, boolean directWS) {
      if (c < 128 && c >= 0) {
         switch (utf7_category[c]) {
            case 0:
               return true;
            case 1:
               return directWS;
            case 2:
               return directO;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   private static char TO_BASE64(int n) {
      return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(n & 63);
   }

   private static int FROM_BASE64(int c) {
      return c >= 128 ? -1 : BASE64_VALUE[c];
   }

   public static String PyUnicode_DecodeUTF7Stateful(String bytes, String errors, int[] consumed) {
      boolean inBase64 = false;
      long base64buffer = 0L;
      int base64bits = 0;
      int startInBytes = 0;
      int syncInBytes = 0;
      int startInUnicode = 0;
      int size = bytes.length();
      StringBuilder unicode = new StringBuilder(size);

      int s;
      for(s = 0; s < size; ++s) {
         int b = bytes.charAt(s);
         if (b >= 128) {
            s = insertReplacementAndGetResume(unicode, errors, "utf-7", bytes, s, s + 1, "unexpected special character") - 1;
         } else if (inBase64) {
            if (base64bits == 0) {
               syncInBytes = s;
            }

            int sixBits = FROM_BASE64(b);
            UTF7Error error;
            if (sixBits >= 0) {
               base64buffer = base64buffer << 6 | (long)sixBits;
               base64bits += 6;
               if (base64bits >= 32) {
                  base64bits = emitCodePoints(unicode, base64buffer, base64bits);
                  if (base64bits >= 32) {
                     error = emitCodePointsDiagnosis(base64buffer, base64bits);
                     s = insertReplacementAndGetResume(unicode, errors, "utf-7", bytes, syncInBytes, s + 1, error.msg) - 1;
                     base64bits -= 16;
                  }
               }
            } else {
               inBase64 = false;
               if (base64bits > 0) {
                  base64bits = emitCodePoints(unicode, base64buffer, base64bits);
                  error = emitCodePointsDiagnosis(base64buffer, base64bits);
                  if (error != codecs.UTF7Error.NONE) {
                     s = insertReplacementAndGetResume(unicode, errors, "utf-7", bytes, s, s + 1, error.msg) - 1;
                  }

                  base64bits = 0;
               }

               if (b == '-') {
                  if (s == startInBytes + 1) {
                     unicode.append('+');
                  }
               } else {
                  unicode.appendCodePoint(b);
               }
            }
         } else if (b == '+') {
            startInBytes = s;
            startInUnicode = unicode.length();
            base64bits = 0;
            inBase64 = true;
         } else {
            unicode.appendCodePoint(b);
         }
      }

      if (inBase64) {
         s = startInBytes;
         unicode.setLength(startInUnicode);
      }

      if (consumed != null) {
         consumed[0] = s;
      } else if (s < size) {
         insertReplacementAndGetResume(unicode, errors, "utf-7", bytes, startInBytes, size, "unterminated shift sequence");
      }

      return unicode.toString();
   }

   public static String PyUnicode_DecodeUTF7(String bytes, String errors) {
      return PyUnicode_DecodeUTF7Stateful(bytes, errors, (int[])null);
   }

   private static int emitCodePoints(StringBuilder v, long buffer, int n) {
      while(true) {
         if (n >= 16) {
            int unit = (int)(buffer >>> n - 16);
            boolean unitIsSurrogate = (unit & '\uf800') == 55296;
            if (!unitIsSurrogate) {
               v.append((char)unit);
               n -= 16;
               continue;
            }

            if (n >= 32) {
               if ((unit & 1024) == 0) {
                  int unit2 = (int)(buffer >>> n - 32);
                  if ((unit2 & 'ﰀ') == 56320) {
                     v.appendCodePoint(65536 + ((unit & 1023) << 10) + (unit2 & 1023));
                     n -= 32;
                     continue;
                  }

                  return n;
               }

               return n;
            }

            return n;
         }

         return n;
      }
   }

   private static UTF7Error emitCodePointsDiagnosis(long buffer, int n) {
      int unit;
      if (n >= 16) {
         unit = (int)(buffer >>> n - 16);
         boolean unitIsSurrogate = (unit & '\uf800') == 55296;
         if (!unitIsSurrogate) {
            return codecs.UTF7Error.NONE;
         } else if (n >= 32) {
            if ((unit & 1024) == 0) {
               int unit2 = (int)(buffer >>> n - 32) & '\uffff';
               return (unit2 & 'ﰀ') == 56320 ? codecs.UTF7Error.NONE : codecs.UTF7Error.MISSING;
            } else {
               return codecs.UTF7Error.TRAIL;
            }
         } else {
            return (unit & 1024) == 0 ? codecs.UTF7Error.TRUNCATED : codecs.UTF7Error.TRAIL;
         }
      } else if (n >= 6) {
         return codecs.UTF7Error.PARTIAL;
      } else {
         unit = (1 << n) - 1;
         int padding = (int)buffer & unit;
         return padding != 0 ? codecs.UTF7Error.PADDING : codecs.UTF7Error.NONE;
      }
   }

   public static String PyUnicode_EncodeUTF7(String unicode, boolean base64SetO, boolean base64WhiteSpace, String errors) {
      boolean inBase64 = false;
      int base64bits = 0;
      long base64buffer = 0L;
      int size = unicode.length();
      StringBuilder v = new StringBuilder(size + size / 8 + 10);

      for(int i = 0; i < size; ++i) {
         int ch = unicode.charAt(i);
         if (inBase64) {
            if (ENCODE_DIRECT(ch, !base64SetO, !base64WhiteSpace)) {
               emitBase64Padded(v, base64buffer, base64bits);
               inBase64 = false;
               if (FROM_BASE64(ch) != -1) {
                  v.append('-');
               }
            }
         } else if (ch == '+') {
            v.append('+');
            ch = '-';
         } else if (!ENCODE_DIRECT(ch, !base64SetO, !base64WhiteSpace)) {
            v.append('+');
            inBase64 = true;
            base64bits = 0;
         }

         if (!inBase64) {
            v.append((char)ch);
         } else {
            if (base64bits > 48) {
               base64bits = emitBase64(v, base64buffer, base64bits);
            }

            base64bits += 16;
            base64buffer = (base64buffer << 16) + (long)ch;
         }
      }

      if (inBase64) {
         emitBase64Padded(v, base64buffer, base64bits);
         v.append('-');
      }

      return v.toString();
   }

   private static int emitBase64(StringBuilder v, long buffer, int n) {
      while(n >= 6) {
         n -= 6;
         long sixBits = buffer >>> n;
         char b64byte = TO_BASE64((int)sixBits);
         v.append(b64byte);
      }

      return n;
   }

   private static void emitBase64Padded(StringBuilder v, long buffer, int n) {
      if (n > 0) {
         int npad = 5 - (n + 5) % 6;
         emitBase64(v, buffer << npad, n + npad);
      }

   }

   public static String PyUnicode_DecodeUTF8(String str, String errors) {
      return PyUnicode_DecodeUTF8Stateful(str, errors, (int[])null);
   }

   public static String PyUnicode_DecodeUTF8Stateful(String str, String errors, int[] consumed) {
      int size = str.length();
      StringBuilder unicode = new StringBuilder(size);
      int i = 0;

      while(i < size) {
         int ch = str.charAt(i);
         if (ch < 128) {
            unicode.append((char)ch);
            ++i;
         } else if (ch > 255) {
            i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 1, "ordinal not in range(255)");
         } else {
            int n = utf8_code_length[ch];
            if (i + n > size) {
               if (consumed != null) {
                  break;
               }

               i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 1, "unexpected end of data");
            } else {
               char ch1;
               char ch2;
               switch (n) {
                  case 0:
                     i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 1, "unexpected code byte");
                     continue;
                  case 1:
                     i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 1, "internal error");
                     continue;
                  case 2:
                     ch1 = str.charAt(i + 1);
                     if ((ch1 & 192) != 128) {
                        i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 2, "invalid data");
                        continue;
                     }

                     ch = ((ch & 31) << 6) + (ch1 & 63);
                     if (ch < 128) {
                        i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 2, "illegal encoding");
                        continue;
                     }

                     unicode.appendCodePoint(ch);
                     break;
                  case 3:
                     ch1 = str.charAt(i + 1);
                     ch2 = str.charAt(i + 2);
                     if ((ch1 & 192) == 128 && (ch2 & 192) == 128) {
                        ch = ((ch & 15) << 12) + ((ch1 & 63) << 6) + (ch2 & 63);
                        if (ch >= 2048 && (ch < 55296 || ch >= 57344)) {
                           unicode.appendCodePoint(ch);
                           break;
                        }

                        i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 3, "illegal encoding");
                        continue;
                     }

                     i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 3, "invalid data");
                     continue;
                  case 4:
                     ch1 = str.charAt(i + 1);
                     ch2 = str.charAt(i + 2);
                     char ch3 = str.charAt(i + 3);
                     if ((ch1 & 192) == 128 && (ch2 & 192) == 128 && (ch3 & 192) == 128) {
                        ch = ((ch & 7) << 18) + ((ch1 & 63) << 12) + ((ch2 & 63) << 6) + (ch3 & 63);
                        if (ch < 65536 || ch > 1114111) {
                           i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 4, "illegal encoding");
                           continue;
                        }

                        unicode.appendCodePoint(ch);
                        break;
                     }

                     i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + 4, "invalid data");
                     continue;
                  default:
                     i = insertReplacementAndGetResume(unicode, errors, "utf-8", str, i, i + n, "unsupported Unicode code range");
                     continue;
               }

               i += n;
            }
         }
      }

      if (consumed != null) {
         consumed[0] = i;
      }

      return unicode.toString();
   }

   public static String PyUnicode_EncodeUTF8(String str, String errors) {
      return StringUtil.fromBytes(Charset.forName("UTF-8").encode(str));
   }

   public static String PyUnicode_DecodeASCII(String str, int size, String errors) {
      return PyUnicode_DecodeIntLimited(str, size, errors, "ascii", 128);
   }

   public static String PyUnicode_DecodeLatin1(String str, int size, String errors) {
      return PyUnicode_DecodeIntLimited(str, size, errors, "latin-1", 256);
   }

   private static String PyUnicode_DecodeIntLimited(String str, int size, String errors, String encoding, int limit) {
      StringBuilder v = new StringBuilder(size);
      String reason = "ordinal not in range(" + limit + ")";

      for(int i = 0; i < size; ++i) {
         char ch = str.charAt(i);
         if (ch < limit) {
            v.append(ch);
         } else {
            i = insertReplacementAndGetResume(v, errors, encoding, str, i, i + 1, reason) - 1;
         }
      }

      return v.toString();
   }

   public static String PyUnicode_EncodeASCII(String str, int size, String errors) {
      return PyUnicode_EncodeIntLimited(str, size, errors, "ascii", 128);
   }

   public static String PyUnicode_EncodeLatin1(String str, int size, String errors) {
      return PyUnicode_EncodeIntLimited(str, size, errors, "latin-1", 256);
   }

   private static String PyUnicode_EncodeIntLimited(String str, int size, String errors, String encoding, int limit) {
      String reason = "ordinal not in range(" + limit + ")";
      StringBuilder v = new StringBuilder(size);

      for(int i = 0; i < size; ++i) {
         char ch = str.charAt(i);
         if (ch < limit) {
            v.append(ch);
         } else {
            int nextGood;
            for(nextGood = i + 1; nextGood < size && str.charAt(nextGood) >= limit; ++nextGood) {
            }

            if (errors != null) {
               if (errors.equals("ignore")) {
                  i = nextGood - 1;
                  continue;
               }

               if (errors.equals("replace")) {
                  for(int j = i; j < nextGood; ++j) {
                     v.append('?');
                  }

                  i = nextGood - 1;
                  continue;
               }

               if (errors.equals("xmlcharrefreplace")) {
                  v.append(xmlcharrefreplace(i, nextGood, str));
                  i = nextGood - 1;
                  continue;
               }

               if (errors.equals("backslashreplace")) {
                  v.append(backslashreplace(i, nextGood, str));
                  i = nextGood - 1;
                  continue;
               }
            }

            PyObject replacement = encoding_error(errors, encoding, str, i, nextGood, reason);
            String replStr = replacement.__getitem__(0).toString();

            for(int j = 0; j < replStr.length(); ++j) {
               if (replStr.charAt(j) >= limit) {
                  throw Py.UnicodeEncodeError(encoding, str, i + j, i + j + 1, reason);
               }
            }

            v.append(replStr);
            i = calcNewPosition(size, replacement) - 1;
         }
      }

      return v.toString();
   }

   public static String PyUnicode_EncodeRawUnicodeEscape(String str, String errors, boolean modifed) {
      StringBuilder v = new StringBuilder(str.length());
      Iterator iter = (new PyUnicode(str)).newSubsequenceIterator();

      while(true) {
         while(iter.hasNext()) {
            int codePoint = (Integer)iter.next();
            if (codePoint >= 65536) {
               v.append("\\U");
               v.append(hexdigit[codePoint >> 28 & 15]);
               v.append(hexdigit[codePoint >> 24 & 15]);
               v.append(hexdigit[codePoint >> 20 & 15]);
               v.append(hexdigit[codePoint >> 16 & 15]);
               v.append(hexdigit[codePoint >> 12 & 15]);
               v.append(hexdigit[codePoint >> 8 & 15]);
               v.append(hexdigit[codePoint >> 4 & 15]);
               v.append(hexdigit[codePoint & 15]);
            } else if (codePoint < 256 && (!modifed || codePoint != 92 && codePoint != 10)) {
               v.append((char)codePoint);
            } else {
               v.append("\\u");
               v.append(hexdigit[codePoint >> 12 & 15]);
               v.append(hexdigit[codePoint >> 8 & 15]);
               v.append(hexdigit[codePoint >> 4 & 15]);
               v.append(hexdigit[codePoint & 15]);
            }
         }

         return v.toString();
      }
   }

   public static String PyUnicode_DecodeRawUnicodeEscape(String str, String errors) {
      int size = str.length();
      StringBuilder v = new StringBuilder(size);
      int i = 0;

      while(true) {
         while(i < size) {
            char ch = str.charAt(i);
            if (ch != '\\') {
               v.append(ch);
               ++i;
            } else {
               int bs;
               for(bs = i; i < size; ++i) {
                  ch = str.charAt(i);
                  if (ch != '\\') {
                     break;
                  }

                  v.append(ch);
               }

               if ((i - bs & 1) != 0 && i < size && (ch == 'u' || ch == 'U')) {
                  v.setLength(v.length() - 1);
                  int count = ch == 'u' ? 4 : 8;
                  ++i;
                  int codePoint = 0;
                  int asDigit = -1;

                  for(int j = 0; j < count; ++j) {
                     if (i == size) {
                        asDigit = -1;
                        break;
                     }

                     ch = str.charAt(i);
                     asDigit = Character.digit(ch, 16);
                     if (asDigit == -1) {
                        break;
                     }

                     codePoint = (codePoint << 4 & -16) + asDigit;
                     ++i;
                  }

                  if (asDigit == -1) {
                     i = insertReplacementAndGetResume(v, errors, "rawunicodeescape", str, bs, i, "truncated \\uXXXX");
                  } else {
                     v.appendCodePoint(codePoint);
                  }
               }
            }
         }

         return v.toString();
      }
   }

   public static String PyUnicode_EncodePunycode(PyUnicode input, String errors) {
      int n = 128;
      int delta = 0;
      int bias = 72;
      int b = 0;
      StringBuilder buffer = new StringBuilder();
      Iterator iter = input.iterator();

      int size;
      while(iter.hasNext()) {
         size = (Integer)iter.next();
         if (codecs.Punycode.isBasic(size)) {
            buffer.appendCodePoint(size);
            ++b;
         }
      }

      if (b > 0) {
         buffer.appendCodePoint(45);
      }

      int h = b;

      for(size = input.getCodePointCount(); h < size; ++n) {
         int m = Integer.MAX_VALUE;
         int i = 0;
         int codePointIndex = 0;

         Iterator iter;
         int c;
         for(iter = input.iterator(); iter.hasNext(); ++i) {
            c = (Integer)iter.next();
            if (c > n && c < m) {
               m = c;
               codePointIndex = i;
            }
         }

         long guard_delta = (long)(delta + (m - n) * (h + 1));
         if (guard_delta > 2147483647L) {
            throw Py.UnicodeEncodeError("punycode", input.getString(), codePointIndex, codePointIndex + 1, "overflow");
         }

         delta = (int)guard_delta;
         n = m;
         i = 0;

         for(iter = input.iterator(); iter.hasNext(); ++i) {
            c = (Integer)iter.next();
            if (c < n) {
               guard_delta = (long)(delta + 1);
               if (guard_delta > 2147483647L) {
                  throw Py.UnicodeEncodeError("punycode", input.getString(), i, i + 1, "overflow");
               }

               delta = (int)guard_delta;
            }

            if (c == n) {
               int q = delta;
               int k = 36;

               while(true) {
                  int t = k <= bias ? 1 : (k >= bias + 26 ? 26 : k - bias);
                  if (q < t) {
                     buffer.appendCodePoint(q);
                     bias = codecs.Punycode.adapt(delta, h + 1, h == b);
                     delta = 0;
                     ++h;
                     break;
                  }

                  buffer.appendCodePoint(t + (q - t) % (36 - t));
                  q = (q - t) / (36 - t);
                  k += 36;
               }
            }
         }

         ++delta;
      }

      return buffer.toString();
   }

   public static PyUnicode PyUnicode_DecodePunycode(String input, String errors) {
      int input_size = input.length();
      int output_size = 0;
      ArrayList ucs4 = new ArrayList(input_size);

      int j;
      int n;
      for(j = 0; j < input_size; ++j) {
         n = input.charAt(j);
         if (!codecs.Punycode.isBasic(n)) {
            throw Py.UnicodeDecodeError("punycode", input, j, j + 1, "not basic");
         }

         if (n == 45) {
            break;
         }

         ucs4.add(n);
         ++output_size;
      }

      n = 128;
      int i = 0;
      int bias = 72;

      while(j < input_size) {
         int old_i = i;
         int w = 1;
         int k = 36;

         while(true) {
            int c = input.charAt(j++);
            int digit = c - 48;
            long guard_i = (long)(i + digit * w);
            if (guard_i > 2147483647L) {
               throw Py.UnicodeDecodeError("punycode", input, j, j + 1, "overflow");
            }

            i = (int)guard_i;
            int t = k <= bias ? 1 : (k >= bias + 26 ? 26 : k - bias);
            if (digit < t) {
               bias = codecs.Punycode.adapt(i - old_i, output_size + 1, old_i == 0);
               n += i / (output_size + 1);
               i %= output_size + 1;
               ucs4.add(i, n);
               break;
            }

            long guard_w = (long)(w * 36 - t);
            if (guard_w > 2147483647L) {
               throw Py.UnicodeDecodeError("punycode", input, j, j + 1, "overflow");
            }

            k += 36;
         }
      }

      return new PyUnicode(ucs4);
   }

   public static String PyUnicode_EncodeIDNA(PyUnicode input, String errors) {
      throw new UnsupportedOperationException();
   }

   public static PyUnicode PyUnicode_DecodeIDNA(String input, String errors) {
      throw new UnsupportedOperationException();
   }

   public static PyObject encoding_error(String errors, String encoding, String toEncode, int start, int end, String reason) {
      PyObject errorHandler = lookup_error(errors);
      PyException exc = Py.UnicodeEncodeError(encoding, toEncode, start, end, reason);
      exc.normalize();
      PyObject replacement = errorHandler.__call__(new PyObject[]{exc.value});
      checkErrorHandlerReturn(errors, replacement);
      return replacement;
   }

   public static int insertReplacementAndGetResume(StringBuilder partialDecode, String errors, String encoding, String toDecode, int start, int end, String reason) {
      if (errors != null) {
         if (errors.equals("ignore")) {
            return end;
         }

         if (errors.equals("replace")) {
            partialDecode.appendCodePoint(Py_UNICODE_REPLACEMENT_CHARACTER);
            return end;
         }
      }

      PyObject replacementSpec = decoding_error(errors, encoding, toDecode, start, end, reason);
      partialDecode.append(replacementSpec.__getitem__(0).toString());
      return calcNewPosition(toDecode.length(), replacementSpec);
   }

   public static PyObject decoding_error(String errors, String encoding, String toDecode, int start, int end, String reason) {
      PyObject errorHandler = lookup_error(errors);
      PyException exc = Py.UnicodeDecodeError(encoding, toDecode, start, end, reason);
      exc.normalize();
      PyObject replacementSpec = errorHandler.__call__(new PyObject[]{exc.value});
      checkErrorHandlerReturn(errors, replacementSpec);
      return replacementSpec;
   }

   private static void checkErrorHandlerReturn(String errors, PyObject replacementSpec) {
      if (!(replacementSpec instanceof PyTuple) || replacementSpec.__len__() != 2 || !(replacementSpec.__getitem__(0) instanceof PyBaseString) || !(replacementSpec.__getitem__(1) instanceof PyInteger)) {
         throw new PyException(Py.TypeError, "error_handler " + errors + " must return a tuple of (replacement, new position)");
      }
   }

   public static int calcNewPosition(int size, PyObject errorTuple) {
      int newPosition = ((PyInteger)errorTuple.__getitem__(1)).getValue();
      if (newPosition < 0) {
         newPosition += size;
      }

      if (newPosition <= size && newPosition >= 0) {
         return newPosition;
      } else {
         throw Py.IndexError(newPosition + " out of bounds of encoded string");
      }
   }

   public static class CodecState {
      private PyList searchPath = new PyList();
      private PyStringMap searchCache = new PyStringMap();
      private PyStringMap errorHandlers = new PyStringMap();
      private String default_encoding = "ascii";
      public static final String[] BUILTIN_ERROR_HANDLERS = new String[]{"strict", "ignore", "replace", "xmlcharrefreplace", "backslashreplace"};

      public CodecState() {
         String[] var1 = BUILTIN_ERROR_HANDLERS;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String builtinErrorHandler = var1[var3];
            this.register_error(builtinErrorHandler, Py.newJavaFunc(codecs.class, builtinErrorHandler + "_errors"));
         }

      }

      public String getDefaultEncoding() {
         return this.default_encoding;
      }

      public void setDefaultEncoding(String encoding) {
         this.lookup(encoding);
         this.default_encoding = encoding;
      }

      public void register_error(String name, PyObject error) {
         if (!error.isCallable()) {
            throw Py.TypeError("argument must be callable");
         } else {
            this.errorHandlers.__setitem__(name.intern(), error);
         }
      }

      public void register(PyObject search_function) {
         if (!search_function.isCallable()) {
            throw Py.TypeError("argument must be callable");
         } else {
            this.searchPath.append(search_function);
         }
      }

      public PyTuple lookup(String encoding) {
         PyString v = new PyString(codecs.normalizestring(encoding));
         PyObject cached = this.searchCache.__finditem__((PyObject)v);
         if (cached != null) {
            return (PyTuple)cached;
         } else if (this.searchPath.__len__() == 0) {
            throw new PyException(Py.LookupError, "no codec search functions registered: can't find encoding '" + encoding + "'");
         } else {
            Iterator var4 = this.searchPath.asIterable().iterator();

            PyObject created;
            do {
               if (!var4.hasNext()) {
                  throw new PyException(Py.LookupError, "unknown encoding '" + encoding + "'");
               }

               PyObject func = (PyObject)var4.next();
               created = func.__call__((PyObject)v);
            } while(created == Py.None);

            if (created instanceof PyTuple && created.__len__() == 4) {
               this.searchCache.__setitem__((PyObject)v, created);
               return (PyTuple)created;
            } else {
               throw Py.TypeError("codec search functions must return 4-tuples");
            }
         }
      }

      public PyObject lookup_error(String handlerName) {
         if (handlerName == null) {
            handlerName = "strict";
         }

         PyObject handler = this.errorHandlers.__finditem__(handlerName.intern());
         if (handler == null) {
            throw new PyException(Py.LookupError, "unknown error handler name '" + handlerName + "'");
         } else {
            return handler;
         }
      }
   }

   private static class Punycode {
      private static final int BASE = 36;
      private static final int TMIN = 1;
      private static final int TMAX = 26;
      private static final int SKEW = 38;
      private static final int DAMP = 700;
      private static final int INITIAL_BIAS = 72;
      private static final int INITIAL_N = 128;
      private static final int BASIC = 128;

      private static int adapt(int delta, int numpoints, boolean firsttime) {
         delta = firsttime ? delta / 700 : delta >> 1;
         delta += delta / numpoints;

         int k;
         for(k = 0; delta > 455; k += 36) {
            delta /= 35;
         }

         return k + 36 * delta / (delta + 38);
      }

      private static boolean isBasic(int codePoint) {
         return codePoint < 128;
      }
   }

   static enum UTF7Error {
      NONE("No error"),
      PADDING("non-zero padding bits in shift sequence"),
      PARTIAL("partial character in shift sequence"),
      TRUNCATED("second surrogate missing at end of shift sequence"),
      MISSING("second surrogate missing"),
      TRAIL("unexpected second surrogate");

      final String msg;

      private UTF7Error(String msg) {
         this.msg = msg;
      }
   }
}
