package org.python.modules;

import java.math.BigInteger;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyTuple;

public class struct implements ClassDictInit {
   public static final PyObject error;
   public static String __doc__;
   private static FormatDef[] lilendian_table;
   private static FormatDef[] bigendian_table;
   private static FormatDef[] native_table;

   static FormatDef[] whichtable(String pfmt) {
      char c = pfmt.charAt(0);
      switch (c) {
         case '!':
         case '>':
            return bigendian_table;
         case '<':
            return lilendian_table;
         case '=':
            return bigendian_table;
         case '@':
         default:
            return native_table;
      }
   }

   private static FormatDef getentry(char c, FormatDef[] f) {
      for(int i = 0; i < f.length; ++i) {
         if (f[i].name == c) {
            return f[i];
         }
      }

      throw StructError("bad char in struct format");
   }

   private static int align(int size, FormatDef e) {
      if (e.alignment != 0) {
         size = (size + e.alignment - 1) / e.alignment * e.alignment;
      }

      return size;
   }

   static int calcsize(String format, FormatDef[] f) {
      int size = 0;
      int len = format.length();

      for(int j = 0; j < len; ++j) {
         char c = format.charAt(j);
         if ((j != 0 || c != '@' && c != '<' && c != '>' && c != '=' && c != '!') && !Character.isWhitespace(c)) {
            int num = 1;
            if (Character.isDigit(c)) {
               num = Character.digit(c, 10);

               while(true) {
                  ++j;
                  if (j >= len || !Character.isDigit(c = format.charAt(j))) {
                     if (j >= len) {
                        return size;
                     }
                     break;
                  }

                  int x = num * 10 + Character.digit(c, 10);
                  if (x / 10 != num) {
                     throw StructError("overflow in item count");
                  }

                  num = x;
               }
            }

            FormatDef e = getentry(c, f);
            int itemsize = e.size;
            size = align(size, e);
            int x = num * itemsize;
            size += x;
            if (x / itemsize != num || size < 0) {
               throw StructError("total struct size too long");
            }
         }
      }

      return size;
   }

   public static int calcsize(String format) {
      FormatDef[] f = whichtable(format);
      return calcsize(format, f);
   }

   public static PyString pack(PyObject[] args) {
      if (args.length < 1) {
         Py.TypeError("illegal argument type for built-in operation");
      }

      String format = args[0].toString();
      FormatDef[] f = whichtable(format);
      int size = calcsize(format, f);
      return new PyString(pack(format, f, size, 1, args).toString());
   }

   public static void pack_into(PyObject[] args) {
      if (args.length < 3) {
         Py.TypeError("illegal argument type for built-in operation");
      }

      String format = args[0].toString();
      FormatDef[] f = whichtable(format);
      int size = calcsize(format, f);
      pack_into(format, f, size, 1, args);
   }

   static void pack_into(String format, FormatDef[] f, int size, int argstart, PyObject[] args) {
      if (args.length - argstart < 2) {
         Py.TypeError("illegal argument type for built-in operation");
      }

      if (!(args[argstart] instanceof PyArray)) {
         throw Py.TypeError("pack_into takes an array arg");
      } else {
         PyArray buffer = (PyArray)args[argstart];
         int offset = args[argstart + 1].asInt();
         ByteStream res = pack(format, f, size, argstart + 2, args);
         if (res.pos > buffer.__len__()) {
            throw StructError("pack_into requires a buffer of at least " + res.pos + " bytes, got " + buffer.__len__());
         } else {
            for(int i = 0; i < res.pos; ++offset) {
               char val = res.data[i];
               buffer.set(offset, val);
               ++i;
            }

         }
      }
   }

   static ByteStream pack(String format, FormatDef[] f, int size, int start, PyObject[] args) {
      ByteStream res = new ByteStream();
      int i = start;
      int len = format.length();

      label68:
      for(int j = 0; j < len; ++j) {
         char c = format.charAt(j);
         if ((j != 0 || c != '@' && c != '<' && c != '>' && c != '=' && c != '!') && !Character.isWhitespace(c)) {
            int num = 1;
            if (Character.isDigit(c)) {
               num = Character.digit(c, 10);

               while(true) {
                  ++j;
                  if (j >= len || !Character.isDigit(c = format.charAt(j))) {
                     if (j >= len) {
                        break label68;
                     }
                     break;
                  }

                  num = num * 10 + Character.digit(c, 10);
               }
            }

            FormatDef e = getentry(c, f);
            int nres = align(res.size(), e) - res.size();

            while(nres-- > 0) {
               res.writeByte(0);
            }

            i += e.doPack(res, num, i, args);
         }
      }

      if (i < args.length) {
         throw StructError("too many arguments for pack format");
      } else {
         return res;
      }
   }

   public static PyTuple unpack(String format, String string) {
      FormatDef[] f = whichtable(format);
      int size = calcsize(format, f);
      int len = string.length();
      if (size != len) {
         throw StructError("unpack str size does not match format");
      } else {
         return unpack(f, size, format, new ByteStream(string));
      }
   }

   public static PyTuple unpack(String format, PyArray buffer) {
      String string = buffer.tostring();
      FormatDef[] f = whichtable(format);
      int size = calcsize(format, f);
      int len = string.length();
      if (size != len) {
         throw StructError("unpack str size does not match format");
      } else {
         return unpack(f, size, format, new ByteStream(string));
      }
   }

   public static PyTuple unpack_from(String format, String string) {
      return unpack_from(format, string, 0);
   }

   public static PyTuple unpack_from(String format, String string, int offset) {
      FormatDef[] f = whichtable(format);
      int size = calcsize(format, f);
      int len = string.length();
      if (size >= len - offset + 1) {
         throw StructError("unpack_from str size does not match format");
      } else {
         return unpack(f, size, format, new ByteStream(string, offset));
      }
   }

   static PyTuple unpack(FormatDef[] f, int size, String format, ByteStream str) {
      PyList res = new PyList();
      int flen = format.length();

      for(int j = 0; j < flen; ++j) {
         char c = format.charAt(j);
         if ((j != 0 || c != '@' && c != '<' && c != '>' && c != '=' && c != '!') && !Character.isWhitespace(c)) {
            int num = 1;
            if (Character.isDigit(c)) {
               num = Character.digit(c, 10);

               while(true) {
                  ++j;
                  if (j >= flen || !Character.isDigit(c = format.charAt(j))) {
                     if (j > flen) {
                        return PyTuple.fromIterable(res);
                     }
                     break;
                  }

                  num = num * 10 + Character.digit(c, 10);
               }
            }

            FormatDef e = getentry(c, f);
            str.skip(align(str.size(), e) - str.size());
            e.doUnpack(str, num, res);
         }
      }

      return PyTuple.fromIterable(res);
   }

   static PyException StructError(String explanation) {
      return new PyException(error, explanation);
   }

   private static PyObject exceptionNamespace() {
      PyObject dict = new PyStringMap();
      dict.__setitem__((String)"__module__", new PyString("struct"));
      return dict;
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"Struct", PyStruct.TYPE);
   }

   static {
      error = Py.makeClass("error", Py.Exception, exceptionNamespace());
      __doc__ = "Functions to convert between Python values and C structs.\nPython strings are used to hold the data representing the C\nstruct and also as format strings to describe the layout of\ndata in the C struct.\n\nThe optional first format char indicates byte ordering and\nalignment:\n @: native w/native alignment(default)\n =: native w/standard alignment\n <: little-endian, std. alignment\n >: big-endian, std. alignment\n !: network, std (same as >)\n\nThe remaining chars indicate types of args and must match\nexactly; these can be preceded by a decimal repeat count:\n x: pad byte (no data); c:char; b:signed byte; B:unsigned byte;\n h:short; H:unsigned short; i:int; I:unsigned int;\n l:long; L:unsigned long; f:float; d:double.\nSpecial cases (preceding decimal count indicates length):\n s:string (array of char); p: pascal string (w. count byte).\nWhitespace between formats is ignored.\n\nThe variable struct.error is an exception raised on errors.";
      lilendian_table = new FormatDef[]{(new PadFormatDef()).init('x', 1, 0), (new ByteFormatDef()).init('b', 1, 0), (new UnsignedByteFormatDef()).init('B', 1, 0), (new CharFormatDef()).init('c', 1, 0), (new StringFormatDef()).init('s', 1, 0), (new PascalStringFormatDef()).init('p', 1, 0), (new LEShortFormatDef()).init('h', 2, 0), (new LEUnsignedShortFormatDef()).init('H', 2, 0), (new LEIntFormatDef()).init('i', 4, 0), (new LEUnsignedIntFormatDef()).init('I', 4, 0), (new LEIntFormatDef()).init('l', 4, 0), (new LEUnsignedIntFormatDef()).init('L', 4, 0), (new LELongFormatDef()).init('q', 8, 0), (new LEUnsignedLongFormatDef()).init('Q', 8, 0), (new LEFloatFormatDef()).init('f', 4, 0), (new LEDoubleFormatDef()).init('d', 8, 0)};
      bigendian_table = new FormatDef[]{(new PadFormatDef()).init('x', 1, 0), (new ByteFormatDef()).init('b', 1, 0), (new UnsignedByteFormatDef()).init('B', 1, 0), (new CharFormatDef()).init('c', 1, 0), (new StringFormatDef()).init('s', 1, 0), (new PascalStringFormatDef()).init('p', 1, 0), (new BEShortFormatDef()).init('h', 2, 0), (new BEUnsignedShortFormatDef()).init('H', 2, 0), (new BEIntFormatDef()).init('i', 4, 0), (new BEUnsignedIntFormatDef()).init('I', 4, 0), (new BEIntFormatDef()).init('l', 4, 0), (new BEUnsignedIntFormatDef()).init('L', 4, 0), (new BELongFormatDef()).init('q', 8, 0), (new BEUnsignedLongFormatDef()).init('Q', 8, 0), (new BEFloatFormatDef()).init('f', 4, 0), (new BEDoubleFormatDef()).init('d', 8, 0)};
      native_table = new FormatDef[]{(new PadFormatDef()).init('x', 1, 0), (new ByteFormatDef()).init('b', 1, 0), (new UnsignedByteFormatDef()).init('B', 1, 0), (new CharFormatDef()).init('c', 1, 0), (new StringFormatDef()).init('s', 1, 0), (new PascalStringFormatDef()).init('p', 1, 0), (new BEShortFormatDef()).init('h', 2, 2), (new BEUnsignedShortFormatDef()).init('H', 2, 2), (new BEIntFormatDef()).init('i', 4, 4), (new BEUnsignedIntFormatDef()).init('I', 4, 4), (new BEIntFormatDef()).init('l', 4, 4), (new BEUnsignedIntFormatDef()).init('L', 4, 4), (new BELongFormatDef()).init('q', 8, 8), (new BEUnsignedLongFormatDef()).init('Q', 8, 8), (new BEFloatFormatDef()).init('f', 4, 4), (new BEDoubleFormatDef()).init('d', 8, 8), (new PointerFormatDef()).init('P')};
   }

   static class BEDoubleFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         long bits = Double.doubleToLongBits(this.get_float(value));
         this.BEwriteInt(buf, (int)(bits >>> 32));
         this.BEwriteInt(buf, (int)(bits & -1L));
      }

      Object unpack(ByteStream buf) {
         long bits = ((long)this.BEreadInt(buf) << 32) + ((long)this.BEreadInt(buf) & 4294967295L);
         double v = Double.longBitsToDouble(bits);
         if (PyFloat.double_format != PyFloat.Format.UNKNOWN || !Double.isInfinite(v) && !Double.isNaN(v)) {
            return Py.newFloat(v);
         } else {
            throw Py.ValueError("can't unpack IEEE 754 special value on non-IEEE platform");
         }
      }
   }

   static class BEFloatFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         int bits = Float.floatToIntBits((float)this.get_float(value));
         this.BEwriteInt(buf, bits);
      }

      Object unpack(ByteStream buf) {
         int bits = this.BEreadInt(buf);
         float v = Float.intBitsToFloat(bits);
         if (PyFloat.float_format != PyFloat.Format.UNKNOWN || !Float.isInfinite(v) && !Float.isNaN(v)) {
            return Py.newFloat(v);
         } else {
            throw Py.ValueError("can't unpack IEEE 754 special value on non-IEEE platform");
         }
      }
   }

   static class LEDoubleFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         long bits = Double.doubleToLongBits(this.get_float(value));
         this.LEwriteInt(buf, (int)(bits & -1L));
         this.LEwriteInt(buf, (int)(bits >>> 32));
      }

      Object unpack(ByteStream buf) {
         long bits = ((long)this.LEreadInt(buf) & 4294967295L) + ((long)this.LEreadInt(buf) << 32);
         double v = Double.longBitsToDouble(bits);
         if (PyFloat.double_format != PyFloat.Format.UNKNOWN || !Double.isInfinite(v) && !Double.isNaN(v)) {
            return Py.newFloat(v);
         } else {
            throw Py.ValueError("can't unpack IEEE 754 special value on non-IEEE platform");
         }
      }
   }

   static class LEFloatFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         int bits = Float.floatToIntBits((float)this.get_float(value));
         this.LEwriteInt(buf, bits);
      }

      Object unpack(ByteStream buf) {
         int bits = this.LEreadInt(buf);
         float v = Float.intBitsToFloat(bits);
         if (PyFloat.float_format != PyFloat.Format.UNKNOWN || !Float.isInfinite(v) && !Float.isNaN(v)) {
            return Py.newFloat(v);
         } else {
            throw Py.ValueError("can't unpack IEEE 754 special value on non-IEEE platform");
         }
      }
   }

   static class BELongFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         long lvalue = this.get_long(value);
         int high = (int)((lvalue & -4294967296L) >> 32);
         int low = (int)(lvalue & 4294967295L);
         this.BEwriteInt(buf, high);
         this.BEwriteInt(buf, low);
      }

      Object unpack(ByteStream buf) {
         long high = (long)this.BEreadInt(buf) << 32 & -4294967296L;
         long low = (long)this.BEreadInt(buf) & 4294967295L;
         long result = high | low;
         return new PyLong(result);
      }
   }

   static class LELongFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         long lvalue = this.get_long(value);
         int high = (int)((lvalue & -4294967296L) >> 32);
         int low = (int)(lvalue & 4294967295L);
         this.LEwriteInt(buf, low);
         this.LEwriteInt(buf, high);
      }

      Object unpack(ByteStream buf) {
         long low = (long)this.LEreadInt(buf) & 4294967295L;
         long high = (long)this.LEreadInt(buf) << 32 & -4294967296L;
         long result = high | low;
         return new PyLong(result);
      }
   }

   static class BEUnsignedLongFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         BigInteger bi = this.get_ulong(value);
         if (bi.compareTo(BigInteger.valueOf(0L)) < 0) {
            throw struct.StructError("can't convert negative long to unsigned");
         } else {
            long lvalue = bi.longValue();
            int high = (int)((lvalue & -4294967296L) >> 32);
            int low = (int)(lvalue & 4294967295L);
            this.BEwriteInt(buf, high);
            this.BEwriteInt(buf, low);
         }
      }

      Object unpack(ByteStream buf) {
         long high = (long)this.BEreadInt(buf) & 4294967295L;
         long low = (long)this.BEreadInt(buf) & 4294967295L;
         BigInteger result = BigInteger.valueOf(high);
         result = result.multiply(BigInteger.valueOf(4294967296L));
         result = result.add(BigInteger.valueOf(low));
         return new PyLong(result);
      }
   }

   static class LEUnsignedLongFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         BigInteger bi = this.get_ulong(value);
         if (bi.compareTo(BigInteger.valueOf(0L)) < 0) {
            throw struct.StructError("can't convert negative long to unsigned");
         } else {
            long lvalue = bi.longValue();
            int high = (int)((lvalue & -4294967296L) >> 32);
            int low = (int)(lvalue & 4294967295L);
            this.LEwriteInt(buf, low);
            this.LEwriteInt(buf, high);
         }
      }

      Object unpack(ByteStream buf) {
         long low = (long)this.LEreadInt(buf) & 4294967295L;
         long high = (long)this.LEreadInt(buf) & 4294967295L;
         BigInteger result = BigInteger.valueOf(high);
         result = result.multiply(BigInteger.valueOf(4294967296L));
         result = result.add(BigInteger.valueOf(low));
         return new PyLong(result);
      }
   }

   static class BEUnsignedIntFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         this.BEwriteInt(buf, (int)(this.get_long(value) & -1L));
      }

      Object unpack(ByteStream buf) {
         long v = (long)this.BEreadInt(buf);
         if (v < 0L) {
            v += 4294967296L;
         }

         return new PyLong(v);
      }
   }

   static class BEIntFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         this.BEwriteInt(buf, this.get_int(value));
      }

      Object unpack(ByteStream buf) {
         return Py.newInteger(this.BEreadInt(buf));
      }
   }

   static class LEUnsignedIntFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         this.LEwriteInt(buf, (int)(this.get_long(value) & -1L));
      }

      Object unpack(ByteStream buf) {
         long v = (long)this.LEreadInt(buf);
         if (v < 0L) {
            v += 4294967296L;
         }

         return new PyLong(v);
      }
   }

   static class LEIntFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         this.LEwriteInt(buf, this.get_int(value));
      }

      Object unpack(ByteStream buf) {
         int v = this.LEreadInt(buf);
         return Py.newInteger(v);
      }
   }

   static class BEUnsignedShortFormatDef extends BEShortFormatDef {
      Object unpack(ByteStream buf) {
         int v = buf.readByte() << 8 | buf.readByte();
         return Py.newInteger(v);
      }
   }

   static class BEShortFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         int v = this.get_int(value);
         buf.writeByte(v >> 8 & 255);
         buf.writeByte(v & 255);
      }

      Object unpack(ByteStream buf) {
         int v = buf.readByte() << 8 | buf.readByte();
         if (v > 32767) {
            v -= 65536;
         }

         return Py.newInteger(v);
      }
   }

   static class LEUnsignedShortFormatDef extends LEShortFormatDef {
      Object unpack(ByteStream buf) {
         int v = buf.readByte() | buf.readByte() << 8;
         return Py.newInteger(v);
      }
   }

   static class LEShortFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         int v = this.get_int(value);
         buf.writeByte(v & 255);
         buf.writeByte(v >> 8 & 255);
      }

      Object unpack(ByteStream buf) {
         int v = buf.readByte() | buf.readByte() << 8;
         if (v > 32767) {
            v -= 65536;
         }

         return Py.newInteger(v);
      }
   }

   static class PointerFormatDef extends FormatDef {
      FormatDef init(char name) {
         String dataModel = System.getProperty("sun.arch.data.model");
         if (dataModel == null) {
            throw Py.NotImplementedError("Can't determine if JVM is 32- or 64-bit");
         } else {
            int length = dataModel.equals("64") ? 8 : 4;
            super.init(name, length, length);
            return this;
         }
      }

      void pack(ByteStream buf, PyObject value) {
         throw Py.NotImplementedError("Pointer packing/unpacking not implemented in Jython");
      }

      Object unpack(ByteStream buf) {
         throw Py.NotImplementedError("Pointer packing/unpacking not implemented in Jython");
      }
   }

   static class UnsignedByteFormatDef extends ByteFormatDef {
      Object unpack(ByteStream buf) {
         return Py.newInteger(buf.readByte());
      }
   }

   static class ByteFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         buf.writeByte(this.get_int(value));
      }

      Object unpack(ByteStream buf) {
         int b = buf.readByte();
         if (b > 127) {
            b -= 256;
         }

         return Py.newInteger(b);
      }
   }

   static class CharFormatDef extends FormatDef {
      void pack(ByteStream buf, PyObject value) {
         if (value instanceof PyString && value.__len__() == 1) {
            buf.writeByte(value.toString().charAt(0));
         } else {
            throw struct.StructError("char format require string of length 1");
         }
      }

      Object unpack(ByteStream buf) {
         return Py.newString((char)buf.readByte());
      }
   }

   static class PascalStringFormatDef extends StringFormatDef {
      int doPack(ByteStream buf, int count, int pos, PyObject[] args) {
         PyObject value = args[pos];
         if (!(value instanceof PyString)) {
            throw struct.StructError("argument for 'p' must be a string");
         } else {
            buf.writeByte(Math.min(255, Math.min(value.toString().length(), count - 1)));
            return super.doPack(buf, count - 1, pos, args);
         }
      }

      void doUnpack(ByteStream buf, int count, PyList list) {
         int n = buf.readByte();
         if (n >= count) {
            n = count - 1;
         }

         super.doUnpack(buf, n, list);
         buf.skip(Math.max(count - n - 1, 0));
      }
   }

   static class StringFormatDef extends FormatDef {
      int doPack(ByteStream buf, int count, int pos, PyObject[] args) {
         PyObject value = args[pos];
         if (!(value instanceof PyString)) {
            throw struct.StructError("argument for 's' must be a string");
         } else {
            String s = value.toString();
            int len = s.length();
            buf.writeString(s, 0, Math.min(count, len));
            if (len < count) {
               count -= len;

               for(int i = 0; i < count; ++i) {
                  buf.writeByte(0);
               }
            }

            return 1;
         }
      }

      void doUnpack(ByteStream buf, int count, PyList list) {
         list.append(Py.newString(buf.readString(count)));
      }
   }

   static class PadFormatDef extends FormatDef {
      int doPack(ByteStream buf, int count, int pos, PyObject[] args) {
         while(count-- > 0) {
            buf.writeByte(0);
         }

         return 0;
      }

      void doUnpack(ByteStream buf, int count, PyList list) {
         while(count-- > 0) {
            buf.readByte();
         }

      }
   }

   static class ByteStream {
      char[] data;
      int len;
      int pos;

      ByteStream() {
         this.data = new char[10];
         this.len = 0;
         this.pos = 0;
      }

      ByteStream(String s) {
         this(s, 0);
      }

      ByteStream(String s, int offset) {
         int size = s.length() - offset;
         this.data = new char[size];
         s.getChars(offset, s.length(), this.data, 0);
         this.len = size;
         this.pos = 0;
      }

      int readByte() {
         return this.data[this.pos++] & 255;
      }

      void read(char[] buf, int pos, int len) {
         System.arraycopy(this.data, this.pos, buf, pos, len);
         this.pos += len;
      }

      String readString(int l) {
         char[] data = new char[l];
         this.read(data, 0, l);
         return new String(data);
      }

      private void ensureCapacity(int l) {
         if (this.pos + l >= this.data.length) {
            char[] b = new char[(this.pos + l) * 2];
            System.arraycopy(this.data, 0, b, 0, this.pos);
            this.data = b;
         }

      }

      void writeByte(int b) {
         this.ensureCapacity(1);
         this.data[this.pos++] = (char)(b & 255);
      }

      void write(char[] buf, int pos, int len) {
         this.ensureCapacity(len);
         System.arraycopy(buf, pos, this.data, this.pos, len);
         this.pos += len;
      }

      void writeString(String s, int pos, int len) {
         char[] data = new char[len];
         s.getChars(pos, len, data, 0);
         this.write(data, 0, len);
      }

      int skip(int l) {
         this.pos += l;
         return this.pos;
      }

      int size() {
         return this.pos;
      }

      public String toString() {
         return new String(this.data, 0, this.pos);
      }
   }

   static class FormatDef {
      char name;
      int size;
      int alignment;

      FormatDef init(char name, int size, int alignment) {
         this.name = name;
         this.size = size;
         this.alignment = alignment;
         return this;
      }

      void pack(ByteStream buf, PyObject value) {
      }

      Object unpack(ByteStream buf) {
         return null;
      }

      int doPack(ByteStream buf, int count, int pos, PyObject[] args) {
         if (pos + count > args.length) {
            throw struct.StructError("insufficient arguments to pack");
         } else {
            int cnt = count;

            while(count-- > 0) {
               this.pack(buf, args[pos++]);
            }

            return cnt;
         }
      }

      void doUnpack(ByteStream buf, int count, PyList list) {
         while(count-- > 0) {
            list.append(Py.java2py(this.unpack(buf)));
         }

      }

      int get_int(PyObject value) {
         try {
            return value.asInt();
         } catch (PyException var3) {
            throw struct.StructError("required argument is not an integer");
         }
      }

      long get_long(PyObject value) {
         if (value instanceof PyLong) {
            Object v = value.__tojava__(Long.TYPE);
            if (v == Py.NoConversion) {
               throw struct.StructError("long int too long to convert");
            } else {
               return (Long)v;
            }
         } else {
            return (long)this.get_int(value);
         }
      }

      BigInteger get_ulong(PyObject value) {
         if (value instanceof PyLong) {
            BigInteger v = (BigInteger)value.__tojava__(BigInteger.class);
            if (v.compareTo(PyLong.MAX_ULONG) > 0) {
               throw struct.StructError("unsigned long int too long to convert");
            } else {
               return v;
            }
         } else {
            return BigInteger.valueOf((long)this.get_int(value));
         }
      }

      double get_float(PyObject value) {
         return value.asDouble();
      }

      void BEwriteInt(ByteStream buf, int v) {
         buf.writeByte(v >>> 24 & 255);
         buf.writeByte(v >>> 16 & 255);
         buf.writeByte(v >>> 8 & 255);
         buf.writeByte(v >>> 0 & 255);
      }

      void LEwriteInt(ByteStream buf, int v) {
         buf.writeByte(v >>> 0 & 255);
         buf.writeByte(v >>> 8 & 255);
         buf.writeByte(v >>> 16 & 255);
         buf.writeByte(v >>> 24 & 255);
      }

      int BEreadInt(ByteStream buf) {
         int b1 = buf.readByte();
         int b2 = buf.readByte();
         int b3 = buf.readByte();
         int b4 = buf.readByte();
         return (b1 << 24) + (b2 << 16) + (b3 << 8) + (b4 << 0);
      }

      int LEreadInt(ByteStream buf) {
         int b1 = buf.readByte();
         int b2 = buf.readByte();
         int b3 = buf.readByte();
         int b4 = buf.readByte();
         return (b1 << 0) + (b2 << 8) + (b3 << 16) + (b4 << 24);
      }
   }
}
