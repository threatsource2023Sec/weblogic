package org.python.modules.jffi;

import com.kenai.jffi.Library;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "jffi.DynamicLibrary",
   base = PyObject.class
)
public class DynamicLibrary extends PyObject {
   public static final PyType TYPE;
   public final String name;
   private final Library lib;

   DynamicLibrary(String libname, int mode) {
      this.name = libname;
      this.lib = Library.getCachedInstance(libname, mode);
      if (this.lib == null) {
         throw Py.RuntimeError("Could not open " + libname != null ? libname : "[current process] " + Library.getLastError());
      }
   }

   private long findSymbol(PyObject name) {
      long address = this.lib.getSymbolAddress(name.asString());
      if (address == 0L) {
         throw Py.NameError("Could not locate symbol '" + name.asString() + "' in " + this.name);
      } else {
         return address;
      }
   }

   public final PyObject find_symbol(PyObject name) {
      long address = this.findSymbol(name);
      return new Symbol(this, name.asString(), new NativeMemory(address));
   }

   public final PyObject find_function(PyObject name) {
      return new TextSymbol(this, name.asString(), this.findSymbol(name));
   }

   public final PyObject find_variable(PyObject name) {
      return new DataSymbol(this, name.asString(), this.findSymbol(name));
   }

   static {
      PyType.addBuilder(DynamicLibrary.class, new PyExposer());
      TYPE = PyType.fromClass(DynamicLibrary.class);
   }

   private static final class SymbolMemory extends NativeMemory {
      private final DynamicLibrary library;

      public SymbolMemory(DynamicLibrary library, long address) {
         super(address);
         this.library = library;
      }
   }

   @Untraversable
   public static final class DataSymbol extends Symbol implements ExposeAsSuperclass {
      public DataSymbol(DynamicLibrary lib, String name, long address) {
         super(lib, name, new SymbolMemory(lib, address));
      }
   }

   @Untraversable
   public static final class TextSymbol extends Symbol implements ExposeAsSuperclass {
      public TextSymbol(DynamicLibrary lib, String name, long address) {
         super(lib, name, new SymbolMemory(lib, address));
      }
   }

   @Untraversable
   @ExposedType(
      name = "jffi.DynamicLibrary.Symbol",
      base = PyObject.class
   )
   public static class Symbol extends BasePointer {
      public static final PyType TYPE;
      final DynamicLibrary library;
      final DirectMemory memory;
      public final String name;

      public Symbol(DynamicLibrary library, String name, DirectMemory memory) {
         super(TYPE);
         this.library = library;
         this.name = name;
         this.memory = memory;
      }

      public final DirectMemory getMemory() {
         return this.memory;
      }

      static {
         PyType.addBuilder(Symbol.class, new PyExposer());
         TYPE = PyType.fromClass(Symbol.class);
      }

      private static class name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public name_descriptor() {
            super("name", String.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            String var10000 = ((Symbol)var1).name;
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

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
            PyDataDescr[] var2 = new PyDataDescr[]{new name_descriptor()};
            super("jffi.DynamicLibrary.Symbol", Symbol.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
         }
      }
   }

   private static class find_symbol_exposer extends PyBuiltinMethodNarrow {
      public find_symbol_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public find_symbol_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new find_symbol_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((DynamicLibrary)this.self).find_symbol(var1);
      }
   }

   private static class find_function_exposer extends PyBuiltinMethodNarrow {
      public find_function_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public find_function_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new find_function_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((DynamicLibrary)this.self).find_function(var1);
      }
   }

   private static class find_variable_exposer extends PyBuiltinMethodNarrow {
      public find_variable_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public find_variable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new find_variable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((DynamicLibrary)this.self).find_variable(var1);
      }
   }

   private static class name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public name_descriptor() {
         super("name", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((DynamicLibrary)var1).name;
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

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new find_symbol_exposer("find_symbol"), new find_function_exposer("find_function"), new find_variable_exposer("find_variable")};
         PyDataDescr[] var2 = new PyDataDescr[]{new name_descriptor()};
         super("jffi.DynamicLibrary", DynamicLibrary.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
