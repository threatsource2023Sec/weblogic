package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class RuntimeParamAnnos extends Attribute {
   private List parameterAnnotations;
   private boolean visible;
   private boolean inflated = false;
   private byte[] annotation_data;

   public RuntimeParamAnnos(byte attrid, boolean visible, int nameIdx, int len, ConstantPool cpool) {
      super(attrid, nameIdx, len, cpool);
      this.visible = visible;
      this.parameterAnnotations = new ArrayList();
   }

   public RuntimeParamAnnos(byte attrid, boolean visible, int nameIdx, int len, byte[] data, ConstantPool cpool) {
      super(attrid, nameIdx, len, cpool);
      this.visible = visible;
      this.parameterAnnotations = new ArrayList();
      this.annotation_data = data;
   }

   public final void dump(DataOutputStream dos) throws IOException {
      super.dump(dos);
      this.writeAnnotations(dos);
   }

   public Attribute copy(ConstantPool constant_pool) {
      throw new RuntimeException("Not implemented yet!");
   }

   public List getParameterAnnotations() {
      if (!this.inflated) {
         this.inflate();
      }

      return this.parameterAnnotations;
   }

   public AnnotationGen[] getAnnotationsOnParameter(int parameterIndex) {
      if (!this.inflated) {
         this.inflate();
      }

      return parameterIndex >= this.parameterAnnotations.size() ? AnnotationGen.NO_ANNOTATIONS : (AnnotationGen[])this.parameterAnnotations.get(parameterIndex);
   }

   public boolean areVisible() {
      return this.visible;
   }

   protected void readParameterAnnotations(DataInputStream dis, ConstantPool cpool) throws IOException {
      this.annotation_data = new byte[this.length];
      dis.readFully(this.annotation_data, 0, this.length);
   }

   private void inflate() {
      try {
         DataInputStream dis = new DataInputStream(new ByteArrayInputStream(this.annotation_data));
         int numParameters = dis.readUnsignedByte();
         if (numParameters > 0) {
            List inflatedParameterAnnotations = new ArrayList();

            for(int i = 0; i < numParameters; ++i) {
               int numAnnotations = dis.readUnsignedShort();
               if (numAnnotations == 0) {
                  inflatedParameterAnnotations.add(AnnotationGen.NO_ANNOTATIONS);
               } else {
                  AnnotationGen[] annotations = new AnnotationGen[numAnnotations];

                  for(int j = 0; j < numAnnotations; ++j) {
                     annotations[j] = AnnotationGen.read(dis, this.getConstantPool(), this.visible);
                  }

                  inflatedParameterAnnotations.add(annotations);
               }
            }

            this.parameterAnnotations = inflatedParameterAnnotations;
         }

         this.inflated = true;
      } catch (IOException var8) {
         throw new RuntimeException("Unabled to inflate annotation data, badly formed?");
      }
   }

   protected void writeAnnotations(DataOutputStream dos) throws IOException {
      if (!this.inflated) {
         dos.write(this.annotation_data, 0, this.length);
      } else {
         dos.writeByte(this.parameterAnnotations.size());

         for(int i = 0; i < this.parameterAnnotations.size(); ++i) {
            AnnotationGen[] annotations = (AnnotationGen[])this.parameterAnnotations.get(i);
            dos.writeShort(annotations.length);

            for(int j = 0; j < annotations.length; ++j) {
               annotations[j].dump(dos);
            }
         }
      }

   }

   public boolean isInflated() {
      return this.inflated;
   }

   public String toString() {
      return "Runtime" + (this.visible ? "Visible" : "Invisible") + "ParameterAnnotations [" + (this.inflated ? "inflated" : "not yet inflated") + "]";
   }
}
