package com.bea.core.repackaged.springframework.cglib.core;

import com.bea.core.repackaged.springframework.asm.ClassWriter;

public class DefaultGeneratorStrategy implements GeneratorStrategy {
   public static final DefaultGeneratorStrategy INSTANCE = new DefaultGeneratorStrategy();

   public byte[] generate(ClassGenerator cg) throws Exception {
      DebuggingClassWriter cw = this.getClassVisitor();
      this.transform(cg).generateClass(cw);
      return this.transform(cw.toByteArray());
   }

   protected DebuggingClassWriter getClassVisitor() throws Exception {
      return new DebuggingClassWriter(2);
   }

   protected final ClassWriter getClassWriter() {
      throw new UnsupportedOperationException("You are calling getClassWriter, which no longer exists in this cglib version.");
   }

   protected byte[] transform(byte[] b) throws Exception {
      return b;
   }

   protected ClassGenerator transform(ClassGenerator cg) throws Exception {
      return cg;
   }
}
