package com.bea.core.repackaged.springframework.cglib.transform.impl;

import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.CodeEmitter;
import com.bea.core.repackaged.springframework.cglib.core.Constants;
import com.bea.core.repackaged.springframework.cglib.core.Signature;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import com.bea.core.repackaged.springframework.cglib.transform.ClassEmitterTransformer;

public class AccessFieldTransformer extends ClassEmitterTransformer {
   private Callback callback;

   public AccessFieldTransformer(Callback callback) {
      this.callback = callback;
   }

   public void declare_field(int access, String name, Type type, Object value) {
      super.declare_field(access, name, type, value);
      String property = TypeUtils.upperFirst(this.callback.getPropertyName(this.getClassType(), name));
      if (property != null) {
         CodeEmitter e = this.begin_method(1, new Signature("get" + property, type, Constants.TYPES_EMPTY), (Type[])null);
         e.load_this();
         e.getfield(name);
         e.return_value();
         e.end_method();
         e = this.begin_method(1, new Signature("set" + property, Type.VOID_TYPE, new Type[]{type}), (Type[])null);
         e.load_this();
         e.load_arg(0);
         e.putfield(name);
         e.return_value();
         e.end_method();
      }

   }

   public interface Callback {
      String getPropertyName(Type var1, String var2);
   }
}
