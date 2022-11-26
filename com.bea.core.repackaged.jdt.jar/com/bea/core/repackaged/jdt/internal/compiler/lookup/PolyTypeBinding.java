package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;

public class PolyTypeBinding extends TypeBinding {
   Expression expression;
   boolean vanillaCompatibilty = true;

   public PolyTypeBinding(Expression expression) {
      this.expression = expression;
   }

   public char[] constantPoolName() {
      throw new UnsupportedOperationException();
   }

   public PackageBinding getPackage() {
      throw new UnsupportedOperationException();
   }

   public boolean isCompatibleWith(TypeBinding left, Scope scope) {
      return this.vanillaCompatibilty ? this.expression.isCompatibleWith(left, scope) : this.expression.isBoxingCompatibleWith(left, scope);
   }

   public boolean isPotentiallyCompatibleWith(TypeBinding targetType, Scope scope) {
      return this.expression.isPotentiallyCompatibleWith(targetType, scope);
   }

   public boolean isPolyType() {
      return true;
   }

   public boolean isFunctionalType() {
      return this.expression.isFunctionalType();
   }

   public char[] qualifiedSourceName() {
      return this.readableName();
   }

   public char[] sourceName() {
      return this.readableName();
   }

   public char[] readableName() {
      return this.expression.printExpression(0, new StringBuffer()).toString().toCharArray();
   }

   public char[] shortReadableName() {
      return this.expression instanceof LambdaExpression ? ((LambdaExpression)this.expression).printExpression(0, new StringBuffer(), true).toString().toCharArray() : this.readableName();
   }

   public boolean sIsMoreSpecific(TypeBinding s, TypeBinding t, Scope scope) {
      return this.expression.sIsMoreSpecific(s, t, scope);
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer("PolyTypeBinding for: ");
      return this.expression.printExpression(0, buffer).toString();
   }

   public int kind() {
      return 65540;
   }

   public TypeBinding computeBoxingType() {
      PolyTypeBinding type = new PolyTypeBinding(this.expression);
      type.vanillaCompatibilty = !this.vanillaCompatibilty;
      return type;
   }
}
