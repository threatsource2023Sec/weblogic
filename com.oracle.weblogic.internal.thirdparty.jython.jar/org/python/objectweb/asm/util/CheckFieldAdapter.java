package org.python.objectweb.asm.util;

import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.Attribute;
import org.python.objectweb.asm.FieldVisitor;
import org.python.objectweb.asm.TypePath;

public class CheckFieldAdapter extends FieldVisitor {
   private boolean end;
   // $FF: synthetic field
   static Class class$org$objectweb$asm$util$CheckFieldAdapter = class$("org.python.objectweb.asm.util.CheckFieldAdapter");

   public CheckFieldAdapter(FieldVisitor var1) {
      this(327680, var1);
      if (this.getClass() != class$org$objectweb$asm$util$CheckFieldAdapter) {
         throw new IllegalStateException();
      }
   }

   protected CheckFieldAdapter(int var1, FieldVisitor var2) {
      super(var1, var2);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      this.checkEnd();
      CheckMethodAdapter.checkDesc(var1, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(var1, var2));
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.checkEnd();
      int var5 = var1 >>> 24;
      if (var5 != 19) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var5));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(var1, var2);
         CheckMethodAdapter.checkDesc(var3, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(var1, var2, var3, var4));
      }
   }

   public void visitAttribute(Attribute var1) {
      this.checkEnd();
      if (var1 == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(var1);
      }
   }

   public void visitEnd() {
      this.checkEnd();
      this.end = true;
      super.visitEnd();
   }

   private void checkEnd() {
      if (this.end) {
         throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         String var1 = var2.getMessage();
         throw new NoClassDefFoundError(var1);
      }
   }
}
