package org.python.objectweb.asm.commons;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.FieldVisitor;
import org.python.objectweb.asm.MethodVisitor;

public class SerialVersionUIDAdder extends ClassVisitor {
   private boolean computeSVUID;
   private boolean hasSVUID;
   private int access;
   private String name;
   private String[] interfaces;
   private Collection svuidFields;
   private boolean hasStaticInitializer;
   private Collection svuidConstructors;
   private Collection svuidMethods;
   // $FF: synthetic field
   static Class class$org$objectweb$asm$commons$SerialVersionUIDAdder = class$("org.python.objectweb.asm.commons.SerialVersionUIDAdder");

   public SerialVersionUIDAdder(ClassVisitor var1) {
      this(327680, var1);
      if (this.getClass() != class$org$objectweb$asm$commons$SerialVersionUIDAdder) {
         throw new IllegalStateException();
      }
   }

   protected SerialVersionUIDAdder(int var1, ClassVisitor var2) {
      super(var1, var2);
      this.svuidFields = new ArrayList();
      this.svuidConstructors = new ArrayList();
      this.svuidMethods = new ArrayList();
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.computeSVUID = (var2 & 16384) == 0;
      if (this.computeSVUID) {
         this.name = var3;
         this.access = var2;
         this.interfaces = new String[var6.length];
         System.arraycopy(var6, 0, this.interfaces, 0, var6.length);
      }

      super.visit(var1, var2, var3, var4, var5, var6);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      if (this.computeSVUID) {
         if ("<clinit>".equals(var2)) {
            this.hasStaticInitializer = true;
         }

         int var6 = var1 & 3391;
         if ((var1 & 2) == 0) {
            if ("<init>".equals(var2)) {
               this.svuidConstructors.add(new SerialVersionUIDAdder$Item(var2, var6, var3));
            } else if (!"<clinit>".equals(var2)) {
               this.svuidMethods.add(new SerialVersionUIDAdder$Item(var2, var6, var3));
            }
         }
      }

      return super.visitMethod(var1, var2, var3, var4, var5);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      if (this.computeSVUID) {
         if ("serialVersionUID".equals(var2)) {
            this.computeSVUID = false;
            this.hasSVUID = true;
         }

         if ((var1 & 2) == 0 || (var1 & 136) == 0) {
            int var6 = var1 & 223;
            this.svuidFields.add(new SerialVersionUIDAdder$Item(var2, var6, var3));
         }
      }

      return super.visitField(var1, var2, var3, var4, var5);
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      if (this.name != null && this.name.equals(var1)) {
         this.access = var4;
      }

      super.visitInnerClass(var1, var2, var3, var4);
   }

   public void visitEnd() {
      if (this.computeSVUID && !this.hasSVUID) {
         try {
            this.addSVUID(this.computeSVUID());
         } catch (Throwable var2) {
            throw new RuntimeException("Error while computing SVUID for " + this.name, var2);
         }
      }

      super.visitEnd();
   }

   public boolean hasSVUID() {
      return this.hasSVUID;
   }

   protected void addSVUID(long var1) {
      FieldVisitor var3 = super.visitField(24, "serialVersionUID", "J", (String)null, new Long(var1));
      if (var3 != null) {
         var3.visitEnd();
      }

   }

   protected long computeSVUID() throws IOException {
      DataOutputStream var1 = null;
      long var2 = 0L;

      try {
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         var1 = new DataOutputStream(var4);
         var1.writeUTF(this.name.replace('/', '.'));
         int var5 = this.access;
         if ((var5 & 512) != 0) {
            var5 = this.svuidMethods.size() > 0 ? var5 | 1024 : var5 & -1025;
         }

         var1.writeInt(var5 & 1553);
         Arrays.sort(this.interfaces);

         for(int var6 = 0; var6 < this.interfaces.length; ++var6) {
            var1.writeUTF(this.interfaces[var6].replace('/', '.'));
         }

         writeItems(this.svuidFields, var1, false);
         if (this.hasStaticInitializer) {
            var1.writeUTF("<clinit>");
            var1.writeInt(8);
            var1.writeUTF("()V");
         }

         writeItems(this.svuidConstructors, var1, true);
         writeItems(this.svuidMethods, var1, true);
         var1.flush();
         byte[] var11 = this.computeSHAdigest(var4.toByteArray());

         for(int var7 = Math.min(var11.length, 8) - 1; var7 >= 0; --var7) {
            var2 = var2 << 8 | (long)(var11[var7] & 255);
         }
      } finally {
         if (var1 != null) {
            var1.close();
         }

      }

      return var2;
   }

   protected byte[] computeSHAdigest(byte[] var1) {
      try {
         return MessageDigest.getInstance("SHA").digest(var1);
      } catch (Exception var3) {
         throw new UnsupportedOperationException(var3.toString());
      }
   }

   private static void writeItems(Collection var0, DataOutput var1, boolean var2) throws IOException {
      int var3 = var0.size();
      SerialVersionUIDAdder$Item[] var4 = (SerialVersionUIDAdder$Item[])var0.toArray(new SerialVersionUIDAdder$Item[var3]);
      Arrays.sort(var4);

      for(int var5 = 0; var5 < var3; ++var5) {
         var1.writeUTF(var4[var5].name);
         var1.writeInt(var4[var5].access);
         var1.writeUTF(var2 ? var4[var5].desc.replace('/', '.') : var4[var5].desc);
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
