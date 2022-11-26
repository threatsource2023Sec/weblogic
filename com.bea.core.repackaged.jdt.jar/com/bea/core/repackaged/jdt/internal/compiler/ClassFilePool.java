package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import java.util.Arrays;

public class ClassFilePool {
   public static final int POOL_SIZE = 25;
   ClassFile[] classFiles = new ClassFile[25];

   private ClassFilePool() {
   }

   public static ClassFilePool newInstance() {
      return new ClassFilePool();
   }

   public synchronized ClassFile acquire(SourceTypeBinding typeBinding) {
      for(int i = 0; i < 25; ++i) {
         ClassFile classFile = this.classFiles[i];
         if (classFile == null) {
            ClassFile newClassFile = new ClassFile(typeBinding);
            this.classFiles[i] = newClassFile;
            newClassFile.isShared = true;
            return newClassFile;
         }

         if (!classFile.isShared) {
            classFile.reset(typeBinding, typeBinding.scope.compilerOptions());
            classFile.isShared = true;
            return classFile;
         }
      }

      return new ClassFile(typeBinding);
   }

   public synchronized ClassFile acquireForModule(ModuleBinding moduleBinding, CompilerOptions options) {
      for(int i = 0; i < 25; ++i) {
         ClassFile classFile = this.classFiles[i];
         if (classFile == null) {
            ClassFile newClassFile = new ClassFile(moduleBinding, options);
            this.classFiles[i] = newClassFile;
            newClassFile.isShared = true;
            return newClassFile;
         }

         if (!classFile.isShared) {
            classFile.reset((SourceTypeBinding)null, options);
            classFile.isShared = true;
            return classFile;
         }
      }

      return new ClassFile(moduleBinding, options);
   }

   public synchronized void release(ClassFile classFile) {
      classFile.isShared = false;
   }

   public void reset() {
      Arrays.fill(this.classFiles, (Object)null);
   }
}
