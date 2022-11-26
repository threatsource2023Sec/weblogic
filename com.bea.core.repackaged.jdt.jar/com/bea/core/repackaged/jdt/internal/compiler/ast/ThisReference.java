package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class ThisReference extends Reference {
   public static ThisReference implicitThis() {
      ThisReference implicitThis = new ThisReference(0, 0);
      implicitThis.bits |= 4;
      return implicitThis;
   }

   public ThisReference(int sourceStart, int sourceEnd) {
      this.sourceStart = sourceStart;
      this.sourceEnd = sourceEnd;
   }

   public FlowInfo analyseAssignment(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean isCompound) {
      return flowInfo;
   }

   public boolean checkAccess(BlockScope scope, ReferenceBinding receiverType) {
      MethodScope methodScope = scope.methodScope();
      if (methodScope.isConstructorCall) {
         methodScope.problemReporter().fieldsOrThisBeforeConstructorInvocation(this);
         return false;
      } else if (methodScope.isStatic) {
         methodScope.problemReporter().errorThisSuperInStatic(this);
         return false;
      } else {
         if (this.isUnqualifiedSuper()) {
            TypeDeclaration type = methodScope.referenceType();
            if (type != null && TypeDeclaration.kind(type.modifiers) == 2) {
               methodScope.problemReporter().errorNoSuperInInterface(this);
               return false;
            }
         }

         if (receiverType != null) {
            scope.tagAsAccessingEnclosingInstanceStateOf(receiverType, false);
         }

         return true;
      }
   }

   public boolean checkNPE(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      return true;
   }

   public void generateAssignment(BlockScope currentScope, CodeStream codeStream, Assignment assignment, boolean valueRequired) {
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (valueRequired) {
         codeStream.aload_0();
      }

      if ((this.bits & 4) == 0) {
         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }

   }

   public void generateCompoundAssignment(BlockScope currentScope, CodeStream codeStream, Expression expression, int operator, int assignmentImplicitConversion, boolean valueRequired) {
   }

   public void generatePostIncrement(BlockScope currentScope, CodeStream codeStream, CompoundAssignment postIncrement, boolean valueRequired) {
   }

   public boolean isImplicitThis() {
      return (this.bits & 4) != 0;
   }

   public boolean isThis() {
      return true;
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return 4;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return this.isImplicitThis() ? output : output.append("this");
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      ReferenceBinding enclosingReceiverType = scope.enclosingReceiverType();
      if (!this.isImplicitThis() && !this.checkAccess(scope, enclosingReceiverType)) {
         return null;
      } else {
         this.resolvedType = enclosingReceiverType;
         MethodScope methodScope = scope.namedMethodScope();
         if (methodScope != null) {
            MethodBinding method = methodScope.referenceMethodBinding();
            if (method != null && method.receiver != null && TypeBinding.equalsEquals(method.receiver, this.resolvedType)) {
               this.resolvedType = method.receiver;
            }
         }

         return this.resolvedType;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      visitor.visit(this, blockScope);
      visitor.endVisit(this, blockScope);
   }

   public void traverse(ASTVisitor visitor, ClassScope blockScope) {
      visitor.visit(this, blockScope);
      visitor.endVisit(this, blockScope);
   }
}
