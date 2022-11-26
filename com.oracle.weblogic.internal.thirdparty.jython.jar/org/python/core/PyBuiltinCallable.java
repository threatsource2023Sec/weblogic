package org.python.core;

import java.io.Serializable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "builtin_function_or_method",
   isBaseType = false
)
public abstract class PyBuiltinCallable extends PyObject {
   protected Info info;
   protected String doc;

   protected PyBuiltinCallable(PyType type, Info info) {
      super(type);
      this.info = info;
   }

   protected PyBuiltinCallable(Info info) {
      this.info = info;
   }

   public abstract PyBuiltinCallable bind(PyObject var1);

   public PyObject fastGetName() {
      return Py.newString(this.info.getName());
   }

   public String getDoc() {
      return this.doc;
   }

   public PyObject getModule() {
      return Py.None;
   }

   public PyObject makeCall() {
      return this;
   }

   public PyObject getSelf() {
      return Py.None;
   }

   public void setInfo(Info info) {
      this.info = info;
   }

   public String toString() {
      PyObject self = this.getSelf();
      return self == null ? String.format("<built-in function %s>", this.info.getName()) : String.format("<built-in method %s of %s object at %s>", this.info.getName(), self.getType().fastGetName(), Py.idstr(self));
   }

   static {
      PyType.addBuilder(PyBuiltinCallable.class, new PyExposer());
   }

   public static class DefaultInfo implements Info {
      private String name;
      private int maxargs;
      private int minargs;

      public DefaultInfo(String name, int minargs, int maxargs) {
         this.name = name;
         this.minargs = minargs;
         this.maxargs = maxargs;
      }

      public DefaultInfo(String name) {
         this(name, -1, -1);
      }

      public String getName() {
         return this.name;
      }

      public int getMaxargs() {
         return this.maxargs;
      }

      public int getMinargs() {
         return this.minargs;
      }

      public static boolean check(int nargs, int minargs, int maxargs) {
         if (nargs < minargs) {
            return false;
         } else {
            return maxargs == -1 || nargs <= maxargs;
         }
      }

      public static PyException unexpectedCall(int nargs, boolean keywords, String name, int minargs, int maxargs) {
         if (keywords) {
            return Py.TypeError(name + "() takes no keyword arguments");
         } else {
            String argsblurb;
            if (minargs == maxargs) {
               if (minargs == 0) {
                  argsblurb = "no arguments";
               } else if (minargs == 1) {
                  argsblurb = "exactly one argument";
               } else {
                  argsblurb = minargs + " arguments";
               }
            } else {
               if (maxargs == -1) {
                  return Py.TypeError(String.format("%s() requires at least %d arguments (%d) given", name, minargs, nargs));
               }

               if (minargs <= 0) {
                  argsblurb = "at most " + maxargs + " arguments";
               } else {
                  argsblurb = minargs + "-" + maxargs + " arguments";
               }
            }

            return Py.TypeError(String.format("%s() takes %s (%d given)", name, argsblurb, nargs));
         }
      }

      public PyException unexpectedCall(int nargs, boolean keywords) {
         return unexpectedCall(nargs, keywords, this.name, this.minargs, this.maxargs);
      }
   }

   public interface Info extends Serializable {
      String getName();

      int getMaxargs();

      int getMinargs();

      PyException unexpectedCall(int var1, boolean var2);
   }

   private static class __self___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __self___descriptor() {
         super("__self__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBuiltinCallable)var1).getSelf();
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

   private static class __name___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __name___descriptor() {
         super("__name__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBuiltinCallable)var1).fastGetName();
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

   private static class __call___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __call___descriptor() {
         super("__call__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBuiltinCallable)var1).makeCall();
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

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyBuiltinCallable)var1).getDoc();
         return var10000 == null ? Py.None : Py.newString(var10000);
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

   private static class __module___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __module___descriptor() {
         super("__module__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBuiltinCallable)var1).getModule();
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

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new __self___descriptor(), new __name___descriptor(), new __call___descriptor(), new __doc___descriptor(), new __module___descriptor()};
         super("builtin_function_or_method", PyBuiltinCallable.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
