package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IntConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.ScannerHelper;

public class IntLiteral extends NumberLiteral {
   private static final char[] HEXA_MIN_VALUE = "0x80000000".toCharArray();
   private static final char[] HEXA_MINUS_ONE_VALUE = "0xffffffff".toCharArray();
   private static final char[] OCTAL_MIN_VALUE = "020000000000".toCharArray();
   private static final char[] OCTAL_MINUS_ONE_VALUE = "037777777777".toCharArray();
   private static final char[] DECIMAL_MIN_VALUE = "2147483648".toCharArray();
   private static final char[] DECIMAL_MAX_VALUE = "2147483647".toCharArray();
   private char[] reducedForm;
   public int value;
   public static final IntLiteral One = new IntLiteral(new char[]{'1'}, (char[])null, 0, 0, 1, IntConstant.fromValue(1));

   public static IntLiteral buildIntLiteral(char[] token, int s, int e) {
      char[] intReducedToken = removePrefixZerosAndUnderscores(token, false);
      switch (intReducedToken.length) {
         case 10:
            if (CharOperation.equals(intReducedToken, HEXA_MIN_VALUE)) {
               return new IntLiteralMinValue(token, intReducedToken != token ? intReducedToken : null, s, e);
            }
         case 11:
         default:
            break;
         case 12:
            if (CharOperation.equals(intReducedToken, OCTAL_MIN_VALUE)) {
               return new IntLiteralMinValue(token, intReducedToken != token ? intReducedToken : null, s, e);
            }
      }

      return new IntLiteral(token, intReducedToken != token ? intReducedToken : null, s, e);
   }

   IntLiteral(char[] token, char[] reducedForm, int start, int end) {
      super(token, start, end);
      this.reducedForm = reducedForm;
   }

   IntLiteral(char[] token, char[] reducedForm, int start, int end, int value, Constant constant) {
      super(token, start, end);
      this.reducedForm = reducedForm;
      this.value = value;
      this.constant = constant;
   }

   public void computeConstant() {
      char[] token = this.reducedForm != null ? this.reducedForm : this.source;
      int tokenLength = token.length;
      int radix = 10;
      int j = 0;
      if (token[0] == '0') {
         if (tokenLength == 1) {
            this.constant = IntConstant.fromValue(0);
            return;
         }

         if (token[1] != 'x' && token[1] != 'X') {
            if (token[1] != 'b' && token[1] != 'B') {
               radix = 8;
               j = 1;
            } else {
               radix = 2;
               j = 2;
            }
         } else {
            radix = 16;
            j = 2;
         }
      }

      switch (radix) {
         case 2:
            if (tokenLength - 2 > 32) {
               return;
            }

            this.computeValue(token, tokenLength, radix, j);
            return;
         case 8:
            if (tokenLength <= 12) {
               if (tokenLength == 12 && token[j] > '4') {
                  return;
               }

               if (CharOperation.equals(token, OCTAL_MINUS_ONE_VALUE)) {
                  this.constant = IntConstant.fromValue(-1);
                  return;
               }

               this.computeValue(token, tokenLength, radix, j);
               return;
            }
            break;
         case 10:
            if (tokenLength > DECIMAL_MAX_VALUE.length || tokenLength == DECIMAL_MAX_VALUE.length && CharOperation.compareTo(token, DECIMAL_MAX_VALUE) > 0) {
               return;
            }

            this.computeValue(token, tokenLength, radix, j);
            break;
         case 16:
            if (tokenLength <= 10) {
               if (CharOperation.equals(token, HEXA_MINUS_ONE_VALUE)) {
                  this.constant = IntConstant.fromValue(-1);
                  return;
               }

               this.computeValue(token, tokenLength, radix, j);
               return;
            }
      }

   }

   private void computeValue(char[] token, int tokenLength, int radix, int j) {
      int digitValue;
      int computedValue;
      for(computedValue = 0; j < tokenLength; computedValue = computedValue * radix + digitValue) {
         if ((digitValue = ScannerHelper.digit(token[j++], radix)) < 0) {
            return;
         }
      }

      this.constant = IntConstant.fromValue(computedValue);
   }

   public IntLiteral convertToMinValue() {
      if ((this.bits & 534773760) >> 21 != 0) {
         return this;
      } else {
         char[] token = this.reducedForm != null ? this.reducedForm : this.source;
         switch (token.length) {
            case 10:
               if (CharOperation.equals(token, DECIMAL_MIN_VALUE)) {
                  return new IntLiteralMinValue(this.source, this.reducedForm, this.sourceStart, this.sourceEnd);
               }
            default:
               return this;
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
      return TypeBinding.INT;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}
