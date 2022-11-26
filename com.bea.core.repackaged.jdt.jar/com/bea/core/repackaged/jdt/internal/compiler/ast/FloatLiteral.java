package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.impl.FloatConstant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.util.FloatUtil;

public class FloatLiteral extends NumberLiteral {
   float value;

   public FloatLiteral(char[] token, int s, int e) {
      super(token, s, e);
   }

   public void computeConstant() {
      boolean containsUnderscores = CharOperation.indexOf('_', this.source) > 0;
      if (containsUnderscores) {
         this.source = CharOperation.remove(this.source, '_');
      }

      Float computedValue;
      float v;
      try {
         computedValue = Float.valueOf(String.valueOf(this.source));
      } catch (NumberFormatException var7) {
         try {
            v = FloatUtil.valueOfHexFloatLiteral(this.source);
            if (v == Float.POSITIVE_INFINITY) {
               return;
            }

            if (Float.isNaN(v)) {
               return;
            }

            this.value = v;
            this.constant = FloatConstant.fromValue(v);
         } catch (NumberFormatException var6) {
         }

         return;
      }

      v = computedValue;
      if (!(v > Float.MAX_VALUE)) {
         if (v < Float.MIN_VALUE) {
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
         this.constant = FloatConstant.fromValue(this.value);
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
      return TypeBinding.FLOAT;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}
