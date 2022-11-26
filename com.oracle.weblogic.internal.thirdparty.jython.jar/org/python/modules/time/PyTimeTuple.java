package org.python.modules.time;

import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "time.struct_time",
   isBaseType = false
)
public class PyTimeTuple extends PyTuple {
   public PyObject tm_year;
   public PyObject tm_mon;
   public PyObject tm_mday;
   public PyObject tm_hour;
   public PyObject tm_min;
   public PyObject tm_sec;
   public PyObject tm_wday;
   public PyObject tm_yday;
   public PyObject tm_isdst;
   public final int n_sequence_fields = 9;
   public final int n_fields = 9;
   public final int n_unnamed_fields = 0;
   public static final PyType TYPE;

   PyTimeTuple(PyObject... vals) {
      super(TYPE, vals);
      this.tm_year = vals[0];
      this.tm_mon = vals[1];
      this.tm_mday = vals[2];
      this.tm_hour = vals[3];
      this.tm_min = vals[4];
      this.tm_sec = vals[5];
      this.tm_wday = vals[6];
      this.tm_yday = vals[7];
      this.tm_isdst = vals[8];
   }

   @ExposedNew
   static PyObject struct_time_new(PyNewWrapper wrapper, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("struct_time", args, keywords, new String[]{"tuple"}, 1);
      PyObject obj = ap.getPyObject(0);
      if (obj instanceof PyTuple) {
         if (obj.__len__() != 9) {
            throw Py.TypeError("time.struct_time() takes a 9-sequence (1-sequence given)");
         } else {
            return new PyTimeTuple(((PyTuple)obj).getArray());
         }
      } else {
         PyList seq = new PyList(obj);
         if (seq.__len__() != 9) {
            throw Py.TypeError("time.struct_time() takes a 9-sequence (1-sequence given)");
         } else {
            return new PyTimeTuple((PyObject[])((PyObject[])seq.__tojava__(PyObject[].class)));
         }
      }
   }

   public synchronized PyObject __eq__(PyObject o) {
      return this.struct_time___eq__(o);
   }

   final synchronized PyObject struct_time___eq__(PyObject o) {
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
      return this.struct_time___ne__(o);
   }

   final synchronized PyObject struct_time___ne__(PyObject o) {
      PyObject eq = this.struct_time___eq__(o);
      return eq == null ? null : eq.__not__();
   }

   public PyObject __reduce__() {
      return this.struct_time___reduce__();
   }

   final PyObject struct_time___reduce__() {
      PyTuple newargs = this.__getnewargs__();
      return new PyTuple(new PyObject[]{this.getType(), newargs});
   }

   public PyTuple __getnewargs__() {
      return new PyTuple(new PyObject[]{new PyList(this.getArray())});
   }

   public String toString() {
      return this.struct_time_toString();
   }

   final String struct_time_toString() {
      return String.format("time.struct_time(tm_year=%s, tm_mon=%s, tm_mday=%s, tm_hour=%s, tm_min=%s, tm_sec=%s, tm_wday=%s, tm_yday=%s, tm_isdst=%s)", this.tm_year, this.tm_mon, this.tm_mday, this.tm_hour, this.tm_min, this.tm_sec, this.tm_wday, this.tm_yday, this.tm_isdst);
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.tm_year != null) {
            retVal = visit.visit(this.tm_year, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_mon != null) {
            retVal = visit.visit(this.tm_mon, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_mday != null) {
            retVal = visit.visit(this.tm_mday, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_hour != null) {
            retVal = visit.visit(this.tm_hour, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_min != null) {
            retVal = visit.visit(this.tm_min, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_sec != null) {
            retVal = visit.visit(this.tm_sec, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_wday != null) {
            retVal = visit.visit(this.tm_wday, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.tm_yday != null) {
            retVal = visit.visit(this.tm_yday, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return this.tm_isdst != null ? visit.visit(this.tm_isdst, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.tm_year || ob == this.tm_mon || ob == this.tm_mday || ob == this.tm_hour || ob == this.tm_min || ob == this.tm_sec || ob == this.tm_wday || ob == this.tm_yday || ob == this.tm_isdst || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyTimeTuple.class, new PyExposer());
      TYPE = PyType.fromClass(PyTimeTuple.class);
   }

   private static class struct_time___eq___exposer extends PyBuiltinMethodNarrow {
      public struct_time___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public struct_time___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new struct_time___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTimeTuple)this.self).struct_time___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class struct_time___ne___exposer extends PyBuiltinMethodNarrow {
      public struct_time___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public struct_time___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new struct_time___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTimeTuple)this.self).struct_time___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class struct_time___reduce___exposer extends PyBuiltinMethodNarrow {
      public struct_time___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public struct_time___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new struct_time___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyTimeTuple)this.self).struct_time___reduce__();
      }
   }

   private static class struct_time_toString_exposer extends PyBuiltinMethodNarrow {
      public struct_time_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public struct_time_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new struct_time_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyTimeTuple)this.self).struct_time_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class tm_min_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_min_descriptor() {
         super("tm_min", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_min;
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

   private static class tm_hour_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_hour_descriptor() {
         super("tm_hour", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_hour;
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

   private static class tm_sec_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_sec_descriptor() {
         super("tm_sec", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_sec;
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
         return Py.newInteger(((PyTimeTuple)var1).n_sequence_fields);
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

   private static class tm_year_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_year_descriptor() {
         super("tm_year", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_year;
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

   private static class tm_mday_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_mday_descriptor() {
         super("tm_mday", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_mday;
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

   private static class tm_mon_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_mon_descriptor() {
         super("tm_mon", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_mon;
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
         return Py.newInteger(((PyTimeTuple)var1).n_unnamed_fields);
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

   private static class tm_isdst_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_isdst_descriptor() {
         super("tm_isdst", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_isdst;
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
         return Py.newInteger(((PyTimeTuple)var1).n_fields);
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

   private static class tm_wday_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_wday_descriptor() {
         super("tm_wday", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_wday;
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

   private static class tm_yday_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tm_yday_descriptor() {
         super("tm_yday", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTimeTuple)var1).tm_yday;
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
         return PyTimeTuple.struct_time_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new struct_time___eq___exposer("__eq__"), new struct_time___ne___exposer("__ne__"), new struct_time___reduce___exposer("__reduce__"), new struct_time_toString_exposer("__str__"), new struct_time_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new tm_min_descriptor(), new tm_hour_descriptor(), new tm_sec_descriptor(), new n_sequence_fields_descriptor(), new tm_year_descriptor(), new tm_mday_descriptor(), new tm_mon_descriptor(), new n_unnamed_fields_descriptor(), new tm_isdst_descriptor(), new n_fields_descriptor(), new tm_wday_descriptor(), new tm_yday_descriptor()};
         super("time.struct_time", PyTimeTuple.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}
