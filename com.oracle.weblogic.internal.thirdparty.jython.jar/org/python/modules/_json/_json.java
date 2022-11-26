package org.python.modules._json;

import java.util.Iterator;
import org.python.core.ArgParser;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyBuiltinFunctionNarrow;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.Untraversable;
import org.python.core.__builtin__;
import org.python.core.codecs;

public class _json implements ClassDictInit {
   public static final PyString __doc__ = new PyString("Port of _json C module.");
   public static final PyObject module = Py.newString("_json");
   private static PyObject errmsg_fn;

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", new PyString("_json"));
      dict.__setitem__((String)"__doc__", __doc__);
      dict.__setitem__((String)"encode_basestring_ascii", new EncodeBasestringAsciiFunction());
      dict.__setitem__((String)"make_encoder", Encoder.TYPE);
      dict.__setitem__((String)"make_scanner", Scanner.TYPE);
      dict.__setitem__((String)"scanstring", new ScanstringFunction());
      dict.__setitem__((String)"__module__", new PyString("_json"));
      Encoder.TYPE.setName("_json.Encoder");
      Scanner.TYPE.setName("_json.Scanner");
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }

   private static synchronized PyObject get_errmsg_fn() {
      if (errmsg_fn == null) {
         PyObject json = __builtin__.__import__("json");
         if (json != null) {
            PyObject decoder = json.__findattr__("decoder");
            if (decoder != null) {
               errmsg_fn = decoder.__findattr__("errmsg");
            }
         }
      }

      return errmsg_fn;
   }

   static void raise_errmsg(String msg, PyObject s) {
      raise_errmsg(msg, s, Py.None, Py.None);
   }

   static void raise_errmsg(String msg, PyObject s, int pos) {
      raise_errmsg(msg, s, Py.newInteger(pos), Py.None);
   }

   static void raise_errmsg(String msg, PyObject s, PyObject pos, PyObject end) {
      PyObject errmsg_fn = get_errmsg_fn();
      if (errmsg_fn != null) {
         throw Py.ValueError(errmsg_fn.__call__((PyObject)Py.newString(msg), s, (PyObject)pos, (PyObject)end).asString());
      } else {
         throw Py.ValueError(msg);
      }
   }

   static PyTuple scanstring(PyString pystr, int end, String encoding, boolean strict) {
      int len = pystr.__len__();
      int begin = end - 1;
      if (end >= 0 && len > end) {
         PyList chunks = new PyList();

         while(true) {
            int c = 0;

            int next;
            for(next = end; next < len; ++next) {
               c = pystr.getInt(next);
               if (c == 34 || c == 92) {
                  break;
               }

               if (strict && c <= 31) {
                  raise_errmsg("Invalid control character at", pystr, next);
               }
            }

            if (c != 34 && c != 92) {
               raise_errmsg("Unterminated string starting at", pystr, begin);
            }

            if (next != end) {
               PyString strchunk = (PyString)pystr.__getslice__(Py.newInteger(end), Py.newInteger(next));
               if (strchunk instanceof PyUnicode) {
                  chunks.append(strchunk);
               } else {
                  chunks.append(codecs.decode(strchunk, encoding, (String)null));
               }
            }

            ++next;
            if (c == 34) {
               return new PyTuple(new PyObject[]{Py.EmptyUnicode.join(chunks), Py.newInteger(next)});
            }

            if (next == len) {
               raise_errmsg("Unterminated string starting at", pystr, begin);
            }

            c = pystr.getInt(next);
            if (c != 117) {
               end = next + 1;
               switch (c) {
                  case 34:
                  case 47:
                  case 92:
                     break;
                  case 98:
                     c = 8;
                     break;
                  case 102:
                     c = 12;
                     break;
                  case 110:
                     c = 10;
                     break;
                  case 114:
                     c = 13;
                     break;
                  case 116:
                     c = 9;
                     break;
                  default:
                     c = 0;
               }

               if (c == 0) {
                  raise_errmsg("Invalid \\escape", pystr, end - 2);
               }
            } else {
               c = 0;
               ++next;
               end = next + 4;
               if (end >= len) {
                  raise_errmsg("Invalid \\uXXXX escape", pystr, next - 1);
               }

               int c2;
               for(; next < end; ++next) {
                  c2 = pystr.getInt(next);
                  c <<= 4;
                  switch (c2) {
                     case 48:
                     case 49:
                     case 50:
                     case 51:
                     case 52:
                     case 53:
                     case 54:
                     case 55:
                     case 56:
                     case 57:
                        c |= c2 - 48;
                        break;
                     case 58:
                     case 59:
                     case 60:
                     case 61:
                     case 62:
                     case 63:
                     case 64:
                     case 71:
                     case 72:
                     case 73:
                     case 74:
                     case 75:
                     case 76:
                     case 77:
                     case 78:
                     case 79:
                     case 80:
                     case 81:
                     case 82:
                     case 83:
                     case 84:
                     case 85:
                     case 86:
                     case 87:
                     case 88:
                     case 89:
                     case 90:
                     case 91:
                     case 92:
                     case 93:
                     case 94:
                     case 95:
                     case 96:
                     default:
                        raise_errmsg("Invalid \\uXXXX escape", pystr, end - 5);
                        break;
                     case 65:
                     case 66:
                     case 67:
                     case 68:
                     case 69:
                     case 70:
                        c |= c2 - 65 + 10;
                        break;
                     case 97:
                     case 98:
                     case 99:
                     case 100:
                     case 101:
                     case 102:
                        c |= c2 - 97 + 10;
                  }
               }

               if ((c & 'ﰀ') == 55296) {
                  c2 = 0;
                  if (end + 6 >= len) {
                     raise_errmsg("Unpaired high surrogate", pystr, end - 5);
                  }

                  if (pystr.getInt(next++) != 92 || pystr.getInt(next++) != 117) {
                     raise_errmsg("Unpaired high surrogate", pystr, end - 5);
                  }

                  for(end += 6; next < end; ++next) {
                     int digit = pystr.getInt(next);
                     c2 <<= 4;
                     switch (digit) {
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                           c2 |= digit - 48;
                           break;
                        case 58:
                        case 59:
                        case 60:
                        case 61:
                        case 62:
                        case 63:
                        case 64:
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                        case 76:
                        case 77:
                        case 78:
                        case 79:
                        case 80:
                        case 81:
                        case 82:
                        case 83:
                        case 84:
                        case 85:
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                        case 92:
                        case 93:
                        case 94:
                        case 95:
                        case 96:
                        default:
                           raise_errmsg("Invalid \\uXXXX escape", pystr, end - 5);
                           break;
                        case 65:
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                           c2 |= digit - 65 + 10;
                           break;
                        case 97:
                        case 98:
                        case 99:
                        case 100:
                        case 101:
                        case 102:
                           c2 |= digit - 97 + 10;
                     }
                  }

                  if ((c2 & 'ﰀ') != 56320) {
                     raise_errmsg("Unpaired high surrogate", pystr, end - 5);
                  }

                  c = 65536 + (c - '\ud800' << 10 | c2 - '\udc00');
               } else if ((c & 'ﰀ') == 56320) {
                  raise_errmsg("Unpaired low surrogate", pystr, end - 5);
               }
            }

            chunks.append(new PyUnicode(c));
         }
      } else {
         throw Py.ValueError("end is out of bounds");
      }
   }

   static PyString encode_basestring_ascii(PyObject pystr) {
      if (pystr instanceof PyUnicode) {
         return ascii_escape((PyUnicode)pystr);
      } else if (pystr instanceof PyString) {
         return ascii_escape((PyString)pystr);
      } else {
         throw Py.TypeError(String.format("first argument must be a string, not %.80s", pystr.getType().fastGetName()));
      }
   }

   private static PyString ascii_escape(PyUnicode pystr) {
      StringBuilder rval = new StringBuilder(pystr.__len__());
      rval.append("\"");
      Iterator iter = pystr.newSubsequenceIterator();

      while(iter.hasNext()) {
         _write_char(rval, (Integer)iter.next());
      }

      rval.append("\"");
      return new PyString(rval.toString());
   }

   private static PyString ascii_escape(PyString pystr) {
      int len = pystr.__len__();
      String s = pystr.getString();
      StringBuilder rval = new StringBuilder(len);
      rval.append("\"");

      for(int i = 0; i < len; ++i) {
         int c = s.charAt(i);
         if (c > 127) {
            return ascii_escape(new PyUnicode(codecs.PyUnicode_DecodeUTF8(s, (String)null)));
         }

         _write_char(rval, c);
      }

      rval.append("\"");
      return new PyString(rval.toString());
   }

   private static void _write_char(StringBuilder builder, int c) {
      if (c >= 32 && c <= 126 && c != 92 & c != 34) {
         builder.append((char)c);
      } else {
         _ascii_escape_char(builder, c);
      }

   }

   private static void _write_hexchar(StringBuilder builder, int c) {
      builder.append("0123456789abcdef".charAt(c & 15));
   }

   private static void _ascii_escape_char(StringBuilder builder, int c) {
      builder.append('\\');
      switch (c) {
         case 8:
            builder.append('b');
            break;
         case 9:
            builder.append('t');
            break;
         case 10:
            builder.append('n');
            break;
         case 12:
            builder.append('f');
            break;
         case 13:
            builder.append('r');
            break;
         case 34:
            builder.append((char)c);
            break;
         case 92:
            builder.append((char)c);
            break;
         default:
            if (c >= 65536) {
               int v = c - 65536;
               c = '\ud800' | v >> 10 & 1023;
               builder.append('u');
               _write_hexchar(builder, c >> 12);
               _write_hexchar(builder, c >> 8);
               _write_hexchar(builder, c >> 4);
               _write_hexchar(builder, c);
               c = '\udc00' | v & 1023;
               builder.append('\\');
            }

            builder.append('u');
            _write_hexchar(builder, c >> 12);
            _write_hexchar(builder, c >> 8);
            _write_hexchar(builder, c >> 4);
            _write_hexchar(builder, c);
      }

   }

   @Untraversable
   static class EncodeBasestringAsciiFunction extends PyBuiltinFunctionNarrow {
      EncodeBasestringAsciiFunction() {
         super("encode_basestring_ascii", 1, 1, "encode_basestring_ascii");
      }

      public PyObject getModule() {
         return _json.module;
      }

      public PyObject __call__(PyObject pystr) {
         return _json.encode_basestring_ascii(pystr);
      }
   }

   @Untraversable
   static class ScanstringFunction extends PyBuiltinFunctionNarrow {
      ScanstringFunction() {
         super("scanstring", 2, 4, "scanstring");
      }

      public PyObject getModule() {
         return _json.module;
      }

      public PyObject __call__(PyObject s, PyObject end) {
         return this.__call__(s, end, new PyString("utf-8"), Py.True);
      }

      public PyObject __call__(PyObject s, PyObject end, PyObject encoding) {
         return this.__call__(s, end, encoding, Py.True);
      }

      public PyObject __call__(PyObject[] args, String[] kwds) {
         ArgParser ap = new ArgParser("scanstring", args, kwds, new String[]{"s", "end", "encoding", "strict"}, 2);
         return this.__call__(ap.getPyObject(0), ap.getPyObject(1), ap.getPyObject(2, new PyString("utf-8")), ap.getPyObject(3, Py.True));
      }

      public PyObject __call__(PyObject s, PyObject end, PyObject encoding, PyObject strict) {
         int end_idx = end.asIndex(Py.OverflowError);
         boolean is_strict = strict.__nonzero__();
         if (s instanceof PyString) {
            return _json.scanstring((PyString)s, end_idx, encoding == Py.None ? null : encoding.toString(), is_strict);
         } else {
            throw Py.TypeError(String.format("first argument must be a string, not %.80s", s.getType().fastGetName()));
         }
      }
   }
}
