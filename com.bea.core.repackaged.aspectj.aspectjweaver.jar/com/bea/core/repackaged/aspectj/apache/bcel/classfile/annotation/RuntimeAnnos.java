package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class RuntimeAnnos extends Attribute {
   private List annotations;
   private boolean visible;
   private boolean inflated = false;
   private byte[] annotation_data;

   public RuntimeAnnos(byte attrid, boolean visible, int nameIdx, int len, ConstantPool cpool) {
      super(attrid, nameIdx, len, cpool);
      this.visible = visible;
      this.annotations = new ArrayList();
   }

   public RuntimeAnnos(byte attrid, boolean visible, int nameIdx, int len, byte[] data, ConstantPool cpool) {
      super(attrid, nameIdx, len, cpool);
      this.visible = visible;
      this.annotations = new ArrayList();
      this.annotation_data = data;
   }

   public List getAnnotations() {
      if (!this.inflated) {
         this.inflate();
      }

      return this.annotations;
   }

   public boolean areVisible() {
      return this.visible;
   }

   protected void readAnnotations(DataInputStream dis, ConstantPool cpool) throws IOException {
      this.annotation_data = new byte[this.length];
      dis.readFully(this.annotation_data, 0, this.length);
   }

   protected void writeAnnotations(DataOutputStream dos) throws IOException {
      if (!this.inflated) {
         dos.write(this.annotation_data, 0, this.length);
      } else {
         dos.writeShort(this.annotations.size());
         Iterator i = this.annotations.iterator();

         while(i.hasNext()) {
            AnnotationGen ann = (AnnotationGen)i.next();
            ann.dump(dos);
         }
      }

   }

   private void inflate() {
      try {
         DataInputStream dis = new DataInputStream(new ByteArrayInputStream(this.annotation_data));
         int numberOfAnnotations = dis.readUnsignedShort();
         if (numberOfAnnotations > 0) {
            List inflatedAnnotations = new ArrayList();

            for(int i = 0; i < numberOfAnnotations; ++i) {
               inflatedAnnotations.add(AnnotationGen.read(dis, this.getConstantPool(), this.visible));
            }

            this.annotations = inflatedAnnotations;
         }

         dis.close();
         this.inflated = true;
      } catch (IOException var5) {
         throw new RuntimeException("Unabled to inflate annotation data, badly formed? ");
      }
   }

   public boolean isInflated() {
      return this.inflated;
   }
}
