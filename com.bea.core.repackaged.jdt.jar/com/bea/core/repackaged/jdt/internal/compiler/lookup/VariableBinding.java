package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;

public abstract class VariableBinding extends Binding {
   public int modifiers;
   public TypeBinding type;
   public char[] name;
   protected Constant constant;
   public int id;
   public long tagBits;

   public VariableBinding(char[] name, TypeBinding type, int modifiers, Constant constant) {
      this.name = name;
      this.type = type;
      this.modifiers = modifiers;
      this.constant = constant;
      if (type != null) {
         this.tagBits |= type.tagBits & 128L;
      }

   }

   public Constant constant() {
      return this.constant;
   }

   public Constant constant(Scope scope) {
      return this.constant();
   }

   public abstract AnnotationBinding[] getAnnotations();

   public final boolean isBlankFinal() {
      return (this.modifiers & 67108864) != 0;
   }

   public final boolean isFinal() {
      return (this.modifiers & 16) != 0;
   }

   public final boolean isEffectivelyFinal() {
      return (this.tagBits & 2048L) != 0L;
   }

   public boolean isNonNull() {
      return (this.tagBits & 72057594037927936L) != 0L || this.type != null && (this.type.tagBits & 72057594037927936L) != 0L;
   }

   public boolean isNullable() {
      return (this.tagBits & 36028797018963968L) != 0L || this.type != null && (this.type.tagBits & 36028797018963968L) != 0L;
   }

   public char[] readableName() {
      return this.name;
   }

   public void setConstant(Constant constant) {
      this.constant = constant;
   }

   public String toString() {
      StringBuffer output = new StringBuffer(10);
      ASTNode.printModifiers(this.modifiers, output);
      if ((this.modifiers & 33554432) != 0) {
         output.append("[unresolved] ");
      }

      output.append(this.type != null ? this.type.debugName() : "<no type>");
      output.append(" ");
      output.append(this.name != null ? new String(this.name) : "<no name>");
      return output.toString();
   }
}
