package org.python.core;

import org.python.expose.ExposedGet;
import org.python.expose.ExposedType;

@ExposedType(
   name = "sys.getwindowsversion",
   isBaseType = false
)
class WinVersion extends PyTuple {
   @ExposedGet
   public PyObject major;
   @ExposedGet
   public PyObject minor;
   @ExposedGet
   public PyObject build;
   @ExposedGet
   public PyObject platform;
   @ExposedGet
   public PyObject service_pack;
   public static final PyType TYPE = PyType.fromClass(WinVersion.class);

   private WinVersion(PyObject... vals) {
      super(TYPE, vals);
      this.major = vals[0];
      this.minor = vals[1];
      this.build = vals[2];
      this.platform = vals[3];
      this.service_pack = vals[4];
   }

   public static WinVersion getWinVersion() {
      try {
         String sysver = PySystemState.getSystemVersionString();
         String[] sys_ver = sysver.split("\\.");
         int major = Integer.parseInt(sys_ver[0]);
         int minor = Integer.parseInt(sys_ver[1]);
         int build = Integer.parseInt(sys_ver[2]);
         if (major > 6) {
            major = 6;
            minor = 2;
            build = 9200;
         } else if (major == 6 && minor > 2) {
            minor = 2;
            build = 9200;
         }

         return new WinVersion(new PyObject[]{Py.newInteger(major), Py.newInteger(minor), Py.newInteger(build), Py.newInteger(2), Py.EmptyString});
      } catch (Exception var5) {
         return new WinVersion(new PyObject[]{Py.EmptyString, Py.EmptyString, Py.EmptyString, Py.EmptyString, Py.EmptyString});
      }
   }

   public PyString __repr__() {
      return (PyString)Py.newString(TYPE.fastGetName() + "(major=%r, minor=%r, build=%r, platform=%r, service_pack=%r)").__mod__(this);
   }
}
