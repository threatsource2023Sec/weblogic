package org.python.modules.jffi;

import com.kenai.jffi.MemoryIO;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;

public class jffi implements ClassDictInit {
   public static final int FUNCFLAG_STDCALL = 0;
   public static final int FUNCFLAG_CDECL = 1;
   public static final int FUNCFLAG_HRESULT = 2;
   public static final int FUNCFLAG_PYTHONAPI = 4;
   public static final int FUNCFLAG_USE_ERRNO = 8;
   public static final int FUNCFLAG_USE_LASTERROR = 16;

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", Py.newString("jffi"));
      dict.__setitem__((String)"DynamicLibrary", DynamicLibrary.TYPE);
      dict.__setitem__((String)"Type", CType.TYPE);
      dict.__setitem__((String)"Function", Function.TYPE);
      dict.__setitem__((String)"CData", CData.TYPE);
      dict.__setitem__((String)"ArrayCData", ArrayCData.TYPE);
      dict.__setitem__((String)"PointerCData", PointerCData.TYPE);
      dict.__setitem__((String)"ScalarCData", ScalarCData.TYPE);
      dict.__setitem__((String)"StringCData", StringCData.TYPE);
      dict.__setitem__((String)"Structure", Structure.TYPE);
      dict.__setitem__((String)"StructLayout", StructLayout.TYPE);
      dict.__setitem__((String)"FUNCFLAG_STDCALL", Py.newInteger(0));
      dict.__setitem__((String)"FUNCFLAG_CDECL", Py.newInteger(1));
      dict.__setitem__((String)"RTLD_GLOBAL", Py.newInteger(8));
      dict.__setitem__((String)"RTLD_LOCAL", Py.newInteger(4));
      dict.__setitem__((String)"RTLD_LAZY", Py.newInteger(1));
      dict.__setitem__((String)"RTLD_NOW", Py.newInteger(2));
      dict.__setitem__((String)"__version__", Py.newString("0.0.1"));
   }

   public static PyObject dlopen(PyObject name, PyObject mode) {
      return new DynamicLibrary(name != Py.None ? name.asString() : null, mode.asInt());
   }

   public static PyObject get_errno() {
      return Py.newInteger(0);
   }

   public static PyObject set_errno(PyObject type) {
      return Py.newInteger(0);
   }

   public static PyObject pointer(PyObject type) {
      return Py.newInteger(0);
   }

   public static PyObject POINTER(PyObject type) {
      return type;
   }

   private static long getMemoryAddress(PyObject obj) {
      if (obj instanceof Pointer) {
         return ((Pointer)obj).getMemory().getAddress();
      } else if (obj instanceof CData) {
         return ((CData)obj).getReferenceMemory().getAddress();
      } else if (obj instanceof PyInteger) {
         return (long)obj.asInt();
      } else if (obj instanceof PyLong) {
         return ((PyLong)obj).asLong(0);
      } else {
         throw Py.TypeError("invalid memory address");
      }
   }

   public static PyObject memmove(PyObject dst, PyObject src, PyObject length) {
      MemoryIO.getInstance().copyMemory(getMemoryAddress(src), getMemoryAddress(dst), (long)length.asInt());
      return Py.None;
   }

   public static PyObject memset(PyObject dst, PyObject value, PyObject length) {
      MemoryIO.getInstance().setMemory(getMemoryAddress(dst), (long)length.asInt(), (byte)value.asInt());
      return Py.None;
   }
}
