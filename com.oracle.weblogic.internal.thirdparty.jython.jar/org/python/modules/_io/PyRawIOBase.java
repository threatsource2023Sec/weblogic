package org.python.modules._io;

import org.python.core.Py;
import org.python.core.PyBuffer;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyByteArray;
import org.python.core.PyDataDescr;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_io._RawIOBase",
   doc = "Base class for raw binary I/O.",
   base = PyIOBase.class
)
public class PyRawIOBase extends PyIOBase {
   public static final PyType TYPE;
   public static final String read_doc = "Read up to n bytes from the object and return them.\nAs a convenience, if n is unspecified or -1, readall() is called.";
   public static final String readall_doc = "Read and return all the bytes from the stream until EOF, using multiple\ncalls to the stream if necessary.";
   public static final String readinto_doc = "Read up to len(b) bytes into bytearray b and return the number of bytes read.\nIf the object is in non-blocking mode and no bytes are available,\nNone is returned.";
   public static final String write_doc = "Write the given bytes or bytearray object, b, to the underlying raw\nstream and return the number of bytes written.";
   static final String doc = "Base class for raw binary I/O.";

   public PyRawIOBase() {
      this(TYPE);
   }

   public PyRawIOBase(PyType subtype) {
      super(subtype);
   }

   @ExposedNew
   static PyObject _RawIOBase__new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      return (PyObject)(new_.for_type == subtype ? new PyRawIOBase() : new PyRawIOBaseDerived(subtype));
   }

   public PyObject read(int n) {
      return this._read(n);
   }

   final PyObject _RawIOBase_read(PyObject n) {
      if (n != null && n != Py.None) {
         if (n.isIndex()) {
            return this._read(n.asInt());
         } else {
            throw tailoredTypeError("integer", n);
         }
      } else {
         return this._read(-1);
      }
   }

   private PyObject _read(int n) {
      if (n < 0) {
         return this.invoke("readall");
      } else {
         PyByteArray b = new PyByteArray(n);
         PyObject m = this.invoke("readinto", b);
         if (m.isIndex()) {
            int count = m.asIndex();
            PyBuffer view = b.getBuffer(284);
            if (count < n) {
               view = view.getBufferSlice(284, 0, count);
            }

            return new PyString(view.toString());
         } else {
            return m;
         }
      }
   }

   public PyObject readall() {
      return this._RawIOBase_readall();
   }

   final synchronized PyObject _RawIOBase_readall() {
      PyObject readMethod = this.__getattr__("read");
      PyObject prev = readMethod.__call__((PyObject)_jyio.DEFAULT_BUFFER_SIZE);
      if (!prev.__nonzero__()) {
         return prev;
      } else {
         PyObject curr = readMethod.__call__((PyObject)_jyio.DEFAULT_BUFFER_SIZE);
         if (!curr.__nonzero__()) {
            return prev;
         } else {
            PyList list = new PyList();
            list.add(prev);

            do {
               list.add(curr);
               curr = readMethod.__call__((PyObject)_jyio.DEFAULT_BUFFER_SIZE);
            } while(curr.__nonzero__());

            return Py.EmptyString.join(list);
         }
      }
   }

   public PyObject readinto(PyObject b) {
      return this._RawIOBase_readinto(b);
   }

   final synchronized PyLong _RawIOBase_readinto(PyObject b) {
      throw this.unsupported("readinto");
   }

   public PyObject write(PyObject b) {
      return this._RawIOBase_write(b);
   }

   final PyLong _RawIOBase_write(PyObject b) {
      throw this.unsupported("write");
   }

   static {
      PyType.addBuilder(PyRawIOBase.class, new PyExposer());
      TYPE = PyType.fromClass(PyRawIOBase.class);
   }

   private static class _RawIOBase_read_exposer extends PyBuiltinMethodNarrow {
      public _RawIOBase_read_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "Read up to n bytes from the object and return them.\nAs a convenience, if n is unspecified or -1, readall() is called.";
      }

      public _RawIOBase_read_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Read up to n bytes from the object and return them.\nAs a convenience, if n is unspecified or -1, readall() is called.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _RawIOBase_read_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyRawIOBase)this.self)._RawIOBase_read(var1);
      }

      public PyObject __call__() {
         return ((PyRawIOBase)this.self)._RawIOBase_read((PyObject)null);
      }
   }

   private static class _RawIOBase_readall_exposer extends PyBuiltinMethodNarrow {
      public _RawIOBase_readall_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Read and return all the bytes from the stream until EOF, using multiple\ncalls to the stream if necessary.";
      }

      public _RawIOBase_readall_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Read and return all the bytes from the stream until EOF, using multiple\ncalls to the stream if necessary.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _RawIOBase_readall_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyRawIOBase)this.self)._RawIOBase_readall();
      }
   }

   private static class _RawIOBase_readinto_exposer extends PyBuiltinMethodNarrow {
      public _RawIOBase_readinto_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Read up to len(b) bytes into bytearray b and return the number of bytes read.\nIf the object is in non-blocking mode and no bytes are available,\nNone is returned.";
      }

      public _RawIOBase_readinto_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Read up to len(b) bytes into bytearray b and return the number of bytes read.\nIf the object is in non-blocking mode and no bytes are available,\nNone is returned.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _RawIOBase_readinto_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyRawIOBase)this.self)._RawIOBase_readinto(var1);
      }
   }

   private static class _RawIOBase_write_exposer extends PyBuiltinMethodNarrow {
      public _RawIOBase_write_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Write the given bytes or bytearray object, b, to the underlying raw\nstream and return the number of bytes written.";
      }

      public _RawIOBase_write_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Write the given bytes or bytearray object, b, to the underlying raw\nstream and return the number of bytes written.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _RawIOBase_write_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyRawIOBase)this.self)._RawIOBase_write(var1);
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyRawIOBase._RawIOBase__new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new _RawIOBase_read_exposer("read"), new _RawIOBase_readall_exposer("readall"), new _RawIOBase_readinto_exposer("readinto"), new _RawIOBase_write_exposer("write")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("_io._RawIOBase", PyRawIOBase.class, PyIOBase.class, (boolean)1, "Base class for raw binary I/O.", var1, var2, new exposed___new__());
      }
   }
}
