package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class ArrayReference extends Reference {
   public Expression receiver;
   public Expression position;

   public ArrayReference(Expression rec, Expression pos) {
      this.receiver = rec;
      this.position = pos;
      this.sourceStart = rec.sourceStart;
   }

   public FlowInfo analyseAssignment(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean compoundAssignment) {
      flowContext.recordAbruptExit();
      if (assignment.expression == null) {
         return this.analyseCode(currentScope, flowContext, flowInfo);
      } else {
         flowInfo = assignment.expression.analyseCode(currentScope, flowContext, this.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits());
         if (currentScope.environment().usesNullTypeAnnotations()) {
            this.checkAgainstNullTypeAnnotation(currentScope, this.resolvedType, assignment.expression, flowContext, flowInfo);
         }

         return flowInfo;
      }
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      flowInfo = this.receiver.analyseCode(currentScope, flowContext, flowInfo);
      this.receiver.checkNPE(currentScope, flowContext, flowInfo, 1);
      flowInfo = this.position.analyseCode(currentScope, flowContext, flowInfo);
      this.position.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
      flowContext.recordAbruptExit();
      return flowInfo;
   }

   public boolean checkNPE(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      if ((this.resolvedType.tagBits & 36028797018963968L) != 0L) {
         scope.problemReporter().arrayReferencePotentialNullReference(this);
         return true;
      } else {
         return super.checkNPE(scope, flowContext, flowInfo, ttlForFieldCheck);
      }
   }

   public void generateAssignment(BlockScope currentScope, CodeStream codeStream, Assignment assignment, boolean valueRequired) {
      int pc = codeStream.position;
      this.receiver.generateCode(currentScope, codeStream, true);
      if (this.receiver instanceof CastExpression && ((CastExpression)this.receiver).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
         codeStream.checkcast(this.receiver.resolvedType);
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
      this.position.generateCode(currentScope, codeStream, true);
      assignment.expression.generateCode(currentScope, codeStream, true);
      codeStream.arrayAtPut(this.resolvedType.id, valueRequired);
      if (valueRequired) {
         codeStream.generateImplicitConversion(assignment.implicitConversion);
      }

   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      this.receiver.generateCode(currentScope, codeStream, true);
      if (this.receiver instanceof CastExpression && ((CastExpression)this.receiver).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
         codeStream.checkcast(this.receiver.resolvedType);
      }

      this.position.generateCode(currentScope, codeStream, true);
      codeStream.arrayAt(this.resolvedType.id);
      if (valueRequired) {
         codeStream.generateImplicitConversion(this.implicitConversion);
      } else {
         boolean isUnboxing = (this.implicitConversion & 1024) != 0;
         if (isUnboxing) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         }

         switch (isUnboxing ? this.postConversionType(currentScope).id : this.resolvedType.id) {
            case 7:
            case 8:
               codeStream.pop2();
               break;
            default:
               codeStream.pop();
         }
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public void generateCompoundAssignment(BlockScope currentScope, CodeStream codeStream, Expression expression, int operator, int assignmentImplicitConversion, boolean valueRequired) {
      this.receiver.generateCode(currentScope, codeStream, true);
      if (this.receiver instanceof CastExpression && ((CastExpression)this.receiver).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
         codeStream.checkcast(this.receiver.resolvedType);
      }

      this.position.generateCode(currentScope, codeStream, true);
      codeStream.dup2();
      codeStream.arrayAt(this.resolvedType.id);
      int operationTypeID;
      switch (operationTypeID = (this.implicitConversion & 255) >> 4) {
         case 0:
         case 1:
         case 11:
            codeStream.generateStringConcatenationAppend(currentScope, (Expression)null, expression);
            break;
         default:
            codeStream.generateImplicitConversion(this.implicitConversion);
            if (expression == IntLiteral.One) {
               codeStream.generateConstant(expression.constant, this.implicitConversion);
            } else {
               expression.generateCode(currentScope, codeStream, true);
            }

            codeStream.sendOperator(operator, operationTypeID);
            codeStream.generateImplicitConversion(assignmentImplicitConversion);
      }

      codeStream.arrayAtPut(this.resolvedType.id, valueRequired);
   }

   public void generatePostIncrement(BlockScope currentScope, CodeStream codeStream, CompoundAssignment postIncrement, boolean valueRequired) {
      this.receiver.generateCode(currentScope, codeStream, true);
      if (this.receiver instanceof CastExpression && ((CastExpression)this.receiver).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
         codeStream.checkcast(this.receiver.resolvedType);
      }

      this.position.generateCode(currentScope, codeStream, true);
      codeStream.dup2();
      codeStream.arrayAt(this.resolvedType.id);
      if (valueRequired) {
         switch (this.resolvedType.id) {
            case 7:
            case 8:
               codeStream.dup2_x2();
               break;
            default:
               codeStream.dup_x2();
         }
      }

      codeStream.generateImplicitConversion(this.implicitConversion);
      codeStream.generateConstant(postIncrement.expression.constant, this.implicitConversion);
      codeStream.sendOperator(postIncrement.operator, this.implicitConversion & 15);
      codeStream.generateImplicitConversion(postIncrement.preAssignImplicitConversion);
      codeStream.arrayAtPut(this.resolvedType.id, false);
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      this.receiver.printExpression(0, output).append('[');
      return this.position.printExpression(0, output).append(']');
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      if (this.receiver instanceof CastExpression && ((CastExpression)this.receiver).innermostCastedExpression() instanceof NullLiteral) {
         Expression var10000 = this.receiver;
         var10000.bits |= 32;
      }

      TypeBinding arrayType = this.receiver.resolveType(scope);
      TypeBinding positionType;
      if (arrayType != null) {
         this.receiver.computeConversion(scope, arrayType, arrayType);
         if (arrayType.isArrayType()) {
            positionType = ((ArrayBinding)arrayType).elementsType();
            this.resolvedType = (this.bits & 8192) == 0 ? positionType.capture(scope, this.sourceStart, this.sourceEnd) : positionType;
         } else {
            scope.problemReporter().referenceMustBeArrayTypeAt(arrayType, this);
         }
      }

      positionType = this.position.resolveTypeExpecting(scope, TypeBinding.INT);
      if (positionType != null) {
         this.position.computeConversion(scope, TypeBinding.INT, positionType);
      }

      return this.resolvedType;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.receiver.traverse(visitor, scope);
         this.position.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return this.resolvedType != null && (this.resolvedType.tagBits & 108086391056891904L) == 0L && this.resolvedType.isFreeTypeVariable() ? 48 : super.nullStatus(flowInfo, flowContext);
   }
}
