package com.bea.core.repackaged.springframework.cglib.transform.impl;

import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.cglib.core.EmitUtils;
import com.bea.core.repackaged.springframework.cglib.core.TypeUtils;
import com.bea.core.repackaged.springframework.cglib.transform.ClassEmitterTransformer;
import java.util.Map;

public class AddPropertyTransformer extends ClassEmitterTransformer {
   private final String[] names;
   private final Type[] types;

   public AddPropertyTransformer(Map props) {
      int size = props.size();
      this.names = (String[])((String[])props.keySet().toArray(new String[size]));
      this.types = new Type[size];

      for(int i = 0; i < size; ++i) {
         this.types[i] = (Type)props.get(this.names[i]);
      }

   }

   public AddPropertyTransformer(String[] names, Type[] types) {
      this.names = names;
      this.types = types;
   }

   public void end_class() {
      if (!TypeUtils.isAbstract(this.getAccess())) {
         EmitUtils.add_properties(this, this.names, this.types);
      }

      super.end_class();
   }
}
