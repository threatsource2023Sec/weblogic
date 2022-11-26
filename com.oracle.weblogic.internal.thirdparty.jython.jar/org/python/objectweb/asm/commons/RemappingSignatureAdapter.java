package org.python.objectweb.asm.commons;

import org.python.objectweb.asm.signature.SignatureVisitor;

/** @deprecated */
public class RemappingSignatureAdapter extends SignatureVisitor {
   private final SignatureVisitor v;
   private final Remapper remapper;
   private String className;

   public RemappingSignatureAdapter(SignatureVisitor var1, Remapper var2) {
      this(327680, var1, var2);
   }

   protected RemappingSignatureAdapter(int var1, SignatureVisitor var2, Remapper var3) {
      super(var1);
      this.v = var2;
      this.remapper = var3;
   }

   public void visitClassType(String var1) {
      this.className = var1;
      this.v.visitClassType(this.remapper.mapType(var1));
   }

   public void visitInnerClassType(String var1) {
      String var2 = this.remapper.mapType(this.className) + '$';
      this.className = this.className + '$' + var1;
      String var3 = this.remapper.mapType(this.className);
      int var4 = var3.startsWith(var2) ? var2.length() : var3.lastIndexOf(36) + 1;
      this.v.visitInnerClassType(var3.substring(var4));
   }

   public void visitFormalTypeParameter(String var1) {
      this.v.visitFormalTypeParameter(var1);
   }

   public void visitTypeVariable(String var1) {
      this.v.visitTypeVariable(var1);
   }

   public SignatureVisitor visitArrayType() {
      this.v.visitArrayType();
      return this;
   }

   public void visitBaseType(char var1) {
      this.v.visitBaseType(var1);
   }

   public SignatureVisitor visitClassBound() {
      this.v.visitClassBound();
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.v.visitExceptionType();
      return this;
   }

   public SignatureVisitor visitInterface() {
      this.v.visitInterface();
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.v.visitInterfaceBound();
      return this;
   }

   public SignatureVisitor visitParameterType() {
      this.v.visitParameterType();
      return this;
   }

   public SignatureVisitor visitReturnType() {
      this.v.visitReturnType();
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.v.visitSuperclass();
      return this;
   }

   public void visitTypeArgument() {
      this.v.visitTypeArgument();
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      this.v.visitTypeArgument(var1);
      return this;
   }

   public void visitEnd() {
      this.v.visitEnd();
   }
}
