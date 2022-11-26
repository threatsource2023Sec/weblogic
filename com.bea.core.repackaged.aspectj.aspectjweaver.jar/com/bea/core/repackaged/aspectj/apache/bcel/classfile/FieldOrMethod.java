package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FieldOrMethod extends Modifiers implements Node {
   protected int nameIndex;
   protected int signatureIndex;
   protected Attribute[] attributes;
   protected ConstantPool cpool;
   private String name;
   private String signature;
   private AnnotationGen[] annotations;
   private String signatureAttributeString;
   private boolean searchedForSignatureAttribute;

   protected FieldOrMethod() {
      this.signatureAttributeString = null;
      this.searchedForSignatureAttribute = false;
   }

   protected FieldOrMethod(FieldOrMethod c) {
      this(c.getModifiers(), c.getNameIndex(), c.getSignatureIndex(), c.getAttributes(), c.getConstantPool());
   }

   protected FieldOrMethod(DataInputStream file, ConstantPool cpool) throws IOException {
      this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), (Attribute[])null, cpool);
      this.attributes = AttributeUtils.readAttributes(file, cpool);
   }

   protected FieldOrMethod(int accessFlags, int nameIndex, int signatureIndex, Attribute[] attributes, ConstantPool cpool) {
      this.signatureAttributeString = null;
      this.searchedForSignatureAttribute = false;
      this.modifiers = accessFlags;
      this.nameIndex = nameIndex;
      this.signatureIndex = signatureIndex;
      this.cpool = cpool;
      this.attributes = attributes;
   }

   public void setAttributes(Attribute[] attributes) {
      this.attributes = attributes;
   }

   public final void dump(DataOutputStream file) throws IOException {
      file.writeShort(this.modifiers);
      file.writeShort(this.nameIndex);
      file.writeShort(this.signatureIndex);
      AttributeUtils.writeAttributes(this.attributes, file);
   }

   public final Attribute[] getAttributes() {
      return this.attributes;
   }

   public final ConstantPool getConstantPool() {
      return this.cpool;
   }

   public final int getNameIndex() {
      return this.nameIndex;
   }

   public final int getSignatureIndex() {
      return this.signatureIndex;
   }

   public final String getName() {
      if (this.name == null) {
         ConstantUtf8 c = (ConstantUtf8)this.cpool.getConstant(this.nameIndex, (byte)1);
         this.name = c.getValue();
      }

      return this.name;
   }

   public final String getSignature() {
      if (this.signature == null) {
         ConstantUtf8 c = (ConstantUtf8)this.cpool.getConstant(this.signatureIndex, (byte)1);
         this.signature = c.getValue();
      }

      return this.signature;
   }

   public final String getDeclaredSignature() {
      return this.getGenericSignature() != null ? this.getGenericSignature() : this.getSignature();
   }

   public AnnotationGen[] getAnnotations() {
      if (this.annotations == null) {
         List accumulatedAnnotations = new ArrayList();

         for(int i = 0; i < this.attributes.length; ++i) {
            Attribute attribute = this.attributes[i];
            if (attribute instanceof RuntimeAnnos) {
               RuntimeAnnos runtimeAnnotations = (RuntimeAnnos)attribute;
               accumulatedAnnotations.addAll(runtimeAnnotations.getAnnotations());
            }
         }

         if (accumulatedAnnotations.size() == 0) {
            this.annotations = AnnotationGen.NO_ANNOTATIONS;
         } else {
            this.annotations = (AnnotationGen[])accumulatedAnnotations.toArray(new AnnotationGen[0]);
         }
      }

      return this.annotations;
   }

   public final String getGenericSignature() {
      if (!this.searchedForSignatureAttribute) {
         Signature sig = AttributeUtils.getSignatureAttribute(this.attributes);
         this.signatureAttributeString = sig == null ? null : sig.getSignature();
         this.searchedForSignatureAttribute = true;
      }

      return this.signatureAttributeString;
   }
}
