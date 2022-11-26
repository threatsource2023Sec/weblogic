package org.jboss.classfilewriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.annotations.AnnotationsAttribute;
import org.jboss.classfilewriter.attributes.Attribute;
import org.jboss.classfilewriter.attributes.SignatureAttribute;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ClassField implements WritableEntry {
   private final int accessFlags;
   private final String name;
   private final int nameIndex;
   private final String descriptor;
   private final int descriptorIndex;
   private final List attributes = new ArrayList();
   private final ClassFile classFile;
   private final AnnotationsAttribute runtimeVisibleAnnotationsAttribute;
   private SignatureAttribute signatureAttribute;
   private String signature;

   ClassField(int accessFlags, String name, String descriptor, ClassFile classFile, ConstPool constPool) {
      this.accessFlags = accessFlags;
      this.name = name;
      this.descriptor = descriptor;
      this.classFile = classFile;
      this.nameIndex = constPool.addUtf8Entry(name);
      this.descriptorIndex = constPool.addUtf8Entry(descriptor);
      this.runtimeVisibleAnnotationsAttribute = new AnnotationsAttribute(AnnotationsAttribute.Type.RUNTIME_VISIBLE, constPool);
      this.attributes.add(this.runtimeVisibleAnnotationsAttribute);
   }

   public void write(ByteArrayDataOutputStream stream) throws IOException {
      if (this.signatureAttribute != null) {
         this.attributes.add(this.signatureAttribute);
      }

      stream.writeShort(this.accessFlags);
      stream.writeShort(this.nameIndex);
      stream.writeShort(this.descriptorIndex);
      stream.writeShort(this.attributes.size());
      Iterator var2 = this.attributes.iterator();

      while(var2.hasNext()) {
         Attribute attribute = (Attribute)var2.next();
         attribute.write(stream);
      }

   }

   public int getAccessFlags() {
      return this.accessFlags;
   }

   public String getName() {
      return this.name;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public ClassFile getClassFile() {
      return this.classFile;
   }

   public String getSignature() {
      return this.signature;
   }

   public void setSignature(String signature) {
      if (signature == null) {
         this.signatureAttribute = null;
      } else {
         this.signatureAttribute = new SignatureAttribute(this.classFile.getConstPool(), signature);
      }

      this.signature = signature;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.descriptor == null ? 0 : this.descriptor.hashCode());
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ClassField other = (ClassField)obj;
         if (this.descriptor == null) {
            if (other.descriptor != null) {
               return false;
            }
         } else if (!this.descriptor.equals(other.descriptor)) {
            return false;
         }

         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         return true;
      }
   }

   public AnnotationsAttribute getRuntimeVisibleAnnotationsAttribute() {
      return this.runtimeVisibleAnnotationsAttribute;
   }
}
