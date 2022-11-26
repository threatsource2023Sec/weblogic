package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CharConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.ScannerHelper;

public class CharLiteral extends NumberLiteral {
   char value;

   public CharLiteral(char[] token, int s, int e) {
      super(token, s, e);
      this.computeValue();
   }

   public void computeConstant() {
      this.constant = CharConstant.fromValue(this.value);
   }

   private void computeValue() {
      if ((this.value = this.source[1]) == '\\') {
         char digit;
         switch (digit = this.source[2]) {
            case '"':
               this.value = '"';
               break;
            case '\'':
               this.value = '\'';
               break;
            case '\\':
               this.value = '\\';
               break;
            case 'b':
               this.value = '\b';
               break;
            case 'f':
               this.value = '\f';
               break;
            case 'n':
               this.value = '\n';
               break;
            case 'r':
               this.value = '\r';
               break;
            case 't':
               this.value = '\t';
               break;
            default:
               int number = ScannerHelper.getNumericValue(digit);
               if ((digit = this.source[3]) != '\'') {
                  number = number * 8 + ScannerHelper.getNumericValue(digit);
                  if ((digit = this.source[4]) != '\'') {
                     number = number * 8 + ScannerHelper.getNumericValue(digit);
                  }

                  this.value = (char)number;
               } else {
                  this.constant = CharConstant.fromValue(this.value = (char)number);
               }
         }

      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (valueRequired) {
         codeStream.generateConstant(this.constant, this.implicitConversion);
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public TypeBinding literalType(BlockScope scope) {
      return TypeBinding.CHAR;
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      visitor.visit(this, blockScope);
      visitor.endVisit(this, blockScope);
   }
}
