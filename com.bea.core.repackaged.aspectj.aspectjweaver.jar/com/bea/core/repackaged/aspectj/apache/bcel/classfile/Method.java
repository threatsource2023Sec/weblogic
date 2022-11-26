package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Method extends FieldOrMethod {
   public static final AnnotationGen[][] NO_PARAMETER_ANNOTATIONS = new AnnotationGen[0][];
   public static final Method[] NoMethods = new Method[0];
   private boolean parameterAnnotationsOutOfDate = true;
   private AnnotationGen[][] unpackedParameterAnnotations;

   private Method() {
      this.parameterAnnotationsOutOfDate = true;
   }

   public Method(Method c) {
      super(c);
      this.parameterAnnotationsOutOfDate = true;
   }

   Method(DataInputStream file, ConstantPool constant_pool) throws IOException {
      super(file, constant_pool);
   }

   public Method(int access_flags, int name_index, int signature_index, Attribute[] attributes, ConstantPool constant_pool) {
      super(access_flags, name_index, signature_index, attributes, constant_pool);
      this.parameterAnnotationsOutOfDate = true;
   }

   public void accept(ClassVisitor v) {
      v.visitMethod(this);
   }

   public void setAttributes(Attribute[] attributes) {
      this.parameterAnnotationsOutOfDate = true;
      super.setAttributes(attributes);
   }

   public final Code getCode() {
      return AttributeUtils.getCodeAttribute(this.attributes);
   }

   public final ExceptionTable getExceptionTable() {
      return AttributeUtils.getExceptionTableAttribute(this.attributes);
   }

   public final LocalVariableTable getLocalVariableTable() {
      Code code = this.getCode();
      return code != null ? code.getLocalVariableTable() : null;
   }

   public final LineNumberTable getLineNumberTable() {
      Code code = this.getCode();
      return code != null ? code.getLineNumberTable() : null;
   }

   public final String toString() {
      String access = Utility.accessToString(this.modifiers);
      ConstantUtf8 c = (ConstantUtf8)this.cpool.getConstant(this.signatureIndex, (byte)1);
      String signature = c.getValue();
      c = (ConstantUtf8)this.cpool.getConstant(this.nameIndex, (byte)1);
      String name = c.getValue();
      signature = Utility.methodSignatureToString(signature, name, access, true, this.getLocalVariableTable());
      StringBuffer buf = new StringBuffer(signature);

      for(int i = 0; i < this.attributes.length; ++i) {
         Attribute a = this.attributes[i];
         if (!(a instanceof Code) && !(a instanceof ExceptionTable)) {
            buf.append(" [" + a.toString() + "]");
         }
      }

      ExceptionTable e = this.getExceptionTable();
      if (e != null) {
         String str = e.toString();
         if (!str.equals("")) {
            buf.append("\n\t\tthrows " + str);
         }
      }

      return buf.toString();
   }

   public Type getReturnType() {
      return Type.getReturnType(this.getSignature());
   }

   public Type[] getArgumentTypes() {
      return Type.getArgumentTypes(this.getSignature());
   }

   private void ensureParameterAnnotationsUnpacked() {
      if (this.parameterAnnotationsOutOfDate) {
         this.parameterAnnotationsOutOfDate = false;
         int parameterCount = this.getArgumentTypes().length;
         if (parameterCount == 0) {
            this.unpackedParameterAnnotations = NO_PARAMETER_ANNOTATIONS;
         } else {
            RuntimeVisParamAnnos parameterAnnotationsVis = null;
            RuntimeInvisParamAnnos parameterAnnotationsInvis = null;
            Attribute[] attrs = this.getAttributes();

            for(int i = 0; i < attrs.length; ++i) {
               Attribute attribute = attrs[i];
               if (attribute instanceof RuntimeVisParamAnnos) {
                  parameterAnnotationsVis = (RuntimeVisParamAnnos)attribute;
               } else if (attribute instanceof RuntimeInvisParamAnnos) {
                  parameterAnnotationsInvis = (RuntimeInvisParamAnnos)attribute;
               }
            }

            boolean foundSome = false;
            if (parameterAnnotationsInvis != null || parameterAnnotationsVis != null) {
               List annotationsForEachParameter = new ArrayList();
               AnnotationGen[] visibleOnes = null;
               AnnotationGen[] invisibleOnes = null;

               for(int i = 0; i < parameterCount; ++i) {
                  int count = 0;
                  visibleOnes = new AnnotationGen[0];
                  invisibleOnes = new AnnotationGen[0];
                  if (parameterAnnotationsVis != null) {
                     visibleOnes = parameterAnnotationsVis.getAnnotationsOnParameter(i);
                     count += visibleOnes.length;
                  }

                  if (parameterAnnotationsInvis != null) {
                     invisibleOnes = parameterAnnotationsInvis.getAnnotationsOnParameter(i);
                     count += invisibleOnes.length;
                  }

                  AnnotationGen[] complete = AnnotationGen.NO_ANNOTATIONS;
                  if (count != 0) {
                     complete = new AnnotationGen[visibleOnes.length + invisibleOnes.length];
                     System.arraycopy(visibleOnes, 0, complete, 0, visibleOnes.length);
                     System.arraycopy(invisibleOnes, 0, complete, visibleOnes.length, invisibleOnes.length);
                     foundSome = true;
                  }

                  annotationsForEachParameter.add(complete);
               }

               if (foundSome) {
                  this.unpackedParameterAnnotations = (AnnotationGen[][])annotationsForEachParameter.toArray(new AnnotationGen[0][]);
                  return;
               }
            }

            this.unpackedParameterAnnotations = NO_PARAMETER_ANNOTATIONS;
         }
      }
   }

   public AnnotationGen[] getAnnotationsOnParameter(int i) {
      this.ensureParameterAnnotationsUnpacked();
      return this.unpackedParameterAnnotations == NO_PARAMETER_ANNOTATIONS ? AnnotationGen.NO_ANNOTATIONS : this.unpackedParameterAnnotations[i];
   }

   public AnnotationGen[][] getParameterAnnotations() {
      this.ensureParameterAnnotationsUnpacked();
      return this.unpackedParameterAnnotations;
   }
}
