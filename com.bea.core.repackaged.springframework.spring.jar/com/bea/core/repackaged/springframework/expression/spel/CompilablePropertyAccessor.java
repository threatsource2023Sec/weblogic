package com.bea.core.repackaged.springframework.expression.spel;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Opcodes;
import com.bea.core.repackaged.springframework.expression.PropertyAccessor;

public interface CompilablePropertyAccessor extends PropertyAccessor, Opcodes {
   boolean isCompilable();

   Class getPropertyType();

   void generateCode(String var1, MethodVisitor var2, CodeFlow var3);
}
