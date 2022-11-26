package org.python.modules.jffi;

import java.util.List;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinClassMethodNarrow;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "jffi.Structure",
   base = CData.class
)
public class Structure extends CData implements Pointer, Traverseproc {
   public static final PyType TYPE;
   private final StructLayout layout;
   private final MemoryOp memoryOp;

   Structure(PyType pyType, StructLayout layout) {
      this(pyType, layout, AllocatedNativeMemory.allocate(layout.size(), true));
   }

   Structure(PyType pyType, StructLayout layout, Memory m) {
      super(pyType, layout);
      this.layout = layout;
      this.memoryOp = new MemoryOp.StructOp(pyType, layout);
      this.setReferenceMemory(m);
   }

   @ExposedNew
   public static PyObject Structure_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      StructLayout layout = getStructLayout(subtype);
      Structure s = new Structure(subtype, layout);
      if (args.length > 0) {
         int n = args.length - keywords.length;
         List fields = layout.getFieldList();
         Memory m = s.getMemory();

         int i;
         StructLayout.Field f;
         for(i = 0; i < n; ++i) {
            f = (StructLayout.Field)fields.get(i);
            f.op.put(m, (long)f.offset, args[i]);
         }

         for(i = n; i < args.length; ++i) {
            f = layout.getField(keywords[i - n]);
            f.op.put(m, (long)f.offset, args[i]);
         }
      }

      return s;
   }

   static final StructLayout getStructLayout(PyType subtype) {
      PyObject jffi_type = subtype.__getattr__("_jffi_type");
      if (!(jffi_type instanceof StructLayout)) {
         throw Py.TypeError("invalid _jffi_type for " + subtype.fastGetName() + "; should be instance of jffi.StructLayout");
      } else {
         return (StructLayout)jffi_type;
      }
   }

   public static final PyObject from_address(PyType subtype, PyObject address) {
      return new Structure(subtype, getStructLayout(subtype), Util.getMemoryForAddress(address));
   }

   protected final void initReferenceMemory(Memory m) {
      throw Py.RuntimeError("reference memory already initialized");
   }

   MemoryOp getMemoryOp() {
      return this.memoryOp;
   }

   StructLayout.Field getField(PyObject key) {
      StructLayout.Field f = this.layout.getField(key);
      if (f == null) {
         throw Py.NameError(String.format("struct %s has no field '%s'", this.getType().fastGetName(), key.toString()));
      } else {
         return f;
      }
   }

   public PyObject __getitem__(PyObject key) {
      StructLayout.Field f = this.getField(key);
      return f.op.get(this.getReferenceMemory(), (long)f.offset);
   }

   public void __setitem__(PyObject key, PyObject value) {
      StructLayout.Field f = this.getField(key);
      f.op.put(this.getReferenceMemory(), (long)f.offset, value);
   }

   public DirectMemory getMemory() {
      return this.getReferenceMemory();
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.layout != null) {
         int res = visit.visit(this.layout, arg);
         if (res != 0) {
            return res;
         }
      }

      return super.traverse(visit, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.layout == ob ? true : super.refersDirectlyTo(ob);
   }

   static {
      PyType.addBuilder(Structure.class, new PyExposer());
      TYPE = PyType.fromClass(Structure.class);
   }

   private static class from_address_exposer extends PyBuiltinClassMethodNarrow {
      public from_address_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public from_address_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new from_address_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Structure.from_address((PyType)this.self, var1);
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return Structure.Structure_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new from_address_exposer("from_address")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("jffi.Structure", Structure.class, CData.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
