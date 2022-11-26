package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.impl.StringConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class StringLiteral extends Literal {
   char[] source;
   int lineNumber;

   public StringLiteral(char[] token, int start, int end, int lineNumber) {
      this(start, end);
      this.source = token;
      this.lineNumber = lineNumber - 1;
   }

   public StringLiteral(int s, int e) {
      super(s, e);
   }

   public void computeConstant() {
      this.constant = StringConstant.fromValue(String.valueOf(this.source));
   }

   public ExtendedStringLiteral extendWith(CharLiteral lit) {
      return new ExtendedStringLiteral(this, lit);
   }

   public ExtendedStringLiteral extendWith(StringLiteral lit) {
      return new ExtendedStringLiteral(this, lit);
   }

   public StringLiteralConcatenation extendsWith(StringLiteral lit) {
      return new StringLiteralConcatenation(this, lit);
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (valueRequired) {
         codeStream.ldc(this.constant.stringValue());
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public TypeBinding literalType(BlockScope scope) {
      return scope.getJavaLangString();
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      output.append('"');

      for(int i = 0; i < this.source.length; ++i) {
         Util.appendEscapedChar(output, this.source[i], true);
      }

      output.append('"');
      return output;
   }

   public char[] source() {
      return this.source;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}
