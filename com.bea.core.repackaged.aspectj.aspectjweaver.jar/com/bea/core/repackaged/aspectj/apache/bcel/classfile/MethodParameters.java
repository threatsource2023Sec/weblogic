package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MethodParameters extends Attribute {
   public static final int[] NO_PARAMETER_NAME_INDEXES = new int[0];
   public static final int[] NO_PARAMETER_ACCESS_FLAGS = new int[0];
   public static final int ACCESS_FLAGS_FINAL = 16;
   public static final int ACCESS_FLAGS_SYNTHETIC = 4096;
   public static final int ACCESS_FLAGS_MANDATED = 32768;
   private boolean isInPackedState = false;
   private byte[] data;
   private int[] names;
   private int[] accessFlags;

   public MethodParameters(int index, int length, DataInputStream dis, ConstantPool cpool) throws IOException {
      super((byte)22, index, length, cpool);
      this.data = new byte[length];
      dis.readFully(this.data, 0, length);
      this.isInPackedState = true;
   }

   private void ensureInflated() {
      if (this.names == null) {
         try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(this.data));
            int parametersCount = dis.readUnsignedByte();
            if (parametersCount == 0) {
               this.names = NO_PARAMETER_NAME_INDEXES;
               this.accessFlags = NO_PARAMETER_ACCESS_FLAGS;
            } else {
               this.names = new int[parametersCount];
               this.accessFlags = new int[parametersCount];

               for(int i = 0; i < parametersCount; ++i) {
                  this.names[i] = dis.readUnsignedShort();
                  this.accessFlags[i] = dis.readUnsignedShort();
               }
            }

            this.isInPackedState = false;
         } catch (IOException var4) {
            throw new RuntimeException("Unabled to inflate type annotation data, badly formed?");
         }
      }
   }

   public void dump(DataOutputStream dos) throws IOException {
      super.dump(dos);
      if (this.isInPackedState) {
         dos.write(this.data);
      } else {
         dos.writeByte(this.names.length);

         for(int i = 0; i < this.names.length; ++i) {
            dos.writeShort(this.names[i]);
            dos.writeShort(this.accessFlags[i]);
         }
      }

   }

   public int getParametersCount() {
      this.ensureInflated();
      return this.names.length;
   }

   public String getParameterName(int parameter) {
      this.ensureInflated();
      ConstantUtf8 c = (ConstantUtf8)this.cpool.getConstant(this.names[parameter], (byte)1);
      return c.getValue();
   }

   public int getAccessFlags(int parameter) {
      this.ensureInflated();
      return this.accessFlags[parameter];
   }

   public boolean isFinal(int parameter) {
      return (this.getAccessFlags(parameter) & 16) != 0;
   }

   public boolean isSynthetic(int parameter) {
      return (this.getAccessFlags(parameter) & 4096) != 0;
   }

   public boolean isMandated(int parameter) {
      return (this.getAccessFlags(parameter) & '耀') != 0;
   }

   public void accept(ClassVisitor v) {
      v.visitMethodParameters(this);
   }
}
