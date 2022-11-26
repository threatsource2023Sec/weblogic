package com.bea.core.repackaged.aspectj.weaver.bcel.asm;

import aj.org.objectweb.asm.ClassReader;
import aj.org.objectweb.asm.ClassVisitor;
import aj.org.objectweb.asm.ClassWriter;
import aj.org.objectweb.asm.MethodVisitor;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;

public class StackMapAdder {
   public static byte[] addStackMaps(World world, byte[] data) {
      try {
         ClassReader cr = new ClassReader(data);
         ClassWriter cw = new AspectJConnectClassWriter(cr, world);
         ClassVisitor cv = new AspectJClassVisitor(cw);
         cr.accept(cv, 0);
         return cw.toByteArray();
      } catch (Throwable var5) {
         System.err.println("AspectJ Internal Error: unable to add stackmap attributes. " + var5.getMessage());
         AsmDetector.isAsmAround = false;
         return data;
      }
   }

   private static class AspectJConnectClassWriter extends ClassWriter {
      private final World world;

      public AspectJConnectClassWriter(ClassReader cr, World w) {
         super(cr, 2);
         this.world = w;
      }

      protected String getCommonSuperClass(String type1, String type2) {
         ResolvedType resolvedType1 = this.world.resolve(UnresolvedType.forName(type1.replace('/', '.')));
         ResolvedType resolvedType2 = this.world.resolve(UnresolvedType.forName(type2.replace('/', '.')));
         if (resolvedType1.isAssignableFrom(resolvedType2)) {
            return type1;
         } else if (resolvedType2.isAssignableFrom(resolvedType1)) {
            return type2;
         } else if (!resolvedType1.isInterface() && !resolvedType2.isInterface()) {
            do {
               resolvedType1 = resolvedType1.getSuperclass();
               if (resolvedType1.isParameterizedOrGenericType()) {
                  resolvedType1 = resolvedType1.getRawType();
               }
            } while(!resolvedType1.isAssignableFrom(resolvedType2));

            return resolvedType1.getRawName().replace('.', '/');
         } else {
            return "java/lang/Object";
         }
      }
   }

   private static class AspectJClassVisitor extends ClassVisitor {
      public AspectJClassVisitor(ClassVisitor classwriter) {
         super(327680, classwriter);
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
         return new AJMethodVisitor(mv);
      }

      static class AJMethodVisitor extends MethodVisitor {
         public AJMethodVisitor(MethodVisitor mv) {
            super(327680, mv);
         }
      }
   }
}
