package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.Type;

public interface FieldTypeCustomizer extends KeyFactoryCustomizer {
   void customize(CodeEmitter var1, int var2, Type var3);

   Type getOutType(int var1, Type var2);
}
