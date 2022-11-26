package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.impl.DoubleConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.FloatUtil;

public class DoubleLiteral extends NumberLiteral {
   double value;

   public DoubleLiteral(char[] token, int s, int e) {
      super(token, s, e);
   }

   public void computeConstant() {
      boolean containsUnderscores = CharOperation.indexOf('_', this.source) > 0;
      if (containsUnderscores) {
         this.source = CharOperation.remove(this.source, '_');
      }

      Double computedValue;
      double v;
      try {
         computedValue = Double.valueOf(String.valueOf(this.source));
      } catch (NumberFormatException var8) {
         try {
            v = FloatUtil.valueOfHexDoubleLiteral(this.source);
            if (v == Double.POSITIVE_INFINITY) {
               return;
            }

            if (Double.isNaN(v)) {
               return;
            }

            this.value = v;
            this.constant = DoubleConstant.fromValue(v);
         } catch (NumberFormatException var7) {
         }

         return;
      }

      v = computedValue;
      if (!(v > Double.MAX_VALUE)) {
         if (v < Double.MIN_VALUE) {
            boolean isHexaDecimal = false;
            int i = 0;

            label61:
            while(i < this.source.length) {
               switch (this.source[i]) {
                  case 'D':
                  case 'E':
                  case 'F':
                  case 'd':
                  case 'e':
                  case 'f':
                     if (isHexaDecimal) {
                        return;
                     }
                  case 'P':
                  case 'p':
                     break label61;
                  case 'X':
                  case 'x':
                     isHexaDecimal = true;
                  case '.':
                  case '0':
                     ++i;
                     break;
                  default:
                     return;
               }
            }
         }

         this.value = v;
         this.constant = DoubleConstant.fromValue(this.value);
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
      return TypeBinding.DOUBLE;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}
