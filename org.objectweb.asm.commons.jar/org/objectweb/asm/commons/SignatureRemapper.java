package org.objectweb.asm.commons;

import java.util.ArrayList;
import org.objectweb.asm.signature.SignatureVisitor;

public class SignatureRemapper extends SignatureVisitor {
   private final SignatureVisitor signatureVisitor;
   private final Remapper remapper;
   private ArrayList classNames;

   public SignatureRemapper(SignatureVisitor signatureVisitor, Remapper remapper) {
      this(458752, signatureVisitor, remapper);
   }

   protected SignatureRemapper(int api, SignatureVisitor signatureVisitor, Remapper remapper) {
      super(api);
      this.classNames = new ArrayList();
      this.signatureVisitor = signatureVisitor;
      this.remapper = remapper;
   }

   public void visitClassType(String name) {
      this.classNames.add(name);
      this.signatureVisitor.visitClassType(this.remapper.mapType(name));
   }

   public void visitInnerClassType(String name) {
      String outerClassName = (String)this.classNames.remove(this.classNames.size() - 1);
      String className = outerClassName + '$' + name;
      this.classNames.add(className);
      String remappedOuter = this.remapper.mapType(outerClassName) + '$';
      String remappedName = this.remapper.mapType(className);
      int index = remappedName.startsWith(remappedOuter) ? remappedOuter.length() : remappedName.lastIndexOf(36) + 1;
      this.signatureVisitor.visitInnerClassType(remappedName.substring(index));
   }

   public void visitFormalTypeParameter(String name) {
      this.signatureVisitor.visitFormalTypeParameter(name);
   }

   public void visitTypeVariable(String name) {
      this.signatureVisitor.visitTypeVariable(name);
   }

   public SignatureVisitor visitArrayType() {
      this.signatureVisitor.visitArrayType();
      return this;
   }

   public void visitBaseType(char descriptor) {
      this.signatureVisitor.visitBaseType(descriptor);
   }

   public SignatureVisitor visitClassBound() {
      this.signatureVisitor.visitClassBound();
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.signatureVisitor.visitExceptionType();
      return this;
   }

   public SignatureVisitor visitInterface() {
      this.signatureVisitor.visitInterface();
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.signatureVisitor.visitInterfaceBound();
      return this;
   }

   public SignatureVisitor visitParameterType() {
      this.signatureVisitor.visitParameterType();
      return this;
   }

   public SignatureVisitor visitReturnType() {
      this.signatureVisitor.visitReturnType();
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.signatureVisitor.visitSuperclass();
      return this;
   }

   public void visitTypeArgument() {
      this.signatureVisitor.visitTypeArgument();
   }

   public SignatureVisitor visitTypeArgument(char wildcard) {
      this.signatureVisitor.visitTypeArgument(wildcard);
      return this;
   }

   public void visitEnd() {
      this.signatureVisitor.visitEnd();
      this.classNames.remove(this.classNames.size() - 1);
   }
}
