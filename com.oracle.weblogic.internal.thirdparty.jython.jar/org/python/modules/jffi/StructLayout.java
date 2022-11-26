package org.python.modules.jffi;

import com.kenai.jffi.Struct;
import com.kenai.jffi.Type;
import com.kenai.jffi.Union;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyList;
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
   name = "jffi.StructLayout",
   base = CType.class
)
public class StructLayout extends CType.Custom implements Traverseproc {
   public static final PyType TYPE;
   private final Map fieldMap;
   private final List fields;

   StructLayout(Field[] fields, Type struct, MemoryOp op) {
      super(NativeType.STRUCT, struct, op);
      Map m = new HashMap(fields.length);
      Field[] var5 = fields;
      int var6 = fields.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Field f = var5[var7];
         m.put(f.name, f);
         m.put(f.name.toString(), f);
      }

      this.fieldMap = m;
      this.fields = Collections.unmodifiableList(Arrays.asList(fields));
   }

   @ExposedNew
   public static PyObject StructLayout_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("__init__", args, keywords, new String[]{"fields", "union"}, 1);
      if (!(ap.getPyObject(0) instanceof PyList)) {
         throw Py.TypeError("expected list of jffi.StructLayout.Field");
      } else {
         PyList pyFields = (PyList)ap.getPyObject(0);
         Field[] fields = new Field[pyFields.size()];

         for(int i = 0; i < fields.length; ++i) {
            PyObject pyField = pyFields.pyget(i);
            if (!(pyField instanceof Field)) {
               throw Py.TypeError(String.format("element %d of field list is not an instance of jffi.StructLayout.Field", i));
            }

            fields[i] = (Field)pyField;
         }

         return StructLayout.StructUtil.newStructLayout(fields, ap.getPyObject(1, Py.False).__nonzero__());
      }
   }

   Field getField(PyObject name) {
      return (Field)this.fieldMap.get(name);
   }

   Field getField(String name) {
      return (Field)this.fieldMap.get(name);
   }

   List getFieldList() {
      return this.fields;
   }

   public PyObject __getitem__(PyObject key) {
      Field f = this.getField(key);
      return (PyObject)(f != null ? f : Py.None);
   }

   public int traverse(Visitproc visit, Object arg) {
      int res = false;
      Iterator var4;
      int res;
      if (this.fields != null) {
         var4 = this.fields.iterator();

         while(var4.hasNext()) {
            Field fld = (Field)var4.next();
            res = visit.visit(fld, arg);
            if (res != 0) {
               return res;
            }
         }
      }

      if (this.fieldMap != null) {
         var4 = this.fieldMap.keySet().iterator();

         while(var4.hasNext()) {
            Object key = var4.next();
            if (key instanceof PyObject) {
               res = visit.visit((PyObject)key, arg);
               if (res != 0) {
                  return res;
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else if (this.fields != null && this.fields.contains(ob)) {
         return true;
      } else {
         return this.fieldMap != null && this.fieldMap.containsKey(ob);
      }
   }

   static {
      PyType.addBuilder(StructLayout.class, new PyExposer());
      TYPE = PyType.fromClass(StructLayout.class);
      TYPE.fastGetDict().__setitem__((String)"Field", StructLayout.Field.TYPE);
      TYPE.fastGetDict().__setitem__((String)"ScalarField", StructLayout.ScalarField.TYPE);
   }

   @ExposedType(
      name = "jffi.StructLayout.ScalarField",
      base = Field.class
   )
   public static class ScalarField extends Field {
      public static final PyType TYPE;

      public ScalarField(PyObject name, CType ctype, int offset) {
         super(name, ctype, offset);
      }

      @ExposedNew
      public static PyObject ScalarField_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
         ArgParser ap = new ArgParser("__init__", args, keywords, new String[]{"name", "type", "offset"});
         return new ScalarField(ap.getPyObject(0), CType.typeOf(ap.getPyObject(1)), ap.getInt(2));
      }

      static {
         PyType.addBuilder(ScalarField.class, new PyExposer());
         TYPE = PyType.fromClass(ScalarField.class);
      }

      private static class exposed___new__ extends PyNewWrapper {
         public exposed___new__() {
         }

         public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
            return StructLayout.ScalarField.ScalarField_new(this, var1, var2, var3, var4);
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("jffi.StructLayout.ScalarField", ScalarField.class, Field.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
         }
      }
   }

   private static final class StructUtil {
      public static final StructLayout newStructLayout(Field[] fields, boolean isUnion) {
         Type[] fieldTypes = new Type[fields.length];

         for(int i = 0; i < fields.length; ++i) {
            fieldTypes[i] = Util.jffiType(fields[i].ctype);
         }

         Type jffiType = isUnion ? new Union(fieldTypes) : new Struct(fieldTypes);
         return new StructLayout(fields, (Type)jffiType, MemoryOp.INVALID);
      }
   }

   @ExposedType(
      name = "jffi.StructLayout.Field",
      base = PyObject.class
   )
   public static class Field extends PyObject implements Traverseproc {
      public static final PyType TYPE;
      final CType ctype;
      final int offset;
      final PyObject name;
      final MemoryOp op;

      Field(PyObject name, CType ctype, int offset, MemoryOp op) {
         this.name = name;
         this.ctype = ctype;
         this.offset = offset;
         this.op = op;
      }

      Field(PyObject name, CType ctype, int offset) {
         this(name, ctype, offset, ctype.getMemoryOp());
      }

      private static org.python.modules.jffi.Pointer asPointer(PyObject obj) {
         if (!(obj instanceof org.python.modules.jffi.Pointer)) {
            throw Py.TypeError("expected pointer");
         } else {
            return (org.python.modules.jffi.Pointer)obj;
         }
      }

      public PyObject __get__(PyObject obj, PyObject type) {
         return this.Field___get__(obj, type);
      }

      public void __set__(PyObject obj, PyObject value) {
         this.Field___set__(obj, value);
      }

      public PyObject Field___get__(PyObject obj, PyObject type) {
         return this.op.get(asPointer(obj).getMemory(), (long)this.offset);
      }

      public void Field___set__(PyObject obj, PyObject value) {
         this.op.put(asPointer(obj).getMemory(), (long)this.offset, value);
      }

      PyObject get(PyObject obj) {
         return this.op.get(asPointer(obj).getMemory(), (long)this.offset);
      }

      PyObject set(PyObject obj, PyObject value) {
         this.op.put(asPointer(obj).getMemory(), (long)this.offset, value);
         return value;
      }

      public int traverse(Visitproc visit, Object arg) {
         if (this.name != null) {
            int retVal = visit.visit(this.name, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return this.ctype != null ? visit.visit(this.ctype, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this.name || ob == this.ctype);
      }

      static {
         PyType.addBuilder(Field.class, new PyExposer());
         TYPE = PyType.fromClass(Field.class);
      }

      private static class Field___get___exposer extends PyBuiltinMethodNarrow {
         public Field___get___exposer(String var1) {
            super(var1, 3, 3);
            super.doc = "";
         }

         public Field___get___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new Field___get___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1, PyObject var2) {
            return ((Field)this.self).Field___get__(var1, var2);
         }
      }

      private static class Field___set___exposer extends PyBuiltinMethodNarrow {
         public Field___set___exposer(String var1) {
            super(var1, 3, 3);
            super.doc = "";
         }

         public Field___set___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new Field___set___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1, PyObject var2) {
            ((Field)this.self).Field___set__(var1, var2);
            return Py.None;
         }
      }

      private static class get_exposer extends PyBuiltinMethodNarrow {
         public get_exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "";
         }

         public get_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new get_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            return ((Field)this.self).get(var1);
         }
      }

      private static class set_exposer extends PyBuiltinMethodNarrow {
         public set_exposer(String var1) {
            super(var1, 3, 3);
            super.doc = "";
         }

         public set_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new set_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1, PyObject var2) {
            return ((Field)this.self).set(var1, var2);
         }
      }

      private static class ctype_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public ctype_descriptor() {
            super("ctype", CType.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return ((Field)var1).ctype;
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

      private static class offset_descriptor extends PyDataDescr implements ExposeAsSuperclass {
         public offset_descriptor() {
            super("offset", Integer.class, (String)null);
         }

         public Object invokeGet(PyObject var1) {
            return Py.newInteger(((Field)var1).offset);
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
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Field___get___exposer("__get__"), new Field___set___exposer("__set__"), new get_exposer("get"), new set_exposer("set")};
            PyDataDescr[] var2 = new PyDataDescr[]{new ctype_descriptor(), new offset_descriptor()};
            super("jffi.StructLayout.Field", Field.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
         }
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return StructLayout.StructLayout_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("jffi.StructLayout", StructLayout.class, CType.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
