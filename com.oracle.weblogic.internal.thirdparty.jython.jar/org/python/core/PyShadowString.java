package org.python.core;

import java.util.Iterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "shadowstr",
   base = PyString.class,
   isBaseType = false
)
public class PyShadowString extends PyString {
   public static final PyType TYPE;
   protected PyList targets;
   protected String shadow;

   public PyShadowString() {
      this(TYPE, "", "");
      this.targets = new PyList();
   }

   public PyShadowString(String str, String shadow) {
      super(TYPE, str);
      this.shadow = shadow;
      this.targets = new PyList();
   }

   public PyShadowString(String str, String shadow, boolean isBytes) {
      super(TYPE, str, isBytes);
      this.shadow = shadow;
      this.targets = new PyList();
   }

   public PyShadowString(String str, String shadow, boolean isBytes, PyList targets) {
      super(TYPE, str, isBytes);
      this.shadow = shadow;
      this.targets = targets;
   }

   public PyShadowString(PyObject str, String shadow) {
      super(str.toString());
      this.shadow = shadow;
      this.targets = new PyList();
   }

   public PyShadowString(PyType subtype, String str, String shadow) {
      super(subtype, str);
      this.shadow = shadow;
      this.targets = new PyList();
   }

   public PyShadowString(PyType subtype, PyObject str, String shadow) {
      super(subtype, str.toString());
      this.shadow = shadow;
      this.targets = new PyList();
   }

   @ExposedNew
   static PyObject shadowstr_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("shadowstr", args, keywords, new String[]{"string", "shadow"}, 0);
      PyObject S = ap.getPyObject(0, (PyObject)null);
      PyObject Sh = ap.getPyObject(1, (PyObject)null);
      String str;
      if (S == null) {
         str = "";
      } else {
         PyObject S = S.__str__();
         if (S instanceof PyUnicode) {
            str = codecs.encode((PyUnicode)S, (String)null, (String)null);
         } else {
            str = S.toString();
         }
      }

      String shd;
      if (Sh == null) {
         shd = "";
      } else {
         PyObject Sh = Sh.__str__();
         if (Sh instanceof PyUnicode) {
            shd = codecs.encode((PyUnicode)Sh, (String)null, (String)null);
         } else {
            shd = Sh.toString();
         }
      }

      return new PyShadowString(str, shd);
   }

   private boolean isTarget() {
      Exception exc = new Exception();
      StackTraceElement[] var2 = exc.getStackTrace();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement ste = var2[var4];
         Iterator var6 = this.targets.getList().iterator();

         while(var6.hasNext()) {
            PyObject itm = (PyObject)var6.next();
            boolean result = true;
            PyObject obj = ((PyTuple)itm).__finditem__(0);
            if (obj != null && obj != Py.None && !ste.getClassName().matches(obj.toString())) {
               result = false;
            }

            if (result) {
               obj = ((PyTuple)itm).__finditem__(1);
               if (obj != null && obj != Py.None && !ste.getMethodName().matches(obj.toString())) {
                  result = false;
               }
            }

            if (result) {
               return true;
            }
         }
      }

      return false;
   }

   public String getShadow() {
      return this.shadow;
   }

   public PyString getshadow() {
      return (PyString)this.shadowstr_getshadow();
   }

   public final PyObject shadowstr_getshadow() {
      return Py.newString(this.shadow);
   }

   public void addTarget(String className, String methodName) {
      PyString classname = className == null ? null : Py.newString(className);
      PyString methodname = methodName == null ? null : Py.newString(methodName);
      this.shadowstr_addtarget(classname, methodname);
   }

   public final void shadowstr_addtarget(PyObject classname, PyObject methodname) {
      this.targets.add(methodname != null ? new PyTuple(new PyObject[]{classname == null ? Py.None : classname, methodname}) : new PyTuple(new PyObject[]{classname == null ? Py.None : classname}));
   }

   public PyList getTargets() {
      return (PyList)this.shadowstr_gettargets();
   }

   public final PyObject shadowstr_gettargets() {
      return this.targets;
   }

   public PyObject __eq__(PyObject other) {
      return !this.isTarget() ? this.str___eq__(other) : this.shadowstr___eq__(other);
   }

   final PyObject shadowstr___eq__(PyObject other) {
      String s = other.toString();
      return (PyObject)(s != null && s.equals(this.shadow) ? Py.True : this.str___eq__(other));
   }

   protected PyShadowString fromSubstring(int begin, int end) {
      int shadowBegin = begin;
      int shadowEnd = end;
      if (begin > this.shadow.length()) {
         shadowBegin = this.shadow.length();
      }

      if (end > this.shadow.length()) {
         shadowEnd = this.shadow.length();
      }

      return new PyShadowString(this.getString().substring(begin, end), this.shadow.substring(shadowBegin, shadowEnd), true, this.targets);
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (step > 0 && stop < start) {
         stop = start;
      }

      if (step == 1) {
         return this.fromSubstring(start, stop);
      } else {
         int n = sliceLength(start, stop, (long)step);
         char[] new_chars = new char[n];
         int j = 0;

         for(int i = start; j < n; i += step) {
            new_chars[j++] = this.getString().charAt(i);
         }

         char[] new_shadow_chars = new char[n];
         j = 0;

         try {
            for(int i = start; j < n; i += step) {
               new_chars[j] = this.shadow.charAt(i);
               ++j;
            }
         } catch (IndexOutOfBoundsException var9) {
            return new PyShadowString(new String(new_chars), new String(new_shadow_chars, 0, j), true, this.targets);
         }

         return new PyShadowString(new String(new_chars), new String(new_shadow_chars), true, this.targets);
      }
   }

   public boolean startswith(PyObject prefix) {
      return this.shadowstr_startswith(prefix, (PyObject)null, (PyObject)null);
   }

   public boolean startswith(PyObject prefix, PyObject start) {
      return this.shadowstr_startswith(prefix, start, (PyObject)null);
   }

   public boolean startswith(PyObject prefix, PyObject start, PyObject end) {
      return this.shadowstr_startswith(prefix, start, end);
   }

   final boolean shadowstr_startswith(PyObject prefix, PyObject startObj, PyObject endObj) {
      if (!this.isTarget()) {
         return this.str_startswith(prefix, startObj, endObj);
      } else {
         int[] indices = this.translateIndices(startObj, endObj);
         int start = indices[0];
         int sliceLen = indices[1] - start;
         if (!(prefix instanceof PyTuple)) {
            String s = asUTF16StringOrError(prefix);
            return sliceLen >= s.length() && (this.getString().startsWith(s, start) || this.shadow.startsWith(s, start));
         } else {
            PyObject[] var7 = ((PyTuple)prefix).getArray();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               PyObject prefixObj = var7[var9];
               String s = asUTF16StringOrError(prefixObj);
               if (sliceLen >= s.length() && (this.getString().startsWith(s, start) || this.shadow.startsWith(s, start))) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public PyString __repr__() {
      return this.shadowstr___repr__();
   }

   final PyString shadowstr___repr__() {
      return new PyString(encode_UnicodeEscape(this.getString() + " ( ==" + this.shadow + " for targets )", true));
   }

   static {
      PyType.addBuilder(PyShadowString.class, new PyExposer());
      TYPE = PyType.fromClass(PyShadowString.class);
   }

   private static class shadowstr_getshadow_exposer extends PyBuiltinMethodNarrow {
      public shadowstr_getshadow_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public shadowstr_getshadow_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new shadowstr_getshadow_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyShadowString)this.self).shadowstr_getshadow();
      }
   }

   private static class shadowstr_addtarget_exposer extends PyBuiltinMethodNarrow {
      public shadowstr_addtarget_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public shadowstr_addtarget_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new shadowstr_addtarget_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyShadowString)this.self).shadowstr_addtarget(var1, var2);
         return Py.None;
      }

      public PyObject __call__(PyObject var1) {
         ((PyShadowString)this.self).shadowstr_addtarget(var1, (PyObject)null);
         return Py.None;
      }
   }

   private static class shadowstr_gettargets_exposer extends PyBuiltinMethodNarrow {
      public shadowstr_gettargets_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public shadowstr_gettargets_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new shadowstr_gettargets_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyShadowString)this.self).shadowstr_gettargets();
      }
   }

   private static class shadowstr___eq___exposer extends PyBuiltinMethodNarrow {
      public shadowstr___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public shadowstr___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new shadowstr___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyShadowString)this.self).shadowstr___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class shadowstr_startswith_exposer extends PyBuiltinMethodNarrow {
      public shadowstr_startswith_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "";
      }

      public shadowstr_startswith_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new shadowstr_startswith_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyShadowString)this.self).shadowstr_startswith(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newBoolean(((PyShadowString)this.self).shadowstr_startswith(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyShadowString)this.self).shadowstr_startswith(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class shadowstr___repr___exposer extends PyBuiltinMethodNarrow {
      public shadowstr___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public shadowstr___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new shadowstr___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyShadowString)this.self).shadowstr___repr__();
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyShadowString.shadowstr_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new shadowstr_getshadow_exposer("getshadow"), new shadowstr_addtarget_exposer("addtarget"), new shadowstr_gettargets_exposer("gettargets"), new shadowstr___eq___exposer("__eq__"), new shadowstr_startswith_exposer("startswith"), new shadowstr___repr___exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("shadowstr", PyShadowString.class, PyString.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}
