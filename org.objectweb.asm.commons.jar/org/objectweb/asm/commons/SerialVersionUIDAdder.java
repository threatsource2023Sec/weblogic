package org.objectweb.asm.commons;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class SerialVersionUIDAdder extends ClassVisitor {
   private static final String CLINIT = "<clinit>";
   private boolean computeSvuid;
   private boolean hasSvuid;
   private int access;
   private String name;
   private String[] interfaces;
   private Collection svuidFields;
   private boolean hasStaticInitializer;
   private Collection svuidConstructors;
   private Collection svuidMethods;

   public SerialVersionUIDAdder(ClassVisitor classVisitor) {
      this(458752, classVisitor);
      if (this.getClass() != SerialVersionUIDAdder.class) {
         throw new IllegalStateException();
      }
   }

   protected SerialVersionUIDAdder(int api, ClassVisitor classVisitor) {
      super(api, classVisitor);
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.computeSvuid = (access & 16384) == 0;
      if (this.computeSvuid) {
         this.name = name;
         this.access = access;
         this.interfaces = new String[interfaces.length];
         this.svuidFields = new ArrayList();
         this.svuidConstructors = new ArrayList();
         this.svuidMethods = new ArrayList();
         System.arraycopy(interfaces, 0, this.interfaces, 0, interfaces.length);
      }

      super.visit(version, access, name, signature, superName, interfaces);
   }

   public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      if (this.computeSvuid) {
         if ("<clinit>".equals(name)) {
            this.hasStaticInitializer = true;
         }

         int mods = access & 3391;
         if ((access & 2) == 0) {
            if ("<init>".equals(name)) {
               this.svuidConstructors.add(new Item(name, mods, descriptor));
            } else if (!"<clinit>".equals(name)) {
               this.svuidMethods.add(new Item(name, mods, descriptor));
            }
         }
      }

      return super.visitMethod(access, name, descriptor, signature, exceptions);
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      if (this.computeSvuid) {
         if ("serialVersionUID".equals(name)) {
            this.computeSvuid = false;
            this.hasSvuid = true;
         }

         if ((access & 2) == 0 || (access & 136) == 0) {
            int mods = access & 223;
            this.svuidFields.add(new Item(name, mods, desc));
         }
      }

      return super.visitField(access, name, desc, signature, value);
   }

   public void visitInnerClass(String innerClassName, String outerName, String innerName, int innerClassAccess) {
      if (this.name != null && this.name.equals(innerClassName)) {
         this.access = innerClassAccess;
      }

      super.visitInnerClass(innerClassName, outerName, innerName, innerClassAccess);
   }

   public void visitEnd() {
      if (this.computeSvuid && !this.hasSvuid) {
         try {
            this.addSVUID(this.computeSVUID());
         } catch (IOException var2) {
            throw new IllegalStateException("Error while computing SVUID for " + this.name, var2);
         }
      }

      super.visitEnd();
   }

   public boolean hasSVUID() {
      return this.hasSvuid;
   }

   protected void addSVUID(long svuid) {
      FieldVisitor fieldVisitor = super.visitField(24, "serialVersionUID", "J", (String)null, svuid);
      if (fieldVisitor != null) {
         fieldVisitor.visitEnd();
      }

   }

   protected long computeSVUID() throws IOException {
      long svuid = 0L;
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      Throwable var4 = null;

      try {
         DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
         Throwable var6 = null;

         try {
            dataOutputStream.writeUTF(this.name.replace('/', '.'));
            int mods = this.access;
            if ((mods & 512) != 0) {
               mods = this.svuidMethods.isEmpty() ? mods & -1025 : mods | 1024;
            }

            dataOutputStream.writeInt(mods & 1553);
            Arrays.sort(this.interfaces);
            String[] var8 = this.interfaces;
            int i = var8.length;

            for(int var10 = 0; var10 < i; ++var10) {
               String interfaceName = var8[var10];
               dataOutputStream.writeUTF(interfaceName.replace('/', '.'));
            }

            writeItems(this.svuidFields, dataOutputStream, false);
            if (this.hasStaticInitializer) {
               dataOutputStream.writeUTF("<clinit>");
               dataOutputStream.writeInt(8);
               dataOutputStream.writeUTF("()V");
            }

            writeItems(this.svuidConstructors, dataOutputStream, true);
            writeItems(this.svuidMethods, dataOutputStream, true);
            dataOutputStream.flush();
            byte[] hashBytes = this.computeSHAdigest(byteArrayOutputStream.toByteArray());

            for(i = Math.min(hashBytes.length, 8) - 1; i >= 0; --i) {
               svuid = svuid << 8 | (long)(hashBytes[i] & 255);
            }
         } catch (Throwable var33) {
            var6 = var33;
            throw var33;
         } finally {
            if (dataOutputStream != null) {
               if (var6 != null) {
                  try {
                     dataOutputStream.close();
                  } catch (Throwable var32) {
                  }
               } else {
                  dataOutputStream.close();
               }
            }

         }
      } catch (Throwable var35) {
         var4 = var35;
         throw var35;
      } finally {
         if (byteArrayOutputStream != null) {
            if (var4 != null) {
               try {
                  byteArrayOutputStream.close();
               } catch (Throwable var31) {
               }
            } else {
               byteArrayOutputStream.close();
            }
         }

      }

      return svuid;
   }

   protected byte[] computeSHAdigest(byte[] value) {
      try {
         return MessageDigest.getInstance("SHA").digest(value);
      } catch (NoSuchAlgorithmException var3) {
         throw new UnsupportedOperationException(var3);
      }
   }

   private static void writeItems(Collection itemCollection, DataOutput dataOutputStream, boolean dotted) throws IOException {
      Item[] items = (Item[])itemCollection.toArray(new Item[0]);
      Arrays.sort(items);
      Item[] var4 = items;
      int var5 = items.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Item item = var4[var6];
         dataOutputStream.writeUTF(item.name);
         dataOutputStream.writeInt(item.access);
         dataOutputStream.writeUTF(dotted ? item.descriptor.replace('/', '.') : item.descriptor);
      }

   }

   private static final class Item implements Comparable {
      final String name;
      final int access;
      final String descriptor;

      Item(String name, int access, String descriptor) {
         this.name = name;
         this.access = access;
         this.descriptor = descriptor;
      }

      public int compareTo(Item item) {
         int result = this.name.compareTo(item.name);
         if (result == 0) {
            result = this.descriptor.compareTo(item.descriptor);
         }

         return result;
      }

      public boolean equals(Object other) {
         if (other instanceof Item) {
            return this.compareTo((Item)other) == 0;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.name.hashCode() ^ this.descriptor.hashCode();
      }
   }
}
