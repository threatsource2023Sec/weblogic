package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.core.compiler.IProblem;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AND_AND_Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayAllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayInitializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AssertStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Assignment;
import com.bea.core.repackaged.jdt.internal.compiler.ast.BinaryExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Block;
import com.bea.core.repackaged.jdt.internal.compiler.ast.BreakStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CaseStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CastExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CharLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ClassLiteralAccess;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Clinit;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompoundAssignment;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ConditionalExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ConstructorDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ContinueStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.DoStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.DoubleLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.EmptyStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.EqualExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExplicitConstructorCall;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExtendedStringLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FalseLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FloatLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ForStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ForeachStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.IfStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ImportReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Initializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.InstanceOfExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.IntLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.IntersectionCastTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Javadoc;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocAllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocArgumentExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocArrayQualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocArraySingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocFieldReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocImplicitTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocMessageSend;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocQualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocReturnStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocSingleNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.JavadocSingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LabeledStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LocalDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LongLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MarkerAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MemberValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MessageSend;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NormalAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NullLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.OR_OR_Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ParameterizedSingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.PostfixExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.PrefixExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedSuperReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedThisReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReferenceExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReturnStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleMemberAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.StringLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.StringLiteralConcatenation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SuperReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SwitchExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SwitchStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SynchronizedStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ThisReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ThrowStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TrueLiteral;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TryStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.UnaryExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.UnionTypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.WhileStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Wildcard;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;

public abstract class ASTVisitor {
   public void acceptProblem(IProblem problem) {
   }

   public void endVisit(AllocationExpression allocationExpression, BlockScope scope) {
   }

   public void endVisit(AND_AND_Expression and_and_Expression, BlockScope scope) {
   }

   public void endVisit(AnnotationMethodDeclaration annotationTypeDeclaration, ClassScope classScope) {
   }

   public void endVisit(Argument argument, BlockScope scope) {
   }

   public void endVisit(Argument argument, ClassScope scope) {
   }

   public void endVisit(ArrayAllocationExpression arrayAllocationExpression, BlockScope scope) {
   }

   public void endVisit(ArrayInitializer arrayInitializer, BlockScope scope) {
   }

   public void endVisit(ArrayInitializer arrayInitializer, ClassScope scope) {
   }

   public void endVisit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, BlockScope scope) {
   }

   public void endVisit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, ClassScope scope) {
   }

   public void endVisit(ArrayReference arrayReference, BlockScope scope) {
   }

   public void endVisit(ArrayTypeReference arrayTypeReference, BlockScope scope) {
   }

   public void endVisit(ArrayTypeReference arrayTypeReference, ClassScope scope) {
   }

   public void endVisit(AssertStatement assertStatement, BlockScope scope) {
   }

   public void endVisit(Assignment assignment, BlockScope scope) {
   }

   public void endVisit(BinaryExpression binaryExpression, BlockScope scope) {
   }

   public void endVisit(Block block, BlockScope scope) {
   }

   public void endVisit(BreakStatement breakStatement, BlockScope scope) {
   }

   public void endVisit(CaseStatement caseStatement, BlockScope scope) {
   }

   public void endVisit(CastExpression castExpression, BlockScope scope) {
   }

   public void endVisit(CharLiteral charLiteral, BlockScope scope) {
   }

   public void endVisit(ClassLiteralAccess classLiteral, BlockScope scope) {
   }

   public void endVisit(Clinit clinit, ClassScope scope) {
   }

   public void endVisit(CompilationUnitDeclaration compilationUnitDeclaration, CompilationUnitScope scope) {
   }

   public void endVisit(CompoundAssignment compoundAssignment, BlockScope scope) {
   }

   public void endVisit(ConditionalExpression conditionalExpression, BlockScope scope) {
   }

   public void endVisit(ConstructorDeclaration constructorDeclaration, ClassScope scope) {
   }

   public void endVisit(ContinueStatement continueStatement, BlockScope scope) {
   }

   public void endVisit(DoStatement doStatement, BlockScope scope) {
   }

   public void endVisit(DoubleLiteral doubleLiteral, BlockScope scope) {
   }

   public void endVisit(EmptyStatement emptyStatement, BlockScope scope) {
   }

   public void endVisit(EqualExpression equalExpression, BlockScope scope) {
   }

   public void endVisit(ExplicitConstructorCall explicitConstructor, BlockScope scope) {
   }

   public void endVisit(ExtendedStringLiteral extendedStringLiteral, BlockScope scope) {
   }

   public void endVisit(FalseLiteral falseLiteral, BlockScope scope) {
   }

   public void endVisit(FieldDeclaration fieldDeclaration, MethodScope scope) {
   }

   public void endVisit(FieldReference fieldReference, BlockScope scope) {
   }

   public void endVisit(FieldReference fieldReference, ClassScope scope) {
   }

   public void endVisit(FloatLiteral floatLiteral, BlockScope scope) {
   }

   public void endVisit(ForeachStatement forStatement, BlockScope scope) {
   }

   public void endVisit(ForStatement forStatement, BlockScope scope) {
   }

   public void endVisit(IfStatement ifStatement, BlockScope scope) {
   }

   public void endVisit(ImportReference importRef, CompilationUnitScope scope) {
   }

   public void endVisit(Initializer initializer, MethodScope scope) {
   }

   public void endVisit(InstanceOfExpression instanceOfExpression, BlockScope scope) {
   }

   public void endVisit(IntLiteral intLiteral, BlockScope scope) {
   }

   public void endVisit(Javadoc javadoc, BlockScope scope) {
   }

   public void endVisit(Javadoc javadoc, ClassScope scope) {
   }

   public void endVisit(JavadocAllocationExpression expression, BlockScope scope) {
   }

   public void endVisit(JavadocAllocationExpression expression, ClassScope scope) {
   }

   public void endVisit(JavadocArgumentExpression expression, BlockScope scope) {
   }

   public void endVisit(JavadocArgumentExpression expression, ClassScope scope) {
   }

   public void endVisit(JavadocArrayQualifiedTypeReference typeRef, BlockScope scope) {
   }

   public void endVisit(JavadocArrayQualifiedTypeReference typeRef, ClassScope scope) {
   }

   public void endVisit(JavadocArraySingleTypeReference typeRef, BlockScope scope) {
   }

   public void endVisit(JavadocArraySingleTypeReference typeRef, ClassScope scope) {
   }

   public void endVisit(JavadocFieldReference fieldRef, BlockScope scope) {
   }

   public void endVisit(JavadocFieldReference fieldRef, ClassScope scope) {
   }

   public void endVisit(JavadocImplicitTypeReference implicitTypeReference, BlockScope scope) {
   }

   public void endVisit(JavadocImplicitTypeReference implicitTypeReference, ClassScope scope) {
   }

   public void endVisit(JavadocMessageSend messageSend, BlockScope scope) {
   }

   public void endVisit(JavadocMessageSend messageSend, ClassScope scope) {
   }

   public void endVisit(JavadocQualifiedTypeReference typeRef, BlockScope scope) {
   }

   public void endVisit(JavadocQualifiedTypeReference typeRef, ClassScope scope) {
   }

   public void endVisit(JavadocReturnStatement statement, BlockScope scope) {
   }

   public void endVisit(JavadocReturnStatement statement, ClassScope scope) {
   }

   public void endVisit(JavadocSingleNameReference argument, BlockScope scope) {
   }

   public void endVisit(JavadocSingleNameReference argument, ClassScope scope) {
   }

   public void endVisit(JavadocSingleTypeReference typeRef, BlockScope scope) {
   }

   public void endVisit(JavadocSingleTypeReference typeRef, ClassScope scope) {
   }

   public void endVisit(LabeledStatement labeledStatement, BlockScope scope) {
   }

   public void endVisit(LocalDeclaration localDeclaration, BlockScope scope) {
   }

   public void endVisit(LongLiteral longLiteral, BlockScope scope) {
   }

   public void endVisit(MarkerAnnotation annotation, BlockScope scope) {
   }

   public void endVisit(MarkerAnnotation annotation, ClassScope scope) {
   }

   public void endVisit(MemberValuePair pair, BlockScope scope) {
   }

   public void endVisit(MemberValuePair pair, ClassScope scope) {
   }

   public void endVisit(MessageSend messageSend, BlockScope scope) {
   }

   public void endVisit(MethodDeclaration methodDeclaration, ClassScope scope) {
   }

   public void endVisit(StringLiteralConcatenation literal, BlockScope scope) {
   }

   public void endVisit(NormalAnnotation annotation, BlockScope scope) {
   }

   public void endVisit(NormalAnnotation annotation, ClassScope scope) {
   }

   public void endVisit(NullLiteral nullLiteral, BlockScope scope) {
   }

   public void endVisit(OR_OR_Expression or_or_Expression, BlockScope scope) {
   }

   public void endVisit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, BlockScope scope) {
   }

   public void endVisit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, ClassScope scope) {
   }

   public void endVisit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, BlockScope scope) {
   }

   public void endVisit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, ClassScope scope) {
   }

   public void endVisit(PostfixExpression postfixExpression, BlockScope scope) {
   }

   public void endVisit(PrefixExpression prefixExpression, BlockScope scope) {
   }

   public void endVisit(QualifiedAllocationExpression qualifiedAllocationExpression, BlockScope scope) {
   }

   public void endVisit(QualifiedNameReference qualifiedNameReference, BlockScope scope) {
   }

   public void endVisit(QualifiedNameReference qualifiedNameReference, ClassScope scope) {
   }

   public void endVisit(QualifiedSuperReference qualifiedSuperReference, BlockScope scope) {
   }

   public void endVisit(QualifiedSuperReference qualifiedSuperReference, ClassScope scope) {
   }

   public void endVisit(QualifiedThisReference qualifiedThisReference, BlockScope scope) {
   }

   public void endVisit(QualifiedThisReference qualifiedThisReference, ClassScope scope) {
   }

   public void endVisit(QualifiedTypeReference qualifiedTypeReference, BlockScope scope) {
   }

   public void endVisit(QualifiedTypeReference qualifiedTypeReference, ClassScope scope) {
   }

   public void endVisit(ReturnStatement returnStatement, BlockScope scope) {
   }

   public void endVisit(SingleMemberAnnotation annotation, BlockScope scope) {
   }

   public void endVisit(SingleMemberAnnotation annotation, ClassScope scope) {
   }

   public void endVisit(SingleNameReference singleNameReference, BlockScope scope) {
   }

   public void endVisit(SingleNameReference singleNameReference, ClassScope scope) {
   }

   public void endVisit(SingleTypeReference singleTypeReference, BlockScope scope) {
   }

   public void endVisit(SingleTypeReference singleTypeReference, ClassScope scope) {
   }

   public void endVisit(StringLiteral stringLiteral, BlockScope scope) {
   }

   public void endVisit(SuperReference superReference, BlockScope scope) {
   }

   public void endVisit(SwitchStatement switchStatement, BlockScope scope) {
   }

   public void endVisit(SynchronizedStatement synchronizedStatement, BlockScope scope) {
   }

   public void endVisit(ThisReference thisReference, BlockScope scope) {
   }

   public void endVisit(ThisReference thisReference, ClassScope scope) {
   }

   public void endVisit(ThrowStatement throwStatement, BlockScope scope) {
   }

   public void endVisit(TrueLiteral trueLiteral, BlockScope scope) {
   }

   public void endVisit(TryStatement tryStatement, BlockScope scope) {
   }

   public void endVisit(TypeDeclaration localTypeDeclaration, BlockScope scope) {
   }

   public void endVisit(TypeDeclaration memberTypeDeclaration, ClassScope scope) {
   }

   public void endVisit(TypeDeclaration typeDeclaration, CompilationUnitScope scope) {
   }

   public void endVisit(TypeParameter typeParameter, BlockScope scope) {
   }

   public void endVisit(TypeParameter typeParameter, ClassScope scope) {
   }

   public void endVisit(UnaryExpression unaryExpression, BlockScope scope) {
   }

   public void endVisit(UnionTypeReference unionTypeReference, BlockScope scope) {
   }

   public void endVisit(UnionTypeReference unionTypeReference, ClassScope scope) {
   }

   public void endVisit(WhileStatement whileStatement, BlockScope scope) {
   }

   public void endVisit(Wildcard wildcard, BlockScope scope) {
   }

   public void endVisit(Wildcard wildcard, ClassScope scope) {
   }

   public void endVisit(LambdaExpression lambdaExpression, BlockScope blockScope) {
   }

   public void endVisit(ReferenceExpression referenceExpression, BlockScope blockScope) {
   }

   public void endVisit(IntersectionCastTypeReference intersectionCastTypeReference, ClassScope scope) {
   }

   public void endVisit(IntersectionCastTypeReference intersectionCastTypeReference, BlockScope scope) {
   }

   public void endVisit(SwitchExpression switchExpression, BlockScope scope) {
   }

   public boolean visit(AllocationExpression allocationExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(AND_AND_Expression and_and_Expression, BlockScope scope) {
      return true;
   }

   public boolean visit(AnnotationMethodDeclaration annotationTypeDeclaration, ClassScope classScope) {
      return true;
   }

   public boolean visit(Argument argument, BlockScope scope) {
      return true;
   }

   public boolean visit(Argument argument, ClassScope scope) {
      return true;
   }

   public boolean visit(ArrayAllocationExpression arrayAllocationExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(ArrayInitializer arrayInitializer, BlockScope scope) {
      return true;
   }

   public boolean visit(ArrayInitializer arrayInitializer, ClassScope scope) {
      return true;
   }

   public boolean visit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(ArrayReference arrayReference, BlockScope scope) {
      return true;
   }

   public boolean visit(ArrayTypeReference arrayTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(ArrayTypeReference arrayTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(AssertStatement assertStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(Assignment assignment, BlockScope scope) {
      return true;
   }

   public boolean visit(BinaryExpression binaryExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(Block block, BlockScope scope) {
      return true;
   }

   public boolean visit(BreakStatement breakStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(CaseStatement caseStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(CastExpression castExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(CharLiteral charLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(ClassLiteralAccess classLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(Clinit clinit, ClassScope scope) {
      return true;
   }

   public boolean visit(ModuleDeclaration module, CompilationUnitScope scope) {
      return true;
   }

   public boolean visit(CompilationUnitDeclaration compilationUnitDeclaration, CompilationUnitScope scope) {
      return true;
   }

   public boolean visit(CompoundAssignment compoundAssignment, BlockScope scope) {
      return true;
   }

   public boolean visit(ConditionalExpression conditionalExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(ConstructorDeclaration constructorDeclaration, ClassScope scope) {
      return true;
   }

   public boolean visit(ContinueStatement continueStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(DoStatement doStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(DoubleLiteral doubleLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(EmptyStatement emptyStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(EqualExpression equalExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(ExplicitConstructorCall explicitConstructor, BlockScope scope) {
      return true;
   }

   public boolean visit(ExtendedStringLiteral extendedStringLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(FalseLiteral falseLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(FieldDeclaration fieldDeclaration, MethodScope scope) {
      return true;
   }

   public boolean visit(FieldReference fieldReference, BlockScope scope) {
      return true;
   }

   public boolean visit(FieldReference fieldReference, ClassScope scope) {
      return true;
   }

   public boolean visit(FloatLiteral floatLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(ForeachStatement forStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(ForStatement forStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(IfStatement ifStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(ImportReference importRef, CompilationUnitScope scope) {
      return true;
   }

   public boolean visit(Initializer initializer, MethodScope scope) {
      return true;
   }

   public boolean visit(InstanceOfExpression instanceOfExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(IntLiteral intLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(Javadoc javadoc, BlockScope scope) {
      return true;
   }

   public boolean visit(Javadoc javadoc, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocAllocationExpression expression, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocAllocationExpression expression, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocArgumentExpression expression, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocArgumentExpression expression, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocArrayQualifiedTypeReference typeRef, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocArrayQualifiedTypeReference typeRef, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocArraySingleTypeReference typeRef, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocArraySingleTypeReference typeRef, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocFieldReference fieldRef, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocFieldReference fieldRef, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocImplicitTypeReference implicitTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocImplicitTypeReference implicitTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocMessageSend messageSend, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocMessageSend messageSend, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocQualifiedTypeReference typeRef, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocQualifiedTypeReference typeRef, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocReturnStatement statement, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocReturnStatement statement, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocSingleNameReference argument, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocSingleNameReference argument, ClassScope scope) {
      return true;
   }

   public boolean visit(JavadocSingleTypeReference typeRef, BlockScope scope) {
      return true;
   }

   public boolean visit(JavadocSingleTypeReference typeRef, ClassScope scope) {
      return true;
   }

   public boolean visit(LabeledStatement labeledStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(LocalDeclaration localDeclaration, BlockScope scope) {
      return true;
   }

   public boolean visit(LongLiteral longLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(MarkerAnnotation annotation, BlockScope scope) {
      return true;
   }

   public boolean visit(MarkerAnnotation annotation, ClassScope scope) {
      return true;
   }

   public boolean visit(MemberValuePair pair, BlockScope scope) {
      return true;
   }

   public boolean visit(MemberValuePair pair, ClassScope scope) {
      return true;
   }

   public boolean visit(MessageSend messageSend, BlockScope scope) {
      return true;
   }

   public boolean visit(MethodDeclaration methodDeclaration, ClassScope scope) {
      return true;
   }

   public boolean visit(StringLiteralConcatenation literal, BlockScope scope) {
      return true;
   }

   public boolean visit(NormalAnnotation annotation, BlockScope scope) {
      return true;
   }

   public boolean visit(NormalAnnotation annotation, ClassScope scope) {
      return true;
   }

   public boolean visit(NullLiteral nullLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(OR_OR_Expression or_or_Expression, BlockScope scope) {
      return true;
   }

   public boolean visit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(PostfixExpression postfixExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(PrefixExpression prefixExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(QualifiedAllocationExpression qualifiedAllocationExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(QualifiedNameReference qualifiedNameReference, BlockScope scope) {
      return true;
   }

   public boolean visit(QualifiedNameReference qualifiedNameReference, ClassScope scope) {
      return true;
   }

   public boolean visit(QualifiedSuperReference qualifiedSuperReference, BlockScope scope) {
      return true;
   }

   public boolean visit(QualifiedSuperReference qualifiedSuperReference, ClassScope scope) {
      return true;
   }

   public boolean visit(QualifiedThisReference qualifiedThisReference, BlockScope scope) {
      return true;
   }

   public boolean visit(QualifiedThisReference qualifiedThisReference, ClassScope scope) {
      return true;
   }

   public boolean visit(QualifiedTypeReference qualifiedTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(QualifiedTypeReference qualifiedTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(ReturnStatement returnStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(SingleMemberAnnotation annotation, BlockScope scope) {
      return true;
   }

   public boolean visit(SingleMemberAnnotation annotation, ClassScope scope) {
      return true;
   }

   public boolean visit(SingleNameReference singleNameReference, BlockScope scope) {
      return true;
   }

   public boolean visit(SingleNameReference singleNameReference, ClassScope scope) {
      return true;
   }

   public boolean visit(SingleTypeReference singleTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(SingleTypeReference singleTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(StringLiteral stringLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(SuperReference superReference, BlockScope scope) {
      return true;
   }

   public boolean visit(SwitchStatement switchStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(SynchronizedStatement synchronizedStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(ThisReference thisReference, BlockScope scope) {
      return true;
   }

   public boolean visit(ThisReference thisReference, ClassScope scope) {
      return true;
   }

   public boolean visit(ThrowStatement throwStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(TrueLiteral trueLiteral, BlockScope scope) {
      return true;
   }

   public boolean visit(TryStatement tryStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(TypeDeclaration localTypeDeclaration, BlockScope scope) {
      return true;
   }

   public boolean visit(TypeDeclaration memberTypeDeclaration, ClassScope scope) {
      return true;
   }

   public boolean visit(TypeDeclaration typeDeclaration, CompilationUnitScope scope) {
      return true;
   }

   public boolean visit(TypeParameter typeParameter, BlockScope scope) {
      return true;
   }

   public boolean visit(TypeParameter typeParameter, ClassScope scope) {
      return true;
   }

   public boolean visit(UnaryExpression unaryExpression, BlockScope scope) {
      return true;
   }

   public boolean visit(UnionTypeReference unionTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(UnionTypeReference unionTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(WhileStatement whileStatement, BlockScope scope) {
      return true;
   }

   public boolean visit(Wildcard wildcard, BlockScope scope) {
      return true;
   }

   public boolean visit(Wildcard wildcard, ClassScope scope) {
      return true;
   }

   public boolean visit(LambdaExpression lambdaExpression, BlockScope blockScope) {
      return true;
   }

   public boolean visit(ReferenceExpression referenceExpression, BlockScope blockScope) {
      return true;
   }

   public boolean visit(IntersectionCastTypeReference intersectionCastTypeReference, ClassScope scope) {
      return true;
   }

   public boolean visit(IntersectionCastTypeReference intersectionCastTypeReference, BlockScope scope) {
      return true;
   }

   public boolean visit(SwitchExpression switchExpression, BlockScope blockScope) {
      return true;
   }
}
