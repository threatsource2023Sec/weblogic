package com.bea.core.repackaged.springframework.cglib.util;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.ClassEmitter;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.Local;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;

class ParallelSorterEmitter extends ClassEmitter {
   private static final Type PARALLEL_SORTER = TypeUtils.parseType("com.bea.core.repackaged.springframework.cglib.util.ParallelSorter");
   private static final Signature CSTRUCT_OBJECT_ARRAY = TypeUtils.parseConstructor("Object[]");
   private static final Signature NEW_INSTANCE;
   private static final Signature SWAP;

   public ParallelSorterEmitter(ClassVisitor v, String className, Object[] arrays) {
      super(v);
      this.begin_class(46, 1, className, PARALLEL_SORTER, (Type[])null, "<generated>");
      EmitUtils.null_constructor(this);
      EmitUtils.factory_method(this, NEW_INSTANCE);
      this.generateConstructor(arrays);
      this.generateSwap(arrays);
      this.end_class();
   }

   private String getFieldName(int index) {
      return "FIELD_" + index;
   }

   private void generateConstructor(Object[] arrays) {
      CodeEmitter e = this.begin_method(1, CSTRUCT_OBJECT_ARRAY, (Type[])null);
      e.load_this();
      e.super_invoke_constructor();
      e.load_this();
      e.load_arg(0);
      e.super_putfield("a", Constants.TYPE_OBJECT_ARRAY);

      for(int i = 0; i < arrays.length; ++i) {
         Type type = Type.getType(arrays[i].getClass());
         this.declare_field(2, this.getFieldName(i), type, (Object)null);
         e.load_this();
         e.load_arg(0);
         e.push(i);
         e.aaload();
         e.checkcast(type);
         e.putfield(this.getFieldName(i));
      }

      e.return_value();
      e.end_method();
   }

   private void generateSwap(Object[] arrays) {
      CodeEmitter e = this.begin_method(1, SWAP, (Type[])null);

      for(int i = 0; i < arrays.length; ++i) {
         Type type = Type.getType(arrays[i].getClass());
         Type component = TypeUtils.getComponentType(type);
         Local T = e.make_local(type);
         e.load_this();
         e.getfield(this.getFieldName(i));
         e.store_local(T);
         e.load_local(T);
         e.load_arg(0);
         e.load_local(T);
         e.load_arg(1);
         e.array_load(component);
         e.load_local(T);
         e.load_arg(1);
         e.load_local(T);
         e.load_arg(0);
         e.array_load(component);
         e.array_store(component);
         e.array_store(component);
      }

      e.return_value();
      e.end_method();
   }

   static {
      NEW_INSTANCE = new Signature("newInstance", PARALLEL_SORTER, new Type[]{Constants.TYPE_OBJECT_ARRAY});
      SWAP = TypeUtils.parseSignature("void swap(int, int)");
   }
}
