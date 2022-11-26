package org.jboss.classfilewriter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.annotations.AnnotationsAttribute;
import org.jboss.classfilewriter.annotations.ParameterAnnotationsAttribute;
import org.jboss.classfilewriter.attributes.Attribute;
import org.jboss.classfilewriter.attributes.ExceptionsAttribute;
import org.jboss.classfilewriter.attributes.SignatureAttribute;
import org.jboss.classfilewriter.code.CodeAttribute;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class ClassMethod implements WritableEntry {
   private final String returnType;
   private final String[] parameters;
   private final String name;
   private final String descriptor;
   private final int accessFlags;
   private final ClassFile classFile;
   private final int nameIndex;
   private final int descriptorIndex;
   private final List attributes = new ArrayList();
   private final CodeAttribute codeAttribute;
   private final ExceptionsAttribute exceptionsAttribute;
   private final boolean constructor;
   private final AnnotationsAttribute runtimeVisibleAnnotationsAttribute;
   private final ParameterAnnotationsAttribute runtimeVisibleParameterAnnotationsAttribute;
   private SignatureAttribute signatureAttribute;
   private String signature;

   ClassMethod(String name, String returnType, String[] parameters, int accessFlags, ClassFile classFile) {
      ConstPool constPool = classFile.getConstPool();
      this.classFile = classFile;
      this.returnType = DescriptorUtils.validateDescriptor(returnType);
      this.parameters = parameters;
      this.name = name;
      this.descriptor = DescriptorUtils.methodDescriptor(parameters, returnType);
      this.accessFlags = accessFlags;
      this.nameIndex = constPool.addUtf8Entry(name);
      this.descriptorIndex = constPool.addUtf8Entry(this.descriptor);
      this.constructor = name.equals("<init>");
      this.exceptionsAttribute = new ExceptionsAttribute(constPool);
      this.attributes.add(this.exceptionsAttribute);
      if (Modifier.isAbstract(accessFlags)) {
         this.codeAttribute = null;
      } else {
         this.codeAttribute = new CodeAttribute(this, constPool);
         this.attributes.add(this.codeAttribute);
      }

      String[] var7 = this.parameters;
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String param = var7[var9];
         DescriptorUtils.validateDescriptor(param);
      }

      this.runtimeVisibleAnnotationsAttribute = new AnnotationsAttribute(AnnotationsAttribute.Type.RUNTIME_VISIBLE, constPool);
      this.attributes.add(this.runtimeVisibleAnnotationsAttribute);
      this.runtimeVisibleParameterAnnotationsAttribute = new ParameterAnnotationsAttribute(ParameterAnnotationsAttribute.Type.RUNTIME_VISIBLE, constPool, parameters.length);
      this.attributes.add(this.runtimeVisibleParameterAnnotationsAttribute);
   }

   public void addCheckedExceptions(Class... exceptions) {
      Class[] var2 = exceptions;
      int var3 = exceptions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class exception = var2[var4];
         this.exceptionsAttribute.addExceptionClass(exception.getName());
      }

   }

   public void addCheckedExceptions(String... exceptions) {
      String[] var2 = exceptions;
      int var3 = exceptions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String exception = var2[var4];
         this.exceptionsAttribute.addExceptionClass(exception);
      }

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

   public CodeAttribute getCodeAttribute() {
      return this.codeAttribute;
   }

   public int getAccessFlags() {
      return this.accessFlags;
   }

   public String getReturnType() {
      return this.returnType;
   }

   public String[] getParameters() {
      return this.parameters;
   }

   public String getName() {
      return this.name;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public boolean isConstructor() {
      return this.constructor;
   }

   public boolean isStatic() {
      return Modifier.isStatic(this.accessFlags);
   }

   public ClassFile getClassFile() {
      return this.classFile;
   }

   public AnnotationsAttribute getRuntimeVisibleAnnotationsAttribute() {
      return this.runtimeVisibleAnnotationsAttribute;
   }

   public ParameterAnnotationsAttribute getRuntimeVisibleParameterAnnotationsAttribute() {
      return this.runtimeVisibleParameterAnnotationsAttribute;
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
         ClassMethod other = (ClassMethod)obj;
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

   public String toString() {
      return "ClassMethod: " + this.name + this.descriptor;
   }
}
