package org.python.modules.jffi;

import com.kenai.jffi.MemoryIO;
import com.kenai.jffi.Type;
import java.math.BigInteger;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;

final class Util {
   private static final MemoryIO IO = MemoryIO.getInstance();
   private static final BigInteger UINT64_BASE;

   private Util() {
   }

   public static final PyObject newSigned8(int value) {
      return Py.newInteger((byte)value);
   }

   public static final PyObject newUnsigned8(int value) {
      int n = (byte)value;
      return Py.newInteger(n < 0 ? (n & 127) + 128 : n);
   }

   public static final PyObject newSigned16(int value) {
      return Py.newInteger((short)value);
   }

   public static final PyObject newUnsigned16(int value) {
      int n = (short)value;
      return Py.newInteger(n < 0 ? (n & 32767) + '耀' : n);
   }

   public static final PyObject newSigned32(int value) {
      return Py.newInteger(value);
   }

   public static final PyObject newUnsigned32(int value) {
      return (PyObject)(value < 0 ? Py.newInteger(((long)value & 2147483647L) + 2147483648L) : Py.newInteger(value));
   }

   public static final PyObject newSigned64(long value) {
      return Py.newInteger(value);
   }

   public static final PyObject newUnsigned64(long value) {
      return (PyObject)(value < 0L ? Py.newLong(BigInteger.valueOf(value & Long.MAX_VALUE).add(UINT64_BASE)) : Py.newInteger(value));
   }

   public static final PyObject newString(long address) {
      return (PyObject)(address != 0L ? Py.newString(new String(IO.getZeroTerminatedByteArray(address))) : Py.None);
   }

   public static final byte int8Value(PyObject parameter) {
      return (byte)intValue(parameter);
   }

   public static final byte uint8Value(PyObject parameter) {
      return (byte)intValue(parameter);
   }

   public static final short int16Value(PyObject parameter) {
      return (short)intValue(parameter);
   }

   public static final short uint16Value(PyObject parameter) {
      return (short)intValue(parameter);
   }

   public static final int int32Value(PyObject parameter) {
      return intValue(parameter);
   }

   public static final int uint32Value(PyObject parameter) {
      return intValue(parameter);
   }

   public static final long int64Value(PyObject value) {
      return longValue(value);
   }

   public static final long uint64Value(PyObject value) {
      return longValue(value);
   }

   public static final float floatValue(PyObject parameter) {
      return (float)parameter.asDouble();
   }

   public static final double doubleValue(PyObject parameter) {
      return parameter.asDouble();
   }

   private static final long __long__value(PyObject value) {
      PyObject l = value.__long__();
      if (l instanceof PyLong) {
         return ((PyLong)l).getValue().longValue();
      } else if (l instanceof PyInteger) {
         return (long)((PyInteger)l).getValue();
      } else {
         throw Py.TypeError("invalid __long__() result");
      }
   }

   public static final void checkBounds(long size, long off, long len) {
      if ((off | len | off + len | size - (off + len)) < 0L) {
         throw Py.IndexError("Memory access offset=" + off + " size=" + len + " is out of bounds");
      }
   }

   static final DirectMemory getMemoryForAddress(PyObject address) {
      if (address instanceof Pointer) {
         return ((Pointer)address).getMemory();
      } else if (address instanceof PyInteger) {
         return new NativeMemory((long)address.asInt());
      } else if (address instanceof PyLong) {
         return new NativeMemory(((PyLong)address).getValue().longValue());
      } else {
         throw Py.TypeError("invalid address");
      }
   }

   static final Type jffiType(CType type) {
      return type.jffiType();
   }

   public static int intValue(PyObject parameter) {
      if (parameter instanceof PyInteger) {
         return ((PyInteger)parameter).getValue();
      } else if (parameter instanceof PyLong) {
         return ((PyLong)parameter).getValue().intValue();
      } else {
         return parameter instanceof ScalarCData ? intValue(((ScalarCData)parameter).getValue()) : (int)__long__value(parameter);
      }
   }

   public static long longValue(PyObject parameter) {
      if (parameter instanceof PyInteger) {
         return (long)((PyInteger)parameter).getValue();
      } else if (parameter instanceof PyLong) {
         return ((PyLong)parameter).getValue().longValue();
      } else {
         return parameter instanceof ScalarCData ? longValue(((ScalarCData)parameter).getValue()) : __long__value(parameter);
      }
   }

   static {
      UINT64_BASE = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
   }
}
