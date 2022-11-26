package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class ArrayInitializer extends Expression {
   public Expression[] expressions;
   public ArrayBinding binding;

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (this.expressions != null) {
         CompilerOptions compilerOptions = currentScope.compilerOptions();
         boolean analyseResources = compilerOptions.analyseResourceLeaks;
         boolean evalNullTypeAnnotations = currentScope.environment().usesNullTypeAnnotations();
         int i = 0;

         for(int max = this.expressions.length; i < max; ++i) {
            flowInfo = this.expressions[i].analyseCode(currentScope, flowContext, (FlowInfo)flowInfo).unconditionalInits();
            if (analyseResources && FakedTrackingVariable.isAnyCloseable(this.expressions[i].resolvedType)) {
               flowInfo = FakedTrackingVariable.markPassedToOutside(currentScope, this.expressions[i], (FlowInfo)flowInfo, flowContext, false);
            }

            if (evalNullTypeAnnotations) {
               this.checkAgainstNullTypeAnnotation(currentScope, this.binding.elementsType(), this.expressions[i], flowContext, (FlowInfo)flowInfo);
            }
         }
      }

      return (FlowInfo)flowInfo;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      this.generateCode((TypeReference)null, (ArrayAllocationExpression)null, currentScope, codeStream, valueRequired);
   }

   public void generateCode(TypeReference typeReference, ArrayAllocationExpression allocationExpression, BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      int expressionLength = this.expressions == null ? 0 : this.expressions.length;
      codeStream.generateInlinedValue(expressionLength);
      codeStream.newArray(typeReference, allocationExpression, this.binding);
      if (this.expressions != null) {
         int elementsTypeID = this.binding.dimensions > 1 ? -1 : this.binding.leafComponentType.id;

         for(int i = 0; i < expressionLength; ++i) {
            Expression expr;
            if ((expr = this.expressions[i]).constant != Constant.NotAConstant) {
               switch (elementsTypeID) {
                  case 2:
                  case 3:
                  case 4:
                  case 7:
                  case 10:
                     if (expr.constant.longValue() != 0L) {
                        codeStream.dup();
                        codeStream.generateInlinedValue(i);
                        expr.generateCode(currentScope, codeStream, true);
                        codeStream.arrayAtPut(elementsTypeID, false);
                     }
                     break;
                  case 5:
                     if (expr.constant.booleanValue()) {
                        codeStream.dup();
                        codeStream.generateInlinedValue(i);
                        expr.generateCode(currentScope, codeStream, true);
                        codeStream.arrayAtPut(elementsTypeID, false);
                     }
                     break;
                  case 6:
                  default:
                     if (!(expr instanceof NullLiteral)) {
                        codeStream.dup();
                        codeStream.generateInlinedValue(i);
                        expr.generateCode(currentScope, codeStream, true);
                        codeStream.arrayAtPut(elementsTypeID, false);
                     }
                     break;
                  case 8:
                  case 9:
                     double constantValue = expr.constant.doubleValue();
                     if (constantValue == -0.0 || constantValue != 0.0) {
                        codeStream.dup();
                        codeStream.generateInlinedValue(i);
                        expr.generateCode(currentScope, codeStream, true);
                        codeStream.arrayAtPut(elementsTypeID, false);
                     }
               }
            } else if (!(expr instanceof NullLiteral)) {
               codeStream.dup();
               codeStream.generateInlinedValue(i);
               expr.generateCode(currentScope, codeStream, true);
               codeStream.arrayAtPut(elementsTypeID, false);
            }
         }
      }

      if (valueRequired) {
         codeStream.generateImplicitConversion(this.implicitConversion);
      } else {
         codeStream.pop();
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      output.append('{');
      if (this.expressions != null) {
         int j = 20;

         for(int i = 0; i < this.expressions.length; ++i) {
            if (i > 0) {
               output.append(", ");
            }

            this.expressions[i].printExpression(0, output);
            --j;
            if (j == 0) {
               output.append('\n');
               printIndent(indent + 1, output);
               j = 20;
            }
         }
      }

      return output.append('}');
   }

   public TypeBinding resolveTypeExpecting(BlockScope scope, TypeBinding expectedType) {
      this.constant = Constant.NotAConstant;
      int i;
      if (expectedType instanceof ArrayBinding) {
         TypeBinding elementType;
         if ((this.bits & 1) == 0) {
            elementType = expectedType.leafComponentType();
            if (!elementType.isReifiable()) {
               scope.problemReporter().illegalGenericArray(elementType, this);
            }
         }

         this.resolvedType = this.binding = (ArrayBinding)expectedType;
         if (this.expressions == null) {
            return this.binding;
         } else {
            elementType = this.binding.elementsType();
            i = 0;

            for(int length = this.expressions.length; i < length; ++i) {
               Expression expression = this.expressions[i];
               expression.setExpressionContext(ExpressionContext.ASSIGNMENT_CONTEXT);
               expression.setExpectedType(elementType);
               TypeBinding expressionType = expression instanceof ArrayInitializer ? expression.resolveTypeExpecting(scope, elementType) : expression.resolveType(scope);
               if (expressionType != null) {
                  if (TypeBinding.notEquals(elementType, expressionType)) {
                     scope.compilationUnitScope().recordTypeConversion(elementType, expressionType);
                  }

                  if (!expression.isConstantValueOfTypeAssignableToType(expressionType, elementType) && !expressionType.isCompatibleWith(elementType)) {
                     if (this.isBoxingCompatible(expressionType, elementType, expression, scope)) {
                        expression.computeConversion(scope, elementType, expressionType);
                     } else {
                        scope.problemReporter().typeMismatchError(expressionType, (TypeBinding)elementType, (ASTNode)expression, (ASTNode)null);
                     }
                  } else {
                     expression.computeConversion(scope, elementType, expressionType);
                  }
               }
            }

            return this.binding;
         }
      } else {
         TypeBinding leafElementType = null;
         i = 1;
         if (this.expressions == null) {
            leafElementType = scope.getJavaLangObject();
         } else {
            Expression expression;
            for(expression = this.expressions[0]; expression != null && expression instanceof ArrayInitializer; expression = ((ArrayInitializer)expression).expressions[0]) {
               ++i;
               Expression[] subExprs = ((ArrayInitializer)expression).expressions;
               if (subExprs == null) {
                  leafElementType = scope.getJavaLangObject();
                  expression = null;
                  break;
               }
            }

            if (expression != null) {
               leafElementType = expression.resolveType(scope);
            }

            int i = 1;

            for(int length = this.expressions.length; i < length; ++i) {
               expression = this.expressions[i];
               if (expression != null) {
                  expression.resolveType(scope);
               }
            }
         }

         if (leafElementType != null) {
            this.resolvedType = scope.createArrayType((TypeBinding)leafElementType, i);
            if (expectedType != null) {
               scope.problemReporter().typeMismatchError(this.resolvedType, (TypeBinding)expectedType, (ASTNode)this, (ASTNode)null);
            }
         }

         return null;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope) && this.expressions != null) {
         int expressionsLength = this.expressions.length;

         for(int i = 0; i < expressionsLength; ++i) {
            this.expressions[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }
}
