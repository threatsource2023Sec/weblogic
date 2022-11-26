package org.python.modules.thread;

import org.python.core.Py;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyDictionary;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "thread._local"
)
public class PyLocal extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   private ThreadLocal tdict;
   private PyObject[] args;
   private String[] keywords;

   public PyLocal() {
      this(TYPE);
   }

   public PyLocal(PyType subType) {
      super(subType);
      this.tdict = new ThreadLocal() {
         protected Object initialValue() {
            return new Object[1];
         }
      };
      this.tdict.set(new Object[]{new PyDictionary()});
   }

   @ExposedNew
   static final PyObject _local___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      PyObject[] where = new PyObject[1];
      subtype.lookup_where("__init__", where);
      if (where[0] == PyObject.TYPE && args.length > 0) {
         throw Py.TypeError("Initialization arguments are not supported");
      } else {
         Object newobj;
         if (new_.getWrappedType() == subtype) {
            newobj = new PyLocal();
         } else {
            newobj = new PyLocalDerived(subtype);
         }

         ((PyLocal)newobj).args = args;
         ((PyLocal)newobj).keywords = keywords;
         return (PyObject)newobj;
      }
   }

   public PyObject getDict() {
      return this.fastGetDict();
   }

   public void setDict(PyObject dict) {
      super.setDict(dict);
   }

   public PyObject fastGetDict() {
      Object[] local = (Object[])this.tdict.get();
      PyDictionary ldict = (PyDictionary)((PyDictionary)local[0]);
      if (ldict == null) {
         ldict = new PyDictionary();
         local[0] = ldict;
         this.dispatch__init__(this.args, this.keywords);
      }

      return ldict;
   }

   public int traverse(Visitproc visit, Object arg) {
      int var5;
      int retVal;
      if (this.args != null) {
         PyObject[] var3 = this.args;
         int var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            PyObject ob = var3[var5];
            if (ob != null) {
               retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      Object[] ob0 = (Object[])this.tdict.get();
      if (ob0 != null) {
         Object[] var10 = ob0;
         var5 = ob0.length;

         for(int var11 = 0; var11 < var5; ++var11) {
            Object obj = var10[var11];
            if (obj != null) {
               if (obj instanceof PyObject) {
                  retVal = visit.visit((PyObject)obj, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               } else if (obj instanceof Traverseproc) {
                  retVal = ((Traverseproc)obj).traverse(visit, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) throws UnsupportedOperationException {
      if (ob == null) {
         return false;
      } else {
         if (this.args != null) {
            PyObject[] var2 = this.args;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PyObject obj = var2[var4];
               if (obj == ob) {
                  return true;
               }
            }
         }

         throw new UnsupportedOperationException();
      }
   }

   static {
      PyType.addBuilder(PyLocal.class, new PyExposer());
      TYPE = PyType.fromClass(PyLocal.class);
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyLocal)var1).getDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyLocal)var1).setDict((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyLocal._local___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new __dict___descriptor()};
         super("thread._local", PyLocal.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
