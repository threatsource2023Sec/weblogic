package org.python.modules.random;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Random;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "_random.Random"
)
public class PyRandom extends PyObject {
   public static final PyType TYPE;
   protected Random javaRandom;

   public PyRandom() {
      this(TYPE);
   }

   public PyRandom(PyType subType) {
      super(subType);
      this.javaRandom = new Random();
   }

   final void Random_seed(PyObject seed) {
      if (seed == null) {
         seed = new PyLong(System.currentTimeMillis());
      }

      long n;
      if (seed instanceof PyLong) {
         PyLong max = new PyLong(Long.MAX_VALUE);
         n = ((PyObject)seed).__mod__(max).asLong();
      } else if (seed instanceof PyInteger) {
         n = ((PyObject)seed).asLong();
      } else {
         n = (long)((PyObject)seed).hashCode();
      }

      this.javaRandom.setSeed(n);
   }

   @ExposedNew
   final void Random___init__(PyObject[] args, String[] keywords) {
   }

   final void Random_jumpahead(PyObject arg0) {
      if (!(arg0 instanceof PyInteger) && !(arg0 instanceof PyLong)) {
         throw Py.TypeError(String.format("jumpahead requires an integer, not '%s'", arg0.getType().fastGetName()));
      } else {
         for(long i = arg0.asLong(); i > 0L; --i) {
            this.javaRandom.nextInt();
         }

      }
   }

   final void Random_setstate(PyObject arg0) {
      if (!(arg0 instanceof PyTuple)) {
         throw Py.TypeError("state vector must be a tuple");
      } else {
         try {
            Object[] arr = ((PyTuple)arg0).toArray();
            byte[] b = new byte[arr.length];

            for(int i = 0; i < arr.length; ++i) {
               if (!(arr[i] instanceof Integer)) {
                  throw Py.TypeError("state vector of unexpected type: " + arr[i].getClass());
               }

               b[i] = ((Integer)arr[i]).byteValue();
            }

            ByteArrayInputStream bin = new ByteArrayInputStream(b);
            ObjectInputStream oin = new ObjectInputStream(bin);
            this.javaRandom = (Random)oin.readObject();
         } catch (IOException var6) {
            throw Py.SystemError("state vector invalid: " + var6.getMessage());
         } catch (ClassNotFoundException var7) {
            throw Py.SystemError("state vector invalid: " + var7.getMessage());
         }
      }
   }

   final PyObject Random_getstate() {
      try {
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         ObjectOutputStream oout = new ObjectOutputStream(bout);
         oout.writeObject(this.javaRandom);
         byte[] b = bout.toByteArray();
         PyInteger[] retarr = new PyInteger[b.length];

         for(int i = 0; i < b.length; ++i) {
            retarr[i] = new PyInteger(b[i]);
         }

         PyTuple ret = new PyTuple(retarr);
         return ret;
      } catch (IOException var6) {
         throw Py.SystemError("creation of state vector failed: " + var6.getMessage());
      }
   }

   final PyObject Random_random() {
      long a = (long)(this.javaRandom.nextInt() >>> 5);
      long b = (long)(this.javaRandom.nextInt() >>> 6);
      double ret = ((double)a * 6.7108864E7 + (double)b) * 1.1102230246251565E-16;
      return new PyFloat(ret);
   }

   final PyLong Random_getrandbits(int k) {
      return new PyLong(new BigInteger(k, this.javaRandom));
   }

   static {
      PyType.addBuilder(PyRandom.class, new PyExposer());
      TYPE = PyType.fromClass(PyRandom.class);
   }

   private static class Random_seed_exposer extends PyBuiltinMethodNarrow {
      public Random_seed_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public Random_seed_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random_seed_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyRandom)this.self).Random_seed(var1);
         return Py.None;
      }

      public PyObject __call__() {
         ((PyRandom)this.self).Random_seed((PyObject)null);
         return Py.None;
      }
   }

   private static class Random___init___exposer extends PyBuiltinMethod {
      public Random___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Random___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyRandom)this.self).Random___init__(var1, var2);
         return Py.None;
      }
   }

   private static class Random_jumpahead_exposer extends PyBuiltinMethodNarrow {
      public Random_jumpahead_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public Random_jumpahead_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random_jumpahead_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyRandom)this.self).Random_jumpahead(var1);
         return Py.None;
      }
   }

   private static class Random_setstate_exposer extends PyBuiltinMethodNarrow {
      public Random_setstate_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public Random_setstate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random_setstate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyRandom)this.self).Random_setstate(var1);
         return Py.None;
      }
   }

   private static class Random_getstate_exposer extends PyBuiltinMethodNarrow {
      public Random_getstate_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Random_getstate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random_getstate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyRandom)this.self).Random_getstate();
      }
   }

   private static class Random_random_exposer extends PyBuiltinMethodNarrow {
      public Random_random_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public Random_random_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random_random_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyRandom)this.self).Random_random();
      }
   }

   private static class Random_getrandbits_exposer extends PyBuiltinMethodNarrow {
      public Random_getrandbits_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public Random_getrandbits_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Random_getrandbits_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyRandom)this.self).Random_getrandbits(Py.py2int(var1));
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyRandom var4 = new PyRandom(this.for_type);
         if (var1) {
            var4.Random___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyRandomDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Random_seed_exposer("seed"), new Random___init___exposer("__init__"), new Random_jumpahead_exposer("jumpahead"), new Random_setstate_exposer("setstate"), new Random_getstate_exposer("getstate"), new Random_random_exposer("random"), new Random_getrandbits_exposer("getrandbits")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("_random.Random", PyRandom.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
