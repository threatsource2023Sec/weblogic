package org.python.modules.posix;

import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jnr.posix.FileStat;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "stat_result",
   isBaseType = false
)
public class PyStatResult extends PyTuple {
   public static final PyType TYPE;
   public PyObject st_mode;
   public PyObject st_ino;
   public PyObject st_dev;
   public PyObject st_nlink;
   public PyObject st_uid;
   public PyObject st_gid;
   public PyObject st_size;
   public PyObject st_atime;
   public PyObject st_mtime;
   public PyObject st_ctime;
   public static final int n_sequence_fields = 10;
   public static final int n_fields = 10;
   public static final int n_unnamed_fields = 10;

   PyStatResult(PyObject... vals) {
      super(TYPE, new PyObject[]{vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7].__int__(), vals[8].__int__(), vals[9].__int__()});
      this.st_mode = vals[0];
      this.st_ino = vals[1];
      this.st_dev = vals[2];
      this.st_nlink = vals[3];
      this.st_uid = vals[4];
      this.st_gid = vals[5];
      this.st_size = vals[6];
      this.st_atime = vals[7];
      this.st_mtime = vals[8];
      this.st_ctime = vals[9];
   }

   protected PyStatResult(PyObject[] vals, PyObject st_atime, PyObject st_mtime, PyObject st_ctime) {
      super(TYPE, vals);
      this.st_mode = vals[0];
      this.st_ino = vals[1];
      this.st_dev = vals[2];
      this.st_nlink = vals[3];
      this.st_uid = vals[4];
      this.st_gid = vals[5];
      this.st_size = vals[6];
      this.st_atime = st_atime;
      this.st_mtime = st_mtime;
      this.st_ctime = st_ctime;
   }

   @ExposedNew
   static PyObject stat_result_new(PyNewWrapper wrapper, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("stat_result", args, keywords, new String[]{"tuple"}, 1);
      PyObject obj = ap.getPyObject(0);
      if (obj instanceof PyTuple) {
         if (obj.__len__() != 10) {
            String msg = String.format("stat_result() takes a %s-sequence (%s-sequence given)", 10, obj.__len__());
            throw Py.TypeError(msg);
         } else {
            return obj instanceof PyStatResult ? new PyStatResult(((PyTuple)obj).getArray(), ((PyStatResult)obj).st_atime, ((PyStatResult)obj).st_mtime, ((PyStatResult)obj).st_ctime) : new PyStatResult(((PyTuple)obj).getArray());
         }
      } else {
         PyList seq = new PyList(obj);
         if (seq.__len__() != 10) {
            String msg = String.format("stat_result() takes a %s-sequence (%s-sequence given)", 10, obj.__len__());
            throw Py.TypeError(msg);
         } else {
            return new PyStatResult((PyObject[])((PyObject[])seq.__tojava__(PyObject[].class)));
         }
      }
   }

   public static PyStatResult fromFileStat(FileStat stat) {
      return new PyStatResult(new PyObject[]{Py.newInteger(stat.mode()), Py.newInteger(stat.ino()), Py.newLong(stat.dev()), Py.newInteger(stat.nlink()), Py.newInteger(stat.uid()), Py.newInteger(stat.gid()), Py.newInteger(stat.st_size()), Py.newFloat((float)stat.atime()), Py.newFloat((float)stat.mtime()), Py.newFloat((float)stat.ctime())});
   }

   private static double fromFileTime(FileTime fileTime) {
      return (double)fileTime.to(TimeUnit.NANOSECONDS) / 1.0E9;
   }

   private static Long zeroOrValue(Long value) {
      return value == null ? new Long(0L) : value;
   }

   private static Integer zeroOrValue(Integer value) {
      return value == null ? new Integer(0) : value;
   }

   public static PyStatResult fromUnixFileAttributes(Map stat) {
      Integer mode = zeroOrValue((Integer)stat.get("mode"));
      Long ino = zeroOrValue((Long)stat.get("ino"));
      Long dev = zeroOrValue((Long)stat.get("dev"));
      Integer nlink = zeroOrValue((Integer)stat.get("nlink"));
      Integer uid = zeroOrValue((Integer)stat.get("uid"));
      Integer gid = zeroOrValue((Integer)stat.get("gid"));
      Long size = zeroOrValue((Long)stat.get("size"));
      return new PyStatResult(new PyObject[]{Py.newInteger(mode), Py.newInteger(ino), Py.newLong(dev), Py.newInteger(nlink), Py.newInteger(uid), Py.newInteger(gid), Py.newInteger(size), Py.newFloat(fromFileTime((FileTime)stat.get("lastAccessTime"))), Py.newFloat(fromFileTime((FileTime)stat.get("lastModifiedTime"))), Py.newFloat(fromFileTime((FileTime)stat.get("ctime")))});
   }

   public static PyStatResult fromDosFileAttributes(int mode, DosFileAttributes stat) {
      return new PyStatResult(new PyObject[]{Py.newInteger(mode), Py.newInteger(0), Py.newLong(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(stat.size()), Py.newFloat(fromFileTime(stat.lastAccessTime())), Py.newFloat(fromFileTime(stat.lastModifiedTime())), Py.newFloat(fromFileTime(stat.creationTime()))});
   }

   public synchronized PyObject __eq__(PyObject o) {
      return this.stat_result___eq__(o);
   }

   final synchronized PyObject stat_result___eq__(PyObject o) {
      if (this.getType() != o.getType() && !this.getType().isSubType(o.getType())) {
         return null;
      } else {
         int tl = this.__len__();
         int ol = o.__len__();
         if (tl != ol) {
            return Py.False;
         } else {
            int i = cmp(this, tl, o, ol);
            return i < 0 ? Py.True : Py.False;
         }
      }
   }

   public synchronized PyObject __ne__(PyObject o) {
      return this.stat_result___ne__(o);
   }

   final synchronized PyObject stat_result___ne__(PyObject o) {
      PyObject eq = this.stat_result___eq__(o);
      return eq == null ? null : eq.__not__();
   }

   public PyObject __reduce__() {
      return this.stat_result___reduce__();
   }

   final PyObject stat_result___reduce__() {
      PyTuple newargs = this.__getnewargs__();
      return new PyTuple(new PyObject[]{this.getType(), newargs});
   }

   public PyTuple __getnewargs__() {
      PyList lst = new PyList(this.getArray());
      lst.pyset(7, this.st_atime);
      lst.pyset(8, this.st_mtime);
      lst.pyset(9, this.st_ctime);
      return new PyTuple(new PyObject[]{lst});
   }

   public PyString __repr__() {
      return (PyString)Py.newString(TYPE.fastGetName() + "(" + "st_mode=%r, st_ino=%r, st_dev=%r, st_nlink=%r, st_uid=%r, " + "st_gid=%r, st_size=%r, st_atime=%r, st_mtime=%r, st_ctime=%r)").__mod__(this);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.st_atime != null) {
            retVal = visit.visit(this.st_atime, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.st_mtime != null) {
            retVal = visit.visit(this.st_mtime, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return this.st_ctime != null ? visit.visit(this.st_ctime, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.st_atime || ob == this.st_mtime || ob == this.st_ctime || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyStatResult.class, new PyExposer());
      TYPE = PyType.fromClass(PyStatResult.class);
      TYPE.setName(PosixModule.getOSName() + "." + TYPE.fastGetName());
   }

   private static class stat_result___eq___exposer extends PyBuiltinMethodNarrow {
      public stat_result___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public stat_result___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stat_result___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyStatResult)this.self).stat_result___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class stat_result___ne___exposer extends PyBuiltinMethodNarrow {
      public stat_result___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public stat_result___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stat_result___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyStatResult)this.self).stat_result___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class stat_result___reduce___exposer extends PyBuiltinMethodNarrow {
      public stat_result___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public stat_result___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stat_result___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStatResult)this.self).stat_result___reduce__();
      }
   }

   private static class st_ino_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_ino_descriptor() {
         super("st_ino", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_ino;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class n_sequence_fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public n_sequence_fields_descriptor() {
         super("n_sequence_fields", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyStatResult)var1).n_sequence_fields);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_gid_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_gid_descriptor() {
         super("st_gid", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_gid;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_mode_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_mode_descriptor() {
         super("st_mode", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_mode;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_ctime_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_ctime_descriptor() {
         super("st_ctime", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_ctime;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_size_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_size_descriptor() {
         super("st_size", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_size;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_mtime_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_mtime_descriptor() {
         super("st_mtime", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_mtime;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_atime_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_atime_descriptor() {
         super("st_atime", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_atime;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class n_unnamed_fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public n_unnamed_fields_descriptor() {
         super("n_unnamed_fields", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyStatResult)var1).n_unnamed_fields);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_dev_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_dev_descriptor() {
         super("st_dev", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_dev;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class n_fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public n_fields_descriptor() {
         super("n_fields", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyStatResult)var1).n_fields);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_nlink_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_nlink_descriptor() {
         super("st_nlink", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_nlink;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class st_uid_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public st_uid_descriptor() {
         super("st_uid", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyStatResult)var1).st_uid;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyStatResult.stat_result_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new stat_result___eq___exposer("__eq__"), new stat_result___ne___exposer("__ne__"), new stat_result___reduce___exposer("__reduce__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new st_ino_descriptor(), new n_sequence_fields_descriptor(), new st_gid_descriptor(), new st_mode_descriptor(), new st_ctime_descriptor(), new st_size_descriptor(), new st_mtime_descriptor(), new st_atime_descriptor(), new n_unnamed_fields_descriptor(), new st_dev_descriptor(), new n_fields_descriptor(), new st_nlink_descriptor(), new st_uid_descriptor()};
         super("stat_result", PyStatResult.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}
