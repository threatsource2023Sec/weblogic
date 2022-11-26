package org.python.objectweb.asm.signature;

public class SignatureWriter extends SignatureVisitor {
   private final StringBuffer a = new StringBuffer();
   private boolean b;
   private boolean c;
   private int d;

   public SignatureWriter() {
      super(327680);
   }

   public void visitFormalTypeParameter(String var1) {
      if (!this.b) {
         this.b = true;
         this.a.append('<');
      }

      this.a.append(var1);
      this.a.append(':');
   }

   public SignatureVisitor visitClassBound() {
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.a.append(':');
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.a();
      return this;
   }

   public SignatureVisitor visitInterface() {
      return this;
   }

   public SignatureVisitor visitParameterType() {
      this.a();
      if (!this.c) {
         this.c = true;
         this.a.append('(');
      }

      return this;
   }

   public SignatureVisitor visitReturnType() {
      this.a();
      if (!this.c) {
         this.a.append('(');
      }

      this.a.append(')');
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.a.append('^');
      return this;
   }

   public void visitBaseType(char var1) {
      this.a.append(var1);
   }

   public void visitTypeVariable(String var1) {
      this.a.append('T');
      this.a.append(var1);
      this.a.append(';');
   }

   public SignatureVisitor visitArrayType() {
      this.a.append('[');
      return this;
   }

   public void visitClassType(String var1) {
      this.a.append('L');
      this.a.append(var1);
      this.d *= 2;
   }

   public void visitInnerClassType(String var1) {
      this.b();
      this.a.append('.');
      this.a.append(var1);
      this.d *= 2;
   }

   public void visitTypeArgument() {
      if (this.d % 2 == 0) {
         ++this.d;
         this.a.append('<');
      }

      this.a.append('*');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      if (this.d % 2 == 0) {
         ++this.d;
         this.a.append('<');
      }

      if (var1 != '=') {
         this.a.append(var1);
      }

      return this;
   }

   public void visitEnd() {
      this.b();
      this.a.append(';');
   }

   public String toString() {
      return this.a.toString();
   }

   private void a() {
      if (this.b) {
         this.b = false;
         this.a.append('>');
      }

   }

   private void b() {
      if (this.d % 2 != 0) {
         this.a.append('>');
      }

      this.d /= 2;
   }
}
