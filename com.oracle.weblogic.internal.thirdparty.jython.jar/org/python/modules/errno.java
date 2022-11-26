package org.python.modules;

import java.util.Iterator;
import jnr.constants.Constant;
import jnr.constants.ConstantSet;
import jnr.constants.platform.Errno;
import jnr.posix.util.Platform;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.imp;

public class errno implements ClassDictInit {
   public static final PyString __doc__ = Py.newString("This module makes available standard errno system symbols.\n\nThe value of each symbol is the corresponding integer value,\ne.g., on most systems, errno.ENOENT equals the integer 2.\n\nThe dictionary errno.errorcode maps numeric codes to symbol names,\ne.g., errno.errorcode[2] could be the string 'ENOENT'.\n\nSymbols that are not relevant to the underlying system are not defined.\n\nTo map error codes to error messages, use the function os.strerror(),\ne.g. os.strerror(2) could return 'No such file or directory'.");
   public static final PyObject errorcode = new PyDictionary();

   public static void classDictInit(PyObject dict) {
      if (Platform.IS_WINDOWS) {
         initWindows(dict);
      } else {
         initPosix(dict);
      }

      addCode(dict, "ESOCKISBLOCKING", 20000, "Socket is in blocking mode");
      addCode(dict, "EGETADDRINFOFAILED", 20001, "getaddrinfo failed");
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }

   private static void initWindows(PyObject dict) {
      ConstantSet winErrnos = ConstantSet.getConstantSet("Errno");
      ConstantSet lastErrors = ConstantSet.getConstantSet("LastError");
      Errno[] var3 = Errno.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Constant errno = var3[var5];
         String errnoName = errno.name();
         Constant constant;
         if ((constant = winErrnos.getConstant(errnoName)) != null || (constant = lastErrors.getConstant("WSA" + errnoName)) != null) {
            addCode(dict, errnoName, constant.intValue(), constant.toString());
         }
      }

      Iterator var9 = lastErrors.iterator();

      while(var9.hasNext()) {
         Constant lastError = (Constant)var9.next();
         if (lastError.name().startsWith("WSA")) {
            addCode(dict, lastError.name(), lastError.intValue(), lastError.toString());
         }
      }

   }

   private static void initPosix(PyObject dict) {
      Iterator var1 = ConstantSet.getConstantSet("Errno").iterator();

      while(var1.hasNext()) {
         Constant constant = (Constant)var1.next();
         addCode(dict, constant.name(), constant.intValue(), constant.toString());
      }

   }

   private static void addCode(PyObject dict, String name, int code, String message) {
      PyObject nameObj = Py.newString(name);
      PyObject codeObj = Py.newInteger(code);
      dict.__setitem__((PyObject)nameObj, codeObj);
      errorcode.__setitem__((PyObject)codeObj, nameObj);
   }

   /** @deprecated */
   @Deprecated
   public static PyObject strerror(PyObject code) {
      Py.warning(Py.DeprecationWarning, "The errno.strerror function is deprecated, use os.strerror.");
      return imp.load("os").__getattr__("strerror").__call__(code);
   }
}
