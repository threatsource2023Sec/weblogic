package org.python.modules.jffi;

import com.kenai.jffi.MemoryIO;
import java.math.BigInteger;
import org.python.core.Py;
import org.python.core.PyObject;

public final class JITRuntime {
   private static final MemoryIO IO = MemoryIO.getInstance();
   private static final BigInteger UINT64_BASE;

   private JITRuntime() {
   }

   public static int pointerValue32(PyObject ptr) {
      return (int)((Pointer)ptr).getMemory().getAddress();
   }

   public static long pointerValue64(PyObject ptr) {
      return ((Pointer)ptr).getMemory().getAddress();
   }

   public static int boolValue32(PyObject parameter) {
      return parameter.__nonzero__() ? 1 : 0;
   }

   public static long boolValue64(PyObject parameter) {
      return parameter.__nonzero__() ? 1L : 0L;
   }

   public static int s8Value32(PyObject parameter) {
      return (byte)Util.intValue(parameter);
   }

   public static long s8Value64(PyObject parameter) {
      return (long)((byte)Util.intValue(parameter));
   }

   public static int u8Value32(PyObject parameter) {
      return Util.intValue(parameter) & 255;
   }

   public static long u8Value64(PyObject parameter) {
      return (long)(Util.intValue(parameter) & 255);
   }

   public static int s16Value32(PyObject parameter) {
      return (short)Util.intValue(parameter);
   }

   public static long s16Value64(PyObject parameter) {
      return (long)((short)Util.intValue(parameter));
   }

   public static int u16Value32(PyObject parameter) {
      return Util.intValue(parameter) & '\uffff';
   }

   public static long u16Value64(PyObject parameter) {
      return (long)(Util.intValue(parameter) & '\uffff');
   }

   public static int s32Value32(PyObject parameter) {
      return Util.intValue(parameter);
   }

   public static long s32Value64(PyObject parameter) {
      return (long)Util.intValue(parameter);
   }

   public static int u32Value32(PyObject parameter) {
      return Util.intValue(parameter);
   }

   public static long u32Value64(PyObject parameter) {
      return (long)Util.intValue(parameter) & 4294967295L;
   }

   public static long s64Value64(PyObject parameter) {
      return Util.longValue(parameter);
   }

   public static long u64Value64(PyObject parameter) {
      return Util.longValue(parameter);
   }

   public static int f32Value32(PyObject parameter) {
      return Float.floatToRawIntBits((float)parameter.asDouble());
   }

   public static long f32Value64(PyObject parameter) {
      return (long)Float.floatToRawIntBits((float)parameter.asDouble());
   }

   public static long f64Value64(PyObject parameter) {
      return Double.doubleToRawLongBits(parameter.asDouble());
   }

   public static PyObject newSigned8(int value) {
      return Py.newInteger((byte)value);
   }

   public static PyObject newSigned8(long value) {
      return Py.newInteger((byte)((int)value));
   }

   public static PyObject newUnsigned8(int value) {
      int n = (byte)value;
      return Py.newInteger(n < 0 ? (n & 127) + 128 : n);
   }

   public static PyObject newUnsigned8(long value) {
      int n = (byte)((int)value);
      return Py.newInteger(n < 0 ? (n & 127) + 128 : n);
   }

   public static PyObject newSigned16(int value) {
      return Py.newInteger((short)value);
   }

   public static PyObject newSigned16(long value) {
      return Py.newInteger((short)((int)value));
   }

   public static PyObject newUnsigned16(int value) {
      int n = (short)value;
      return Py.newInteger(n < 0 ? (n & 32767) + '耀' : n);
   }

   public static PyObject newUnsigned16(long value) {
      int n = (short)((int)value);
      return Py.newInteger(n < 0 ? (n & 32767) + '耀' : n);
   }

   public static PyObject newSigned32(int value) {
      return Py.newInteger(value);
   }

   public static PyObject newSigned32(long value) {
      return Py.newInteger((int)value);
   }

   public static PyObject newUnsigned32(int value) {
      return (PyObject)(value < 0 ? Py.newInteger(((long)value & 2147483647L) + 2147483648L) : Py.newInteger(value));
   }

   public static PyObject newUnsigned32(long value) {
      long n = (long)((int)value);
      return n < 0L ? Py.newInteger((n & 2147483647L) + 2147483648L) : Py.newInteger(n);
   }

   public static PyObject newSigned64(int value) {
      return Py.newInteger(value);
   }

   public static PyObject newSigned64(long value) {
      return Py.newInteger(value);
   }

   public static PyObject newUnsigned64(long value) {
      return (PyObject)(value < 0L ? Py.newLong(BigInteger.valueOf(value & Long.MAX_VALUE).add(UINT64_BASE)) : Py.newInteger(value));
   }

   public static PyObject newFloat32(int value) {
      return Py.newFloat(Float.intBitsToFloat(value));
   }

   public static PyObject newFloat32(long value) {
      return Py.newFloat(Float.intBitsToFloat((int)value));
   }

   public static PyObject newFloat64(long value) {
      return Py.newFloat(Double.longBitsToDouble(value));
   }

   public static PyObject newBoolean(int value) {
      return (value & 1) != 0 ? Py.True : Py.False;
   }

   public static PyObject newBoolean(long value) {
      return (value & 1L) != 0L ? Py.True : Py.False;
   }

   public static PyObject newNone(int unused) {
      return Py.None;
   }

   public static PyObject newNone(long unused) {
      return Py.None;
   }

   public static PyObject newPointer32(int value) {
      return Py.newLong(value);
   }

   public static PyObject newPointer32(long value) {
      return Py.newLong(value & 4294967295L);
   }

   public static PyObject newPointer64(long value) {
      return Py.newLong(value);
   }

   public static PyObject newString(int address) {
      return (PyObject)(address != 0 ? Py.newString(new String(IO.getZeroTerminatedByteArray((long)address))) : Py.None);
   }

   public static PyObject newString(long address) {
      return (PyObject)(address != 0L ? Py.newString(new String(IO.getZeroTerminatedByteArray(address))) : Py.None);
   }

   static {
      UINT64_BASE = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
   }
}
