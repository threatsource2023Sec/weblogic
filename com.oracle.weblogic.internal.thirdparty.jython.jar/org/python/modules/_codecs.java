package org.python.modules;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import org.python.core.Py;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyDictionary;
import org.python.core.PyInteger;
import org.python.core.PyNewWrapper;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Untraversable;
import org.python.core.codecs;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

public class _codecs {
   public static void register(PyObject search_function) {
      codecs.register(search_function);
   }

   private static String _castString(PyString pystr) {
      if (pystr == null) {
         return null;
      } else {
         String s = pystr.toString();
         return pystr instanceof PyUnicode ? s : codecs.PyUnicode_EncodeASCII(s, s.length(), (String)null);
      }
   }

   public static PyTuple lookup(PyString encoding) {
      return codecs.lookup(_castString(encoding));
   }

   public static PyObject lookup_error(PyString handlerName) {
      return codecs.lookup_error(_castString(handlerName));
   }

   public static void register_error(String name, PyObject errorHandler) {
      codecs.register_error(name, errorHandler);
   }

   public static PyObject decode(PyString bytes) {
      return decode(bytes, (PyString)null, (PyString)null);
   }

   public static PyObject decode(PyString bytes, PyString encoding) {
      return decode(bytes, encoding, (PyString)null);
   }

   public static PyObject decode(PyString bytes, PyString encoding, PyString errors) {
      return codecs.decode(bytes, _castString(encoding), _castString(errors));
   }

   public static PyString encode(PyUnicode unicode) {
      return encode(unicode, (PyString)null, (PyString)null);
   }

   public static PyString encode(PyUnicode unicode, PyString encoding) {
      return encode(unicode, encoding, (PyString)null);
   }

   public static PyString encode(PyUnicode unicode, PyString encoding, PyString errors) {
      return Py.newString(codecs.encode(unicode, _castString(encoding), _castString(errors)));
   }

   public static PyObject charmap_build(PyUnicode map) {
      return _codecs.EncodingMap.buildEncodingMap(map);
   }

   private static PyTuple decode_tuple(String u, int bytesConsumed) {
      return new PyTuple(new PyObject[]{new PyUnicode(u), Py.newInteger(bytesConsumed)});
   }

   private static PyTuple decode_tuple(String u, int[] consumed, int defConsumed) {
      return decode_tuple(u, consumed != null ? consumed[0] : defConsumed);
   }

   private static PyTuple decode_tuple(String u, int bytesConsumed, ByteOrder order) {
      int bo = order.code();
      return new PyTuple(new PyObject[]{new PyUnicode(u), Py.newInteger(bytesConsumed), Py.newInteger(bo)});
   }

   private static PyTuple decode_tuple_str(String s, int len) {
      return new PyTuple(new PyObject[]{new PyString(s), Py.newInteger(len)});
   }

   private static PyTuple encode_tuple(String s, int len) {
      return new PyTuple(new PyObject[]{new PyString(s), Py.newInteger(len)});
   }

   public static PyTuple utf_8_decode(String str) {
      return utf_8_decode(str, (String)null);
   }

   public static PyTuple utf_8_decode(String str, String errors) {
      return utf_8_decode(str, errors, false);
   }

   public static PyTuple utf_8_decode(String str, String errors, PyObject final_) {
      return utf_8_decode(str, errors, final_.__nonzero__());
   }

   public static PyTuple utf_8_decode(String str, String errors, boolean final_) {
      int[] consumed = final_ ? null : new int[1];
      return decode_tuple(codecs.PyUnicode_DecodeUTF8Stateful(str, errors, consumed), final_ ? str.length() : consumed[0]);
   }

   public static PyTuple utf_8_encode(String str) {
      return utf_8_encode(str, (String)null);
   }

   public static PyTuple utf_8_encode(String str, String errors) {
      int size = str.length();
      return encode_tuple(codecs.PyUnicode_EncodeUTF8(str, errors), size);
   }

   public static PyTuple utf_7_decode(String bytes) {
      return utf_7_decode(bytes, (String)null);
   }

   public static PyTuple utf_7_decode(String bytes, String errors) {
      return utf_7_decode(bytes, (String)null, false);
   }

   public static PyTuple utf_7_decode(String bytes, String errors, boolean finalFlag) {
      int[] consumed = finalFlag ? null : new int[1];
      String decoded = codecs.PyUnicode_DecodeUTF7Stateful(bytes, errors, consumed);
      return decode_tuple(decoded, consumed, bytes.length());
   }

   public static PyTuple utf_7_encode(String str) {
      return utf_7_encode(str, (String)null);
   }

   public static PyTuple utf_7_encode(String str, String errors) {
      int size = str.length();
      return encode_tuple(codecs.PyUnicode_EncodeUTF7(str, false, false, errors), size);
   }

   public static PyTuple escape_decode(String str) {
      return escape_decode(str, (String)null);
   }

   public static PyTuple escape_decode(String str, String errors) {
      return decode_tuple_str(PyString.decode_UnicodeEscape(str, 0, str.length(), errors, true), str.length());
   }

   public static PyTuple escape_encode(String str) {
      return escape_encode(str, (String)null);
   }

   public static PyTuple escape_encode(String str, String errors) {
      return encode_tuple(PyString.encode_UnicodeEscape(str, false), str.length());
   }

   public static PyTuple charmap_decode(String bytes) {
      return charmap_decode(bytes, (String)null, (PyObject)null);
   }

   public static PyTuple charmap_decode(String bytes, String errors) {
      return charmap_decode(bytes, errors, (PyObject)null);
   }

   public static PyTuple charmap_decode(String bytes, String errors, PyObject mapping) {
      return mapping != null && mapping != Py.None ? charmap_decode(bytes, errors, mapping, false) : latin_1_decode(bytes, errors);
   }

   public static PyTuple charmap_decode(String bytes, String errors, PyObject mapping, boolean ignoreUnmapped) {
      int size = bytes.length();
      StringBuilder v = new StringBuilder(size);

      for(int i = 0; i < size; ++i) {
         int b = bytes.charAt(i);
         if (b > 255) {
            i = codecs.insertReplacementAndGetResume(v, errors, "charmap", bytes, i, i + 1, "ordinal not in range(255)") - 1;
         } else {
            PyObject w = Py.newInteger(b);
            PyObject x = mapping.__finditem__((PyObject)w);
            if (x == null) {
               if (ignoreUnmapped) {
                  v.appendCodePoint(b);
               } else {
                  i = codecs.insertReplacementAndGetResume(v, errors, "charmap", bytes, i, i + 1, "no mapping found") - 1;
               }
            } else if (x instanceof PyInteger) {
               int value = ((PyInteger)x).getValue();
               if (value < 0 || value > 1114111) {
                  throw Py.TypeError("character mapping must return integer greater than 0 and less than sys.maxunicode");
               }

               v.appendCodePoint(value);
            } else if (x == Py.None) {
               i = codecs.insertReplacementAndGetResume(v, errors, "charmap", bytes, i, i + 1, "character maps to <undefined>") - 1;
            } else {
               if (!(x instanceof PyString)) {
                  throw Py.TypeError("character mapping must return integer, None or str");
               }

               String s = x.toString();
               if (s.charAt(0) == '\ufffe') {
                  i = codecs.insertReplacementAndGetResume(v, errors, "charmap", bytes, i, i + 1, "character maps to <undefined>") - 1;
               } else {
                  v.append(s);
               }
            }
         }
      }

      return decode_tuple(v.toString(), size);
   }

   public static PyObject translateCharmap(PyUnicode str, String errors, PyObject mapping) {
      StringBuilder buf = new StringBuilder(str.toString().length());
      Iterator iter = str.newSubsequenceIterator();

      while(true) {
         while(true) {
            PyObject result;
            label31:
            do {
               while(iter.hasNext()) {
                  int codePoint = (Integer)iter.next();
                  result = mapping.__finditem__((PyObject)Py.newInteger(codePoint));
                  if (result != null) {
                     continue label31;
                  }

                  buf.appendCodePoint(codePoint);
               }

               return new PyUnicode(buf.toString());
            } while(result == Py.None);

            if (result instanceof PyInteger) {
               int value = result.asInt();
               if (value < 0 || value > 1114111) {
                  throw Py.TypeError(String.format("character mapping must be in range(0x%x)", 1114112));
               }

               buf.appendCodePoint(value);
            } else {
               if (!(result instanceof PyUnicode)) {
                  throw Py.TypeError("character mapping must return integer, None or unicode");
               }

               buf.append(result.toString());
            }
         }
      }
   }

   public static PyTuple charmap_encode(String str) {
      return charmap_encode(str, (String)null, (PyObject)null);
   }

   public static PyTuple charmap_encode(String str, String errors) {
      return charmap_encode(str, errors, (PyObject)null);
   }

   public static PyTuple charmap_encode(String str, String errors, PyObject mapping) {
      return mapping != null && mapping != Py.None ? charmap_encode_internal(str, errors, mapping, new StringBuilder(str.length()), true) : latin_1_encode(str, errors);
   }

   private static PyTuple charmap_encode_internal(String str, String errors, PyObject mapping, StringBuilder v, boolean letLookupHandleError) {
      EncodingMap encodingMap = mapping instanceof EncodingMap ? (EncodingMap)mapping : null;
      int size = str.length();

      for(int i = 0; i < size; ++i) {
         char ch = str.charAt(i);
         int value;
         Object x;
         if (encodingMap != null) {
            value = encodingMap.lookup(ch);
            x = value == -1 ? null : Py.newInteger(value);
         } else {
            x = mapping.__finditem__((PyObject)Py.newInteger(ch));
         }

         if (x == null) {
            if (!letLookupHandleError) {
               throw Py.UnicodeEncodeError("charmap", str, i, i + 1, "character maps to <undefined>");
            }

            i = handleBadMapping(str, errors, mapping, v, size, i);
         } else if (x instanceof PyInteger) {
            value = ((PyInteger)x).getValue();
            if (value < 0 || value > 255) {
               throw Py.TypeError("character mapping must be in range(256)");
            }

            v.append((char)value);
         } else if (x instanceof PyString && !(x instanceof PyUnicode)) {
            v.append(((PyObject)x).toString());
         } else {
            if (!(x instanceof PyNone)) {
               throw Py.TypeError("character mapping must return integer, None or str");
            }

            i = handleBadMapping(str, errors, mapping, v, size, i);
         }
      }

      return encode_tuple(v.toString(), size);
   }

   private static int handleBadMapping(String str, String errors, PyObject mapping, StringBuilder v, int size, int i) {
      String replStr;
      if (errors != null) {
         if (errors.equals("ignore")) {
            return i;
         }

         if (errors.equals("replace")) {
            replStr = "?";
            charmap_encode_internal(replStr, errors, mapping, v, false);
            return i;
         }

         if (errors.equals("xmlcharrefreplace")) {
            replStr = codecs.xmlcharrefreplace(i, i + 1, str).toString();
            charmap_encode_internal(replStr, errors, mapping, v, false);
            return i;
         }

         if (errors.equals("backslashreplace")) {
            replStr = codecs.backslashreplace(i, i + 1, str).toString();
            charmap_encode_internal(replStr, errors, mapping, v, false);
            return i;
         }
      }

      replStr = "character maps to <undefined>";
      PyObject replacement = codecs.encoding_error(errors, "charmap", str, i, i + 1, replStr);
      String replStr = replacement.__getitem__(0).toString();
      charmap_encode_internal(replStr, errors, mapping, v, false);
      return codecs.calcNewPosition(size, replacement) - 1;
   }

   public static PyTuple ascii_decode(String str) {
      return ascii_decode(str, (String)null);
   }

   public static PyTuple ascii_decode(String str, String errors) {
      int size = str.length();
      return decode_tuple(codecs.PyUnicode_DecodeASCII(str, size, errors), size);
   }

   public static PyTuple ascii_encode(String str) {
      return ascii_encode(str, (String)null);
   }

   public static PyTuple ascii_encode(String str, String errors) {
      int size = str.length();
      return encode_tuple(codecs.PyUnicode_EncodeASCII(str, size, errors), size);
   }

   public static PyTuple latin_1_decode(String str) {
      return latin_1_decode(str, (String)null);
   }

   public static PyTuple latin_1_decode(String str, String errors) {
      int size = str.length();
      return decode_tuple(codecs.PyUnicode_DecodeLatin1(str, size, errors), size);
   }

   public static PyTuple latin_1_encode(String str) {
      return latin_1_encode(str, (String)null);
   }

   public static PyTuple latin_1_encode(String str, String errors) {
      int size = str.length();
      return encode_tuple(codecs.PyUnicode_EncodeLatin1(str, size, errors), size);
   }

   public static PyTuple utf_16_encode(String str) {
      return utf_16_encode(str, (String)null);
   }

   public static PyTuple utf_16_encode(String str, String errors) {
      return encode_tuple(encode_UTF16(str, errors, 0), str.length());
   }

   public static PyTuple utf_16_encode(String str, String errors, int byteorder) {
      return encode_tuple(encode_UTF16(str, errors, byteorder), str.length());
   }

   public static PyTuple utf_16_le_encode(String str) {
      return utf_16_le_encode(str, (String)null);
   }

   public static PyTuple utf_16_le_encode(String str, String errors) {
      return encode_tuple(encode_UTF16(str, errors, -1), str.length());
   }

   public static PyTuple utf_16_be_encode(String str) {
      return utf_16_be_encode(str, (String)null);
   }

   public static PyTuple utf_16_be_encode(String str, String errors) {
      return encode_tuple(encode_UTF16(str, errors, 1), str.length());
   }

   public static String encode_UTF16(String str, String errors, int byteorder) {
      Charset utf16;
      if (byteorder == 0) {
         utf16 = Charset.forName("UTF-16");
      } else if (byteorder == -1) {
         utf16 = Charset.forName("UTF-16LE");
      } else {
         utf16 = Charset.forName("UTF-16BE");
      }

      ByteBuffer bbuf = utf16.encode(str);

      StringBuilder v;
      int val;
      for(v = new StringBuilder(bbuf.limit()); bbuf.remaining() > 0; v.appendCodePoint(val)) {
         val = bbuf.get();
         if (val < 0) {
            val += 256;
         }
      }

      return v.toString();
   }

   public static PyTuple utf_16_decode(String str) {
      return utf_16_decode(str, (String)null);
   }

   public static PyTuple utf_16_decode(String str, String errors) {
      return utf_16_decode(str, errors, false);
   }

   public static PyTuple utf_16_decode(String str, String errors, boolean final_) {
      int[] bo = new int[]{0};
      int[] consumed = final_ ? null : new int[1];
      return decode_tuple(decode_UTF16(str, errors, bo, consumed), final_ ? str.length() : consumed[0]);
   }

   public static PyTuple utf_16_le_decode(String str) {
      return utf_16_le_decode(str, (String)null);
   }

   public static PyTuple utf_16_le_decode(String str, String errors) {
      return utf_16_le_decode(str, errors, false);
   }

   public static PyTuple utf_16_le_decode(String str, String errors, boolean final_) {
      int[] bo = new int[]{-1};
      int[] consumed = final_ ? null : new int[1];
      return decode_tuple(decode_UTF16(str, errors, bo, consumed), final_ ? str.length() : consumed[0]);
   }

   public static PyTuple utf_16_be_decode(String str) {
      return utf_16_be_decode(str, (String)null);
   }

   public static PyTuple utf_16_be_decode(String str, String errors) {
      return utf_16_be_decode(str, errors, false);
   }

   public static PyTuple utf_16_be_decode(String str, String errors, boolean final_) {
      int[] bo = new int[]{1};
      int[] consumed = final_ ? null : new int[1];
      return decode_tuple(decode_UTF16(str, errors, bo, consumed), final_ ? str.length() : consumed[0]);
   }

   public static PyTuple utf_16_ex_decode(String str) {
      return utf_16_ex_decode(str, (String)null);
   }

   public static PyTuple utf_16_ex_decode(String str, String errors) {
      return utf_16_ex_decode(str, errors, 0);
   }

   public static PyTuple utf_16_ex_decode(String str, String errors, int byteorder) {
      return utf_16_ex_decode(str, errors, byteorder, false);
   }

   public static PyTuple utf_16_ex_decode(String str, String errors, int byteorder, boolean final_) {
      int[] bo = new int[]{0};
      int[] consumed = final_ ? null : new int[1];
      String decoded = decode_UTF16(str, errors, bo, consumed);
      return new PyTuple(new PyObject[]{new PyUnicode(decoded), Py.newInteger(final_ ? str.length() : consumed[0]), Py.newInteger(bo[0])});
   }

   private static String decode_UTF16(String str, String errors, int[] byteorder) {
      return decode_UTF16(str, errors, byteorder, (int[])null);
   }

   private static String decode_UTF16(String str, String errors, int[] byteorder, int[] consumed) {
      int bo = 0;
      if (byteorder != null) {
         bo = byteorder[0];
      }

      int size = str.length();
      StringBuilder v = new StringBuilder(size / 2);

      int i;
      for(i = 0; i < size; i += 2) {
         char ch1 = str.charAt(i);
         if (i + 1 == size) {
            if (consumed != null) {
               break;
            }

            i = codecs.insertReplacementAndGetResume(v, errors, "utf-16", str, i, i + 1, "truncated data");
         } else {
            char ch2 = str.charAt(i + 1);
            if (ch1 == 254 && ch2 == 255) {
               bo = 1;
            } else if (ch1 == 255 && ch2 == 254) {
               bo = -1;
            } else {
               int W1;
               if (bo == -1) {
                  W1 = ch2 << 8 | ch1;
               } else {
                  W1 = ch1 << 8 | ch2;
               }

               if (W1 >= 55296 && W1 <= 57343) {
                  if (W1 >= 55296 && W1 <= 56319 && i < size - 1) {
                     i += 2;
                     char ch3 = str.charAt(i);
                     char ch4 = str.charAt(i + 1);
                     int W2;
                     if (bo == -1) {
                        W2 = ch4 << 8 | ch3;
                     } else {
                        W2 = ch3 << 8 | ch4;
                     }

                     if (W2 >= 56320 && W2 <= 57343) {
                        int U = ((W1 & 1023) << 10 | W2 & 1023) + 65536;
                        v.appendCodePoint(U);
                     } else {
                        i = codecs.insertReplacementAndGetResume(v, errors, "utf-16", str, i, i + 1, "illegal UTF-16 surrogate");
                     }
                  } else {
                     i = codecs.insertReplacementAndGetResume(v, errors, "utf-16", str, i, i + 1, "illegal encoding");
                  }
               } else {
                  v.appendCodePoint(W1);
               }
            }
         }
      }

      if (byteorder != null) {
         byteorder[0] = bo;
      }

      if (consumed != null) {
         consumed[0] = i;
      }

      return v.toString();
   }

   public static PyTuple utf_32_encode(String unicode) {
      return utf_32_encode(unicode, (String)null);
   }

   public static PyTuple utf_32_encode(String unicode, String errors) {
      return PyUnicode_EncodeUTF32(unicode, errors, _codecs.ByteOrder.UNDEFINED);
   }

   public static PyTuple utf_32_encode(String unicode, String errors, int byteorder) {
      ByteOrder order = _codecs.ByteOrder.fromInt(byteorder);
      return PyUnicode_EncodeUTF32(unicode, errors, order);
   }

   public static PyTuple utf_32_le_encode(String unicode) {
      return utf_32_le_encode(unicode, (String)null);
   }

   public static PyTuple utf_32_le_encode(String unicode, String errors) {
      return PyUnicode_EncodeUTF32(unicode, errors, _codecs.ByteOrder.LE);
   }

   public static PyTuple utf_32_be_encode(String unicode) {
      return utf_32_be_encode(unicode, (String)null);
   }

   public static PyTuple utf_32_be_encode(String unicode, String errors) {
      return PyUnicode_EncodeUTF32(unicode, errors, _codecs.ByteOrder.BE);
   }

   private static PyTuple PyUnicode_EncodeUTF32(String unicode, String errors, ByteOrder order) {
      StringBuilder v = new StringBuilder(4 * (unicode.length() + 1));
      int uptr = false;
      if (order == _codecs.ByteOrder.UNDEFINED) {
         v.append("\u0000\u0000þÿ");
         order = _codecs.ByteOrder.BE;
      }

      int uptr;
      if (order != _codecs.ByteOrder.LE) {
         uptr = PyUnicode_EncodeUTF32BELoop(v, unicode, errors);
      } else {
         uptr = PyUnicode_EncodeUTF32LELoop(v, unicode, errors);
      }

      return encode_tuple(v.toString(), uptr);
   }

   private static int PyUnicode_EncodeUTF32BELoop(StringBuilder v, String unicode, String errors) {
      int len = unicode.length();
      int uptr = 0;
      char[] buf = new char[6];

      while(uptr < len) {
         int ch = unicode.charAt(uptr++);
         if ((ch & '\uf800') == 55296) {
            if ((ch & 1024) == 0) {
               if (uptr < len) {
                  int ch2 = unicode.charAt(uptr++);
                  if ((ch2 & 'ﰀ') == 56320) {
                     ch = ((ch & 1023) << 10) + (ch2 & 1023) + 65536;
                     buf[3] = (char)(ch >> 16 & 255);
                     buf[4] = (char)(ch >> 8 & 255);
                     buf[5] = (char)(ch & 255);
                     v.append(buf, 2, 4);
                  } else {
                     uptr = PyUnicode_EncodeUTF32Error(v, errors, _codecs.ByteOrder.BE, unicode, uptr - 2, uptr - 1, "second surrogate missing");
                  }
               } else {
                  uptr = PyUnicode_EncodeUTF32Error(v, errors, _codecs.ByteOrder.BE, unicode, uptr - 1, len, "truncated data");
               }
            } else {
               uptr = PyUnicode_EncodeUTF32Error(v, errors, _codecs.ByteOrder.BE, unicode, uptr - 2, uptr - 1, "unexpected second surrogate");
            }
         } else if (ch > 255) {
            buf[3] = (char)(ch >> 8 & 255);
            buf[4] = (char)(ch & 255);
            v.append(buf, 1, 4);
         } else {
            buf[3] = (char)(ch & 255);
            v.append(buf, 0, 4);
         }
      }

      return uptr;
   }

   private static int PyUnicode_EncodeUTF32LELoop(StringBuilder v, String unicode, String errors) {
      int len = unicode.length();
      int uptr = 0;
      char[] buf = new char[6];

      while(uptr < len) {
         int ch = unicode.charAt(uptr++);
         if ((ch & '\uf800') == 55296) {
            if ((ch & 1024) == 0) {
               if (uptr < len) {
                  int ch2 = unicode.charAt(uptr++);
                  if ((ch2 & 'ﰀ') == 56320) {
                     ch = ((ch & 1023) << 10) + (ch2 & 1023) + 65536;
                     buf[0] = (char)(ch & 255);
                     buf[1] = (char)(ch >> 8 & 255);
                     buf[2] = (char)(ch >> 16 & 255);
                     v.append(buf, 0, 4);
                  } else {
                     uptr = PyUnicode_EncodeUTF32Error(v, errors, _codecs.ByteOrder.LE, unicode, uptr - 2, uptr - 1, "second surrogate missing");
                  }
               } else {
                  uptr = PyUnicode_EncodeUTF32Error(v, errors, _codecs.ByteOrder.LE, unicode, uptr - 1, len, "truncated data");
               }
            } else {
               uptr = PyUnicode_EncodeUTF32Error(v, errors, _codecs.ByteOrder.LE, unicode, uptr - 2, uptr - 1, "unexpected second surrogate");
            }
         } else if (ch > 255) {
            buf[1] = (char)(ch & 255);
            buf[2] = (char)(ch >> 8 & 255);
            v.append(buf, 1, 4);
         } else {
            buf[2] = (char)(ch & 255);
            v.append(buf, 2, 4);
         }
      }

      return uptr;
   }

   private static int PyUnicode_EncodeUTF32Error(StringBuilder v, String errors, ByteOrder order, String toEncode, int start, int end, String reason) {
      if (errors != null) {
         if (errors.equals("ignore")) {
            return end;
         }

         if (errors.equals("replace")) {
            for(int i = start; i < end; ++i) {
               if (order != _codecs.ByteOrder.LE) {
                  v.append("\u0000\u0000\u0000?");
               } else {
                  v.append("?\u0000\u0000\u0000");
               }
            }

            return end;
         }
      }

      PyObject replacementSpec = codecs.encoding_error(errors, "utf-32", toEncode, start, end, reason);
      String u = replacementSpec.__getitem__(0).toString();
      PyUnicode_EncodeUTF32BELoop(v, u, errors);
      return codecs.calcNewPosition(toEncode.length(), replacementSpec);
   }

   public static PyTuple utf_32_decode(String bytes) {
      return utf_32_decode(bytes, (String)null);
   }

   public static PyTuple utf_32_decode(String bytes, String errors) {
      return utf_32_decode(bytes, errors, false);
   }

   public static PyTuple utf_32_decode(String bytes, String errors, boolean isFinal) {
      return PyUnicode_DecodeUTF32Stateful(bytes, errors, _codecs.ByteOrder.UNDEFINED, isFinal, false);
   }

   public static PyTuple utf_32_le_decode(String bytes) {
      return utf_32_le_decode(bytes, (String)null);
   }

   public static PyTuple utf_32_le_decode(String bytes, String errors) {
      return utf_32_le_decode(bytes, errors, false);
   }

   public static PyTuple utf_32_le_decode(String bytes, String errors, boolean isFinal) {
      return PyUnicode_DecodeUTF32Stateful(bytes, errors, _codecs.ByteOrder.LE, isFinal, false);
   }

   public static PyTuple utf_32_be_decode(String bytes) {
      return utf_32_be_decode(bytes, (String)null);
   }

   public static PyTuple utf_32_be_decode(String bytes, String errors) {
      return utf_32_be_decode(bytes, errors, false);
   }

   public static PyTuple utf_32_be_decode(String bytes, String errors, boolean isFinal) {
      return PyUnicode_DecodeUTF32Stateful(bytes, errors, _codecs.ByteOrder.BE, isFinal, false);
   }

   public static PyTuple utf_32_ex_decode(String bytes, String errors, int byteorder) {
      return utf_32_ex_decode(bytes, errors, byteorder, false);
   }

   public static PyTuple utf_32_ex_decode(String bytes, String errors, int byteorder, boolean isFinal) {
      ByteOrder order = _codecs.ByteOrder.fromInt(byteorder);
      return PyUnicode_DecodeUTF32Stateful(bytes, errors, order, isFinal, true);
   }

   private static PyTuple PyUnicode_DecodeUTF32Stateful(String bytes, String errors, ByteOrder order, boolean isFinal, boolean findOrder) {
      int size = bytes.length();
      int limit = size & -4;
      StringBuilder unicode = new StringBuilder(1 + limit / 4);
      int q = 0;
      if (limit > 0) {
         if (order == _codecs.ByteOrder.UNDEFINED) {
            char a = bytes.charAt(q);
            if (a == 255) {
               if (bytes.charAt(q + 1) == 254 && bytes.charAt(q + 2) == 0 && bytes.charAt(q + 3) == 0) {
                  order = _codecs.ByteOrder.LE;
                  q += 4;
               }
            } else if (a == 0 && bytes.charAt(q + 1) == 0 && bytes.charAt(q + 2) == 254 && bytes.charAt(q + 3) == 255) {
               order = _codecs.ByteOrder.BE;
               q += 4;
            }
         }

         if (order != _codecs.ByteOrder.LE) {
            q = PyUnicode_DecodeUTF32BELoop(unicode, bytes, q, limit, errors);
         } else {
            q = PyUnicode_DecodeUTF32LELoop(unicode, bytes, q, limit, errors);
         }
      }

      if (isFinal && q < size) {
         q = codecs.insertReplacementAndGetResume(unicode, errors, "utf-32", bytes, q, size, "truncated data");
      }

      return findOrder ? decode_tuple(unicode.toString(), q, order) : decode_tuple(unicode.toString(), q);
   }

   private static int PyUnicode_DecodeUTF32BELoop(StringBuilder unicode, String bytes, int q, int limit, String errors) {
      while(q < limit) {
         int hi = bytes.charAt(q) << 8 | bytes.charAt(q + 1);
         int lo = bytes.charAt(q + 2) << 8 | bytes.charAt(q + 3);
         if (hi == 0) {
            unicode.append((char)lo);
            q += 4;
         } else {
            try {
               unicode.appendCodePoint((hi << 16) + lo);
               q += 4;
            } catch (IllegalArgumentException var8) {
               q = codecs.insertReplacementAndGetResume(unicode, errors, "utf-32", bytes, q, q + 4, "codepoint not in range(0x110000)");
            }
         }
      }

      return q;
   }

   private static int PyUnicode_DecodeUTF32LELoop(StringBuilder unicode, String bytes, int q, int limit, String errors) {
      while(q < limit) {
         int hi = bytes.charAt(q + 3) << 8 | bytes.charAt(q + 2);
         int lo = bytes.charAt(q + 1) << 8 | bytes.charAt(q);
         if (hi == 0) {
            unicode.append((char)lo);
            q += 4;
         } else {
            try {
               unicode.appendCodePoint((hi << 16) + lo);
               q += 4;
            } catch (IllegalArgumentException var8) {
               q = codecs.insertReplacementAndGetResume(unicode, errors, "utf-32", bytes, q, q + 4, "codepoint not in range(0x110000)");
            }
         }
      }

      return q;
   }

   public static PyTuple raw_unicode_escape_encode(String str) {
      return raw_unicode_escape_encode(str, (String)null);
   }

   public static PyTuple raw_unicode_escape_encode(String str, String errors) {
      return encode_tuple(codecs.PyUnicode_EncodeRawUnicodeEscape(str, errors, false), str.length());
   }

   public static PyTuple raw_unicode_escape_decode(String str) {
      return raw_unicode_escape_decode(str, (String)null);
   }

   public static PyTuple raw_unicode_escape_decode(String str, String errors) {
      return decode_tuple(codecs.PyUnicode_DecodeRawUnicodeEscape(str, errors), str.length());
   }

   public static PyTuple unicode_escape_encode(String str) {
      return unicode_escape_encode(str, (String)null);
   }

   public static PyTuple unicode_escape_encode(String str, String errors) {
      return encode_tuple(PyString.encode_UnicodeEscape(str, false), str.length());
   }

   public static PyTuple unicode_escape_decode(String str) {
      return unicode_escape_decode(str, (String)null);
   }

   public static PyTuple unicode_escape_decode(String str, String errors) {
      int n = str.length();
      return decode_tuple(PyString.decode_UnicodeEscape(str, 0, n, errors, true), n);
   }

   /** @deprecated */
   @Deprecated
   public static PyTuple unicode_internal_encode(String unicode) {
      return utf_32_be_encode(unicode, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public static PyTuple unicode_internal_encode(String unicode, String errors) {
      return utf_32_be_encode(unicode, errors);
   }

   /** @deprecated */
   @Deprecated
   public static PyTuple unicode_internal_decode(String bytes) {
      return utf_32_be_decode(bytes, (String)null, true);
   }

   /** @deprecated */
   @Deprecated
   public static PyTuple unicode_internal_decode(String bytes, String errors) {
      return utf_32_be_decode(bytes, errors, true);
   }

   @Untraversable
   @ExposedType(
      name = "EncodingMap",
      isBaseType = false
   )
   public static class EncodingMap extends PyObject {
      char[] level1;
      char[] level23;
      int count2;
      int count3;

      private EncodingMap(char[] level1, char[] level23, int count2, int count3) {
         this.level1 = level1;
         this.level23 = level23;
         this.count2 = count2;
         this.count3 = count3;
      }

      public static PyObject buildEncodingMap(PyObject string) {
         if (string instanceof PyUnicode && string.__len__() == 256) {
            boolean needDict = false;
            char[] level1 = new char[32];
            char[] level23 = new char[512];
            int count2 = 0;
            int count3 = 0;
            String decode = string.toString();

            int i;
            for(i = 0; i < level1.length; ++i) {
               level1[i] = 255;
            }

            for(i = 0; i < level23.length; ++i) {
               level23[i] = 255;
            }

            if (decode.charAt(0) != 0) {
               needDict = true;
            }

            int length2;
            int length3;
            for(i = 1; i < 256; ++i) {
               char charAt = decode.charAt(i);
               if (charAt == 0) {
                  needDict = true;
               }

               if (charAt != '\ufffe') {
                  length2 = charAt >> 11;
                  length3 = charAt >> 7;
                  if (level1[length2] == 255) {
                     level1[length2] = (char)(count2++);
                  }

                  if (level23[length3] == 255) {
                     level23[length3] = (char)(count3++);
                  }
               }
            }

            if (count2 > 255 || count3 > 255) {
               needDict = true;
            }

            if (needDict) {
               PyObject result = new PyDictionary();

               for(i = 0; i < 256; ++i) {
                  result.__setitem__((PyObject)Py.newInteger(decode.charAt(i)), Py.newInteger(i));
               }

               return result;
            } else {
               length2 = 16 * count2;
               length3 = 128 * count3;
               level23 = new char[length2 + length3];
               PyObject result = new EncodingMap(level1, level23, count2, count3);

               for(i = 0; i < length2; ++i) {
                  level23[i] = 255;
               }

               for(i = length2; i < length2 + length3; ++i) {
                  level23[i] = 0;
               }

               count3 = 0;

               for(i = 1; i < 256; ++i) {
                  char charAt = decode.charAt(i);
                  if (charAt != '\ufffe') {
                     int o1 = charAt >> 11;
                     int o2 = charAt >> 7 & 15;
                     int i2 = 16 * level1[o1] + o2;
                     if (level23[i2] == 255) {
                        level23[i2] = (char)(count3++);
                     }

                     int o3 = charAt & 127;
                     int i3 = 128 * level23[i2] + o3;
                     level23[length2 + i3] = (char)i;
                  }
               }

               return result;
            }
         } else {
            throw Py.TypeError("bad argument type for built-in operation");
         }
      }

      public int lookup(char c) {
         int l1 = c >> 11;
         int l2 = c >> 7 & 15;
         int l3 = c & 127;
         if (c == 0) {
            return 0;
         } else {
            int i = this.level1[l1];
            if (i == 255) {
               return -1;
            } else {
               i = this.level23[16 * i + l2];
               if (i == 255) {
                  return -1;
               } else {
                  i = this.level23[16 * this.count2 + 128 * i + l3];
                  return i == 0 ? -1 : i;
               }
            }
         }
      }

      static {
         PyType.addBuilder(EncodingMap.class, new PyExposer());
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("EncodingMap", EncodingMap.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
         }
      }
   }

   static enum ByteOrder {
      LE,
      UNDEFINED,
      BE;

      int code() {
         return this.ordinal() - 1;
      }

      static ByteOrder fromInt(int byteorder) {
         switch (byteorder) {
            case -1:
               return LE;
            case 1:
               return BE;
            default:
               return UNDEFINED;
         }
      }
   }
}
