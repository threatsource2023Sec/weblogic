package org.python.modules;

import java.util.regex.Pattern;
import org.python.core.ArgParser;
import org.python.core.BufferProtocol;
import org.python.core.Py;
import org.python.core.PyBuffer;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyTuple;
import org.python.core.util.StringUtil;

public class binascii {
   public static String __doc__ = "Conversion between binary data and ASCII";
   public static final PyObject Error;
   public static final PyObject Incomplete;
   private static char RUNCHAR;
   private static short DONE;
   private static short SKIP;
   private static short FAIL;
   private static short[] table_a2b_hqx;
   private static byte[] table_b2a_hqx;
   private static short[] table_a2b_base64;
   private static char BASE64_PAD;
   private static int BASE64_MAXBIN;
   private static byte[] table_b2a_base64;
   private static int[] crctab_hqx;
   public static PyString __doc__a2b_uu;
   public static PyString __doc__b2a_uu;
   public static PyString __doc__a2b_base64;
   public static PyString __doc__b2a_base64;
   public static PyString __doc__a2b_hqx;
   public static PyString __doc__rlecode_hqx;
   public static PyString __doc__b2a_hqx;
   public static PyString __doc__rledecode_hqx;
   public static PyString __doc__crc_hqx;
   static long[] crc_32_tab;
   private static char[] hexdigit;
   public static PyString __doc__b2a_hex;
   public static PyString a2b_hex$doc;
   private static final char[] upper_hexdigit;
   private static final Pattern UNDERSCORE;
   public static final PyString __doc__a2b_qp;
   private static final Pattern RN_TO_N;
   private static final Pattern N_TO_RN;
   public static final PyString __doc__b2a_qp;

   public static PyObject exceptionNamespace() {
      PyObject dict = new PyStringMap();
      dict.__setitem__((String)"__module__", new PyString("binascii"));
      return dict;
   }

   public static PyString a2b_uu(BufferProtocol bp) {
      int leftbits = 0;
      int leftchar = 0;
      StringBuilder bin_data = new StringBuilder();
      PyBuffer ascii_data = bp.getBuffer(0);

      PyString var5;
      try {
         if (ascii_data.getLen() != 0) {
            int ascii_len = ascii_data.getLen() - 1;
            int bin_len = ascii_data.intAt(0) - 32 & 63;

            int i;
            char this_ch;
            for(i = 0; bin_len > 0 && ascii_len > 0; --ascii_len) {
               this_ch = (char)ascii_data.intAt(i + 1);
               if (this_ch != '\n' && this_ch != '\r' && ascii_len > 0) {
                  if (this_ch < ' ' || this_ch > '`') {
                     throw new PyException(Error, "Illegal char");
                  }

                  this_ch = (char)(this_ch - 32 & 63);
               } else {
                  this_ch = 0;
               }

               leftchar = leftchar << 6 | this_ch;
               leftbits += 6;
               if (leftbits >= 8) {
                  leftbits -= 8;
                  bin_data.append((char)(leftchar >> leftbits & 255));
                  leftchar &= (1 << leftbits) - 1;
                  --bin_len;
               }

               ++i;
            }

            while(ascii_len-- > 0) {
               ++i;
               this_ch = (char)ascii_data.intAt(i);
               if (this_ch != ' ' && this_ch != '@' && this_ch != '\n' && this_ch != '\r') {
                  throw new PyException(Error, "Trailing garbage");
               }
            }

            while(i < bin_len) {
               bin_data.append('\u0000');
               ++i;
            }

            return new PyString(bin_data.toString());
         }

         var5 = new PyString("");
      } finally {
         ascii_data.release();
      }

      return var5;
   }

   public static PyString b2a_uu(BufferProtocol bp) {
      int leftbits = 0;
      int leftchar = 0;
      PyBuffer bin_data = bp.getBuffer(0);
      StringBuilder ascii_data = new StringBuilder();

      try {
         int bin_len = bin_data.getLen();
         if (bin_len > 45) {
            throw new PyException(Error, "At most 45 bytes at once");
         }

         ascii_data.append((char)(32 + (bin_len & 63)));

         for(int i = 0; bin_len > 0 || leftbits != 0; --bin_len) {
            if (bin_len > 0) {
               leftchar = leftchar << 8 | (char)bin_data.intAt(i);
            } else {
               leftchar <<= 8;
            }

            leftbits += 8;

            while(leftbits >= 6) {
               char this_ch = (char)(leftchar >> leftbits - 6 & 63);
               leftbits -= 6;
               ascii_data.append((char)(this_ch + 32));
            }

            ++i;
         }
      } finally {
         bin_data.release();
      }

      ascii_data.append('\n');
      return new PyString(ascii_data.toString());
   }

   private static int binascii_find_valid(PyBuffer b, int offset, int num) {
      int blen = b.getLen() - offset;

      int ret;
      for(ret = -1; blen > 0 && ret == -1; --blen) {
         int c = b.intAt(offset);
         short b64val = table_a2b_base64[c & 127];
         if (c <= 127 && b64val != -1) {
            if (num == 0) {
               ret = c;
            }

            --num;
         }

         ++offset;
      }

      return ret;
   }

   public static PyString a2b_base64(BufferProtocol bp) {
      int leftbits = 0;
      int leftchar = 0;
      int quad_pos = 0;
      PyBuffer ascii_data = bp.getBuffer(0);
      int ascii_len = ascii_data.getLen();
      int bin_len = 0;
      StringBuilder bin_data = new StringBuilder();

      try {
         for(int i = 0; ascii_len > 0; ++i) {
            char this_ch = (char)ascii_data.intAt(i);
            if (this_ch <= 127 && this_ch != '\r' && this_ch != '\n' && this_ch != ' ') {
               if (this_ch == BASE64_PAD) {
                  if (quad_pos >= 2 && (quad_pos != 2 || binascii_find_valid(ascii_data, i, 1) == BASE64_PAD)) {
                     leftbits = 0;
                     break;
                  }
               } else {
                  short this_v = table_a2b_base64[this_ch];
                  if (this_v != -1) {
                     quad_pos = quad_pos + 1 & 3;
                     leftchar = leftchar << 6 | this_v;
                     leftbits += 6;
                     if (leftbits >= 8) {
                        leftbits -= 8;
                        bin_data.append((char)(leftchar >> leftbits & 255));
                        ++bin_len;
                        leftchar &= (1 << leftbits) - 1;
                     }
                  }
               }
            }

            --ascii_len;
         }
      } finally {
         ascii_data.release();
      }

      if (leftbits != 0) {
         throw new PyException(Error, "Incorrect padding");
      } else {
         return new PyString(bin_data.toString());
      }
   }

   public static PyString b2a_base64(BufferProtocol bp) {
      int leftbits = 0;
      int leftchar = 0;
      StringBuilder ascii_data = new StringBuilder();
      PyBuffer bin_data = bp.getBuffer(0);

      try {
         int bin_len = bin_data.getLen();
         if (bin_len > BASE64_MAXBIN) {
            throw new PyException(Error, "Too much data for base64 line");
         }

         for(int i = 0; bin_len > 0; ++i) {
            leftchar = leftchar << 8 | (char)bin_data.intAt(i);
            leftbits += 8;

            while(leftbits >= 6) {
               char this_ch = (char)(leftchar >> leftbits - 6 & 63);
               leftbits -= 6;
               ascii_data.append((char)table_b2a_base64[this_ch]);
            }

            --bin_len;
         }
      } finally {
         bin_data.release();
      }

      if (leftbits == 2) {
         ascii_data.append((char)table_b2a_base64[(leftchar & 3) << 4]);
         ascii_data.append(BASE64_PAD);
         ascii_data.append(BASE64_PAD);
      } else if (leftbits == 4) {
         ascii_data.append((char)table_b2a_base64[(leftchar & 15) << 2]);
         ascii_data.append(BASE64_PAD);
      }

      ascii_data.append('\n');
      return new PyString(ascii_data.toString());
   }

   public static PyTuple a2b_hqx(BufferProtocol bp) {
      int leftbits = 0;
      int leftchar = 0;
      boolean done = false;
      PyBuffer ascii_data = bp.getBuffer(0);
      int len = ascii_data.getLen();
      StringBuilder bin_data = new StringBuilder();

      try {
         for(int i = 0; len > 0; ++i) {
            char this_ch = (char)table_a2b_hqx[ascii_data.intAt(i)];
            if (this_ch != SKIP) {
               if (this_ch == FAIL) {
                  throw new PyException(Error, "Illegal char");
               }

               if (this_ch == DONE) {
                  done = true;
                  break;
               }

               leftchar = leftchar << 6 | this_ch;
               leftbits += 6;
               if (leftbits >= 8) {
                  leftbits -= 8;
                  bin_data.append((char)(leftchar >> leftbits & 255));
                  leftchar &= (1 << leftbits) - 1;
               }
            }

            --len;
         }
      } finally {
         ascii_data.release();
      }

      if (leftbits != 0 && !done) {
         throw new PyException(Incomplete, "String has incomplete number of bytes");
      } else {
         return new PyTuple(new PyObject[]{new PyString(bin_data.toString()), Py.newInteger(done ? 1 : 0)});
      }
   }

   public static PyString rlecode_hqx(BufferProtocol bp) {
      PyBuffer in_data = bp.getBuffer(0);
      int len = in_data.getLen();
      StringBuilder out_data = new StringBuilder();

      try {
         for(int in = 0; in < len; ++in) {
            char ch = (char)in_data.intAt(in);
            if (ch == RUNCHAR) {
               out_data.append(RUNCHAR);
               out_data.append('\u0000');
            } else {
               int inend;
               for(inend = in + 1; inend < len && (char)in_data.intAt(inend) == ch && inend < in + 255; ++inend) {
               }

               if (inend - in > 3) {
                  out_data.append(ch);
                  out_data.append(RUNCHAR);
                  out_data.append((char)(inend - in));
                  in = inend - 1;
               } else {
                  out_data.append(ch);
               }
            }
         }
      } finally {
         in_data.release();
      }

      return new PyString(out_data.toString());
   }

   public static PyString b2a_hqx(BufferProtocol bp) {
      int leftbits = 0;
      int leftchar = 0;
      PyBuffer bin_data = bp.getBuffer(0);
      int len = bin_data.getLen();
      StringBuilder ascii_data = new StringBuilder();

      try {
         for(int i = 0; len > 0; ++i) {
            leftchar = leftchar << 8 | (char)bin_data.intAt(i);
            leftbits += 8;

            while(leftbits >= 6) {
               char this_ch = (char)(leftchar >> leftbits - 6 & 63);
               leftbits -= 6;
               ascii_data.append((char)table_b2a_hqx[this_ch]);
            }

            --len;
         }
      } finally {
         bin_data.release();
      }

      if (leftbits != 0) {
         leftchar <<= 6 - leftbits;
         ascii_data.append((char)table_b2a_hqx[leftchar & 63]);
      }

      return new PyString(ascii_data.toString());
   }

   public static PyString rledecode_hqx(BufferProtocol bp) {
      PyBuffer in_data = bp.getBuffer(0);
      int in_len = in_data.getLen();
      int i = 0;
      StringBuilder out_data = new StringBuilder();

      PyString var5;
      try {
         if (in_len != 0) {
            --in_len;
            if (in_len < 0) {
               throw new PyException(Incomplete);
            }

            char in_byte = (char)in_data.intAt(i++);
            char in_repeat;
            if (in_byte == RUNCHAR) {
               --in_len;
               if (in_len < 0) {
                  throw new PyException(Incomplete);
               }

               in_repeat = (char)in_data.intAt(i++);
               if (in_repeat != 0) {
                  throw new PyException(Error, "Orphaned RLE code at start");
               }

               out_data.append(RUNCHAR);
            } else {
               out_data.append(in_byte);
            }

            while(in_len > 0) {
               --in_len;
               if (in_len < 0) {
                  throw new PyException(Incomplete);
               }

               in_byte = (char)in_data.intAt(i++);
               if (in_byte == RUNCHAR) {
                  --in_len;
                  if (in_len < 0) {
                     throw new PyException(Incomplete);
                  }

                  in_repeat = (char)in_data.intAt(i++);
                  if (in_repeat == 0) {
                     out_data.append(RUNCHAR);
                  } else {
                     in_byte = out_data.charAt(out_data.length() - 1);

                     while(true) {
                        --in_repeat;
                        if (in_repeat <= 0) {
                           break;
                        }

                        out_data.append(in_byte);
                     }
                  }
               } else {
                  out_data.append(in_byte);
               }
            }

            return new PyString(out_data.toString());
         }

         var5 = Py.EmptyString;
      } finally {
         in_data.release();
      }

      return var5;
   }

   public static int crc_hqx(BufferProtocol bp, int crc) {
      PyBuffer bin_data = bp.getBuffer(0);
      int len = bin_data.getLen();
      int i = 0;

      try {
         while(len-- > 0) {
            crc = crc << 8 & '\uff00' ^ crctab_hqx[crc >> 8 & 255 ^ (char)bin_data.intAt(i++)];
         }
      } finally {
         bin_data.release();
      }

      return crc;
   }

   public static int crc32(BufferProtocol bp) {
      return crc32(bp, 0L);
   }

   public static int crc32(BufferProtocol bp, long crc) {
      PyBuffer bin_data = bp.getBuffer(0);
      int len = bin_data.getLen();
      crc &= 4294967295L;
      crc ^= 4294967295L;

      try {
         for(int i = 0; i < len; ++i) {
            char ch = (char)bin_data.intAt(i);
            crc = (long)((int)crc_32_tab[(int)((crc ^ (long)ch) & 255L)]) ^ crc >> 8;
            crc &= 4294967295L;
         }
      } finally {
         bin_data.release();
      }

      return crc >= -2147483648L ? -((int)(crc + 1L & -1L)) : (int)(crc & -1L);
   }

   public static PyString b2a_hex(BufferProtocol bp) {
      PyBuffer argbuf = bp.getBuffer(0);
      int arglen = argbuf.getLen();
      StringBuilder retbuf = new StringBuilder(arglen * 2);

      try {
         for(int i = 0; i < arglen; ++i) {
            char ch = (char)argbuf.intAt(i);
            retbuf.append(hexdigit[ch >>> 4 & 15]);
            retbuf.append(hexdigit[ch & 15]);
         }
      } finally {
         argbuf.release();
      }

      return new PyString(retbuf.toString());
   }

   public static PyString hexlify(BufferProtocol argbuf) {
      return b2a_hex(argbuf);
   }

   public static PyString a2b_hex(BufferProtocol bp) {
      PyBuffer argbuf = bp.getBuffer(0);
      int arglen = argbuf.getLen();
      StringBuilder retbuf = new StringBuilder(arglen / 2);

      try {
         if (arglen % 2 != 0) {
            throw Py.TypeError("Odd-length string");
         }

         for(int i = 0; i < arglen; i += 2) {
            int top = Character.digit(argbuf.intAt(i), 16);
            int bot = Character.digit(argbuf.intAt(i + 1), 16);
            if (top == -1 || bot == -1) {
               throw Py.TypeError("Non-hexadecimal digit found");
            }

            retbuf.append((char)((top << 4) + bot));
         }
      } finally {
         argbuf.release();
      }

      return new PyString(retbuf.toString());
   }

   public static PyString unhexlify(BufferProtocol argbuf) {
      return a2b_hex(argbuf);
   }

   private static StringBuilder qpEscape(StringBuilder sb, char c) {
      sb.append('=');
      sb.append(upper_hexdigit[c >>> 4 & 15]);
      sb.append(upper_hexdigit[c & 15]);
      return sb;
   }

   public static boolean getIntFlagAsBool(ArgParser ap, int index, int dflt, String errMsg) {
      try {
         boolean val = ap.getInt(index, dflt) != 0;
         return val;
      } catch (PyException var6) {
         if (!var6.match(Py.AttributeError) && !var6.match(Py.ValueError)) {
            throw var6;
         } else {
            throw Py.TypeError(errMsg);
         }
      }
   }

   public static PyString a2b_qp(PyObject[] arg, String[] kws) {
      ArgParser ap = new ArgParser("a2b_qp", arg, kws, new String[]{"s", "header"});
      PyObject pyObject = ap.getPyObject(0);
      if (pyObject instanceof BufferProtocol) {
         BufferProtocol bp = (BufferProtocol)pyObject;
         StringBuilder sb = new StringBuilder();
         boolean header = getIntFlagAsBool(ap, 1, 0, "an integer is required");
         PyBuffer ascii_data = bp.getBuffer(0);

         try {
            int i = 0;
            int m = ascii_data.getLen();

            while(true) {
               while(i < m) {
                  char c = (char)ascii_data.intAt(i++);
                  if (header && c == '_') {
                     sb.append(' ');
                  } else if (c == '=') {
                     if (i < m) {
                        c = (char)ascii_data.intAt(i++);
                        if (c == '=') {
                           sb.append(c);
                        } else if (c == ' ') {
                           sb.append("= ");
                        } else if ((c >= '0' && c <= '9' || c >= 'A' && c <= 'F') && i < m) {
                           char nc = (char)ascii_data.intAt(i++);
                           if ((nc < '0' || nc > '9') && (nc < 'A' || nc > 'F')) {
                              sb.append('=').append(c).append(nc);
                           } else {
                              sb.append((char)(Character.digit(c, 16) << 4 | Character.digit(nc, 16)));
                           }
                        } else if (c != '\n') {
                           sb.append('=').append(c);
                        }
                     }
                  } else {
                     sb.append(c);
                  }
               }

               return new PyString(sb.toString());
            }
         } finally {
            ascii_data.release();
         }
      } else {
         throw Py.TypeError("expected something conforming to the buffer protocol, got " + pyObject.getType().fastGetName());
      }
   }

   public static PyString b2a_qp(PyObject[] arg, String[] kws) {
      ArgParser ap = new ArgParser("b2a_qp", arg, kws, new String[]{"s", "quotetabs", "istext", "header"});
      boolean quotetabs = getIntFlagAsBool(ap, 1, 0, "an integer is required");
      boolean istext = getIntFlagAsBool(ap, 2, 1, "an integer is required");
      boolean header = getIntFlagAsBool(ap, 3, 0, "an integer is required");
      PyObject pyObject = ap.getPyObject(0);
      if (pyObject instanceof BufferProtocol) {
         BufferProtocol bp = (BufferProtocol)pyObject;
         PyBuffer bin_data = bp.getBuffer(0);
         int datalen = bin_data.getLen();
         StringBuilder sb = new StringBuilder(datalen);

         try {
            String lineEnd = "\n";
            int count = 0;

            for(int m = bin_data.getLen(); count < m; ++count) {
               if (10 == bin_data.intAt(count)) {
                  if (count > 0 && 13 == bin_data.intAt(count - 1)) {
                     lineEnd = "\r\n";
                  }
                  break;
               }
            }

            count = 0;
            int MAXLINESIZE = 76;
            int in = 0;

            while(true) {
               while(true) {
                  while(in < datalen) {
                     char ch = (char)bin_data.intAt(in);
                     if (ch <= '~' && ch != '=' && (!header || ch != '_') && (ch != '.' || count != 0 || in + 1 != datalen && (char)bin_data.intAt(in + 1) != '\n' && (char)bin_data.intAt(in + 1) != '\r') && (istext || ch != '\r' && ch != '\n') && (ch != '\t' && ch != ' ' || in + 1 != datalen) && (ch >= '!' || ch == '\r' || ch == '\n' || !quotetabs && (quotetabs || ch == '\t' || ch == ' '))) {
                        if (istext && (ch == '\n' || in + 1 < datalen && ch == '\r' && bin_data.intAt(in + 1) == 10)) {
                           count = 0;
                           int out = sb.length();
                           if (out > 0 && (sb.charAt(out - 1) == ' ' || sb.charAt(out - 1) == '\t')) {
                              ch = sb.charAt(out - 1);
                              sb.setLength(out - 1);
                              qpEscape(sb, ch);
                           }

                           sb.append(lineEnd);
                           if (ch == '\r') {
                              in += 2;
                           } else {
                              ++in;
                           }
                        } else {
                           if (in + 1 != datalen && (char)bin_data.intAt(in + 1) != '\n' && count + 1 >= MAXLINESIZE) {
                              sb.append('=');
                              sb.append(lineEnd);
                              count = 0;
                           }

                           ++count;
                           if (header && ch == ' ') {
                              sb.append('_');
                              ++in;
                           } else {
                              sb.append(ch);
                              ++in;
                           }
                        }
                     } else {
                        if (count + 3 >= MAXLINESIZE) {
                           sb.append('=');
                           sb.append(lineEnd);
                           count = 0;
                        }

                        qpEscape(sb, ch);
                        ++in;
                        count += 3;
                     }
                  }

                  return new PyString(sb.toString());
               }
            }
         } finally {
            bin_data.release();
         }
      } else {
         throw Py.TypeError("expected something conforming to the buffer protocol, got " + pyObject.getType().fastGetName());
      }
   }

   static {
      Error = Py.makeClass("Error", Py.Exception, exceptionNamespace());
      Incomplete = Py.makeClass("Incomplete", Py.Exception, exceptionNamespace());
      RUNCHAR = 144;
      DONE = 127;
      SKIP = 126;
      FAIL = 125;
      table_a2b_hqx = new short[]{FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, SKIP, FAIL, FAIL, SKIP, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, FAIL, FAIL, 13, 14, 15, 16, 17, 18, 19, FAIL, 20, 21, DONE, FAIL, FAIL, FAIL, FAIL, FAIL, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, FAIL, 37, 38, 39, 40, 41, 42, 43, FAIL, 44, 45, 46, 47, FAIL, FAIL, FAIL, FAIL, 48, 49, 50, 51, 52, 53, 54, FAIL, 55, 56, 57, 58, 59, 60, FAIL, FAIL, 61, 62, 63, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL, FAIL};
      table_b2a_hqx = StringUtil.toBytes("!\"#$%&'()*+,-012345689@ABCDEFGHIJKLMNPQRSTUVXYZ[`abcdefhijklmpqr");
      table_a2b_base64 = new short[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, 0, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};
      BASE64_PAD = '=';
      BASE64_MAXBIN = 1073741820;
      table_b2a_base64 = StringUtil.toBytes("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
      crctab_hqx = new int[]{0, 4129, 8258, 12387, 16516, 20645, 24774, 28903, 33032, 37161, 41290, 45419, 49548, 53677, 57806, 61935, 4657, 528, 12915, 8786, 21173, 17044, 29431, 25302, 37689, 33560, 45947, 41818, 54205, 50076, 62463, 58334, 9314, 13379, 1056, 5121, 25830, 29895, 17572, 21637, 42346, 46411, 34088, 38153, 58862, 62927, 50604, 54669, 13907, 9842, 5649, 1584, 30423, 26358, 22165, 18100, 46939, 42874, 38681, 34616, 63455, 59390, 55197, 51132, 18628, 22757, 26758, 30887, 2112, 6241, 10242, 14371, 51660, 55789, 59790, 63919, 35144, 39273, 43274, 47403, 23285, 19156, 31415, 27286, 6769, 2640, 14899, 10770, 56317, 52188, 64447, 60318, 39801, 35672, 47931, 43802, 27814, 31879, 19684, 23749, 11298, 15363, 3168, 7233, 60846, 64911, 52716, 56781, 44330, 48395, 36200, 40265, 32407, 28342, 24277, 20212, 15891, 11826, 7761, 3696, 65439, 61374, 57309, 53244, 48923, 44858, 40793, 36728, 37256, 33193, 45514, 41451, 53516, 49453, 61774, 57711, 4224, 161, 12482, 8419, 20484, 16421, 28742, 24679, 33721, 37784, 41979, 46042, 49981, 54044, 58239, 62302, 689, 4752, 8947, 13010, 16949, 21012, 25207, 29270, 46570, 42443, 38312, 34185, 62830, 58703, 54572, 50445, 13538, 9411, 5280, 1153, 29798, 25671, 21540, 17413, 42971, 47098, 34713, 38840, 59231, 63358, 50973, 55100, 9939, 14066, 1681, 5808, 26199, 30326, 17941, 22068, 55628, 51565, 63758, 59695, 39368, 35305, 47498, 43435, 22596, 18533, 30726, 26663, 6336, 2273, 14466, 10403, 52093, 56156, 60223, 64286, 35833, 39896, 43963, 48026, 19061, 23124, 27191, 31254, 2801, 6864, 10931, 14994, 64814, 60687, 56684, 52557, 48554, 44427, 40424, 36297, 31782, 27655, 23652, 19525, 15522, 11395, 7392, 3265, 61215, 65342, 53085, 57212, 44955, 49082, 36825, 40952, 28183, 32310, 20053, 24180, 11923, 16050, 3793, 7920};
      __doc__a2b_uu = new PyString("(ascii) -> bin. Decode a line of uuencoded data");
      __doc__b2a_uu = new PyString("(bin) -> ascii. Uuencode line of data");
      __doc__a2b_base64 = new PyString("(ascii) -> bin. Decode a line of base64 data");
      __doc__b2a_base64 = new PyString("(bin) -> ascii. Base64-code line of data");
      __doc__a2b_hqx = new PyString("ascii -> bin, done. Decode .hqx coding");
      __doc__rlecode_hqx = new PyString("Binhex RLE-code binary data");
      __doc__b2a_hqx = new PyString("Encode .hqx data");
      __doc__rledecode_hqx = new PyString("Decode hexbin RLE-coded string");
      __doc__crc_hqx = new PyString("(data, oldcrc) -> newcrc. Compute hqx CRC incrementally");
      crc_32_tab = new long[]{0L, 1996959894L, 3993919788L, 2567524794L, 124634137L, 1886057615L, 3915621685L, 2657392035L, 249268274L, 2044508324L, 3772115230L, 2547177864L, 162941995L, 2125561021L, 3887607047L, 2428444049L, 498536548L, 1789927666L, 4089016648L, 2227061214L, 450548861L, 1843258603L, 4107580753L, 2211677639L, 325883990L, 1684777152L, 4251122042L, 2321926636L, 335633487L, 1661365465L, 4195302755L, 2366115317L, 997073096L, 1281953886L, 3579855332L, 2724688242L, 1006888145L, 1258607687L, 3524101629L, 2768942443L, 901097722L, 1119000684L, 3686517206L, 2898065728L, 853044451L, 1172266101L, 3705015759L, 2882616665L, 651767980L, 1373503546L, 3369554304L, 3218104598L, 565507253L, 1454621731L, 3485111705L, 3099436303L, 671266974L, 1594198024L, 3322730930L, 2970347812L, 795835527L, 1483230225L, 3244367275L, 3060149565L, 1994146192L, 31158534L, 2563907772L, 4023717930L, 1907459465L, 112637215L, 2680153253L, 3904427059L, 2013776290L, 251722036L, 2517215374L, 3775830040L, 2137656763L, 141376813L, 2439277719L, 3865271297L, 1802195444L, 476864866L, 2238001368L, 4066508878L, 1812370925L, 453092731L, 2181625025L, 4111451223L, 1706088902L, 314042704L, 2344532202L, 4240017532L, 1658658271L, 366619977L, 2362670323L, 4224994405L, 1303535960L, 984961486L, 2747007092L, 3569037538L, 1256170817L, 1037604311L, 2765210733L, 3554079995L, 1131014506L, 879679996L, 2909243462L, 3663771856L, 1141124467L, 855842277L, 2852801631L, 3708648649L, 1342533948L, 654459306L, 3188396048L, 3373015174L, 1466479909L, 544179635L, 3110523913L, 3462522015L, 1591671054L, 702138776L, 2966460450L, 3352799412L, 1504918807L, 783551873L, 3082640443L, 3233442989L, 3988292384L, 2596254646L, 62317068L, 1957810842L, 3939845945L, 2647816111L, 81470997L, 1943803523L, 3814918930L, 2489596804L, 225274430L, 2053790376L, 3826175755L, 2466906013L, 167816743L, 2097651377L, 4027552580L, 2265490386L, 503444072L, 1762050814L, 4150417245L, 2154129355L, 426522225L, 1852507879L, 4275313526L, 2312317920L, 282753626L, 1742555852L, 4189708143L, 2394877945L, 397917763L, 1622183637L, 3604390888L, 2714866558L, 953729732L, 1340076626L, 3518719985L, 2797360999L, 1068828381L, 1219638859L, 3624741850L, 2936675148L, 906185462L, 1090812512L, 3747672003L, 2825379669L, 829329135L, 1181335161L, 3412177804L, 3160834842L, 628085408L, 1382605366L, 3423369109L, 3138078467L, 570562233L, 1426400815L, 3317316542L, 2998733608L, 733239954L, 1555261956L, 3268935591L, 3050360625L, 752459403L, 1541320221L, 2607071920L, 3965973030L, 1969922972L, 40735498L, 2617837225L, 3943577151L, 1913087877L, 83908371L, 2512341634L, 3803740692L, 2075208622L, 213261112L, 2463272603L, 3855990285L, 2094854071L, 198958881L, 2262029012L, 4057260610L, 1759359992L, 534414190L, 2176718541L, 4139329115L, 1873836001L, 414664567L, 2282248934L, 4279200368L, 1711684554L, 285281116L, 2405801727L, 4167216745L, 1634467795L, 376229701L, 2685067896L, 3608007406L, 1308918612L, 956543938L, 2808555105L, 3495958263L, 1231636301L, 1047427035L, 2932959818L, 3654703836L, 1088359270L, 936918000L, 2847714899L, 3736837829L, 1202900863L, 817233897L, 3183342108L, 3401237130L, 1404277552L, 615818150L, 3134207493L, 3453421203L, 1423857449L, 601450431L, 3009837614L, 3294710456L, 1567103746L, 711928724L, 3020668471L, 3272380065L, 1510334235L, 755167117L};
      hexdigit = "0123456789abcdef".toCharArray();
      __doc__b2a_hex = new PyString("b2a_hex(data) -> s; Hexadecimal representation of binary data.\n\nThis function is also available as \"hexlify()\".");
      a2b_hex$doc = new PyString("a2b_hex(hexstr) -> s; Binary data of hexadecimal representation.\n\nhexstr must contain an even number of hex digits (upper or lower case).\nThis function is also available as \"unhexlify()\"");
      upper_hexdigit = "0123456789ABCDEF".toCharArray();
      UNDERSCORE = Pattern.compile("_");
      __doc__a2b_qp = new PyString("Decode a string of qp-encoded data");
      RN_TO_N = Pattern.compile("\r\n");
      N_TO_RN = Pattern.compile("(?<!\r)\n");
      __doc__b2a_qp = new PyString("b2a_qp(data, quotetabs=0, istext=1, header=0) -> s;\nEncode a string using quoted-printable encoding.\n\nOn encoding, when istext is set, newlines are not encoded, and white\nspace at end of lines is.  When istext is not set, \r and \n (CR/LF) are\nboth encoded.  When quotetabs is set, space and tabs are encoded.");
   }
}
