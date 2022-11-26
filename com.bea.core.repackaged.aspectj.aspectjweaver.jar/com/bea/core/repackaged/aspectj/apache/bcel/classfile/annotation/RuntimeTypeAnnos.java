package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class RuntimeTypeAnnos extends Attribute {
   private boolean visible;
   private TypeAnnotationGen[] typeAnnotations;
   private byte[] annotation_data;

   public RuntimeTypeAnnos(byte attrid, boolean visible, int nameIdx, int len, ConstantPool cpool) {
      super(attrid, nameIdx, len, cpool);
      this.visible = visible;
   }

   protected void readTypeAnnotations(DataInputStream dis, ConstantPool cpool) throws IOException {
      this.annotation_data = new byte[this.length];
      dis.readFully(this.annotation_data, 0, this.length);
   }

   public final void dump(DataOutputStream dos) throws IOException {
      super.dump(dos);
      this.writeTypeAnnotations(dos);
   }

   protected void writeTypeAnnotations(DataOutputStream dos) throws IOException {
      if (this.typeAnnotations == null) {
         dos.write(this.annotation_data, 0, this.length);
      } else {
         dos.writeShort(this.typeAnnotations.length);

         for(int i = 0; i < this.typeAnnotations.length; ++i) {
            this.typeAnnotations[i].dump(dos);
         }
      }

   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }

   public TypeAnnotationGen[] getTypeAnnotations() {
      this.ensureInflated();
      return this.typeAnnotations;
   }

   public boolean areVisible() {
      return this.visible;
   }

   private void ensureInflated() {
      if (this.typeAnnotations == null) {
         try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(this.annotation_data));
            int numTypeAnnotations = dis.readUnsignedShort();
            if (numTypeAnnotations == 0) {
               this.typeAnnotations = TypeAnnotationGen.NO_TYPE_ANNOTATIONS;
            } else {
               this.typeAnnotations = new TypeAnnotationGen[numTypeAnnotations];

               for(int i = 0; i < numTypeAnnotations; ++i) {
                  this.typeAnnotations[i] = TypeAnnotationGen.read(dis, this.getConstantPool(), this.visible);
               }
            }

         } catch (IOException var4) {
            throw new RuntimeException("Unabled to inflate type annotation data, badly formed?");
         }
      }
   }

   public String toString() {
      return "Runtime" + (this.visible ? "Visible" : "Invisible") + "TypeAnnotations [" + (this.isInflated() ? "inflated" : "not yet inflated") + "]";
   }

   public boolean isInflated() {
      return this.typeAnnotations != null;
   }
}
